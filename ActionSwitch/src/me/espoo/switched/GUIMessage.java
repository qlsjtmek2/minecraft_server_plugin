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
		CloseGUI.add("��7������ Ŭ���� â�� �ݽ��ϴ�.");
		BackRanking.add("��7������ Ŭ���� ���� â���� ���ư��ϴ�.");
		NextRanking.add("��7������ Ŭ���� ���� â���� �Ѿ�ϴ�.");
		SheepTitle.add("��f�ؿ� �� ������ �ƹ� ������");
		SheepTitle.add("��f�����ø� ������ �ٲٽ� ��");
		SheepTitle.add("��f�ֽ��ϴ�.");
		OPTrade.add("��fŬ���Ͻø� ��ȯ�� ������ �߰��մϴ�.");
	}
}
