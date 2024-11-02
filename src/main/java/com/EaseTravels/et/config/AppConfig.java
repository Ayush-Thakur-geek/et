package com.EaseTravels.et.config;

import io.ipinfo.spring.IPinfoSpring;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableCaching
public class AppConfig implements WebMvcConfigurer {

    @Value("${app.default-profile-pic}")
    private String defaultProfilePic;

    public String getDefaultProfilePic() {
        return defaultProfilePic;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IPinfoSpring.Builder().build());
    }
}