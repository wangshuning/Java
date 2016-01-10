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
            ftp.login("101.201.150.246", 21, "dhcc", "aDadfSDFasdfR43456F");
            List<FileBean> list= ftp.getFileList("/home/dhcc/");
            for(FileBean file : list){
                System.out.println("" + file);
            }


//            ftp.downloadFile("/opt/server/cmsImages/img/", "79cecaf5-f5ea-4fd2-98c8-17161ce657e0.jpg", "/Volumes/Software/79cecaf5-f5ea-4fd2-98c8-17161ce657e0.jpg");

            //ftp.uploadFile("/Test", "/Volumes/Software/a.txt");
//            List<FileBean> list= ftp.getFileList("/");
//            for(FileBean file : list){
////                ftp.downloadFile(file.getFolderPath(), file.getFileName(), "/Volumes/Software/Syn/Record");
//            }



        }


        catch (Exception e){
            e.printStackTrace();
        }
    }
}
