package me.espoo.book;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUITool {
	static PlayerYml P;
	static GUIMessage G;
	static GUImain M;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "µµ±¸ µµ°¨");
		int num = 0;

		for (int i = 0; i < 11; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 36;
			else if (i == 5) num = 8;
			else if (i == 6) num = 17;
			else if (i == 7) num = 26;
			else if (i == 8) num = 35;
			else if (i == 9) num = 44;
			else if (i == 10) num = 53;
			
			M.Stack2("¡×fµµ°¨", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 26; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 10;
			else if (i == 8) num = 19;
			else if (i == 9) num = 28;
			else if (i == 10) num = 37;
			else if (i == 11) num = 46;
			else if (i == 12) num = 47;
			else if (i == 13) num = 48;
			else if (i == 14) num = 49;
			else if (i == 15) num = 50;
			else if (i == 16) num = 51;
			else if (i == 17) num = 52;
			else if (i == 18) num = 43;
			else if (i == 19) num = 16;
			else if (i == 20) num = 25;
			else if (i == 21) num = 34;
			else if (i == 22) num = 39;
			else if (i == 23) num = 40;
			else if (i == 24) num = 41;
			else if (i == 25) num = 42;
			
			M.Stack2("¡×fµµ°¨", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("¡×f[ ¡×7µÚ·Î°¡±â ¡×f]", 8, 0, 1, G.Water, 45, GUI);
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸»ó È¹µæ")) {
			M.Stack2("¡×fº¸»óÀ» ¹ÞÀ¸¼Ì½À´Ï´Ù.", 323, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("¡×4¡×l¡á  ¡×dÇÁ¸®¹Ì¾ö ¡×6°­È­ ÁÖ¹®¼­  ¡×4¡×l¡á", 339, 0, 50, G.ToolBook, 43, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë0")) {
			Stack("¡×eº¸Åë °î±ªÀÌ", 257, 0, 1, GUIMessage.getNoLore(0), 10, GUI, 1, 0);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 10, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë1")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+1", 257, 0, 1, GUIMessage.getNoLore(1), 11, GUI, 2, 0);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+1 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 11, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë2")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+2", 257, 0, 1, GUIMessage.getNoLore(2), 12, GUI, 2, 1);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+2 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 12, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë3")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+3", 257, 0, 1, GUIMessage.getNoLore(3), 13, GUI, 3, 1);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+3 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 13, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë4")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+4", 257, 0, 1, GUIMessage.getNoLore(4), 14, GUI, 3, 2);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+4 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 14, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë5")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+5", 257, 0, 1, GUIMessage.getNoLore(5), 15, GUI, 4, 2);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+5 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 15, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë6")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+6", 257, 0, 1, GUIMessage.getNoLore(6), 16, GUI, 4, 3);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+6 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 16, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë7")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+7", 257, 0, 1, GUIMessage.getNoLore(7), 19, GUI, 5, 3);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+7 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 19, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë8")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+8", 257, 0, 1, GUIMessage.getNoLore(8), 20, GUI, 5, 4);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+8 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 20, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë9")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+9", 257, 0, 1, GUIMessage.getNoLore(9), 21, GUI, 6, 4);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+9 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 21, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.º¸Åë10")) {
			Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+10", 257, 0, 1, GUIMessage.getNoLore(10), 22, GUI, 6, 5);
		} else {
			M.Stack("¡×eº¸Åë °î±ªÀÌ ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 22, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É0")) {
			Stack("¡×6Áø½É °î±ªÀÌ", 278, 0, 1, GUIMessage.getOneLore(0), 23, GUI, 7, 5);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 23, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É1")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+1", 278, 0, 1, GUIMessage.getOneLore(1), 24, GUI, 8, 5);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+1 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 24, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É2")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+2", 278, 0, 1, GUIMessage.getOneLore(2), 25, GUI, 9, 5);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+2 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 25, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É3")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+3", 278, 0, 1, GUIMessage.getOneLore(3), 28, GUI, 10, 5);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+3 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 28, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É4")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+4", 278, 0, 1, GUIMessage.getOneLore(4), 29, GUI, 11, 5);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+4 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 29, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É5")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+5", 278, 0, 1, GUIMessage.getOneLore(5), 30, GUI, 12, 5);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+5 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 30, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É6")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+6", 278, 0, 1, GUIMessage.getOneLore(6), 31, GUI, 12, 6);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+6 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 31, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É7")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+7", 278, 0, 1, GUIMessage.getOneLore(7), 32, GUI, 13, 6);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+7 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 32, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É8")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+8", 278, 0, 1, GUIMessage.getOneLore(8), 33, GUI, 14, 6);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+8 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 33, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É9")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+9", 278, 0, 1, GUIMessage.getOneLore(9), 34, GUI, 15, 6);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+9 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 34, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.Áø½É10")) {
			Stack("¡×6Áø½É °î±ªÀÌ ¡×f+10", 278, 0, 1, GUIMessage.getOneLore(10), 37, GUI, 15, 7);
		} else {
			M.Stack("¡×6Áø½É °î±ªÀÌ ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 37, GUI);
		}
		
		if (P.getInfoBoolean(p, "µµ±¸.¸¶½ºÅÍ¸®")) {
			Stack("¡×b¸¶½ºÅÍ¸® °î±ªÀÌ", 285, 0, 1, GUIMessage.getMaxLore(), 38, GUI, 16, 7);
		} else {
			M.Stack("¡×b¸¶½ºÅÍ¸® °î±ªÀÌ ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 38, GUI);
		}

		p.openInventory(GUI);
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv, int x, int y) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		if (x != 0) item_Meta.addEnchant(Enchantment.DIG_SPEED, x, true);
		if (y != 0) item_Meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, y, true);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
}
