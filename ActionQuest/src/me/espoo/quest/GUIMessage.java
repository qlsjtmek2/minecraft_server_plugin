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
		SkipLore.add("��fŬ���� ����Ʈ ������ ��ŵ�մϴ�.");
		SkipLore.add("��7��o(��õ���� �ʴ� ����Դϴ�..)");
		CloseGUI.add("��7������ Ŭ���� â�� �ݽ��ϴ�.");
		BackRanking.add("��7������ Ŭ���� ���� â���� ���ư��ϴ�.");
		NowMove.add("��f������ Ŭ���� ����Ʈ");
		NowMove.add("��f��û�ڿ��� �̵��մϴ�.");
		ReViewContents.add("��f������ Ŭ���� ����Ʈ");
		ReViewContents.add("��f��ȭ ������ �ٽ� ���ϴ�.");
		BackInfo.add("��f���� ������ â���� ���ư��ϴ�.");
		nullTip.add("��f����Ʈ ���� �������� �ʽ��ϴ�.");
	}
}
