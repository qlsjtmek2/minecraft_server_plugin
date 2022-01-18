// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.list.TFloatList;
import java.util.RandomAccess;

public class TUnmodifiableRandomAccessFloatList extends TUnmodifiableFloatList implements RandomAccess
{
    private static final long serialVersionUID = -2542308836966382001L;
    
    public TUnmodifiableRandomAccessFloatList(final TFloatList list) {
        super(list);
    }
    
    public TFloatList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableRandomAccessFloatList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object writeReplace() {
        return new TUnmodifiableFloatList(this.list);
    }
}
