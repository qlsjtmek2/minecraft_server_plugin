// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalTakeFlower extends PathfinderGoal
{
    private EntityVillager a;
    private EntityIronGolem b;
    private int c;
    private boolean d;
    
    public PathfinderGoalTakeFlower(final EntityVillager a) {
        this.d = false;
        this.a = a;
        this.a(3);
    }
    
    public boolean a() {
        if (this.a.getAge() >= 0) {
            return false;
        }
        if (!this.a.world.v()) {
            return false;
        }
        final List a = this.a.world.a(EntityIronGolem.class, this.a.boundingBox.grow(6.0, 2.0, 6.0));
        if (a.isEmpty()) {
            return false;
        }
        for (final EntityIronGolem b : a) {
            if (b.o() > 0) {
                this.b = b;
                break;
            }
        }
        return this.b != null;
    }
    
    public boolean b() {
        return this.b.o() > 0;
    }
    
    public void c() {
        this.c = this.a.aE().nextInt(320);
        this.d = false;
        this.b.getNavigation().g();
    }
    
    public void d() {
        this.b = null;
        this.a.getNavigation().g();
    }
    
    public void e() {
        this.a.getControllerLook().a(this.b, 30.0f, 30.0f);
        if (this.b.o() == this.c) {
            this.a.getNavigation().a(this.b, 0.15f);
            this.d = true;
        }
        if (this.d && this.a.e(this.b) < 4.0) {
            this.b.a(false);
            this.a.getNavigation().g();
        }
    }
}
