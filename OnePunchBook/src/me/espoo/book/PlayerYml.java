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
			config.set("�Ϲ� ����.�������", false);
			config.set("�Ϲ� ����.�ݼӹ�Ʈ", false);
			config.set("�Ϲ� ����.����", false);
			config.set("�Ϲ� ����.����", false);
			config.set("�Ϲ� ����.��Ż����Ʈ", false);
			config.set("�Ϲ� ����.���߸�", false);
			config.set("�Ϲ� ����.�������÷���", false);
			config.set("�Ϲ� ����.�ǹ���", false);
			config.set("�Ϲ� ����.�Ƹ��̸���ũ", false);
			config.set("�Ϲ� ����.�����", false);
			config.set("�Ϲ� ����.�����ǼҴ�", false);
			config.set("�Ϲ� ����.���뽺", false);
			config.set("�Ϲ� ����.�����", false);
			config.set("�Ϲ� ����.���ձݰ�����", false);
			config.set("�Ϲ� ����.ŷ", false);
			config.set("�Ϲ� ����.Ÿ����Ű", false);
			config.set("�Ϲ� ����.��ũ�鸶����", false);
			config.set("�Ϲ� ����.�������", false);
			config.set("�Ϲ� ����.�ĺ�Ű", false);
			config.set("�Ϲ� ����.���� ȹ��", false);
			
			config.set("Ǯ�� ����.�������", false);
			config.set("Ǯ�� ����.�ݼӹ�Ʈ", false);
			config.set("Ǯ�� ����.����", false);
			config.set("Ǯ�� ����.����", false);
			config.set("Ǯ�� ����.��Ż����Ʈ", false);
			config.set("Ǯ�� ����.���߸�", false);
			config.set("Ǯ�� ����.�������÷���", false);
			config.set("Ǯ�� ����.�ǹ���", false);
			config.set("Ǯ�� ����.�Ƹ��̸���ũ", false);
			config.set("Ǯ�� ����.�����", false);
			config.set("Ǯ�� ����.�����ǼҴ�", false);
			config.set("Ǯ�� ����.���뽺", false);
			config.set("Ǯ�� ����.�����", false);
			config.set("Ǯ�� ����.���ձݰ�����", false);
			config.set("Ǯ�� ����.ŷ", false);
			config.set("Ǯ�� ����.Ÿ����Ű", false);
			config.set("Ǯ�� ����.��ũ�鸶����", false);
			config.set("Ǯ�� ����.�������", false);
			config.set("Ǯ�� ����.�ĺ�Ű", false);
			config.set("Ǯ�� ����.���� ȹ��", false);
			
			config.set("�Ϲ� ���.����������", false);
			config.set("�Ϲ� ���.�ݼӹ�Ʈ����", false);
			config.set("�Ϲ� ���.���Ÿ���", false);
			config.set("�Ϲ� ���.��������", false);
			config.set("�Ϲ� ���.��Ż����Ʈ����", false);
			config.set("�Ϲ� ���.���߸Ǹ���", false);
			config.set("�Ϲ� ���.�������÷��ø���", false);
			config.set("�Ϲ� ���.�ǹ��ظ���", false);
			config.set("�Ϲ� ���.�Ƹ��̸���ũ����", false);
			config.set("�Ϲ� ���.����͸���", false);
			config.set("�Ϲ� ���.�����ǼҴи���", false);
			config.set("�Ϲ� ���.���뽺����", false);
			config.set("�Ϲ� ���.����Ǹ���", false);
			config.set("�Ϲ� ���.���ձݰ���������", false);
			config.set("�Ϲ� ���.ŷ����", false);
			config.set("�Ϲ� ���.Ÿ����Ű����", false);
			config.set("�Ϲ� ���.��ũ�鸶���͸���", false);
			config.set("�Ϲ� ���.������ʸ���", false);
			config.set("�Ϲ� ���.�ĺ�Ű����", false);
			config.set("�Ϲ� ���.�������Ʃ��", false);
			config.set("�Ϲ� ���.�ݼӹ�ƮƩ��", false);
			config.set("�Ϲ� ���.����Ʃ��", false);
			config.set("�Ϲ� ���.����Ʃ��", false);
			config.set("�Ϲ� ���.��Ż����ƮƩ��", false);
			config.set("�Ϲ� ���.���߸�Ʃ��", false);
			config.set("�Ϲ� ���.�������÷���Ʃ��", false);
			config.set("�Ϲ� ���.�ǹ���Ʃ��", false);
			config.set("�Ϲ� ���.�Ƹ��̸���ũƩ��", false);
			config.set("�Ϲ� ���.�����Ʃ��", false);
			config.set("�Ϲ� ���.�����ǼҴ�Ʃ��", false);
			config.set("�Ϲ� ���.���뽺Ʃ��", false);
			config.set("�Ϲ� ���.�����Ʃ��", false);
			config.set("�Ϲ� ���.���ձݰ�����Ʃ��", false);
			config.set("�Ϲ� ���.ŷƩ��", false);
			config.set("�Ϲ� ���.Ÿ����ŰƩ��", false);
			config.set("�Ϲ� ���.��ũ�鸶����Ʃ��", false);
			config.set("�Ϲ� ���.�������Ʃ��", false);
			config.set("�Ϲ� ���.�ĺ�ŰƩ��", false);
			config.set("�Ϲ� ���.����������", false);
			config.set("�Ϲ� ���.�ݼӹ�Ʈ����", false);
			config.set("�Ϲ� ���.���Ź���", false);
			config.set("�Ϲ� ���.��������", false);
			config.set("�Ϲ� ���.��Ż����Ʈ����", false);
			config.set("�Ϲ� ���.���߸ǹ���", false);
			config.set("�Ϲ� ���.�������÷��ù���", false);
			config.set("�Ϲ� ���.�ǹ��ع���", false);
			config.set("�Ϲ� ���.�Ƹ��̸���ũ����", false);
			config.set("�Ϲ� ���.����͹���", false);
			config.set("�Ϲ� ���.�����ǼҴй���", false);
			config.set("�Ϲ� ���.���뽺����", false);
			config.set("�Ϲ� ���.����ǹ���", false);
			config.set("�Ϲ� ���.���ձݰ���������", false);
			config.set("�Ϲ� ���.ŷ����", false);
			config.set("�Ϲ� ���.Ÿ����Ű����", false);
			config.set("�Ϲ� ���.��ũ�鸶���͹���", false);
			config.set("�Ϲ� ���.������ʹ���", false);
			config.set("�Ϲ� ���.�ĺ�Ű����", false);
			config.set("�Ϲ� ���.�������Ź�", false);
			config.set("�Ϲ� ���.�ݼӹ�Ʈ�Ź�", false);
			config.set("�Ϲ� ���.���ŽŹ�", false);
			config.set("�Ϲ� ���.�����Ź�", false);
			config.set("�Ϲ� ���.��Ż����Ʈ�Ź�", false);
			config.set("�Ϲ� ���.���߸ǽŹ�", false);
			config.set("�Ϲ� ���.�������÷��ýŹ�", false);
			config.set("�Ϲ� ���.�ǹ��ؽŹ�", false);
			config.set("�Ϲ� ���.�Ƹ��̸���ũ�Ź�", false);
			config.set("�Ϲ� ���.����ͽŹ�", false);
			config.set("�Ϲ� ���.�����ǼҴнŹ�", false);
			config.set("�Ϲ� ���.���뽺�Ź�", false);
			config.set("�Ϲ� ���.����ǽŹ�", false);
			config.set("�Ϲ� ���.���ձݰ������Ź�", false);
			config.set("�Ϲ� ���.ŷ�Ź�", false);
			config.set("�Ϲ� ���.Ÿ����Ű�Ź�", false);
			config.set("�Ϲ� ���.��ũ�鸶���ͽŹ�", false);
			config.set("�Ϲ� ���.������ʽŹ�", false);
			config.set("�Ϲ� ���.�ĺ�Ű�Ź�", false);
			config.set("�Ϲ� ���.���� ȹ��", false);
			
			config.set("����ũ ���.����������", false);
			config.set("����ũ ���.�ݼӹ�Ʈ����", false);
			config.set("����ũ ���.���Ÿ���", false);
			config.set("����ũ ���.��������", false);
			config.set("����ũ ���.��Ż����Ʈ����", false);
			config.set("����ũ ���.���߸Ǹ���", false);
			config.set("����ũ ���.�������÷��ø���", false);
			config.set("����ũ ���.�ǹ��ظ���", false);
			config.set("����ũ ���.�Ƹ��̸���ũ����", false);
			config.set("����ũ ���.����͸���", false);
			config.set("����ũ ���.�����ǼҴи���", false);
			config.set("����ũ ���.���뽺����", false);
			config.set("����ũ ���.����Ǹ���", false);
			config.set("����ũ ���.���ձݰ���������", false);
			config.set("����ũ ���.ŷ����", false);
			config.set("����ũ ���.Ÿ����Ű����", false);
			config.set("����ũ ���.��ũ�鸶���͸���", false);
			config.set("����ũ ���.������ʸ���", false);
			config.set("����ũ ���.�ĺ�Ű����", false);
			config.set("����ũ ���.�������Ʃ��", false);
			config.set("����ũ ���.�ݼӹ�ƮƩ��", false);
			config.set("����ũ ���.����Ʃ��", false);
			config.set("����ũ ���.����Ʃ��", false);
			config.set("����ũ ���.��Ż����ƮƩ��", false);
			config.set("����ũ ���.���߸�Ʃ��", false);
			config.set("����ũ ���.�������÷���Ʃ��", false);
			config.set("����ũ ���.�ǹ���Ʃ��", false);
			config.set("����ũ ���.�Ƹ��̸���ũƩ��", false);
			config.set("����ũ ���.�����Ʃ��", false);
			config.set("����ũ ���.�����ǼҴ�Ʃ��", false);
			config.set("����ũ ���.���뽺Ʃ��", false);
			config.set("����ũ ���.�����Ʃ��", false);
			config.set("����ũ ���.���ձݰ�����Ʃ��", false);
			config.set("����ũ ���.ŷƩ��", false);
			config.set("����ũ ���.Ÿ����ŰƩ��", false);
			config.set("����ũ ���.��ũ�鸶����Ʃ��", false);
			config.set("����ũ ���.�������Ʃ��", false);
			config.set("����ũ ���.�ĺ�ŰƩ��", false);
			config.set("����ũ ���.����������", false);
			config.set("����ũ ���.�ݼӹ�Ʈ����", false);
			config.set("����ũ ���.���Ź���", false);
			config.set("����ũ ���.��������", false);
			config.set("����ũ ���.��Ż����Ʈ����", false);
			config.set("����ũ ���.���߸ǹ���", false);
			config.set("����ũ ���.�������÷��ù���", false);
			config.set("����ũ ���.�ǹ��ع���", false);
			config.set("����ũ ���.�Ƹ��̸���ũ����", false);
			config.set("����ũ ���.����͹���", false);
			config.set("����ũ ���.�����ǼҴй���", false);
			config.set("����ũ ���.���뽺����", false);
			config.set("����ũ ���.����ǹ���", false);
			config.set("����ũ ���.���ձݰ���������", false);
			config.set("����ũ ���.ŷ����", false);
			config.set("����ũ ���.Ÿ����Ű����", false);
			config.set("����ũ ���.��ũ�鸶���͹���", false);
			config.set("����ũ ���.������ʹ���", false);
			config.set("����ũ ���.�ĺ�Ű����", false);
			config.set("����ũ ���.�������Ź�", false);
			config.set("����ũ ���.�ݼӹ�Ʈ�Ź�", false);
			config.set("����ũ ���.���ŽŹ�", false);
			config.set("����ũ ���.�����Ź�", false);
			config.set("����ũ ���.��Ż����Ʈ�Ź�", false);
			config.set("����ũ ���.���߸ǽŹ�", false);
			config.set("����ũ ���.�������÷��ýŹ�", false);
			config.set("����ũ ���.�ǹ��ؽŹ�", false);
			config.set("����ũ ���.�Ƹ��̸���ũ�Ź�", false);
			config.set("����ũ ���.����ͽŹ�", false);
			config.set("����ũ ���.�����ǼҴнŹ�", false);
			config.set("����ũ ���.���뽺�Ź�", false);
			config.set("����ũ ���.����ǽŹ�", false);
			config.set("����ũ ���.���ձݰ������Ź�", false);
			config.set("����ũ ���.ŷ�Ź�", false);
			config.set("����ũ ���.Ÿ����Ű�Ź�", false);
			config.set("����ũ ���.��ũ�鸶���ͽŹ�", false);
			config.set("����ũ ���.������ʽŹ�", false);
			config.set("����ũ ���.�ĺ�Ű�Ź�", false);
			config.set("����ũ ���.���� ȹ��", false);
			
			config.set("�Ϲ� ��.�ҼӼ�", false);
			config.set("�Ϲ� ��.�ٶ��Ӽ�", false);
			config.set("�Ϲ� ��.ġ���Ӽ�", false);
			config.set("�Ϲ� ��.��ҼӼ�", false);
			config.set("�Ϲ� ��.���Ӽ�", false);
			config.set("�Ϲ� ��.���мӼ�", false);
			config.set("�Ϲ� ��.�����Ӽ�", false);
			config.set("�Ϲ� ��.���� ȹ��", false);
			
			config.set("Ǯ�� ��.�ҼӼ�", false);
			config.set("Ǯ�� ��.�ٶ��Ӽ�", false);
			config.set("Ǯ�� ��.ġ���Ӽ�", false);
			config.set("Ǯ�� ��.��ҼӼ�", false);
			config.set("Ǯ�� ��.���Ӽ�", false);
			config.set("Ǯ�� ��.���мӼ�", false);
			config.set("Ǯ�� ��.�����Ӽ�", false);
			config.set("Ǯ�� ��.���� ȹ��", false);
			
			config.set("����.����0", false);
			config.set("����.����1", false);
			config.set("����.����2", false);
			config.set("����.����3", false);
			config.set("����.����4", false);
			config.set("����.����5", false);
			config.set("����.����6", false);
			config.set("����.����7", false);
			config.set("����.����8", false);
			config.set("����.����9", false);
			config.set("����.����10", false);
			config.set("����.����0", false);
			config.set("����.����1", false);
			config.set("����.����2", false);
			config.set("����.����3", false);
			config.set("����.����4", false);
			config.set("����.����5", false);
			config.set("����.����6", false);
			config.set("����.����7", false);
			config.set("����.����8", false);
			config.set("����.����9", false);
			config.set("����.����10", false);
			config.set("����.�����͸�", false);
			config.set("����.���� ȹ��", false);
			
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
