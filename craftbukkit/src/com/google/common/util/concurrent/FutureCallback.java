// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public interface FutureCallback<V>
{
    void onSuccess(final V p0);
    
    void onFailure(final Throwable p0);
}
