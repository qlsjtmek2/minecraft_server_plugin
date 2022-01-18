// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TIntStack
{
    int getNoEntryValue();
    
    void push(final int p0);
    
    int pop();
    
    int peek();
    
    int size();
    
    void clear();
    
    int[] toArray();
    
    void toArray(final int[] p0);
}
