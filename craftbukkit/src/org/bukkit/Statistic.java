// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Statistic
{
    DAMAGE_DEALT(2020), 
    DAMAGE_TAKEN(2021), 
    DEATHS(2022), 
    MOB_KILLS(2023), 
    PLAYER_KILLS(2024), 
    FISH_CAUGHT(2025), 
    MINE_BLOCK(16777216, true), 
    USE_ITEM(6908288, false), 
    BREAK_ITEM(16973824, true);
    
    private static final Map<Integer, Statistic> BY_ID;
    private final int id;
    private final boolean isSubstat;
    private final boolean isBlock;
    
    private Statistic(final int id) {
        this(id, false, false);
    }
    
    private Statistic(final int id, final boolean isBlock) {
        this(id, true, isBlock);
    }
    
    private Statistic(final int id, final boolean isSubstat, final boolean isBlock) {
        this.id = id;
        this.isSubstat = isSubstat;
        this.isBlock = isBlock;
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean isSubstatistic() {
        return this.isSubstat;
    }
    
    public boolean isBlock() {
        return this.isSubstat && this.isBlock;
    }
    
    public static Statistic getById(final int id) {
        return Statistic.BY_ID.get(id);
    }
    
    static {
        BY_ID = Maps.newHashMap();
        for (final Statistic statistic : values()) {
            Statistic.BY_ID.put(statistic.id, statistic);
        }
    }
}
