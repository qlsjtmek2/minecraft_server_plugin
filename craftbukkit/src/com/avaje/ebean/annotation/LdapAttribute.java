// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LdapAttribute {
    String name() default "";
    
    Class<?> adapter() default void.class;
    
    boolean insertable() default true;
    
    boolean updatable() default true;
}
