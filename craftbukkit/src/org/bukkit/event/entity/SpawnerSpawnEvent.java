// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.block.CreatureSpawner;

public class SpawnerSpawnEvent extends EntitySpawnEvent
{
    private final CreatureSpawner spawner;
    
    public SpawnerSpawnEvent(final Entity spawnee, final CreatureSpawner spawner) {
        super(spawnee);
        this.spawner = spawner;
    }
    
    public CreatureSpawner getSpawner() {
        return this.spawner;
    }
}
