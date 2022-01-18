// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import javax.annotation.Nullable;
import java.io.Serializable;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class Enums
{
    public static <T extends Enum<T>> Function<String, T> valueOfFunction(final Class<T> enumClass) {
        return new ValueOfFunction<T>((Class)enumClass);
    }
    
    private static final class ValueOfFunction<T extends Enum<T>> implements Function<String, T>, Serializable
    {
        private final Class<T> enumClass;
        private static final long serialVersionUID = 0L;
        
        private ValueOfFunction(final Class<T> enumClass) {
            this.enumClass = Preconditions.checkNotNull(enumClass);
        }
        
        public T apply(final String value) {
            try {
                return Enum.valueOf(this.enumClass, value);
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        }
        
        public boolean equals(@Nullable final Object obj) {
            return obj instanceof ValueOfFunction && this.enumClass.equals(((ValueOfFunction)obj).enumClass);
        }
        
        public int hashCode() {
            return this.enumClass.hashCode();
        }
        
        public String toString() {
            return "Enums.valueOf(" + this.enumClass + ")";
        }
    }
}
