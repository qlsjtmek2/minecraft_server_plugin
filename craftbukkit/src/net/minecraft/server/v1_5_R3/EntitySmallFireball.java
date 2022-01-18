// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.entity.Projectile;

public class EntitySmallFireball extends EntityFireball
{
    public EntitySmallFireball(final World world) {
        super(world);
        this.a(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World world, final EntityLiving entityliving, final double d0, final double d1, final double d2) {
        super(world, entityliving, d0, d1, d2);
        this.a(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World world, final double d0, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d0, d1, d2, d3, d4, d5);
        this.a(0.3125f, 0.3125f);
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (!this.world.isStatic) {
            if (movingobjectposition.entity != null) {
                if (!movingobjectposition.entity.isFireproof() && movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5)) {
                    final EntityCombustByEntityEvent event = new EntityCombustByEntityEvent(this.getBukkitEntity(), movingobjectposition.entity.getBukkitEntity(), 5);
                    movingobjectposition.entity.world.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        movingobjectposition.entity.setOnFire(event.getDuration());
                    }
                }
            }
            else {
                int i = movingobjectposition.b;
                int j = movingobjectposition.c;
                int k = movingobjectposition.d;
                switch (movingobjectposition.face) {
                    case 0: {
                        --j;
                        break;
                    }
                    case 1: {
                        ++j;
                        break;
                    }
                    case 2: {
                        --k;
                        break;
                    }
                    case 3: {
                        ++k;
                        break;
                    }
                    case 4: {
                        --i;
                        break;
                    }
                    case 5: {
                        ++i;
                        break;
                    }
                }
                if (this.world.isEmpty(i, j, k) && !CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
                    this.world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
                }
            }
            this.die();
        }
    }
    
    public boolean K() {
        return false;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        return false;
    }
}
