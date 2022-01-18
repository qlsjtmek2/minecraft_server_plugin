// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalPlay extends PathfinderGoal
{
    private EntityVillager a;
    private EntityLiving b;
    private float c;
    private int d;
    
    public PathfinderGoalPlay(final EntityVillager a, final float c) {
        this.a = a;
        this.c = c;
        this.a(1);
    }
    
    public boolean a() {
        if (this.a.getAge() >= 0) {
            return false;
        }
        if (this.a.aE().nextInt(400) != 0) {
            return false;
        }
        final List a = this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0, 3.0, 6.0));
        double n = Double.MAX_VALUE;
        for (final EntityVillager b : a) {
            if (b == this.a) {
                continue;
            }
            if (b.o()) {
                continue;
            }
            if (b.getAge() >= 0) {
                continue;
            }
            final double e = b.e(this.a);
            if (e > n) {
                continue;
            }
            n = e;
            this.b = b;
        }
        return this.b != null || RandomPositionGenerator.a(this.a, 16, 3) != null;
    }
    
    public boolean b() {
        return this.d > 0;
    }
    
    public void c() {
        if (this.b != null) {
            this.a.j(true);
        }
        this.d = 1000;
    }
    
    public void d() {
        this.a.j(false);
        this.b = null;
    }
    
    public void e() {
        --this.d;
        if (this.b != null) {
            if (this.a.e(this.b) > 4.0) {
                this.a.getNavigation().a(this.b, this.c);
            }
        }
        else if (this.a.getNavigation().f()) {
            final Vec3D a = RandomPositionGenerator.a(this.a, 16, 3);
            if (a == null) {
                return;
            }
            this.a.getNavigation().a(a.c, a.d, a.e, this.c);
        }
    }
}
