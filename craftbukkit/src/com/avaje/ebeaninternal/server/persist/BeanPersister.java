// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public interface BeanPersister
{
    void insert(final PersistRequestBean<?> p0) throws PersistenceException;
    
    void update(final PersistRequestBean<?> p0) throws PersistenceException;
    
    void delete(final PersistRequestBean<?> p0) throws PersistenceException;
}
