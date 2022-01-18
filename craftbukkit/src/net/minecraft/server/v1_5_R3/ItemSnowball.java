// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSnowball extends Item
{
    public ItemSnowball(final int n) {
        super(n);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.f);
    }
    
    public ItemStack a(final ItemStack itemStack, final World world, final EntityHuman entity) {
        if (!entity.abilities.canInstantlyBuild) {
            --itemStack.count;
        }
        world.makeSound(entity, "random.bow", 0.5f, 0.4f / (ItemSnowball.e.nextFloat() * 0.4f + 0.8f));
        if (!world.isStatic) {
            world.addEntity(new EntitySnowball(world, entity));
        }
        return itemStack;
    }
}
