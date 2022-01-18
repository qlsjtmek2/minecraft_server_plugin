package me.espoo.upgrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Method {
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "」1");
        message = message.replaceAll("&2", "」2");
        message = message.replaceAll("&3", "」3");
        message = message.replaceAll("&4", "」4");
        message = message.replaceAll("&5", "」5");
        message = message.replaceAll("&6", "」6");
        message = message.replaceAll("&7", "」7");
        message = message.replaceAll("&8", "」8");
        message = message.replaceAll("&9", "」9");
        message = message.replaceAll("&0", "」0");
        message = message.replaceAll("&a", "」a");
        message = message.replaceAll("&b", "」b");
        message = message.replaceAll("&c", "」c");
        message = message.replaceAll("&d", "」d");
        message = message.replaceAll("&e", "」e");
        message = message.replaceAll("&f", "」f");
        message = message.replaceAll("&k", "」k");
        message = message.replaceAll("&l", "」l");
        message = message.replaceAll("&m", "」m");
        message = message.replaceAll("&n", "」n");
        message = message.replaceAll("&o", "」o");
        message = message.replaceAll("&r", "」r");
        return message;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	public static boolean isItemName(Player p, ItemStack i, String name, int code, int data) {
		String i1 = ChatColor.stripColor(name);
		String i2 = ChatColor.stripColor(name + " +1");
		String i3 = ChatColor.stripColor(name + " +2");
		String i4 = ChatColor.stripColor(name + " +3");
		String i5 = ChatColor.stripColor(name + " +4");
		String i6 = ChatColor.stripColor(name + " +5");
		String i7 = ChatColor.stripColor(name + " +6");
		String i8 = ChatColor.stripColor(name + " +7");
		String i9 = ChatColor.stripColor(name + " +8");
		String i10 = ChatColor.stripColor(name + " +9");
		String i11 = ChatColor.stripColor(name + " +10");
		if (i == null) return false;
		if (i.getItemMeta() == null) return false;
		if (i.getTypeId() != code) return false;
		if (i.getData().getData() != data) return false;
		
		String y = ChatColor.stripColor(i.getItemMeta().getDisplayName());
		if (y == null) return false;
		if (y.equalsIgnoreCase(i1)) return true;
		else if (y.equalsIgnoreCase(i2)) return true;
		else if (y.equalsIgnoreCase(i3)) return true;
		else if (y.equalsIgnoreCase(i4)) return true;
		else if (y.equalsIgnoreCase(i5)) return true;
		else if (y.equalsIgnoreCase(i6)) return true;
		else if (y.equalsIgnoreCase(i7)) return true;
		else if (y.equalsIgnoreCase(i8)) return true;
		else if (y.equalsIgnoreCase(i9)) return true;
		else if (y.equalsIgnoreCase(i10)) return true;
		else if (y.equalsIgnoreCase(i11)) return true;
		else return false;
	}
}
