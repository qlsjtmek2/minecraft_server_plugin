// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSeeds extends Item
{
    private int id;
    private int b;
    
    public ItemSeeds(final int i, final int j, final int k) {
        super(i);
        this.id = j;
        this.b = k;
        this.a(CreativeModeTab.l);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        if (l != 1) {
            return false;
        }
        if (!entityhuman.e(i, j, k) || !entityhuman.e(i, j + 1, k)) {
            return false;
        }
        final int i2 = world.getTypeId(i, j, k);
        if (i2 != this.b || !world.isEmpty(i, j + 1, k)) {
            return false;
        }
        if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j + 1, k, this.id, 0, i, j, k)) {
            return false;
        }
        --itemstack.count;
        return true;
    }
}
