package cn.sbx0.zhibei.config;

import cn.sbx0.zhibei.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns(
                                "/demand/normal/list",
                                "/user/base/login",
                                "/user/base/register",
                                "/user/base/basic",
                                "/user/base/notLogin",
                                "/user/base/logout"
                        );
            }
        };
    }

}
