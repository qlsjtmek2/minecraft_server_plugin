// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public class TransactionRequiredException extends PersistenceException
{
    public TransactionRequiredException() {
    }
    
    public TransactionRequiredException(final String message) {
        super(message);
    }
}
