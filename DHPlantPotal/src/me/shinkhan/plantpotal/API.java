package me.shinkhan.plantpotal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class API {
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
        return message;
	}
	
	public static String getFinalArg(String[] args, int start)
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			if (i != start) {
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
	
	public static List<String> getPotalList() {
		return Config.getList("포탈 목록");
	}
	
	public static void addPotalList(String name) {
		List<String> list = getPotalList();
		if (list == null || list.isEmpty()) list = new ArrayList<String>();
		list.add(name);
		API.setPotalList(list);
	}
	
	public static void removePotalList(String name) {
		List<String> list = getPotalList();
		if (list == null || list.isEmpty()) return;
		list.remove(name);
		API.setPotalList(list);
	}
	
	public static void setPotalList(List<String> list) {
		Config.setList("포탈 목록", list);
	}
	
	public static String getPotalName(String name) {
		return Config.getString(name + ".이름");
	}
	
	public static int getPotalCode(String name) {
		return Config.getInt(name + ".코드");
	}
	
	public static int getPotalDateCode(String name) {
		return Config.getInt(name + ".데이터 코드");
	}
	
	public static List<String> getPotalInfo(String name) {
		return Config.getList(name + ".설명");
	}
	
	public static boolean getPotalMove(String name) {
		return Config.getBoolean(name + ".이동");
	}
	
	public static void setPotalMove(String name, boolean is) {
		Config.setBoolean(name + ".이동", is);
	}
	
	public static void setPotalName(String name, String itemname) {
		Config.setString(name + ".이름", itemname);
	}
	
	public static void setPotalCode(String name, int code) {
		Config.setInt(name + ".코드", code);
	}
	
	public static void setPotalDateCode(String name, int code) {
		Config.setInt(name + ".데이터 코드", code);
	}
	
	public static void setPotalInfo(String name, List<String> info) {
		Config.setList(name + ".설명", info);
	}
	
	public static boolean isPotalList(String name) {
		List<String> list = getPotalList();
		if (list == null || list.isEmpty()) return false;
		if (list.contains(name)) return true;
		return false;
	}
	
	public static ItemStack getInfoItem(String name) {
		if (isPotalList(name)) {
			ItemStack item = new MaterialData(getPotalCode(name), (byte) getPotalDateCode(name)).toItemStack(1);
			ItemMeta item_Meta = item.getItemMeta();
			List<String> list = new ArrayList<String>();
			list.add("§7소유권: §f" + name);
			if (API.getPotalMove(name)) list.add("§7이동 여부: §a활성화");
			else list.add("§7이동 여부: §c비활성화");
			list.add("§0§mDHPlantPotal");
			for (String str : getPotalInfo(name)) list.add("§f" + str);
			list.add("§0§m" + name);
			item_Meta.setLore(list);
			item_Meta.setDisplayName("§f" + getPotalName(name));
			item.setItemMeta(item_Meta);
			return item;
		} return null;
	}
	
	public static void GoToThePlant(Player p, String name) {
		boolean is = p.isOp();
		p.setOp(true);
		Bukkit.getServer().dispatchCommand(p, "섬 warp " + name);
		
		if (is) {
			p.setOp(is);
		} else {
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
		}
	}
	
	public static void setIslandWarp(Player p, String name) {
		boolean is = p.isOp();
		p.setOp(true);
		Bukkit.getServer().dispatchCommand(p, "섬 setwarp");
		
		if (!is) {
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
		}
	}
}
