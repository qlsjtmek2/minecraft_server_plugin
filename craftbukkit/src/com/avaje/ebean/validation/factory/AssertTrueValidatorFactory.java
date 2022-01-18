// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public class AssertTrueValidatorFactory implements ValidatorFactory
{
    public static final Validator ASSERT_TRUE;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        return AssertTrueValidatorFactory.ASSERT_TRUE;
    }
    
    static {
        ASSERT_TRUE = new AssertTrueValidator();
    }
    
    public static class AssertTrueValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "asserttrue";
        }
        
        public boolean isValid(final Object value) {
            return value == null || (boolean)value;
        }
    }
}
