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
		Wood.add("§f이곳에다 소모할 §d§l재료§f를");
		Wood.add("§f올려주시기 바랍니다.");
	}
	
	public static void setLava()
	{
		Lava.add("§f이곳에서 요리된 §b§l음식");
		Lava.add("§f이 생성됩니다.");
	}
	
	public static void setWeek()
	{
		Week.add("§7§l==============================");
		Week.add("§c재료 1. §7:: §f<Mater1>, <Amount1>개");
		Week.add("§b재료 2. §7:: §f<Mater2>, <Amount2>개");
		Week.add("§e재료 3. §7:: §f<Mater3>, <Amount3>개");
		Week.add("§a재료 4. §7:: §f<Mater4>, <Amount4>개");
		Week.add("§f");
		Week.add("§6만들어질 요리 §7:: §f<Food>");
		Week.add("§6요리 효과 §7:: §f<Effect>");
		Week.add("§7§l==============================");
		Week.add("§7- <YesorNo>");
	}
	
	public static void setWeekFir()
	{
		WeekFir.add("§7§l==============================");
		WeekFir.add("§c재료 1. §7:: §f없음, 0개");
		WeekFir.add("§b재료 2. §7:: §f없음, 0개");
		WeekFir.add("§e재료 3. §7:: §f없음, 0개");
		WeekFir.add("§a재료 4. §7:: §f없음, 0개");
		WeekFir.add("§f");
		WeekFir.add("§6만들어질 요리 §7:: §f없음");
		WeekFir.add("§6요리 효과 §7:: §f없음");
		WeekFir.add("§7§l==============================");
		WeekFir.add("§7- §4요리 불가능 상태");
	}
	
	public static void setWeekMake()
	{
		WeekMake.add("§7§l====================");
		WeekMake.add("§f현재 §6<Food> §f요리를 만들고");
		WeekMake.add("§f있습니다. 요리를 만들기 까지");
		WeekMake.add("§e<Time> §f분 남았습니다.");
		WeekMake.add("§7§l====================");
		WeekMake.add("§7- §2요리 만드는 상태");
	}
	
	public static void setWeekFin()
	{
		WeekFin.add("§7§l====================");
		WeekFin.add("§6<Food> §f요리가 완성되었");
		WeekFin.add("§f습니다. 밑에서 찾아가세요!");
		WeekFin.add("§7§l====================");
		WeekFin.add("§7- §e요리 완성된 상태");
	}
	
	public static List<String> getWeekMake(Player p)
	{
		List<String> list = new ArrayList<String>();
		for (String str : WeekMake) {
			if (str.contains("<Food>")) {
				str = str.replaceAll("<Food>", PlayerYml.getInfoString(p, "만들고 있는 요리"));
			}
			
			if (str.contains("<Time>")) {
				str = str.replaceAll("<Time>", PlayerYml.getInfoString(p, "남은 시간"));
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
				str = str.replaceAll("<Food>", PlayerYml.getInfoString(p, "만들고 있는 요리"));
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
					str = str.replaceAll("<Mater1>", "없음");
					str = str.replaceAll("<Amount1>", "0");
				}
			}
			
			if (str.contains("<Mater2>") && str.contains("<Amount2>")) {
				if (v.getItem(20) != null && v.getItem(20).getType() != Material.AIR) {
					str = str.replaceAll("<Mater2>", v.getItem(20).getType().toString());
					str = str.replaceAll("<Amount2>", Integer.toString(v.getItem(20).getAmount()));
				} else {
					str = str.replaceAll("<Mater2>", "없음");
					str = str.replaceAll("<Amount2>", "0");
				}
			}
			
			if (str.contains("<Mater3>") && str.contains("<Amount3>")) {
				if (v.getItem(29) != null && v.getItem(29).getType() != Material.AIR) {
					str = str.replaceAll("<Mater3>", v.getItem(29).getType().toString());
					str = str.replaceAll("<Amount3>", Integer.toString(v.getItem(29).getAmount()));
				} else {
					str = str.replaceAll("<Mater3>", "없음");
					str = str.replaceAll("<Amount3>", "0");
				}
			}
			
			if (str.contains("<Mater4>") && str.contains("<Amount4>")) {
				if (v.getItem(38) != null && v.getItem(38).getType() != Material.AIR) {
					str = str.replaceAll("<Mater4>", v.getItem(38).getType().toString());
					str = str.replaceAll("<Amount4>", Integer.toString(v.getItem(38).getAmount()));
				} else {
					str = str.replaceAll("<Mater4>", "없음");
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
					str = str.replaceAll("<YesorNo>", "§a요리 가능 상태 §7(아이템 클릭시 요리진행)");
				}
			} else {
				if (str.contains("<Food>")) {
					str = str.replaceAll("<Food>", "없음");
				}
				
				if (str.contains("<Effect>")) {
					str = str.replaceAll("<Effect>", "없음");
				}
				
				if (str.contains("<YesorNo>")) {
					str = str.replaceAll("<YesorNo>", "§4요리 불가능 상태");
				}
			}
			
			list.add(str);
		} return list;
	}
}
