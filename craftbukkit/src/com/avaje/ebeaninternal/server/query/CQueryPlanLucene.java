// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.sql.ResultSet;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

public class CQueryPlanLucene extends CQueryPlan
{
    private final OrmQueryRequest<?> request;
    
    public CQueryPlanLucene(final OrmQueryRequest<?> request, final SqlTree sqlTree) {
        super(request, null, sqlTree, false, "", getLuceneDescription(request));
        this.request = request;
    }
    
    public boolean isLucene() {
        return true;
    }
    
    public DataReader createDataReader(final ResultSet rset) {
        return new LuceneIndexDataReader(this.request);
    }
    
    private static String getLuceneDescription(final OrmQueryRequest<?> request) {
        final LuceneOrmQueryRequest req = request.getLuceneOrmQueryRequest();
        final String description = req.getDescription();
        final String sortDesc = req.getSortDesc();
        final BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
        final StringBuilder sb = new StringBuilder();
        sb.append("lucene query from ").append(beanDescriptor.getName());
        sb.append(" ").append(description);
        if (sortDesc != null && sortDesc.length() > 0) {
            sb.append(" order by ").append(sortDesc);
        }
        return sb.toString();
    }
}
