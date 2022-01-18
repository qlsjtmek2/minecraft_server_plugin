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
            Alist1 = config.getStringList("1.������ ����");
            Alist1.add("&7&l==============");
            Alist1.add("&f��ȭ�� �ȵ� ����");
            Alist1.add("&f��̴�. ����ϰ�");
            Alist1.add("&fä���� �õ��غ���.");
            Alist1.add("&7&l==============");
            config.set("1.������ ���", "��");
            config.set("1.��� ����", 32);
            config.set("1.������ �̸�", "&e���� ���");
            config.set("1.������ ����", Alist1);
            config.set("1.������ �ڵ�", 257);
            config.set("1.������ �������ڵ�", 0);
            config.set("1.ȿ��", 1);
            
            List<String> Alist2;
            Alist2 = config.getStringList("2.������ ����");
            Alist2.add("&7&l==============");
            Alist2.add("&f��ȭ�� +1�� ����");
            Alist2.add("&f��̴�. ����ϰ�");
            Alist2.add("&fä���� �õ��غ���.");
            Alist2.add("&7&l==============");
            config.set("2.������ ���", "��");
            config.set("2.��� ����", 64);
            config.set("2.������ �̸�", "&e���� ��� &f+1");
            config.set("2.������ ����", Alist2);
            config.set("2.������ �ڵ�", 257);
            config.set("2.������ �������ڵ�", 0);
            config.set("2.ȿ��", 2);
            
            List<String> Alist3;
            Alist3 = config.getStringList("3.������ ����");
            Alist3.add("&7&l==============");
            Alist3.add("&f��ȭ�� +2�� ����");
            Alist3.add("&f��̴�. ����ϰ�");
            Alist3.add("&fä���� �õ��غ���.");
            Alist3.add("&7&l==============");
            config.set("3.������ ���", "��ź");
            config.set("3.��� ����", 64);
            config.set("3.������ �̸�", "&e���� ��� &f+2");
            config.set("3.������ ����", Alist3);
            config.set("3.������ �ڵ�", 257);
            config.set("3.������ �������ڵ�", 0);
            config.set("3.ȿ��", 2);
            config.set("3.���", 1);
            
            List<String> Alist4;
            Alist4 = config.getStringList("4.������ ����");
            Alist4.add("&7&l==============");
            Alist4.add("&f��ȭ�� +3�� ����");
            Alist4.add("&f��̴�. ����ϰ�");
            Alist4.add("&fä���� �õ��غ���.");
            Alist4.add("&7&l==============");
            config.set("4.������ ���", "��ź ���");
            config.set("4.��� ����", 12);
            config.set("4.������ �̸�", "&e���� ��� &f+3");
            config.set("4.������ ����", Alist4);
            config.set("4.������ �ڵ�", 257);
            config.set("4.������ �������ڵ�", 0);
            config.set("4.ȿ��", 3);
            config.set("4.���", 1);
            
            List<String> Alist5;
            Alist5 = config.getStringList("5.������ ����");
            Alist5.add("&7&l==============");
            Alist5.add("&f��ȭ�� +4�� ����");
            Alist5.add("&f��̴�. ����ϰ�");
            Alist5.add("&fä���� �õ��غ���.");
            Alist5.add("&7&l==============");
            config.set("5.������ ���", "ö ���");
            config.set("5.��� ����", 12);
            config.set("5.������ �̸�", "&e���� ��� &f+4");
            config.set("5.������ ����", Alist5);
            config.set("5.������ �ڵ�", 257);
            config.set("5.������ �������ڵ�", 0);
            config.set("5.ȿ��", 3);
            config.set("5.���", 2);
            
            List<String> Alist6;
            Alist6 = config.getStringList("6.������ ����");
            Alist6.add("&7&l==============");
            Alist6.add("&f��ȭ�� +5�� ����");
            Alist6.add("&f��̴�. ����ϰ�");
            Alist6.add("&fä���� �õ��غ���.");
            Alist6.add("&7&l==============");
            config.set("6.������ ���", "ö ���");
            config.set("6.��� ����", 36);
            config.set("6.������ �̸�", "&e���� ��� &f+5");
            config.set("6.������ ����", Alist6);
            config.set("6.������ �ڵ�", 257);
            config.set("6.������ �������ڵ�", 0);
            config.set("6.ȿ��", 4);
            config.set("6.���", 2);
            
            List<String> Alist7;
            Alist7 = config.getStringList("7.������ ����");
            Alist7.add("&7&l==============");
            Alist7.add("&f��ȭ�� +6�� ����");
            Alist7.add("&f��̴�. ����ϰ�");
            Alist7.add("&fä���� �õ��غ���.");
            Alist7.add("&7&l==============");
            config.set("7.������ ���", "�� ���");
            config.set("7.��� ����", 48);
            config.set("7.������ �̸�", "&e���� ��� &f+6");
            config.set("7.������ ����", Alist7);
            config.set("7.������ �ڵ�", 257);
            config.set("7.������ �������ڵ�", 0);
            config.set("7.ȿ��", 4);
            config.set("7.���", 3);
            
            List<String> Alist8;
            Alist8 = config.getStringList("8.������ ����");
            Alist8.add("&7&l==============");
            Alist8.add("&f��ȭ�� +7�� ����");
            Alist8.add("&f��̴�. ����ϰ�");
            Alist8.add("&fä���� �õ��غ���.");
            Alist8.add("&7&l==============");
            config.set("8.������ ���", "�� ���");
            config.set("8.��� ����", 64);
            config.set("8.������ �̸�", "&e���� ��� &f+7");
            config.set("8.������ ����", Alist8);
            config.set("8.������ �ڵ�", 257);
            config.set("8.������ �������ڵ�", 0);
            config.set("8.ȿ��", 5);
            config.set("8.���", 3);
            
            List<String> Alist9;
            Alist9 = config.getStringList("9.������ ����");
            Alist9.add("&7&l==============");
            Alist9.add("&f��ȭ�� +8�� ����");
            Alist9.add("&f��̴�. ����ϰ�");
            Alist9.add("&fä���� �õ��غ���.");
            Alist9.add("&7&l==============");
            config.set("9.������ ���", "ö ���� ���");
            config.set("9.��� ����", 8);
            config.set("9.������ �̸�", "&e���� ��� &f+8");
            config.set("9.������ ����", Alist9);
            config.set("9.������ �ڵ�", 257);
            config.set("9.������ �������ڵ�", 0);
            config.set("9.ȿ��", 5);
            config.set("9.���", 4);
            
            List<String> Alist10;
            Alist10 = config.getStringList("10.������ ����");
            Alist10.add("&7&l==============");
            Alist10.add("&f��ȭ�� +9�� ����");
            Alist10.add("&f��̴�. ����ϰ�");
            Alist10.add("&fä���� �õ��غ���.");
            Alist10.add("&7&l==============");
            config.set("10.������ ���", "ö ���� ���");
            config.set("10.��� ����", 12);
            config.set("10.������ �̸�", "&e���� ��� &f+9");
            config.set("10.������ ����", Alist10);
            config.set("10.������ �ڵ�", 257);
            config.set("10.������ �������ڵ�", 0);
            config.set("10.ȿ��", 6);
            config.set("10.���", 4);
            
            List<String> Alist11;
            Alist11 = config.getStringList("11.������ ����");
            Alist11.add("&7&l==============");
            Alist11.add("&f��ȭ�� +10�� ����");
            Alist11.add("&f��̴�. ����ϰ�");
            Alist11.add("&fä���� �õ��غ���.");
            Alist11.add("&7&l==============");
            config.set("11.������ ���", "�� ���� ���");
            config.set("11.��� ����", 16);
            config.set("11.������ �̸�", "&e���� ��� &f+10");
            config.set("11.������ ����", Alist11);
            config.set("11.������ �ڵ�", 257);
            config.set("11.������ �������ڵ�", 0);
            config.set("11.ȿ��", 6);
            config.set("11.���", 5);
            
            List<String> Blist1;
            Blist1 = config.getStringList("12.������ ����");
            Blist1.add("&7&l==============");
            Blist1.add("&f��ȭ�� �ȵ� ����");
            Blist1.add("&f��̴�. ��������");
            Blist1.add("&fä���� �õ��غ���.");
            Blist1.add("&7&l==============");
            config.set("12.������ ���", "�� ���� ���");
            config.set("12.��� ����", 20);
            config.set("12.������ �̸�", "&6���� ���");
            config.set("12.������ ����", Blist1);
            config.set("12.������ �ڵ�", 278);
            config.set("12.������ �������ڵ�", 0);
            config.set("12.ȿ��", 7);
            config.set("12.���", 5);
            
            List<String> Blist2;
            Blist2 = config.getStringList("13.������ ����");
            Blist2.add("&7&l==============");
            Blist2.add("&f��ȭ�� +1�� ����");
            Blist2.add("&f��̴�. ��������");
            Blist2.add("&fä���� �õ��غ���.");
            Blist2.add("&7&l==============");
            config.set("13.������ ���", "û�ݼ� ���� ���");
            config.set("13.��� ����", 24);
            config.set("13.������ �̸�", "&6���� ��� &f+1");
            config.set("13.������ ����", Blist2);
            config.set("13.������ �ڵ�", 278);
            config.set("13.������ �������ڵ�", 0);
            config.set("13.ȿ��", 8);
            config.set("13.���", 5);
            
            List<String> Blist3;
            Blist3 = config.getStringList("14.������ ����");
            Blist3.add("&7&l==============");
            Blist3.add("&f��ȭ�� +2�� ����");
            Blist3.add("&f��̴�. ��������");
            Blist3.add("&fä���� �õ��غ���.");
            Blist3.add("&7&l==============");
            config.set("14.������ ���", "û�ݼ� ���� ���");
            config.set("14.��� ����", 28);
            config.set("14.������ �̸�", "&6���� ��� &f+2");
            config.set("14.������ ����", Blist3);
            config.set("14.������ �ڵ�", 278);
            config.set("14.������ �������ڵ�", 0);
            config.set("14.ȿ��", 9);
            config.set("14.���", 5);
            
            List<String> Blist4;
            Blist4 = config.getStringList("15.������ ����");
            Blist4.add("&7&l==============");
            Blist4.add("&f��ȭ�� +3�� ����");
            Blist4.add("&f��̴�. ��������");
            Blist4.add("&fä���� �õ��غ���.");
            Blist4.add("&7&l==============");
            config.set("15.������ ���", "���彺�� ���� ���");
            config.set("15.��� ����", 32);
            config.set("15.������ �̸�", "&6���� ��� &f+3");
            config.set("15.������ ����", Blist4);
            config.set("15.������ �ڵ�", 278);
            config.set("15.������ �������ڵ�", 0);
            config.set("15.ȿ��", 10);
            config.set("15.���", 5);
            
            List<String> Blist5;
            Blist5 = config.getStringList("16.������ ����");
            Blist5.add("&7&l==============");
            Blist5.add("&f��ȭ�� +4�� ����");
            Blist5.add("&f��̴�. ��������");
            Blist5.add("&fä���� �õ��غ���.");
            Blist5.add("&7&l==============");
            config.set("16.������ ���", "���彺�� ���� ���");
            config.set("16.��� ����", 36);
            config.set("16.������ �̸�", "&6���� ��� &f+4");
            config.set("16.������ ����", Blist5);
            config.set("16.������ �ڵ�", 278);
            config.set("16.������ �������ڵ�", 0);
            config.set("16.ȿ��", 11);
            config.set("16.���", 5);
            
            List<String> Blist6;
            Blist6 = config.getStringList("17.������ ����");
            Blist6.add("&7&l==============");
            Blist6.add("&f��ȭ�� +5�� ����");
            Blist6.add("&f��̴�. ��������");
            Blist6.add("&fä���� �õ��غ���.");
            Blist6.add("&7&l==============");
            config.set("17.������ ���", "�״� ���� ���� ���");
            config.set("17.��� ����", 40);
            config.set("17.������ �̸�", "&6���� ��� &f+5");
            config.set("17.������ ����", Blist6);
            config.set("17.������ �ڵ�", 278);
            config.set("17.������ �������ڵ�", 0);
            config.set("17.ȿ��", 12);
            config.set("17.���", 5);

            List<String> Blist7;
            Blist7 = config.getStringList("18.������ ����");
            Blist7.add("&7&l==============");
            Blist7.add("&f��ȭ�� +6�� ����");
            Blist7.add("&f��̴�. ��������");
            Blist7.add("&fä���� �õ��غ���.");
            Blist7.add("&7&l==============");
            config.set("18.������ ���", "�״� ���� ���� ���");
            config.set("18.��� ����", 44);
            config.set("18.������ �̸�", "&6���� ��� &f+6");
            config.set("18.������ ����", Blist7);
            config.set("18.������ �ڵ�", 278);
            config.set("18.������ �������ڵ�", 0);
            config.set("18.ȿ��", 12);
            config.set("18.���", 6);
            
            List<String> Blist8;
            Blist8 = config.getStringList("19.������ ����");
            Blist8.add("&7&l==============");
            Blist8.add("&f��ȭ�� +7�� ����");
            Blist8.add("&f��̴�. ��������");
            Blist8.add("&fä���� �õ��غ���.");
            Blist8.add("&7&l==============");
            config.set("19.������ ���", "���̾Ƹ�� ���� ���");
            config.set("19.��� ����", 48);
            config.set("19.������ �̸�", "&6���� ��� &f+7");
            config.set("19.������ ����", Blist8);
            config.set("19.������ �ڵ�", 278);
            config.set("19.������ �������ڵ�", 0);
            config.set("19.ȿ��", 13);
            config.set("19.���", 6);
            
            List<String> Blist9;
            Blist9 = config.getStringList("20.������ ����");
            Blist9.add("&7&l==============");
            Blist9.add("&f��ȭ�� +8�� ����");
            Blist9.add("&f��̴�. ��������");
            Blist9.add("&fä���� �õ��غ���.");
            Blist9.add("&7&l==============");
            config.set("20.������ ���", "���̾Ƹ�� ���� ���");
            config.set("20.��� ����", 52);
            config.set("20.������ �̸�", "&6���� ��� &f+8");
            config.set("20.������ ����", Blist9);
            config.set("20.������ �ڵ�", 278);
            config.set("20.������ �������ڵ�", 0);
            config.set("20.ȿ��", 14);
            config.set("20.���", 6);
            
            List<String> Blist10;
            Blist10 = config.getStringList("21.������ ����");
            Blist10.add("&7&l==============");
            Blist10.add("&f��ȭ�� +9�� ����");
            Blist10.add("&f��̴�. ��������");
            Blist10.add("&fä���� �õ��غ���.");
            Blist10.add("&7&l==============");
            config.set("21.������ ���", "���޶��� ���� ���");
            config.set("21.��� ����", 58);
            config.set("21.������ �̸�", "&6���� ��� &f+9");
            config.set("21.������ ����", Blist10);
            config.set("21.������ �ڵ�", 278);
            config.set("21.������ �������ڵ�", 0);
            config.set("21.ȿ��", 15);
            config.set("21.���", 6);
            
            List<String> Blist11;
            Blist11 = config.getStringList("22.������ ����");
            Blist11.add("&7&l==============");
            Blist11.add("&f��ȭ�� +10�� ����");
            Blist11.add("&f��̴�. ��������");
            Blist11.add("&fä���� �õ��غ���.");
            Blist11.add("&7&l==============");
            config.set("22.������ ���", "���޶��� ���� ���");
            config.set("22.��� ����", 64);
            config.set("22.������ �̸�", "&6���� ��� &f+10");
            config.set("22.������ ����", Blist11);
            config.set("22.������ �ڵ�", 278);
            config.set("22.������ �������ڵ�", 0);
            config.set("22.ȿ��", 15);
            config.set("22.���", 7);
            
            List<String> Clist1;
            Clist1 = config.getStringList("23.������ ����");
            Clist1.add("&7&l==============");
            Clist1.add("&f��ȭ�� �ȵ� �ְ�");
            Clist1.add("&f��̴�. �����ְ�");
            Clist1.add("&fä���� ����������.");
            Clist1.add("&7&l==============");
            config.set("23.������ ���", "����");
            config.set("23.��� ����", 0);
            config.set("23.������ �̸�", "&b�����͸� ���");
            config.set("23.������ ����", Clist1);
            config.set("23.������ �ڵ�", 285);
            config.set("23.������ �������ڵ�", 0);
            config.set("23.ȿ��", 16);
            config.set("23.���", 7);
            
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
