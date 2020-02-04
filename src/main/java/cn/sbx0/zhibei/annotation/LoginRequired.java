package cn.sbx0.zhibei.annotation;

import java.lang.annotation.*;

/**
 * 需要登录
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginRequired {
}