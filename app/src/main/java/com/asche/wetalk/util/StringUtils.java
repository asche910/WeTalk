package com.asche.wetalk.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class StringUtils {

    public static boolean isEmpty(String str){
        return str == null || str.trim().equals("");
    }

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
}
