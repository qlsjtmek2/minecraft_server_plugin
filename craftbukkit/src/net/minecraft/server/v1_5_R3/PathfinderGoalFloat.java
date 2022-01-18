// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalFloat extends PathfinderGoal
{
    private EntityLiving a;
    
    public PathfinderGoalFloat(final EntityLiving a) {
        this.a = a;
        this.a(4);
        a.getNavigation().e(true);
    }
    
    public boolean a() {
        return this.a.G() || this.a.I();
    }
    
    public void e() {
        if (this.a.aE().nextFloat() < 0.8f) {
            this.a.getControllerJump().a();
        }
    }
}
