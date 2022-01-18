// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.util.HashMap;
import com.avaje.ebean.validation.Length;
import java.lang.annotation.Annotation;
import java.util.Map;

public final class LengthValidatorFactory implements ValidatorFactory
{
    private static final Map<String, Validator> cache;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        if (!type.equals(String.class)) {
            final String msg = "You can only specify @Length on String types";
            throw new RuntimeException(msg);
        }
        final Length length = (Length)annotation;
        return create(length.min(), length.max());
    }
    
    public static synchronized Validator create(final int min, final int max) {
        final String key = min + ":" + max;
        Validator validator = LengthValidatorFactory.cache.get(key);
        if (validator == null) {
            validator = new LengthValidator(min, max);
            LengthValidatorFactory.cache.put(key, validator);
        }
        return validator;
    }
    
    static {
        cache = new HashMap<String, Validator>();
    }
    
    public static final class LengthValidator implements Validator
    {
        private final int min;
        private final int max;
        private final String key;
        private final Object[] attributes;
        
        private LengthValidator(final int min, final int max) {
            this.min = min;
            this.max = max;
            this.key = this.determineKey(min, max);
            this.attributes = this.determineAttributes(min, max);
        }
        
        private String determineKey(final int min, final int max) {
            if (min == 0 && max > 0) {
                return "length.max";
            }
            if (min > 0 && max == 0) {
                return "length.min";
            }
            return "length.minmax";
        }
        
        private Object[] determineAttributes(final int min, final int max) {
            if (min == 0 && max > 0) {
                return new Object[] { max };
            }
            if (min > 0 && max == 0) {
                return new Object[] { min };
            }
            return new Object[] { min, max };
        }
        
        public Object[] getAttributes() {
            return this.attributes;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return true;
            }
            final String s = (String)value;
            final int len = s.length();
            return len >= this.min && len <= this.max;
        }
    }
}
