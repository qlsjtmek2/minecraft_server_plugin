// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockIgniteEvent;

public class ItemFlintAndSteel extends Item
{
    public ItemFlintAndSteel(final int i) {
        super(i);
        this.maxStackSize = 1;
        this.setMaxDurability(64);
        this.a(CreativeModeTab.i);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, final int l, final float f, final float f1, final float f2) {
        final int clickedX = i;
        final int clickedY = j;
        final int clickedZ = k;
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
            if (CraftEventFactory.callBlockIgniteEvent(world, i, j, k, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
                itemstack.damage(1, entityhuman);
                return false;
            }
            final CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
            world.makeSound(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, ItemFlintAndSteel.e.nextFloat() * 0.4f + 0.8f);
            world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
            final BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ);
            if (placeEvent.isCancelled() || !placeEvent.canBuild()) {
                placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
                return false;
            }
        }
        itemstack.damage(1, entityhuman);
        return true;
    }
}
