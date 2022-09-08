package com.comvee.cdms.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MachineCodeUtils {

    /**
     * 获取机器码
     * @return
     */
    public static String getMachineCode(){
        if(judgeOs("windows")){
            return getWindowsMachineCode();
        }else{
            return getLinuxMachineCode();
        }
    }

    /**
     * windows的机器码获取
     * @return
     */
    private static String getWindowsMachineCode(){
        String[] cmdArr = new String[]{"wmic csproduct get uuid"
                ,"wmic CPU get ProcessorID"
                ,"wmic baseboard get serialnumber"
                ,"wmic diskdrive get serialnumber"
        };
        String execResult = execCmd(cmdArr);
        return createMachineCode(execResult);
    }

    /**
     * linux的机器码获取
     * @return
     */
    private static String getLinuxMachineCode(){
        String[] cmdArr = new String[]{"dmidecode -t 4 | grep ID |sort -u |awk -F': ' '{print $2}'"
            ,"fdisk -l |grep 'Disk identifier' |awk {'print $3'}"
            ,"dmidecode -s system-serial-number"};
        String execResult = execCmd(cmdArr);
        return createMachineCode(execResult);
    }

    /**
     * 生成机器码
     * @param result
     * @return
     */
    private static String createMachineCode(String result){
        return MD5Util.md5(result).toUpperCase();
    }

    /**
     * 执行cmd命令
     * @param cmd
     * @param ignoreStart
     * @return
     */
    private static String execCmd(String[] cmd){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            for(String s : cmd){
                Process process = Runtime.getRuntime().exec(s);
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                String line = "";
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        }catch (Exception e){
            throw new RuntimeException("执行cmd命令发生错误" ,e);
        }
        return stringBuilder.toString();
    }

    /**
     * 判断操作系统
     * @param osName
     * @return
     */
    private static boolean judgeOs(String osName){
        return System.getProperty("os.name").toLowerCase().indexOf(osName.toLowerCase()) >= 0;
    }

}
