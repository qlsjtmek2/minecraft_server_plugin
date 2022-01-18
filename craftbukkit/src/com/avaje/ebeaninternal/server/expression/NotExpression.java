// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebean.Expression;
import com.avaje.ebeaninternal.api.SpiExpression;

final class NotExpression implements SpiExpression, LuceneAwareExpression
{
    private static final long serialVersionUID = 5648926732402355781L;
    private static final String NOT = "not (";
    private final SpiExpression exp;
    
    NotExpression(final Expression exp) {
        this.exp = (SpiExpression)exp;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
        this.exp.containsMany(desc, manyWhereJoin);
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        this.exp.addBindValues(request);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        request.append("not (");
        this.exp.addSql(request);
        request.append(") ");
    }
    
    public int queryAutoFetchHash() {
        int hc = NotExpression.class.getName().hashCode();
        hc = hc * 31 + this.exp.queryAutoFetchHash();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        int hc = NotExpression.class.getName().hashCode();
        hc = hc * 31 + this.exp.queryPlanHash(request);
        return hc;
    }
    
    public int queryBindHash() {
        return this.exp.queryBindHash();
    }
}
