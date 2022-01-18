// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.list.TIntList;
import java.util.RandomAccess;

public class TUnmodifiableRandomAccessIntList extends TUnmodifiableIntList implements RandomAccess
{
    private static final long serialVersionUID = -2542308836966382001L;
    
    public TUnmodifiableRandomAccessIntList(final TIntList list) {
        super(list);
    }
    
    public TIntList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableRandomAccessIntList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object writeReplace() {
        return new TUnmodifiableIntList(this.list);
    }
}
