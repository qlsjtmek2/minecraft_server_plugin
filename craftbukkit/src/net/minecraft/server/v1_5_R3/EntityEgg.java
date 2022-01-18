// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.entity.Ageable;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;

public class EntityEgg extends EntityProjectile
{
    public EntityEgg(final World world) {
        super(world);
    }
    
    public EntityEgg(final World world, final EntityLiving entityliving) {
        super(world, entityliving);
    }
    
    public EntityEgg(final World world, final double d0, final double d1, final double d2) {
        super(world, d0, d1, d2);
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.entity != null) {
            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), 0);
        }
        boolean hatching = !this.world.isStatic && this.random.nextInt(8) == 0;
        int numHatching = (this.random.nextInt(32) == 0) ? 4 : 1;
        if (!hatching) {
            numHatching = 0;
        }
        EntityType hatchingType = EntityType.CHICKEN;
        final Entity shooter = this.getShooter();
        if (shooter instanceof EntityPlayer) {
            final Player player = (shooter == null) ? null : ((Player)shooter.getBukkitEntity());
            final PlayerEggThrowEvent event = new PlayerEggThrowEvent(player, (Egg)this.getBukkitEntity(), hatching, (byte)numHatching, hatchingType);
            this.world.getServer().getPluginManager().callEvent(event);
            hatching = event.isHatching();
            numHatching = event.getNumHatches();
            hatchingType = event.getHatchingType();
        }
        if (hatching) {
            for (int k = 0; k < numHatching; ++k) {
                final org.bukkit.entity.Entity entity = this.world.getWorld().spawn(new Location(this.world.getWorld(), this.locX, this.locY, this.locZ, this.yaw, 0.0f), hatchingType.getEntityClass(), CreatureSpawnEvent.SpawnReason.EGG);
                if (entity instanceof Ageable) {
                    ((Ageable)entity).setBaby();
                }
            }
        }
        for (int j = 0; j < 8; ++j) {
            this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0, 0.0, 0.0);
        }
        if (!this.world.isStatic) {
            this.die();
        }
    }
}
