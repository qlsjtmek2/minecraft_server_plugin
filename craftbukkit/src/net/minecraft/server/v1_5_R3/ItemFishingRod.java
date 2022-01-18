// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;

public class ItemFishingRod extends Item
{
    public ItemFishingRod(final int i) {
        super(i);
        this.setMaxDurability(64);
        this.d(1);
        this.a(CreativeModeTab.i);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        if (entityhuman.hookedFish != null) {
            final int i = entityhuman.hookedFish.c();
            itemstack.damage(i, entityhuman);
            entityhuman.bK();
        }
        else {
            final EntityFishingHook hook = new EntityFishingHook(world, entityhuman);
            final PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)entityhuman.getBukkitEntity(), null, (Fish)hook.getBukkitEntity(), PlayerFishEvent.State.FISHING);
            world.getServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
                return itemstack;
            }
            world.makeSound(entityhuman, "random.bow", 0.5f, 0.4f / (ItemFishingRod.e.nextFloat() * 0.4f + 0.8f));
            if (!world.isStatic) {
                world.addEntity(hook);
            }
            entityhuman.bK();
        }
        return itemstack;
    }
}
