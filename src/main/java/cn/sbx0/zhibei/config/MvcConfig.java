package cn.sbx0.zhibei.config;

import cn.sbx0.zhibei.interceptor.UserHandlerInterceptor;
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
    @Resource
    private UserHandlerInterceptor userHandlerInterceptor;

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(statisticalHandlerInterceptor)
                        .addPathPatterns("/**");
                registry.addInterceptor(userHandlerInterceptor)
                        .addPathPatterns("/**");
            }
        };
    }

}
