package cn.sbx0.zhibei.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志注解
 *
 * @Documented 一般注解是不会加入javadoc，添加该注解后就可以
 * @Target(ElementType.METHOD) 只可放在方法上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExpFunction {
    String value(); // 会加多少经验
}