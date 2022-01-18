// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

import org.bukkit.Color;

public interface LeatherArmorMeta extends ItemMeta
{
    Color getColor();
    
    void setColor(final Color p0);
    
    LeatherArmorMeta clone();
}
