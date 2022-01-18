// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TByteStack
{
    byte getNoEntryValue();
    
    void push(final byte p0);
    
    byte pop();
    
    byte peek();
    
    int size();
    
    void clear();
    
    byte[] toArray();
    
    void toArray(final byte[] p0);
}
