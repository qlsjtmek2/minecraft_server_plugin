// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.list.TByteList;
import java.util.RandomAccess;

public class TUnmodifiableRandomAccessByteList extends TUnmodifiableByteList implements RandomAccess
{
    private static final long serialVersionUID = -2542308836966382001L;
    
    public TUnmodifiableRandomAccessByteList(final TByteList list) {
        super(list);
    }
    
    public TByteList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableRandomAccessByteList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object writeReplace() {
        return new TUnmodifiableByteList(this.list);
    }
}
