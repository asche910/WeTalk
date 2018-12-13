package com.asche.wetalk.util;

import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class StringUtils {

    public static boolean isEmpty(String str){
        return str == null || str.trim().equals("");
    }

    public static String addOne(String value){
        int n = 0;
        try {
            n = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return Integer.toString(n + 1);
    }

    public static String addInteger(String value, int num){
        int n = 0;
        try {
            n = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return Integer.toString(n + num);
    }

    /**
     * 返回n+1倍的字符串
     */
    public static String expandLength(int n, String str){
        if (isEmpty(str))
            return null;
        if (n < 1)
            return str;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++){
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    /**
     * 对象序列化
     * @param object
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String objToString(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field: fields){
            String name = field.getName();
            if (name.contains("$") || name.contains("serialVersionUID"))
                continue;
            stringBuilder.append("\"" + name + "\":");

            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field.getGenericType().toString();

            Method method = object.getClass().getMethod("get" + name);

            if(type.contains("String")){
                String value = (String)method.invoke(object);
                stringBuilder.append("\"" + value + "\", ");
            }else if(type.contains("int")){
                int value = (int)method.invoke(object);
                stringBuilder.append(value + ", ");
            }else if(type.contains("long")){
                long value = (long)method.invoke(object);
                stringBuilder.append(value + ", ");
            }
        }
        String str = stringBuilder.toString();
        String result = str.substring(str.length()-2).equals(", ") ?
                str.substring(0, str.length() - 2) : str;
        return result + "}";
    }

    /**
     * 对象反序列化 至 参数obj中
     * @param json
     * @param obj
     * @throws Exception
     */
    public static void stringToObj(String json, Object obj) throws Exception {
        if (obj == null){
            return;
        }
        JSONObject jb = new JSONObject(json);

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field: fields){
            String name = field.getName();
            if (name.contains("$") || name.contains("serialVersionUID"))
                continue;
            String type = field.getGenericType().toString();

            field.setAccessible(true);
            if (type.contains("String")){
                if (jb.has(name)) {
                    if (jb.getString(name).equals("null")){
                        field.set(obj, null);
                    }else {
                        field.set(obj, jb.getString(name));
                    }
                }
            }else if (type.contains("int")){
                if (jb.has(name)) {
                    field.set(obj, jb.getInt(name));
                }
            }
        }
    }
}
