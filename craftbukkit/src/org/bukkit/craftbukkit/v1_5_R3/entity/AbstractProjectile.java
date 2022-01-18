// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile
{
    private boolean doesBounce;
    
    public AbstractProjectile(final CraftServer server, final net.minecraft.server.v1_5_R3.Entity entity) {
        super(server, entity);
        this.doesBounce = false;
    }
    
    public boolean doesBounce() {
        return this.doesBounce;
    }
    
    public void setBounce(final boolean doesBounce) {
        this.doesBounce = doesBounce;
    }
}
