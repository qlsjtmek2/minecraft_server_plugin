package me.shinkhan.music;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		BackRanking.add("§7아이템 클릭시 이전 창으로 돌아갑니다.");
	}
	
	public static List<String> getType(Player p)
	{
		List<String> list = new ArrayList<String>();
		String str = null;
		
		if (main.Type.get(p) != null) {
			if (main.Type.get(p).equalsIgnoreCase("순차")) str = "순차적 반복";
			else if (main.Type.get(p).equalsIgnoreCase("무작위")) str = "무작위 반복";
			else if (main.Type.get(p).equalsIgnoreCase("한곡")) str = "한곡 반복";
			else str = "반복 없음";
		} else {
			str = "반복 없음";
		}
		
		list.add("§7재생 타입: §f" + str);
		return list;
	}
	
	public static List<String> getSong(Player p)
	{
		List<String> list = new ArrayList<String>();
		if (main.Song.get(p) == null) list.add("§7재생 음악: §f없음");
		else list.add("§7재생 음악: §f" + Method.getFileList().get(main.Song.get(p)));
		return list;
	}
}
