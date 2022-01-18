// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalTradeWithPlayer extends PathfinderGoal
{
    private EntityVillager a;
    
    public PathfinderGoalTradeWithPlayer(final EntityVillager a) {
        this.a = a;
        this.a(5);
    }
    
    public boolean a() {
        if (!this.a.isAlive()) {
            return false;
        }
        if (this.a.G()) {
            return false;
        }
        if (!this.a.onGround) {
            return false;
        }
        if (this.a.velocityChanged) {
            return false;
        }
        final EntityHuman m_ = this.a.m_();
        return m_ != null && this.a.e(m_) <= 16.0 && m_.activeContainer instanceof Container;
    }
    
    public void c() {
        this.a.getNavigation().g();
    }
    
    public void d() {
        this.a.a((EntityHuman)null);
    }
}
