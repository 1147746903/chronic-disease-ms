package com.comvee.cdms.prescription.service;

import com.comvee.cdms.defender.mapper.DictMapper;
import com.comvee.cdms.defender.model.CourseModel;
import com.comvee.cdms.defender.model.DictModel;
import com.comvee.cdms.defender.model.PageRequestModel;
import com.comvee.cdms.defender.service.CourseServiceI;
import com.comvee.cdms.common.utils.BeanUtils;
import com.comvee.cdms.prescription.bo.*;
import com.comvee.cdms.prescription.cfg.PrescriptionConstant;
import com.comvee.cdms.prescription.dto.PrescriptionEduDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("prescriptionApi")
public class PrescriptionApiNewImpl extends PrescriptionApiImpl {

    @Autowired
    private CourseServiceI courseService;
    
    @Autowired
    private DictMapper dictMapper;
	
	@Override
    public List<ApiKnowledgeClassifyBO> loadKnowledgeClassify(Long pid) {

        try {
        	List<ApiKnowledgeClassifyBO> list = new ArrayList<ApiKnowledgeClassifyBO>();
        	if(pid == 0L) {
        		ApiKnowledgeClassifyBO bo = new ApiKnowledgeClassifyBO();
        		bo.setId(1L);
        		bo.setName("糖尿病科学饮食与食物交换份法");
        		bo.setPid(0L);
        		bo.setSort(1);
        		bo.setZindex(0);
        		
        		list.add(bo);
        	}else {
        		
        	}
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@Override
    public List<ApiKnowledgeBO> loadKnowledge(int page, int rows, ApiKnowledgeBO bo) {
        try {
            List<ApiKnowledgeBO> list = new ArrayList<ApiKnowledgeBO>();
            String pCode = "";
            //高血压的文章
            if(bo.getEohType() != null && bo.getEohType() == PrescriptionConstant.PRESCRIPTION_EOH_TYPE_HYP){
                pCode = "course_classify_hyp";
            }else{
                pCode = "course_classify";
            }
            List<DictModel> dictModels = dictMapper.listDictByPcode(pCode);
            for (Iterator<DictModel> iterator = dictModels.iterator(); iterator.hasNext();) {
				DictModel dictModel = (DictModel) iterator.next();
				
				ApiKnowledgeBO apiKnowledgeBO = new ApiKnowledgeBO();
				apiKnowledgeBO.setId(Long.valueOf(dictModel.getCode()));
				apiKnowledgeBO.setName(dictModel.getName());
				apiKnowledgeBO.setPid(1L);
				
				list.add(apiKnowledgeBO);
			}
            
            /*
            PageRequestModel pager = new PageRequestModel();
            pager.setPage(1);
            pager.setRows(rows);
            
            List<ApiKnowledgeBO> list = new ArrayList<ApiKnowledgeBO>();
            List<CourseModel> courseList = courseService.loadCourse(pager, null);
            for (Iterator<CourseModel> iterator = courseList.iterator(); iterator.hasNext();) {
				CourseModel courseModel = (CourseModel) iterator.next();
				
				ApiKnowledgeBO apiKnowledgeBO = new ApiKnowledgeBO();
				apiKnowledgeBO.setId(Long.valueOf(courseModel.getSid()));
				apiKnowledgeBO.setName(courseModel.getName());
				apiKnowledgeBO.setPid(1L);
				
				list.add(apiKnowledgeBO);
			}
			*/
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	@Override
    public List<ApiArticleBO> loadByKnowledgeId(Long classifyCode ,String memberId) {
        try {
        	List<ApiArticleBO> list = new ArrayList<ApiArticleBO>();
        	
        	List<CourseModel> courseList = courseService.loadCourseByClassifyCode(classifyCode+"");
        	for (Iterator<CourseModel> iterator = courseList.iterator(); iterator.hasNext();) {
				CourseModel courseModel = (CourseModel) iterator.next();
				ApiArticleBO apiArticleBO = new ApiArticleBO();
				apiArticleBO.setId(Long.valueOf(courseModel.getSid()));
				apiArticleBO.setTitle(courseModel.getName());
				apiArticleBO.setImg(courseModel.getImg());
                courseStatusHandler(apiArticleBO ,memberId);
				list.add(apiArticleBO);
			}
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void courseStatusHandler(ApiArticleBO apiArticleBO ,String memberId){
        /**
         * 学习状态 1 已学习  0 未学习
         */
        Integer learnStatus = 0;
        /**
         * 关注状态 1 已关注 0 未关注
         */
        Integer followStatus = 0;
        /**
         * 下发状态 1 已下发 0 未下发
         */
        Integer hairDownStatus = 0;
        List<CourseModel> list = this.courseService.listMemberCourseById(memberId ,apiArticleBO.getId().toString());
        if(list != null && !list.isEmpty()){
            for(CourseModel courseModel : list){
                //status = 2 为已学习
                if("2".equals(courseModel.getStatus())){
                    learnStatus = 1;
                }
                //当课程是学习计划的内容时，认为已下发
                if(1 == courseModel.getIsPlan()){
                    hairDownStatus = 1;
                }
                //当课程存在非学习计划的内容时，认为已关注
                if(0 == courseModel.getIsPlan()){
                    followStatus = 1;
                }
            }
        }
        apiArticleBO.setFollowStatus(followStatus);
        apiArticleBO.setHairDownStatus(hairDownStatus);
        apiArticleBO.setLearnStatus(learnStatus);
    }

	@Override
    public List<ApiArticleBO> loadArticle(PrescriptionEduDTO prescriptionEduDTO, PagerBO pager) {
        try {
        	PageRequestModel pagerModel = new PageRequestModel();
        	pagerModel.setPage(1);
        	pagerModel.setRows(500);
        	
        	List<ApiArticleBO> list = new ArrayList<ApiArticleBO>();
        	List<CourseModel> courseList = this.courseService.loadCourse(pagerModel, prescriptionEduDTO.getParam());
        	for (Iterator<CourseModel> iterator = courseList.iterator(); iterator.hasNext();) {
				CourseModel courseModel = (CourseModel) iterator.next();
				ApiArticleBO apiArticleBO = new ApiArticleBO();
				apiArticleBO.setId(Long.valueOf(courseModel.getSid()));
				apiArticleBO.setTitle(courseModel.getName());
				apiArticleBO.setImg(courseModel.getImg());
				courseStatusHandler(apiArticleBO ,prescriptionEduDTO.getMemberId());
				list.add(apiArticleBO);
			}
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
	
	
	@Override
    public KnowledgePlanBO createKnowledgePlan(String archives, String memberId ,Integer eohType) {
        KnowledgePlanBO knowledgePlanBO = new KnowledgePlanBO();
        Map<String, Object> map = courseService.createStudyPlan(archives, memberId, null ,eohType);//this.knowledgeService.createKnowledgePlan(archives, memberId, null);
        if(map!=null){
            List<String> list = (List<String>)map.get("knowledgeTag");
            knowledgePlanBO.setKnowledgeTag(list);
            
            List<ApiKnowledgePlanBO> results = new ArrayList<ApiKnowledgePlanBO>();
            knowledgePlanBO.setKnowledges(results);
            
            List<com.comvee.cdms.knowledge.model.KnowledgePlanModel> knowledgePlanModels = (ArrayList<com.comvee.cdms.knowledge.model.KnowledgePlanModel>)map.get("knowledges");
            if(knowledgePlanModels!=null && knowledgePlanModels.size()>0){
                for(com.comvee.cdms.knowledge.model.KnowledgePlanModel model:knowledgePlanModels){
                    if(model!=null){
                        ApiKnowledgePlanBO apiKnowledgePlanBO = new ApiKnowledgePlanBO();
                        BeanUtils.copyProperties(apiKnowledgePlanBO,model);
                        results.add(apiKnowledgePlanBO);
                    }
                }
            }
        }
        return knowledgePlanBO;
    }
	
}
