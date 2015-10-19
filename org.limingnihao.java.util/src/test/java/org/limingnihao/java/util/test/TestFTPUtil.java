package org.limingnihao.java.util.test;


import org.junit.Test;
import org.limingnihao.model.FileBean;
import org.limingnihao.util.FTPUtil;

import java.util.List;

public class TestFTPUtil {

    @Test
    public void upload(){
        try{
            FTPUtil ftp = new FTPUtil();
            ftp.login("101.200.3.17", 21, "syn", "syn123456");
            //ftp.uploadFile("/Test", "/Volumes/Software/a.txt");
            List<FileBean> list= ftp.getFileList("/Record/201510");
            for(FileBean file : list){
                ftp.downloadFile(file.getFolderPath(), file.getFileName(), "/Volumes/Software/Syn/Record");
            }
        }


        catch (Exception e){
            e.printStackTrace();
        }
    }
}
