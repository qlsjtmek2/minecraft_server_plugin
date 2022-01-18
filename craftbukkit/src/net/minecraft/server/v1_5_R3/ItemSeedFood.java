// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSeedFood extends ItemFood
{
    private int b;
    private int c;
    
    public ItemSeedFood(final int i, final int j, final float f, final int k, final int l) {
        super(i, j, f, false);
        this.b = k;
        this.c = l;
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        if (l != 1) {
            return false;
        }
        if (!entityhuman.a(i, j, k, l, itemstack) || !entityhuman.a(i, j + 1, k, l, itemstack)) {
            return false;
        }
        final int i2 = world.getTypeId(i, j, k);
        if (i2 != this.c || !world.isEmpty(i, j + 1, k)) {
            return false;
        }
        if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j + 1, k, this.b, 0, i, j, k)) {
            return false;
        }
        --itemstack.count;
        return true;
    }
}
