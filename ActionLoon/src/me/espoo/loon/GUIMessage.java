package me.espoo.loon;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> Eander = new ArrayList<String>();
	public static List<String> Tip1 = new ArrayList<String>();
	public static List<String> Tip2 = new ArrayList<String>();
	public static List<String> Loon = new ArrayList<String>();
	
	public static void setEander()
	{
		Eander.add("§f이곳에다 장착할 §d§l룬§f을");
		Eander.add("§f올려주시기 바랍니다.");
	}
	
	public static void setTip()
	{
		Tip1.add("§7§l==================");
		Tip1.add("§61. §f장착할 룬을 집는다.");
		Tip1.add("§62. §f옆에 있는 공간에 룬을 넣는다.");
		Tip1.add("§63. §fE키로 인벤토리를 닫으면 완료");
		Tip1.add("§7§l==================");
		
		Tip2.add("§7§l============================");
		Tip2.add("§6              룬 종류들");
		Tip2.add("§c불 §6속성 §7:: §f상대방에게 불이 붙는다.");
		Tip2.add("§b바람 §6속성 §7:: §f영구적으로 신속이 붙는다.");
		Tip2.add("§d치유 §6속성 §7:: §f영구적으로 재생이 걸린다.");
		Tip2.add("§7§l어둠 §6속성 §7:: §f상대방에게 피로, 멀미를 준다.");
		Tip2.add("§a독 §6속성 §7:: §f상대방에게 독을 준다.");
		Tip2.add("§e부패 §6속성 §7:: §f상대방에게 위더 효과를 준다.");
		Tip2.add("§3얼음 §6속성 §7:: §f상대방에게 구속 효과를 준다.");
		Tip2.add("§7§l============================");
	}
	
	public static void setLoon()
	{
		Loon.add("불");
		Loon.add("바람");
		Loon.add("치유");
		Loon.add("어둠");
		Loon.add("독");
		Loon.add("부패");
		Loon.add("얼음");
	}
}
