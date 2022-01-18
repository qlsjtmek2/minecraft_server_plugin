package me.espoo.rpg2;

import java.util.List;

import org.bukkit.Location;

public class DunzeonAPI {
	public static List<String> getDunzeonList() {
		return Dunzeon.getList("던전 목록");
	}
	
	public static String getLandPos1(String name) {
		return Dunzeon.getString(name + ".첫번째 좌표");
	}
	
	public static String getLandPos2(String name) {
		return Dunzeon.getString(name + ".두번째 좌표");
	}
	
    public static boolean inDunzeonArea(Location loc, String str) {
		double XP = loc.getX();
		double ZP = loc.getZ();
		String W = loc.getWorld().getName();
    	
    	if (getLandPos1(str) == null) return false;
		String[] pos1 = getLandPos1(str).split(",");
		String[] pos2 = getLandPos2(str).split(",");
		double X1 = Double.parseDouble(pos1[0]);
		double Z1 = Double.parseDouble(pos1[1]);
		double X2 = Double.parseDouble(pos2[0]);
		double Z2 = Double.parseDouble(pos2[1]);
		String WW = pos1[2];
		
		if (W.equalsIgnoreCase(WW)) {
			if (X1 >= XP && XP >= X2 || X1 <= XP && XP <= X2) {
				if (Z1 >= ZP && ZP >= Z2 || Z1 <= ZP && ZP <= Z2) {
					return true;	
				}
			}
		}
			
		return false;
    }
}
