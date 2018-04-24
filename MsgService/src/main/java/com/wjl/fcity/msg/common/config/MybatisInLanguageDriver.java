package com.wjl.fcity.msg.common.config;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mybatis用于解析 select in 注解
 * @author xuhaihong
 * @date 2018-03-28 11:46
 **/
public class MybatisInLanguageDriver extends XMLLanguageDriver implements LanguageDriver{

    private static final Pattern IN_PATTERN = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = IN_PATTERN.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("<foreach collection=\"$1\" item=\"_item\" open=\"(\" " +
                    "separator=\",\" close=\")\" >#{_item}</foreach>");
        }
        script = "<script>" + script + "</script>";
        return super.createSqlSource(configuration, script, parameterType);

    }

}
