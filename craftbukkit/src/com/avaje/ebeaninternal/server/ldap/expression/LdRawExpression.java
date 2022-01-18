// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.api.SpiExpression;

class LdRawExpression implements SpiExpression
{
    private static final long serialVersionUID = 7973903141340334607L;
    private final String rawExpr;
    private final Object[] values;
    
    LdRawExpression(final String rawExpr, final Object[] values) {
        this.rawExpr = rawExpr;
        this.values = values;
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
        if (this.values != null) {
            for (int i = 0; i < this.values.length; ++i) {
                request.addBindValue(this.values[i]);
            }
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        request.append(this.rawExpr);
    }
    
    public int queryAutoFetchHash() {
        int hc = LdRawExpression.class.getName().hashCode();
        hc = hc * 31 + this.rawExpr.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.rawExpr.hashCode();
    }
}
