// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;

final class PropertyMap
{
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, String> map;
    
    PropertyMap() {
        this.map = new LinkedHashMap<String, String>();
    }
    
    public String toString() {
        return this.map.toString();
    }
    
    public void evaluateProperties() {
        for (final Map.Entry<String, String> e : this.entrySet()) {
            final String key = e.getKey();
            final String val = e.getValue();
            final String eval = this.eval(val);
            if (eval != null && !eval.equals(val)) {
                this.put(key, eval);
            }
        }
    }
    
    public synchronized String eval(final String val) {
        return PropertyExpression.eval(val, this);
    }
    
    public synchronized boolean getBoolean(final String key, final boolean defaultValue) {
        final String value = this.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    public synchronized int getInt(final String key, final int defaultValue) {
        final String value = this.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
    
    public synchronized String get(final String key, final String defaultValue) {
        final String value = this.map.get(key.toLowerCase());
        return (value == null) ? defaultValue : value;
    }
    
    public synchronized String get(final String key) {
        return this.map.get(key.toLowerCase());
    }
    
    synchronized void putAll(final Map<String, String> keyValueMap) {
        for (final Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    synchronized String putEval(final String key, String value) {
        value = PropertyExpression.eval(value, this);
        return this.map.put(key.toLowerCase(), value);
    }
    
    synchronized String put(final String key, final String value) {
        return this.map.put(key.toLowerCase(), value);
    }
    
    synchronized String remove(final String key) {
        return this.map.remove(key.toLowerCase());
    }
    
    synchronized Set<Map.Entry<String, String>> entrySet() {
        return this.map.entrySet();
    }
}
