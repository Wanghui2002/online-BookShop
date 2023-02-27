package com.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 该方法为通用的工具类，放置一些共用的方法
 */
public class Util {
    public static String DBDRIVER = "com.mysql.jdbc.Driver";
    public static String DBURL = "jdbc:mysql://localhost:3306/BookShop?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false";
    public static String DBUSER = "root";
    public static String PASSWORD = "znssg321";

    /**
     * 取得数据库连接对象
     *
     * @return 如果连接成功则返回连接对象，如果连接失败返回null
     */
    public static Connection getConnection() {
        try {
            Class.forName(DBDRIVER);
            return DriverManager.getConnection(DBURL, DBUSER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 测试连接是否成功
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
            Connection conn = Util.getConnection();
            System.out.println("数据库连接成功！！！");
            conn.close();
        } catch (Exception e) {
            System.out.println("数据库连接失败！！！");
        }
    }

}
