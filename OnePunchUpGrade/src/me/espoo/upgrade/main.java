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
	    gm1.setDisplayName("§c§l■  §f일반 §6강화 주문서  §c§l■");
	    List<String> list1 = new ArrayList<String>();
	    list1.add("§7§l=================");
	    list1.add("§f평범한 강화 주문서이다.");
	    list1.add("§f이 아이템을 들고 강화");
	    list1.add("§f하고 싶은 아이템에 올려");
	    list1.add("§f두면 그 아이템이 강화된다.");
	    list1.add("§7§l=================");
	    gm1.setLore(list1);
	    gang1.setItemMeta(gm1);
	    
	    gang2 = new ItemStack(Material.PAPER);
	    ItemMeta gm2 = main.gang2.getItemMeta();
	    gm2.setDisplayName("§c§l■  §b특별 §6강화 주문서  §c§l■");
	    List<String> list2 = new ArrayList<String>();
	    list2.add("§7§l=================");
	    list2.add("§f특별한 강화 주문서이다.");
	    list2.add("§f이 아이템을 들고 강화");
	    list2.add("§f하고 싶은 아이템에 올려");
	    list2.add("§f두면 그 아이템이 강화된다.");
	    list2.add("");
	    list2.add("§7특수 능력: §f강화 할 아이");
	    list2.add("§f템이 §c파괴§f되지 않는다.");
	    list2.add("§7§l=================");
	    gm2.setLore(list2);
	    gang2.setItemMeta(gm2);
	    
	    gang3 = new ItemStack(Material.PAPER);
	    ItemMeta gm3 = main.gang3.getItemMeta();
	    gm3.setDisplayName("§4§l■  §d프리미엄 §6강화 주문서  §4§l■");
	    List<String> list3 = new ArrayList<String>();
	    list3.add("§7§l=================");
	    list3.add("§f프리미엄 강화 주문서이다.");
	    list3.add("§f이 아이템을 들고 강화");
	    list3.add("§f하고 싶은 아이템에 올려");
	    list3.add("§f두면 그 아이템이 강화된다.");
	    list3.add("");
	    list3.add("§7특수 능력: §f강화 할 아이");
	    list3.add("§f템이 §c파괴§f되지 않고, 강화");
	    list3.add("§f성공 확률이 대폭 §b상승§f한다.");
	    list3.add("§7§l=================");
	    gm3.setLore(list3);
	    gang3.setItemMeta(gm3);
		
		getCommand("도구").setExecutor(new mainCommand(this));
		getCommand("주문서지급").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.closeInventory();
		}

		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public static ItemStack getUpGrade() {
		ItemStack item = new ItemStack(Material.PAPER);
	    ItemMeta gm4 = item.getItemMeta();
	    gm4.setDisplayName("§a§l■  §b<num>강 §6강화 주문서  §a§l■");
	    List<String> list4 = new ArrayList<String>();
	    list4.add("§7§l=================");
	    list4.add("§f무기나 룬의 강화 수를");
	    list4.add("§f무조건 <num>강 으로 맞춰주");
	    list4.add("§f는 강화 주문서이다.");
	    list4.add("");
	    list4.add("§f이 아이템을 들고 강화");
	    list4.add("§f하고 싶은 아이템에 올려");
	    list4.add("§f두면 그 아이템이 강화된다.");
	    list4.add("§7§l=================");
	    gm4.setLore(list4);
	    item.setItemMeta(gm4);
		return item;
	}
}
