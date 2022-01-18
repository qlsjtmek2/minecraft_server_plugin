// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum GrassSpecies
{
    DEAD(0), 
    NORMAL(1), 
    FERN_LIKE(2);
    
    private final byte data;
    private static final Map<Byte, GrassSpecies> BY_DATA;
    
    private GrassSpecies(final int data) {
        this.data = (byte)data;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static GrassSpecies getByData(final byte data) {
        return GrassSpecies.BY_DATA.get(data);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final GrassSpecies grassSpecies : values()) {
            GrassSpecies.BY_DATA.put(grassSpecies.getData(), grassSpecies);
        }
    }
}
