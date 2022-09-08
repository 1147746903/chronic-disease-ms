package com.comvee.cdms.prescription.bo;

import java.io.Serializable;
import java.util.List;

public class KnowledgePlanBO implements Serializable {
    private List<String> knowledgeTag;

    private List<ApiKnowledgePlanBO>  knowledges;

    public List<String> getKnowledgeTag() {
        return knowledgeTag;
    }

    public void setKnowledgeTag(List<String> knowledgeTag) {
        this.knowledgeTag = knowledgeTag;
    }

    public List<ApiKnowledgePlanBO> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<ApiKnowledgePlanBO> knowledges) {
        this.knowledges = knowledges;
    }
}
