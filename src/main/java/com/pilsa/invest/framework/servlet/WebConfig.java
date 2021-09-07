package com.pilsa.invest.framework.servlet;

import com.pilsa.invest.framework.servlet.filter.FilterConfig;
import com.pilsa.invest.framework.servlet.interceptor.Interceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
//@Profile({"stg", "dev"})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor())
        ;
    }

    /**
     * Sets filter registration for api.
     * {@link FilterConfig} 필터를 주입 한다.
     * 추후에 URL 패턴으로 필터 구분이 필요할 경우 추가한다.
     *
     * @return the filter registration for web
     */
    @Bean
    public FilterRegistrationBean setFilterRegistrationForInvest() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new FilterConfig());
        // filterRegistrationBean.setUrlPatterns(Collections.singletonList("/filtered/*")); // list 를 받는 메소드
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }


    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(loggingFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public Filter loggingFilter() {
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludeHeaders(true);
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setMaxPayloadLength(10000);
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludeClientInfo(true);

        return commonsRequestLoggingFilter;
    }
}
