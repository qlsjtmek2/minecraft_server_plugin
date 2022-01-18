// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemExpBottle extends Item
{
    public ItemExpBottle(final int n) {
        super(n);
        this.a(CreativeModeTab.f);
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        if (!entityHuman.abilities.canInstantlyBuild) {
            --itemStack.count;
        }
        world.makeSound(entityHuman, "random.bow", 0.5f, 0.4f / (ItemExpBottle.e.nextFloat() * 0.4f + 0.8f));
        if (!world.isStatic) {
            world.addEntity(new EntityThrownExpBottle(world, entityHuman));
        }
        return itemStack;
    }
}
