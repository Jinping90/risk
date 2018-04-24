package com.wjl.fcity.phone.common.util;

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
}
