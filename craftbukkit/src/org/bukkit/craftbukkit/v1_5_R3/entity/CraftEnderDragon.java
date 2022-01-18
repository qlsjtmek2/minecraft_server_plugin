// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityComplexPart;
import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.ComplexEntityPart;
import java.util.Set;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.EnderDragon;

public class CraftEnderDragon extends CraftComplexLivingEntity implements EnderDragon
{
    public CraftEnderDragon(final CraftServer server, final EntityEnderDragon entity) {
        super(server, entity);
    }
    
    public Set<ComplexEntityPart> getParts() {
        final ImmutableSet.Builder<ComplexEntityPart> builder = ImmutableSet.builder();
        for (final EntityComplexPart part : this.getHandle().children) {
            builder.add((ComplexEntityPart)part.getBukkitEntity());
        }
        return builder.build();
    }
    
    public EntityEnderDragon getHandle() {
        return (EntityEnderDragon)this.entity;
    }
    
    public String toString() {
        return "CraftEnderDragon";
    }
    
    public EntityType getType() {
        return EntityType.ENDER_DRAGON;
    }
}
