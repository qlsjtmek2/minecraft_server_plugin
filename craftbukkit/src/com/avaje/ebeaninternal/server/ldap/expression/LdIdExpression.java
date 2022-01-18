// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.api.SpiExpression;

class LdIdExpression extends LdAbstractExpression implements SpiExpression
{
    private static final long serialVersionUID = -3065936341718489843L;
    private final Object value;
    
    LdIdExpression(final Object value) {
        super(null);
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
        final BeanProperty[] propertiesId = r.getBeanDescriptor().propertiesId();
        if (propertiesId.length > 1) {
            throw new RuntimeException("Only single Id property is supported for LDAP");
        }
        request.addBindValue(this.value);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        final BeanProperty[] propertiesId = r.getBeanDescriptor().propertiesId();
        if (propertiesId.length > 1) {
            throw new RuntimeException("Only single Id property is supported for LDAP");
        }
        final String ldapProp = propertiesId[0].getDbColumn();
        request.append(ldapProp).append("=").append(this.nextParam(request));
    }
    
    public int queryAutoFetchHash() {
        return LdIdExpression.class.getName().hashCode();
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.value.hashCode();
    }
}
