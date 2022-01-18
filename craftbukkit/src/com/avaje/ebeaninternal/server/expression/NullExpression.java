// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class NullExpression extends AbstractExpression
{
    private static final long serialVersionUID = 4246991057451128269L;
    private final boolean notNull;
    
    NullExpression(final FilterExprPath pathPrefix, final String propertyName, final boolean notNull) {
        super(pathPrefix, propertyName);
        this.notNull = notNull;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final String propertyName = this.getPropertyName();
        final String nullExpr = this.notNull ? " is not null " : " is null ";
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isAssocId()) {
            request.append(prop.getAssocOneIdExpr(propertyName, nullExpr));
            return;
        }
        request.append(propertyName).append(nullExpr);
    }
    
    public int queryAutoFetchHash() {
        int hc = NullExpression.class.getName().hashCode();
        hc = hc * 31 + (this.notNull ? 1 : 0);
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.notNull ? 1 : 0;
    }
}
