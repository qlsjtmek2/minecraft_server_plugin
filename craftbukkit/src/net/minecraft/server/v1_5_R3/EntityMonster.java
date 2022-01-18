// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public abstract class EntityMonster extends EntityCreature implements IMonster
{
    public EntityMonster(final World world) {
        super(world);
        this.be = 5;
    }
    
    public void c() {
        this.br();
        final float f = this.c(1.0f);
        if (f > 0.5f) {
            this.bC += 2;
        }
        super.c();
    }
    
    public void l_() {
        super.l_();
        if (!this.world.isStatic && this.world.difficulty == 0) {
            this.die();
        }
    }
    
    protected Entity findTarget() {
        final EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0);
        return (entityhuman != null && this.n(entityhuman)) ? entityhuman : null;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (!super.damageEntity(damagesource, i)) {
            return false;
        }
        final Entity entity = damagesource.getEntity();
        if (this.passenger != entity && this.vehicle != entity) {
            if (entity != this) {
                if (entity != this.target && (this instanceof EntityBlaze || this instanceof EntityEnderman || this instanceof EntitySpider || this instanceof EntityGiantZombie || this instanceof EntitySilverfish)) {
                    final EntityTargetEvent event = CraftEventFactory.callEntityTargetEvent(this, entity, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
                    if (!event.isCancelled()) {
                        if (event.getTarget() == null) {
                            this.target = null;
                        }
                        else {
                            this.target = ((CraftEntity)event.getTarget()).getHandle();
                        }
                    }
                }
                else {
                    this.target = entity;
                }
            }
            return true;
        }
        return true;
    }
    
    public boolean m(final Entity entity) {
        int i = this.c(entity);
        if (this.hasEffect(MobEffectList.INCREASE_DAMAGE)) {
            i += 3 << this.getEffect(MobEffectList.INCREASE_DAMAGE).getAmplifier();
        }
        if (this.hasEffect(MobEffectList.WEAKNESS)) {
            i -= 2 << this.getEffect(MobEffectList.WEAKNESS).getAmplifier();
        }
        int j = 0;
        if (entity instanceof EntityLiving) {
            i += EnchantmentManager.a(this, (EntityLiving)entity);
            j += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving)entity);
        }
        final boolean flag = entity.damageEntity(DamageSource.mobAttack(this), i);
        if (flag) {
            if (j > 0) {
                entity.g(-MathHelper.sin(this.yaw * 3.1415927f / 180.0f) * j * 0.5f, 0.1, MathHelper.cos(this.yaw * 3.1415927f / 180.0f) * j * 0.5f);
                this.motX *= 0.6;
                this.motZ *= 0.6;
            }
            final int k = EnchantmentManager.getFireAspectEnchantmentLevel(this);
            if (k > 0) {
                entity.setOnFire(k * 4);
            }
            if (entity instanceof EntityLiving) {
                EnchantmentThorns.a(this, (EntityLiving)entity, this.random);
            }
        }
        return flag;
    }
    
    protected void a(final Entity entity, final float f) {
        if (this.attackTicks <= 0 && f < 2.0f && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.attackTicks = 20;
            this.m(entity);
        }
    }
    
    public float a(final int i, final int j, final int k) {
        return 0.5f - this.world.q(i, j, k);
    }
    
    protected boolean i_() {
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.floor(this.boundingBox.b);
        final int k = MathHelper.floor(this.locZ);
        if (this.world.b(EnumSkyBlock.SKY, i, j, k) > this.random.nextInt(32)) {
            return false;
        }
        int l = this.world.getLightLevel(i, j, k);
        if (this.world.O()) {
            final int i2 = this.world.j;
            this.world.j = 10;
            l = this.world.getLightLevel(i, j, k);
            this.world.j = i2;
        }
        return l <= this.random.nextInt(8);
    }
    
    public boolean canSpawn() {
        return this.i_() && super.canSpawn();
    }
    
    public int c(final Entity entity) {
        return 2;
    }
}
