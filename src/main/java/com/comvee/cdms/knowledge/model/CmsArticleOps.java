/**
 * @File name: CmsArticleOps.java 
 * @Create on:  2016-12-27
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
**/
package com.comvee.cdms.knowledge.model;

import com.comvee.cdms.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @File name: CmsArticleOps.java 
 * @Create on:  2016-12-27
 * @Author   :  zhengsw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 * 
 *
 **/
public class CmsArticleOps {
    
//    /**
//     * 构建菜单树json
//     * @param  
//     * @return
//     */
//    public static List<BarModel> loadTree(List<BarModel> barModelList) {
//        List<BarModel> nodeList = new ArrayList<BarModel>();
//        for (BarModel BarModel1 : barModelList) {
//            boolean mark = false;
//            for (BarModel BarModel2 : barModelList) {
//                if (BarModel1.getPid().longValue() == BarModel2.getId().longValue()) {
//                    mark = true;
//                    if (BarModel2.getChildren() == null) {
//                        BarModel2.setChildren(new ArrayList<BarModel>());
//                    }
//                    BarModel2.getChildren().add(BarModel1);
//                    break;
//                }
//            }
//            if (!mark) {
//                nodeList.add(BarModel1);
//            }
//        }
//        return nodeList;
//    }
    
    /**
     * 构建菜单树json
     * @param  
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String,Object>> loadTree1(List<Map<String, Object>> barModelList) {
        List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> map1 : barModelList) {
            //添加text字段，combotree只认text^_^!!!
            map1.put("text", map1.get("name"));
            
            boolean mark = false;
            for (Map<String,Object> map2 : barModelList) {
                //Long对像相等会有问题，后面调整
                if (map1.get("pid") == map2.get("id")) {
                    mark = true;
                    if (map2.get("children") == null) {
                        map2.put("children", new ArrayList<Map<String,Object>>());
                        
                    }
                    ArrayList<Map<String,Object>> childrenList =  (ArrayList<Map<String, Object>>) map2.get("children");
                    //处理top
                    if(childrenList.isEmpty()){
                        map1.put("top", -1);
                        childrenList.add(map1);
                    }else if(childrenList.size() == 1){
                        Map<String,Object> map3 = childrenList.get(0);
                        map3.put("top", 1);
                        map1.put("top", 2);
                        childrenList.add(map1);
                    } else {
                        Map<String,Object> map3 = childrenList.get(childrenList.size() -1 );
                        map3.put("top", 0);
                        
                        map1.put("top", 2);
                        childrenList.add(map1);
                    }
                    break;
                }
            }
            if (!mark) {
                if(nodeList.isEmpty()){
                    map1.put("top", -1);
                    nodeList.add(map1);
                }else if(nodeList.size() == 1){
                    Map<String,Object> map3 = nodeList.get(0);
                    map3.put("top", 1);
                    
                    map1.put("top", 2);
                    nodeList.add(map1);
                } else {
                    Map<String,Object> map3 = nodeList.get(nodeList.size() -1 );
                    map3.put("top", 0);
                    
                    map1.put("top", 2);
                    nodeList.add(map1);
                }
            }
        }
        return nodeList;
    }
    
    /**
     * 拆分逗号隔开的字符串
     * @param tagStr
     * @return
     * @author zhengsw
     * @date 2016-12-29
     */
    public static String[] getSplitStr(String tagStr){
        if(!StringUtils.isBlank(tagStr)){
            String[] temp = tagStr.split(",");
            return temp;
        } else {
            return new String[0];
        }
        
    }
    
    /**
     * @TODO 处理null
     * @param str
     * @return
     * @author zhengsw
     * @date 2016-12-29
     */
    public static String strFormat(String str){
        if(str == null){
            return "";
        }else {
            return str;
        }
    }
    
    public static boolean checkPlatformStyle(String platform){
        if(StringUtils.isBlank(platform)){
            return false;
        } else {
            String re = "^,[0-9,]+,$";
            Pattern pPlatform  = Pattern.compile(re);
            return pPlatform.matcher(platform).matches();
        }
    }
//    public static void main(String[] args) {
//        String str =",1,";
//        System.out.println(checkPlatformStyle(str));
//    }
}
