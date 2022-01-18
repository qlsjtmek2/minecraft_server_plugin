// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.annotation;

import com.avaje.ebean.Query;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheStrategy {
    boolean useBeanCache() default true;
    
    boolean readOnly() default false;
    
    String warmingQuery() default "";
    
    Query.UseIndex useIndex() default Query.UseIndex.DEFAULT;
}
