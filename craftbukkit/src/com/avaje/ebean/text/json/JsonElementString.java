// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

public class JsonElementString implements JsonElement
{
    private final String value;
    
    public JsonElementString(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String toString() {
        return this.value;
    }
    
    public boolean isPrimitive() {
        return true;
    }
    
    public String toPrimitiveString() {
        return this.value;
    }
}
