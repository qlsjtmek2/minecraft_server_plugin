// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.event;

import org.bukkit.entity.EntityType;
import org.bukkit.craftbukkit.v1_5_R3.util.CraftDamageSource;
import net.minecraft.server.v1_5_R3.Slot;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet103SetSlot;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.event.inventory.InventoryCloseEvent;
import net.minecraft.server.v1_5_R3.Explosion;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.Note;
import org.bukkit.Instrument;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryCrafting;
import org.bukkit.inventory.InventoryView;
import net.minecraft.server.v1_5_R3.InventoryCrafting;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import net.minecraft.server.v1_5_R3.Packet101CloseWindow;
import net.minecraft.server.v1_5_R3.Container;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Pig;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlockState;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import net.minecraft.server.v1_5_R3.EntityDamageSourceIndirect;
import net.minecraft.server.v1_5_R3.EntityDamageSource;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.server.ServerListPingEvent;
import java.net.InetAddress;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.event.entity.PlayerDeathEvent;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import java.util.Map;
import net.minecraft.server.v1_5_R3.EntityPotion;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import net.minecraft.server.v1_5_R3.EntityItem;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.minecraft.server.v1_5_R3.EntityArrow;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import net.minecraft.server.v1_5_R3.Item;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import net.minecraft.server.v1_5_R3.ItemStack;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.BlockState;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.World;
import org.bukkit.event.Event;
import net.minecraft.server.v1_5_R3.ChunkCoordinates;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import net.minecraft.server.v1_5_R3.DamageSource;

public class CraftEventFactory
{
    public static final DamageSource MELTING;
    public static final DamageSource POISON;
    
    private static boolean canBuild(final CraftWorld world, final Player player, final int x, final int z) {
        final WorldServer worldServer = world.getHandle();
        final int spawnSize = Bukkit.getServer().getSpawnRadius();
        if (world.getHandle().dimension != 0) {
            return true;
        }
        if (spawnSize <= 0) {
            return true;
        }
        if (((CraftServer)Bukkit.getServer()).getHandle().getOPs().isEmpty()) {
            return true;
        }
        if (player.isOp()) {
            return true;
        }
        final ChunkCoordinates chunkcoordinates = worldServer.getSpawn();
        final int distanceFromSpawn = Math.max(Math.abs(x - chunkcoordinates.x), Math.abs(z - chunkcoordinates.z));
        return distanceFromSpawn >= spawnSize;
    }
    
    public static <T extends Event> T callEvent(final T event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockPlaceEvent callBlockPlaceEvent(final World world, final EntityHuman who, final BlockState replacedBlockState, final int clickedX, final int clickedY, final int clickedZ) {
        final CraftWorld craftWorld = world.getWorld();
        final CraftServer craftServer = world.getServer();
        final Player player = (who == null) ? null : ((Player)who.getBukkitEntity());
        final Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
        final Block placedBlock = replacedBlockState.getBlock();
        final boolean canBuild = canBuild(craftWorld, player, placedBlock.getX(), placedBlock.getZ());
        final BlockPlaceEvent event = new BlockPlaceEvent(placedBlock, replacedBlockState, blockClicked, player.getItemInHand(), player, canBuild);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static SpawnerSpawnEvent callSpawnerSpawnEvent(final Entity spawnee, final int spawnerX, final int spawnerY, final int spawnerZ) {
        final CraftEntity entity = spawnee.getBukkitEntity();
        BlockState state = entity.getWorld().getBlockAt(spawnerX, spawnerY, spawnerZ).getState();
        if (!(state instanceof CreatureSpawner)) {
            state = null;
        }
        final SpawnerSpawnEvent event = new SpawnerSpawnEvent(entity, (CreatureSpawner)state);
        entity.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemInHand) {
        return (PlayerBucketEmptyEvent)getPlayerBucketEvent(false, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, Item.BUCKET);
    }
    
    public static PlayerBucketFillEvent callPlayerBucketFillEvent(final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemInHand, final Item bucket) {
        return (PlayerBucketFillEvent)getPlayerBucketEvent(true, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
    }
    
    private static PlayerEvent getPlayerBucketEvent(final boolean isFilling, final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemstack, final Item item) {
        final Player player = (who == null) ? null : ((Player)who.getBukkitEntity());
        final CraftItemStack itemInHand = CraftItemStack.asNewCraftStack(item);
        final Material bucket = Material.getMaterial(itemstack.id);
        final CraftWorld craftWorld = (CraftWorld)player.getWorld();
        final CraftServer craftServer = (CraftServer)player.getServer();
        final Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
        final BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
        PlayerEvent event = null;
        if (isFilling) {
            event = new PlayerBucketFillEvent(player, blockClicked, blockFace, bucket, itemInHand);
            ((PlayerBucketFillEvent)event).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
        }
        else {
            event = new PlayerBucketEmptyEvent(player, blockClicked, blockFace, bucket, itemInHand);
            ((PlayerBucketEmptyEvent)event).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
        }
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static PlayerInteractEvent callPlayerInteractEvent(final EntityHuman who, final Action action, final ItemStack itemstack) {
        if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR) {
            throw new IllegalArgumentException();
        }
        return callPlayerInteractEvent(who, action, 0, 256, 0, 0, itemstack);
    }
    
    public static PlayerInteractEvent callPlayerInteractEvent(final EntityHuman who, Action action, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemstack) {
        final Player player = (who == null) ? null : ((Player)who.getBukkitEntity());
        CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
        final CraftWorld craftWorld = (CraftWorld)player.getWorld();
        final CraftServer craftServer = (CraftServer)player.getServer();
        Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
        final BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
        if (clickedY > 255) {
            blockClicked = null;
            switch (action) {
                case LEFT_CLICK_BLOCK: {
                    action = Action.LEFT_CLICK_AIR;
                    break;
                }
                case RIGHT_CLICK_BLOCK: {
                    action = Action.RIGHT_CLICK_AIR;
                    break;
                }
            }
        }
        if (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0) {
            itemInHand = null;
        }
        final PlayerInteractEvent event = new PlayerInteractEvent(player, action, itemInHand, blockClicked, blockFace);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityShootBowEvent callEntityShootBowEvent(final EntityLiving who, final ItemStack itemstack, final EntityArrow entityArrow, final float force) {
        final LivingEntity shooter = (LivingEntity)who.getBukkitEntity();
        CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
        final Arrow arrow = (Arrow)entityArrow.getBukkitEntity();
        if (itemInHand != null && (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0)) {
            itemInHand = null;
        }
        final EntityShootBowEvent event = new EntityShootBowEvent(shooter, itemInHand, arrow, force);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockDamageEvent callBlockDamageEvent(final EntityHuman who, final int x, final int y, final int z, final ItemStack itemstack, final boolean instaBreak) {
        final Player player = (who == null) ? null : ((Player)who.getBukkitEntity());
        final CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
        final CraftWorld craftWorld = (CraftWorld)player.getWorld();
        final CraftServer craftServer = (CraftServer)player.getServer();
        final Block blockClicked = craftWorld.getBlockAt(x, y, z);
        final BlockDamageEvent event = new BlockDamageEvent(player, blockClicked, itemInHand, instaBreak);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static CreatureSpawnEvent callCreatureSpawnEvent(final EntityLiving entityliving, final CreatureSpawnEvent.SpawnReason spawnReason) {
        final LivingEntity entity = (LivingEntity)entityliving.getBukkitEntity();
        final CraftServer craftServer = (CraftServer)entity.getServer();
        final CreatureSpawnEvent event = new CreatureSpawnEvent(entity, spawnReason);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityTameEvent callEntityTameEvent(final EntityLiving entity, final EntityHuman tamer) {
        final org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
        final AnimalTamer bukkitTamer = (tamer != null) ? tamer.getBukkitEntity() : null;
        final CraftServer craftServer = (CraftServer)bukkitEntity.getServer();
        entity.persistent = true;
        final EntityTameEvent event = new EntityTameEvent((LivingEntity)bukkitEntity, bukkitTamer);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static ItemSpawnEvent callItemSpawnEvent(final EntityItem entityitem) {
        final org.bukkit.entity.Item entity = (org.bukkit.entity.Item)entityitem.getBukkitEntity();
        final CraftServer craftServer = (CraftServer)entity.getServer();
        final ItemSpawnEvent event = new ItemSpawnEvent(entity, entity.getLocation());
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static ItemDespawnEvent callItemDespawnEvent(final EntityItem entityitem) {
        final org.bukkit.entity.Item entity = (org.bukkit.entity.Item)entityitem.getBukkitEntity();
        final ItemDespawnEvent event = new ItemDespawnEvent(entity, entity.getLocation());
        entity.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static PotionSplashEvent callPotionSplashEvent(final EntityPotion potion, final Map<LivingEntity, Double> affectedEntities) {
        final ThrownPotion thrownPotion = (ThrownPotion)potion.getBukkitEntity();
        final PotionSplashEvent event = new PotionSplashEvent(thrownPotion, affectedEntities);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockFadeEvent callBlockFadeEvent(final Block block, final int type) {
        final BlockState state = block.getState();
        state.setTypeId(type);
        final BlockFadeEvent event = new BlockFadeEvent(block, state);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static void handleBlockSpreadEvent(final Block block, final Block source, final int type, final int data) {
        final BlockState state = block.getState();
        state.setTypeId(type);
        state.setRawData((byte)data);
        final BlockSpreadEvent event = new BlockSpreadEvent(block, source, state);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            state.update(true);
        }
    }
    
    public static EntityDeathEvent callEntityDeathEvent(final EntityLiving victim) {
        return callEntityDeathEvent(victim, new ArrayList<org.bukkit.inventory.ItemStack>(0));
    }
    
    public static EntityDeathEvent callEntityDeathEvent(final EntityLiving victim, final List<org.bukkit.inventory.ItemStack> drops) {
        final CraftLivingEntity entity = (CraftLivingEntity)victim.getBukkitEntity();
        final EntityDeathEvent event = new EntityDeathEvent(entity, drops, victim.getExpReward());
        final org.bukkit.World world = entity.getWorld();
        Bukkit.getServer().getPluginManager().callEvent(event);
        victim.expToDrop = event.getDroppedExp();
        for (final org.bukkit.inventory.ItemStack stack : event.getDrops()) {
            if (stack != null) {
                if (stack.getType() == Material.AIR) {
                    continue;
                }
                world.dropItemNaturally(entity.getLocation(), stack);
            }
        }
        return event;
    }
    
    public static PlayerDeathEvent callPlayerDeathEvent(final EntityPlayer victim, final List<org.bukkit.inventory.ItemStack> drops, final String deathMessage) {
        final CraftPlayer entity = victim.getBukkitEntity();
        final PlayerDeathEvent event = new PlayerDeathEvent(entity, drops, victim.getExpReward(), 0, deathMessage);
        final org.bukkit.World world = entity.getWorld();
        Bukkit.getServer().getPluginManager().callEvent(event);
        victim.keepLevel = event.getKeepLevel();
        victim.newLevel = event.getNewLevel();
        victim.newTotalExp = event.getNewTotalExp();
        victim.expToDrop = event.getDroppedExp();
        victim.newExp = event.getNewExp();
        for (final org.bukkit.inventory.ItemStack stack : event.getDrops()) {
            if (stack != null) {
                if (stack.getType() == Material.AIR) {
                    continue;
                }
                world.dropItemNaturally(entity.getLocation(), stack);
            }
        }
        return event;
    }
    
    public static ServerListPingEvent callServerListPingEvent(final Server craftServer, final InetAddress address, final String motd, final int numPlayers, final int maxPlayers) {
        final ServerListPingEvent event = new ServerListPingEvent(address, motd, numPlayers, maxPlayers);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityDamageEvent callEntityDamageEvent(final Entity damager, final Entity damagee, final EntityDamageEvent.DamageCause cause, final int damage) {
        EntityDamageEvent event;
        if (damager != null) {
            event = new EntityDamageByEntityEvent(damager.getBukkitEntity(), damagee.getBukkitEntity(), cause, damage);
        }
        else {
            event = new EntityDamageEvent(damagee.getBukkitEntity(), cause, damage);
        }
        callEvent(event);
        if (!event.isCancelled()) {
            event.getEntity().setLastDamageCause(event);
        }
        return event;
    }
    
    public static EntityDamageEvent handleEntityDamageEvent(final Entity entity, final DamageSource source, final int damage) {
        if (source instanceof EntityDamageSource) {
            Entity damager = source.getEntity();
            EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
            if (source instanceof EntityDamageSourceIndirect) {
                damager = ((EntityDamageSourceIndirect)source).getProximateDamageSource();
                if (damager.getBukkitEntity() instanceof ThrownPotion) {
                    cause = EntityDamageEvent.DamageCause.MAGIC;
                }
                else if (damager.getBukkitEntity() instanceof Projectile) {
                    cause = EntityDamageEvent.DamageCause.PROJECTILE;
                }
            }
            else if ("thorns".equals(source.translationIndex)) {
                cause = EntityDamageEvent.DamageCause.THORNS;
            }
            return callEntityDamageEvent(damager, entity, cause, damage);
        }
        if (source == DamageSource.OUT_OF_WORLD) {
            final EntityDamageEvent event = callEvent(new EntityDamageByBlockEvent(null, entity.getBukkitEntity(), EntityDamageEvent.DamageCause.VOID, damage));
            if (!event.isCancelled()) {
                event.getEntity().setLastDamageCause(event);
            }
            return event;
        }
        EntityDamageEvent.DamageCause cause2 = null;
        if (source == DamageSource.FIRE) {
            cause2 = EntityDamageEvent.DamageCause.FIRE;
        }
        else if (source == DamageSource.STARVE) {
            cause2 = EntityDamageEvent.DamageCause.STARVATION;
        }
        else if (source == DamageSource.WITHER) {
            cause2 = EntityDamageEvent.DamageCause.WITHER;
        }
        else if (source == DamageSource.STUCK) {
            cause2 = EntityDamageEvent.DamageCause.SUFFOCATION;
        }
        else if (source == DamageSource.DROWN) {
            cause2 = EntityDamageEvent.DamageCause.DROWNING;
        }
        else if (source == DamageSource.BURN) {
            cause2 = EntityDamageEvent.DamageCause.FIRE_TICK;
        }
        else if (source == CraftEventFactory.MELTING) {
            cause2 = EntityDamageEvent.DamageCause.MELTING;
        }
        else if (source == CraftEventFactory.POISON) {
            cause2 = EntityDamageEvent.DamageCause.POISON;
        }
        else if (source == DamageSource.MAGIC) {
            cause2 = EntityDamageEvent.DamageCause.MAGIC;
        }
        if (cause2 != null) {
            return callEntityDamageEvent(null, entity, cause2, damage);
        }
        return null;
    }
    
    public static boolean handleNonLivingEntityDamageEvent(final Entity entity, final DamageSource source, final int damage) {
        if (!(source instanceof EntityDamageSource)) {
            return false;
        }
        final EntityDamageEvent event = handleEntityDamageEvent(entity, source, damage);
        return event.isCancelled() || event.getDamage() == 0;
    }
    
    public static PlayerLevelChangeEvent callPlayerLevelChangeEvent(final Player player, final int oldLevel, final int newLevel) {
        final PlayerLevelChangeEvent event = new PlayerLevelChangeEvent(player, oldLevel, newLevel);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static PlayerExpChangeEvent callPlayerExpChangeEvent(final EntityHuman entity, final int expAmount) {
        final Player player = (Player)entity.getBukkitEntity();
        final PlayerExpChangeEvent event = new PlayerExpChangeEvent(player, expAmount);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static void handleBlockGrowEvent(final World world, final int x, final int y, final int z, final int type, final int data) {
        final Block block = world.getWorld().getBlockAt(x, y, z);
        final CraftBlockState state = (CraftBlockState)block.getState();
        state.setTypeId(type);
        state.setRawData((byte)data);
        final BlockGrowEvent event = new BlockGrowEvent(block, state);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            state.update(true);
        }
    }
    
    public static FoodLevelChangeEvent callFoodLevelChangeEvent(final EntityHuman entity, final int level) {
        final FoodLevelChangeEvent event = new FoodLevelChangeEvent(entity.getBukkitEntity(), level);
        entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static PigZapEvent callPigZapEvent(final Entity pig, final Entity lightning, final Entity pigzombie) {
        final PigZapEvent event = new PigZapEvent((Pig)pig.getBukkitEntity(), (LightningStrike)lightning.getBukkitEntity(), (PigZombie)pigzombie.getBukkitEntity());
        pig.getBukkitEntity().getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityChangeBlockEvent callEntityChangeBlockEvent(final org.bukkit.entity.Entity entity, final Block block, final Material material) {
        return callEntityChangeBlockEvent(entity, block, material, 0);
    }
    
    public static EntityChangeBlockEvent callEntityChangeBlockEvent(final Entity entity, final Block block, final Material material) {
        return callEntityChangeBlockEvent(entity.getBukkitEntity(), block, material, 0);
    }
    
    public static EntityChangeBlockEvent callEntityChangeBlockEvent(final Entity entity, final int x, final int y, final int z, final int type, final int data) {
        final Block block = entity.world.getWorld().getBlockAt(x, y, z);
        final Material material = Material.getMaterial(type);
        return callEntityChangeBlockEvent(entity.getBukkitEntity(), block, material, data);
    }
    
    public static EntityChangeBlockEvent callEntityChangeBlockEvent(final org.bukkit.entity.Entity entity, final Block block, final Material material, final int data) {
        final EntityChangeBlockEvent event = new EntityChangeBlockEvent(entity, block, material, (byte)data);
        entity.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static CreeperPowerEvent callCreeperPowerEvent(final Entity creeper, final Entity lightning, final CreeperPowerEvent.PowerCause cause) {
        final CreeperPowerEvent event = new CreeperPowerEvent((Creeper)creeper.getBukkitEntity(), (LightningStrike)lightning.getBukkitEntity(), cause);
        creeper.getBukkitEntity().getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityTargetEvent callEntityTargetEvent(final Entity entity, final Entity target, final EntityTargetEvent.TargetReason reason) {
        final EntityTargetEvent event = new EntityTargetEvent(entity.getBukkitEntity(), (target == null) ? null : target.getBukkitEntity(), reason);
        entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityTargetLivingEntityEvent callEntityTargetLivingEvent(final Entity entity, final EntityLiving target, final EntityTargetEvent.TargetReason reason) {
        final EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(entity.getBukkitEntity(), (LivingEntity)target.getBukkitEntity(), reason);
        entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static EntityBreakDoorEvent callEntityBreakDoorEvent(final Entity entity, final int x, final int y, final int z) {
        final org.bukkit.entity.Entity entity2 = entity.getBukkitEntity();
        final Block block = entity2.getWorld().getBlockAt(x, y, z);
        final EntityBreakDoorEvent event = new EntityBreakDoorEvent((LivingEntity)entity2, block);
        entity2.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static Container callInventoryOpenEvent(final EntityPlayer player, final Container container) {
        if (player.activeContainer != player.defaultContainer) {
            player.playerConnection.handleContainerClose(new Packet101CloseWindow(player.activeContainer.windowId));
        }
        final CraftServer server = player.world.getServer();
        final CraftPlayer craftPlayer = player.getBukkitEntity();
        player.activeContainer.transferTo(container, craftPlayer);
        final InventoryOpenEvent event = new InventoryOpenEvent(container.getBukkitView());
        server.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            container.transferTo(player.activeContainer, craftPlayer);
            return null;
        }
        return container;
    }
    
    public static ItemStack callPreCraftEvent(final InventoryCrafting matrix, final ItemStack result, final InventoryView lastCraftView, final boolean isRepair) {
        final CraftInventoryCrafting inventory = new CraftInventoryCrafting(matrix, matrix.resultInventory);
        inventory.setResult(CraftItemStack.asCraftMirror(result));
        final PrepareItemCraftEvent event = new PrepareItemCraftEvent(inventory, lastCraftView, isRepair);
        Bukkit.getPluginManager().callEvent(event);
        final org.bukkit.inventory.ItemStack bitem = event.getInventory().getResult();
        return CraftItemStack.asNMSCopy(bitem);
    }
    
    public static ProjectileLaunchEvent callProjectileLaunchEvent(final Entity entity) {
        final Projectile bukkitEntity = (Projectile)entity.getBukkitEntity();
        final ProjectileLaunchEvent event = new ProjectileLaunchEvent(bukkitEntity);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static ProjectileHitEvent callProjectileHitEvent(final Entity entity) {
        final ProjectileHitEvent event = new ProjectileHitEvent((Projectile)entity.getBukkitEntity());
        entity.world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static ExpBottleEvent callExpBottleEvent(final Entity entity, final int exp) {
        final ThrownExpBottle bottle = (ThrownExpBottle)entity.getBukkitEntity();
        final ExpBottleEvent event = new ExpBottleEvent(bottle, exp);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockRedstoneEvent callRedstoneChange(final World world, final int x, final int y, final int z, final int oldCurrent, final int newCurrent) {
        final BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(x, y, z), oldCurrent, newCurrent);
        world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static NotePlayEvent callNotePlayEvent(final World world, final int x, final int y, final int z, final byte instrument, final byte note) {
        final NotePlayEvent event = new NotePlayEvent(world.getWorld().getBlockAt(x, y, z), Instrument.getByType(instrument), new Note(note));
        world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static void callPlayerItemBreakEvent(final EntityHuman human, final ItemStack brokenItem) {
        final CraftItemStack item = CraftItemStack.asCraftMirror(brokenItem);
        final PlayerItemBreakEvent event = new PlayerItemBreakEvent((Player)human.getBukkitEntity(), item);
        Bukkit.getPluginManager().callEvent(event);
    }
    
    public static BlockIgniteEvent callBlockIgniteEvent(final World world, final int x, final int y, final int z, final int igniterX, final int igniterY, final int igniterZ) {
        final org.bukkit.World bukkitWorld = world.getWorld();
        final Block igniter = bukkitWorld.getBlockAt(igniterX, igniterY, igniterZ);
        BlockIgniteEvent.IgniteCause cause = null;
        switch (igniter.getType()) {
            case LAVA:
            case STATIONARY_LAVA: {
                cause = BlockIgniteEvent.IgniteCause.LAVA;
                break;
            }
            case DISPENSER: {
                cause = BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL;
                break;
            }
            default: {
                cause = BlockIgniteEvent.IgniteCause.SPREAD;
                break;
            }
        }
        final BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), cause, igniter);
        world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockIgniteEvent callBlockIgniteEvent(final World world, final int x, final int y, final int z, final Entity igniter) {
        final org.bukkit.World bukkitWorld = world.getWorld();
        final org.bukkit.entity.Entity bukkitIgniter = igniter.getBukkitEntity();
        BlockIgniteEvent.IgniteCause cause = null;
        switch (bukkitIgniter.getType()) {
            case ENDER_CRYSTAL: {
                cause = BlockIgniteEvent.IgniteCause.ENDER_CRYSTAL;
                break;
            }
            case LIGHTNING: {
                cause = BlockIgniteEvent.IgniteCause.LIGHTNING;
                break;
            }
            case SMALL_FIREBALL:
            case FIREBALL: {
                cause = BlockIgniteEvent.IgniteCause.FIREBALL;
                break;
            }
            default: {
                cause = BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL;
                break;
            }
        }
        final BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), cause, bukkitIgniter);
        world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockIgniteEvent callBlockIgniteEvent(final World world, final int x, final int y, final int z, final Explosion explosion) {
        final org.bukkit.World bukkitWorld = world.getWorld();
        final org.bukkit.entity.Entity igniter = (explosion.source == null) ? null : explosion.source.getBukkitEntity();
        final BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), BlockIgniteEvent.IgniteCause.EXPLOSION, igniter);
        world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static BlockIgniteEvent callBlockIgniteEvent(final World world, final int x, final int y, final int z, final BlockIgniteEvent.IgniteCause cause, final Entity igniter) {
        final BlockIgniteEvent event = new BlockIgniteEvent(world.getWorld().getBlockAt(x, y, z), cause, igniter.getBukkitEntity());
        world.getServer().getPluginManager().callEvent(event);
        return event;
    }
    
    public static void handleInventoryCloseEvent(final EntityHuman human) {
        final InventoryCloseEvent event = new InventoryCloseEvent(human.activeContainer.getBukkitView());
        human.world.getServer().getPluginManager().callEvent(event);
        human.activeContainer.transferTo(human.defaultContainer, human.getBukkitEntity());
    }
    
    public static void handleEditBookEvent(final EntityPlayer player, final ItemStack newBookItem) {
        final int itemInHandIndex = player.inventory.itemInHandIndex;
        final PlayerEditBookEvent editBookEvent = new PlayerEditBookEvent(player.getBukkitEntity(), player.inventory.itemInHandIndex, (BookMeta)CraftItemStack.getItemMeta(player.inventory.getItemInHand()), (BookMeta)CraftItemStack.getItemMeta(newBookItem), newBookItem.id == Item.WRITTEN_BOOK.id);
        player.world.getServer().getPluginManager().callEvent(editBookEvent);
        final ItemStack itemInHand = player.inventory.getItem(itemInHandIndex);
        if (itemInHand.id == Item.BOOK_AND_QUILL.id) {
            if (!editBookEvent.isCancelled()) {
                CraftItemStack.setItemMeta(itemInHand, editBookEvent.getNewBookMeta());
                if (editBookEvent.isSigning()) {
                    itemInHand.id = Item.WRITTEN_BOOK.id;
                }
            }
            final Slot slot = player.activeContainer.a(player.inventory, itemInHandIndex);
            player.playerConnection.sendPacket(new Packet103SetSlot(player.activeContainer.windowId, slot.g, itemInHand));
        }
    }
    
    static {
        MELTING = CraftDamageSource.copyOf(DamageSource.BURN);
        POISON = CraftDamageSource.copyOf(DamageSource.MAGIC);
    }
}
