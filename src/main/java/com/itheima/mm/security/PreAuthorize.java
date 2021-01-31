package com.itheima.mm.security;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//加在的方法
@Retention(RetentionPolicy.RUNTIME)//保留到的阶段
public @interface PreAuthorize {
    String value();
}
