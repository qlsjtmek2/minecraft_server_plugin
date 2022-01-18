// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.annotations.Beta;

@Deprecated
@Beta
public interface MapEvictionListener<K, V>
{
    void onEviction(@Nullable final K p0, @Nullable final V p1);
}
