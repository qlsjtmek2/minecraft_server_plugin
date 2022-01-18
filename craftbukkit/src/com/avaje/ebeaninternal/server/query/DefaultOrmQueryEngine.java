// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.Iterator;
import java.util.Collection;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.CopyBeanCollection;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.core.OrmQueryEngine;

public class DefaultOrmQueryEngine implements OrmQueryEngine
{
    private final CQueryEngine queryEngine;
    
    public DefaultOrmQueryEngine(final BeanDescriptorManager descMgr, final CQueryEngine queryEngine) {
        this.queryEngine = queryEngine;
    }
    
    public <T> int findRowCount(final OrmQueryRequest<T> request) {
        return this.queryEngine.findRowCount(request);
    }
    
    public <T> BeanIdList findIds(final OrmQueryRequest<T> request) {
        return this.queryEngine.findIds(request);
    }
    
    public <T> QueryIterator<T> findIterate(final OrmQueryRequest<T> request) {
        final SpiTransaction t = request.getTransaction();
        t.flushBatch();
        return this.queryEngine.findIterate(request);
    }
    
    public <T> BeanCollection<T> findMany(final OrmQueryRequest<T> request) {
        final SpiQuery<T> query = request.getQuery();
        if (query.isUseQueryCache() || query.isLoadBeanCache()) {
            query.setSharedInstance();
        }
        BeanCollection<T> result = null;
        final SpiTransaction t = request.getTransaction();
        t.flushBatch();
        final BeanFinder<T> finder = request.getBeanFinder();
        if (finder != null) {
            result = finder.findMany(request);
        }
        else {
            result = this.queryEngine.findMany(request);
        }
        if (query.isLoadBeanCache()) {
            final BeanDescriptor<T> descriptor = request.getBeanDescriptor();
            final Collection<T> c = result.getActualDetails();
            for (final T bean : c) {
                descriptor.cachePut(bean, query.isSharedInstance());
            }
        }
        if (query.isSharedInstance() && !result.isEmpty()) {
            if (query.isUseQueryCache()) {
                request.putToQueryCache(result);
            }
            if (Boolean.FALSE.equals(query.isReadOnly())) {
                final CopyContext ctx = new CopyContext(request.isVanillaMode(), false);
                result = new CopyBeanCollection<T>(result, request.getBeanDescriptor(), ctx, 5).copy();
            }
        }
        return result;
    }
    
    public <T> T findId(final OrmQueryRequest<T> request) {
        T result = null;
        final SpiTransaction t = request.getTransaction();
        if (t.isBatchFlushOnQuery()) {
            t.flushBatch();
        }
        final BeanFinder<T> finder = request.getBeanFinder();
        if (finder != null) {
            result = finder.find(request);
        }
        else {
            result = this.queryEngine.find(request);
        }
        if (result != null && request.isUseBeanCache()) {
            final BeanDescriptor<T> descriptor = request.getBeanDescriptor();
            descriptor.cachePut(result, request.isUseBeanCacheReadOnly());
        }
        return result;
    }
}
