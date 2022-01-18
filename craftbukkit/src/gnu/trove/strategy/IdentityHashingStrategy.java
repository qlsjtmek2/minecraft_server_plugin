// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.strategy;

public class IdentityHashingStrategy<K> implements HashingStrategy<K>
{
    static final long serialVersionUID = -5188534454583764904L;
    
    public int computeHashCode(final K object) {
        return System.identityHashCode(object);
    }
    
    public boolean equals(final K o1, final K o2) {
        return o1 == o2;
    }
}
