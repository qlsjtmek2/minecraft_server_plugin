// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.parse.DeployInheritInfo;
import java.util.HashMap;
import java.util.ArrayList;

public class InheritInfo
{
    private final String discriminatorStringValue;
    private final Object discriminatorValue;
    private final String discriminatorColumn;
    private final int discriminatorType;
    private final int discriminatorLength;
    private final String where;
    private final Class<?> type;
    private final ArrayList<InheritInfo> children;
    private final HashMap<String, InheritInfo> discMap;
    private final HashMap<String, InheritInfo> typeMap;
    private final InheritInfo parent;
    private final InheritInfo root;
    private BeanDescriptor<?> descriptor;
    
    public InheritInfo(final InheritInfo r, final InheritInfo parent, final DeployInheritInfo deploy) {
        this.children = new ArrayList<InheritInfo>();
        this.parent = parent;
        this.type = deploy.getType();
        this.discriminatorColumn = InternString.intern(deploy.getDiscriminatorColumn(parent));
        this.discriminatorValue = deploy.getDiscriminatorObjectValue();
        this.discriminatorStringValue = deploy.getDiscriminatorStringValue();
        this.discriminatorType = deploy.getDiscriminatorType(parent);
        this.discriminatorLength = deploy.getDiscriminatorLength(parent);
        this.where = InternString.intern(deploy.getWhere());
        if (r == null) {
            this.root = this;
            this.discMap = new HashMap<String, InheritInfo>();
            this.typeMap = new HashMap<String, InheritInfo>();
            this.registerWithRoot(this);
        }
        else {
            this.root = r;
            this.discMap = null;
            this.typeMap = null;
            this.root.registerWithRoot(this);
        }
    }
    
    public void visitChildren(final InheritInfoVisitor visitor) {
        for (int i = 0; i < this.children.size(); ++i) {
            final InheritInfo child = this.children.get(i);
            visitor.visit(child);
            child.visitChildren(visitor);
        }
    }
    
    public boolean isSaveRecurseSkippable() {
        return this.root.isNodeSaveRecurseSkippable();
    }
    
    private boolean isNodeSaveRecurseSkippable() {
        if (!this.descriptor.isSaveRecurseSkippable()) {
            return false;
        }
        for (int i = 0; i < this.children.size(); ++i) {
            final InheritInfo child = this.children.get(i);
            if (!child.isNodeSaveRecurseSkippable()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isDeleteRecurseSkippable() {
        return this.root.isNodeDeleteRecurseSkippable();
    }
    
    private boolean isNodeDeleteRecurseSkippable() {
        if (!this.descriptor.isDeleteRecurseSkippable()) {
            return false;
        }
        for (int i = 0; i < this.children.size(); ++i) {
            final InheritInfo child = this.children.get(i);
            if (!child.isNodeDeleteRecurseSkippable()) {
                return false;
            }
        }
        return true;
    }
    
    public void setDescriptor(final BeanDescriptor<?> descriptor) {
        this.descriptor = descriptor;
    }
    
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.descriptor;
    }
    
    public BeanProperty findSubTypeProperty(final String propertyName) {
        BeanProperty prop = null;
        for (int i = 0, x = this.children.size(); i < x; ++i) {
            final InheritInfo childInfo = this.children.get(i);
            prop = childInfo.getBeanDescriptor().findBeanProperty(propertyName);
            if (prop != null) {
                return prop;
            }
        }
        return null;
    }
    
    public void addChildrenProperties(final SqlTreeProperties selectProps) {
        for (int i = 0, x = this.children.size(); i < x; ++i) {
            final InheritInfo childInfo = this.children.get(i);
            selectProps.add(childInfo.descriptor.propertiesLocal());
            childInfo.addChildrenProperties(selectProps);
        }
    }
    
    public InheritInfo readType(final DbReadContext ctx) throws SQLException {
        final String discValue = ctx.getDataReader().getString();
        return this.readType(discValue);
    }
    
    public InheritInfo readType(final String discValue) {
        if (discValue == null) {
            return null;
        }
        final InheritInfo typeInfo = this.root.getType(discValue);
        if (typeInfo == null) {
            final String m = "Inheritance type for discriminator value [" + discValue + "] was not found?";
            throw new PersistenceException(m);
        }
        return typeInfo;
    }
    
    public InheritInfo readType(final Class<?> beanType) {
        final InheritInfo typeInfo = this.root.getTypeByClass(beanType);
        if (typeInfo == null) {
            final String m = "Inheritance type for bean type [" + beanType.getName() + "] was not found?";
            throw new PersistenceException(m);
        }
        return typeInfo;
    }
    
    public Object createBean(final boolean vanillaMode) {
        return this.descriptor.createBean(vanillaMode);
    }
    
    public IdBinder getIdBinder() {
        return this.descriptor.getIdBinder();
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public InheritInfo getRoot() {
        return this.root;
    }
    
    public InheritInfo getParent() {
        return this.parent;
    }
    
    public boolean isAbstract() {
        return this.discriminatorValue == null;
    }
    
    public boolean isRoot() {
        return this.parent == null;
    }
    
    public InheritInfo getType(final String discValue) {
        return this.discMap.get(discValue);
    }
    
    private InheritInfo getTypeByClass(final Class<?> beanType) {
        final String clsName = SubClassUtil.getSuperClassName(beanType.getName());
        return this.typeMap.get(clsName);
    }
    
    private void registerWithRoot(final InheritInfo info) {
        if (info.getDiscriminatorStringValue() != null) {
            final String stringDiscValue = info.getDiscriminatorStringValue();
            this.discMap.put(stringDiscValue, info);
        }
        final String clsName = SubClassUtil.getSuperClassName(info.getType().getName());
        this.typeMap.put(clsName, info);
    }
    
    public void addChild(final InheritInfo childInfo) {
        this.children.add(childInfo);
    }
    
    public String getWhere() {
        return this.where;
    }
    
    public String getDiscriminatorColumn() {
        return this.discriminatorColumn;
    }
    
    public int getDiscriminatorType() {
        return this.discriminatorType;
    }
    
    public int getDiscriminatorLength() {
        return this.discriminatorLength;
    }
    
    public String getDiscriminatorStringValue() {
        return this.discriminatorStringValue;
    }
    
    public Object getDiscriminatorValue() {
        return this.discriminatorValue;
    }
    
    public String toString() {
        return "InheritInfo[" + this.type.getName() + "] disc[" + this.discriminatorStringValue + "]";
    }
}
