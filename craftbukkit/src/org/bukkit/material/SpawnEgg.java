// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.entity.EntityType;
import org.bukkit.Material;

public class SpawnEgg extends MaterialData
{
    public SpawnEgg() {
        super(Material.MONSTER_EGG);
    }
    
    public SpawnEgg(final int type, final byte data) {
        super(type, data);
    }
    
    public SpawnEgg(final byte data) {
        super(Material.MONSTER_EGG, data);
    }
    
    public SpawnEgg(final EntityType type) {
        this();
        this.setSpawnedType(type);
    }
    
    public EntityType getSpawnedType() {
        return EntityType.fromId(this.getData());
    }
    
    public void setSpawnedType(final EntityType type) {
        this.setData((byte)type.getTypeId());
    }
    
    public String toString() {
        return "SPAWN EGG{" + this.getSpawnedType() + "}";
    }
    
    public SpawnEgg clone() {
        return (SpawnEgg)super.clone();
    }
}
