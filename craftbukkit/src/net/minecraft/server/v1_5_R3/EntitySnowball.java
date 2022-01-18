// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntitySnowball extends EntityProjectile
{
    public EntitySnowball(final World world) {
        super(world);
    }
    
    public EntitySnowball(final World world, final EntityLiving entityliving) {
        super(world, entityliving);
    }
    
    public EntitySnowball(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
    }
    
    protected void a(final MovingObjectPosition movingObjectPosition) {
        if (movingObjectPosition.entity != null) {
            int i = 0;
            if (movingObjectPosition.entity instanceof EntityBlaze) {
                i = 3;
            }
            movingObjectPosition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), i);
        }
        for (int j = 0; j < 8; ++j) {
            this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0, 0.0, 0.0);
        }
        if (!this.world.isStatic) {
            this.die();
        }
    }
}
