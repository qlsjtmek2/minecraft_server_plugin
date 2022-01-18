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
		Lava.add("§f이곳에다 판매할 §e§l아이템");
		Lava.add("§f을 올려주시기 바랍니다.");
	}
	
	public static void setBack()
	{
		Back.add("§f전에 열었던 창으로 돌아갑니다.");
	}
	
	public static void setTip()
	{
		Tip.add("§f이 판매 상점은 §c§l괴인 전리품§f,");
		Tip.add("§7§l광물§f, §e§l요리§f만 판매가 가능합니다.");
	}
	
	public static void setButton()
	{
		Button.add("§7§l========================");
		Button.add("§6판매 아이템 §7:: §f<Item>, <Amount>개");
		Button.add("§b판매 가격 §7:: §f<Money>원");
		Button.add("§a아이템 타입 §7:: §f<Type>");
		Button.add("§7§l========================");
		Button.add("§7- <YesorNo>");
	}
	
	public static void setButtonFir()
	{
		ButtonFir.add("§7§l========================");
		ButtonFir.add("§6판매 아이템 §7:: §f없음, 0개");
		ButtonFir.add("§b판매 가격 §7:: §f0원");
		ButtonFir.add("§a아이템 타입 §7:: §f없음");
		ButtonFir.add("§7§l========================");
		ButtonFir.add("§7- §4판매 불가능 상태");
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
				itemname = "돌";
				amount = item.getAmount();
				type = "광물";
				money = 2 * amount;
			}
			
			else if (item.getTypeId() == 263) {
				isSell = true;
				itemname = "석탄";
				amount = item.getAmount();
				type = "광물";
				money = 5 * amount;
			}
			
			else if (item.getTypeId() == 265) {
				isSell = true;
				itemname = "철";
				amount = item.getAmount();
				type = "광물";
				money = 12 * amount;
			}
			
			else if (item.getTypeId() == 266) {
				isSell = true;
				itemname = "금";
				amount = item.getAmount();
				type = "광물";
				money = 15 * amount;
			}
			
			else if (item.getTypeId() == 351 && item.getData().getData() == (byte) 4) {
				isSell = true;
				itemname = "청금석";
				amount = item.getAmount();
				type = "광물";
				money = 18 * amount;
			}
			
			else if (item.getTypeId() == 331) {
				isSell = true;
				itemname = "레드스톤";
				amount = item.getAmount();
				type = "광물";
				money = 20 * amount;
			}
			
			else if (item.getTypeId() == 406) {
				isSell = true;
				itemname = "네더 석영";
				amount = item.getAmount();
				type = "광물";
				money = 18 * amount;
			}
			
			else if (item.getTypeId() == 264) {
				isSell = true;
				itemname = "다이아몬드";
				amount = item.getAmount();
				type = "광물";
				money = 150 * amount;
			}
			
			else if (item.getTypeId() == 388) {
				isSell = true;
				itemname = "에메랄드";
				amount = item.getAmount();
				type = "광물";
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
								type = "요리";
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
								type = "전리품";
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
								type = "전리품";
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
								type = "전리품";
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
								type = "전리품";
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
								type = "전리품";
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
								type = "전리품";
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
					str = str.replaceAll("<YesorNo>", "§a판매 가능 상태 §7(클릭시 아이템 판매)");
				}
			} else {
				if (str.contains("<Item>")) {
					str = str.replaceAll("<Item>", "없음");
				}
				
				if (str.contains("<Amount>")) {
					str = str.replaceAll("<Amount>", "0");
				}
				
				if (str.contains("<Money>")) {
					str = str.replaceAll("<Money>", "0");
				}
				
				if (str.contains("<Type>")) {
					str = str.replaceAll("<Type>", "없음");
				}
				
				if (str.contains("<YesorNo>")) {
					str = str.replaceAll("<YesorNo>", "§4판매 불가능 상태");
				}
			}
			
			list.add(str);
		} return list;
	}
}
