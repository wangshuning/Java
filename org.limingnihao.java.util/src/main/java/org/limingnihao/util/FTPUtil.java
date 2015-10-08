package org.limingnihao.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ftp常用方法
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private FTPClient ftp;

    public FTPUtil(){
    }

    /**
     * 登陆服务器
     * @param url - 服务器地址
     * @param port - 端口号,默认21
     * @param username - 用户名
     * @param password - 密码
     * @return - 登陆结果
     */
    public boolean login(String url, int port, String username, String password){
        //连接FTP服务器, 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        try {
            this.ftp = new FTPClient();
            this.ftp.connect(url, port);
            boolean login = this.ftp.login(username, password);
            logger.info("login - username=" + username + ", password=" + password + ", login=" + login);
            int replyCode = this.ftp.getReplyCode();
            logger.info("login - username=" + username + ", password=" + password + ", replyCode=" + replyCode + ", isPositiveCompletion=" + FTPReply.isPositiveCompletion(replyCode));
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                this.ftp.disconnect();
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注销
     */
    public void logout(){
        try {
            boolean logout = this.ftp.logout();
            this.ftp.disconnect();
            this.ftp = null;
            logger.info("logout - " + logout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param ftpPath - FTP上保存的路径
     * @param filePath - 实际文件路径
     * @return
     */
    public boolean uploadFile(String ftpPath, String filePath) {
        logger.info("uploadFile - ftpPath=" + ftpPath + ", filePath=" + filePath + ", isConnected=" + this.ftp.isConnected() + ", isAvailable=" + this.ftp.isAvailable());

        boolean success = false;
        try {
            boolean changeDir = this.ftp.changeWorkingDirectory(ftpPath);
            logger.info("uploadFile - ftpPath=" + ftpPath + ", changeDir=" + changeDir);
            File file = new File(filePath);
            logger.info("uploadFile - filePath=" + filePath + ", exists=" + file.exists() + ", canRead=" + file.canRead());
            InputStream is = new FileInputStream(file);
            boolean storeFile = this.ftp.storeFile(file.getName(), is);
            logger.info("uploadFile - storeFile=" + storeFile);
            is.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }


    /**
     * 获取文件列表
     * @param ftpPath
     * @return
     */
    public String[] getFileNameList(String ftpPath) {
        logger.info("getFileNameList - ftpPath=" + ftpPath + ", isConnected=" + this.ftp.isConnected() + ", isAvailable=" + this.ftp.isAvailable());
        if(!this.ftp.isConnected()){
            return null;
        }
        try{
            String[] names = this.ftp.listNames(ftpPath);
            logger.info("getFileNameList - ftpPath=" + ftpPath + ", " + Arrays.toString(names));
            return names;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
