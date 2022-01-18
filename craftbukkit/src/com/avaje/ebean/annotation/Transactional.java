// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.annotation;

import com.avaje.ebean.TxIsolation;
import com.avaje.ebean.TxType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
    TxType type() default TxType.REQUIRED;
    
    TxIsolation isolation() default TxIsolation.DEFAULT;
    
    boolean readOnly() default false;
    
    String serverName() default "";
    
    Class<? extends Throwable>[] rollbackFor() default {};
    
    Class<? extends Throwable>[] noRollbackFor() default {};
}
