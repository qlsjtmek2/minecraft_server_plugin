package me.espoo.dunzeon;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	public static void CheckPvPGUI(Player p, int num)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��Ƽ ���� ���� ��ȸ�� �������ڰ� �մϴ�.");
		Stack2("��f��, ���� ��ȸ�� �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, ���� ��ȸ�� �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
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
