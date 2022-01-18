// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.serialization;

import java.lang.ref.WeakReference;
import java.lang.ref.Reference;
import java.util.Map;

final class WeakReferenceMap<K, V> extends ReferenceMap<K, V>
{
    public WeakReferenceMap(final Map<K, Reference<V>> delegate) {
        super(delegate);
    }
    
    @Override
    Reference<V> fold(final V value) {
        return new WeakReference<V>(value);
    }
}
