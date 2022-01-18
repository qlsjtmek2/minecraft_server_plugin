// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.hash;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.strategy.HashingStrategy;

public abstract class TCustomObjectHash<T> extends TObjectHash<T>
{
    static final long serialVersionUID = 8766048185963756400L;
    protected HashingStrategy<? super T> strategy;
    
    public TCustomObjectHash() {
    }
    
    public TCustomObjectHash(final HashingStrategy<? super T> strategy) {
        this.strategy = strategy;
    }
    
    public TCustomObjectHash(final HashingStrategy<? super T> strategy, final int initialCapacity) {
        super(initialCapacity);
        this.strategy = strategy;
    }
    
    public TCustomObjectHash(final HashingStrategy<? super T> strategy, final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.strategy = strategy;
    }
    
    protected int hash(final Object obj) {
        return this.strategy.computeHashCode((Object)obj);
    }
    
    protected boolean equals(final Object one, final Object two) {
        return two != TCustomObjectHash.REMOVED && this.strategy.equals((Object)one, (Object)two);
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeObject(this.strategy);
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.strategy = (HashingStrategy<? super T>)in.readObject();
    }
}
