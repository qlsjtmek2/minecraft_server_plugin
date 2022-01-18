// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebean.Expression;

public interface SpiExpression extends Expression
{
    boolean isLuceneResolvable(final LuceneResolvableRequest p0);
    
    SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest p0);
    
    void containsMany(final BeanDescriptor<?> p0, final ManyWhereJoins p1);
    
    int queryAutoFetchHash();
    
    int queryPlanHash(final BeanQueryRequest<?> p0);
    
    int queryBindHash();
    
    void addSql(final SpiExpressionRequest p0);
    
    void addBindValues(final SpiExpressionRequest p0);
}
