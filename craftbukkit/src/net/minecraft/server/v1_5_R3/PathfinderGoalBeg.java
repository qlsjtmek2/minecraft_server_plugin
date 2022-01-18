// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalBeg extends PathfinderGoal
{
    private EntityWolf a;
    private EntityHuman b;
    private World c;
    private float d;
    private int e;
    
    public PathfinderGoalBeg(final EntityWolf a, final float d) {
        this.a = a;
        this.c = a.world;
        this.d = d;
        this.a(2);
    }
    
    public boolean a() {
        this.b = this.c.findNearbyPlayer(this.a, this.d);
        return this.b != null && this.a(this.b);
    }
    
    public boolean b() {
        return this.b.isAlive() && this.a.e(this.b) <= this.d * this.d && this.e > 0 && this.a(this.b);
    }
    
    public void c() {
        this.a.m(true);
        this.e = 40 + this.a.aE().nextInt(40);
    }
    
    public void d() {
        this.a.m(false);
        this.b = null;
    }
    
    public void e() {
        this.a.getControllerLook().a(this.b.locX, this.b.locY + this.b.getHeadHeight(), this.b.locZ, 10.0f, this.a.bs());
        --this.e;
    }
    
    private boolean a(final EntityHuman entityHuman) {
        final ItemStack itemInHand = entityHuman.inventory.getItemInHand();
        return itemInHand != null && ((!this.a.isTamed() && itemInHand.id == Item.BONE.id) || this.a.c(itemInHand));
    }
}
