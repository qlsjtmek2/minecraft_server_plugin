// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class CreatureSpawnEvent extends EntitySpawnEvent
{
    private final SpawnReason spawnReason;
    
    public CreatureSpawnEvent(final LivingEntity spawnee, final SpawnReason spawnReason) {
        super(spawnee);
        this.spawnReason = spawnReason;
    }
    
    public CreatureSpawnEvent(final Entity spawnee, final CreatureType type, final Location loc, final SpawnReason reason) {
        super(spawnee);
        this.spawnReason = reason;
    }
    
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    @Deprecated
    public CreatureType getCreatureType() {
        return CreatureType.fromEntityType(this.getEntityType());
    }
    
    public SpawnReason getSpawnReason() {
        return this.spawnReason;
    }
    
    public enum SpawnReason
    {
        NATURAL, 
        JOCKEY, 
        CHUNK_GEN, 
        SPAWNER, 
        EGG, 
        SPAWNER_EGG, 
        LIGHTNING, 
        BED, 
        BUILD_SNOWMAN, 
        BUILD_IRONGOLEM, 
        BUILD_WITHER, 
        VILLAGE_DEFENSE, 
        VILLAGE_INVASION, 
        BREEDING, 
        SLIME_SPLIT, 
        CUSTOM, 
        DEFAULT;
    }
}
