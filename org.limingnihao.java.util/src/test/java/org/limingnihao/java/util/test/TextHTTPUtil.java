package org.limingnihao.java.util.test;

import org.junit.Test;
import org.limingnihao.util.HTTPUtil;

/**
 * Created by lishiming on 15/12/17.
 */
public class TextHTTPUtil {

    @Test
    public void test1(){
        String url = "http://192.168.1.222:8080/cms4c/articlePublish/getArticlePublishInformationByApId.action?apId=24";
        String result = HTTPUtil.sendApachePostRequest(url, null);
        System.out.print("&&&&&&" + result);
    }
}
