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

abstract class LogicExpression implements SpiExpression
{
    private static final long serialVersionUID = 616860781960645251L;
    static final String AND = " and ";
    static final String OR = " or ";
    private final SpiExpression expOne;
    private final SpiExpression expTwo;
    private final String joinType;
    
    LogicExpression(final String joinType, final Expression expOne, final Expression expTwo) {
        this.joinType = joinType;
        this.expOne = (SpiExpression)expOne;
        this.expTwo = (SpiExpression)expTwo;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return this.expOne.isLuceneResolvable(req) && this.expTwo.isLuceneResolvable(req);
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return new LogicExpressionLucene().addLuceneQuery(this.joinType, request, this.expOne, this.expTwo);
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
        this.expOne.containsMany(desc, manyWhereJoin);
        this.expTwo.containsMany(desc, manyWhereJoin);
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        this.expOne.addBindValues(request);
        this.expTwo.addBindValues(request);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        request.append("(");
        this.expOne.addSql(request);
        request.append(this.joinType);
        this.expTwo.addSql(request);
        request.append(") ");
    }
    
    public int queryAutoFetchHash() {
        int hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
        hc = hc * 31 + this.expOne.queryAutoFetchHash();
        hc = hc * 31 + this.expTwo.queryAutoFetchHash();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        int hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
        hc = hc * 31 + this.expOne.queryPlanHash(request);
        hc = hc * 31 + this.expTwo.queryPlanHash(request);
        return hc;
    }
    
    public int queryBindHash() {
        int hc = this.expOne.queryBindHash();
        hc = hc * 31 + this.expTwo.queryBindHash();
        return hc;
    }
    
    static class And extends LogicExpression
    {
        private static final long serialVersionUID = -3832889676798526444L;
        
        And(final Expression expOne, final Expression expTwo) {
            super(" and ", expOne, expTwo);
        }
    }
    
    static class Or extends LogicExpression
    {
        private static final long serialVersionUID = -6871993143194094819L;
        
        Or(final Expression expOne, final Expression expTwo) {
            super(" or ", expOne, expTwo);
        }
    }
}
