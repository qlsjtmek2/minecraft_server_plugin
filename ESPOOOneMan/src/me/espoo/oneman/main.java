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
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] ";
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
        
		getCommand("인첸트").setExecutor(new TimCommand(this));
		getCommand("인첸트목록").setExecutor(new TimCommand(this));
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
        shop1.add("&f강화가 안된 보통");
        shop1.add("&f곡괭이다. 평범하게");
        shop1.add("&f채광을 시도해보자.");
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
            			case 1: loca = "B시 (X: 1339, Y: 39, Z: 1112)"; break;
            			case 2: loca = "B시 (X: 1353, Y: 39, Z: 1250)"; break;
            			case 3: loca = "B시 (X: 1487, Y: 39, Z: 1211)"; break;
            			case 4: loca = "D시 (X: -1492, Y: 39, Z: 2809)"; break;
            			case 5: loca = "D시 (X: -1359, Y: 39, Z: 2775)"; break;
            			case 6: loca = "D시 (X: -1449, Y: 39, Z: 2944)"; break;
            			case 7: loca = "F시 (X: 2869, Y: 11, Z: 2272)"; break;
            			case 8: loca = "F시 (X: 2904, Y: 11, Z: 2419)"; break;
            			case 9: loca = "F시 (X: 2779, Y: 11, Z: 2481)"; break;
            			case 10: loca = "J시 (X: -1093, Y: 10, Z: -989)"; break;
            			case 11: loca = "J시 (X: -1127, Y: 10, Z: -1142)"; break;
            			case 12: loca = "J시 (X: -990, Y: 10, Z: -1118)"; break;
            			case 13: loca = "Z시 (X: -56, Y: 29, Z: 287)"; break;
            			case 14: loca = "Z시 (X: -13, Y: 30, Z: 398)"; break;
            			case 15: loca = "Z시 (X: -176, Y: 30, Z: 164)"; break;
            		}
            		
            		if (bossCount == 7) {
            			bossCount = 0;
            			Bukkit.broadcastMessage("§e[ §c가로우 (상급 용) §e] §6이 §c" + loca + " §6에 스폰되었습니다.");
            			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 가로우 도시맵-" + random);
            		} else {
            			String name = null;
            			String rank = null;
            			
            			int monster = new Random().nextInt(10) + 1;
            			switch (monster) {
            				case 1: name = "거대흑조"; rank = "귀"; break;
            				case 2: name = "17만년 매미 유충"; rank = "귀"; break;
            				case 3: name = "17만년 매미 성충"; rank = "귀"; break;
            				case 4: name = "선풍귀"; rank = "귀"; break;
            				case 5: name = "그리즈냐"; rank = "귀"; break;
            				case 6: name = "눈사람맨"; rank = "귀"; break;
            				case 7: name = "백신맨"; rank = "용"; break;
            				case 8: name = "마루고리"; rank = "용"; break;
            				case 9: name = "침봉도치"; rank = "용"; break;
            				case 10: name = "명계왕 플루돈"; rank = "용"; break;
            			}
            			
            			Bukkit.broadcastMessage("§e[ §c" + name + " (" + rank + ") §e] §6이/가 §c" + loca + " §6에 스폰되었습니다.");
            			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn " + name.replaceAll(" ", "_") + " 도시맵-" + random);
            		}
        		}
        	}
        }, 7200L, 7200L);
        
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
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
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public static void openMacro(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 9, "매크로 체크 §l(E를 눌러 창을 닫아주세요)");
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
		getCommand("지급").setExecutor(command);
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
        final String[] week = { "일", "월", "화", "수", "목", "금", "토" };
        String str = week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1];
        if (str.equalsIgnoreCase("월")) {
        	if (Method.getConfigBoolean("월요일") == false) {
            	Method.setConfigBoolean("월요일", true);
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
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + one + " " + Method.getConfigInt("1등 보상"));
            	} else {
            	  	Bukkit.getServer().dispatchCommand(p, "npc create 없음 --at -813:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}
            	
            	if (two != null) {
                   	Bukkit.getServer().dispatchCommand(p, "npc create " + two + " --at -806:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + two + " " + Method.getConfigInt("2등 보상"));
            	} else {
                   	Bukkit.getServer().dispatchCommand(p, "npc create 없음 --at -806:117:330:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}
            	
            	if (three != null) {
                  	Bukkit.getServer().dispatchCommand(p, "npc create " + three + " --at -806:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + three + " " + Method.getConfigInt("3등 보상"));
            	} else {
                  	Bukkit.getServer().dispatchCommand(p, "npc create 없음 --at -806:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
            	}

            	if (four != null) {
                  	Bukkit.getServer().dispatchCommand(p, "npc create " + four + " --at -813:117:337:world_spawn");
                	Bukkit.getServer().dispatchCommand(p, "npc look");
                	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + four + " " + Method.getConfigInt("4등 보상"));
            	} else {
                  	Bukkit.getServer().dispatchCommand(p, "npc create 없음 --at -813:117:337:world_spawn");
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
                    	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop 월요일 데이터 갱신 리로드");
        			}
        		}, timeToRun2);
        	}
        } else if (str.equalsIgnoreCase("화") || str.equalsIgnoreCase("수") || str.equalsIgnoreCase("목") 
        		|| str.equalsIgnoreCase("금") || str.equalsIgnoreCase("토") || str.equalsIgnoreCase("일")) {
        	if (Method.getConfigBoolean("월요일")) {
        		Method.setConfigBoolean("월요일", false);
        	}
        }
        
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 300);
		timer1.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					p.sendMessage("");
				}
				p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				Message.sendCenteredMessage(p, "§6환영합니다 §e" + p.getName() + "§6님! 오늘도 즐거운 §c마인크래프트 §6하세요~");
				p.sendMessage("");
				p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				String guild = GuildAPI.getJoinGuild(p);
				
				if (guild != null) {
					if (GuildAPI.isWar(guild)) {
						String emeny = GuildAPI.getWarGuild(guild);
						
						if (GuildAPI.isSubmit(emeny)) {
							p.sendMessage(" 　　　　　            　§6현재 우리 길드가 §c" + emeny + " §6길드에게 항복한 상태입니다.");
							p.sendMessage("");
							p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							p.sendMessage("");
						}
						
						else if (GuildAPI.isSubmit(guild)) {
							p.sendMessage(" 　       　　　　            　§6현재 상대 길드가 우리 길드에게 항복한 상태입니다.");
							if (GuildAPI.getJoinMaster(guild).equalsIgnoreCase(p.getName()))
								p.sendMessage(" 　       　　　　         §e[ /길드 -> 전쟁 항복 수락/거절 ] §6으로 항복을 수락 또는 거절하세요.");
							p.sendMessage("");
							p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							p.sendMessage("");
						}
						
						else {
							p.sendMessage(" 　　　　　　§6현재 §c" + guild + " §6길드와 §c" + emeny + " §6길드가 전쟁중인 상태입니다.");
							p.sendMessage("");
							p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							p.sendMessage("");
						}
					}
				}
				return;
			}
		}, timeToRun);
		
		if (!reload) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 심해왕");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 보로스");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 가로우");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 아수라_카부토");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall world");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 심해왕 고스트타운-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 보로스 다크매터-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 가로우 도시맵-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 아수라_카부토 진화의집-BOSS");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 코뿔소_레슬러");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 마셜_고릴라");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 전기메기남");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 충신");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 피닉스남");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 괴인공주_여신안경");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 라플레시돈");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 이블_천연수");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 잇몸");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 추남_대통령");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 홈리스_황제");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 너무_크게_자란_포치");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 황금_정자");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 사이코스");
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
										if (!me.espoo.option.PlayerYml.getInfoBoolean((Player) event.getRightClicked(), "플레이어 라이딩")) {
											event.setCancelled(true);
											player.sendMessage("§c이 플레이어 위에 앉으실 수 없습니다.");
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
							API.sayMessage(player, "피로도가 부족하여 플레이어 위에 앉을 수 없습니다.");
						}
					} else {
						vehicle.eject();
					}
				}
			} else {
				Player vehicle = (Player) event.getRightClicked();
				
				if (player.isOp() || me.espoo.option.PlayerYml.getInfoBoolean(vehicle, "정보 공개 여부")) {
					event.setCancelled(true);
					player.chat("/정보 " + vehicle.getName());
				} else {
					event.setCancelled(true);
					player.sendMessage("§c이 플레이어는 정보를 비공개 해두었습니다.");
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
	    		p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
	    	else
	    		p.sendMessage("§f[§4경고§f] §c당신은 권한이 없으므로 타인에게 오피 지급이 불가능 합니다.");
	    }
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e) 
	{
	    Player diedP = e.getEntity();
	    if (diedP == null) return;

	    if (PlayerYml.getInfoBoolean(diedP, "효과음")) diedP.playSound(diedP.getLocation(), Sound.WITHER_SPAWN, 1.0F, 1.0F);
	    
		PlayerDeathEvent Event0 = (PlayerDeathEvent) e;
		Player p = Event0.getEntity();
		Entity kn = p.getKiller();
		
		if (kn instanceof Player) {
			Player kp = (Player) p.getKiller();
			
	        if (kp.getWorld().getName().equalsIgnoreCase("world_pvp") && p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
	        	if (ExpAPI.getExp(p.getName()) / 5 <= 9) {
	        		int num = ExpAPI.getExp(kp.getName()) + 5;
	        		ExpAPI.addExp(kp, "§6당신은 §c" + p.getName() + "§6님을 죽이고 §c<exp><bouns> §6만큼 경험치를 받았습니다. §c<playerexp> §6EXP", 5);
	        	} else {
	        		int num = ExpAPI.getExp(kp.getName()) + (ExpAPI.getExp(p.getName()) / 10);
	        		ExpAPI.addExp(kp, "§6당신은 §c" + p.getName() + "§6님을 죽이고 §c<exp><bouns> §6만큼 경험치를 받았습니다. §c<playerexp> §6EXP", ExpAPI.getExp(p.getName()) / 10);
	        		ExpAPI.setExp(p, ExpAPI.getExp(p.getName()) - (ExpAPI.getExp(p.getName()) / 20));
	        		num = ExpAPI.getExp(p.getName()) - (ExpAPI.getExp(p.getName()) / 20);
	        		p.sendMessage("§6당신은 §c" + kp.getName() + "§6님에게 죽고 §c" + ExpAPI.getExp(p.getName()) / 20 + "§6만큼 경험치를 뺏겼습니다. §c" + num + " §6EXP");
	        	}
	        }
	        
	        else if (kp.getWorld().getName().equalsIgnoreCase("world") && p.getWorld().getName().equalsIgnoreCase("world")) {
	        	if (!kp.isOp()) {
	        		if (GuildAPI.getJoinGuild(kp) != null && GuildAPI.getJoinGuild(p) != null) {
	        			if (GuildAPI.getWarGuild(GuildAPI.getJoinGuild(kp)).equalsIgnoreCase(GuildAPI.getJoinGuild(p))) {
	        				return;
	        			}
	        		}
	        		
		        	kp.kickPlayer("당신은 도시맵에서 살인을 저질렀으므로 10분 밴 처리 됩니다.");
		        	p.sendMessage("§6당신을 죽인 §c" + kp.getName() + "§6 님은 살인으로 10분동안 §c밴 §6처리되었습니다.");
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
    	if (e.getInventory().getName().equalsIgnoreCase("플레이어 정보")) {
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
		p.sendMessage("§c인첸트는 하실 수 없습니다.");
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
		if (PlayerYml.getInfoBoolean(p, "효과음")) {
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
			p.sendMessage("§f[§4경고§f] §c당신은 권한이 없으므로 게임모드가 불가능 합니다.");
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
		list.add("§7이 농작물은 요리 전용으로,");
		list.add("§7심거나 판매하실 수 없습니다.");
		list.add("§f채집장 §7에서만 얻을 수 있습니다.");
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
							p.sendMessage("§c채집은 철 낫, 다이아몬드 낫 으로만 가능합니다.");
							p.sendMessage("§f- §c다이아몬드 낫, 철 낫 둘 중 하나를 조합해서 채집해주시기 바랍니다.");
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
					p.sendMessage("§6돌 안에서 §e[ §8최하급 소켓석 §e] §6을 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 최하급소켓석 give " + p.getName() + " " + 1);
				}

				else if (i >= 3329 && i < 3340) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §7하급 소켓석 §e] §6을 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 하급소켓석 give " + p.getName() + " " + 1);
				}

				else if (i >= 3341 && i < 3353) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §a§l■  §b1강 §6강화 주문서  §a§l■ §e] §6를 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "주문서지급 1 " + p.getName());
				}

				else if (i >= 3354 && i < 3364) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §a§l■  §b2강 §6강화 주문서  §a§l■§e] §6를 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "주문서지급 2 " + p.getName());
				}

				else if (i >= 3365 && i < 3373) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §a§l■  §b3강 §6강화 주문서  §a§l■ §e] §6를 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "주문서지급 3 " + p.getName());
				}

				else if (i >= 3374 && i < 3379) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §c§l■  §f일반 §6강화 주문서  §c§l■ §e] §6를 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "주문서지급 일반 " + p.getName());
				}

				else if (i >= 3380 && i < 3384) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §7§l광산 §6랜덤 무기 큐브 §e] §6를 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 광산랜덤무기큐브 give " + p.getName() + " " + 1);
				}

				else if (i >= 3389 && i < 3399) {
					e.getBlock().getDrops().clear();
					e.getBlock().setType(Material.AIR);
					p.sendMessage("§6돌 안에서 §e[ §7§l광산 §6금가루 §e] §6를 발견하셨습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 광산금가루 give " + p.getName() + " " + 1);
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
				e.getPlayer().sendMessage("§f[§4경고§f] §c지금은 블럭을 부술 수 없습니다.");
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPortal(PlayerPortalEvent e)
	{
		Player p = e.getPlayer();
		
		if (!portal.contains(p)) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 포탈 " + p.getName());
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
				e.getPlayer().sendMessage("§f[§4경고§f] §c지금은 말할 수 없습니다.");
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
							if (rpgitem.equalsIgnoreCase("불속성") || rpgitem.equalsIgnoreCase("바람속성") || rpgitem.equalsIgnoreCase("치유속성") || 
								rpgitem.equalsIgnoreCase("독속성") || rpgitem.equalsIgnoreCase("부패속성") || rpgitem.equalsIgnoreCase("어둠속성") || 
								rpgitem.equalsIgnoreCase("얼음속성")) {
					            int t = 0;
					            ItemStack[] contents;
					            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
					                ItemStack it = contents[j];
					                if (it == null) {
					                    ++t;
					                }
					            }
					            
					            if (t < i) {
					            	p.sendMessage("§c인벤토리에 공간이 부족하여 아이템을 구매할 수 없습니다.");
					    			shop.remove(p.getName());
					    			buyme.remove(p.getName());
					    			buyto.remove(p.getName());
									e.setCancelled(true);
					            	return;
					            } else {
									economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + rpgitem + " give " + p.getName() + " " + i);
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
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
				            	p.sendMessage("§c인벤토리에 공간이 부족하여 아이템을 구매할 수 없습니다.");
				    			shop.remove(p.getName());
				    			buyme.remove(p.getName());
				    			buyto.remove(p.getName());
								e.setCancelled(true);
				            	return;
				            } else {
								economy.withdrawPlayer(p.getName(), y);
								p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + rpgitem + " give " + p.getName() + " " + i);
								if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
								e.setCancelled(true);
								shop.remove(p.getName());
								buyme.remove(p.getName());
								buyto.remove(p.getName());
								return;
				            }
						} else {
							p.sendMessage("§c돈이 부족하여 불건을 구매할 수 없습니다.");
							shop.remove(p.getName());
							buyme.remove(p.getName());
							buyto.remove(p.getName());
							e.setCancelled(true);
							return;
						}
					} else {
						p.sendMessage("§c물건 양은 채팅에 숫자로만 입력해 주시기 바랍니다.");
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
						
						if (itemname.equalsIgnoreCase("보통 곡괭이") || itemname.equalsIgnoreCase("철 괭이") || itemname.equalsIgnoreCase("다이아몬드 괭이")) {
							int t = 0;
				            ItemStack[] contents;
				            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
				                ItemStack it = contents[j];
				                if (it == null) {
				                    ++t;
				                }
				            }
				            
				            if (t < i) {
				            	p.sendMessage("§c인벤토리에 공간이 부족하여 아이템을 구매할 수 없습니다.");
				    			shop.remove(p.getName());
				    			buyme.remove(p.getName());
				    			buyto.remove(p.getName());
								e.setCancelled(true);
				            	return;
				            } else {
				            	if (itemname.equalsIgnoreCase("보통 곡괭이")) {
									economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									ItemStack item = new MaterialData(257, (byte) 0).toItemStack(1);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("§e보통 곡괭이");
									List<String> lore = new ArrayList<String>();
									lore.add("§7§l==============");
									lore.add("§f강화가 안된 보통");
									lore.add("§f곡괭이다. 평범하게");
									lore.add("§f채광을 시도해보자");
									lore.add("§7§l==============");
									item_Meta.setLore(lore);
									item_Meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
									item.setItemMeta(item_Meta);
									for (int z = 0; z < i; z++)
										p.getInventory().addItem(new ItemStack[] { item });
									
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("철 괭이")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_HOE) });
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + "257 " + i);
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("다이아몬드 괭이")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_HOE) });
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            }
						}
						
						else if (itemname.equalsIgnoreCase("일반 강화 주문서") || itemname.equalsIgnoreCase("특별 강화 주문서") || itemname.equalsIgnoreCase("프리미엄 강화 주문서")) {
							int t = 0;
				            ItemStack[] contents;
				            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
				                ItemStack it = contents[j];
				                if (it == null) {
				                    ++t;
				                }
				            }

				            if ((double) t < ((double) i / 64)) {
				            	p.sendMessage("§c인벤토리에 공간이 부족하여 아이템을 구매할 수 없습니다.");
				    			shop.remove(p.getName());
				    			buyme.remove(p.getName());
				    			buyto.remove(p.getName());
								e.setCancelled(true);
				            	return;
				            } else {
				            	if (itemname.equalsIgnoreCase("일반 강화 주문서")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									ItemStack item = new MaterialData(339, (byte) 0).toItemStack(i);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("§c§l■  §f일반 §6강화 주문서  §c§l■");
								    List<String> list1 = new ArrayList<String>();
								    list1.add("§7§l=================");
								    list1.add("§f평범한 강화 주문서이다.");
								    list1.add("§f이 아이템을 들고 강화");
								    list1.add("§f하고 싶은 아이템에 올려");
								    list1.add("§f두면 그 아이템이 강화된다.");
								    list1.add("§7§l=================");
								    item_Meta.setLore(list1);
								    item.setItemMeta(item_Meta);
									p.getInventory().addItem(new ItemStack[] { item });
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("특별 강화 주문서")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									ItemStack item = new MaterialData(339, (byte) 0).toItemStack(i);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("§c§l■  §b특별 §6강화 주문서  §c§l■");
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
								    item_Meta.setLore(list2);
								    item.setItemMeta(item_Meta);
									p.getInventory().addItem(new ItemStack[] { item });
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            	
				            	else if (itemname.equalsIgnoreCase("프리미엄 강화 주문서")) {
				            		economy.withdrawPlayer(p.getName(), y);
									p.sendMessage("§c" + itemname + "§6 아이템을 §c" + y + " §6원 지불하고 구매하셨습니다.");
									ItemStack item = new MaterialData(339, (byte) 0).toItemStack(i);
									ItemMeta item_Meta = item.getItemMeta();
									item_Meta.setDisplayName("§4§l■  §d프리미엄 §6강화 주문서  §4§l■");
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
								    item_Meta.setLore(list3);
								    item.setItemMeta(item_Meta);
									p.getInventory().addItem(new ItemStack[] { item });
									if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
									e.setCancelled(true);
									shop.remove(p.getName());
									buyme.remove(p.getName());
									buyto.remove(p.getName());
									return;
				            	}
				            }
						}
					} else {
						p.sendMessage("§c물건 양은 채팅에 숫자로만 입력해 주시기 바랍니다.");
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
							p.sendMessage("§c이 아이템은 요리 전용 작물로, 설치가 불가능 합니다.");
							p.sendMessage("§f- §c길드에 가입하셨다면 §f/길드 -> 길드 상점 §c클릭으로 작물을 구매해주세요.");
						}
					}
				}
				
				else if (item.getTypeId() == 338 || item.getTypeId() == 111 || item.getTypeId() == 37 || item.getTypeId() == 38) {
					if (item.hasItemMeta()) {
						e.setCancelled(true);
						p.updateInventory();
						p.sendMessage("");
						p.sendMessage("§c이 아이템은 요리 전용 작물로, 설치가 불가능 합니다.");
						p.sendMessage("§f- §c길드에 가입하셨다면 §f/길드 -> 길드 상점 §c클릭으로 작물을 구매해주세요.");
					}
				}
			}
		}
		
		if ((act == Action.RIGHT_CLICK_AIR) || (act == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CAKE_BLOCK) {
				e.setCancelled(true);
	        	p.sendMessage("§c케이크는 먹으실 수 없습니다.");
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
                        	p.sendMessage("§c포션을 들고 버튼을 클릭하시면 작동하지 않습니다.");
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
				p.sendMessage("§c평범한 음식은 드실 수 없습니다.");
				p.sendMessage("§f- §c오로지 §f요리 §c로만 제작된 음식만 먹으실 수 있습니다. §f/도움말 -> 요리 도움말");
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
    				p.sendMessage("§c평범한 음식은 드실 수 없습니다.");
    				p.sendMessage("§f- §c오로지 §f요리 §c로만 제작된 음식만 먹으실 수 있습니다. §f/도움말 -> 요리 도움말");
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
    				p.sendMessage("§c평범한 음식은 드실 수 없습니다.");
    				p.sendMessage("§f- §c오로지 §f요리 §c로만 제작된 음식만 먹으실 수 있습니다. §f/도움말 -> 요리 도움말");
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
				p.chat("/도구");
				e.setCancelled(true);
			}
			
			else if (b.getType() == Material.FURNACE || b.getType() == Material.BURNING_FURNACE) {
				p.chat("/요리");
				e.setCancelled(true);
			}
			
			else if (b.getType() == Material.ENCHANTMENT_TABLE) {
				p.chat("/소켓");
				e.setCancelled(true);
			}
		}
		
	    if ((act == Action.RIGHT_CLICK_AIR) || (act == Action.RIGHT_CLICK_BLOCK))
	    {
	        if ((e.getItem() != null) && 
	      	    (e.getItem().getItemMeta().getDisplayName() != null) && 
	        	(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(" 수표")) && 
	        	(p.getItemInHand().getTypeId() == 339)) {
	        	
	    		ItemStack pi = p.getItemInHand();
	    		ItemMeta pitem = pi.getItemMeta();
	    		int won = Integer.parseInt((String)pitem.getLore().get(1));
	    		if (pitem.getLore() != null)
	    		{
	    			main.economy.depositPlayer(pname, won);
	    			p.sendMessage("§c수표§6를 사용하여 §c" + won + " §6원을 획득하였습니다.");
	    			int amount = pi.getAmount();
	    			if (amount == 1)
	    				p.getInventory().removeItem(new ItemStack[] { pi });
	    			else
	    				pi.setAmount(amount - 1);
	    		}
	    	}
	        
	        else if ((e.getItem() != null) && 
		      	    (e.getItem().getItemMeta().getDisplayName() != null) && 
		        	(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase(" 레벨 수표")) && 
		        	(p.getItemInHand().getTypeId() == 339)) {
		        	
	        	ItemStack pi = p.getItemInHand();
	        	ItemMeta pitem = pi.getItemMeta();
	        	int lv = Integer.parseInt((String)pitem.getLore().get(1));
	        	if (pitem.getLore() != null)
	        	{
	        		p.setLevel(p.getLevel() + lv);
	        		p.sendMessage("§c레벨 수표§6를 사용하여 레벨를 §c" + lv + " §6만큼 획득하였습니다.");
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
						  if (PlayerYml.getInfoBoolean(e.getPlayer(), "효과음")) e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIZZ, 1.0F, 1.0F);
						  main.FirePlayers.remove(e.getPlayer().getName());  
					  } else {
						  e.setCancelled(true);
						  API.sayMessage(e.getPlayer(), "피로도가 부족하여 불을 끄실 수 없습니다.");
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
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "쓰레기통");
		p.openInventory(inv);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (commandLabel.equalsIgnoreCase("bn")) {
			if (sender.isOp()) {
				if (args.length == 0) {
					if (explode) {
						explode = false;
						sender.sendMessage(ChatColor.RED + "이제부터 표지판, 상자 등 폭발 보호를 받지 않습니다.");
					} else {
						explode = true;
						sender.sendMessage(ChatColor.GREEN + "이제부터 표지판, 상자 등 폭발 보호를 받습니다.");
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
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
			}
		}
		
		if(commandLabel.equalsIgnoreCase("ec"))
		{
			if(sender.isOp() == true) 
			{
				if(args.length == 0) {
					sender.sendMessage("§a■§7■                                                 §a■§7■");
					sender.sendMessage("§7■§c■ §e----- §6ESPOO core §e-- §6도움말 §e----- §7■§c■");
					sender.sendMessage("");
					sender.sendMessage("§6/ec §f- ESPOO Farm 도움말을 봅니다.");
					sender.sendMessage("§6/ec reload §f- ESPOO Farm 콘피그를 리로딩합니다.");
					sender.sendMessage("§6/채팅청소, cc §f- 채팅청소를 합니다.");
					sender.sendMessage("§6/정지 §f- 모든플레이어를 정지 시킵니다.");
					sender.sendMessage("§6/블럭 §f- 모든플레이어가 블럭을 캘 수 없게 됩니다.");
					sender.sendMessage("§6/무적 §f- 모든플레이어가 무적이 됩니다.");
					sender.sendMessage("§6/채팅 §f- 모든플레이어가 채팅을 할 수 없게 됩니다.");
					sender.sendMessage("§6/만렙 §f- 만렙을 설정 또는 해제합니다.");
					sender.sendMessage("§6/만렙 <0 이상의 숫자> §f- 만렙을 <0 이상의 숫자>로 설정합니다.");
					sender.sendMessage("§6/쓰레기통 §f- 쓰레기통을 오픈합니다.");
					sender.sendMessage("§6/표지판 <줄> <내용> §f- 표지판의 내용을 변경합니다.");
					sender.sendMessage("§6/지급 <수량> §f손에 든 아이템을 모두에게 지급합니다.");
					sender.sendMessage("§6/수표 <돈> §f- 돈 만큼 수표를 발행합니다.");
					sender.sendMessage("§6/인기도 <보기/상승/감소> <닉네임> §f- 인기도를 보거나 상승시키거나 감소시킵니다.");
					sender.sendMessage("§6/인첸트 §f- 인첸트를 합니다.");
					sender.sendMessage("§6/인첸트목록 도움말 §f- 인첸트 목록을 봅니다.");
					sender.sendMessage("§6/아이템금지 §f- 도움말을 봅니다.");
					sender.sendMessage("§6/아이템금지 추가 <경고메세지> §f- 들고있는 아이템을 사용하지 못하게 합니다.");
					sender.sendMessage("§6/아이템금지 추가 모두 <경고메세지> §f- 들고있는 아이템을 데이터 코드도 모두 사용하지 못하게 합니다.");
					sender.sendMessage("§6/아이템금지 제거 §f- 들고있는 아이템 금지 여부를 제거합니다.");
					sender.sendMessage("§6/아이템금지 확인 §f- 들고있는 아이템 금지여부를 확인합니다.");
					sender.sendMessage("§6/장비추첨 <모자|튜닉|바지|신발> <히어로이름> §f- 랜덤으로 장비를 추첨합니다.");
					sender.sendMessage("");
					sender.sendMessage("§e----------------------------------------------------");
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
				    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "알림" + ChatColor.WHITE + "]" + ChatColor.GREEN + "콘피그 리로드를 완료했습니다.");
				}
			}
		}
		
		if(commandLabel.equalsIgnoreCase("cc") || commandLabel.equalsIgnoreCase("채팅청소")) {
			if(sender.isOp() == true) {
				for (int i = 0; i < 50; i++) {
					Bukkit.broadcastMessage("");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
			}
		}
		
		if (commandLabel.equals("정지"))
		{
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.stopE)
				{
					Bukkit.broadcastMessage("§f[§4알림§f] §6움직일 수 없게 설정되었습니다.");
					main.stopE = true;
					getConfig().addDefault("정지", Boolean.valueOf(true));
					return true;
				}

				Bukkit.broadcastMessage("§f[§4알림§f] §6움직일 수 있게 설정되었습니다.");
				main.stopE = false;
				getConfig().addDefault("정지", Boolean.valueOf(false));
				return true;
			}

			Bukkit.broadcastMessage("§f[§4알림§f] §6움직일 수 있게 설정되었습니다.");
			return true;
		}
		
		if (commandLabel.equals("던전아이템give"))
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
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c5초 §6후 종료됩니다.");
						}
						
						else if (inte == 4) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c4초 §6후 종료됩니다.");
						}

						else if (inte == 3) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c3초 §6후 종료됩니다.");
						}

						else if (inte == 2) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c2초 §6후 종료됩니다.");
						}

						else if (inte == 1) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c1초 §6후 종료됩니다.");
						}

						else if (inte == 0) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
						}
						
						--inte;
					}
				}, 20L, 20L);
				
				return false;
			}

			sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
			return false;
		}
		
		if (commandLabel.equals("블럭"))
		{
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.blockE)
				{
					main.blockE = true;
					Bukkit.broadcastMessage("§f[§4알림§f] §6블럭을 부술수 없게 설정되었습니다.");
					getConfig().addDefault("블럭", Boolean.valueOf(true));
					return true;
				}
				
				main.blockE = false;
				Bukkit.broadcastMessage("§f[§4알림§f] §6블럭을 부술수 있게 설정되었습니다.");
				getConfig().addDefault("블럭", Boolean.valueOf(false));
				return true;
			}

			sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
			return true;
		}
		
		if (commandLabel.equals("물건구매"))
		{
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("보통_곡괭이")) {
					p.closeInventory();
					p.sendMessage("§6몇 개의 물건을 §c구매§6하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "보통_곡괭이");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("철_괭이")) {
					p.closeInventory();
					p.sendMessage("§6몇 개의 물건을 §c구매§6하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "철_괭이");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("다이아몬드_괭이")) {
					p.closeInventory();
					p.sendMessage("§6몇 개의 물건을 §c구매§6하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "다이아몬드_괭이");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("일반_강화_주문서")) {
					p.closeInventory();
					p.sendMessage("§6몇 개의 물건을 §c구매§6하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "일반_강화_주문서");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("특별_강화_주문서")) {
					p.closeInventory();
					p.sendMessage("§6몇 개의 물건을 §c구매§6하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "특별_강화_주문서");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("프리미엄_강화_주문서")) {
					p.closeInventory();
					p.sendMessage("§6몇 개의 물건을 §c구매§6하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					shop.put(p.getName(), Integer.parseInt(args[1]));
					buyme.put(p.getName(), "프리미엄_강화_주문서");
					return false;
				}
			}
			
			else if (args.length == 3) {
				p.closeInventory();
				String str = args[2].replaceAll("_", " ");
				p.sendMessage("§6몇 개의 §c" + str + " §6을/를 구매하시겠습니까?");
				p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
				shop.put(p.getName(), Integer.parseInt(args[1]));
				buyme.put(p.getName(), args[0]);
				buyto.put(p.getName(), str);
				return false;
			}
		}
		
		
		
		if (commandLabel.equals("랜덤장비큐브"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
			if (!(args.length == 1)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (args[0].equalsIgnoreCase("모자") || args[0].equalsIgnoreCase("투구")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c구동기사 투구 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 구동기사투구 give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c금속 배트 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 금속배트모자 give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c돈신 모자§6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 돈신모자 give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c동제 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 동제모자 give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c메탈나이트 투구 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 메탈나이트투구 give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c번견맨 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 번견맨모자 give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c섬광의 플래시 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 섬광의플래시모자 give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c실버팽 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 실버팽모자 give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c아마이마스크 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아마이마스크모자 give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c아토믹 사무라이 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아토믹모자 give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c음속의 소닉 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 음속의소닉모자 give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c제노스 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 제노스모자 give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c좀비맨 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 좀비맨모자 give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c초합금 검은빛 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초합금검은빛모자 give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c킹 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 킹모자 give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c타츠마키 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 타츠마키모자 give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c탱크톱 마스터 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 탱크톱마스터모자 give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c포동포동 프리즈너 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 프리즈너모자 give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("§6랜덤 모자 큐브를 오픈하여 §c후부키 모자 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 후부키모자 give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("튜닉") || args[0].equalsIgnoreCase("갑옷")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c구동기사 갑옷 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 구동기사갑옷 give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c금속 배트 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 금속배트튜닉 give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c돈신 튜닉§6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 돈신튜닉 give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c동제 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 동제튜닉 give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c메탈나이트 갑옷 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 메탈나이트갑옷 give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c번견맨 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 번견맨튜닉 give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c섬광의 플래시 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 섬광의플래시튜닉 give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c실버팽 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 실버팽튜닉 give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c아마이마스크 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아마이마스크튜닉 give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c아토믹 사무라이 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아토믹튜닉 give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c음속의 소닉 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 음속의소닉튜닉 give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c제노스 갑옷 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 제노스갑옷 give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c좀비맨 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 좀비맨튜닉 give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c초합금 검은빛 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초합금검은빛튜닉 give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c킹 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 킹튜닉 give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c타츠마키 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 타츠마키튜닉 give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c탱크톱 마스터 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 탱크톱마스터튜닉 give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c포동포동 프리즈너 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 프리즈너튜닉 give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("§6랜덤 튜닉 큐브를 오픈하여 §c후부키 튜닉 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 후부키튜닉 give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("바지") || args[0].equalsIgnoreCase("레깅스")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c구동기사 레깅스 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 구동기사레깅스 give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c금속 배트 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 금속배트바지 give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c돈신 바지§6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 돈신바지 give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c동제 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 동제바지 give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c메탈나이트 레깅스 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 메탈나이트레깅스 give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c번견맨 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 번견맨바지 give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c섬광의 플래시 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 섬광의플래시바지 give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c실버팽 레깅스 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 실버팽레깅스 give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c아마이마스크 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아마이마스크바지 give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c아토믹 사무라이 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아토믹바지 give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c음속의 소닉 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 음속의소닉바지 give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c제노스 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 제노스바지 give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c좀비맨 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 좀비맨바지 give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c초합금 검은빛 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초합금검은빛바지 give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c킹 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 킹바지 give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c타츠마키 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 타츠마키바지 give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c탱크톱 마스터 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 탱크톱마스터바지 give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c포동포동 프리즈너 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 프리즈너바지 give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("§6랜덤 바지 큐브를 오픈하여 §c후부키 바지 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 후부키바지 give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("신발") || args[0].equalsIgnoreCase("부츠")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
				Date timeToRun = new Date(System.currentTimeMillis() + 3800);
				main.timer.schedule(new TimerTask() {
					public void run() {
						int RandomAmount = new Random().nextInt(19) + 1;
						
						switch (RandomAmount) {
						case 1:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c구동기사 부츠 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 구동기사부츠 give " + p.getName() + " 1");
							break;

						case 2:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c금속 배트 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 금속배트신발 give " + p.getName() + " 1");
							break;

						case 3:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c돈신 신발§6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 돈신신발 give " + p.getName() + " 1");
							break;

						case 4:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c동제 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 동제신발 give " + p.getName() + " 1");
							break;

						case 5:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c메탈나이트 부츠 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 메탈나이트부츠 give " + p.getName() + " 1");
							break;

						case 6:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c번견맨 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 번견맨신발 give " + p.getName() + " 1");
							break;

						case 7:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c섬광의 플래시 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 섬광의플래시신발 give " + p.getName() + " 1");
							break;

						case 8:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c실버팽 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 실버팽신발 give " + p.getName() + " 1");
							break;

						case 9:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c아마이마스크 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아마이마스크신발 give " + p.getName() + " 1");
							break;

						case 10:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c아토믹 사무라이 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아토믹신발 give " + p.getName() + " 1");
							break;

						case 11:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c음속의 소닉 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 음속의소닉신발 give " + p.getName() + " 1");
							break;

						case 12:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c제노스 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 제노스신발 give " + p.getName() + " 1");
							break;

						case 13:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c좀비맨 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 좀비맨신발 give " + p.getName() + " 1");
							break;

						case 14:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c초합금 검은빛 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초합금검은빛신발 give " + p.getName() + " 1");
							break;

						case 15:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c킹 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 킹신발 give " + p.getName() + " 1");
							break;

						case 16:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c타츠마키 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 타츠마키신발 give " + p.getName() + " 1");
							break;

						case 17:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c탱크톱 마스터 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 탱크톱마스터신발 give " + p.getName() + " 1");
							break;

						case 18:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c포동포동 프리즈너 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 프리즈너신발 give " + p.getName() + " 1");
							break;

						case 19:
							p.sendMessage("§6랜덤 신발 큐브를 오픈하여 §c후부키 신발 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 후부키신발 give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
			
			else if (args[0].equalsIgnoreCase("사이타마일반")) {
				int num = 0;
				ItemStack[] contents;
				for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
					ItemStack it = contents[j];
					if (it == null) {
						num++;
					}
				}
				
				if (num < 4) {
					p.sendMessage("§c인벤토리 4칸을 비워주시고 사용해 주시기 바랍니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마일반큐브 give " + p.getName() + " 1");
					return false;
				} else {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마모자 give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마튜닉 give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마바지 give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마신발 give " + p.getName() + " 1");
					castLvup(p);
					p.sendMessage("§6사이타마 일반 장비 세트를 §c획득§6하셨습니다.");
					return false;
				}
			}
			
			else if (args[0].equalsIgnoreCase("사이타마유니크")) {
				int num = 0;
				ItemStack[] contents;
				for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
					ItemStack it = contents[j];
					if (it == null) {
						num++;
					}
				}
				
				if (num < 4) {
					p.sendMessage("§c인벤토리 4칸을 비워주시고 사용해 주시기 바랍니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마유니크큐브 give " + p.getName() + " 1");
					return false;
				} else {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마모자 give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마튜닉 give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마바지 give " + p.getName() + " 1");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마신발 give " + p.getName() + " 1");
					castLvup(p);
					p.sendMessage("§6사이타마 유니크 장비 세트를 §c획득§6하셨습니다.");
					return false;
				}
			}
		}
		
		if (commandLabel.equals("광산랜덤무기큐브"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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
				p.sendMessage("§6광산 랜덤 무기 큐브를 오픈하여 §c1성 무기 큐브 §6를 획득하셨습니다.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1성무기큐브 give " + p.getName() + " 1");
				castLvup(p);
				break;
			case 2:
				p.sendMessage("§6광산 랜덤 무기 큐브를 오픈하여 §c3성 무기 큐브 §6를 획득하셨습니다.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3성무기큐브 give " + p.getName() + " 1");
				castLvup(p);
				break;
			case 3:
				p.sendMessage("§6광산 랜덤 무기 큐브를 오픈하여 §c4성 무기 큐브 §6를 획득하셨습니다.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4성무기큐브 give " + p.getName() + " 1");
				castLvup(p);
				break;
			} return false;
		}
		
		if (commandLabel.equals("광산금가루"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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
			p.sendMessage("§6광산 금가루를 감정하여 §c" + won + " §6원을 획득하셨습니다.");
			return false;
		}
		
		if (commandLabel.equals("랜덤무기큐브"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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
						p.sendMessage("§61성 무기 큐브를 오픈하여 §c킹의 게임기 §6를 획득하셨습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 킹무기 give " + p.getName() + " 1");
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
						p.sendMessage("§63성 무기 큐브를 오픈하여 §c좀비맨 도끼 §6를 획득하셨습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 좀비맨무기 give " + p.getName() + " 1");
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
							p.sendMessage("§64성 무기 큐브를 오픈하여 §c후부키 염동력 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 후부키무기 give " + p.getName() + " 1");
							break;
							
						case 2:
							p.sendMessage("§64성 무기 큐브를 오픈하여 §c레이저 빔 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 구동기사무기 give " + p.getName() + " 1");
							break;
							
						case 3:
							p.sendMessage("§64성 무기 큐브를 오픈하여 §c번견맨의 손톱 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 번견맨무기 give " + p.getName() + " 1");
							break;
							
						case 4:
							p.sendMessage("§64성 무기 큐브를 오픈하여 §c검은빛의 역기 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초합금검은빛무기 give " + p.getName() + " 1");
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
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c금속 배트 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 금속배트무기 give " + p.getName() + " 1");
							break;
							
						case 2:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c숟가락 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 돈신무기 give " + p.getName() + " 1");
							break;
							
						case 3:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c동제 머신건 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 동제무기 give " + p.getName() + " 1");
							break;
							
						case 4:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c꽃미남 가면 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 꽃미남가면무기 give " + p.getName() + " 1");
							break;
							
						case 5:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c아토믹 사무라이 검 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 아토믹무기 give " + p.getName() + " 1");
							break;
							
						case 6:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c음속의 소닉 칼 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 음속의소닉무기 give " + p.getName() + " 1");
							break;
							
						case 7:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c엔젤 스타일 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 프리즈너무기 give " + p.getName() + " 1");
							break;
							
						case 8:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c제노스의 파츠 §6를 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 제노스무기 give " + p.getName() + " 1");
							break;
							
						case 9:
							p.sendMessage("§65성 무기 큐브를 오픈하여 §c탱크톱 마스터 근육 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 탱크톱마스터무기 give " + p.getName() + " 1");
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
							p.sendMessage("§66성 무기 큐브를 오픈하여 §c메탈나이트 미사일 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 메탈나이트무기 give " + p.getName() + " 1");
							break;
							
						case 2:
							p.sendMessage("§66성 무기 큐브를 오픈하여 §c섬광의 플래시 검 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 섬광의플래시무기 give " + p.getName() + " 1");
							break;
							
						case 3:
							p.sendMessage("§66성 무기 큐브를 오픈하여 §c유수암쇄권 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 실버팽무기 give " + p.getName() + " 1");
							break;
							
						case 4:
							p.sendMessage("§66성 무기 큐브를 오픈하여 §c타츠마키 염동력 §6을 획득하셨습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 타츠마키무기 give " + p.getName() + " 1");
							break;
						} castLvup(p); p.closeInventory();
					}
				}, timeToRun);
				return false;
			}
		}
		
		if (commandLabel.equals("장비추첨"))
		{
			if (!sender.hasPermission("*"))
			{
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
			if (!(args.length == 2)) {
				return false;
			}
			
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
				
			if (args[0].equalsIgnoreCase("모자") || args[0].equalsIgnoreCase("투구")) {
				if (args[1].equalsIgnoreCase("타츠마키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c타츠마키 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1타츠마키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2타츠마키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3타츠마키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4타츠마키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("실버팽")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c실버팽 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1실버팽모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2실버팽모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3실버팽모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4실버팽모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아토믹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아토믹 사무라이 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아토믹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아토믹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아토믹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아토믹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("동제")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c동제 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1동제모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c동제 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2동제모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c동제 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3동제모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c동제 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4동제모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("메탈나이트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c메탈나이트 투구 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1메탈나이트투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 투구 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2메탈나이트투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 투구 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3메탈나이트투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 투구 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4메탈나이트투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("킹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c킹 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1킹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c킹 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2킹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c킹 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3킹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c킹 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4킹모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("좀비맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c좀비맨 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1좀비맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2좀비맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3좀비맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4좀비맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("구동기사")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c구동기사 투구 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1구동기사투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 투구 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2구동기사투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 투구 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3구동기사투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 투구 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4구동기사투구 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("돈신")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c돈신 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1돈신모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2돈신모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3돈신모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4돈신모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("초합금검은빛")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c초합금 검은빛 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1초합금검은빛모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2초합금검은빛모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3초합금검은빛모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4초합금검은빛모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("번견맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c번견맨 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1번견맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2번견맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3번견맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4번견맨모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("섬광의플래시")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c섬광의 플래시 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1섬광의플래시모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2섬광의플래시모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3섬광의플래시모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4섬광의플래시모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("탱크톱마스터")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c탱크톱 마스터 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1탱크톱마스터모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2탱크톱마스터모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3탱크톱마스터모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4탱크톱마스터모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("제노스")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c제노스 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1제노스모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2제노스모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3제노스모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4제노스모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("금속배트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c금속배트 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1금속배트모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2금속배트모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3금속배트모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4금속배트모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("프리즈너")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c포동포동 프리즈너 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1프리즈너모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2프리즈너모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3프리즈너모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4프리즈너모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아마이마스크")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아마이마스크 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아마이마스크모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아마이마스크모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아마이마스크모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아마이마스크모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("후부키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c후부키 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1후부키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2후부키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3후부키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4후부키모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("음속의소닉")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c음속의 소닉 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1음속의소닉모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2음속의소닉모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3음속의소닉모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4음속의소닉모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("사이타마")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c사이타마 모자 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 모자 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2사이타마모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 모자 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3사이타마모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 모자 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마모자 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("튜닉") || args[0].equalsIgnoreCase("갑옷")) {
				if (args[1].equalsIgnoreCase("타츠마키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c타츠마키 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1타츠마키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2타츠마키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3타츠마키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4타츠마키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("실버팽")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c실버팽 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1실버팽튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2실버팽튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3실버팽튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4실버팽튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아토믹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아토믹 사무라이 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아토믹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아토믹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아토믹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아토믹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("동제")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c동제 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1동제튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c동제 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2동제튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c동제 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3동제튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c동제 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4동제튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("메탈나이트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c메탈나이트 갑옷 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1메탈나이트갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 갑옷 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2메탈나이트갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 갑옷 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3메탈나이트갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 갑옷 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4메탈나이트갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("킹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c킹 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1킹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c킹 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2킹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c킹 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3킹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c킹 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4킹튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("좀비맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c좀비맨 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1좀비맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2좀비맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3좀비맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4좀비맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("구동기사")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c구동기사 갑옷 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1구동기사갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 갑옷 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2구동기사갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 갑옷 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3구동기사갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 갑옷 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4구동기사갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("돈신")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c돈신 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1돈신튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2돈신튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3돈신튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4돈신튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("초합금검은빛")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c초합금 검은빛 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1초합금검은빛튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2초합금검은빛튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3초합금검은빛튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4초합금검은빛튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("번견맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c번견맨 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1번견맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2번견맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3번견맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4번견맨튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("섬광의플래시")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c섬광의 플래시 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1섬광의플래시튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2섬광의플래시튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3섬광의플래시튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4섬광의플래시튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("탱크톱마스터")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c탱크톱 마스터 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1탱크톱마스터튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2탱크톱마스터튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3탱크톱마스터튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4탱크톱마스터튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("제노스")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c제노스 갑옷 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1제노스갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 갑옷 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2제노스갑옷 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 갑옷 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3제노스튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 갑옷 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4제노스튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("금속배트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c금속배트 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1금속배트튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2금속배트튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3금속배트튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4금속배트튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("프리즈너")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c포동포동 프리즈너 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1프리즈너튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2프리즈너튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3프리즈너튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4프리즈너튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아마이마스크")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아마이마스크 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아마이마스크튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아마이마스크튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아마이마스크튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아마이마스크튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("후부키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c후부키 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1후부키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2후부키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3후부키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4후부키튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("음속의소닉")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c음속의 소닉 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1음속의소닉튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2음속의소닉튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3음속의소닉튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4음속의소닉튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("사이타마")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c사이타마 튜닉 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 튜닉 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2사이타마튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 튜닉 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3사이타마튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 튜닉 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마튜닉 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("바지") || args[0].equalsIgnoreCase("레깅스")) {
				if (args[1].equalsIgnoreCase("타츠마키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c타츠마키 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1타츠마키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2타츠마키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3타츠마키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4타츠마키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("실버팽")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c실버팽 레깅스 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1실버팽레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 레깅스 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2실버팽레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 레깅스 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3실버팽레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 레깅스 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4실버팽레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아토믹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아토믹 사무라이 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아토믹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아토믹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아토믹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아토믹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("동제")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c동제 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1동제바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c동제 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2동제바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c동제 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3동제바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c동제 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4동제바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("메탈나이트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c메탈나이트 레깅스 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1메탈나이트레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 레깅스 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2메탈나이트레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 레깅스 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3메탈나이트레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 레깅스 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4메탈나이트레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("킹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c킹 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1킹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c킹 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2킹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c킹 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3킹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c킹 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4킹바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("좀비맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c좀비맨 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1좀비맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2좀비맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3좀비맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4좀비맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("구동기사")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c구동기사 레깅스 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1구동기사레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 레깅스 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2구동기사레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 레깅스 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3구동기사레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 레깅스 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4구동기사레깅스 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("돈신")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c돈신 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1돈신바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2돈신바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3돈신바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4돈신바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("초합금검은빛")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c초합금 검은빛 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1초합금검은빛바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2초합금검은빛바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3초합금검은빛바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4초합금검은빛바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("번견맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c번견맨 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1번견맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2번견맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3번견맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4번견맨바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("섬광의플래시")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c섬광의 플래시 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1섬광의플래시바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2섬광의플래시바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3섬광의플래시바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4섬광의플래시바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("탱크톱마스터")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c탱크톱 마스터 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1탱크톱마스터바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2탱크톱마스터바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3탱크톱마스터바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4탱크톱마스터바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("제노스")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c제노스 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1제노스바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2제노스바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3제노스바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4제노스바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("금속배트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c금속배트 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1금속배트바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2금속배트바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3금속배트바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4금속배트바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("프리즈너")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c포동포동 프리즈너 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1프리즈너바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2프리즈너바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3프리즈너바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4프리즈너바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아마이마스크")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아마이마스크 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아마이마스크바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아마이마스크바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아마이마스크바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아마이마스크바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("후부키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c후부키 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1후부키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2후부키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3후부키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4후부키바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("음속의소닉")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c음속의 소닉 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1음속의소닉바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2음속의소닉바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3음속의소닉바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4음속의소닉바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("사이타마")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c사이타마 바지 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 바지 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2사이타마바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 바지 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3사이타마바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 바지 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마바지 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("신발") || args[0].equalsIgnoreCase("부츠")) {
				if (args[1].equalsIgnoreCase("타츠마키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c타츠마키 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1타츠마키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2타츠마키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3타츠마키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
							
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c타츠마키 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4타츠마키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("실버팽")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c실버팽 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1실버팽신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2실버팽신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3실버팽신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c실버팽 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4실버팽신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아토믹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아토믹 사무라이 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아토믹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아토믹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아토믹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아토믹 사무라이 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아토믹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("동제")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c동제 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1동제신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c동제 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2동제신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c동제 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3동제신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c동제 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4동제신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("메탈나이트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c메탈나이트 부츠 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1메탈나이트부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 부츠 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2메탈나이트부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 부츠 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3메탈나이트부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c메탈나이트 부츠 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4메탈나이트부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("킹")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c킹 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1킹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c킹 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2킹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c킹 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3킹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c킹 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4킹신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("좀비맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c좀비맨 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1좀비맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2좀비맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3좀비맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c좀비맨 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4좀비맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("구동기사")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c구동기사 부츠 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1구동기사부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 부츠 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2구동기사부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 부츠 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3구동기사부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c구동기사 부츠 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4구동기사부츠 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("돈신")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c돈신 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1돈신신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2돈신신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3돈신신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c돈신 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4돈신신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("초합금검은빛")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c초합금 검은빛 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1초합금검은빛신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2초합금검은빛신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3초합금검은빛신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c초합금 검은빛 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4초합금검은빛신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("번견맨")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c번견맨 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1번견맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2번견맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3번견맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c번견맨 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4번견맨신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("섬광의플래시")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c섬광의 플래시 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1섬광의플래시신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2섬광의플래시신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3섬광의플래시신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c섬광의 플래시 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4섬광의플래시신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("탱크톱마스터")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c탱크톱 마스터 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1탱크톱마스터신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2탱크톱마스터신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3탱크톱마스터신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c탱크톱 마스터 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4탱크톱마스터신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("제노스")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c제노스 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1제노스신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2제노스신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3제노스신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c제노스 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4제노스신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("금속배트")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c금속배트 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1금속배트신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2금속배트신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3금속배트신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c금속배트 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4금속배트신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("프리즈너")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c포동포동 프리즈너 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1프리즈너신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2프리즈너신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3프리즈너신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c포동포동 프리즈너 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4프리즈너신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("아마이마스크")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c아마이마스크 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1아마이마스크신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2아마이마스크신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3아마이마스크신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c아마이마스크 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4아마이마스크신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("후부키")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c후부키 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1후부키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2후부키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3후부키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c후부키 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4후부키신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("음속의소닉")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c음속의 소닉 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1음속의소닉신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2음속의소닉신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3음속의소닉신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c음속의 소닉 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4음속의소닉신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						}
					}, timeToRun);
					return false;
				}
				
				if (args[1].equalsIgnoreCase("사이타마")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run upgrade:koin " + p.getName());
					Date timeToRun = new Date(System.currentTimeMillis() + 3800);
					main.timer.schedule(new TimerTask() {
						public void run() {
							int RandomAmount = new Random().nextInt(100) + 1;

							if (RandomAmount >= 1 && RandomAmount <= 59) {
								p.sendMessage("§6장비 §c사이타마 신발 §6를 감정하여 §f일반 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 1사이타마신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}

							else if (RandomAmount >= 60 && RandomAmount <= 84) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 신발 §6를 감정하여 §e레어 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 2사이타마신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 85 && RandomAmount <= 97) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 신발 §6를 감정하여 §d엘리트 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 3사이타마신발 give " + p.getName() + " 1");
								p.closeInventory();
								castLvup(p);
								return;
							}
						
							else if (RandomAmount >= 98 && RandomAmount <= 100) {
								p.sendMessage("§6축하합니다! 장비 §c사이타마 신발 §6를 감정하여 §c유니크 §6등급을 획득하셨습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 4사이타마신발 give " + p.getName() + " 1");
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

		if (commandLabel.equals("무적"))
	    {
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.healthE)
				{
					main.healthE = true;
					Bukkit.broadcastMessage("§f[§4알림§f] §6무적상태로 변경되었습니다.");
					getConfig().addDefault("무적", Boolean.valueOf(true));
					return true;
				}

				main.healthE = false;
				Bukkit.broadcastMessage("§f[§4알림§f] §6일반상태로 변경되었습니다.");
				getConfig().addDefault("무적", Boolean.valueOf(false));
				return true;
			}

			sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
			return true;
	    }
		
		if (commandLabel.equals("채팅"))
		{
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (!main.chatE)
				{
					main.chatE = true;
					Bukkit.broadcastMessage("§f[§4알림§f] §6채팅을 못하도록 설정되었습니다.");
					getConfig().addDefault("무적", Boolean.valueOf(true));
					return true;
				}

				main.chatE = false;
				Bukkit.broadcastMessage("§f[§4알림§f] §6채팅을 할수있게 설정되었습니다.");
				getConfig().addDefault("무적", Boolean.valueOf(false));
				return true;
			}

		}
		
		if (commandLabel.equals("만렙"))
	    {
			if (sender.hasPermission("AllPlayer.admin"))
			{
				if (args.length == 0)
				{
					if (!main.levelE)
					{
						main.levelE = true;
						Bukkit.broadcastMessage("§f[§4알림§f] §6만렙이 설정되었습니다.");
						Bukkit.broadcastMessage(ChatColor.GOLD + "만렙 : " + main.levelF);
						getConfig().addDefault("만렙", Boolean.valueOf(true));
						return true;
					}

					main.levelE = false;
					Bukkit.broadcastMessage("§f[§4알림§f] §6만렙이 해제되었습니다.");
					getConfig().addDefault("만렙", Boolean.valueOf(false));
					return true;
				}

				if (args.length == 1)
				{
					main.levelF = Integer.parseInt(args[0]);
					Bukkit.broadcastMessage("§f[§4알림§f] §6만렙이 §c" + main.levelF + "§6로 설정되었습니다.");
					getConfig().addDefault("레벨", Integer.valueOf(main.levelF));
					return true;
				}

				sender.sendMessage(ChatColor.RED + "명령어 오류");
				sender.sendMessage(ChatColor.RED + "/만렙 - 만렙을 설정 또는 해제합니다.");
				sender.sendMessage(ChatColor.RED + "/만렙 <0 이상의 숫자> - 만렙을 <0 이상의 숫자>로 설정합니다.");
				return true;
			}
	    }
		
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if (commandLabel.equalsIgnoreCase("로비"))
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
			
			else if (commandLabel.equalsIgnoreCase("자살"))
			{
				p.chat("/suicide");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("스폰") || commandLabel.equalsIgnoreCase("넴주") || commandLabel.equalsIgnoreCase("tmvhs"))
			{
				p.chat("/spawn");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("홈"))
			{
				p.chat("/home");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("셋홈"))
			{
				p.chat("/sethome");
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("길드땅이동"))
			{
				GuildGrandToMove(p);
				return false;
			}
		    
			try {
				if(commandLabel.equalsIgnoreCase("쓰레기통"))
				{
					TrashCan(p);
					return false;
				}
			} catch (NumberFormatException ex) {
				TrashCan(p);
				return false;
			}
			
			try {
				if (commandLabel.equals("표지판")) {
					Player play = (Player)sender;
					if (!play.hasPermission("se.edit")) {
						sender.sendMessage("§f[§4경고§f] §c당신은 권한이 없습니다.");
						return true;
					}
			
					Block b = play.getTargetBlock(null, 10);
					if (b == null) {
						sender.sendMessage("§f[§4경고§f] §c당신의 앞에 표지판이 없습니다.");
						return true;
					}
					
					if (!(b.getState() instanceof Sign)) {
						sender.sendMessage("§f[§4경고§f] §c당신의 앞에 표지판이 없습니다.");
						return true;
					}
		
					if (args.length == 0) {
						sender.sendMessage("§f[§4경고§f] §c행 번호와 표지판 내용을 적어주세요.");
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
						sender.sendMessage("§f[§4경고§f] §c라인 수를 1~4로 적어주세요.");
						return true;
					}
		
					line--;
					String message = "";
					for (int i = 1; i < args.length; i++) message = message + " " + args[i];
					if (play.hasPermission("se.editCol")) message = Method.replaceAllColors(message);
					message = message.trim();
					if (message.length() > 15) {
						sender.sendMessage("§f[§4경고§f] §c표지판 내용이 너무 깁니다.");
						return true;
					}
					Bukkit.getScheduler().runTaskAsynchronously(this, new Runner(play, line, message, b));
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/표지판 <줄> <내용> §f- 표지판의 내용을 변경합니다.");
				return true;
			}
			
			if (commandLabel.equalsIgnoreCase("랜덤돈")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(150000) + 1;
					main.economy.depositPlayer(p.getName(), RandomAmount);
					p.sendMessage("§c복권§6을 긁어 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					castLvup(p);
					return false;
				}
			}
			
			if (commandLabel.equalsIgnoreCase("랜덤스텟")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(3) + 1;
					int random = new Random().nextInt(4) + 1;
					
					switch (random) {
					case 1:
						Method.sendCommand("스텟a 추가 1 " + p.getName() + " " + RandomAmount);
						p.sendMessage("§c랜덤 스텟권 §6을 사용하여 §c팔 근력 §6 스텟을 §c" + RandomAmount + " §6만큼 획득하셨습니다.");
						break;
						
					case 2:
						Method.sendCommand("스텟a 추가 2 " + p.getName() + " " + RandomAmount);
						p.sendMessage("§c랜덤 스텟권 §6을 사용하여 §c복근 §6 스텟을 §c" + RandomAmount + " §6만큼 획득하셨습니다.");
						break;
						
					case 3:
						Method.sendCommand("스텟a 추가 3 " + p.getName() + " " + RandomAmount);
						p.sendMessage("§c랜덤 스텟권 §6을 사용하여 §c다리 근력 §6 스텟을 §c" + RandomAmount + " §6만큼 획득하셨습니다.");
						break;
						
					case 4:
						Method.sendCommand("스텟a 추가 4 " + p.getName() + " " + RandomAmount);
						p.sendMessage("§c랜덤 스텟권 §6을 사용하여 §c스피드 §6 스텟을 §c" + RandomAmount + " §6만큼 획득하셨습니다.");
						break;
					} castLvup(p);
					return false;
				}
			}

			if (commandLabel.equalsIgnoreCase("랜덤선물")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(3) + 1;

					switch (RandomAmount) {
					case 1:
						p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_BLOCK, 2) });
						p.sendMessage("§c랜덤 선물 상자§6를 오픈하여 §c다이아몬드 블럭 §6을 획득하셨습니다.");
						break;
						
					case 2:
						p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_BLOCK, 4) });
						p.sendMessage("§c랜덤 선물 상자§6를 오픈하여 §c철 블럭 §6을 획득하셨습니다.");
						break;
						
					case 3:
						p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE, 64) });
						p.sendMessage("§c랜덤 선물 상자§6를 오픈하여 §c돌 §6을 획득하셨습니다.");
						break;
					} castLvup(p);
					return false;
				}
			}
			
			if (commandLabel.equalsIgnoreCase("랜덤칭호")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					String str = null;

					switch (RandomAmount) {
					case 1:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3이상적인&f]"); break;
					case 2:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b노화하는&f]"); break;
					case 3:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6전설적인&f]"); break;
					case 4:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e순진한&f]"); break;
					case 5:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d황당한&f]"); break;
					case 6:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c격렬한&f]"); break;
					case 7:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a온화한&f]"); break;
					case 8:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2황폐한&f]"); break;
					case 9:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9무뚝뚝한&f]"); break;
					case 10:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&4부서진&f]"); break;
					case 11:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6경솔한&f]"); break;
					case 12:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e순결한&f]"); break;
					case 13:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c저렴한&f]"); break;
					case 14:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b차가운&f]"); break;
					case 15:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3일반적인&f]"); break;
					case 16:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b평범한&f]"); break;
					case 17:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&4저주받은&f]"); break;
					case 18:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5손상된&f]"); break;
					case 19:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9구만적인&f]"); break;
					case 20:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5더러운&f]"); break;
					case 21:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a기초적인&f]"); break;
					case 22:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d엽기적인&f]"); break;
					case 23:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a환영의&f]"); break;
					case 24:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&7흉악한&f]"); break;
					case 25:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c손상된&f]"); break;
					case 26:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3불완전한&f]"); break;
					case 27:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2중독되는&f]"); break;
					case 28:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a가벼운&f]"); break;
					case 29:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&7빈약한&f]"); break;
					case 30:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2순진한&f]"); break;
					case 31:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2고전의&f]"); break;
					case 32:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5구식적인&f]"); break;
					case 33:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b창백한&f]"); break;
					case 34:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6겸손한&f]"); break;
					case 35:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9슬픔의&f]"); break;
					case 36:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a무모한&f]"); break;
					case 37:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&4파괴의&f]"); break;
					case 38:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e갸날픈&f]"); break;
					case 39:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c폐기된&f]"); break;
					case 40:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d변질된&f]"); break;
					case 41:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2오염된&f]"); break;
					case 42:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6전통적인&f]"); break;
					case 43:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b창의적인&f]"); break;
					case 44:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a분별있는&f]"); break;
					case 45:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6유능한&f]"); break;
					case 46:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d활동적인&f]"); break;
					case 47:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&4공격적인&f]"); break;
					case 48:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b냉혹한&f]"); break;
					case 49:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9진보적인&f]"); break;
					case 50:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3용기있는&f]"); break;
					case 51:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e사교적인&f]"); break;
					case 52:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c단호한&f]"); break;
					case 53:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e수다스러운&f]"); break;
					case 54:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9성의있는&f]"); break;
					case 55:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c끈질긴&f]"); break;
					case 56:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&7소심한&f]"); break;
					case 57:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&4시끄러운&f]"); break;
					case 58:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2까다로운&f]"); break;
					case 59:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d이기적인&f]"); break;
					case 60:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c거만한&f]"); break;
					case 61:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5변덕스런&f]"); break;
					case 62:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6관대한&f]"); break;
					case 63:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9예민한&f]"); break;
					case 64:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2싹싹한&f]"); break;
					case 65:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b긍정적인&f]"); break;
					case 66:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e외향적인&f]"); break;
					case 67:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5내성적인&f]"); break;
					case 68:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3낭만적인&f]"); break;
					case 69:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&7침착한&f]"); break;
					case 70:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c단순한&f]"); break;
					case 71:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b적극적인&f]"); break;
					case 72:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&a솔직한&f]"); break;
					case 73:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c다혈직인&f]"); break;
					case 74:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5악명높은&f]"); break;
					case 75:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3진보적인&f]"); break;
					case 76:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e양심적인&f]"); break;
					case 77:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6유순한&f]"); break;
					case 78:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5융통성있는&f]"); break;
					case 79:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2건방진&f]"); break;
					case 80:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9헝클어진&f]"); break;
					case 81:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&2특이한&f]"); break;
					case 82:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3사소한&f]"); break;
					case 83:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6무분별한&f]"); break;
					case 84:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e명랑한&f]"); break;
					case 85:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&7약한&f]"); break;
					case 86:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c권위적인&f]"); break;
					case 87:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e게으른&f]"); break;
					case 88:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6다재다능한&f]"); break;
					case 89:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&c악랄한&f]"); break;
					case 90:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&5매력적인&f]"); break;
					case 91:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d매혹적인&f]"); break;
					case 92:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6박식한&f]"); break;
					case 93:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3현명한&f]"); break;
					case 94:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&9열정적인&f]"); break;
					case 95:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&4잔인한&f]"); break;
					case 96:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&6잘생긴&f]"); break;
					case 97:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&3못생긴&f]"); break;
					case 98:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&d얄팍한&f]"); break;
					case 99:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&e재미있는&f]"); break;
					case 100:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "칭호관리 주기 " + p.getName() + " &f[&b창의적인&f]"); break;
					}
					
					castLvup(p);
					return false;
				}
			}
			
			try {
				if(commandLabel.equalsIgnoreCase("수표"))
				{
					String pname = p.getName();
					if ((main.economy.getBalance(pname) >= Integer.valueOf(args[0]).intValue()) && (Integer.valueOf(args[0]).intValue() > 0))
						{
						ItemStack item1 = new ItemStack(339, 1);
						ItemMeta itemM = item1.getItemMeta();
						itemM.setDisplayName("§e§o 수표");
						itemM.setLore(Arrays.asList(new String[] { "§f§oㅡㅡㅡㅡㅡㅡㅡㅡ", args[0], "§f§oㅡㅡㅡㅡㅡㅡㅡㅡ", "§f", "§7§o오른쪽 클릭으로", "§7§o돈으로 환전 가능", "§7", "§7§oby. §a§oESPOO 은행" }));
						item1.setItemMeta(itemM);
						p.getInventory().addItem(new ItemStack[] { item1 });
						main.economy.withdrawPlayer(pname, Double.valueOf(args[0]).doubleValue());
						p.sendMessage("§6정상적으로 발급되었습니다.");
						return true;
						}
			
					p.sendMessage(ChatColor.RED + "발행하고 싶은 돈이 보유한 돈보다 더 많거나 0보다 작은 값을 입력하였습니다.");
					return true;
				}
			} catch (Exception e) {
				p.sendMessage("§6/수표 <돈> §f- 수표를 발행합니다.");
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
				if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args.length == 0)) {
					p.sendMessage("§6/아이템금지 §f- 도움말을 봅니다.");
					p.sendMessage("§6/아이템금지 추가 <경고메세지> §f- 들고있는 아이템을 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 추가 모두 <경고메세지> §f- 들고있는 아이템을 데이터 코드도 모두 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 제거 §f- 들고있는 아이템 금지 여부를 제거합니다.");
					p.sendMessage("§6/아이템금지 확인 §f- 들고있는 아이템 금지여부를 확인합니다.");
					return false;
				} else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("추가")) && (args.length == 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())){
						p.sendMessage("§c명령어를 제대로 사용해주세요. §f- §6/아이템금지 추가 <경고메세지>");
						return false;
					}
					else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				}
				else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("추가")) && (args[1].equalsIgnoreCase("모두")) && (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0) {
							StringBuilder sb1 = new StringBuilder();
							for (int i = 2; i < args.length; i++) {
								sb1.append(args[i]).append(" ");
							}
							String allArgs1 = sb1.toString();
							main.all.add(id + ":-1:" + allArgs1);
        		  
							p.sendMessage("§6아이템 코드 §e[§c" + id + ":*§e] §6는 다음과 같은 이유로 §c금지됩니다§6.:");
							p.sendMessage(" " + allArgs1);

							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						}
						else
						{
							p.sendMessage("§e[§c" + id + ":*" + "§e] §6아이템은 이미 금지되어 있습니다.");
							return false;
						}
					}
					else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				}
				else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("추가")) && (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0)
						{
							main.all.add(id + ":" + data + ":" + allArgs);

							p.sendMessage("§6아이템코드 §e[§c" + id + ":" + data + "§e] §6는 다음과 같은 이유로 §c금지됩니다§6.:");		
							p.sendMessage(" " + allArgs);
    			
							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("§e[§c" + id + ":" + data + "§e] §6아이템은 이미 금지되어 있습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("확인")) && (args.length == 1)) {
					if ((p.hasPermission("banitem.*")) || (p.isOp()) || (p.hasPermission("banitem.check"))) {
						if (itemmethod.getnumber() == 1)
						{
							p.sendMessage("§e[§c" + itemmethod.getId() + ":" + itemmethod.getData() + "§e] §6아이템은 이러한 이유로 금지되어 있습니다:");
							p.sendMessage(" " + itemmethod.getReason());
							return false;
						} else {
							p.sendMessage("§c이 아이템은 금지되어 있지 않습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}	
				}
				else if ((commandLabel.equalsIgnoreCase("아이템금지")) && ((args[0].equalsIgnoreCase("삭제")) || (args[0].equalsIgnoreCase("제거"))) && 
						(args.length == 1)) {
					if ((p.hasPermission("banitem.remove")) || (p.hasPermission("banitem.del")) || (p.hasPermission("banitem.*")) || 
							(p.isOp())) {
						if (itemmethod.getnumber() == 1) {
							main.all.remove(itemmethod.getId() + ":" + itemmethod.getData() + ":" + itemmethod.getReason());

							p.sendMessage("§c아이템§6을 성공적으로 §c금지 목록§6에서 제거하였습니다.");
							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("§c이 아이템은 금지되어 있지 않습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다."); 
						return false;
					}
				}
			} catch (Exception e) {
				if ((sender.isOp()))
				{
					p.sendMessage("§6/아이템금지 §f- 도움말을 봅니다.");
					p.sendMessage("§6/아이템금지 추가 <경고메세지> §f- 들고있는 아이템을 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 추가 모두 <경고메세지> §f- 들고있는 아이템을 데이터 코드도 모두 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 제거 §f- 들고있는 아이템 금지 여부를 제거합니다.");
					p.sendMessage("§6/아이템금지 확인 §f- 들고있는 아이템 금지여부를 확인합니다.");
					return false;
				}
	    	
				else {
					p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
					return false;
				}
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
			return false;
		}
		
		return false;
	}
	
	public static class WorkTask extends TimerTask
	{
		public void run()
		{
			main.Memory = "[알림] 메모리 정리 사용전 : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L + "MB";
			System.gc();
			System.out.println(main.Memory + " , 메모리 정리 사용후 : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L + "MB");
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
					this.play.sendMessage("§cLogBlock이 블록의 레코드를 제공하지 않습니다.");
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