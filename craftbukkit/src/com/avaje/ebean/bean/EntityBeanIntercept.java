// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.math.BigDecimal;
import java.io.ObjectStreamException;
import javax.persistence.PersistenceException;
import com.avaje.ebean.Ebean;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public final class EntityBeanIntercept implements Serializable
{
    private static final long serialVersionUID = -3664031775464862648L;
    public static final int DEFAULT = 0;
    public static final int UPDATE = 1;
    public static final int READONLY = 2;
    public static final int SHARED = 3;
    private transient NodeUsageCollector nodeUsageCollector;
    private transient PropertyChangeSupport pcs;
    private transient PersistenceContext persistenceContext;
    private transient BeanLoader beanLoader;
    private int beanLoaderIndex;
    private String ebeanServerName;
    private EntityBean owner;
    private Object parentBean;
    private boolean loaded;
    private boolean disableLazyLoad;
    private boolean intercepting;
    private int state;
    private boolean useCache;
    private Object oldValues;
    private Set<String> loadedProps;
    private HashSet<String> changedProps;
    private String lazyLoadProperty;
    
    public EntityBeanIntercept(final Object owner) {
        this.owner = (EntityBean)owner;
    }
    
    public void setState(final int parentState) {
        this.state = parentState;
    }
    
    public int getState() {
        return this.state;
    }
    
    public void copyStateTo(final EntityBeanIntercept dest) {
        dest.loadedProps = this.loadedProps;
        dest.ebeanServerName = this.ebeanServerName;
        if (this.loaded) {
            dest.setLoaded();
        }
    }
    
    public EntityBean getOwner() {
        return this.owner;
    }
    
    public String toString() {
        if (!this.loaded) {
            return "Reference...";
        }
        return "OldValues: " + this.oldValues;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }
    
    public void setPersistenceContext(final PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }
    
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        if (this.pcs == null) {
            this.pcs = new PropertyChangeSupport(this.owner);
        }
        this.pcs.addPropertyChangeListener(listener);
    }
    
    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        if (this.pcs == null) {
            this.pcs = new PropertyChangeSupport(this.owner);
        }
        this.pcs.addPropertyChangeListener(propertyName, listener);
    }
    
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        if (this.pcs != null) {
            this.pcs.removePropertyChangeListener(listener);
        }
    }
    
    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        if (this.pcs != null) {
            this.pcs.removePropertyChangeListener(propertyName, listener);
        }
    }
    
    public void setNodeUsageCollector(final NodeUsageCollector usageCollector) {
        this.nodeUsageCollector = usageCollector;
    }
    
    public Object getParentBean() {
        return this.parentBean;
    }
    
    public void setParentBean(final Object parentBean) {
        this.parentBean = parentBean;
    }
    
    public int getBeanLoaderIndex() {
        return this.beanLoaderIndex;
    }
    
    public void setBeanLoader(final int index, final BeanLoader ctx) {
        this.beanLoaderIndex = index;
        this.beanLoader = ctx;
        this.ebeanServerName = ctx.getName();
    }
    
    public boolean isDirty() {
        return this.oldValues != null || this.owner._ebean_isEmbeddedNewOrDirty();
    }
    
    public boolean isNew() {
        return !this.intercepting && !this.loaded;
    }
    
    public boolean isNewOrDirty() {
        return this.isNew() || this.isDirty();
    }
    
    public boolean isReference() {
        return this.intercepting && !this.loaded;
    }
    
    public void setReference() {
        this.loaded = false;
        this.intercepting = true;
    }
    
    public Object getOldValues() {
        return this.oldValues;
    }
    
    public boolean isUseCache() {
        return this.useCache;
    }
    
    public void setUseCache(final boolean loadFromCache) {
        this.useCache = loadFromCache;
    }
    
    public boolean isSharedInstance() {
        return this.state == 3;
    }
    
    public void setSharedInstance() {
        this.state = 3;
    }
    
    public boolean isReadOnly() {
        return this.state >= 2;
    }
    
    public void setReadOnly(final boolean readOnly) {
        if (this.state == 3) {
            if (!readOnly) {
                throw new IllegalStateException("sharedInstance so must remain readOnly");
            }
        }
        else {
            this.state = (readOnly ? 2 : 0);
        }
    }
    
    public boolean isIntercepting() {
        return this.intercepting;
    }
    
    public void setIntercepting(final boolean intercepting) {
        this.intercepting = intercepting;
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    public void setLoaded() {
        this.loaded = true;
        this.oldValues = null;
        this.intercepting = true;
        this.owner._ebean_setEmbeddedLoaded();
        this.lazyLoadProperty = null;
        this.changedProps = null;
    }
    
    public void setLoadedLazy() {
        this.loaded = true;
        this.intercepting = true;
        this.lazyLoadProperty = null;
    }
    
    public boolean isDisableLazyLoad() {
        return this.disableLazyLoad;
    }
    
    public void setDisableLazyLoad(final boolean disableLazyLoad) {
        this.disableLazyLoad = disableLazyLoad;
    }
    
    public void setEmbeddedLoaded(final Object embeddedBean) {
        if (embeddedBean instanceof EntityBean) {
            final EntityBean eb = (EntityBean)embeddedBean;
            eb._ebean_getIntercept().setLoaded();
        }
    }
    
    public boolean isEmbeddedNewOrDirty(final Object embeddedBean) {
        return embeddedBean != null && (!(embeddedBean instanceof EntityBean) || ((EntityBean)embeddedBean)._ebean_getIntercept().isNewOrDirty());
    }
    
    public void setLoadedProps(final Set<String> loadedPropertyNames) {
        this.loadedProps = loadedPropertyNames;
    }
    
    public Set<String> getLoadedProps() {
        return this.loadedProps;
    }
    
    public Set<String> getChangedProps() {
        return this.changedProps;
    }
    
    public String getLazyLoadProperty() {
        return this.lazyLoadProperty;
    }
    
    protected void loadBean(final String loadProperty) {
        synchronized (this) {
            if (this.disableLazyLoad) {
                this.loaded = true;
                return;
            }
            if (this.lazyLoadProperty == null) {
                if (this.beanLoader == null) {
                    this.beanLoader = (BeanLoader)Ebean.getServer(this.ebeanServerName);
                }
                if (this.beanLoader == null) {
                    final String msg = "Lazy loading but InternalEbean is null? The InternalEbean needs to be set after deserialization to support lazy loading.";
                    throw new PersistenceException(msg);
                }
                this.lazyLoadProperty = loadProperty;
                if (this.nodeUsageCollector != null) {
                    this.nodeUsageCollector.setLoadProperty(this.lazyLoadProperty);
                }
                this.beanLoader.loadBean(this);
            }
        }
    }
    
    protected void createOldValues() {
        this.oldValues = this.owner._ebean_createCopy();
        if (this.nodeUsageCollector != null) {
            this.nodeUsageCollector.setModified();
        }
    }
    
    public Object writeReplaceIntercept() throws ObjectStreamException {
        if (!SerializeControl.isVanillaBeans()) {
            return this.owner;
        }
        return this.owner._ebean_createCopy();
    }
    
    protected boolean areEqual(final Object obj1, final Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        }
        if (obj2 == null) {
            return false;
        }
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 instanceof BigDecimal) {
            if (obj2 instanceof BigDecimal) {
                final Comparable com1 = (Comparable)obj1;
                return com1.compareTo(obj2) == 0;
            }
            return false;
        }
        else {
            if (obj1 instanceof URL) {
                return obj1.toString().equals(obj2.toString());
            }
            return obj1.equals(obj2);
        }
    }
    
    public void preGetter(final String propertyName) {
        if (!this.intercepting) {
            return;
        }
        if (!this.loaded) {
            this.loadBean(propertyName);
        }
        else if (this.loadedProps != null && !this.loadedProps.contains(propertyName)) {
            this.loadBean(propertyName);
        }
        if (this.nodeUsageCollector != null && this.loaded) {
            this.nodeUsageCollector.addUsed(propertyName);
        }
    }
    
    public void postSetter(final PropertyChangeEvent event) {
        if (this.pcs != null && event != null) {
            this.pcs.firePropertyChange(event);
        }
    }
    
    public void postSetter(final PropertyChangeEvent event, final Object newValue) {
        if (this.pcs != null && event != null) {
            if (newValue != null && newValue.equals(event.getNewValue())) {
                this.pcs.firePropertyChange(event);
            }
            else {
                this.pcs.firePropertyChange(event.getPropertyName(), event.getOldValue(), newValue);
            }
        }
    }
    
    public PropertyChangeEvent preSetterMany(final boolean interceptField, final String propertyName, final Object oldValue, final Object newValue) {
        if (this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    private final void addDirty(final String propertyName) {
        if (!this.intercepting) {
            return;
        }
        if (this.state >= 2) {
            throw new IllegalStateException("This bean is readOnly");
        }
        if (this.loaded) {
            if (this.oldValues == null) {
                this.createOldValues();
            }
            if (this.changedProps == null) {
                this.changedProps = new HashSet<String>();
            }
            this.changedProps.add(propertyName);
        }
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final Object oldValue, final Object newValue) {
        final boolean changed = !this.areEqual(oldValue, newValue);
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final boolean oldValue, final boolean newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final int oldValue, final int newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final long oldValue, final long newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final double oldValue, final double newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final float oldValue, final float newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final short oldValue, final short newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final char oldValue, final char newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final byte oldValue, final byte newValue) {
        final boolean changed = oldValue != newValue;
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final char[] oldValue, final char[] newValue) {
        final boolean changed = !areEqualChars(oldValue, newValue);
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    public PropertyChangeEvent preSetter(final boolean intercept, final String propertyName, final byte[] oldValue, final byte[] newValue) {
        final boolean changed = !areEqualBytes(oldValue, newValue);
        if (intercept && changed) {
            this.addDirty(propertyName);
        }
        if (changed && this.pcs != null) {
            return new PropertyChangeEvent(this.owner, propertyName, oldValue, newValue);
        }
        return null;
    }
    
    private static boolean areEqualBytes(final byte[] b1, final byte[] b2) {
        if (b1 == null) {
            return b2 == null;
        }
        if (b2 == null) {
            return false;
        }
        if (b1 == b2) {
            return true;
        }
        if (b1.length != b2.length) {
            return false;
        }
        for (int i = 0; i < b1.length; ++i) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean areEqualChars(final char[] b1, final char[] b2) {
        if (b1 == null) {
            return b2 == null;
        }
        if (b2 == null) {
            return false;
        }
        if (b1 == b2) {
            return true;
        }
        if (b1.length != b2.length) {
            return false;
        }
        for (int i = 0; i < b1.length; ++i) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }
}
