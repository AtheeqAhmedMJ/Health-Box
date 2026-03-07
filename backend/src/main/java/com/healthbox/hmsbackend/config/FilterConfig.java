package com.healthbox.hmsbackend.config;

import com.healthbox.hmsbackend.tenant.TenantInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TenantInterceptor> tenantFilter(
            TenantInterceptor interceptor) {

        FilterRegistrationBean<TenantInterceptor> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(interceptor);
        bean.addUrlPatterns("/*");

        // AFTER JWT FILTER
        bean.setOrder(2);

        return bean;
    }
}