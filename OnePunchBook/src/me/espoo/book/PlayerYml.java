package me.espoo.book;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerYml {
	public static void CreatePlayerInfo(File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("일반 무기.구동기사", false);
			config.set("일반 무기.금속배트", false);
			config.set("일반 무기.돈신", false);
			config.set("일반 무기.동제", false);
			config.set("일반 무기.메탈나이트", false);
			config.set("일반 무기.번견맨", false);
			config.set("일반 무기.섬광의플래시", false);
			config.set("일반 무기.실버팽", false);
			config.set("일반 무기.아마이마스크", false);
			config.set("일반 무기.아토믹", false);
			config.set("일반 무기.음속의소닉", false);
			config.set("일반 무기.제노스", false);
			config.set("일반 무기.좀비맨", false);
			config.set("일반 무기.초합금검은빛", false);
			config.set("일반 무기.킹", false);
			config.set("일반 무기.타츠마키", false);
			config.set("일반 무기.탱크톱마스터", false);
			config.set("일반 무기.프리즈너", false);
			config.set("일반 무기.후부키", false);
			config.set("일반 무기.보상 획득", false);
			
			config.set("풀강 무기.구동기사", false);
			config.set("풀강 무기.금속배트", false);
			config.set("풀강 무기.돈신", false);
			config.set("풀강 무기.동제", false);
			config.set("풀강 무기.메탈나이트", false);
			config.set("풀강 무기.번견맨", false);
			config.set("풀강 무기.섬광의플래시", false);
			config.set("풀강 무기.실버팽", false);
			config.set("풀강 무기.아마이마스크", false);
			config.set("풀강 무기.아토믹", false);
			config.set("풀강 무기.음속의소닉", false);
			config.set("풀강 무기.제노스", false);
			config.set("풀강 무기.좀비맨", false);
			config.set("풀강 무기.초합금검은빛", false);
			config.set("풀강 무기.킹", false);
			config.set("풀강 무기.타츠마키", false);
			config.set("풀강 무기.탱크톱마스터", false);
			config.set("풀강 무기.프리즈너", false);
			config.set("풀강 무기.후부키", false);
			config.set("풀강 무기.보상 획득", false);
			
			config.set("일반 장비.구동기사모자", false);
			config.set("일반 장비.금속배트모자", false);
			config.set("일반 장비.돈신모자", false);
			config.set("일반 장비.동제모자", false);
			config.set("일반 장비.메탈나이트모자", false);
			config.set("일반 장비.번견맨모자", false);
			config.set("일반 장비.섬광의플래시모자", false);
			config.set("일반 장비.실버팽모자", false);
			config.set("일반 장비.아마이마스크모자", false);
			config.set("일반 장비.아토믹모자", false);
			config.set("일반 장비.음속의소닉모자", false);
			config.set("일반 장비.제노스모자", false);
			config.set("일반 장비.좀비맨모자", false);
			config.set("일반 장비.초합금검은빛모자", false);
			config.set("일반 장비.킹모자", false);
			config.set("일반 장비.타츠마키모자", false);
			config.set("일반 장비.탱크톱마스터모자", false);
			config.set("일반 장비.프리즈너모자", false);
			config.set("일반 장비.후부키모자", false);
			config.set("일반 장비.구동기사튜닉", false);
			config.set("일반 장비.금속배트튜닉", false);
			config.set("일반 장비.돈신튜닉", false);
			config.set("일반 장비.동제튜닉", false);
			config.set("일반 장비.메탈나이트튜닉", false);
			config.set("일반 장비.번견맨튜닉", false);
			config.set("일반 장비.섬광의플래시튜닉", false);
			config.set("일반 장비.실버팽튜닉", false);
			config.set("일반 장비.아마이마스크튜닉", false);
			config.set("일반 장비.아토믹튜닉", false);
			config.set("일반 장비.음속의소닉튜닉", false);
			config.set("일반 장비.제노스튜닉", false);
			config.set("일반 장비.좀비맨튜닉", false);
			config.set("일반 장비.초합금검은빛튜닉", false);
			config.set("일반 장비.킹튜닉", false);
			config.set("일반 장비.타츠마키튜닉", false);
			config.set("일반 장비.탱크톱마스터튜닉", false);
			config.set("일반 장비.프리즈너튜닉", false);
			config.set("일반 장비.후부키튜닉", false);
			config.set("일반 장비.구동기사바지", false);
			config.set("일반 장비.금속배트바지", false);
			config.set("일반 장비.돈신바지", false);
			config.set("일반 장비.동제바지", false);
			config.set("일반 장비.메탈나이트바지", false);
			config.set("일반 장비.번견맨바지", false);
			config.set("일반 장비.섬광의플래시바지", false);
			config.set("일반 장비.실버팽바지", false);
			config.set("일반 장비.아마이마스크바지", false);
			config.set("일반 장비.아토믹바지", false);
			config.set("일반 장비.음속의소닉바지", false);
			config.set("일반 장비.제노스바지", false);
			config.set("일반 장비.좀비맨바지", false);
			config.set("일반 장비.초합금검은빛바지", false);
			config.set("일반 장비.킹바지", false);
			config.set("일반 장비.타츠마키바지", false);
			config.set("일반 장비.탱크톱마스터바지", false);
			config.set("일반 장비.프리즈너바지", false);
			config.set("일반 장비.후부키바지", false);
			config.set("일반 장비.구동기사신발", false);
			config.set("일반 장비.금속배트신발", false);
			config.set("일반 장비.돈신신발", false);
			config.set("일반 장비.동제신발", false);
			config.set("일반 장비.메탈나이트신발", false);
			config.set("일반 장비.번견맨신발", false);
			config.set("일반 장비.섬광의플래시신발", false);
			config.set("일반 장비.실버팽신발", false);
			config.set("일반 장비.아마이마스크신발", false);
			config.set("일반 장비.아토믹신발", false);
			config.set("일반 장비.음속의소닉신발", false);
			config.set("일반 장비.제노스신발", false);
			config.set("일반 장비.좀비맨신발", false);
			config.set("일반 장비.초합금검은빛신발", false);
			config.set("일반 장비.킹신발", false);
			config.set("일반 장비.타츠마키신발", false);
			config.set("일반 장비.탱크톱마스터신발", false);
			config.set("일반 장비.프리즈너신발", false);
			config.set("일반 장비.후부키신발", false);
			config.set("일반 장비.보상 획득", false);
			
			config.set("유니크 장비.구동기사모자", false);
			config.set("유니크 장비.금속배트모자", false);
			config.set("유니크 장비.돈신모자", false);
			config.set("유니크 장비.동제모자", false);
			config.set("유니크 장비.메탈나이트모자", false);
			config.set("유니크 장비.번견맨모자", false);
			config.set("유니크 장비.섬광의플래시모자", false);
			config.set("유니크 장비.실버팽모자", false);
			config.set("유니크 장비.아마이마스크모자", false);
			config.set("유니크 장비.아토믹모자", false);
			config.set("유니크 장비.음속의소닉모자", false);
			config.set("유니크 장비.제노스모자", false);
			config.set("유니크 장비.좀비맨모자", false);
			config.set("유니크 장비.초합금검은빛모자", false);
			config.set("유니크 장비.킹모자", false);
			config.set("유니크 장비.타츠마키모자", false);
			config.set("유니크 장비.탱크톱마스터모자", false);
			config.set("유니크 장비.프리즈너모자", false);
			config.set("유니크 장비.후부키모자", false);
			config.set("유니크 장비.구동기사튜닉", false);
			config.set("유니크 장비.금속배트튜닉", false);
			config.set("유니크 장비.돈신튜닉", false);
			config.set("유니크 장비.동제튜닉", false);
			config.set("유니크 장비.메탈나이트튜닉", false);
			config.set("유니크 장비.번견맨튜닉", false);
			config.set("유니크 장비.섬광의플래시튜닉", false);
			config.set("유니크 장비.실버팽튜닉", false);
			config.set("유니크 장비.아마이마스크튜닉", false);
			config.set("유니크 장비.아토믹튜닉", false);
			config.set("유니크 장비.음속의소닉튜닉", false);
			config.set("유니크 장비.제노스튜닉", false);
			config.set("유니크 장비.좀비맨튜닉", false);
			config.set("유니크 장비.초합금검은빛튜닉", false);
			config.set("유니크 장비.킹튜닉", false);
			config.set("유니크 장비.타츠마키튜닉", false);
			config.set("유니크 장비.탱크톱마스터튜닉", false);
			config.set("유니크 장비.프리즈너튜닉", false);
			config.set("유니크 장비.후부키튜닉", false);
			config.set("유니크 장비.구동기사바지", false);
			config.set("유니크 장비.금속배트바지", false);
			config.set("유니크 장비.돈신바지", false);
			config.set("유니크 장비.동제바지", false);
			config.set("유니크 장비.메탈나이트바지", false);
			config.set("유니크 장비.번견맨바지", false);
			config.set("유니크 장비.섬광의플래시바지", false);
			config.set("유니크 장비.실버팽바지", false);
			config.set("유니크 장비.아마이마스크바지", false);
			config.set("유니크 장비.아토믹바지", false);
			config.set("유니크 장비.음속의소닉바지", false);
			config.set("유니크 장비.제노스바지", false);
			config.set("유니크 장비.좀비맨바지", false);
			config.set("유니크 장비.초합금검은빛바지", false);
			config.set("유니크 장비.킹바지", false);
			config.set("유니크 장비.타츠마키바지", false);
			config.set("유니크 장비.탱크톱마스터바지", false);
			config.set("유니크 장비.프리즈너바지", false);
			config.set("유니크 장비.후부키바지", false);
			config.set("유니크 장비.구동기사신발", false);
			config.set("유니크 장비.금속배트신발", false);
			config.set("유니크 장비.돈신신발", false);
			config.set("유니크 장비.동제신발", false);
			config.set("유니크 장비.메탈나이트신발", false);
			config.set("유니크 장비.번견맨신발", false);
			config.set("유니크 장비.섬광의플래시신발", false);
			config.set("유니크 장비.실버팽신발", false);
			config.set("유니크 장비.아마이마스크신발", false);
			config.set("유니크 장비.아토믹신발", false);
			config.set("유니크 장비.음속의소닉신발", false);
			config.set("유니크 장비.제노스신발", false);
			config.set("유니크 장비.좀비맨신발", false);
			config.set("유니크 장비.초합금검은빛신발", false);
			config.set("유니크 장비.킹신발", false);
			config.set("유니크 장비.타츠마키신발", false);
			config.set("유니크 장비.탱크톱마스터신발", false);
			config.set("유니크 장비.프리즈너신발", false);
			config.set("유니크 장비.후부키신발", false);
			config.set("유니크 장비.보상 획득", false);
			
			config.set("일반 룬.불속성", false);
			config.set("일반 룬.바람속성", false);
			config.set("일반 룬.치유속성", false);
			config.set("일반 룬.어둠속성", false);
			config.set("일반 룬.독속성", false);
			config.set("일반 룬.부패속성", false);
			config.set("일반 룬.얼음속성", false);
			config.set("일반 룬.보상 획득", false);
			
			config.set("풀강 룬.불속성", false);
			config.set("풀강 룬.바람속성", false);
			config.set("풀강 룬.치유속성", false);
			config.set("풀강 룬.어둠속성", false);
			config.set("풀강 룬.독속성", false);
			config.set("풀강 룬.부패속성", false);
			config.set("풀강 룬.얼음속성", false);
			config.set("풀강 룬.보상 획득", false);
			
			config.set("도구.보통0", false);
			config.set("도구.보통1", false);
			config.set("도구.보통2", false);
			config.set("도구.보통3", false);
			config.set("도구.보통4", false);
			config.set("도구.보통5", false);
			config.set("도구.보통6", false);
			config.set("도구.보통7", false);
			config.set("도구.보통8", false);
			config.set("도구.보통9", false);
			config.set("도구.보통10", false);
			config.set("도구.진심0", false);
			config.set("도구.진심1", false);
			config.set("도구.진심2", false);
			config.set("도구.진심3", false);
			config.set("도구.진심4", false);
			config.set("도구.진심5", false);
			config.set("도구.진심6", false);
			config.set("도구.진심7", false);
			config.set("도구.진심8", false);
			config.set("도구.진심9", false);
			config.set("도구.진심10", false);
			config.set("도구.마스터리", false);
			config.set("도구.보상 획득", false);
			
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchBook] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		if (getString.equalsIgnoreCase("NONE")) return null;
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchBook] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchBook] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, String amount) {
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchBook] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
