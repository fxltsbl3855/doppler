package com.sinoservices.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: zy
 * Date: 14-4-16
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 */
public class NumberUtil {

    public static double formatPrice(Double str,Integer scaleNum ) {
        try {
            BigDecimal b = new BigDecimal(str);
            return b.setScale(scaleNum,BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public static Byte formatByte(String str, Byte defaultValue) {
        if(str == null || "".equals(str)){
            return defaultValue;
        }
        try {
            Byte result = Byte.parseByte(str.trim());
            return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Short formatShort(String str, Short defaultValue) {
        if(str == null || "".equals(str)){
            return defaultValue;
        }
        try {
            Short result = Short.parseShort(str.trim());
            return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String minus(String str, String str2) {
        BigDecimal b1 = new BigDecimal(str);
        BigDecimal b2 = new BigDecimal(str2);
        BigDecimal res = b1.subtract(b2);
        res = res.setScale(2,BigDecimal.ROUND_HALF_UP) ;
        return res.toString();

    }

    public static BigDecimal formatBigDecimal(String str) {
        try{
            BigDecimal b1 = new BigDecimal(str);
            return b1;
        }catch (Exception e){
             return null;
        }
    }

    public static long formatLong(String str, long defaultValue) {
        if (str == null || "".equals(str))
            return defaultValue;
        try {
            Long result = Long.parseLong(str.trim());
            return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer formatInteger(BigDecimal bg) {
        bg =  bg.setScale(2, BigDecimal.ROUND_HALF_UP);
        bg = bg.movePointRight(2) ;
        Integer val = bg.intValue();
        return val;
    }

    public static String ybToRMB(Integer yb) {
        if(yb == null || yb <= 0){
            return "0";
        }
        BigDecimal bd = new BigDecimal(yb.toString());
        bd = bd.movePointLeft(2);
        return bd.toString();
    }

    public static Integer formatNumber(String str, Integer defaultValue) {
        if (str == null || "".equals(str))
            return defaultValue;
        try {
            BigDecimal b1 = new BigDecimal(str);
            return b1.intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double devide(Integer value,Integer value2,int pointNum) {
        if(value == null){
            value=0;
        }
        if(value2 == null || value2 ==0){
            return 0;
        }
        double v = value;
        v= v/value2;
        BigDecimal   b   =   new   BigDecimal(v);
        return b.setScale(pointNum,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double doublePoint(double value,int pointNum) {
        BigDecimal   b   =   new   BigDecimal(value);
        return b.setScale(pointNum,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int doubleToInt(double value) {
        BigDecimal   b   =   new   BigDecimal(value);
        return b.intValue();
    }

    public static double formatDouble(String str, Double defaultValue) {
        if (str == null || "".equals(str))
            return defaultValue;
        try {
            Double result = Double.parseDouble(str.trim());
            return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float formatFolat(String str, Float defaultValue) {
        if (str == null || "".equals(str))
            return defaultValue;
        try {
            Float result = Float.parseFloat(str.trim());
            return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void main(String[] a) {

        System.out.println(devide(2,3,2));


    }


}
