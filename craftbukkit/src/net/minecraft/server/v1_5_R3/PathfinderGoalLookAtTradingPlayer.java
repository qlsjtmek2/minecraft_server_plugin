// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalLookAtTradingPlayer extends PathfinderGoalLookAtPlayer
{
    private final EntityVillager b;
    
    public PathfinderGoalLookAtTradingPlayer(final EntityVillager b) {
        super(b, EntityHuman.class, 8.0f);
        this.b = b;
    }
    
    public boolean a() {
        if (this.b.p()) {
            this.a = this.b.m_();
            return true;
        }
        return false;
    }
}
