package cn.sbx0.zhibei.annotation;

import java.lang.annotation.*;

/**
 * 判断用户角色
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoleCheck {
    String[] values(); // 角色数组
}