// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.CreatureType;

public interface CreatureSpawner extends BlockState
{
    @Deprecated
    CreatureType getCreatureType();
    
    EntityType getSpawnedType();
    
    void setSpawnedType(final EntityType p0);
    
    @Deprecated
    void setCreatureType(final CreatureType p0);
    
    @Deprecated
    String getCreatureTypeId();
    
    void setCreatureTypeByName(final String p0);
    
    String getCreatureTypeName();
    
    @Deprecated
    void setCreatureTypeId(final String p0);
    
    int getDelay();
    
    void setDelay(final int p0);
}
