// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemEnderPearl extends Item
{
    public ItemEnderPearl(final int n) {
        super(n);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.f);
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        if (entityHuman.abilities.canInstantlyBuild) {
            return itemStack;
        }
        if (entityHuman.vehicle != null) {
            return itemStack;
        }
        --itemStack.count;
        world.makeSound(entityHuman, "random.bow", 0.5f, 0.4f / (ItemEnderPearl.e.nextFloat() * 0.4f + 0.8f));
        if (!world.isStatic) {
            world.addEntity(new EntityEnderPearl(world, entityHuman));
        }
        return itemStack;
    }
}
