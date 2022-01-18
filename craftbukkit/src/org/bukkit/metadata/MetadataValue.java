// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.metadata;

import org.bukkit.plugin.Plugin;

public interface MetadataValue
{
    Object value();
    
    int asInt();
    
    float asFloat();
    
    double asDouble();
    
    long asLong();
    
    short asShort();
    
    byte asByte();
    
    boolean asBoolean();
    
    String asString();
    
    Plugin getOwningPlugin();
    
    void invalidate();
}
