package me.espoo.book;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUImain {
	static GUIMessage G;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "����");

		Stack("��f[ ��c���� ��6���� ��f]", 276, 0, 1, G.ML, 11, GUI);
		Stack("��f[ ��f�Ϲ� ��b��� ��6���� ��f]", 307, 0, 1, G.ML, 12, GUI);
		Stack("��f[ ��6����ũ ��b��� ��6���� ��f]", 311, 0, 1, G.ML, 13, GUI);
		Stack("��f[ ��a���� ��6���� ��f]", 278, 0, 1, G.ML, 14, GUI);
		Stack("��f[ ��d�� ��6���� ��f]", 2257, 0, 1, G.ML, 15, GUI);
		
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
