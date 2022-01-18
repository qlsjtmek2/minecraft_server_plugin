// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalRestrictSun extends PathfinderGoal
{
    private EntityCreature a;
    
    public PathfinderGoalRestrictSun(final EntityCreature a) {
        this.a = a;
    }
    
    public boolean a() {
        return this.a.world.v();
    }
    
    public void c() {
        this.a.getNavigation().d(true);
    }
    
    public void d() {
        this.a.getNavigation().d(false);
    }
}
