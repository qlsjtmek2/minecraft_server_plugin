// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SandstoneType
{
    CRACKED(0), 
    GLYPHED(1), 
    SMOOTH(2);
    
    private final byte data;
    private static final Map<Byte, SandstoneType> BY_DATA;
    
    private SandstoneType(final int data) {
        this.data = (byte)data;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public static SandstoneType getByData(final byte data) {
        return SandstoneType.BY_DATA.get(data);
    }
    
    static {
        BY_DATA = Maps.newHashMap();
        for (final SandstoneType type : values()) {
            SandstoneType.BY_DATA.put(type.data, type);
        }
    }
}
