// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemGlassBottle extends Item
{
    public ItemGlassBottle(final int n) {
        super(n);
        this.a(CreativeModeTab.k);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        final MovingObjectPosition a = this.a(world, entityhuman, true);
        if (a == null) {
            return itemstack;
        }
        if (a.type == EnumMovingObjectType.TILE) {
            final int b = a.b;
            final int c = a.c;
            final int d = a.d;
            if (!world.a(entityhuman, b, c, d)) {
                return itemstack;
            }
            if (!entityhuman.a(b, c, d, a.face, itemstack)) {
                return itemstack;
            }
            if (world.getMaterial(b, c, d) == Material.WATER) {
                --itemstack.count;
                if (itemstack.count <= 0) {
                    return new ItemStack(Item.POTION);
                }
                if (!entityhuman.inventory.pickup(new ItemStack(Item.POTION))) {
                    entityhuman.drop(new ItemStack(Item.POTION.id, 1, 0));
                }
            }
        }
        return itemstack;
    }
}
