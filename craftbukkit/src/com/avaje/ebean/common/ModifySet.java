// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.util.Collection;
import com.avaje.ebean.bean.BeanCollection;
import java.util.Set;

class ModifySet<E> extends ModifyCollection<E> implements Set<E>
{
    public ModifySet(final BeanCollection<E> owner, final Set<E> s) {
        super(owner, s);
    }
}
