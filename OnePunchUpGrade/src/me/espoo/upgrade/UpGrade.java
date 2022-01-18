package me.espoo.upgrade;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class UpGrade {
	public static void CreateUpGradeConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			
            List<String> Alist1;
            Alist1 = config.getStringList("1.아이템 설명");
            Alist1.add("&7&l==============");
            Alist1.add("&f강화가 안된 보통");
            Alist1.add("&f곡괭이다. 평범하게");
            Alist1.add("&f채광을 시도해보자.");
            Alist1.add("&7&l==============");
            config.set("1.아이템 재료", "돌");
            config.set("1.재료 수량", 32);
            config.set("1.아이템 이름", "&e보통 곡괭이");
            config.set("1.아이템 설명", Alist1);
            config.set("1.아이템 코드", 257);
            config.set("1.아이템 데이터코드", 0);
            config.set("1.효율", 1);
            
            List<String> Alist2;
            Alist2 = config.getStringList("2.아이템 설명");
            Alist2.add("&7&l==============");
            Alist2.add("&f강화가 +1된 보통");
            Alist2.add("&f곡괭이다. 평범하게");
            Alist2.add("&f채광을 시도해보자.");
            Alist2.add("&7&l==============");
            config.set("2.아이템 재료", "돌");
            config.set("2.재료 수량", 64);
            config.set("2.아이템 이름", "&e보통 곡괭이 &f+1");
            config.set("2.아이템 설명", Alist2);
            config.set("2.아이템 코드", 257);
            config.set("2.아이템 데이터코드", 0);
            config.set("2.효율", 2);
            
            List<String> Alist3;
            Alist3 = config.getStringList("3.아이템 설명");
            Alist3.add("&7&l==============");
            Alist3.add("&f강화가 +2된 보통");
            Alist3.add("&f곡괭이다. 평범하게");
            Alist3.add("&f채광을 시도해보자.");
            Alist3.add("&7&l==============");
            config.set("3.아이템 재료", "석탄");
            config.set("3.재료 수량", 64);
            config.set("3.아이템 이름", "&e보통 곡괭이 &f+2");
            config.set("3.아이템 설명", Alist3);
            config.set("3.아이템 코드", 257);
            config.set("3.아이템 데이터코드", 0);
            config.set("3.효율", 2);
            config.set("3.행운", 1);
            
            List<String> Alist4;
            Alist4 = config.getStringList("4.아이템 설명");
            Alist4.add("&7&l==============");
            Alist4.add("&f강화가 +3된 보통");
            Alist4.add("&f곡괭이다. 평범하게");
            Alist4.add("&f채광을 시도해보자.");
            Alist4.add("&7&l==============");
            config.set("4.아이템 재료", "석탄 블록");
            config.set("4.재료 수량", 12);
            config.set("4.아이템 이름", "&e보통 곡괭이 &f+3");
            config.set("4.아이템 설명", Alist4);
            config.set("4.아이템 코드", 257);
            config.set("4.아이템 데이터코드", 0);
            config.set("4.효율", 3);
            config.set("4.행운", 1);
            
            List<String> Alist5;
            Alist5 = config.getStringList("5.아이템 설명");
            Alist5.add("&7&l==============");
            Alist5.add("&f강화가 +4된 보통");
            Alist5.add("&f곡괭이다. 평범하게");
            Alist5.add("&f채광을 시도해보자.");
            Alist5.add("&7&l==============");
            config.set("5.아이템 재료", "철 블록");
            config.set("5.재료 수량", 12);
            config.set("5.아이템 이름", "&e보통 곡괭이 &f+4");
            config.set("5.아이템 설명", Alist5);
            config.set("5.아이템 코드", 257);
            config.set("5.아이템 데이터코드", 0);
            config.set("5.효율", 3);
            config.set("5.행운", 2);
            
            List<String> Alist6;
            Alist6 = config.getStringList("6.아이템 설명");
            Alist6.add("&7&l==============");
            Alist6.add("&f강화가 +5된 보통");
            Alist6.add("&f곡괭이다. 평범하게");
            Alist6.add("&f채광을 시도해보자.");
            Alist6.add("&7&l==============");
            config.set("6.아이템 재료", "철 블록");
            config.set("6.재료 수량", 36);
            config.set("6.아이템 이름", "&e보통 곡괭이 &f+5");
            config.set("6.아이템 설명", Alist6);
            config.set("6.아이템 코드", 257);
            config.set("6.아이템 데이터코드", 0);
            config.set("6.효율", 4);
            config.set("6.행운", 2);
            
            List<String> Alist7;
            Alist7 = config.getStringList("7.아이템 설명");
            Alist7.add("&7&l==============");
            Alist7.add("&f강화가 +6된 보통");
            Alist7.add("&f곡괭이다. 평범하게");
            Alist7.add("&f채광을 시도해보자.");
            Alist7.add("&7&l==============");
            config.set("7.아이템 재료", "금 블록");
            config.set("7.재료 수량", 48);
            config.set("7.아이템 이름", "&e보통 곡괭이 &f+6");
            config.set("7.아이템 설명", Alist7);
            config.set("7.아이템 코드", 257);
            config.set("7.아이템 데이터코드", 0);
            config.set("7.효율", 4);
            config.set("7.행운", 3);
            
            List<String> Alist8;
            Alist8 = config.getStringList("8.아이템 설명");
            Alist8.add("&7&l==============");
            Alist8.add("&f강화가 +7된 보통");
            Alist8.add("&f곡괭이다. 평범하게");
            Alist8.add("&f채광을 시도해보자.");
            Alist8.add("&7&l==============");
            config.set("8.아이템 재료", "금 블록");
            config.set("8.재료 수량", 64);
            config.set("8.아이템 이름", "&e보통 곡괭이 &f+7");
            config.set("8.아이템 설명", Alist8);
            config.set("8.아이템 코드", 257);
            config.set("8.아이템 데이터코드", 0);
            config.set("8.효율", 5);
            config.set("8.행운", 3);
            
            List<String> Alist9;
            Alist9 = config.getStringList("9.아이템 설명");
            Alist9.add("&7&l==============");
            Alist9.add("&f강화가 +8된 보통");
            Alist9.add("&f곡괭이다. 평범하게");
            Alist9.add("&f채광을 시도해보자.");
            Alist9.add("&7&l==============");
            config.set("9.아이템 재료", "철 압축 블록");
            config.set("9.재료 수량", 8);
            config.set("9.아이템 이름", "&e보통 곡괭이 &f+8");
            config.set("9.아이템 설명", Alist9);
            config.set("9.아이템 코드", 257);
            config.set("9.아이템 데이터코드", 0);
            config.set("9.효율", 5);
            config.set("9.행운", 4);
            
            List<String> Alist10;
            Alist10 = config.getStringList("10.아이템 설명");
            Alist10.add("&7&l==============");
            Alist10.add("&f강화가 +9된 보통");
            Alist10.add("&f곡괭이다. 평범하게");
            Alist10.add("&f채광을 시도해보자.");
            Alist10.add("&7&l==============");
            config.set("10.아이템 재료", "철 압축 블록");
            config.set("10.재료 수량", 12);
            config.set("10.아이템 이름", "&e보통 곡괭이 &f+9");
            config.set("10.아이템 설명", Alist10);
            config.set("10.아이템 코드", 257);
            config.set("10.아이템 데이터코드", 0);
            config.set("10.효율", 6);
            config.set("10.행운", 4);
            
            List<String> Alist11;
            Alist11 = config.getStringList("11.아이템 설명");
            Alist11.add("&7&l==============");
            Alist11.add("&f강화가 +10된 보통");
            Alist11.add("&f곡괭이다. 평범하게");
            Alist11.add("&f채광을 시도해보자.");
            Alist11.add("&7&l==============");
            config.set("11.아이템 재료", "금 압축 블록");
            config.set("11.재료 수량", 16);
            config.set("11.아이템 이름", "&e보통 곡괭이 &f+10");
            config.set("11.아이템 설명", Alist11);
            config.set("11.아이템 코드", 257);
            config.set("11.아이템 데이터코드", 0);
            config.set("11.효율", 6);
            config.set("11.행운", 5);
            
            List<String> Blist1;
            Blist1 = config.getStringList("12.아이템 설명");
            Blist1.add("&7&l==============");
            Blist1.add("&f강화가 안된 원펀");
            Blist1.add("&f곡괭이다. 진심으로");
            Blist1.add("&f채광을 시도해보자.");
            Blist1.add("&7&l==============");
            config.set("12.아이템 재료", "금 압축 블록");
            config.set("12.재료 수량", 20);
            config.set("12.아이템 이름", "&6진심 곡괭이");
            config.set("12.아이템 설명", Blist1);
            config.set("12.아이템 코드", 278);
            config.set("12.아이템 데이터코드", 0);
            config.set("12.효율", 7);
            config.set("12.행운", 5);
            
            List<String> Blist2;
            Blist2 = config.getStringList("13.아이템 설명");
            Blist2.add("&7&l==============");
            Blist2.add("&f강화가 +1된 원펀");
            Blist2.add("&f곡괭이다. 진심으로");
            Blist2.add("&f채광을 시도해보자.");
            Blist2.add("&7&l==============");
            config.set("13.아이템 재료", "청금석 압축 블록");
            config.set("13.재료 수량", 24);
            config.set("13.아이템 이름", "&6진심 곡괭이 &f+1");
            config.set("13.아이템 설명", Blist2);
            config.set("13.아이템 코드", 278);
            config.set("13.아이템 데이터코드", 0);
            config.set("13.효율", 8);
            config.set("13.행운", 5);
            
            List<String> Blist3;
            Blist3 = config.getStringList("14.아이템 설명");
            Blist3.add("&7&l==============");
            Blist3.add("&f강화가 +2된 원펀");
            Blist3.add("&f곡괭이다. 진심으로");
            Blist3.add("&f채광을 시도해보자.");
            Blist3.add("&7&l==============");
            config.set("14.아이템 재료", "청금석 압축 블록");
            config.set("14.재료 수량", 28);
            config.set("14.아이템 이름", "&6진심 곡괭이 &f+2");
            config.set("14.아이템 설명", Blist3);
            config.set("14.아이템 코드", 278);
            config.set("14.아이템 데이터코드", 0);
            config.set("14.효율", 9);
            config.set("14.행운", 5);
            
            List<String> Blist4;
            Blist4 = config.getStringList("15.아이템 설명");
            Blist4.add("&7&l==============");
            Blist4.add("&f강화가 +3된 원펀");
            Blist4.add("&f곡괭이다. 진심으로");
            Blist4.add("&f채광을 시도해보자.");
            Blist4.add("&7&l==============");
            config.set("15.아이템 재료", "레드스톤 압축 블록");
            config.set("15.재료 수량", 32);
            config.set("15.아이템 이름", "&6진심 곡괭이 &f+3");
            config.set("15.아이템 설명", Blist4);
            config.set("15.아이템 코드", 278);
            config.set("15.아이템 데이터코드", 0);
            config.set("15.효율", 10);
            config.set("15.행운", 5);
            
            List<String> Blist5;
            Blist5 = config.getStringList("16.아이템 설명");
            Blist5.add("&7&l==============");
            Blist5.add("&f강화가 +4된 원펀");
            Blist5.add("&f곡괭이다. 진심으로");
            Blist5.add("&f채광을 시도해보자.");
            Blist5.add("&7&l==============");
            config.set("16.아이템 재료", "레드스톤 압축 블록");
            config.set("16.재료 수량", 36);
            config.set("16.아이템 이름", "&6진심 곡괭이 &f+4");
            config.set("16.아이템 설명", Blist5);
            config.set("16.아이템 코드", 278);
            config.set("16.아이템 데이터코드", 0);
            config.set("16.효율", 11);
            config.set("16.행운", 5);
            
            List<String> Blist6;
            Blist6 = config.getStringList("17.아이템 설명");
            Blist6.add("&7&l==============");
            Blist6.add("&f강화가 +5된 원펀");
            Blist6.add("&f곡괭이다. 진심으로");
            Blist6.add("&f채광을 시도해보자.");
            Blist6.add("&7&l==============");
            config.set("17.아이템 재료", "네더 석영 압축 블록");
            config.set("17.재료 수량", 40);
            config.set("17.아이템 이름", "&6진심 곡괭이 &f+5");
            config.set("17.아이템 설명", Blist6);
            config.set("17.아이템 코드", 278);
            config.set("17.아이템 데이터코드", 0);
            config.set("17.효율", 12);
            config.set("17.행운", 5);

            List<String> Blist7;
            Blist7 = config.getStringList("18.아이템 설명");
            Blist7.add("&7&l==============");
            Blist7.add("&f강화가 +6된 원펀");
            Blist7.add("&f곡괭이다. 진심으로");
            Blist7.add("&f채광을 시도해보자.");
            Blist7.add("&7&l==============");
            config.set("18.아이템 재료", "네더 석영 압축 블록");
            config.set("18.재료 수량", 44);
            config.set("18.아이템 이름", "&6진심 곡괭이 &f+6");
            config.set("18.아이템 설명", Blist7);
            config.set("18.아이템 코드", 278);
            config.set("18.아이템 데이터코드", 0);
            config.set("18.효율", 12);
            config.set("18.행운", 6);
            
            List<String> Blist8;
            Blist8 = config.getStringList("19.아이템 설명");
            Blist8.add("&7&l==============");
            Blist8.add("&f강화가 +7된 원펀");
            Blist8.add("&f곡괭이다. 진심으로");
            Blist8.add("&f채광을 시도해보자.");
            Blist8.add("&7&l==============");
            config.set("19.아이템 재료", "다이아몬드 압축 블록");
            config.set("19.재료 수량", 48);
            config.set("19.아이템 이름", "&6진심 곡괭이 &f+7");
            config.set("19.아이템 설명", Blist8);
            config.set("19.아이템 코드", 278);
            config.set("19.아이템 데이터코드", 0);
            config.set("19.효율", 13);
            config.set("19.행운", 6);
            
            List<String> Blist9;
            Blist9 = config.getStringList("20.아이템 설명");
            Blist9.add("&7&l==============");
            Blist9.add("&f강화가 +8된 원펀");
            Blist9.add("&f곡괭이다. 진심으로");
            Blist9.add("&f채광을 시도해보자.");
            Blist9.add("&7&l==============");
            config.set("20.아이템 재료", "다이아몬드 압축 블록");
            config.set("20.재료 수량", 52);
            config.set("20.아이템 이름", "&6진심 곡괭이 &f+8");
            config.set("20.아이템 설명", Blist9);
            config.set("20.아이템 코드", 278);
            config.set("20.아이템 데이터코드", 0);
            config.set("20.효율", 14);
            config.set("20.행운", 6);
            
            List<String> Blist10;
            Blist10 = config.getStringList("21.아이템 설명");
            Blist10.add("&7&l==============");
            Blist10.add("&f강화가 +9된 원펀");
            Blist10.add("&f곡괭이다. 진심으로");
            Blist10.add("&f채광을 시도해보자.");
            Blist10.add("&7&l==============");
            config.set("21.아이템 재료", "에메랄드 압축 블록");
            config.set("21.재료 수량", 58);
            config.set("21.아이템 이름", "&6진심 곡괭이 &f+9");
            config.set("21.아이템 설명", Blist10);
            config.set("21.아이템 코드", 278);
            config.set("21.아이템 데이터코드", 0);
            config.set("21.효율", 15);
            config.set("21.행운", 6);
            
            List<String> Blist11;
            Blist11 = config.getStringList("22.아이템 설명");
            Blist11.add("&7&l==============");
            Blist11.add("&f강화가 +10된 원펀");
            Blist11.add("&f곡괭이다. 진심으로");
            Blist11.add("&f채광을 시도해보자.");
            Blist11.add("&7&l==============");
            config.set("22.아이템 재료", "에메랄드 압축 블록");
            config.set("22.재료 수량", 64);
            config.set("22.아이템 이름", "&6진심 곡괭이 &f+10");
            config.set("22.아이템 설명", Blist11);
            config.set("22.아이템 코드", 278);
            config.set("22.아이템 데이터코드", 0);
            config.set("22.효율", 15);
            config.set("22.행운", 7);
            
            List<String> Clist1;
            Clist1 = config.getStringList("23.아이템 설명");
            Clist1.add("&7&l==============");
            Clist1.add("&f강화가 안된 최고");
            Clist1.add("&f곡괭이다. 여유있게");
            Clist1.add("&f채광을 마무리하자.");
            Clist1.add("&7&l==============");
            config.set("23.아이템 재료", "없음");
            config.set("23.재료 수량", 0);
            config.set("23.아이템 이름", "&b마스터리 곡괭이");
            config.set("23.아이템 설명", Clist1);
            config.set("23.아이템 코드", 285);
            config.set("23.아이템 데이터코드", 0);
            config.set("23.효율", 16);
            config.set("23.행운", 7);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static List<String> getUpGradeList(String name) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateUpGradeConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		Returnlist.add(str);
        }
		return Returnlist;
	}
	
	public static Boolean getUpGradeBoolean(String name) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateUpGradeConfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getUpGradeInt(String name) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateUpGradeConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return -1;
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getUpGradeString(String name) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateUpGradeConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = Method.replaceAllColors(config.getString(name));
		return getString;
	}
	
	public static String getUpGradeOrString(String name) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateUpGradeConfig(f, folder, config);
		}
		
		if (config.getString(name) == null)
			return null;
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setUpGradeBoolean(String name, Boolean amount) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateUpGradeConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setUpGradeInt(String name, int amount) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateUpGradeConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setUpGradeString(String name, String amount) {
		File f = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateUpGradeConfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
