package com.comvee.cdms.prescription.cfg;

import com.comvee.cdms.prescription.bo.ApiKnowledgeBO;

import java.util.HashMap;
import java.util.Map;

public class InfoManager {
    private static InfoManager instance = null;

    public static synchronized InfoManager getInstance() {
        if (instance == null) {
            instance = new InfoManager();
        }
        return instance;
    }

    /**
     * 知识点
     */
    public Map<String, ApiKnowledgeBO> knowledgeMap = new HashMap<String, ApiKnowledgeBO>();
}
