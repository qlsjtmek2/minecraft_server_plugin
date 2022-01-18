// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import com.google.common.annotations.Beta;

@Beta
public interface RemovalListener<K, V>
{
    void onRemoval(final RemovalNotification<K, V> p0);
}
