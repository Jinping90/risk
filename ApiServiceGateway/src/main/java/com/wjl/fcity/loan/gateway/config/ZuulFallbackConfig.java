package com.wjl.fcity.loan.gateway.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.wjl.fcity.loan.gateway.enums.CodeEnum;
import com.wjl.fcity.loan.gateway.model.Response;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * Zuul转发失败回调
 * 
 * @author 秦瑞华
 *
 */
@Slf4j
@Component
public class ZuulFallbackConfig implements ZuulFallbackProvider {
	private static byte[] TryLater_Body = JSON.toJSONString(new Response<>(CodeEnum.ROUTE_CALL_BACK_FAIL,"")).getBytes();
    
	private static ClientHttpResponse fallbackResponse = new ClientHttpResponse() {
        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 200;
        }

        @Override
        public String getStatusText() throws IOException {
            return "OK";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(TryLater_Body);
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }
    };
	
	@Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if(request != null) {
			String requestPath = request.getServletPath();
	    	log.error(String.format("路由失败回调,req=%s", requestPath));
		}
    	
        return fallbackResponse;
    }
}