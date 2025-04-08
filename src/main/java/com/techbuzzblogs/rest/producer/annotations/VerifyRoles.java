package com.techbuzzblogs.rest.producer.annotations;

import com.techbuzzblogs.rest.producer.enums.UserRole;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyRoles {
    UserRole[] value();
}
