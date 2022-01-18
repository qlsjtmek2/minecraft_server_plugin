// 
// Decompiled by Procyon v0.5.29
// 

package Cro.Zombie.Moudule;

import org.bukkit.Location;
import org.bukkit.Effect;
import org.bukkit.Server;
import org.bukkit.World;
import Cro.Zombie.Command.CroZombie;

public class Util
{
    public static CroZombie plugin;
    public static World world;
    public static Server server;
    
    public static void Initialize(final CroZombie plugin) {
        Util.server = plugin.getServer();
        Util.world = Util.server.getWorlds().get(0);
    }
    
    public static void playEffect(final Effect e, final Location l, final int num) {
        for (int i = 0; i < Util.server.getOnlinePlayers().length; ++i) {
            Util.server.getOnlinePlayers()[i].playEffect(l, e, num);
        }
    }
}
