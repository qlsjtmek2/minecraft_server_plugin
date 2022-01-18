// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalRandomLookaround extends PathfinderGoal
{
    private EntityLiving a;
    private double b;
    private double c;
    private int d;
    
    public PathfinderGoalRandomLookaround(final EntityLiving a) {
        this.d = 0;
        this.a = a;
        this.a(3);
    }
    
    public boolean a() {
        return this.a.aE().nextFloat() < 0.02f;
    }
    
    public boolean b() {
        return this.d >= 0;
    }
    
    public void c() {
        final double n = 6.283185307179586 * this.a.aE().nextDouble();
        this.b = Math.cos(n);
        this.c = Math.sin(n);
        this.d = 20 + this.a.aE().nextInt(20);
    }
    
    public void e() {
        --this.d;
        this.a.getControllerLook().a(this.a.locX + this.b, this.a.locY + this.a.getHeadHeight(), this.a.locZ + this.c, 10.0f, this.a.bs());
    }
}
