package me.shinkhan.treasurechest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class API {
	// 0: X, 1: Y, 2: Z, 3: World, 4: Yaw, 5: Pitch
	public static String LocationToString(Location loc) {
	    return String.valueOf(loc.getX()) + "," + String.valueOf(loc.getY()) + "," + String.valueOf(loc.getZ()) + "," + loc.getWorld().getName() + "," + loc.getYaw() + "," + loc.getPitch();
	}
	
	public static Location StringToLocation(String s) {
		String[] loc = s.split(",");
		Location loc2 = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
		return loc2;
	}
	
	public static void Stack(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
	
	public static void setGUI(Inventory inv) {
		for (int i = 0; i <= 27; i++) {
			if (Config.getItemStack(Integer.toString(i)) != null) Config.setItemStack(Integer.toString(i), null);
		}
		
		int num = 0;
		for (ItemStack item : inv.getContents()) {
			if (item != null) {
				Config.setItemStack(Integer.toString(num), item);
			} num++;
		}
		//c.getKeys(false)
	}

	public static boolean isMap() {
		boolean is = false;
		for (int i = 0; i <= 27; i++) {
			if (Config.getItemStack(Integer.toString(i)) != null) {
				is = true;
				break;
			}
		}
		
		return is;
	}
	
	public static void setMap() {
		Inventory GUI = Bukkit.createInventory(null, 27, "보급 상자");
		
		for (int i = 0; i <= 27; i++) {
			ItemStack item = Config.getItemStack(Integer.toString(i));
			if (item != null) {
				API.Stack(item, i, GUI);
			}
		}
		
		main.gui = GUI;
	}
}
