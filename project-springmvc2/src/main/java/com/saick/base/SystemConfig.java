package com.saick.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统基本配置系你想读取类，可以读取对应的properties文件进行相应的操作
 * 
 * @author Liubao
 * @2015年1月19日
 * 
 */
public class SystemConfig {

    private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);

    public static final String username;
    public static final String password;
    public static final String config;

    private static Properties pop = null;
    static {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("messages.properties");
        pop = new Properties();
        try {
            pop.load(in);
        } catch (Exception e) {
            logger.error("读取配置文件失败");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                in = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // username = "Liubao";
        // password = "123456";
        config = "003";
        username = pop.getProperty("username");
        password = pop.getProperty("password");
        System.out.println(SystemConfig.username);
        System.out.println(SystemConfig.password);
        System.out.println(SystemConfig.config);
    }

}