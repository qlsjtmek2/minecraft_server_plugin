// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum WorldType
{
    NORMAL("DEFAULT"), 
    FLAT("FLAT"), 
    VERSION_1_1("DEFAULT_1_1"), 
    LARGE_BIOMES("LARGEBIOMES");
    
    private static final Map<String, WorldType> BY_NAME;
    private final String name;
    
    private WorldType(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static WorldType getByName(final String name) {
        return WorldType.BY_NAME.get(name.toUpperCase());
    }
    
    static {
        BY_NAME = Maps.newHashMap();
        for (final WorldType type : values()) {
            WorldType.BY_NAME.put(type.name, type);
        }
    }
}
