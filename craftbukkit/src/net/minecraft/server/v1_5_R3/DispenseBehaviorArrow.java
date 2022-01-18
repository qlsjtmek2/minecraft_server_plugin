// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class DispenseBehaviorArrow extends DispenseBehaviorProjectile
{
    protected IProjectile a(final World world, final IPosition position) {
        final EntityArrow entityArrow = new EntityArrow(world, position.getX(), position.getY(), position.getZ());
        entityArrow.fromPlayer = 1;
        return entityArrow;
    }
}
