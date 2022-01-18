package me.shinkhan.epm;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	public static void openGUI(Player p) {
		Inventory GUI = Bukkit.createInventory(null, 54, "Æê ¸ñ·Ï");
		
		int num = 0;
		for (int i = 0; i < 18; i++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 1;
			else if (i == 2) num = 2;
			else if (i == 3) num = 3;
			else if (i == 4) num = 4;
			else if (i == 5) num = 5;
			else if (i == 6) num = 6;
			else if (i == 7) num = 7;
			else if (i == 8) num = 8;
			else if (i == 9) num = 45;
			else if (i == 10) num = 46;
			else if (i == 11) num = 47;
			else if (i == 12) num = 48;
			else if (i == 13) num = 49;
			else if (i == 14) num = 50;
			else if (i == 15) num = 51;
			else if (i == 16) num = 52;
			else if (i == 17) num = 53;
			
			Stack2("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		num = 9;
		for (String str : API.getPetList(p)) {
			String name = API.PetToString(str);
			String code = API.PetToPetCode(str);
			if (code != null && name != null) {
				Stack("¡×6¡×l" + name, Integer.parseInt(code.split(":")[0]), 
					  Integer.parseInt(code.split(":")[1]), 1, GUIMessage.getPetLore(str), num, GUI);
				num++; if (num == 43) break;
			}
		}
		
		Stack("¡×fÃ¢ ´Ý±â", 324, 0, 1, GUIMessage.CloseGUI, 53, GUI);
		Stack("¡×eÆê Á¤º¸", 323, 0, 1, GUIMessage.getPetSet(p), 44, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack2(String Display, int ID, int DATA, int STACK, List<Object> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
}
