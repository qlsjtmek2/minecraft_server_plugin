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
		CloseGUI.add("��7������ Ŭ���� â�� �ݽ��ϴ�.");
		Leave.add("��7������ Ŭ���� ǥ���ǿ��� Ż���մϴ�.");
		Add.add("��7������ Ŭ���� ���ϴ� �÷��̾ �߰��մϴ�.");
	}
	
	public static List<String> getUserMan(String p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Method.getOnorOffLine(p) != null) {
			list.add("��7���� ����: ��a�¶���");
		} else {
			list.add("��7���� ����: ��c��������");
			if (QuitTime.isFile(p)) {
				try {
					String from = QuitTime.getMakeDate(Bukkit.getOfflinePlayer(p)).replaceAll(",", "-");
					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
					Date to = transFormat.parse(from);
					transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String Message = transFormat.format(to);
					if (QuitTime.isFile(p)) list.add("��7������ ���� �ð�: ��f" + Message);
				} catch (ParseException e) {}
			}
		}
		
		return list;
	}
	
	public static List<String> getManagerMan(String p)
	{
		List<String> list = new ArrayList<String>();
		
		if (Method.getOnorOffLine(p) != null) {
			list.add("��7���� ����: ��a�¶���");
		} else {
			list.add("��7���� ����: ��c��������");
			if (QuitTime.isFile(p)) {
				try {
					String from = QuitTime.getMakeDate(Bukkit.getOfflinePlayer(p)).replaceAll(",", "-");
					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
					Date to = transFormat.parse(from);
					transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String Message = transFormat.format(to);
					if (QuitTime.isFile(p)) list.add("��7������ ���� �ð�: ��f" + Message);
				} catch (ParseException e) {}
			}
		}

		list.add("");
		list.add("��8��l<HELP> ��7�÷��̾� ��Ŭ���� ����");
		list.add("��7����, ����Ʈ Ŭ���� ǥ���� ����");
		list.add("��7������ �絵���ݴϴ�.");
		
		return list;
	}
}
