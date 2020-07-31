package com.orderovation.apigateway.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author devin
 */
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    private static final String SECRET_KEY_HEADER = "secret_key";
    private static final String SECRET_KEY_VALUE = "gateway";

    @Bean("FeignInterceptor")
    public RequestInterceptor feignInterceptor() {
        return requestTemplate -> requestTemplate.header(SECRET_KEY_HEADER, SECRET_KEY_VALUE);
    }

    @Bean("RequestFilter")
    public ZuulFilter requestFilter() {
        return new ZuulFilter() {
            @Override
            public String filterType() {
                return "pre";
            }
            @Override
            public int filterOrder() {
                return 0;
            }
            @Override
            public boolean shouldFilter() {
                return true;
            }
            @Override
            public Object run() throws ZuulException {
                RequestContext requestContext = RequestContext.getCurrentContext();
                requestContext.addZuulRequestHeader(SECRET_KEY_HEADER, SECRET_KEY_VALUE);
                return null;
            }
        };
    }

}
