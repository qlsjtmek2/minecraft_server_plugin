// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class InvalidValue implements Serializable
{
    private static final long serialVersionUID = 2408556605456324913L;
    private static final Object[] EMPTY;
    private final String beanType;
    private final String propertyName;
    private final String validatorKey;
    private final Object value;
    private final InvalidValue[] children;
    private final Object[] validatorAttributes;
    private String message;
    
    public InvalidValue(final String validatorKey, final String beanType, final Object bean, final InvalidValue[] children) {
        this.validatorKey = validatorKey;
        this.validatorAttributes = InvalidValue.EMPTY;
        this.beanType = beanType;
        this.propertyName = null;
        this.value = bean;
        this.children = children;
    }
    
    public InvalidValue(final String validatorKey, final Object[] validatorAttributes, final String beanType, final String propertyName, final Object value) {
        this.validatorKey = validatorKey;
        this.validatorAttributes = validatorAttributes;
        this.beanType = beanType;
        this.propertyName = propertyName;
        this.value = value;
        this.children = null;
    }
    
    public String getBeanType() {
        return this.beanType;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public String getValidatorKey() {
        return this.validatorKey;
    }
    
    public Object[] getValidatorAttributes() {
        return this.validatorAttributes;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public InvalidValue[] getChildren() {
        return this.children;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public boolean isBean() {
        return !this.isError();
    }
    
    public boolean isError() {
        return this.children == null;
    }
    
    public InvalidValue[] getErrors() {
        final ArrayList<InvalidValue> list = new ArrayList<InvalidValue>();
        this.buildList(list);
        return toArray(list);
    }
    
    private void buildList(final List<InvalidValue> list) {
        if (this.isError()) {
            list.add(this);
        }
        else {
            for (int i = 0; i < this.children.length; ++i) {
                this.children[i].buildList(list);
            }
        }
    }
    
    public String toString() {
        if (this.isError()) {
            return "errorKey=" + this.validatorKey + " type=" + this.beanType + " property=" + this.propertyName + " value=" + this.value;
        }
        if (this.children.length == 1) {
            return this.children[0].toString();
        }
        final StringBuilder sb = new StringBuilder(50);
        sb.append("\r\nCHILDREN[").append(this.children.length).append("]");
        for (int i = 0; i < this.children.length; ++i) {
            sb.append(this.children[i].toString()).append(", ");
        }
        return sb.toString();
    }
    
    public static InvalidValue[] toArray(final List<InvalidValue> list) {
        return list.toArray(new InvalidValue[list.size()]);
    }
    
    public static List<InvalidValue> toList(final InvalidValue invalid) {
        final ArrayList<InvalidValue> list = new ArrayList<InvalidValue>();
        list.add(invalid);
        return list;
    }
    
    static {
        EMPTY = new Object[0];
    }
}
