package com.jspxcms.customer.utils;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class CasUtil {

    private static Logger logger = LoggerFactory.getLogger(CasUtil.class);

    public static void checkSSOLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String ticket = request.getParameter("ticket");
        Map<String, String> params = new HashMap<>();
        params.put("ticket",ticket);
        params.put("service","http://localhost:8989/");
        String userXml = HttpUtil.doGet("http://localhost:8080/cas/serviceValidate", params);
        System.out.println(ticket);
        System.out.println(userXml);
        Assertion assertion = AssertionHolder.getAssertion();
        if(assertion != null){
            AttributePrincipal principal = assertion.getPrincipal();
            if(principal != null){
                Map<String, Object> attributes = principal.getAttributes();
                logger.error("SSO用户信息:{}",attributes);
            }
        }else {
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie c : cookies){
                    String name = URLDecoder.decode(c.getName(), "UTF-8");
                    String value = URLDecoder.decode(c.getValue(), "UTF-8");
                    System.out.println("key : " + name + " --- value : " + value);
                }
            }

        }
    }
}
