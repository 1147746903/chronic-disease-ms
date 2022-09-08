/**
 * @File name:   KnowledgeServiceImpl.java  知识点 service层接口实现类
 * @Create on:  2016-12-29 18:33:32
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
 **/

package com.comvee.cdms.knowledge.service;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.cfg.Constant;
import com.comvee.cdms.common.utils.DaoHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.knowledge.dto.IncrMemberArticleClicksDTO;
import com.comvee.cdms.knowledge.mapper.KnowledgeMapper;
import com.comvee.cdms.knowledge.model.*;
import com.comvee.cdms.knowledge.po.MemberArticleClicksPO;
import com.comvee.cdms.knowledge.tool.InfoManager;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service("knowledgeService")
public class KnowledgeServiceImpl  implements KnowledgeServiceI {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(KnowledgeServiceImpl.class);
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    
    @Override
    public List<ArticleModel> loadByKnowledgeId(Long knowledgeId) {
        return this.knowledgeMapper.loadByKnowledgeId(knowledgeId);
    }
    @Override
    public List<KnowledgeModel>  loadKnowledgeByAid(Long articleId) {
        return this.knowledgeMapper.loadKnowledgeByAid(articleId);
    }
	@Override
    public List<ArticleKnowledgeModel> findArticleKnowledgeByIds(List<Long> articleIds) {
        List<ArticleKnowledgeModel> articleKnowledgeByIds = new ArrayList<>();
        if(null!=articleIds && articleIds.size()>0){
            articleKnowledgeByIds = this.knowledgeMapper.findArticleKnowledgeByIds(articleIds);
        }
        return articleKnowledgeByIds;
    }
	
	@Override
	public ArticleModel loadArticleById(Long id) {
	    return this.knowledgeMapper.loadArticleById(id);
	}
	
	
	@Override
	public List<ArticleModel> loadArticle(int page, int rows,String startDt,String endDt,
            String platform,String title,String barId) {
		PageHelper.startPage(page,rows);
		return this.knowledgeMapper.loadArticle(page, rows, startDt, endDt, platform, title, barId);
	}
	
	@Override
	public long countArticle(String startDt,String endDt,
            String platform,String title,String barId) {
		return this.knowledgeMapper.countArticle(startDt, endDt, platform, title, barId);
	}
	
	@Override
    public ArticleTxtModel loadArticleTxtByArticleId(Long articleId)  {
        return this.knowledgeMapper.loadArticleTxtByArticleId(articleId);
    }

    @Override
    public void createRuleFile() {
    	PageHelper.startPage(1,5000);
        List<CategoryRuleModel> list  = this.knowledgeMapper.loadCategoryRule(null);
        
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("package com.comvee.open.component.rule;").append("\r\n");;
        sb.append("import java.util.Map;").append("\r\n");;
        sb.append("import java.io.Console;").append("\r\n");;
        sb.append("import com.comvee.open.component.RuleResult;").append("\r\n");;
        sb.append("import com.comvee.open.component.DroolRuleTools;").append("\r\n");;
        sb.append("import com.alibaba.fastjson.JSON;").append("\r\n");;
        sb.append("\r\n").append("\r\n");
        
        for (int i = 0;i <list.size();i++) {
            CategoryRuleModel bean = list.get(i);
            sb.append("rule \"rule_knowledge_plan_"+bean.getId()+"\"");
            sb.append("\r\n");
            sb.append("\tdialect \"mvel\"  ");
            sb.append("\r\n");
            int salience = bean.getSalience() == null ? 0 : bean.getSalience();
            sb.append("salience ").append(salience);
            sb.append("\r\n");
            String condition = getConditionStr(bean);
            sb.append("when ");
            sb.append("\r\n");
            sb.append("\t$ruleResult : RuleResult() && ");
            //条件处理
            sb.append("\t").append(condition);
            
            sb.append("then");
            sb.append("\r\n");
            sb.append("\t$ruleResult.addResList(\""+bean.getId()+"\");");
            sb.append("\r\n");
            sb.append("end");
            sb.append("\r\n");
            sb.append("\r\n");
            sb.append("\r\n");
        
        }
        
        
        createFromTempFile(sb.toString(),"suggest.drl");
    }
    
    
    @Override
    public List<ArticleModel> getSuggest(Map<String,String> mapMemberFile,String uid){
        
       LOGGER.info("mapMemberFile:"+JSON.toJSONString(mapMemberFile));
        
       List<ArticleModel> resArticle = new ArrayList<ArticleModel>();
        
        List<String> idList = new ArrayList<String>();
        RuleResultModel ruleResult   = this.getSuggest(mapMemberFile);
        if(ruleResult.getResList().size() > 0){
            List<Object> ruleList = ruleResult.getResList();
            for (int i = 0; i < ruleList.size(); i++) {
                String id = ruleList.get(i).toString();
                if(id != null){
                    CategoryRuleModel categoryRuleModel = this.knowledgeMapper.loadCategoryRuleById(Long.parseLong(id));
                    if(categoryRuleModel != null && categoryRuleModel.getOutId() !=null && categoryRuleModel.getOutId().trim().length() > 0 ){
                        String[] param = categoryRuleModel.getOutId().split(",");
                        for (int j = 0; j < param.length; j++) {
                            if(param[j].trim().length() > 0){
                                idList.add(param[j]);
                            }
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < idList.size(); i++) {
            List<ArticleModel> articles = this.knowledgeMapper.loadByKnowledgeId(Long.parseLong(idList.get(i)));
            resArticle.addAll(articles);
        }
        
        return removeSame(resArticle,mapMemberFile);
    }

    /**
     * 去重方法常量
     */
    private final static String LIKE_FOOD = "LIKE_FOOD";
    private final static String LIKE_SPORT = "LIKE_SPORT";
    private final static String LIKE_MEDICINE = "LIKE_MEDICINE";
    private final static String LIKE_COMPLICATION = "LIKE_COMPLICATION";
    private final static String LIKE_WEIGHT = "LIKE_WEIGHT";
    private final static String LIKE_SMBG = "LIKE_SMBG";
    private final static String LIKE_LOWBLOOD = "LIKE_LOWBLOOD";
    /**
     * 去重
     * @param list
     * @return
     * @author zhengsw
     * @param mapMemberFile 
     * @date 2017-1-23
     */
    private List<ArticleModel> removeSame(List<ArticleModel> list, Map<String, String> mapMemberFile){
        List<ArticleModel> res = new ArrayList<ArticleModel>(list.size());
        Set<String> idSet = new HashSet<String>();
        List<String>knowKnowledge = new ArrayList<String>();

        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_FOOD)) ){
            knowKnowledge.add(LIKE_FOOD);
        }
        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_SPORT))){
            knowKnowledge.add(LIKE_SPORT);
        }
        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_MEDICINE))){
            knowKnowledge.add(LIKE_MEDICINE);
        }
        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_COMPLICATION))){
            knowKnowledge.add(LIKE_COMPLICATION);
        }
        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_WEIGHT))){
            knowKnowledge.add(LIKE_WEIGHT);
        }
        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_SMBG))){
            knowKnowledge.add(LIKE_SMBG);
        }
        if(Constant.CONST_NUM_01.equals(mapMemberFile.get(LIKE_LOWBLOOD))){
            knowKnowledge.add(LIKE_LOWBLOOD);
        }
        
        for (ArticleModel article: list) {
           if(!idSet.contains(article.getId().toString())){
               idSet.add(article.getId().toString());
               res.add(article);
           }
        }
        return sortArticleByClassify(res,knowKnowledge);
    }
    
    /**
     * 处理排序(暂时没用)
     * @param list
     * @param sortKeyword
     * @return
     * @author zhengsw
     * @date 2017-1-23
     */
    @SuppressWarnings("unused")
    private List<ArticleModel> sortArticle(List<ArticleModel> list,List<String> sortKeyword ){
        
        List<ArticleModel>  resList = new ArrayList<ArticleModel>();
        for (String keyword:sortKeyword) {
            for (ArticleModel article:list) {
                if(article.getKeyId() != null){
                    String keyIds = ",".concat(article.getKeyId()).concat(",");
                    if(keyIds.contains(","+keyword+",")){
                        if(!resList.contains(article)){
                            resList.add(article);
                        }
                    }  
                }
            }
        }
        
        for (ArticleModel article:list) {
            if(!resList.contains(article)){
                resList.add(article);
            }
        }
        return resList;
    }
    

    /**
     * @TODO 处理排序——根据偏好
     * @param list
     * @param knowKnowledge
     * @return
     * @author zhengsw
     * @date 2017-1-23
     */
    private List<ArticleModel> sortArticleByClassify(List<ArticleModel> list,List<String> knowKnowledge ){
        Map<String, CfgTagModel> tag = InfoManager.getInstance().tagMap;
        List<ArticleModel>  resList = new ArrayList<ArticleModel>();
        for (ArticleModel article:list) {
            if(article.getTagId() != null){
                String[] tagIds = article.getTagId().split(",");
                for (String tagId : tagIds) {
                    if (tag.get(tagId).getClassify() != null && knowKnowledge.contains(tag.get(tagId).getClassify())) {
                        resList.add(article);
                        break;
                    }
                }
            }
        }
        
        for (ArticleModel article:list) {
            if(!resList.contains(article)){
                resList.add(article);
            }
        }
        return resList;
    }
    
    /**
     * @TODO 处理排序(暂时没用)
     * @param resArticle
     * @param sortKeyword
     * @return
     * @author zhengsw
     * @date 2017-1-23
     */
    @SuppressWarnings("unused")
    private List<ArticleModel> sortArticle(Map<String,ArticleModel> resArticle,List<String> sortKeyword ){
        List<ArticleModel>  list = new ArrayList<ArticleModel>();
        for (Map.Entry<String, ArticleModel> entry:resArticle.entrySet()) {
            list.add(entry.getValue());
        }
        
        
        List<ArticleModel>  resList = new ArrayList<ArticleModel>();
        for (String keyword:sortKeyword) {
            for (ArticleModel article:list) {
                if(article.getKeyId() != null){
                    String keyIds = ",".concat(article.getKeyId()).concat(",");
                    if(keyIds.contains(","+keyword+",")){
                        if(!resList.contains(article)){
                            resList.add(article);
                        }
                    }  
                }
            }
        }
        
        for (ArticleModel article:list) {
            if(!resList.contains(article)){
                resList.add(article);
            }
        }
        return resList;
    }
    
    
//    private List<String> sortKeyword(Map<String,String> mapMemberFile){
//        List<FeatureModel> features =  this.knowledgeMapper.loadSortElement();
//        for (FeatureModel featureModel:features) {
//            if(mapMemberFile.containsKey(featureModel.getCode())){//存在档案
//                //判断选项是否要排序
//                this.featureItemDao.loadFeatureItem(1, 20, featureModel.getId());
//                
//            }
//        }
//    }
    
    /**
     * 获取优先排序的关键字列表
     * @TODO
     * @return
     * @
     * @author zhengsw
     * @date 2017-1-17
     */
    @SuppressWarnings("unused")
    private List<String> sortKeyword(Map<String,String> mapMemberFile) {
        List<String> sortList = new ArrayList<String>();
        List<FeatureItemModel> items = this.knowledgeMapper.loadFeatureForSort();
        
        for (FeatureItemModel featureItemModel : items) {
            //用户存在这个档案
            if(mapMemberFile.containsKey(featureItemModel.getCode())){
                //档案值
                if(featureItemModel.getVals().equals(mapMemberFile.get(featureItemModel.getCode()))){
                    if(featureItemModel.getKeyId() != null ){
                        String[] param = featureItemModel.getKeyId().split(",");
                        for (int i = 0; i < param.length; i++) {
                            if(param[i].trim().length() > 0){
                                if (!sortList.contains(param[i].toString())) {
                                    sortList.add(param[i].trim());
                                }
                            }
                        }
                    }
                }
            }
        }
        return sortList;
    }
    

    private RuleResultModel getSuggest(Map<String,String> mapMemberFile){
        RuleResultModel ruleSuggest= new RuleResultModel();
        return ruleSuggest;
    }
    
    @SuppressWarnings(value={"rawtypes", "unchecked"})
    private String getConditionStr(CategoryRuleModel bean) {
        
        
        String ruleJson = bean.getRuleJson();
        StringBuffer sb = new StringBuffer();
        List<List> list = JSON.parseArray(ruleJson,List.class);
        sb.append("Map ( ");
        int fsize = list.size();
        for (int i = 0; i < list.size(); i++) {
            StringBuffer sbCon = new StringBuffer();
            List<Map<String,Object>>  cons = list.get(i);
            int size = cons.size();
            sb.append("(");
            for (int j = 0; j < size; j++) {
                Map<String,Object> data = cons.get(j);
                String dataPcode  = (String) data.get("data_pcode");
                String dataValue  = (String) data.get("data_value");
                String dataPid  = (String) data.get("data_pid");
                String dataId  = (String) data.get("data_id");
                
                
                FeatureModel featureModel = this.knowledgeMapper.loadFeatureById(Long.parseLong(dataPid));
                //单选
                if(featureModel.getType().intValue() == 2){
                    if(size -1  == j) {
                        sbCon.append("(").append("this[\""+dataPcode+"\"] == \""+dataValue+"\"").append(")");
                    } else {
                        sbCon.append("(").append("this[\""+dataPcode+"\"] == \""+dataValue+"\"").append(")").append("  ||");
                    }
                } //数值埴空
                else if(featureModel.getType().intValue() == 4){
                    FeatureItemModel featureItemModel = this.knowledgeMapper.loadFeatureItemById(Long.parseLong(dataId));
                    //选项的转化规则(0选项,1数值,2时间－年3时间-月4时间-天5时间-小时,6时间-分钟)
                    if(size -1  == j) {
                        sbCon.append("(").append("this[\""+dataPcode+"\"] >= "+featureItemModel.getMinRule()+" && "  ).append("this[\""+dataPcode+"\"] <= "+featureItemModel.getMaxRule()).append(")");
                    } else {
                        sbCon.append("(").append("this[\""+dataPcode+"\"] >= "+featureItemModel.getMinRule()+" && "  ).append("this[\""+dataPcode+"\"] <= "+featureItemModel.getMaxRule()).append(")").append("  ||");
                    }
                }
            }
            if (fsize - 1 == i) {
                sb.append(sbCon).append(")");
            } else {
                sb.append(sbCon).append(")").append(" &&");
            }
        }
        sb.append(" )");
        return sb.toString();
    }
    
    /**
     * @TODO 根据不同模版生成所需文件
     * @param str 填写的文件内容
     * @param fileName 文件名字
     * @
     * @author zhengsw
     * @date 2016-2-2
     */
    private void createFromTempFile(String str,String fileName) {
        // 经过测试，这种方法也是安全的
        String root = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()).getParent();
        root = root.replaceAll("%20", " ");
        OutputStreamWriter fw = null;
        BufferedWriter bw = null;
        try {
            String fileDir = root +File.separator + "classes"+File.separator+"rules" + File.separator;
            String fileWrite =fileDir + fileName;
            File filePach=new File(fileDir);  
            if(!filePach.exists()){  
                filePach.mkdirs();  //创建文件夹  
            }  
            fw = new OutputStreamWriter(new FileOutputStream(fileWrite),"utf-8");
            bw = new BufferedWriter(fw);
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            try {
                bw.newLine();
                bw.flush();
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
    @Override
    public List<KnowledgeClassifyModel> loadKnowledgeClassify(Long pid)  {
        return this.knowledgeMapper.loadKnowledgeClassify(pid);
    }
	
    
    
    
    
    
    
    
    
    
    

    @Override
    public List<KnowledgeModel> loadKnowledge(int page, int rows, KnowledgeModel model)  {
        PageHelper.startPage(1,5000);
    	List<KnowledgeModel> list = this.knowledgeMapper.loadKnowledge( model);
        Map<String, CfgKeywordModel> keyWordMap = InfoManager.getInstance().keyWordMap;
        for (int i = 0; i < list.size(); i++) {
            String str = "";
            if (!StringUtils.isBlank(list.get(i).getKeyId())) {
                String []keyIds = list.get(i).getKeyId().split(",");
                for (String string : keyIds) {
                    str+=keyWordMap.get(string).getLable()+",";
                }
            }
            if (!StringUtils.isBlank(str)) {
                list.get(i).setKeyName(str.substring(0, str.length()-1-1));
            }
            
        }
        return list;
    }

    @Override
    public Map<String, Object> createKnowledgePlan(String archives, String memberId, String drugList) {
        Map<String,String> mapMemberFile =  DoctorPlanOps.transformArchives(archives, drugList);

        List<String> list = DoctorPlanOps.getKnowledgeTag(mapMemberFile);

        List<ArticleModel> articles = getSuggest(mapMemberFile, memberId);

        List<KnowledgePlanModel>  resList = getKnowledge(articles);

        Map<String,Object> map = new HashMap<String,Object>(2);
        map.put("knowledges", resList);
        map.put("knowledgeTag", list);
        return map;
    }

    @Override
    public void incrMemberArticleClick(IncrMemberArticleClicksDTO incrMemberArticleClicksDTO) {
        MemberArticleClicksPO memberArticleClicksPO = this.knowledgeMapper.getMemberArticleClicks(incrMemberArticleClicksDTO.getMemberId());
        if(memberArticleClicksPO == null){
            memberArticleClicksPO = new MemberArticleClicksPO();
            memberArticleClicksPO.setSid(DaoHelper.getSeq());
            memberArticleClicksPO.setMemberId(incrMemberArticleClicksDTO.getMemberId());
            memberArticleClicksPO.setWikiClicks(Optional.ofNullable(incrMemberArticleClicksDTO.getWikiClicks()).orElse(0));
            memberArticleClicksPO.setKnowledgeClicks(Optional.ofNullable(incrMemberArticleClicksDTO.getKnowledgeClicks()).orElse(0));
            this.knowledgeMapper.addMemberArticleClicks(memberArticleClicksPO);
        }else{
            if(incrMemberArticleClicksDTO.getWikiClicks() != null){
                memberArticleClicksPO.setWikiClicks(memberArticleClicksPO.getWikiClicks() + 1);
            }
            if(incrMemberArticleClicksDTO.getKnowledgeClicks() != null){
                memberArticleClicksPO.setKnowledgeClicks(memberArticleClicksPO.getKnowledgeClicks() + 1);
            }
            this.knowledgeMapper.updateMemberArticleClicks(memberArticleClicksPO);
        }
    }

    /**
     * @TODO 处理文章知识
     * @param articles
     * @return
     * @throws Exception
     * @author zhengsw
     * @date 2017-1-10
     */
    private List<KnowledgePlanModel> getKnowledge( List<ArticleModel>  articles){
        List<Long> articleIds = new ArrayList<Long>();
        for (ArticleModel article: articles) {
            articleIds.add(article.getId());
        }

        List<ArticleKnowledgeModel>  ls = findArticleKnowledgeByIds(articleIds);


        List<KnowledgePlanModel> resList =new ArrayList<KnowledgePlanModel>();
        int total = articles.size();
        for (int i = 0; i <total ; i++) {
            ArticleModel article = articles.get(i);
            StringBuilder knowledge = new StringBuilder();
            for (ArticleKnowledgeModel aKnowledge : ls) {
                if (aKnowledge.getArticleId().longValue() == article.getId().longValue()) {
                    KnowledgeModel knowledgeModel = InfoManager.getInstance().knowledgeMap.get(aKnowledge.getKnowledgeId().toString());
                    if (knowledgeModel != null) {
                        knowledge.append(knowledgeModel.getName()).append(",");
                    }
                }
            }

            String strKnowledge = knowledge.length() > 0 ? knowledge.substring(0,knowledge.length() - 1):knowledge.toString();
            KnowledgePlanModel np = new KnowledgePlanModel();
            np.setId(article.getId().toString());
            np.setKnowledge(strKnowledge);
            np.setTitle(article.getTitle());
            np.setDay(getDay(total, i) + "");

            resList.add(np);
        }


        return resList;
    }

    private static int getDay(int total,int index){
        int num = 12;
        if(total > num){
            Float day = (index-1) * ( 84.0f/total) +1;
            return day.intValue();
        } else {
            //index第几周+1天
            return (index -1)*7 + 1;
        }
    }
}
