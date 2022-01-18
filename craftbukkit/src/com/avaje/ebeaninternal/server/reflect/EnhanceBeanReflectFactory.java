// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.reflect;

public final class EnhanceBeanReflectFactory implements BeanReflectFactory
{
    public BeanReflect create(final Class<?> vanillaType, final Class<?> entityBeanType) {
        return new EnhanceBeanReflect(vanillaType, entityBeanType);
    }
}
