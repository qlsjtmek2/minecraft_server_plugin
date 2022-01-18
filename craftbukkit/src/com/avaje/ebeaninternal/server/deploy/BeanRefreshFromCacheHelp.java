// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.HashSet;
import java.util.Set;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;

public class BeanRefreshFromCacheHelp
{
    private final BeanDescriptor<?> desc;
    private final EntityBeanIntercept ebi;
    private final EntityBean bean;
    private final Object cacheBean;
    private final Object originalOldValues;
    private final boolean isLazyLoad;
    private final boolean readOnly;
    private final boolean sharedInstance;
    private final int parentState;
    private final Set<String> excludes;
    private final Set<String> cacheBeanLoadedProps;
    private final Set<String> loadedProps;
    private final boolean setOriginalOldValues;
    
    public BeanRefreshFromCacheHelp(final BeanDescriptor<?> desc, final EntityBeanIntercept ebi, final Object cacheBean, final boolean isLazyLoad) {
        this.desc = desc;
        this.ebi = ebi;
        this.bean = ebi.getOwner();
        this.cacheBean = cacheBean;
        this.cacheBeanLoadedProps = ((EntityBean)cacheBean)._ebean_getIntercept().getLoadedProps();
        if (this.cacheBeanLoadedProps != null) {
            this.loadedProps = new HashSet<String>();
        }
        else {
            this.loadedProps = null;
        }
        this.isLazyLoad = isLazyLoad;
        this.readOnly = ebi.isReadOnly();
        this.sharedInstance = ebi.isSharedInstance();
        if (this.sharedInstance) {
            this.parentState = 1;
        }
        else if (this.readOnly) {
            this.parentState = 2;
        }
        else {
            this.parentState = 0;
        }
        this.excludes = (isLazyLoad ? ebi.getLoadedProps() : null);
        if (this.excludes != null) {
            this.originalOldValues = ebi.getOldValues();
        }
        else {
            this.originalOldValues = null;
        }
        this.setOriginalOldValues = (this.originalOldValues != null);
    }
    
    private boolean includeProperty(final BeanProperty prop) {
        final String name = prop.getName();
        if (this.excludes != null && this.excludes.contains(name)) {
            return false;
        }
        if (this.cacheBeanLoadedProps != null && !this.cacheBeanLoadedProps.contains(name)) {
            return false;
        }
        if (this.loadedProps != null) {
            this.loadedProps.add(name);
        }
        return true;
    }
    
    private void propagateParentState(final Object bean) {
        if (bean != null && this.parentState > 0) {
            ((EntityBean)bean)._ebean_getIntercept().setState(this.parentState);
        }
    }
    
    public void refresh() {
        this.ebi.setIntercepting(false);
        final BeanProperty[] props = this.desc.propertiesBaseScalar();
        for (int i = 0; i < props.length; ++i) {
            final BeanProperty prop = props[i];
            if (this.includeProperty(prop)) {
                final Object val = prop.getValue(this.cacheBean);
                if (this.isLazyLoad) {
                    prop.setValue(this.bean, val);
                }
                else {
                    prop.setValueIntercept(this.bean, val);
                }
                if (this.setOriginalOldValues) {
                    prop.setValue(this.originalOldValues, val);
                }
            }
        }
        final BeanPropertyAssocOne<?>[] ones = this.desc.propertiesOne();
        for (int j = 0; j < ones.length; ++j) {
            final BeanPropertyAssocOne<?> prop2 = ones[j];
            if (this.includeProperty(prop2)) {
                Object val2 = prop2.getValue(this.cacheBean);
                if (!this.sharedInstance) {
                    val2 = prop2.getTargetDescriptor().createCopyForUpdate(val2, false);
                }
                if (this.isLazyLoad) {
                    prop2.setValue(this.bean, val2);
                }
                else {
                    prop2.setValueIntercept(this.bean, val2);
                }
                if (this.setOriginalOldValues) {
                    prop2.setValue(this.originalOldValues, val2);
                }
                this.propagateParentState(val2);
            }
        }
        this.refreshEmbedded();
        final BeanPropertyAssocMany<?>[] manys = this.desc.propertiesMany();
        for (int k = 0; k < manys.length; ++k) {
            final BeanPropertyAssocMany<?> prop3 = manys[k];
            if (this.includeProperty(prop3)) {
                prop3.createReference(this.bean);
            }
        }
        this.ebi.setLoadedProps(this.loadedProps);
        this.ebi.setLoaded();
    }
    
    private void refreshEmbedded() {
        final BeanPropertyAssocOne<?>[] embeds = this.desc.propertiesEmbedded();
        for (int i = 0; i < embeds.length; ++i) {
            final BeanPropertyAssocOne<?> prop = embeds[i];
            if (this.includeProperty(prop)) {
                final Object oEmb = prop.getValue(this.bean);
                final Object cacheEmb = prop.getValue(this.cacheBean);
                if (oEmb == null) {
                    if (cacheEmb == null) {
                        prop.setValueIntercept(this.bean, null);
                    }
                    else {
                        final Object copyEmb = prop.getTargetDescriptor().createCopyForUpdate(cacheEmb, false);
                        prop.setValueIntercept(this.bean, copyEmb);
                        this.propagateParentState(copyEmb);
                    }
                }
                else {
                    if (oEmb instanceof EntityBean) {
                        ((EntityBean)oEmb)._ebean_getIntercept().setIntercepting(false);
                    }
                    final BeanProperty[] props = prop.getProperties();
                    for (int j = 0; j < props.length; ++j) {
                        final Object v = props[j].getValue(cacheEmb);
                        props[j].setValueIntercept(oEmb, v);
                    }
                }
            }
        }
    }
}
