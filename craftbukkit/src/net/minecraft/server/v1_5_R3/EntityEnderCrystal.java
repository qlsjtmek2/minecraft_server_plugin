// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityEnderCrystal extends Entity
{
    public int a;
    public int b;
    
    public EntityEnderCrystal(final World world) {
        super(world);
        this.a = 0;
        this.m = true;
        this.a(2.0f, 2.0f);
        this.height = this.length / 2.0f;
        this.b = 5;
        this.a = this.random.nextInt(100000);
    }
    
    protected boolean f_() {
        return false;
    }
    
    protected void a() {
        this.datawatcher.a(8, (Object)this.b);
    }
    
    public void l_() {
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        ++this.a;
        this.datawatcher.watch(8, this.b);
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.floor(this.locY);
        final int k = MathHelper.floor(this.locZ);
        if (this.world.getTypeId(i, j, k) != Block.FIRE.id && !CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
            this.world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
        }
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
    }
    
    public boolean K() {
        return true;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (!this.dead && !this.world.isStatic) {
            if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, i)) {
                return false;
            }
            this.b = 0;
            if (this.b <= 0) {
                this.die();
                if (!this.world.isStatic) {
                    this.world.explode(this, this.locX, this.locY, this.locZ, 6.0f, true);
                }
            }
        }
        return true;
    }
}
