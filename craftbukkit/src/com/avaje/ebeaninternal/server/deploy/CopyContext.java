// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
import com.avaje.ebean.bean.PersistenceContext;

public class CopyContext
{
    private final boolean vanillaMode;
    private final boolean sharing;
    private final PersistenceContext pc;
    
    public CopyContext(final boolean vanillaMode, final boolean sharing) {
        this.vanillaMode = vanillaMode;
        this.sharing = sharing;
        this.pc = new DefaultPersistenceContext();
    }
    
    public CopyContext(final boolean vanillaMode) {
        this(vanillaMode, false);
    }
    
    public boolean isVanillaMode() {
        return this.vanillaMode;
    }
    
    public boolean isSharing() {
        return this.sharing;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.pc;
    }
    
    public Object putIfAbsent(final Object id, final Object bean) {
        return this.pc.putIfAbsent(id, bean);
    }
    
    public Object get(final Class<?> beanType, final Object beanId) {
        return this.pc.get(beanType, beanId);
    }
}
