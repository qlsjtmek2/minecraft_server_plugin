// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalMoveTowardsRestriction extends PathfinderGoal
{
    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private float e;
    
    public PathfinderGoalMoveTowardsRestriction(final EntityCreature a, final float e) {
        this.a = a;
        this.e = e;
        this.a(1);
    }
    
    public boolean a() {
        if (this.a.aL()) {
            return false;
        }
        final ChunkCoordinates am = this.a.aM();
        final Vec3D a = RandomPositionGenerator.a(this.a, 16, 7, this.a.world.getVec3DPool().create(am.x, am.y, am.z));
        if (a == null) {
            return false;
        }
        this.b = a.c;
        this.c = a.d;
        this.d = a.e;
        return true;
    }
    
    public boolean b() {
        return !this.a.getNavigation().f();
    }
    
    public void c() {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }
}
