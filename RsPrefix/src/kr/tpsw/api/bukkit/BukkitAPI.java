// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.api.bukkit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.event.Listener;

public class BukkitAPI implements Listener
{
    private static final List<Player> ONLINE;
    private static final List<OfflinePlayer> OFFLINE;
    
    static {
        ONLINE = new ArrayList<Player>();
        OFFLINE = new ArrayList<OfflinePlayer>();
    }
    
    public BukkitAPI(final Plugin plugin) {
        Bukkit.getPluginManager().registerEvents((Listener)this, plugin);
        updatePlayerList();
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (isRegister(event.getPlayer().getName())) {
            BukkitAPI.ONLINE.add(event.getPlayer());
        }
        else {
            BukkitAPI.ONLINE.add(event.getPlayer());
            BukkitAPI.OFFLINE.add((OfflinePlayer)event.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        BukkitAPI.ONLINE.remove(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
        BukkitAPI.ONLINE.remove(event.getPlayer());
    }
    
    public static boolean isOnline(final String name) {
        final OfflinePlayer p = getOfflinePlayer(name);
        return p != null && p.isOnline();
    }
    
    public static boolean isRegister(final String name) {
        for (final OfflinePlayer p : BukkitAPI.OFFLINE) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public static void updatePlayerList() {
        final Object obj = Bukkit.getOnlinePlayers();
        if (obj instanceof Player[]) {
            final Player[] pp = (Player[])obj;
            Player[] array;
            for (int length = (array = pp).length, i = 0; i < length; ++i) {
                final Player p = array[i];
                BukkitAPI.ONLINE.add(p);
            }
        }
        else if (obj instanceof Collection) {
            final Collection<Player> pp2 = (Collection<Player>)obj;
            for (final Player p : pp2) {
                BukkitAPI.ONLINE.add(p);
            }
        }
        else {
            System.out.println("\uc624\ub958\ubc1c\uc0dd \ube7c\uc560\uc560\uc561!");
        }
        OfflinePlayer[] offlinePlayers;
        for (int length2 = (offlinePlayers = Bukkit.getOfflinePlayers()).length, j = 0; j < length2; ++j) {
            final OfflinePlayer p2 = offlinePlayers[j];
            BukkitAPI.OFFLINE.add(p2);
        }
    }
    
    public static Player getPlayer(final String name) {
        for (final Player p : BukkitAPI.ONLINE) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    public static OfflinePlayer getOfflinePlayer(final String name) {
        for (final OfflinePlayer p : BukkitAPI.OFFLINE) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    public static String getPlayerName(String target) {
        final List<String> list1 = new ArrayList<String>();
        target = target.toLowerCase();
        for (int i = 0; i < 16; ++i) {
            for (final Player player : BukkitAPI.ONLINE) {
                if (player.getName().toLowerCase().indexOf(target) == i) {
                    list1.add(player.getName());
                }
            }
            if (list1.size() != 0) {
                int len = 100;
                final List<String> list2 = new ArrayList<String>();
                for (int j = 0; j < list1.size(); ++j) {
                    final int l = list1.get(j).length();
                    if (l < len) {
                        len = l;
                    }
                }
                for (int j = 0; j < list1.size(); ++j) {
                    if (list1.get(j).length() == len) {
                        list2.add(list1.get(j));
                    }
                }
                final String[] list3 = new String[list2.size()];
                for (int k = 0; k < list2.size(); ++k) {
                    list3[k] = list2.get(k);
                }
                Arrays.sort(list3);
                return list3[0];
            }
        }
        return null;
    }
    
    public static String getOfflinePlayerName(String target) {
        final List<String> list1 = new ArrayList<String>();
        target = target.toLowerCase();
        for (int i = 0; i < 16; ++i) {
            for (final OfflinePlayer player : BukkitAPI.OFFLINE) {
                if (player.getName().toLowerCase().indexOf(target) == i) {
                    list1.add(player.getName());
                }
            }
            if (list1.size() != 0) {
                int len = 100;
                final List<String> list2 = new ArrayList<String>();
                for (int j = 0; j < list1.size(); ++j) {
                    final int l = list1.get(j).length();
                    if (l < len) {
                        len = l;
                    }
                }
                for (int j = 0; j < list1.size(); ++j) {
                    if (list1.get(j).length() == len) {
                        list2.add(list1.get(j));
                    }
                }
                final String[] list3 = new String[list2.size()];
                for (int k = 0; k < list2.size(); ++k) {
                    list3[k] = list2.get(k);
                }
                Arrays.sort(list3);
                return list3[0];
            }
        }
        return null;
    }
    
    public static List<Player> getOnlinePlayers() {
        return BukkitAPI.ONLINE;
    }
    
    public static List<OfflinePlayer> getOfflinePlayers() {
        return BukkitAPI.OFFLINE;
    }
}
