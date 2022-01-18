// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import com.avaje.ebean.validation.factory.LengthValidatorFactory;
import java.lang.annotation.Annotation;

@ValidatorMeta(factory = LengthValidatorFactory.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    int min() default 0;
    
    int max() default Integer.MAX_VALUE;
}
