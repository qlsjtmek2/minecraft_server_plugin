// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKeyJoinColumn {
    String name() default "";
    
    String referencedColumnName() default "";
    
    String columnDefinition() default "";
}