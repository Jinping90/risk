package com.wjl.fcity.user.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

/**
 * @author : Fy
 * @date : 2018-04-01 10:27
 */
@Slf4j
public class LinkFacePost {

    /**
     * 请求商汤人脸识别返回成功的code.
     */
    private static final Integer SUCCESS_CODE = 200;

    /**
     * @param url        调用第三方的url
     * @param name       用户的姓名
     * @param idNumber   用户的身份证号码
     * @param livenessId 云端的活体数据(liveness_data)
     * @param apiKey     api的id.
     * @param apiSecret  apiSecret的密钥
     * @return String
     * @throws UnsupportedEncodingException UrlEncodedFormEntity抛出异常
     */
    public static String httpClientPost(String url, String name, String idNumber, String livenessId, String apiKey, String apiSecret) throws UnsupportedEncodingException {

        String result = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        NameValuePair livenessIdPair1 = new BasicNameValuePair("liveness_id", livenessId);
        NameValuePair idNumberPair2 = new BasicNameValuePair("idnumber", idNumber);
        NameValuePair namePair3 = new BasicNameValuePair("name", name);

        List<NameValuePair> pairsList = new ArrayList<>();
        pairsList.add(livenessIdPair1);
        pairsList.add(idNumberPair2);
        pairsList.add(namePair3);

        try {
            log.info("【人脸识别】请求商汤人脸识别name={},cardNo={},liveness_id={}", name, idNumber, livenessId);
            HttpEntity requestEntity = new UrlEncodedFormEntity(pairsList, "utf-8");
            httpPost.setEntity(requestEntity);
            //请将AUTHORIZATION替换为根据API_KEY和API_SECRET得到的签名认证串
            String authorization = getHeader(apiKey, apiSecret);
            httpPost.setHeader("Authorization", authorization);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == SUCCESS_CODE) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                result = reader.readLine();
                log.info("【人脸识别】result={}", result);
            } else {
                HttpEntity rEntity = response.getEntity();
                String responseString = EntityUtils.toString(rEntity);
                log.error("【人脸识别】请求商汤人脸识别name={},cardNo={},liveness_id={}出错,错误码statusCode={},信息reasonPhrase={}", name, idNumber, livenessId, response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                log.error("【人脸识别】出错原因responseString={}", responseString);
                result = responseString;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取商汤请求头信息
     *
     * @param apiKey    api的id
     * @param apiSecret apiSecret的密钥
     * @return String
     */
    private static String getHeader(String apiKey, String apiSecret) throws Exception {
        String authorization = null;

        try {
            //获取当前的时间
            String timesTamp = System.currentTimeMillis() + "";
            //随机生成nonce字符串
            String str = UUID.randomUUID().toString();
            String nonce = str.replace("-", "");

            //将timesTamp,nonce,API_key 这三个字符串进行升序排列(依据字符串首位字符的ASCII码)，并join成一个字符串
            List<String> beforeSort = new ArrayList<>();
            beforeSort.add(apiKey);
            beforeSort.add(timesTamp);
            beforeSort.add(nonce);
            beforeSort.sort(new SpellComparator());

            //将集合，进行String类型数据的拼接
            StringBuilder afterSort = new StringBuilder();
            for (String aBeforeSort : beforeSort) {
                afterSort.append(aBeforeSort);
            }
            String joinStr = afterSort.toString();

            //用API_Secret对join_str做hamc_sha256签名，且以16进制编码
            Key secretKey = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);

            //完成hamc-sha256签名
            final byte[] hmac = mac.doFinal(joinStr.getBytes());
            StringBuilder sb = new StringBuilder(hmac.length * 2);
            Formatter formatter = new Formatter(sb);
            for (byte b : hmac) {
                formatter.format("%02x", b);
            }
            //完成16进制编码
            String signature = sb.toString();

            //将上述的值按照 #{k}=#{v} 并以 ',' join在一起
            authorization = "key=" + apiKey
                    + ",timestamp=" + timesTamp
                    + ",nonce=" + nonce
                    + ",signature=" + signature;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorization;
    }


}
