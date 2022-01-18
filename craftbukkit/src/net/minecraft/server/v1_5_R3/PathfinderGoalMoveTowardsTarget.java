// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalMoveTowardsTarget extends PathfinderGoal
{
    private EntityCreature a;
    private EntityLiving b;
    private double c;
    private double d;
    private double e;
    private float f;
    private float g;
    
    public PathfinderGoalMoveTowardsTarget(final EntityCreature a, final float f, final float g) {
        this.a = a;
        this.f = f;
        this.g = g;
        this.a(1);
    }
    
    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (this.b == null) {
            return false;
        }
        if (this.b.e(this.a) > this.g * this.g) {
            return false;
        }
        final Vec3D a = RandomPositionGenerator.a(this.a, 16, 7, this.a.world.getVec3DPool().create(this.b.locX, this.b.locY, this.b.locZ));
        if (a == null) {
            return false;
        }
        this.c = a.c;
        this.d = a.d;
        this.e = a.e;
        return true;
    }
    
    public boolean b() {
        return !this.a.getNavigation().f() && this.b.isAlive() && this.b.e(this.a) < this.g * this.g;
    }
    
    public void d() {
        this.b = null;
    }
    
    public void c() {
        this.a.getNavigation().a(this.c, this.d, this.e, this.f);
    }
}
