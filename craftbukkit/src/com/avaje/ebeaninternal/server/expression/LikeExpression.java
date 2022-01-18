// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebean.LikeType;

class LikeExpression extends AbstractExpression implements LuceneAwareExpression
{
    private static final long serialVersionUID = -5398151809111172380L;
    private final String val;
    private final boolean caseInsensitive;
    private final LikeType type;
    
    LikeExpression(final FilterExprPath pathPrefix, final String propertyName, final String value, final boolean caseInsensitive, final LikeType type) {
        super(pathPrefix, propertyName);
        this.caseInsensitive = caseInsensitive;
        this.type = type;
        this.val = value;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        final String propertyName = this.getPropertyName();
        return !LikeType.ENDS_WITH.equals(this.type) && req.indexContains(propertyName);
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        final String propertyName = this.getPropertyName();
        return new LikeExpressionLucene().createLuceneExpr(request, propertyName, this.type, this.caseInsensitive, this.val);
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            final String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
            request.addBindValue(encryptKey);
        }
        final String bindValue = getValue(this.val, this.caseInsensitive, this.type);
        request.addBindValue(bindValue);
    }
    
    public void addSql(final SpiExpressionRequest request) {
        String pname;
        final String propertyName = pname = this.getPropertyName();
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            pname = prop.getBeanProperty().getDecryptProperty(propertyName);
        }
        if (this.caseInsensitive) {
            request.append("lower(").append(pname).append(")");
        }
        else {
            request.append(pname);
        }
        if (this.type.equals(LikeType.EQUAL_TO)) {
            request.append(" = ? ");
        }
        else {
            request.append(" like ? ");
        }
    }
    
    public int queryAutoFetchHash() {
        int hc = LikeExpression.class.getName().hashCode();
        hc = hc * 31 + (this.caseInsensitive ? 0 : 1);
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }
    
    public int queryBindHash() {
        return this.val.hashCode();
    }
    
    private static String getValue(String value, final boolean caseInsensitive, final LikeType type) {
        if (caseInsensitive) {
            value = value.toLowerCase();
        }
        switch (type) {
            case RAW: {
                return value;
            }
            case STARTS_WITH: {
                return value + "%";
            }
            case ENDS_WITH: {
                return "%" + value;
            }
            case CONTAINS: {
                return "%" + value + "%";
            }
            case EQUAL_TO: {
                return value;
            }
            default: {
                throw new RuntimeException("LikeType " + type + " missed?");
            }
        }
    }
}
