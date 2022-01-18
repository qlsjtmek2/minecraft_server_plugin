// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemReed extends Item
{
    private int id;
    
    public ItemReed(final int i, final Block block) {
        super(i);
        this.id = block.id;
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
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        if (itemstack.count == 0) {
            return false;
        }
        if (world.mayPlace(this.id, i, j, k, false, l, null, itemstack)) {
            final Block block = Block.byId[this.id];
            final int j2 = block.getPlacedData(world, i, j, k, l, f, f1, f2, 0);
            ItemBlock.processBlockPlace(world, entityhuman, itemstack, i, j, k, this.id, j2, clickedX, clickedY, clickedZ);
        }
        return true;
    }
}
