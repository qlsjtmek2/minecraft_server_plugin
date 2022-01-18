// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.Collection;

class InExpression extends AbstractExpression
{
    private static final long serialVersionUID = 3150665801693551260L;
    private final Object[] values;
    
    InExpression(final FilterExprPath pathPrefix, final String propertyName, final Collection<?> coll) {
        super(pathPrefix, propertyName);
        this.values = coll.toArray(new Object[coll.size()]);
    }
    
    InExpression(final FilterExprPath pathPrefix, final String propertyName, final Object[] array) {
        super(pathPrefix, propertyName);
        this.values = array;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && !prop.isAssocId()) {
            prop = null;
        }
        for (int i = 0; i < this.values.length; ++i) {
            if (prop == null) {
                request.addBindValue(this.values[i]);
            }
            else {
                final Object[] ids = prop.getAssocOneIdValues(this.values[i]);
                if (ids != null) {
                    for (int j = 0; j < ids.length; ++j) {
                        request.addBindValue(ids[j]);
                    }
                }
            }
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        if (this.values.length == 0) {
            request.append("1=0");
            return;
        }
        final String propertyName = this.getPropertyName();
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && !prop.isAssocId()) {
            prop = null;
        }
        if (prop != null) {
            request.append(prop.getAssocIdInExpr(propertyName));
            final String inClause = prop.getAssocIdInValueExpr(this.values.length);
            request.append(inClause);
        }
        else {
            request.append(propertyName);
            request.append(" in (?");
            for (int i = 1; i < this.values.length; ++i) {
                request.append(", ").append("?");
            }
            request.append(" ) ");
        }
    }
    
    public int queryAutoFetchHash() {
        int hc = InExpression.class.getName().hashCode() + 31 * this.values.length;
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        int hc = 0;
        for (int i = 1; i < this.values.length; ++i) {
            hc = 31 * hc + this.values[i].hashCode();
        }
        return hc;
    }
}
