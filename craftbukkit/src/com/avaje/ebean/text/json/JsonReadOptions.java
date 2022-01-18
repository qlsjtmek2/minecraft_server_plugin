// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonReadOptions
{
    protected JsonValueAdapter valueAdapter;
    protected Map<String, JsonReadBeanVisitor<?>> visitorMap;
    
    public JsonReadOptions() {
        this.visitorMap = new LinkedHashMap<String, JsonReadBeanVisitor<?>>();
    }
    
    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }
    
    public Map<String, JsonReadBeanVisitor<?>> getVisitorMap() {
        return this.visitorMap;
    }
    
    public JsonReadOptions setValueAdapter(final JsonValueAdapter valueAdapter) {
        this.valueAdapter = valueAdapter;
        return this;
    }
    
    public JsonReadOptions addRootVisitor(final JsonReadBeanVisitor<?> visitor) {
        return this.addVisitor(null, visitor);
    }
    
    public JsonReadOptions addVisitor(final String path, final JsonReadBeanVisitor<?> visitor) {
        this.visitorMap.put(path, visitor);
        return this;
    }
}
