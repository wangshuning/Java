package org.limingnihao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 数据库操作
 */
public class MySqlUtil {
    private static final Logger logger = LoggerFactory.getLogger(MySqlUtil.class);

    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public MySqlUtil(String url, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            logger.info("MySqlUtil - url=" + url + ", username=" + username + ", password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean execute(String sql){
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
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

    public boolean procedure(String sql, HashMap<String, String> parameter){
        CallableStatement cst = null;
        try {
            cst = connection.prepareCall(sql);
            Iterator iter = parameter.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                cst.setString(key.toString(), val.toString());
            }
            return cst.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("procedure - error - sql=" + sql);
        } finally {
            if(cst !=null ){
                try {
                    cst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
