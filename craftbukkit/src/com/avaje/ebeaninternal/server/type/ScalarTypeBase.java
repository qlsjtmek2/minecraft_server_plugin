// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;

public abstract class ScalarTypeBase<T> implements ScalarType<T>
{
    protected final Class<T> type;
    protected final boolean jdbcNative;
    protected final int jdbcType;
    
    public ScalarTypeBase(final Class<T> type, final boolean jdbcNative, final int jdbcType) {
        this.type = type;
        this.jdbcNative = jdbcNative;
        this.jdbcType = jdbcType;
    }
    
    public int getLength() {
        return 0;
    }
    
    public boolean isJdbcNative() {
        return this.jdbcNative;
    }
    
    public int getJdbcType() {
        return this.jdbcType;
    }
    
    public Class<T> getType() {
        return this.type;
    }
    
    public String format(final Object v) {
        return this.formatValue((T)v);
    }
    
    public boolean isDbNull(final Object value) {
        return value == null;
    }
    
    public Object getDbNullValue(final Object value) {
        return value;
    }
    
    public void loadIgnore(final DataReader dataReader) {
        dataReader.incrementPos(1);
    }
    
    public void accumulateScalarTypes(final String propName, final CtCompoundTypeScalarList list) {
        list.addScalarType(propName, this);
    }
    
    public String jsonToString(final T value, final JsonValueAdapter ctx) {
        return this.formatValue(value);
    }
    
    public T jsonFromString(final String value, final JsonValueAdapter ctx) {
        return this.parse(value);
    }
}
