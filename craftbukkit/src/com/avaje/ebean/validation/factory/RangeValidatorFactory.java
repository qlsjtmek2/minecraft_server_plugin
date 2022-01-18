// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.util.HashMap;
import java.math.BigDecimal;
import com.avaje.ebean.validation.Range;
import java.lang.annotation.Annotation;
import java.util.Map;

public final class RangeValidatorFactory implements ValidatorFactory
{
    private static final Map<String, Validator> cache;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        final Range range = (Range)annotation;
        return this.create(range.min(), range.max(), type);
    }
    
    public synchronized Validator create(final long min, final long max, final Class<?> type) {
        final String key = type + ":" + min + ":" + max;
        Validator validator = RangeValidatorFactory.cache.get(key);
        if (validator != null) {
            return validator;
        }
        if (type.equals(String.class)) {
            validator = new StringValidator(min, max);
        }
        else if (useDouble(type)) {
            validator = new DoubleValidator(min, max);
        }
        else {
            if (!useLong(type)) {
                final String msg = "@Range annotation not assignable to type " + type;
                throw new RuntimeException(msg);
            }
            validator = new LongValidator(min, max);
        }
        RangeValidatorFactory.cache.put(key, validator);
        return validator;
    }
    
    private static boolean useLong(final Class<?> type) {
        return type.equals(Integer.TYPE) || type.equals(Long.TYPE) || type.equals(Short.TYPE) || Number.class.isAssignableFrom(type);
    }
    
    private static boolean useDouble(final Class<?> type) {
        return type.equals(Float.TYPE) || type.equals(Double.TYPE) || type.equals(BigDecimal.class) || Double.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type);
    }
    
    static {
        cache = new HashMap<String, Validator>();
    }
    
    private static class DoubleValidator implements Validator
    {
        final long min;
        final long max;
        final String key;
        final Object[] attributes;
        
        private DoubleValidator(final long min, final long max) {
            this.min = min;
            this.max = max;
            this.key = this.determineKey(min, max);
            this.attributes = this.determineAttributes(min, max);
        }
        
        private String determineKey(final long min, final long max) {
            if (min > Long.MIN_VALUE && max < Long.MAX_VALUE) {
                return "range.minmax";
            }
            if (min > Long.MIN_VALUE) {
                return "range.min";
            }
            return "range.max";
        }
        
        private Object[] determineAttributes(final long min, final long max) {
            if (min > Long.MIN_VALUE && max < Long.MAX_VALUE) {
                return new Object[] { min, max };
            }
            if (min > Long.MIN_VALUE) {
                return new Object[] { min };
            }
            return new Object[] { max };
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
            final Number n = (Number)value;
            final double dv = n.doubleValue();
            return dv >= this.min && dv <= this.max;
        }
        
        public String toString() {
            return this.getClass().getName() + "key:" + this.key + " min:" + this.min + " max:" + this.max;
        }
    }
    
    private static class LongValidator extends DoubleValidator
    {
        private LongValidator(final long min, final long max) {
            super(min, max);
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return true;
            }
            final Number n = (Number)value;
            final long lv = n.longValue();
            return lv >= this.min && lv <= this.max;
        }
    }
    
    private static class StringValidator extends DoubleValidator
    {
        private StringValidator(final long min, final long max) {
            super(min, max);
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return true;
            }
            final BigDecimal bd = new BigDecimal((String)value);
            final double dv = bd.doubleValue();
            return dv >= this.min && dv <= this.max;
        }
    }
}
