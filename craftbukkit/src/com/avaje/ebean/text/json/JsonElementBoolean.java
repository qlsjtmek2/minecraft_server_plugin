// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.json;

public class JsonElementBoolean implements JsonElement
{
    public static final JsonElementBoolean TRUE;
    public static final JsonElementBoolean FALSE;
    private final boolean value;
    
    private JsonElementBoolean(final boolean value) {
        this.value = value;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public String toString() {
        return Boolean.toString(this.value);
    }
    
    public boolean isPrimitive() {
        return true;
    }
    
    public String toPrimitiveString() {
        return Boolean.toString(this.value);
    }
    
    static {
        TRUE = new JsonElementBoolean(true);
        FALSE = new JsonElementBoolean(false);
    }
}
