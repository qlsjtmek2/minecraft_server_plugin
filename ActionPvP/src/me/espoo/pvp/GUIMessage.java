package me.espoo.pvp;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> one = new ArrayList<String>();
	public static List<String> eosksxn = new ArrayList<String>();
	public static List<String> stop = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		stop.add("§7클릭시 매칭중인 대련을 취소합니다.");

		eosksxn.add("§f클릭시 대난투에 입장합니다.");
		eosksxn.add("");
		eosksxn.add("§8§l<HELP> §7대난투는 모든 플레");
		eosksxn.add("§7이어가 자유로이 출입할 수 있는");
		eosksxn.add("§7광할한 서바이벌 PVP장입니다.");
		
		one.add("§f클릭시 1:1 대련 매칭을 돌립니다.");
		one.add("§f파티가 없을 때만 입장 가능합니다.");
		one.add("");
		one.add("§8§l<HELP> §7매칭은 스폰에서만 가능합니다.");
	}
}
