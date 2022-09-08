package com.comvee.cdms.exportexcle.tool;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.common.utils.StringUtils;
import com.comvee.cdms.dybloodsugar.dto.DyBloodSugarValueDTO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDynamicItemVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodSugarDynamicVO;
import com.comvee.cdms.hospital.model.bo.CheckinInfoBO;
import com.comvee.cdms.member.po.MemberPO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**导出excle工具类
 * @author wyc
 * @date 2019/4/18 15:02
 */
public class ExcelUtil {


    private final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 导出excel
     * @param title  导出表的标题
     * @param rowsName 导出表的列名
     * @param dataList  需要导出的数据
     * @param fileName  生成excel文件的文件名
     * @param response
     */
    public void exportExcel1(String title,Object[] total,List<Object[]> dataList, String fileName, HttpServletResponse response) throws Exception{
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition",
                "attachment; filename="+fileName);
//        response.setContentType("application/msexcel");
        response.setContentType("application/vnd.ms-excel");
        this.export1(title,total,dataList,fileName,output);
        this.close(output);
    }
    public void exportExcel2(String title,List<Object[]> dataList, String fileName,List<Object> listName,List<List<Object>> listBloodSugar,List<Object> listDate, HttpServletResponse response) throws Exception{
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition",
                "attachment; filename="+fileName);
//        response.setContentType("application/msexcel");
        response.setContentType("application/vnd.ms-excel");
        this.export2(title,dataList,fileName,listName,listBloodSugar,listDate,output);
        this.close(output);
    }

    public void exportExcelHospitalBlood(String[] rowName, List<Object[]> dataList, String fileName, HttpServletResponse response) throws Exception{
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition",
                "attachment; filename="+fileName);
//        response.setContentType("application/msexcel");
        response.setContentType("application/vnd.ms-excel");
        this.exportHospitalBlood(dataList,rowName,output);
        this.close(output);
    }



    private void merageStyle(int start,int end,HSSFCellStyle style,HSSFRow row,HSSFCell cell){
        for (int j = start; j <= end; j++) {
            cell = row.createCell(j);
            cell.setCellStyle(style); //style为带边框的样式 上面有定义
            cell.setCellValue("");
        }
    }

    private void merageUpDown(int col,HSSFCellStyle style,HSSFRow row ){
        HSSFCell cell = row.createCell(col);
        cell.setCellValue("");
        cell.setCellStyle(style);
    }

    /*
     * 导出数据
     */
    private void export1(String title,Object[] total,List<Object[]> data,String fileName,OutputStream out) {
        try {

            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            int sheetNum = data.size() / 65500 + 1;
            for (int k = 0; k < sheetNum; k++) {
                HSSFSheet sheet =null;
                if (k==0){
                    sheet = workbook.createSheet("综合数据统计"); // 创建工作表
                }else {
                   sheet = workbook.createSheet("综合数据统计"+k); // 创建工作表
                }
                List<Object[]> dataList = new ArrayList<>();
                if ((k * 65500 + 65500) > data.size()){
                    dataList = data.subList(k * 65500,data.size());
                }else{
                    dataList = data.subList(k * 65500,k * 65500 + 65500);
                }
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook); // 获取单元格样式对象
            String[] rowName = {"               ", "患者总数", "新增患者总数", "血糖监测人数", "血糖监测次数", "检查VPT总数",
                    "检查VPT人数", "检查ABI总数", "检查ABI人数", "制定处方总数", "新增处方数", "制定随访总数",
                    "新增随访数", "在线咨询人数", "套餐开通总次数", "套餐新开通次数"};
            int columnNum = rowName.length;     // 定义所需列数
            HSSFRow rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < rowName.length; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
                cellRowName.setCellValue(rowName[n]); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }
            //设置总计
            HSSFRow row1 = sheet.createRow(1);
            HSSFCell cellRow1 = row1.createCell(0);
            cellRow1.setCellValue("总计");
            cellRow1.setCellStyle(style);
            for (int i = 0; i < total.length; i++) {
                HSSFCell cellTotal = row1.createCell(i + 1);
                cellTotal.setCellStyle(style);
                if (!"".equals(total[i]) && total[i] != null) {
                    cellTotal.setCellValue(total[i].toString()); // 设置单元格的值
                }
            }
            // 将查询出的数据设置到sheet对应/的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);   // 遍历每个对象
                HSSFRow row = sheet.createRow(i + 2);   // 创建所需的行数
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   // 设置单元格的数据类型
                    cell = row.createCell(j, CellType.STRING);
                    if (!"".equals(obj[j]) && obj[j] != null) {
                        cell.setCellValue(obj[j].toString()); // 设置单元格的值
                    }
                    if ("".equals(obj[j]) || obj[j] == null) {
                        cell.setCellValue(""); // 设置单元格的值
                    }

                    cell.setCellStyle(style); // 设置单元格样式
                }
            }
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == CellType.STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        }
            workbook.write(out);
        } catch (Exception e) {
            logger.error("",e);
        }
    }

    private void export2(String title,List<Object[]> data,String fileName,List<Object> listName,List<List<Object>> listBloodSugar,List<Object> listDate,OutputStream out) throws Exception {
        try{

        HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook); // 获取单元格样式对象
            List<Object[]> dataList = new ArrayList<>();
            int sheetNum = data.size()/65500 + 1;
            for (int k = 0; k < sheetNum; k++) {
                HSSFSheet sheet = null;
                if (k == 0) {
                    sheet = workbook.createSheet("患者数据"); // 创建工作表
                } else {
                    sheet = workbook.createSheet("患者数据" + k); // 创建工作表
                }
                if ((k * 65500 + 65500) > data.size()) {
                    dataList = data.subList(k * 65500, data.size());
                } else {
                    dataList = data.subList(k * 65500, k * 65500 + 65500);
                }
                HSSFRow rowm = sheet.createRow(0);  // 产生表格标题行
                HSSFCell cellTiltle = rowm.createCell(0);   //创建表格标题列
                cellTiltle.setCellStyle(columnTopStyle);    //设置标题行样式
                cellTiltle.setCellValue(title);     //设置标题行值

//      //合并表格标题行，合并列数为列名的长度,第一个0为起始行号，第二个1为终止行号，第三个0为起始列好，第四个参数为终止列号
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 98));  //(rowName.length - 1)
                merageStyle(1, 98, columnTopStyle, rowm, cellTiltle);

                String[] rowName = {"患者姓名", "身份证", "性别", "年龄", "身高", "体重", "BMI", "腰围", "臀围",
                        "腰臀比", "收缩压", "舒张压", "手机号", "劳动强度", "确诊时间", "糖尿病类型", "空腹血糖",
                        "餐后2小时血糖", "睡前血糖", "目前血糖监测频率", "糖化血红蛋白", "是否接受过糖尿病教育",
                        "规划饮食每日总能量", "患者目前每日摄入总能量", "目前运动方式", "患者目前用药情况",
                        "是否发生过低血糖", "近一个月内发生低血糖次数", "低密度脂蛋白", "高密度脂蛋白", "甘油三脂",
                        "总胆固醇", "血肌酐", "血尿酸", "谷丙转氨酶", "尿蛋白", "方案名称", "适用说明", "每周血糖监测次数",
                        "患者上传血糖次数", "患者提问次数", "医生回答次数", "是否有", "检查时间", "治疗情况", "疾病类型",
                        "表现症状", "是否有", "检查时间", "治疗情况", "疾病类型", "表现症状", "是否有", "检查时间",
                        "治疗情况", "疾病类型", "表现症状", "是否有", "检查时间", "治疗情况", "疾病类型", "表现症状",
                        "是否有", "检查时间", "治疗情况", "疾病类型", "表现症状", "是否有", "检查时间", "治疗情况",
                        "疾病类型", "表现症状", "是否有", "检查时间", "治疗情况", "疾病类型", "表现症状", "是否有",
                        "检查时间", "治疗情况", "疾病类型", "表现症状", "是否有", "检查时间", "治疗情况", "疾病类型", "心血管危险因素",
                        "近一年急性并发症情况", "做VPT次数", "左脚", "右脚", "做ABI次数", "左脚", "右脚", "首次管理处方制定时间",
                        "首次套餐开通时间", "套餐开通次数(总)", "管理处方制定数(总)", "随访制定数(总)"};
                //创建列头行
                HSSFRow rowUp = sheet.createRow(1);
                HSSFCell cell1 = rowUp.createCell(0);
                cell1.setCellValue("患者基本信息");

                cell1.setCellStyle(getBackStyle(workbook, IndexedColors.RED.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));
                merageStyle(1, 13, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell2 = rowUp.createCell(14);
                cell2.setCellValue("糖尿病现状");
                cell2.setCellStyle(getBackStyle(workbook, IndexedColors.LIGHT_YELLOW.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 14, 22));
                merageStyle(15, 22, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell3 = rowUp.createCell(23);
                cell3.setCellValue("生活方式");
                cell3.setCellStyle(getBackStyle(workbook, IndexedColors.BLUE_GREY.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 23, 27));
                merageStyle(24, 27, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell4 = rowUp.createCell(28);
                cell4.setCellValue("患者实验室检查");
                cell4.setCellStyle(getBackStyle(workbook, IndexedColors.LIGHT_GREEN.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 28, 35));
                merageStyle(29, 35, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell5 = rowUp.createCell(36);
                cell5.setCellValue("医嘱");
                cell5.setCellStyle(getBackStyle(workbook, IndexedColors.BRIGHT_GREEN.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 36, 38));
                merageStyle(37, 38, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell6 = rowUp.createCell(39);
                cell6.setCellValue("患者教育依从性");
                cell6.setCellStyle(getBackStyle(workbook, IndexedColors.GREY_40_PERCENT.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 39, 41));
                merageStyle(40, 41, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell7 = rowUp.createCell(42);
                cell7.setCellValue("并发症情况");
                cell7.setCellStyle(getBackStyle(workbook, IndexedColors.GREY_25_PERCENT.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 42, 87));
                merageStyle(43, 87, columnTopStyle, rowm, cellTiltle);

                HSSFCell cell8 = rowUp.createCell(88);
                cell8.setCellValue("其他信息");
                cell8.setCellStyle(getBackStyle(workbook, IndexedColors.ORANGE.index));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 88, 98));
                merageStyle(89, 98, columnTopStyle, rowm, cellTiltle);

                int columnNum = rowName.length;     // 定义所需列数
                HSSFRow rowDown2 = sheet.createRow(2);
                HSSFRow rowDown3 = sheet.createRow(3);
                for (int i = 0; i < columnNum; i++) {
                    if ((i >= 0 && i <= 35) || (i >= 38 && i <= 41) || i == 87 || i == 88 || i == 91 || (i >= 94 && i <= 99)) {
                        HSSFCell cell9 = rowDown2.createCell(i);
                        cell9.setCellValue(rowName[i]);
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 3, i, i));
                        merageUpDown(i, columnTopStyle, rowDown3);
                    }
                    if (i == 36) {
                        HSSFCell cell9 = rowDown2.createCell(36);
                        cell9.setCellValue("治疗阶段血糖监测方案");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 36, 37));
                        merageStyle(37, 37, columnTopStyle, rowDown2, cell9);
                    } else if (i == 42) {
                        HSSFCell cell9 = rowDown2.createCell(42);
                        cell9.setCellValue("糖尿病眼底病变");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 42, 46));
                        merageStyle(43, 46, columnTopStyle, rowDown2, cell9);
                    } else if (i == 47) {
                        HSSFCell cell9 = rowDown2.createCell(47);
                        cell9.setCellValue("糖尿病肾病");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 47, 51));
                        merageStyle(48, 51, columnTopStyle, rowDown2, cell9);
                    } else if (i == 52) {
                        HSSFCell cell9 = rowDown2.createCell(52);
                        cell9.setCellValue("糖尿病周围神经病变");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 52, 56));
                        merageStyle(53, 56, columnTopStyle, rowDown2, cell9);
                    } else if (i == 57) {
                        HSSFCell cell9 = rowDown2.createCell(57);
                        cell9.setCellValue("糖尿病足");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 57, 61));
                        merageStyle(58, 61, columnTopStyle, rowDown2, cell9);
                    } else if (i == 62) {
                        HSSFCell cell9 = rowDown2.createCell(62);
                        cell9.setCellValue("糖尿病自主神经病变");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 62, 66));
                        merageStyle(63, 66, columnTopStyle, rowDown2, cell9);
                    } else if (i == 67) {
                        HSSFCell cell9 = rowDown2.createCell(67);
                        cell9.setCellValue("冠心病");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 67, 71));
                        merageStyle(68, 71, columnTopStyle, rowDown2, cell9);
                    } else if (i == 72) {
                        HSSFCell cell9 = rowDown2.createCell(72);
                        cell9.setCellValue("原发性高血压");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 72, 76));
                        merageStyle(73, 76, columnTopStyle, rowDown2, cell9);
                    } else if (i == 77) {
                        HSSFCell cell9 = rowDown2.createCell(77);
                        cell9.setCellValue(" 下肢血管病变");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 77, 81));
                        merageStyle(78, 81, columnTopStyle, rowDown2, cell9);
                    } else if (i == 82) {
                        HSSFCell cell9 = rowDown2.createCell(82);
                        cell9.setCellValue("心脑血管疾病");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 82, 86));
                        merageStyle(83, 86, columnTopStyle, rowDown2, cell9);
                    } else if (i == 89) {
                        HSSFCell cell9 = rowDown2.createCell(89);
                        cell9.setCellValue("VPT值");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 89, 90));
                        merageStyle(89, 89, columnTopStyle, rowDown2, cell9);
                    } else if (i == 92) {
                        HSSFCell cell9 = rowDown2.createCell(92);
                        cell9.setCellValue("ABI值");
                        cell9.setCellStyle(columnTopStyle);
                        sheet.addMergedRegion(new CellRangeAddress(2, 2, 92, 93));
                        merageStyle(93, 93, columnTopStyle, rowDown2, cell9);
                    }
                    HSSFCell cell1Row3 = rowDown3.createCell(i);
                    cell1Row3.setCellValue(rowName[i]);
                    cell1Row3.setCellStyle(columnTopStyle);

                }


                // 将查询出的数据设置到sheet对应/的单元格中
                for (int i = 0; i < dataList.size(); i++) {
                    Object[] obj = dataList.get(i);   // 遍历每个对象
                    HSSFRow row = sheet.createRow(i + 4);   // 创建所需的行数
                    for (int j = 0; j < obj.length; j++) {
                        HSSFCell cellVal = null;   // 设置单元格的数据类型
                        cellVal = row.createCell(j, CellType.STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cellVal.setCellValue(obj[j].toString()); // 设置单元格的值
                        }
                        if ("".equals(obj[j]) || obj[j] == null) {
                            cellVal.setCellValue("  --  "); // 设置单元格的值
                        }
                        cellVal.setCellStyle(style); // 设置单元格样式
                    }
                }

                // 让列宽随着导出的列长自动适应
                for (int colNum = 0; colNum < columnNum; colNum++) {
                    int columnWidth = sheet.getColumnWidth(colNum) / 256;
                    for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                        HSSFRow currentRow;
                        // 当前行未被使用过
                        if (sheet.getRow(rowNum) == null) {
                            currentRow = sheet.createRow(rowNum);
                        } else {
                            currentRow = sheet.getRow(rowNum);
                        }
                        if (currentRow.getCell(colNum) != null) {
                            HSSFCell currentCell = currentRow.getCell(colNum);
                            if (currentCell.getCellType() == CellType.STRING) {
                                int length = currentCell.getStringCellValue()
                                        .getBytes().length;
                                if (columnWidth < length) {
                                    if (length > 250) {
                                        columnWidth = 250;
                                    } else {
                                        columnWidth = length;
                                    }

                                }
                            }
                        }
                    }
                    if (colNum == 0) {
                        sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                    } else {
                        sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                    }
                }
            }
                List<Object> name = new ArrayList<>();
                List<List<Object>> blood = new ArrayList<>();
                int a = listName.size() / 240 + 1;
                for (int i = 0; i < a; i++) {
                    HSSFSheet sheet1 = null;
                    if (i == 0){
                        sheet1 = workbook.createSheet("血糖记录"); // 创建工作表
                    }else {
                        sheet1 = workbook.createSheet("血糖记录"+i); // 创建工作表
                    }
                    if ((i * 240 + 240) > listName.size()){
                        name = listName.subList(i * 240,listName.size());
                        blood = listBloodSugar.subList(i * 240,listName.size());
                    }else{
                        name = listName.subList(i * 240,i * 240 + 240);
                        blood = listBloodSugar.subList(i * 240,i * 240 + 240);
                    }
                    export3(sheet1, columnTopStyle, style,name, blood,listDate);
                }
            workbook.write(out);
        }catch (Exception e){
            logger.error("",e);
        }
    }

    private void exportHospitalBlood(List<Object[]> data,String[] rowName,OutputStream out) {
        try {

            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            int sheetNum = data.size() / 65500 + 1;
            for (int k = 0; k < sheetNum; k++) {
                HSSFSheet sheet =null;
                if (k==0){
                    sheet = workbook.createSheet("血糖列表"); // 创建工作表
                }else {
                    sheet = workbook.createSheet("血糖列表"+k); // 创建工作表
                }
                List<Object[]> dataList = new ArrayList<>();
                if ((k * 65500 + 65500) > data.size()){
                    dataList = data.subList(k * 65500,data.size());
                }else{
                    dataList = data.subList(k * 65500,k * 65500 + 65500);
                }
                HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
                HSSFCellStyle style = this.getBloodSugarStyle(workbook); // 获取单元格样式对象
                int columnNum = rowName.length;     // 定义所需列数
                HSSFRow rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)

                // 将列头设置到sheet的单元格中
                for (int n = 0; n < rowName.length; n++) {
                    HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                    cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
                    cellRowName.setCellValue(rowName[n]); // 设置列头单元格的值
                    cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
                }
                // 将查询出的数据设置到sheet对应/的单元格中
                for (int i = 0; i < dataList.size(); i++) {
                    Object[] obj = dataList.get(i);   // 遍历每个对象
                    HSSFRow row = sheet.createRow(i + 1);   // 创建所需的行数
                    for (int j = 0; j < obj.length; j++) {
                        HSSFCell cell = null;   // 设置单元格的数据类型
                        cell = row.createCell(j, CellType.STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            //cell.setCellValue(obj[j].toString()); // 设置单元格的值
                            cell.setCellValue(new HSSFRichTextString(obj[j].toString())); // 设置单元格的值
                        }
                        if ("".equals(obj[j]) || obj[j] == null) {
                            cell.setCellValue(""); // 设置单元格的值
                        }

                        cell.setCellStyle(style); // 设置单元格样式
                    }
                }
                // 让列宽随着导出的列长自动适应
                for (int colNum = 0; colNum < columnNum; colNum++) {
                    int columnWidth = sheet.getColumnWidth(colNum) / 256;
                    for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                        HSSFRow currentRow;
                        // 当前行未被使用过
                        if (sheet.getRow(rowNum) == null) {
                            currentRow = sheet.createRow(rowNum);
                        } else {
                            currentRow = sheet.getRow(rowNum);
                        }
                        if (currentRow.getCell(colNum) != null) {
                            HSSFCell currentCell = currentRow.getCell(colNum);
                            if (currentCell.getCellType() == CellType.STRING) {
                                int length = currentCell.getStringCellValue()
                                        .getBytes().length;
                                if (columnWidth < length) {
                                    columnWidth = length;
                                }
                            }
                        }
                    }
                    if (colNum == 0) {
                        sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                    } else {
                        sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            logger.error("",e);
        }
    }

    private void export3(HSSFSheet sheet,HSSFCellStyle columnTopStyle,HSSFCellStyle style,List<Object> listName,List<List<Object>> listBloodSugar,List<Object> listDate/*,OutputStream out*/) {
        try {
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle0 = rowm.createCell(0);
            cellTiltle0.setCellValue("                     ");
            cellTiltle0.setCellStyle(columnTopStyle);
            HSSFCell cellTiltle1= rowm.createCell(1);
            cellTiltle1.setCellValue("             ");
            cellTiltle1.setCellStyle(columnTopStyle);
            for (int i = 0; i < listName.size(); i++) {

                HSSFCell cellTiltle2 = rowm.createCell(i+2);
                cellTiltle2.setCellValue(listName.get(i).toString());
                cellTiltle2.setCellStyle(columnTopStyle);
            }
            List<HSSFRow> listRow = new ArrayList<>();
            String[] lineName = {"空腹","早餐后","午餐前","午餐后","晚餐前","晚餐后"};
            for (int i = 0; i < listDate.size(); i++) {
                for (int j = 0; j < lineName.length; j++) {
                    HSSFRow row1 = sheet.createRow(1+j+i*6);
                    listRow.add(row1);
                    HSSFCell cellTiltle = row1.createCell(1);
                    cellTiltle.setCellValue(lineName[j]);
                    cellTiltle.setCellStyle(columnTopStyle);
                    if (lineName[j].equals("空腹")){
                        HSSFCell cell = row1.createCell(0);
                        cell.setCellStyle(columnTopStyle);
                        cell.setCellValue(listDate.get(i).toString());
                        sheet.addMergedRegion(new CellRangeAddress(1+i*6, 6+i*6,0,0));
                    }
                    if (i == listDate.size()-1 && j == lineName.length-1){
                        merageUpDown(0,columnTopStyle,row1);
                    }
                }
            }

            for (int j = 0; j < listBloodSugar.size(); j++) {
                for (int i = 0; i < listDate.size()*6; i++) {
                    HSSFCell cell = listRow.get(i).createCell( 2+j);
                    cell.setCellValue(listBloodSugar.get(j).get(i).toString());
                    cell.setCellStyle(style);
                }
            }
            int columnNum = 2;// 定义所需列数
            if (listName != null && listName.size() > 0){
                columnNum = listName.size()+2;
            }
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == CellType.STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        } catch (Exception e) {
            logger.error("",e);
        }
    }

    private void exportHospitalDate(List<Object[]> data,String[] rowName,OutputStream out) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet("医院统计"); // 创建工作表
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
            HSSFCellStyle style = this.getBloodSugarStyle(workbook); // 获取单元格样式对象
            int columnNum = rowName.length;     // 定义所需列数
            HSSFRow rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < rowName.length; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
                cellRowName.setCellValue(rowName[n]); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }
            // 将查询出的数据设置到sheet对应/的单元格中
            for (int i = 0; i < data.size(); i++) {
                Object[] obj = data.get(i);   // 遍历每个对象
                HSSFRow row = sheet.createRow(i + 1);   // 创建所需的行数
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   // 设置单元格的数据类型
                    cell = row.createCell(j, CellType.STRING);
                    if (!"".equals(obj[j]) && obj[j] != null) {
                        cell.setCellValue(new HSSFRichTextString(obj[j].toString())); // 设置单元格的值
                    }
                    if ("".equals(obj[j]) || obj[j] == null) {
                        cell.setCellValue(""); // 设置单元格的值
                    }
                    cell.setCellStyle(style); // 设置单元格样式
                }
            }
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == CellType.STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            logger.error("",e);
        }
    }


    public void exportDailyDyBloodSugar(DynamicBloodSugarDynamicVO dynamicVO, Map<String, Object> memberMap, Map<String, Map<String, Double>> dtDataMap, HttpServletResponse response){
        MemberPO memberPO = (MemberPO) memberMap.get("member");
        CheckinInfoBO inHospitalInfo = (CheckinInfoBO) memberMap.get("inHospitalInfo");
        String name = "--";
        String sex = "--";
        String age = "--";
        String diabetesType = "--";
        String hospitalNo = "--";
        String departmentName = "--";
        String bedNo = "--";
        String idCard = "--";
        if(memberPO != null){
            name = memberPO.getMemberName();
            sex = memberPO.getSex() == 1? "男": memberPO.getSex() == 2? "女" : "--";
            age = memberPO.getAge() + "";
            idCard = memberPO.getIdCard();
            if(!StringUtils.isBlank(memberPO.getDiabetesType())){
                switch (memberPO.getDiabetesType()) {
                    case "SUGAR_TYPE_001":
                        diabetesType = "1型糖尿病";
                        break;
                    case "SUGAR_TYPE_002":
                        diabetesType = "2型糖尿病";
                        break;
                    case "SUGAR_TYPE_003":
                        diabetesType = "妊娠糖尿病";
                        break;
                    case "SUGAR_TYPE_004":
                        diabetesType = "其他";
                        break;
                    case "SUGAR_TYPE_005":
                        diabetesType = "非糖尿病";
                        break;
                    case "SUGAR_TYPE_006":
                        diabetesType = "特殊糖尿病";
                        break;
                }
            }
        }
        if(inHospitalInfo != null){
            hospitalNo = inHospitalInfo.getHospitalNo();
            departmentName = inHospitalInfo.getDepartmentName();
            bedNo = inHospitalInfo.getBedNo();
        }
        String memberInfo = "%-25s\t%-25s\t%-25s\t%-25s\t\n%-25s\t%-25s\t%-25s\t%-25s\t\n%-25s\t%-25s\t";
        memberInfo = String.format(memberInfo, "姓名：" + name, "性别：" + sex, "年龄：" + age, "诊断：" + diabetesType, "住院号/门诊号：" + hospitalNo,
                "科室：" + departmentName, "病区：--", "床号：" + bedNo,
                "导出时间：" + DateHelper.getDate(new Date(), DateHelper.DAY_FORMAT), "身份证号：" + idCard, "", "");
        HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
        int totalEvent = dynamicVO.getTotalEvent();
        HSSFCellStyle style = this.getBloodSugarStyle(workbook); // 获取单元格样式对象
        HSSFCellStyle columnTopStyle = this.getBackStyle(workbook, IndexedColors.LIGHT_GREEN.index);// 获取列头样式对象
        List<DyBloodSugarValueDTO> valueDTOS = dynamicVO.getValueList();
        for(int i = 0; i < dynamicVO.getDataList().size(); i ++){
            DynamicBloodSugarDynamicItemVO itemVO = dynamicVO.getDataList().get(i);
            HSSFSheet sheet = workbook.createSheet(itemVO.getRecordDt()); // 创建工作表
            HSSFRow row = sheet.createRow(0);
            HSSFCell c = row.createCell(0, CellType.STRING);
            c.setCellStyle(style);
            c.setCellValue(new HSSFRichTextString(memberInfo));
            CellRangeAddress cra = new CellRangeAddress(0, 8, 0, 12);
            sheet.addMergedRegion(cra);
            String [] titles = new String[]{"日期","测量次数","达标时间占比（TIR）","<3mmol/L时间占比","低于目标范围时间占比","高于目标范围时间占比",">13mmol/L时间占比","平均血糖","预估糖化血红蛋白","血糖标准差","平均血糖波动幅度","血糖变异系数","日间血糖平均绝对差"};
            row = sheet.createRow(9);
            row.setHeight((short) (2 * 256));
            HSSFRow valRow = sheet.createRow(10);
            valRow.setHeight((short) (2 * 256));
            HSSFRow title = sheet.createRow(11);
            title.setHeight((short) (2 * 256));
            for(int j = 0; j < titles.length;j ++){
                if(j == 0 || j == 1 || j == 7 || j == 9) {
                    sheet.setColumnWidth(j, 15 * 256);
                }else {
                    sheet.setColumnWidth(j, 23 * 256);
                }
                HSSFCell cell = row.createCell(j, CellType.STRING);  // 设置单元格的数据类型
                cell.setCellValue(new HSSFRichTextString(titles[j])); // 设置单元格的值
                cell.setCellStyle(columnTopStyle); // 设置单元格样式

                HSSFCell valCell = valRow.createCell(j, CellType.STRING);;   // 设置单元格的数据类型
                valCell.setCellStyle(style);

                HSSFCell titleCell = title.createCell(j, CellType.STRING);  // 设置单元格的数据类型
                if (j == 1 || j == 4 || j == 7 || j == 10) {
                    titleCell.setCellValue(new HSSFRichTextString("时间")); // 设置单元格的值
                }else if(j == 2 || j == 5 || j == 8 || j == 11){
                    titleCell.setCellValue(new HSSFRichTextString("血糖")); // 设置单元格的值
                }else {
                    titleCell.setCellValue(new HSSFRichTextString("")); // 设置单元格的值
                }
                titleCell.setCellStyle(columnTopStyle); // 设置单元格样式
                String value = "";
                switch (j){
                    case 0:
                        value = itemVO.getRecordDt(); // 日期
                        break;
                    case 1:
                        value = String.valueOf(itemVO.getTotalEvent()); // 测量次数
                        break;
                    case 2:
                        value = itemVO.getAwiTimeRateOfNormal() + "%"; // 达标时间占比（TIR）
                        break;
                    case 3:
                        value = itemVO.getCustomLessThanRatio() + "%"; // < 3mmol/L时间占比
                        break;
                    case 4:
                        value = itemVO.getAwiTimeRateOfLow() + "%"; // 低于目标范围时间占比
                        break;
                    case 5:
                        value = itemVO.getAwiTimeRateOfHigh() + "%"; // 高于目标范围时间占比
                        break;
                    case 6:
                        value = itemVO.getCustomGreaterThanRatio() + "%"; // > 13mmol/L时间占比
                        break;
                    case 7:
                        value = itemVO.getAvgNum() + ""; // 平均血糖
                        break;
                    case 8:
                        value = itemVO.getGhb() + ""; // 预估糖化血红蛋白
                        break;
                    case 9:
                        value = itemVO.getStandardVal() + ""; // 血糖标准差
                        break;
                    case 10:
                        value = itemVO.getMeanAmplitudeOfGlycemicExcursion() + ""; // 平均血糖波动幅度
                        break;
                    case 11:
                        value = itemVO.getCoefficientOfVariation() + ""; // 血糖变异系数
                        break;
                    case 12:
                        if(itemVO.getBloodSugarMeanAbsoluteDeviation() != null) {
                            value = itemVO.getBloodSugarMeanAbsoluteDeviation() + ""; // 日间血糖平均绝对差
                        }else {
                            value = "--";
                        }
                        break;
                }
                valCell.setCellValue(new HSSFRichTextString(value));
            }

            List<HSSFRow> rows = new ArrayList<>();

            Map<String, Double> map = dtDataMap.get(itemVO.getRecordDt());
            List<String> keys = new ArrayList<>(map.keySet());
            Collections.sort(keys);
            for (int j = 0; j < keys.size(); j++) {
                if(j < 24) {
                    row = sheet.createRow(12 + j);
                    rows.add(row);
                }else {
                    row = rows.get(j % 24);
                }
                int index = j/24;
                HSSFCell cell = row.createCell(3 * index, CellType.STRING);;   // 设置单元格的数据类型
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(""));
                cell = row.createCell(3*index + 1, CellType.STRING);;   // 设置单元格的数据类型
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(keys.get(j)));
                cell = row.createCell(3*index + 2, CellType.STRING);;   // 设置单元格的数据类型
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(String.format("%.2f", map.get(keys.get(j)))));
            }
        }
        try {
            OutputStream output = response.getOutputStream();
            String fileName = new String("动态血糖.xls".getBytes("UTF-8"), "iso-8859-1");
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename="+fileName);
//        response.setContentType("application/msexcel");
            response.setContentType("application/vnd.ms-excel");
            workbook.write(output);
            this.close(output);
        } catch (IOException e) {
            logger.error("", e);
        }
    }
    //带填充颜色的单元格样式
    private HSSFCellStyle getBackStyle(HSSFWorkbook workbook,short s) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBold(true);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置单元格的填充颜色
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(s);
        return style;

    }

    /*
     * 列头单元格样式
     */
    private HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBold(true);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }



    /*
     * 列数据信息单元格样式
     */
    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        // font.setFontHeightInPoints((short)10);
        // 字体加粗
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private HSSFCellStyle getBloodSugarStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        // font.setFontHeightInPoints((short)10);
        // 字体加粗
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(true);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
