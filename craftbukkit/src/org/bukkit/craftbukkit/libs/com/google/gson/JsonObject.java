// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson;

import java.util.Set;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.$Gson$Preconditions;
import java.util.LinkedHashMap;
import java.util.Map;

public final class JsonObject extends JsonElement
{
    private final Map<String, JsonElement> members;
    
    public JsonObject() {
        this.members = new LinkedHashMap<String, JsonElement>();
    }
    
    public void add(final String property, JsonElement value) {
        if (value == null) {
            value = JsonNull.INSTANCE;
        }
        this.members.put($Gson$Preconditions.checkNotNull(property), value);
    }
    
    public JsonElement remove(final String property) {
        return this.members.remove(property);
    }
    
    public void addProperty(final String property, final String value) {
        this.add(property, this.createJsonElement(value));
    }
    
    public void addProperty(final String property, final Number value) {
        this.add(property, this.createJsonElement(value));
    }
    
    public void addProperty(final String property, final Boolean value) {
        this.add(property, this.createJsonElement(value));
    }
    
    public void addProperty(final String property, final Character value) {
        this.add(property, this.createJsonElement(value));
    }
    
    private JsonElement createJsonElement(final Object value) {
        return (value == null) ? JsonNull.INSTANCE : new JsonPrimitive(value);
    }
    
    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }
    
    public boolean has(final String memberName) {
        return this.members.containsKey(memberName);
    }
    
    public JsonElement get(final String memberName) {
        if (this.members.containsKey(memberName)) {
            final JsonElement member = this.members.get(memberName);
            return (member == null) ? JsonNull.INSTANCE : member;
        }
        return null;
    }
    
    public JsonPrimitive getAsJsonPrimitive(final String memberName) {
        return this.members.get(memberName);
    }
    
    public JsonArray getAsJsonArray(final String memberName) {
        return this.members.get(memberName);
    }
    
    public JsonObject getAsJsonObject(final String memberName) {
        return this.members.get(memberName);
    }
    
    public boolean equals(final Object o) {
        return o == this || (o instanceof JsonObject && ((JsonObject)o).members.equals(this.members));
    }
    
    public int hashCode() {
        return this.members.hashCode();
    }
}
