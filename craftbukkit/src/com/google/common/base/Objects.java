// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Objects
{
    public static boolean equal(@Nullable final Object a, @Nullable final Object b) {
        return a == b || (a != null && a.equals(b));
    }
    
    public static int hashCode(@Nullable final Object... objects) {
        return Arrays.hashCode(objects);
    }
    
    public static ToStringHelper toStringHelper(final Object self) {
        return new ToStringHelper(simpleName(self.getClass()));
    }
    
    public static ToStringHelper toStringHelper(final Class<?> clazz) {
        return new ToStringHelper(simpleName(clazz));
    }
    
    public static ToStringHelper toStringHelper(final String className) {
        return new ToStringHelper(className);
    }
    
    private static String simpleName(final Class<?> clazz) {
        String name = clazz.getName();
        name = name.replaceAll("\\$[0-9]+", "\\$");
        int start = name.lastIndexOf(36);
        if (start == -1) {
            start = name.lastIndexOf(46);
        }
        return name.substring(start + 1);
    }
    
    public static <T> T firstNonNull(@Nullable final T first, @Nullable final T second) {
        return (first != null) ? first : Preconditions.checkNotNull(second);
    }
    
    public static final class ToStringHelper
    {
        private final StringBuilder builder;
        private boolean needsSeparator;
        
        private ToStringHelper(final String className) {
            this.needsSeparator = false;
            Preconditions.checkNotNull(className);
            this.builder = new StringBuilder(32).append(className).append('{');
        }
        
        public ToStringHelper add(final String name, @Nullable final Object value) {
            Preconditions.checkNotNull(name);
            this.maybeAppendSeparator().append(name).append('=').append(value);
            return this;
        }
        
        public ToStringHelper addValue(@Nullable final Object value) {
            this.maybeAppendSeparator().append(value);
            return this;
        }
        
        public String toString() {
            try {
                return this.builder.append('}').toString();
            }
            finally {
                this.builder.setLength(this.builder.length() - 1);
            }
        }
        
        private StringBuilder maybeAppendSeparator() {
            if (this.needsSeparator) {
                return this.builder.append(", ");
            }
            this.needsSeparator = true;
            return this.builder;
        }
    }
}
