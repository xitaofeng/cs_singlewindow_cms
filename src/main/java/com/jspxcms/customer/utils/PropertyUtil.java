package com.jspxcms.customer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("customer.properties");
//            InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream("customer_pro.properties");
            properties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        String value = PropertyUtil.getValue("local.url");
        System.out.println(value);
    }

}
