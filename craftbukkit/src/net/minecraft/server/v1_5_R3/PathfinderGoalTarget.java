// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public abstract class PathfinderGoalTarget extends PathfinderGoal
{
    protected EntityLiving d;
    protected float e;
    protected boolean f;
    private boolean a;
    private int b;
    private int c;
    private int g;
    
    public PathfinderGoalTarget(final EntityLiving entityliving, final float f, final boolean flag) {
        this(entityliving, f, flag, false);
    }
    
    public PathfinderGoalTarget(final EntityLiving entityliving, final float f, final boolean flag, final boolean flag1) {
        this.b = 0;
        this.c = 0;
        this.g = 0;
        this.d = entityliving;
        this.e = f;
        this.f = flag;
        this.a = flag1;
    }
    
    public boolean b() {
        final EntityLiving entityliving = this.d.getGoalTarget();
        if (entityliving == null) {
            return false;
        }
        if (!entityliving.isAlive()) {
            return false;
        }
        if (this.d.e(entityliving) > this.e * this.e) {
            return false;
        }
        if (this.f) {
            if (this.d.getEntitySenses().canSee(entityliving)) {
                this.g = 0;
            }
            else if (++this.g > 60) {
                return false;
            }
        }
        return true;
    }
    
    public void c() {
        this.b = 0;
        this.c = 0;
        this.g = 0;
    }
    
    public void d() {
        this.d.setGoalTarget(null);
    }
    
    protected boolean a(final EntityLiving entityliving, final boolean flag) {
        if (entityliving == null) {
            return false;
        }
        if (entityliving == this.d) {
            return false;
        }
        if (!entityliving.isAlive()) {
            return false;
        }
        if (!this.d.a(entityliving.getClass())) {
            return false;
        }
        if (this.d instanceof EntityTameableAnimal && ((EntityTameableAnimal)this.d).isTamed()) {
            if (entityliving instanceof EntityTameableAnimal && ((EntityTameableAnimal)entityliving).isTamed()) {
                return false;
            }
            if (entityliving == ((EntityTameableAnimal)this.d).getOwner()) {
                return false;
            }
        }
        else if (entityliving instanceof EntityHuman && !flag && ((EntityHuman)entityliving).abilities.isInvulnerable) {
            return false;
        }
        if (!this.d.d(MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ))) {
            return false;
        }
        if (this.f && !this.d.getEntitySenses().canSee(entityliving)) {
            return false;
        }
        if (this.a) {
            if (--this.c <= 0) {
                this.b = 0;
            }
            if (this.b == 0) {
                this.b = (this.a(entityliving) ? 1 : 2);
            }
            if (this.b == 2) {
                return false;
            }
        }
        EntityTargetEvent.TargetReason reason = EntityTargetEvent.TargetReason.RANDOM_TARGET;
        if (this instanceof PathfinderGoalDefendVillage) {
            reason = EntityTargetEvent.TargetReason.DEFEND_VILLAGE;
        }
        else if (this instanceof PathfinderGoalHurtByTarget) {
            reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY;
        }
        else if (this instanceof PathfinderGoalNearestAttackableTarget) {
            if (entityliving instanceof EntityHuman) {
                reason = EntityTargetEvent.TargetReason.CLOSEST_PLAYER;
            }
        }
        else if (this instanceof PathfinderGoalOwnerHurtByTarget) {
            reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER;
        }
        else if (this instanceof PathfinderGoalOwnerHurtTarget) {
            reason = EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET;
        }
        final EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(this.d, entityliving, reason);
        if (event.isCancelled() || event.getTarget() == null) {
            this.d.setGoalTarget(null);
            return false;
        }
        if (entityliving.getBukkitEntity() != event.getTarget()) {
            this.d.setGoalTarget((EntityLiving)((CraftEntity)event.getTarget()).getHandle());
        }
        if (this.d instanceof EntityCreature) {
            ((EntityCreature)this.d).target = ((CraftEntity)event.getTarget()).getHandle();
        }
        return true;
    }
    
    private boolean a(final EntityLiving entityliving) {
        this.c = 10 + this.d.aE().nextInt(5);
        final PathEntity pathentity = this.d.getNavigation().a(entityliving);
        if (pathentity == null) {
            return false;
        }
        final PathPoint pathpoint = pathentity.c();
        if (pathpoint == null) {
            return false;
        }
        final int i = pathpoint.a - MathHelper.floor(entityliving.locX);
        final int j = pathpoint.c - MathHelper.floor(entityliving.locZ);
        return i * i + j * j <= 2.25;
    }
}
