// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalOcelotAttack extends PathfinderGoal
{
    World a;
    EntityLiving b;
    EntityLiving c;
    int d;
    
    public PathfinderGoalOcelotAttack(final EntityLiving b) {
        this.d = 0;
        this.b = b;
        this.a = b.world;
        this.a(3);
    }
    
    public boolean a() {
        final EntityLiving goalTarget = this.b.getGoalTarget();
        if (goalTarget == null) {
            return false;
        }
        this.c = goalTarget;
        return true;
    }
    
    public boolean b() {
        return this.c.isAlive() && this.b.e(this.c) <= 225.0 && (!this.b.getNavigation().f() || this.a());
    }
    
    public void d() {
        this.c = null;
        this.b.getNavigation().g();
    }
    
    public void e() {
        this.b.getControllerLook().a(this.c, 30.0f, 30.0f);
        final double n = this.b.width * 2.0f * (this.b.width * 2.0f);
        final double e = this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
        float n2 = 0.23f;
        if (e > n && e < 16.0) {
            n2 = 0.4f;
        }
        else if (e < 225.0) {
            n2 = 0.18f;
        }
        this.b.getNavigation().a(this.c, n2);
        this.d = Math.max(this.d - 1, 0);
        if (e > n) {
            return;
        }
        if (this.d > 0) {
            return;
        }
        this.d = 20;
        this.b.m(this.c);
    }
}
