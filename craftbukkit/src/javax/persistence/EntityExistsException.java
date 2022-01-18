// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public class EntityExistsException extends PersistenceException
{
    public EntityExistsException() {
    }
    
    public EntityExistsException(final String message) {
        super(message);
    }
    
    public EntityExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public EntityExistsException(final Throwable cause) {
        super(cause);
    }
}
