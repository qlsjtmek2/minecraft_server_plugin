// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TLongStack
{
    long getNoEntryValue();
    
    void push(final long p0);
    
    long pop();
    
    long peek();
    
    int size();
    
    void clear();
    
    long[] toArray();
    
    void toArray(final long[] p0);
}
