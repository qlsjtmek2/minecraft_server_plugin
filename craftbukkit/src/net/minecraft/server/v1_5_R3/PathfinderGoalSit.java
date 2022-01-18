// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalSit extends PathfinderGoal
{
    private EntityTameableAnimal a;
    private boolean b;
    
    public PathfinderGoalSit(final EntityTameableAnimal entitytameableanimal) {
        this.b = false;
        this.a = entitytameableanimal;
        this.a(5);
    }
    
    public boolean a() {
        if (!this.a.isTamed()) {
            return this.b && this.a.getGoalTarget() == null;
        }
        if (this.a.G()) {
            return false;
        }
        if (!this.a.onGround) {
            return false;
        }
        final EntityLiving entityliving = this.a.getOwner();
        return entityliving == null || ((this.a.e(entityliving) >= 144.0 || entityliving.aF() == null) && this.b);
    }
    
    public void c() {
        this.a.getNavigation().g();
        this.a.setSitting(true);
    }
    
    public void d() {
        this.a.setSitting(false);
    }
    
    public void setSitting(final boolean flag) {
        this.b = flag;
    }
}
