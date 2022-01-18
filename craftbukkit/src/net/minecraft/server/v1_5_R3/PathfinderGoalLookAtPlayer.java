// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalLookAtPlayer extends PathfinderGoal
{
    private EntityLiving b;
    protected Entity a;
    private float c;
    private int d;
    private float e;
    private Class f;
    
    public PathfinderGoalLookAtPlayer(final EntityLiving b, final Class f, final float c) {
        this.b = b;
        this.f = f;
        this.c = c;
        this.e = 0.02f;
        this.a(2);
    }
    
    public PathfinderGoalLookAtPlayer(final EntityLiving b, final Class f, final float c, final float e) {
        this.b = b;
        this.f = f;
        this.c = c;
        this.e = e;
        this.a(2);
    }
    
    public boolean a() {
        if (this.b.aE().nextFloat() >= this.e) {
            return false;
        }
        if (this.f == EntityHuman.class) {
            this.a = this.b.world.findNearbyPlayer(this.b, this.c);
        }
        else {
            this.a = this.b.world.a(this.f, this.b.boundingBox.grow(this.c, 3.0, this.c), this.b);
        }
        return this.a != null;
    }
    
    public boolean b() {
        return this.a.isAlive() && this.b.e(this.a) <= this.c * this.c && this.d > 0;
    }
    
    public void c() {
        this.d = 40 + this.b.aE().nextInt(40);
    }
    
    public void d() {
        this.a = null;
    }
    
    public void e() {
        this.b.getControllerLook().a(this.a.locX, this.a.locY + this.a.getHeadHeight(), this.a.locZ, 10.0f, this.b.bs());
        --this.d;
    }
}
