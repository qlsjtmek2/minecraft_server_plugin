// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import java.io.Serializable;

public interface TLinkable<T extends TLinkable> extends Serializable
{
    public static final long serialVersionUID = 997545054865482562L;
    
    T getNext();
    
    T getPrevious();
    
    void setNext(final T p0);
    
    void setPrevious(final T p0);
}
