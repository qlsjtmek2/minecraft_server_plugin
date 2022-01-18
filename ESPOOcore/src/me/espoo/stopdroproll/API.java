package me.espoo.stopdroproll;

import org.bukkit.entity.Player;

import me.espoo.main.main;

public class API {
	public static void setEnabled(boolean enabled) {
		main.config.set("Enabled", Boolean.valueOf(enabled));
	}

	public static boolean isEnabled() {
		return main.config.getBoolean("Enabled");
	}

	public static void setSpinTime(Player player, int time) {
		main.FirePlayers.put(player.getName(), Integer.valueOf(time));
	}

	public static int getSpinTime(Player player) {
		if (main.FirePlayers.containsKey(player.getName()))
			return ((Integer) main.FirePlayers.get(player.getName())).intValue();
		return 0;
	}

	public static void setDegreesSpunPerSecond(int degrees) {
		degrees = Math.abs(degrees);
		while (degrees >= 360)
			degrees -= 360;
		main.config.set("Degrees spun per second", Integer.valueOf(degrees));
	}

	public static int getDegreesSpunPerSecond() {
		return main.config.getInt("Degrees spun per second");
	}

	public static void setTimeSpinning(int time) {
		main.config.set("Time spinning in seconds", Integer.valueOf(time));
	}

	public static int getTimeSpinning() {
		return main.config.getInt("Time spinning in seconds");
	}

	public static void setEnablePermissions(boolean enabled) {
		main.config.set("Enable permissions", Boolean.valueOf(enabled));
	}

	public static boolean getEnablePermissions() {
		return main.config.getBoolean("Enable permissions");
	}
}