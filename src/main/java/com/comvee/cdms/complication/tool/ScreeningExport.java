package com.comvee.cdms.complication.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.complication.model.po.ScreeningDataPO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author: suyz
 * @date: 2019/3/28
 */
public class ScreeningExport {

    private List<ScreeningDataPO> list;

    private HSSFWorkbook wb;
    private HSSFSheet sheet;

    private ScreeningExport(List<ScreeningDataPO> list){
        this.list = list;
        wb = new HSSFWorkbook();
        sheet = wb.createSheet();
    }

    public static ScreeningExport of(List<ScreeningDataPO> list){
        return new ScreeningExport(list);
    }

    /***
     * 导出数据
     * @return
     */
    public byte[] export(){
        //ByteOutputStream byteOutputStream = new ByteOutputStream();
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        byte[] bytes;
        try{
            columnWidthSet();
            excelHeaderHandler();
            excelBodyHandler();
            wb.write(byteOutputStream);
//            bytes = byteOutputStream.getBytes();
            bytes = byteOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(wb != null){
                    wb.close();
                }
            } catch (IOException e) {}
            try{
                if(byteOutputStream != null){
                    byteOutputStream.close();
                }
            }catch (Exception e){}
        }
        return bytes;
    }

    /**
     * 宽度设置
     */
    private void columnWidthSet(){
        for(int i = 0; i < 31; i++){
            sheet.setColumnWidth(i , 5000);
        }
    }

    /**
     * excel数据处理
     */
    private void excelBodyHandler(){
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        JSONObject vptData = null;
        JSONObject abiData = null;
        for(int i = 0,size = list.size(); i < size ; i++){
            ScreeningDataPO screeningDataPO = list.get(i);
            vptData = JSON.parseObject(screeningDataPO.getVptDataJson());
            abiData = JSON.parseObject(screeningDataPO.getAbiDataJson());
            HSSFRow row = sheet.createRow(i + 1);
            createCell(row , 0 , screeningDataPO.getPatientName() ,cellStyle);
            createCell(row , 1 ,  sexText(screeningDataPO.getSex()) ,cellStyle);
            createCell(row , 2 , screeningDataPO.getAge().toString() ,cellStyle);
            createCell(row , 3 , screeningDataPO.getScreeningDt() ,cellStyle);
            createCell(row , 4 , getValueNullDefault(screeningDataPO.getLeftVpt()) ,cellStyle);
            createCell(row , 5 , getValueNullDefault(screeningDataPO.getRightVpt()) ,cellStyle);
            createCell(row , 6 , footSymptomText(vptData , "ttmmgL") ,cellStyle);
            createCell(row , 7 , footSymptomText(vptData , "ttmmgR") ,cellStyle);
            createCell(row , 8 , footSymptomText(vptData , "zjxL") ,cellStyle);
            createCell(row , 9 , footSymptomText(vptData , "zjxR") ,cellStyle);
            createCell(row , 10 , footSymptomText(vptData , "zkyL") ,cellStyle);
            createCell(row , 11 , footSymptomText(vptData , "zkyR") ,cellStyle);
            createCell(row , 12 , nlsText(vptData ,"nlszjqsL") ,cellStyle);
            createCell(row , 13 , nlsText(vptData ,"nlszjqsR") ,cellStyle);
            createCell(row , 14 , footRiskText(vptData ,"bwgjL") ,cellStyle);
            createCell(row , 15 , footRiskText(vptData ,"bwgjR") ,cellStyle);
            createCell(row , 16 , footRiskText(vptData ,"zcttgjL") ,cellStyle);
            createCell(row , 17 , footRiskText(vptData ,"zcttgjR") ,cellStyle);
            createCell(row , 18 , footRiskText(vptData ,"gjfsL") ,cellStyle);
            createCell(row , 19 , footRiskText(vptData ,"gjfsR") ,cellStyle);
            createCell(row , 20 , getValueNullDefault(screeningDataPO.getLeftAbi()) ,cellStyle);
            createCell(row , 21 , getValueNullDefault(screeningDataPO.getRightAbi()) ,cellStyle);
            createCell(row , 22 , getAbiJsonValue(abiData ,"systemPressure") ,cellStyle);
            createCell(row , 23 , getAbiJsonValue(abiData ,"tibialPostRightMmgh") ,cellStyle);
            createCell(row , 24 , getAbiJsonValue(abiData ,"tibialPostRightIndex") ,cellStyle);
            createCell(row , 25 , getAbiJsonValue(abiData ,"tibialPostLeftMmgh") ,cellStyle);
            createCell(row , 26 , getAbiJsonValue(abiData ,"tibialPostLeftIndex") ,cellStyle);
            createCell(row , 27 , getAbiJsonValue(abiData ,"dorsPedisLeftMmgh") ,cellStyle);
            createCell(row , 28 , getAbiJsonValue(abiData ,"dorsPedisLeftIndex") ,cellStyle);
            createCell(row , 29 , getAbiJsonValue(abiData ,"dorsPedisRightMmgh") ,cellStyle);
            createCell(row , 30 , getAbiJsonValue(abiData ,"dorsPedisRightIndex") ,cellStyle);
        }
    }

    /**
     * 性别文案
     * @param sex
     * @return
     */
    private String sexText(Integer sex){
        if(sex == null){
            return "/";
        }
        String text = "";
        if(sex == 1){
            text = "男";
        }else if(sex == 2){
            text = "女";
        }
        return text;
    }

    /**
     * 获取abi json中的值
     * @param jsonObject
     * @param key
     * @return
     */
    private String getAbiJsonValue(JSONObject jsonObject ,String key){
        if(jsonObject == null){
            return "/";
        }
        return Optional.ofNullable(jsonObject.getString(key)).orElse("/");
    }

    /**
     * excel头部处理
     */
    private void excelHeaderHandler(){
        HSSFCellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 11);//设置字体大小
        font.setBold(true);
        headCellStyle.setFont(font);

        HSSFRow row0 = sheet.createRow(0);
        createCell(row0 , 0, "姓名" ,headCellStyle);
        createCell(row0 , 1, "性别" ,headCellStyle);
        createCell(row0 , 2, "年龄" ,headCellStyle);
        createCell(row0 , 3, "筛查时间" ,headCellStyle);
        createCell(row0 , 4, "左VPT" ,headCellStyle);
        createCell(row0 , 5, "右VPT" ,headCellStyle);
        createCell(row0 , 6, "疼痛麻木(左腿)" ,headCellStyle);
        createCell(row0 , 7, "疼痛麻木(右腿)" ,headCellStyle);
        createCell(row0 , 8, "足畸形(左腿)" ,headCellStyle);
        createCell(row0 , 9, "足畸形(右腿)" ,headCellStyle);
        createCell(row0 , 10, "足溃疡(左腿)" ,headCellStyle);
        createCell(row0 , 11, "足溃疡(右腿)" ,headCellStyle);
        createCell(row0 , 12, "10g尼龙丝触觉(左腿)" ,headCellStyle);
        createCell(row0 , 13, "10g尼龙丝触觉(右腿)" ,headCellStyle);
        createCell(row0 , 14, "凉温感觉(左腿)" ,headCellStyle);
        createCell(row0 , 15, "凉温感觉(右腿)" ,headCellStyle);
        createCell(row0 , 16, "针刺疼痛感觉(左腿)" ,headCellStyle);
        createCell(row0 , 17, "针刺疼痛感觉(右腿)" ,headCellStyle);
        createCell(row0 , 18, "跟腱反射(左腿)" ,headCellStyle);
        createCell(row0 , 19, "跟腱反射(右腿)" ,headCellStyle);
        createCell(row0 , 20, "左ABI" ,headCellStyle);
        createCell(row0 , 21, "右ABI" ,headCellStyle);
        createCell(row0 , 22, "肱动脉血压" ,headCellStyle);
        createCell(row0 , 23, "右侧胫后动脉血压" ,headCellStyle);
        createCell(row0 , 24, "右侧胫后动脉ABI指数" ,headCellStyle);
        createCell(row0 , 25, "左侧胫后动脉血压" ,headCellStyle);
        createCell(row0 , 26, "左侧胫后动脉ABI指数" ,headCellStyle);
        createCell(row0 , 27, "右侧足背动脉血压" ,headCellStyle);
        createCell(row0 , 28, "右侧足背动脉ABI指数" ,headCellStyle);
        createCell(row0 , 29, "左侧足背动脉血压" ,headCellStyle);
        createCell(row0 , 30, "左侧足背动脉ABI指数" ,headCellStyle);

    }

    /**
     * 创建单元格
     * @param row0
     * @param column
     * @param value
     * @param cellStyle
     */
    private void createCell(HSSFRow row0 ,int column ,String value , CellStyle cellStyle){
        HSSFCell cell = row0.createCell(column);
        cell.setCellValue(value);
        if(cellStyle != null){
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 足部症状文案处理
     * @param jsonObject
     * @param key
     * @return
     */
    private String footSymptomText(JSONObject jsonObject ,String key){
        if(jsonObject == null){
            return "/";
        }
        String value = jsonObject.getString(key);
        if(StringUtils.isBlank(value)){
            return "/";
        }
        String text = "/";
        if(value.equals("1")){
            text = "有";
        }else if(value.equals("2")){
            text = "无";
        }
        return text;
    }

    /**
     * 足部风险筛查文案处理
     * @param jsonObject
     * @param key
     * @return
     */
    private String footRiskText(JSONObject jsonObject ,String key){
        if(jsonObject == null){
            return "/";
        }
        String value = jsonObject.getString(key);
        if(StringUtils.isBlank(value)){
            return "/";
        }
        String text = "/";
        if(value.equals("1")){
            text = "正常";
        }else if(value.equals("2")){
            text = "减弱";
        }else if(value.equals("3")){
            text = "缺失";
        }
        return text;
    }

    /**
     * 尼龙丝触觉点数
     * @param jsonObject
     * @param key
     * @return
     */
    private String nlsText(JSONObject jsonObject ,String key){
        if(jsonObject == null){
            return "/";
        }
        String value = jsonObject.getString(key);
        if(StringUtils.isBlank(value)){
            return "/";
        }
        return value + "点感觉缺失";
    }

    /**
     * 为空时返回默认值
     * @param value
     * @param defaultValue
     * @return
     */
    public String getValueNullDefault(String value ,String defaultValue){
        if(value == null || "".equals(value)){
            return defaultValue;
        }
        return value;
    }

    /**
     * 为空时返回默认值
     * @param value
     * @return
     */
    public String getValueNullDefault(String value ){
        return getValueNullDefault(value , "/");
    }
}

