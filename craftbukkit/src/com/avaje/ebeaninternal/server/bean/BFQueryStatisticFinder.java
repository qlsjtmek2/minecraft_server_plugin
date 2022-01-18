// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.bean;

import com.avaje.ebeaninternal.server.query.CQueryPlan;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.common.BeanList;
import javax.persistence.PersistenceException;
import com.avaje.ebean.Query;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.meta.MetaQueryStatistic;
import com.avaje.ebean.event.BeanFinder;

public class BFQueryStatisticFinder implements BeanFinder<MetaQueryStatistic>
{
    public MetaQueryStatistic find(final BeanQueryRequest<MetaQueryStatistic> request) {
        throw new RuntimeException("Not Supported yet");
    }
    
    public BeanCollection<MetaQueryStatistic> findMany(final BeanQueryRequest<MetaQueryStatistic> request) {
        final Query.Type queryType = request.getQuery().getType();
        if (!queryType.equals(Query.Type.LIST)) {
            throw new PersistenceException("Only findList() supported at this stage.");
        }
        final BeanList<MetaQueryStatistic> list = new BeanList<MetaQueryStatistic>();
        final SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
        this.build(list, server);
        String orderBy = request.getQuery().order().toStringFormat();
        if (orderBy == null) {
            orderBy = "beanType, origQueryPlanHash, autofetchTuned";
        }
        server.sort(list, orderBy);
        return list;
    }
    
    private void build(final List<MetaQueryStatistic> list, final SpiEbeanServer server) {
        for (final BeanDescriptor<?> desc : server.getBeanDescriptors()) {
            desc.clearQueryStatistics();
            this.build(list, desc);
        }
    }
    
    private void build(final List<MetaQueryStatistic> list, final BeanDescriptor<?> desc) {
        final Iterator<CQueryPlan> it = desc.queryPlans();
        while (it.hasNext()) {
            final CQueryPlan queryPlan = it.next();
            list.add(queryPlan.createMetaQueryStatistic(desc.getFullName()));
        }
    }
}
