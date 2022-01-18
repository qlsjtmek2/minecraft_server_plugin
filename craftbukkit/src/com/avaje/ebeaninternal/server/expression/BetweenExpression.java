// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import java.util.Set;

class BetweenExpression extends AbstractExpression
{
    private static final long serialVersionUID = 2078918165221454910L;
    private static final String BETWEEN = " between ";
    private final Object valueHigh;
    private final Object valueLow;
    
    BetweenExpression(final FilterExprPath pathPrefix, final String propertyName, final Object valLo, final Object valHigh) {
        super(pathPrefix, propertyName);
        this.valueLow = valLo;
        this.valueHigh = valHigh;
    }
    
    public boolean isLuceneResolvable(final Set<String> indexedProperties) {
        return indexedProperties.contains(this.getPropertyName());
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        return null;
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        request.addBindValue(this.valueLow);
        request.addBindValue(this.valueHigh);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        request.append(this.getPropertyName()).append(" between ").append(" ? and ? ");
    }
    
    public int queryAutoFetchHash() {
        int hc = BetweenExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        int hc = this.valueLow.hashCode();
        hc = hc * 31 + this.valueHigh.hashCode();
        return hc;
    }
}
