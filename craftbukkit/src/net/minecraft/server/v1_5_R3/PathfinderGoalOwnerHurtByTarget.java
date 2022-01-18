// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalOwnerHurtByTarget extends PathfinderGoalTarget
{
    EntityTameableAnimal a;
    EntityLiving b;
    
    public PathfinderGoalOwnerHurtByTarget(final EntityTameableAnimal entityTameableAnimal) {
        super(entityTameableAnimal, 32.0f, false);
        this.a = entityTameableAnimal;
        this.a(1);
    }
    
    public boolean a() {
        if (!this.a.isTamed()) {
            return false;
        }
        final EntityLiving owner = this.a.getOwner();
        if (owner == null) {
            return false;
        }
        this.b = owner.aF();
        return this.a(this.b, false);
    }
    
    public void c() {
        this.d.setGoalTarget(this.b);
        super.c();
    }
}
