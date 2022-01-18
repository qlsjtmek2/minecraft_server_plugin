package me.shinkhan.Finance.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Message {
	public static void CreateMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("��Ŷ ��� (���ڵ� X)", "��Ŷ���� ������ �Ұ����� ��ɾ� �Դϴ�.");
			config.set("���� ���", "&c����� ������ ��ȯ�� ���� �����մϴ�.");
			config.set("���� ���", "&c����� ������ �� ���� �����մϴ�.");
			config.set("���� ���", "&c����� ���ڸ� �� ���� �����մϴ�.");
			config.set("���� ����", "&c����&6�� �����̽��ϴ�. �� ���� &c��ȯ&6���ּ���!");
			config.set("���� ����", "&c����&6�� �ϼ̽��ϴ�. &c���� �ð�&6���� ��ٷ� �ּ���.");
			config.set("���� ����", "&c����&6�� �ϼ̽��ϴ�. &c���� �ð�&6���� ��ٷ� �ּ���.");
			config.set("���� ����", "&6���� ����� &c��ȯ&6�ϼ̽��ϴ�.");
			config.set("���� ����", "&c����&6�� ��뿡 &c����&6�� ���� ���������̽��ϴ�.");
			config.set("���� ����", "&c����&6�� ��뿡 &c����&6�� ���� ���������̽��ϴ�.");
			config.set("���� ��ȯ �ʰ�", "&6����� ���� ��ȯ �ð��� &c�ʰ� &6�Ǿ����ϴ�. &c�߰� ����&6�� �ٽ��ϴ�.");
			config.set("���� ���� �Ϸ�", "&6����� ���� �ð��� &c����&6�Ǿ����ϴ�. &e[ /���� ] &6��ɾ�� ã�ư��ּ���!");
			config.set("���� ���� �Ϸ�", "&6����� ���� �ð��� &c����&6�Ǿ����ϴ�. &e[ /���� ] &6��ɾ�� ã�ư��ּ���!");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHFinance] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getMessage(String name) {
		File f = new File("plugins/DHFinance/Message.yml");
		File folder = new File("plugins/DHFinance");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMessageConfig(f, folder, config);
		}
		
		String Message = replaceAllColors(config.getString(name));
		return Message;
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
