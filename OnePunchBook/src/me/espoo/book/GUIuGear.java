package me.espoo.book;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIuGear {
	static PlayerYml P;
	static GUIMessage G;
	static GUImain M;
	
	@SuppressWarnings("static-access")
	public static void openGUI1(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "유니크 장비 도감 1/3");
		int num = 0;

		for (int i = 0; i < 10; i ++) {
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
			
			M.Stack2("§f도감", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 14; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 46;
			else if (i == 8) num = 47;
			else if (i == 9) num = 48;
			else if (i == 10) num = 49;
			else if (i == 11) num = 50;
			else if (i == 12) num = 51;
			else if (i == 13) num = 52;
			
			M.Stack2("§f도감", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("§f[ §7뒤로가기 §f]", 8, 0, 1, G.Water, 45, GUI);
		M.Stack("§f[ §e다음으로 §f]", 10, 0, 1, G.Lava, 53, GUI);
		
		if (P.getInfoBoolean(p, "유니크 장비.구동기사모자")) {
			M.Stack2("§0§0§0§0§0§1§b§b§7§l§6 구동기사 투구", 1, 0, 1, Arrays.asList(), 10, GUI);
		} else {
			M.Stack("§6 구동기사 투구 §f§l도감", 389, 0, 1, G.Glass, 10, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.구동기사튜닉")) {
			M.Stack2("§0§0§0§0§0§1§b§c§7§l§6 구동기사 갑옷", 1, 0, 1, Arrays.asList(), 19, GUI);
		} else {
			M.Stack("§6 구동기사 갑옷 §f§l도감", 389, 0, 1, G.Glass, 19, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.구동기사바지")) {
			M.Stack2("§0§0§0§0§0§1§b§d§7§l§6 구동기사 레깅스", 1, 0, 1, Arrays.asList(), 28, GUI);
		} else {
			M.Stack("§6 구동기사 레깅스 §f§l도감", 389, 0, 1, G.Glass, 28, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.구동기사신발")) {
			M.Stack2("§0§0§0§0§0§1§b§e§7§l§6 구동기사 부츠", 1, 0, 1, Arrays.asList(), 37, GUI);
		} else {
			M.Stack("§6 구동기사 부츠 §f§l도감", 389, 0, 1, G.Glass, 37, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.금속배트모자")) {
			M.Stack2("§0§0§0§0§0§2§6§b§7§l§6 금속 배트 모자", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("§6 금속 배트 모자 §f§l도감", 389, 0, 1, G.Glass, 11, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.금속배트튜닉")) {
			M.Stack2("§0§0§0§0§0§2§6§c§7§l§6 금속 배트 튜닉", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("§6 금속 배트 튜닉 §f§l도감", 389, 0, 1, G.Glass, 20, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.금속배트바지")) {
			M.Stack2("§0§0§0§0§0§2§6§d§7§l§6 금속 배트 바지", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("§6 금속 배트 바지 §f§l도감", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.금속배트신발")) {
			M.Stack2("§0§0§0§0§0§2§6§e§7§l§6 금속 배트 신발", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("§6 금속 배트 신발 §f§l도감", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "유니크 장비.돈신모자")) {
			M.Stack2("§0§0§0§0§0§1§c§b§7§l§6 돈신 모자", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("§6 돈신 모자 §f§l도감", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.돈신튜닉")) {
			M.Stack2("§0§0§0§0§0§1§c§c§7§l§6 돈신 튜닉", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("§6 돈신 튜닉 §f§l도감", 389, 0, 1, G.Glass, 21, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.돈신바지")) {
			M.Stack2("§0§0§0§0§0§1§c§d§7§l§6 돈신 바지", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("§6 돈신 바지 §f§l도감", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.돈신신발")) {
			M.Stack2("§0§0§0§0§0§1§c§e§7§l§6 돈신 신발", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("§6 돈신 신발 §f§l도감", 389, 0, 1, G.Glass, 39, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.동제모자")) {
			M.Stack2("§0§0§0§0§0§3§5§f§7§l§6 동제 모자", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("§6 동제 모자 §f§l도감", 389, 0, 1, G.Glass, 13, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.동제튜닉")) {
			M.Stack2("§0§0§0§0§0§3§6§0§7§l§6 동제 튜닉", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("§6 동제 튜닉 §f§l도감", 389, 0, 1, G.Glass, 22, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.동제바지")) {
			M.Stack2("§0§0§0§0§0§3§6§1§7§l§6 동제 바지", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("§6 동제 바지 §f§l도감", 389, 0, 1, G.Glass, 31, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.동제신발")) {
			M.Stack2("§0§0§0§0§0§3§6§2§7§l§6 동제 신발", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("§6 동제 신발 §f§l도감", 389, 0, 1, G.Glass, 40, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.메탈나이트모자")) {
			M.Stack2("§0§0§0§0§0§1§9§b§7§l§6 메탈나이트 투구", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("§6 메탈나이트 투구 §f§l도감", 389, 0, 1, G.Glass, 14, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.메탈나이트튜닉")) {
			M.Stack2("§0§0§0§0§0§1§9§c§7§l§6 메탈나이트 갑옷", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("§6 메탈나이트 갑옷 §f§l도감", 389, 0, 1, G.Glass, 23, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.메탈나이트바지")) {
			M.Stack2("§0§0§0§0§0§1§9§d§7§l§6 메탈나이트 레깅스", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("§6 메탈나이트 레깅스 §f§l도감", 389, 0, 1, G.Glass, 32, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.메탈나이트신발")) {
			M.Stack2("§0§0§0§0§0§1§9§e§7§l§6 메탈나이트 부츠", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("§6 메탈나이트 부츠 §f§l도감", 389, 0, 1, G.Glass, 41, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.번견맨모자")) {
			M.Stack2("§0§0§0§0§0§1§e§b§7§l§6 번견맨 모자", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("§6 번견맨 모자 §f§l도감", 389, 0, 1, G.Glass, 15, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.번견맨튜닉")) {
			M.Stack2("§0§0§0§0§0§1§e§c§7§l§6 번견맨 튜닉", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("§6 번견맨 튜닉 §f§l도감", 389, 0, 1, G.Glass, 24, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.번견맨바지")) {
			M.Stack2("§0§0§0§0§0§1§e§d§7§l§6 번견맨 바지", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("§6 번견맨 바지 §f§l도감", 389, 0, 1, G.Glass, 33, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.번견맨신발")) {
			M.Stack2("§0§0§0§0§0§1§e§e§7§l§6 번견맨 신발", 1, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("§6 번견맨 신발 §f§l도감", 389, 0, 1, G.Glass, 42, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시모자")) {
			M.Stack2("§0§0§0§0§0§4§d§d§7§l§6 섬광의 플래시 모자", 1, 0, 1, Arrays.asList(), 16, GUI);
		} else {
			M.Stack("§6 섬광의 플래시 모자 §f§l도감", 389, 0, 1, G.Glass, 16, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시튜닉")) {
			M.Stack2("§0§0§0§0§0§4§d§e§7§l§6 섬광의 플래시 튜닉", 1, 0, 1, Arrays.asList(), 25, GUI);
		} else {
			M.Stack("§6 섬광의 플래시 튜닉 §f§l도감", 389, 0, 1, G.Glass, 25, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시바지")) {
			M.Stack2("§0§0§0§0§0§4§d§f§7§l§6 섬광의 플래시 바지", 1, 0, 1, Arrays.asList(), 34, GUI);
		} else {
			M.Stack("§6 섬광의 플래시 바지 §f§l도감", 389, 0, 1, G.Glass, 34, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시신발")) {
			M.Stack2("§0§0§0§0§0§4§e§0§7§l§6 섬광의 플래시 신발", 1, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("§6 섬광의 플래시 신발 §f§l도감", 389, 0, 1, G.Glass, 43, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void openGUI2(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "유니크 장비 도감 2/3");
		int num = 0;

		for (int i = 0; i < 10; i ++) {
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
			
			M.Stack2("§f도감", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 14; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 46;
			else if (i == 8) num = 47;
			else if (i == 9) num = 48;
			else if (i == 10) num = 49;
			else if (i == 11) num = 50;
			else if (i == 12) num = 51;
			else if (i == 13) num = 52;
			
			M.Stack2("§f도감", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("§f[ §7뒤로가기 §f]", 8, 0, 1, G.Water, 45, GUI);
		M.Stack("§f[ §e다음으로 §f]", 10, 0, 1, G.Lava, 53, GUI);
		
		if (P.getInfoBoolean(p, "유니크 장비.실버팽모자")) {
			M.Stack2("§0§0§0§0§0§3§4§f§7§l§6 실버팽 모자", 1, 0, 1, Arrays.asList(), 10, GUI);
		} else {
			M.Stack("§6 실버팽 모자 §f§l도감", 389, 0, 1, G.Glass, 10, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.실버팽튜닉")) {
			M.Stack2("§0§0§0§0§0§3§5§0§7§l§6 실버팽 튜닉", 1, 0, 1, Arrays.asList(), 19, GUI);
		} else {
			M.Stack("§6 실버팽 튜닉 §f§l도감", 389, 0, 1, G.Glass, 19, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.실버팽바지")) {
			M.Stack2("§0§0§0§0§0§3§5§1§7§l§6 실버팽 레깅스", 1, 0, 1, Arrays.asList(), 28, GUI);
		} else {
			M.Stack("§6 실버팽 레깅스 §f§l도감", 389, 0, 1, G.Glass, 28, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.실버팽신발")) {
			M.Stack2("§0§0§0§0§0§3§5§2§7§l§6 실버팽 신발", 1, 0, 1, Arrays.asList(), 37, GUI);
		} else {
			M.Stack("§6 실버팽 신발 §f§l도감", 389, 0, 1, G.Glass, 37, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.아마이마스크모자")) {
			M.Stack2("§0§0§0§0§0§2§9§b§7§l§6 아마이마스크 모자", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("§6 아마이마스크 모자 §f§l도감", 389, 0, 1, G.Glass, 11, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.아마이마스크튜닉")) {
			M.Stack2("§0§0§0§0§0§2§9§c§7§l§6 아마이마스크 튜닉", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("§6 아마이마스크 튜닉 §f§l도감", 389, 0, 1, G.Glass, 20, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.아마이마스크바지")) {
			M.Stack2("§0§0§0§0§0§2§9§d§7§l§6 아마이마스크 바지", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("§6 아마이마스크 바지 §f§l도감", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.아마이마스크신발")) {
			M.Stack2("§0§0§0§0§0§2§9§e§7§l§6 아마이마스크 신발", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("§6 아마이마스크 신발 §f§l도감", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "유니크 장비.아토믹모자")) {
			M.Stack2("§0§0§0§0§0§1§4§b§7§l§6 아토믹 사무라이 모자", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("§6 아토믹 사무라이 모자 §f§l도감", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.아토믹튜닉")) {
			M.Stack2("§0§0§0§0§0§1§4§c§7§l§6 아토믹 사무라이 튜닉", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("§6 아토믹 사무라이 튜닉 §f§l도감", 389, 0, 1, G.Glass, 21, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.아토믹바지")) {
			M.Stack2("§0§0§0§0§0§1§4§d§7§l§6 아토믹 사무라이 바지", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("§6 아토믹 사무라이 바지 §f§l도감", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.아토믹신발")) {
			M.Stack2("§0§0§0§0§0§1§4§e§7§l§6 아토믹 사무라이 신발", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("§6 아토믹 사무라이 신발 §f§l도감", 389, 0, 1, G.Glass, 39, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.음속의소닉모자")) {
			M.Stack2("§0§0§0§0§0§2§c§b§7§l§6 음속의 소닉 모자", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("§6 음속의 소닉 모자 §f§l도감", 389, 0, 1, G.Glass, 13, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.음속의소닉튜닉")) {
			M.Stack2("§0§0§0§0§0§2§c§c§7§l§6 음속의 소닉 튜닉", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("§6 음속의 소닉 튜닉 §f§l도감", 389, 0, 1, G.Glass, 22, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.음속의소닉바지")) {
			M.Stack2("§0§0§0§0§0§2§c§d§7§l§6 음속의 소닉 바지", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("§6 음속의 소닉 바지 §f§l도감", 389, 0, 1, G.Glass, 31, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.음속의소닉신발")) {
			M.Stack2("§0§0§0§0§0§2§c§e§7§l§6 음속의 소닉 신발", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("§6 음속의 소닉 신발 §f§l도감", 389, 0, 1, G.Glass, 40, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.제노스모자")) {
			M.Stack2("§0§0§0§0§0§2§4§b§7§l§6 제노스 모자", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("§6 제노스 모자 §f§l도감", 389, 0, 1, G.Glass, 14, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.제노스튜닉")) {
			M.Stack2("§0§0§0§0§0§2§4§c§7§l§6 제노스 갑옷", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("§6 제노스 갑옷 §f§l도감", 389, 0, 1, G.Glass, 23, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.제노스바지")) {
			M.Stack2("§0§0§0§0§0§2§4§d§7§l§6 제노스 바지", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("§6 제노스 바지 §f§l도감", 389, 0, 1, G.Glass, 32, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.제노스신발")) {
			M.Stack2("§0§0§0§0§0§2§4§e§7§l§6 제노스 신발", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("§6 제노스 신발 §f§l도감", 389, 0, 1, G.Glass, 41, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.좀비맨모자")) {
			M.Stack2("§0§0§0§0§0§1§a§b§7§l§6 좀비맨 모자", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("§6 좀비맨 모자 §f§l도감", 389, 0, 1, G.Glass, 15, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.좀비맨튜닉")) {
			M.Stack2("§0§0§0§0§0§1§a§c§7§l§6 좀비맨 튜닉", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("§6 좀비맨 튜닉 §f§l도감", 389, 0, 1, G.Glass, 24, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.좀비맨바지")) {
			M.Stack2("§0§0§0§0§0§1§a§d§7§l§6 좀비맨 바지", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("§6 좀비맨 바지 §f§l도감", 389, 0, 1, G.Glass, 33, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.좀비맨신발")) {
			M.Stack2("§0§0§0§0§0§1§a§e§7§l§6 좀비맨 신발", 1, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("§6 좀비맨 신발 §f§l도감", 389, 0, 1, G.Glass, 42, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛모자")) {
			M.Stack2("§0§0§0§0§0§2§b§f§7§l§6 초합금 검은빛 모자", 1, 0, 1, Arrays.asList(), 16, GUI);
		} else {
			M.Stack("§6 초합금 검은빛 모자 §f§l도감", 389, 0, 1, G.Glass, 16, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛튜닉")) {
			M.Stack2("§0§0§0§0§0§2§c§0§7§l§6 초합금 검은빛 튜닉", 1, 0, 1, Arrays.asList(), 25, GUI);
		} else {
			M.Stack("§6 초합금 검은빛 튜닉 §f§l도감", 389, 0, 1, G.Glass, 25, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛바지")) {
			M.Stack2("§0§0§0§0§0§2§0§d§7§l§6 초합금 검은빛 바지", 1, 0, 1, Arrays.asList(), 34, GUI);
		} else {
			M.Stack("§6 초합금 검은빛 바지 §f§l도감", 389, 0, 1, G.Glass, 34, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛신발")) {
			M.Stack2("§0§0§0§0§0§2§0§e§7§l§6 초합금 검은빛 신발", 1, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("§6 초합금 검은빛 신발 §f§l도감", 389, 0, 1, G.Glass, 43, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void openGUI3(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "유니크 장비 도감 3/3");
		int num = 0;

		for (int i = 0; i < 11; i ++) {
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
			else if (i == 10) num = 53;
			
			M.Stack2("§f도감", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 21; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 46;
			else if (i == 8) num = 47;
			else if (i == 9) num = 48;
			else if (i == 10) num = 49;
			else if (i == 11) num = 50;
			else if (i == 12) num = 51;
			else if (i == 13) num = 52;
			else if (i == 14) num = 15;
			else if (i == 15) num = 16;
			else if (i == 16) num = 24;
			else if (i == 17) num = 25;
			else if (i == 18) num = 33;
			else if (i == 19) num = 34;
			else if (i == 20) num = 42;
			
			M.Stack2("§f도감", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("§f[ §7뒤로가기 §f]", 8, 0, 1, G.Water, 45, GUI);
		
		if (P.getInfoBoolean(p, "유니크 장비.보상 획득")) {
			M.Stack2("§f보상을 받으셨습니다.", 323, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("§6 사이타마 장비 큐브", 124, 0, 1, G.uGearBook, 43, GUI);
		}
		
		if (P.getInfoBoolean(p, "유니크 장비.킹모자")) {
			M.Stack2("§0§0§0§0§0§1§8§b§7§l§6 킹 모자", 1, 0, 1, Arrays.asList(), 10, GUI);
		} else {
			M.Stack("§6 킹 모자 §f§l도감", 389, 0, 1, G.Glass, 10, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.킹튜닉")) {
			M.Stack2("§0§0§0§0§0§1§8§c§7§l§6 킹 튜닉", 1, 0, 1, Arrays.asList(), 19, GUI);
		} else {
			M.Stack("§6 킹 튜닉 §f§l도감", 389, 0, 1, G.Glass, 19, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.킹바지")) {
			M.Stack2("§0§0§0§0§0§1§8§d§7§l§6 킹 바지", 1, 0, 1, Arrays.asList(), 28, GUI);
		} else {
			M.Stack("§6 킹 바지 §f§l도감", 389, 0, 1, G.Glass, 28, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.킹신발")) {
			M.Stack2("§0§0§0§0§0§1§8§e§7§l§6 킹 신발", 1, 0, 1, Arrays.asList(), 37, GUI);
		} else {
			M.Stack("§6 킹 신발 §f§l도감", 389, 0, 1, G.Glass, 37, GUI);
		}
 		
		if (P.getInfoBoolean(p, "유니크 장비.타츠마키모자")) {
			M.Stack2("§0§0§0§0§0§3§3§f§7§l§6 타츠마키 모자", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("§6 타츠마키 모자 §f§l도감", 389, 0, 1, G.Glass, 11, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.타츠마키튜닉")) {
			M.Stack2("§0§0§0§0§0§3§4§0§7§l§6 타츠마키 튜닉", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("§6 타츠마키 튜닉 §f§l도감", 389, 0, 1, G.Glass, 20, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.타츠마키바지")) {
			M.Stack2("§0§0§0§0§0§3§4§1§7§l§6 타츠마키 바지", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("§6 타츠마키 바지 §f§l도감", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.타츠마키신발")) {
			M.Stack2("§0§0§0§0§0§3§4§2§7§l§6 타츠마키 신발", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("§6 타츠마키 신발 §f§l도감", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터모자")) {
			M.Stack2("§0§0§0§0§0§2§7§b§7§l§6 탱크톱 마스터 모자", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("§6 탱크톱 마스터 모자 §f§l도감", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터튜닉")) {
			M.Stack2("§0§0§0§0§0§2§7§c§7§l§6 탱크톱 마스터 튜닉", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("§6 탱크톱 마스터 튜닉 §f§l도감", 389, 0, 1, G.Glass, 21, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터바지")) {
			M.Stack2("§0§0§0§0§0§2§7§d§7§l§6 탱크톱 마스터 바지", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("§6 탱크톱 마스터 바지 §f§l도감", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터신발")) {
			M.Stack2("§0§0§0§0§0§2§7§e§7§l§6 탱크톱 마스터 신발", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("§6 탱크톱 마스터 신발 §f§l도감", 389, 0, 1, G.Glass, 39, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.프리즈너모자")) {
			M.Stack2("§0§0§0§0§0§2§8§b§7§l§6 포동포동 프리즈너 모자", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("§6 포동포동 프리즈너 모자 §f§l도감", 389, 0, 1, G.Glass, 13, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.프리즈너튜닉")) {
			M.Stack2("§0§0§0§0§0§2§8§c§7§l§6 포동포동 프리즈너 튜닉", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("§6 포동포동 프리즈너 튜닉 §f§l도감", 389, 0, 1, G.Glass, 22, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.프리즈너바지")) {
			M.Stack2("§0§0§0§0§0§2§8§d§7§l§6 포동포동 프리즈너 바지", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("§6 포동포동 프리즈너 바지 §f§l도감", 389, 0, 1, G.Glass, 31, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.프리즈너신발")) {
			M.Stack2("§0§0§0§0§0§2§8§e§7§l§6 포동포동 프리즈너 신발", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("§6 포동포동 프리즈너 신발 §f§l도감", 389, 0, 1, G.Glass, 40, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.후부키모자")) {
			M.Stack2("§0§0§0§0§0§2§a§b§7§l§6 후부키 모자", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("§6 후부키 모자 §f§l도감", 389, 0, 1, G.Glass, 14, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.후부키튜닉")) {
			M.Stack2("§0§0§0§0§0§2§a§c§7§l§6 후부키 튜닉", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("§6 후부키 튜닉 §f§l도감", 389, 0, 1, G.Glass, 23, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.후부키바지")) {
			M.Stack2("§0§0§0§0§0§2§a§d§7§l§6 후부키 바지", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("§6 후부키 바지 §f§l도감", 389, 0, 1, G.Glass, 32, GUI);
		}

		if (P.getInfoBoolean(p, "유니크 장비.후부키신발")) {
			M.Stack2("§0§0§0§0§0§2§a§e§7§l§6 후부키 신발", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("§6 후부키 신발 §f§l도감", 389, 0, 1, G.Glass, 41, GUI);
		}
		
		p.openInventory(GUI);
	}
}
