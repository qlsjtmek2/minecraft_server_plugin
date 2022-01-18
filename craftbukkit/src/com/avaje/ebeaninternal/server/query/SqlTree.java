// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.ArrayList;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.Set;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public class SqlTree
{
    private SqlTreeNode rootNode;
    private BeanPropertyAssocMany<?> manyProperty;
    private String manyPropertyName;
    private ElPropertyValue manyPropEl;
    private Set<String> includes;
    private String summary;
    private String selectSql;
    private String fromSql;
    private BeanProperty[] encryptedProps;
    private String inheritanceWhereSql;
    
    public List<String> buildSelectExpressionChain() {
        final ArrayList<String> list = new ArrayList<String>();
        this.rootNode.buildSelectExpressionChain(list);
        return list;
    }
    
    public Set<String> getIncludes() {
        return this.includes;
    }
    
    public void setIncludes(final Set<String> includes) {
        this.includes = includes;
    }
    
    public void setManyProperty(final BeanPropertyAssocMany<?> manyProperty, final String manyPropertyName, final ElPropertyValue manyPropEl) {
        this.manyProperty = manyProperty;
        this.manyPropertyName = manyPropertyName;
        this.manyPropEl = manyPropEl;
    }
    
    public String getSelectSql() {
        return this.selectSql;
    }
    
    public void setSelectSql(final String selectSql) {
        this.selectSql = selectSql;
    }
    
    public String getFromSql() {
        return this.fromSql;
    }
    
    public void setFromSql(final String fromSql) {
        this.fromSql = fromSql;
    }
    
    public String getInheritanceWhereSql() {
        return this.inheritanceWhereSql;
    }
    
    public void setInheritanceWhereSql(final String whereSql) {
        this.inheritanceWhereSql = whereSql;
    }
    
    public void setSummary(final String summary) {
        this.summary = summary;
    }
    
    public String getSummary() {
        return this.summary;
    }
    
    public SqlTreeNode getRootNode() {
        return this.rootNode;
    }
    
    public void setRootNode(final SqlTreeNode rootNode) {
        this.rootNode = rootNode;
    }
    
    public BeanPropertyAssocMany<?> getManyProperty() {
        return this.manyProperty;
    }
    
    public String getManyPropertyName() {
        return this.manyPropertyName;
    }
    
    public ElPropertyValue getManyPropertyEl() {
        return this.manyPropEl;
    }
    
    public boolean isManyIncluded() {
        return this.manyProperty != null;
    }
    
    public BeanProperty[] getEncryptedProps() {
        return this.encryptedProps;
    }
    
    public void setEncryptedProps(final BeanProperty[] encryptedProps) {
        this.encryptedProps = encryptedProps;
    }
}
