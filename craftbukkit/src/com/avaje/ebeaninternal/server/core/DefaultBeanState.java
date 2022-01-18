// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Set;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.BeanState;

public class DefaultBeanState implements BeanState
{
    final EntityBean entityBean;
    final EntityBeanIntercept intercept;
    
    public DefaultBeanState(final EntityBean entityBean) {
        this.entityBean = entityBean;
        this.intercept = entityBean._ebean_getIntercept();
    }
    
    public boolean isReference() {
        return this.intercept.isReference();
    }
    
    public boolean isSharedInstance() {
        return this.intercept.isSharedInstance();
    }
    
    public boolean isNew() {
        return this.intercept.isNew();
    }
    
    public boolean isNewOrDirty() {
        return this.intercept.isNewOrDirty();
    }
    
    public boolean isDirty() {
        return this.intercept.isDirty();
    }
    
    public Set<String> getLoadedProps() {
        final Set<String> props = this.intercept.getLoadedProps();
        return (props == null) ? null : Collections.unmodifiableSet((Set<? extends String>)props);
    }
    
    public Set<String> getChangedProps() {
        final Set<String> props = this.intercept.getChangedProps();
        return (props == null) ? null : Collections.unmodifiableSet((Set<? extends String>)props);
    }
    
    public boolean isReadOnly() {
        return this.intercept.isReadOnly();
    }
    
    public void setReadOnly(final boolean readOnly) {
        this.intercept.setReadOnly(readOnly);
    }
    
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.entityBean.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.entityBean.removePropertyChangeListener(listener);
    }
    
    public void setLoaded(final Set<String> loadedProperties) {
        this.intercept.setLoadedProps(loadedProperties);
        this.intercept.setLoaded();
    }
    
    public void setReference() {
        this.intercept.setReference();
    }
}
