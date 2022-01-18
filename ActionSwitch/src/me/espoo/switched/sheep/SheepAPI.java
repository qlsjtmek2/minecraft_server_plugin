package me.espoo.switched.sheep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.espoo.switched.GUIMessage;

public class SheepAPI {
	public static List<ItemStack> SheepAllList(int amount) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new MaterialData(35, (byte) 0).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 1).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 2).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 3).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 4).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 5).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 6).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 7).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 8).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 9).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 10).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 11).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 12).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 13).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 14).toItemStack(amount));
		list.add(new MaterialData(35, (byte) 15).toItemStack(amount));
		return list;
	}
	
	public static void setGUIGlass(Inventory GUI) {
		int num = 0;
		for (int i = 0; i < 15; i ++) {
			if (i == 0) num = 13;
			else if (i == 4) num = 22;
			else if (i == 8) num = 31;
			else if (i == 12) num = 40;
			else num++;
			
			SheepGUI.Stack("", 102, 0, 1, Arrays.asList(), num, GUI);
		}
	}
	
	public static boolean CheckGUIAir(Inventory GUI) {
		boolean is = false;
		int num = 0;
		for (int i = 0; i < 15; i ++) {
			if (i == 0) num = 13;
			else if (i == 4) num = 22;
			else if (i == 8) num = 31;
			else if (i == 12) num = 40;
			else num++;
			
			if (GUI.getItem(num) == null || GUI.getItem(num).getType() == Material.AIR) {
				is = true;
				break;
			}
		}
		
		return is;
	}
	
	public static int A(Inventory G, int N) {
		return G.getItem(N).getAmount();
	}
	
	public static int CheckGUIAmount(Inventory G) {
		if (A(G, 13) == A(G, 14) && A(G, 15) == A(G, 16) && A(G, 22) == A(G, 23) && A(G, 24) == A(G, 25) &&
		    A(G, 31) == A(G, 32) && A(G, 33) == A(G, 34) && A(G, 40) == A(G, 41) && A(G, 41) == A(G, 42)) {
			return -1;	
		} else {
			int num = 0;
			int amount = G.getItem(28).getAmount();
			for (int i = 0; i < 15; i ++) {
				if (i == 0) num = 13;
				else if (i == 4) num = 22;
				else if (i == 8) num = 31;
				else if (i == 12) num = 40;
				else num++;
				
				if (G.getItem(num).getAmount() != amount)
					return G.getItem(num).getAmount();
			} return -1;	
		}
	}
	
	public static void setFirstSheepGUI(Inventory GUI) {
		SheepGUI.Stack("¡×6¡×l¡é", 323, 0, 1, GUIMessage.SheepTitle, 19, GUI);
		setGUIGlass(GUI);
	}
	
	public static void setGUI(Inventory GUI, int amount) {
		ItemStack item = GUI.getItem(28);
		List<ItemStack> list = SheepAllList(amount);
		for (int i = 0; i < 16; i++) {
			if (list.get(i).isSimilar(item)) {
				list.remove(i);
				break;
			}
		}
		
		if (list.size() == 15) {
			int num = 0;
			for (int i = 0; i < 15; i ++) {
				if (i == 0) num = 13;
				else if (i == 4) num = 22;
				else if (i == 8) num = 31;
				else if (i == 12) num = 40;
				else num++;
				
				SheepGUI.Stack3(list.get(i), num, GUI);
			}
			
			item.setAmount(amount);
			SheepGUI.Stack3(item, 28, GUI);
		}
	}
	
	public static void setSheepGUI(Inventory GUI) {
		if (GUI.getItem(28) != null && GUI.getItem(28).getType() != Material.AIR) {
			if (CheckGUIAir(GUI)) {
				SheepGUI.Stack3(null, 28, GUI);
				setGUIGlass(GUI);
				return;
			}
			
			int num = CheckGUIAmount(GUI);
			if (GUI.getItem(13).getType() != Material.THIN_GLASS && num != -1) {
				setGUI(GUI, num);
				return;
			}

			ItemStack item = GUI.getItem(28);
			if (item.getType() == Material.WOOL) {
				setGUI(GUI, item.getAmount());
				return;
			}
		} else {
			if (!CheckGUIAir(GUI)) {
				setGUIGlass(GUI);
				return;
			}
		}
	}
}
