// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CommandString {
    String value();
    
    boolean handlePermissions() default false;
}
