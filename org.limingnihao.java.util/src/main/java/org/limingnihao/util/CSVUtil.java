package org.limingnihao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lishiming on 15/9/26.
 */
public class CSVUtil {

    private static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    public static void main(String args[]) {
        // DateBean bean = DateUtil.getCurrentDateBean();
        // String str = XmlUtil.objectToXml(bean);
        // System.out.println(str);
        // DateBean b = XmlUtil.xmlToObject(DateBean.class, str);
        // System.out.println(b.getMonth());
    }

    public static String listToCSV(Object[] list){
        StringBuffer dateString = new StringBuffer();
        for(Object obj : list){
            dateString.append(objectToCSV(obj) + "\n");
        }
        return dateString.toString();
    }

    public static String objectToCSV(Object targetObj) {
        StringBuffer dateString = new StringBuffer();
        try {
            for (Field field : targetObj.getClass().getDeclaredFields()) {
                String fileName = field.getName();
                String methodName = getMethodGetName(fileName);
                Method method = targetObj.getClass().getMethod(methodName);
                Object value = method.invoke(targetObj);
                String valueString = "";
                // 识别日期
                if (value != null && value.getClass().getName().equals(Date.class.getName())) {
                    SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                    valueString = format.format(value);
                }
                //其他直接获取值
                else if(value != null) {
                    valueString = value.toString();
                }
                dateString.append("\"" + valueString + "\"" + ",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dateString.length() > 0){
            return dateString.substring(0, dateString.length() -1);
        }else{
            return "";
        }
    }

    protected static String getMethodSetName(String fieldName) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        return "set" + firstLetter + fieldName.substring(1);
    }

    protected static String getMethodGetName(String fieldName) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        return "get" + firstLetter + fieldName.substring(1);
    }

    protected static String getFieldName(String methodName) {
        String fieldName = methodName.substring(3, methodName.length());
        String firstLetter = fieldName.substring(0, 1).toLowerCase();
        return firstLetter + fieldName.substring(1);
    }
}
