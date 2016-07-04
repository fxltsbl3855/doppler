package com.sinoservices.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 */
public class ObjectUtil {

   public static void main(String[] a){

   }
    public static boolean equals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }

        if(object1 instanceof String && ("".equals(String.valueOf(object1).trim())||"null".equals(String.valueOf(object1).trim()))){
            object1=null;
        }
        if(object2 instanceof String && ("".equals(String.valueOf(object2).trim())||"null".equals(String.valueOf(object2).trim()))){
            object2=null;
        }
        if (object1 == null && object2 == null) {
            return true;
        }
        if (object1 == null || object2 == null) {
            return false;
        }
        return object1.equals(object2);
    }

    public static void transferAll(Object fromObj,Object toObj)  {
        Field[] fromField  = fromObj.getClass().getDeclaredFields();
        Field[] toField  = toObj.getClass().getDeclaredFields();
        for(Field f : fromField){
            for(Field temp : toField){
                if(temp.getName().replaceAll("_", "").equalsIgnoreCase(f.getName().replaceAll("_", ""))){
                    try {
                        setValue(toObj, temp, getValue(fromObj, f));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
            }
        }
    }


    public static void transfer(Object fromObj,Object toObj) {
        Field[] fromField  = fromObj.getClass().getDeclaredFields();
        Field[] toField  = toObj.getClass().getDeclaredFields();
        for(Field f : fromField){
            if(f.getModifiers() != Modifier.PUBLIC) {
                continue;
            }
            for(Field temp : toField){
                if(temp.getName().replaceAll("_", "").equalsIgnoreCase(f.getName().replaceAll("_", ""))){
                    try {
                        setValue(toObj, temp, getValue(fromObj, f));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
            }
        }
    }



    private static String getValue(Object o1,Field f1) throws IllegalAccessException {
        f1.setAccessible(true);
        String str = f1.getGenericType().toString();
        if(str.equals("int")){
            return f1.get(o1).toString();
        }else if(str.equals("double")){
            return String.valueOf(f1.getDouble(o1));
        }else if(str.equals("float")){
            return String.valueOf(f1.getFloat(o1));
        }else if(str.equals("boolean")){
            return String.valueOf(f1.getBoolean(o1));
        }else if(str.equals("long")){
            return String.valueOf(f1.getLong(o1));
        }else if(str.equals("byte")){
            return String.valueOf(f1.getByte(o1));
        }else{
            return String.valueOf(f1.get(o1));
        }
    }

    private static void setValue(Object o1,Field f1,String value) throws IllegalAccessException {
        f1.setAccessible(true);
        String str = f1.getGenericType().toString();
        if(str.equals("int")) {
            f1.setInt(o1, NumberUtil.formatNumber(value, 0));
        }else if(str.equals("class java.lang.Integer")){
            f1.set(o1, NumberUtil.formatNumber(value, 0));
        }else if(str.equals("long")) {
            f1.setLong(o1, NumberUtil.formatLong(value, 0L));
        }else if(str.equals("class java.lang.Long")){
            f1.set(o1, NumberUtil.formatLong(value, 0L));
        }else if(str.equals("double")){
            f1.setDouble(o1, NumberUtil.formatDouble(value, 0D));
        }else if(str.equals("class java.lang.Double")){
            f1.set(o1, NumberUtil.formatDouble(value, 0D));
        }else if(str.equals("float")){
            f1.setFloat(o1, NumberUtil.formatFolat(value, 0F));
        }else if(str.equals("class java.lang.Float")){
            f1.set(o1, NumberUtil.formatFolat(value, 0F));
        }else if(str.equals("boolean")){
            f1.setBoolean(o1,value.equalsIgnoreCase("true")?true:false);
        }else if(str.equals("class java.lang.Boolean")){
            f1.set(o1, value.equalsIgnoreCase("true") ? true : false);
        }else  if(str.equals("class java.lang.String")){
            f1.set(o1, value);
        }else if(str.equals("byte")){
            f1.setByte(o1,NumberUtil.formatByte(value,(byte)0));
        }else if(str.equals("class java.lang.Byte")) {
            f1.set(o1,NumberUtil.formatByte(value,(byte)0));
        }else if(str.equals("class java.lang.Short")) {
            f1.set(o1,NumberUtil.formatShort(value, (short) 0));
        }else if(str.equals("short")){
            f1.setShort(o1, NumberUtil.formatShort(value, (short)0));
        }
    }

    /**
     * 对象转换为boolean
     * @param object
     * @return
     */
    public static boolean object2Bool(Object object) {
        if(object instanceof Boolean){
            return  (Boolean)object;
        }else if(object instanceof String){
            if("true".equals((object.toString()).trim())){
                return true;
            }
            return false;
        }
        return false;
    }

}
