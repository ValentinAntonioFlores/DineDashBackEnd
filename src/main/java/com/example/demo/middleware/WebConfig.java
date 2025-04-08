package com.example.demo.middleware;

import com.example.demo.middleware.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Autowired
    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/clientUsers/**", "/restaurantUsers/**") // Aplica el interceptor a estas rutas
                .excludePathPatterns("/clientUsers/register", "/clientUsers/login",
                        "/restaurantUsers/register", "/restaurantUsers/login"); // Excluye rutas públicas
    }
}
