package com.comvee.cdms.dybloodsugar.tool;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.HttpUtils;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dybloodsugar.constant.DynamicBloodSugarConstant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hbj
 * @description 菜品识别工具
 * @date 2021/5/10 13:42
 */
public class DishIdentifyTool {
    //设置APPID/AK/SK
    public static final String APP_ID = "24122030";
    public static final String API_KEY = "808fTDN4LzMNS4vAH4YTSzMG";
    public static final String SECRET_KEY = "u6X2IFqaMotcgxlCTC3qWPSVtUSWMgmh";
    public static String ACCESS_TOKEN = "";
    public static final String DISH_IDENTIFY_URL = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";

    /**
     * 获取百度智云token
     * @author
     * @date 2021/5/10
     * @param
     * @return java.lang.String
     */
    public static  String getToken(){
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + API_KEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + SECRET_KEY;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = JSON.parseObject(result);
            ACCESS_TOKEN = jsonObject.getString("access_token");
            return  ACCESS_TOKEN;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return  null;
    }

    public static JSONObject dishIdentify(String url){
        if (StringUtils.isNullOrEmpty(ACCESS_TOKEN)){
            getToken();
        }

        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> formParam = new HashMap<>();
        formParam.put("url", url);
        formParam.put("filter_threshold", "0.7");
        formParam.put("baike_num", "5");
        String result = HttpUtils.doPost(DISH_IDENTIFY_URL + "?access_token=" + ACCESS_TOKEN, formParam, header);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject reVal =  jsonObject.getJSONArray("result").getJSONObject(0);

        if (reVal == null){
            int error_code = jsonObject.getInteger("error_code");

            if (error_code == DynamicBloodSugarConstant.ACCESS_TOKEN_EXPIRED){
                getToken();
                reVal = dishIdentify(url);
            }

        }

        return reVal;
    }
}
