// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.api.SpiExpression;

class IdExpression implements SpiExpression
{
    private static final long serialVersionUID = -3065936341718489842L;
    private final Object value;
    
    IdExpression(final Object value) {
        this.value = value;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        final DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        final Object[] bindIdValues = r.getBeanDescriptor().getBindIdValues(this.value);
        for (int i = 0; i < bindIdValues.length; ++i) {
            request.addBindValue(bindIdValues[i]);
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        final String idSql = r.getBeanDescriptor().getIdBinderIdSql();
        request.append(idSql).append(" ");
    }
    
    public int queryAutoFetchHash() {
        return IdExpression.class.getName().hashCode();
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.value.hashCode();
    }
}
