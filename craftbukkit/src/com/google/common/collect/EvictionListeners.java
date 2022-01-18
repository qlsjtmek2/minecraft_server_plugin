// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import java.util.concurrent.Executor;
import com.google.common.annotations.Beta;

@Deprecated
@Beta
public final class EvictionListeners
{
    @Deprecated
    public static <K, V> MapEvictionListener<K, V> asynchronous(final MapEvictionListener<K, V> listener, final Executor executor) {
        return new MapEvictionListener<K, V>() {
            public void onEviction(@Nullable final K key, @Nullable final V value) {
                executor.execute(new Runnable() {
                    public void run() {
                        listener.onEviction(key, value);
                    }
                });
            }
        };
    }
}
