// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class DispenseBehaviorThrownPotion extends DispenseBehaviorProjectile
{
    final /* synthetic */ ItemStack b;
    final /* synthetic */ DispenseBehaviorPotion c;
    
    DispenseBehaviorThrownPotion(final DispenseBehaviorPotion c, final ItemStack b) {
        this.c = c;
        this.b = b;
    }
    
    protected IProjectile a(final World world, final IPosition position) {
        return new EntityPotion(world, position.getX(), position.getY(), position.getZ(), this.b.cloneItemStack());
    }
    
    protected float a() {
        return super.a() * 0.5f;
    }
    
    protected float b() {
        return super.b() * 1.25f;
    }
}
