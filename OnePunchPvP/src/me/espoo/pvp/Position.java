package me.espoo.pvp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class Position {
	public static Location getRandomColoWatchLocation() {
		if (Config.getList("�ݷμ���.����") == null) return null;
		List<String> list = Config.getList("�ݷμ���.����");
		int random = new Random().nextInt(list.size());
		return API.StringToLocation(list.get(random));
	}
	
	public static void addColoWatchLocation(Location location) {
		if (Config.getList("�ݷμ���.����") == null) {
			List<String> list = new ArrayList<String>();
            list.add(API.LocationToString(location));
			Config.setList("�ݷμ���.����", list);
		} else {
			List<String> list = Config.getList("�ݷμ���.����");
            list.add(API.LocationToString(location));
			Config.setList("�ݷμ���.����", list);
		}
	}
	
	public static Location getColoRed() {
		return API.StringToLocation(Config.getString("�ݷμ���.Red.��ǥ"));
	}
	
	public static Location getColoBlue() {
		return API.StringToLocation(Config.getString("�ݷμ���.Blue.��ǥ"));
	}
	
	public static Location getOneRed() {
		return API.StringToLocation(Config.getString("1:1.Red.��ǥ"));
	}
	
	public static Location getOneBlue() {
		return API.StringToLocation(Config.getString("1:1.Blue.��ǥ"));
	}
	
	public static Location getTwoRed1() {
		return API.StringToLocation(Config.getString("2:2.Red.ù��° ��ǥ"));
	}
	
	public static Location getTwoRed2() {
		return API.StringToLocation(Config.getString("2:2.Red.�ι�° ��ǥ"));
	}
	
	public static Location getTwoBlue1() {
		return API.StringToLocation(Config.getString("2:2.Blue.ù��° ��ǥ"));
	}
	
	public static Location getTwoBlue2() {
		return API.StringToLocation(Config.getString("2:2.Blue.�ι�° ��ǥ"));
	}
	
	public static Location getThreeRed1() {
		return API.StringToLocation(Config.getString("3:3.Red.ù��° ��ǥ"));
	}
	
	public static Location getThreeRed2() {
		return API.StringToLocation(Config.getString("3:3.Red.�ι�° ��ǥ"));
	}
	
	public static Location getThreeRed3() {
		return API.StringToLocation(Config.getString("3:3.Red.����° ��ǥ"));
	}
	
	public static Location getThreeBlue1() {
		return API.StringToLocation(Config.getString("3:3.Blue.ù��° ��ǥ"));
	}
	
	public static Location getThreeBlue2() {
		return API.StringToLocation(Config.getString("3:3.Blue.�ι�° ��ǥ"));
	}
	
	public static Location getThreeBlue3() {
		return API.StringToLocation(Config.getString("3:3.Blue.����° ��ǥ"));
	}
	
	public static Location getFourRed1() {
		return API.StringToLocation(Config.getString("4:4.Red.ù��° ��ǥ"));
	}
	
	public static Location getFourRed2() {
		return API.StringToLocation(Config.getString("4:4.Red.�ι�° ��ǥ"));
	}
	
	public static Location getFourRed3() {
		return API.StringToLocation(Config.getString("4:4.Red.����° ��ǥ"));
	}
	
	public static Location getFourRed4() {
		return API.StringToLocation(Config.getString("4:4.Red.�׹�° ��ǥ"));
	}
	
	public static Location getFourBlue1() {
		return API.StringToLocation(Config.getString("4:4.Blue.ù��° ��ǥ"));
	}
	
	public static Location getFourBlue2() {
		return API.StringToLocation(Config.getString("4:4.Blue.�ι�° ��ǥ"));
	}
	
	public static Location getFourBlue3() {
		return API.StringToLocation(Config.getString("4:4.Blue.����° ��ǥ"));
	}
	
	public static Location getFourBlue4() {
		return API.StringToLocation(Config.getString("4:4.Blue.�׹�° ��ǥ"));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------

	public static void setColoRed(Location location) {
		Config.setString("�ݷμ���.Red.��ǥ", API.LocationToString(location));
	}
	
	public static void setColoBlue(Location location) {
		Config.setString("�ݷμ���.Blue.��ǥ", API.LocationToString(location));
	}
	
	public static void setOneRed(Location location) {
		Config.setString("1:1.Red.��ǥ", API.LocationToString(location));
	}
	
	public static void setOneBlue(Location location) {
		Config.setString("1:1.Blue.��ǥ", API.LocationToString(location));
	}
	
	public static void setTwoRed1(Location location) {
		Config.setString("2:2.Red.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwoRed2(Location location) {
		Config.setString("2:2.Red.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwoBlue1(Location location) {
		Config.setString("2:2.Blue.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setTwoBlue2(Location location) {
		Config.setString("2:2.Blue.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThreeRed1(Location location) {
		Config.setString("3:3.Red.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThreeRed2(Location location) {
		Config.setString("3:3.Red.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThreeRed3(Location location) {
		Config.setString("3:3.Red.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThreeBlue1(Location location) {
		Config.setString("3:3.Blue.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThreeBlue2(Location location) {
		Config.setString("3:3.Blue.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setThreeBlue3(Location location) {
		Config.setString("3:3.Blue.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourRed1(Location location) {
		Config.setString("4:4.Red.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourRed2(Location location) {
		Config.setString("4:4.Red.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourRed3(Location location) {
		Config.setString("4:4.Red.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourRed4(Location location) {
		Config.setString("4:4.Red.�׹�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourBlue1(Location location) {
		Config.setString("4:4.Blue.ù��° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourBlue2(Location location) {
		Config.setString("4:4.Blue.�ι�° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourBlue3(Location location) {
		Config.setString("4:4.Blue.����° ��ǥ", API.LocationToString(location));
	}
	
	public static void setFourBlue4(Location location) {
		Config.setString("4:4.Blue.�׹�° ��ǥ", API.LocationToString(location));
	}
}
