// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.hash;

import gnu.trove.impl.HashFunctions;

public abstract class TPrimitiveHash extends THash
{
    static final long serialVersionUID = 1L;
    public transient byte[] _states;
    public static final byte FREE = 0;
    public static final byte FULL = 1;
    public static final byte REMOVED = 2;
    
    public TPrimitiveHash() {
    }
    
    public TPrimitiveHash(final int initialCapacity) {
        this(initialCapacity, 0.5f);
    }
    
    public TPrimitiveHash(int initialCapacity, final float loadFactor) {
        initialCapacity = Math.max(1, initialCapacity);
        this._loadFactor = loadFactor;
        this.setUp(HashFunctions.fastCeil(initialCapacity / loadFactor));
    }
    
    public int capacity() {
        return this._states.length;
    }
    
    protected void removeAt(final int index) {
        this._states[index] = 2;
        super.removeAt(index);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._states = new byte[capacity];
        return capacity;
    }
}
