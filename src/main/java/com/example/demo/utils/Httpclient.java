package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @package: com.example.demo.Utils
 * @author: QuJiaQi
 * @date: 2020/6/19 11:51
 */
@Slf4j
public class Httpclient {
    public static String httpClientGet(String urlStr, Map<String, String> map) {
        CloseableHttpClient client = null;
        HttpGet get = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            String linkStringByGet = createLinkStringByGet(map);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            client = HttpClients.custom().setDefaultRequestConfig(config).build();
            String url = urlStr + "?" + linkStringByGet;
            get = new HttpGet(url);
            get.setHeader("Content-Type", "multipart/form-data;boundary=----footfoodapplicationrequestnetwork");
            get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            get.setHeader("Accept", "*/*");
            get.setHeader("Range", "bytes=" + "");
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("调用外部系统失败！");
                throw new SocketTimeoutException();
            }
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            log.error("SupplierUtil.httpClientGet has exception ：{}", e);
        } finally {
            try {
                client.close();
                response.close();
                try {
                    get.clone();
                } catch (CloneNotSupportedException e) {
                    log.error("SupplierUtil.httpClientGet has exception ：{}", e);
                }
            } catch (IOException e) {
                log.error("SupplierUtil.httpClientGet has exception ：{}", e);
            }
        }
        return result;
    }

    /**
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createLinkStringByGet(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            value = URLEncoder.encode(value, "UTF-8");
            if (i == keys.size() - 1) {
                //拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * @param url
     * @param jsonString
     * @return
     */
    public static String httpClientPost(String url, String jsonString) {
        String content = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-Type", "application/json;charset=utf-8");
            StringEntity postingString = new StringEntity(jsonString,
                    "utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("调用外部系统失败！");
                throw new SocketTimeoutException();
            }
            content = EntityUtils.toString(response.getEntity());
            System.out.println(content);
            return content;
        } catch (SocketTimeoutException e) {
            log.error("调用Dat+"
                    + ".aService接口超时,超时时间:" + 300
                    + "秒,url:" + url + ",参数：" + jsonString, e);
//            throw new CommonException("接口超时，调用失败");
        } catch (Exception e) {
            log.error("调用DataService接口失败,url:" + url + ",参数：" + jsonString,
                    e);
//            throw new CommonException("调用DataService接口失败!");

        }
        return null;
    }
}
