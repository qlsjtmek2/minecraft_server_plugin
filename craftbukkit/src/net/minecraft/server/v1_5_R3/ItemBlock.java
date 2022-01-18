// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlockState;

public class ItemBlock extends Item
{
    private int id;
    
    public ItemBlock(final int i) {
        super(i);
        this.id = i + 256;
    }
    
    public int g() {
        return this.id;
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, int l, final float f, final float f1, final float f2) {
        final int clickedX = i;
        final int clickedY = j;
        final int clickedZ = k;
        final int i2 = world.getTypeId(i, j, k);
        if (i2 == Block.SNOW.id && (world.getData(i, j, k) & 0x7) < 1) {
            l = 1;
        }
        else if (i2 != Block.VINE.id && i2 != Block.LONG_GRASS.id && i2 != Block.DEAD_BUSH.id) {
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
        }
        if (itemstack.count == 0) {
            return false;
        }
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        if (j == 255 && Block.byId[this.id].material.isBuildable()) {
            return false;
        }
        if (world.mayPlace(this.id, i, j, k, false, l, entityhuman, itemstack)) {
            final Block block = Block.byId[this.id];
            final int j2 = this.filterData(itemstack.getData());
            final int k2 = Block.byId[this.id].getPlacedData(world, i, j, k, l, f, f1, f2, j2);
            return processBlockPlace(world, entityhuman, itemstack, i, j, k, this.id, k2, clickedX, clickedY, clickedZ);
        }
        return false;
    }
    
    static boolean processBlockPlace(final World world, final EntityHuman entityhuman, final ItemStack itemstack, final int x, final int y, final int z, final int id, final int data, final int clickedX, final int clickedY, final int clickedZ) {
        final BlockState blockstate = CraftBlockState.getBlockState(world, x, y, z);
        world.callingPlaceEvent = true;
        world.setTypeIdAndData(x, y, z, id, data, 2);
        final BlockPlaceEvent event = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockstate, clickedX, clickedY, clickedZ);
        if (event.isCancelled() || !event.canBuild()) {
            blockstate.update(true, false);
            return world.callingPlaceEvent = false;
        }
        world.callingPlaceEvent = false;
        final int newId = world.getTypeId(x, y, z);
        final int newData = world.getData(x, y, z);
        final Block block = Block.byId[newId];
        if (block != null && !(block instanceof BlockContainer)) {
            block.onPlace(world, x, y, z);
        }
        world.update(x, y, z, newId);
        if (block != null && block != Block.SKULL) {
            block.postPlace(world, x, y, z, entityhuman, itemstack);
            block.postPlace(world, x, y, z, newData);
            world.makeSound(x + 0.5f, y + 0.5f, z + 0.5f, block.stepSound.getPlaceSound(), (block.stepSound.getVolume1() + 1.0f) / 2.0f, block.stepSound.getVolume2() * 0.8f);
        }
        if (itemstack != null) {
            --itemstack.count;
        }
        return true;
    }
    
    public String d(final ItemStack itemstack) {
        return Block.byId[this.id].a();
    }
    
    public String getName() {
        return Block.byId[this.id].a();
    }
}
