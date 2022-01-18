// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.list.TLongList;
import java.util.RandomAccess;

public class TSynchronizedRandomAccessLongList extends TSynchronizedLongList implements RandomAccess
{
    static final long serialVersionUID = 1530674583602358482L;
    
    public TSynchronizedRandomAccessLongList(final TLongList list) {
        super(list);
    }
    
    public TSynchronizedRandomAccessLongList(final TLongList list, final Object mutex) {
        super(list, mutex);
    }
    
    public TLongList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedRandomAccessLongList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    private Object writeReplace() {
        return new TSynchronizedLongList(this.list);
    }
}
