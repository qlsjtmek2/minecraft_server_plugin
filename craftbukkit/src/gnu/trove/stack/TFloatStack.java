// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TFloatStack
{
    float getNoEntryValue();
    
    void push(final float p0);
    
    float pop();
    
    float peek();
    
    int size();
    
    void clear();
    
    float[] toArray();
    
    void toArray(final float[] p0);
}
