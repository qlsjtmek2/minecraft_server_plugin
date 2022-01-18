// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.TDoubleCollection;
import java.io.Serializable;
import gnu.trove.set.TDoubleSet;

public class TUnmodifiableDoubleSet extends TUnmodifiableDoubleCollection implements TDoubleSet, Serializable
{
    private static final long serialVersionUID = -9215047833775013803L;
    
    public TUnmodifiableDoubleSet(final TDoubleSet s) {
        super(s);
    }
    
    public boolean equals(final Object o) {
        return o == this || this.c.equals(o);
    }
    
    public int hashCode() {
        return this.c.hashCode();
    }
}
