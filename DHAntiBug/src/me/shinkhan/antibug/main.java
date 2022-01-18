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
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] ";
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
		
		allowIP = OnlyKRYml.getList("허용 해외 아이피");
		allowOPIP = OnlyKRYml.getList("허용 오피계정 아이피");
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "WDL|INIT");
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "WDL|CONTROL");
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (channel.equals("WDL|INIT") && !player.hasPermission("antiwdl.bypass")) {
            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "kick " + player.getName() + " §4WDL is not authorized on this server!");
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
	    Bukkit.getConsoleSender().sendMessage(prefix + "§c데이터 파일이 생성되었습니다.");
	    a = a.replace("null", "");
	    cantuse = a.split(",");
	}
	
	private void ProcessRpg()
	{
		if (API.CheckFindRpgItem(Bukkit.getPluginManager())) {
			Plugin rpgplugin = Bukkit.getPluginManager().getPlugin("RPG Items");
			
			if (rpgplugin == null) {
	    	  API.LogInfo("경고 - RPG Item 플러그인이 정상적이지 않아, 감시 기능만 동작 됩니다!");
			} else {
				 API.LogInfo("안내 - " + rpgplugin.toString() + " 복사방지중");

				PlayerPickupItemEvent.getHandlerList().unregister(rpgplugin);
			}
		} else {
	    	 API.LogInfo("경고 - RPG Item 플러그인이 감지 되지 않아, 감시 기능만 동작 됩니다!");
	    }
	}
	
	public void PatternDetectFileWrite(String msg)
	{
		try {
			DateFormat datePrefixFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date date_Prefix = new Date();

			FileWriter fs = new FileWriter(new File(getDataFolder().getAbsolutePath(), datePrefixFormat.format(date_Prefix) + "_복사유저.log"), true);
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
			if (commandLabel.equalsIgnoreCase("allowIP") || commandLabel.equalsIgnoreCase("아이피허용")) {
				if (sender.isOp()) {
					if (args.length != 1) {
						sender.sendMessage(ChatColor.GOLD + "/allowIP <IP>" + ChatColor.WHITE + " - 특정 해외 아아피 접속을 허용합니다.");
						sender.sendMessage(ChatColor.GOLD + "/아이피허용 <IP>" + ChatColor.WHITE + " - 특정 해외 아아피 접속을 허용합니다.");
						return false;
					}
					
					if (args[0].equalsIgnoreCase("reload")) {
						allowIP = OnlyKRYml.getList("허용 해외 아이피");
						sender.sendMessage(ChatColor.GOLD + "성공적으로 리로드했습니다.");
						return false;
					}
					
					API.addAllowIP(args[0]);
					sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + args[0] + ChatColor.GOLD + " 해외 아이피 접속을 " + ChatColor.RED + "허용" + ChatColor.GOLD + "하였습니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
		} 
		try {
			if (commandLabel.equalsIgnoreCase("allowOpIP") || commandLabel.equalsIgnoreCase("오피아이피허용")) {
				if (sender.isOp()) {
					if (args.length != 1) {
						sender.sendMessage(ChatColor.GOLD + "/allowOpIP <IP>" + ChatColor.WHITE + " - 오피 계정 접속을 허용합니다.");
						sender.sendMessage(ChatColor.GOLD + "/오피아이피허용 <IP>" + ChatColor.WHITE + " - 오피 계정 접속을 허용합니다.");
						return false;
					}
					
					if (args[0].equalsIgnoreCase("reload")) {
						allowOPIP = OnlyKRYml.getList("허용 오피계정 아이피");
						sender.sendMessage(ChatColor.GOLD + "성공적으로 리로드했습니다.");
						return false;
					}
					
					API.addAllowOpIP(args[0]);
					sender.sendMessage(ChatColor.GOLD + "성공적으로 " + ChatColor.RED + args[0] + ChatColor.GOLD + " 오피 계정 접속을 " + ChatColor.RED + "허용" + ChatColor.GOLD + "하였습니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
		} return false;
	}
}
