package com.comvee.cdms.common.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * @author linyd
 */
public class FileOfReadLine {
    private String fileName;
    private ArrayList<String> arrayList;
    private long chars;
    // 初始化
    public FileOfReadLine(String fileName) {
        super();
        this.fileName = fileName;
        this.chars = 0;
    }

    public ArrayList<String> toArray() {
        RandomAccessFile rf = null;
        try {
            arrayList = new ArrayList<String>();
            File file = new File(fileName);
            rf = new RandomAccessFile(file,"rw");
            rf.seek(chars);
            if(chars==0){
                int totalLine = getTotalLines(file);
                for(int i=0;i<totalLine-50;i++){
                    rf.readLine();
                }
            }
            String str;
            while ((str = rf.readLine())!=null) {
                str = new String(str.getBytes("ISO-8859-1"),"utf-8");
                arrayList.add(str);
            }
            //返回此文件中的当前偏移量。
            chars = rf.getFilePointer();
            rf.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rf!=null){
                try {
                    rf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    /**
     * 文件内容的总行数
     */
    private int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }

    public static void main(String[] args) {
        FileOfReadLine a = new FileOfReadLine("/Users/linyudui/comvee/log4j2-log/console.log");
        a.toArray();
        ArrayList<String> list = a.getArrayList();
        for (String ss : list) {
            // 左对齐
            System.out.printf("%-4s",ss);
        }
    }
}
