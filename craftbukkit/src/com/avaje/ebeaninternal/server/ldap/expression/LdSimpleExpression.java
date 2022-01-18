// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

class LdSimpleExpression extends LdAbstractExpression
{
    private static final long serialVersionUID = 4091359751840929075L;
    private final Op type;
    private final Object value;
    
    public LdSimpleExpression(final String propertyName, final Op type, final Object value) {
        super(propertyName);
        this.type = type;
        this.value = value;
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
            final ScalarType<?> scalarType = prop.getBeanProperty().getScalarType();
            final Object v = scalarType.toJdbcType(this.value);
            request.addBindValue(v);
        }
        else {
            request.addBindValue(this.value);
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isAssocId()) {
            final String rawExpr = prop.getAssocOneIdExpr(this.propertyName, this.type.toString());
            final String parsed = request.parseDeploy(rawExpr);
            request.append(parsed);
            return;
        }
        final String parsed2 = request.parseDeploy(this.propertyName);
        request.append("(").append(parsed2).append("").append(this.type.toString()).append(this.nextParam(request)).append(")");
    }
    
    public int queryAutoFetchHash() {
        int hc = LdSimpleExpression.class.getName().hashCode();
        hc = hc * 31 + this.propertyName.hashCode();
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
        EQ {
            public String toString() {
                return "=";
            }
        }, 
        NOT_EQ {
            public String toString() {
                return "<>";
            }
        }, 
        LT {
            public String toString() {
                return "<";
            }
        }, 
        LT_EQ {
            public String toString() {
                return "<=";
            }
        }, 
        GT {
            public String toString() {
                return ">";
            }
        }, 
        GT_EQ {
            public String toString() {
                return ">=";
            }
        };
    }
}
