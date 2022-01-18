// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.reflect;

public interface BeanReflect
{
    Object createEntityBean();
    
    Object createVanillaBean();
    
    boolean isVanillaOnly();
    
    BeanReflectGetter getGetter(final String p0);
    
    BeanReflectSetter getSetter(final String p0);
}
