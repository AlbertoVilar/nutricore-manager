package com.nutricore.manager.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class EditorialAccessConfig implements WebMvcConfigurer {

    private final EditorialAccessInterceptor editorialAccessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(editorialAccessInterceptor)
                .addPathPatterns("/v1/admin/**");
    }
}
