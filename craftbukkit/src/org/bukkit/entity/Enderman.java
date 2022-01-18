// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.material.MaterialData;

public interface Enderman extends Monster
{
    MaterialData getCarriedMaterial();
    
    void setCarriedMaterial(final MaterialData p0);
}
