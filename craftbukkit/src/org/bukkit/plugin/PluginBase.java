// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

public abstract class PluginBase implements Plugin
{
    public final int hashCode() {
        return this.getName().hashCode();
    }
    
    public final boolean equals(final Object obj) {
        return this == obj || (obj != null && obj instanceof Plugin && this.getName().equals(((Plugin)obj).getName()));
    }
    
    public final String getName() {
        return this.getDescription().getName();
    }
}
