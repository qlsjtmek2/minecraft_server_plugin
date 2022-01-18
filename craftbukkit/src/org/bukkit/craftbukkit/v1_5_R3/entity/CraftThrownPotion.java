// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.EntityProjectile;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import java.util.Collection;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityPotion;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.ThrownPotion;

public class CraftThrownPotion extends CraftProjectile implements ThrownPotion
{
    public CraftThrownPotion(final CraftServer server, final EntityPotion entity) {
        super(server, entity);
    }
    
    public Collection<PotionEffect> getEffects() {
        return Potion.getBrewer().getEffectsFromDamage(this.getHandle().getPotionValue());
    }
    
    public ItemStack getItem() {
        this.getHandle().getPotionValue();
        return CraftItemStack.asBukkitCopy(this.getHandle().c);
    }
    
    public void setItem(final ItemStack item) {
        Validate.notNull(item, "ItemStack cannot be null.");
        Validate.isTrue(item.getType() == Material.POTION, "ItemStack must be a potion. This item stack was " + item.getType() + ".");
        this.getHandle().c = CraftItemStack.asNMSCopy(item);
    }
    
    public EntityPotion getHandle() {
        return (EntityPotion)this.entity;
    }
    
    public String toString() {
        return "CraftThrownPotion";
    }
    
    public EntityType getType() {
        return EntityType.SPLASH_POTION;
    }
}
