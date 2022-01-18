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
		Inventory GUI = Bukkit.createInventory(null, 36, "파티에 초대받으셨습니다. 수락하시겠습니까?");
		
		List<String> lore = new ArrayList<String>();
		for (String str : PartyAPI.getInfo(name)) lore.add(str);
		Stack("§f" + PartyAPI.getManager(name) + " 님의 파티", 341, 0, 1, lore, 13, GUI);
		Stack2("§f예, 수락하겠습니다.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("§f아니요, 수락하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void AskJoinParty(Player p, String name)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "플레이어의 파티 가입을 수락하시겠습니까?");
		
		String player = main.PartyJoin.get(p).getName();
		List<String> lore = new ArrayList<String>();
		lore.add("§7경험치: §f" + ExpAPI.getExp(player));
		lore.add("§7히어로 점수: §f" + me.espoo.score.Method.getPlayerScore(player));
				
		Stack("§f" + player, 341, 0, 1, lore, 13, GUI);
		Stack2("§f예, 수락하겠습니다.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("§f아니요, 수락하지 않겠습니다.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void OptionGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "파티 옵션");
		
		Stack("§3파티 가입조건", 399, 0, 1, GUIMessage.getJoinTypeParty(p), 11, GUI);
		Stack("§a파티 경험치, 히어로 점수 분배 여부", 384, 0, 1, GUIMessage.getPartyShare(p), 13, GUI);
		Stack("§6파티 설명 §e§l수정", 339, 0, 1, GUIMessage.PartyInfo, 15, GUI);
		Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 18, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void noPartyGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "파티가 없으시군요!");
		
		Stack("§6파티 생성", 401, 0, 1, GUIMessage.PartyCreate, 12, GUI);
		Stack("§b파티 목록", 402, 0, 1, GUIMessage.PartyList, 14, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void userPartyGUI(Player p)
	{
		if (PartyAPI.getJoinParty(p) != null) {
			String party = PartyAPI.getJoinParty(p); int num = 0;
			Inventory GUI = Bukkit.createInventory(null, 54, "§2§lP §0§l" + party + " 님의 파티");
			
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§b파티 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 38, GUI);
			Stack("§c파티 탈퇴", 372, 0, 1, GUIMessage.PartyLeave, 42, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("§f파티원 목록 §6§l→", 323, 0, 1, PartyAPI.getInfo(party), 11, GUI);
			
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
			
			Stack("§f" + PartyAPI.getManager(party), 397, 3, 1, GUIMessage.PartyM, 12, GUI);
			
			int y = 13;
			for (String str : PartyAPI.getUser(party))
				if (!str.equalsIgnoreCase(PartyAPI.getManager(party)))
					Stack("§f" + str, 397, 3, 1, GUIMessage.PartyU, y, GUI); y++;
			
			p.openInventory(GUI);
		}
	}
	
	public static void MasterPartyGUI(Player p)
	{
		if (PartyAPI.getJoinParty(p) != null) {
			String party = PartyAPI.getJoinParty(p); int num = 0;
			Inventory GUI = Bukkit.createInventory(null, 54, "§2§lP §0§l" + party + " 님의 파티");
			
			Stack("§f" + p.getName(), 397, 3, 1, GUIMessage.getPlayerClassMessage(p), 11, GUI);
			Stack("§b파티 채팅", 339, 0, 1, GUIMessage.getPlayerChatMessage(p), 38, GUI);
			Stack("§4파티 해제", 331, 0, 1, GUIMessage.PartyDelete, 53, GUI);
			Stack("§a플레이어 초대", 138, 0, 1, GUIMessage.PartyInvite, 40, GUI);
			Stack("§f파티 옵션", 368, 0, 1, GUIMessage.PartyOption, 42, GUI);
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
			Stack("§f파티원 목록 §6§l→", 323, 0, 1, PartyAPI.getInfo(party), 11, GUI);
			
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
			
			Stack("§f" + PartyAPI.getManager(party), 397, 3, 1, GUIMessage.PartyM, 12, GUI);
			
			int y = 13;
			for (String str : PartyAPI.getUser(party)) {
				if (!str.equalsIgnoreCase(PartyAPI.getManager(party))) {
					List<String> lore = new ArrayList<String>();
					lore.add("§7계급: §f파티원");
					lore.add("");
					lore.add("§8§l<HELP> §7");
					lore.add("§7좌클릭 - 파티원의 정보 확인");
					lore.add("§7우클릭 - 파티에서 강제 퇴장");
					lore.add("§7쉬프트 클릭 - 파티장을 플레이어에게 양도");
					Stack("§f" + str, 397, 3, 1, lore, y, GUI); y++;
				}
			}
			
			p.openInventory(GUI);
		}
	}
	
	public static void ListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 9, "파티 목록 불러오는중..");
		p.openInventory(G);
		
		List<String> list = PartyYml.getPartyList();
		int num2 = (list.size() / 45) + 1;
		
		if (list.size() < 1) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 목록은 존재하지 않습니다.");
			p.closeInventory();
			return;
		}
		
		int i = (num - 1) * 45; i++;	// 어떤 순위만큼 보여줄 것인지
		int y = 1;	// for문 순위
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "파티 목록 " + num + "/" + num2);
		
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
			if (PartyAPI.getJoinType(st).equalsIgnoreCase("공개")) {
				lore.add("§f");
				lore.add("§8§l<HELP> §7아이템 클릭시");
				lore.add("§7해당 파티에 가입합니다.");
			}
			
			else if (PartyAPI.getJoinType(st).equalsIgnoreCase("신청")) {
				lore.add("§f");
				lore.add("§8§l<HELP> §7아이템 클릭시");
				lore.add("§7해당 파티에 가입을 신청합니다.");
			}
			
			Stack("§f" + st + " 님의 파티", 341, 0, 1, lore, z, GUI);
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("§6다음 목록 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
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
