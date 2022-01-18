package me.shinkhan.Finance.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.shinkhan.Finance.Main;

public class GUIMessage {
	static Message E;
	static Borrow B;
	static Deposit D;
	static Invest I;
	
	public static void CreateGUIMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("금융 GUI 이름", "금융");
            
    		List<String> list1;
            list1 = config.getStringList("돈.아이템 설명");
            list1.add("&7당신은 현재 &a<money> &7원이 있습니다.");
            config.set("돈.아이템 이름", "&6플레이어가 소지한 &c돈");
            config.set("돈.아이템 설명", list1);
            config.set("돈.아이템 코드", 388);
            config.set("돈.아이템 데이터코드", 0);
            config.set("돈.아이템 수량", 1);

     		List<String> list2;
            list2 = config.getStringList("대출.아이템 설명");
            list2.add("&7&l===========================");
            list2.add("&f대출은 일정량의 돈을 빌리고, 나중에");
            list2.add("&f이자를 붙혀 갚는 것 입니다.");
            list2.add("&7&l===========================");
            list2.add("&f");
            list2.add("&7* &f대출은 꼭 &b상환&f해야 합니다. 대출");
            list2.add("&7* &f상환을 제때 못하면 &e추가 이자&f가 붙고,");
            list2.add("&7* &f다음 대출을 하실 수 없습니다.");
            list2.add("&f");
            list2.add("&a밑에 종이 클릭시 대출이 가능합니다.");
            config.set("대출.아이템 이름", "&a대출");
            config.set("대출.아이템 설명", list2);
            config.set("대출.아이템 코드", 336);
            config.set("대출.아이템 데이터코드", 0);
            config.set("대출.아이템 수량", 1);
            
     		List<String> list3;
            list3 = config.getStringList("예금.아이템 설명");
            list3.add("&7&l===========================");
            list3.add("&f예금은 일정량의 돈을 저축하고,");
            list3.add("&f그 돈으로 이율을 얻는 방식입니다.");
            list3.add("&7&l===========================");
            list3.add("&f");
            list3.add("&7* &f예금이 &c만기&f되었으면 꼭 돈을");
            list3.add("&7* &f다시 찾아 이익을 봅시다.");
            list3.add("&f");
            list3.add("&a밑에 종이 클릭시 예금이 가능합니다.");
            config.set("예금.아이템 이름", "&e예금");
            config.set("예금.아이템 설명", list3);
            config.set("예금.아이템 코드", 265);
            config.set("예금.아이템 데이터코드", 0);
            config.set("예금.아이템 수량", 1);
            
     		List<String> list4;
            list4 = config.getStringList("투자.아이템 설명");
            list4.add("&7&l===========================");
            list4.add("&f투자는 설정된 일에 돈을 대어서");
            list4.add("&f그 돈에 따라 이익을 얻는 것입니다.");
            list4.add("&7&l===========================");
            list4.add("&f");
            list4.add("&7* &f투자가 &c만기&f되었으면 꼭 돈을");
            list4.add("&7* &f다시 찾아 이익을 봅시다.");
            list4.add("&f");
            list4.add("&a밑에 종이 클릭시 투자가 가능합니다.");
            config.set("투자.아이템 이름", "&b투자");
            config.set("투자.아이템 설명", list4);
            config.set("투자.아이템 코드", 266);
            config.set("투자.아이템 데이터코드", 0);
            config.set("투자.아이템 수량", 1);
            
     		List<String> list5;
            list5 = config.getStringList("대출설정.아이템 설명");
            list5.add("&f[&e&l+&f] 대출가능 금액 :: &a<money1>\\");
            list5.add("&f[&e&l+&f] 상환금 :: &a<money2>\\");
            list5.add("&f[&e&l+&f] 상환시간 :: &b<time>m");
            list5.add("&f[&e&l+&f] 이율 :: &c<interest>");
            config.set("대출설정.아이템 이름색깔", "&6");
            config.set("대출설정.아이템 설명", list5);
            config.set("대출설정.아이템 코드", 339);
            config.set("대출설정.아이템 데이터코드", 0);
            config.set("대출설정.아이템 수량", 1);
            
     		List<String> list6;
            list6 = config.getStringList("예금설정.아이템 설명");
            list6.add("&f[&e&l+&f] 예금가능 금액 :: &a<money1>\\");
            list6.add("&f[&e&l+&f] 만기 예금 :: &a<money2>\\");
            list6.add("&f[&e&l+&f] 만기 시간 :: &b<time>m");
            list6.add("&f[&e&l+&f] 이율 :: &c<interest>");
            config.set("예금설정.아이템 이름색깔", "&6");
            config.set("예금설정.아이템 설명", list6);
            config.set("예금설정.아이템 코드", 339);
            config.set("예금설정.아이템 데이터코드", 0);
            config.set("예금설정.아이템 수량", 1);
            
     		List<String> list7;
            list7 = config.getStringList("투자설정.아이템 설명");
            list7.add("&f[&e&l+&f] 투자가능 금액 :: &a<money1>\\");
            list7.add("&f[&e&l+&f] 만기 투자금 :: &a<money2>\\");
            list7.add("&f[&e&l+&f] 만기 시간 :: &b<time>m");
            list7.add("&f[&e&l+&f] 이율 :: &c<interest>");
            config.set("투자설정.아이템 이름색깔", "&6");
            config.set("투자설정.아이템 설명", list7);
            config.set("투자설정.아이템 코드", 339);
            config.set("투자설정.아이템 데이터코드", 0);
            config.set("투자설정.아이템 수량", 1);
            
            List<String> list8;
            list8 = config.getStringList("대출중.아이템 설명");
            list8.add("&f[&e&l+&f] &7대출가능 금액 &f:: &7<money1>\\");
            list8.add("&f[&e&l+&f] &7상환금 &f:: &7<money2>\\");
            list8.add("&f[&e&l+&f] &7상환시간 &f:: &7<time>m");
            list8.add("&f[&e&l+&f] &7이율 &f:: &7<interest>");
            list8.add("&c당신은 현재 대출을 이용중입니다. <&6<nowtime>분&c>");
            list8.add("&a상환을 하고싶으시면 클릭해주세요!");
            config.set("대출중.아이템 이름색깔", "&7");
            config.set("대출중.아이템 설명", list8);
            config.set("대출중.아이템 코드", 395);
            config.set("대출중.아이템 데이터코드", 0);
            config.set("대출중.아이템 수량", 1);

            List<String> list9;
            list9 = config.getStringList("예금중.아이템 설명");
            list9.add("&f[&e&l+&f] &7예금가능 금액 &f:: &7<money1>\\");
            list9.add("&f[&e&l+&f] &7만기 예금 &f:: &7<money2>\\");
            list9.add("&f[&e&l+&f] &7만기 시간 &f:: &7<time>m");
            list9.add("&f[&e&l+&f] &7이율 &f:: &7<interest>");
            list9.add("&c당신은 현재 예금을 이용중입니다. <&6<nowtime>분&c>");
            config.set("예금중.아이템 이름색깔", "&7");
            config.set("예금중.아이템 설명", list9);
            config.set("예금중.아이템 코드", 395);
            config.set("예금중.아이템 데이터코드", 0);
            config.set("예금중.아이템 수량", 1);

            List<String> list10;
            list10 = config.getStringList("투자중.아이템 설명");
            list10.add("&f[&e&l+&f] &7투자가능 금액 &f:: &7<money1>\\");
            list10.add("&f[&e&l+&f] &7만기 투자금 &f:: &7<money2>\\");
            list10.add("&f[&e&l+&f] &7만기 시간 &f:: &7<time>m");
            list10.add("&f[&e&l+&f] &7이율 &f:: &7<interest>");
            list10.add("&c당신은 현재 투자를 이용중입니다. <&6<nowtime>분&c>");
            config.set("투자중.아이템 이름색깔", "&7");
            config.set("투자중.아이템 설명", list10);
            config.set("투자중.아이템 코드", 395);
            config.set("투자중.아이템 데이터코드", 0);
            config.set("투자중.아이템 수량", 1);
            
            List<String> list11;
            list11 = config.getStringList("대출완료.아이템 설명");
            list11.add("&f[&e&l+&f] &7대출가능 금액 &f:: &7<money1>\\");
            list11.add("&f[&e&l+&f] &7상환금 &f:: &b<money3>\\");
            list11.add("&f[&e&l+&f] &7상환시간 &f:: &7<time>m");
            list11.add("&f[&e&l+&f] &7이율 &f:: &7<interest>");
            list11.add("&c대출 상환 기간이 초과되었습니다.");
            list11.add("&c상환금에 &4추가 이자&c가 붙었습니다.");
            list11.add("&a상환을 하고싶으시면 클릭해주세요!");
            config.set("대출완료.아이템 이름색깔", "&7");
            config.set("대출완료.아이템 설명", list11);
            config.set("대출완료.아이템 코드", 332);
            config.set("대출완료.아이템 데이터코드", 0);
            config.set("대출완료.아이템 수량", 1);

            List<String> list12;
            list12 = config.getStringList("예금완료.아이템 설명");
            list12.add("&f[&e&l+&f] &7예금가능 금액 &f:: &7<money1>\\");
            list12.add("&f[&e&l+&f] &7만기 예금 &f:: &b<money2>\\");
            list12.add("&f[&e&l+&f] &7만기 시간 &f:: &7<time>m");
            list12.add("&f[&e&l+&f] &7이율 &f:: &7<interest>");
            list12.add("&b예금 시간 만기가 완료되었습니다.");
            list12.add("&a금액을 찾고싶으시면 클릭해주세요!");
            config.set("예금완료.아이템 이름색깔", "&7");
            config.set("예금완료.아이템 설명", list12);
            config.set("예금완료.아이템 코드", 332);
            config.set("예금완료.아이템 데이터코드", 0);
            config.set("예금완료.아이템 수량", 1);

            List<String> list13;
            list13 = config.getStringList("투자완료.아이템 설명");
            list13.add("&f[&e&l+&f] &7투자가능 금액 &f:: &7<money1>\\");
            list13.add("&f[&e&l+&f] &7만기 투자금 &f:: &b<money2>\\");
            list13.add("&f[&e&l+&f] &7만기 시간 &f:: &7<time>m");
            list13.add("&f[&e&l+&f] &7이율 &f:: &7<interest>");
            list13.add("&b투자 시간 만기가 완료되었습니다.");
            list13.add("&a금액을 찾고싶으시면 클릭해주세요!");
            config.set("투자완료.아이템 이름색깔", "&7");
            config.set("투자완료.아이템 설명", list13);
            config.set("투자완료.아이템 코드", 332);
            config.set("투자완료.아이템 데이터코드", 0);
            config.set("투자완료.아이템 수량", 1);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getMessage(Player p, String name) {
		File f = new File("plugins/DHFinance/GUIMessage.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		String Message = config.getString(name);
		Message = replaceAllColors(Message);
		Message = replaceMoney(p, Message);
		return Message;
	}
	
	public static int getItemCode(String name) {
		File f = new File("plugins/DHFinance/GUIMessage.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		int Code = config.getInt(name);
		return Code;
	}
	
	public static List<String> getLoreList(Player p, String name) {
		File f = new File("plugins/DHFinance/GUIMessage.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		str = replaceMoney(p, str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static List<String> getLoreBorrow(Player p, String name) {
		File f = new File("plugins/DHFinance/GUIMessage.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		str = replaceBorrow(p, str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static List<String> getLoreInvest(Player p, String name) {
		File f = new File("plugins/DHFinance/GUIMessage.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		str = replaceInvest(p, str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	public static List<String> getLoreDeposit(Player p, String name) {
		File f = new File("plugins/DHFinance/GUIMessage.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = replaceAllColors(str);
    		str = replaceDeposit(p, str);
    		Returnlist.add(str);
        } return Returnlist;
	}
	
	@SuppressWarnings("static-access")
	public static String replaceBorrow(Player p, String message) {
		int i = PlayerYml.getInfoInt(p, "대출값") + 1;
        message = message.replaceAll("<money1>", Integer.toString(B.getBorrowInt(i + ".대출가능 금액")));
        message = message.replaceAll("<money2>", Integer.toString(B.getBorrowInt(i + ".상환금")));
        message = message.replaceAll("<money3>", Integer.toString(B.getBorrowInt(i + ".상환이자")));
        message = message.replaceAll("<time>", Integer.toString(B.getBorrowInt(i + ".상환시간 (분)")));
        message = message.replaceAll("<interest>", B.getBorrowString(i + ".이율"));
        message = message.replaceAll("<nowtime>", Integer.toString(PlayerYml.getInfoInt(p, "대출 타이머")));
        return message;
	}
	
	@SuppressWarnings("static-access")
	public static String replaceDeposit(Player p, String message) {
		int i = PlayerYml.getInfoInt(p, "투자값") + 1;
        message = message.replaceAll("<money1>", Integer.toString(D.getDepositInt(i + ".투자가능 금액")));
        message = message.replaceAll("<money2>", Integer.toString(D.getDepositInt(i + ".만기 투자금")));
        message = message.replaceAll("<time>", Integer.toString(D.getDepositInt(i + ".만기시간 (분)")));
        message = message.replaceAll("<interest>", D.getDepositString(i + ".이율"));
        message = message.replaceAll("<nowtime>", Integer.toString(PlayerYml.getInfoInt(p, "투자 타이머")));
        return message;
	}
	
	@SuppressWarnings("static-access")
	public static String replaceInvest(Player p, String message) {
		int i = PlayerYml.getInfoInt(p, "예금값") + 1;
        message = message.replaceAll("<money1>", Integer.toString(I.getInvestInt(i + ".예금가능 금액")));
        message = message.replaceAll("<money2>", Integer.toString(I.getInvestInt(i + ".만기 예금")));
        message = message.replaceAll("<time>", Integer.toString(I.getInvestInt(i + ".만기시간 (분)")));
        message = message.replaceAll("<interest>", I.getInvestString(i + ".이율"));
        message = message.replaceAll("<nowtime>", Integer.toString(PlayerYml.getInfoInt(p, "예금 타이머")));
        return message;
	}
	
	public static String replaceMoney(Player p, String message) {
		double money = Main.economy.getBalance(p.getName());
		String sm = Double.toString(money);
        message = message.replaceAll("<money>", sm);
        return message;
	}
	
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
}
