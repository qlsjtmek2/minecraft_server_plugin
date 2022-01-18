package me.espoo.pvp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class Position {
	public static Location getRandomColoWatchLocation() {
		if (Config.getList("ÄÝ·Î¼¼¿ò.°üÀü") == null) return null;
		List<String> list = Config.getList("ÄÝ·Î¼¼¿ò.°üÀü");
		int random = new Random().nextInt(list.size());
		return API.StringToLocation(list.get(random));
	}
	
	public static void addColoWatchLocation(Location location) {
		if (Config.getList("ÄÝ·Î¼¼¿ò.°üÀü") == null) {
			List<String> list = new ArrayList<String>();
            list.add(API.LocationToString(location));
			Config.setList("ÄÝ·Î¼¼¿ò.°üÀü", list);
		} else {
			List<String> list = Config.getList("ÄÝ·Î¼¼¿ò.°üÀü");
            list.add(API.LocationToString(location));
			Config.setList("ÄÝ·Î¼¼¿ò.°üÀü", list);
		}
	}
	
	public static Location getColoRed() {
		return API.StringToLocation(Config.getString("ÄÝ·Î¼¼¿ò.Red.ÁÂÇ¥"));
	}
	
	public static Location getColoBlue() {
		return API.StringToLocation(Config.getString("ÄÝ·Î¼¼¿ò.Blue.ÁÂÇ¥"));
	}
	
	public static Location getOneRed() {
		return API.StringToLocation(Config.getString("1:1.Red.ÁÂÇ¥"));
	}
	
	public static Location getOneBlue() {
		return API.StringToLocation(Config.getString("1:1.Blue.ÁÂÇ¥"));
	}
	
	public static Location getTwoRed1() {
		return API.StringToLocation(Config.getString("2:2.Red.Ã¹¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getTwoRed2() {
		return API.StringToLocation(Config.getString("2:2.Red.µÎ¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getTwoBlue1() {
		return API.StringToLocation(Config.getString("2:2.Blue.Ã¹¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getTwoBlue2() {
		return API.StringToLocation(Config.getString("2:2.Blue.µÎ¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getThreeRed1() {
		return API.StringToLocation(Config.getString("3:3.Red.Ã¹¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getThreeRed2() {
		return API.StringToLocation(Config.getString("3:3.Red.µÎ¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getThreeRed3() {
		return API.StringToLocation(Config.getString("3:3.Red.¼¼¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getThreeBlue1() {
		return API.StringToLocation(Config.getString("3:3.Blue.Ã¹¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getThreeBlue2() {
		return API.StringToLocation(Config.getString("3:3.Blue.µÎ¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getThreeBlue3() {
		return API.StringToLocation(Config.getString("3:3.Blue.¼¼¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourRed1() {
		return API.StringToLocation(Config.getString("4:4.Red.Ã¹¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourRed2() {
		return API.StringToLocation(Config.getString("4:4.Red.µÎ¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourRed3() {
		return API.StringToLocation(Config.getString("4:4.Red.¼¼¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourRed4() {
		return API.StringToLocation(Config.getString("4:4.Red.³×¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourBlue1() {
		return API.StringToLocation(Config.getString("4:4.Blue.Ã¹¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourBlue2() {
		return API.StringToLocation(Config.getString("4:4.Blue.µÎ¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourBlue3() {
		return API.StringToLocation(Config.getString("4:4.Blue.¼¼¹øÂ° ÁÂÇ¥"));
	}
	
	public static Location getFourBlue4() {
		return API.StringToLocation(Config.getString("4:4.Blue.³×¹øÂ° ÁÂÇ¥"));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------

	public static void setColoRed(Location location) {
		Config.setString("ÄÝ·Î¼¼¿ò.Red.ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setColoBlue(Location location) {
		Config.setString("ÄÝ·Î¼¼¿ò.Blue.ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setOneRed(Location location) {
		Config.setString("1:1.Red.ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setOneBlue(Location location) {
		Config.setString("1:1.Blue.ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setTwoRed1(Location location) {
		Config.setString("2:2.Red.Ã¹¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setTwoRed2(Location location) {
		Config.setString("2:2.Red.µÎ¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setTwoBlue1(Location location) {
		Config.setString("2:2.Blue.Ã¹¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setTwoBlue2(Location location) {
		Config.setString("2:2.Blue.µÎ¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setThreeRed1(Location location) {
		Config.setString("3:3.Red.Ã¹¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setThreeRed2(Location location) {
		Config.setString("3:3.Red.µÎ¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setThreeRed3(Location location) {
		Config.setString("3:3.Red.¼¼¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setThreeBlue1(Location location) {
		Config.setString("3:3.Blue.Ã¹¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setThreeBlue2(Location location) {
		Config.setString("3:3.Blue.µÎ¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setThreeBlue3(Location location) {
		Config.setString("3:3.Blue.¼¼¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourRed1(Location location) {
		Config.setString("4:4.Red.Ã¹¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourRed2(Location location) {
		Config.setString("4:4.Red.µÎ¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourRed3(Location location) {
		Config.setString("4:4.Red.¼¼¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourRed4(Location location) {
		Config.setString("4:4.Red.³×¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourBlue1(Location location) {
		Config.setString("4:4.Blue.Ã¹¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourBlue2(Location location) {
		Config.setString("4:4.Blue.µÎ¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourBlue3(Location location) {
		Config.setString("4:4.Blue.¼¼¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
	
	public static void setFourBlue4(Location location) {
		Config.setString("4:4.Blue.³×¹øÂ° ÁÂÇ¥", API.LocationToString(location));
	}
}
