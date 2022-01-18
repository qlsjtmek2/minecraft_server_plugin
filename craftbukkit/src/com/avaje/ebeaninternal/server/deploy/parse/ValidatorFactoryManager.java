// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import java.util.logging.Level;
import com.avaje.ebean.validation.ValidatorMeta;
import com.avaje.ebean.validation.factory.Validator;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import com.avaje.ebean.validation.factory.ValidatorFactory;
import java.util.Map;
import java.util.logging.Logger;

public class ValidatorFactoryManager
{
    static final Logger logger;
    Map<Class<?>, ValidatorFactory> factoryMap;
    
    public ValidatorFactoryManager() {
        this.factoryMap = new HashMap<Class<?>, ValidatorFactory>();
    }
    
    public Validator create(final Annotation ann, final Class<?> type) {
        synchronized (this) {
            final ValidatorMeta meta = ann.annotationType().getAnnotation(ValidatorMeta.class);
            if (meta == null) {
                return null;
            }
            final Class<?> factoryClass = meta.factory();
            final ValidatorFactory factory = this.getFactory(factoryClass);
            return factory.create(ann, type);
        }
    }
    
    private ValidatorFactory getFactory(final Class<?> factoryClass) {
        try {
            ValidatorFactory factory = this.factoryMap.get(factoryClass);
            if (factory == null) {
                factory = (ValidatorFactory)factoryClass.newInstance();
                this.factoryMap.put(factoryClass, factory);
            }
            return factory;
        }
        catch (Exception e) {
            final String msg = "Error creating ValidatorFactory " + factoryClass.getName();
            ValidatorFactoryManager.logger.log(Level.SEVERE, msg, e);
            return null;
        }
    }
    
    static {
        logger = Logger.getLogger(ValidatorFactoryManager.class.getName());
    }
}
