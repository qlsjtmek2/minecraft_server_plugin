// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SecondaryTable {
    String name();
    
    String catalog() default "";
    
    String schema() default "";
    
    PrimaryKeyJoinColumn[] pkJoinColumns() default {};
    
    UniqueConstraint[] uniqueConstraints() default {};
}
