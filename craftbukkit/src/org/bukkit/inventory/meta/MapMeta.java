// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

public interface MapMeta extends ItemMeta
{
    boolean isScaling();
    
    void setScaling(final boolean p0);
    
    MapMeta clone();
}
