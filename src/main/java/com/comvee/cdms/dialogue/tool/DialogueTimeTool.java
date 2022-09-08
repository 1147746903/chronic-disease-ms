package com.comvee.cdms.dialogue.tool;

import com.comvee.cdms.dialogue.constant.DialogueConstant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: suyz
 * @date: 2019/1/25
 */
public class DialogueTimeTool {

    /**
     * MAX_SIZE  缓存最大数量
     * EXPIRE_DURATION 过期时间
     */
    private final static int MAX_SIZE = 10000;
    private final static int EXPIRE_DURATION = 5;

    /**
     * 医生最新消息时间戳
     */
    private static final Cache<String,Long> DOCTOR_LATEST_TIME = CacheBuilder.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterAccess(EXPIRE_DURATION, TimeUnit.MINUTES)
            .build();

    /**
     * 患者最新消息时间戳
     */
    private static final Cache<String,Long> MEMBER_LATEST_TIME = CacheBuilder.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterAccess(EXPIRE_DURATION, TimeUnit.MINUTES)
            .build();

    /**
     * 医患对话最新消息时间戳
     */
    private static final Cache<String,Long> DOCTOR_MEMBER_DIALOGUE_TIME = CacheBuilder.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterAccess(EXPIRE_DURATION, TimeUnit.MINUTES)
            .build();

    /**
     * 新消息的时间点缓存
     * @param doctorId
     * @param memberId
     * @param ownerType
     * @param timestamp
     */
    public static void newDialogueTimeCache(String doctorId, String memberId, Integer ownerType, long timestamp){
        String dialogueKey = doctorId.concat("-").concat(memberId);
        DOCTOR_MEMBER_DIALOGUE_TIME.put(dialogueKey, timestamp);
        if(DialogueConstant.DIALOGUE_OWNER_TYPE_MEMBER == ownerType){
            DOCTOR_LATEST_TIME.put(doctorId, timestamp);
        }else{
            MEMBER_LATEST_TIME.put(memberId, timestamp);
        }
    }

    /**
     * 患者新消息时间比较
     * @param memberId
     * @param timestamp
     * @return 需要查询数据库返回true
     */
    public static boolean memberNewDialogueCompare(String memberId, Long timestamp){
        //没有刷新时间，说明首次加载，直接从数据库取
        if(timestamp == null){
            return true;
        }
        Long cacheTimestamp = MEMBER_LATEST_TIME.getIfPresent(memberId);
        if(cacheTimestamp == null){
            MEMBER_LATEST_TIME.put(memberId, timestamp);
            return true;
        }
        if(cacheTimestamp > timestamp){
            return true;
        }
        return false;
    }

    /**
     * 医生新消息时间比较
     * @param doctorId
     * @param timestamp
     * @return 需要查询数据库返回true
     */
    public static boolean doctorNewDialogueCompare(String doctorId, Long timestamp){
        //没有刷新时间，说明首次加载，直接从数据库取
        if(timestamp == null){
            return true;
        }
        Long cacheTimestamp = DOCTOR_LATEST_TIME.getIfPresent(doctorId);
        if(cacheTimestamp == null){
            DOCTOR_LATEST_TIME.put(doctorId, timestamp);
            return true;
        }
        if(cacheTimestamp > timestamp){
            return true;
        }
        return false;
    }

    /**
     * 医生新消息时间比较
     * @param doctorId
     * @param timestamp
     * @return 需要查询数据库返回true
     */
    public static boolean doctorListNewDialogueCompare(List<String> doctorList, Long timestamp){
        //没有刷新时间，说明首次加载，直接从数据库取
        if(timestamp == null){
            return true;
        }
        long count = doctorList.stream().filter(x -> doctorNewDialogueCompare(x, timestamp)).count();
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 对话新消息时间比较
     * @param doctorId
     * @param memberId
     * @param timestamp
     * @return 需要查询数据库返回true
     */
    public static boolean newDialogueCompare(String doctorId, String memberId, Long timestamp){
        //没有刷新时间，说明首次加载，直接从数据库取
        if(timestamp == null){
            return true;
        }
        String dialogueKey = doctorId.concat("-").concat(memberId);
        Long cacheTimestamp = DOCTOR_MEMBER_DIALOGUE_TIME.getIfPresent(dialogueKey);
        if(cacheTimestamp == null){
            DOCTOR_MEMBER_DIALOGUE_TIME.put(dialogueKey, timestamp);
            return true;
        }
        if(cacheTimestamp > timestamp){
            return true;
        }
        return false;
    }
}
