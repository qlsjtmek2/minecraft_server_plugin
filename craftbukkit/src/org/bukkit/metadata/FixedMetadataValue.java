// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.metadata;

import org.bukkit.plugin.Plugin;

public class FixedMetadataValue extends LazyMetadataValue
{
    private final Object internalValue;
    
    public FixedMetadataValue(final Plugin owningPlugin, final Object value) {
        super(owningPlugin);
        this.internalValue = value;
    }
    
    public void invalidate() {
    }
    
    public Object value() {
        return this.internalValue;
    }
}
