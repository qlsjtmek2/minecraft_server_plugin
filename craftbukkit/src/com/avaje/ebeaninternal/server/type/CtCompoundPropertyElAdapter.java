// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;

public class CtCompoundPropertyElAdapter implements ElPropertyValue
{
    private final CtCompoundProperty prop;
    private int deployOrder;
    
    public CtCompoundPropertyElAdapter(final CtCompoundProperty prop) {
        this.prop = prop;
    }
    
    public void setDeployOrder(final int deployOrder) {
        this.deployOrder = deployOrder;
    }
    
    public Object elConvertType(final Object value) {
        return value;
    }
    
    public Object elGetReference(final Object bean) {
        return bean;
    }
    
    public Object elGetValue(final Object bean) {
        return this.prop.getValue(bean);
    }
    
    public void elSetReference(final Object bean) {
    }
    
    public void elSetValue(final Object bean, final Object value, final boolean populate, final boolean reference) {
        this.prop.setValue(bean, value);
    }
    
    public int getDeployOrder() {
        return this.deployOrder;
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        throw new RuntimeException("Not Supported or Expected");
    }
    
    public Object[] getAssocOneIdValues(final Object bean) {
        throw new RuntimeException("Not Supported or Expected");
    }
    
    public String getAssocIdInExpr(final String prefix) {
        throw new RuntimeException("Not Supported or Expected");
    }
    
    public String getAssocIdInValueExpr(final int size) {
        throw new RuntimeException("Not Supported or Expected");
    }
    
    public BeanProperty getBeanProperty() {
        return null;
    }
    
    public StringFormatter getStringFormatter() {
        return null;
    }
    
    public StringParser getStringParser() {
        return null;
    }
    
    public boolean isDbEncrypted() {
        return false;
    }
    
    public boolean isLocalEncrypted() {
        return false;
    }
    
    public boolean isAssocId() {
        return false;
    }
    
    public boolean isAssocProperty() {
        return false;
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public int getJdbcType() {
        return 0;
    }
    
    public Object parseDateTime(final long systemTimeMillis) {
        throw new RuntimeException("Not Supported or Expected");
    }
    
    public boolean containsMany() {
        return false;
    }
    
    public boolean containsManySince(final String sinceProperty) {
        return this.containsMany();
    }
    
    public String getDbColumn() {
        return null;
    }
    
    public String getElPlaceholder(final boolean encrypted) {
        return null;
    }
    
    public String getElPrefix() {
        return null;
    }
    
    public String getName() {
        return this.prop.getPropertyName();
    }
    
    public String getElName() {
        return this.prop.getPropertyName();
    }
}
