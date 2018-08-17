package com.jspxcms.customer.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public static String doPost(String url, Map<String,String> params){
        String resultStr = "";
        CloseableHttpResponse response = null;
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            if(params != null){
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for(String key : params.keySet()){
                    NameValuePair nameValuePair = new BasicNameValuePair(key,params.get(key));
                    nameValuePairs.add(nameValuePair);
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
                httpPost.setEntity(urlEncodedFormEntity);
            }

            response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            if(entity != null){
                resultStr = EntityUtils.toString(entity,"utf-8");
            }
            return  resultStr;
        } catch (Exception e){
            logger.info("POST请求异常:{}",e);
        }
        return resultStr;
    }

}
