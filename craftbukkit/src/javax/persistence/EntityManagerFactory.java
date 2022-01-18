// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.util.Map;

public interface EntityManagerFactory
{
    EntityManager createEntityManager();
    
    EntityManager createEntityManager(final Map p0);
    
    void close();
    
    boolean isOpen();
}
