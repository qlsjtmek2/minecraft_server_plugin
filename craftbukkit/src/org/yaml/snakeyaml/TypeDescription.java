// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml;

import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.nodes.Tag;

public final class TypeDescription
{
    private final Class<?> type;
    private Tag tag;
    private Map<String, Class<?>> listProperties;
    private Map<String, Class<?>> keyProperties;
    private Map<String, Class<?>> valueProperties;
    
    public TypeDescription(final Class<?> clazz, final Tag tag) {
        this.type = clazz;
        this.tag = tag;
        this.listProperties = new HashMap<String, Class<?>>();
        this.keyProperties = new HashMap<String, Class<?>>();
        this.valueProperties = new HashMap<String, Class<?>>();
    }
    
    public TypeDescription(final Class<?> clazz, final String tag) {
        this(clazz, new Tag(tag));
    }
    
    public TypeDescription(final Class<?> clazz) {
        this(clazz, (Tag)null);
    }
    
    public Tag getTag() {
        return this.tag;
    }
    
    public void setTag(final Tag tag) {
        this.tag = tag;
    }
    
    public void setTag(final String tag) {
        this.setTag(new Tag(tag));
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public void putListPropertyType(final String property, final Class<?> type) {
        this.listProperties.put(property, type);
    }
    
    public Class<?> getListPropertyType(final String property) {
        return this.listProperties.get(property);
    }
    
    public void putMapPropertyType(final String property, final Class<?> key, final Class<?> value) {
        this.keyProperties.put(property, key);
        this.valueProperties.put(property, value);
    }
    
    public Class<?> getMapKeyType(final String property) {
        return this.keyProperties.get(property);
    }
    
    public Class<?> getMapValueType(final String property) {
        return this.valueProperties.get(property);
    }
    
    public String toString() {
        return "TypeDescription for " + this.getType() + " (tag='" + this.getTag() + "')";
    }
}
