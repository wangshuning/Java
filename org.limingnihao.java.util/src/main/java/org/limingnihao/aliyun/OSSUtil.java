package org.limingnihao.aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.junit.Test;
import org.limingnihao.util.FileUtil;
import org.limingnihao.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * 阿里云文件操作
 */
public class OSSUtil {

     private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    private String endpoint = "oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "NiIxhX0zXR52j0xF";
    private String accessKeySecret = "Yf72eULKyZPIc2rd8smnNxNaqJOLLj";
    private String bucketName = "dhcc-activity";
    private OSSClient client = null;

    public OSSUtil(){

    }

    public OSSUtil(String bucketName){
        this.bucketName = bucketName;
    }

    /**
     * 链接
     */
    public void connect(){
        this.client = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        client.getClientConfiguration().setMaxConnections(100);
        client.getClientConfiguration().setConnectionTimeout(5000);
        client.getClientConfiguration().setMaxErrorRetry(3);
        client.getClientConfiguration().setSocketTimeout(2000);
    }

    /**
     * 断开
     */
    public void shutdown(){
        client.shutdown();
    }

    public void list(){
        ObjectListing objectListing = client.listObjects(new ListObjectsRequest(bucketName));
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +  "(size = " + objectSummary.getSize() + ")");
        }

    }

    public String createFile(String key, String filePath)  throws Exception{
        return createFile(key, filePath, null);
    }

    public String createFile(String key, String filePath, String dns) throws Exception {
        logger.info("createFile - key=" + key + ", filePath=" + filePath + ", dns=" + dns);
        if(FileUtil.isExists(filePath)){
            PutObjectResult result = client.putObject(bucketName, key, new File(filePath));
            if(StringUtil.isNotBlank(dns)){
                return dns + "/" + key;
            }
            else{
                return "http://" + bucketName + "." + endpoint + "/" + key;
            }
        }
        throw new FileNotFoundException("文件不存在!");
    }

    public String createFile(String key, InputStream is){
        return createFile(key, is, null);
    }

    public String createFile(String key, InputStream is, String dns){
        PutObjectResult result = client.putObject(bucketName, key, is);
        if(StringUtil.isNotBlank(dns)){
            return dns + "/" + key;
        }
        else{
            return "http://" + bucketName + "." + endpoint + "/" + key;
        }
    }

    public boolean createFolder(String folder){
        try{
            PutObjectResult result = client.putObject(bucketName, folder + "/", new ByteArrayInputStream(new byte[0]));
            logger.info("createFolder - folder=" + folder + ", " + result.toString());
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String agrs[]){
        OSSUtil oss = new OSSUtil();
        oss.connect();
//        oss.createFolder("asdfa/adfasdf");
        try{
            String s = oss.createFile("goods/1.jpg", "/Users/lishiming/Pictures/算盘.jpg", "http://img-activity.kjrd.com.cn/");
            System.out.println("" + s);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
