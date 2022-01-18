// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemRedstone extends Item
{
    public ItemRedstone(final int i) {
        super(i);
        this.a(CreativeModeTab.d);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, final int l, final float f, final float f1, final float f2) {
        final int clickedX = i;
        final int clickedY = j;
        final int clickedZ = k;
        if (world.getTypeId(i, j, k) != Block.SNOW.id) {
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
            if (!world.isEmpty(i, j, k)) {
                return false;
            }
        }
        return entityhuman.a(i, j, k, l, itemstack) && (!Block.REDSTONE_WIRE.canPlace(world, i, j, k) || ItemBlock.processBlockPlace(world, entityhuman, itemstack, i, j, k, Block.REDSTONE_WIRE.id, 0, clickedX, clickedY, clickedZ));
    }
}
