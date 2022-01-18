// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public class PathfinderGoalMeleeAttack extends PathfinderGoal
{
    World a;
    EntityLiving b;
    EntityLiving c;
    int d;
    float e;
    boolean f;
    PathEntity g;
    Class h;
    private int i;
    
    public PathfinderGoalMeleeAttack(final EntityLiving entityliving, final Class oclass, final float f, final boolean flag) {
        this(entityliving, f, flag);
        this.h = oclass;
    }
    
    public PathfinderGoalMeleeAttack(final EntityLiving entityliving, final float f, final boolean flag) {
        this.d = 0;
        this.b = entityliving;
        this.a = entityliving.world;
        this.e = f;
        this.f = flag;
        this.a(3);
    }
    
    public boolean a() {
        final EntityLiving entityliving = this.b.getGoalTarget();
        if (entityliving == null) {
            return false;
        }
        if (this.h != null && !this.h.isAssignableFrom(entityliving.getClass())) {
            return false;
        }
        this.c = entityliving;
        this.g = this.b.getNavigation().a(this.c);
        return this.g != null;
    }
    
    public boolean b() {
        final EntityLiving entityliving = this.b.getGoalTarget();
        return entityliving != null && this.c.isAlive() && (this.f ? this.b.d(MathHelper.floor(this.c.locX), MathHelper.floor(this.c.locY), MathHelper.floor(this.c.locZ)) : (!this.b.getNavigation().f()));
    }
    
    public void c() {
        this.b.getNavigation().a(this.g, this.e);
        this.i = 0;
    }
    
    public void d() {
        final EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        CraftEventFactory.callEntityTargetEvent(this.b, null, reason);
        this.c = null;
        this.b.getNavigation().g();
    }
    
    public void e() {
        this.b.getControllerLook().a(this.c, 30.0f, 30.0f);
        if ((this.f || this.b.getEntitySenses().canSee(this.c)) && --this.i <= 0) {
            this.i = 4 + this.b.aE().nextInt(7);
            this.b.getNavigation().a(this.c, this.e);
        }
        this.d = Math.max(this.d - 1, 0);
        final double d0 = this.b.width * 2.0f * this.b.width * 2.0f;
        if (this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ) <= d0 && this.d <= 0) {
            this.d = 20;
            if (this.b.bG() != null) {
                this.b.bK();
            }
            this.b.m(this.c);
        }
    }
}
