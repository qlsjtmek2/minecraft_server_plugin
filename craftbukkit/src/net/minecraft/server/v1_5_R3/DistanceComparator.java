// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Comparator;

public class DistanceComparator implements Comparator
{
    private Entity b;
    final /* synthetic */ PathfinderGoalNearestAttackableTarget a;
    
    public DistanceComparator(final PathfinderGoalNearestAttackableTarget a, final Entity b) {
        this.a = a;
        this.b = b;
    }
    
    public int a(final Entity entity, final Entity entity2) {
        final double e = this.b.e(entity);
        final double e2 = this.b.e(entity2);
        if (e < e2) {
            return -1;
        }
        if (e > e2) {
            return 1;
        }
        return 0;
    }
}
