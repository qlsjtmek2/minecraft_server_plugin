// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public class PersistenceException extends RuntimeException
{
    public PersistenceException() {
    }
    
    public PersistenceException(final String message) {
        super(message);
    }
    
    public PersistenceException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public PersistenceException(final Throwable cause) {
        super(cause);
    }
}
