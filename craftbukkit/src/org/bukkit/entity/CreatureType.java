// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import java.util.Iterator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public enum CreatureType
{
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
    PIG("Pig", (Class<? extends Entity>)Pig.class, 90), 
    SHEEP("Sheep", (Class<? extends Entity>)Sheep.class, 91), 
    COW("Cow", (Class<? extends Entity>)Cow.class, 92), 
    CHICKEN("Chicken", (Class<? extends Entity>)Chicken.class, 93), 
    SQUID("Squid", (Class<? extends Entity>)Squid.class, 94), 
    WOLF("Wolf", (Class<? extends Entity>)Wolf.class, 95), 
    MUSHROOM_COW("MushroomCow", (Class<? extends Entity>)MushroomCow.class, 96), 
    SNOWMAN("SnowMan", (Class<? extends Entity>)Snowman.class, 97), 
    VILLAGER("Villager", (Class<? extends Entity>)Villager.class, 120);
    
    private String name;
    private Class<? extends Entity> clazz;
    private short typeId;
    private static final Map<String, CreatureType> NAME_MAP;
    private static final Map<Short, CreatureType> ID_MAP;
    
    private CreatureType(final String name, final Class<? extends Entity> clazz, final int typeId) {
        this.name = name;
        this.clazz = clazz;
        this.typeId = (short)typeId;
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
    
    public static CreatureType fromName(final String name) {
        return CreatureType.NAME_MAP.get(name);
    }
    
    public static CreatureType fromId(final int id) {
        if (id > 32767) {
            return null;
        }
        return CreatureType.ID_MAP.get((short)id);
    }
    
    @Deprecated
    public EntityType toEntityType() {
        return EntityType.fromName(this.getName());
    }
    
    public static CreatureType fromEntityType(final EntityType creatureType) {
        return fromName(creatureType.getName());
    }
    
    static {
        NAME_MAP = new HashMap<String, CreatureType>();
        ID_MAP = new HashMap<Short, CreatureType>();
        for (final CreatureType type : EnumSet.allOf(CreatureType.class)) {
            CreatureType.NAME_MAP.put(type.name, type);
            if (type.typeId != 0) {
                CreatureType.ID_MAP.put(type.typeId, type);
            }
        }
    }
}
