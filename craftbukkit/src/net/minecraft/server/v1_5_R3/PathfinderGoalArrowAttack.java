// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public class PathfinderGoalArrowAttack extends PathfinderGoal
{
    private final EntityLiving a;
    private final IRangedEntity b;
    private EntityLiving c;
    private int d;
    private float e;
    private int f;
    private int g;
    private int h;
    private float i;
    private float j;
    
    public PathfinderGoalArrowAttack(final IRangedEntity irangedentity, final float f, final int i, final float f1) {
        this(irangedentity, f, i, i, f1);
    }
    
    public PathfinderGoalArrowAttack(final IRangedEntity irangedentity, final float f, final int i, final int j, final float f1) {
        this.d = -1;
        this.f = 0;
        if (!(irangedentity instanceof EntityLiving)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.b = irangedentity;
        this.a = (EntityLiving)irangedentity;
        this.e = f;
        this.g = i;
        this.h = j;
        this.i = f1;
        this.j = f1 * f1;
        this.a(3);
    }
    
    public boolean a() {
        final EntityLiving entityliving = this.a.getGoalTarget();
        if (entityliving == null) {
            return false;
        }
        this.c = entityliving;
        return true;
    }
    
    public boolean b() {
        return this.a() || !this.a.getNavigation().f();
    }
    
    public void d() {
        final EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        CraftEventFactory.callEntityTargetEvent((Entity)this.b, null, reason);
        this.c = null;
        this.f = 0;
        this.d = -1;
    }
    
    public void e() {
        final double d0 = this.a.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
        final boolean flag = this.a.getEntitySenses().canSee(this.c);
        if (flag) {
            ++this.f;
        }
        else {
            this.f = 0;
        }
        if (d0 <= this.j && this.f >= 20) {
            this.a.getNavigation().g();
        }
        else {
            this.a.getNavigation().a(this.c, this.e);
        }
        this.a.getControllerLook().a(this.c, 30.0f, 30.0f);
        final int d2 = this.d - 1;
        this.d = d2;
        if (d2 == 0) {
            if (d0 > this.j || !flag) {
                return;
            }
            float f2;
            final float f = f2 = MathHelper.sqrt(d0) / this.i;
            if (f < 0.1f) {
                f2 = 0.1f;
            }
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            this.b.a(this.c, f2);
            this.d = MathHelper.d(f * (this.h - this.g) + this.g);
        }
        else if (this.d < 0) {
            final float f = MathHelper.sqrt(d0) / this.i;
            this.d = MathHelper.d(f * (this.h - this.g) + this.g);
        }
    }
}
