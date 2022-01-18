// 
// Decompiled by Procyon v0.5.30
// 

package javax.annotation;

import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Annotation;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Nonnull {
    When when() default When.ALWAYS;
    
    public static class Checker implements TypeQualifierValidator<Nonnull>
    {
        public When forConstantValue(final Nonnull qualifierqualifierArgument, final Object value) {
            if (value == null) {
                return When.NEVER;
            }
            return When.ALWAYS;
        }
    }
}
