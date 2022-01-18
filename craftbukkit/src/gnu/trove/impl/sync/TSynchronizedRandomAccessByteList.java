// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.list.TByteList;
import java.util.RandomAccess;

public class TSynchronizedRandomAccessByteList extends TSynchronizedByteList implements RandomAccess
{
    static final long serialVersionUID = 1530674583602358482L;
    
    public TSynchronizedRandomAccessByteList(final TByteList list) {
        super(list);
    }
    
    public TSynchronizedRandomAccessByteList(final TByteList list, final Object mutex) {
        super(list, mutex);
    }
    
    public TByteList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedRandomAccessByteList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    private Object writeReplace() {
        return new TSynchronizedByteList(this.list);
    }
}
