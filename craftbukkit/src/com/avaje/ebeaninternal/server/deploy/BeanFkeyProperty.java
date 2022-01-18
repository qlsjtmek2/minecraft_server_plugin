// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;

public final class BeanFkeyProperty implements ElPropertyValue
{
    private final String placeHolder;
    private final String prefix;
    private final String name;
    private final String dbColumn;
    private int deployOrder;
    
    public BeanFkeyProperty(final String prefix, final String name, final String dbColumn, final int deployOrder) {
        this.prefix = prefix;
        this.name = name;
        this.dbColumn = dbColumn;
        this.deployOrder = deployOrder;
        this.placeHolder = this.calcPlaceHolder(prefix, dbColumn);
    }
    
    public int getDeployOrder() {
        return this.deployOrder;
    }
    
    private String calcPlaceHolder(final String prefix, final String dbColumn) {
        if (prefix != null) {
            return "${" + prefix + "}" + dbColumn;
        }
        return "${}" + dbColumn;
    }
    
    public BeanFkeyProperty create(final String expression) {
        final int len = expression.length() - this.name.length() - 1;
        final String prefix = expression.substring(0, len);
        return new BeanFkeyProperty(prefix, this.name, this.dbColumn, this.deployOrder);
    }
    
    public boolean isDbEncrypted() {
        return false;
    }
    
    public boolean isLocalEncrypted() {
        return false;
    }
    
    public boolean isDeployOnly() {
        return true;
    }
    
    public boolean containsMany() {
        return false;
    }
    
    public boolean containsManySince(final String sinceProperty) {
        return this.containsMany();
    }
    
    public String getDbColumn() {
        return this.dbColumn;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getElName() {
        return this.name;
    }
    
    public Object[] getAssocOneIdValues(final Object value) {
        return null;
    }
    
    public String getAssocOneIdExpr(final String prefix, final String operator) {
        return null;
    }
    
    public String getAssocIdInExpr(final String prefix) {
        return null;
    }
    
    public String getAssocIdInValueExpr(final int size) {
        return null;
    }
    
    public boolean isAssocId() {
        return false;
    }
    
    public boolean isAssocProperty() {
        return false;
    }
    
    public String getElPlaceholder(final boolean encrypted) {
        return this.placeHolder;
    }
    
    public String getElPrefix() {
        return this.prefix;
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public int getJdbcType() {
        return 0;
    }
    
    public Object parseDateTime(final long systemTimeMillis) {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public StringFormatter getStringFormatter() {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public StringParser getStringParser() {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public void elSetReference(final Object bean) {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public Object elConvertType(final Object value) {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public void elSetValue(final Object bean, final Object value, final boolean populate, final boolean reference) {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public Object elGetValue(final Object bean) {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public Object elGetReference(final Object bean) {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public BeanProperty getBeanProperty() {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
    
    public String getDeployProperty() {
        throw new RuntimeException("ElPropertyDeploy only - not implemented");
    }
}
