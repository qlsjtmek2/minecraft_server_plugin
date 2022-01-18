// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalTempt extends PathfinderGoal
{
    private EntityCreature a;
    private float b;
    private double c;
    private double d;
    private double e;
    private double f;
    private double g;
    private EntityHuman h;
    private int i;
    private boolean j;
    private int k;
    private boolean l;
    private boolean m;
    
    public PathfinderGoalTempt(final EntityCreature a, final float b, final int k, final boolean l) {
        this.i = 0;
        this.a = a;
        this.b = b;
        this.k = k;
        this.l = l;
        this.a(3);
    }
    
    public boolean a() {
        if (this.i > 0) {
            --this.i;
            return false;
        }
        this.h = this.a.world.findNearbyPlayer(this.a, 10.0);
        if (this.h == null) {
            return false;
        }
        final ItemStack cd = this.h.cd();
        return cd != null && cd.id == this.k;
    }
    
    public boolean b() {
        if (this.l) {
            if (this.a.e(this.h) < 36.0) {
                if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002) {
                    return false;
                }
                if (Math.abs(this.h.pitch - this.f) > 5.0 || Math.abs(this.h.yaw - this.g) > 5.0) {
                    return false;
                }
            }
            else {
                this.c = this.h.locX;
                this.d = this.h.locY;
                this.e = this.h.locZ;
            }
            this.f = this.h.pitch;
            this.g = this.h.yaw;
        }
        return this.a();
    }
    
    public void c() {
        this.c = this.h.locX;
        this.d = this.h.locY;
        this.e = this.h.locZ;
        this.j = true;
        this.m = this.a.getNavigation().a();
        this.a.getNavigation().a(false);
    }
    
    public void d() {
        this.h = null;
        this.a.getNavigation().g();
        this.i = 100;
        this.j = false;
        this.a.getNavigation().a(this.m);
    }
    
    public void e() {
        this.a.getControllerLook().a(this.h, 30.0f, this.a.bs());
        if (this.a.e(this.h) < 6.25) {
            this.a.getNavigation().g();
        }
        else {
            this.a.getNavigation().a(this.h, this.b);
        }
    }
    
    public boolean f() {
        return this.j;
    }
}
