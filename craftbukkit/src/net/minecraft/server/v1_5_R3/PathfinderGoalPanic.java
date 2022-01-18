// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalPanic extends PathfinderGoal
{
    private EntityCreature a;
    private float b;
    private double c;
    private double d;
    private double e;
    
    public PathfinderGoalPanic(final EntityCreature a, final float b) {
        this.a = a;
        this.b = b;
        this.a(1);
    }
    
    public boolean a() {
        if (this.a.aF() == null && !this.a.isBurning()) {
            return false;
        }
        final Vec3D a = RandomPositionGenerator.a(this.a, 5, 4);
        if (a == null) {
            return false;
        }
        this.c = a.c;
        this.d = a.d;
        this.e = a.e;
        return true;
    }
    
    public void c() {
        this.a.getNavigation().a(this.c, this.d, this.e, this.b);
    }
    
    public boolean b() {
        return !this.a.getNavigation().f();
    }
}
