// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.TLongCollection;
import java.io.Serializable;
import gnu.trove.set.TLongSet;

public class TUnmodifiableLongSet extends TUnmodifiableLongCollection implements TLongSet, Serializable
{
    private static final long serialVersionUID = -9215047833775013803L;
    
    public TUnmodifiableLongSet(final TLongSet s) {
        super(s);
    }
    
    public boolean equals(final Object o) {
        return o == this || this.c.equals(o);
    }
    
    public int hashCode() {
        return this.c.hashCode();
    }
}
