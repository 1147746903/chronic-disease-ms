package com.comvee.cdms.actiontrack.core;

import com.comvee.cdms.actiontrack.mapper.ActionTrackConfigMapper;
import com.comvee.cdms.actiontrack.model.ActionTrackConfigPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class ActionTrackConfig{

    private final static Logger log = LoggerFactory.getLogger(ActionTrackConfig.class);

    private static final Map<String ,String> CONFIG = new ConcurrentHashMap();

    private static volatile Boolean INIT_FLAG = false;

    @Autowired
    private ActionTrackConfigMapper actionTrackConfigMapper;

    @PostConstruct
    private void init(){
        synchronized (INIT_FLAG){
            if(INIT_FLAG){
                return;
            }
            initData();
            INIT_FLAG = true;
            new Thread(new ReloadActionTrackConfigRunnable())
                    .start();
        }
    }

    public Map<String ,String> getConfig(){
        return CONFIG;
    }

    private void initData(){
        Map<String ,String> configMap = loadData();
        if(configMap == null || configMap.isEmpty()){
            log.info("行为追踪配置为空，请确认下配置");
            return;
        }
        CONFIG.putAll(configMap);
        log.info("初始化行为追踪配置成功，当前配置数量为:{}" ,CONFIG.size());
    }

    private void reloadData(){
        Map<String ,String> configMap = loadData();
        CONFIG.clear();
        CONFIG.putAll(configMap);
    }

    private Map<String ,String> loadData(){
        Map<String ,String> result = new HashMap<>();
        List<ActionTrackConfigPO> list = this.actionTrackConfigMapper.listActionTrackConfig();
        if(list == null || list.isEmpty()){
            return result;
        }
        for(ActionTrackConfigPO config : list){
            String[] arr = config.getRequestUri().split(",");
            for(String str : arr){
                result.put(str ,config.getActionName());
            }
        }
        return result;
    }

    class ReloadActionTrackConfigRunnable implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    TimeUnit.MINUTES.sleep(5L);
                    reloadData();
                } catch (Exception e) {
                }
            }
        }
    }


}
