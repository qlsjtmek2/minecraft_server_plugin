// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockIgniteEvent;

public class ItemFireball extends Item
{
    public ItemFireball(final int i) {
        super(i);
        this.a(CreativeModeTab.f);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, final int l, final float f, final float f1, final float f2) {
        if (world.isStatic) {
            return true;
        }
        if (l == 0) {
            --j;
        }
        if (l == 1) {
            ++j;
        }
        if (l == 2) {
            --k;
        }
        if (l == 3) {
            ++k;
        }
        if (l == 4) {
            --i;
        }
        if (l == 5) {
            ++i;
        }
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        final int i2 = world.getTypeId(i, j, k);
        if (i2 == 0) {
            if (CraftEventFactory.callBlockIgniteEvent(world, i, j, k, BlockIgniteEvent.IgniteCause.FIREBALL, entityhuman).isCancelled()) {
                if (!entityhuman.abilities.canInstantlyBuild) {
                    --itemstack.count;
                }
                return false;
            }
            world.makeSound(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, ItemFireball.e.nextFloat() * 0.4f + 0.8f);
            world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
        }
        if (!entityhuman.abilities.canInstantlyBuild) {
            --itemstack.count;
        }
        return true;
    }
}
