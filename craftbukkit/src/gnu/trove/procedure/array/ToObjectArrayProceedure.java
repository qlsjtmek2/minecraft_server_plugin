// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.procedure.array;

import gnu.trove.procedure.TObjectProcedure;

public final class ToObjectArrayProceedure<T> implements TObjectProcedure<T>
{
    private final T[] target;
    private int pos;
    
    public ToObjectArrayProceedure(final T[] target) {
        this.pos = 0;
        this.target = target;
    }
    
    public final boolean execute(final T value) {
        this.target[this.pos++] = value;
        return true;
    }
}
