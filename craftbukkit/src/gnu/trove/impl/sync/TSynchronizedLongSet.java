// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.TLongCollection;
import gnu.trove.set.TLongSet;

public class TSynchronizedLongSet extends TSynchronizedLongCollection implements TLongSet
{
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedLongSet(final TLongSet s) {
        super(s);
    }
    
    public TSynchronizedLongSet(final TLongSet s, final Object mutex) {
        super(s, mutex);
    }
    
    public boolean equals(final Object o) {
        synchronized (this.mutex) {
            return this.c.equals(o);
        }
    }
    
    public int hashCode() {
        synchronized (this.mutex) {
            return this.c.hashCode();
        }
    }
}
