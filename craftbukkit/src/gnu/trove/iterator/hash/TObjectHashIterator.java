// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator.hash;

import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.impl.hash.THashIterator;

public class TObjectHashIterator<E> extends THashIterator<E>
{
    protected final TObjectHash _objectHash;
    
    public TObjectHashIterator(final TObjectHash<E> hash) {
        super(hash);
        this._objectHash = hash;
    }
    
    protected E objectAtIndex(final int index) {
        final Object obj = this._objectHash._set[index];
        if (obj == TObjectHash.FREE || obj == TObjectHash.REMOVED) {
            return null;
        }
        return (E)obj;
    }
}
