// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.TFloatCollection;
import gnu.trove.set.TFloatSet;

public class TSynchronizedFloatSet extends TSynchronizedFloatCollection implements TFloatSet
{
    private static final long serialVersionUID = 487447009682186044L;
    
    public TSynchronizedFloatSet(final TFloatSet s) {
        super(s);
    }
    
    public TSynchronizedFloatSet(final TFloatSet s, final Object mutex) {
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
