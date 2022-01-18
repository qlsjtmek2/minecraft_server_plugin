package me.espoo.oneman;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import me.espoo.Banitem.BlockListener;
import me.espoo.Banitem.PlayerListener;
import me.espoo.Banitem.WorldScanner;
import me.espoo.Banitem.itemcheck;
import me.espoo.option.PlayerYml;
import me.espoo.rpg.exp.ExpAPI;
import me.espoo.rpg.exp.ExpYml;
import me.espoo.rpg.guild.GuildAPI;
import me.shinkhan.fatigue.API;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.milkbowl.vault.economy.Economy;

@SuppressWarnings("deprecation")
public class main extends JavaPlugin implements Listener 
{
	static final Map <String, Integer> Timer = new HashMap<>();
	public static HashMap<String, Integer> FirePlayers = new HashMap<String, Integer>();
	public static HashMap<Player, PlayerInteractEntityEvent> EventHashMap = new HashMap<Player, PlayerInteractEntityEvent>();
	private static GiveAll command;
	public static int CleanTime = 300;
	public static Timer timer = new Timer();
	public static String Memory;
	public static boolean stopE = false;
	public static boolean blockE = false;
	public static boolean healthE = false;
	public static boolean chatE = false;
	public static boolean levelE = false;
	public static int levelF = 100;
	static LogBlock logblock = null;
	static Consumer lbconsumer = null;
	public static Logger log = Logger.getLogger("MineCraft");
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ";
	private static Economy economy = null;
	FileConfiguration Config;
	double enchant;
	double repair;
	static final int MAX_ENCHANT = 32767;
	static HashMap<String, Enchantment> enchantmentNames = new HashMap<String, Enchantment>();
	public static ArrayList<Player> portal = new ArrayList<Player>();
	public static ArrayList<String> all = new ArrayList<String>();
	public static ArrayList<String> place = new ArrayList<String>();
	public static ArrayList<String> pickup = new ArrayList<String>();
	public static ArrayList<String> world = new ArrayList<String>();
	public static ArrayList<String> interact = new ArrayList<String>();
	public PluginDescriptionFile pdfFile = getDescription();
	public static ArrayList<String> click = new ArrayList<String>();
	public static ArrayList<String> br = new ArrayList<String>();
	public ArrayList<String> worlds = new ArrayList<String>();
	static ArrayList<Integer> list;
	public final PlayerListener pl = new PlayerListener(this);
	public final BlockListener bl = new BlockListener(this);
	public final WorldScanner ws = new WorldScanner(this);
    static final Map <String, Integer> QTimer = new HashMap<>();
	public static HashMap<String, Integer> QScoreTime = new HashMap<String, Integer>();
    public static ArrayList<Player> QScoreSpem = new ArrayList<Player>();
    public static ArrayList<String> shop1 = new ArrayList<String>();
    static final Map <String, Integer> shop = new HashMap<>();
    static final Map <String, String> buyme = new HashMap<>();
    static final Map <String, String> buyto = new HashMap<>();
    public static HashMap <Player, Integer> hungry = new HashMap<Player, Integer>();
    public static int inte = 5;
    public static me.espoo.option.PlayerYml P;
    public static boolean explode = true;
    public static boolean reload = false;
    public static int bossCount = 0;
	
	@SuppressWarnings({ })
	public void onEnable()
    {
        main.all = ((ArrayList<String>)getConfig().getStringList("Blacklist"));
        main.place = ((ArrayList<String>)getConfig().getStringList("Blacklist Placement"));
        main.pickup = ((ArrayList<String>)getConfig().getStringList("Blacklist Pickup"));
        main.interact = ((ArrayList<String>)getConfig().getStringList("Blacklist Interaction"));
        main.click = ((ArrayList<String>)getConfig().getStringList("Blacklist Click"));
        this.worlds = ((ArrayList<String>)getConfig().getStringList("Blacklist World"));
        main.br = ((ArrayList<String>)getConfig().getStringList("Blacklist Break"));
        
        List<Integer> tempList = new ArrayList<Integer>();
        tempList.add(Integer.valueOf(116));
        getConfig().addDefault("blacklist.items", tempList);
        main.list = ((ArrayList<Integer>)getConfig().getIntegerList(
          "blacklist.items"));
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(this.pl, this);
        pm.registerEvents(this.bl, this);
        pm.registerEvents(this.ws, this);
        
		File f = new File("plugins/ESPOOOneMan/File.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		File folder = new File("plugins/ESPOOOneMan");
		if (!f.exists()) Method.CreateConfig(f, folder, config);
        
		getCommand("��þƮ").setExecutor(new TimCommand(this));
		getCommand("��þƮ���").setExecutor(new TimCommand(this));
        initCommand();
		CraftServer cs = (CraftServer)getServer();
		cs.getServer().primaryThread.setPriority(10);
        getServer().getPluginManager().registerEvents(this, this);
	    timer = new Timer();
	    timer.schedule(new WorkTask(), 1000 * CleanTime, 1000 * CleanTime);
        main.logblock = ((LogBlock)getServer().getPluginManager().getPlugin("LogBlock"));
        if (main.logblock != null) main.lbconsumer = main.logblock.getConsumer();
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy)economyProvider.getProvider();
        getConfig().options().copyDefaults(true);
        saveConfig();
        saveDefaultConfig();
        reloadConfig();
        loadConfig();
        
        shop1.add("&7&l==============");
        shop1.add("&f��ȭ�� �ȵ� ����");
        shop1.add("&f��̴�. ����ϰ�");
        shop1.add("&fä���� �õ��غ���.");
        shop1.add("&7&l==============");
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        		for (Player p : Bukkit.getOnlinePlayers()) {
        			p.getWorld().setStorm(false);
        			p.getWorld().setThundering(false);
        			p.getWorld().setTime(846000);
        		}
        	}
        }, 100L, 100L);
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        		for (Player p : Bukkit.getOnlinePlayers()) {
        			if (p.getWorld().getName().equalsIgnoreCase("world_mining") || p.getWorld().getName().equalsIgnoreCase("world_plant")) {
        				openMacro(p);
        			}
        		}
        	}
        }, 12000L, 12000L);
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        		if (Bukkit.getOnlinePlayers().length != 0) {
        			int random = new Random().nextInt(15) + 1;
            		String loca = null;
            		bossCount++;
            		
            		switch (random) {
            			case 1: loca = "B�� (X: 1339, Y: 39, Z: 1112)"; break;
            			case 2: loca = "B�� (X: 1353, Y: 39, Z: 1250)"; break;
            			case 3: loca = "B�� (X: 1487, Y: 39, Z: 1211)"; break;
            			case 4: loca = "D�� (X: -1492, Y: 39, Z: 2809)"; break;
            			case 5: loca = "D�� (X: -1359, Y: 39, Z: 2775)"; break;
            			case 6: loca = "D�� (X: -1449, Y: 39, Z: 2944)"; break;
            			case 7: loca = "F�� (X: 2869, Y: 11, Z: 2272)"; break;
            			case 8: loca = "F�� (X: 2904, Y: 11, Z: 2419)"; break;
            			case 9: loca = "F�� (X: 2779, Y: 11, Z: 2481)"; break;
            			case 10: loca = "J�� (X: -1093, Y: 10, Z: -989)"; break;
            			case 11: loca = "J�� (X: -1127, Y: 10, Z: -1142)"; break;
            			case 12: loca = "J�� (X: -990, Y: 10, Z: -1118)"; break;
            			case 13: loca = "Z�� (X: -56, Y: 29, Z: 287)"; break;
            			case 14: loca = "Z�� (X: -13, Y: 30, Z: 398)"; break;
            			case 15: loca = "Z�� (X: -176, Y: 30, Z: 164)"; break;
            		}
            		
            		if (bossCount == 7) {
            			bossCount = 0;
            			Bukkit.broadcastMessage("��e[ ��c���ο� (��� ��) ��e] ��6�� ��c" + loca + " ��6�� �����Ǿ����ϴ�.");
            			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn ���ο� ���ø�-" + random);
            		} else {
            			String name = null;
            			String rank = null;
            			
            			int monster = new Random().nextInt(10) + 1;
            			switch (monster) {
            				case 1: name = "�Ŵ�����"; rank = "��"; break;
            				case 2: name = "17���� �Ź� ����"; rank = "��"; break;
            				case 3: name = "17���� �Ź� ����"; rank = "��"; break;
            				case 4: name = "��ǳ��"; rank = "��"; break;
            				case 5: name = "�׸����"; rank = "��"; break;
            				case 6: name = "�������"; rank = "��"; break;
            				case 7: name = "��Ÿ�"; rank = "��"; break;
            				case 8: name = "�����"; rank = "��"; break;
            				case 9: name = "ħ����ġ"; rank = "��"; break;
            				case 10: name = "���� �÷絷"; rank = "��"; break;
            			}
            			
            			Bukkit.broadcastMessage("��e[ ��c" + name + " (" + rank + ") ��e] ��6��/�� ��c" + loca + " ��6�� �����Ǿ����ϴ�.");
            			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn " + name.replaceAll(" ", "_") + " ���ø�-" + random);
            		}
        		}
        	}
        }, 7200L, 7200L);
        
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
    }

	public void onDisable()
	{
	    getConfig().set("Blacklist", main.all);
	    getConfig().set("Blacklist Placement", main.place);
	    getConfig().set("Blacklist Pickup", main.pickup);
	    getConfig().set("Blacklist Interaction", main.interact);
	    getConfig().set("Blacklist Click", main.click);
	    getConfig().set("Blacklist Break", main.br);
	    getConfig().set("Blacklist World", this.worlds);
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static void openMacro(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 9, "��ũ�� üũ ��l(E�� ���� â�� �ݾ��ּ���)");
		p.openInventory(GUI);
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack2(String Display, int ID, int DATA, int STACK, List<Object> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
	
	public static Location StringToLocation(String s) {
		String[] loc = s.split(",");
		Location loc2 = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
		return loc2;
	}
	
	public static void GuildGrandToMove(Player p) {
		String X = Integer.toString(new Random().nextInt(6000) - 3000);
		String Z = Integer.toString(new Random().nextInt(6000) - 3000);
		
		if (GuildAPI.inGuildAreaNoY(StringToLocation(X + ",255," + Z + ",world_guild"))) {
			GuildGrandToMove(p);
		} else {
			p.teleport(StringToLocation(X + ",255," + Z + ",world_guild"));
			Method.sendCommand("tppos " + p.getName() + " " + X + " 255 " + Z);
		}
	}
	
	private void loadConfig()
	{
		reloadConfig();
	    this.Config = getConfig();
	    this.Config.options().copyDefaults(true);

	    this.enchant = Double.valueOf(this.Config.getDouble("enchantCost")).doubleValue();
	    this.repair = Double.valueOf(this.Config.getDouble("repairCost")).doubleValue();
	    saveConfig();
	}
	
	public void initCommand()
	{
		command = new GiveAll(null);
		getCommand("����").setExecutor(command);
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	@SuppressWarnings("resource")
	public static boolean isNumeric(String str)  
	{
		Scanner scan = new Scanner(str);
		if (!scan.hasNextInt()) {
			return false;
		} else {
			return true;  
		}
	}
	
	static EnchantmentResult enchantItem(Player player, Enchantment enchantment, int level) {
		if (enchantment == null) {
			return EnchantmentResult.INVALID_ID;
		}
		if ((level < 1) || ((!player.hasPermission("enchanter.dirty")) && (level > enchantment.getMaxLevel()))) {
			level = enchantment.getMaxLevel();
		}
		ItemStack item = player.getInventory().getItemInHand();
		if (item == null)
			return EnchantmentResult.CANNOT_ENCHANT;
		try
		{
			item.addUnsafeEnchantment(enchantment, level > 32767 ? 32767 : level);
		} catch (Exception e) {
			return EnchantmentResult.CANNOT_ENCHANT;
		}
		return EnchantmentResult.VICIOUS_STREAK_A_MILE_WIDE;
	}

	static EnchantmentResult enchantItem(Player player, String enchantmentString, int level) {
		try {
			return enchantItem(player, Enchantment.getById(Integer.valueOf(enchantmentString).intValue()), level); } catch (NumberFormatException e) {
			}
		return enchantItem(player, getEnchantment(enchantmentString), level);
	}
	
	private static Enchantment getEnchantment(String query) {
		return (Enchantment)main.enchantmentNames.get(query.toLowerCase());
	}

	@SuppressWarnings("unused")
	private void loadEnchantments(CommandSender sender) {
		main.enchantmentNames.clear();
		saveDefaultConfig();
		reloadConfig();
		Map<?, ?> map = getConfig().getValues(false);
		StringBuilder builder = new StringBuilder();
		Collection<Enchantment> registeredEnchantments = main.enchantmentNames.values();
		Enchantment[] arrayOfEnchantment;
		int localException1 = (arrayOfEnchantment = Enchantment.values()).length; for (int e = 0; e < localException1; e++) { Enchantment enchantment = arrayOfEnchantment[e];
		if (!registeredEnchantments.contains(enchantment)) {
			builder.append(enchantment.getName()).append('(').append(enchantment.getId()).append(") ");
		}
		}
	}

	static enum EnchantmentResult
	{
		INVALID_ID, 
		CANNOT_ENCHANT, 
		VICIOUS_STREAK_A_MILE_WIDE;
	}
	
    @EventHandler
    public void Death(final PlayerDeathEvent event) {
        final Player p = event.getEntity();
        final int h = p.getFoodLevel();
        main.hungry.put(p, h);
    }
    
    @EventHandler
    public void Respawn(PlayerRespawnEvent event) {
        final Player ps = event.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this, (Runnable) new Runnable() {
            @Override
            public void run() {
                main.this.h(ps);
            }
        }, 10L);
    }
    
    public void h (final Player p) {
        final int hun = main.hungry.get(p);
        p.setFoodLevel(hun);
    }
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void eventOnJoin(PlayerJoinEvent e) { 
		e.setJoinMessage(null);
		
		Player p = e.getPlayer();
		File f = new File("plugins/ESPOOOneMan/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ESPOOOneMan");
		File folder2 = new File("plugins/ESPOOOneMan/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreatePlayerInfo(p, f, folder, folder2, config);
		
        Calendar oCalendar = Calendar.getInstance();
        final String[] week = { "��", "��", "ȭ", "��", "��", "��", "��" };
        String str = week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1];
        if (str.equalsIgnoreCase("��")) {
        	if (Method.getConfigBoolean("������") == false) {
            	Method.setConfigBoolean("������", true);
            	boolean isOp = p.isOp();
            	p.setOp(true);
            	Bukkit.getServer().dispatchCommand(p, "npc sel 0");
            	Bukkit.getServer().dispatchCommand(p, "npc remove");
               	Bukkit.getServer().dispatchCommand(p, "npc sel 1");
            	Bukkit.getServer().dispatchCommand(p, "npc remove");
               	Bukkit.getServer().dispatchCommand(p, "npc sel 2");
            	Bukkit.getServer().dispatchCommand(p, "npc remove");
               	Bukkit.getServer().dispatchCommand(p, "npc sel 3");
            	Bukkit.getServer().dispatchCommand(p, "npc remove");
            	
				List<String> list = ExpYml.ArrayExp();
				int i = 1;
				String one = null;
				String two = null;
				String three = null;
				String four = null;
				
				for (String st : list)
				{
					if (i >= 5) break;
					switch (i) {
						case 1:
							one = st.split(",")[0]; break;
						case 2:
							two = st.split(",")[0]; break;
						case 3:
							three = st.split(",")[0]; break;
						case 4:
							four = st.split(",")[0]; break;
					} i++;
				}
            	
            	if (one != null) {
                   	Bukkit.getServer().dispatchCommand(p, "npc create " + one + " --at -813:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + one + " " + Method.getConfigInt("1�� ����"));
            	} else {
            	  	Bukkit.getServer().dispatchCommand(p, "npc create ���� --at -813:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}
            	
            	if (two != null) {
                   	Bukkit.getServer().dispatchCommand(p, "npc create " + two + " --at -806:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + two + " " + Method.getConfigInt("2�� ����"));
            	} else {
                   	Bukkit.getServer().dispatchCommand(p, "npc create ���� --at -806:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}
            	
            	if (three != null) {
                  	Bukkit.getServer().dispatchCommand(p, "npc create " + three + " --at -806:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + three + " " + Method.getConfigInt("3�� ����"));
            	} else {
                  	Bukkit.getServer().dispatchCommand(p, "npc create ���� --at -806:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}

            	if (four != null) {
                  	Bukkit.getServer().dispatchCommand(p, "npc create " + four + " --at -813:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + four + " " + Method.getConfigInt("4�� ����"));
            	} else {
                  	Bukkit.getServer().dispatchCommand(p, "npc create ���� --at -813:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}
            	
            	p.setOp(isOp);
            	p.setOp(isOp);
            	p.setOp(isOp);
            	p.setOp(isOp);

        		File f2 = new File("plugins/OnePunchRpg/Exp.yml");
        		f2.delete();
            	
        		Timer timer12 = new Timer();
        		Date timeToRun2 = new Date(System.currentTimeMillis() + 2000);
        		timer12.schedule(new TimerTask() {
        			public void run() {
                    	p.setOp(isOp);
                    	p.setOp(isOp);
                    	p.setOp(isOp);
                    	p.setOp(isOp);
                    	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop ������ ������ ���� ���ε�");
        			}
        		}, timeToRun2);
        	}
        } else if (str.equalsIgnoreCase("ȭ") || str.equalsIgnoreCase("��") || str.equalsIgnoreCase("��") 
        		|| str.equalsIgnoreCase("��") || str.equalsIgnoreCase("��") || str.equalsIgnoreCase("��")) {
        	if (Method.getConfigBoolean("������")) {
        		Method.setConfigBoolean("������", false);
        	}
        }
        
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 300);
		timer1.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					p.sendMessage("");
				}
				p.sendMessage("��e�����������������������������������������");
				p.sendMessage("");
				Message.sendCenteredMessage(p, "��6ȯ���մϴ� ��e" + p.getName() + "��6��! ���õ� ��ſ� ��c����ũ����Ʈ ��6�ϼ���~");
				p.sendMessage("");
				p.sendMessage("��e�����������������������������������������");
				p.sendMessage("");
				String guild = GuildAPI.getJoinGuild(p);
				
				if (guild != null) {
					if (GuildAPI.isWar(guild)) {
						String emeny = GuildAPI.getWarGuild(guild);
						
						if (GuildAPI.isSubmit(emeny)) {
							p.sendMessage(" ����������            ����6���� �츮 ��尡 ��c" + emeny + " ��6��忡�� �׺��� �����Դϴ�.");
							p.sendMessage("");
							p.sendMessage("��e�����������������������������������������");
							p.sendMessage("");
						}
						
						else if (GuildAPI.isSubmit(guild)) {
							p.sendMessage(" ��       ��������            ����6���� ��� ��尡 �츮 ��忡�� �׺��� �����Դϴ�.");
							if (GuildAPI.getJoinMaster(guild).equalsIgnoreCase(p.getName()))
								p.sendMessage(" ��       ��������         ��e[ /��� -> ���� �׺� ����/���� ] ��6���� �׺��� ���� �Ǵ� �����ϼ���.");
							p.sendMessage("");
							p.sendMessage("��e�����������������������������������������");
							p.sendMessage("");
						}
						
						else {
							p.sendMessage(" ��������������6���� ��c" + guild + " ��6���� ��c" + emeny + " ��6��尡 �������� �����Դϴ�.");
							p.sendMessage("");
							p.sendMessage("��e�����������������������������������������");
							p.sendMessage("");
						}
					}
				}
				return;
			}
		}, timeToRun);
		
		if (!reload) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ���ؿ�");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ���ν�");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ���ο�");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �Ƽ���_ī����");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall world");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn ���ؿ� ��ƮŸ��-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn ���ν� ��ũ����-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn ���ο� ���ø�-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �Ƽ���_ī���� ��ȭ����-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �ڻԼ�_������");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ����_����");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ����ޱⳲ");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ���");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �Ǵн���");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ���ΰ���_���žȰ�");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype ���÷��õ�");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �̺�_õ����");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �ո�");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �߳�_�����");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype Ȩ����_Ȳ��");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �ʹ�_ũ��_�ڶ�_��ġ");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype Ȳ��_����");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype �����ڽ�");
			reload = true;
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerKicked(PlayerKickEvent e) { e.setLeaveMessage(null); }
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void eventOnLeave(PlayerQuitEvent e) { e.setQuitMessage(null); }
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onNPCRightClickEvent(NPCRightClickEvent event)
	{
		Player p = event.getClicker();
		if (p.isSneaking()) {
			PlayerInteractEntityEvent e = EventHashMap.get(p);
			e.setCancelled(true);
			if (EventHashMap.containsKey(p)) EventHashMap.remove(p);
		}
	}
	
	@EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if ((event.getRightClicked() instanceof Player)) {
			Player player = event.getPlayer();
			if (player.isSneaking()) {
				if ((!RideAPI.duckEjectPassenger(player, event.getRightClicked())) && (RideAPI.playerCanRide(player))) {
					if (EventHashMap.containsKey(player)) EventHashMap.remove(player);
					EventHashMap.put(player, event);
					Player vehicle = RideAPI.getVehicle(player);
					
					if (vehicle == null) {
						if (API.isCheck(player)) {
							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 20);
							timer.schedule(new TimerTask() { 
								public void run() {
									if (!event.isCancelled()) {
										if (!me.espoo.option.PlayerYml.getInfoBoolean((Player) event.getRightClicked(), "�÷��̾� ���̵�")) {
											event.setCancelled(true);
											player.sendMessage("��c�� �÷��̾� ���� ������ �� �����ϴ�.");
											return;
										}
										
										RideAPI.getLastPassenger((Player) event.getRightClicked()).setPassenger(player);
										if (EventHashMap.containsKey(player)) EventHashMap.remove(player);
									}
									return;
								}
							}, timeToRun); 
						} else {
							event.setCancelled(true);
							API.sayMessage(player, "�Ƿε��� �����Ͽ� �÷��̾� ���� ���� �� �����ϴ�.");
						}
					} else {
						vehicle.eject();
					}
				}
			} else {
				Player vehicle = (Player) event.getRightClicked();
				
				if (player.isOp() || me.espoo.option.PlayerYml.getInfoBoolean(vehicle, "���� ���� ����")) {
					event.setCancelled(true);
					player.chat("/���� " + vehicle.getName());
				} else {
					event.setCancelled(true);
					player.sendMessage("��c�� �÷��̾�� ������ ����� �صξ����ϴ�.");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
	    String cmd = e.getMessage().replaceAll("/", "");
	    if (((cmd.equalsIgnoreCase("op")) || (cmd.equalsIgnoreCase("deop"))) && 
	    		(!p.hasPermission("anticheat.op"))) {
	    	e.setCancelled(true);
	    	if (cmd.split(" ").length > 0)
	    		p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
	    	else
	    		p.sendMessage("��f[��4����f] ��c����� ������ �����Ƿ� Ÿ�ο��� ���� ������ �Ұ��� �մϴ�.");
	    }
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e) 
	{
	    Player diedP = e.getEntity();
	    if (diedP == null) return;

	    if (PlayerYml.getInfoBoolean(diedP, "ȿ����")) diedP.playSound(diedP.getLocation(), Sound.WITHER_SPAWN, 1.0F, 1.0F);
	    
		PlayerDeathEvent Event0 = (PlayerDeathEvent) e;
		Player p = Event0.getEntity();
		Entity kn = p.getKiller();
		
		if (kn instanceof Player) {
			Player kp = (Player) p.getKiller();
			
	        if (kp.getWorld().getName().equalsIgnoreCase("world_pvp") && p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
	        	if (ExpAPI.getExp(p.getName()) / 5 <= 9) {
	        		int num = ExpAPI.getExp(kp.getName()) + 5;
	        		ExpAPI.addExp(kp, "��6����� ��c" + p.getName() + "��6���� ���̰� ��c<exp><bouns> ��6��ŭ ����ġ�� �޾ҽ��ϴ�. ��c<playerexp> ��6EXP", 5);
	        	} else {
	        		int num = ExpAPI.getExp(kp.getName()) + (ExpAPI.getExp(p.getName()) / 10);
	        		ExpAPI.addExp(kp, "��6����� ��c" + p.getName() + "��6���� ���̰� ��c<exp><bouns> ��6��ŭ ����ġ�� �޾ҽ��ϴ�. ��c<playerexp> ��6EXP", ExpAPI.getExp(p.getName()) / 10);
	        		ExpAPI.setExp(p, ExpAPI.getExp(p.getName()) - (ExpAPI.getExp(p.getName()) / 20));
	        		num = ExpAPI.getExp(p.getName()) - (ExpAPI.getExp(p.getName()) / 20);
	        		p.sendMessage("��6����� ��c" + kp.getName() + "��6�Կ��� �װ� ��c" + ExpAPI.getExp(p.getName()) / 20 + "��6��ŭ ����ġ�� ������ϴ�. ��c" + num + " ��6EXP");
	        	}
	        }
	        
	        else if (kp.getWorld().getName().equalsIgnoreCase("world") && p.getWorld().getName().equalsIgnoreCase("world")) {
	        	if (!kp.isOp()) {
	        		if (GuildAPI.getJoinGuild(kp) != null && GuildAPI.getJoinGuild(p) != null) {
	        			if (GuildAPI.getWarGuild(GuildAPI.getJoinGuild(kp)).equalsIgnoreCase(GuildAPI.getJoinGuild(p))) {
	        				return;
	        			}
	        		}
	        		
		        	kp.kickPlayer("����� ���øʿ��� ������ ���������Ƿ� 10�� �� ó�� �˴ϴ�.");
		        	p.sendMessage("��6����� ���� ��c" + kp.getName() + "��6 ���� �������� 10�е��� ��c�� ��6ó���Ǿ����ϴ�.");
		        	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tempban " + kp.getName() + " 600");
	        	}
	        }
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerHealth(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player))
		{
			if ((me.espoo.oneman.main.healthE) && ((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Player)))
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
    	if (e.getInventory().getName().equalsIgnoreCase("�÷��̾� ����")) {
    		e.setCancelled(true);
    	}
	}
	
	@EventHandler
	public static void onEntityDamage(EntityDamageEvent e) 
	{
		
		if ((e.getEntity() instanceof Player))
		{
			Player p = (Player)e.getEntity();
			PlayerInventory inv = p.getInventory();

			if (inv.getChestplate() != null) {
				inv.getChestplate().setDurability((short) 0);
			}
		
			if (inv.getHelmet() != null) {
				if (inv.getHelmet().getTypeId() != 397) {
					inv.getHelmet().setDurability((short) 0);
				}
			}

			if (inv.getLeggings() != null) {
	        inv.getLeggings().setDurability((short) 0);
			}

			if (inv.getBoots() != null)
				inv.getBoots().setDurability((short) 0);
      
		    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) 
		    {
		        e.setDamage(0);
		        e.setCancelled(true);
		    }
		}
	}
	
	@EventHandler
	public void eighty(EnchantItemEvent event) {
		Player p = event.getEnchanter();
		p.sendMessage("��c��þƮ�� �Ͻ� �� �����ϴ�.");
		event.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		if (event.isCancelled()) return;
		
		if (explode) {
			for (int x = 0; x < event.blockList().size(); x++) {
				Block block = (Block) event.blockList().get(x);
				
				if (block.getType() == Material.DIRT || block.getType() == Material.GRASS || block.getType() == Material.IRON_DOOR || block.getType() == Material.WOOD_DOOR || block.getType() == Material.CHEST || 
					block.getType() == Material.ENDER_CHEST || block.getType() == Material.FURNACE || block.getType() == Material.BURNING_FURNACE || 
					block.getTypeId() == 58 || block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.TORCH ||
					block.getType() == Material.WALL_SIGN || block.getType() == Material.WOOL && block.getData() == 0 || block.getTypeId() == 101 ||
					block.getType() == Material.WOOL && block.getData() == 7 ||  block.getType() == Material.WOOL && block.getData() == 8)
				{
					event.blockList().remove(x);
					x--;
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void teleportEvent(PlayerTeleportEvent e) {
		String cause = e.getCause().toString();
		if (!cause.equals("COMMAND")) return;

		Player p = e.getPlayer();
		Location to = e.getTo().clone();
		Location from = e.getFrom().clone();
		if (PlayerYml.getInfoBoolean(p, "ȿ����")) {
			p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 1.0F, 1.0F);
			p.getWorld().playEffect(from.add(0.0D, 1.4D, 0.0D), Effect.ENDER_SIGNAL, 1);
			p.getWorld().playEffect(to.add(0.0D, 1.4D, 0.0D), Effect.ENDER_SIGNAL, 1);
		}
	}

	@EventHandler
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent e)
	{
		Player p = e.getPlayer();
		if ((!p.hasPermission("anticheat.gamemode")) && 
				(e.getNewGameMode() != GameMode.SURVIVAL)) {
			e.setCancelled(true);
			p.sendMessage("��f[��4����f] ��c����� ������ �����Ƿ� ���Ӹ�尡 �Ұ��� �մϴ�.");
		}
	}
	
	@EventHandler
	public static void onPlayerInteract2(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
	    int hand = p.getItemInHand().getTypeId();
	    
	    if ((hand == 268) || (hand == 270) || (hand == 271) || (hand == 269) || (hand == 290) || 
	    	(hand == 272) || (hand == 274) || (hand == 273) || (hand == 275) || (hand == 291) || 
	    	(hand == 267) || (hand == 257) || (hand == 258) || (hand == 256) || (hand == 283) || 
	    	(hand == 285) || (hand == 286) || (hand == 284) || (hand == 294) || (hand == 276) || 
	    	(hand == 278) || (hand == 279) || (hand == 277) || (hand == 259) || (hand == 261) || 
	    	(hand == 346) || (hand == 359))
	    {
	    	p.getItemInHand().setDurability((short) 0);
	    }
	}
	
	@EventHandler
	public void onPlayerItemBreak(PlayerItemBreakEvent e) 
	{
		if (e.getBrokenItem().getType() != Material.DIAMOND_HOE && e.getBrokenItem().getType() != Material.IRON_HOE) {
			e.getBrokenItem().setAmount(1);
			e.getPlayer().updateInventory();
		}
	}
	
	public static void dropBlock(Player p, Block b, Material type)
	{
		ItemStack item = null;
		ItemStack item2 = null;
		if (type == Material.SUGAR_CANE_BLOCK) {
			item = new ItemStack(338, 1);
		}
		
		else if (type == Material.WATER_LILY) {
			item = new ItemStack(111, 1);
		}
		
		else if (type == Material.YELLOW_FLOWER) {
			item = new ItemStack(37, 1);
		}
		
		else if (type == Material.RED_ROSE) {
			item = new ItemStack(38, 1);
		}
		
		else if (type == Material.CROPS) {
			item = new ItemStack(296, 1);
			item2 = new ItemStack(295, 1);
		}
		
		else if (type == Material.CARROT) {
			item = new ItemStack(391, 1);
		}
		
		else if (type == Material.POTATO) {
			item = new ItemStack(392, 1);
		}
		
		else return;
		
		ItemMeta meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();
		list.add("��7�� ���۹��� �丮 ��������,");
		list.add("��7�ɰų� �Ǹ��Ͻ� �� �����ϴ�.");
		list.add("��fä���� ��7������ ���� �� �ֽ��ϴ�.");
		meta.setLore(list);
		item.setItemMeta(meta);
		int num = b.getDrops().size();
		
		while (num > 0) {
			num--;
			p.getWorld().dropItemNaturally(b.getLocation(), item);
		}
		
		if (item2 != null) {
			int Random = new Random().nextInt(4) + 1;
			switch (Random) {
				case 1:
					break;
				default:
					item2.setItemMeta(meta);
					p.getWorld().dropItemNaturally(b.getLocation(), item2);
					break;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();

		if (!p.isOp()) {
			if (p.getWorld().getName().equalsIgnoreCase("world_plant")) {
				if (e.getBlock().getType() == Material.SUGAR_CANE_BLOCK || e.getBlock().getType() == Material.WATER_LILY ||
					e.getBlock().getType() == Material.YELLOW_FLOWER || e.getBlock().getType() == Material.RED_ROSE ||
					e.getBlock().getType() == Material.CROPS || e.getBlock().getType() == Material.CARROT ||
					e.getBlock().getType() == Material.POTATO) {
					if (p.getItemInHand() != null) {
						ItemStack i = p.getItemInHand();
						
						if (i.getType() == Material.DIAMOND_HOE) {
							i.setDurability((short) (i.getDurability() + 10));
							
							if (i.getDurability() >= 1561) {
								p.setItemInHand(null);
								p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
							}
							
							dropBlock(p, e.getBlock(), e.getBlock().getType());
						}
						
						else if (i.getType() == Material.IRON_HOE) {
							i.setDurability((short) (i.getDurability() + 3));

							if (i.getDurability() >= 250) {
								p.setItemInHand(null);
								p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
							}
							
							dropBlock(p, e.getBlock(), e.getBlock().getType());
						}
						
						else {
							e.setCancelled(true);
							p.sendMessage("");
							p.sendMessage("��cä���� ö ��, ���̾Ƹ�� �� ���θ� �����մϴ�.");
							p.sendMessage("��f- ��c���̾Ƹ�� ��, ö �� �� �� �ϳ��� �����ؼ� ä�����ֽñ� �ٶ��ϴ�.");
						}
					}
				} else {
					e.setCancelled(true);
				}
			}
		} else {
			if (p.getWorld().getName().equalsIgnoreCase("world_plant")) {
				if (e.getBlock().getType() == Material.SUGAR_CANE_BLOCK || e.getBlock().getType() == Material.WATER_LILY ||
					e.getBlock().getType() == Material.YELLOW_FLOWER || e.getBlock().getType() == Material.RED_ROSE ||
					e.getBlock().getType() == Material.CROPS || e.getBlock().getType() == Material.CARROT ||
					e.getBlock().getType() == Material.POTATO) {
					dropBlock(p, e.getBlock(), e.getBlock().getType());
				} else {
					e.setCancelled(true);
				}
			}
		}
		
		if (!(p.getGameMode() == GameMode.CREATIVE)) {
			if (e.getBlock().getTypeId() == 1) {
				int i = new Random().nextInt(10000) + 1;
				
				if (i >= 3317 && i < 3328) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��8���ϱ� ���ϼ� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ϱ޼��ϼ� give " + p.getName() + " " + 1);
				}

				else if (i >= 3329 && i < 3340) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��7�ϱ� ���ϼ� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ϱ޼��ϼ� give " + p.getName() + " " + 1);
				}

				else if (i >= 3341 && i < 3353) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��a��l��  ��b1�� ��6��ȭ �ֹ���  ��a��l�� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "�ֹ������� 1 " + p.getName());
				}

				else if (i >= 3354 && i < 3364) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��a��l��  ��b2�� ��6��ȭ �ֹ���  ��a��l���e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "�ֹ������� 2 " + p.getName());
				}

				else if (i >= 3365 && i < 3373) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��a��l��  ��b3�� ��6��ȭ �ֹ���  ��a��l�� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "�ֹ������� 3 " + p.getName());
				}

				else if (i >= 3374 && i < 3379) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��c��l��  ��f�Ϲ� ��6��ȭ �ֹ���  ��c��l�� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "�ֹ������� �Ϲ� " + p.getName());
				}

				else if (i >= 3380 && i < 3384) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��7��l���� ��6���� ���� ť�� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���귣������ť�� give " + p.getName() + " " + 1);
				}

				else if (i >= 3389 && i < 3399) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("��6�� �ȿ��� ��e[ ��7��l���� ��6�ݰ��� ��e] ��6�� �߰��ϼ̽��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����ݰ��� give " + p.getName() + " " + 1);
				}
				
				if (p.getItemInHand().getEnchantments().size() >= 1) {
					if (p.getItemInHand().getItemMeta() != null) {
						ItemStack S = p.getItemInHand();
						ItemMeta M = S.getItemMeta();
						
						if (i >= 1 && i < 600) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.COAL, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.COAL, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.COAL, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.COAL, 1));
								}
							}
							
							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.COAL, 1));
								}
							}
						}
						
						else if (i >= 601 && i < 950) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
								}
							}
						}
						
						else if (i >= 951 && i < 1200) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
								}
							}
						}
						
						else if (i >= 1201 && i < 1350) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 1));
								}
							}
						}
						
						else if (i >= 1351 && i < 1500) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new MaterialData(351, (byte) 4).toItemStack(1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new MaterialData(351, (byte) 4).toItemStack(1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new MaterialData(351, (byte) 4).toItemStack(1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new MaterialData(351, (byte) 4).toItemStack(1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new MaterialData(351, (byte) 4).toItemStack(1));
								}
							}
						}
						
						else if (i >= 1501 && i < 1650) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
								}
							}
						}
						
						else if (i >= 1651 && i < 1660) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
								}
							}
						}
						
						else if (i >= 1661 && i < 1666) {
							e.getBlock().getDrops().clear();
							e.getBlock().setType(Material.AIR);
							
							if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) == 1) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 10) + 1;
								} else num = new Random().nextInt(2) + 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, 1));
								}
							}
							
							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 2 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 3) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 2;
								} else num = new Random().nextInt(3) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 4 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 5) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 2) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 1) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 5) + 2;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 8) + 2;
								} else num = new Random().nextInt(4) + 2;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, 1));
								}
							}

							else if (M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 6 && M.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) <= 7) {
								int num = 0;
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) + 3) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p))) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 4) + 3;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 7) + 3;
								} else num = new Random().nextInt(5) + 3;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, 1));
								}
							}

							else {
								int num = 0;
								
								if (GuildAPI.getJoinGuild(p) != null) {
									if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
										num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
									
									else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
										num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
								} else num = 1;
								
								while (num > 0) {
									num--;
									p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, 1));
								}
							}
						}
					}
				} else {
					if (i >= 1 && i < 1200) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.COAL, 1));
						}
					}
					
					else if (i >= 1201 && i < 1900) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
						}
					}

					else if (i >= 1901 && i < 2400) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
						}
					}

					else if (i >= 2401 && i < 2700) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 1));
						}
					}

					else if (i >= 2701 && i < 3000) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new MaterialData(351, (byte) 4).toItemStack(1));
						}
					}

					else if (i >= 3001 && i < 3300) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
						}
					}

					else if (i >= 3301 && i < 3310) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
						}
					}

					else if (i >= 3311 && i < 3316) {
						e.getBlock().getDrops().clear();
						e.getBlock().setType(Material.AIR);
						int num = 0;
						
						if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 5)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 2) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 5 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 10)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 6) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 10 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 14)
								num = new Random().nextInt(GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 9) + 1;
							
							else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 14 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
								num = new Random().nextInt( GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) - 15) + 1;
						} else num = 1;
						
						while (num > 0) {
							num--;
							p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, 1));
						}
					}
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getWorld().getName().equals("world_mining")) {
			if (!e.getPlayer().isOp()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (blockE)
		{
			if (!e.getPlayer().hasPermission("AllPlayer.admin"))
			{
				e.getPlayer().sendMessage("��f[��4����f] ��c������ ���� �μ� �� �����ϴ�.");
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPortal(PlayerPortalEvent e)
	{
		Player p = e.getPlayer();
		
		if (!portal.contains(p)) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ��Ż " + p.getName());
			portal.add(p);
		}
		
		e.setCancelled(true);
		Integer id = main.Timer.remove(p.getName());
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		
		Timer.put(p.getName(), new BukkitRunnable()
		{
			public void run()
			{
				Integer id = main.Timer.remove(p.getName());
				portal.remove(p);
				if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
				return;
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ESPOOOneMan"), 5L, 5L).getTaskId());
	}

	@EventHandler
	public void onPlayerStop(PlayerMoveEvent e) {
		if (stopE)
		{
			if (!e.getPlayer().hasPermission("AllPlayer.admin"))
			{
				e.getPlayer().teleport(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		if (chatE)
		{
			if (!e.getPlayer().hasPermission("AllPlayer.admin"))
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage("��f[��4����f] ��c������ ���� �� �����ϴ�.");
			}
		}

		Player p = e.getPlayer();
		
		if (shop.get(p.getName()) != null) {
			if (buyme.get(p.getName()) != null) {
				if (buyto.get(p.getName()) != null) {
					if (main.isNumeric(e.getMessage())) {
						int i = Integer.parseInt(e.getMessage());
						int money = shop.get(p.getName());
						double y = (double) money * i;
						String rpgitem = buyme.get(p.getName());
						String itemname = buyto.get(p.getName());
						
						if (economy.getBalance(p.getName()) >= y) {
							if (rpgitem.equalsIgnoreCase("�ҼӼ�") || rpgitem.equalsIgnoreCase("�ٶ��Ӽ�") || rpgitem.equalsIgnoreCase("ġ���Ӽ�") || 
								rpgitem.equalsIgnoreCase("���Ӽ�") || rpgitem.equalsIgnoreCase("���мӼ�") || rpgitem.equalsIgnoreCase("��ҼӼ�") || 
								rpgitem.equalsIgnoreCase("�����Ӽ�")) {
					            int t = 0;
					            ItemStack[] contents;
					            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
					                ItemStack it = contents[j];
					                if (it == null) {
					                    ++t;
					                }
					            }
					            
					            if (t < i) {
					            	p.sendMessage("��c�κ��丮�� ������ �����Ͽ� �������� ������ �� �����ϴ�.");
					    			shop.remove(p.getName());
					    			buyme.remove(p.getName());
					    			buyto.remove(p.getName());
									e.setCancelled(true);
					            	return;
					            } else {
									economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + rpgitem + " give " + p.getName() + " " + i);
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
					            }
							}
							
							int t = 0;
				            ItemStack[] contents;
				            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
				                ItemStack it = contents[j];
				                if (it == null) {
				                    ++t;
				                }
				            }

				            if ((double) t < ((double) i / 64)) {
				            	p.sendMessage("��c�κ��丮�� ������ �����Ͽ� �������� ������ �� �����ϴ�.");
				    			shop.remove(p.getName());
				    			buyme.remove(p.getName());
				    			buyto.remove(p.getName());
								e.setCancelled(true);
				            	return;
				            } else {
								economy.withdrawPlayer(p.getName(), y);
								p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + rpgitem + " give " + p.getName() + " " + i);
								if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
								e.setCancelled(true);
								shop.remove(p.getName());
								buyme.remove(p.getName());
								buyto.remove(p.getName());
								return;
				            }
						} else {
							p.sendMessage("��c���� �����Ͽ� �Ұ��� ������ �� �����ϴ�.");
							shop.remove(p.getName());
							buyme.remove(p.getName());
							buyto.remove(p.getName());
							e.setCancelled(true);
							return;
						}
					} else {
						p.sendMessage("��c���� ���� ä�ÿ� ���ڷθ� �Է��� �ֽñ� �ٶ��ϴ�.");
						shop.remove(p.getName());
						buyme.remove(p.getName());
						buyto.remove(p.getName());
						e.setCancelled(true);
						return;
					}
				} else {
					if (main.isNumeric(e.getMessage())) {
						int i = Integer.parseInt(e.getMessage());
						int money = shop.get(p.getName());
						double y = (double) money * i;
						String itemname = buyme.get(p.getName()).replaceAll("_", " ");
						
						if (itemname.equalsIgnoreCase("���� ���") || itemname.equalsIgnoreCase("ö ����") || itemname.equalsIgnoreCase("���̾Ƹ�� ����")) {
							int t = 0;
				            ItemStack[] contents;
				            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
				                ItemStack it = contents[j];
				                if (it == null) {
				                    ++t;
				                }
				            }
				            
				            if (t < i) {
				            	p.sendMessage("��c�κ��丮�� ������ �����Ͽ� �������� ������ �� �����ϴ�.");
				    			shop.remove(p.getName());
				    			buyme.remove(p.getName());
				    			buyto.remove(p.getName());
								e.setCancelled(true);
				            	return;
				            } else {
				            	if (itemname.equalsIgnoreCase("���� ���")) {
									economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									ItemStack item = new MaterialData(257, (byte) 0).toItemStack(1);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("��e���� ���");
									List<String> lore = new ArrayList<String>();
									lore.add("��7��l==============");
									lore.add("��f��ȭ�� �ȵ� ����");
									lore.add("��f��̴�. ����ϰ�");
									lore.add("��fä���� �õ��غ���");
									lore.add("��7��l==============");
									item_Meta.setLore(lore);
									item_Meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
									item.setItemMeta(item_Meta);
									for (int z = 0; z < i; z++)
										p.getInventory().addItem(new ItemStack[] { item });
									
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("ö ����")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_HOE) });
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + "257 " + i);
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("���̾Ƹ�� ����")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_HOE) });
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            }
						}
						
						else if (itemname.equalsIgnoreCase("�Ϲ� ��ȭ �ֹ���") || itemname.equalsIgnoreCase("Ư�� ��ȭ �ֹ���") || itemname.equalsIgnoreCase("�����̾� ��ȭ �ֹ���")) {
							int t = 0;
				            ItemStack[] contents;
				            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
				                ItemStack it = contents[j];
				                if (it == null) {
				                    ++t;
				                }
				            }

				            if ((double) t < ((double) i / 64)) {
				            	p.sendMessage("��c�κ��丮�� ������ �����Ͽ� �������� ������ �� �����ϴ�.");
				    			shop.remove(p.getName());
				    			buyme.remove(p.getName());
				    			buyto.remove(p.getName());
								e.setCancelled(true);
				            	return;
				            } else {
				            	if (itemname.equalsIgnoreCase("�Ϲ� ��ȭ �ֹ���")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									ItemStack item = new MaterialData(339, (byte) 0).toItemStack(i);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("��c��l��  ��f�Ϲ� ��6��ȭ �ֹ���  ��c��l��");
								    List<String> list1 = new ArrayList<String>();
								    list1.add("��7��l=================");
								    list1.add("��f����� ��ȭ �ֹ����̴�.");
								    list1.add("��f�� �������� ��� ��ȭ");
								    list1.add("��f�ϰ� ���� �����ۿ� �÷�");
								    list1.add("��f�θ� �� �������� ��ȭ�ȴ�.");
								    list1.add("��7��l=================");
								    item_Meta.setLore(list1);
								    item.setItemMeta(item_Meta);
									p.getInventory().addItem(new ItemStack[] { item });
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("Ư�� ��ȭ �ֹ���")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									ItemStack item = new MaterialData(339, (byte) 0).toItemStack(i);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("��c��l��  ��bƯ�� ��6��ȭ �ֹ���  ��c��l��");
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
								    item_Meta.setLore(list2);
								    item.setItemMeta(item_Meta);
									p.getInventory().addItem(new ItemStack[] { item });
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("�����̾� ��ȭ �ֹ���")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("��c" + itemname + "��6 �������� ��c" + y + " ��6�� �����ϰ� �����ϼ̽��ϴ�.");
									ItemStack item = new MaterialData(339, (byte) 0).toItemStack(i);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("��4��l��  ��d�����̾� ��6��ȭ �ֹ���  ��4��l��");
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
								    item_Meta.setLore(list3);
								    item.setItemMeta(item_Meta);
									p.getInventory().addItem(new ItemStack[] { item });
									if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            }
						}
					} else {
						p.sendMessage("��c���� ���� ä�ÿ� ���ڷθ� �Է��� �ֽñ� �ٶ��ϴ�.");
						shop.remove(p.getName());
						buyme.remove(p.getName());
						buyto.remove(p.getName());
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent e) {
		if (levelE)
		{
			if (e.getPlayer().getLevel() >= levelF)
			{
				e.getPlayer().setLevel(levelF);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(BlockExpEvent e) {
		e.setExpToDrop(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(EntityDeathEvent e) {
		e.setDroppedExp(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(ExpBottleEvent e) {
		e.setExperience(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(PlayerFishEvent e) {
		e.setExpToDrop(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onIce(BlockFadeEvent e) {
		e.setCancelled(true);
	}
	
    @EventHandler
    public void noFarmlanddestroy(final PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }
    }
    
	@EventHandler
	public void onPlayerInteract3(PlayerInteractEvent e) 
	{
		Action act = e.getAction();
		Player p = e.getPlayer();
		
		if (act == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = e.getItem();
			if (item != null) {
				if (item.getTypeId() == 295 || item.getTypeId() == 296 || item.getTypeId() == 391 || item.getTypeId() == 392) {
					if (e.getClickedBlock().getTypeId() == 60) {
						if (item.hasItemMeta()) {
							e.setCancelled(true);
							p.updateInventory();
							p.sendMessage("");
							p.sendMessage("��c�� �������� �丮 ���� �۹���, ��ġ�� �Ұ��� �մϴ�.");
							p.sendMessage("��f- ��c��忡 �����ϼ̴ٸ� ��f/��� -> ��� ���� ��cŬ������ �۹��� �������ּ���.");
						}
					}
				}
				
				else if (item.getTypeId() == 338 || item.getTypeId() == 111 || item.getTypeId() == 37 || item.getTypeId() == 38) {
					if (item.hasItemMeta()) {
						e.setCancelled(true);
						p.updateInventory();
						p.sendMessage("");
						p.sendMessage("��c�� �������� �丮 ���� �۹���, ��ġ�� �Ұ��� �մϴ�.");
						p.sendMessage("��f- ��c��忡 �����ϼ̴ٸ� ��f/��� -> ��� ���� ��cŬ������ �۹��� �������ּ���.");
					}
				}
			}
		}
		
		if ((act == Action.RIGHT_CLICK_AIR) || (act == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CAKE_BLOCK) {
				e.setCancelled(true);
	        	p.sendMessage("��c����ũ�� ������ �� �����ϴ�.");
			}
	    }
		
		if (e.useItemInHand() != null) {
			ItemStack Item = p.getItemInHand();
			Material Mate = Item.getType();
			
			if (Mate == Material.POTION) {
				if (p.isOp()) {
                    return;
                }
				
                if (act == Action.RIGHT_CLICK_BLOCK) {
                    switch (e.getClickedBlock().getType()) {
                        case DISPENSER:
                        case CHEST:
                        case FURNACE:
                        case LEVER:
                        case STONE_BUTTON:
                        case WOOD_BUTTON: {
                        	p.sendMessage("��c������ ��� ��ư�� Ŭ���Ͻø� �۵����� �ʽ��ϴ�.");
                            break;
                        }
                        
                        default: {
                            break;
                        }
                    }
                }
                
                e.setCancelled(true);
                p.updateInventory();
			}
			
			else if (Item.getTypeId() == 260 || Item.getTypeId() == 282 || Item.getTypeId() == 297 || Item.getTypeId() == 319 || 
	 	        	 Item.getTypeId() == 320 || Item.getTypeId() == 322 || Item.getTypeId() == 349 || Item.getTypeId() == 350 || 
	 	        	 Item.getTypeId() == 357 || Item.getTypeId() == 360 || Item.getTypeId() == 363 || Item.getTypeId() == 364 || 
	 	        	 Item.getTypeId() == 365 || Item.getTypeId() == 366 || Item.getTypeId() == 367 || Item.getTypeId() == 375 || 
	 	        	 Item.getTypeId() == 393 || Item.getTypeId() == 400 || Item.getTypeId() == 394 || Item.getTypeId() == 391 || 
	 	        			Item.getTypeId() == 392) {
				if (p.isOp()) {
                    return;
                }
				
				if (Item.hasItemMeta() && Item.getItemMeta().hasDisplayName()) {
					return;
				}
				
                if (act == Action.RIGHT_CLICK_BLOCK) {
                    switch (e.getClickedBlock().getType()) {
                        case DISPENSER:
                        case CHEST:
                        case FURNACE:
                        case LEVER:
                        case STONE_BUTTON:
                        case WOOD_BUTTON: {
                            break;
                        }
                        
                        default: {
                            break;
                        }
                    }
                }
                
				e.setCancelled(true);
                p.updateInventory();
                p.sendMessage("");
				p.sendMessage("��c����� ������ ��� �� �����ϴ�.");
				p.sendMessage("��f- ��c������ ��f�丮 ��c�θ� ���۵� ���ĸ� ������ �� �ֽ��ϴ�. ��f/���� -> �丮 ����");
			}
			
			if (e.getClickedBlock() != null) {
        		if (e.getClickedBlock().getTypeId() != 60 && Item.getTypeId() == 391 ||
	 		        e.getClickedBlock().getTypeId() != 60 && Item.getTypeId() == 392) {
        			if (p.isOp()) {
                        return;
                    }
    				
    				if (Item.hasItemMeta()) {
    					return;
    				}
    				
                    if (act == Action.RIGHT_CLICK_BLOCK) {
                        switch (e.getClickedBlock().getType()) {
                            case DISPENSER:
                            case CHEST:
                            case FURNACE:
                            case LEVER:
                            case STONE_BUTTON:
                            case WOOD_BUTTON: {
                                break;
                            }
                            
                            default: {
                                break;
                            }
                        }
                    }
                    
    				e.setCancelled(true);
                    p.updateInventory();
                    p.sendMessage("");
    				p.sendMessage("��c����� ������ ��� �� �����ϴ�.");
    				p.sendMessage("��f- ��c������ ��f�丮 ��c�θ� ���۵� ���ĸ� ������ �� �ֽ��ϴ�. ��f/���� -> �丮 ����");
	        	}
        	} else {
        		if (Item.getTypeId() == 391 || Item.getTypeId() == 392) {
        			if (p.isOp()) {
                        return;
                    }
    				
    				if (Item.hasItemMeta()) {
    					return;
    				}
        			
    				e.setCancelled(true);
                    p.updateInventory();
                    p.sendMessage("");
    				p.sendMessage("��c����� ������ ��� �� �����ϴ�.");
    				p.sendMessage("��f- ��c������ ��f�丮 ��c�θ� ���۵� ���ĸ� ������ �� �ֽ��ϴ�. ��f/���� -> �丮 ����");
	        	}
        	}
		}
	}
    
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		Action act = e.getAction();
		Player p = e.getPlayer();
	    String pname = p.getName();
	    
		if (act == Action.RIGHT_CLICK_BLOCK)
		{
			Block b = e.getClickedBlock();
			  
			if (b.getType() == Material.ANVIL) {
				p.chat("/����");
				e.setCancelled(true);
			}
			
			else if (b.getType() == Material.FURNACE || b.getType() == Material.BURNING_FURNACE) {
				p.chat("/�丮");
				e.setCancelled(true);
			}
			
			else if (b.getType() == Material.ENCHANTMENT_TABLE) {
				p.chat("/����");
				e.setCancelled(true);
			}
		}
		
	    if ((act == Action.RIGHT_CLICK_AIR) || (act == Action.RIGHT_CLICK_BLOCK))
	    {
	        if ((e.getItem() != null) && 
	      	    (e.getItem().getItemMeta().getDisplayName() != null) && 
	        	(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(" ��ǥ")) && 
	        	(p.getItemInHand().getTypeId() == 339)) {
	        	
	    		ItemStack pi = p.getItemInHand();
	    		ItemMeta pitem = pi.getItemMeta();
	    		int won = Integer.parseInt((String)pitem.getLore().get(1));
	    		if (pitem.getLore() != null)
	    		{
	    			main.economy.depositPlayer(pname, won);
	    			p.sendMessage("��c��ǥ��6�� ����Ͽ� ��c" + won + " ��6���� ȹ���Ͽ����ϴ�.");
	    			int amount = pi.getAmount();
	    			if (amount == 1)
	    				p.getInventory().removeItem(new ItemStack[] { pi });
	    			else
	    				pi.setAmount(amount - 1);
	    		}
	    	}
	        
	        else if ((e.getItem() != null) && 
		      	    (e.getItem().getItemMeta().getDisplayName() != null) && 
		        	(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(" ���� ��ǥ")) && 
		        	(p.getItemInHand().getTypeId() == 339)) {
		        	
	        	ItemStack pi = p.getItemInHand();
	        	ItemMeta pitem = pi.getItemMeta();
	        	int lv = Integer.parseInt((String)pitem.getLore().get(1));
	        	if (pitem.getLore() != null)
	        	{
	        		p.setLevel(p.getLevel() + lv);
	        		p.sendMessage("��c���� ��ǥ��6�� ����Ͽ� ������ ��c" + lv + " ��6��ŭ ȹ���Ͽ����ϴ�.");
	        		int amount = pi.getAmount();
	        		if (amount == 1)
	        			p.getInventory().removeItem(new ItemStack[] { pi });
	        		else
	        			pi.setAmount(amount - 1);
	        	}
	        }
	    }
	}
	
	
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.LOWEST)
	  public void onMove(PlayerMoveEvent e)
	  {
		  if (e.getPlayer().getFireTicks() >= 1 && e.getPlayer().isSneaking() && e.getFrom().getX() == e.getTo().getX() && 
			  e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
		  {
			  if (Math.abs(e.getFrom().getYaw() - e.getTo().getYaw()) > 4 / 20.0D) {
				  if (main.FirePlayers.containsKey(e.getPlayer().getName()))
					  main.FirePlayers.put(e.getPlayer().getName(), Integer.valueOf(((Integer)main.FirePlayers.get(e.getPlayer().getName())).intValue() + 1));
				  else main.FirePlayers.put(e.getPlayer().getName(), Integer.valueOf(0));
				  
				  if (((Integer) main.FirePlayers.get(e.getPlayer().getName())).intValue() > 2 * 20) {
					  if (API.isCheck(e.getPlayer())) {
						  e.getPlayer().setFireTicks(0);
						  if (PlayerYml.getInfoBoolean(e.getPlayer(), "ȿ����")) e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIZZ, 1.0F, 1.0F);
						  main.FirePlayers.remove(e.getPlayer().getName());  
					  } else {
						  e.setCancelled(true);
						  API.sayMessage(e.getPlayer(), "�Ƿε��� �����Ͽ� ���� ���� �� �����ϴ�.");
					  }
				  }
			  } else if (main.FirePlayers.containsKey(e.getPlayer().getName())) {
				  main.FirePlayers.remove(e.getPlayer().getName());
			  }
		  } else if (main.FirePlayers.containsKey(e.getPlayer().getName()))
			  main.FirePlayers.remove(e.getPlayer().getName());
	  }
    
	public void TrashCan(Player p) 
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "��������");
		p.openInventory(inv);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (commandLabel.equalsIgnoreCase("bn")) {
			if (sender.isOp()) {
				if (args.length == 0) {
					if (explode) {
						explode = false;
						sender.sendMessage(ChatColor.RED + "�������� ǥ����, ���� �� ���� ��ȣ�� ���� �ʽ��ϴ�.");
					} else {
						explode = true;
						sender.sendMessage(ChatColor.GREEN + "�������� ǥ����, ���� �� ���� ��ȣ�� �޽��ϴ�.");
					}
				}
				
				else if (args.length == 1) {
					if (!(sender instanceof Player)) {
						return false;
					}
					
					((Player) sender).setFoodLevel(Integer.parseInt(args[0]));
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "����� �� ��ɾ ����� ������ �����ϴ�.");
			}
		}
		
		if(commandLabel.equalsIgnoreCase("ec"))
		{
			if(sender.isOp() == true) 
			{
				if(args.length == 0) {
					sender.sendMessage("��a���7��                                                 ��a���7��");
					sender.sendMessage("��7���c�� ��e----- ��6ESPOO core ��e-- ��6���� ��e----- ��7���c��");
					sender.sendMessage("");
					sender.sendMessage("��6/ec ��f- ESPOO Farm ������ ���ϴ�.");
					sender.sendMessage("��6/ec reload ��f- ESPOO Farm ���Ǳ׸� ���ε��մϴ�.");
					sender.sendMessage("��6/ä��û��, cc ��f- ä��û�Ҹ� �մϴ�.");
					sender.sendMessage("��6/���� ��f- ����÷��̾ ���� ��ŵ�ϴ�.");
					sender.sendMessage("��6/�� ��f- ����÷��̾ ���� Ķ �� ���� �˴ϴ�.");
					sender.sendMessage("��6/���� ��f- ����÷��̾ ������ �˴ϴ�.");
					sender.sendMessage("��6/ä�� ��f- ����÷��̾ ä���� �� �� ���� �˴ϴ�.");
					sender.sendMessage("��6/���� ��f- ������ ���� �Ǵ� �����մϴ�.");
					sender.sendMessage("��6/���� <0 �̻��� ����> ��f- ������ <0 �̻��� ����>�� �����մϴ�.");
					sender.sendMessage("��6/�������� ��f- ���������� �����մϴ�.");
					sender.sendMessage("��6/ǥ���� <��> <����> ��f- ǥ������ ������ �����մϴ�.");
					sender.sendMessage("��6/���� <����> ��f�տ� �� �������� ��ο��� �����մϴ�.");
					sender.sendMessage("��6/��ǥ <��> ��f- �� ��ŭ ��ǥ�� �����մϴ�.");
					sender.sendMessage("��6/�α⵵ <����/���/����> <�г���> ��f- �α⵵�� ���ų� ��½�Ű�ų� ���ҽ�ŵ�ϴ�.");
					sender.sendMessage("��6/��þƮ ��f- ��þƮ�� �մϴ�.");
					sender.sendMessage("��6/��þƮ��� ���� ��f- ��þƮ ����� ���ϴ�.");
					sender.sendMessage("��6/�����۱��� ��f- ������ ���ϴ�.");
					sender.sendMessage("��6/�����۱��� �߰� <���޼���> ��f- ����ִ� �������� ������� ���ϰ� �մϴ�.");
					sender.sendMessage("��6/�����۱��� �߰� ��� <���޼���> ��f- ����ִ� �������� ������ �ڵ嵵 ��� ������� ���ϰ� �մϴ�.");
					sender.sendMessage("��6/�����۱��� ���� ��f- ����ִ� ������ ���� ���θ� �����մϴ�.");
					sender.sendMessage("��6/�����۱��� Ȯ�� ��f- ����ִ� ������ �������θ� Ȯ���մϴ�.");
					sender.sendMessage("��6/�����÷ <����|Ʃ��|����|�Ź�> <������̸�> ��f- �������� ��� ��÷�մϴ�.");
					sender.sendMessage("");
					sender.sendMessage("��e----------------------------------------------------");
					return false;
				}
				
				if (args[0].equals("reload")) {
			    	loadConfig();
					getConfig().options().copyDefaults(true);
		            reloadConfig();
		            main.all = ((ArrayList<String>)getConfig().getStringList("Blacklist"));
		            main.place = ((ArrayList<String>)getConfig().getStringList("Blacklist Placement"));
		            main.pickup = ((ArrayList<String>)getConfig().getStringList("Blacklist Pickup"));
		            main.interact = ((ArrayList<String>)getConfig().getStringList("Blacklist Interaction"));
		            main.click = ((ArrayList<String>)getConfig().getStringList("Blacklist Click"));
		            main.br = ((ArrayList<String>)getConfig().getStringList("Blacklist Break"));
		            main.world = ((ArrayList<String>)getConfig().getStringList("Blacklist World"));
				    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "�˸�" + ChatColor.WHITE + "]" + ChatColor.GREEN + "���Ǳ� ���ε带 �Ϸ��߽��ϴ�.");
				}
			}
		}
		
		if(commandLabel.equalsIgnoreCase("cc") || commandLabel.equalsIgnoreCase("ä��û��")) {
			if(sender.isOp() == true) {
				for (int i = 0; i < 50; i++) {
					Bukkit.broadcastMessage("");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "����� �� ��ɾ ����� ������ �����ϴ�.");
			}
		}
		
		if (commandLabel.equals("����"))
		{
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.stopE)
				{
					Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �� ���� �����Ǿ����ϴ�.");
					main.stopE = true;
					getConfig().addDefault("����", Boolean.valueOf(true));
					return true;
				}

				Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �� �ְ� �����Ǿ����ϴ�.");
				main.stopE = false;
				getConfig().addDefault("����", Boolean.valueOf(false));
				return true;
			}

			Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �� �ְ� �����Ǿ����ϴ�.");
			return true;
		}
		
		if (commandLabel.equals("����������give"))
		{
			if (sender.isOp()) {
				if (args.length == 2) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 20) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + args[0] + " give " + args[1] + " 1");	
					}
				} return false;
			} return false;
		}

		if (commandLabel.equals("o"))
		{
			if (sender.isOp())
			{
				stopE = true;
				blockE = true;
				healthE = true;
				chatE = true;

				Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
					public void run() {
						if (inte == 5) {
							for (int i = 0; i < 100; i++) {
								Bukkit.broadcastMessage("");
							}
							
							for (Player p : Bukkit.getOnlinePlayers()) {
								 p.closeInventory();
							 }
							
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c5�� ��6�� ����˴ϴ�.");
						}
						
						else if (inte == 4) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c4�� ��6�� ����˴ϴ�.");
						}

						else if (inte == 3) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c3�� ��6�� ����˴ϴ�.");
						}

						else if (inte == 2) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c2�� ��6�� ����˴ϴ�.");
						}

						else if (inte == 1) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c1�� ��6�� ����˴ϴ�.");
						}

						else if (inte == 0) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
						}
						
						--inte;
					}
				}, 20L, 20L);
				
				return false;
			}

			sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
			return false;
		}
		
		if (commandLabel.equals("��"))
		{
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.blockE)
				{
					main.blockE = true;
					Bukkit.broadcastMessage("��f[��4�˸���f] ��6���� �μ��� ���� �����Ǿ����ϴ�.");
					getConfig().addDefault("��", Boolean.valueOf(true));
					return true;
				}
				
				main.blockE = false;
				Bukkit.broadcastMessage("��f[��4�˸���f] ��6���� �μ��� �ְ� �����Ǿ����ϴ�.");
				getConfig().addDefault("��", Boolean.valueOf(false));
				return true;
			}

			sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
			return true;
		}
		
		if (commandLabel.equals("���Ǳ���"))
		{
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("����_���")) {
					p.closeInventory();
					p.sendMessage("��6�� ���� ������ ��c���š�6�Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "����_���");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("ö_����")) {
					p.closeInventory();
					p.sendMessage("��6�� ���� ������ ��c���š�6�Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "ö_����");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("���̾Ƹ��_����")) {
					p.closeInventory();
					p.sendMessage("��6�� ���� ������ ��c���š�6�Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "���̾Ƹ��_����");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�Ϲ�_��ȭ_�ֹ���")) {
					p.closeInventory();
					p.sendMessage("��6�� ���� ������ ��c���š�6�Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "�Ϲ�_��ȭ_�ֹ���");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("Ư��_��ȭ_�ֹ���")) {
					p.closeInventory();
					p.sendMessage("��6�� ���� ������ ��c���š�6�Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "Ư��_��ȭ_�ֹ���");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�����̾�_��ȭ_�ֹ���")) {
					p.closeInventory();
					p.sendMessage("��6�� ���� ������ ��c���š�6�Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "�����̾�_��ȭ_�ֹ���");
					return false;
				}
			}
			
			else if (args.length == 3) {
				p.closeInventory();
				String str = args[2].replaceAll("_", " ");
				p.sendMessage("��6�� ���� ��c" + str + " ��6��/�� �����Ͻðڽ��ϱ�?");
				p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
				shop.put(p.getName(), Integer.parseInt(args[1]));
				buyme.put(p.getName(), args[0]);
				buyto.put(p.getName(), str);
				return false;
			}
		}
		
		
		
		if (commandLabel.equals("�������ť��"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
			
			if (!(args.length == 1)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("����")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c������� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����������� give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�ݼ� ��Ʈ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ݼӹ�Ʈ���� give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���� ���ڡ�6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���Ÿ��� give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������� give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c��Ż����Ʈ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��Ż����Ʈ���� give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���߸� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���߸Ǹ��� give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c������ �÷��� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������÷��ø��� give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�ǹ��� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ǹ��ظ��� give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�Ƹ��̸���ũ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �Ƹ��̸���ũ���� give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c����� �繫���� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����͸��� give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c������ �Ҵ� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ǼҴи��� give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���뽺 ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���뽺���� give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c����� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ǹ��� give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���ձ� ������ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ձݰ��������� give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��cŷ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ŷ���� give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��cŸ����Ű ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ÿ����Ű���� give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c��ũ�� ������ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ũ�鸶���͸��� give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�������� ������� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ʸ��� give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�ĺ�Ű ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ĺ�Ű���� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("Ʃ��") || args[0].equalsIgnoreCase("����")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c������� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������簩�� give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c�ݼ� ��Ʈ Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ݼӹ�ƮƩ�� give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c���� Ʃ�С�6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ʃ�� give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c���� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ʃ�� give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c��Ż����Ʈ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��Ż����Ʈ���� give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c���߸� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���߸�Ʃ�� give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c������ �÷��� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������÷���Ʃ�� give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c�ǹ��� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ǹ���Ʃ�� give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c�Ƹ��̸���ũ Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �Ƹ��̸���ũƩ�� give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c����� �繫���� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����Ʃ�� give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c������ �Ҵ� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ǼҴ�Ʃ�� give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c���뽺 ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���뽺���� give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c����� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����Ʃ�� give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c���ձ� ������ Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ձݰ�����Ʃ�� give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��cŷ Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ŷƩ�� give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��cŸ����Ű Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ÿ����ŰƩ�� give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c��ũ�� ������ Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ũ�鸶����Ʃ�� give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c�������� ������� Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������Ʃ�� give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("��6���� Ʃ�� ť�긦 �����Ͽ� ��c�ĺ�Ű Ʃ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ĺ�ŰƩ�� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("���뽺")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c������� ���뽺 ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������緹�뽺 give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�ݼ� ��Ʈ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ݼӹ�Ʈ���� give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���� ������6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���Ź��� give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������� give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c��Ż����Ʈ ���뽺 ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��Ż����Ʈ���뽺 give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���߸� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���߸ǹ��� give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c������ �÷��� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������÷��ù��� give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�ǹ��� ���뽺 ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ǹ��ط��뽺 give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�Ƹ��̸���ũ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �Ƹ��̸���ũ���� give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c����� �繫���� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����͹��� give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c������ �Ҵ� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ǼҴй��� give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���뽺 ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���뽺���� give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c����� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����ǹ��� give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c���ձ� ������ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ձݰ��������� give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��cŷ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ŷ���� give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��cŸ����Ű ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ÿ����Ű���� give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c��ũ�� ������ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ũ�鸶���͹��� give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�������� ������� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ʹ��� give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("��6���� ���� ť�긦 �����Ͽ� ��c�ĺ�Ű ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ĺ�Ű���� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("�Ź�") || args[0].equalsIgnoreCase("����")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c������� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���������� give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c�ݼ� ��Ʈ �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ݼӹ�Ʈ�Ź� give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c���� �Źߡ�6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ŽŹ� give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c���� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����Ź� give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c��Ż����Ʈ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��Ż����Ʈ���� give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c���߸� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���߸ǽŹ� give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c������ �÷��� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������÷��ýŹ� give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c�ǹ��� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ǹ��ؽŹ� give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c�Ƹ��̸���ũ �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �Ƹ��̸���ũ�Ź� give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c����� �繫���� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����ͽŹ� give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c������ �Ҵ� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ǼҴнŹ� give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c���뽺 �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���뽺�Ź� give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c����� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����ǽŹ� give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c���ձ� ������ �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ձݰ������Ź� give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��cŷ �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ŷ�Ź� give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��cŸ����Ű �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ÿ����Ű�Ź� give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c��ũ�� ������ �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ũ�鸶���ͽŹ� give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c�������� ������� �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ʽŹ� give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("��6���� �Ź� ť�긦 �����Ͽ� ��c�ĺ�Ű �Ź� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ĺ�Ű�Ź� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("����Ÿ���Ϲ�")) {
				int num = 0;
				ItemStack[] contents;
				for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
					ItemStack it = contents[j];
					if (it == null) {
						num++;
					}
				}
				
				if (num < 4) {
					p.sendMessage("��c�κ��丮 4ĭ�� ����ֽð� ����� �ֽñ� �ٶ��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ���Ϲ�ť�� give " + p.getName() + " 1");
					return false;
				} else {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ������ give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ��Ʃ�� give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ������ give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ���Ź� give " + p.getName() + " 1");
					castLvup(p);
					p.sendMessage("��6����Ÿ�� �Ϲ� ��� ��Ʈ�� ��cȹ���6�ϼ̽��ϴ�.");
					return false;
				}
			}
			
			else if (args[0].equalsIgnoreCase("����Ÿ������ũ")) {
				int num = 0;
				ItemStack[] contents;
				for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
					ItemStack it = contents[j];
					if (it == null) {
						num++;
					}
				}
				
				if (num < 4) {
					p.sendMessage("��c�κ��丮 4ĭ�� ����ֽð� ����� �ֽñ� �ٶ��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ������ũť�� give " + p.getName() + " 1");
					return false;
				} else {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ������ give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ��Ʃ�� give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ������ give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ���Ź� give " + p.getName() + " 1");
					castLvup(p);
					p.sendMessage("��6����Ÿ�� ����ũ ��� ��Ʈ�� ��cȹ���6�ϼ̽��ϴ�.");
					return false;
				}
			}
		}
		
		if (commandLabel.equals("���귣������ť��"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
			
			if (!(args.length == 0)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			int RandomAmount = new Random().nextInt(3) + 1;
			
			switch (RandomAmount) {
			case 1:
				p.sendMessage("��6���� ���� ���� ť�긦 �����Ͽ� ��c1�� ���� ť�� ��6�� ȹ���ϼ̽��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1������ť�� give " + p.getName() + " 1");
				castLvup(p);
				break;
			case 2:
				p.sendMessage("��6���� ���� ���� ť�긦 �����Ͽ� ��c3�� ���� ť�� ��6�� ȹ���ϼ̽��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3������ť�� give " + p.getName() + " 1");
				castLvup(p);
				break;
			case 3:
				p.sendMessage("��6���� ���� ���� ť�긦 �����Ͽ� ��c4�� ���� ť�� ��6�� ȹ���ϼ̽��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4������ť�� give " + p.getName() + " 1");
				castLvup(p);
				break;
			} return false;
		}
		
		if (commandLabel.equals("����ݰ���"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
			
			if (!(args.length == 0)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			int won = new Random().nextInt(195) + 6;
			main.economy.depositPlayer(p.getName(), won);
			p.sendMessage("��6���� �ݰ��縦 �����Ͽ� ��c" + won + " ��6���� ȹ���ϼ̽��ϴ�.");
			return false;
		}
		
		if (commandLabel.equals("��������ť��"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
			
			if (!(args.length == 1)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (args[0].equalsIgnoreCase("1")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						p.sendMessage("��61�� ���� ť�긦 �����Ͽ� ��cŷ�� ���ӱ� ��6�� ȹ���ϼ̽��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ŷ���� give " + p.getName() + " 1");
						castLvup(p);
						p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("3")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						p.sendMessage("��63�� ���� ť�긦 �����Ͽ� ��c����� ���� ��6�� ȹ���ϼ̽��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����ǹ��� give " + p.getName() + " 1");
						castLvup(p);
						p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("4")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(4) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��64�� ���� ť�긦 �����Ͽ� ��c�ĺ�Ű ������ ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ĺ�Ű���� give " + p.getName() + " 1");
							break;
							
						case 2:
							p.sendMessage("��64�� ���� ť�긦 �����Ͽ� ��c������ �� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������繫�� give " + p.getName() + " 1");
							break;
							
						case 3:
							p.sendMessage("��64�� ���� ť�긦 �����Ͽ� ��c���߸��� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���߸ǹ��� give " + p.getName() + " 1");
							break;
							
						case 4:
							p.sendMessage("��64�� ���� ť�긦 �����Ͽ� ��c�������� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���ձݰ��������� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("5")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(9) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c�ݼ� ��Ʈ ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ݼӹ�Ʈ���� give " + p.getName() + " 1");
							break;
							
						case 2:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c������ ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���Ź��� give " + p.getName() + " 1");
							break;
							
						case 3:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c���� �ӽŰ� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������� give " + p.getName() + " 1");
							break;
							
						case 4:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c�ɹ̳� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ɹ̳����鹫�� give " + p.getName() + " 1");
							break;
							
						case 5:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c����� �繫���� �� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����͹��� give " + p.getName() + " 1");
							break;
							
						case 6:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c������ �Ҵ� Į ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ǼҴй��� give " + p.getName() + " 1");
							break;
							
						case 7:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c���� ��Ÿ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ʹ��� give " + p.getName() + " 1");
							break;
							
						case 8:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c���뽺�� ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���뽺���� give " + p.getName() + " 1");
							break;
							
						case 9:
							p.sendMessage("��65�� ���� ť�긦 �����Ͽ� ��c��ũ�� ������ ���� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ũ�鸶���͹��� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("6")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(4) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("��66�� ���� ť�긦 �����Ͽ� ��c��Ż����Ʈ �̻��� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��Ż����Ʈ���� give " + p.getName() + " 1");
							break;
							
						case 2:
							p.sendMessage("��66�� ���� ť�긦 �����Ͽ� ��c������ �÷��� �� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������÷��ù��� give " + p.getName() + " 1");
							break;
							
						case 3:
							p.sendMessage("��66�� ���� ť�긦 �����Ͽ� ��c�����ϼ�� ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ǹ��ع��� give " + p.getName() + " 1");
							break;
							
						case 4:
							p.sendMessage("��66�� ���� ť�긦 �����Ͽ� ��cŸ����Ű ������ ��6�� ȹ���ϼ̽��ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ÿ����Ű���� give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
		}
		
		if (commandLabel.equals("�����÷"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
			
			if (!(args.length == 2)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
				
			if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("����")) {
				if (args[1].equalsIgnoreCase("Ÿ����Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ǹ���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ǹ��� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ǹ��ظ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ǹ��ظ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ǹ��ظ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ǹ��ظ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��Ż����Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("ŷ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŷ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���Ÿ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���Ÿ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���Ÿ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���Ÿ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���ձݰ�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���߸�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���߸� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���߸Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���߸Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���߸Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���߸Ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������÷���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������÷��ø��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������÷��ø��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������÷��ø��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������÷��ø��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��ũ�鸶����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��ũ�鸶���͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��ũ�鸶���͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��ũ�鸶���͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��ũ�鸶���͸��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���뽺")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���뽺 ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ݼӹ�Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�������� ������� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1������ʸ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2������ʸ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3������ʸ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4������ʸ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�Ƹ��̸���ũ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ĺ�Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����ǼҴ�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����ǼҴи��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����ǼҴи��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����ǼҴи��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����ǼҴи��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����Ÿ��")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("Ʃ��") || args[0].equalsIgnoreCase("����")) {
				if (args[1].equalsIgnoreCase("Ÿ����Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŸ����Ű Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1Ÿ����ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2Ÿ����ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3Ÿ����ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4Ÿ����ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ǹ���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ǹ��� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ǹ���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ǹ���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ǹ���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ǹ���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� �繫���� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��Ż����Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("ŷ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŷ Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1ŷƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2ŷƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3ŷƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4ŷƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1������簩�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2������簩�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3������簩�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4������簩�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���ձݰ�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���ձ� ������ Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���ձݰ�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���ձݰ�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���ձݰ�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���ձݰ�����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���߸�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���߸� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���߸�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���߸�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���߸�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���߸�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������÷���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �÷��� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������÷���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������÷���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������÷���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������÷���Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��ũ�鸶����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��ũ�� ������ Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��ũ�鸶����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��ũ�鸶����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��ũ�鸶����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��ũ�鸶����Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���뽺")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���뽺 ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���뽺Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���뽺Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ݼӹ�Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ݼӹ�Ʈ Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ݼӹ�ƮƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ݼӹ�ƮƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ݼӹ�ƮƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ݼӹ�ƮƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�������� ������� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�Ƹ��̸���ũ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�Ƹ��̸���ũ Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�Ƹ��̸���ũƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�Ƹ��̸���ũƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�Ƹ��̸���ũƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�Ƹ��̸���ũƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ĺ�Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ĺ�Ű Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ĺ�ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ĺ�ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ĺ�ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ĺ�ŰƩ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����ǼҴ�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �Ҵ� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����ǼҴ�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����ǼҴ�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����ǼҴ�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����ǼҴ�Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����Ÿ��")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����Ÿ�� Ʃ�� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ��Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� Ʃ�� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ÿ��Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� Ʃ�� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ÿ��Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� Ʃ�� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ��Ʃ�� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("���뽺")) {
				if (args[1].equalsIgnoreCase("Ÿ����Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4Ÿ����Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ǹ���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ǹ��� ���뽺 ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ǹ��ط��뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� ���뽺 ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ǹ��ط��뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� ���뽺 ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ǹ��ط��뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� ���뽺 ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ǹ��ط��뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��Ż����Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��Ż����Ʈ ���뽺 ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��Ż����Ʈ���뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���뽺 ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��Ż����Ʈ���뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���뽺 ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��Ż����Ʈ���뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���뽺 ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��Ż����Ʈ���뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("ŷ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŷ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4ŷ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������� ���뽺 ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1������緹�뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���뽺 ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2������緹�뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���뽺 ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3������緹�뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���뽺 ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4������緹�뽺 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���Ź��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���Ź��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���Ź��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���Ź��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���ձݰ�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���ձݰ��������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���߸�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���߸� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���߸ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���߸ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���߸ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���߸ǹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������÷���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������÷��ù��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������÷��ù��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������÷��ù��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������÷��ù��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��ũ�鸶����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��ũ�鸶���͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��ũ�鸶���͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��ũ�鸶���͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��ũ�鸶���͹��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���뽺")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���뽺 ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���뽺���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ݼӹ�Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ݼӹ�Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�������� ������� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1������ʹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2������ʹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3������ʹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4������ʹ��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�Ƹ��̸���ũ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�Ƹ��̸���ũ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ĺ�Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ĺ�Ű���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����ǼҴ�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����ǼҴй��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����ǼҴй��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����ǼҴй��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����ǼҴй��� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����Ÿ��")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ������ give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("�Ź�") || args[0].equalsIgnoreCase("����")) {
				if (args[1].equalsIgnoreCase("Ÿ����Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŸ����Ű �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1Ÿ����Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2Ÿ����Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3Ÿ����Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŸ����Ű �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4Ÿ����Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ǹ���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ǹ��� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ǹ��ؽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ǹ��ؽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ǹ��ؽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ǹ��� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ǹ��ؽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� �繫���� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �繫���� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��Ż����Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��Ż����Ʈ ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��Ż����Ʈ���� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("ŷ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��cŷ �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1ŷ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2ŷ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3ŷ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��cŷ �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4ŷ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������� ���� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������� ���� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���������� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���ŽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���ŽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���ŽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���ŽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���ձݰ�����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���ձ� ������ �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���ձݰ������Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���ձݰ������Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���ձݰ������Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���ձ� ������ �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���ձݰ������Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���߸�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���߸� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���߸ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���߸ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���߸ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���߸� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���߸ǽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������÷���")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �÷��� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�������÷��ýŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�������÷��ýŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�������÷��ýŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �÷��� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�������÷��ýŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("��ũ�鸶����")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c��ũ�� ������ �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1��ũ�鸶���ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2��ũ�鸶���ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3��ũ�鸶���ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c��ũ�� ������ �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4��ũ�鸶���ͽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("���뽺")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c���뽺 �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1���뽺�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2���뽺�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3���뽺�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c���뽺 �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4���뽺�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ݼӹ�Ʈ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ݼӹ�Ʈ �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ݼӹ�Ʈ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ݼӹ�Ʈ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ݼӹ�Ʈ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ݼӹ�Ʈ �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ݼӹ�Ʈ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�������")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�������� ������� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1������ʽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2������ʽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3������ʽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�������� ������� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4������ʽŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�Ƹ��̸���ũ")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�Ƹ��̸���ũ �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�Ƹ��̸���ũ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�Ƹ��̸���ũ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�Ƹ��̸���ũ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�Ƹ��̸���ũ �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�Ƹ��̸���ũ�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�ĺ�Ű")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c�ĺ�Ű �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�ĺ�Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�ĺ�Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�ĺ�Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c�ĺ�Ű �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�ĺ�Ű�Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("�����ǼҴ�")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c������ �Ҵ� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1�����ǼҴнŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2�����ǼҴнŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3�����ǼҴнŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c������ �Ҵ� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4�����ǼҴнŹ� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("����Ÿ��")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("��6��� ��c����Ÿ�� �Ź� ��6�� �����Ͽ� ��f�Ϲ� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1����Ÿ���Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� �Ź� ��6�� �����Ͽ� ��e���� ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2����Ÿ���Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� �Ź� ��6�� �����Ͽ� ��d����Ʈ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3����Ÿ���Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("��6�����մϴ�! ��� ��c����Ÿ�� �Ź� ��6�� �����Ͽ� ��c����ũ ��6����� ȹ���ϼ̽��ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4����Ÿ���Ź� give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			return false;
		}

		if (commandLabel.equals("����"))
	    {
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.healthE)
				{
					main.healthE = true;
					Bukkit.broadcastMessage("��f[��4�˸���f] ��6�������·� ����Ǿ����ϴ�.");
					getConfig().addDefault("����", Boolean.valueOf(true));
					return true;
				}

				main.healthE = false;
				Bukkit.broadcastMessage("��f[��4�˸���f] ��6�Ϲݻ��·� ����Ǿ����ϴ�.");
				getConfig().addDefault("����", Boolean.valueOf(false));
				return true;
			}

			sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
			return true;
	    }
		
		if (commandLabel.equals("ä��"))
		{
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.chatE)
				{
					main.chatE = true;
					Bukkit.broadcastMessage("��f[��4�˸���f] ��6ä���� ���ϵ��� �����Ǿ����ϴ�.");
					getConfig().addDefault("����", Boolean.valueOf(true));
					return true;
				}

				main.chatE = false;
				Bukkit.broadcastMessage("��f[��4�˸���f] ��6ä���� �Ҽ��ְ� �����Ǿ����ϴ�.");
				getConfig().addDefault("����", Boolean.valueOf(false));
				return true;
			}

		}
		
		if (commandLabel.equals("����"))
	    {
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (args.length == 0)
				{
					if (!main.levelE)
					{
						main.levelE = true;
						Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �����Ǿ����ϴ�.");
						Bukkit.broadcastMessage(ChatColor.GOLD + "���� : " + main.levelF);
						getConfig().addDefault("����", Boolean.valueOf(true));
						return true;
					}

					main.levelE = false;
					Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �����Ǿ����ϴ�.");
					getConfig().addDefault("����", Boolean.valueOf(false));
					return true;
				}

				if (args.length == 1)
				{
					main.levelF = Integer.parseInt(args[0]);
					Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c" + main.levelF + "��6�� �����Ǿ����ϴ�.");
					getConfig().addDefault("����", Integer.valueOf(main.levelF));
					return true;
				}

				sender.sendMessage(ChatColor.RED + "��ɾ� ����");
				sender.sendMessage(ChatColor.RED + "/���� - ������ ���� �Ǵ� �����մϴ�.");
				sender.sendMessage(ChatColor.RED + "/���� <0 �̻��� ����> - ������ <0 �̻��� ����>�� �����մϴ�.");
				return true;
			}
	    }
		
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if (commandLabel.equalsIgnoreCase("�κ�"))
			{
				boolean isOp = p.isOp();
				p.setOp(true);
				p.chat("/bwp lobby");
				p.setOp(isOp);
				p.setOp(isOp);
				p.setOp(isOp);
				p.setOp(isOp);
				p.setOp(isOp);
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڻ�"))
			{
				p.chat("/suicide");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("tmvhs"))
			{
				p.chat("/spawn");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("Ȩ"))
			{
				p.chat("/home");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("��Ȩ"))
			{
				p.chat("/sethome");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("��嶥�̵�"))
			{
				GuildGrandToMove(p);
				return false;
			}
		    
			try {
				if(commandLabel.equalsIgnoreCase("��������"))
				{
					TrashCan(p);
					return false;
				}
			} catch (NumberFormatException ex) {
				TrashCan(p);
				return false;
			}
			
			try {
				if (commandLabel.equals("ǥ����")) {
					Player play = (Player)sender;
					if (!play.hasPermission("se.edit")) {
						sender.sendMessage("��f[��4����f] ��c����� ������ �����ϴ�.");
						return true;
					}
			
					Block b = play.getTargetBlock(null, 10);
					if (b == null) {
						sender.sendMessage("��f[��4����f] ��c����� �տ� ǥ������ �����ϴ�.");
						return true;
					}
					
					if (!(b.getState() instanceof Sign)) {
						sender.sendMessage("��f[��4����f] ��c����� �տ� ǥ������ �����ϴ�.");
						return true;
					}
		
					if (args.length == 0) {
						sender.sendMessage("��f[��4����f] ��c�� ��ȣ�� ǥ���� ������ �����ּ���.");
						return true;
					}
		
					try {
						int line = Integer.parseInt(args[0]);
					} catch (NumberFormatException e) {
						int line;
						sender.sendMessage("Could not parse '" + args[0] + "' as a number.");
						return true;
					}
		
					int line = Integer.parseInt(args[0]);
					if ((line < 1) || (line > 4)) {
						sender.sendMessage("��f[��4����f] ��c���� ���� 1~4�� �����ּ���.");
						return true;
					}
		
					line--;
					String message = "";
					for (int i = 1; i < args.length; i++) message = message + " " + args[i];
					if (play.hasPermission("se.editCol")) message = Method.replaceAllColors(message);
					message = message.trim();
					if (message.length() > 15) {
						sender.sendMessage("��f[��4����f] ��cǥ���� ������ �ʹ� ��ϴ�.");
						return true;
					}
					Bukkit.getScheduler().runTaskAsynchronously(this, new Runner(play, line, message, b));
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/ǥ���� <��> <����> ��f- ǥ������ ������ �����մϴ�.");
				return true;
			}
			
			if (commandLabel.equalsIgnoreCase("������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(150000) + 1;
					main.economy.depositPlayer(p.getName(), RandomAmount);
					p.sendMessage("��c���ǡ�6�� �ܾ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					castLvup(p);
					return false;
				}
			}
			
			if (commandLabel.equalsIgnoreCase("��������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(3) + 1;
					int random = new Random().nextInt(4) + 1;
					
					switch (random) {
					case 1:
						Method.sendCommand("����a �߰� 1 " + p.getName() + " " + RandomAmount);
						p.sendMessage("��c���� ���ݱ� ��6�� ����Ͽ� ��c�� �ٷ� ��6 ������ ��c" + RandomAmount + " ��6��ŭ ȹ���ϼ̽��ϴ�.");
						break;
						
					case 2:
						Method.sendCommand("����a �߰� 2 " + p.getName() + " " + RandomAmount);
						p.sendMessage("��c���� ���ݱ� ��6�� ����Ͽ� ��c���� ��6 ������ ��c" + RandomAmount + " ��6��ŭ ȹ���ϼ̽��ϴ�.");
						break;
						
					case 3:
						Method.sendCommand("����a �߰� 3 " + p.getName() + " " + RandomAmount);
						p.sendMessage("��c���� ���ݱ� ��6�� ����Ͽ� ��c�ٸ� �ٷ� ��6 ������ ��c" + RandomAmount + " ��6��ŭ ȹ���ϼ̽��ϴ�.");
						break;
						
					case 4:
						Method.sendCommand("����a �߰� 4 " + p.getName() + " " + RandomAmount);
						p.sendMessage("��c���� ���ݱ� ��6�� ����Ͽ� ��c���ǵ� ��6 ������ ��c" + RandomAmount + " ��6��ŭ ȹ���ϼ̽��ϴ�.");
						break;
					} castLvup(p);
					return false;
				}
			}

			if (commandLabel.equalsIgnoreCase("��������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(3) + 1;

					switch (RandomAmount) {
					case 1:
						p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_BLOCK, 2) });
						p.sendMessage("��c���� ���� ���ڡ�6�� �����Ͽ� ��c���̾Ƹ�� �� ��6�� ȹ���ϼ̽��ϴ�.");
						break;
						
					case 2:
						p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_BLOCK, 4) });
						p.sendMessage("��c���� ���� ���ڡ�6�� �����Ͽ� ��cö �� ��6�� ȹ���ϼ̽��ϴ�.");
						break;
						
					case 3:
						p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE, 64) });
						p.sendMessage("��c���� ���� ���ڡ�6�� �����Ͽ� ��c�� ��6�� ȹ���ϼ̽��ϴ�.");
						break;
					} castLvup(p);
					return false;
				}
			}
			
			if (commandLabel.equalsIgnoreCase("����Īȣ")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					String str = null;

					switch (RandomAmount) {
					case 1:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3�̻�����&f]"); break;
					case 2:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&b��ȭ�ϴ�&f]"); break;
					case 3:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6��������&f]"); break;
					case 4:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e������&f]"); break;
					case 5:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&dȲ����&f]"); break;
					case 6:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c�ݷ���&f]"); break;
					case 7:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&a��ȭ��&f]"); break;
					case 8:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2Ȳ����&f]"); break;
					case 9:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9���Ҷ���&f]"); break;
					case 10:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&4�μ���&f]"); break;
					case 11:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6�����&f]"); break;
					case 12:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e������&f]"); break;
					case 13:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c������&f]"); break;
					case 14:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&b������&f]"); break;
					case 15:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3�Ϲ�����&f]"); break;
					case 16:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&b�����&f]"); break;
					case 17:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&4���ֹ���&f]"); break;
					case 18:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5�ջ��&f]"); break;
					case 19:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9��������&f]"); break;
					case 20:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5������&f]"); break;
					case 21:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&a��������&f]"); break;
					case 22:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&d��������&f]"); break;
					case 23:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&aȯ����&f]"); break;
					case 24:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&7�����&f]"); break;
					case 25:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c�ջ��&f]"); break;
					case 26:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3�ҿ�����&f]"); break;
					case 27:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2�ߵ��Ǵ�&f]"); break;
					case 28:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&a������&f]"); break;
					case 29:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&7�����&f]"); break;
					case 30:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2������&f]"); break;
					case 31:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2������&f]"); break;
					case 32:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5��������&f]"); break;
					case 33:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&bâ����&f]"); break;
					case 34:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6�����&f]"); break;
					case 35:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9������&f]"); break;
					case 36:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&a������&f]"); break;
					case 37:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&4�ı���&f]"); break;
					case 38:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e������&f]"); break;
					case 39:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c����&f]"); break;
					case 40:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&d������&f]"); break;
					case 41:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2������&f]"); break;
					case 42:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6��������&f]"); break;
					case 43:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&bâ������&f]"); break;
					case 44:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&a�к��ִ�&f]"); break;
					case 45:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6������&f]"); break;
					case 46:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&dȰ������&f]"); break;
					case 47:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&4��������&f]"); break;
					case 48:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&b��Ȥ��&f]"); break;
					case 49:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9��������&f]"); break;
					case 50:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3����ִ�&f]"); break;
					case 51:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e�米����&f]"); break;
					case 52:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c��ȣ��&f]"); break;
					case 53:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e���ٽ�����&f]"); break;
					case 54:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9�����ִ�&f]"); break;
					case 55:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c������&f]"); break;
					case 56:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&7�ҽ���&f]"); break;
					case 57:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&4�ò�����&f]"); break;
					case 58:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2��ٷο�&f]"); break;
					case 59:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&d�̱�����&f]"); break;
					case 60:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c�Ÿ���&f]"); break;
					case 61:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5��������&f]"); break;
					case 62:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6������&f]"); break;
					case 63:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9������&f]"); break;
					case 64:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2�Ͻ���&f]"); break;
					case 65:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&b��������&f]"); break;
					case 66:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e��������&f]"); break;
					case 67:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5��������&f]"); break;
					case 68:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3��������&f]"); break;
					case 69:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&7ħ����&f]"); break;
					case 70:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c�ܼ���&f]"); break;
					case 71:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&b��������&f]"); break;
					case 72:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&a������&f]"); break;
					case 73:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c��������&f]"); break;
					case 74:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5�Ǹ����&f]"); break;
					case 75:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3��������&f]"); break;
					case 76:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e�������&f]"); break;
					case 77:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6������&f]"); break;
					case 78:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5���뼺�ִ�&f]"); break;
					case 79:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2�ǹ���&f]"); break;
					case 80:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9��Ŭ����&f]"); break;
					case 81:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&2Ư����&f]"); break;
					case 82:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3�����&f]"); break;
					case 83:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6���к���&f]"); break;
					case 84:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e�����&f]"); break;
					case 85:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&7����&f]"); break;
					case 86:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c��������&f]"); break;
					case 87:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e������&f]"); break;
					case 88:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6����ٴ���&f]"); break;
					case 89:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&c�Ƕ���&f]"); break;
					case 90:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&5�ŷ�����&f]"); break;
					case 91:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&d��Ȥ����&f]"); break;
					case 92:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6�ڽ���&f]"); break;
					case 93:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3������&f]"); break;
					case 94:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&9��������&f]"); break;
					case 95:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&4������&f]"); break;
					case 96:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&6�߻���&f]"); break;
					case 97:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&3������&f]"); break;
					case 98:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&d������&f]"); break;
					case 99:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&e����ִ�&f]"); break;
					case 100:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "Īȣ���� �ֱ� " + p.getName() + " &f[&bâ������&f]"); break;
					}
					
					castLvup(p);
					return false;
				}
			}
			
			try {
				if(commandLabel.equalsIgnoreCase("��ǥ"))
				{
					String pname = p.getName();
					if ((main.economy.getBalance(pname) >= Integer.valueOf(args[0]).intValue()) && (Integer.valueOf(args[0]).intValue() > 0))
						{
						ItemStack item1 = new ItemStack(339, 1);
						ItemMeta itemM = item1.getItemMeta();
						itemM.setDisplayName("��e��o ��ǥ");
						itemM.setLore(Arrays.asList(new String[] { "��f��o�ѤѤѤѤѤѤѤ�", args[0], "��f��o�ѤѤѤѤѤѤѤ�", "��f", "��7��o������ Ŭ������", "��7��o������ ȯ�� ����", "��7", "��7��oby. ��a��oESPOO ����" }));
						item1.setItemMeta(itemM);
						p.getInventory().addItem(new ItemStack[] { item1 });
						main.economy.withdrawPlayer(pname, Double.valueOf(args[0]).doubleValue());
						p.sendMessage("��6���������� �߱޵Ǿ����ϴ�.");
						return true;
						}
			
					p.sendMessage(ChatColor.RED + "�����ϰ� ���� ���� ������ ������ �� ���ų� 0���� ���� ���� �Է��Ͽ����ϴ�.");
					return true;
				}
			} catch (Exception e) {
				p.sendMessage("��6/��ǥ <��> ��f- ��ǥ�� �����մϴ�.");
				return false;
			}
			
			ItemStack itemHand = p.getItemInHand();
			int id = itemHand.getType().getId();
			byte data = itemHand.getData().getData();
			String world = p.getWorld().getName();
			itemcheck itemmethod = new itemcheck(main.all, id, data, world);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String allArgs = sb.toString();
			
			try {
				if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args.length == 0)) {
					p.sendMessage("��6/�����۱��� ��f- ������ ���ϴ�.");
					p.sendMessage("��6/�����۱��� �߰� <���޼���> ��f- ����ִ� �������� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� �߰� ��� <���޼���> ��f- ����ִ� �������� ������ �ڵ嵵 ��� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� ���� ��f- ����ִ� ������ ���� ���θ� �����մϴ�.");
					p.sendMessage("��6/�����۱��� Ȯ�� ��f- ����ִ� ������ �������θ� Ȯ���մϴ�.");
					return false;
				} else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("�߰�")) && (args.length == 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())){
						p.sendMessage("��c��ɾ ����� ������ּ���. ��f- ��6/�����۱��� �߰� <���޼���>");
						return false;
					}
					else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				}
				else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("�߰�")) && (args[1].equalsIgnoreCase("���")) && (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0) {
							StringBuilder sb1 = new StringBuilder();
							for (int i = 2; i < args.length; i++) {
								sb1.append(args[i]).append(" ");
							}
							String allArgs1 = sb1.toString();
							main.all.add(id + ":-1:" + allArgs1);
        		  
							p.sendMessage("��6������ �ڵ� ��e[��c" + id + ":*��e] ��6�� ������ ���� ������ ��c�����˴ϴ١�6.:");
							p.sendMessage(" " + allArgs1);

							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						}
						else
						{
							p.sendMessage("��e[��c" + id + ":*" + "��e] ��6�������� �̹� �����Ǿ� �ֽ��ϴ�.");
							return false;
						}
					}
					else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				}
				else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("�߰�")) && (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0)
						{
							main.all.add(id + ":" + data + ":" + allArgs);

							p.sendMessage("��6�������ڵ� ��e[��c" + id + ":" + data + "��e] ��6�� ������ ���� ������ ��c�����˴ϴ١�6.:");		
							p.sendMessage(" " + allArgs);
    			
							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("��e[��c" + id + ":" + data + "��e] ��6�������� �̹� �����Ǿ� �ֽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("Ȯ��")) && (args.length == 1)) {
					if ((p.hasPermission("banitem.*")) || (p.isOp()) || (p.hasPermission("banitem.check"))) {
						if (itemmethod.getnumber() == 1)
						{
							p.sendMessage("��e[��c" + itemmethod.getId() + ":" + itemmethod.getData() + "��e] ��6�������� �̷��� ������ �����Ǿ� �ֽ��ϴ�:");
							p.sendMessage(" " + itemmethod.getReason());
							return false;
						} else {
							p.sendMessage("��c�� �������� �����Ǿ� ���� �ʽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}	
				}
				else if ((commandLabel.equalsIgnoreCase("�����۱���")) && ((args[0].equalsIgnoreCase("����")) || (args[0].equalsIgnoreCase("����"))) && 
						(args.length == 1)) {
					if ((p.hasPermission("banitem.remove")) || (p.hasPermission("banitem.del")) || (p.hasPermission("banitem.*")) || 
							(p.isOp())) {
						if (itemmethod.getnumber() == 1) {
							main.all.remove(itemmethod.getId() + ":" + itemmethod.getData() + ":" + itemmethod.getReason());

							p.sendMessage("��c�����ۡ�6�� ���������� ��c���� ��ϡ�6���� �����Ͽ����ϴ�.");
							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("��c�� �������� �����Ǿ� ���� �ʽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�."); 
						return false;
					}
				}
			} catch (Exception e) {
				if ((sender.isOp()))
				{
					p.sendMessage("��6/�����۱��� ��f- ������ ���ϴ�.");
					p.sendMessage("��6/�����۱��� �߰� <���޼���> ��f- ����ִ� �������� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� �߰� ��� <���޼���> ��f- ����ִ� �������� ������ �ڵ嵵 ��� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� ���� ��f- ����ִ� ������ ���� ���θ� �����մϴ�.");
					p.sendMessage("��6/�����۱��� Ȯ�� ��f- ����ִ� ������ �������θ� Ȯ���մϴ�.");
					return false;
				}
	    	
				else {
					p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
			return false;
		}
		
		return false;
	}
	
	public static class WorkTask extends TimerTask
	{
		public void run()
		{
			main.Memory = "[�˸�] �޸� ���� ����� : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L + "MB";
			System.gc();
			System.out.println(main.Memory + " , �޸� ���� ����� : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L + "MB");
		}
	}
	
	class Runner implements Runnable 
	{ 
		Player play;
		int line;
		String message;
		Block b;

		public Runner(Player player, int l, String m, Block block) 
		{ 
			this.play = player;
			this.line = l;
			this.message = m;
			this.b = block; 
		}

		public void run()
		{
			try 
			{
				Sign s = (Sign)this.b.getState();
				if (!this.play.hasPermission("se.editAny")) 
				{
					if (main.logblock == null) 
					{
						return;
					}
					
					QueryParams params = new QueryParams(main.logblock);
					params.bct = QueryParams.BlockChangeType.CREATED;
					params.limit = 1;
					params.needPlayer = true;
					params.loc = this.b.getLocation();
					params.needSignText = true;
					List<?> changes = main.logblock.getBlockChanges(params);
					if ((changes == null) || (changes.size() == 0)) {
					this.play.sendMessage("��cLogBlock�� ����� ���ڵ带 �������� �ʽ��ϴ�.");
					return;
				}
					BlockChange bc = (BlockChange)changes.get(0);
					if (bc.playerName != this.play.getName()) 
					{
						return;
					}
					
					String[] message = bc.signtext.split("");
					for (int i = 0; i < 4; i++) 
					{
						if (message[i] != s.getLine(i)) 
						{
							return;
						}
					}
				}
				if (main.lbconsumer != null) main.lbconsumer.queueSignBreak(this.play.getName(), s);
				
				String[] lines = (String[])s.getLines().clone();
				String tline = lines[this.line];
				lines[this.line] = this.message;
				s.setLine(this.line, this.message);
				s.update();
				SignChangeEvent e = new SignChangeEvent(this.b, this.play, lines);
				Bukkit.getPluginManager().callEvent(e);
				if (!e.isCancelled()) 
				{
					s.setLine(this.line, this.message);
				}
				
				else 
				{
					s.setLine(this.line, tline);
				}
				s.update();
		    } 
			
			catch (Exception e) 
			{
			}
		}
		
	}
}