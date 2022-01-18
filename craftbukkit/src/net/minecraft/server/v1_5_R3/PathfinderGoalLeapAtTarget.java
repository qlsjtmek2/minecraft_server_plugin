// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalLeapAtTarget extends PathfinderGoal
{
    EntityLiving a;
    EntityLiving b;
    float c;
    
    public PathfinderGoalLeapAtTarget(final EntityLiving a, final float c) {
        this.a = a;
        this.c = c;
        this.a(5);
    }
    
    public boolean a() {
        this.b = this.a.getGoalTarget();
        if (this.b == null) {
            return false;
        }
        final double e = this.a.e(this.b);
        return e >= 4.0 && e <= 16.0 && this.a.onGround && this.a.aE().nextInt(5) == 0;
    }
    
    public boolean b() {
        return !this.a.onGround;
    }
    
    public void c() {
        final double n = this.b.locX - this.a.locX;
        final double n2 = this.b.locZ - this.a.locZ;
        final float sqrt = MathHelper.sqrt(n * n + n2 * n2);
        final EntityLiving a = this.a;
        a.motX += n / sqrt * 0.5 * 0.800000011920929 + this.a.motX * 0.20000000298023224;
        final EntityLiving a2 = this.a;
        a2.motZ += n2 / sqrt * 0.5 * 0.800000011920929 + this.a.motZ * 0.20000000298023224;
        this.a.motY = this.c;
    }
}
