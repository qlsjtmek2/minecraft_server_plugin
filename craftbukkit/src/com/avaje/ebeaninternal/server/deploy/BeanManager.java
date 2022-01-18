// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.persist.BeanPersister;

public class BeanManager<T>
{
    private final BeanPersister persister;
    private final BeanDescriptor<T> descriptor;
    
    public BeanManager(final BeanDescriptor<T> descriptor, final BeanPersister persister) {
        this.descriptor = descriptor;
        this.persister = persister;
    }
    
    public BeanPersister getBeanPersister() {
        return this.persister;
    }
    
    public BeanDescriptor<T> getBeanDescriptor() {
        return this.descriptor;
    }
    
    public boolean isLdapEntityType() {
        return this.descriptor.isLdapEntityType();
    }
}
