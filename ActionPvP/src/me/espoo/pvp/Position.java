package me.espoo.pvp;

import org.bukkit.Location;

public class Position {
	public static String getOneRed() {
		return API.ChangeTppos(Config.getString("1:1.Red.��ǥ"));
	}
	
	public static String getOneBlue() {
		return API.ChangeTppos(Config.getString("1:1.Blue.��ǥ"));
	}
	
	public static void setOneRed(Location location) {
		Config.setString("1:1.Red.��ǥ", API.LocationToString(location));
	}
	
	public static void setOneBlue(Location location) {
		Config.setString("1:1.Blue.��ǥ", API.LocationToString(location));
	}
}
