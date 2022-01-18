package me.shinkhan.pgui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> Leave = new ArrayList<String>();
	public static List<String> Add = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		Leave.add("§7아이템 클릭시 표지판에서 탈퇴합니다.");
		Add.add("§7아이템 클릭시 원하는 플레이어를 추가합니다.");
	}
	
	public static List<String> getUserMan(String p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Method.getOnorOffLine(p) != null) {
			list.add("§7접속 상태: §a온라인");
		} else {
			list.add("§7접속 상태: §c오프라인");
			if (QuitTime.isFile(p)) {
				try {
					String from = QuitTime.getMakeDate(Bukkit.getOfflinePlayer(p)).replaceAll(",", "-");
					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
					Date to = transFormat.parse(from);
					transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String Message = transFormat.format(to);
					if (QuitTime.isFile(p)) list.add("§7마지막 접속 시간: §f" + Message);
				} catch (ParseException e) {}
			}
		}
		
		return list;
	}
	
	public static List<String> getManagerMan(String p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Method.getOnorOffLine(p) != null) {
			list.add("§7접속 상태: §a온라인");
		} else {
			list.add("§7접속 상태: §c오프라인");
			if (QuitTime.isFile(p)) {
				try {
					String from = QuitTime.getMakeDate(Bukkit.getOfflinePlayer(p)).replaceAll(",", "-");
					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
					Date to = transFormat.parse(from);
					transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String Message = transFormat.format(to);
					if (QuitTime.isFile(p)) list.add("§7마지막 접속 시간: §f" + Message);
				} catch (ParseException e) {}
			}
		}

		list.add("");
		list.add("§8§l<HELP> §7플레이어 우클릭시 강제");
		list.add("§7퇴장, 쉬프트 클릭시 표지판 관리");
		list.add("§7권한을 양도해줍니다.");
		
		return list;
	}
}
