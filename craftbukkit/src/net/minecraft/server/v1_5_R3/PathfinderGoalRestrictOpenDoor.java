// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalRestrictOpenDoor extends PathfinderGoal
{
    private EntityCreature a;
    private VillageDoor b;
    
    public PathfinderGoalRestrictOpenDoor(final EntityCreature a) {
        this.a = a;
    }
    
    public boolean a() {
        if (this.a.world.v()) {
            return false;
        }
        final Village closestVillage = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 16);
        if (closestVillage == null) {
            return false;
        }
        this.b = closestVillage.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
        return this.b != null && this.b.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ)) < 2.25;
    }
    
    public boolean b() {
        return !this.a.world.v() && !this.b.removed && this.b.a(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locZ));
    }
    
    public void c() {
        this.a.getNavigation().b(false);
        this.a.getNavigation().c(false);
    }
    
    public void d() {
        this.a.getNavigation().b(true);
        this.a.getNavigation().c(true);
        this.b = null;
    }
    
    public void e() {
        this.b.e();
    }
}
