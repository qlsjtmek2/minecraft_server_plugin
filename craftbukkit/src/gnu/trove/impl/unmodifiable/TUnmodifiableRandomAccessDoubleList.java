// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.list.TDoubleList;
import java.util.RandomAccess;

public class TUnmodifiableRandomAccessDoubleList extends TUnmodifiableDoubleList implements RandomAccess
{
    private static final long serialVersionUID = -2542308836966382001L;
    
    public TUnmodifiableRandomAccessDoubleList(final TDoubleList list) {
        super(list);
    }
    
    public TDoubleList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableRandomAccessDoubleList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object writeReplace() {
        return new TUnmodifiableDoubleList(this.list);
    }
}
