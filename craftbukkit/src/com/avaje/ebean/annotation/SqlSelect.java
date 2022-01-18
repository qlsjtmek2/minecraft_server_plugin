// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface SqlSelect {
    String name() default "default";
    
    String tableAlias() default "";
    
    String query() default "";
    
    String extend() default "";
    
    String where() default "";
    
    String having() default "";
    
    String columnMapping() default "";
    
    boolean debug() default false;
}
