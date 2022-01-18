package me.espoo.is;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> ClickPartyInvite = new ArrayList<String>();
	public static List<String> ClickGuildInvite = new ArrayList<String>();
	public static List<String> ClickPvPAsk = new ArrayList<String>();
	public static List<String> ClickTradeAsk = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		ClickPartyInvite.add("§7클릭시 내 파티로 초대합니다.");
		ClickGuildInvite.add("§7클릭시 내가 가입한 길드로 초대합니다.");
		ClickPvPAsk.add("§7클릭시 플레이어에게 대련 신청을 합니다.");
		ClickTradeAsk.add("§7클릭시 플레이어에게 거래를 요청합니다.");
	}
}
