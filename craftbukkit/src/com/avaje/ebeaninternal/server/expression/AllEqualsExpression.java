// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import java.util.Set;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import java.util.Iterator;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.Map;
import com.avaje.ebeaninternal.api.SpiExpression;

class AllEqualsExpression implements SpiExpression
{
    private static final long serialVersionUID = -8691773558205937025L;
    private final Map<String, Object> propMap;
    private final FilterExprPath pathPrefix;
    
    AllEqualsExpression(final FilterExprPath pathPrefix, final Map<String, Object> propMap) {
        this.pathPrefix = pathPrefix;
        this.propMap = propMap;
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
        if (this.propMap != null) {
            for (final String propertyName : this.propMap.keySet()) {
                final ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.name(propertyName));
                if (elProp != null && elProp.containsMany()) {
                    manyWhereJoin.add(elProp);
                }
            }
        }
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        if (this.propMap.isEmpty()) {
            return;
        }
        for (final Object value : this.propMap.values()) {
            if (value != null) {
                request.addBindValue(value);
            }
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        if (this.propMap.isEmpty()) {
            return;
        }
        request.append("(");
        final Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
        final Iterator<Map.Entry<String, Object>> it = entries.iterator();
        int count = 0;
        while (it.hasNext()) {
            final Map.Entry<String, Object> entry = it.next();
            final Object value = entry.getValue();
            final String propName = entry.getKey();
            if (count > 0) {
                request.append("and ");
            }
            request.append(this.name(propName));
            if (value == null) {
                request.append(" is null ");
            }
            else {
                request.append(" = ? ");
            }
            ++count;
        }
        request.append(")");
    }
    
    public int queryAutoFetchHash() {
        int hc = AllEqualsExpression.class.getName().hashCode();
        final Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
        for (final Map.Entry<String, Object> entry : entries) {
            final Object value = entry.getValue();
            final String propName = entry.getKey();
            hc = hc * 31 + propName.hashCode();
            hc = hc * 31 + ((value != null) ? 1 : 0);
        }
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.queryAutoFetchHash();
    }
}
