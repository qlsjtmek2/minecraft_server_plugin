// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.EntityEffect;
import java.util.UUID;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Server;
import java.util.List;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.metadata.Metadatable;

public interface Entity extends Metadatable
{
    Location getLocation();
    
    Location getLocation(final Location p0);
    
    void setVelocity(final Vector p0);
    
    Vector getVelocity();
    
    boolean isOnGround();
    
    World getWorld();
    
    boolean teleport(final Location p0);
    
    boolean teleport(final Location p0, final PlayerTeleportEvent.TeleportCause p1);
    
    boolean teleport(final Entity p0);
    
    boolean teleport(final Entity p0, final PlayerTeleportEvent.TeleportCause p1);
    
    List<Entity> getNearbyEntities(final double p0, final double p1, final double p2);
    
    int getEntityId();
    
    int getFireTicks();
    
    int getMaxFireTicks();
    
    void setFireTicks(final int p0);
    
    void remove();
    
    boolean isDead();
    
    boolean isValid();
    
    Server getServer();
    
    Entity getPassenger();
    
    boolean setPassenger(final Entity p0);
    
    boolean isEmpty();
    
    boolean eject();
    
    float getFallDistance();
    
    void setFallDistance(final float p0);
    
    void setLastDamageCause(final EntityDamageEvent p0);
    
    EntityDamageEvent getLastDamageCause();
    
    UUID getUniqueId();
    
    int getTicksLived();
    
    void setTicksLived(final int p0);
    
    void playEffect(final EntityEffect p0);
    
    EntityType getType();
    
    boolean isInsideVehicle();
    
    boolean leaveVehicle();
    
    Entity getVehicle();
}
