// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.TIntCollection;
import gnu.trove.set.TIntSet;

public class TSynchronizedIntSet extends TSynchronizedIntCollection implements TIntSet
{
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedIntSet(final TIntSet s) {
        super(s);
    }
    
    public TSynchronizedIntSet(final TIntSet s, final Object mutex) {
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
