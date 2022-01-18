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
		Inventory GUI = Bukkit.createInventory(null, 54, "옵션");

		if (Ps.getInfoBoolean(p, "팔 근력 스텟 적용")) {
			Stack("§f[ §c팔 근력 스텟 §a§lON §f]", 35, 5, 1, getLore("팔 근력 스텟", "§a활성화"), 10, GUI);
		} else {
			Stack("§f[ §c팔 근력 스텟 §c§lOFF §f]", 35, 14, 1, getLore("팔 근력 스텟", "§c비활성화"), 10, GUI);
		}
		
		if (Ps.getInfoBoolean(p, "복근 스텟 적용")) {
			Stack("§f[ §d복근 스텟 §a§lON §f]", 35, 5, 1, getLore("복근 스텟", "§a활성화"), 12, GUI);
		} else {
			Stack("§f[ §d복근 스텟 §c§lOFF §f]", 35, 14, 1, getLore("복근 스텟", "§c비활성화"), 12, GUI);
		}
		
		if (Ps.getInfoBoolean(p, "다리 근력 스텟 적용")) {
			Stack("§f[ §b다리 근력 스텟 §a§lON §f]", 35, 5, 1, getLore("다리 근력 스텟", "§a활성화"), 14, GUI);
		} else {
			Stack("§f[ §b다리 근력 스텟 §c§lOFF §f]", 35, 14, 1, getLore("다리 근력 스텟", "§c비활성화"), 14, GUI);
		}
		
		if (Ps.getInfoBoolean(p, "스피드 스텟 적용")) {
			Stack("§f[ §a스피드 스텟 §a§lON §f]", 35, 5, 1, getLore("스피드 스텟", "§a활성화"), 16, GUI);
		} else {
			Stack("§f[ §a스피드 스텟 §c§lOFF §f]", 35, 14, 1, getLore("스피드 스텟", "§c비활성화"), 16, GUI);
		}
		
		if (P.getInfoBoolean(p, "아이템 버리기")) {
			Stack("§f[ §e아이템 버리기 §a§lON §f]", 35, 5, 1, getLore("아이템 버리기", "§a활성화"), 20, GUI);
		} else {
			Stack("§f[ §e아이템 버리기 §c§lOFF §f]", 35, 14, 1, getLore("아이템 버리기", "§c비활성화"), 20, GUI);
		}
		
		if (P.getInfoBoolean(p, "채팅 보기")) {
			Stack("§f[ §6채팅 보기 §a§lON §f]", 35, 5, 1, getLore("채팅 보기", "§a활성화"), 22, GUI);
		} else {
			Stack("§f[ §6채팅 보기 §c§lOFF §f]", 35, 14, 1, getLore("채팅 보기", "§c비활성화"), 22, GUI);
		}
		
		if (P.getInfoBoolean(p, "플레이어 숨기기")) {
			Stack("§f[ §e플레이어 숨기기 §a§lON §f]", 35, 5, 1, getLore("플레이어 숨기기", "§a활성화"), 24, GUI);
		} else {
			Stack("§f[ §e플레이어 숨기기 §c§lOFF §f]", 35, 14, 1, getLore("플레이어 숨기기", "§c비활성화"), 24, GUI);
		}
		
		if (P.getInfoBoolean(p, "플레이어 라이딩")) {
			Stack("§f[ §6플레이어 라이딩 §a§lON §f]", 35, 5, 1, getLore("플레이어 라이딩", "§a활성화"), 28, GUI);
		} else {
			Stack("§f[ §6플레이어 라이딩 §c§lOFF §f]", 35, 14, 1, getLore("플레이어 라이딩", "§c비활성화"), 28, GUI);
		}
		
		if (P.getInfoBoolean(p, "정보 공개 여부")) {
			Stack("§f[ §e정보 공개 여부 §a§lON §f]", 35, 5, 1, getLore("정보 공개 여부", "§a활성화"), 30, GUI);
		} else {
			Stack("§f[ §e정보 공개 여부 §c§lOFF §f]", 35, 14, 1, getLore("정보 공개 여부", "§c비활성화"), 30, GUI);
		}
		
		if (P.getInfoBoolean(p, "효과음")) {
			Stack("§f[ §6효과음 §a§lON §f]", 35, 5, 1, getLore("효과음", "§a활성화"), 32, GUI);
		} else {
			Stack("§f[ §6효과음 §c§lOFF §f]", 35, 14, 1, getLore("효과음", "§c비활성화"), 32, GUI);
		}

		if (P.getInfoBoolean(p, "귓속말 거부")) {
			Stack("§f[ §e귓속말 거부 §a§lON §f]", 35, 5, 1, getLore("귓속말 거부", "§a활성화"), 34, GUI);
		} else {
			Stack("§f[ §e귓속말 거부 §c§lOFF §f]", 35, 14, 1, getLore("귓속말 거부", "§c비활성화"), 34, GUI);
		}
		
		if (P.getInfoBoolean(p, "파티 초대")) {
			Stack("§f[ §e파티 초대 §a§lON §f]", 35, 5, 1, getLore("파티 초대", "§a활성화"), 38, GUI);
		} else {
			Stack("§f[ §e파티 초대 §c§lOFF §f]", 35, 14, 1, getLore("파티 초대", "§c비활성화"), 38, GUI);
		}

		if (P.getInfoBoolean(p, "길드 초대")) {
			Stack("§f[ §6길드 초대 §a§lON §f]", 35, 5, 1, getLore("길드 초대", "§a활성화"), 40, GUI);
		} else {
			Stack("§f[ §6길드 초대 §c§lOFF §f]", 35, 14, 1, getLore("길드 초대", "§c비활성화"), 40, GUI);
		}
		
		if (P.getInfoBoolean(p, "상태 공개 여부")) {
			Stack("§f[ §d상태 공개 여부 §a§lON §f]", 35, 5, 1, getLore("상태 공개 여부", "§a활성화"), 42, GUI);
		} else {
			Stack("§f[ §d상태 공개 여부 §c§lOFF §f]", 35, 14, 1, getLore("상태 공개 여부", "§c비활성화"), 42, GUI);
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
		lore.add("§f현재 §e" + str + " §f옵션이 " + of + " §f상태입니다.");
		lore.add("§f활성화 또는 비활성화를 원하신다면");
		lore.add("§f현재 보고계신 이 아이템을 클릭해주세요.");
		return lore;
	}
	
	public static List<String> willAdd() {
		List<String> lore = new ArrayList<String>();
		lore.add("§7추가 준비중인 옵션입니다.");
		return lore;
	}
}
