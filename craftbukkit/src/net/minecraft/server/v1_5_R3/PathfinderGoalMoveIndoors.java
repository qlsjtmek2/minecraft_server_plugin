// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalMoveIndoors extends PathfinderGoal
{
    private EntityCreature a;
    private VillageDoor b;
    private int c;
    private int d;
    
    public PathfinderGoalMoveIndoors(final EntityCreature a) {
        this.c = -1;
        this.d = -1;
        this.a = a;
        this.a(1);
    }
    
    public boolean a() {
        if ((this.a.world.v() && !this.a.world.P()) || this.a.world.worldProvider.f) {
            return false;
        }
        if (this.a.aE().nextInt(50) != 0) {
            return false;
        }
        if (this.c != -1 && this.a.e(this.c, this.a.locY, this.d) < 4.0) {
            return false;
        }
        final Village closestVillage = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 14);
        if (closestVillage == null) {
            return false;
        }
        this.b = closestVillage.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
        return this.b != null;
    }
    
    public boolean b() {
        return !this.a.getNavigation().f();
    }
    
    public void c() {
        this.c = -1;
        if (this.a.e(this.b.getIndoorsX(), this.b.locY, this.b.getIndoorsZ()) > 256.0) {
            final Vec3D a = RandomPositionGenerator.a(this.a, 14, 3, this.a.world.getVec3DPool().create(this.b.getIndoorsX() + 0.5, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5));
            if (a != null) {
                this.a.getNavigation().a(a.c, a.d, a.e, 0.3f);
            }
        }
        else {
            this.a.getNavigation().a(this.b.getIndoorsX() + 0.5, this.b.getIndoorsY(), this.b.getIndoorsZ() + 0.5, 0.3f);
        }
    }
    
    public void d() {
        this.c = this.b.getIndoorsX();
        this.d = this.b.getIndoorsZ();
        this.b = null;
    }
}
