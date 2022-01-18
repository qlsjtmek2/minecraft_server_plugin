// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public class RollbackException extends PersistenceException
{
    public RollbackException() {
    }
    
    public RollbackException(final String message) {
        super(message);
    }
    
    public RollbackException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public RollbackException(final Throwable cause) {
        super(cause);
    }
}
