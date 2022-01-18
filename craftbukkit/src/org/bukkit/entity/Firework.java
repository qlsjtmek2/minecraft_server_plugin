// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.inventory.meta.FireworkMeta;

public interface Firework extends Entity
{
    FireworkMeta getFireworkMeta();
    
    void setFireworkMeta(final FireworkMeta p0);
}
