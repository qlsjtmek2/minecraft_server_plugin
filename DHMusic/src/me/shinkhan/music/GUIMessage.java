package me.shinkhan.music;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("��7������ Ŭ���� â�� �ݽ��ϴ�.");
		BackRanking.add("��7������ Ŭ���� ���� â���� ���ư��ϴ�.");
	}
	
	public static List<String> getType(Player p)
	{
		List<String> list = new ArrayList<String>();
		String str = null;
		
		if (main.Type.get(p) != null) {
			if (main.Type.get(p).equalsIgnoreCase("����")) str = "������ �ݺ�";
			else if (main.Type.get(p).equalsIgnoreCase("������")) str = "������ �ݺ�";
			else if (main.Type.get(p).equalsIgnoreCase("�Ѱ�")) str = "�Ѱ� �ݺ�";
			else str = "�ݺ� ����";
		} else {
			str = "�ݺ� ����";
		}
		
		list.add("��7��� Ÿ��: ��f" + str);
		return list;
	}
	
	public static List<String> getSong(Player p)
	{
		List<String> list = new ArrayList<String>();
		if (main.Song.get(p) == null) list.add("��7��� ����: ��f����");
		else list.add("��7��� ����: ��f" + Method.getFileList().get(main.Song.get(p)));
		return list;
	}
}
