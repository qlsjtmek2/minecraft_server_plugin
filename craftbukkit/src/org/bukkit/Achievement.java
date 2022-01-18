// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Achievement
{
    OPEN_INVENTORY(0), 
    MINE_WOOD(1), 
    BUILD_WORKBENCH(2), 
    BUILD_PICKAXE(3), 
    BUILD_FURNACE(4), 
    ACQUIRE_IRON(5), 
    BUILD_HOE(6), 
    MAKE_BREAD(7), 
    BAKE_CAKE(8), 
    BUILD_BETTER_PICKAXE(9), 
    COOK_FISH(10), 
    ON_A_RAIL(11), 
    BUILD_SWORD(12), 
    KILL_ENEMY(13), 
    KILL_COW(14), 
    FLY_PIG(15), 
    SNIPE_SKELETON(16), 
    GET_DIAMONDS(17), 
    NETHER_PORTAL(18), 
    GHAST_RETURN(19), 
    GET_BLAZE_ROD(20), 
    BREW_POTION(21), 
    END_PORTAL(22), 
    THE_END(23), 
    ENCHANTMENTS(24), 
    OVERKILL(25), 
    BOOKCASE(26);
    
    public static final int STATISTIC_OFFSET = 5242880;
    private static final Map<Integer, Achievement> BY_ID;
    private final int id;
    
    private Achievement(final int id) {
        this.id = 5242880 + id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static Achievement getById(final int id) {
        return Achievement.BY_ID.get(id);
    }
    
    static {
        BY_ID = Maps.newHashMap();
        for (final Achievement achievement : values()) {
            Achievement.BY_ID.put(achievement.id, achievement);
        }
    }
}
