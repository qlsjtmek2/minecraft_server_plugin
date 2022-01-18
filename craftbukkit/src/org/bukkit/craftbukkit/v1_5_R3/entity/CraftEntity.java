// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.EntityEffect;
import java.util.UUID;
import org.bukkit.Server;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import net.minecraft.server.v1_5_R3.EntityFireworks;
import net.minecraft.server.v1_5_R3.EntityTNTPrimed;
import net.minecraft.server.v1_5_R3.EntityItemFrame;
import net.minecraft.server.v1_5_R3.EntityPainting;
import net.minecraft.server.v1_5_R3.EntityHanging;
import net.minecraft.server.v1_5_R3.EntityMinecartRideable;
import net.minecraft.server.v1_5_R3.EntityMinecartMobSpawner;
import net.minecraft.server.v1_5_R3.EntityMinecartHopper;
import net.minecraft.server.v1_5_R3.EntityMinecartTNT;
import net.minecraft.server.v1_5_R3.EntityMinecartChest;
import net.minecraft.server.v1_5_R3.EntityMinecartFurnace;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_5_R3.EntityLightning;
import net.minecraft.server.v1_5_R3.EntityWeather;
import net.minecraft.server.v1_5_R3.EntityItem;
import net.minecraft.server.v1_5_R3.EntityFishingHook;
import net.minecraft.server.v1_5_R3.EntityEnderCrystal;
import net.minecraft.server.v1_5_R3.EntityEnderSignal;
import net.minecraft.server.v1_5_R3.EntityWitherSkull;
import net.minecraft.server.v1_5_R3.EntityLargeFireball;
import net.minecraft.server.v1_5_R3.EntitySmallFireball;
import net.minecraft.server.v1_5_R3.EntityFireball;
import net.minecraft.server.v1_5_R3.EntityFallingBlock;
import net.minecraft.server.v1_5_R3.EntityThrownExpBottle;
import net.minecraft.server.v1_5_R3.EntityEnderPearl;
import net.minecraft.server.v1_5_R3.EntityPotion;
import net.minecraft.server.v1_5_R3.EntitySnowball;
import net.minecraft.server.v1_5_R3.EntityEgg;
import net.minecraft.server.v1_5_R3.EntityProjectile;
import net.minecraft.server.v1_5_R3.EntityBoat;
import net.minecraft.server.v1_5_R3.EntityArrow;
import net.minecraft.server.v1_5_R3.EntityExperienceOrb;
import net.minecraft.server.v1_5_R3.EntityComplexPart;
import net.minecraft.server.v1_5_R3.EntityBat;
import net.minecraft.server.v1_5_R3.EntityAmbient;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import net.minecraft.server.v1_5_R3.EntityGhast;
import net.minecraft.server.v1_5_R3.EntityFlying;
import net.minecraft.server.v1_5_R3.EntityMagmaCube;
import net.minecraft.server.v1_5_R3.EntitySlime;
import net.minecraft.server.v1_5_R3.EntityVillager;
import net.minecraft.server.v1_5_R3.EntityIronGolem;
import net.minecraft.server.v1_5_R3.EntitySnowman;
import net.minecraft.server.v1_5_R3.EntityGolem;
import net.minecraft.server.v1_5_R3.EntitySquid;
import net.minecraft.server.v1_5_R3.EntityWaterAnimal;
import net.minecraft.server.v1_5_R3.EntityCaveSpider;
import net.minecraft.server.v1_5_R3.EntitySpider;
import net.minecraft.server.v1_5_R3.EntityWither;
import net.minecraft.server.v1_5_R3.EntityWitch;
import net.minecraft.server.v1_5_R3.EntityBlaze;
import net.minecraft.server.v1_5_R3.EntitySkeleton;
import net.minecraft.server.v1_5_R3.EntityGiantZombie;
import net.minecraft.server.v1_5_R3.EntitySilverfish;
import net.minecraft.server.v1_5_R3.EntityEnderman;
import net.minecraft.server.v1_5_R3.EntityCreeper;
import net.minecraft.server.v1_5_R3.EntityPigZombie;
import net.minecraft.server.v1_5_R3.EntityZombie;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntitySheep;
import net.minecraft.server.v1_5_R3.EntityOcelot;
import net.minecraft.server.v1_5_R3.EntityWolf;
import net.minecraft.server.v1_5_R3.EntityTameableAnimal;
import net.minecraft.server.v1_5_R3.EntityPig;
import net.minecraft.server.v1_5_R3.EntityMushroomCow;
import net.minecraft.server.v1_5_R3.EntityCow;
import net.minecraft.server.v1_5_R3.EntityChicken;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Entity;

public abstract class CraftEntity implements Entity
{
    protected final CraftServer server;
    protected net.minecraft.server.v1_5_R3.Entity entity;
    private EntityDamageEvent lastDamageEvent;
    
    public CraftEntity(final CraftServer server, final net.minecraft.server.v1_5_R3.Entity entity) {
        this.server = server;
        this.entity = entity;
    }
    
    public static CraftEntity getEntity(final CraftServer server, final net.minecraft.server.v1_5_R3.Entity entity) {
        if (entity instanceof EntityLiving) {
            if (entity instanceof EntityHuman) {
                if (entity instanceof EntityPlayer) {
                    return new CraftPlayer(server, (EntityPlayer)entity);
                }
                return new CraftHumanEntity(server, (EntityHuman)entity);
            }
            else if (entity instanceof EntityCreature) {
                if (entity instanceof EntityAnimal) {
                    if (entity instanceof EntityChicken) {
                        return new CraftChicken(server, (EntityChicken)entity);
                    }
                    if (entity instanceof EntityCow) {
                        if (entity instanceof EntityMushroomCow) {
                            return new CraftMushroomCow(server, (EntityMushroomCow)entity);
                        }
                        return new CraftCow(server, (EntityCow)entity);
                    }
                    else {
                        if (entity instanceof EntityPig) {
                            return new CraftPig(server, (EntityPig)entity);
                        }
                        if (entity instanceof EntityTameableAnimal) {
                            if (entity instanceof EntityWolf) {
                                return new CraftWolf(server, (EntityWolf)entity);
                            }
                            if (entity instanceof EntityOcelot) {
                                return new CraftOcelot(server, (EntityOcelot)entity);
                            }
                        }
                        else {
                            if (entity instanceof EntitySheep) {
                                return new CraftSheep(server, (EntitySheep)entity);
                            }
                            return new CraftAnimals(server, (EntityAnimal)entity);
                        }
                    }
                }
                else if (entity instanceof EntityMonster) {
                    if (entity instanceof EntityZombie) {
                        if (entity instanceof EntityPigZombie) {
                            return new CraftPigZombie(server, (EntityPigZombie)entity);
                        }
                        return new CraftZombie(server, (EntityZombie)entity);
                    }
                    else {
                        if (entity instanceof EntityCreeper) {
                            return new CraftCreeper(server, (EntityCreeper)entity);
                        }
                        if (entity instanceof EntityEnderman) {
                            return new CraftEnderman(server, (EntityEnderman)entity);
                        }
                        if (entity instanceof EntitySilverfish) {
                            return new CraftSilverfish(server, (EntitySilverfish)entity);
                        }
                        if (entity instanceof EntityGiantZombie) {
                            return new CraftGiant(server, (EntityGiantZombie)entity);
                        }
                        if (entity instanceof EntitySkeleton) {
                            return new CraftSkeleton(server, (EntitySkeleton)entity);
                        }
                        if (entity instanceof EntityBlaze) {
                            return new CraftBlaze(server, (EntityBlaze)entity);
                        }
                        if (entity instanceof EntityWitch) {
                            return new CraftWitch(server, (EntityWitch)entity);
                        }
                        if (entity instanceof EntityWither) {
                            return new CraftWither(server, (EntityWither)entity);
                        }
                        if (!(entity instanceof EntitySpider)) {
                            return new CraftMonster(server, (EntityMonster)entity);
                        }
                        if (entity instanceof EntityCaveSpider) {
                            return new CraftCaveSpider(server, (EntityCaveSpider)entity);
                        }
                        return new CraftSpider(server, (EntitySpider)entity);
                    }
                }
                else if (entity instanceof EntityWaterAnimal) {
                    if (entity instanceof EntitySquid) {
                        return new CraftSquid(server, (EntitySquid)entity);
                    }
                    return new CraftWaterMob(server, (EntityWaterAnimal)entity);
                }
                else if (entity instanceof EntityGolem) {
                    if (entity instanceof EntitySnowman) {
                        return new CraftSnowman(server, (EntitySnowman)entity);
                    }
                    if (entity instanceof EntityIronGolem) {
                        return new CraftIronGolem(server, (EntityIronGolem)entity);
                    }
                }
                else {
                    if (entity instanceof EntityVillager) {
                        return new CraftVillager(server, (EntityVillager)entity);
                    }
                    return new CraftCreature(server, (EntityCreature)entity);
                }
            }
            else if (entity instanceof EntitySlime) {
                if (entity instanceof EntityMagmaCube) {
                    return new CraftMagmaCube(server, (EntityMagmaCube)entity);
                }
                return new CraftSlime(server, (EntitySlime)entity);
            }
            else if (entity instanceof EntityFlying) {
                if (entity instanceof EntityGhast) {
                    return new CraftGhast(server, (EntityGhast)entity);
                }
                return new CraftFlying(server, (EntityFlying)entity);
            }
            else {
                if (entity instanceof EntityEnderDragon) {
                    return new CraftEnderDragon(server, (EntityEnderDragon)entity);
                }
                if (!(entity instanceof EntityAmbient)) {
                    return new CraftLivingEntity(server, (EntityLiving)entity);
                }
                if (entity instanceof EntityBat) {
                    return new CraftBat(server, (EntityBat)entity);
                }
                return new CraftAmbient(server, (EntityAmbient)entity);
            }
        }
        else if (entity instanceof EntityComplexPart) {
            final EntityComplexPart part = (EntityComplexPart)entity;
            if (part.owner instanceof EntityEnderDragon) {
                return new CraftEnderDragonPart(server, (EntityComplexPart)entity);
            }
            return new CraftComplexPart(server, (EntityComplexPart)entity);
        }
        else {
            if (entity instanceof EntityExperienceOrb) {
                return new CraftExperienceOrb(server, (EntityExperienceOrb)entity);
            }
            if (entity instanceof EntityArrow) {
                return new CraftArrow(server, (EntityArrow)entity);
            }
            if (entity instanceof EntityBoat) {
                return new CraftBoat(server, (EntityBoat)entity);
            }
            if (entity instanceof EntityProjectile) {
                if (entity instanceof EntityEgg) {
                    return new CraftEgg(server, (EntityEgg)entity);
                }
                if (entity instanceof EntitySnowball) {
                    return new CraftSnowball(server, (EntitySnowball)entity);
                }
                if (entity instanceof EntityPotion) {
                    return new CraftThrownPotion(server, (EntityPotion)entity);
                }
                if (entity instanceof EntityEnderPearl) {
                    return new CraftEnderPearl(server, (EntityEnderPearl)entity);
                }
                if (entity instanceof EntityThrownExpBottle) {
                    return new CraftThrownExpBottle(server, (EntityThrownExpBottle)entity);
                }
            }
            else {
                if (entity instanceof EntityFallingBlock) {
                    return new CraftFallingSand(server, (EntityFallingBlock)entity);
                }
                if (entity instanceof EntityFireball) {
                    if (entity instanceof EntitySmallFireball) {
                        return new CraftSmallFireball(server, (EntitySmallFireball)entity);
                    }
                    if (entity instanceof EntityLargeFireball) {
                        return new CraftLargeFireball(server, (EntityLargeFireball)entity);
                    }
                    if (entity instanceof EntityWitherSkull) {
                        return new CraftWitherSkull(server, (EntityWitherSkull)entity);
                    }
                    return new CraftFireball(server, (EntityFireball)entity);
                }
                else {
                    if (entity instanceof EntityEnderSignal) {
                        return new CraftEnderSignal(server, (EntityEnderSignal)entity);
                    }
                    if (entity instanceof EntityEnderCrystal) {
                        return new CraftEnderCrystal(server, (EntityEnderCrystal)entity);
                    }
                    if (entity instanceof EntityFishingHook) {
                        return new CraftFish(server, (EntityFishingHook)entity);
                    }
                    if (entity instanceof EntityItem) {
                        return new CraftItem(server, (EntityItem)entity);
                    }
                    if (entity instanceof EntityWeather) {
                        if (entity instanceof EntityLightning) {
                            return new CraftLightningStrike(server, (EntityLightning)entity);
                        }
                        return new CraftWeather(server, (EntityWeather)entity);
                    }
                    else if (entity instanceof EntityMinecartAbstract) {
                        if (entity instanceof EntityMinecartFurnace) {
                            return new CraftMinecartFurnace(server, (EntityMinecartFurnace)entity);
                        }
                        if (entity instanceof EntityMinecartChest) {
                            return new CraftMinecartChest(server, (EntityMinecartChest)entity);
                        }
                        if (entity instanceof EntityMinecartTNT) {
                            return new CraftMinecartTNT(server, (EntityMinecartTNT)entity);
                        }
                        if (entity instanceof EntityMinecartHopper) {
                            return new CraftMinecartHopper(server, (EntityMinecartHopper)entity);
                        }
                        if (entity instanceof EntityMinecartMobSpawner) {
                            return new CraftMinecartMobSpawner(server, (EntityMinecartMobSpawner)entity);
                        }
                        if (entity instanceof EntityMinecartRideable) {
                            return new CraftMinecartRideable(server, (EntityMinecartAbstract)entity);
                        }
                    }
                    else if (entity instanceof EntityHanging) {
                        if (entity instanceof EntityPainting) {
                            return new CraftPainting(server, (EntityPainting)entity);
                        }
                        if (entity instanceof EntityItemFrame) {
                            return new CraftItemFrame(server, (EntityItemFrame)entity);
                        }
                        return new CraftHanging(server, (EntityHanging)entity);
                    }
                    else {
                        if (entity instanceof EntityTNTPrimed) {
                            return new CraftTNTPrimed(server, (EntityTNTPrimed)entity);
                        }
                        if (entity instanceof EntityFireworks) {
                            return new CraftFirework(server, (EntityFireworks)entity);
                        }
                    }
                }
            }
        }
        throw new AssertionError(("Unknown entity " + entity == null) ? null : entity.getClass());
    }
    
    public Location getLocation() {
        return new Location(this.getWorld(), this.entity.locX, this.entity.locY, this.entity.locZ, this.entity.yaw, this.entity.pitch);
    }
    
    public Location getLocation(final Location loc) {
        if (loc != null) {
            loc.setWorld(this.getWorld());
            loc.setX(this.entity.locX);
            loc.setY(this.entity.locY);
            loc.setZ(this.entity.locZ);
            loc.setYaw(this.entity.yaw);
            loc.setPitch(this.entity.pitch);
        }
        return loc;
    }
    
    public Vector getVelocity() {
        return new Vector(this.entity.motX, this.entity.motY, this.entity.motZ);
    }
    
    public void setVelocity(final Vector vel) {
        this.entity.motX = vel.getX();
        this.entity.motY = vel.getY();
        this.entity.motZ = vel.getZ();
        this.entity.velocityChanged = true;
    }
    
    public boolean isOnGround() {
        return this.entity.onGround;
    }
    
    public World getWorld() {
        return this.entity.world.getWorld();
    }
    
    public boolean teleport(final Location location) {
        return this.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
    
    public boolean teleport(final Location location, final PlayerTeleportEvent.TeleportCause cause) {
        if (this.entity.vehicle != null || this.entity.passenger != null || this.entity.dead) {
            return false;
        }
        this.entity.world = ((CraftWorld)location.getWorld()).getHandle();
        this.entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        return true;
    }
    
    public boolean teleport(final Entity destination) {
        return this.teleport(destination.getLocation());
    }
    
    public boolean teleport(final Entity destination, final PlayerTeleportEvent.TeleportCause cause) {
        return this.teleport(destination.getLocation(), cause);
    }
    
    public List<Entity> getNearbyEntities(final double x, final double y, final double z) {
        final List<net.minecraft.server.v1_5_R3.Entity> notchEntityList = (List<net.minecraft.server.v1_5_R3.Entity>)this.entity.world.getEntities(this.entity, this.entity.boundingBox.grow(x, y, z));
        final List<Entity> bukkitEntityList = new ArrayList<Entity>(notchEntityList.size());
        for (final net.minecraft.server.v1_5_R3.Entity e : notchEntityList) {
            bukkitEntityList.add(e.getBukkitEntity());
        }
        return bukkitEntityList;
    }
    
    public int getEntityId() {
        return this.entity.id;
    }
    
    public int getFireTicks() {
        return this.entity.fireTicks;
    }
    
    public int getMaxFireTicks() {
        return this.entity.maxFireTicks;
    }
    
    public void setFireTicks(final int ticks) {
        this.entity.fireTicks = ticks;
    }
    
    public void remove() {
        this.entity.dead = true;
    }
    
    public boolean isDead() {
        return !this.entity.isAlive();
    }
    
    public boolean isValid() {
        return this.entity.isAlive() && this.entity.valid;
    }
    
    public Server getServer() {
        return this.server;
    }
    
    public Vector getMomentum() {
        return this.getVelocity();
    }
    
    public void setMomentum(final Vector value) {
        this.setVelocity(value);
    }
    
    public Entity getPassenger() {
        return this.isEmpty() ? null : this.getHandle().passenger.getBukkitEntity();
    }
    
    public boolean setPassenger(final Entity passenger) {
        if (passenger instanceof CraftEntity) {
            ((CraftEntity)passenger).getHandle().setPassengerOf(this.getHandle());
            return true;
        }
        return false;
    }
    
    public boolean isEmpty() {
        return this.getHandle().passenger == null;
    }
    
    public boolean eject() {
        if (this.getHandle().passenger == null) {
            return false;
        }
        this.getHandle().passenger.setPassengerOf(null);
        return true;
    }
    
    public float getFallDistance() {
        return this.getHandle().fallDistance;
    }
    
    public void setFallDistance(final float distance) {
        this.getHandle().fallDistance = distance;
    }
    
    public void setLastDamageCause(final EntityDamageEvent event) {
        this.lastDamageEvent = event;
    }
    
    public EntityDamageEvent getLastDamageCause() {
        return this.lastDamageEvent;
    }
    
    public UUID getUniqueId() {
        return this.getHandle().uniqueID;
    }
    
    public int getTicksLived() {
        return this.getHandle().ticksLived;
    }
    
    public void setTicksLived(final int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Age must be at least 1 tick");
        }
        this.getHandle().ticksLived = value;
    }
    
    public net.minecraft.server.v1_5_R3.Entity getHandle() {
        return this.entity;
    }
    
    public void playEffect(final EntityEffect type) {
        this.getHandle().world.broadcastEntityEffect(this.getHandle(), type.getData());
    }
    
    public void setHandle(final net.minecraft.server.v1_5_R3.Entity entity) {
        this.entity = entity;
    }
    
    public String toString() {
        return "CraftEntity{id=" + this.getEntityId() + '}';
    }
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final CraftEntity other = (CraftEntity)obj;
        return this.getEntityId() == other.getEntityId();
    }
    
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.getEntityId();
        return hash;
    }
    
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        ((MetadataStoreBase<CraftEntity>)this.server.getEntityMetadata()).setMetadata(this, metadataKey, newMetadataValue);
    }
    
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftEntity>)this.server.getEntityMetadata()).getMetadata(this, metadataKey);
    }
    
    public boolean hasMetadata(final String metadataKey) {
        return ((MetadataStoreBase<CraftEntity>)this.server.getEntityMetadata()).hasMetadata(this, metadataKey);
    }
    
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        ((MetadataStoreBase<CraftEntity>)this.server.getEntityMetadata()).removeMetadata(this, metadataKey, owningPlugin);
    }
    
    public boolean isInsideVehicle() {
        return this.getHandle().vehicle != null;
    }
    
    public boolean leaveVehicle() {
        if (this.getHandle().vehicle == null) {
            return false;
        }
        this.getHandle().setPassengerOf(null);
        return true;
    }
    
    public Entity getVehicle() {
        if (this.getHandle().vehicle == null) {
            return null;
        }
        return this.getHandle().vehicle.getBukkitEntity();
    }
}
