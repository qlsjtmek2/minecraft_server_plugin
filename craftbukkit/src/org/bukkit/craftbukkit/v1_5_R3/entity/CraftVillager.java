// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import net.minecraft.server.v1_5_R3.EntityVillager;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Villager;

public class CraftVillager extends CraftAgeable implements Villager
{
    public CraftVillager(final CraftServer server, final EntityVillager entity) {
        super(server, entity);
    }
    
    public EntityVillager getHandle() {
        return (EntityVillager)this.entity;
    }
    
    public String toString() {
        return "CraftVillager";
    }
    
    public EntityType getType() {
        return EntityType.VILLAGER;
    }
    
    public Profession getProfession() {
        return Profession.getProfession(this.getHandle().getProfession());
    }
    
    public void setProfession(final Profession profession) {
        Validate.notNull(profession);
        this.getHandle().setProfession(profession.getId());
    }
}
