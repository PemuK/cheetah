package com.ujnbox.cheetah.common.config;

import com.ujnbox.cheetah.common.handle.CookieCheckHandle;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类，用于注册拦截器。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 CookieCheckInterceptor 拦截器
        registry.addInterceptor(new CookieCheckHandle())
                .addPathPatterns("/**"); // 对所有路径应用拦截器
    }
}
