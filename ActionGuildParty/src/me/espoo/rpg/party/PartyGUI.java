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

import me.espoo.option.PlayerYml;
import me.espoo.rpg.GUIMessage;
import me.espoo.rpg.main;
import to.oa.tpsw.rpgexpsystem.api.Rpg;

public class PartyGUI {
	public static void InviteGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��Ƽ�� �ʴ�����̽��ϴ�. �����Ͻðڽ��ϱ�?");
		Party party = new Party(name);
		
		Stack("��f" + name + " ���� ��Ƽ", 341, 0, 1, party.getLore(), 13, GUI);
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void AskJoinParty(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "�÷��̾��� ��Ƽ ������ �����Ͻðڽ��ϱ�?");
		
		String player = main.PartyJoin.get(p).getName();
		List<String> lore = new ArrayList<String>();
		lore.add("��7����: ��f" + Rpg.getRpgPlayera(player).getRpgLevel());
				
		Stack("��f" + player, 341, 0, 1, lore, 13, GUI);
		Stack2("��f��, �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��f�ƴϿ�, �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void OptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��Ƽ �ɼ�");
		
		Stack("��3��Ƽ ��������", 399, 0, 1, GUIMessage.getJoinTypeParty(p), 11, GUI);
		Stack("��a��Ƽ ����ġ �й� ����", 384, 0, 1, GUIMessage.getPartyShare(p), 13, GUI);
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
		if (Party.players.containsKey(p.getName())) {
			Party party = Party.players.get(p.getName());
			Inventory GUI = Bukkit.createInventory(null, 54, "��2��lP ��0��l" + party.getName() + " ���� ��Ƽ");
			int num = 0;
			
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��b��Ƽ ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 38, GUI);
			Stack("��c��Ƽ Ż��", 372, 0, 1, GUIMessage.PartyLeave, 42, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("��f��Ƽ�� ��� ��6��l��", 323, 0, 1, party.getLore(), 11, GUI);
			
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
			
			Stack("��f" + party.getName(), 397, 3, 1, GUIMessage.PartyM, 12, GUI);
			
			int y = 13;
			for (String str : party.getUsers()) {
				if (!str.equalsIgnoreCase(party.getName())) {
					Stack("��f" + str, 397, 3, 1, GUIMessage.PartyU, y, GUI); y++;	
				}
			}
			
			p.openInventory(GUI);
		}
	}
	
	public static void MasterPartyGUI(Player p)
	{
		if (Party.players.containsKey(p.getName())) {
			Party party = Party.players.get(p.getName());
			Inventory GUI = Bukkit.createInventory(null, 54, "��2��lP ��0��l" + party.getName() + " ���� ��Ƽ");
			
			Stack("��f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("��b��Ƽ ä��", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 38, GUI);
			Stack("��4��Ƽ ����", 331, 0, 1, GUIMessage.PartyDelete, 53, GUI);
			Stack("��a�÷��̾� �ʴ�", 138, 0, 1, GUIMessage.PartyInvite, 40, GUI);
			Stack("��f��Ƽ �ɼ�", 368, 0, 1, GUIMessage.PartyOption, 42, GUI);
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("��f��Ƽ�� ��� ��6��l��", 323, 0, 1, party.getLore(), 11, GUI);
			
			int num = 0;
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
			
			Stack("��f" + party.getName(), 397, 3, 1, GUIMessage.PartyM, 12, GUI);
			
			int y = 13;
			for (String str : party.getUsers()) {
				if (!str.equalsIgnoreCase(party.getName())) {
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
	
	public static void ListGUI(Player p, int k)
	{
		p.closeInventory();
		p.sendMessage("��6��Ƽ ����� �ҷ����� ���Դϴ�..");
		
		List<Party> list = Party.partylist;
        if (list.size() < 1 || k * 45 - 44 > list.size() || k == 0) {//
			if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			p.closeInventory();
            return;
        }
		
        Inventory GUI = null;
        if (list.size() % 45 == 0) {//
            GUI = Bukkit.createInventory(null, 54, "��Ƽ ��� " + k + "/" + list.size() / 45);
        } else {
            GUI = Bukkit.createInventory(null, 54, "��Ƽ ��� " + k + "/" + (list.size() / 45 + 1));
        }
		
        if (GUI == null) {
        	p.sendMessage("��cGUI â�� �ٽ� �������ּ���.");
        	return;
        }
        
        int num = 0;
        for (int j = (k - 1) * 45; j < k * 45; ++j) {//
			List<String> lore = new ArrayList<String>();
			Party party = list.get(j);
			if (party.getLore() != null)
				for (String str : party.getLore()) lore.add(str);
			
			if (party.getJoinType() != null) {
				if (party.getJoinType().equalsIgnoreCase("����")) {
					lore.add("��f");
					lore.add("��8��l<HELP> ��7������ Ŭ����");
					lore.add("��7�ش� ��Ƽ�� �����մϴ�.");
				}
				
				else if (party.getJoinType().equalsIgnoreCase("��û")) {
					lore.add("��f");
					lore.add("��8��l<HELP> ��7������ Ŭ����");
					lore.add("��7�ش� ��Ƽ�� ������ ��û�մϴ�.");
				}
			}
			
			Stack("��f" + party.getName() + " ���� ��Ƽ", 341, 0, 1, lore, num, GUI);
        	
        	if (list.size() == j + 1) {
    			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
                break;
            }
            if (k * 45 - 1 == j && list.size() > j + 1) {//
    			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
            } num++;
        }
		
		if (k == 1) {
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
