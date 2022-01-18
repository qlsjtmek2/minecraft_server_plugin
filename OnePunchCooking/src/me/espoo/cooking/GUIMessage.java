package me.espoo.cooking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class GUIMessage {
	public static List<String> Wood = new ArrayList<String>();
	public static List<String> Week = new ArrayList<String>();
	public static List<String> WeekFir = new ArrayList<String>();
	public static List<String> WeekMake = new ArrayList<String>();
	public static List<String> WeekFin = new ArrayList<String>();
	public static List<String> Lava = new ArrayList<String>();
	
	public static void setWood()
	{
		Wood.add("��f�̰����� �Ҹ��� ��d��l����f��");
		Wood.add("��f�÷��ֽñ� �ٶ��ϴ�.");
	}
	
	public static void setLava()
	{
		Lava.add("��f�̰����� �丮�� ��b��l����");
		Lava.add("��f�� �����˴ϴ�.");
	}
	
	public static void setWeek()
	{
		Week.add("��7��l==============================");
		Week.add("��c��� 1. ��7:: ��f<Mater1>, <Amount1>��");
		Week.add("��b��� 2. ��7:: ��f<Mater2>, <Amount2>��");
		Week.add("��e��� 3. ��7:: ��f<Mater3>, <Amount3>��");
		Week.add("��a��� 4. ��7:: ��f<Mater4>, <Amount4>��");
		Week.add("��f");
		Week.add("��6������� �丮 ��7:: ��f<Food>");
		Week.add("��6�丮 ȿ�� ��7:: ��f<Effect>");
		Week.add("��7��l==============================");
		Week.add("��7- <YesorNo>");
	}
	
	public static void setWeekFir()
	{
		WeekFir.add("��7��l==============================");
		WeekFir.add("��c��� 1. ��7:: ��f����, 0��");
		WeekFir.add("��b��� 2. ��7:: ��f����, 0��");
		WeekFir.add("��e��� 3. ��7:: ��f����, 0��");
		WeekFir.add("��a��� 4. ��7:: ��f����, 0��");
		WeekFir.add("��f");
		WeekFir.add("��6������� �丮 ��7:: ��f����");
		WeekFir.add("��6�丮 ȿ�� ��7:: ��f����");
		WeekFir.add("��7��l==============================");
		WeekFir.add("��7- ��4�丮 �Ұ��� ����");
	}
	
	public static void setWeekMake()
	{
		WeekMake.add("��7��l====================");
		WeekMake.add("��f���� ��6<Food> ��f�丮�� �����");
		WeekMake.add("��f�ֽ��ϴ�. �丮�� ����� ����");
		WeekMake.add("��e<Time> ��f�� ���ҽ��ϴ�.");
		WeekMake.add("��7��l====================");
		WeekMake.add("��7- ��2�丮 ����� ����");
	}
	
	public static void setWeekFin()
	{
		WeekFin.add("��7��l====================");
		WeekFin.add("��6<Food> ��f�丮�� �ϼ��Ǿ�");
		WeekFin.add("��f���ϴ�. �ؿ��� ã�ư�����!");
		WeekFin.add("��7��l====================");
		WeekFin.add("��7- ��e�丮 �ϼ��� ����");
	}
	
	public static List<String> getWeekMake(Player p)
	{
		List<String> list = new ArrayList<String>();
		for (String str : WeekMake) {
			if (str.contains("<Food>")) {
				str = str.replaceAll("<Food>", PlayerYml.getInfoString(p, "����� �ִ� �丮"));
			}
			
			if (str.contains("<Time>")) {
				str = str.replaceAll("<Time>", PlayerYml.getInfoString(p, "���� �ð�"));
			}
			
			list.add(str);
		}
		
		return list;
	}
	
	public static List<String> getWeekFin(Player p)
	{
		List<String> list = new ArrayList<String>();
		for (String str : WeekFin) {
			if (str.contains("<Food>")) {
				str = str.replaceAll("<Food>", PlayerYml.getInfoString(p, "����� �ִ� �丮"));
			}
			
			list.add(str);
		}
		
		return list;
	}
	
	public static List<String> getWeek(Player p, InventoryView v)
	{
		List<String> list = new ArrayList<String>();
		for (String str : Week) {
			if (str.contains("<Mater1>") && str.contains("<Amount1>")) {
				if (v.getItem(11) != null && v.getItem(11).getType() != Material.AIR) {
					str = str.replaceAll("<Mater1>", v.getItem(11).getType().toString());
					str = str.replaceAll("<Amount1>", Integer.toString(v.getItem(11).getAmount()));
				} else {
					str = str.replaceAll("<Mater1>", "����");
					str = str.replaceAll("<Amount1>", "0");
				}
			}
			
			if (str.contains("<Mater2>") && str.contains("<Amount2>")) {
				if (v.getItem(20) != null && v.getItem(20).getType() != Material.AIR) {
					str = str.replaceAll("<Mater2>", v.getItem(20).getType().toString());
					str = str.replaceAll("<Amount2>", Integer.toString(v.getItem(20).getAmount()));
				} else {
					str = str.replaceAll("<Mater2>", "����");
					str = str.replaceAll("<Amount2>", "0");
				}
			}
			
			if (str.contains("<Mater3>") && str.contains("<Amount3>")) {
				if (v.getItem(29) != null && v.getItem(29).getType() != Material.AIR) {
					str = str.replaceAll("<Mater3>", v.getItem(29).getType().toString());
					str = str.replaceAll("<Amount3>", Integer.toString(v.getItem(29).getAmount()));
				} else {
					str = str.replaceAll("<Mater3>", "����");
					str = str.replaceAll("<Amount3>", "0");
				}
			}
			
			if (str.contains("<Mater4>") && str.contains("<Amount4>")) {
				if (v.getItem(38) != null && v.getItem(38).getType() != Material.AIR) {
					str = str.replaceAll("<Mater4>", v.getItem(38).getType().toString());
					str = str.replaceAll("<Amount4>", Integer.toString(v.getItem(38).getAmount()));
				} else {
					str = str.replaceAll("<Mater4>", "����");
					str = str.replaceAll("<Amount4>", "0");
				}
			}
			
			boolean isFood = false;
			int num = 0;
			List<Material> lt = new ArrayList<Material>();
			lt.add(v.getItem(11).getType());
			lt.add(v.getItem(20).getType());
			lt.add(v.getItem(29).getType());
			lt.add(v.getItem(38).getType());
			
			for (List<Material> it : FoodList.FoodList) {
				if (it.equals(lt)) {
					isFood = true;
					break;
				} num++;
			}
			
			if (isFood) {
				if (str.contains("<Food>")) {
					str = str.replaceAll("<Food>", FoodList.StringList.get(num));
				}
				
				if (str.contains("<Effect>")) {
					str = str.replaceAll("<Effect>", FoodList.EffectList.get(num));
				}
				
				if (str.contains("<YesorNo>")) {
					str = str.replaceAll("<YesorNo>", "��a�丮 ���� ���� ��7(������ Ŭ���� �丮����)");
				}
			} else {
				if (str.contains("<Food>")) {
					str = str.replaceAll("<Food>", "����");
				}
				
				if (str.contains("<Effect>")) {
					str = str.replaceAll("<Effect>", "����");
				}
				
				if (str.contains("<YesorNo>")) {
					str = str.replaceAll("<YesorNo>", "��4�丮 �Ұ��� ����");
				}
			}
			
			list.add(str);
		} return list;
	}
}
