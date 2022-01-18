// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonElementObject implements JsonElement
{
    private final Map<String, JsonElement> map;
    
    public JsonElementObject() {
        this.map = new LinkedHashMap<String, JsonElement>();
    }
    
    public void put(final String key, final JsonElement value) {
        this.map.put(key, value);
    }
    
    public JsonElement get(final String key) {
        return this.map.get(key);
    }
    
    public JsonElement getValue(final String key) {
        return this.map.get(key);
    }
    
    public Set<String> keySet() {
        return this.map.keySet();
    }
    
    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.map.entrySet();
    }
    
    public String toString() {
        return this.map.toString();
    }
    
    public boolean isPrimitive() {
        return false;
    }
    
    public String toPrimitiveString() {
        return null;
    }
}
