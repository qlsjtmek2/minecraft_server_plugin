package me.shinkhan.epm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	
	public static void setGUIMessage()
	{
		CloseGUI.add("��7Ŭ���� �κ��丮 â�� �ݽ��ϴ�.");
	}
	
	public static List<String> getPetLore(String str)
	{
		List<String> list = new ArrayList<String>();
		list.add("��7������ Ŭ���� �� ������ �����մϴ�.");
		list.add("��0��m��n" + str);
		return list;
	}
	
	public static List<String> getPetSet(Player p)
	{
		List<String> list = new ArrayList<String>();
		list.add("��7Ŭ���� ���� Ȱ��ȭ�ϰų� ��Ȱ��ȭ �մϴ�.");
		
		if (API.isShowPet(p)) {
			list.add("��7���� �������� ��: ��f" + API.PetToString(API.getSetPet(p)));
			list.add("��7�� Ȱ��ȭ ����: ��aȰ��ȭ");
		} else list.add("��7�� Ȱ��ȭ ����: ��c��Ȱ��ȭ");
		return list;
	}
}
