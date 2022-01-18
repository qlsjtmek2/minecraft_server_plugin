// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.list.TIntList;
import java.util.RandomAccess;

public class TSynchronizedRandomAccessIntList extends TSynchronizedIntList implements RandomAccess
{
    static final long serialVersionUID = 1530674583602358482L;
    
    public TSynchronizedRandomAccessIntList(final TIntList list) {
        super(list);
    }
    
    public TSynchronizedRandomAccessIntList(final TIntList list, final Object mutex) {
        super(list, mutex);
    }
    
    public TIntList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedRandomAccessIntList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    private Object writeReplace() {
        return new TSynchronizedIntList(this.list);
    }
}
