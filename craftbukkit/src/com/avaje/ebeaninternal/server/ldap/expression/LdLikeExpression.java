// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class LdLikeExpression extends LdAbstractExpression
{
    private static final long serialVersionUID = 4091359751840929076L;
    private final String value;
    
    public LdLikeExpression(final String propertyName, final String value) {
        super(propertyName);
        this.value = value;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
    }
    
    public void addSql(final SpiExpressionRequest request) {
        String escapedValue;
        if (this.value == null) {
            escapedValue = "*";
        }
        else {
            escapedValue = LdEscape.forLike(this.value);
        }
        final String parsed = request.parseDeploy(this.propertyName);
        request.append("(").append(parsed).append("=").append(escapedValue).append(")");
    }
    
    public int queryAutoFetchHash() {
        int hc = LdLikeExpression.class.getName().hashCode();
        hc = hc * 31 + this.propertyName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.value.hashCode();
    }
}
