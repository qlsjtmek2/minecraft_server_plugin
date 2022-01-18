package me.shinkhan.wing;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sphere {

	public static void a(final Player p, ParticleEffect effect, double p2,
			double r) {

		Location loc = p.getLocation().add(0, 1, 0);
		for (double i = 0; i <= Math.PI; i += Math.PI / p2) {
			double radius = Math.sin(i) * r;
			double y = Math.cos(i) * r;
			for (double a = 0; a < Math.PI * 2; a += Math.PI / p2) {
				double x = Math.cos(a) * radius;
				double z = Math.sin(a) * radius;
				loc.add(x, y, z);
				effect.display(0, 0, 0, 0, 1, loc, Bukkit.getOnlinePlayers());
				loc.subtract(x, y, z);
			}
		}

	}
}
