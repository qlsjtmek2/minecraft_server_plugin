// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum TreeSpecies
{
    GENERIC(0), 
    REDWOOD(1), 
    BIRCH(2), 
    JUNGLE(3);
    
    private final byte data;
    private static final Map<Byte, TreeSpecies> BY_DATA;
    
    private TreeSpecies(final int data) {
        this.data = (byte)data;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static TreeSpecies getByData(final byte data) {
        return TreeSpecies.BY_DATA.get(data);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final TreeSpecies species : values()) {
            TreeSpecies.BY_DATA.put(species.data, species);
        }
    }
}
