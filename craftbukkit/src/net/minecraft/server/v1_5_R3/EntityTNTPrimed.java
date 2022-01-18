// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Explosive;

public class EntityTNTPrimed extends Entity
{
    public int fuseTicks;
    private EntityLiving source;
    public float yield;
    public boolean isIncendiary;
    
    public EntityTNTPrimed(final World world) {
        super(world);
        this.yield = 4.0f;
        this.isIncendiary = false;
        this.fuseTicks = 0;
        this.m = true;
        this.a(0.98f, 0.98f);
        this.height = this.length / 2.0f;
    }
    
    public EntityTNTPrimed(final World world, final double d0, final double d1, final double d2, final EntityLiving entityliving) {
        this(world);
        this.setPosition(d0, d1, d2);
        final float f = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.motX = -(float)Math.sin(f) * 0.02f;
        this.motY = 0.20000000298023224;
        this.motZ = -(float)Math.cos(f) * 0.02f;
        this.fuseTicks = 80;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
        this.source = entityliving;
    }
    
    protected void a() {
    }
    
    protected boolean f_() {
        return false;
    }
    
    public boolean K() {
        return !this.dead;
    }
    
    public void l_() {
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033;
        this.move(this.motX, this.motY, this.motZ);
        this.motX *= 0.9800000190734863;
        this.motY *= 0.9800000190734863;
        this.motZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motX *= 0.699999988079071;
            this.motZ *= 0.699999988079071;
            this.motY *= -0.5;
        }
        if (this.fuseTicks-- <= 0) {
            if (!this.world.isStatic) {
                this.explode();
            }
            this.die();
        }
        else {
            this.world.addParticle("smoke", this.locX, this.locY + 0.5, this.locZ, 0.0, 0.0, 0.0);
        }
    }
    
    private void explode() {
        final CraftServer server = this.world.getServer();
        final ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(server, this));
        server.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), true);
        }
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("Fuse", (byte)this.fuseTicks);
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
        this.fuseTicks = nbttagcompound.getByte("Fuse");
    }
    
    public EntityLiving getSource() {
        return this.source;
    }
}
