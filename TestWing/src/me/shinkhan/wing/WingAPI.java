package me.shinkhan.wing;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WingAPI {
	static boolean o = false;
	static boolean x = true;
	
	static boolean[][] background = {
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o }};
	
	static boolean[][] exp2 = {
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, x, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, x, o, o, o, x, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, x, o, o, o, o, o, o, o, x, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o }};
	
	static int[][] exp3 = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

	static boolean[][] exm = {
			{ o, o, o, o, x, o, o, o, o, o, o, x, o, o, o, o },
			{ o, o, o, o, x, o, o, o, o, o, o, x, o, o, o, o },
			{ o, o, o, x, o, x, o, o, o, o, x, o, x, o, o, o },
			{ o, o, o, x, x, x, o, o, o, o, x, x, x, o, o, o },
			{ o, o, x, x, o, x, x, o, o, x, x, o, x, x, o, o },
			{ o, x, x, o, o, x, x, o, o, x, x, o, o, x, x, o },
			{ o, x, o, o, o, x, x, o, o, x, x, o, o, o, x, o },
			{ o, o, o, o, o, o, x, o, o, x, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o },
			{ o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o }};
	

	
	public static void drawWing(Player p, boolean[][] shape, double space,
			double between, ParticleEffect particle) {
		Location location = p.getLocation();
		double defX = location.getX() - (space * shape[0].length / 2) + space;
		double x = defX;
		double y = location.clone().getY() + 2.8;
		double fire = -((location.getYaw() + 180) / 60);
		fire += (location.getYaw() < -180 ? 3.25 : 2.985);

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j]) {

					Location target = location.clone();
					target.setX(x);
					target.setY(y);

					Vector v = target.toVector().subtract(location.toVector());
					Vector v2 = getBackVector(location);
					v = rotateAroundAxisY(v, fire);
					v2.setY(0).multiply(-(between));

					location.add(v);
					location.add(v2);
					for (int k = 0; k < 3; k++) {
						if (Arrays.asList(Bukkit.getOnlinePlayers()).isEmpty()) {
							return;
						}
						particle.display(0, 0, 0, 0, 1,
								location, Bukkit.getOnlinePlayers());
					}
					location.subtract(v2);
					location.subtract(v);
				}
				x += space;
			}
			y -= space;
			x = defX;
		}
	}
	
	public static void drawWingColor(Player p, int[][] shape, double space,
			double between, List<ParticleEffect> particle) {
		Location location = p.getLocation();
		double defX = location.getX() - (space * shape[0].length / 2) + space;
		double x = defX;
		double y = location.clone().getY() + 2.8;
		double fire = -((location.getYaw() + 180) / 60);
		fire += (location.getYaw() < -180 ? 3.25 : 2.985);

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				for (int z = 1; z < (particle.size() + 1); z++) {
					if (shape[i][j] == z) {

						Location target = location.clone();
						target.setX(x);
						target.setY(y);

						Vector v = target.toVector().subtract(location.toVector());
						Vector v2 = getBackVector(location);
						v = rotateAroundAxisY(v, fire);
						v2.setY(0).multiply(-(between));

						location.add(v);
						location.add(v2);
						for (int k = 0; k < 3; k++) {
							if (Arrays.asList(Bukkit.getOnlinePlayers()).isEmpty()) {
								return;
							}
							particle.get((z - 1)).display(0, 0, 0, 0, 1,
									location, Bukkit.getOnlinePlayers());
						}
						location.subtract(v2);
						location.subtract(v);
					}
				}
				
				x += space;
			}
			y -= space;
			x = defX;
		}
	}

	public static final Vector rotateAroundAxisY(Vector v, double angle) {
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static Vector getBackVector(Location loc) {
		final float newZ = (float) (loc.getZ() + (1 * Math.sin(Math
				.toRadians(loc.getYaw() + 90 * 1))));
		final float newX = (float) (loc.getX() + (1 * Math.cos(Math
				.toRadians(loc.getYaw() + 90 * 1))));
		return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
	}

}
