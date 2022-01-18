// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.TCharCollection;
import gnu.trove.set.TCharSet;

public class TSynchronizedCharSet extends TSynchronizedCharCollection implements TCharSet
{
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedCharSet(final TCharSet s) {
        super(s);
    }
    
    public TSynchronizedCharSet(final TCharSet s, final Object mutex) {
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
