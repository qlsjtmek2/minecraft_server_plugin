package me.espoo.dunzeon;

import org.bukkit.Location;

public class Position {
	public static Location getAssociationOne() {
	    return API.StringToLocation("-579.0,230.0,-457.0,world,0.0,0.0");
	}
	
	public static Location getAssociationTwo() {
	    return API.StringToLocation("-74.0,114.0,-1172.0,world,0.0,0.0");
	}
	
	public static Location getStangByOne() {
	    return API.StringToLocation(Config.getString("괴인 협회 대기실 1"));
	}
	
	public static Location getStangByTwo() {
	    return API.StringToLocation(Config.getString("괴인 협회 대기실 2"));
	}
	
	public static void setStangByOne(String str) {
		Config.setString("괴인 협회 대기실 1", str);
	}
	
	public static void setStangByTwo(String str) {
		Config.setString("괴인 협회 대기실 2", str);
	}
	
	public static Location getOne1() {
		return API.StringToLocation(Config.getString("1라운드.첫번째 좌표"));
	}
	
	public static Location getOne2() {
		return API.StringToLocation(Config.getString("1라운드.두번째 좌표"));
	}
	
	public static Location getOne3() {
		return API.StringToLocation(Config.getString("1라운드.세번째 좌표"));
	}
	
	public static Location getOne4() {
		return API.StringToLocation(Config.getString("1라운드.네번째 좌표"));
	}
	
	public static Location getTwo1() {
		return API.StringToLocation(Config.getString("2라운드.첫번째 좌표"));
	}
	
	public static Location getTwo2() {
		return API.StringToLocation(Config.getString("2라운드.두번째 좌표"));
	}
	
	public static Location getTwo3() {
		return API.StringToLocation(Config.getString("2라운드.세번째 좌표"));
	}
	
	public static Location getTwo4() {
		return API.StringToLocation(Config.getString("2라운드.네번째 좌표"));
	}
	
	public static Location getThree1() {
		return API.StringToLocation(Config.getString("3라운드.첫번째 좌표"));
	}
	
	public static Location getThree2() {
		return API.StringToLocation(Config.getString("3라운드.두번째 좌표"));
	}
	
	public static Location getThree3() {
		return API.StringToLocation(Config.getString("3라운드.세번째 좌표"));
	}
	
	public static Location getThree4() {
		return API.StringToLocation(Config.getString("3라운드.네번째 좌표"));
	}
	
	public static Location getFour1() {
		return API.StringToLocation(Config.getString("4라운드.첫번째 좌표"));
	}
	
	public static Location getFour2() {
		return API.StringToLocation(Config.getString("4라운드.두번째 좌표"));
	}
	
	public static Location getFour3() {
		return API.StringToLocation(Config.getString("4라운드.세번째 좌표"));
	}
	
	public static Location getFour4() {
		return API.StringToLocation(Config.getString("4라운드.네번째 좌표"));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------
	
	public static void setOne1(Location location) {
		Config.setString("1라운드.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setOne2(Location location) {
		Config.setString("1라운드.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setOne3(Location location) {
		Config.setString("1라운드.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setOne4(Location location) {
		Config.setString("1라운드.네번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwo1(Location location) {
		Config.setString("2라운드.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwo2(Location location) {
		Config.setString("2라운드.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwo3(Location location) {
		Config.setString("2라운드.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setTwo4(Location location) {
		Config.setString("2라운드.네번째 좌표", API.LocationToString(location));
	}
	
	public static void setThree1(Location location) {
		Config.setString("3라운드.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setThree2(Location location) {
		Config.setString("3라운드.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setThree3(Location location) {
		Config.setString("3라운드.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setThree4(Location location) {
		Config.setString("3라운드.네번째 좌표", API.LocationToString(location));
	}
	
	public static void setFour1(Location location) {
		Config.setString("4라운드.첫번째 좌표", API.LocationToString(location));
	}
	
	public static void setFour2(Location location) {
		Config.setString("4라운드.두번째 좌표", API.LocationToString(location));
	}
	
	public static void setFour3(Location location) {
		Config.setString("4라운드.세번째 좌표", API.LocationToString(location));
	}
	
	public static void setFour4(Location location) {
		Config.setString("4라운드.네번째 좌표", API.LocationToString(location));
	}
}
