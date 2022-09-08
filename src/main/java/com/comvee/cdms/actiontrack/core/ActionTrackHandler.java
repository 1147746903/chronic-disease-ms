package com.comvee.cdms.actiontrack.core;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.actiontrack.mapper.ActionTrackRecordMapper;
import com.comvee.cdms.actiontrack.model.AddActionTrackRecordPO;
import com.comvee.cdms.actiontrack.model.NewAction;
import com.comvee.cdms.actiontrack.tool.UriTool;
import com.comvee.cdms.common.utils.RequestTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service("actionTrackHandler")
public class ActionTrackHandler {

    private final static Logger log = LoggerFactory.getLogger(ActionTrackHandler.class);

    private final static BlockingQueue<NewAction> QUEUE = new ArrayBlockingQueue(10000);

    private final ThreadLocal<UserInfo> USER_INFO_CACHE = new ThreadLocal();

    private static Thread CONSUMER_THREAD = null;

    @Autowired
    private ActionTrackConfig actionTrackConfig;

    @Autowired
    private SessionSupport sessionSupport;

    @Autowired
    private ActionTrackRecordMapper actionTrackRecordMapper;

    @PostConstruct
    public void init(){
        synchronized (ActionTrackHandler.class){
            if(CONSUMER_THREAD != null){
                return;
            }
            CONSUMER_THREAD = new Thread(new ActionTrackConsumer());
            CONSUMER_THREAD.start();
            log.info("行为跟踪消费者线程启动成功...");
        }
    }

    /**
     * 判断是否支持的uri
     * @param uri
     * @return
     */
    public boolean support(String uri){
        if(uri == null){
            return false;
        }
        Map<String ,String> config = actionTrackConfig.getConfig();
        return config.containsKey(UriTool.uriHandler(uri));
    }

    /**
     * 获取行为名称
     * @param uri
     * @return
     */
    public String getActionName(String uri){
        Map<String ,String> config = actionTrackConfig.getConfig();
        return config.get(UriTool.uriHandler(uri));
    }

    /**
     * 组装新动作对象
     * @param request
     * @param returnObject
     * @return
     */
    public NewAction assembleNewAction(HttpServletRequest request ,Object returnObject){
        String uri = UriTool.uriHandler(request.getServletPath());
        UserInfo userInfo = USER_INFO_CACHE.get();
        if(userInfo == null){
            log.warn("行为追踪无法获取到当前操作的用户信息！url:{}" ,uri);
            return null;
        }
        NewAction newAction = new NewAction();
        newAction.setUri(uri);
        newAction.setIp(RequestTool.getIpAddr(request));
        newAction.setRequestData(RequestTool.getParameterMap(request));
        newAction.setResponseData(returnObject);
        newAction.setActionName(getActionName(uri));
        newAction.setActionDt(new Date());
        newAction.setIdentify(userInfo.getIdentify());
        newAction.setUid(userInfo.getUid());
        return newAction;
    }

    /**
     * 新动作添加
     * @param newAction
     */
    public void newAction(NewAction newAction){
        if(newAction == null){
            return;
        }
        if(!QUEUE.offer(newAction)){
            log.warn("添加行为跟踪数据到队列失败，数据内容:{}" ,JSON.toJSONString(newAction));
        }
    }

    public void cacheUserInfo(){
        if(USER_INFO_CACHE.get() != null){
            return;
        }
        Subject subject = SecurityUtils.getSubject();
        if(subject == null || subject.getPrincipal() == null){
            return;
        }
        Integer identify = this.sessionSupport.getIdentify(subject.getPrincipal());
        String uid = this.sessionSupport.getUid(subject.getPrincipal());
        if(identify == null || uid == null || uid.length() == 0){
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setIdentify(identify);
        userInfo.setUid(uid);
        USER_INFO_CACHE.set(userInfo);
    }

    public void clearCache(){
        USER_INFO_CACHE.remove();
    }

    /**
     * 将新动作对象转化成入库对象
     * @param newAction
     * @return
     */
    private AddActionTrackRecordPO castNewActionToAddActionTrackRecord(NewAction newAction){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AddActionTrackRecordPO result = new AddActionTrackRecordPO();
        result.setUid(newAction.getUid());
        result.setIdentify(newAction.getIdentify());
        result.setActionName(newAction.getActionName());
        result.setActionDt(sdf.format(newAction.getActionDt()));
        result.setRequestUri(newAction.getUri());
        result.setIp(newAction.getIp());
        result.setRequestData(JSON.toJSONString(newAction.getRequestData()));
        result.setResponseData(JSON.toJSONString(newAction.getResponseData()));
        return result;
    }

    /**
     * 行为跟踪消费线程
     */
    class ActionTrackConsumer implements Runnable{

        @Override
        public void run() {
            while(true){
                try{
                    consumerQueue();
                }catch (Exception e){
                    log.error("消费行为跟踪数据失败" ,e);
                }
                try {
                    TimeUnit.SECONDS.sleep(0);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /**
     * 消费队列
     */
    private void consumerQueue(){
        int emptyTimes = 0;
        List<AddActionTrackRecordPO> list = new ArrayList<>();
        NewAction newAction = null;
        do{
            try {
                newAction = QUEUE.poll(3 , TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                newAction = null;
            }
            if(newAction == null){
                emptyTimes ++;
            }else{
                list.add(castNewActionToAddActionTrackRecord(newAction));
            }
        }
        //当数据长度大于20或者取3次空的数据的时候入库
        while (list.size() < 20 && emptyTimes < 3);
        if(list.isEmpty()){
            return;
        }
        this.actionTrackRecordMapper.batchAddActionTrackRecord(list);
    }

    class UserInfo{
        private Integer identify;
        private String uid;

        public Integer getIdentify() {
            return identify;
        }

        public void setIdentify(Integer identify) {
            this.identify = identify;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
