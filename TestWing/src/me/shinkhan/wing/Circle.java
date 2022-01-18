package me.shinkhan.wing;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Circle {
	public static void a(final Player p, ParticleEffect effect, double radius) {
		for (int i = 0; i <= 360; i++) {
			Location flameloc = p.getLocation();
			flameloc.add(Math.cos(i) * radius, 0, Math.sin(i) * radius);
			effect.display(0, 0, 0, 0, 1, flameloc, Bukkit.getOnlinePlayers());
		}
	}
}
