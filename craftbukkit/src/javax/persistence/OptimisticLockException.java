// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public class OptimisticLockException extends PersistenceException
{
    Object entity;
    
    public OptimisticLockException() {
    }
    
    public OptimisticLockException(final String message) {
        super(message);
    }
    
    public OptimisticLockException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public OptimisticLockException(final Throwable cause) {
        super(cause);
    }
    
    public OptimisticLockException(final Object entity) {
        this.entity = entity;
    }
    
    public OptimisticLockException(final String message, final Throwable cause, final Object entity) {
        super(message, cause);
        this.entity = entity;
    }
    
    public Object getEntity() {
        return this.entity;
    }
}
