// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.list.TShortList;
import java.util.RandomAccess;

public class TUnmodifiableRandomAccessShortList extends TUnmodifiableShortList implements RandomAccess
{
    private static final long serialVersionUID = -2542308836966382001L;
    
    public TUnmodifiableRandomAccessShortList(final TShortList list) {
        super(list);
    }
    
    public TShortList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableRandomAccessShortList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object writeReplace() {
        return new TUnmodifiableShortList(this.list);
    }
}
