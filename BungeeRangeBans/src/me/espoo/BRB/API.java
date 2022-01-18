package me.espoo.BRB;

import org.bukkit.entity.Player;

public class API {
	public static String getIp(Player p) {
		new SchedulerRun(main.Main, p).runTaskLater(main.Main, 7L);
		return main.player.get(p.getName());
	}
}
