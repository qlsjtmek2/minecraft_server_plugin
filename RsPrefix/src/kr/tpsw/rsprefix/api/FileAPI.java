// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix.api;

import java.util.List;
import kr.tpsw.api.bukkit.CustomConfig3;
import java.io.File;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.HashMap;
import java.util.Map;

public class FileAPI
{
    private static final Map<String, PrefixPlayer> map;
    
    static {
        map = new HashMap<String, PrefixPlayer>();
    }
    
    public static boolean isLoadedPlayer(final String name) {
        return FileAPI.map.containsKey(name);
    }
    
    public static void initLoad() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            if (!isLoadedPlayer(p.getName())) {
                loadPlayer(p.getName());
            }
        }
    }
    
    public static void endSave() {
        for (final String name : FileAPI.map.keySet()) {
            savePlayer(name);
        }
        FileAPI.map.clear();
    }
    
    public static void loadPlayer(final String name) {
        final File file = new File("plugins\\RsPrefix\\users\\" + name + ".yml");
        boolean firstaccess = false;
        if (!file.exists()) {
            firstaccess = true;
        }
        final CustomConfig3 user = new CustomConfig3("plugins\\RsPrefix\\users\\" + name + ".yml");
        final List<String> list = user.getStringList("list");
        final String main = user.getString("main");
        final PrefixPlayer pp = new PrefixPlayer(name, list, main);
        FileAPI.map.put(name, pp);
        if (firstaccess) {
            final List<String> li = pp.getList();
            for (final String str : RanPreAPI.basic) {
                li.add(str);
            }
        }
        pp.updateInvList();
    }
    
    public static void savePlayer(final String name) {
        final CustomConfig3 user = new CustomConfig3("plugins\\RsPrefix\\users\\" + name + ".yml");
        final PrefixPlayer pp = FileAPI.map.get(name);
        user.set("list", pp.getList());
        user.set("main", pp.getMainPrefix());
        user.saveConfig();
        FileAPI.map.remove(name);
    }
    
    public static PrefixPlayer getPrefixPlayer(final String name) {
        return FileAPI.map.get(name);
    }
}
