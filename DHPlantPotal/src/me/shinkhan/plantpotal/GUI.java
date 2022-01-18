package me.shinkhan.plantpotal;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.option.PlayerYml;

public class GUI {
	public static void RankingGUI(Player p, int k)
	{
		p.closeInventory();
		p.sendMessage("��6���� ��Ż ����� �ҷ����� ���Դϴ�..");
		
		List<String> list = API.getPotalList();
        if (list.size() < 1 || k * 45 - 44 > list.size() || k == 0) {//
			if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
            return;
        }
        
        Inventory GUI = null;
        if (list.size() % 45 == 0) {//
            GUI = Bukkit.createInventory(null, 54, "���� ��Ż " + k + "/" + list.size() / 45);
        } else {
            GUI = Bukkit.createInventory(null, 54, "���� ��Ż " + k + "/" + (list.size() / 45 + 1));
        }
		
        if (GUI == null) {
        	p.sendMessage("��cGUI â�� �ٽ� �������ּ���.");
        	return;
        }
        
        int num = 0;
        for (int j = (k - 1) * 45; j < k * 45; ++j) {//
        	String name = list.get(j);
			Stack3(API.getInfoItem(name), num, GUI);
            if (list.size() == j + 1) {
    			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
                break;
            }
            if (k * 45 - 1 == j && list.size() > j + 1) {//
    			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
            } num++;
        }
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("��6���� ��� Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			Stack2("", 119, 0, 1, Arrays.asList(), x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void OptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 45, "��Ż �ɼ�");

		Stack("��a�̸� ����", 323, 0, 1, GUIMessage.Name, 10, GUI);
		Stack("��b���� �߰�/����", 321, 0, 1, GUIMessage.Info, 12, GUI);
		Stack("��d������ �ڵ�", 2, 0, 1, GUIMessage.Code, 14, GUI);
		
		if (API.getPotalMove(p.getName())) Stack("��e�� �̵� ��Ȱ��ȭ", 35, 5, 1, GUIMessage.MoveOn, 16, GUI);
		else Stack("��e�� �̵� Ȱ��ȭ", 35, 14, 1, GUIMessage.MoveOff, 16, GUI);
			

		Stack("��4���� ��Ż ����", 51, 0, 1, GUIMessage.Delete, 29, GUI);
		Stack("��6�� ���� ��ġ ����", 90, 0, 1, GUIMessage.setwarp, 31, GUI);
		Stack("��f�ڷΰ���", 324, 0, 1, GUIMessage.BackRanking, 33, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void OpOptionGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, name + " ��Ż �ɼ�");

		Stack("��4���� ��Ż ����", 46, 0, 1, GUIMessage.OpDelete, 11, GUI);
		Stack("��f�ڷΰ���", 324, 0, 1, GUIMessage.BackRanking, 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void CheckRemoveGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "������ " + name + " ���� ������Ż�� �����Ͻðڽ��ϱ�?");
		
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void SetItemCodeGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "������ ������ �ڵ带 Ŭ���� �ֽñ� �ٶ��ϴ�.");
		
		Stack2(null, 35, 0, 1, Arrays.asList(), 10, GUI);
		Stack2(null, 35, 14, 1, Arrays.asList(), 11, GUI);
		Stack2(null, 35, 5, 1, Arrays.asList(), 12, GUI);
		Stack2(null, 35, 1, 1, Arrays.asList(), 13, GUI);
		Stack2(null, 35, 6, 1, Arrays.asList(), 14, GUI);
		Stack2(null, 35, 7, 1, Arrays.asList(), 15, GUI);
		Stack2(null, 35, 9, 1, Arrays.asList(), 16, GUI);
		Stack2(null, 29, 0, 1, Arrays.asList(), 19, GUI);
		Stack2(null, 42, 0, 1, Arrays.asList(), 20, GUI);
		Stack2(null, 41, 0, 1, Arrays.asList(), 21, GUI);
		Stack2(null, 57, 0, 1, Arrays.asList(), 22, GUI);
		Stack2(null, 79, 0, 1, Arrays.asList(), 23, GUI);
		Stack2(null, 116, 0, 1, Arrays.asList(), 24, GUI);
		Stack2(null, 120, 0, 1, Arrays.asList(), 25, GUI);
		Stack2(null, 264, 0, 1, Arrays.asList(), 28, GUI);
		Stack2(null, 263, 0, 1, Arrays.asList(), 29, GUI);
		Stack2(null, 265, 0, 1, Arrays.asList(), 30, GUI);
		Stack2(null, 266, 0, 1, Arrays.asList(), 31, GUI);
		Stack2(null, 388, 0, 1, Arrays.asList(), 32, GUI);
		Stack2(null, 397, 3, 1, Arrays.asList(), 33, GUI);
		Stack2(null, 339, 0, 1, Arrays.asList(), 34, GUI);
		Stack2(null, 323, 0, 1, Arrays.asList(), 37, GUI);
		Stack2(null, 368, 0, 1, Arrays.asList(), 38, GUI);
		Stack2(null, 381, 0, 1, Arrays.asList(), 39, GUI);
		Stack2(null, 378, 0, 1, Arrays.asList(), 40, GUI);
		Stack2(null, 276, 0, 1, Arrays.asList(), 41, GUI);
		Stack2(null, 278, 0, 1, Arrays.asList(), 42, GUI);
		Stack2(null, 293, 0, 1, Arrays.asList(), 43, GUI);
		Stack("��f�ڷ� ����", 324, 0, 1, GUIMessage.BackRanking, 45, GUI);
		
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
	
	public static void Stack3(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
}
