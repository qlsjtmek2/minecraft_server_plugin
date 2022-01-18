// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public interface EntityTransaction
{
    void begin();
    
    void commit();
    
    void rollback();
    
    void setRollbackOnly();
    
    boolean getRollbackOnly();
    
    boolean isActive();
}
