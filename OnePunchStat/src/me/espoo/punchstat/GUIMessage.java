package me.espoo.punchstat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.espoo.file.PlayerYml;

public class GUIMessage {
	static PlayerYml P;
	
	public static void CreateGUIMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("���� GUI �̸�", "����Ÿ�� ����");

     		List<String> list1;
            list1 = config.getStringList("�� �ٷ�.������ ����");
            list1.add("&7&l===========================");
            list1.add("&f���� �տ� �ΰ� ��Ŭ���� �Ͽ� �ȱ���");
            list1.add("&f��� �ڼ��� ���ϰ� &b100�� &f�����ϸ�");
            list1.add("&f���ݷ��� &c0.3 &f�� ����Ѵ�.");
            list1.add("&7&l===========================");
            list1.add("&f");
            list1.add("&6���� ����� &c�� �ٷ� &6������ &c<1stat> &6�Դϴ�.");
            list1.add("&6����� &c<1effect> &6��ŭ�� &c���ݷ� &6ȿ���� ���ϴ�.");
            list1.add("&6�� �ٷ� ���� ���� ���δ� &c<1true> &6�Դϴ�.");
            config.set("�� �ٷ�.������ �̸�", "&c�� �ٷ� &6����");
            config.set("�� �ٷ�.������ ����", list1);
            config.set("�� �ٷ�.������ �ڵ�", 276);
            config.set("�� �ٷ�.������ �������ڵ�", 0);
            config.set("�� �ٷ�.������ ����", 1);

     		List<String> list2;
            list2 = config.getStringList("����.������ ����");
            list2.add("&7&l===========================");
            list2.add("&f����Ʈ�� ���� ���¿��� ������ &b100��");
            list2.add("&f�����ϸ� ���� ����Ű�� ȿ���� ����");
            list2.add("&f������ ��ȭ�Ǿ� ü���� &c0.5 &f�� ����Ѵ�.");
            list2.add("&7&l===========================");
            list2.add("&f");
            list2.add("&6���� ����� &c���� &6������ &c<2stat> &6�Դϴ�.");
            list2.add("&6����� &c<2effect> &6��ŭ�� &cü�� &6ȿ���� ���ϴ�.");
            list2.add("&6���� ���� ���� ���δ� &c<2true> &6�Դϴ�.");
            config.set("����.������ �̸�", "&d���� &6����");
            config.set("����.������ ����", list2);
            config.set("����.������ �ڵ�", 311);
            config.set("����.������ �������ڵ�", 0);
            config.set("����.������ ����", 1);
            
     		List<String> list3;
            list3 = config.getStringList("�ٸ� �ٷ�.������ ����");
            list3.add("&7&l===========================");
            list3.add("&f����ƮŰ�� &b100�� &f�����ϸ� ����Ʈ");
            list3.add("&f100���� ȿ���� ��� 10���ݴ�");
            list3.add("&f���� ��ȭ�� &cI &f�� ����Ѵ�.");
            list3.add("&7&l===========================");
            list3.add("&f");
            list3.add("&6���� ����� &c�ٸ� �ٷ� &6������ &c<3stat> &6�Դϴ�.");
            list3.add("&6����� &c<3effect> &6��ŭ�� &b���� ��ȭ &6ȿ���� ���ϴ�.");
            list3.add("&6�ٸ� �ٷ� ���� ���� ���δ� &c<3true> &6�Դϴ�.");
            config.set("�ٸ� �ٷ�.������ �̸�", "&b�ٸ� �ٷ� &6����");
            config.set("�ٸ� �ٷ�.������ ����", list3);
            config.set("�ٸ� �ٷ�.������ �ڵ�", 312);
            config.set("�ٸ� �ٷ�.������ �������ڵ�", 0);
            config.set("�ٸ� �ٷ�.������ ����", 1);
            
     		List<String> list4;
            list4 = config.getStringList("���ǵ�.������ ����");
            list4.add("&7&l===========================");
            list4.add("&f���ǵ�� ���� &b1000ĭ &f�̵��ϸ�");
            list4.add("&f���� 10km�� ȿ���� ��� ���ǵ尡");
            list4.add("&c0.005 &f�� ����Ѵ�.");
            list4.add("&7&l===========================");
            list4.add("&f");
            list4.add("&6���� ����� &c���ǵ� &6������ &c<4stat>&6/&c200 &6�Դϴ�.");
            list4.add("&6����� &c<4effect> &6��ŭ�� &a���ǵ� &6ȿ���� ���ϴ�.");
            list4.add("&6���ǵ� ���� ���� ���δ� &c<4true> &6�Դϴ�.");
            config.set("���ǵ�.������ �̸�", "&a���ǵ� &6����");
            config.set("���ǵ�.������ ����", list4);
            config.set("���ǵ�.������ �ڵ�", 313);
            config.set("���ǵ�.������ �������ڵ�", 0);
            config.set("���ǵ�.������ ����", 1);
            
     		List<String> list5;
            list5 = config.getStringList("����.������ ����");
            list5.add("&6����Ÿ���� &c&l�ϵ��ھ� &6Ʈ���̴�");
            list5.add("&63�� ���� &c������ &6�غ���!");
            list5.add("&f");
            list5.add("&71. &f�ȱ������ 100ȸ");
            list5.add("&72. &f���� ����Ű�� 100ȸ");
            list5.add("&73. &f����Ʈ 100ȸ");
            list5.add("&74. &f���� 10km");
            config.set("����.������ �̸�", "&eƮ���̴� ���");
            config.set("����.������ ����", list5);
            config.set("����.������ �ڵ�", 386);
            config.set("����.������ �������ڵ�", 0);
            config.set("����.������ ����", 1);
            
     		List<String> list6;
            list6 = config.getStringList("����2.������ ����");
            list6.add("&f[&c�� �ٷ�&f] &6���� ���� ��ġ &f- &c200 ����");
            list6.add("&f[&c�� �ٷ�&f] &6���� ��ġ &f- &c500 ����");
            list6.add("&f[&d����&f] &6���� ��ġ�� &f- &c200 ����");
            list6.add("&f[&d����&f] &6���� ��ȭ &f- &c500 ����");
            list6.add("&f[&b�ٷ� ��ȭ&f] &6���� ���� &f- &c200 ����");
            list6.add("&f[&b�ٷ� ��ȭ&f] &6���� ��ű &f- &c500 ����");
            list6.add("&f[&a���ǵ�&f] &6���� �¿� �ݺ��ٱ� &f- &c100 ����");
            list6.add("&f[&a���ǵ�&f] &6���� ���� &f- &c200 ����");
            config.set("����2.������ �̸�", "&e��ų ȹ�� ���");
            config.set("����2.������ ����", list6);
            config.set("����2.������ �ڵ�", 386);
            config.set("����2.������ �������ڵ�", 0);
            config.set("����2.������ ����", 1);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchStat] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getMessage(Player p, String name) {
		File f = new File("plugins/OnePunchStat/GUIMessage.yml");
		File folder = new File("plugins/OnePunchStat");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		String Message = config.getString(name);
		Message = Method.replaceAllColors(Message);
		return Message;
	}
	
	public static int getItemCode(String name) {
		File f = new File("plugins/OnePunchStat/GUIMessage.yml");
		File folder = new File("plugins/OnePunchStat");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		int Code = config.getInt(name);
		return Code;
	}
	
	@SuppressWarnings("static-access")
	public static List<String> getLoreList(Player p, String name) {
		File f = new File("plugins/OnePunchStat/GUIMessage.yml");
		File folder = new File("plugins/OnePunchStat");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		str = str.replaceAll("<1stat>", Integer.toString(Method.get1Stat(p) + P.getInfoInt(p, "�� �ٷ�") + Method.get1StatEffect(p) + Method.getLoon(p)));
    		str = str.replaceAll("<2stat>", Integer.toString(Method.get2Stat(p) + P.getInfoInt(p, "����") + Method.get2StatEffect(p) + Method.getLoon(p)));
    		str = str.replaceAll("<3stat>", Integer.toString(Method.get3Stat(p) + P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3StatEffect(p) + Method.getLoon(p)));
    		if (Method.get4Stat(p) + P.getInfoInt(p, "���ǵ�") > 200) {
        		str = str.replaceAll("<4stat>", Integer.toString(200));
    		} else {
        		str = str.replaceAll("<4stat>", Integer.toString(Method.get4Stat(p) + P.getInfoInt(p, "���ǵ�") + Method.get4StatEffect(p) + Method.getLoon(p)));
    		}
    		str = str.replaceAll("<1true>", String.valueOf(P.getInfoBoolean(p, "�� �ٷ� ���� ����")));
    		str = str.replaceAll("<2true>", String.valueOf(P.getInfoBoolean(p, "���� ���� ����")));
    		str = str.replaceAll("<3true>", String.valueOf(P.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")));
    		str = str.replaceAll("<4true>", String.valueOf(P.getInfoBoolean(p, "���ǵ� ���� ����")));
    		double i = (P.getInfoInt(p, "�� �ٷ�") + Method.get1Stat(p) + Method.get1StatEffect(p) + Method.getLoon(p)) * 0.3;
    		str = str.replaceAll("<1effect>", Double.toString(i));
    		i = (P.getInfoInt(p, "����") + Method.get2Stat(p) + Method.get2StatEffect(p) + Method.getLoon(p)) * 0.6;
    		str = str.replaceAll("<2effect>", Double.toString(i));
    		int y = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
    		str = str.replaceAll("<3effect>", Integer.toString(y));
    		i = (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) * 0.005;
    		if (i < 0.2) {
    			str = str.replaceAll("<4effect>", "�⺻");
    		} else {
    			if (i > 1) {
        			str = str.replaceAll("<4effect>", Double.toString(1D));
    			} else {
        			str = str.replaceAll("<4effect>", Double.toString(i));
    			}
    		}
    		Returnlist.add(str);
        } return Returnlist;
	}
}
