package me.espoo.pvp;

import org.bukkit.Location;

public class Position {
	public static String getOneRed() {
		return API.ChangeTppos(Config.getString("1:1.Red.ÁÂÇ¥"));
	}
	
	public static String getOneBlue() {
		return API.ChangeTppos(Config.getString("1:1.Blue.ÁÂÇ¥"));
	}
	
	public static void setOneRed(Location location) {
		Config.setString("1:1.Red.ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setOneBlue(Location location) {
		Config.setString("1:1.Blue.ÁÂÇ¥", API.LocationToString(location));
	}
}
