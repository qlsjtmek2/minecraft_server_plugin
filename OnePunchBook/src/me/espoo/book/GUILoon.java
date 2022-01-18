package me.espoo.book;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUILoon {
	static PlayerYml P;
	static GUIMessage G;
	static GUImain M;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "·é µµ°¨");
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
			else if (i == 22) num = 13;
			else if (i == 23) num = 22;
			else if (i == 24) num = 31;
			else if (i == 25) num = 40;
			
			M.Stack2("¡×fµµ°¨", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("¡×f[ ¡×7µÚ·Î°¡±â ¡×f]", 8, 0, 1, G.Water, 45, GUI);
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.º¸»ó È¹µæ")) {
			M.Stack2("¡×fº¸»óÀ» ¹ÞÀ¸¼Ì½À´Ï´Ù.", 323, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("¡×6 È÷µç ¼Ó¼º ·é", 2263, 0, 1, G.LoonBook, 41, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.º¸»ó È¹µæ")) {
			M.Stack2("¡×fº¸»óÀ» ¹ÞÀ¸¼Ì½À´Ï´Ù.", 323, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("¡×6 È÷µç ¼Ó¼º ·é ¡×f+10", 2263, 0, 1, G.XLoonBook, 42, GUI);
		}
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.ºÒ¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×0¡×0¡×7¡×l¡×c ºÒ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("¡×c ºÒ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 11, GUI);
		}
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.¹Ù¶÷¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×5¡×8¡×7¡×l¡×b ¹Ù¶÷ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("¡×b ¹Ù¶÷ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 20, GUI);
		}
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.Ä¡À¯¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×2¡×1¡×7¡×l¡×d Ä¡À¯ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("¡×d Ä¡À¯ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.¾îµÒ¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×2¡×c¡×7¡×l¡×7¡×l ¾îµÒ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("¡×7¡×l ¾îµÒ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.µ¶¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×3¡×7¡×7¡×l¡×a µ¶ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("¡×a µ¶ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 14, GUI);
		}
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.ºÎÆÐ¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×4¡×2¡×7¡×l¡×e ºÎÆÐ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("¡×e ºÎÆÐ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 23, GUI);
		}
		
		if (P.getInfoBoolean(p, "ÀÏ¹Ý ·é.¾óÀ½¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×4¡×d¡×7¡×l¡×3 ¾óÀ½ ¼Ó¼º ¡×6·é", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("¡×3 ¾óÀ½ ¼Ó¼º ¡×6·é ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 32, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.ºÒ¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×0¡×a¡×7¡×l¡×c ºÒ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("¡×c ºÒ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 12, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.¹Ù¶÷¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×6¡×2¡×7¡×l¡×b ¹Ù¶÷ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("¡×b ¹Ù¶÷ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 21, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.Ä¡À¯¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×2¡×b¡×7¡×l¡×d Ä¡À¯ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("¡×d Ä¡À¯ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "Ç®°­ ·é.¾îµÒ¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×3¡×6¡×7¡×l¡×7¡×l ¾îµÒ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("¡×7¡×l ¾îµÒ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 39, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.µ¶¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×4¡×1¡×7¡×l¡×a µ¶ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("¡×a µ¶ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 15, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.ºÎÆÐ¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×4¡×c¡×7¡×l¡×e ºÎÆÐ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("¡×e ºÎÆÐ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 24, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ç®°­ ·é.¾óÀ½¼Ó¼º")) {
			M.Stack2("¡×0¡×0¡×0¡×0¡×0¡×7¡×5¡×7¡×7¡×l¡×3 ¾óÀ½ ¼Ó¼º ¡×6·é ¡×f+10", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("¡×3 ¾óÀ½ ¼Ó¼º ¡×6·é ¡×f+10 ¡×f¡×lµµ°¨", 389, 0, 1, G.Glass, 33, GUI);
		}
		
		p.openInventory(GUI);
	}
}
