// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.hawtjni.runtime;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JniClass {
    ClassFlag[] flags() default {};
    
    String conditional() default "";
    
    String name() default "";
}
