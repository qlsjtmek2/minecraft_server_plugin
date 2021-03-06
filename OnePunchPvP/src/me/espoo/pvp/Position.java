package me.espoo.pvp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class Position {
	public static Location getRandomColoWatchLocation() {
		if (Config.getList("콜로세움.관전") == null) return null;
		List<String> list = Config.getList("콜로세움.관전");
		int random = new Random().nextInt(list.size());
		return API.StringToLocation(list.get(random));
	}
	
	public static void addColoWatchLocation(Location location) {
		if (Config.getList("콜로세움.관전") == null) {
			List<String> list = new ArrayList<String>();
            list.add(API.LocationToString(location));
			Config.setList("콜로세움.관전", list);
		} else {
			List<String> list = Config.getList("콜로세움.관전");
            list.add(API.LocationToString(location));
			Config.setList("콜로세움.관전", list);
		}
	}
	
	public static Location getColoRed() {
		return API.StringToLocation(Config.getString("콜로세움.Red.좌표"));
	}
	
	public static Location getColoBlue() {
		return API.StringToLocation(Config.getString("콜로세움.Blue.좌표"));
	}
	
	public static Location getOneRed() {
		return API.StringToLocation(Config.getString("1:1.Red.좌표"));
	}
	
	public static Location getOneBlue() {
		return API.StringToLocation(Config.getString("1:1.Blue.좌표"));
	}
	
	public static Location getTwoRed1() {
		return API.StringToLocation(Config.getString("2:2.Red.첫번째 좌표"));
	}
	
	public static Location getTwoRed2() {
		return API.StringToLocation(Config.getString("2:2.Red.두번째 좌표"));
	}
	
	public static Location getTwoBlue1() {
		return API.StringToLocation(Config.getString("2:2.Blue.첫번째 좌표"));
	}
	
	public static Location getTwoBlue2() {
		return API.StringToLocation(Config.getString("2:2.Blue.두번째 좌표"));
	}
	
	public static Location getThreeRed1() {
		return API.StringToLocation(Config.getString("3:3.Red.첫번째 좌표"));
	}
	
	public static Location getThreeRed2() {
		return API.StringToLocation(Config.getString("3:3.Red.두번째 좌표"));
	}
	
	public static Location getThreeRed3() {
		return API.StringToLocation(Config.getString("3:3.Red.세번째 좌표"));
	}
	
	public static Location getThreeBlue1() {
		return API.StringToLocation(Config.getString("3:3.Blue.첫번째 좌표"));
	}
	
	public static Location getThreeBlue2() {
		return API.StringToLocation(Config.getString("3:3.Blue.두번째 좌표"));
	}
	
	public static Location getThreeBlue3() {
		return API.StringToLocation(Config.getString("3:3.Blue.세번째 좌표"));
	}
	
	public static Location getFourRed1() {
		return API.StringToLocation(Config.getString("4:4.Red.첫번째 좌표"));
	}
	
	public static Location getFourRed2() {
		return API.StringToLocation(Config.getString("4:4.Red.두번째 좌표"));
	}
	
	public static Location getFourRed3() {
		return API.StringToLocation(Config.getString("4:4.Red.세번째 좌표"));
	}
	
	public static Location getFourRed4() {
		return API.StringToLocation(Config.getString("4:4.Red.네번째 좌표"));
	}
	
	public static Location getFourBlue1() {
		return API.StringToLocation(Config.getString("4:4.Blue.첫번째 좌표"));
	}
	
	public static Location getFourBlue2() {
		return API.StringToLocation(Config.getString("4:4.Blue.두번째 좌표"));
	}
	
	public static Location getFourBlue3() {
		return API.StringToLocation(Config.getString("4:4.Blue.세번째 좌표"));
	}
	
	public static Location getFourBlue4() {
		return API.StringToLocation(Config.getString("4:4.Blue.네번째 좌표"));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------

	public static void setColoRed(Location location) {
		Config.setString("콜로세움.Red.좌표", API.LocationToString(location));
	}
	
	public static void setColoBlue(Location location) {
		Config.setString("콜로세움.Blue.좌표", API.LocationToString(location));
	}
	
	public static void setOneRed(Location location) {
		Config.setString("1:1.Red.좌표", API.LocationToString(location));
	}
	
	public static void setOneBlue(Location location) {
		Config.setString("1:1.Blue.좌표", API.LocationToString(location));
	}
	
	public static void setTwoRed1(Location location) {
		Config.setString("2:2.Red.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwoRed2(Location location) {
		Config.setString("2:2.Red.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwoBlue1(Location location) {
		Config.setString("2:2.Blue.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwoBlue2(Location location) {
		Config.setString("2:2.Blue.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setThreeRed1(Location location) {
		Config.setString("3:3.Red.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setThreeRed2(Location location) {
		Config.setString("3:3.Red.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setThreeRed3(Location location) {
		Config.setString("3:3.Red.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setThreeBlue1(Location location) {
		Config.setString("3:3.Blue.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setThreeBlue2(Location location) {
		Config.setString("3:3.Blue.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setThreeBlue3(Location location) {
		Config.setString("3:3.Blue.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourRed1(Location location) {
		Config.setString("4:4.Red.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourRed2(Location location) {
		Config.setString("4:4.Red.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourRed3(Location location) {
		Config.setString("4:4.Red.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourRed4(Location location) {
		Config.setString("4:4.Red.네번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourBlue1(Location location) {
		Config.setString("4:4.Blue.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourBlue2(Location location) {
		Config.setString("4:4.Blue.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourBlue3(Location location) {
		Config.setString("4:4.Blue.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setFourBlue4(Location location) {
		Config.setString("4:4.Blue.네번째 좌표", API.LocationToString(location));
	}
}
