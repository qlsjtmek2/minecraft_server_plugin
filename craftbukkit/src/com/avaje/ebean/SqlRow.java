// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Collection;
import java.util.Set;
import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Iterator;
import java.util.Map;
import java.io.Serializable;

public interface SqlRow extends Serializable, Map<String, Object>
{
    Iterator<String> keys();
    
    Object remove(final Object p0);
    
    Object get(final Object p0);
    
    Object put(final String p0, final Object p1);
    
    Object set(final String p0, final Object p1);
    
    Boolean getBoolean(final String p0);
    
    UUID getUUID(final String p0);
    
    Integer getInteger(final String p0);
    
    BigDecimal getBigDecimal(final String p0);
    
    Long getLong(final String p0);
    
    Double getDouble(final String p0);
    
    Float getFloat(final String p0);
    
    String getString(final String p0);
    
    Date getUtilDate(final String p0);
    
    java.sql.Date getDate(final String p0);
    
    Timestamp getTimestamp(final String p0);
    
    String toString();
    
    void clear();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final Object p0);
    
    Set<Entry<String, Object>> entrySet();
    
    boolean isEmpty();
    
    Set<String> keySet();
    
    void putAll(final Map<? extends String, ?> p0);
    
    int size();
    
    Collection<Object> values();
}
