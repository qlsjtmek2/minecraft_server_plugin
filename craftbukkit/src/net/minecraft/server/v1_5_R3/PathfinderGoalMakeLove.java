// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalMakeLove extends PathfinderGoal
{
    private EntityVillager b;
    private EntityVillager c;
    private World d;
    private int e;
    Village a;
    
    public PathfinderGoalMakeLove(final EntityVillager b) {
        this.e = 0;
        this.b = b;
        this.d = b.world;
        this.a(3);
    }
    
    public boolean a() {
        if (this.b.getAge() != 0) {
            return false;
        }
        if (this.b.aE().nextInt(500) != 0) {
            return false;
        }
        this.a = this.d.villages.getClosestVillage(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.locY), MathHelper.floor(this.b.locZ), 0);
        if (this.a == null) {
            return false;
        }
        if (!this.f()) {
            return false;
        }
        final Entity a = this.d.a(EntityVillager.class, this.b.boundingBox.grow(8.0, 3.0, 8.0), this.b);
        if (a == null) {
            return false;
        }
        this.c = (EntityVillager)a;
        return this.c.getAge() == 0;
    }
    
    public void c() {
        this.e = 300;
        this.b.i(true);
    }
    
    public void d() {
        this.a = null;
        this.c = null;
        this.b.i(false);
    }
    
    public boolean b() {
        return this.e >= 0 && this.f() && this.b.getAge() == 0;
    }
    
    public void e() {
        --this.e;
        this.b.getControllerLook().a(this.c, 10.0f, 30.0f);
        if (this.b.e(this.c) > 2.25) {
            this.b.getNavigation().a(this.c, 0.25f);
        }
        else if (this.e == 0 && this.c.n()) {
            this.g();
        }
        if (this.b.aE().nextInt(35) == 0) {
            this.d.broadcastEntityEffect(this.b, (byte)12);
        }
    }
    
    private boolean f() {
        return this.a.i() && this.a.getPopulationCount() < (int)(this.a.getDoorCount() * 0.35);
    }
    
    private void g() {
        final EntityVillager b = this.b.b(this.c);
        this.c.setAge(6000);
        this.b.setAge(6000);
        b.setAge(-24000);
        b.setPositionRotation(this.b.locX, this.b.locY, this.b.locZ, 0.0f, 0.0f);
        this.d.addEntity(b);
        this.d.broadcastEntityEffect(b, (byte)12);
    }
}
