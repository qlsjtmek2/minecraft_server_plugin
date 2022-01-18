// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.metadata;

import org.bukkit.util.NumberConversions;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import java.lang.ref.WeakReference;

public abstract class MetadataValueAdapter implements MetadataValue
{
    protected final WeakReference<Plugin> owningPlugin;
    
    protected MetadataValueAdapter(final Plugin owningPlugin) {
        Validate.notNull(owningPlugin, "owningPlugin cannot be null");
        this.owningPlugin = new WeakReference<Plugin>(owningPlugin);
    }
    
    public Plugin getOwningPlugin() {
        return this.owningPlugin.get();
    }
    
    public int asInt() {
        return NumberConversions.toInt(this.value());
    }
    
    public float asFloat() {
        return NumberConversions.toFloat(this.value());
    }
    
    public double asDouble() {
        return NumberConversions.toDouble(this.value());
    }
    
    public long asLong() {
        return NumberConversions.toLong(this.value());
    }
    
    public short asShort() {
        return NumberConversions.toShort(this.value());
    }
    
    public byte asByte() {
        return NumberConversions.toByte(this.value());
    }
    
    public boolean asBoolean() {
        final Object value = this.value();
        if (value instanceof Boolean) {
            return (boolean)value;
        }
        if (value instanceof Number) {
            return ((Number)value).intValue() != 0;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String)value);
        }
        return value != null;
    }
    
    public String asString() {
        final Object value = this.value();
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
