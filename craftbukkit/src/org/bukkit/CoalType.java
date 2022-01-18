// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum CoalType
{
    COAL(0), 
    CHARCOAL(1);
    
    private final byte data;
    private static final Map<Byte, CoalType> BY_DATA;
    
    private CoalType(final int data) {
        this.data = (byte)data;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static CoalType getByData(final byte data) {
        return CoalType.BY_DATA.get(data);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final CoalType type : values()) {
            CoalType.BY_DATA.put(type.data, type);
        }
    }
}
