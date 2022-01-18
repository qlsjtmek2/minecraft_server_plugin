package me.espoo.upgrade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIMessage {
	public static List<String> Water = new ArrayList<String>();
	public static List<String> Lava = new ArrayList<String>();
	public static List<String> AnvilX = new ArrayList<String>();
	public static List<String> BookOne = new ArrayList<String>();
	public static List<String> BookTwo = new ArrayList<String>();
	public static List<String> BookThree = new ArrayList<String>();
	public static List<String> BookFour = new ArrayList<String>();
	
	public static void setWater()
	{
		Water.add("§f이곳에 강화할 §b§l도구§f를");
		Water.add("§f올려주시기 바랍니다.");
	}
	
	public static void setLava()
	{
        Lava.add("§f이곳에 소모할 §a§l재료§f를");
        Lava.add("§f올려주시기 바랍니다.");
	}
	
	public static void setAnvilX()
	{
		AnvilX.add("§7§l===========================");
		AnvilX.add("§6 강화할 장비 §7:: §f없음");
		AnvilX.add("§e 강화후 장비 §7:: §f없음");
		AnvilX.add("§a 필요한 재료 §7:: §f없음");
		AnvilX.add("§b 재료 수량 §7:: §f0개");
		AnvilX.add("§7§l===========================");
		AnvilX.add("§7- §4강화 불가능 상태");
	}
	
	public static void setBookOne()
	{
		BookOne.add("§7§l==================");
		BookOne.add("§61. §f강화할 도구를 집고 물 안에 둔다.");
		BookOne.add("§62. §f모루 아이템 설명을 확인해서 어떤 ");
		BookOne.add("§f   재료를 몇개 구해야 하는지 확인한다.");
		BookOne.add("§63. §f재료를 구해서 적당량의 ");
		BookOne.add("§f    재료을 용암 안에 둔다.");
		BookOne.add("§64. §f모루를 클릭하고 도구를 강화한다.");
		BookOne.add("§7§l==================");
	}
	
	public static void setBookTwo()
	{
		BookTwo.add("§7§l==================");
		BookTwo.add("§f도구 강화는 §e보통 곡괭이§f, §6진심 ");
		BookTwo.add("§6곡괭이§f밖에 불가능합니다.");
		BookTwo.add("§c무기 강화§f는 따로 있습니다. ");
		BookTwo.add("§a도구 강화§f와 별개의 ");
		BookTwo.add("§f강화 시스템입니다.");
		BookTwo.add("§f장비는 강화가 불가능합니다. ");
		BookTwo.add("§f오직 §b감정§f만 가능합니다.");
		BookTwo.add("§7§l==================");
	}
	
	public static void setBookThree()
	{
        BookThree.add("§7§l==================");
        BookThree.add("§6강화 가능한 아이템 목록");
        BookThree.add("§e보통 곡괭이 §f-> ");
        BookThree.add("§e보통 곡괭이 §f+1 ->");
        BookThree.add("§e보통 곡괭이 §f+2 -> ");
        BookThree.add("§e보통 곡괭이 §f+3 ->");
        BookThree.add("§e보통 곡괭이 §f+4 ->");
        BookThree.add("§e보통 곡괭이 §f+5 -> ");
        BookThree.add("§e보통 곡괭이 §f+6 ->");
        BookThree.add("§e보통 곡괭이 §f+7 ->");
        BookThree.add("§e보통 곡괭이 §f+8 -> ");
        BookThree.add("§e보통 곡괭이 §f+9 ->");
        BookThree.add("§e보통 곡괭이 §f+10 ->");
        BookThree.add("§7§l==================");
	}
	
	public static void setBookFour()
	{
        BookFour.add("§7§l==================");
        BookFour.add("§6진심 곡괭이 §f-> ");
        BookFour.add("§6진심 곡괭이 §f+1 ->");
        BookFour.add("§6진심 곡괭이 §f+2 -> ");
        BookFour.add("§6진심 곡괭이 §f+3 ->");
        BookFour.add("§6진심 곡괭이 §f+4 ->");
        BookFour.add("§6진심 곡괭이 §f+5 -> ");
        BookFour.add("§6진심 곡괭이 §f+6 ->");
        BookFour.add("§6진심 곡괭이 §f+7 ->");
        BookFour.add("§6진심 곡괭이 §f+8 -> ");
        BookFour.add("§6진심 곡괭이 §f+9 ->");
        BookFour.add("§6진심 곡괭이 §f+10 ->");
        BookFour.add("§b마스터리 곡괭이");
        BookFour.add("§7§l==================");
	}
	
	public static void CreateGUIMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("도구 강화 GUI 이름", "도구 강화");
            config.set("강화 가능", "&a강화 가능 상태 &7(아이템 클릭시 강화 진행)");
            config.set("강화 불가능", "&4강화 불가능 상태");
            config.set("존재하지 않음", "&f존재하지 않음");
            
            List<String> Alist;
            Alist = config.getStringList("물.아이템 설명");
            Alist.add("&f이곳에 강화할 &b&l도구&f를");
            Alist.add("&f올려주시기 바랍니다.");
            config.set("물.아이템 색깔", "§c§l");
            config.set("물.아이템 이름", "↘,↓,↙,←,↖,↑,↗,→");
            config.set("물.아이템 설명", Alist);
            config.set("물.아이템 코드", 8);
            config.set("물.아이템 데이터코드", 0);
            config.set("물.아이템 수량", 1);
            
            List<String> Blist;
            Blist = config.getStringList("용암.아이템 설명");
            Blist.add("&f이곳에 소모할 &a&l재료&f를");
            Blist.add("&f올려주시기 바랍니다.");
            config.set("용암.아이템 색깔", "§c§l");
            config.set("용암.아이템 이름", "↘,↓,↙,←,↖,↑,↗,→");
            config.set("용암.아이템 설명", Blist);
            config.set("용암.아이템 코드", 10);
            config.set("용암.아이템 데이터코드", 0);
            config.set("용암.아이템 수량", 1);
            
            config.set("+.아이템 이름", "&f&l+");
            config.set("+.아이템 코드", 101);
            config.set("+.아이템 데이터코드", 0);
            config.set("+.아이템 수량", 1);
            
       		List<String> list0;
            list0 = config.getStringList("모루X.아이템 설명");
            list0.add("&7&l===========================");
            list0.add("&6 강화할 장비 &7:: &f없음");
            list0.add("&e 강화후 장비 &7:: &f없음");
            list0.add("&a 필요한 재료 &7:: &f없음");
            list0.add("&b 재료 수량 &7:: &f0개");
            list0.add("&7&l===========================");
            list0.add("&7- &4강화 불가능 상태");
            config.set("모루X.아이템 이름", "&f[ &6강화 하기 &f]");
            config.set("모루X.아이템 설명", list0);
            config.set("모루X.아이템 코드", 145);
            config.set("모루X.아이템 데이터코드", 0);
            config.set("모루X.아이템 수량", 1);
            
       		List<String> list1;
            list1 = config.getStringList("모루.아이템 설명");
            list1.add("&7&l===========================");
            list1.add("&6 강화할 장비 &7:: &f<itemname>");
            list1.add("&e 강화후 장비 &7:: &f<nextitem>");
            list1.add("&a 필요한 재료 &7:: &f<material>");
            list1.add("&b 재료 수량 &7:: &f<num>개");
            list1.add("&7&l===========================");
            list1.add("&7- <YESorNO>");
            config.set("모루.아이템 이름", "&f[ &6강화 하기 &f]");
            config.set("모루.아이템 설명", list1);
            config.set("모루.아이템 코드", 145);
            config.set("모루.아이템 데이터코드", 0);
            config.set("모루.아이템 수량", 1);

     		List<String> list2;
            list2 = config.getStringList("도움말1.아이템 설명");
            list2.add("&7&l==================");
            list2.add("&61. &f강화할 도구를 집고 물 안에 둔다.");
            list2.add("&62. &f모루 아이템 설명을 확인해서 어떤 ");
            list2.add("&f   재료를 몇개 구해야 하는지 확인한다.");
            list2.add("&63. &f재료를 구해서 적당량의 ");
            list2.add("&f    재료을 용암 안에 둔다.");
            list2.add("&64. &f모루를 클릭하고 도구를 강화한다.");
            list2.add("&7&l==================");
            config.set("도움말1.아이템 이름", "&e도구 &6강화 &cTip 1.");
            config.set("도움말1.아이템 설명", list2);
            config.set("도움말1.아이템 코드", 386);
            config.set("도움말1.아이템 데이터코드", 0);
            config.set("도움말1.아이템 수량", 1);
            
     		List<String> list3;
            list3 = config.getStringList("도움말2.아이템 설명");
            list3.add("&7&l==================");
            list3.add("&f도구 강화는 &e보통 곡괭이&f, &6진심 ");
            list3.add("&6곡괭이&f밖에 불가능합니다.");
            list3.add("&c무기 강화&f는 따로 있습니다. ");
            list3.add("&a도구 강화&f와 별개의 ");
            list3.add("&f강화 시스템입니다.");
            list3.add("&f장비는 강화가 불가능합니다. ");
            list3.add("&f오직 &b감정&f만 가능합니다.");
            list3.add("&7&l==================");
            config.set("도움말2.아이템 이름", "&e도구 &6강화 &cTip 2.");
            config.set("도움말2.아이템 설명", list3);
            config.set("도움말2.아이템 코드", 386);
            config.set("도움말2.아이템 데이터코드", 0);
            config.set("도움말2.아이템 수량", 1);
            
     		List<String> list4;
            list4 = config.getStringList("도움말3.아이템 설명");
            list4.add("&7&l==================");
            list4.add("&6강화 가능한 아이템 목록");
            list4.add("&e보통 곡괭이 &f-> ");
            list4.add("&e보통 곡괭이 &f+1 ->");
            list4.add("&e보통 곡괭이 &f+2 -> ");
            list4.add("&e보통 곡괭이 &f+3 ->");
            list4.add("&e보통 곡괭이 &f+4 ->");
            list4.add("&e보통 곡괭이 &f+5 -> ");
            list4.add("&e보통 곡괭이 &f+6 ->");
            list4.add("&e보통 곡괭이 &f+7 ->");
            list4.add("&e보통 곡괭이 &f+8 -> ");
            list4.add("&e보통 곡괭이 &f+9 ->");
            list4.add("&e보통 곡괭이 &f+10 ->");
            list4.add("&7&l==================");
            config.set("도움말3.아이템 이름", "&e도구 &6강화 &cTip 3.");
            config.set("도움말3.아이템 설명", list4);
            config.set("도움말3.아이템 코드", 386);
            config.set("도움말3.아이템 데이터코드", 0);
            config.set("도움말3.아이템 수량", 1);
            
     		List<String> list5;
            list5 = config.getStringList("도움말4.아이템 설명");
            list5.add("&7&l==================");
            list5.add("&6진심 곡괭이 &f-> ");
            list5.add("&6진심 곡괭이 &f+1 ->");
            list5.add("&6진심 곡괭이 &f+2 -> ");
            list5.add("&6진심 곡괭이 &f+3 ->");
            list5.add("&6진심 곡괭이 &f+4 ->");
            list5.add("&6진심 곡괭이 &f+5 -> ");
            list5.add("&6진심 곡괭이 &f+6 ->");
            list5.add("&6진심 곡괭이 &f+7 ->");
            list5.add("&6진심 곡괭이 &f+8 -> ");
            list5.add("&6진심 곡괭이 &f+9 ->");
            list5.add("&6진심 곡괭이 &f+10 ->");
            list5.add("&b마스터리 곡괭이");
            list5.add("&7&l==================");
            config.set("도움말4.아이템 이름", "&e도구 &6강화 &cTip 4.");
            config.set("도움말4.아이템 설명", list5);
            config.set("도움말4.아이템 코드", 386);
            config.set("도움말4.아이템 데이터코드", 0);
            config.set("도움말4.아이템 수량", 1);

			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchUpGrade] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getMessage(Player p, String name) {
		File f = new File("plugins/OnePunchUpGrade/GUIMessage.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		String Message = config.getString(name);
		Message = Method.replaceAllColors(Message);
		return Message;
	}
	
	public static String getOrMessage(Player p, String name) {
		File f = new File("plugins/OnePunchUpGrade/GUIMessage.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		String Message = config.getString(name);
		return Message;
	}
	
	public static int getItemCode(String name) {
		File f = new File("plugins/OnePunchUpGrade/GUIMessage.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		int Code = config.getInt(name);
		return Code;
	}
	
	public static List<String> getNullLoreList(Player p, String name) {
		File f = new File("plugins/OnePunchUpGrade/GUIMessage.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static List<String> getLoreList(Player p, String name) {
		File f = new File("plugins/OnePunchUpGrade/GUIMessage.yml");
		File folder = new File("plugins/OnePunchUpGrade");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		if (p.getOpenInventory().getItem(10) != null) {
    			if (p.getOpenInventory().getItem(10).getItemMeta() != null) {
    				if (p.getOpenInventory().getItem(10).getItemMeta().getDisplayName() != null) {
                 		str = str.replaceAll("<itemname>", ChatColor.stripColor(p.getOpenInventory().getItem(10).getItemMeta().getDisplayName()));
    				} else str = str.replaceAll("<itemname>", "없음");
    			} else str = str.replaceAll("<itemname>", "없음");
    		} else str = str.replaceAll("<itemname>", "없음");
    		
    		if (p.getOpenInventory().getItem(16) != null) {
    			for (int i = 1; ; i++) {
    				if (ChatColor.stripColor(UpGrade.getUpGradeString(i + ".아이템 이름")) == null) {
    	         		str = str.replaceAll("<nextitem>", "없음");
    	         		str = str.replaceAll("<material>", "없음");
    	         		str = str.replaceAll("<num>", "0");
    	          		str = str.replace("<YESorNO>", getMessage(p, "강화 불가능"));
    					break;
    				}
    				
    				if (p.getOpenInventory().getItem(10) != null) {
    					if (p.getOpenInventory().getItem(10).getItemMeta() != null) {
    						if (p.getOpenInventory().getItem(10).getItemMeta().getDisplayName() != null) {
        						if (ChatColor.stripColor(p.getOpenInventory().getItem(10).getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(UpGrade.getUpGradeString(i + ".아이템 이름")))) {
        							int z = i + 1;
        							if (ChatColor.stripColor(UpGrade.getUpGradeString(z + ".아이템 이름")) != null) str = str.replaceAll("<nextitem>", ChatColor.stripColor(UpGrade.getUpGradeString(z + ".아이템 이름")));
        							else str = str.replaceAll("<nextitem>", getMessage(p, "존재하지 않음"));
            						str = str.replaceAll("<material>", UpGrade.getUpGradeString(i + ".아이템 재료"));
            						
            						if (p.getOpenInventory().getItem(16) == null || p.getOpenInventory().getItem(16).getType() == Material.AIR) {
            							str = str.replaceAll("<num>", UpGrade.getUpGradeString(i + ".재료 수량"));
            							str = str.replace("<YESorNO>", getMessage(p, "강화 불가능"));
                						break;
            						} else {
            							if (p.getOpenInventory().getItem(16).getType() == Material.DIAMOND && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("다이아몬드")
            							 || p.getOpenInventory().getItem(16).getType() == Material.IRON_INGOT && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("철")
            							 || p.getOpenInventory().getItem(16).getType() == Material.GOLD_INGOT && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("금")
            							 || p.getOpenInventory().getItem(16).getType() == Material.COAL && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("석탄")
            							 || p.getOpenInventory().getItem(16).getType() == Material.EMERALD && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("에메랄드")
            							 || p.getOpenInventory().getItem(16).getType() == Material.REDSTONE && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("레드스톤")
            							 || p.getOpenInventory().getItem(16).getType() == Material.LAPIS_ORE && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("청금석")
            							 || p.getOpenInventory().getItem(16).getType() == Material.QUARTZ && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("네더 석영")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 42 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("철 블록")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 41 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("금 블록")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 57 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("다이아몬드 블록")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 133 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("에메랄드 블록")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 152 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("레드스톤 블록")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 22 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("청금석 블록")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 4 && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("돌")) {
            								if (UpGrade.getUpGradeInt(i + ".재료 수량") - p.getOpenInventory().getItem(16).getAmount() <= 0) {
                								str = str.replaceAll("<num>", "0");
                						  		str = str.replace("<YESorNO>", getMessage(p, "강화 가능"));
                						  		break;
            								} else {
            									str = str.replaceAll("<num>", Integer.toString(UpGrade.getUpGradeInt(i + ".재료 수량") - p.getOpenInventory().getItem(16).getAmount()));
            									str = str.replace("<YESorNO>", getMessage(p, "강화 불가능"));
            									break;
            								}
            							}
            							
            							else if (p.getOpenInventory().getItem(16).getTypeId() == 35 || p.getOpenInventory().getItem(16).getTypeId() == 155 || p.getOpenInventory().getItem(16).getTypeId() == 49) {
            								ItemStack i1 = p.getOpenInventory().getItem(16);
            								if (i1.getItemMeta() != null) {
            	    							if (i1.getItemMeta().getDisplayName() != null) {
            	    								if (i1.getData().getData() == 15 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("석탄 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("석탄 블록")
            	    								 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("네더 석영 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("네더 석영 블록")
            	    								 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("철 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("철 압축 블록")
            	    								 || i1.getData().getData() == 4 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("금 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("금 압축 블록")
            	    								 || i1.getData().getData() == 9 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("다이아몬드 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("다이아몬드 압축 블록")
            	    								 || i1.getData().getData() == 5 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("에메랄드 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("에메랄드 압축 블록")
            	    								 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("석탄 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("석탄 압축 블록")
            	    								 || i1.getData().getData() == 8 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("네더 석영 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("네더 석영 압축 블록")
            	    								 || i1.getData().getData() == 14 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("레드스톤 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("레드스톤 압축 블록")
            	    								 || i1.getData().getData() == 11 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("청금석 압축 블록") && UpGrade.getUpGradeString(i + ".아이템 재료").equalsIgnoreCase("청금석 압축 블록")) {
                        								if (UpGrade.getUpGradeInt(i + ".재료 수량") - p.getOpenInventory().getItem(16).getAmount() <= 0) {
                            								str = str.replaceAll("<num>", "0");
                            						  		str = str.replace("<YESorNO>", getMessage(p, "강화 가능"));
                            						  		break;
                        								} else {
                        									str = str.replaceAll("<num>", Integer.toString(UpGrade.getUpGradeInt(i + ".재료 수량") - p.getOpenInventory().getItem(16).getAmount()));
                        									str = str.replace("<YESorNO>", getMessage(p, "강화 불가능"));
                        									break;
                        								}
            	    								}
            	    							}
            								}
            							} else {
            								str = str.replaceAll("<num>", UpGrade.getUpGradeString(i + ".재료 수량"));
                							str = str.replace("<YESorNO>", getMessage(p, "강화 불가능"));
                    						break;
            							}
            						}
        						}
    						}
    					}
    				}
    			}
    		} else {
    			str = str.replaceAll("<material>", "없음");
    			str = str.replace("<YESorNO>", "§4강화 불가능 상태");
    		}

    		Returnlist.add(str);
        } return Returnlist;
	}
}
