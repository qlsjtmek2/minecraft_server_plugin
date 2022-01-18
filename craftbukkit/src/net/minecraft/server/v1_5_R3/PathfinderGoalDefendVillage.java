// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget
{
    EntityIronGolem a;
    EntityLiving b;
    
    public PathfinderGoalDefendVillage(final EntityIronGolem entityIronGolem) {
        super(entityIronGolem, 16.0f, false, true);
        this.a = entityIronGolem;
        this.a(1);
    }
    
    public boolean a() {
        final Village m = this.a.m();
        if (m == null) {
            return false;
        }
        this.b = m.b(this.a);
        if (this.a(this.b, false)) {
            return true;
        }
        if (this.d.aE().nextInt(20) == 0) {
            this.b = m.c(this.a);
            return this.a(this.b, false);
        }
        return false;
    }
    
    public void c() {
        this.a.setGoalTarget(this.b);
        super.c();
    }
}
