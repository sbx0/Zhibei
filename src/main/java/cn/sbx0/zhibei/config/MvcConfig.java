package cn.sbx0.zhibei.config;

import cn.sbx0.zhibei.interceptor.LoginHandlerInterceptor;
import cn.sbx0.zhibei.interceptor.StatisticalHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StatisticalHandlerInterceptor statisticalHandlerInterceptor;

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(statisticalHandlerInterceptor)
                        .addPathPatterns("/**");
                registry.addInterceptor(new LoginHandlerInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns(
                                "/demand/normal/list",
                                "/user/base/login",
                                "/user/base/register",
                                "/user/base/basic",
                                "/user/base/notLogin",
                                "/user/base/logout",
                                "/statistical/**"
                        );
            }
        };
    }

}
