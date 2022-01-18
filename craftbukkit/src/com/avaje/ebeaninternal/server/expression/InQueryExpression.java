// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import java.util.List;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.api.SpiQuery;

class InQueryExpression extends AbstractExpression
{
    private static final long serialVersionUID = 666990277309851644L;
    private final SpiQuery<?> subQuery;
    private transient CQuery<?> compiledSubQuery;
    
    public InQueryExpression(final FilterExprPath pathPrefix, final String propertyName, final SpiQuery<?> subQuery) {
        super(pathPrefix, propertyName);
        this.subQuery = subQuery;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public int queryAutoFetchHash() {
        int hc = InQueryExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        hc = hc * 31 + this.subQuery.queryAutofetchHash();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        this.compiledSubQuery = this.compileSubQuery(request);
        int hc = InQueryExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        hc = hc * 31 + this.subQuery.queryPlanHash(request);
        return hc;
    }
    
    private CQuery<?> compileSubQuery(final BeanQueryRequest<?> queryRequest) {
        final SpiEbeanServer ebeanServer = (SpiEbeanServer)queryRequest.getEbeanServer();
        return ebeanServer.compileQuery(this.subQuery, queryRequest.getTransaction());
    }
    
    public int queryBindHash() {
        return this.subQuery.queryBindHash();
    }
    
    public void addSql(final SpiExpressionRequest request) {
        String subSelect = this.compiledSubQuery.getGeneratedSql();
        subSelect = subSelect.replace('\n', ' ');
        final String propertyName = this.getPropertyName();
        request.append(" (");
        request.append(propertyName);
        request.append(") in (");
        request.append(subSelect);
        request.append(") ");
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        final List<Object> bindParams = this.compiledSubQuery.getPredicates().getWhereExprBindValues();
        if (bindParams == null) {
            return;
        }
        for (int i = 0; i < bindParams.size(); ++i) {
            request.addBindValue(bindParams.get(i));
        }
    }
}
