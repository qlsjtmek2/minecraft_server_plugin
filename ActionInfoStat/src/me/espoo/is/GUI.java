package me.espoo.is;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;

public class GUI {
	static me.espoo.socket.PlayerYml P;
	static me.espoo.loon.PlayerYml PL;
	
	public static void CheckPvPGUI(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, name + " ���� ����� ��û�߽��ϴ�.");
		Stack2("��f����� �����ϰڽ��ϴ�.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("��f����� �������� �ʰڽ��ϴ�.", 35, 14, 1, Arrays.asList(), 15, GUI);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		p.openInventory(GUI);
	}
	
	public static void StatGUI(Player p, Player name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "����: " + name.getName());
		
		Stack("��e���� ȿ�� ���", 373, 0, 1, Method.getPotion(name), 13, GUI);
		Stack("��f�����", 320, 0, 1, Method.getHunger(name), 15, GUI);
		
		Stack("��6������", 403, 0, 1, Method.getPlayerAllStat(name), 11, GUI);
		
		int num = 0;
		for (int i = 0; i < 6; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 8;
			else if (i == 4) num = 17;
			else if (i == 5) num = 26;
				
			Stack2("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void InfoGUI(Player p, Player name)
	{
		Inventory GUI = null;
		if (!p.equals(name)) {
			GUI = Bukkit.createInventory(null, 54, "����: " + name.getName());
		} else {
			GUI = Bukkit.createInventory(null, 45, "����: " + name.getName());
		}
		
		ItemStack TargetHelmet = name.getInventory().getHelmet();
		ItemStack TargetChestplate = name.getInventory().getChestplate();
		ItemStack TargetLeggings = name.getInventory().getLeggings();
		ItemStack TargetBoots = name.getInventory().getBoots();
		ItemStack TargetHand = name.getInventory().getItemInHand();
		
		Stack("��6��l" + name.getName(), 397, 3, 1, Method.getPlayerHead(name, p.getName()), 11, GUI);
		
		if (TargetHand.getType() != Material.AIR) GUI.setItem(33, TargetHand);
		else Stack2("��f����ִ� �������� �����ϴ�.", 102, 0, 1, Arrays.asList(), 33, GUI);
		
		if (TargetHelmet != null) GUI.setItem(29, TargetHelmet);
		else Stack2("��f������ ������ �����ϴ�.", 102, 0, 1, Arrays.asList(), 29, GUI);
		
		if (TargetChestplate != null) GUI.setItem(30, TargetChestplate);
		else Stack2("��f������ ������ �����ϴ�.", 102, 0, 1, Arrays.asList(), 30, GUI);
		
		if (TargetLeggings != null) GUI.setItem(31, TargetLeggings);
		else Stack2("��f������ ���뽺�� �����ϴ�.", 102, 0, 1, Arrays.asList(), 31, GUI);
		
		if (TargetBoots != null) GUI.setItem(32, TargetBoots);
		else Stack2("��f������ ������ �����ϴ�.", 102, 0, 1, Arrays.asList(), 32, GUI);
		
		if (PL.getInfoInt(name, "��.������ �ڵ�") != 0 && PL.getInfoString(name, "��.������ �̸�") != null && !PL.getInfoList(name, "��.������ ����").equals(Arrays.asList()))
			Stack(PL.getInfoString(name, "��.������ �̸�"), PL.getInfoInt(name, "��.������ �ڵ�"), 0, 1, PL.getInfoList(name, "��.������ ����"), 15, GUI);
		else Stack2("��f������ ���� �����ϴ�.", 368, 0, 1, Arrays.asList(), 15, GUI);
		
		if (P.getInfoInt(name, "1.������ �ڵ�") != 0 && P.getInfoString(name, "1.������ �̸�") != null && !P.getInfoList(name, "1.������ ����").equals(Arrays.asList()))
			Stack(P.getInfoString(name, "1.������ �̸�"), P.getInfoInt(name, "1.������ �ڵ�"), 0, 1, P.getInfoList(name, "1.������ ����"), 12, GUI);
		else Stack2("��f������ ������ �����ϴ�.", 370, 0, 1, Arrays.asList(), 12, GUI);
		
		if (P.getInfoInt(name, "2.������ �ڵ�") != 0 && P.getInfoString(name, "2.������ �̸�") != null && !P.getInfoList(name, "2.������ ����").equals(Arrays.asList()))
			Stack(P.getInfoString(name, "2.������ �̸�"), P.getInfoInt(name, "2.������ �ڵ�"), 0, 1, P.getInfoList(name, "2.������ ����"), 13, GUI);
		else Stack2("��f������ ������ �����ϴ�.", 370, 0, 1, Arrays.asList(), 13, GUI);
		
		if (P.getInfoInt(name, "3.������ �ڵ�") != 0 && P.getInfoString(name, "3.������ �̸�") != null && !P.getInfoList(name, "3.������ ����").equals(Arrays.asList()))
			Stack(P.getInfoString(name, "3.������ �̸�"), P.getInfoInt(name, "3.������ �ڵ�"), 0, 1, P.getInfoList(name, "3.������ ����"), 14, GUI);
		else Stack2("��f������ ������ �����ϴ�.", 370, 0, 1, Arrays.asList(), 14, GUI);
		
		int num = 0;
		for (int i = 0; i < 10; i++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 36;
			else if (i == 5) num = 8;
			else if (i == 6) num = 17;
			else if (i == 7) num = 26;
			else if (i == 8) num = 35;
			else if (i == 9) num = 44;
				
			Stack2("", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		num = 48;
		String guild = GuildAPI.getJoinGuild(p.getName());
		String party = PartyAPI.getJoinParty(p);
		boolean is1 = false;
		boolean is2 = false;
		boolean is3 = false;
		
		if (!p.equals(name)) {
			for (int i = 0; i < 3; i++) {
				if (guild != null && !is1) {
					Stack("��a��� �ʴ��ϱ�", 341, 0, 1, GUIMessage.ClickGuildInvite, num, GUI);
					num++; is1 = true;
				}
				
				else if (party != null && !is2) {
					Stack("��b��Ƽ �ʴ��ϱ�", 402, 0, 1, GUIMessage.ClickPartyInvite, num, GUI);
					num++; is2 = true;
				}
				
				else if (!me.espoo.pvp.main.oneVS && !is3 && me.espoo.option.PlayerYml.getInfoBoolean(name, "��� ��û")) {
					Stack("��c��� ��û�ϱ�", 276, 0, 1, GUIMessage.ClickPvPAsk, num, GUI);
					num++; is3 = true;
				}
			}
			
			if (me.espoo.option.PlayerYml.getInfoBoolean(name, "��ȯ ��û"))
				Stack("��d�ŷ� ��û�ϱ�", 34, 0, 1, GUIMessage.ClickTradeAsk, 52, GUI);
			Stack2("", 119, 0, 1, Arrays.asList(), 45, GUI);
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
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
