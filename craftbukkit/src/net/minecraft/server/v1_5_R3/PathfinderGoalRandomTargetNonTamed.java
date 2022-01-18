// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalRandomTargetNonTamed extends PathfinderGoalNearestAttackableTarget
{
    private EntityTameableAnimal g;
    
    public PathfinderGoalRandomTargetNonTamed(final EntityTameableAnimal g, final Class clazz, final float n, final int n2, final boolean b) {
        super(g, clazz, n, n2, b);
        this.g = g;
    }
    
    public boolean a() {
        return !this.g.isTamed() && super.a();
    }
}
