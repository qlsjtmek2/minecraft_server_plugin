// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class PathfinderGoalMoveThroughVillage extends PathfinderGoal
{
    private EntityCreature a;
    private float b;
    private PathEntity c;
    private VillageDoor d;
    private boolean e;
    private List f;
    
    public PathfinderGoalMoveThroughVillage(final EntityCreature a, final float b, final boolean e) {
        this.f = new ArrayList();
        this.a = a;
        this.b = b;
        this.e = e;
        this.a(1);
    }
    
    public boolean a() {
        this.f();
        if (this.e && this.a.world.v()) {
            return false;
        }
        final Village closestVillage = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 0);
        if (closestVillage == null) {
            return false;
        }
        this.d = this.a(closestVillage);
        if (this.d == null) {
            return false;
        }
        final boolean c = this.a.getNavigation().c();
        this.a.getNavigation().b(false);
        this.c = this.a.getNavigation().a(this.d.locX, this.d.locY, this.d.locZ);
        this.a.getNavigation().b(c);
        if (this.c != null) {
            return true;
        }
        final Vec3D a = RandomPositionGenerator.a(this.a, 10, 7, this.a.world.getVec3DPool().create(this.d.locX, this.d.locY, this.d.locZ));
        if (a == null) {
            return false;
        }
        this.a.getNavigation().b(false);
        this.c = this.a.getNavigation().a(a.c, a.d, a.e);
        this.a.getNavigation().b(c);
        return this.c != null;
    }
    
    public boolean b() {
        if (this.a.getNavigation().f()) {
            return false;
        }
        final float n = this.a.width + 4.0f;
        return this.a.e(this.d.locX, this.d.locY, this.d.locZ) > n * n;
    }
    
    public void c() {
        this.a.getNavigation().a(this.c, this.b);
    }
    
    public void d() {
        if (this.a.getNavigation().f() || this.a.e(this.d.locX, this.d.locY, this.d.locZ) < 16.0) {
            this.f.add(this.d);
        }
    }
    
    private VillageDoor a(final Village village) {
        VillageDoor villageDoor = null;
        int n = Integer.MAX_VALUE;
        for (final VillageDoor villageDoor2 : village.getDoors()) {
            final int b = villageDoor2.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
            if (b < n) {
                if (this.a(villageDoor2)) {
                    continue;
                }
                villageDoor = villageDoor2;
                n = b;
            }
        }
        return villageDoor;
    }
    
    private boolean a(final VillageDoor villageDoor) {
        for (final VillageDoor villageDoor2 : this.f) {
            if (villageDoor.locX == villageDoor2.locX && villageDoor.locY == villageDoor2.locY && villageDoor.locZ == villageDoor2.locZ) {
                return true;
            }
        }
        return false;
    }
    
    private void f() {
        if (this.f.size() > 15) {
            this.f.remove(0);
        }
    }
}
