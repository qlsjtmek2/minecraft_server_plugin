// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Iterator;
import javax.persistence.PersistenceException;
import java.util.List;
import com.avaje.ebean.event.BeanFinder;
import java.util.HashMap;

public class DefaultBeanFinderManager implements BeanFinderManager
{
    HashMap<Class<?>, BeanFinder<?>> registerFor;
    
    public DefaultBeanFinderManager() {
        this.registerFor = new HashMap<Class<?>, BeanFinder<?>>();
    }
    
    public int createBeanFinders(final List<Class<?>> finderClassList) {
        for (final Class<?> cls : finderClassList) {
            final Class<?> entityType = this.getEntityClass(cls);
            try {
                final BeanFinder<?> beanFinder = (BeanFinder<?>)cls.newInstance();
                this.registerFor.put(entityType, beanFinder);
            }
            catch (Exception ex) {
                throw new PersistenceException(ex);
            }
        }
        return this.registerFor.size();
    }
    
    public int getRegisterCount() {
        return this.registerFor.size();
    }
    
    public <T> BeanFinder<T> getBeanFinder(final Class<T> entityType) {
        return (BeanFinder<T>)this.registerFor.get(entityType);
    }
    
    private Class<?> getEntityClass(final Class<?> controller) {
        final Class<?> cls = ParamTypeUtil.findParamType(controller, BeanFinder.class);
        if (cls == null) {
            final String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
            throw new PersistenceException(msg);
        }
        return cls;
    }
}
