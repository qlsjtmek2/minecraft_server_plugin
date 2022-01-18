// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class PathfinderGoal
{
    private int a;
    
    public PathfinderGoal() {
        this.a = 0;
    }
    
    public abstract boolean a();
    
    public boolean b() {
        return this.a();
    }
    
    public boolean i() {
        return true;
    }
    
    public void c() {
    }
    
    public void d() {
    }
    
    public void e() {
    }
    
    public void a(final int a) {
        this.a = a;
    }
    
    public int j() {
        return this.a;
    }
}
