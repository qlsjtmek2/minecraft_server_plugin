// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private boolean a;
    private int b;
    
    public EntityMinecartHopper(final World world) {
        super(world);
        this.a = true;
        this.b = -1;
    }
    
    public EntityMinecartHopper(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
        this.a = true;
        this.b = -1;
    }
    
    public int getType() {
        return 5;
    }
    
    public Block n() {
        return Block.HOPPER;
    }
    
    public int r() {
        return 1;
    }
    
    public int getSize() {
        return 5;
    }
    
    public boolean a_(final EntityHuman entityHuman) {
        if (!this.world.isStatic) {
            entityHuman.openMinecartHopper(this);
        }
        return true;
    }
    
    public void a(final int n, final int n2, final int n3, final boolean b) {
        final boolean b2 = !b;
        if (b2 != this.ay()) {
            this.f(b2);
        }
    }
    
    public boolean ay() {
        return this.a;
    }
    
    public void f(final boolean a) {
        this.a = a;
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public double aA() {
        return this.locX;
    }
    
    public double aB() {
        return this.locY;
    }
    
    public double aC() {
        return this.locZ;
    }
    
    public void l_() {
        super.l_();
        if (!this.world.isStatic && this.isAlive() && this.ay()) {
            --this.b;
            if (!this.aE()) {
                this.n(0);
                if (this.aD()) {
                    this.n(4);
                    this.update();
                }
            }
        }
    }
    
    public boolean aD() {
        if (TileEntityHopper.suckInItems(this)) {
            return true;
        }
        final List a = this.world.a(EntityItem.class, this.boundingBox.grow(0.25, 0.0, 0.25), IEntitySelector.a);
        if (a.size() > 0) {
            TileEntityHopper.addEntityItem(this, a.get(0));
        }
        return false;
    }
    
    public void a(final DamageSource damagesource) {
        super.a(damagesource);
        this.a(Block.HOPPER.id, 1, 0.0f);
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("TransferCooldown", this.b);
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.b = nbttagcompound.getInt("TransferCooldown");
    }
    
    public void n(final int b) {
        this.b = b;
    }
    
    public boolean aE() {
        return this.b > 0;
    }
}
