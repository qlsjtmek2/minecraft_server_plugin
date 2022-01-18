// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalFollowParent extends PathfinderGoal
{
    EntityAnimal a;
    EntityAnimal b;
    float c;
    private int d;
    
    public PathfinderGoalFollowParent(final EntityAnimal a, final float c) {
        this.a = a;
        this.c = c;
    }
    
    public boolean a() {
        if (this.a.getAge() >= 0) {
            return false;
        }
        final List a = this.a.world.a(this.a.getClass(), this.a.boundingBox.grow(8.0, 4.0, 8.0));
        EntityAnimal b = null;
        double n = Double.MAX_VALUE;
        for (final EntityAnimal entity : a) {
            if (entity.getAge() < 0) {
                continue;
            }
            final double e = this.a.e(entity);
            if (e > n) {
                continue;
            }
            n = e;
            b = entity;
        }
        if (b == null) {
            return false;
        }
        if (n < 9.0) {
            return false;
        }
        this.b = b;
        return true;
    }
    
    public boolean b() {
        if (!this.b.isAlive()) {
            return false;
        }
        final double e = this.a.e(this.b);
        return e >= 9.0 && e <= 256.0;
    }
    
    public void c() {
        this.d = 0;
    }
    
    public void d() {
        this.b = null;
    }
    
    public void e() {
        final int d = this.d - 1;
        this.d = d;
        if (d > 0) {
            return;
        }
        this.d = 10;
        this.a.getNavigation().a(this.b, this.c);
    }
}
