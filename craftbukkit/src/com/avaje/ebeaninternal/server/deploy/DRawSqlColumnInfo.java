// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

public class DRawSqlColumnInfo
{
    final String name;
    final String label;
    final String propertyName;
    final boolean scalarProperty;
    
    public DRawSqlColumnInfo(final String name, final String label, final String propertyName, final boolean scalarProperty) {
        this.name = name;
        this.label = label;
        this.propertyName = propertyName;
        this.scalarProperty = scalarProperty;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public boolean isScalarProperty() {
        return this.scalarProperty;
    }
    
    public String toString() {
        return "name:" + this.name + " label:" + this.label + " prop:" + this.propertyName;
    }
}
