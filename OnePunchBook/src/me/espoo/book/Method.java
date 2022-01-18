package me.espoo.book;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Method {
	static PlayerYml P;
	
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        return message;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void voidWaepon(Player p, InventoryClickEvent e, String str, ItemStack c, String name) {
		P.setInfoBoolean(p, "일반 무기." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6무기를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "풀강 무기." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6무기를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "일반 장비." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6장비를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "일반 장비." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6장비를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "일반 장비." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6장비를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "유니크 장비." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6장비를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "유니크 장비." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6장비를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "유니크 장비." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6장비를 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "일반 룬." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6룬을 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "풀강 룬." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6룬을 도감에 등록하셨습니다.");
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
		P.setInfoBoolean(p, "도구." + name, true);
		if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 4.0F, 1.5F);
		p.sendMessage("§6성공적으로 §c" + str + " §6도구를 도감에 등록하셨습니다.");
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
		if (P.getInfoBoolean(p, "일반 룬.불속성") && P.getInfoBoolean(p, "일반 룬.바람속성") && P.getInfoBoolean(p, "일반 룬.치유속성") && 
			P.getInfoBoolean(p, "일반 룬.어둠속성") && P.getInfoBoolean(p, "일반 룬.독속성") && P.getInfoBoolean(p, "일반 룬.부패속성") && 
			P.getInfoBoolean(p, "일반 룬.얼음속성")) {
			if (P.getInfoBoolean(p, "일반 룬.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isXLoon(Player p) {
		if (P.getInfoBoolean(p, "풀강 룬.불속성") && P.getInfoBoolean(p, "풀강 룬.바람속성") && P.getInfoBoolean(p, "풀강 룬.치유속성") && 
			P.getInfoBoolean(p, "풀강 룬.어둠속성") && P.getInfoBoolean(p, "풀강 룬.독속성") && P.getInfoBoolean(p, "풀강 룬.부패속성") && 
			P.getInfoBoolean(p, "풀강 룬.얼음속성")) {
			if (P.getInfoBoolean(p, "풀강 룬.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isWaepon(Player p) {
		if (P.getInfoBoolean(p, "일반 무기.구동기사") && P.getInfoBoolean(p, "일반 무기.금속배트") && P.getInfoBoolean(p, "일반 무기.돈신") && 
			P.getInfoBoolean(p, "일반 무기.동제") && P.getInfoBoolean(p, "일반 무기.메탈나이트") && P.getInfoBoolean(p, "일반 무기.번견맨") && 
			P.getInfoBoolean(p, "일반 무기.섬광의플래시") && P.getInfoBoolean(p, "일반 무기.실버팽") && P.getInfoBoolean(p, "일반 무기.아마이마스크") && 
			P.getInfoBoolean(p, "일반 무기.아토믹") && P.getInfoBoolean(p, "일반 무기.음속의소닉") && P.getInfoBoolean(p, "일반 무기.제노스") && 
			P.getInfoBoolean(p, "일반 무기.좀비맨") && P.getInfoBoolean(p, "일반 무기.초합금검은빛") && P.getInfoBoolean(p, "일반 무기.킹") && 
			P.getInfoBoolean(p, "일반 무기.타츠마키") && P.getInfoBoolean(p, "일반 무기.탱크톱마스터") && P.getInfoBoolean(p, "일반 무기.프리즈너") && 
			P.getInfoBoolean(p, "일반 무기.후부키")) {
			if (P.getInfoBoolean(p, "일반 무기.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isXWaepon(Player p) {
		if (P.getInfoBoolean(p, "풀강 무기.구동기사") && P.getInfoBoolean(p, "풀강 무기.금속배트") && P.getInfoBoolean(p, "풀강 무기.돈신") && 
			P.getInfoBoolean(p, "풀강 무기.동제") && P.getInfoBoolean(p, "풀강 무기.메탈나이트") && P.getInfoBoolean(p, "풀강 무기.번견맨") && 
			P.getInfoBoolean(p, "풀강 무기.섬광의플래시") && P.getInfoBoolean(p, "풀강 무기.실버팽") && P.getInfoBoolean(p, "풀강 무기.아마이마스크") && 
			P.getInfoBoolean(p, "풀강 무기.아토믹") && P.getInfoBoolean(p, "풀강 무기.음속의소닉") && P.getInfoBoolean(p, "풀강 무기.제노스") && 
			P.getInfoBoolean(p, "풀강 무기.좀비맨") && P.getInfoBoolean(p, "풀강 무기.초합금검은빛") && P.getInfoBoolean(p, "풀강 무기.킹") && 
			P.getInfoBoolean(p, "풀강 무기.타츠마키") && P.getInfoBoolean(p, "풀강 무기.탱크톱마스터") && P.getInfoBoolean(p, "풀강 무기.프리즈너") && 
			P.getInfoBoolean(p, "풀강 무기.후부키")) {
			if (P.getInfoBoolean(p, "풀강 무기.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isnGear(Player p) {
		if (P.getInfoBoolean(p, "일반 장비.구동기사모자") && P.getInfoBoolean(p, "일반 장비.금속배트모자") && P.getInfoBoolean(p, "일반 장비.돈신모자") && 
				P.getInfoBoolean(p, "일반 장비.동제모자") && P.getInfoBoolean(p, "일반 장비.메탈나이트모자") && P.getInfoBoolean(p, "일반 장비.번견맨모자") && 
				P.getInfoBoolean(p, "일반 장비.섬광의플래시모자") && P.getInfoBoolean(p, "일반 장비.실버팽모자") && P.getInfoBoolean(p, "일반 장비.아마이마스크모자") && 
				P.getInfoBoolean(p, "일반 장비.아토믹모자") && P.getInfoBoolean(p, "일반 장비.음속의소닉모자") && P.getInfoBoolean(p, "일반 장비.제노스모자") && 
				P.getInfoBoolean(p, "일반 장비.좀비맨모자") && P.getInfoBoolean(p, "일반 장비.초합금검은빛모자") && P.getInfoBoolean(p, "일반 장비.킹모자") && 
				P.getInfoBoolean(p, "일반 장비.타츠마키모자") && P.getInfoBoolean(p, "일반 장비.탱크톱마스터모자") && P.getInfoBoolean(p, "일반 장비.프리즈너모자") && 
				P.getInfoBoolean(p, "일반 장비.후부키모자") && P.getInfoBoolean(p, "일반 장비.구동기사튜닉") && P.getInfoBoolean(p, "일반 장비.금속배트튜닉") && 
				P.getInfoBoolean(p, "일반 장비.돈신튜닉") && P.getInfoBoolean(p, "일반 장비.동제튜닉") && P.getInfoBoolean(p, "일반 장비.메탈나이트튜닉") && 
				P.getInfoBoolean(p, "일반 장비.번견맨튜닉") && P.getInfoBoolean(p, "일반 장비.섬광의플래시튜닉") && P.getInfoBoolean(p, "일반 장비.실버팽튜닉") && 
				P.getInfoBoolean(p, "일반 장비.아마이마스크튜닉") && P.getInfoBoolean(p, "일반 장비.아토믹튜닉") && P.getInfoBoolean(p, "일반 장비.음속의소닉튜닉") && 
				P.getInfoBoolean(p, "일반 장비.제노스튜닉") && P.getInfoBoolean(p, "일반 장비.좀비맨튜닉") && P.getInfoBoolean(p, "일반 장비.초합금검은빛튜닉") && 
				P.getInfoBoolean(p, "일반 장비.킹튜닉") && P.getInfoBoolean(p, "일반 장비.타츠마키튜닉") && P.getInfoBoolean(p, "일반 장비.탱크톱마스터튜닉") && 
				P.getInfoBoolean(p, "일반 장비.프리즈너튜닉") && P.getInfoBoolean(p, "일반 장비.후부키튜닉") && P.getInfoBoolean(p, "일반 장비.구동기사바지") && 
				P.getInfoBoolean(p, "일반 장비.금속배트바지") && P.getInfoBoolean(p, "일반 장비.돈신바지") && P.getInfoBoolean(p, "일반 장비.동제바지") && 
				P.getInfoBoolean(p, "일반 장비.메탈나이트바지") && P.getInfoBoolean(p, "일반 장비.번견맨바지") && P.getInfoBoolean(p, "일반 장비.섬광의플래시바지") && 
				P.getInfoBoolean(p, "일반 장비.실버팽바지") && P.getInfoBoolean(p, "일반 장비.아마이마스크바지") && P.getInfoBoolean(p, "일반 장비.아토믹바지") && 
				P.getInfoBoolean(p, "일반 장비.음속의소닉바지") && P.getInfoBoolean(p, "일반 장비.제노스바지") && P.getInfoBoolean(p, "일반 장비.좀비맨바지") && 
				P.getInfoBoolean(p, "일반 장비.초합금검은빛바지") && P.getInfoBoolean(p, "일반 장비.킹바지") && P.getInfoBoolean(p, "일반 장비.타츠마키바지") && 
				P.getInfoBoolean(p, "일반 장비.탱크톱마스터바지") && P.getInfoBoolean(p, "일반 장비.프리즈너바지") && P.getInfoBoolean(p, "일반 장비.후부키바지") && 
				P.getInfoBoolean(p, "일반 장비.구동기사신발") && P.getInfoBoolean(p, "일반 장비.금속배트신발") && P.getInfoBoolean(p, "일반 장비.돈신신발") && 
				P.getInfoBoolean(p, "일반 장비.동제신발") && P.getInfoBoolean(p, "일반 장비.메탈나이트신발") && P.getInfoBoolean(p, "일반 장비.번견맨신발") && 
				P.getInfoBoolean(p, "일반 장비.섬광의플래시신발") && P.getInfoBoolean(p, "일반 장비.실버팽신발") && P.getInfoBoolean(p, "일반 장비.아마이마스크신발") && 
				P.getInfoBoolean(p, "일반 장비.아토믹신발") && P.getInfoBoolean(p, "일반 장비.음속의소닉신발") && P.getInfoBoolean(p, "일반 장비.제노스신발") && 
				P.getInfoBoolean(p, "일반 장비.좀비맨신발") && P.getInfoBoolean(p, "일반 장비.초합금검은빛신발") && P.getInfoBoolean(p, "일반 장비.킹신발") && 
				P.getInfoBoolean(p, "일반 장비.타츠마키신발") && P.getInfoBoolean(p, "일반 장비.탱크톱마스터신발") && P.getInfoBoolean(p, "일반 장비.프리즈너신발") && 
				P.getInfoBoolean(p, "일반 장비.후부키신발")) {
			if (P.getInfoBoolean(p, "일반 장비.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isuGear(Player p) {
		if (P.getInfoBoolean(p, "유니크 장비.구동기사모자") && P.getInfoBoolean(p, "유니크 장비.금속배트모자") && P.getInfoBoolean(p, "유니크 장비.돈신모자") && 
				P.getInfoBoolean(p, "유니크 장비.동제모자") && P.getInfoBoolean(p, "유니크 장비.메탈나이트모자") && P.getInfoBoolean(p, "유니크 장비.번견맨모자") && 
				P.getInfoBoolean(p, "유니크 장비.섬광의플래시모자") && P.getInfoBoolean(p, "유니크 장비.실버팽모자") && P.getInfoBoolean(p, "유니크 장비.아마이마스크모자") && 
				P.getInfoBoolean(p, "유니크 장비.아토믹모자") && P.getInfoBoolean(p, "유니크 장비.음속의소닉모자") && P.getInfoBoolean(p, "유니크 장비.제노스모자") && 
				P.getInfoBoolean(p, "유니크 장비.좀비맨모자") && P.getInfoBoolean(p, "유니크 장비.초합금검은빛모자") && P.getInfoBoolean(p, "유니크 장비.킹모자") && 
				P.getInfoBoolean(p, "유니크 장비.타츠마키모자") && P.getInfoBoolean(p, "유니크 장비.탱크톱마스터모자") && P.getInfoBoolean(p, "유니크 장비.프리즈너모자") && 
				P.getInfoBoolean(p, "유니크 장비.후부키모자") && P.getInfoBoolean(p, "유니크 장비.구동기사튜닉") && P.getInfoBoolean(p, "유니크 장비.금속배트튜닉") && 
				P.getInfoBoolean(p, "유니크 장비.돈신튜닉") && P.getInfoBoolean(p, "유니크 장비.동제튜닉") && P.getInfoBoolean(p, "유니크 장비.메탈나이트튜닉") && 
				P.getInfoBoolean(p, "유니크 장비.번견맨튜닉") && P.getInfoBoolean(p, "유니크 장비.섬광의플래시튜닉") && P.getInfoBoolean(p, "유니크 장비.실버팽튜닉") && 
				P.getInfoBoolean(p, "유니크 장비.아마이마스크튜닉") && P.getInfoBoolean(p, "유니크 장비.아토믹튜닉") && P.getInfoBoolean(p, "유니크 장비.음속의소닉튜닉") && 
				P.getInfoBoolean(p, "유니크 장비.제노스튜닉") && P.getInfoBoolean(p, "유니크 장비.좀비맨튜닉") && P.getInfoBoolean(p, "유니크 장비.초합금검은빛튜닉") && 
				P.getInfoBoolean(p, "유니크 장비.킹튜닉") && P.getInfoBoolean(p, "유니크 장비.타츠마키튜닉") && P.getInfoBoolean(p, "유니크 장비.탱크톱마스터튜닉") && 
				P.getInfoBoolean(p, "유니크 장비.프리즈너튜닉") && P.getInfoBoolean(p, "유니크 장비.후부키튜닉") && P.getInfoBoolean(p, "유니크 장비.구동기사바지") && 
				P.getInfoBoolean(p, "유니크 장비.금속배트바지") && P.getInfoBoolean(p, "유니크 장비.돈신바지") && P.getInfoBoolean(p, "유니크 장비.동제바지") && 
				P.getInfoBoolean(p, "유니크 장비.메탈나이트바지") && P.getInfoBoolean(p, "유니크 장비.번견맨바지") && P.getInfoBoolean(p, "유니크 장비.섬광의플래시바지") && 
				P.getInfoBoolean(p, "유니크 장비.실버팽바지") && P.getInfoBoolean(p, "유니크 장비.아마이마스크바지") && P.getInfoBoolean(p, "유니크 장비.아토믹바지") && 
				P.getInfoBoolean(p, "유니크 장비.음속의소닉바지") && P.getInfoBoolean(p, "유니크 장비.제노스바지") && P.getInfoBoolean(p, "유니크 장비.좀비맨바지") && 
				P.getInfoBoolean(p, "유니크 장비.초합금검은빛바지") && P.getInfoBoolean(p, "유니크 장비.킹바지") && P.getInfoBoolean(p, "유니크 장비.타츠마키바지") && 
				P.getInfoBoolean(p, "유니크 장비.탱크톱마스터바지") && P.getInfoBoolean(p, "유니크 장비.프리즈너바지") && P.getInfoBoolean(p, "유니크 장비.후부키바지") && 
				P.getInfoBoolean(p, "유니크 장비.구동기사신발") && P.getInfoBoolean(p, "유니크 장비.금속배트신발") && P.getInfoBoolean(p, "유니크 장비.돈신신발") && 
				P.getInfoBoolean(p, "유니크 장비.동제신발") && P.getInfoBoolean(p, "유니크 장비.메탈나이트신발") && P.getInfoBoolean(p, "유니크 장비.번견맨신발") && 
				P.getInfoBoolean(p, "유니크 장비.섬광의플래시신발") && P.getInfoBoolean(p, "유니크 장비.실버팽신발") && P.getInfoBoolean(p, "유니크 장비.아마이마스크신발") && 
				P.getInfoBoolean(p, "유니크 장비.아토믹신발") && P.getInfoBoolean(p, "유니크 장비.음속의소닉신발") && P.getInfoBoolean(p, "유니크 장비.제노스신발") && 
				P.getInfoBoolean(p, "유니크 장비.좀비맨신발") && P.getInfoBoolean(p, "유니크 장비.초합금검은빛신발") && P.getInfoBoolean(p, "유니크 장비.킹신발") && 
				P.getInfoBoolean(p, "유니크 장비.타츠마키신발") && P.getInfoBoolean(p, "유니크 장비.탱크톱마스터신발") && P.getInfoBoolean(p, "유니크 장비.프리즈너신발") && 
				P.getInfoBoolean(p, "유니크 장비.후부키신발")) {
			if (P.getInfoBoolean(p, "유니크 장비.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isTool(Player p) {
		if (P.getInfoBoolean(p, "도구.보통0") && P.getInfoBoolean(p, "도구.보통1") && P.getInfoBoolean(p, "도구.보통2") && P.getInfoBoolean(p, "도구.보통3") && 
			P.getInfoBoolean(p, "도구.보통4") && P.getInfoBoolean(p, "도구.보통5") && P.getInfoBoolean(p, "도구.보통6") && P.getInfoBoolean(p, "도구.보통7") && 
			P.getInfoBoolean(p, "도구.보통8") && P.getInfoBoolean(p, "도구.보통9") && P.getInfoBoolean(p, "도구.보통10") && P.getInfoBoolean(p, "도구.진심0") && 
			P.getInfoBoolean(p, "도구.진심1") && P.getInfoBoolean(p, "도구.진심2") && P.getInfoBoolean(p, "도구.진심3") && P.getInfoBoolean(p, "도구.진심4") && 
			P.getInfoBoolean(p, "도구.진심5") && P.getInfoBoolean(p, "도구.진심6") && P.getInfoBoolean(p, "도구.진심7") && P.getInfoBoolean(p, "도구.진심8") && 
			P.getInfoBoolean(p, "도구.진심9") && P.getInfoBoolean(p, "도구.진심10") && P.getInfoBoolean(p, "도구.마스터리")) {
			if (P.getInfoBoolean(p, "도구.보상 획득") == false) {
				return true;
			}
		}
		
		return false;
	}
}
