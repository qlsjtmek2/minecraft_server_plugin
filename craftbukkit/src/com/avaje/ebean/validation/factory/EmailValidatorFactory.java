// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public class EmailValidatorFactory implements ValidatorFactory
{
    public static final Validator EMAIL;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        if (!type.equals(String.class)) {
            throw new RuntimeException("Can only apply this annotation to String types, not " + type);
        }
        return EmailValidatorFactory.EMAIL;
    }
    
    static {
        EMAIL = new EmailValidator();
    }
    
    public static class EmailValidator extends NoAttributesValidator
    {
        private final EmailValidation emailValidation;
        
        EmailValidator() {
            this.emailValidation = EmailValidation.create(false, false);
        }
        
        public String getKey() {
            return "email";
        }
        
        public boolean isValid(final Object value) {
            return value == null || this.emailValidation.isValid((String)value);
        }
    }
}
