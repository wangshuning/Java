package org.limingnihao.java.util.test;


import org.junit.Test;
import org.limingnihao.util.FTPUtil;

public class TestFTPUtil {

    @Test
    public void upload(){
        try{
            FTPUtil ftp = new FTPUtil();
            ftp.login("101.200.3.17", 21, "syn", "syn123456");
            //ftp.uploadFile("/Test", "/Volumes/Software/a.txt");
            String[] list = ftp.getFileNameList("/ALL");
            for(String file : list){
                ftp.getFileNameList(file);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
