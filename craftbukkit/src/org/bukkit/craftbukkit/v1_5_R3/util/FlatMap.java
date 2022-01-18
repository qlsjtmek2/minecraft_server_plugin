// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

public class FlatMap<V>
{
    private static final int FLAT_LOOKUP_SIZE = 512;
    private final Object[][] flatLookup;
    
    public FlatMap() {
        this.flatLookup = new Object[1024][1024];
    }
    
    public void put(final long msw, final long lsw, final V value) {
        final long acx = Math.abs(msw);
        final long acz = Math.abs(lsw);
        if (acx < 512L && acz < 512L) {
            this.flatLookup[(int)(msw + 512L)][(int)(lsw + 512L)] = value;
        }
    }
    
    public void put(final long key, final V value) {
        this.put(LongHash.msw(key), LongHash.lsw(key), value);
    }
    
    public V get(final long msw, final long lsw) {
        final long acx = Math.abs(msw);
        final long acz = Math.abs(lsw);
        if (acx < 512L && acz < 512L) {
            return (V)this.flatLookup[(int)(msw + 512L)][(int)(lsw + 512L)];
        }
        return null;
    }
    
    public V get(final long key) {
        return this.get(LongHash.msw(key), LongHash.lsw(key));
    }
}
