// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.util.Vector;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Minecart;

public abstract class CraftMinecart extends CraftVehicle implements Minecart
{
    public CraftMinecart(final CraftServer server, final EntityMinecartAbstract entity) {
        super(server, entity);
    }
    
    public void setDamage(final int damage) {
        this.getHandle().setDamage(damage);
    }
    
    public int getDamage() {
        return this.getHandle().getDamage();
    }
    
    public double getMaxSpeed() {
        return this.getHandle().maxSpeed;
    }
    
    public void setMaxSpeed(final double speed) {
        if (speed >= 0.0) {
            this.getHandle().maxSpeed = speed;
        }
    }
    
    public boolean isSlowWhenEmpty() {
        return this.getHandle().slowWhenEmpty;
    }
    
    public void setSlowWhenEmpty(final boolean slow) {
        this.getHandle().slowWhenEmpty = slow;
    }
    
    public Vector getFlyingVelocityMod() {
        return this.getHandle().getFlyingVelocityMod();
    }
    
    public void setFlyingVelocityMod(final Vector flying) {
        this.getHandle().setFlyingVelocityMod(flying);
    }
    
    public Vector getDerailedVelocityMod() {
        return this.getHandle().getDerailedVelocityMod();
    }
    
    public void setDerailedVelocityMod(final Vector derailed) {
        this.getHandle().setDerailedVelocityMod(derailed);
    }
    
    public EntityMinecartAbstract getHandle() {
        return (EntityMinecartAbstract)this.entity;
    }
}
