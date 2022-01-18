// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.CreatureType;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityMobSpawner;
import org.bukkit.block.CreatureSpawner;

public class CraftCreatureSpawner extends CraftBlockState implements CreatureSpawner
{
    private final TileEntityMobSpawner spawner;
    
    public CraftCreatureSpawner(final Block block) {
        super(block);
        this.spawner = (TileEntityMobSpawner)((CraftWorld)block.getWorld()).getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    @Deprecated
    public CreatureType getCreatureType() {
        return CreatureType.fromName(this.spawner.a().getMobName());
    }
    
    public EntityType getSpawnedType() {
        return EntityType.fromName(this.spawner.a().getMobName());
    }
    
    @Deprecated
    public void setCreatureType(final CreatureType creatureType) {
        this.spawner.a().a(creatureType.getName());
    }
    
    public void setSpawnedType(final EntityType entityType) {
        if (entityType == null || entityType.getName() == null) {
            throw new IllegalArgumentException("Can't spawn EntityType " + entityType + " from mobspawners!");
        }
        this.spawner.a().a(entityType.getName());
    }
    
    @Deprecated
    public String getCreatureTypeId() {
        return this.spawner.a().getMobName();
    }
    
    @Deprecated
    public void setCreatureTypeId(final String creatureName) {
        this.setCreatureTypeByName(creatureName);
    }
    
    public String getCreatureTypeName() {
        return this.spawner.a().getMobName();
    }
    
    public void setCreatureTypeByName(final String creatureType) {
        final EntityType type = EntityType.fromName(creatureType);
        if (type == null) {
            return;
        }
        this.setSpawnedType(type);
    }
    
    public int getDelay() {
        return this.spawner.a().spawnDelay;
    }
    
    public void setDelay(final int delay) {
        this.spawner.a().spawnDelay = delay;
    }
}
