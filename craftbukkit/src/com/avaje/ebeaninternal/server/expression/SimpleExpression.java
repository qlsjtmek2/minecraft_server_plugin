// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class SimpleExpression extends AbstractExpression implements LuceneAwareExpression
{
    private static final long serialVersionUID = -382881395755603790L;
    private final Op type;
    private final Object value;
    
    public SimpleExpression(final FilterExprPath pathPrefix, final String propertyName, final Op type, final Object value) {
        super(pathPrefix, propertyName);
        this.type = type;
        this.value = value;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        final String propertyName = this.getPropertyName();
        if (!req.indexContains(propertyName)) {
            return false;
        }
        final ElPropertyValue prop = req.getBeanDescriptor().getElGetValue(propertyName);
        if (prop == null) {
            return false;
        }
        final BeanProperty beanProperty = prop.getBeanProperty();
        final ScalarType<?> scalarType = beanProperty.getScalarType();
        if (scalarType == null) {
            return false;
        }
        final int luceneType = scalarType.getLuceneType();
        if (7 == luceneType) {
            return false;
        }
        if (0 == luceneType) {
            return Op.EQ.equals(this.type) || Op.NOT_EQ.equals(this.type);
        }
        return !Op.NOT_EQ.equals(this.type);
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        final String propertyName = this.getPropertyName();
        final ElPropertyValue prop = this.getElProp(request);
        return new SimpleExpressionLucene().addLuceneQuery(request, this.type, propertyName, this.value, prop);
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null) {
            if (prop.isAssocId()) {
                final Object[] ids = prop.getAssocOneIdValues(this.value);
                if (ids != null) {
                    for (int i = 0; i < ids.length; ++i) {
                        request.addBindValue(ids[i]);
                    }
                }
                return;
            }
            if (prop.isDbEncrypted()) {
                final String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
                request.addBindValue(encryptKey);
            }
            else if (prop.isLocalEncrypted()) {}
        }
        request.addBindValue(this.value);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final String propertyName = this.getPropertyName();
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null) {
            if (prop.isAssocId()) {
                request.append(prop.getAssocOneIdExpr(propertyName, this.type.bind()));
                return;
            }
            if (prop.isDbEncrypted()) {
                final String dsql = prop.getBeanProperty().getDecryptSql();
                request.append(dsql).append(this.type.bind());
                return;
            }
        }
        request.append(propertyName).append(this.type.bind());
    }
    
    public int queryAutoFetchHash() {
        int hc = SimpleExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        hc = hc * 31 + this.type.name().hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.value.hashCode();
    }
    
    enum Op
    {
        EQ(" = ? ", " = "), 
        NOT_EQ(" <> ? ", " <> "), 
        LT(" < ? ", " < "), 
        LT_EQ(" <= ? ", " <= "), 
        GT(" > ? ", " > "), 
        GT_EQ(" >= ? ", " >= ");
        
        String exp;
        String shortDesc;
        
        private Op(final String exp, final String shortDesc) {
            this.exp = exp;
            this.shortDesc = shortDesc;
        }
        
        public String bind() {
            return this.exp;
        }
        
        public String shortDesc() {
            return this.shortDesc;
        }
    }
}
