// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemBed extends Item
{
    public ItemBed(final int i) {
        super(i);
        this.a(CreativeModeTab.c);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, int j, final int k, final int l, final float f, final float f1, final float f2) {
        final int clickedY = j;
        if (world.isStatic) {
            return true;
        }
        if (l != 1) {
            return false;
        }
        ++j;
        final BlockBed blockbed = (BlockBed)Block.BED;
        final int i2 = MathHelper.floor(entityhuman.yaw * 4.0f / 360.0f + 0.5) & 0x3;
        byte b0 = 0;
        byte b2 = 0;
        if (i2 == 0) {
            b2 = 1;
        }
        if (i2 == 1) {
            b0 = -1;
        }
        if (i2 == 2) {
            b2 = -1;
        }
        if (i2 == 3) {
            b0 = 1;
        }
        if (!entityhuman.a(i, j, k, l, itemstack) || !entityhuman.a(i + b0, j, k + b2, l, itemstack)) {
            return false;
        }
        if (!world.isEmpty(i, j, k) || !world.isEmpty(i + b0, j, k + b2) || !world.w(i, j - 1, k) || !world.v(i + b0, j - 1, k + b2)) {
            return false;
        }
        if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, blockbed.id, i2, i, clickedY, k)) {
            return false;
        }
        if (world.getTypeId(i, j, k) == blockbed.id) {
            world.setTypeIdAndData(i + b0, j, k + b2, blockbed.id, i2 + 8, 3);
        }
        --itemstack.count;
        return true;
    }
}
