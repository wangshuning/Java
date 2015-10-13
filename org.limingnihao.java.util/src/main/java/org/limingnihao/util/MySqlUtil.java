package org.limingnihao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 数据库操作
 */
public class MySqlUtil {
    private static final Logger logger = LoggerFactory.getLogger(MySqlUtil.class);

    public static final String url = "jdbc:mysql://127.0.0.1/cms?characterEncoding=utf8&characterSetResults=UTF-8";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "admin";
    public static final String password = "admin";

    public Connection conn = null;
    public MySqlUtil() {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean execute(String sql){
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);//准备执行语句
            return pst.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("execute - error - sql=" + sql);
        } finally {
            if(pst !=null ){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
