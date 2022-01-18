// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.item;

import org.bukkit.ChatColor;

public enum Quality
{
    TRASH("TRASH", 0, ChatColor.GRAY.toString(), "7"), 
    COMMON("COMMON", 1, ChatColor.WHITE.toString(), "f"), 
    UNCOMMON("UNCOMMON", 2, ChatColor.GREEN.toString(), "a"), 
    RARE("RARE", 3, ChatColor.BLUE.toString(), "9"), 
    EPIC("EPIC", 4, ChatColor.DARK_PURPLE.toString(), "5"), 
    LEGENDARY("LEGENDARY", 5, ChatColor.GOLD.toString(), "6");
    
    public final String colour;
    public final String cCode;
    
    private Quality(final String s, final int n, final String colour, final String code) {
        this.colour = colour;
        this.cCode = code;
    }
}
