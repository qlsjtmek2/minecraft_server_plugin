// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.eventbus;

import com.google.common.collect.Multimap;

interface HandlerFindingStrategy
{
    Multimap<Class<?>, EventHandler> findAllHandlers(final Object p0);
}
