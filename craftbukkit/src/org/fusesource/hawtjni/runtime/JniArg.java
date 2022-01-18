// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.hawtjni.runtime;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface JniArg {
    ArgFlag[] flags() default {};
    
    String cast() default "";
}
