// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TShortStack
{
    short getNoEntryValue();
    
    void push(final short p0);
    
    short pop();
    
    short peek();
    
    int size();
    
    void clear();
    
    short[] toArray();
    
    void toArray(final short[] p0);
}
