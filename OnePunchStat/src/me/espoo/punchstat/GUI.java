package me.espoo.punchstat;

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
		Inventory GUI = Bukkit.createInventory(null, 27, G.getMessage(p, "���� GUI �̸�"));
		
		if (G.getLoreList(p, "�� �ٷ�.������ ����").size() == 0)
			Stack2(G.getMessage(p, "�� �ٷ�.������ �̸�"), G.getItemCode("�� �ٷ�.������ �ڵ�"), G.getItemCode("�� �ٷ�.������ �������ڵ�"), 
				   G.getItemCode("�� �ٷ�.������ ����"), Arrays.asList(), 10, GUI);
		else
			Stack(G.getMessage(p, "�� �ٷ�.������ �̸�"), G.getItemCode("�� �ٷ�.������ �ڵ�"), G.getItemCode("�� �ٷ�.������ �������ڵ�"), 
				  G.getItemCode("�� �ٷ�.������ ����"), G.getLoreList(p, "�� �ٷ�.������ ����"), 10, GUI);
		
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 12, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 12, GUI);
		
		if (G.getLoreList(p, "�ٸ� �ٷ�.������ ����").size() == 0)
			Stack2(G.getMessage(p, "�ٸ� �ٷ�.������ �̸�"), G.getItemCode("�ٸ� �ٷ�.������ �ڵ�"), G.getItemCode("�ٸ� �ٷ�.������ �������ڵ�"), 
				   G.getItemCode("�ٸ� �ٷ�.������ ����"), Arrays.asList(), 14, GUI);
		else
			Stack(G.getMessage(p, "�ٸ� �ٷ�.������ �̸�"), G.getItemCode("�ٸ� �ٷ�.������ �ڵ�"), G.getItemCode("�ٸ� �ٷ�.������ �������ڵ�"), 
				  G.getItemCode("�ٸ� �ٷ�.������ ����"), G.getLoreList(p, "�ٸ� �ٷ�.������ ����"), 14, GUI);
		
		if (G.getLoreList(p, "���ǵ�.������ ����").size() == 0)
			Stack2(G.getMessage(p, "���ǵ�.������ �̸�"), G.getItemCode("���ǵ�.������ �ڵ�"), G.getItemCode("���ǵ�.������ �������ڵ�"), 
				   G.getItemCode("���ǵ�.������ ����"), Arrays.asList(), 16, GUI);
		else
			Stack(G.getMessage(p, "���ǵ�.������ �̸�"), G.getItemCode("���ǵ�.������ �ڵ�"), G.getItemCode("���ǵ�.������ �������ڵ�"), 
				  G.getItemCode("���ǵ�.������ ����"), G.getLoreList(p, "���ǵ�.������ ����"), 16, GUI);
		
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 26, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 26, GUI);
		
		if (G.getLoreList(p, "����2.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����2.������ �̸�"), G.getItemCode("����2.������ �ڵ�"), G.getItemCode("����2.������ �������ڵ�"), 
				   G.getItemCode("����2.������ ����"), Arrays.asList(), 18, GUI);
		else
			Stack(G.getMessage(p, "����2.������ �̸�"), G.getItemCode("����2.������ �ڵ�"), G.getItemCode("����2.������ �������ڵ�"), 
				  G.getItemCode("����2.������ ����"), G.getLoreList(p, "����2.������ ����"), 18, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void viewGUI(Player p, Player me)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, G.getMessage(p, "���� GUI �̸�"));
		
		if (G.getLoreList(p, "�� �ٷ�.������ ����").size() == 0)
			Stack2(G.getMessage(p, "�� �ٷ�.������ �̸�"), G.getItemCode("�� �ٷ�.������ �ڵ�"), G.getItemCode("�� �ٷ�.������ �������ڵ�"), 
				   G.getItemCode("�� �ٷ�.������ ����"), Arrays.asList(), 10, GUI);
		else
			Stack(G.getMessage(p, "�� �ٷ�.������ �̸�"), G.getItemCode("�� �ٷ�.������ �ڵ�"), G.getItemCode("�� �ٷ�.������ �������ڵ�"), 
				  G.getItemCode("�� �ٷ�.������ ����"), G.getLoreList(p, "�� �ٷ�.������ ����"), 10, GUI);
		
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 12, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 12, GUI);
		
		if (G.getLoreList(p, "�ٸ� �ٷ�.������ ����").size() == 0)
			Stack2(G.getMessage(p, "�ٸ� �ٷ�.������ �̸�"), G.getItemCode("�ٸ� �ٷ�.������ �ڵ�"), G.getItemCode("�ٸ� �ٷ�.������ �������ڵ�"), 
				   G.getItemCode("�ٸ� �ٷ�.������ ����"), Arrays.asList(), 14, GUI);
		else
			Stack(G.getMessage(p, "�ٸ� �ٷ�.������ �̸�"), G.getItemCode("�ٸ� �ٷ�.������ �ڵ�"), G.getItemCode("�ٸ� �ٷ�.������ �������ڵ�"), 
				  G.getItemCode("�ٸ� �ٷ�.������ ����"), G.getLoreList(p, "�ٸ� �ٷ�.������ ����"), 14, GUI);
		
		if (G.getLoreList(p, "���ǵ�.������ ����").size() == 0)
			Stack2(G.getMessage(p, "���ǵ�.������ �̸�"), G.getItemCode("���ǵ�.������ �ڵ�"), G.getItemCode("���ǵ�.������ �������ڵ�"), 
				   G.getItemCode("���ǵ�.������ ����"), Arrays.asList(), 16, GUI);
		else
			Stack(G.getMessage(p, "���ǵ�.������ �̸�"), G.getItemCode("���ǵ�.������ �ڵ�"), G.getItemCode("���ǵ�.������ �������ڵ�"), 
				  G.getItemCode("���ǵ�.������ ����"), G.getLoreList(p, "���ǵ�.������ ����"), 16, GUI);
		
		if (G.getLoreList(p, "����.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				   G.getItemCode("����.������ ����"), Arrays.asList(), 26, GUI);
		else
			Stack(G.getMessage(p, "����.������ �̸�"), G.getItemCode("����.������ �ڵ�"), G.getItemCode("����.������ �������ڵ�"), 
				  G.getItemCode("����.������ ����"), G.getLoreList(p, "����.������ ����"), 26, GUI);
		
		if (G.getLoreList(p, "����2.������ ����").size() == 0)
			Stack2(G.getMessage(p, "����2.������ �̸�"), G.getItemCode("����2.������ �ڵ�"), G.getItemCode("����2.������ �������ڵ�"), 
				   G.getItemCode("����2.������ ����"), Arrays.asList(), 18, GUI);
		else
			Stack(G.getMessage(p, "����2.������ �̸�"), G.getItemCode("����2.������ �ڵ�"), G.getItemCode("����2.������ �������ڵ�"), 
				  G.getItemCode("����2.������ ����"), G.getLoreList(p, "����2.������ ����"), 18, GUI);
		
		me.openInventory(GUI);
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
