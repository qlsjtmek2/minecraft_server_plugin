// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

import org.bukkit.FireworkEffect;

public interface FireworkEffectMeta extends ItemMeta
{
    void setEffect(final FireworkEffect p0);
    
    boolean hasEffect();
    
    FireworkEffect getEffect();
    
    FireworkEffectMeta clone();
}
