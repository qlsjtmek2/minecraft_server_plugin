// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.strategy;

import java.io.Serializable;

public interface HashingStrategy<T> extends Serializable
{
    public static final long serialVersionUID = 5674097166776615540L;
    
    int computeHashCode(final T p0);
    
    boolean equals(final T p0, final T p1);
}
