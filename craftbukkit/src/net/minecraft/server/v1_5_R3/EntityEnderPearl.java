// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EntityEnderPearl extends EntityProjectile
{
    public EntityEnderPearl(final World world) {
        super(world);
    }
    
    public EntityEnderPearl(final World world, final EntityLiving entityliving) {
        super(world, entityliving);
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.entity != null) {
            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), 0);
        }
        for (int i = 0; i < 32; ++i) {
            this.world.addParticle("portal", this.locX, this.locY + this.random.nextDouble() * 2.0, this.locZ, this.random.nextGaussian(), 0.0, this.random.nextGaussian());
        }
        if (!this.world.isStatic) {
            if (this.getShooter() != null && this.getShooter() instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)this.getShooter();
                if (!entityplayer.playerConnection.disconnected && entityplayer.world == this.world) {
                    final CraftPlayer player = entityplayer.getBukkitEntity();
                    final Location location = this.getBukkitEntity().getLocation();
                    location.setPitch(player.getLocation().getPitch());
                    location.setYaw(player.getLocation().getYaw());
                    final PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent(teleEvent);
                    if (!teleEvent.isCancelled() && !entityplayer.playerConnection.disconnected) {
                        entityplayer.playerConnection.teleport(teleEvent.getTo());
                        this.getShooter().fallDistance = 0.0f;
                        final EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent(this.getBukkitEntity(), player, EntityDamageEvent.DamageCause.FALL, 5);
                        Bukkit.getPluginManager().callEvent(damageEvent);
                        if (!damageEvent.isCancelled() && !entityplayer.playerConnection.disconnected) {
                            entityplayer.invulnerableTicks = -1;
                            player.setLastDamageCause(damageEvent);
                            entityplayer.damageEntity(DamageSource.FALL, damageEvent.getDamage());
                        }
                    }
                }
            }
            this.die();
        }
    }
}
