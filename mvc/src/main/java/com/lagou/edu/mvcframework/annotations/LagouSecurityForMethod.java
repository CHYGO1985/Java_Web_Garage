package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 *
 * @author jingjiejiang
 * @history Jul 7, 2021
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LagouSecurityForMethod {
    String[] usernames() default "";
}
