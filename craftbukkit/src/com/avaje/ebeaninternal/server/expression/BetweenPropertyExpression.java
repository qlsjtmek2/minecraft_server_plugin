// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.api.SpiExpression;

class BetweenPropertyExpression implements SpiExpression
{
    private static final long serialVersionUID = 2078918165221454910L;
    private static final String BETWEEN = " between ";
    private final FilterExprPath pathPrefix;
    private final String lowProperty;
    private final String highProperty;
    private final Object value;
    
    BetweenPropertyExpression(final FilterExprPath pathPrefix, final String lowProperty, final String highProperty, final Object value) {
        this.pathPrefix = pathPrefix;
        this.lowProperty = lowProperty;
        this.highProperty = highProperty;
        this.value = value;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    protected String name(final String propName) {
        if (this.pathPrefix == null) {
            return propName;
        }
        final String path = this.pathPrefix.getPath();
        if (path == null || path.length() == 0) {
            return propName;
        }
        return path + "." + propName;
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
        ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.name(this.lowProperty));
        if (elProp != null && elProp.containsMany()) {
            manyWhereJoin.add(elProp);
        }
        elProp = desc.getElPropertyDeploy(this.name(this.highProperty));
        if (elProp != null && elProp.containsMany()) {
            manyWhereJoin.add(elProp);
        }
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        request.addBindValue(this.value);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        request.append(" ? ").append(" between ").append(this.name(this.lowProperty)).append(" and ").append(this.name(this.highProperty));
    }
    
    public int queryAutoFetchHash() {
        int hc = BetweenPropertyExpression.class.getName().hashCode();
        hc = hc * 31 + this.lowProperty.hashCode();
        hc = hc * 31 + this.highProperty.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.value.hashCode();
    }
}
