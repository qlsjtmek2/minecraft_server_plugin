package me.espoo.pvp;

import java.util.ArrayList;
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

	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��� �ϱ�");
		
		Stack("��c1:1 ��6��� �ϱ�", 272, 0, 1, G.one, 10, GUI);
		Stack("��c2:2 ��6��� �ϱ�", 283, 0, 1, G.two, 11, GUI);
		Stack("��c3:3 ��6��� �ϱ�", 267, 0, 1, G.three, 12, GUI);
		Stack("��c4:4 ��6��� �ϱ�", 276, 0, 1, G.four, 13, GUI);
		Stack("��b�ݷμ��� ��6��� �ϱ�", 268, 0, 1, G.colosseum, 16, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void stopGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��Ī ���");
		Stack("��c��Ī ���", 372, 0, 1, G.stop, 13, GUI);
		p.openInventory(GUI);
	}
	
	public static void CheckPvPGUI(Player p, int num)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��Ƽ ���� " + num + ":" + num + " ����� �������ڰ� �մϴ�.");
		Stack2("��f��, ��ÿ� �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, ��ÿ� �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckContinueGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "����� ����Ǿ����ϴ�. �̾ �����Ͻðڽ��ϱ�?");
		List<String> lore = new ArrayList<String>();
		lore.add("��7�̾ ������ �� ���� �ݾ��� 500�� �� �þ�ϴ�.");
		lore.add("��7���� ���� �ݾ�: ��f" + main.coloBetting + "��");
		
		Stack("��f��, �ݷμ����� �̾ �ϰڽ��ϴ�.", 35, 5, 1, lore, 11, GUI);
		Stack2("��f�ƴϿ�, �ݷμ����� �̾ ���� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
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
