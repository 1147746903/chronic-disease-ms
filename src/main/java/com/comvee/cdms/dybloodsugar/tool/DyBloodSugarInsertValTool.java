package com.comvee.cdms.dybloodsugar.tool;

import com.comvee.cdms.common.utils.DateHelper;
import com.comvee.cdms.dybloodsugar.dto.CoordinateDTO;
import com.comvee.cdms.dybloodsugar.dto.HasFewDataDTO;
import com.comvee.cdms.dybloodsugar.po.DYYPBloodSugarPO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodChartDataItemVO;
import com.comvee.cdms.dybloodsugar.vo.DynamicBloodChartDayItemVO;
import org.springframework.util.MultiValueMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: chenhb
 * @description: 动态血糖插值模拟
 * @data: 2021/5/17 16:30
 **/
public class DyBloodSugarInsertValTool {
    private static DecimalFormat df = new DecimalFormat("#.0");

    /**
     * @param {number} num 点个数
     * @param {Array}  p1 点坐标
     * @param {Array}  p2 点坐标
     * @param {Array}  p3 点坐标
     * @param {Array}  p4 点坐标
     *                 如果参数是 num, p1, p2 为一阶贝塞尔
     *                 如果参数是 num, p1, c1, p2 为二阶贝塞尔
     *                 如果参数是 num, p1, c1, c2, p2 为三阶贝塞尔
     * @desc 获取点，这里可以设置点的个数
     */
    public static List<CoordinateDTO> getBezierPoints(double num, CoordinateDTO p1, CoordinateDTO p2, CoordinateDTO p3, CoordinateDTO p4) {
        List<CoordinateDTO> points = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            points.add(threeBezier(i / num, p1, p2, p3, p4));
        }
        if (p4 != null) {
            points.add(p4);
        } else if (p3 != null) {
            points.add(p3);
        }
        return points;
    }


    /**
     * @param {number} t 当前百分比
     * @param {Array}  p1 起点坐标
     * @param {Array}  p2 终点坐标
     * @param {Array}  cp1 控制点1
     * @param {Array}  cp2 控制点2
     * @desc 三阶贝塞尔
     */
    private static CoordinateDTO threeBezier(double t, CoordinateDTO p1, CoordinateDTO cp1, CoordinateDTO cp2, CoordinateDTO p2) {
        Double x = p1.getX() * (1 - t) * (1 - t) * (1 - t) +
                3 * cp1.getX() * t * (1 - t) * (1 - t) +
                3 * cp2.getX() * t * t * (1 - t) +
                p2.getX() * t * t * t;
        Double y = p1.getY() * (1 - t) * (1 - t) * (1 - t) +
                3 * cp1.getY() * t * (1 - t) * (1 - t) +
                3 * cp2.getY() * t * t * (1 - t) +
                p2.getY() * t * t * t;
        return new CoordinateDTO(x, y);
    }


    private static void add(CoordinateDTO out, CoordinateDTO v1, CoordinateDTO v2) {
        out.setX(v1.getX() + v2.getX());
        out.setY(v1.getY() + v2.getY());
    }

    private static Double distance(CoordinateDTO v1, CoordinateDTO v2) {
        return Math.sqrt((v1.getX() - v2.getX()) * (v1.getX() - v2.getX()) +
                (v1.getY() - v2.getY()) * (v1.getY() - v2.getY()));
    }

    private static CoordinateDTO min(CoordinateDTO out, CoordinateDTO v1, CoordinateDTO v2) {
        out.setX(Math.min(v1.getX(), v2.getX()));
        out.setY(Math.min(v1.getY(), v2.getY()));
        return out;
    }

    private static CoordinateDTO max(CoordinateDTO out, CoordinateDTO v1, CoordinateDTO v2) {
        out.setX(Math.max(v1.getX(), v2.getX()));
        out.setY(Math.max(v1.getY(), v2.getY()));
        return out;
    }

    private static void scale(CoordinateDTO out, CoordinateDTO v, Double s) {
        out.setX(v.getX() * s);
        out.setY(v.getY() * s);
    }


    private static CoordinateDTO clone(CoordinateDTO v) {
        return new CoordinateDTO(v.getX(), v.getY());
    }

    private static void sub(CoordinateDTO out, CoordinateDTO v1, CoordinateDTO v2) {
        out.setX(v1.getX() - v2.getX());
        out.setY(v1.getY() - v2.getY());
    }


    private static List<CoordinateDTO> smoothBezier(List<CoordinateDTO> points, double smooth, boolean isLoop) {
        // debugger
        List<CoordinateDTO> cps = new ArrayList<>();
        CoordinateDTO v = new CoordinateDTO();
        CoordinateDTO v1 = new CoordinateDTO();
        CoordinateDTO v2 = new CoordinateDTO();
        CoordinateDTO prevPoint;
        CoordinateDTO nextPoint;
        for (int i = 0, len$$1 = points.size(); i < len$$1; i++) {
            CoordinateDTO point = points.get(i);

            if (i == 0 || i == len$$1 - 1) {
                cps.add(clone(points.get(i)));
                continue;
            } else {
                prevPoint = points.get(i - 1);
                nextPoint = points.get(i + 1);
            }


            sub(v, nextPoint, prevPoint);
            scale(v, v, smooth);
            Double d0 = distance(point, prevPoint);
            Double d1 = distance(point, nextPoint);
            Double sum = d0 + d1;

            if (sum != 0) {
                d0 /= sum;
                d1 /= sum;
            }

            scale(v1, v, -d0);
            scale(v2, v, d1);
            CoordinateDTO cp0 = new CoordinateDTO();
            CoordinateDTO cp1 = new CoordinateDTO();
            add(cp0, point, v1);
            add(cp1, point, v2);

            cps.add(cp0);
            cps.add(cp1);
        }
        return cps;
    }

    // 时间转点
    private static Double changeStrToMinutes(String str) {
        String[] abominates = str.split(":");
        if (abominates.length == 2) {
            return Double.parseDouble(abominates[0]) * 60 + Double.parseDouble(abominates[1]);
        } else {
            return 0d;
        }
    }


    // date 代表指定的日期，格式：2018-09-27
    // day 传-1表始前一天，传1表始后一天
    // JS获取指定日期的前一天，后一天
    private static String getNextDate(String date, int day) {
        Date offsetDate = DateHelper.getOffsetDate(DateHelper.getDate(date, "yyyy-MM-dd"), day);
        return DateHelper.getDate(offsetDate, "yyyy-MM-dd");
    }

    private static HasFewDataDTO hasFewData(String date, List<DynamicBloodChartDataItemVO> dataItemList, MultiValueMap<String, DYYPBloodSugarPO> dataSimulationList) {
        HasFewDataDTO ret = new HasFewDataDTO();
        if (dataItemList != null && !dataItemList.isEmpty()) {
            String startTime = dataItemList.get(0).getTime();
            String endTime = dataItemList.get(dataItemList.size() - 1).getTime();
            String beforeDate = getNextDate(date, -1);
            String nextDate = getNextDate(date, 1);
            // 头少值
            if (dataSimulationList.containsKey(beforeDate)) {
                DYYPBloodSugarPO lastTime = dataSimulationList.get(beforeDate).get(1);
                String recordTime = DateHelper.getDate(lastTime.getRecordTime(), "HH:mm");
                if (changeStrToMinutes(startTime) + changeStrToMinutes("24:00") - changeStrToMinutes(recordTime) == 15) {
                    if (changeStrToMinutes(startTime) > 10 && changeStrToMinutes(startTime) < 15) {
                        // 差两个值
                        CoordinateDTO pointHead = new CoordinateDTO();
                        pointHead.setX(changeStrToMinutes(recordTime) - changeStrToMinutes("24:00"));
                        pointHead.setY(lastTime.getValue().doubleValue());
                        ret.setPointHead(pointHead);
                        ret.setCountHead(2);
                    } else if (changeStrToMinutes(startTime) > 5 && changeStrToMinutes(startTime) < 10) {
                        // 差一个值
                        CoordinateDTO pointHead = new CoordinateDTO();
                        pointHead.setX(changeStrToMinutes(recordTime) - changeStrToMinutes("24:00"));
                        pointHead.setY(lastTime.getValue().doubleValue());
                        ret.setPointHead(pointHead);
                        ret.setCountHead(1);
                    }
                }
            }
            // 尾少值
            if (dataSimulationList.containsKey(nextDate)) {
                DYYPBloodSugarPO lastTime = dataSimulationList.get(nextDate).get(0);
                String recordTime = DateHelper.getDate(lastTime.getRecordTime(), "HH:mm");
                if (changeStrToMinutes(recordTime) + changeStrToMinutes("24:00") - changeStrToMinutes(endTime) == 15) {
                    if (changeStrToMinutes(endTime) > 1425 && changeStrToMinutes(endTime) < 1430) {
                        // 差两个值
                        CoordinateDTO pointHead = new CoordinateDTO();
                        pointHead.setX(changeStrToMinutes(recordTime) + changeStrToMinutes("24:00"));
                        pointHead.setY(lastTime.getValue().doubleValue());
                        ret.setPointHead(pointHead);
                        ret.setCountHead(2);
                    } else if (changeStrToMinutes(endTime) > 1430 && changeStrToMinutes(endTime) < 1435) {
                        // 差一个值
                        CoordinateDTO pointHead = new CoordinateDTO();
                        pointHead.setX(changeStrToMinutes(recordTime) + changeStrToMinutes("24:00"));
                        pointHead.setY(lastTime.getValue().doubleValue());
                        ret.setPointHead(pointHead);
                        ret.setCountHead(1);
                    }
                }
            }
        }
        return ret;
    }


    public static List<CoordinateDTO> bloodGlucoseInterpolation(DynamicBloodChartDayItemVO chartDatum, MultiValueMap<String, DYYPBloodSugarPO> dataSimulationList) {
        List<CoordinateDTO> finalData = new ArrayList<>();
        String date = chartDatum.getDate();
        List<DynamicBloodChartDataItemVO> dataItemList = chartDatum.getItemList();
        List<CoordinateDTO> points = new ArrayList<>();
        for (DynamicBloodChartDataItemVO itemVO : dataItemList) {
            itemVO.setTime(itemVO.getTime().substring(0, itemVO.getTime().lastIndexOf(':')));
            CoordinateDTO dto = new CoordinateDTO();
            dto.setX(changeStrToMinutes(itemVO.getTime()));
            dto.setY(itemVO.getValue());
            points.add(dto);
        }
        // 判断是否少值
        HasFewDataDTO fewData = hasFewData(chartDatum.getDate(), dataItemList, dataSimulationList);
        if (fewData.getCountHead() != null && fewData.getCountHead() > 0) {
            if (fewData.getPointHead() != null)
                points.add(0, fewData.getPointHead());
        }
        if (fewData.getCountTail() != null && fewData.getCountTail() > 0) {
            if (fewData.getPointTail() != null)
                points.add(fewData.getPointTail());
        }
        int len = points.size();
        List<CoordinateDTO> controlPoints = smoothBezier(points, 0.5, false);
        int finalDataLn = finalData.size();
        for (int i = 0; i < len - 1; i++) {
            CoordinateDTO cp1 = controlPoints.get(i * 2);
            CoordinateDTO cp2 = controlPoints.get(i * 2 + 1);
            CoordinateDTO p = points.get((i + 1) % len);
            int ln = 0;
            List<CoordinateDTO> bezierPoints = getBezierPoints(60, points.get(i), cp1, cp2, p);
            for (int j = 0; j < bezierPoints.size(); j++) {
                if (j != 60 || i == len - 2) {
                    Integer a = points.get(i).getX().intValue() + ln * 5;
                    Integer b = bezierPoints.get(j).getX().intValue();
                    // if(a===b)console.log(a,b);
                    if (a.equals(b)) {
                        boolean flag = false;
                        for (CoordinateDTO bezierPoint : finalData) {
                            Integer a1 = bezierPoint.getX().intValue();
                            if (a1.equals(a) && bezierPoint.getT().equals(date)) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            ln++;
                            bezierPoints.get(j).setT(date);
                            finalData.add(bezierPoints.get(j));
                        }
                    }
                }
            }
        }
        if (fewData.getCountHead() != null) {
            if (fewData.getCountHead() == 2) {
                //过滤条1条
                finalData.remove(finalDataLn);
            } else if (fewData.getCountHead() == 1) {
                //过滤条2条
                finalData.remove(finalDataLn + 1);
                finalData.remove(finalDataLn);
            }
        }

        if (fewData.getCountTail() != null) {
            if (fewData.getCountTail() == 2) {
                //过滤条1条
                finalData.remove(finalData.size() - 1);
            } else if (fewData.getCountTail() == 1) {
                //过滤条2条
                finalData.remove(finalData.size() - 2);
            }
        }
        return finalData;
    }

    public static List<DynamicBloodChartDataItemVO> changeHourMinutes(DynamicBloodChartDayItemVO chartDatum, MultiValueMap<String, DYYPBloodSugarPO> dataSimulationList) {
        List<CoordinateDTO> finalData = bloodGlucoseInterpolation(chartDatum, dataSimulationList);
        List<DynamicBloodChartDataItemVO> itemList = new ArrayList<>();
        for (CoordinateDTO itemVO : finalData) {
            DynamicBloodChartDataItemVO vo = new DynamicBloodChartDataItemVO();
            vo.setValue(Double.parseDouble(df.format(itemVO.getY())));
            vo.setTime(transTime(itemVO.getX()));
            itemList.add(vo);
        }
        return itemList;
    }

    private static String transTime(Double xx) {
        int x = xx.intValue();
        int s1 = x / 60;
        int s2 = x % 60;
        if (s1 < 10) {
            if (s2 < 10) {
                return "0" + s1 + ":" + "0" + s2;
            } else {
                return "0" + s1 + ":" + s2;
            }
        } else {
            if (s2 < 10) {
                return s1 + ":" + "0" + s2;
            } else {
                return s1 + ":" + s2;
            }
        }
    }

}
