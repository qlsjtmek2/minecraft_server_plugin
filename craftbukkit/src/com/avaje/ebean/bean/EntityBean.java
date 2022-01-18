// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

public interface EntityBean extends Serializable
{
    String _ebean_getMarker();
    
    Object _ebean_newInstance();
    
    void addPropertyChangeListener(final PropertyChangeListener p0);
    
    void removePropertyChangeListener(final PropertyChangeListener p0);
    
    void _ebean_setEmbeddedLoaded();
    
    boolean _ebean_isEmbeddedNewOrDirty();
    
    EntityBeanIntercept _ebean_getIntercept();
    
    EntityBeanIntercept _ebean_intercept();
    
    Object _ebean_createCopy();
    
    String[] _ebean_getFieldNames();
    
    void _ebean_setField(final int p0, final Object p1, final Object p2);
    
    void _ebean_setFieldIntercept(final int p0, final Object p1, final Object p2);
    
    Object _ebean_getField(final int p0, final Object p1);
    
    Object _ebean_getFieldIntercept(final int p0, final Object p1);
}
