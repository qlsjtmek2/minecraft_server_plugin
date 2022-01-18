// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemEgg extends Item
{
    public ItemEgg(final int n) {
        super(n);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.l);
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entityHuman) {
        if (!entityHuman.abilities.canInstantlyBuild) {
            --itemStack.count;
        }
        world.makeSound(entityHuman, "random.bow", 0.5f, 0.4f / (ItemEgg.e.nextFloat() * 0.4f + 0.8f));
        if (!world.isStatic) {
            world.addEntity(new EntityEgg(world, entityHuman));
        }
        return itemStack;
    }
}
