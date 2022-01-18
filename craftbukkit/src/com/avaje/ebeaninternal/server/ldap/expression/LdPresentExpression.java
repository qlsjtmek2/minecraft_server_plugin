// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class LdPresentExpression extends LdAbstractExpression
{
    private static final long serialVersionUID = -4221300142054382003L;
    
    public LdPresentExpression(final String propertyName) {
        super(propertyName);
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
        final String parsed = request.parseDeploy(this.propertyName);
        request.append("(").append(parsed).append("=*").append(")");
    }
    
    public int queryAutoFetchHash() {
        int hc = LdPresentExpression.class.getName().hashCode();
        hc = hc * 31 + this.propertyName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return 1;
    }
}
