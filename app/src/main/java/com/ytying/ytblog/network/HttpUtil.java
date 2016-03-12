package com.ytying.ytblog.network;

import com.ytying.ytblog.utils.StringUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by UKfire on 15/11/24.
 */
public class HttpUtil {

    private static final String DEFAULT_CHARSET = "utf-8";
    private static HttpClient simpleHttpClient = null;

    public static final int CONNECT_TIME_OUT = 10 * 1000;

    public static final int WAIT_TIME_OUT = 10 * 1000;

    public static final int READ_TIME_OUT = 40 * 1000;

    public static final int MAX_CONNECTION = 10;

    public static String post(String url,Map<String,String> map){
        if(StringUtil.isBlank(url))
            return null;

        //singeton
        if(simpleHttpClient == null){
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, DEFAULT_CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            ConnManagerParams.setTimeout(params, WAIT_TIME_OUT);
            ConnManagerParams.setMaxTotalConnections(params, MAX_CONNECTION);
            HttpConnectionParams.setSoTimeout(params, READ_TIME_OUT);
            HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIME_OUT);
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
            ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(params,schReg);
            simpleHttpClient = new DefaultHttpClient(connectionManager,params);
        }

        //httpPost
        HttpPost httpPost = new HttpPost(url);
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if(null != map){
                Iterator<String> it = map.keySet().iterator();
                while(it.hasNext()){
                    String ak = it.next();
                    params.add(new BasicNameValuePair(ak,map.get(ak)));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params,DEFAULT_CHARSET));
            if(null == httpPost)
                return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        //post

        try {
            HttpResponse response = simpleHttpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                httpPost.abort();
                return null;
            }
            HttpEntity entity = response.getEntity();
            return entity == null ? null : EntityUtils.toString(entity,DEFAULT_CHARSET);

        } catch (IOException e) {
            e.printStackTrace();
            if(null != httpPost)
                httpPost.abort();
            return null;
        }

    }

    public static String upload(String surl, String funId, String file) {

        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL(surl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "----WebKitFormBoundarydyVd4OxlaM9Ud3oP";
        String fileName = file.substring(file.lastIndexOf("/"));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            // 设置附加参数
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);

            // 新建流
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"funId\"" + lineEnd + lineEnd + funId + lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes("Content-Type: application/octet-stream" + lineEnd + lineEnd);

            // 写文件
            int bytesAvailable = fileInputStream.available();
            byte[] buffer = new byte[bytesAvailable];
            int bytesRead = fileInputStream.read(buffer, 0, bytesAvailable);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bytesAvailable);
                bytesAvailable = fileInputStream.available();
                bytesRead = fileInputStream.read(buffer, 0, bytesAvailable);
            }
            fileInputStream.close();
            dos.writeBytes(lineEnd);

            // 写结束分界符
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // 更新关闭流
            dos.flush();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
            return null;
        }

        try {
            // 开始链接并取得返回数据
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String tmp = null;
            StringBuffer ke = new StringBuffer();
            while ((tmp = br.readLine()) != null) {
                ke.append(tmp);
            }
            br.close();

            return ke.toString();
        } catch (Exception e) {
            if (connection != null) {
                connection.disconnect();
            }
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
