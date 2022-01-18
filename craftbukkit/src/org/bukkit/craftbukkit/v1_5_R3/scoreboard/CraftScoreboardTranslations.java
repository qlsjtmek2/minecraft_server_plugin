// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scoreboard;

import net.minecraft.server.v1_5_R3.Scoreboard;
import org.bukkit.scoreboard.DisplaySlot;
import com.google.common.collect.ImmutableBiMap;

class CraftScoreboardTranslations
{
    static final int MAX_DISPLAY_SLOT = 3;
    static ImmutableBiMap<DisplaySlot, String> SLOTS;
    
    static DisplaySlot toBukkitSlot(final int i) {
        return CraftScoreboardTranslations.SLOTS.inverse().get(Scoreboard.getSlotName(i));
    }
    
    static int fromBukkitSlot(final DisplaySlot slot) {
        return Scoreboard.getSlotForName(CraftScoreboardTranslations.SLOTS.get(slot));
    }
    
    static {
        CraftScoreboardTranslations.SLOTS = ImmutableBiMap.of(DisplaySlot.BELOW_NAME, "belowName", DisplaySlot.PLAYER_LIST, "list", DisplaySlot.SIDEBAR, "sidebar");
    }
}
