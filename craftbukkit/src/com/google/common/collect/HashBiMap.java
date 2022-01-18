// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class HashBiMap<K, V> extends AbstractBiMap<K, V>
{
    @GwtIncompatible("Not needed in emulated source")
    private static final long serialVersionUID = 0L;
    
    public static <K, V> HashBiMap<K, V> create() {
        return new HashBiMap<K, V>();
    }
    
    public static <K, V> HashBiMap<K, V> create(final int expectedSize) {
        return new HashBiMap<K, V>(expectedSize);
    }
    
    public static <K, V> HashBiMap<K, V> create(final Map<? extends K, ? extends V> map) {
        final HashBiMap<K, V> bimap = create(map.size());
        bimap.putAll(map);
        return bimap;
    }
    
    private HashBiMap() {
        super(new HashMap(), new HashMap());
    }
    
    private HashBiMap(final int expectedSize) {
        super(Maps.newHashMapWithExpectedSize(expectedSize), Maps.newHashMapWithExpectedSize(expectedSize));
    }
    
    public V put(@Nullable final K key, @Nullable final V value) {
        return super.put(key, value);
    }
    
    public V forcePut(@Nullable final K key, @Nullable final V value) {
        return super.forcePut(key, value);
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMap((Map<Object, Object>)this, stream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        final int size = Serialization.readCount(stream);
        this.setDelegates((Map<K, V>)Maps.newHashMapWithExpectedSize(size), (Map<V, K>)Maps.newHashMapWithExpectedSize(size));
        Serialization.populateMap((Map<Object, Object>)this, stream, size);
    }
}
