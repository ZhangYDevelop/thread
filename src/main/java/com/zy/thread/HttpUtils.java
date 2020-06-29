package com.zy.thread;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {

    /**
     * java.net.HttpURLConnection
     * @param requestUrl
     * @param params
     * @return
     */
    public static String sendPost(String requestUrl ,String params) {

        HttpURLConnection con = null;
        BufferedReader reader = null;
        try {
            // 1. 获取访问地址URL
            URL url  = new URL(requestUrl);
            // 2. 创建HttpURLConnection对象
            con =(HttpURLConnection)url.openConnection();
            // 构建参数
            StringBuilder builder = new StringBuilder();
            builder.append(params);
            byte[] data = builder.toString().getBytes("UTF-8");

            //设置超时时间
            con.setConnectTimeout(3000);
            // 设置请求方式
            con.setRequestMethod("POST");
            // 设置使用标准编码格式编码参数的名-值对
            con.setRequestProperty("Content-Type", "application/json");
            // 设置是否写出
            con.setDoOutput(true);
            // 设置是否写入
            con.setDoInput(true);
            // 开始连接
            con.connect();
            // 拖过网络IO向服务器发送数据包
            con.getOutputStream().write(data);
            // 服务端响应代码
            int responseCode = con.getResponseCode();

            if (200 == responseCode) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }
                return  sb.toString();
            } else {
                new RuntimeException("连接异常 ,错误代码："  + responseCode);
            }
        }  catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != con) {
                con.disconnect();
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * org.apache.http.client.HttpClient
     * @param requestUrl
     * @param jsonString
     * @return
     */
    public static String sendPosteEx(String requestUrl ,String jsonString) {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpPost对象
        HttpPost post = new HttpPost( requestUrl);
        // 3. 设置POST请求传递参数
        post.setEntity(new StringEntity(jsonString, Charset.forName("utf-8")));

        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                System.out.println("响应内容：");
                System.out.println(EntityUtils.toString(entity));
                return EntityUtils.toString(entity);
            }
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }
}
