package me.espoo.quest;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> SkipLore = new ArrayList<String>();
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	public static List<String> NowMove = new ArrayList<String>();
	public static List<String> ReViewContents = new ArrayList<String>();
	public static List<String> BackInfo = new ArrayList<String>();
	public static List<String> nullTip = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		SkipLore.add("§f클릭시 퀘스트 내용을 스킵합니다.");
		SkipLore.add("§7§o(추천하지 않는 기능입니다..)");
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		BackRanking.add("§7아이템 클릭시 이전 창으로 돌아갑니다.");
		NowMove.add("§f아이템 클릭시 퀘스트");
		NowMove.add("§f요청자에게 이동합니다.");
		ReViewContents.add("§f아이템 클릭시 퀘스트");
		ReViewContents.add("§f대화 내용을 다시 봅니다.");
		BackInfo.add("§f전에 열었던 창으로 돌아갑니다.");
		nullTip.add("§f퀘스트 팁이 존재하지 않습니다.");
	}
}
