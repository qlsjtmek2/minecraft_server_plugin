package me.espoo.stopdroproll;

import me.espoo.main.main;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RollDetection implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		if ((e.getPlayer().getFireTicks() >= 1) && (e.getPlayer().isSneaking()) && (main.config.getBoolean("Enabled"))
				&& (e.getFrom().getX() == e.getTo().getX()) && (e.getFrom().getY() == e.getTo().getY())
				&& (e.getFrom().getZ() == e.getTo().getZ())
				&& ((e.getPlayer().hasPermission("stopdroproll.extinguishfire"))
						|| (!main.config.getBoolean("Permissions enabled")))) {
			if (Math.abs(e.getFrom().getYaw() - e.getTo().getYaw()) > main.config.getInt("Degrees spun per second")
					/ 20.0D) {
				if (main.FirePlayers.containsKey(e.getPlayer().getName()))
					main.FirePlayers.put(e.getPlayer().getName(),
							Integer.valueOf(((Integer) main.FirePlayers.get(e.getPlayer().getName())).intValue() + 1));
				else {
					main.FirePlayers.put(e.getPlayer().getName(), Integer.valueOf(0));
				}
				if (((Integer) main.FirePlayers.get(e.getPlayer().getName()))
						.intValue() > main.config.getInt("Time spinning in seconds") * 20) {
					PlayerStopDropRollEvent callEvent = new PlayerStopDropRollEvent(e.getPlayer());
					Bukkit.getPluginManager().callEvent(callEvent);
					if (!callEvent.isCancelled()) {
						e.getPlayer().setFireTicks(0);
						e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.FIZZ, 1.0F, 1.0F);
						main.FirePlayers.remove(e.getPlayer().getName());
					}
				}
			} else if (main.FirePlayers.containsKey(e.getPlayer().getName())) {
				main.FirePlayers.remove(e.getPlayer().getName());
			}
		} else if (main.FirePlayers.containsKey(e.getPlayer().getName()))
			main.FirePlayers.remove(e.getPlayer().getName());
	}
}