// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class DispenseBehaviorSnowBall extends DispenseBehaviorProjectile
{
    protected IProjectile a(final World world, final IPosition position) {
        return new EntitySnowball(world, position.getX(), position.getY(), position.getZ());
    }
}
