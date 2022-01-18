// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.util.regex.Matcher;
import java.util.HashMap;
import com.avaje.ebean.validation.Pattern;
import java.lang.annotation.Annotation;
import java.util.Map;

public final class PatternValidatorFactory implements ValidatorFactory
{
    private static final Map<String, Validator> cache;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        if (!type.equals(String.class)) {
            final String msg = "You can only specify @Pattern on String types";
            throw new RuntimeException(msg);
        }
        final Pattern pattern = (Pattern)annotation;
        return create(pattern.regex(), pattern.flags());
    }
    
    public static synchronized Validator create(String regex, final int flags) {
        regex = regex.trim();
        if (regex.length() == 0) {
            throw new RuntimeException("Missing regex attribute on @Pattern");
        }
        final String key = regex;
        Validator validator = PatternValidatorFactory.cache.get(key);
        if (validator == null) {
            validator = new PatternValidator(regex, flags);
            PatternValidatorFactory.cache.put(key, validator);
        }
        return validator;
    }
    
    static {
        cache = new HashMap<String, Validator>();
    }
    
    private static final class PatternValidator implements Validator
    {
        private final java.util.regex.Pattern pattern;
        private final Object[] attributes;
        
        private PatternValidator(final String regex, final int flags) {
            this.pattern = java.util.regex.Pattern.compile(regex, flags);
            this.attributes = new Object[] { regex, flags };
        }
        
        public Object[] getAttributes() {
            return this.attributes;
        }
        
        public String getKey() {
            return "pattern";
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return true;
            }
            final String string = (String)value;
            final Matcher m = this.pattern.matcher(string);
            return m.matches();
        }
    }
}
