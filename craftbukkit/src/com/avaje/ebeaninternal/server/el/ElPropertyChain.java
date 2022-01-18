// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class ElPropertyChain implements ElPropertyValue
{
    private final String prefix;
    private final String placeHolder;
    private final String placeHolderEncrypted;
    private final String name;
    private final String expression;
    private final boolean containsMany;
    private final ElPropertyValue[] chain;
    private final boolean assocId;
    private final int last;
    private final BeanProperty lastBeanProperty;
    private final ScalarType<?> scalarType;
    private final ElPropertyValue lastElPropertyValue;
    
    public ElPropertyChain(final boolean containsMany, final boolean embedded, final String expression, final ElPropertyValue[] chain) {
        this.containsMany = containsMany;
        this.chain = chain;
        this.expression = expression;
        final int dotPos = expression.lastIndexOf(46);
        if (dotPos > -1) {
            this.name = expression.substring(dotPos + 1);
            if (embedded) {
                final int embPos = expression.lastIndexOf(46, dotPos - 1);
                this.prefix = ((embPos == -1) ? null : expression.substring(0, embPos));
            }
            else {
                this.prefix = expression.substring(0, dotPos);
            }
        }
        else {
            this.prefix = null;
            this.name = expression;
        }
        this.assocId = chain[chain.length - 1].isAssocId();
        this.last = chain.length - 1;
        this.lastBeanProperty = chain[chain.length - 1].getBeanProperty();
        if (this.lastBeanProperty != null) {
            this.scalarType = this.lastBeanProperty.getScalarType();
        }
        else {
            this.scalarType = null;
        }
        this.lastElPropertyValue = chain[chain.length - 1];
        this.placeHolder = this.getElPlaceHolder(this.prefix, this.lastElPropertyValue, false);
        this.placeHolderEncrypted = this.getElPlaceHolder(this.prefix, this.lastElPropertyValue, true);
    }
    
    private String getElPlaceHolder(final String prefix, final ElPropertyValue lastElPropertyValue, final boolean encrypted) {
        if (prefix == null) {
            return lastElPropertyValue.getElPlaceholder(encrypted);
        }
        final String el = lastElPropertyValue.getElPlaceholder(encrypted);
        if (!el.contains("${}")) {
            return StringHelper.replaceString(el, "${", "${" + prefix + ".");
        }
        return StringHelper.replaceString(el, "${}", "${" + prefix + "}");
    }
    
    public boolean isDeployOnly() {
        return false;
    }
    
    public boolean containsManySince(final String sinceProperty) {
        if (sinceProperty == null) {
            return this.containsMany;
        }
        if (!this.expression.startsWith(sinceProperty)) {
            return this.containsMany;
        }
        for (int i = 1 + SplitName.count('.', sinceProperty); i < this.chain.length; ++i) {
            if (this.chain[i].getBeanProperty().containsMany()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsMany() {
        return this.containsMany;
    }
    
    public String getElPrefix() {
        return this.prefix;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getElName() {
        return this.expression;
    }
    
    public String getElPlaceholder(final boolean encrypted) {
        return encrypted ? this.placeHolderEncrypted : this.placeHolder;
    }
    
    public boolean isDbEncrypted() {
        return this.lastElPropertyValue.isDbEncrypted();
    }
    
    public boolean isLocalEncrypted() {
        return this.lastElPropertyValue.isLocalEncrypted();
    }
    
    public Object[] getAssocOneIdValues(final Object bean) {
        return this.lastElPropertyValue.getAssocOneIdValues(bean);
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        return this.lastElPropertyValue.getAssocOneIdExpr(this.expression, operator);
    }
    
    public String getAssocIdInExpr(final String prefix) {
        return this.lastElPropertyValue.getAssocIdInExpr(prefix);
    }
    
    public String getAssocIdInValueExpr(final int size) {
        return this.lastElPropertyValue.getAssocIdInValueExpr(size);
    }
    
    public int getDeployOrder() {
        int i = this.lastBeanProperty.getDeployOrder();
        for (int max = this.chain.length - 1, j = 0; j < max; ++j) {
            final int xtra = (max - j) * 1000 * this.chain[j].getDeployOrder();
            i += xtra;
        }
        return i;
    }
    
    public boolean isAssocId() {
        return this.assocId;
    }
    
    public boolean isAssocProperty() {
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].isAssocProperty()) {
                return true;
            }
        }
        return false;
    }
    
    public String getDbColumn() {
        return this.lastElPropertyValue.getDbColumn();
    }
    
    public BeanProperty getBeanProperty() {
        return this.lastBeanProperty;
    }
    
    public boolean isDateTimeCapable() {
        return this.scalarType != null && this.scalarType.isDateTimeCapable();
    }
    
    public int getJdbcType() {
        return (this.scalarType == null) ? 0 : this.scalarType.getJdbcType();
    }
    
    public Object parseDateTime(final long systemTimeMillis) {
        return this.scalarType.parseDateTime(systemTimeMillis);
    }
    
    public StringParser getStringParser() {
        return this.scalarType;
    }
    
    public StringFormatter getStringFormatter() {
        return this.scalarType;
    }
    
    public Object elConvertType(final Object value) {
        return this.lastElPropertyValue.elConvertType(value);
    }
    
    public Object elGetValue(Object bean) {
        for (int i = 0; i < this.chain.length; ++i) {
            bean = this.chain[i].elGetValue(bean);
            if (bean == null) {
                return null;
            }
        }
        return bean;
    }
    
    public Object elGetReference(Object bean) {
        Object prevBean = bean;
        for (int i = 0; i < this.last; ++i) {
            prevBean = this.chain[i].elGetReference(prevBean);
        }
        bean = this.chain[this.last].elGetValue(prevBean);
        return bean;
    }
    
    public void elSetLoaded(Object bean) {
        for (int i = 0; i < this.last; ++i) {
            bean = this.chain[i].elGetValue(bean);
            if (bean == null) {
                break;
            }
        }
        if (bean != null) {
            ((EntityBean)bean)._ebean_getIntercept().setLoaded();
        }
    }
    
    public void elSetReference(Object bean) {
        for (int i = 0; i < this.last; ++i) {
            bean = this.chain[i].elGetValue(bean);
            if (bean == null) {
                break;
            }
        }
        if (bean != null) {
            ((EntityBean)bean)._ebean_getIntercept().setReference();
        }
    }
    
    public void elSetValue(final Object bean, final Object value, final boolean populate, final boolean reference) {
        Object prevBean = bean;
        if (populate) {
            for (int i = 0; i < this.last; ++i) {
                prevBean = this.chain[i].elGetReference(prevBean);
            }
        }
        else {
            for (int i = 0; i < this.last; ++i) {
                prevBean = this.chain[i].elGetValue(prevBean);
                if (prevBean == null) {
                    break;
                }
            }
        }
        if (prevBean != null) {
            if (this.lastBeanProperty != null) {
                this.lastBeanProperty.setValueIntercept(prevBean, value);
                if (reference) {
                    ((EntityBean)prevBean)._ebean_getIntercept().setReference();
                }
            }
            else {
                this.lastElPropertyValue.elSetValue(prevBean, value, populate, reference);
            }
        }
    }
}
