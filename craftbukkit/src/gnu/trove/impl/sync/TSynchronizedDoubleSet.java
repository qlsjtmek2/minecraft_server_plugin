// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.TDoubleCollection;
import gnu.trove.set.TDoubleSet;

public class TSynchronizedDoubleSet extends TSynchronizedDoubleCollection implements TDoubleSet
{
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedDoubleSet(final TDoubleSet s) {
        super(s);
    }
    
    public TSynchronizedDoubleSet(final TDoubleSet s, final Object mutex) {
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
