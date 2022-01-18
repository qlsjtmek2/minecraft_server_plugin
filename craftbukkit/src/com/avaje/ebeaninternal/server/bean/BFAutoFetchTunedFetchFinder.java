// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.bean;

import java.util.Iterator;
import java.util.List;
import com.avaje.ebean.common.BeanList;
import com.avaje.ebean.Query;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.autofetch.TunedQueryInfo;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.meta.MetaAutoFetchTunedQueryInfo;
import com.avaje.ebean.event.BeanFinder;

public class BFAutoFetchTunedFetchFinder implements BeanFinder<MetaAutoFetchTunedQueryInfo>
{
    public MetaAutoFetchTunedQueryInfo find(final BeanQueryRequest<MetaAutoFetchTunedQueryInfo> request) {
        final SpiQuery<?> query = (SpiQuery<?>)(SpiQuery)request.getQuery();
        try {
            final String queryPointKey = (String)query.getId();
            final SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
            final AutoFetchManager manager = server.getAutoFetchManager();
            final TunedQueryInfo tunedFetch = manager.getTunedQueryInfo(queryPointKey);
            if (tunedFetch != null) {
                return tunedFetch.createPublicMeta();
            }
            return null;
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public BeanCollection<MetaAutoFetchTunedQueryInfo> findMany(final BeanQueryRequest<MetaAutoFetchTunedQueryInfo> request) {
        final Query.Type queryType = request.getQuery().getType();
        if (!queryType.equals(Query.Type.LIST)) {
            throw new PersistenceException("Only findList() supported at this stage.");
        }
        final SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
        final AutoFetchManager manager = server.getAutoFetchManager();
        final BeanList<MetaAutoFetchTunedQueryInfo> list = new BeanList<MetaAutoFetchTunedQueryInfo>();
        final Iterator<TunedQueryInfo> it = manager.iterateTunedQueryInfo();
        while (it.hasNext()) {
            final TunedQueryInfo tunedFetch = it.next();
            list.add(tunedFetch.createPublicMeta());
        }
        String orderBy = request.getQuery().order().toStringFormat();
        if (orderBy == null) {
            orderBy = "beanType, origQueryPlanHash";
        }
        server.sort(list, orderBy);
        return list;
    }
}
