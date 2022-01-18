package me.espoo.option;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Clock {
	public static ArrayList<Player> toggled = new ArrayList<Player>();
	
	public static int anti_spam_interval = 0;
	public static int item_id = 0;
	public static int reminder_interval = 0;
	
	public static boolean item_on_join = false;
	public static boolean exempt_with_perm = false;
	public static boolean reminder_msg = false;
	  
    public static void toggleClockOn(final Player p) {
        if (!Clock.toggled.contains(p)) {
            if (Clock.exempt_with_perm) {
                Player[] onlinePlayers2;
                for (int length2 = (onlinePlayers2 = Bukkit.getOnlinePlayers()).length, j = 0; j < length2; ++j) {
                    final Player pl = onlinePlayers2[j];
                    p.hidePlayer(pl);
                }
            } else {
                Player[] onlinePlayers3;
                for (int length3 = (onlinePlayers3 = Bukkit.getOnlinePlayers()).length, k = 0; k < length3; ++k) {
                    final Player pl = onlinePlayers3[k];
                    p.hidePlayer(pl);
                }
            }
            Clock.toggled.add(p);
        }
    }
    
    public static void toggleClockOff(final Player p) {
        if (Clock.toggled.contains(p)) {
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
                final Player pl = onlinePlayers[i];
                p.showPlayer(pl);
            } Clock.toggled.remove(p);
        }
    }
}
