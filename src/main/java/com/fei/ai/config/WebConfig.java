package com.fei.ai.config;

import com.fei.ai.web.HeaderValidationInterceptor;
import com.fei.ai.web.AuthContextArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderValidationInterceptor())
                .addPathPatterns("/api/messages", "/api/chat/long", "/api/messages/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthContextArgumentResolver());
    }
}
