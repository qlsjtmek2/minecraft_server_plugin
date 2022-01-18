// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import gnu.trove.list.TDoubleList;
import java.util.RandomAccess;

public class TSynchronizedRandomAccessDoubleList extends TSynchronizedDoubleList implements RandomAccess
{
    static final long serialVersionUID = 1530674583602358482L;
    
    public TSynchronizedRandomAccessDoubleList(final TDoubleList list) {
        super(list);
    }
    
    public TSynchronizedRandomAccessDoubleList(final TDoubleList list, final Object mutex) {
        super(list, mutex);
    }
    
    public TDoubleList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedRandomAccessDoubleList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    private Object writeReplace() {
        return new TSynchronizedDoubleList(this.list);
    }
}
