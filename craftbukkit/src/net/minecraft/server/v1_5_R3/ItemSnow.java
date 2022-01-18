// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSnow extends ItemBlockWithAuxData
{
    public ItemSnow(final int i, final Block block) {
        super(i, block);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        if (itemstack.count == 0) {
            return false;
        }
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        final int i2 = world.getTypeId(i, j, k);
        if (i2 == Block.SNOW.id) {
            final Block block = Block.byId[this.g()];
            final int j2 = world.getData(i, j, k);
            final int k2 = j2 & 0x7;
            if (k2 <= 6 && world.b(block.b(world, i, j, k)) && ItemBlock.processBlockPlace(world, entityhuman, itemstack, i, j, k, Block.SNOW.id, k2 + 1 | (j2 & 0xFFFFFFF8), i, j, k)) {
                return true;
            }
        }
        return super.interactWith(itemstack, entityhuman, world, i, j, k, l, f, f1, f2);
    }
}
