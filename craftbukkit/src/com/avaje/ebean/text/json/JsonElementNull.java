// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

public class JsonElementNull implements JsonElement
{
    public static final JsonElementNull NULL;
    
    public String getValue() {
        return "null";
    }
    
    public String toString() {
        return "json null";
    }
    
    public boolean isPrimitive() {
        return true;
    }
    
    public String toPrimitiveString() {
        return null;
    }
    
    static {
        NULL = new JsonElementNull();
    }
}
