// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.EntityLargeFireball;
import net.minecraft.server.v1_5_R3.EntityWitherSkull;
import org.bukkit.entity.WitherSkull;
import net.minecraft.server.v1_5_R3.EntitySmallFireball;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Fireball;
import net.minecraft.server.v1_5_R3.EntityPotion;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.ThrownPotion;
import net.minecraft.server.v1_5_R3.EntityArrow;
import net.minecraft.server.v1_5_R3.EntityEnderPearl;
import org.bukkit.entity.EnderPearl;
import net.minecraft.server.v1_5_R3.EntityEgg;
import net.minecraft.server.v1_5_R3.EntitySnowball;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.entity.Projectile;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet42RemoveMobEffect;
import net.minecraft.server.v1_5_R3.MobEffectList;
import org.bukkit.potion.PotionEffectType;
import java.util.Collection;
import net.minecraft.server.v1_5_R3.MobEffect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import org.bukkit.entity.Arrow;
import java.util.Iterator;
import org.bukkit.util.BlockIterator;
import java.util.ArrayList;
import org.bukkit.block.Block;
import java.util.List;
import java.util.HashSet;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Egg;
import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.DamageSource;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import org.bukkit.entity.HumanEntity;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftEntityEquipment;
import org.bukkit.entity.LivingEntity;

public class CraftLivingEntity extends CraftEntity implements LivingEntity
{
    private CraftEntityEquipment equipment;
    
    public CraftLivingEntity(final CraftServer server, final EntityLiving entity) {
        super(server, entity);
        if (!(this instanceof HumanEntity)) {
            this.equipment = new CraftEntityEquipment(this);
        }
    }
    
    public int getHealth() {
        return Math.min(Math.max(0, this.getHandle().getHealth()), this.getMaxHealth());
    }
    
    public void setHealth(final int health) {
        if (health < 0 || health > this.getMaxHealth()) {
            throw new IllegalArgumentException("Health must be between 0 and " + this.getMaxHealth());
        }
        if (this.entity instanceof EntityPlayer && health == 0) {
            ((EntityPlayer)this.entity).die(DamageSource.GENERIC);
        }
        this.getHandle().setHealth(health);
    }
    
    public int getMaxHealth() {
        return this.getHandle().maxHealth;
    }
    
    public void setMaxHealth(final int amount) {
        Validate.isTrue(amount > 0, "Max health must be greater than 0");
        this.getHandle().maxHealth = amount;
        if (this.getHealth() > amount) {
            this.setHealth(amount);
        }
    }
    
    public void resetMaxHealth() {
        this.setMaxHealth(this.getHandle().getMaxHealth());
    }
    
    @Deprecated
    public Egg throwEgg() {
        return this.launchProjectile((Class<? extends Egg>)Egg.class);
    }
    
    @Deprecated
    public Snowball throwSnowball() {
        return this.launchProjectile((Class<? extends Snowball>)Snowball.class);
    }
    
    public double getEyeHeight() {
        return this.getHandle().getHeadHeight();
    }
    
    public double getEyeHeight(final boolean ignoreSneaking) {
        return this.getEyeHeight();
    }
    
    private List<Block> getLineOfSight(final HashSet<Byte> transparent, int maxDistance, final int maxLength) {
        if (maxDistance > 120) {
            maxDistance = 120;
        }
        final ArrayList<Block> blocks = new ArrayList<Block>();
        final Iterator<Block> itr = new BlockIterator(this, maxDistance);
        while (itr.hasNext()) {
            final Block block = itr.next();
            blocks.add(block);
            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }
            final int id = block.getTypeId();
            if (transparent == null) {
                if (id != 0) {
                    break;
                }
                continue;
            }
            else {
                if (!transparent.contains((byte)id)) {
                    break;
                }
                continue;
            }
        }
        return blocks;
    }
    
    public List<Block> getLineOfSight(final HashSet<Byte> transparent, final int maxDistance) {
        return this.getLineOfSight(transparent, maxDistance, 0);
    }
    
    public Block getTargetBlock(final HashSet<Byte> transparent, final int maxDistance) {
        final List<Block> blocks = this.getLineOfSight(transparent, maxDistance, 1);
        return blocks.get(0);
    }
    
    public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent, final int maxDistance) {
        return this.getLineOfSight(transparent, maxDistance, 2);
    }
    
    @Deprecated
    public Arrow shootArrow() {
        return this.launchProjectile((Class<? extends Arrow>)Arrow.class);
    }
    
    public int getRemainingAir() {
        return this.getHandle().getAirTicks();
    }
    
    public void setRemainingAir(final int ticks) {
        this.getHandle().setAirTicks(ticks);
    }
    
    public int getMaximumAir() {
        return this.getHandle().maxAirTicks;
    }
    
    public void setMaximumAir(final int ticks) {
        this.getHandle().maxAirTicks = ticks;
    }
    
    public void damage(final int amount) {
        this.damage(amount, null);
    }
    
    public void damage(final int amount, final Entity source) {
        DamageSource reason = DamageSource.GENERIC;
        if (source instanceof HumanEntity) {
            reason = DamageSource.playerAttack(((CraftHumanEntity)source).getHandle());
        }
        else if (source instanceof LivingEntity) {
            reason = DamageSource.mobAttack(((CraftLivingEntity)source).getHandle());
        }
        if (this.entity instanceof EntityEnderDragon) {
            ((EntityEnderDragon)this.entity).dealDamage(reason, amount);
        }
        else {
            this.entity.damageEntity(reason, amount);
        }
    }
    
    public Location getEyeLocation() {
        final Location loc = this.getLocation();
        loc.setY(loc.getY() + this.getEyeHeight());
        return loc;
    }
    
    public int getMaximumNoDamageTicks() {
        return this.getHandle().maxNoDamageTicks;
    }
    
    public void setMaximumNoDamageTicks(final int ticks) {
        this.getHandle().maxNoDamageTicks = ticks;
    }
    
    public int getLastDamage() {
        return this.getHandle().lastDamage;
    }
    
    public void setLastDamage(final int damage) {
        this.getHandle().lastDamage = damage;
    }
    
    public int getNoDamageTicks() {
        return this.getHandle().noDamageTicks;
    }
    
    public void setNoDamageTicks(final int ticks) {
        this.getHandle().noDamageTicks = ticks;
    }
    
    public EntityLiving getHandle() {
        return (EntityLiving)this.entity;
    }
    
    public void setHandle(final EntityLiving entity) {
        super.setHandle(entity);
    }
    
    public String toString() {
        return "CraftLivingEntity{id=" + this.getEntityId() + '}';
    }
    
    public Player getKiller() {
        return (this.getHandle().killer == null) ? null : ((Player)this.getHandle().killer.getBukkitEntity());
    }
    
    public boolean addPotionEffect(final PotionEffect effect) {
        return this.addPotionEffect(effect, false);
    }
    
    public boolean addPotionEffect(final PotionEffect effect, final boolean force) {
        if (this.hasPotionEffect(effect.getType())) {
            if (!force) {
                return false;
            }
            this.removePotionEffect(effect.getType());
        }
        this.getHandle().addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier()));
        return true;
    }
    
    public boolean addPotionEffects(final Collection<PotionEffect> effects) {
        boolean success = true;
        for (final PotionEffect effect : effects) {
            success &= this.addPotionEffect(effect);
        }
        return success;
    }
    
    public boolean hasPotionEffect(final PotionEffectType type) {
        return this.getHandle().hasEffect(MobEffectList.byId[type.getId()]);
    }
    
    public void removePotionEffect(final PotionEffectType type) {
        this.getHandle().effects.remove(type.getId());
        this.getHandle().updateEffects = true;
        if (this.getHandle() instanceof EntityPlayer) {
            if (((EntityPlayer)this.getHandle()).playerConnection == null) {
                return;
            }
            ((EntityPlayer)this.getHandle()).playerConnection.sendPacket(new Packet42RemoveMobEffect(this.getHandle().id, new MobEffect(type.getId(), 0, 0)));
        }
    }
    
    public Collection<PotionEffect> getActivePotionEffects() {
        final List<PotionEffect> effects = new ArrayList<PotionEffect>();
        for (final Object raw : this.getHandle().effects.values()) {
            if (!(raw instanceof MobEffect)) {
                continue;
            }
            final MobEffect handle = (MobEffect)raw;
            effects.add(new PotionEffect(PotionEffectType.getById(handle.getEffectId()), handle.getDuration(), handle.getAmplifier()));
        }
        return effects;
    }
    
    public <T extends Projectile> T launchProjectile(final Class<? extends T> projectile) {
        final World world = ((CraftWorld)this.getWorld()).getHandle();
        net.minecraft.server.v1_5_R3.Entity launch = null;
        if (Snowball.class.isAssignableFrom(projectile)) {
            launch = new EntitySnowball(world, this.getHandle());
        }
        else if (Egg.class.isAssignableFrom(projectile)) {
            launch = new EntityEgg(world, this.getHandle());
        }
        else if (EnderPearl.class.isAssignableFrom(projectile)) {
            launch = new EntityEnderPearl(world, this.getHandle());
        }
        else if (Arrow.class.isAssignableFrom(projectile)) {
            launch = new EntityArrow(world, this.getHandle(), 1.0f);
        }
        else if (ThrownPotion.class.isAssignableFrom(projectile)) {
            launch = new EntityPotion(world, this.getHandle(), CraftItemStack.asNMSCopy(new ItemStack(Material.POTION, 1)));
        }
        else if (Fireball.class.isAssignableFrom(projectile)) {
            final Location location = this.getEyeLocation();
            final Vector direction = location.getDirection().multiply(10);
            if (SmallFireball.class.isAssignableFrom(projectile)) {
                launch = new EntitySmallFireball(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ());
            }
            else if (WitherSkull.class.isAssignableFrom(projectile)) {
                launch = new EntityWitherSkull(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ());
            }
            else {
                launch = new EntityLargeFireball(world, this.getHandle(), direction.getX(), direction.getY(), direction.getZ());
            }
            launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        }
        Validate.notNull(launch, "Projectile not supported");
        world.addEntity(launch);
        return (T)launch.getBukkitEntity();
    }
    
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
    
    public boolean hasLineOfSight(final Entity other) {
        return this.getHandle().getEntitySenses().canSee(((CraftEntity)other).getHandle());
    }
    
    public boolean getRemoveWhenFarAway() {
        return !this.getHandle().persistent;
    }
    
    public void setRemoveWhenFarAway(final boolean remove) {
        this.getHandle().persistent = !remove;
    }
    
    public EntityEquipment getEquipment() {
        return this.equipment;
    }
    
    public void setCanPickupItems(final boolean pickup) {
        this.getHandle().canPickUpLoot = pickup;
    }
    
    public boolean getCanPickupItems() {
        return this.getHandle().canPickUpLoot;
    }
    
    public boolean teleport(final Location location, final PlayerTeleportEvent.TeleportCause cause) {
        return this.getHealth() != 0 && super.teleport(location, cause);
    }
    
    public void setCustomName(String name) {
        if (name == null) {
            name = "";
        }
        if (name.length() > 64) {
            name = name.substring(0, 64);
        }
        this.getHandle().setCustomName(name);
    }
    
    public String getCustomName() {
        final String name = this.getHandle().getCustomName();
        if (name == null || name.length() == 0) {
            return null;
        }
        return name;
    }
    
    public void setCustomNameVisible(final boolean flag) {
        this.getHandle().setCustomNameVisible(flag);
    }
    
    public boolean isCustomNameVisible() {
        return this.getHandle().getCustomNameVisible();
    }
}
