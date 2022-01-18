// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public class MatchingNamingConvention extends AbstractNamingConvention
{
    public MatchingNamingConvention() {
    }
    
    public MatchingNamingConvention(final String sequenceFormat) {
        super(sequenceFormat);
    }
    
    public String getColumnFromProperty(final Class<?> beanClass, final String propertyName) {
        return propertyName;
    }
    
    public TableName getTableNameByConvention(final Class<?> beanClass) {
        return new TableName(this.getCatalog(), this.getSchema(), beanClass.getSimpleName());
    }
    
    public String getPropertyFromColumn(final Class<?> beanClass, final String dbColumnName) {
        return dbColumnName;
    }
}
