// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalSwell extends PathfinderGoal
{
    EntityCreeper a;
    EntityLiving b;
    
    public PathfinderGoalSwell(final EntityCreeper a) {
        this.a = a;
        this.a(1);
    }
    
    public boolean a() {
        final EntityLiving goalTarget = this.a.getGoalTarget();
        return this.a.o() > 0 || (goalTarget != null && this.a.e(goalTarget) < 9.0);
    }
    
    public void c() {
        this.a.getNavigation().g();
        this.b = this.a.getGoalTarget();
    }
    
    public void d() {
        this.b = null;
    }
    
    public void e() {
        if (this.b == null) {
            this.a.a(-1);
            return;
        }
        if (this.a.e(this.b) > 49.0) {
            this.a.a(-1);
            return;
        }
        if (!this.a.getEntitySenses().canSee(this.b)) {
            this.a.a(-1);
            return;
        }
        this.a.a(1);
    }
}
