// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class PathfinderGoalDoorInteract extends PathfinderGoal
{
    protected EntityLiving a;
    protected int b;
    protected int c;
    protected int d;
    protected BlockDoor e;
    boolean f;
    float g;
    float h;
    
    public PathfinderGoalDoorInteract(final EntityLiving a) {
        this.a = a;
    }
    
    public boolean a() {
        if (!this.a.positionChanged) {
            return false;
        }
        final Navigation navigation = this.a.getNavigation();
        final PathEntity d = navigation.d();
        if (d == null || d.b() || !navigation.c()) {
            return false;
        }
        for (int i = 0; i < Math.min(d.e() + 2, d.d()); ++i) {
            final PathPoint a = d.a(i);
            this.b = a.a;
            this.c = a.b + 1;
            this.d = a.c;
            if (this.a.e(this.b, this.a.locY, this.d) <= 2.25) {
                this.e = this.a(this.b, this.c, this.d);
                if (this.e != null) {
                    return true;
                }
            }
        }
        this.b = MathHelper.floor(this.a.locX);
        this.c = MathHelper.floor(this.a.locY + 1.0);
        this.d = MathHelper.floor(this.a.locZ);
        this.e = this.a(this.b, this.c, this.d);
        return this.e != null;
    }
    
    public boolean b() {
        return !this.f;
    }
    
    public void c() {
        this.f = false;
        this.g = (float)(this.b + 0.5f - this.a.locX);
        this.h = (float)(this.d + 0.5f - this.a.locZ);
    }
    
    public void e() {
        if (this.g * (float)(this.b + 0.5f - this.a.locX) + this.h * (float)(this.d + 0.5f - this.a.locZ) < 0.0f) {
            this.f = true;
        }
    }
    
    private BlockDoor a(final int i, final int j, final int k) {
        final int typeId = this.a.world.getTypeId(i, j, k);
        if (typeId != Block.WOODEN_DOOR.id) {
            return null;
        }
        return (BlockDoor)Block.byId[typeId];
    }
}
