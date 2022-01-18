package me.espoo.pvp;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> one = new ArrayList<String>();
	public static List<String> two = new ArrayList<String>();
	public static List<String> three = new ArrayList<String>();
	public static List<String> four = new ArrayList<String>();
	public static List<String> colosseum = new ArrayList<String>();
	public static List<String> stop = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		stop.add("§7클릭시 매칭중인 대련을 취소합니다.");
		
		one.add("§f클릭시 1:1 대련 매칭을 돌립니다.");
		one.add("§f파티가 없을 때만 입장 가능합니다.");
		one.add("");
		one.add("§8§l<HELP> §7매칭은 스폰에서만 가능합니다.");
		
		two.add("§f클릭시 2:2 대련 매칭을 돌립니다.");
		two.add("§f파티가 있을 때만 입장 가능합니다.");
		two.add("");
		two.add("§8§l<HELP> §7매칭은 스폰에서만 가능합니다.");

		three.add("§f클릭시 3:3 대련 매칭을 돌립니다.");
		three.add("§f파티가 있을 때만 입장 가능합니다.");
		three.add("");
		three.add("§8§l<HELP> §7매칭은 스폰에서만 가능합니다.");

		four.add("§f클릭시 4:4 대련 매칭을 돌립니다.");
		four.add("§f파티가 있을 때만 입장 가능합니다.");
		four.add("");
		four.add("§8§l<HELP> §7매칭은 스폰에서만 가능합니다.");
		

		colosseum.add("§f클릭시 콜로세움 대련 매칭을 돌립니다.");
		colosseum.add("§f파티가 없고, 장비, 룬, 소켓, 무기");
		colosseum.add("§f가 착용이 안되있고 스텟 효과가 OFF");
		colosseum.add("§f되어있을때만 가능합니다.");
		colosseum.add("");
		colosseum.add("§8§l<HELP> §7스폰, 콜로세움에서만 가능합니다.");
	}
}
