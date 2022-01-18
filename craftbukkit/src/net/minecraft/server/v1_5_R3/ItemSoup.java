// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSoup extends ItemFood
{
    public ItemSoup(final int i, final int j) {
        super(i, j, false);
        this.d(1);
    }
    
    public ItemStack b(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        super.b(itemstack, world, entityhuman);
        return new ItemStack(Item.BOWL);
    }
}
