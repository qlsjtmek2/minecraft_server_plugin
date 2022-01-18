// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

import java.util.Set;

public abstract class BeanPersistAdapter implements BeanPersistController
{
    public abstract boolean isRegisterFor(final Class<?> p0);
    
    public int getExecutionOrder() {
        return 10;
    }
    
    public boolean preDelete(final BeanPersistRequest<?> request) {
        return true;
    }
    
    public boolean preInsert(final BeanPersistRequest<?> request) {
        return true;
    }
    
    public boolean preUpdate(final BeanPersistRequest<?> request) {
        return true;
    }
    
    public void postDelete(final BeanPersistRequest<?> request) {
    }
    
    public void postInsert(final BeanPersistRequest<?> request) {
    }
    
    public void postUpdate(final BeanPersistRequest<?> request) {
    }
    
    public void postLoad(final Object bean, final Set<String> includedProperties) {
    }
}
