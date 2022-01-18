// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.ArrayList;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebeaninternal.api.SpiExpression;

public class DefaultExampleExpression implements SpiExpression, ExampleExpression
{
    private static final long serialVersionUID = 1L;
    private final Object entity;
    private boolean caseInsensitive;
    private LikeType likeType;
    private boolean includeZeros;
    private ArrayList<SpiExpression> list;
    private final FilterExprPath pathPrefix;
    
    public DefaultExampleExpression(final FilterExprPath pathPrefix, final Object entity, final boolean caseInsensitive, final LikeType likeType) {
        this.pathPrefix = pathPrefix;
        this.entity = entity;
        this.caseInsensitive = caseInsensitive;
        this.likeType = likeType;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return false;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins whereManyJoins) {
        if (this.list != null) {
            for (int i = 0; i < this.list.size(); ++i) {
                this.list.get(i).containsMany(desc, whereManyJoins);
            }
        }
    }
    
    public ExampleExpression includeZeros() {
        this.includeZeros = true;
        return this;
    }
    
    public ExampleExpression caseInsensitive() {
        this.caseInsensitive = true;
        return this;
    }
    
    public ExampleExpression useStartsWith() {
        this.likeType = LikeType.STARTS_WITH;
        return this;
    }
    
    public ExampleExpression useContains() {
        this.likeType = LikeType.CONTAINS;
        return this;
    }
    
    public ExampleExpression useEndsWith() {
        this.likeType = LikeType.ENDS_WITH;
        return this;
    }
    
    public ExampleExpression useEqualTo() {
        this.likeType = LikeType.EQUAL_TO;
        return this;
    }
    
    public String getPropertyName() {
        return null;
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        for (int i = 0; i < this.list.size(); ++i) {
            final SpiExpression item = this.list.get(i);
            item.addBindValues(request);
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        if (!this.list.isEmpty()) {
            request.append("(");
            for (int i = 0; i < this.list.size(); ++i) {
                final SpiExpression item = this.list.get(i);
                if (i > 0) {
                    request.append(" and ");
                }
                item.addSql(request);
            }
            request.append(") ");
        }
    }
    
    public int queryAutoFetchHash() {
        return DefaultExampleExpression.class.getName().hashCode();
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        this.list = this.buildExpressions(request);
        int hc = DefaultExampleExpression.class.getName().hashCode();
        for (int i = 0; i < this.list.size(); ++i) {
            hc = hc * 31 + this.list.get(i).queryPlanHash(request);
        }
        return hc;
    }
    
    public int queryBindHash() {
        int hc = DefaultExampleExpression.class.getName().hashCode();
        for (int i = 0; i < this.list.size(); ++i) {
            hc = hc * 31 + this.list.get(i).queryBindHash();
        }
        return hc;
    }
    
    private ArrayList<SpiExpression> buildExpressions(final BeanQueryRequest<?> request) {
        final ArrayList<SpiExpression> list = new ArrayList<SpiExpression>();
        final OrmQueryRequest<?> r = (OrmQueryRequest<?>)(OrmQueryRequest)request;
        final BeanDescriptor<?> beanDescriptor = r.getBeanDescriptor();
        final Iterator<BeanProperty> propIter = beanDescriptor.propertiesAll();
        while (propIter.hasNext()) {
            final BeanProperty beanProperty = propIter.next();
            final String propName = beanProperty.getName();
            final Object value = beanProperty.getValue(this.entity);
            if (beanProperty.isScalar() && value != null) {
                if (value instanceof String) {
                    list.add(new LikeExpression(this.pathPrefix, propName, (String)value, this.caseInsensitive, this.likeType));
                }
                else {
                    if (!this.includeZeros && this.isZero(value)) {
                        continue;
                    }
                    list.add(new SimpleExpression(this.pathPrefix, propName, SimpleExpression.Op.EQ, value));
                }
            }
        }
        return list;
    }
    
    private boolean isZero(final Object value) {
        if (value instanceof Number) {
            final Number num = (Number)value;
            final double doubleValue = num.doubleValue();
            if (doubleValue == 0.0) {
                return true;
            }
        }
        return false;
    }
}
