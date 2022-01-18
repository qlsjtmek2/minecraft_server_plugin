package me.espoo.upgrade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main  extends JavaPlugin implements Listener {
	public static ItemStack gang1;
	public static ItemStack gang2;
	public static ItemStack gang3;
	  
	public void onEnable()
	{
		Waepon.setWaepon();
		GUIMessage.setAnvilX();
		GUIMessage.setBookFour();
		GUIMessage.setBookOne();
		GUIMessage.setBookThree();
		GUIMessage.setBookTwo();
		GUIMessage.setLava();
		GUIMessage.setWater();
		
		File folder = new File("plugins/OnePunchUpGrade");
		File GF = new File("plugins/OnePunchUpGrade/GUIMessage.yml");
		File UF = new File("plugins/OnePunchUpGrade/UpGrade.yml");
		File WF = new File("plugins/OnePunchUpGrade/Waepon.yml");
		File CF = new File("plugins/OnePunchUpGrade/Chance.yml");
		YamlConfiguration Gconfig = YamlConfiguration.loadConfiguration(GF);
		YamlConfiguration Uconfig = YamlConfiguration.loadConfiguration(UF);
		YamlConfiguration Wconfig = YamlConfiguration.loadConfiguration(WF);
		YamlConfiguration Cconfig = YamlConfiguration.loadConfiguration(CF);
		if (!GF.exists()) GUIMessage.CreateGUIMessageConfig(GF, folder, Gconfig);
		if (!UF.exists()) UpGrade.CreateUpGradeConfig(UF, folder, Uconfig);
		if (!WF.exists()) Waepon.CreateWaeponConfig(WF, folder, Wconfig);
		if (!CF.exists()) Chance.CreateChanceConfig(CF, folder, Cconfig);
		
	    gang1 = new ItemStack(Material.PAPER);
	    ItemMeta gm1 = main.gang1.getItemMeta();
	    gm1.setDisplayName("��c��l��  ��f�Ϲ� ��6��ȭ �ֹ���  ��c��l��");
	    List<String> list1 = new ArrayList<String>();
	    list1.add("��7��l=================");
	    list1.add("��f����� ��ȭ �ֹ����̴�.");
	    list1.add("��f�� �������� ��� ��ȭ");
	    list1.add("��f�ϰ� ���� �����ۿ� �÷�");
	    list1.add("��f�θ� �� �������� ��ȭ�ȴ�.");
	    list1.add("��7��l=================");
	    gm1.setLore(list1);
	    gang1.setItemMeta(gm1);
	    
	    gang2 = new ItemStack(Material.PAPER);
	    ItemMeta gm2 = main.gang2.getItemMeta();
	    gm2.setDisplayName("��c��l��  ��bƯ�� ��6��ȭ �ֹ���  ��c��l��");
	    List<String> list2 = new ArrayList<String>();
	    list2.add("��7��l=================");
	    list2.add("��fƯ���� ��ȭ �ֹ����̴�.");
	    list2.add("��f�� �������� ��� ��ȭ");
	    list2.add("��f�ϰ� ���� �����ۿ� �÷�");
	    list2.add("��f�θ� �� �������� ��ȭ�ȴ�.");
	    list2.add("");
	    list2.add("��7Ư�� �ɷ�: ��f��ȭ �� ����");
	    list2.add("��f���� ��c�ı���f���� �ʴ´�.");
	    list2.add("��7��l=================");
	    gm2.setLore(list2);
	    gang2.setItemMeta(gm2);
	    
	    gang3 = new ItemStack(Material.PAPER);
	    ItemMeta gm3 = main.gang3.getItemMeta();
	    gm3.setDisplayName("��4��l��  ��d�����̾� ��6��ȭ �ֹ���  ��4��l��");
	    List<String> list3 = new ArrayList<String>();
	    list3.add("��7��l=================");
	    list3.add("��f�����̾� ��ȭ �ֹ����̴�.");
	    list3.add("��f�� �������� ��� ��ȭ");
	    list3.add("��f�ϰ� ���� �����ۿ� �÷�");
	    list3.add("��f�θ� �� �������� ��ȭ�ȴ�.");
	    list3.add("");
	    list3.add("��7Ư�� �ɷ�: ��f��ȭ �� ����");
	    list3.add("��f���� ��c�ı���f���� �ʰ�, ��ȭ");
	    list3.add("��f���� Ȯ���� ���� ��b��¡�f�Ѵ�.");
	    list3.add("��7��l=================");
	    gm3.setLore(list3);
	    gang3.setItemMeta(gm3);
		
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("�ֹ�������").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.closeInventory();
		}

		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static ItemStack getUpGrade() {
		ItemStack item = new ItemStack(Material.PAPER);
	    ItemMeta gm4 = item.getItemMeta();
	    gm4.setDisplayName("��a��l��  ��b<num>�� ��6��ȭ �ֹ���  ��a��l��");
	    List<String> list4 = new ArrayList<String>();
	    list4.add("��7��l=================");
	    list4.add("��f���⳪ ���� ��ȭ ����");
	    list4.add("��f������ <num>�� ���� ������");
	    list4.add("��f�� ��ȭ �ֹ����̴�.");
	    list4.add("");
	    list4.add("��f�� �������� ��� ��ȭ");
	    list4.add("��f�ϰ� ���� �����ۿ� �÷�");
	    list4.add("��f�θ� �� �������� ��ȭ�ȴ�.");
	    list4.add("��7��l=================");
	    gm4.setLore(list4);
	    item.setItemMeta(gm4);
		return item;
	}
}
