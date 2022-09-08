package com.comvee.cdms.dybloodsugar.task;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.dybloodsugar.service.DyBloodSugarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service("dynamicBloodSugarPushHandler")
public class DynamicBloodSugarPushHandler {

    private final static Logger log = LoggerFactory.getLogger(DynamicBloodSugarPushHandler.class);

    /**
     * 存放探头最后一次上传的时间戳
     */
    private final static Map<String ,Long> SENSOR_LAST_UPLOAD_TIME = new ConcurrentHashMap<>();
    /**
     * 存放设备电量提醒信息
     */
    private final static Map<String ,String> MACHINE_LAST_EQ_REMIND = new ConcurrentHashMap<>();

    /**
     * 线程启动标识
     */
    private static volatile boolean RUNNABLE_FLAG = false;

    /**
     * 可推送的间隔时间
     */
    private final static long AVAILABLE_PUSH_INTERVAL = 10 * 1000;

    @Autowired
    private DyBloodSugarService dyBloodSugarService;

    /**
     * 刷新探头最新上传时间戳
     * @param sensorNo
     */
    public void refreshSensorLatestTimestamp(String sensorNo){
        SENSOR_LAST_UPLOAD_TIME.put(sensorNo ,System.currentTimeMillis());
    }


    /**
     * 设备设备电量提醒信息
     * @param sensorNo
     */
    public void refreshMachineLastElectricQuantity(String sensorNo, String machineEqRemind){
        MACHINE_LAST_EQ_REMIND.put(sensorNo , machineEqRemind);
    }

    @PostConstruct
    public void init(){
        synchronized (this){
            if(!RUNNABLE_FLAG){
                Thread thread = new Thread(new MessagePushJob());
                thread.start();
                RUNNABLE_FLAG = true;
            }
        }
    }

    class MessagePushJob implements Runnable{

        @Override
        public void run() {
            log.info("探头推送线程启动成功...");
            while(true){
                try{
                    pushHandler();
                }catch (Exception e){
                    log.error("动态血糖上传推送处理失败" ,e);
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                }
            }
        }

        private void pushHandler(){
            int size = SENSOR_LAST_UPLOAD_TIME.size();
            if(size == 0){
                return;
            }
            log.info("目前待处理推送逻辑的探头数量:"  + size);
            Iterator iterator = SENSOR_LAST_UPLOAD_TIME.entrySet().iterator();
            long currentTimeMillis = System.currentTimeMillis();
            while(iterator.hasNext()){
                Map.Entry<String ,Long> entry = (Map.Entry<String, Long>) iterator.next();
                if((currentTimeMillis - entry.getValue()) >=  AVAILABLE_PUSH_INTERVAL){
                    try{
                        String machineEqRemind = MACHINE_LAST_EQ_REMIND.get(entry.getKey());
                        dyBloodSugarService.uploadDynamicBloodSugarHandler(entry.getKey() ,entry.getValue(), machineEqRemind);
                        log.info("动态血糖上传事件成功，探头号:{}" ,entry.getKey());
                    }catch (Exception e){
                        log.error("动态血糖上传事件处理失败, 探头号:{},最后上传时间:{}"
                                ,entry.getKey()
                                ,DateHelper.getDate(new Date(entry.getValue()) ,"yyyy-MM-dd HH:mm:ss")
                                ,e);
                    }finally {
                        iterator.remove();
                        MACHINE_LAST_EQ_REMIND.remove(entry.getKey());
//                        SENSOR_LAST_UPLOAD_TIME.remove(entry.getKey());
                    }
                }
            }
        }
    }

}
