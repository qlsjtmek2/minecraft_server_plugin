// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import org.bukkit.entity.EntityType;
import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.EntityTameableAnimal;
import net.minecraft.server.v1_5_R3.EntityOcelot;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Ocelot;

public class CraftOcelot extends CraftTameableAnimal implements Ocelot
{
    public CraftOcelot(final CraftServer server, final EntityOcelot wolf) {
        super(server, wolf);
    }
    
    public EntityOcelot getHandle() {
        return (EntityOcelot)this.entity;
    }
    
    public Type getCatType() {
        return Type.getType(this.getHandle().getCatType());
    }
    
    public void setCatType(final Type type) {
        Validate.notNull(type, "Cat type cannot be null");
        this.getHandle().setCatType(type.getId());
    }
    
    public EntityType getType() {
        return EntityType.OCELOT;
    }
}
