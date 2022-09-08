package com.comvee.cdms.common.utils;

import java.io.*;
import java.util.Base64;

public class SerializableUtils {

    /**
     * 序列化对象
     * @param obj
     * @return
     */
    public static String serialize(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }

    /**
     * 反序列化对象
     * @param sessionStr
     * @return
     */
    public static Object deserialize(String sessionStr) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(sessionStr));
            MyObjectInputStream ois = new MyObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }

    static class MyObjectInputStream extends ObjectInputStream {

        public MyObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            String name = desc.getName();
            try {
                //使用当前线程上下文的类加载，防止springboot jar运行的情况下加载不到class的问题
                return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException ex) {
                throw ex;
            }
        }
    }
}
