// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract
{
    private int i;
    private int j;
    
    public PathfinderGoalBreakDoor(final EntityLiving entityliving) {
        super(entityliving);
        this.j = -1;
    }
    
    public boolean a() {
        return super.a() && this.a.world.getGameRules().getBoolean("mobGriefing") && !this.e.b_(this.a.world, this.b, this.c, this.d);
    }
    
    public void c() {
        super.c();
        this.i = 0;
    }
    
    public boolean b() {
        final double d0 = this.a.e(this.b, this.c, this.d);
        return this.i <= 240 && !this.e.b_(this.a.world, this.b, this.c, this.d) && d0 < 4.0;
    }
    
    public void d() {
        super.d();
        this.a.world.f(this.a.id, this.b, this.c, this.d, -1);
    }
    
    public void e() {
        super.e();
        if (this.a.aE().nextInt(20) == 0) {
            this.a.world.triggerEffect(1010, this.b, this.c, this.d, 0);
        }
        ++this.i;
        final int i = (int)(this.i / 240.0f * 10.0f);
        if (i != this.j) {
            this.a.world.f(this.a.id, this.b, this.c, this.d, i);
            this.j = i;
        }
        if (this.i == 240 && this.a.world.difficulty == 3) {
            if (CraftEventFactory.callEntityBreakDoorEvent(this.a, this.b, this.c, this.d).isCancelled()) {
                this.e();
                return;
            }
            this.a.world.setAir(this.b, this.c, this.d);
            this.a.world.triggerEffect(1012, this.b, this.c, this.d, 0);
            this.a.world.triggerEffect(2001, this.b, this.c, this.d, this.e.id);
        }
    }
}
