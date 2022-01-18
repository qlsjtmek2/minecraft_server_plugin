// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.Collection;
import java.util.Set;
import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.util.UUID;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import com.avaje.ebean.SqlRow;

public class DefaultSqlRow implements SqlRow
{
    static final long serialVersionUID = -3120927797041336242L;
    private final String dbTrueValue;
    Map<String, Object> map;
    
    public DefaultSqlRow(final Map<String, Object> map, final String dbTrueValue) {
        this.map = map;
        this.dbTrueValue = dbTrueValue;
    }
    
    public DefaultSqlRow(final String dbTrueValue) {
        this.map = new LinkedHashMap<String, Object>();
        this.dbTrueValue = dbTrueValue;
    }
    
    public DefaultSqlRow(final int initialCapacity, final float loadFactor, final String dbTrueValue) {
        this.map = new LinkedHashMap<String, Object>(initialCapacity, loadFactor);
        this.dbTrueValue = dbTrueValue;
    }
    
    public Iterator<String> keys() {
        return this.map.keySet().iterator();
    }
    
    public Object remove(Object name) {
        name = ((String)name).toLowerCase();
        return this.map.remove(name);
    }
    
    public Object get(Object name) {
        name = ((String)name).toLowerCase();
        return this.map.get(name);
    }
    
    public Object put(final String name, final Object value) {
        return this.setInternal(name, value);
    }
    
    public Object set(final String name, final Object value) {
        return this.setInternal(name, value);
    }
    
    private Object setInternal(String name, final Object newValue) {
        name = name.toLowerCase();
        return this.map.put(name, newValue);
    }
    
    public UUID getUUID(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toUUID(val);
    }
    
    public Boolean getBoolean(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toBoolean(val, this.dbTrueValue);
    }
    
    public Integer getInteger(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toInteger(val);
    }
    
    public BigDecimal getBigDecimal(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toBigDecimal(val);
    }
    
    public Long getLong(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toLong(val);
    }
    
    public Double getDouble(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toDouble(val);
    }
    
    public Float getFloat(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toFloat(val);
    }
    
    public String getString(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toString(val);
    }
    
    public Date getUtilDate(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toUtilDate(val);
    }
    
    public java.sql.Date getDate(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toDate(val);
    }
    
    public Timestamp getTimestamp(final String name) {
        final Object val = this.get(name);
        return BasicTypeConverter.toTimestamp(val);
    }
    
    public String toString() {
        return this.map.toString();
    }
    
    public void clear() {
        this.map.clear();
    }
    
    public boolean containsKey(Object key) {
        key = ((String)key).toLowerCase();
        return this.map.containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        return this.map.containsValue(value);
    }
    
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }
    
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    public Set<String> keySet() {
        return this.map.keySet();
    }
    
    public void putAll(final Map<? extends String, ?> t) {
        this.map.putAll(t);
    }
    
    public int size() {
        return this.map.size();
    }
    
    public Collection<Object> values() {
        return this.map.values();
    }
}
