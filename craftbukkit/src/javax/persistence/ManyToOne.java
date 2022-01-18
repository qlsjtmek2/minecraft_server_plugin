// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    Class targetEntity() default void.class;
    
    CascadeType[] cascade() default {};
    
    FetchType fetch() default FetchType.EAGER;
    
    boolean optional() default true;
}
