package me.espoo.book;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Method {
	static PlayerYml P;
	
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "��1");
        message = message.replaceAll("&2", "��2");
        message = message.replaceAll("&3", "��3");
        message = message.replaceAll("&4", "��4");
        message = message.replaceAll("&5", "��5");
        message = message.replaceAll("&6", "��6");
        message = message.replaceAll("&7", "��7");
        message = message.replaceAll("&8", "��8");
        message = message.replaceAll("&9", "��9");
        message = message.replaceAll("&0", "��0");
        message = message.replaceAll("&a", "��a");
        message = message.replaceAll("&b", "��b");
        message = message.replaceAll("&c", "��c");
        message = message.replaceAll("&d", "��d");
        message = message.replaceAll("&e", "��e");
        message = message.replaceAll("&f", "��f");
        message = message.replaceAll("&k", "��k");
        message = message.replaceAll("&l", "��l");
        message = message.replaceAll("&m", "��m");
        message = message.replaceAll("&n", "��n");
        message = message.replaceAll("&o", "��o");
        message = message.replaceAll("&r", "��r");
        return message;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidWaepon(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "�Ϲ� ����." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6���⸦ ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUIWaepon.openGUI(p);
				return;
			}
		}
		
		GUIWaepon.openGUI(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidXWaepon(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "Ǯ�� ����." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6���⸦ ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUIXWaepon.openGUI(p);
				return;
			}
		}
		
		GUIXWaepon.openGUI(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidnGear1(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "�Ϲ� ���." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6��� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUInGear.openGUI1(p);
				return;
			}
		}
		
		GUInGear.openGUI1(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidnGear2(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "�Ϲ� ���." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6��� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUInGear.openGUI2(p);
				return;
			}
		}
		
		GUInGear.openGUI2(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidnGear3(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "�Ϲ� ���." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6��� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUInGear.openGUI3(p);
				return;
			}
		}
		
		GUInGear.openGUI3(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voiduGear1(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "����ũ ���." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6��� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUIuGear.openGUI1(p);
				return;
			}
		}
		
		GUIuGear.openGUI1(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voiduGear2(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "����ũ ���." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6��� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUIuGear.openGUI2(p);
				return;
			}
		}
		
		GUIuGear.openGUI2(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voiduGear3(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "����ũ ���." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6��� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUIuGear.openGUI3(p);
				return;
			}
		}
		
		GUIuGear.openGUI3(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidLoon(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "�Ϲ� ��." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6���� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUILoon.openGUI(p);
				return;
			}
		}
		
		GUILoon.openGUI(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidXLoon(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "Ǯ�� ��." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6���� ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUILoon.openGUI(p);
				return;
			}
		}
		
		GUILoon.openGUI(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidTool(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "����." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("��6���������� ��c" + str + " ��6������ ������ ����ϼ̽��ϴ�.");
		ItemStack[] contents;
		for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			ItemStack it = contents[j];
			if (it == null) {
				e.setCursor(null);
				p.getInventory().setItem(j, c);
				GUITool.openGUI(p);
				return;
			}
		}
		
		GUITool.openGUI(p);
		e.setCancelled(true);
	}
	
	@SuppressWarnings("static-access")
	public static boolean isLoon(Player p) {
		if (P.getInfoBoolean(p, "�Ϲ� ��.�ҼӼ�") && P.getInfoBoolean(p, "�Ϲ� ��.�ٶ��Ӽ�") && P.getInfoBoolean(p, "�Ϲ� ��.ġ���Ӽ�") && 
			P.getInfoBoolean(p, "�Ϲ� ��.��ҼӼ�") && P.getInfoBoolean(p, "�Ϲ� ��.���Ӽ�") && P.getInfoBoolean(p, "�Ϲ� ��.���мӼ�") && 
			P.getInfoBoolean(p, "�Ϲ� ��.�����Ӽ�")) {
			if (P.getInfoBoolean(p, "�Ϲ� ��.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isXLoon(Player p) {
		if (P.getInfoBoolean(p, "Ǯ�� ��.�ҼӼ�") && P.getInfoBoolean(p, "Ǯ�� ��.�ٶ��Ӽ�") && P.getInfoBoolean(p, "Ǯ�� ��.ġ���Ӽ�") && 
			P.getInfoBoolean(p, "Ǯ�� ��.��ҼӼ�") && P.getInfoBoolean(p, "Ǯ�� ��.���Ӽ�") && P.getInfoBoolean(p, "Ǯ�� ��.���мӼ�") && 
			P.getInfoBoolean(p, "Ǯ�� ��.�����Ӽ�")) {
			if (P.getInfoBoolean(p, "Ǯ�� ��.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isWaepon(Player p) {
		if (P.getInfoBoolean(p, "�Ϲ� ����.�������") && P.getInfoBoolean(p, "�Ϲ� ����.�ݼӹ�Ʈ") && P.getInfoBoolean(p, "�Ϲ� ����.����") && 
			P.getInfoBoolean(p, "�Ϲ� ����.����") && P.getInfoBoolean(p, "�Ϲ� ����.��Ż����Ʈ") && P.getInfoBoolean(p, "�Ϲ� ����.���߸�") && 
			P.getInfoBoolean(p, "�Ϲ� ����.�������÷���") && P.getInfoBoolean(p, "�Ϲ� ����.�ǹ���") && P.getInfoBoolean(p, "�Ϲ� ����.�Ƹ��̸���ũ") && 
			P.getInfoBoolean(p, "�Ϲ� ����.�����") && P.getInfoBoolean(p, "�Ϲ� ����.�����ǼҴ�") && P.getInfoBoolean(p, "�Ϲ� ����.���뽺") && 
			P.getInfoBoolean(p, "�Ϲ� ����.�����") && P.getInfoBoolean(p, "�Ϲ� ����.���ձݰ�����") && P.getInfoBoolean(p, "�Ϲ� ����.ŷ") && 
			P.getInfoBoolean(p, "�Ϲ� ����.Ÿ����Ű") && P.getInfoBoolean(p, "�Ϲ� ����.��ũ�鸶����") && P.getInfoBoolean(p, "�Ϲ� ����.�������") && 
			P.getInfoBoolean(p, "�Ϲ� ����.�ĺ�Ű")) {
			if (P.getInfoBoolean(p, "�Ϲ� ����.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isXWaepon(Player p) {
		if (P.getInfoBoolean(p, "Ǯ�� ����.�������") && P.getInfoBoolean(p, "Ǯ�� ����.�ݼӹ�Ʈ") && P.getInfoBoolean(p, "Ǯ�� ����.����") && 
			P.getInfoBoolean(p, "Ǯ�� ����.����") && P.getInfoBoolean(p, "Ǯ�� ����.��Ż����Ʈ") && P.getInfoBoolean(p, "Ǯ�� ����.���߸�") && 
			P.getInfoBoolean(p, "Ǯ�� ����.�������÷���") && P.getInfoBoolean(p, "Ǯ�� ����.�ǹ���") && P.getInfoBoolean(p, "Ǯ�� ����.�Ƹ��̸���ũ") && 
			P.getInfoBoolean(p, "Ǯ�� ����.�����") && P.getInfoBoolean(p, "Ǯ�� ����.�����ǼҴ�") && P.getInfoBoolean(p, "Ǯ�� ����.���뽺") && 
			P.getInfoBoolean(p, "Ǯ�� ����.�����") && P.getInfoBoolean(p, "Ǯ�� ����.���ձݰ�����") && P.getInfoBoolean(p, "Ǯ�� ����.ŷ") && 
			P.getInfoBoolean(p, "Ǯ�� ����.Ÿ����Ű") && P.getInfoBoolean(p, "Ǯ�� ����.��ũ�鸶����") && P.getInfoBoolean(p, "Ǯ�� ����.�������") && 
			P.getInfoBoolean(p, "Ǯ�� ����.�ĺ�Ű")) {
			if (P.getInfoBoolean(p, "Ǯ�� ����.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isnGear(Player p) {
		if (P.getInfoBoolean(p, "�Ϲ� ���.����������") && P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�Ʈ����") && P.getInfoBoolean(p, "�Ϲ� ���.���Ÿ���") && 
				P.getInfoBoolean(p, "�Ϲ� ���.��������") && P.getInfoBoolean(p, "�Ϲ� ���.��Ż����Ʈ����") && P.getInfoBoolean(p, "�Ϲ� ���.���߸Ǹ���") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�������÷��ø���") && P.getInfoBoolean(p, "�Ϲ� ���.�ǹ��ظ���") && P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũ����") && 
				P.getInfoBoolean(p, "�Ϲ� ���.����͸���") && P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴи���") && P.getInfoBoolean(p, "�Ϲ� ���.���뽺����") && 
				P.getInfoBoolean(p, "�Ϲ� ���.����Ǹ���") && P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ���������") && P.getInfoBoolean(p, "�Ϲ� ���.ŷ����") && 
				P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����Ű����") && P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶���͸���") && P.getInfoBoolean(p, "�Ϲ� ���.������ʸ���") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�Ű����") && P.getInfoBoolean(p, "�Ϲ� ���.�������Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�ƮƩ��") && 
				P.getInfoBoolean(p, "�Ϲ� ���.����Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.����Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.��Ż����ƮƩ��") && 
				P.getInfoBoolean(p, "�Ϲ� ���.���߸�Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.�������÷���Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.�ǹ���Ʃ��") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũƩ��") && P.getInfoBoolean(p, "�Ϲ� ���.�����Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴ�Ʃ��") && 
				P.getInfoBoolean(p, "�Ϲ� ���.���뽺Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.�����Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ�����Ʃ��") && 
				P.getInfoBoolean(p, "�Ϲ� ���.ŷƩ��") && P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����ŰƩ��") && P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶����Ʃ��") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�������Ʃ��") && P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�ŰƩ��") && P.getInfoBoolean(p, "�Ϲ� ���.����������") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�Ʈ����") && P.getInfoBoolean(p, "�Ϲ� ���.���Ź���") && P.getInfoBoolean(p, "�Ϲ� ���.��������") && 
				P.getInfoBoolean(p, "�Ϲ� ���.��Ż����Ʈ����") && P.getInfoBoolean(p, "�Ϲ� ���.���߸ǹ���") && P.getInfoBoolean(p, "�Ϲ� ���.�������÷��ù���") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�ǹ��ع���") && P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũ����") && P.getInfoBoolean(p, "�Ϲ� ���.����͹���") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴй���") && P.getInfoBoolean(p, "�Ϲ� ���.���뽺����") && P.getInfoBoolean(p, "�Ϲ� ���.����ǹ���") && 
				P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ���������") && P.getInfoBoolean(p, "�Ϲ� ���.ŷ����") && P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����Ű����") && 
				P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶���͹���") && P.getInfoBoolean(p, "�Ϲ� ���.������ʹ���") && P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�Ű����") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�������Ź�") && P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�Ʈ�Ź�") && P.getInfoBoolean(p, "�Ϲ� ���.���ŽŹ�") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�����Ź�") && P.getInfoBoolean(p, "�Ϲ� ���.��Ż����Ʈ�Ź�") && P.getInfoBoolean(p, "�Ϲ� ���.���߸ǽŹ�") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�������÷��ýŹ�") && P.getInfoBoolean(p, "�Ϲ� ���.�ǹ��ؽŹ�") && P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũ�Ź�") && 
				P.getInfoBoolean(p, "�Ϲ� ���.����ͽŹ�") && P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴнŹ�") && P.getInfoBoolean(p, "�Ϲ� ���.���뽺�Ź�") && 
				P.getInfoBoolean(p, "�Ϲ� ���.����ǽŹ�") && P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ������Ź�") && P.getInfoBoolean(p, "�Ϲ� ���.ŷ�Ź�") && 
				P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����Ű�Ź�") && P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶���ͽŹ�") && P.getInfoBoolean(p, "�Ϲ� ���.������ʽŹ�") && 
				P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�Ű�Ź�")) {
			if (P.getInfoBoolean(p, "�Ϲ� ���.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isuGear(Player p) {
		if (P.getInfoBoolean(p, "����ũ ���.����������") && P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ����") && P.getInfoBoolean(p, "����ũ ���.���Ÿ���") && 
				P.getInfoBoolean(p, "����ũ ���.��������") && P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ����") && P.getInfoBoolean(p, "����ũ ���.���߸Ǹ���") && 
				P.getInfoBoolean(p, "����ũ ���.�������÷��ø���") && P.getInfoBoolean(p, "����ũ ���.�ǹ��ظ���") && P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ����") && 
				P.getInfoBoolean(p, "����ũ ���.����͸���") && P.getInfoBoolean(p, "����ũ ���.�����ǼҴи���") && P.getInfoBoolean(p, "����ũ ���.���뽺����") && 
				P.getInfoBoolean(p, "����ũ ���.����Ǹ���") && P.getInfoBoolean(p, "����ũ ���.���ձݰ���������") && P.getInfoBoolean(p, "����ũ ���.ŷ����") && 
				P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű����") && P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���͸���") && P.getInfoBoolean(p, "����ũ ���.������ʸ���") && 
				P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű����") && P.getInfoBoolean(p, "����ũ ���.�������Ʃ��") && P.getInfoBoolean(p, "����ũ ���.�ݼӹ�ƮƩ��") && 
				P.getInfoBoolean(p, "����ũ ���.����Ʃ��") && P.getInfoBoolean(p, "����ũ ���.����Ʃ��") && P.getInfoBoolean(p, "����ũ ���.��Ż����ƮƩ��") && 
				P.getInfoBoolean(p, "����ũ ���.���߸�Ʃ��") && P.getInfoBoolean(p, "����ũ ���.�������÷���Ʃ��") && P.getInfoBoolean(p, "����ũ ���.�ǹ���Ʃ��") && 
				P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũƩ��") && P.getInfoBoolean(p, "����ũ ���.�����Ʃ��") && P.getInfoBoolean(p, "����ũ ���.�����ǼҴ�Ʃ��") && 
				P.getInfoBoolean(p, "����ũ ���.���뽺Ʃ��") && P.getInfoBoolean(p, "����ũ ���.�����Ʃ��") && P.getInfoBoolean(p, "����ũ ���.���ձݰ�����Ʃ��") && 
				P.getInfoBoolean(p, "����ũ ���.ŷƩ��") && P.getInfoBoolean(p, "����ũ ���.Ÿ����ŰƩ��") && P.getInfoBoolean(p, "����ũ ���.��ũ�鸶����Ʃ��") && 
				P.getInfoBoolean(p, "����ũ ���.�������Ʃ��") && P.getInfoBoolean(p, "����ũ ���.�ĺ�ŰƩ��") && P.getInfoBoolean(p, "����ũ ���.����������") && 
				P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ����") && P.getInfoBoolean(p, "����ũ ���.���Ź���") && P.getInfoBoolean(p, "����ũ ���.��������") && 
				P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ����") && P.getInfoBoolean(p, "����ũ ���.���߸ǹ���") && P.getInfoBoolean(p, "����ũ ���.�������÷��ù���") && 
				P.getInfoBoolean(p, "����ũ ���.�ǹ��ع���") && P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ����") && P.getInfoBoolean(p, "����ũ ���.����͹���") && 
				P.getInfoBoolean(p, "����ũ ���.�����ǼҴй���") && P.getInfoBoolean(p, "����ũ ���.���뽺����") && P.getInfoBoolean(p, "����ũ ���.����ǹ���") && 
				P.getInfoBoolean(p, "����ũ ���.���ձݰ���������") && P.getInfoBoolean(p, "����ũ ���.ŷ����") && P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű����") && 
				P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���͹���") && P.getInfoBoolean(p, "����ũ ���.������ʹ���") && P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű����") && 
				P.getInfoBoolean(p, "����ũ ���.�������Ź�") && P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ�Ź�") && P.getInfoBoolean(p, "����ũ ���.���ŽŹ�") && 
				P.getInfoBoolean(p, "����ũ ���.�����Ź�") && P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ�Ź�") && P.getInfoBoolean(p, "����ũ ���.���߸ǽŹ�") && 
				P.getInfoBoolean(p, "����ũ ���.�������÷��ýŹ�") && P.getInfoBoolean(p, "����ũ ���.�ǹ��ؽŹ�") && P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ�Ź�") && 
				P.getInfoBoolean(p, "����ũ ���.����ͽŹ�") && P.getInfoBoolean(p, "����ũ ���.�����ǼҴнŹ�") && P.getInfoBoolean(p, "����ũ ���.���뽺�Ź�") && 
				P.getInfoBoolean(p, "����ũ ���.����ǽŹ�") && P.getInfoBoolean(p, "����ũ ���.���ձݰ������Ź�") && P.getInfoBoolean(p, "����ũ ���.ŷ�Ź�") && 
				P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű�Ź�") && P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���ͽŹ�") && P.getInfoBoolean(p, "����ũ ���.������ʽŹ�") && 
				P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű�Ź�")) {
			if (P.getInfoBoolean(p, "����ũ ���.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isTool(Player p) {
		if (P.getInfoBoolean(p, "����.����0") && P.getInfoBoolean(p, "����.����1") && P.getInfoBoolean(p, "����.����2") && P.getInfoBoolean(p, "����.����3") && 
			P.getInfoBoolean(p, "����.����4") && P.getInfoBoolean(p, "����.����5") && P.getInfoBoolean(p, "����.����6") && P.getInfoBoolean(p, "����.����7") && 
			P.getInfoBoolean(p, "����.����8") && P.getInfoBoolean(p, "����.����9") && P.getInfoBoolean(p, "����.����10") && P.getInfoBoolean(p, "����.����0") && 
			P.getInfoBoolean(p, "����.����1") && P.getInfoBoolean(p, "����.����2") && P.getInfoBoolean(p, "����.����3") && P.getInfoBoolean(p, "����.����4") && 
			P.getInfoBoolean(p, "����.����5") && P.getInfoBoolean(p, "����.����6") && P.getInfoBoolean(p, "����.����7") && P.getInfoBoolean(p, "����.����8") && 
			P.getInfoBoolean(p, "����.����9") && P.getInfoBoolean(p, "����.����10") && P.getInfoBoolean(p, "����.�����͸�")) {
			if (P.getInfoBoolean(p, "����.���� ȹ��") == false) {
				return true;
			}
		}
		
		return false;
	}
}
