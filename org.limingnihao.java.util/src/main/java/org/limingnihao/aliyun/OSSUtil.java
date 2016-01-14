package org.limingnihao.aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云文件操作
 */
public class OSSUtil {

     private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    private String endpoint = "oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "NiIxhX0zXR52j0xF";
    private String accessKeySecret = "Yf72eULKyZPIc2rd8smnNxNaqJOLLj";
    private String bucketName = "dhcc-ecommerce";
    private OSSClient client = null;


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

    public String createFile(String key, String filePath){
        PutObjectResult result = client.putObject(bucketName, key, new File(filePath));
        return "http://" + bucketName + "." + endpoint + "/" + key;
    }

    public String createFile(String key, InputStream is){
        PutObjectResult result = client.putObject(bucketName, key, is);
        return "http://" + bucketName + "." + endpoint + "/" + key;
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
        oss.createFolder("asdfa/adfasdf");
        String s = oss.createFile("goods/1.jpg", "/Users/lishiming/Pictures/IMG_1707.jpg");
        System.out.println(s);
    }

}
