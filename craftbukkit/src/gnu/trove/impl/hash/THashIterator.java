// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.hash;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import gnu.trove.iterator.TIterator;

public abstract class THashIterator<V> implements TIterator, Iterator<V>
{
    private final TObjectHash<V> _object_hash;
    protected final THash _hash;
    protected int _expectedSize;
    protected int _index;
    
    protected THashIterator(final TObjectHash<V> hash) {
        this._hash = hash;
        this._expectedSize = this._hash.size();
        this._index = this._hash.capacity();
        this._object_hash = hash;
    }
    
    public V next() {
        this.moveToNextIndex();
        return this.objectAtIndex(this._index);
    }
    
    public boolean hasNext() {
        return this.nextIndex() >= 0;
    }
    
    public void remove() {
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        try {
            this._hash.tempDisableAutoCompaction();
            this._hash.removeAt(this._index);
        }
        finally {
            this._hash.reenableAutoCompaction(false);
        }
        --this._expectedSize;
    }
    
    protected final void moveToNextIndex() {
        final int nextIndex = this.nextIndex();
        this._index = nextIndex;
        if (nextIndex < 0) {
            throw new NoSuchElementException();
        }
    }
    
    protected final int nextIndex() {
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        final Object[] set = this._object_hash._set;
        int i = this._index;
        while (i-- > 0) {
            if (set[i] != TObjectHash.FREE) {
                if (set[i] == TObjectHash.REMOVED) {
                    continue;
                }
                break;
            }
        }
        return i;
    }
    
    protected abstract V objectAtIndex(final int p0);
}
