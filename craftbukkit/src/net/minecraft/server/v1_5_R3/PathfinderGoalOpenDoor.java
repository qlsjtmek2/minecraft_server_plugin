// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalOpenDoor extends PathfinderGoalDoorInteract
{
    boolean i;
    int j;
    
    public PathfinderGoalOpenDoor(final EntityLiving a, final boolean i) {
        super(a);
        this.a = a;
        this.i = i;
    }
    
    public boolean b() {
        return this.i && this.j > 0 && super.b();
    }
    
    public void c() {
        this.j = 20;
        this.e.setDoor(this.a.world, this.b, this.c, this.d, true);
    }
    
    public void d() {
        if (this.i) {
            this.e.setDoor(this.a.world, this.b, this.c, this.d, false);
        }
    }
    
    public void e() {
        --this.j;
        super.e();
    }
}
