package com.hucho.feign.demos.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @ClassName: FilterRegistration
 * @Author zhangjin
 * @Date 2022/3/26 0:36
 * @Description:
 */
@Configuration
public class FilterRegistration {

    @Resource
    private GZIPFilter gzipFilter;

    @Bean
    public FilterRegistrationBean<GZIPFilter> gzipFilterRegistrationBean() {
        FilterRegistrationBean<GZIPFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(gzipFilter);
        //过滤器名称
        registration.setName("gzipFilter");
        //拦截路径
        registration.addUrlPatterns("/*");
        //设置顺序
        registration.setOrder(1);
        return registration;
    }
}