package me.espoo.loon;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	static GUIMessage G;
	static PlayerYml P;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "·é ÀåÂø");
		int num = 0;
		
		for (int i = 0; i < 16; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 1;
			else if (i == 2) num = 2;
			else if (i == 3) num = 11;
			else if (i == 4) num = 20;
			else if (i == 5) num = 19;
			else if (i == 6) num = 18;
			else if (i == 7) num = 9;
			else if (i == 8) num = 6;
			else if (i == 9) num = 7;
			else if (i == 10) num = 8;
			else if (i == 11) num = 17;
			else if (i == 12) num = 26;
			else if (i == 13) num = 25;
			else if (i == 14) num = 24;
			else if (i == 15) num = 15;
				
			Stack2("", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		String[] water = "¢Ù,¡é,¢×,¡ç,¢Ø,¡è,¢Ö,¡æ".split(",");
		
		for (int i = 0; i < 8; i ++) {
			if (i == 0) num = 3;
			else if (i == 1) num = 4;
			else if (i == 2) num = 5;
			else if (i == 3) num = 14;
			else if (i == 4) num = 23;
			else if (i == 5) num = 22;
			else if (i == 6) num = 21;
			else if (i == 7) num = 12;
			
			Stack("¡×c¡×l" + water[i], 119, 0, 1, G.Eander, num, GUI);
		}
		
		Stack("¡×d·é ¡×eÀåÂø ¡×cTip 1.", 386, 0, 1, G.Tip1, 10, GUI);
		Stack("¡×d·é ¡×eÀåÂø ¡×cTip 2.", 386, 0, 1, G.Tip2, 16, GUI);
		
		if (P.getInfoInt(p, "·é.¾ÆÀÌÅÛ ÄÚµå") != 0 && P.getInfoString(p, "·é.¾ÆÀÌÅÛ ÀÌ¸§") != null && !P.getInfoList(p, "·é.¾ÆÀÌÅÛ ¼³¸í").equals(Arrays.asList())) {
			Stack(P.getInfoString(p, "·é.¾ÆÀÌÅÛ ÀÌ¸§"), P.getInfoInt(p, "·é.¾ÆÀÌÅÛ ÄÚµå"), 0, 1, P.getInfoList(p, "·é.¾ÆÀÌÅÛ ¼³¸í"), 13, GUI);
		}
		
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
