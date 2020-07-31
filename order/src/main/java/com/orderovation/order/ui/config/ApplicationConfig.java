package com.orderovation.order.ui.config;

import com.alibaba.fastjson.JSON;
import com.orderovation.order.ui.dto.Rsp;
import com.orderovation.order.ui.dto.RspEnum;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author devin
 */
@Configuration
@EnableSwagger2
public class ApplicationConfig implements WebMvcConfigurer {

    private static final String SECRET_KEY_HEADER = "secret_key";
    private static final String SECRET_KEY_VALUE = "gateway";

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                httpRequest.getHeaders().set(SECRET_KEY_HEADER, SECRET_KEY_VALUE);
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        });
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor()).addPathPatterns("/**").excludePathPatterns("/test");
    }

    public HandlerInterceptor requestInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String secretKey = request.getHeader(SECRET_KEY_HEADER);
                if (!SECRET_KEY_VALUE.equals(secretKey)) {
                    response.getWriter().println(JSON.toJSONString(Rsp.error(RspEnum.ILLEGAL_REQUEST)));
                    return false;
                }
                return true;
            }
        };
    }
}
