// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public class NotNullValidatorFactory implements ValidatorFactory
{
    public static final Validator NOT_NULL;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        return NotNullValidatorFactory.NOT_NULL;
    }
    
    static {
        NOT_NULL = new NotNullValidator();
    }
    
    public static class NotNullValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "notnull";
        }
        
        public boolean isValid(final Object value) {
            return value != null;
        }
    }
}
