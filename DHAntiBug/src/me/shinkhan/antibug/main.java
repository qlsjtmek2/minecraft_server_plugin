package me.shinkhan.antibug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import me.shinkhan.antibug.yml.OnlyKRYml;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener, PluginMessageListener {
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ";
	public static HashMap<EntityType, Material> hm = new HashMap<EntityType, Material>();
	public static HashMap<String, Boolean> isProxy = new HashMap<String, Boolean>();
	public static HashMap<Player, String> chatSave = new HashMap<Player, String>();
	public static ArrayList<Material> storagelist = new ArrayList<Material>();
	protected static Logger log = Logger.getLogger("Minecraft");
    public static String[] cantuse = null;
	public static Economy economy = null;
	public static List<String> badwards;
	public static List<String> allowIP;
	public static List<String> allowOPIP;
	public static main plugin;
	
	public void onEnable()
	{
	    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
	    	public void run() {
	    		for (Player p : Bukkit.getOnlinePlayers()) {
	    			ItemStack[] arrayofItemStack;
	    			int j = (arrayofItemStack = p.getInventory().getContents()).length;
	    			for (int i = 0; i < j; i++)
	    				if (arrayofItemStack[i] != null) {
	    					ItemStack IS = arrayofItemStack[i];
	    					if ((IS.getAmount() > 0) && (IS.getAmount() <= 64)) continue; p.getInventory().clear(i);
	    					API.announceop(p);
	    				}
	    		}
	    	}
	    }, 0L, 5L);
	    
		File folder = new File("plugins/DHAntiBug");
		File F1 = new File("plugins/DHAntiBug/OnlyKR.yml");
		File F2 = new File("plugins/DHAntiBug/BadWards.yml");
		YamlConfiguration C1 = YamlConfiguration.loadConfiguration(F1);
		YamlConfiguration C2 = YamlConfiguration.loadConfiguration(F2);
		if (!F1.exists()) OnlyKRYml.CreateOnlyKRYml(F1, folder, C1);
		if (!F2.exists()) Config.CreateConfig(F2, folder, C2);
		
		allowIP = OnlyKRYml.getList("��� �ؿ� ������");
		allowOPIP = OnlyKRYml.getList("��� ���ǰ��� ������");
		badwards = Config.getList("BadWards");
	    plugin = this;
        protectfile();
        ProcessRpg();
        putHashAndList();
        getServer().getPluginManager().registerEvents(new Event(this), this);
        getServer().getPluginManager().registerEvents((Listener)new SkriptGUI(), (Plugin)this);
        getServer().getMessenger().registerIncomingPluginChannel(this, "WDL|INIT", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "WDL|CONTROL");
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "WDL|INIT");
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "WDL|CONTROL");
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (channel.equals("WDL|INIT") && !player.hasPermission("antiwdl.bypass")) {
            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "kick " + player.getName() + " ��4WDL is not authorized on this server!");
        }
    }
    
    public void putHashAndList() {
    	hm.put(EntityType.MINECART_CHEST, Material.STORAGE_MINECART);
		hm.put(EntityType.MINECART_HOPPER, Material.HOPPER_MINECART);
		hm.put(EntityType.MINECART_FURNACE, Material.POWERED_MINECART);
		hm.put(EntityType.BOAT, Material.BOAT);
		
		storagelist.add(Material.CHEST);
	    storagelist.add(Material.FURNACE);
	    storagelist.add(Material.HOPPER);
	    storagelist.add(Material.ANVIL);
	    storagelist.add(Material.BURNING_FURNACE);
	    storagelist.add(Material.TRAPPED_CHEST);
	    storagelist.add(Material.DROPPER);
	    storagelist.add(Material.ENCHANTMENT_TABLE);
	    storagelist.add(Material.DISPENSER);
	    storagelist.add(Material.BEACON);
	    storagelist.add(Material.BREWING_STAND);
    }
	
	public static void protectfile()
	{
		String a = "this,playername,playerdisplayname,playerlistname,playerprefix,playersuffix,helditemname,";
		a = a + "helditemdisplayname,itemid,playerloc,triggerloc,issneaking,issprinting,health,worldname,";
	    a = a + "biome,gamemode,cmdname,cmdargcount,cmdline,cmdarg,whodied,killedbyplayer,killername,blockid,";
	    a = a + "blockdata,blocktype,entitytype,entityname,weather,worldto,worldfrom,sneaking,sprinting,";
	    a = a + "flying,areaentered,areaexited,chatline,chatwordcount,onlineplayeramount,chatword,";
	    a = a + "haspermission,haspotioneffect,currentloc,random0to,random1to,health,issneaking,";
	    a = a + "issprinting,totalexp,relativeloc,hasmoney,givemoney,takemoney,isblocktype,";
	    a = a + "distance,startswith,endswith,direction,secondticks,getarea,hour,min,getblocklos,var,";
	    a = a + "getchar,hasitem,takeitem,giveitem,uuid,food,saturation,playeruuid,playerloc,holdingitem,";
	    a = a + "clickedslot,clickeditem,clickeditemname,inventorytitle,clickeditemlore,eval,systemtime,signtext,sn";
	    Bukkit.getConsoleSender().sendMessage(prefix + "��c������ ������ �����Ǿ����ϴ�.");
	    a = a.replace("null", "");
	    cantuse = a.split(",");
	}
	
	private void ProcessRpg()
	{
		if (API.CheckFindRpgItem(Bukkit.getPluginManager())) {
			Plugin rpgplugin = Bukkit.getPluginManager().getPlugin("RPG Items");
			
			if (rpgplugin == null) {
	    	  API.LogInfo("��� - RPG Item �÷������� ���������� �ʾ�, ���� ��ɸ� ���� �˴ϴ�!");
			} else {
				 API.LogInfo("�ȳ� - " + rpgplugin.toString() + " ���������");

				PlayerPickupItemEvent.getHandlerList().unregister(rpgplugin);
			}
		} else {
	    	 API.LogInfo("��� - RPG Item �÷������� ���� ���� �ʾ�, ���� ��ɸ� ���� �˴ϴ�!");
	    }
	}
	
	public void PatternDetectFileWrite(String msg)
	{
		try {
			DateFormat datePrefixFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date date_Prefix = new Date();

			FileWriter fs = new FileWriter(new File(getDataFolder().getAbsolutePath(), datePrefixFormat.format(date_Prefix) + "_��������.log"), true);
			BufferedWriter wf = new BufferedWriter(fs);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
			Date date = new Date();

			String message = msg + "\r\n";
			wf.append(dateFormat.format(date) + msg);
			message = message.replaceAll("Item Copy Protect", "");
			API.SendOperator(message);

			wf.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("allowIP") || commandLabel.equalsIgnoreCase("���������")) {
				if (sender.isOp()) {
					if (args.length != 1) {
						sender.sendMessage(ChatColor.GOLD + "/allowIP <IP>" + ChatColor.WHITE + " - Ư�� �ؿ� �ƾ��� ������ ����մϴ�.");
						sender.sendMessage(ChatColor.GOLD + "/��������� <IP>" + ChatColor.WHITE + " - Ư�� �ؿ� �ƾ��� ������ ����մϴ�.");
						return false;
					}
					
					if (args[0].equalsIgnoreCase("reload")) {
						allowIP = OnlyKRYml.getList("��� �ؿ� ������");
						sender.sendMessage(ChatColor.GOLD + "���������� ���ε��߽��ϴ�.");
						return false;
					}
					
					API.addAllowIP(args[0]);
					sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + args[0] + ChatColor.GOLD + " �ؿ� ������ ������ " + ChatColor.RED + "���" + ChatColor.GOLD + "�Ͽ����ϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
		} 
		try {
			if (commandLabel.equalsIgnoreCase("allowOpIP") || commandLabel.equalsIgnoreCase("���Ǿ��������")) {
				if (sender.isOp()) {
					if (args.length != 1) {
						sender.sendMessage(ChatColor.GOLD + "/allowOpIP <IP>" + ChatColor.WHITE + " - ���� ���� ������ ����մϴ�.");
						sender.sendMessage(ChatColor.GOLD + "/���Ǿ�������� <IP>" + ChatColor.WHITE + " - ���� ���� ������ ����մϴ�.");
						return false;
					}
					
					if (args[0].equalsIgnoreCase("reload")) {
						allowOPIP = OnlyKRYml.getList("��� ���ǰ��� ������");
						sender.sendMessage(ChatColor.GOLD + "���������� ���ε��߽��ϴ�.");
						return false;
					}
					
					API.addAllowOpIP(args[0]);
					sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + args[0] + ChatColor.GOLD + " ���� ���� ������ " + ChatColor.RED + "���" + ChatColor.GOLD + "�Ͽ����ϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
		} return false;
	}
}
