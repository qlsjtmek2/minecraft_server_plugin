package me.espoo.switched.trade;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TradeAPI {
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        message = message.replaceAll("@", "§");
        return message;
	}
	
	public static boolean isTradeName(String name) {
		return TradeAPI.getResult(replaceAllColors(name), 1) != null ? true : false;
	}
	
	public static int getRecipeAmount(String name) {
		return TradeConfig.getInt(name + ".조합개수");
	}
	
	public static void setRecipeAmount(String name, int amount) {
		TradeConfig.setInt(name + ".조합개수", amount);
	}
	
	public static void setOne(String name, int amount, ItemStack item) {
		TradeConfig.setItemStack(name + ".첫번째칸." + amount, item);
	}
	
	public static void setTwo(String name, int amount, ItemStack item) {
		TradeConfig.setItemStack(name + ".두번째칸." + amount, item);
	}
	
	public static void setResult(String name, int amount, ItemStack item) {
		TradeConfig.setItemStack(name + ".결과물." + amount, item);
	}
	
	public static ItemStack getOne(String name, int amount) {
		return TradeConfig.getItemStack(name + ".첫번째칸." + amount);
	}
	
	public static ItemStack getTwo(String name, int amount) {
		return TradeConfig.getItemStack(name + ".두번째칸." + amount);
	}
	
	public static ItemStack getResult(String name, int amount) {
		return TradeConfig.getItemStack(name + ".결과물." + amount);
	}
	
	public static void addRecipe(String name, ItemStack item1, ItemStack item2, ItemStack result) {
		int num = getRecipeAmount(name) + 1;
		setRecipeAmount(name, num);
		setOne(name, num, item1);
		setTwo(name, num, item2);
		setResult(name, num, result);
	}
	
	public static void stack(Inventory GUI) {
		TradeGUI.Stack("§f이곳에서 조합 결과가 나옵니다.", 102, 0, 1, Arrays.asList(), 33, GUI);
	}
	
	public static boolean equals(ItemStack item1, ItemStack item2) {
		if (item1 == null && item2 == null)
			return true;
		else if (item1 != null && item1.getTypeId() == 119 && item2 == null)
			return true;
		else if (item1 == null || item2 == null)
			return false;
		else if (item1.isSimilar(item2)) 
			return true;
		return false;
	}
	
	public static boolean CheckAmount(ItemStack item1, ItemStack item2) {
		if (item1 == null && item2 == null)
			return true;
		else if (item1 != null && item1.getTypeId() == 119 && item2 == null)
			return true;
		else if (item1 == null || item2 == null)
			return false;
		else if (item1.getAmount() >= item2.getAmount()) 
			return true;
		return false;
	}
	
	public static boolean isInv(Player p) {
        int t = 0;
        ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
            ItemStack it = contents[j];
            if (it == null) {
                ++t;
            }
        }
        
        if (t <= 0) return false;
        return true;
	}
	
	public static String splitGUIName(String name) {
		if (name.contains("§2§0")) return name.split("§2§0")[0];
		else return null;
	}
	
	public static int CompareReturnSmaller(int i1, int i2) {
		if (i1 < i2) return i1;
		else if (i1 > i2) return i2;
		else return i1;
	}
	
	public static int getInventoryEmptyAmount(Player p) {
		int t = 0;
        ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
            ItemStack it = contents[j];
            if (it == null) {
                ++t;
            }
        }
        
        return t;
	}
	
	public static void setTradeGUI(Inventory GUI, Player p, ItemStack item1, ItemStack item2) {
		/*
		if (GUI.getItem(11) != null && GUI.getItem(12) == null) {
			int num = getNumOne(item1);
			if (num != -1) {
				if (getTwo(num) == null) {
					if (item1.getAmount() >= getOne(num).getAmount()) {
						TradeGUI.Stack3(getResult(num), 15, GUI);
						return;
					}
				}
			}
		}
		
		else if (GUI.getItem(11) == null && GUI.getItem(12) != null) {
			int num = getNumTwo(item2);
			if (num != -1) {
				if (getOne(num) == null) {
					if (item2.getAmount() >= getTwo(num).getAmount()) {
						TradeGUI.Stack3(getResult(num), 15, GUI);
						return;
					}
				}
			}
		}
		
		else if (GUI.getItem(11) != null && GUI.getItem(12) != null) {
			int num = getNumOne(item1);
			if (num != -1) {
				if (item1.getAmount() >= getOne(num).getAmount()) {
					if (getTwo(num).isSimilar(item2)) {
						if (item2.getAmount() >= getTwo(num).getAmount()) {
							TradeGUI.Stack3(getResult(num), 15, GUI);
							return;	
						}
					}	
				}
			}
		}
		
		else { stack(GUI); return; }*/
	}
}
