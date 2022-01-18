// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityEnderCrystal;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.EnderCrystal;

public class CraftEnderCrystal extends CraftEntity implements EnderCrystal
{
    public CraftEnderCrystal(final CraftServer server, final EntityEnderCrystal entity) {
        super(server, entity);
    }
    
    public EntityEnderCrystal getHandle() {
        return (EntityEnderCrystal)this.entity;
    }
    
    public String toString() {
        return "CraftEnderCrystal";
    }
    
    public EntityType getType() {
        return EntityType.ENDER_CRYSTAL;
    }
}
