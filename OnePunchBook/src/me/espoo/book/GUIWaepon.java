package me.espoo.book;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIWaepon {
	static PlayerYml P;
	static GUIMessage G;
	static GUImain M;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "일반 무기 도감");
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
		
		for (int i = 0; i < 22; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 10;
			else if (i == 8) num = 19;
			else if (i == 9) num = 28;
			else if (i == 10) num = 37;
			else if (i == 11) num = 46;
			else if (i == 12) num = 47;
			else if (i == 13) num = 48;
			else if (i == 14) num = 49;
			else if (i == 15) num = 50;
			else if (i == 16) num = 51;
			else if (i == 17) num = 52;
			else if (i == 18) num = 43;
			else if (i == 19) num = 16;
			else if (i == 20) num = 25;
			else if (i == 21) num = 34;
			
			M.Stack2("§f도감", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("§f[ §7뒤로가기 §f]", 8, 0, 1, G.Water, 45, GUI);
		M.Stack("§f[ §e다음으로 §f]", 10, 0, 1, G.Lava, 53, GUI);
		
		if (P.getInfoBoolean(p, "일반 무기.보상 획득")) {
			M.Stack2("§f보상을 받으셨습니다.", 323, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("§6 사이타마 장갑", 294, 0, 1, G.WaeponBook, 42, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.구동기사")) {
			M.Stack2("§0§0§0§0§0§5§c§b§7§l§6 레이저 빔", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("§6 레이저 빔 §f§l도감", 389, 0, 1, G.Glass, 11, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.금속배트")) {
			M.Stack2("§0§0§0§0§0§5§d§6§7§l§6 금속 배트", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("§6 금속 배트 §f§l도감", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "일반 무기.돈신")) {
			M.Stack2("§0§0§0§0§0§5§e§1§7§l§6 숟가락", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("§6 숟가락 §f§l도감", 389, 0, 1, G.Glass, 13, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.동제")) {
			M.Stack2("§0§0§0§0§0§6§8§7§7§l§6 동제 머신건", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("§6 동제 머신건 §f§l도감", 389, 0, 1, G.Glass, 14, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.메탈나이트")) {
			M.Stack2("§0§0§0§0§0§5§e§c§7§l§6 메탈나이트 미사일", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("§6 메탈나이트 미사일 §f§l도감", 389, 0, 1, G.Glass, 15, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.번견맨")) {
			M.Stack2("§0§0§0§0§0§5§f§7§7§l§6 번견맨의 손톱", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("§6 번견맨의 손톱 §f§l도감", 389, 0, 1, G.Glass, 20, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.섬광의플래시")) {
			M.Stack2("§0§0§0§0§0§6§0§d§7§l§6 섬광의 플래시 검", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("§6 섬광의 플래시 검 §f§l도감", 389, 0, 1, G.Glass, 21, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.실버팽")) {
			M.Stack2("§0§0§0§0§0§4§2§a§7§l§6 유수암쇄권", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("§6 유수암쇄권 §f§l도감", 389, 0, 1, G.Glass, 22, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.아마이마스크")) {
			M.Stack2("§0§0§0§0§0§6§1§8§7§l§6 꽃미남 가면", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("§6 꽃미남 가면 §f§l도감", 389, 0, 1, G.Glass, 23, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.아토믹")) {
			M.Stack2("§0§0§0§0§0§6§2§3§7§l§6 아토믹 사무라이 검", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("§6 아토믹 사무라이 검 §f§l도감", 389, 0, 1, G.Glass, 24, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.음속의소닉")) {
			M.Stack2("§0§0§0§0§0§5§c§0§7§l§6 음속의 소닉 칼", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("§6 음속의 소닉 칼 §f§l도감", 389, 0, 1, G.Glass, 29, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.제노스")) {
			M.Stack2("§0§0§0§0§0§6§2§e§7§l§6 제노스의 파츠", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("§6 제노스의 파츠 §f§l도감", 389, 0, 1, G.Glass, 30, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.좀비맨")) {
			M.Stack2("§0§0§0§0§0§6§3§9§7§l§6 좀비맨 도끼", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("§6 좀비맨 도끼 §f§l도감", 389, 0, 1, G.Glass, 31, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.초합금검은빛")) {
			M.Stack2("§0§0§0§0§0§6§4§4§7§l§6 검은빛의 역기", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("§6 검은빛의 역기 §f§l도감", 389, 0, 1, G.Glass, 32, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.킹")) {
			M.Stack2("§0§0§0§0§0§4§5§8§7§l§6 킹의 게임기", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("§6 킹의 게임기 §f§l도감", 389, 0, 1, G.Glass, 33, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.타츠마키")) {
			M.Stack2("§0§0§0§0§0§4§1§f§7§l§6 타츠마키 염동력", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("§6 타츠마키 염동력 §f§l도감", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.탱크톱마스터")) {
			M.Stack2("§0§0§0§0§0§6§4§f§7§l§6 탱크톱 마스터 근육", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("§6 탱크톱 마스터 근육 §f§l도감", 389, 0, 1, G.Glass, 39, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.프리즈너")) {
			M.Stack2("§0§0§0§0§0§6§5§a§7§l§6 엔젤 스타일", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("§6 엔젤 스타일 §f§l도감", 389, 0, 1, G.Glass, 40, GUI);
		}
		
		if (P.getInfoBoolean(p, "일반 무기.후부키")) {
			M.Stack2("§0§0§0§0§0§6§6§5§7§l§6 후부키 염동력", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("§6 후부키 염동력 §f§l도감", 389, 0, 1, G.Glass, 41, GUI);
		}
		
		p.openInventory(GUI);
	}
}
