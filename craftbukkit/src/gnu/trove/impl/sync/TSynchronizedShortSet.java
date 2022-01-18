// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.TShortCollection;
import gnu.trove.set.TShortSet;

public class TSynchronizedShortSet extends TSynchronizedShortCollection implements TShortSet
{
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedShortSet(final TShortSet s) {
        super(s);
    }
    
    public TSynchronizedShortSet(final TShortSet s, final Object mutex) {
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
