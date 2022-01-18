// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget
{
    boolean a;
    EntityLiving b;
    
    public PathfinderGoalHurtByTarget(final EntityLiving entityliving, final boolean a) {
        super(entityliving, 16.0f, false);
        this.a = a;
        this.a(1);
    }
    
    public boolean a() {
        return this.a(this.d.aF(), true);
    }
    
    public boolean b() {
        return this.d.aF() != null && this.d.aF() != this.b;
    }
    
    public void c() {
        this.d.setGoalTarget(this.d.aF());
        this.b = this.d.aF();
        if (this.a) {
            for (final EntityLiving entityLiving : this.d.world.a(this.d.getClass(), AxisAlignedBB.a().a(this.d.locX, this.d.locY, this.d.locZ, this.d.locX + 1.0, this.d.locY + 1.0, this.d.locZ + 1.0).grow(this.e, 10.0, this.e))) {
                if (this.d == entityLiving) {
                    continue;
                }
                if (entityLiving.getGoalTarget() != null) {
                    continue;
                }
                entityLiving.setGoalTarget(this.d.aF());
            }
        }
        super.c();
    }
    
    public void d() {
        if (this.d.getGoalTarget() != null && this.d.getGoalTarget() instanceof EntityHuman && ((EntityHuman)this.d.getGoalTarget()).abilities.isInvulnerable) {
            super.d();
        }
    }
}
