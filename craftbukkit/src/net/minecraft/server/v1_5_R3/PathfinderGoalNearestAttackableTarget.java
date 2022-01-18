// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class PathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget
{
    EntityLiving a;
    Class b;
    int c;
    private final IEntitySelector g;
    private DistanceComparator h;
    
    public PathfinderGoalNearestAttackableTarget(final EntityLiving entityLiving, final Class clazz, final float n, final int n2, final boolean b) {
        this(entityLiving, clazz, n, n2, b, false);
    }
    
    public PathfinderGoalNearestAttackableTarget(final EntityLiving entityLiving, final Class clazz, final float n, final int n2, final boolean b, final boolean b2) {
        this(entityLiving, clazz, n, n2, b, b2, null);
    }
    
    public PathfinderGoalNearestAttackableTarget(final EntityLiving entityliving, final Class b, final float n, final int c, final boolean flag, final boolean flag2, final IEntitySelector g) {
        super(entityliving, n, flag, flag2);
        this.b = b;
        this.e = n;
        this.c = c;
        this.h = new DistanceComparator(this, entityliving);
        this.g = g;
        this.a(1);
    }
    
    public boolean a() {
        if (this.c > 0 && this.d.aE().nextInt(this.c) != 0) {
            return false;
        }
        if (this.b == EntityHuman.class) {
            final EntityHuman nearbyVulnerablePlayer = this.d.world.findNearbyVulnerablePlayer(this.d, this.e);
            if (this.a(nearbyVulnerablePlayer, false)) {
                this.a = nearbyVulnerablePlayer;
                return true;
            }
        }
        else {
            final List a = this.d.world.a(this.b, this.d.boundingBox.grow(this.e, 4.0, this.e), this.g);
            Collections.sort((List<Object>)a, this.h);
            for (final EntityLiving entityLiving : a) {
                if (this.a(entityLiving, false)) {
                    this.a = entityLiving;
                    return true;
                }
            }
        }
        return false;
    }
    
    public void c() {
        this.d.setGoalTarget(this.a);
        super.c();
    }
}
