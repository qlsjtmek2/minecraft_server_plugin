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
public @interface UpdateMode {
    boolean updateChangesOnly() default true;
}
