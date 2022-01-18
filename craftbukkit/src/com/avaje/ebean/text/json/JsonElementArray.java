// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

import java.util.ArrayList;
import java.util.List;

public class JsonElementArray implements JsonElement
{
    private final List<JsonElement> values;
    
    public JsonElementArray() {
        this.values = new ArrayList<JsonElement>();
    }
    
    public List<JsonElement> getValues() {
        return this.values;
    }
    
    public void add(final JsonElement value) {
        this.values.add(value);
    }
    
    public String toString() {
        return this.values.toString();
    }
    
    public boolean isPrimitive() {
        return false;
    }
    
    public String toPrimitiveString() {
        return null;
    }
}
