// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.support;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class WorldGuard
{
    private static WorldGuardPlugin plugin;
    private static boolean hasSupport;
    public static boolean useWorldGuard;
    
    static {
        WorldGuard.hasSupport = false;
        WorldGuard.useWorldGuard = true;
    }
    
    public static void init(final Plugin plugin2) {
        final org.bukkit.plugin.Plugin plugin3 = plugin2.getServer().getPluginManager().getPlugin("WorldGuard");
        WorldGuard.useWorldGuard = plugin2.getConfig().getBoolean("support.worldguard", false);
        if (plugin3 == null || !(plugin3 instanceof WorldGuardPlugin)) {
            return;
        }
        WorldGuard.hasSupport = true;
        WorldGuard.plugin = (WorldGuardPlugin)plugin3;
        Plugin.logger.info("[RPG Items] World Guard found");
    }
    
    public static boolean isEnabled() {
        return WorldGuard.hasSupport;
    }
    
    public static boolean canBuild(final Player player, final Location location) {
        return !WorldGuard.hasSupport || !WorldGuard.useWorldGuard || WorldGuard.plugin.canBuild(player, location);
    }
    
    public static boolean canPvP(final Location location) {
        return !WorldGuard.hasSupport || !WorldGuard.useWorldGuard || WorldGuard.plugin.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.PVP);
    }
}
