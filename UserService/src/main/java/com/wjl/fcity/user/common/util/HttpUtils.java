package com.wjl.fcity.user.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * HTTP工具
 *
 * @author tom
 */
@Slf4j
public class HttpUtils {
    private HttpUtils() {
    }

    /**
     * 设置常用请求头
     * @param connection 连接实体
     * @return 连接实体
     */
    private static URLConnection setDefaultHeaders(URLConnection connection) {
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return connection;
    }


    /**
     * 发送POST请求
     *
     * @param url   目标URL
     * @param param 参数，格式：name1=value1&name2=value2
     * @return 响应结果
     */
    public static String sendPost(String url, String param) throws IOException {
        StringBuilder result = new StringBuilder();
        URL realUrl = new URL(url);
        // 打开URL连接
        URLConnection conn = realUrl.openConnection();
        // 设置请求头
        conn = setDefaultHeaders(conn);
        // 判断是否为JSON数据格式
        if (JSONObject.parse(param) != null) {
            conn.setRequestProperty("Content-Type", "application/json");
        }
        // POST请求必须设置
        conn.setDoOutput(true);
        conn.setDoInput(true);
        try(PrintWriter out = new PrintWriter(conn.getOutputStream())) {
            // 设置请求体
            out.print(param);
            // 发送请求体
            out.flush();
            // 设置输入流读取响应
            try(BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null){
                    result.append(line);
                }
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发送GET方法的请求  魔蝎认证
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String configFile) {
        //正式机配置文件
        String configFileName = "prod";
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection = setDefaultHeaders(connection);
            if (configFileName.equals(configFile)){
                //正式
                connection.setRequestProperty("Authorization","token 5ff27773428147d4a4cc037e0e71fd33");
            }else {
                //测试账号token
                connection.setRequestProperty("Authorization","token e45be6307a354a87bb6f1cb3f615da3d");
            }
            connection.setConnectTimeout(30*1000);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

}
