// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.List;
import com.avaje.ebeaninternal.api.SpiExpression;

public class IdInExpression implements SpiExpression
{
    private static final long serialVersionUID = 1L;
    private final List<?> idList;
    
    public IdInExpression(final List<?> idList) {
        this.idList = idList;
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
        final BeanDescriptor<?> descriptor = r.getBeanDescriptor();
        final IdBinder idBinder = descriptor.getIdBinder();
        for (int i = 0; i < this.idList.size(); ++i) {
            idBinder.addIdInBindValue(request, this.idList.get(i));
        }
    }
    
    public void addSqlNoAlias(final SpiExpressionRequest request) {
        final DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        final BeanDescriptor<?> descriptor = r.getBeanDescriptor();
        final IdBinder idBinder = descriptor.getIdBinder();
        request.append(descriptor.getIdBinder().getBindIdInSql(null));
        final String inClause = idBinder.getIdInValueExpr(this.idList.size());
        request.append(inClause);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        final BeanDescriptor<?> descriptor = r.getBeanDescriptor();
        final IdBinder idBinder = descriptor.getIdBinder();
        request.append(descriptor.getIdBinderInLHSSql());
        final String inClause = idBinder.getIdInValueExpr(this.idList.size());
        request.append(inClause);
    }
    
    public int queryAutoFetchHash() {
        int hc = IdInExpression.class.getName().hashCode();
        hc = hc * 31 + this.idList.size();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.idList.hashCode();
    }
}
