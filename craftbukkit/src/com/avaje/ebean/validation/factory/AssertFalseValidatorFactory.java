// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public class AssertFalseValidatorFactory implements ValidatorFactory
{
    public static final Validator ASSERT_FALSE;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        return AssertFalseValidatorFactory.ASSERT_FALSE;
    }
    
    static {
        ASSERT_FALSE = new AssertFalseValidator();
    }
    
    public static class AssertFalseValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "assertfalse";
        }
        
        public boolean isValid(final Object value) {
            return value == null || (boolean)value;
        }
    }
}
