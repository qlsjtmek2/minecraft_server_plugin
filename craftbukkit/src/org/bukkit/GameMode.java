// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum GameMode
{
    CREATIVE(1), 
    SURVIVAL(0), 
    ADVENTURE(2);
    
    private final int value;
    private static final Map<Integer, GameMode> BY_ID;
    
    private GameMode(final int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static GameMode getByValue(final int value) {
        return GameMode.BY_ID.get(value);
    }
    
    static {
        BY_ID = Maps.newHashMap();
        for (final GameMode mode : values()) {
            GameMode.BY_ID.put(mode.getValue(), mode);
        }
    }
}
