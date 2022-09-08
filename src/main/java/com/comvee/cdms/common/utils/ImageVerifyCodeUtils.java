package com.comvee.cdms.common.utils;

import com.alibaba.fastjson.JSON;
import com.comvee.cdms.common.exception.BusinessException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
//import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ImageVerifyCodeUtils {

//    public static final String VERIFY_CODES = "123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final String VERIFY_CODES = "1234567890";
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private static final Cache<String ,String> VERIFY_CODES_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(5L , TimeUnit.MINUTES)
            .maximumSize(10000L)
            .build();

    public static CreateImageVerifyCodeResult createImageVerifyCode(){
        ByteArrayOutputStream byteArrayOutputStream = null;
        try{
            String verifyCode = createVerifyCode(4);
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputImage(200 ,80 ,byteArrayOutputStream ,verifyCode);
            String id = UUID.randomUUID().toString().replaceAll("-" ,"");
            VERIFY_CODES_CACHE.put(id ,verifyCode);
            CreateImageVerifyCodeResult result = new CreateImageVerifyCodeResult();
            result.setId(id);
            result.setVerifyCode(verifyCode);
            result.setImageBase64(Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
            return result;
        }catch (Exception e){
            throw new BusinessException("验证码生成失败" ,e);
        }finally {
            try {
                if(byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static boolean doVerifyCode(String id ,String verifyCode){
        if(StringUtils.isBlank(id) || StringUtils.isBlank(verifyCode)){
            return false;
        }
        String cacheVerifyCode = VERIFY_CODES_CACHE.getIfPresent(id);
        if(StringUtils.isBlank(cacheVerifyCode)){
            return false;
        }
        VERIFY_CODES_CACHE.invalidate(id);
        //大小写不敏感
        return cacheVerifyCode.toLowerCase().equals(verifyCode.toLowerCase());
    }

    private static String createVerifyCode(int codeSize){
        int length = VERIFY_CODES.length();
        StringBuilder verifyCode = new StringBuilder(codeSize);
        for(int i = 0; i < codeSize; i++){
            verifyCode.append(VERIFY_CODES.charAt(ThreadLocalRandom.current().nextInt(length)));
        }
        return verifyCode.toString();
    }

    private static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for(int i = 0; i < colors.length; i++){
            colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];
            fractions[i] = random.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h-4);

        //绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲


        int fontSize = h - 10;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for(int i = 0; i < verifySize; i++){
            g2.setColor(getRandColor(100, 160));
/*            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize/2, h/2);
            g2.setTransform(affine);*/
            g2.drawChars(chars, i, 1, ((w-10) / verifySize) * i + 5, h/2 + fontSize/2 - 10);
        }

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }

    public static class CreateImageVerifyCodeResult{

        private String id;
        private String verifyCode;
        private String imageBase64;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getImageBase64() {
            return imageBase64;
        }

        public void setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
        }
    }

}
