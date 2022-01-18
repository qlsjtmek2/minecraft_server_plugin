package me.shinkhan.epm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("§7클릭시 인벤토리 창을 닫습니다.");
	}
	
	public static List<String> getPetLore(String str)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7아이템 클릭시 이 펫으로 변경합니다.");
		list.add("§0§m§n" + str);
		return list;
	}
	
	public static List<String> getPetSet(Player p)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7클릭시 펫을 활성화하거나 비활성화 합니다.");
		
		if (API.isShowPet(p)) {
			list.add("§7현재 착용중인 펫: §f" + API.PetToString(API.getSetPet(p)));
			list.add("§7펫 활성화 여부: §a활성화");
		} else list.add("§7펫 활성화 여부: §c비활성화");
		return list;
	}
}
