// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import java.util.List;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import java.util.Iterator;
import java.util.ArrayList;

public class DeployInheritInfo
{
    private static final String JPA_DEFAULT_DISCRIM_COLUMN = "dtype";
    private int discriminatorLength;
    private int discriminatorType;
    private String discriminatorStringValue;
    private Object discriminatorObjectValue;
    private String discriminatorColumn;
    private String discriminatorWhere;
    private Class<?> type;
    private Class<?> parent;
    private ArrayList<DeployInheritInfo> children;
    
    public DeployInheritInfo(final Class<?> type) {
        this.children = new ArrayList<DeployInheritInfo>();
        this.type = type;
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public Class<?> getParent() {
        return this.parent;
    }
    
    public void setParent(final Class<?> parent) {
        this.parent = parent;
    }
    
    public boolean isAbstract() {
        return this.discriminatorObjectValue == null;
    }
    
    public boolean isRoot() {
        return this.parent == null;
    }
    
    public Iterator<DeployInheritInfo> children() {
        return this.children.iterator();
    }
    
    public void addChild(final DeployInheritInfo childInfo) {
        this.children.add(childInfo);
    }
    
    public String getDiscriminatorWhere() {
        return this.discriminatorWhere;
    }
    
    public void setDiscriminatorWhere(final String discriminatorWhere) {
        this.discriminatorWhere = discriminatorWhere;
    }
    
    public String getDiscriminatorColumn(final InheritInfo parent) {
        if (this.discriminatorColumn == null) {
            if (parent == null) {
                this.discriminatorColumn = "dtype";
            }
            else {
                this.discriminatorColumn = parent.getDiscriminatorColumn();
            }
        }
        return this.discriminatorColumn;
    }
    
    public void setDiscriminatorColumn(final String discriminatorColumn) {
        this.discriminatorColumn = discriminatorColumn;
    }
    
    public int getDiscriminatorLength(final InheritInfo parent) {
        if (this.discriminatorLength == 0) {
            if (parent == null) {
                this.discriminatorLength = 10;
            }
            else {
                this.discriminatorLength = parent.getDiscriminatorLength();
            }
        }
        return this.discriminatorLength;
    }
    
    public int getDiscriminatorType(final InheritInfo parent) {
        if (this.discriminatorType == 0) {
            if (parent == null) {
                this.discriminatorType = 12;
            }
            else {
                this.discriminatorType = parent.getDiscriminatorType();
            }
        }
        return this.discriminatorType;
    }
    
    public void setDiscriminatorType(final int discriminatorType) {
        this.discriminatorType = discriminatorType;
    }
    
    public int getDiscriminatorLength() {
        return this.discriminatorLength;
    }
    
    public void setDiscriminatorLength(final int discriminatorLength) {
        this.discriminatorLength = discriminatorLength;
    }
    
    public Object getDiscriminatorObjectValue() {
        return this.discriminatorObjectValue;
    }
    
    public String getDiscriminatorStringValue() {
        return this.discriminatorStringValue;
    }
    
    public void setDiscriminatorValue(String value) {
        if (value != null) {
            value = value.trim();
            if (value.length() == 0) {
                value = null;
            }
            else {
                this.discriminatorStringValue = value;
                if (this.discriminatorType == 4) {
                    this.discriminatorObjectValue = Integer.valueOf(value.toString());
                }
                else {
                    this.discriminatorObjectValue = value;
                }
            }
        }
    }
    
    public String getWhere() {
        final List<Object> discList = new ArrayList<Object>();
        this.appendDiscriminator(discList);
        return this.buildWhereLiteral(discList);
    }
    
    private void appendDiscriminator(final List<Object> list) {
        if (this.discriminatorObjectValue != null) {
            list.add(this.discriminatorObjectValue);
        }
        for (final DeployInheritInfo child : this.children) {
            child.appendDiscriminator(list);
        }
    }
    
    private String buildWhereLiteral(final List<Object> discList) {
        final int size = discList.size();
        if (size == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(this.discriminatorColumn);
        if (size == 1) {
            sb.append(" = ");
        }
        else {
            sb.append(" in (");
        }
        for (int i = 0; i < discList.size(); ++i) {
            this.appendSqlLiteralValue(i, discList.get(i), sb);
        }
        if (size > 1) {
            sb.append(")");
        }
        return sb.toString();
    }
    
    private void appendSqlLiteralValue(final int count, final Object value, final StringBuilder sb) {
        if (count > 0) {
            sb.append(",");
        }
        if (value instanceof String) {
            sb.append("'").append(value).append("'");
        }
        else {
            sb.append(value);
        }
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("InheritInfo[").append(this.type.getName()).append("]");
        sb.append(" root[").append(this.parent.getName()).append("]");
        sb.append(" disValue[").append(this.discriminatorStringValue).append("]");
        return sb.toString();
    }
}
