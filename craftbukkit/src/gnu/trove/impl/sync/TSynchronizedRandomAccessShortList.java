// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.list.TShortList;
import java.util.RandomAccess;

public class TSynchronizedRandomAccessShortList extends TSynchronizedShortList implements RandomAccess
{
    static final long serialVersionUID = 1530674583602358482L;
    
    public TSynchronizedRandomAccessShortList(final TShortList list) {
        super(list);
    }
    
    public TSynchronizedRandomAccessShortList(final TShortList list, final Object mutex) {
        super(list, mutex);
    }
    
    public TShortList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedRandomAccessShortList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    private Object writeReplace() {
        return new TSynchronizedShortList(this.list);
    }
}
