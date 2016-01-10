package org.limingnihao.java.util.test;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.junit.Test;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishiming on 15/11/6.
 */
public class TestCXFUtil {

    public static void main(String[] args) throws Exception {
    }

    @Test
    public void test_1(){
        try {
            //不依赖服务器端接口来完成调用的，也就是不仅仅能调用Java的接口
            JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
            Client client = clientFactory.createClient("http://127.0.0.1:8080/webservice/bossService?wsdl");
            Object[] result = new Object[0];
            result = client.invoke("openAccount", "aaaa", "bbbb", 2, "ccc");

            result = client.invoke("infoChange", "aaaa", "bbbb", 3, "ccc");

            result = client.invoke("changeCard", "aaaa", "bbbb", "ccc");

            result = client.invoke("closeAccount", "aaaa", "bbbb", 12);

            System.out.println(result[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_2(){
        try {


            //不依赖服务器端接口来完成调用的，也就是不仅仅能调用Java的接口
            JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();

            Client client = clientFactory.createClient("http://127.0.0.1:8080/webservice/bossService?wsdl");
//            client.getOutInterceptors().add(new ClientLoginInterceptor("admin", "123456"));

            client.getOutInterceptors().add(new LoginSoapInterceptor("dhcfcs", "dhcfcs2015"));

            client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "sayHi"), "猪打天下");

//            ArrayOfCustProperty a = new ArrayOfCustProperty();
//            a.getCustProperty().add(p1);
//            a.getCustProperty().add(p2);
//            client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "openAccount"), "aaaa", "bbbb", 2, a);
//            client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "infoChange"), "aaaa", "bbbb", 3, "ccc");
//            client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "changeCard"), "aaa", "bbbb", "ccc");
//            client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "closeAccount"), "aaaa", "bbbb", 12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_auth(){
        try {
            JaxWsProxyFactoryBean factory=new JaxWsProxyFactoryBean();
            Map<String, Object> outProps = new HashMap<String, Object>();
            outProps.put(WSHandlerConstants.ACTION,WSHandlerConstants.USERNAME_TOKEN);
            outProps.put(WSHandlerConstants.USER, "userName");
            outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
            outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                    ClientPasswordCallback.class.getName());
            WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
            factory.getOutInterceptors().add(wssOut);
            factory.getOutInterceptors().add(new SAAJOutInterceptor());

//            factory.crea
//            Client client = factory.createClient("http://127.0.0.1:8080/webservice/bossService?wsdl"); // 记得要加入"?wsdl"
//            Object[] result = client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "sayHi"), "猪打天下");
//            System.out.println("Say: " + result[0]);
//
//
//            result = client.invoke(new QName("http://service.synchronize.manager.dhcc.com/", "closeAccount"), "aaaa", "bbbb", 12);
//            System.out.println("closeAccount: " + result[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ClientPasswordCallback implements CallbackHandler {
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
            pc.setIdentifier("admin");
            pc.setPassword("123456");
        }
    }
}
