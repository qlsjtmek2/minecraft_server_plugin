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
		Inventory GUI = Bukkit.createInventory(null, 27, name + " 님이 대련을 신청했습니다.");
		Stack2("§f대련을 수락하겠습니다.", 35, 5, 1, Arrays.asList(), 11, GUI);
		Stack2("§f대련을 수락하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 15, GUI);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		p.openInventory(GUI);
	}
	
	public static void StatGUI(Player p, Player name)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "상태: " + name.getName());
		
		Stack("§e포션 효과 목록", 373, 0, 1, Method.getPotion(name), 13, GUI);
		Stack("§f배고픔", 320, 0, 1, Method.getHunger(name), 15, GUI);
		
		Stack("§6전투력", 403, 0, 1, Method.getPlayerAllStat(name), 11, GUI);
		
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
			GUI = Bukkit.createInventory(null, 54, "정보: " + name.getName());
		} else {
			GUI = Bukkit.createInventory(null, 45, "정보: " + name.getName());
		}
		
		ItemStack TargetHelmet = name.getInventory().getHelmet();
		ItemStack TargetChestplate = name.getInventory().getChestplate();
		ItemStack TargetLeggings = name.getInventory().getLeggings();
		ItemStack TargetBoots = name.getInventory().getBoots();
		ItemStack TargetHand = name.getInventory().getItemInHand();
		
		Stack("§6§l" + name.getName(), 397, 3, 1, Method.getPlayerHead(name, p.getName()), 11, GUI);
		
		if (TargetHand.getType() != Material.AIR) GUI.setItem(33, TargetHand);
		else Stack2("§f들고있는 아이템이 없습니다.", 102, 0, 1, Arrays.asList(), 33, GUI);
		
		if (TargetHelmet != null) GUI.setItem(29, TargetHelmet);
		else Stack2("§f장착한 투구가 없습니다.", 102, 0, 1, Arrays.asList(), 29, GUI);
		
		if (TargetChestplate != null) GUI.setItem(30, TargetChestplate);
		else Stack2("§f장착한 갑옷이 없습니다.", 102, 0, 1, Arrays.asList(), 30, GUI);
		
		if (TargetLeggings != null) GUI.setItem(31, TargetLeggings);
		else Stack2("§f장착한 레깅스가 없습니다.", 102, 0, 1, Arrays.asList(), 31, GUI);
		
		if (TargetBoots != null) GUI.setItem(32, TargetBoots);
		else Stack2("§f장착한 부츠가 없습니다.", 102, 0, 1, Arrays.asList(), 32, GUI);
		
		if (PL.getInfoInt(name, "룬.아이템 코드") != 0 && PL.getInfoString(name, "룬.아이템 이름") != null && !PL.getInfoList(name, "룬.아이템 설명").equals(Arrays.asList()))
			Stack(PL.getInfoString(name, "룬.아이템 이름"), PL.getInfoInt(name, "룬.아이템 코드"), 0, 1, PL.getInfoList(name, "룬.아이템 설명"), 15, GUI);
		else Stack2("§f장착한 룬이 없습니다.", 368, 0, 1, Arrays.asList(), 15, GUI);
		
		if (P.getInfoInt(name, "1.아이템 코드") != 0 && P.getInfoString(name, "1.아이템 이름") != null && !P.getInfoList(name, "1.아이템 설명").equals(Arrays.asList()))
			Stack(P.getInfoString(name, "1.아이템 이름"), P.getInfoInt(name, "1.아이템 코드"), 0, 1, P.getInfoList(name, "1.아이템 설명"), 12, GUI);
		else Stack2("§f장착한 소켓이 없습니다.", 370, 0, 1, Arrays.asList(), 12, GUI);
		
		if (P.getInfoInt(name, "2.아이템 코드") != 0 && P.getInfoString(name, "2.아이템 이름") != null && !P.getInfoList(name, "2.아이템 설명").equals(Arrays.asList()))
			Stack(P.getInfoString(name, "2.아이템 이름"), P.getInfoInt(name, "2.아이템 코드"), 0, 1, P.getInfoList(name, "2.아이템 설명"), 13, GUI);
		else Stack2("§f장착한 소켓이 없습니다.", 370, 0, 1, Arrays.asList(), 13, GUI);
		
		if (P.getInfoInt(name, "3.아이템 코드") != 0 && P.getInfoString(name, "3.아이템 이름") != null && !P.getInfoList(name, "3.아이템 설명").equals(Arrays.asList()))
			Stack(P.getInfoString(name, "3.아이템 이름"), P.getInfoInt(name, "3.아이템 코드"), 0, 1, P.getInfoList(name, "3.아이템 설명"), 14, GUI);
		else Stack2("§f장착한 소켓이 없습니다.", 370, 0, 1, Arrays.asList(), 14, GUI);
		
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
					Stack("§a길드 초대하기", 341, 0, 1, GUIMessage.ClickGuildInvite, num, GUI);
					num++; is1 = true;
				}
				
				else if (party != null && !is2) {
					Stack("§b파티 초대하기", 402, 0, 1, GUIMessage.ClickPartyInvite, num, GUI);
					num++; is2 = true;
				}
				
				else if (!me.espoo.pvp.main.oneVS && !is3 && me.espoo.option.PlayerYml.getInfoBoolean(name, "대련 신청")) {
					Stack("§c대련 신청하기", 276, 0, 1, GUIMessage.ClickPvPAsk, num, GUI);
					num++; is3 = true;
				}
			}
			
			if (me.espoo.option.PlayerYml.getInfoBoolean(name, "교환 신청"))
				Stack("§d거래 신청하기", 34, 0, 1, GUIMessage.ClickTradeAsk, 52, GUI);
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
