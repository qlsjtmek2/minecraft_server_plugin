// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class DispenseBehaviorExpBottle extends DispenseBehaviorProjectile
{
    protected IProjectile a(final World world, final IPosition position) {
        return new EntityThrownExpBottle(world, position.getX(), position.getY(), position.getZ());
    }
    
    protected float a() {
        return super.a() * 0.5f;
    }
    
    protected float b() {
        return super.b() * 1.25f;
    }
}
