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
            config.set("���� GUI �̸�", "����");
            
    		List<String> list1;
            list1 = config.getStringList("��.������ ����");
            list1.add("&7����� ���� &a<money> &7���� �ֽ��ϴ�.");
            config.set("��.������ �̸�", "&6�÷��̾ ������ &c��");
            config.set("��.������ ����", list1);
            config.set("��.������ �ڵ�", 388);
            config.set("��.������ �������ڵ�", 0);
            config.set("��.������ ����", 1);

     		List<String> list2;
            list2 = config.getStringList("����.������ ����");
            list2.add("&7&l===========================");
            list2.add("&f������ �������� ���� ������, ���߿�");
            list2.add("&f���ڸ� ���� ���� �� �Դϴ�.");
            list2.add("&7&l===========================");
            list2.add("&f");
            list2.add("&7* &f������ �� &b��ȯ&f�ؾ� �մϴ�. ����");
            list2.add("&7* &f��ȯ�� ���� ���ϸ� &e�߰� ����&f�� �ٰ�,");
            list2.add("&7* &f���� ������ �Ͻ� �� �����ϴ�.");
            list2.add("&f");
            list2.add("&a�ؿ� ���� Ŭ���� ������ �����մϴ�.");
            config.set("����.������ �̸�", "&a����");
            config.set("����.������ ����", list2);
            config.set("����.������ �ڵ�", 336);
            config.set("����.������ �������ڵ�", 0);
            config.set("����.������ ����", 1);
            
     		List<String> list3;
            list3 = config.getStringList("����.������ ����");
            list3.add("&7&l===========================");
            list3.add("&f������ �������� ���� �����ϰ�,");
            list3.add("&f�� ������ ������ ��� ����Դϴ�.");
            list3.add("&7&l===========================");
            list3.add("&f");
            list3.add("&7* &f������ &c����&f�Ǿ����� �� ����");
            list3.add("&7* &f�ٽ� ã�� ������ ���ô�.");
            list3.add("&f");
            list3.add("&a�ؿ� ���� Ŭ���� ������ �����մϴ�.");
            config.set("����.������ �̸�", "&e����");
            config.set("����.������ ����", list3);
            config.set("����.������ �ڵ�", 265);
            config.set("����.������ �������ڵ�", 0);
            config.set("����.������ ����", 1);
            
     		List<String> list4;
            list4 = config.getStringList("����.������ ����");
            list4.add("&7&l===========================");
            list4.add("&f���ڴ� ������ �Ͽ� ���� ��");
            list4.add("&f�� ���� ���� ������ ��� ���Դϴ�.");
            list4.add("&7&l===========================");
            list4.add("&f");
            list4.add("&7* &f���ڰ� &c����&f�Ǿ����� �� ����");
            list4.add("&7* &f�ٽ� ã�� ������ ���ô�.");
            list4.add("&f");
            list4.add("&a�ؿ� ���� Ŭ���� ���ڰ� �����մϴ�.");
            config.set("����.������ �̸�", "&b����");
            config.set("����.������ ����", list4);
            config.set("����.������ �ڵ�", 266);
            config.set("����.������ �������ڵ�", 0);
            config.set("����.������ ����", 1);
            
     		List<String> list5;
            list5 = config.getStringList("���⼳��.������ ����");
            list5.add("&f[&e&l+&f] ���Ⱑ�� �ݾ� :: &a<money1>\\");
            list5.add("&f[&e&l+&f] ��ȯ�� :: &a<money2>\\");
            list5.add("&f[&e&l+&f] ��ȯ�ð� :: &b<time>m");
            list5.add("&f[&e&l+&f] ���� :: &c<interest>");
            config.set("���⼳��.������ �̸�����", "&6");
            config.set("���⼳��.������ ����", list5);
            config.set("���⼳��.������ �ڵ�", 339);
            config.set("���⼳��.������ �������ڵ�", 0);
            config.set("���⼳��.������ ����", 1);
            
     		List<String> list6;
            list6 = config.getStringList("���ݼ���.������ ����");
            list6.add("&f[&e&l+&f] ���ݰ��� �ݾ� :: &a<money1>\\");
            list6.add("&f[&e&l+&f] ���� ���� :: &a<money2>\\");
            list6.add("&f[&e&l+&f] ���� �ð� :: &b<time>m");
            list6.add("&f[&e&l+&f] ���� :: &c<interest>");
            config.set("���ݼ���.������ �̸�����", "&6");
            config.set("���ݼ���.������ ����", list6);
            config.set("���ݼ���.������ �ڵ�", 339);
            config.set("���ݼ���.������ �������ڵ�", 0);
            config.set("���ݼ���.������ ����", 1);
            
     		List<String> list7;
            list7 = config.getStringList("���ڼ���.������ ����");
            list7.add("&f[&e&l+&f] ���ڰ��� �ݾ� :: &a<money1>\\");
            list7.add("&f[&e&l+&f] ���� ���ڱ� :: &a<money2>\\");
            list7.add("&f[&e&l+&f] ���� �ð� :: &b<time>m");
            list7.add("&f[&e&l+&f] ���� :: &c<interest>");
            config.set("���ڼ���.������ �̸�����", "&6");
            config.set("���ڼ���.������ ����", list7);
            config.set("���ڼ���.������ �ڵ�", 339);
            config.set("���ڼ���.������ �������ڵ�", 0);
            config.set("���ڼ���.������ ����", 1);
            
            List<String> list8;
            list8 = config.getStringList("������.������ ����");
            list8.add("&f[&e&l+&f] &7���Ⱑ�� �ݾ� &f:: &7<money1>\\");
            list8.add("&f[&e&l+&f] &7��ȯ�� &f:: &7<money2>\\");
            list8.add("&f[&e&l+&f] &7��ȯ�ð� &f:: &7<time>m");
            list8.add("&f[&e&l+&f] &7���� &f:: &7<interest>");
            list8.add("&c����� ���� ������ �̿����Դϴ�. <&6<nowtime>��&c>");
            list8.add("&a��ȯ�� �ϰ�����ø� Ŭ�����ּ���!");
            config.set("������.������ �̸�����", "&7");
            config.set("������.������ ����", list8);
            config.set("������.������ �ڵ�", 395);
            config.set("������.������ �������ڵ�", 0);
            config.set("������.������ ����", 1);

            List<String> list9;
            list9 = config.getStringList("������.������ ����");
            list9.add("&f[&e&l+&f] &7���ݰ��� �ݾ� &f:: &7<money1>\\");
            list9.add("&f[&e&l+&f] &7���� ���� &f:: &7<money2>\\");
            list9.add("&f[&e&l+&f] &7���� �ð� &f:: &7<time>m");
            list9.add("&f[&e&l+&f] &7���� &f:: &7<interest>");
            list9.add("&c����� ���� ������ �̿����Դϴ�. <&6<nowtime>��&c>");
            config.set("������.������ �̸�����", "&7");
            config.set("������.������ ����", list9);
            config.set("������.������ �ڵ�", 395);
            config.set("������.������ �������ڵ�", 0);
            config.set("������.������ ����", 1);

            List<String> list10;
            list10 = config.getStringList("������.������ ����");
            list10.add("&f[&e&l+&f] &7���ڰ��� �ݾ� &f:: &7<money1>\\");
            list10.add("&f[&e&l+&f] &7���� ���ڱ� &f:: &7<money2>\\");
            list10.add("&f[&e&l+&f] &7���� �ð� &f:: &7<time>m");
            list10.add("&f[&e&l+&f] &7���� &f:: &7<interest>");
            list10.add("&c����� ���� ���ڸ� �̿����Դϴ�. <&6<nowtime>��&c>");
            config.set("������.������ �̸�����", "&7");
            config.set("������.������ ����", list10);
            config.set("������.������ �ڵ�", 395);
            config.set("������.������ �������ڵ�", 0);
            config.set("������.������ ����", 1);
            
            List<String> list11;
            list11 = config.getStringList("����Ϸ�.������ ����");
            list11.add("&f[&e&l+&f] &7���Ⱑ�� �ݾ� &f:: &7<money1>\\");
            list11.add("&f[&e&l+&f] &7��ȯ�� &f:: &b<money3>\\");
            list11.add("&f[&e&l+&f] &7��ȯ�ð� &f:: &7<time>m");
            list11.add("&f[&e&l+&f] &7���� &f:: &7<interest>");
            list11.add("&c���� ��ȯ �Ⱓ�� �ʰ��Ǿ����ϴ�.");
            list11.add("&c��ȯ�ݿ� &4�߰� ����&c�� �پ����ϴ�.");
            list11.add("&a��ȯ�� �ϰ�����ø� Ŭ�����ּ���!");
            config.set("����Ϸ�.������ �̸�����", "&7");
            config.set("����Ϸ�.������ ����", list11);
            config.set("����Ϸ�.������ �ڵ�", 332);
            config.set("����Ϸ�.������ �������ڵ�", 0);
            config.set("����Ϸ�.������ ����", 1);

            List<String> list12;
            list12 = config.getStringList("���ݿϷ�.������ ����");
            list12.add("&f[&e&l+&f] &7���ݰ��� �ݾ� &f:: &7<money1>\\");
            list12.add("&f[&e&l+&f] &7���� ���� &f:: &b<money2>\\");
            list12.add("&f[&e&l+&f] &7���� �ð� &f:: &7<time>m");
            list12.add("&f[&e&l+&f] &7���� &f:: &7<interest>");
            list12.add("&b���� �ð� ���Ⱑ �Ϸ�Ǿ����ϴ�.");
            list12.add("&a�ݾ��� ã������ø� Ŭ�����ּ���!");
            config.set("���ݿϷ�.������ �̸�����", "&7");
            config.set("���ݿϷ�.������ ����", list12);
            config.set("���ݿϷ�.������ �ڵ�", 332);
            config.set("���ݿϷ�.������ �������ڵ�", 0);
            config.set("���ݿϷ�.������ ����", 1);

            List<String> list13;
            list13 = config.getStringList("���ڿϷ�.������ ����");
            list13.add("&f[&e&l+&f] &7���ڰ��� �ݾ� &f:: &7<money1>\\");
            list13.add("&f[&e&l+&f] &7���� ���ڱ� &f:: &b<money2>\\");
            list13.add("&f[&e&l+&f] &7���� �ð� &f:: &7<time>m");
            list13.add("&f[&e&l+&f] &7���� &f:: &7<interest>");
            list13.add("&b���� �ð� ���Ⱑ �Ϸ�Ǿ����ϴ�.");
            list13.add("&a�ݾ��� ã������ø� Ŭ�����ּ���!");
            config.set("���ڿϷ�.������ �̸�����", "&7");
            config.set("���ڿϷ�.������ ����", list13);
            config.set("���ڿϷ�.������ �ڵ�", 332);
            config.set("���ڿϷ�.������ �������ڵ�", 0);
            config.set("���ڿϷ�.������ ����", 1);
            
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
		int i = PlayerYml.getInfoInt(p, "���Ⱚ") + 1;
        message = message.replaceAll("<money1>", Integer.toString(B.getBorrowInt(i + ".���Ⱑ�� �ݾ�")));
        message = message.replaceAll("<money2>", Integer.toString(B.getBorrowInt(i + ".��ȯ��")));
        message = message.replaceAll("<money3>", Integer.toString(B.getBorrowInt(i + ".��ȯ����")));
        message = message.replaceAll("<time>", Integer.toString(B.getBorrowInt(i + ".��ȯ�ð� (��)")));
        message = message.replaceAll("<interest>", B.getBorrowString(i + ".����"));
        message = message.replaceAll("<nowtime>", Integer.toString(PlayerYml.getInfoInt(p, "���� Ÿ�̸�")));
        return message;
	}
	
	@SuppressWarnings("static-access")
	public static String replaceDeposit(Player p, String message) {
		int i = PlayerYml.getInfoInt(p, "���ڰ�") + 1;
        message = message.replaceAll("<money1>", Integer.toString(D.getDepositInt(i + ".���ڰ��� �ݾ�")));
        message = message.replaceAll("<money2>", Integer.toString(D.getDepositInt(i + ".���� ���ڱ�")));
        message = message.replaceAll("<time>", Integer.toString(D.getDepositInt(i + ".����ð� (��)")));
        message = message.replaceAll("<interest>", D.getDepositString(i + ".����"));
        message = message.replaceAll("<nowtime>", Integer.toString(PlayerYml.getInfoInt(p, "���� Ÿ�̸�")));
        return message;
	}
	
	@SuppressWarnings("static-access")
	public static String replaceInvest(Player p, String message) {
		int i = PlayerYml.getInfoInt(p, "���ݰ�") + 1;
        message = message.replaceAll("<money1>", Integer.toString(I.getInvestInt(i + ".���ݰ��� �ݾ�")));
        message = message.replaceAll("<money2>", Integer.toString(I.getInvestInt(i + ".���� ����")));
        message = message.replaceAll("<time>", Integer.toString(I.getInvestInt(i + ".����ð� (��)")));
        message = message.replaceAll("<interest>", I.getInvestString(i + ".����"));
        message = message.replaceAll("<nowtime>", Integer.toString(PlayerYml.getInfoInt(p, "���� Ÿ�̸�")));
        return message;
	}
	
	public static String replaceMoney(Player p, String message) {
		double money = Main.economy.getBalance(p.getName());
		String sm = Double.toString(money);
        message = message.replaceAll("<money>", sm);
        return message;
	}
	
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "��1");
        message = message.replaceAll("&2", "��2");
        message = message.replaceAll("&3", "��3");
        message = message.replaceAll("&4", "��4");
        message = message.replaceAll("&5", "��5");
        message = message.replaceAll("&6", "��6");
        message = message.replaceAll("&7", "��7");
        message = message.replaceAll("&8", "��8");
        message = message.replaceAll("&9", "��9");
        message = message.replaceAll("&0", "��0");
        message = message.replaceAll("&a", "��a");
        message = message.replaceAll("&b", "��b");
        message = message.replaceAll("&c", "��c");
        message = message.replaceAll("&d", "��d");
        message = message.replaceAll("&e", "��e");
        message = message.replaceAll("&f", "��f");
        message = message.replaceAll("&k", "��k");
        message = message.replaceAll("&l", "��l");
        message = message.replaceAll("&m", "��m");
        message = message.replaceAll("&n", "��n");
        message = message.replaceAll("&o", "��o");
        message = message.replaceAll("&r", "��r");
        return message;
	}
}
