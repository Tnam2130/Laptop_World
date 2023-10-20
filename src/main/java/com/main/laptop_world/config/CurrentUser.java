package com.main.laptop_world.config;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CurrentUser {
}
