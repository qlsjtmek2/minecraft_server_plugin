// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.util.Calendar;
import java.util.Date;
import java.lang.annotation.Annotation;

public class PastValidatorFactory implements ValidatorFactory
{
    Validator DATE;
    Validator CALENDAR;
    
    public PastValidatorFactory() {
        this.DATE = new DateValidator();
        this.CALENDAR = new CalendarValidator();
    }
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        if (Date.class.isAssignableFrom(type)) {
            return this.DATE;
        }
        if (Calendar.class.isAssignableFrom(type)) {
            return this.CALENDAR;
        }
        final String msg = "Can not use @Past on type " + type;
        throw new RuntimeException(msg);
    }
    
    private static class DateValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "past";
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return true;
            }
            final Date date = (Date)value;
            return date.before(new Date());
        }
    }
    
    private static class CalendarValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "past";
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return true;
            }
            final Calendar cal = (Calendar)value;
            return cal.before(Calendar.getInstance());
        }
    }
}
