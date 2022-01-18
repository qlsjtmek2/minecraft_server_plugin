// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ConfigUtil
{
    public static String locationToFormattedString(final Location loc) {
        return loc.getWorld().getName() + "~" + loc.getBlockX() + "~" + loc.getBlockY() + "~" + loc.getBlockZ();
    }
    
    public static Location formattedStringToLocation(final String formattedLoc) {
        final String[] loc = formattedLoc.split("~");
        return new Location(Bukkit.getWorld(loc[0]), (double)Integer.parseInt(loc[1]), (double)Integer.parseInt(loc[2]), (double)Integer.parseInt(loc[3]));
    }
}
