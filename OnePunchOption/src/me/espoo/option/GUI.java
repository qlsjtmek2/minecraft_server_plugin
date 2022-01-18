package me.espoo.option;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	static me.espoo.file.PlayerYml Ps;
	static PlayerYml P;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "�ɼ�");

		if (Ps.getInfoBoolean(p, "�� �ٷ� ���� ����")) {
			Stack("��f[ ��c�� �ٷ� ���� ��a��lON ��f]", 35, 5, 1, getLore("�� �ٷ� ����", "��aȰ��ȭ"), 10, GUI);
		} else {
			Stack("��f[ ��c�� �ٷ� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("�� �ٷ� ����", "��c��Ȱ��ȭ"), 10, GUI);
		}
		
		if (Ps.getInfoBoolean(p, "���� ���� ����")) {
			Stack("��f[ ��d���� ���� ��a��lON ��f]", 35, 5, 1, getLore("���� ����", "��aȰ��ȭ"), 12, GUI);
		} else {
			Stack("��f[ ��d���� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("���� ����", "��c��Ȱ��ȭ"), 12, GUI);
		}
		
		if (Ps.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
			Stack("��f[ ��b�ٸ� �ٷ� ���� ��a��lON ��f]", 35, 5, 1, getLore("�ٸ� �ٷ� ����", "��aȰ��ȭ"), 14, GUI);
		} else {
			Stack("��f[ ��b�ٸ� �ٷ� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("�ٸ� �ٷ� ����", "��c��Ȱ��ȭ"), 14, GUI);
		}
		
		if (Ps.getInfoBoolean(p, "���ǵ� ���� ����")) {
			Stack("��f[ ��a���ǵ� ���� ��a��lON ��f]", 35, 5, 1, getLore("���ǵ� ����", "��aȰ��ȭ"), 16, GUI);
		} else {
			Stack("��f[ ��a���ǵ� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("���ǵ� ����", "��c��Ȱ��ȭ"), 16, GUI);
		}
		
		if (P.getInfoBoolean(p, "������ ������")) {
			Stack("��f[ ��e������ ������ ��a��lON ��f]", 35, 5, 1, getLore("������ ������", "��aȰ��ȭ"), 20, GUI);
		} else {
			Stack("��f[ ��e������ ������ ��c��lOFF ��f]", 35, 14, 1, getLore("������ ������", "��c��Ȱ��ȭ"), 20, GUI);
		}
		
		if (P.getInfoBoolean(p, "ä�� ����")) {
			Stack("��f[ ��6ä�� ���� ��a��lON ��f]", 35, 5, 1, getLore("ä�� ����", "��aȰ��ȭ"), 22, GUI);
		} else {
			Stack("��f[ ��6ä�� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("ä�� ����", "��c��Ȱ��ȭ"), 22, GUI);
		}
		
		if (P.getInfoBoolean(p, "�÷��̾� �����")) {
			Stack("��f[ ��e�÷��̾� ����� ��a��lON ��f]", 35, 5, 1, getLore("�÷��̾� �����", "��aȰ��ȭ"), 24, GUI);
		} else {
			Stack("��f[ ��e�÷��̾� ����� ��c��lOFF ��f]", 35, 14, 1, getLore("�÷��̾� �����", "��c��Ȱ��ȭ"), 24, GUI);
		}
		
		if (P.getInfoBoolean(p, "�÷��̾� ���̵�")) {
			Stack("��f[ ��6�÷��̾� ���̵� ��a��lON ��f]", 35, 5, 1, getLore("�÷��̾� ���̵�", "��aȰ��ȭ"), 28, GUI);
		} else {
			Stack("��f[ ��6�÷��̾� ���̵� ��c��lOFF ��f]", 35, 14, 1, getLore("�÷��̾� ���̵�", "��c��Ȱ��ȭ"), 28, GUI);
		}
		
		if (P.getInfoBoolean(p, "���� ���� ����")) {
			Stack("��f[ ��e���� ���� ���� ��a��lON ��f]", 35, 5, 1, getLore("���� ���� ����", "��aȰ��ȭ"), 30, GUI);
		} else {
			Stack("��f[ ��e���� ���� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("���� ���� ����", "��c��Ȱ��ȭ"), 30, GUI);
		}
		
		if (P.getInfoBoolean(p, "ȿ����")) {
			Stack("��f[ ��6ȿ���� ��a��lON ��f]", 35, 5, 1, getLore("ȿ����", "��aȰ��ȭ"), 32, GUI);
		} else {
			Stack("��f[ ��6ȿ���� ��c��lOFF ��f]", 35, 14, 1, getLore("ȿ����", "��c��Ȱ��ȭ"), 32, GUI);
		}

		if (P.getInfoBoolean(p, "�ӼӸ� �ź�")) {
			Stack("��f[ ��e�ӼӸ� �ź� ��a��lON ��f]", 35, 5, 1, getLore("�ӼӸ� �ź�", "��aȰ��ȭ"), 34, GUI);
		} else {
			Stack("��f[ ��e�ӼӸ� �ź� ��c��lOFF ��f]", 35, 14, 1, getLore("�ӼӸ� �ź�", "��c��Ȱ��ȭ"), 34, GUI);
		}
		
		if (P.getInfoBoolean(p, "��Ƽ �ʴ�")) {
			Stack("��f[ ��e��Ƽ �ʴ� ��a��lON ��f]", 35, 5, 1, getLore("��Ƽ �ʴ�", "��aȰ��ȭ"), 38, GUI);
		} else {
			Stack("��f[ ��e��Ƽ �ʴ� ��c��lOFF ��f]", 35, 14, 1, getLore("��Ƽ �ʴ�", "��c��Ȱ��ȭ"), 38, GUI);
		}

		if (P.getInfoBoolean(p, "��� �ʴ�")) {
			Stack("��f[ ��6��� �ʴ� ��a��lON ��f]", 35, 5, 1, getLore("��� �ʴ�", "��aȰ��ȭ"), 40, GUI);
		} else {
			Stack("��f[ ��6��� �ʴ� ��c��lOFF ��f]", 35, 14, 1, getLore("��� �ʴ�", "��c��Ȱ��ȭ"), 40, GUI);
		}
		
		if (P.getInfoBoolean(p, "���� ���� ����")) {
			Stack("��f[ ��d���� ���� ���� ��a��lON ��f]", 35, 5, 1, getLore("���� ���� ����", "��aȰ��ȭ"), 42, GUI);
		} else {
			Stack("��f[ ��d���� ���� ���� ��c��lOFF ��f]", 35, 14, 1, getLore("���� ���� ����", "��c��Ȱ��ȭ"), 42, GUI);
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
	
	public static List<String> getLore(String str, String of) {
		List<String> lore = new ArrayList<String>();
		lore.add("��f���� ��e" + str + " ��f�ɼ��� " + of + " ��f�����Դϴ�.");
		lore.add("��fȰ��ȭ �Ǵ� ��Ȱ��ȭ�� ���ϽŴٸ�");
		lore.add("��f���� ������ �� �������� Ŭ�����ּ���.");
		return lore;
	}
	
	public static List<String> willAdd() {
		List<String> lore = new ArrayList<String>();
		lore.add("��7�߰� �غ����� �ɼ��Դϴ�.");
		return lore;
	}
}
