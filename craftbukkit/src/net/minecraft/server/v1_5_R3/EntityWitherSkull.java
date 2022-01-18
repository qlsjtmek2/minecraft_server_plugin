// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityWitherSkull extends EntityFireball
{
    public EntityWitherSkull(final World world) {
        super(world);
        this.a(0.3125f, 0.3125f);
    }
    
    public EntityWitherSkull(final World world, final EntityLiving entityliving, final double d0, final double d1, final double d2) {
        super(world, entityliving, d0, d1, d2);
        this.a(0.3125f, 0.3125f);
    }
    
    protected float c() {
        return this.d() ? 0.73f : super.c();
    }
    
    public boolean isBurning() {
        return false;
    }
    
    public float a(final Explosion explosion, final World world, final int i, final int j, final int k, final Block block) {
        float f = super.a(explosion, world, i, j, k, block);
        if (this.d() && block != Block.BEDROCK && block != Block.ENDER_PORTAL && block != Block.ENDER_PORTAL_FRAME) {
            f = Math.min(0.8f, f);
        }
        return f;
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (!this.world.isStatic) {
            if (movingobjectposition.entity != null) {
                if (this.shooter != null) {
                    if (movingobjectposition.entity.damageEntity(DamageSource.mobAttack(this.shooter), 8) && !movingobjectposition.entity.isAlive()) {
                        this.shooter.heal(5, EntityRegainHealthEvent.RegainReason.WITHER);
                    }
                }
                else {
                    movingobjectposition.entity.damageEntity(DamageSource.MAGIC, 5);
                }
                if (movingobjectposition.entity instanceof EntityLiving) {
                    byte b0 = 0;
                    if (this.world.difficulty > 1) {
                        if (this.world.difficulty == 2) {
                            b0 = 10;
                        }
                        else if (this.world.difficulty == 3) {
                            b0 = 40;
                        }
                    }
                    if (b0 > 0) {
                        ((EntityLiving)movingobjectposition.entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 20 * b0, 1));
                    }
                }
            }
            final ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), 1.0f, false);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
            }
            this.die();
        }
    }
    
    public boolean K() {
        return false;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        return false;
    }
    
    protected void a() {
        this.datawatcher.a(10, (Object)(byte)0);
    }
    
    public boolean d() {
        return this.datawatcher.getByte(10) == 1;
    }
    
    public void a(final boolean flag) {
        this.datawatcher.watch(10, (byte)(byte)(flag ? 1 : 0));
    }
}
