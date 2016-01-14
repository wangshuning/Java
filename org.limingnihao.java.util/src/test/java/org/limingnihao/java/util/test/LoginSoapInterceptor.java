package org.limingnihao.java.util.test;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.phase.Phase;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * SOAP登陆验证
 */
public class LoginSoapInterceptor extends AbstractSoapInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginSoapInterceptor.class);
    private String username;
    private String password;
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LoginSoapInterceptor(String username, String password) {
        super(Phase.WRITE);
        logger.info("LoginSoapInterceptor - username=" + username + ", password=" + password);
        this.username = username;
        this.password = password;
    }

    public void handleMessage(SoapMessage message) throws Fault {
        Document doc = (Document) DOMUtils.createDocument();
        Element eHeader = doc.createElement("header");
//        Element eSystem = doc.createElement(xml_systemID_el);
//        Element eUsername = doc.createElement(xml_username_el);
//        Element ePassword = doc.createElement(xml_password_el);

//        eHeader.setTextContent("header");
//        eSystem.setTextContent("dhcfcs");
//        eUsername.setTextContent(username);
//        ePassword.setTextContent(password);

//        Element child = doc.createElementNS(xml_namespaceUR_att, xml_authentication_el);
//
//        child.appendChild(eSystem);
//        child.appendChild(eUsername);
//        child.appendChild(ePassword);
//
//        eHeader.appendChild(child);
        eHeader.setAttribute("username", username);
        eHeader.setAttribute("password", password);
        eHeader.setAttribute("systemId", "dhcfcs");

        QName qname = new QName("RequestSOAPHeader");//这个值暂时不清楚具体做什么用，可以随便写
        SoapHeader head = new SoapHeader(qname, eHeader);
        List<Header> headers = message.getHeaders();
        headers.add(head);
    }

}
