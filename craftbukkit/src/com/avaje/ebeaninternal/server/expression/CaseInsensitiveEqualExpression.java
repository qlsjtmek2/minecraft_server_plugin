// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class CaseInsensitiveEqualExpression extends AbstractExpression implements LuceneAwareExpression
{
    private static final long serialVersionUID = -6406036750998971064L;
    private final String value;
    
    CaseInsensitiveEqualExpression(final FilterExprPath pathPrefix, final String propertyName, final String value) {
        super(pathPrefix, propertyName);
        this.value = value.toLowerCase();
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        return true;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        final String propertyName = this.getPropertyName();
        return new CaseInsensitiveEqualExpressionLucene().createLuceneExpr(request, propertyName, this.value);
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            final String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
            request.addBindValue(encryptKey);
        }
        request.addBindValue(this.value);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        String pname;
        final String propertyName = pname = this.getPropertyName();
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            pname = prop.getBeanProperty().getDecryptProperty(propertyName);
        }
        request.append("lower(").append(pname).append(") =? ");
    }
    
    public int queryAutoFetchHash() {
        int hc = CaseInsensitiveEqualExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.value.hashCode();
    }
}
