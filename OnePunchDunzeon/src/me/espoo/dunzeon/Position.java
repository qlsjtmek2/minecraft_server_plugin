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
	    return API.StringToLocation(Config.getString("���� ��ȸ ���� 1"));
	}
	
	public static Location getStangByTwo() {
	    return API.StringToLocation(Config.getString("���� ��ȸ ���� 2"));
	}
	
	public static void setStangByOne(String str) {
		Config.setString("���� ��ȸ ���� 1", str);
	}
	
	public static void setStangByTwo(String str) {
		Config.setString("���� ��ȸ ���� 2", str);
	}
	
	public static Location getOne1() {
		return API.StringToLocation(Config.getString("1����.ù��° ��ǥ"));
	}
	
	public static Location getOne2() {
		return API.StringToLocation(Config.getString("1����.�ι�° ��ǥ"));
	}
	
	public static Location getOne3() {
		return API.StringToLocation(Config.getString("1����.����° ��ǥ"));
	}
	
	public static Location getOne4() {
		return API.StringToLocation(Config.getString("1����.�׹�° ��ǥ"));
	}
	
	public static Location getTwo1() {
		return API.StringToLocation(Config.getString("2����.ù��° ��ǥ"));
	}
	
	public static Location getTwo2() {
		return API.StringToLocation(Config.getString("2����.�ι�° ��ǥ"));
	}
	
	public static Location getTwo3() {
		return API.StringToLocation(Config.getString("2����.����° ��ǥ"));
	}
	
	public static Location getTwo4() {
		return API.StringToLocation(Config.getString("2����.�׹�° ��ǥ"));
	}
	
	public static Location getThree1() {
		return API.StringToLocation(Config.getString("3����.ù��° ��ǥ"));
	}
	
	public static Location getThree2() {
		return API.StringToLocation(Config.getString("3����.�ι�° ��ǥ"));
	}
	
	public static Location getThree3() {
		return API.StringToLocation(Config.getString("3����.����° ��ǥ"));
	}
	
	public static Location getThree4() {
		return API.StringToLocation(Config.getString("3����.�׹�° ��ǥ"));
	}
	
	public static Location getFour1() {
		return API.StringToLocation(Config.getString("4����.ù��° ��ǥ"));
	}
	
	public static Location getFour2() {
		return API.StringToLocation(Config.getString("4����.�ι�° ��ǥ"));
	}
	
	public static Location getFour3() {
		return API.StringToLocation(Config.getString("4����.����° ��ǥ"));
	}
	
	public static Location getFour4() {
		return API.StringToLocation(Config.getString("4����.�׹�° ��ǥ"));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------
	
	public static void setOne1(Location location) {
		Config.setString("1����.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setOne2(Location location) {
		Config.setString("1����.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setOne3(Location location) {
		Config.setString("1����.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setOne4(Location location) {
		Config.setString("1����.�׹�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwo1(Location location) {
		Config.setString("2����.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwo2(Location location) {
		Config.setString("2����.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwo3(Location location) {
		Config.setString("2����.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwo4(Location location) {
		Config.setString("2����.�׹�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThree1(Location location) {
		Config.setString("3����.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThree2(Location location) {
		Config.setString("3����.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThree3(Location location) {
		Config.setString("3����.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThree4(Location location) {
		Config.setString("3����.�׹�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFour1(Location location) {
		Config.setString("4����.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFour2(Location location) {
		Config.setString("4����.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFour3(Location location) {
		Config.setString("4����.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFour4(Location location) {
		Config.setString("4����.�׹�° ��ǥ", API.LocationToString(location));
	}
}
