package me.shinkhan.wing;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Helix {
	
	public static void a(Player p, ParticleEffect effect, double radius) {
		Location loc = p.getLocation();

		for (double y = 0; y <= 5; y += 0.007) {
			radius = y / 5;
			double x = radius * Math.cos(3 * y);
			double z = radius * Math.sin(3 * y);

			double y2 = 5 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x,
					loc.getY() + y2, loc.getZ() + z);
			effect.display(0, 0, 0, 0, 1, loc2, Bukkit.getOnlinePlayers());
		}
		
		for (double y = 0; y <= 5; y += 0.007) {
			radius = y / 5;
			double x = -(radius * Math.cos(3 * y));
			double z = -(radius * Math.sin(3 * y));

			double y2 = 5 - y;

			Location loc2 = new Location(loc.getWorld(), loc.getX() + x,
					loc.getY() + y2, loc.getZ() + z);
			effect.display(0, 0, 0, 0, 1, loc2, Bukkit.getOnlinePlayers());
		}

	}
}
