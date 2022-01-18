// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

public abstract class TLinkableAdapter<T extends TLinkable> implements TLinkable<T>
{
    private volatile T next;
    private volatile T prev;
    
    public T getNext() {
        return this.next;
    }
    
    public void setNext(final T next) {
        this.next = next;
    }
    
    public T getPrevious() {
        return this.prev;
    }
    
    public void setPrevious(final T prev) {
        this.prev = prev;
    }
}
