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
		ClickPartyInvite.add("��7Ŭ���� �� ��Ƽ�� �ʴ��մϴ�.");
		ClickGuildInvite.add("��7Ŭ���� ���� ������ ���� �ʴ��մϴ�.");
		ClickPvPAsk.add("��7Ŭ���� �÷��̾�� ��� ��û�� �մϴ�.");
		ClickTradeAsk.add("��7Ŭ���� �÷��̾�� �ŷ��� ��û�մϴ�.");
	}
}
