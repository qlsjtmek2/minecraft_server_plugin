// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import java.util.Set;

public class SqlTreeProperties
{
    Set<String> includedProps;
    boolean readOnly;
    boolean includeId;
    TableJoin[] tableJoins;
    List<BeanProperty> propsList;
    LinkedHashSet<String> propNames;
    
    public SqlTreeProperties() {
        this.includeId = true;
        this.tableJoins = new TableJoin[0];
        this.propsList = new ArrayList<BeanProperty>();
        this.propNames = new LinkedHashSet<String>();
    }
    
    public boolean containsProperty(final String propName) {
        return this.propNames.contains(propName);
    }
    
    public void add(final BeanProperty[] props) {
        for (final BeanProperty beanProperty : props) {
            this.propsList.add(beanProperty);
        }
    }
    
    public void add(final BeanProperty prop) {
        this.propsList.add(prop);
        this.propNames.add(prop.getName());
    }
    
    public BeanProperty[] getProps() {
        return this.propsList.toArray(new BeanProperty[this.propsList.size()]);
    }
    
    public boolean isIncludeId() {
        return this.includeId;
    }
    
    public void setIncludeId(final boolean includeId) {
        this.includeId = includeId;
    }
    
    public boolean isPartialObject() {
        return this.includedProps != null;
    }
    
    public Set<String> getIncludedProperties() {
        return this.includedProps;
    }
    
    public void setIncludedProperties(final Set<String> includedProps) {
        this.includedProps = includedProps;
    }
    
    public boolean isReadOnly() {
        return this.readOnly;
    }
    
    public void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
    }
    
    public TableJoin[] getTableJoins() {
        return this.tableJoins;
    }
    
    public void setTableJoins(final TableJoin[] tableJoins) {
        this.tableJoins = tableJoins;
    }
}
