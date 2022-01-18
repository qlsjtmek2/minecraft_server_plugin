// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import java.util.HashMap;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.entity.minecart.RideableMinecart;
import java.util.Map;

public enum EntityType
{
    DROPPED_ITEM("Item", (Class<? extends Entity>)Item.class, 1, false), 
    EXPERIENCE_ORB("XPOrb", (Class<? extends Entity>)ExperienceOrb.class, 2), 
    PAINTING("Painting", (Class<? extends Entity>)Painting.class, 9), 
    ARROW("Arrow", (Class<? extends Entity>)Arrow.class, 10), 
    SNOWBALL("Snowball", (Class<? extends Entity>)Snowball.class, 11), 
    FIREBALL("Fireball", (Class<? extends Entity>)LargeFireball.class, 12), 
    SMALL_FIREBALL("SmallFireball", (Class<? extends Entity>)SmallFireball.class, 13), 
    ENDER_PEARL("ThrownEnderpearl", (Class<? extends Entity>)EnderPearl.class, 14), 
    ENDER_SIGNAL("EyeOfEnderSignal", (Class<? extends Entity>)EnderSignal.class, 15), 
    THROWN_EXP_BOTTLE("ThrownExpBottle", (Class<? extends Entity>)ThrownExpBottle.class, 17), 
    ITEM_FRAME("ItemFrame", (Class<? extends Entity>)ItemFrame.class, 18), 
    WITHER_SKULL("WitherSkull", (Class<? extends Entity>)WitherSkull.class, 19), 
    PRIMED_TNT("PrimedTnt", (Class<? extends Entity>)TNTPrimed.class, 20), 
    FALLING_BLOCK("FallingSand", (Class<? extends Entity>)FallingBlock.class, 21, false), 
    FIREWORK("FireworksRocketEntity", (Class<? extends Entity>)Firework.class, 22, false), 
    BOAT("Boat", (Class<? extends Entity>)Boat.class, 41), 
    MINECART("MinecartRideable", (Class<? extends Entity>)RideableMinecart.class, 42), 
    MINECART_CHEST("MinecartChest", (Class<? extends Entity>)StorageMinecart.class, 43), 
    MINECART_FURNACE("MinecartFurnace", (Class<? extends Entity>)PoweredMinecart.class, 44), 
    MINECART_TNT("MinecartTNT", (Class<? extends Entity>)ExplosiveMinecart.class, 45), 
    MINECART_HOPPER("MinecartHopper", (Class<? extends Entity>)HopperMinecart.class, 46), 
    MINECART_MOB_SPAWNER("MinecartMobSpawner", (Class<? extends Entity>)SpawnerMinecart.class, 47), 
    CREEPER("Creeper", (Class<? extends Entity>)Creeper.class, 50), 
    SKELETON("Skeleton", (Class<? extends Entity>)Skeleton.class, 51), 
    SPIDER("Spider", (Class<? extends Entity>)Spider.class, 52), 
    GIANT("Giant", (Class<? extends Entity>)Giant.class, 53), 
    ZOMBIE("Zombie", (Class<? extends Entity>)Zombie.class, 54), 
    SLIME("Slime", (Class<? extends Entity>)Slime.class, 55), 
    GHAST("Ghast", (Class<? extends Entity>)Ghast.class, 56), 
    PIG_ZOMBIE("PigZombie", (Class<? extends Entity>)PigZombie.class, 57), 
    ENDERMAN("Enderman", (Class<? extends Entity>)Enderman.class, 58), 
    CAVE_SPIDER("CaveSpider", (Class<? extends Entity>)CaveSpider.class, 59), 
    SILVERFISH("Silverfish", (Class<? extends Entity>)Silverfish.class, 60), 
    BLAZE("Blaze", (Class<? extends Entity>)Blaze.class, 61), 
    MAGMA_CUBE("LavaSlime", (Class<? extends Entity>)MagmaCube.class, 62), 
    ENDER_DRAGON("EnderDragon", (Class<? extends Entity>)EnderDragon.class, 63), 
    WITHER("WitherBoss", (Class<? extends Entity>)Wither.class, 64), 
    BAT("Bat", (Class<? extends Entity>)Bat.class, 65), 
    WITCH("Witch", (Class<? extends Entity>)Witch.class, 66), 
    PIG("Pig", (Class<? extends Entity>)Pig.class, 90), 
    SHEEP("Sheep", (Class<? extends Entity>)Sheep.class, 91), 
    COW("Cow", (Class<? extends Entity>)Cow.class, 92), 
    CHICKEN("Chicken", (Class<? extends Entity>)Chicken.class, 93), 
    SQUID("Squid", (Class<? extends Entity>)Squid.class, 94), 
    WOLF("Wolf", (Class<? extends Entity>)Wolf.class, 95), 
    MUSHROOM_COW("MushroomCow", (Class<? extends Entity>)MushroomCow.class, 96), 
    SNOWMAN("SnowMan", (Class<? extends Entity>)Snowman.class, 97), 
    OCELOT("Ozelot", (Class<? extends Entity>)Ocelot.class, 98), 
    IRON_GOLEM("VillagerGolem", (Class<? extends Entity>)IronGolem.class, 99), 
    VILLAGER("Villager", (Class<? extends Entity>)Villager.class, 120), 
    ENDER_CRYSTAL("EnderCrystal", (Class<? extends Entity>)EnderCrystal.class, 200), 
    SPLASH_POTION((String)null, (Class<? extends Entity>)ThrownPotion.class, -1, false), 
    EGG((String)null, (Class<? extends Entity>)Egg.class, -1, false), 
    FISHING_HOOK((String)null, (Class<? extends Entity>)Fish.class, -1, false), 
    LIGHTNING((String)null, (Class<? extends Entity>)LightningStrike.class, -1, false), 
    WEATHER((String)null, (Class<? extends Entity>)Weather.class, -1, false), 
    PLAYER((String)null, (Class<? extends Entity>)Player.class, -1, false), 
    COMPLEX_PART((String)null, (Class<? extends Entity>)ComplexEntityPart.class, -1, false), 
    UNKNOWN((String)null, (Class<? extends Entity>)null, -1, false);
    
    private String name;
    private Class<? extends Entity> clazz;
    private short typeId;
    private boolean independent;
    private boolean living;
    private static final Map<String, EntityType> NAME_MAP;
    private static final Map<Short, EntityType> ID_MAP;
    
    private EntityType(final String name, final Class<? extends Entity> clazz, final int typeId) {
        this(name, clazz, typeId, true);
    }
    
    private EntityType(final String name, final Class<? extends Entity> clazz, final int typeId, final boolean independent) {
        this.name = name;
        this.clazz = clazz;
        this.typeId = (short)typeId;
        this.independent = independent;
        if (clazz != null) {
            this.living = LivingEntity.class.isAssignableFrom(clazz);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<? extends Entity> getEntityClass() {
        return this.clazz;
    }
    
    public short getTypeId() {
        return this.typeId;
    }
    
    public static EntityType fromName(final String name) {
        if (name == null) {
            return null;
        }
        return EntityType.NAME_MAP.get(name.toLowerCase());
    }
    
    public static EntityType fromId(final int id) {
        if (id > 32767) {
            return null;
        }
        return EntityType.ID_MAP.get((short)id);
    }
    
    public boolean isSpawnable() {
        return this.independent;
    }
    
    public boolean isAlive() {
        return this.living;
    }
    
    static {
        NAME_MAP = new HashMap<String, EntityType>();
        ID_MAP = new HashMap<Short, EntityType>();
        for (final EntityType type : values()) {
            if (type.name != null) {
                EntityType.NAME_MAP.put(type.name.toLowerCase(), type);
            }
            if (type.typeId > 0) {
                EntityType.ID_MAP.put(type.typeId, type);
            }
        }
    }
}
