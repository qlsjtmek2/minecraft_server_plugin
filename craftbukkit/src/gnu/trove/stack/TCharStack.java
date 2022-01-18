// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TCharStack
{
    char getNoEntryValue();
    
    void push(final char p0);
    
    char pop();
    
    char peek();
    
    int size();
    
    void clear();
    
    char[] toArray();
    
    void toArray(final char[] p0);
}
