package me.shinkhan.shop;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		BackRanking.add("§7아이템 클릭시 이전 창으로 돌아갑니다.");
	}
}
