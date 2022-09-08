package com.comvee.cdms.common.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    /**
     * 文件响应对象
     * @param bytes
     * @param fileName
     * @return
     */
    public static ResponseEntity<byte[]> fileResponse(final byte[] bytes ,String fileName){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attchement;filename=" + fileName);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes ,httpHeaders , HttpStatus.OK);
        return responseEntity;
    }
}
