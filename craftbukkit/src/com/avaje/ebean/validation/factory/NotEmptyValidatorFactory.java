// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

public class NotEmptyValidatorFactory implements ValidatorFactory
{
    static final Logger logger;
    static final Validator STRING;
    static final Validator ARRAY;
    static final Validator LIST;
    static final Validator SET;
    static final Validator MAP;
    static final Validator COLLECTION;
    
    public Validator create(final Annotation annotation, final Class<?> type) {
        if (type.equals(String.class)) {
            return NotEmptyValidatorFactory.STRING;
        }
        if (type.isArray()) {
            return NotEmptyValidatorFactory.ARRAY;
        }
        if (List.class.isAssignableFrom(type)) {
            return NotEmptyValidatorFactory.LIST;
        }
        if (Set.class.isAssignableFrom(type)) {
            return NotEmptyValidatorFactory.SET;
        }
        if (Collection.class.isAssignableFrom(type)) {
            return NotEmptyValidatorFactory.COLLECTION;
        }
        if (Map.class.isAssignableFrom(type)) {
            return NotEmptyValidatorFactory.MAP;
        }
        final String msg = "@NotEmpty not assignable to type " + type;
        NotEmptyValidatorFactory.logger.log(Level.SEVERE, msg);
        return null;
    }
    
    static {
        logger = Logger.getLogger(NotEmptyValidatorFactory.class.getName());
        STRING = new StringNotEmptyValidator();
        ARRAY = new ArrayValidator();
        LIST = new ListNotEmptyValidator();
        SET = new SetNotEmptyValidator();
        MAP = new MapNotEmptyValidator();
        COLLECTION = new CollectionNotEmptyValidator();
    }
    
    private static class StringNotEmptyValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "notempty.string";
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return false;
            }
            final String s = (String)value;
            return s.length() > 0;
        }
    }
    
    private static class MapNotEmptyValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "notempty.map";
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return false;
            }
            final Map<?, ?> map = (Map<?, ?>)value;
            return map.size() > 0;
        }
    }
    
    private static class ArrayValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "notempty.array";
        }
        
        public boolean isValid(final Object value) {
            return value != null && Array.getLength(value) > 0;
        }
    }
    
    private static class SetNotEmptyValidator extends CollectionNotEmptyValidator
    {
        public String getKey() {
            return "notempty.set";
        }
    }
    
    private static class ListNotEmptyValidator extends CollectionNotEmptyValidator
    {
        public String getKey() {
            return "notempty.list";
        }
    }
    
    private static class CollectionNotEmptyValidator extends NoAttributesValidator
    {
        public String getKey() {
            return "notempty.collection";
        }
        
        public boolean isValid(final Object value) {
            if (value == null) {
                return false;
            }
            final Collection<?> c = (Collection<?>)value;
            return c.size() > 0;
        }
    }
}
