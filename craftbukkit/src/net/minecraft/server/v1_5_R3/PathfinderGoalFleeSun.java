// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class PathfinderGoalFleeSun extends PathfinderGoal
{
    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private float e;
    private World f;
    
    public PathfinderGoalFleeSun(final EntityCreature a, final float e) {
        this.a = a;
        this.e = e;
        this.f = a.world;
        this.a(1);
    }
    
    public boolean a() {
        if (!this.f.v()) {
            return false;
        }
        if (!this.a.isBurning()) {
            return false;
        }
        if (!this.f.l(MathHelper.floor(this.a.locX), (int)this.a.boundingBox.b, MathHelper.floor(this.a.locZ))) {
            return false;
        }
        final Vec3D f = this.f();
        if (f == null) {
            return false;
        }
        this.b = f.c;
        this.c = f.d;
        this.d = f.e;
        return true;
    }
    
    public boolean b() {
        return !this.a.getNavigation().f();
    }
    
    public void c() {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }
    
    private Vec3D f() {
        final Random ae = this.a.aE();
        for (int i = 0; i < 10; ++i) {
            final int floor = MathHelper.floor(this.a.locX + ae.nextInt(20) - 10.0);
            final int floor2 = MathHelper.floor(this.a.boundingBox.b + ae.nextInt(6) - 3.0);
            final int floor3 = MathHelper.floor(this.a.locZ + ae.nextInt(20) - 10.0);
            if (!this.f.l(floor, floor2, floor3) && this.a.a(floor, floor2, floor3) < 0.0f) {
                return this.f.getVec3DPool().create(floor, floor2, floor3);
            }
        }
        return null;
    }
}
