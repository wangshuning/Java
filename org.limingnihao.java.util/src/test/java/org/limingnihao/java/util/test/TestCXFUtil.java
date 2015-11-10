package org.limingnihao.java.util.test;

//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//import org.apache.cxf.endpoint.Client;

import javax.xml.namespace.QName;
import java.lang.reflect.Method;

/**
 * Created by lishiming on 15/11/6.
 */
public class TestCXFUtil {

    public static void main(String[] args) throws Exception {

        //不依赖服务器端接口来完成调用的，也就是不仅仅能调用Java的接口
//        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
//        Client client = clientFactory.createClient("http://127.0.0.1:7080/webservice/bossService?wsdl");
//        Object[] result = client.invoke("closeAccount", "aaaa", "bbbb", 12);
//
//        System.out.println(result[0]);

//        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//        Client client = factory.createClient("http://127.0.0.1:7080/webservice/bossService?wsdl"); // 记得要加入"?wsdl"
//        QName opName = new QName("http://service.synchronize.provider.rpc.dhcc.com/", "sayHi");  // 指定到接口类所在包
//        Object[] res = client.invoke(opName, "猪打天下");
//        System.out.println("Say: " + res[0]);
    }
}
