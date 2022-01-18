// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack;

public interface TDoubleStack
{
    double getNoEntryValue();
    
    void push(final double p0);
    
    double pop();
    
    double peek();
    
    int size();
    
    void clear();
    
    double[] toArray();
    
    void toArray(final double[] p0);
}
