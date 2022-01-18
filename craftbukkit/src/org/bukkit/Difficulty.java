// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Difficulty
{
    PEACEFUL(0), 
    EASY(1), 
    NORMAL(2), 
    HARD(3);
    
    private final int value;
    private static final Map<Integer, Difficulty> BY_ID;
    
    private Difficulty(final int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static Difficulty getByValue(final int value) {
        return Difficulty.BY_ID.get(value);
    }
    
    static {
        BY_ID = Maps.newHashMap();
        for (final Difficulty diff : values()) {
            Difficulty.BY_ID.put(diff.value, diff);
        }
    }
}
