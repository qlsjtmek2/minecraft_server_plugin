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
		Water.add("��f�̰��� ��ȭ�� ��b��l������f��");
		Water.add("��f�÷��ֽñ� �ٶ��ϴ�.");
	}
	
	public static void setLava()
	{
        Lava.add("��f�̰��� �Ҹ��� ��a��l����f��");
        Lava.add("��f�÷��ֽñ� �ٶ��ϴ�.");
	}
	
	public static void setAnvilX()
	{
		AnvilX.add("��7��l===========================");
		AnvilX.add("��6 ��ȭ�� ��� ��7:: ��f����");
		AnvilX.add("��e ��ȭ�� ��� ��7:: ��f����");
		AnvilX.add("��a �ʿ��� ��� ��7:: ��f����");
		AnvilX.add("��b ��� ���� ��7:: ��f0��");
		AnvilX.add("��7��l===========================");
		AnvilX.add("��7- ��4��ȭ �Ұ��� ����");
	}
	
	public static void setBookOne()
	{
		BookOne.add("��7��l==================");
		BookOne.add("��61. ��f��ȭ�� ������ ���� �� �ȿ� �д�.");
		BookOne.add("��62. ��f��� ������ ������ Ȯ���ؼ� � ");
		BookOne.add("��f   ��Ḧ � ���ؾ� �ϴ��� Ȯ���Ѵ�.");
		BookOne.add("��63. ��f��Ḧ ���ؼ� ���緮�� ");
		BookOne.add("��f    ����� ��� �ȿ� �д�.");
		BookOne.add("��64. ��f��縦 Ŭ���ϰ� ������ ��ȭ�Ѵ�.");
		BookOne.add("��7��l==================");
	}
	
	public static void setBookTwo()
	{
		BookTwo.add("��7��l==================");
		BookTwo.add("��f���� ��ȭ�� ��e���� ��̡�f, ��6���� ");
		BookTwo.add("��6��̡�f�ۿ� �Ұ����մϴ�.");
		BookTwo.add("��c���� ��ȭ��f�� ���� �ֽ��ϴ�. ");
		BookTwo.add("��a���� ��ȭ��f�� ������ ");
		BookTwo.add("��f��ȭ �ý����Դϴ�.");
		BookTwo.add("��f���� ��ȭ�� �Ұ����մϴ�. ");
		BookTwo.add("��f���� ��b������f�� �����մϴ�.");
		BookTwo.add("��7��l==================");
	}
	
	public static void setBookThree()
	{
        BookThree.add("��7��l==================");
        BookThree.add("��6��ȭ ������ ������ ���");
        BookThree.add("��e���� ��� ��f-> ");
        BookThree.add("��e���� ��� ��f+1 ->");
        BookThree.add("��e���� ��� ��f+2 -> ");
        BookThree.add("��e���� ��� ��f+3 ->");
        BookThree.add("��e���� ��� ��f+4 ->");
        BookThree.add("��e���� ��� ��f+5 -> ");
        BookThree.add("��e���� ��� ��f+6 ->");
        BookThree.add("��e���� ��� ��f+7 ->");
        BookThree.add("��e���� ��� ��f+8 -> ");
        BookThree.add("��e���� ��� ��f+9 ->");
        BookThree.add("��e���� ��� ��f+10 ->");
        BookThree.add("��7��l==================");
	}
	
	public static void setBookFour()
	{
        BookFour.add("��7��l==================");
        BookFour.add("��6���� ��� ��f-> ");
        BookFour.add("��6���� ��� ��f+1 ->");
        BookFour.add("��6���� ��� ��f+2 -> ");
        BookFour.add("��6���� ��� ��f+3 ->");
        BookFour.add("��6���� ��� ��f+4 ->");
        BookFour.add("��6���� ��� ��f+5 -> ");
        BookFour.add("��6���� ��� ��f+6 ->");
        BookFour.add("��6���� ��� ��f+7 ->");
        BookFour.add("��6���� ��� ��f+8 -> ");
        BookFour.add("��6���� ��� ��f+9 ->");
        BookFour.add("��6���� ��� ��f+10 ->");
        BookFour.add("��b�����͸� ���");
        BookFour.add("��7��l==================");
	}
	
	public static void CreateGUIMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("���� ��ȭ GUI �̸�", "���� ��ȭ");
            config.set("��ȭ ����", "&a��ȭ ���� ���� &7(������ Ŭ���� ��ȭ ����)");
            config.set("��ȭ �Ұ���", "&4��ȭ �Ұ��� ����");
            config.set("�������� ����", "&f�������� ����");
            
            List<String> Alist;
            Alist = config.getStringList("��.������ ����");
            Alist.add("&f�̰��� ��ȭ�� &b&l����&f��");
            Alist.add("&f�÷��ֽñ� �ٶ��ϴ�.");
            config.set("��.������ ����", "��c��l");
            config.set("��.������ �̸�", "��,��,��,��,��,��,��,��");
            config.set("��.������ ����", Alist);
            config.set("��.������ �ڵ�", 8);
            config.set("��.������ �������ڵ�", 0);
            config.set("��.������ ����", 1);
            
            List<String> Blist;
            Blist = config.getStringList("���.������ ����");
            Blist.add("&f�̰��� �Ҹ��� &a&l���&f��");
            Blist.add("&f�÷��ֽñ� �ٶ��ϴ�.");
            config.set("���.������ ����", "��c��l");
            config.set("���.������ �̸�", "��,��,��,��,��,��,��,��");
            config.set("���.������ ����", Blist);
            config.set("���.������ �ڵ�", 10);
            config.set("���.������ �������ڵ�", 0);
            config.set("���.������ ����", 1);
            
            config.set("+.������ �̸�", "&f&l+");
            config.set("+.������ �ڵ�", 101);
            config.set("+.������ �������ڵ�", 0);
            config.set("+.������ ����", 1);
            
       		List<String> list0;
            list0 = config.getStringList("���X.������ ����");
            list0.add("&7&l===========================");
            list0.add("&6 ��ȭ�� ��� &7:: &f����");
            list0.add("&e ��ȭ�� ��� &7:: &f����");
            list0.add("&a �ʿ��� ��� &7:: &f����");
            list0.add("&b ��� ���� &7:: &f0��");
            list0.add("&7&l===========================");
            list0.add("&7- &4��ȭ �Ұ��� ����");
            config.set("���X.������ �̸�", "&f[ &6��ȭ �ϱ� &f]");
            config.set("���X.������ ����", list0);
            config.set("���X.������ �ڵ�", 145);
            config.set("���X.������ �������ڵ�", 0);
            config.set("���X.������ ����", 1);
            
       		List<String> list1;
            list1 = config.getStringList("���.������ ����");
            list1.add("&7&l===========================");
            list1.add("&6 ��ȭ�� ��� &7:: &f<itemname>");
            list1.add("&e ��ȭ�� ��� &7:: &f<nextitem>");
            list1.add("&a �ʿ��� ��� &7:: &f<material>");
            list1.add("&b ��� ���� &7:: &f<num>��");
            list1.add("&7&l===========================");
            list1.add("&7- <YESorNO>");
            config.set("���.������ �̸�", "&f[ &6��ȭ �ϱ� &f]");
            config.set("���.������ ����", list1);
            config.set("���.������ �ڵ�", 145);
            config.set("���.������ �������ڵ�", 0);
            config.set("���.������ ����", 1);

     		List<String> list2;
            list2 = config.getStringList("����1.������ ����");
            list2.add("&7&l==================");
            list2.add("&61. &f��ȭ�� ������ ���� �� �ȿ� �д�.");
            list2.add("&62. &f��� ������ ������ Ȯ���ؼ� � ");
            list2.add("&f   ��Ḧ � ���ؾ� �ϴ��� Ȯ���Ѵ�.");
            list2.add("&63. &f��Ḧ ���ؼ� ���緮�� ");
            list2.add("&f    ����� ��� �ȿ� �д�.");
            list2.add("&64. &f��縦 Ŭ���ϰ� ������ ��ȭ�Ѵ�.");
            list2.add("&7&l==================");
            config.set("����1.������ �̸�", "&e���� &6��ȭ &cTip 1.");
            config.set("����1.������ ����", list2);
            config.set("����1.������ �ڵ�", 386);
            config.set("����1.������ �������ڵ�", 0);
            config.set("����1.������ ����", 1);
            
     		List<String> list3;
            list3 = config.getStringList("����2.������ ����");
            list3.add("&7&l==================");
            list3.add("&f���� ��ȭ�� &e���� ���&f, &6���� ");
            list3.add("&6���&f�ۿ� �Ұ����մϴ�.");
            list3.add("&c���� ��ȭ&f�� ���� �ֽ��ϴ�. ");
            list3.add("&a���� ��ȭ&f�� ������ ");
            list3.add("&f��ȭ �ý����Դϴ�.");
            list3.add("&f���� ��ȭ�� �Ұ����մϴ�. ");
            list3.add("&f���� &b����&f�� �����մϴ�.");
            list3.add("&7&l==================");
            config.set("����2.������ �̸�", "&e���� &6��ȭ &cTip 2.");
            config.set("����2.������ ����", list3);
            config.set("����2.������ �ڵ�", 386);
            config.set("����2.������ �������ڵ�", 0);
            config.set("����2.������ ����", 1);
            
     		List<String> list4;
            list4 = config.getStringList("����3.������ ����");
            list4.add("&7&l==================");
            list4.add("&6��ȭ ������ ������ ���");
            list4.add("&e���� ��� &f-> ");
            list4.add("&e���� ��� &f+1 ->");
            list4.add("&e���� ��� &f+2 -> ");
            list4.add("&e���� ��� &f+3 ->");
            list4.add("&e���� ��� &f+4 ->");
            list4.add("&e���� ��� &f+5 -> ");
            list4.add("&e���� ��� &f+6 ->");
            list4.add("&e���� ��� &f+7 ->");
            list4.add("&e���� ��� &f+8 -> ");
            list4.add("&e���� ��� &f+9 ->");
            list4.add("&e���� ��� &f+10 ->");
            list4.add("&7&l==================");
            config.set("����3.������ �̸�", "&e���� &6��ȭ &cTip 3.");
            config.set("����3.������ ����", list4);
            config.set("����3.������ �ڵ�", 386);
            config.set("����3.������ �������ڵ�", 0);
            config.set("����3.������ ����", 1);
            
     		List<String> list5;
            list5 = config.getStringList("����4.������ ����");
            list5.add("&7&l==================");
            list5.add("&6���� ��� &f-> ");
            list5.add("&6���� ��� &f+1 ->");
            list5.add("&6���� ��� &f+2 -> ");
            list5.add("&6���� ��� &f+3 ->");
            list5.add("&6���� ��� &f+4 ->");
            list5.add("&6���� ��� &f+5 -> ");
            list5.add("&6���� ��� &f+6 ->");
            list5.add("&6���� ��� &f+7 ->");
            list5.add("&6���� ��� &f+8 -> ");
            list5.add("&6���� ��� &f+9 ->");
            list5.add("&6���� ��� &f+10 ->");
            list5.add("&b�����͸� ���");
            list5.add("&7&l==================");
            config.set("����4.������ �̸�", "&e���� &6��ȭ &cTip 4.");
            config.set("����4.������ ����", list5);
            config.set("����4.������ �ڵ�", 386);
            config.set("����4.������ �������ڵ�", 0);
            config.set("����4.������ ����", 1);

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
    				} else str = str.replaceAll("<itemname>", "����");
    			} else str = str.replaceAll("<itemname>", "����");
    		} else str = str.replaceAll("<itemname>", "����");
    		
    		if (p.getOpenInventory().getItem(16) != null) {
    			for (int i = 1; ; i++) {
    				if (ChatColor.stripColor(UpGrade.getUpGradeString(i + ".������ �̸�")) == null) {
    	         		str = str.replaceAll("<nextitem>", "����");
    	         		str = str.replaceAll("<material>", "����");
    	         		str = str.replaceAll("<num>", "0");
    	          		str = str.replace("<YESorNO>", getMessage(p, "��ȭ �Ұ���"));
    					break;
    				}
    				
    				if (p.getOpenInventory().getItem(10) != null) {
    					if (p.getOpenInventory().getItem(10).getItemMeta() != null) {
    						if (p.getOpenInventory().getItem(10).getItemMeta().getDisplayName() != null) {
        						if (ChatColor.stripColor(p.getOpenInventory().getItem(10).getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(UpGrade.getUpGradeString(i + ".������ �̸�")))) {
        							int z = i + 1;
        							if (ChatColor.stripColor(UpGrade.getUpGradeString(z + ".������ �̸�")) != null) str = str.replaceAll("<nextitem>", ChatColor.stripColor(UpGrade.getUpGradeString(z + ".������ �̸�")));
        							else str = str.replaceAll("<nextitem>", getMessage(p, "�������� ����"));
            						str = str.replaceAll("<material>", UpGrade.getUpGradeString(i + ".������ ���"));
            						
            						if (p.getOpenInventory().getItem(16) == null || p.getOpenInventory().getItem(16).getType() == Material.AIR) {
            							str = str.replaceAll("<num>", UpGrade.getUpGradeString(i + ".��� ����"));
            							str = str.replace("<YESorNO>", getMessage(p, "��ȭ �Ұ���"));
                						break;
            						} else {
            							if (p.getOpenInventory().getItem(16).getType() == Material.DIAMOND && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���̾Ƹ��")
            							 || p.getOpenInventory().getItem(16).getType() == Material.IRON_INGOT && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("ö")
            							 || p.getOpenInventory().getItem(16).getType() == Material.GOLD_INGOT && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��")
            							 || p.getOpenInventory().getItem(16).getType() == Material.COAL && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��ź")
            							 || p.getOpenInventory().getItem(16).getType() == Material.EMERALD && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���޶���")
            							 || p.getOpenInventory().getItem(16).getType() == Material.REDSTONE && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���彺��")
            							 || p.getOpenInventory().getItem(16).getType() == Material.LAPIS_ORE && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("û�ݼ�")
            							 || p.getOpenInventory().getItem(16).getType() == Material.QUARTZ && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�״� ����")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 42 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("ö ���")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 41 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�� ���")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 57 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���̾Ƹ�� ���")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 133 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���޶��� ���")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 152 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���彺�� ���")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 22 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("û�ݼ� ���")
		            					 || p.getOpenInventory().getItem(16).getTypeId() == 4 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��")) {
            								if (UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount() <= 0) {
                								str = str.replaceAll("<num>", "0");
                						  		str = str.replace("<YESorNO>", getMessage(p, "��ȭ ����"));
                						  		break;
            								} else {
            									str = str.replaceAll("<num>", Integer.toString(UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount()));
            									str = str.replace("<YESorNO>", getMessage(p, "��ȭ �Ұ���"));
            									break;
            								}
            							}
            							
            							else if (p.getOpenInventory().getItem(16).getTypeId() == 35 || p.getOpenInventory().getItem(16).getTypeId() == 155 || p.getOpenInventory().getItem(16).getTypeId() == 49) {
            								ItemStack i1 = p.getOpenInventory().getItem(16);
            								if (i1.getItemMeta() != null) {
            	    							if (i1.getItemMeta().getDisplayName() != null) {
            	    								if (i1.getData().getData() == 15 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("��ź ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��ź ���")
            	    								 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("�״� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�״� ���� ���")
            	    								 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("ö ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("ö ���� ���")
            	    								 || i1.getData().getData() == 4 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("�� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�� ���� ���")
            	    								 || i1.getData().getData() == 9 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("���̾Ƹ�� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���̾Ƹ�� ���� ���")
            	    								 || i1.getData().getData() == 5 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("���޶��� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���޶��� ���� ���")
            	    								 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("��ź ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��ź ���� ���")
            	    								 || i1.getData().getData() == 8 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("�״� ���� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�״� ���� ���� ���")
            	    								 || i1.getData().getData() == 14 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("���彺�� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���彺�� ���� ���")
            	    								 || i1.getData().getData() == 11 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("û�ݼ� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("û�ݼ� ���� ���")) {
                        								if (UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount() <= 0) {
                            								str = str.replaceAll("<num>", "0");
                            						  		str = str.replace("<YESorNO>", getMessage(p, "��ȭ ����"));
                            						  		break;
                        								} else {
                        									str = str.replaceAll("<num>", Integer.toString(UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount()));
                        									str = str.replace("<YESorNO>", getMessage(p, "��ȭ �Ұ���"));
                        									break;
                        								}
            	    								}
            	    							}
            								}
            							} else {
            								str = str.replaceAll("<num>", UpGrade.getUpGradeString(i + ".��� ����"));
                							str = str.replace("<YESorNO>", getMessage(p, "��ȭ �Ұ���"));
                    						break;
            							}
            						}
        						}
    						}
    					}
    				}
    			}
    		} else {
    			str = str.replaceAll("<material>", "����");
    			str = str.replace("<YESorNO>", "��4��ȭ �Ұ��� ����");
    		}

    		Returnlist.add(str);
        } return Returnlist;
	}
}
