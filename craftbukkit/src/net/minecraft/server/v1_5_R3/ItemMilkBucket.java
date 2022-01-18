// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemMilkBucket extends Item
{
    public ItemMilkBucket(final int n) {
        super(n);
        this.d(1);
        this.a(CreativeModeTab.f);
    }
    
    public ItemStack b(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        if (!entityHuman.abilities.canInstantlyBuild) {
            --itemStack.count;
        }
        if (!world.isStatic) {
            entityHuman.bB();
        }
        if (itemStack.count <= 0) {
            return new ItemStack(Item.BUCKET);
        }
        return itemStack;
    }
    
    public int c_(final ItemStack itemStack) {
        return 32;
    }
    
    public EnumAnimation b_(final ItemStack itemStack) {
        return EnumAnimation.DRINK;
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityHuman) {
        entityHuman.a(itemstack, this.c_(itemstack));
        return itemstack;
    }
}
