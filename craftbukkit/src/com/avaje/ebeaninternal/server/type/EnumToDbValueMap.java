// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

public abstract class EnumToDbValueMap<T>
{
    final LinkedHashMap<Object, T> keyMap;
    final LinkedHashMap<T, Object> valueMap;
    final boolean allowNulls;
    final boolean isIntegerType;
    
    public static EnumToDbValueMap<?> create(final boolean integerType) {
        return (EnumToDbValueMap<?>)(integerType ? new EnumToDbIntegerMap() : new EnumToDbStringMap());
    }
    
    public EnumToDbValueMap() {
        this(false, false);
    }
    
    public EnumToDbValueMap(final boolean allowNulls, final boolean isIntegerType) {
        this.allowNulls = allowNulls;
        this.isIntegerType = isIntegerType;
        this.keyMap = new LinkedHashMap<Object, T>();
        this.valueMap = new LinkedHashMap<T, Object>();
    }
    
    public boolean isIntegerType() {
        return this.isIntegerType;
    }
    
    public Iterator<T> dbValues() {
        return this.valueMap.keySet().iterator();
    }
    
    public Iterator<Object> beanValues() {
        return this.valueMap.values().iterator();
    }
    
    public abstract void bind(final DataBind p0, final Object p1) throws SQLException;
    
    public abstract Object read(final DataReader p0) throws SQLException;
    
    public abstract int getDbType();
    
    public abstract EnumToDbValueMap<T> add(final Object p0, final String p1);
    
    protected void addInternal(final Object beanValue, final T dbValue) {
        this.keyMap.put(beanValue, dbValue);
        this.valueMap.put(dbValue, beanValue);
    }
    
    public T getDbValue(final Object beanValue) {
        if (beanValue == null) {
            return null;
        }
        final T dbValue = this.keyMap.get(beanValue);
        if (dbValue == null && !this.allowNulls) {
            final String msg = "DB value for " + beanValue + " not found in " + this.valueMap;
            throw new IllegalArgumentException(msg);
        }
        return dbValue;
    }
    
    public Object getBeanValue(final T dbValue) {
        if (dbValue == null) {
            return null;
        }
        final Object beanValue = this.valueMap.get(dbValue);
        if (beanValue == null && !this.allowNulls) {
            final String msg = "Bean value for " + dbValue + " not found in " + this.valueMap;
            throw new IllegalArgumentException(msg);
        }
        return beanValue;
    }
}
