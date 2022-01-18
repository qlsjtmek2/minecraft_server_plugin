// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap;

import com.avaje.ebean.bean.BeanCollection;
import java.util.Set;
import java.util.Map;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;

public class LdapOrmQueryRequest<T> implements SpiOrmQueryRequest<T>
{
    private final SpiQuery<T> query;
    private final BeanDescriptor<T> desc;
    private final LdapOrmQueryEngine queryEngine;
    
    public LdapOrmQueryRequest(final SpiQuery<T> query, final BeanDescriptor<T> desc, final LdapOrmQueryEngine queryEngine) {
        this.query = query;
        this.desc = desc;
        this.queryEngine = queryEngine;
    }
    
    public BeanDescriptor<T> getBeanDescriptor() {
        return this.desc;
    }
    
    public SpiQuery<T> getQuery() {
        return this.query;
    }
    
    public Object findId() {
        return this.queryEngine.findId((LdapOrmQueryRequest<Object>)this);
    }
    
    public List<Object> findIds() {
        throw new RuntimeException("Not Implemented yet");
    }
    
    public List<T> findList() {
        return this.queryEngine.findList(this);
    }
    
    public void findVisit(final QueryResultVisitor<T> visitor) {
        throw new RuntimeException("Not Implemented yet");
    }
    
    public QueryIterator<T> findIterate() {
        throw new RuntimeException("Not Implemented yet");
    }
    
    public Map<?, ?> findMap() {
        throw new RuntimeException("Not Implemented yet");
    }
    
    public int findRowCount() {
        throw new RuntimeException("Not Implemented yet");
    }
    
    public Set<?> findSet() {
        throw new RuntimeException("Not Implemented yet");
    }
    
    public T getFromPersistenceContextOrCache() {
        return null;
    }
    
    public BeanCollection<T> getFromQueryCache() {
        return null;
    }
    
    public void initTransIfRequired() {
    }
    
    public void rollbackTransIfRequired() {
    }
    
    public void endTransIfRequired() {
    }
}
