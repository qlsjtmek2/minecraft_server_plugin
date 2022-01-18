// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.io.Serializable;

class ModifyHolder<E> implements Serializable
{
    private static final long serialVersionUID = 2572572897923801083L;
    private Set<E> modifyDeletions;
    private Set<E> modifyAdditions;
    
    ModifyHolder() {
        this.modifyDeletions = new LinkedHashSet<E>();
        this.modifyAdditions = new LinkedHashSet<E>();
    }
    
    void reset() {
        this.modifyDeletions = new LinkedHashSet<E>();
        this.modifyAdditions = new LinkedHashSet<E>();
    }
    
    void modifyAdditionAll(final Collection<? extends E> c) {
        if (c != null) {
            this.modifyAdditions.addAll(c);
        }
    }
    
    void modifyAddition(final E bean) {
        if (bean != null) {
            this.modifyAdditions.add(bean);
        }
    }
    
    void modifyRemoval(final Object bean) {
        if (bean != null) {
            this.modifyDeletions.add((E)bean);
        }
    }
    
    Set<E> getModifyAdditions() {
        return this.modifyAdditions;
    }
    
    Set<E> getModifyRemovals() {
        return this.modifyDeletions;
    }
}
