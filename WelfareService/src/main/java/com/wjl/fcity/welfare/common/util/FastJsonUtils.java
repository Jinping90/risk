package com.wjl.fcity.welfare.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Fy
 * @implSpec : Created with IntelliJ IDEA.
 * @date : 2018-03-22 15:43
 * @version: 1.0.0
 * @deprecate :alibaba fastjson工具类
 */
@Slf4j
@SuppressWarnings("all")
public class FastJsonUtils {

    public static <T> List<T> getList(String jsonText, String listString, Class<T> clazz) {
        JSONObject jsonObject = JSON.parseObject(jsonText);
        if (jsonObject == null) {
            return null;
        }

        Object obj = jsonObject.get(listString);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            List<T> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                list.add(jsonArray.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }

    /**
     * @param <T>      -> DepartmentBean
     * @param jsonText -> {"department":{"id":"1","name":"生产部"},"password":"admin",
     *                 "username":"admin"}
     * @param objStr   -> department
     * @param clazz    -> DepartmentBean
     * @return -> T
     */
    public static <T> T getObject(String jsonText, String objStr, Class<T> clazz) {
        JSONObject jsonObject = JSON.parseObject(jsonText);
        if (jsonObject == null) {
            return null;
        }

        Object obj = jsonObject.get(objStr);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonObject.getObject(objStr, clazz);
        } else {
            log.info(obj.getClass() + "");
        }

        return null;
    }

    /**
     * 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
     *
     * @param <T>      泛型
     * @param jsonText ->{"department":{"id":"1","name":"生产部"},"password":"admin",
     *                 "username":"admin"}
     * @param clazz    -> UserBean.class
     * @return -> UserBean
     */
    public static <T> T getObject(String jsonText, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsonText, clazz);
        } catch (Exception e) {
            log.error("json字符串转换失败！" + jsonText, e);
        }
        return t;
    }


    public static String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }


    /**
     * 说明：json字符串转成为List
     *
     * @param jsonStr -> json字符串
     * @param clazz   -> class名称
     * @param <T>     -> 泛型
     * @return ->list
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            log.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     * json字符串转换成list<Map>
     *
     * @param jsonString json字符串
     * @return list
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            log.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * json字符串转换为Map
     *
     * @param jsonStr json字符串
     * @return Map
     */
    public static Map json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            log.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }
}