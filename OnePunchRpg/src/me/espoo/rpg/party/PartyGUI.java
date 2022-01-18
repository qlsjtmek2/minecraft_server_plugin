package me.espoo.rpg.party;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.rpg.GUIMessage;
import me.espoo.rpg.main;
import me.espoo.rpg.exp.ExpAPI;

public class PartyGUI {
	public static void InviteGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��Ƽ�� �ʴ�����̽��ϴ�. �����Ͻðڽ��ϱ�?");
		
		List<String> lore = new ArrayList<String>();
		for (String str : PartyAPI.getInfo(name)) lore.add(str);
		Stack("��f" + PartyAPI.getManager(name) + " ���� ��Ƽ", 341, 0, 1, lore, 13, GUI);
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void AskJoinParty(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "�÷��̾��� ��Ƽ ������ �����Ͻðڽ��ϱ�?");
		
		String player = main.PartyJoin.get(p).getName();
		List<String> lore = new ArrayList<String>();
		lore.add("��7����ġ: ��f" + ExpAPI.getExp(player));
		lore.add("��7����� ����: ��f" + me.espoo.score.Method.getPlayerScore(player));
				
		Stack("��f" + player, 341, 0, 1, lore, 13, GUI);
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void OptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��Ƽ �ɼ�");
		
		Stack("��3��Ƽ ��������", 399, 0, 1, GUIMessage.getJoinTypeParty(p), 11, GUI);
		Stack("��a��Ƽ ����ġ, ����� ���� �й� ����", 384, 0, 1, GUIMessage.getPartyShare(p), 13, GUI);
		Stack("��6��Ƽ ���� ��e��l����", 339, 0, 1, GUIMessage.PartyInfo, 15, GUI);
		Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void noPartyGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��Ƽ�� �����ñ���!");
		
		Stack("��6��Ƽ ����", 401, 0, 1, GUIMessage.PartyCreate, 12, GUI);
		Stack("��b��Ƽ ���", 402, 0, 1, GUIMessage.PartyList, 14, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void userPartyGUI(Player p)
	{
		if (PartyAPI.getJoinParty(p) != null) {
			String party = PartyAPI.getJoinParty(p); int num = 0;
			Inventory GUI = Bukkit.createInventory(null, 54, "��2��lP ��0��l" + party + " ���� ��Ƽ");
			
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��b��Ƽ ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 38, GUI);
			Stack("��c��Ƽ Ż��", 372, 0, 1, GUIMessage.PartyLeave, 42, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("��f��Ƽ�� ��� ��6��l��", 323, 0, 1, PartyAPI.getInfo(party), 11, GUI);
			
			for (int i = 0; i < 12; i ++) {
				if (i == 0) num = 0;
				else if (i == 1) num = 8;
				else if (i == 2) num = 9;
				else if (i == 3) num = 17;
				else if (i == 4) num = 18;
				else if (i == 5) num = 26;
				else if (i == 6) num = 27;
				else if (i == 7) num = 35;
				else if (i == 8) num = 36;
				else if (i == 9) num = 44;
				else if (i == 10) num = 45;
				else if (i == 11) num = 53;
				
				Stack2("", 66, 0, 1, Arrays.asList(), num, GUI);
			}
			
			for (int i = 0; i < 16; i ++) {
				if (i == 0) num = 1;
				else if (i == 1) num = 2;
				else if (i == 2) num = 3;
				else if (i == 3) num = 4;
				else if (i == 4) num = 5;
				else if (i == 5) num = 6;
				else if (i == 6) num = 7;
				else if (i == 7) num = 10;
				else if (i == 8) num = 16;
				else if (i == 9) num = 19;
				else if (i == 10) num = 20;
				else if (i == 11) num = 21;
				else if (i == 12) num = 22;
				else if (i == 13) num = 23;
				else if (i == 14) num = 24;
				else if (i == 15) num = 25;
				
				Stack2("", 119, 0, 1, Arrays.asList(), num, GUI);
			}
			
			Stack("��f" + PartyAPI.getManager(party), 397, 3, 1, GUIMessage.PartyM, 12, GUI);
			
			int y = 13;
			for (String str : PartyAPI.getUser(party))
				if (!str.equalsIgnoreCase(PartyAPI.getManager(party)))
					Stack("��f" + str, 397, 3, 1, GUIMessage.PartyU, y, GUI); y++;
			
			p.openInventory(GUI);
		}
	}
	
	public static void MasterPartyGUI(Player p)
	{
		if (PartyAPI.getJoinParty(p) != null) {
			String party = PartyAPI.getJoinParty(p); int num = 0;
			Inventory GUI = Bukkit.createInventory(null, 54, "��2��lP ��0��l" + party + " ���� ��Ƽ");
			
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��b��Ƽ ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 38, GUI);
			Stack("��4��Ƽ ����", 331, 0, 1, GUIMessage.PartyDelete, 53, GUI);
			Stack("��a�÷��̾� �ʴ�", 138, 0, 1, GUIMessage.PartyInvite, 40, GUI);
			Stack("��f��Ƽ �ɼ�", 368, 0, 1, GUIMessage.PartyOption, 42, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("��f��Ƽ�� ��� ��6��l��", 323, 0, 1, PartyAPI.getInfo(party), 11, GUI);
			
			for (int i = 0; i < 11; i ++) {
				if (i == 0) num = 0;
				else if (i == 1) num = 8;
				else if (i == 2) num = 9;
				else if (i == 3) num = 17;
				else if (i == 4) num = 18;
				else if (i == 5) num = 26;
				else if (i == 6) num = 27;
				else if (i == 7) num = 35;
				else if (i == 8) num = 36;
				else if (i == 9) num = 44;
				else if (i == 10) num = 45;
				
				Stack2("", 66, 0, 1, Arrays.asList(), num, GUI);
			}
			
			for (int i = 0; i < 16; i ++) {
				if (i == 0) num = 1;
				else if (i == 1) num = 2;
				else if (i == 2) num = 3;
				else if (i == 3) num = 4;
				else if (i == 4) num = 5;
				else if (i == 5) num = 6;
				else if (i == 6) num = 7;
				else if (i == 7) num = 10;
				else if (i == 8) num = 16;
				else if (i == 9) num = 19;
				else if (i == 10) num = 20;
				else if (i == 11) num = 21;
				else if (i == 12) num = 22;
				else if (i == 13) num = 23;
				else if (i == 14) num = 24;
				else if (i == 15) num = 25;
				
				Stack2("", 119, 0, 1, Arrays.asList(), num, GUI);
			}
			
			Stack("��f" + PartyAPI.getManager(party), 397, 3, 1, GUIMessage.PartyM, 12, GUI);
			
			int y = 13;
			for (String str : PartyAPI.getUser(party)) {
				if (!str.equalsIgnoreCase(PartyAPI.getManager(party))) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7���: ��f��Ƽ��");
					lore.add("");
					lore.add("��8��l<HELP> ��7");
					lore.add("��7��Ŭ�� - ��Ƽ���� ���� Ȯ��");
					lore.add("��7��Ŭ�� - ��Ƽ���� ���� ����");
					lore.add("��7����Ʈ Ŭ�� - ��Ƽ���� �÷��̾�� �絵");
					Stack("��f" + str, 397, 3, 1, lore, y, GUI); y++;
				}
			}
			
			p.openInventory(GUI);
		}
	}
	
	public static void ListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "��Ƽ ��� �ҷ�������..");
		p.openInventory(G);
		
		List<String> list = PartyYml.getPartyList();
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "��Ƽ ��� " + num + "/" + num2);
		
		for (String st : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 45) + 46) break;
			List<String> lore = new ArrayList<String>();
			if (PartyAPI.getInfo(st) != null)
				for (String str : PartyAPI.getInfo(st)) lore.add(str);
			if (PartyAPI.getJoinType(st).equalsIgnoreCase("����")) {
				lore.add("��f");
				lore.add("��8��l<HELP> ��7������ Ŭ����");
				lore.add("��7�ش� ��Ƽ�� �����մϴ�.");
			}
			
			else if (PartyAPI.getJoinType(st).equalsIgnoreCase("��û")) {
				lore.add("��f");
				lore.add("��8��l<HELP> ��7������ Ŭ����");
				lore.add("��7�ش� ��Ƽ�� ������ ��û�մϴ�.");
			}
			
			Stack("��f" + st + " ���� ��Ƽ", 341, 0, 1, lore, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
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
