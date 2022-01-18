// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Explosive;

public class EntityLargeFireball extends EntityFireball
{
    public int e;
    
    public EntityLargeFireball(final World world) {
        super(world);
        this.e = 1;
    }
    
    public EntityLargeFireball(final World world, final EntityLiving entityliving, final double d0, final double d1, final double d2) {
        super(world, entityliving, d0, d1, d2);
        this.e = 1;
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (!this.world.isStatic) {
            if (movingobjectposition.entity != null) {
                movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 6);
            }
            final ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(this.world.getServer(), this));
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
            }
            this.die();
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("ExplosionPower", this.e);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("ExplosionPower")) {
            final int int1 = nbttagcompound.getInt("ExplosionPower");
            this.e = int1;
            this.yield = int1;
        }
    }
}
