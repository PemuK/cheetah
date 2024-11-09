package com.ujnbox.cheetah.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义权限注解
@Target(ElementType.METHOD)  // 作用于方法
@Retention(RetentionPolicy.RUNTIME)  // 运行时有效
public @interface RequiresPermission {
    int value() default 1;  // 权限标识符，例如 "admin" 或 "user"
}
