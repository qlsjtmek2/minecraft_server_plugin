// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class PathfinderGoalAvoidPlayer extends PathfinderGoal
{
    public final IEntitySelector a;
    private EntityCreature b;
    private float c;
    private float d;
    private Entity e;
    private float f;
    private PathEntity g;
    private Navigation h;
    private Class i;
    
    public PathfinderGoalAvoidPlayer(final EntityCreature b, final Class i, final float f, final float c, final float d) {
        this.a = new EntitySelectorViewable(this);
        this.b = b;
        this.i = i;
        this.f = f;
        this.c = c;
        this.d = d;
        this.h = b.getNavigation();
        this.a(1);
    }
    
    public boolean a() {
        if (this.i == EntityHuman.class) {
            if (this.b instanceof EntityTameableAnimal && ((EntityTameableAnimal)this.b).isTamed()) {
                return false;
            }
            this.e = this.b.world.findNearbyPlayer(this.b, this.f);
            if (this.e == null) {
                return false;
            }
        }
        else {
            final List a = this.b.world.a(this.i, this.b.boundingBox.grow(this.f, 3.0, this.f), this.a);
            if (a.isEmpty()) {
                return false;
            }
            this.e = a.get(0);
        }
        final Vec3D b = RandomPositionGenerator.b(this.b, 16, 7, this.b.world.getVec3DPool().create(this.e.locX, this.e.locY, this.e.locZ));
        if (b == null) {
            return false;
        }
        if (this.e.e(b.c, b.d, b.e) < this.e.e(this.b)) {
            return false;
        }
        this.g = this.h.a(b.c, b.d, b.e);
        return this.g != null && this.g.b(b);
    }
    
    public boolean b() {
        return !this.h.f();
    }
    
    public void c() {
        this.h.a(this.g, this.c);
    }
    
    public void d() {
        this.e = null;
    }
    
    public void e() {
        if (this.b.e(this.e) < 49.0) {
            this.b.getNavigation().a(this.d);
        }
        else {
            this.b.getNavigation().a(this.c);
        }
    }
}
