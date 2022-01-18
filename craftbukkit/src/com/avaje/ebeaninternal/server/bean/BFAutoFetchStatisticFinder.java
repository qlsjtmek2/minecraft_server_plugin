// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.bean;

import java.util.Iterator;
import java.util.List;
import com.avaje.ebean.common.BeanList;
import com.avaje.ebean.Query;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.autofetch.Statistics;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import com.avaje.ebean.event.BeanFinder;

public class BFAutoFetchStatisticFinder implements BeanFinder<MetaAutoFetchStatistic>
{
    public MetaAutoFetchStatistic find(final BeanQueryRequest<MetaAutoFetchStatistic> request) {
        final SpiQuery<MetaAutoFetchStatistic> query = (SpiQuery<MetaAutoFetchStatistic>)(SpiQuery)request.getQuery();
        try {
            final String queryPointKey = (String)query.getId();
            final SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
            final AutoFetchManager manager = server.getAutoFetchManager();
            final Statistics stats = manager.getStatistics(queryPointKey);
            if (stats != null) {
                return stats.createPublicMeta();
            }
            return null;
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public BeanCollection<MetaAutoFetchStatistic> findMany(final BeanQueryRequest<MetaAutoFetchStatistic> request) {
        final Query.Type queryType = request.getQuery().getType();
        if (!queryType.equals(Query.Type.LIST)) {
            throw new PersistenceException("Only findList() supported at this stage.");
        }
        final SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
        final AutoFetchManager manager = server.getAutoFetchManager();
        final BeanList<MetaAutoFetchStatistic> list = new BeanList<MetaAutoFetchStatistic>();
        final Iterator<Statistics> it = manager.iterateStatistics();
        while (it.hasNext()) {
            final Statistics stats = it.next();
            list.add(stats.createPublicMeta());
        }
        String orderBy = request.getQuery().order().toStringFormat();
        if (orderBy == null) {
            orderBy = "beanType";
        }
        server.sort(list, orderBy);
        return list;
    }
}
