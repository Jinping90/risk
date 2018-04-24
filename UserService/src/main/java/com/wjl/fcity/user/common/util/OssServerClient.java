package com.wjl.fcity.user.common.util;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.wjl.fcity.user.common.config.AliOssConfig;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;

/**
 * 阿里云图片服务器公共类
 *
 * @author fy
 * 图片上传
 */

public class OssServerClient {

    private volatile static OSSClient instance;

    /**
     * 单例
     *
     * @return OSS工具类实例
     */
    private static OSSClient getOSSClient(AliOssConfig aliOssConfig) {
        if (instance == null) {
            synchronized (OssServerClient.class) {
                if (instance == null) {
                    ClientConfiguration conf = new ClientConfiguration();
                    // 设置HTTP最大连接数为10
                    conf.setMaxConnections(10);
                    // 设置TCP连接超时为5000毫秒
                    conf.setConnectionTimeout(5000);
                    // 设置最大的重试次数为3
                    conf.setMaxErrorRetry(3);
                    // 设置Socket传输数据超时的时间为2000毫秒
                    conf.setSocketTimeout(2000);
                    instance = new OSSClient(aliOssConfig.getEndpoint(), aliOssConfig.getAccessKeyId(), aliOssConfig.getAccessKeySecret(), conf);
                }
            }
        }
        return instance;
    }


    /**
     * 上传单个Object
     *
     * @param base64       照片base64
     * @param userId       用户的userId
     * @param aliOssConfig 阿里oss上传配置字段
     * @return ossKey字段
     */
    public static String putObjects(String base64, Long userId, AliOssConfig aliOssConfig) {

        // 阿里云的输入流
        ByteArrayInputStream inputStream;

        //拼接图片路径
        String ossKey = userId + "_" + System.currentTimeMillis();

        try {
            // Base64解码  读取byte的流
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64);

            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }

            //转换流
            inputStream = new ByteArrayInputStream(bytes);

            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength(inputStream.available());
            //设置格式
            meta.setContentType("image/jpeg");

            //创建连接
            OSSClient client = getOSSClient(aliOssConfig);
            // 上传Object.
            client.putObject(aliOssConfig.getBucketName(), ossKey, inputStream, meta);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回ossKey给调用者
        return ossKey;
    }

}


