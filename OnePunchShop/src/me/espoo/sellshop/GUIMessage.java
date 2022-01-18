package me.espoo.sellshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.espoo.cooking.FoodList;

public class GUIMessage {
	public static List<String> Lava = new ArrayList<String>();
	public static List<String> Button = new ArrayList<String>();
	public static List<String> ButtonFir = new ArrayList<String>();
	public static List<String> Tip = new ArrayList<String>();
	public static List<String> Back = new ArrayList<String>();
	
	public static void setLava()
	{
		Lava.add("��f�̰����� �Ǹ��� ��e��l������");
		Lava.add("��f�� �÷��ֽñ� �ٶ��ϴ�.");
	}
	
	public static void setBack()
	{
		Back.add("��f���� ������ â���� ���ư��ϴ�.");
	}
	
	public static void setTip()
	{
		Tip.add("��f�� �Ǹ� ������ ��c��l���� ����ǰ��f,");
		Tip.add("��7��l������f, ��e��l�丮��f�� �ǸŰ� �����մϴ�.");
	}
	
	public static void setButton()
	{
		Button.add("��7��l========================");
		Button.add("��6�Ǹ� ������ ��7:: ��f<Item>, <Amount>��");
		Button.add("��b�Ǹ� ���� ��7:: ��f<Money>��");
		Button.add("��a������ Ÿ�� ��7:: ��f<Type>");
		Button.add("��7��l========================");
		Button.add("��7- <YesorNo>");
	}
	
	public static void setButtonFir()
	{
		ButtonFir.add("��7��l========================");
		ButtonFir.add("��6�Ǹ� ������ ��7:: ��f����, 0��");
		ButtonFir.add("��b�Ǹ� ���� ��7:: ��f0��");
		ButtonFir.add("��a������ Ÿ�� ��7:: ��f����");
		ButtonFir.add("��7��l========================");
		ButtonFir.add("��7- ��4�Ǹ� �Ұ��� ����");
	}
	
	public static List<String> getButton(Player p, InventoryView v)
	{
		List<String> list = new ArrayList<String>();
		for (String str : Button) {
			boolean isSell = false;
			ItemStack item = v.getItem(24);
			String type = null;
			String itemname = null;
			int amount = 0;
			int money = 0;
			int num = 0;
			
			if (item.getTypeId() == 4) {
				isSell = true;
				itemname = "��";
				amount = item.getAmount();
				type = "����";
				money = 2 * amount;
			}
			
			else if (item.getTypeId() == 263) {
				isSell = true;
				itemname = "��ź";
				amount = item.getAmount();
				type = "����";
				money = 5 * amount;
			}
			
			else if (item.getTypeId() == 265) {
				isSell = true;
				itemname = "ö";
				amount = item.getAmount();
				type = "����";
				money = 12 * amount;
			}
			
			else if (item.getTypeId() == 266) {
				isSell = true;
				itemname = "��";
				amount = item.getAmount();
				type = "����";
				money = 15 * amount;
			}
			
			else if (item.getTypeId() == 351 && item.getData().getData() == (byte) 4) {
				isSell = true;
				itemname = "û�ݼ�";
				amount = item.getAmount();
				type = "����";
				money = 18 * amount;
			}
			
			else if (item.getTypeId() == 331) {
				isSell = true;
				itemname = "���彺��";
				amount = item.getAmount();
				type = "����";
				money = 20 * amount;
			}
			
			else if (item.getTypeId() == 406) {
				isSell = true;
				itemname = "�״� ����";
				amount = item.getAmount();
				type = "����";
				money = 18 * amount;
			}
			
			else if (item.getTypeId() == 264) {
				isSell = true;
				itemname = "���̾Ƹ��";
				amount = item.getAmount();
				type = "����";
				money = 150 * amount;
			}
			
			else if (item.getTypeId() == 388) {
				isSell = true;
				itemname = "���޶���";
				amount = item.getAmount();
				type = "����";
				money = 166 * amount;
			}
			
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasDisplayName()) {
					if (item.getItemMeta().hasLore()) {
						for (String st : FoodList.StringList) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "�丮";
								money = FoodList.IntegerList.get(num) * 100 * amount;
								break;
							} else {
								num++;
							}
						}
						
						for (String st : BootyList.OneList) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "����ǰ";
								money = 80 * amount;
								break;
							} else {
								num++;
							}
						}
						
						for (String st : BootyList.TwoList) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "����ǰ";
								money = 300 * amount;
								break;
							} else {
								num++;
							}
						}
						
						for (String st : BootyList.ThreeList) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "����ǰ";
								money = 1500 * amount;
								break;
							} else {
								num++;
							}
						}
						
						for (String st : BootyList.Three2List) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "����ǰ";
								money = 1800 * amount;
								break;
							} else {
								num++;
							}
						}
						
						for (String st : BootyList.FourList) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "����ǰ";
								money = 4000 * amount;
								break;
							} else {
								num++;
							}
						}
						
						for (String st : BootyList.FiveList) {
							if (st.equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
								isSell = true;
								itemname = st;
								amount = item.getAmount();
								type = "����ǰ";
								money = 5500 * amount;
								break;
							} else {
								num++;
							}
						}
					}
				}
			}

			
			if (isSell) {
				if (str.contains("<Item>")) {
					str = str.replaceAll("<Item>", itemname);
				}
				
				if (str.contains("<Amount>")) {
					str = str.replaceAll("<Amount>", Integer.toString(amount));
				}
				
				if (str.contains("<Money>")) {
					str = str.replaceAll("<Money>", Integer.toString(money));
				}
				
				if (str.contains("<Type>")) {
					str = str.replaceAll("<Type>", type);
				}
				
				if (str.contains("<YesorNo>")) {
					str = str.replaceAll("<YesorNo>", "��a�Ǹ� ���� ���� ��7(Ŭ���� ������ �Ǹ�)");
				}
			} else {
				if (str.contains("<Item>")) {
					str = str.replaceAll("<Item>", "����");
				}
				
				if (str.contains("<Amount>")) {
					str = str.replaceAll("<Amount>", "0");
				}
				
				if (str.contains("<Money>")) {
					str = str.replaceAll("<Money>", "0");
				}
				
				if (str.contains("<Type>")) {
					str = str.replaceAll("<Type>", "����");
				}
				
				if (str.contains("<YesorNo>")) {
					str = str.replaceAll("<YesorNo>", "��4�Ǹ� �Ұ��� ����");
				}
			}
			
			list.add(str);
		} return list;
	}
}
