// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.entity.Player;

public class EntityMushroomCow extends EntityCow
{
    public EntityMushroomCow(final World world) {
        super(world);
        this.texture = "/mob/redcow.png";
        this.a(0.9f, 1.3f);
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.inventory.getItemInHand();
        if (itemstack != null && itemstack.id == Item.BOWL.id && this.getAge() >= 0) {
            if (itemstack.count == 1) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(Item.MUSHROOM_SOUP));
                return true;
            }
            if (entityhuman.inventory.pickup(new ItemStack(Item.MUSHROOM_SOUP)) && !entityhuman.abilities.canInstantlyBuild) {
                entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
                return true;
            }
        }
        if (itemstack == null || itemstack.id != Item.SHEARS.id || this.getAge() < 0) {
            return super.a_(entityhuman);
        }
        final PlayerShearEntityEvent event = new PlayerShearEntityEvent((Player)entityhuman.getBukkitEntity(), this.getBukkitEntity());
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        this.die();
        this.world.addParticle("largeexplode", this.locX, this.locY + this.length / 2.0f, this.locZ, 0.0, 0.0, 0.0);
        if (!this.world.isStatic) {
            final EntityCow entitycow = new EntityCow(this.world);
            entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            entitycow.setHealth(this.getHealth());
            entitycow.ay = this.ay;
            this.world.addEntity(entitycow);
            for (int i = 0; i < 5; ++i) {
                this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + this.length, this.locZ, new ItemStack(Block.RED_MUSHROOM)));
            }
        }
        return true;
    }
    
    public EntityMushroomCow c(final EntityAgeable entityageable) {
        return new EntityMushroomCow(this.world);
    }
    
    public EntityCow b(final EntityAgeable entityageable) {
        return this.c(entityageable);
    }
    
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        return this.c(entityageable);
    }
}
