// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.event.BeanQueryRequest;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebean.ExpressionList;

public interface SpiExpressionList<T> extends ExpressionList<T>
{
    boolean isLuceneResolvable(final LuceneResolvableRequest p0);
    
    SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest p0, final SpiLuceneExpr.ExprOccur p1);
    
    void trimPath(final int p0);
    
    void setExpressionFactory(final ExpressionFactory p0);
    
    void containsMany(final BeanDescriptor<?> p0, final ManyWhereJoins p1);
    
    boolean isEmpty();
    
    String buildSql(final SpiExpressionRequest p0);
    
    ArrayList<Object> buildBindValues(final SpiExpressionRequest p0);
    
    int queryPlanHash(final BeanQueryRequest<?> p0);
}
