package me.espoo.switched;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> SheepTitle = new ArrayList<String>();
	public static List<String> OPTrade = new ArrayList<String>();
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	public static List<String> NextRanking = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		BackRanking.add("§7아이템 클릭시 이전 창으로 돌아갑니다.");
		NextRanking.add("§7아이템 클릭시 다음 창으로 넘어갑니다.");
		SheepTitle.add("§f밑에 빈 공간에 아무 양털을");
		SheepTitle.add("§f넣으시면 색깔을 바꾸실 수");
		SheepTitle.add("§f있습니다.");
		OPTrade.add("§f클릭하시면 교환기 조합을 추가합니다.");
	}
}
