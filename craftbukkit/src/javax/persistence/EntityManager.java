// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

public interface EntityManager
{
    void persist(final Object p0);
    
     <T> T merge(final T p0);
    
    void remove(final Object p0);
    
     <T> T find(final Class<T> p0, final Object p1);
    
     <T> T getReference(final Class<T> p0, final Object p1);
    
    void flush();
    
    void setFlushMode(final FlushModeType p0);
    
    FlushModeType getFlushMode();
    
    void lock(final Object p0, final LockModeType p1);
    
    void refresh(final Object p0);
    
    void clear();
    
    boolean contains(final Object p0);
    
    Query createQuery(final String p0);
    
    Query createNamedQuery(final String p0);
    
    Query createNativeQuery(final String p0);
    
    Query createNativeQuery(final String p0, final Class p1);
    
    Query createNativeQuery(final String p0, final String p1);
    
    void joinTransaction();
    
    Object getDelegate();
    
    void close();
    
    boolean isOpen();
    
    EntityTransaction getTransaction();
}
