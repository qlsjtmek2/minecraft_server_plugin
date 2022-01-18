// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.beans.PropertyChangeListener;
import java.util.Set;

public interface BeanState
{
    boolean isReference();
    
    boolean isNew();
    
    boolean isNewOrDirty();
    
    boolean isDirty();
    
    Set<String> getLoadedProps();
    
    Set<String> getChangedProps();
    
    boolean isReadOnly();
    
    void setReadOnly(final boolean p0);
    
    boolean isSharedInstance();
    
    void addPropertyChangeListener(final PropertyChangeListener p0);
    
    void removePropertyChangeListener(final PropertyChangeListener p0);
    
    void setReference();
    
    void setLoaded(final Set<String> p0);
}
