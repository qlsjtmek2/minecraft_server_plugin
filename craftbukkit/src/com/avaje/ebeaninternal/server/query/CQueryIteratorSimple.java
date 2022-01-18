// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebean.QueryIterator;

class CQueryIteratorSimple<T> implements QueryIterator<T>
{
    private final CQuery<T> cquery;
    private final OrmQueryRequest<T> request;
    
    CQueryIteratorSimple(final CQuery<T> cquery, final OrmQueryRequest<T> request) {
        this.cquery = cquery;
        this.request = request;
    }
    
    public boolean hasNext() {
        try {
            return this.cquery.hasNextBean(true);
        }
        catch (SQLException e) {
            throw this.cquery.createPersistenceException(e);
        }
    }
    
    public T next() {
        return this.cquery.getLoadedBean();
    }
    
    public void close() {
        this.cquery.updateExecutionStatistics();
        this.cquery.close();
        this.request.endTransIfRequired();
    }
    
    public void remove() {
        throw new PersistenceException("Remove not allowed");
    }
}
