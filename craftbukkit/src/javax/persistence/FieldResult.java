// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldResult {
    String name();
    
    String column();
}