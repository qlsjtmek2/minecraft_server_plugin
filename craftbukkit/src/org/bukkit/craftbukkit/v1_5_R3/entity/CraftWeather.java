// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityWeather;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Weather;

public class CraftWeather extends CraftEntity implements Weather
{
    public CraftWeather(final CraftServer server, final EntityWeather entity) {
        super(server, entity);
    }
    
    public EntityWeather getHandle() {
        return (EntityWeather)this.entity;
    }
    
    public String toString() {
        return "CraftWeather";
    }
    
    public EntityType getType() {
        return EntityType.WEATHER;
    }
}
