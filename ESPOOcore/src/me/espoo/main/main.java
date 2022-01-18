package me.espoo.main;

import java.io.File;

import me.espoo.Banitem.*;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import net.minecraft.server.v1_5_R3.Packet205ClientCommand;
import me.espoo.NameSings.MetricsLite;
import me.espoo.NameSings.PacketUpdateSignWrapper;
import me.espoo.NameSings.SimpleChanger;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Bukkit;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.help.HelpTopic;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import me.espoo.TrashCan.TrashCan;
import me.espoo.Armor.*;
import me.espoo.GiveAll.GiveAll;
import to.oa.tpsw.rpgexpsystem.main.CustomConfig2;
import me.espoo.FarmAssist.FarmAssistBlockListener;
import me.espoo.PTweaks.*;
import me.espoo.og.*;
import me.espoo.stopdroproll.RollDetection;
import me.espoo.NameSings.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.server.ServerListPingEvent;

import me.michidk.DKLib.command.CommandManager;
import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;

import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("deprecation")
public class main extends JavaPlugin implements Listener {

	public main plugin;

	{
		this.plugin = plugin;
	}

	public static Economy econ = null;
	static ArrayList<Integer> list;
	ArrayList<Integer> list2;
	public static int config_itemcode = 339;
	public static boolean config_setlore = true;
	public static Economy economy;
	public static int inte = 5;

	static Calendar today = Calendar.getInstance();

	static int SystemYear = today.get(Calendar.YEAR);
	static int SystemMonth = today.get(Calendar.MONTH) + 1;
	static int SystemDay = today.get(Calendar.DATE);

	ItemStack AirItem = new ItemStack(Material.AIR, 1);

	private static final int MAX_ENCHANT = 32767;
	static HashMap<String, Enchantment> enchantmentNames = new HashMap<String, Enchantment>();
	public static HashMap<String, Integer> FirePlayers = new HashMap<String, Integer>();

	public static int e;
	public static String Motd = "";

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
		try {
			item.addUnsafeEnchantment(enchantment, level > 32767 ? 32767 : level);
		} catch (Exception e) {
			return EnchantmentResult.CANNOT_ENCHANT;
		}
		return EnchantmentResult.VICIOUS_STREAK_A_MILE_WIDE;
	}

	static EnchantmentResult enchantItem(Player player, String enchantmentString, int level) {
		try {
			return enchantItem(player, Enchantment.getById(Integer.valueOf(enchantmentString).intValue()), level);
		} catch (NumberFormatException e) {
		}
		return enchantItem(player, getEnchantment(enchantmentString), level);
	}

	static String iteminfo;
	static String itemname;
	static String itemlore;
	public static Logger log = Logger.getLogger("Minecraft");
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ";
	private static GiveAll command;

	public static Boolean perm = null;
	public static int seconds = 10;
	public static int item = 347;
	public static String onmsgraw = null;
	public static String offmsgraw = null;

	public static main p;
	public boolean debug;
	public int delay2;

	public static float stone;
	public static float coal;
	public static float iron;
	public static float gold;
	public static float redstone;
	public static float lapis;
	public static float emerald;
	public static float diamond;

	public static ArrayList<Player> Players = new ArrayList();
	public static ArrayList<Player> Antispam = new ArrayList();
	public static HashMap<Player, Integer> hungry = new HashMap<Player, Integer>();

	static File configFile;
	public static FileConfiguration config;
	public static List<String> playerList = new ArrayList();
	public static boolean Enabled = true;

	public static TrashCan T = new TrashCan();
	public char Trash;
	public Object localObject;
	public static int i;
	private Map<String, Integer> forceRespawn = new HashMap();
	private int delay = 1;

	public final PlayerListener pl = new PlayerListener(this);
	public final BlockListener bl = new BlockListener(this);
	public final WorldScanner ws = new WorldScanner(this);
	public static ArrayList<String> all = new ArrayList();
	public static ArrayList<String> place = new ArrayList();
	public static ArrayList<String> pickup = new ArrayList();
	public static ArrayList<String> world = new ArrayList();
	public static ArrayList<String> interact = new ArrayList();
	public PluginDescriptionFile pdfFile = getDescription();
	public static ArrayList<String> click = new ArrayList();
	public static ArrayList<String> br = new ArrayList();
	public ArrayList<String> worlds = new ArrayList();

	public static ArrayList<String> all2 = new ArrayList();

	public static boolean stopE = false;
	public static boolean blockE = false;
	public static boolean healthE = false;
	public static boolean chatE = false;
	public static boolean levelE = true;
	public static int levelF = 300;

	Map<String, Sound> soundMap = new HashMap();

	private static main instance = null;
	private CommandManager cm;

	private AutoSaveStopper mStopper;
	private MonsterLimiter mMonster;
	private ChunkPersistance mChunkP;
	public FileConfiguration autoSaveStopper;
	public FileConfiguration monsterLimiter;
	public FileConfiguration chunkPersistance;

	@Deprecated
	private Map<Changer, SimpleChanger> changers = new HashMap();
	private static ProtocolManager protocolManager;

	int task;
	HashMap<String, Integer> place_map = new HashMap();
	HashMap<String, Integer> break_map = new HashMap();

	HashMap<String, Integer> shop_map = new HashMap();
	HashMap<String, Integer> lever_map = new HashMap();
	HashMap<String, Integer> lighter_map = new HashMap();
	HashMap<String, String> mute = new HashMap();

	public static CustomConfig2 message;

	static LogBlock logblock = null;
	static Consumer lbconsumer = null;
	
	

	public void onEnable() {
		setupEconomy();
		loadConfig();
		getServer().getPluginManager().registerEvents(this, this);
		new mainCommand(this);
		getServer().getPluginManager().registerEvents(new mainCommand(this), this);
		getCommand("��������").setExecutor(new mainCommand(this));
		getCommand("ä��û��").setExecutor(new mainCommand(this));
		getCommand("cc").setExecutor(new mainCommand(this));
		getCommand("save").setExecutor(new mainCommand(this));
		getCommand("��纸��").setExecutor(new mainCommand(this));
		getCommand("��ǥ").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("��").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("ä��").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("��þƮ���").setExecutor(new mainCommand(this));
		getCommand("��þƮ").setExecutor(new mainCommand(this));
		getCommand("��").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("tppod").setExecutor(new mainCommand(this));
		getCommand("f").setExecutor(new mainCommand(this));
		getCommand("itemtimer").setExecutor(new mainCommand(this));
		getCommand("permit").setExecutor(new mainCommand(this));
		getCommand("���չ���").setExecutor(new mainCommand(this));
		getCommand("�����۱���").setExecutor(new mainCommand(this));
		getCommand("o").setExecutor(new mainCommand(this));
		Bukkit.getPluginManager().registerEvents(new RollDetection(), this);

		if (!setupEconomy()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		List tempList = new ArrayList();
		tempList.add(Integer.valueOf(116));
		getConfig().addDefault("blacklist.items", tempList);
		this.list = ((ArrayList) getConfig().getIntegerList("blacklist.items"));

		List tempList2 = new ArrayList();
		tempList2.add(Integer.valueOf(116));
		getConfig().addDefault("placelist.items", tempList2);
		this.list2 = ((ArrayList) getConfig().getIntegerList("placelist.items"));

		PluginDescriptionFile pdfFile = getDescription();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this.pl, this);
		pm.registerEvents(this.bl, this);
		pm.registerEvents(this.ws, this);
		this.all2 = ((ArrayList) getConfig().getStringList("placelist.items"));
		this.all = ((ArrayList) getConfig().getStringList("Blacklist"));
		this.place = ((ArrayList) getConfig().getStringList("Blacklist Placement"));
		this.pickup = ((ArrayList) getConfig().getStringList("Blacklist Pickup"));
		this.interact = ((ArrayList) getConfig().getStringList("Blacklist Interaction"));
		this.click = ((ArrayList) getConfig().getStringList("Blacklist Click"));
		this.worlds = ((ArrayList) getConfig().getStringList("Blacklist World"));
		this.br = ((ArrayList) getConfig().getStringList("Blacklist Break"));
		instance = this;
		plugin = this;
		perm = Boolean.valueOf(getConfig().getBoolean("usepermission"));
		seconds = getConfig().getInt("wait_time");
		item = getConfig().getInt("itemid");
		onmsgraw = getConfig().getString("toggleon");
		offmsgraw = getConfig().getString("toggleoff");

		getDataFolder().mkdir();

		this.setCm(new CommandManager(this));

		new SimpleChanger(this, "<playername>", "insigns.create.player") {
			public String getValue(Player player, Location location, String originalLine) {
				return player.getName();
			}
		};
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL,
				new PacketType[] { PacketType.Play.Server.UPDATE_SIGN }) {
			public void onPacketSending(PacketEvent event) {
				PacketContainer signUpdatePacket = event.getPacket();
				PacketUpdateSignWrapper incoming = new PacketUpdateSignWrapper(signUpdatePacket);
				Player player = event.getPlayer();
				Location location = new Location(player.getWorld(), incoming.getX(), incoming.getY(), incoming.getZ());

				SignSendEvent signEvent = new SignSendEvent(player, location, incoming.getLines());
				main.this.getServer().getPluginManager().callEvent(signEvent);

				if (signEvent.isCancelled()) {
					event.setCancelled(true);
				} else if (signEvent.isModified()) {
					String[] lines = signEvent.getLines();

					for (int i = 0; i < lines.length; i++) {
						if (lines[i].length() > 15) {
							if ((i < lines.length - 1) && (lines[(i + 1)].isEmpty())) {
								lines[(i + 1)] = lines[i].substring(15);
							}
							lines[i] = lines[i].substring(0, 15);
						}

					}

					PacketUpdateSignWrapper outgoing = new PacketUpdateSignWrapper(signUpdatePacket.shallowClone());
					outgoing.setLines(lines);

					event.setPacket(outgoing.getPacket());
				}
			}
		});
		if (getConfig().getBoolean("metrics-stats", true)) {
			try {
				MetricsLite metrics = new MetricsLite(this);
				metrics.start();
			} catch (IOException localIOException) {
			}
		}
		getLogger().info(getDescription().getVersion() + " enabled.");

		new ConfigManager(this);
		if (this.autoSaveStopper.getBoolean("Enabled")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.YELLOW + "AutoSaveStopper ��� Ȱ��ȭ");
			this.mStopper = new AutoSaveStopper(this);
			this.mStopper.onEnable();
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.RED + "AutoSaveStopper ��� ��Ȱ��ȭ");
		}

		if (this.chunkPersistance.getBoolean("Enabled")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.YELLOW + "ChunkPersistance ��� Ȱ��ȭ");
			this.mChunkP = new ChunkPersistance(this);
			this.mChunkP.onEnable();
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.RED + "ChunkPersistance ��� ��Ȱ��ȭ");
		}

		if (this.monsterLimiter.getBoolean("Enabled")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.YELLOW + "MonsterLimiter ��� Ȱ��ȭ");
			this.mMonster = new MonsterLimiter(this);
			this.mMonster.onEnable();
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "- " + ChatColor.RED + "MonsterLimiter ��� ��Ȱ��ȭ");
		}

		if (this.chunkPersistance.getBoolean("Enabled"))
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				public void run() {
					if (main.this.freeMem() < 60L)
						main.this.mChunkP.clear();
				}
			}, 1L, 1L);

		new Listeners(this);
		if (!getDataFolder().exists()) {
			getConfig().set("World", addWorlds());
		}
		reloadChances();

		saveDefaultConfig();
		this.Motd = getConfig().getString("motd", this.Motd);

		this.logblock = ((LogBlock) getServer().getPluginManager().getPlugin("LogBlock"));
		if (this.logblock != null)
			this.lbconsumer = this.logblock.getConsumer();
		new FarmAssistBlockListener(this);
		getServer().getPluginManager().registerEvents(new FarmAssistBlockListener(this), this);

		this.configFile = new File(getDataFolder(), "config.yml");
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.config = new YamlConfiguration();
		getConfig().options().copyDefaults(true);
		saveConfig();
		loadYamls();
		stopE = getConfig().getBoolean("����");
		blockE = getConfig().getBoolean("��");
		healthE = getConfig().getBoolean("����");
		chatE = getConfig().getBoolean("ä��");
		levelE = getConfig().getBoolean("����");
		levelF = getConfig().getInt("����");

		PluginManager manager = getServer().getPluginManager();
		CraftServer cs = (CraftServer) getServer();
		cs.getServer().primaryThread.setPriority(10);
		initCommand();
		PluginDescriptionFile pdFile = this.getDescription();
		getConfig().options().copyDefaults(true);
		saveConfig();

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.getWorld().setStorm(false);
					p.getWorld().setThundering(false);
					p.getWorld().setTime(846000);
				}
			}
		}, 100, 100);

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE
				+ pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");

		getServer().getPluginManager().registerEvents(new armorListener(), this);

		try {
			this.config.save(this.configFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static main getInstance() {
		return instance;
	}

	public void onDisable() {
		getConfig().set("Blacklist", this.all);
		getConfig().set("Blacklist Placement", this.place);
		getConfig().set("Blacklist Pickup", this.pickup);
		getConfig().set("Blacklist Interaction", this.interact);
		getConfig().set("Blacklist Click", this.click);
		getConfig().set("Blacklist Break", this.br);
		getConfig().set("Blacklist World", this.worlds);

		for (Player p : Players) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				p.showPlayer(pl);
			}
		}
		Players.clear();
		Antispam.clear();

		getServer().getScheduler().cancelTasks(this);
		protocolManager = null;
		if (this.mChunkP != null) {
			this.mChunkP.onDisable();
		}

		if (this.mStopper != null) {
			this.mStopper.onDisable();
		}

		getServer().getScheduler().cancelTasks(this);
		getServer().getScheduler().cancelTasks(this);
		PluginDescriptionFile pdFile = this.getDescription();
		saveConfig();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE
				+ pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}

	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

	public static Player p(String s) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getName().equals(s)) {
				return all;
			}
		}
		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			if (commandLabel.equalsIgnoreCase("ec")) {
				if (sender.isOp() == true) {
					if (args.length == 0 || args[0].equalsIgnoreCase("1")) {
						sender.sendMessage("��a���7��                                                 ��a���7��");
						sender.sendMessage("��7���c�� ��e----- ��6ESPOO core ��e-- ��6���� ��e----- ��7���c��");
						sender.sendMessage("");
						sender.sendMessage("��6/ec ��f- ESPOO core ������ ���ϴ�.");
						sender.sendMessage("��6/ec reload ��f- ESPOO core config�� ���ε� �մϴ�.");
						sender.sendMessage("��6/�������� ��f- ���������� ���ϴ�.");
						sender.sendMessage("��6/ä��û��, cc ��f- ä��û�Ҹ� �մϴ�.");
						sender.sendMessage("��6/���� ��f- ��� �÷��̾�� �տ� �� �������� �����մϴ�.");
						sender.sendMessage("��6/��ǥ <��> ��f- �� ��ŭ ��ǥ�� �����մϴ�.");
						sender.sendMessage("��6/��纸�� Toggle ��f- ���� �ڵ� �ɱ� ����� ���ų� ŵ�ϴ�.");
						sender.sendMessage("��6/��纸�� Global ��f- ���� �ڵ� �ɱ� �۷ι� ����� ���ų� ŵ�ϴ�.");
						sender.sendMessage("��6/���� ��f- ��� �÷��̾ ���� ��ŵ�ϴ�.");
						sender.sendMessage("��6/�� ��f- ����÷��̾ ���� Ķ �� ���� �˴ϴ�.");
						sender.sendMessage("");
						sender.sendMessage("��6��ɾ� ��c/ec 2 ��6�� �ļ� ������������ �Ѿ����.");
						return false;
					}

					else if (args[0].equalsIgnoreCase("2")) {
						sender.sendMessage("��a���7��                                                 ��a���7��");
						sender.sendMessage("��7���c�� ��e----- ��6ESPOO core ��e-- ��6���� ��e----- ��7���c��");
						sender.sendMessage("");
						sender.sendMessage("��6/���� ��f- ����÷��̾ ������ �˴ϴ�.");
						sender.sendMessage("��6/ä�� ��f- ����÷��̾ ä���� �� �� ���� �˴ϴ�.");
						sender.sendMessage("��6/���� ��f- ������ ���� �Ǵ� �����մϴ�.");
						sender.sendMessage("��6/���� <0 �̻��� ����> ��f- ������ <0 �̻��� ����>�� �����մϴ�.");
						sender.sendMessage("��6/��þƮ��� ��ȣ ��f- ��ȣ ��þƮ �ڵ带 ���ϴ�.");
						sender.sendMessage("��6/��þƮ��� Ȱ ��f- Ȱ ��þƮ �ڵ带 ���ϴ�.");
						sender.sendMessage("��6/��þƮ ��� ��f- �ڽ��� ����ִ°Ϳ� ��� ��þƮ�� �ο��մϴ�.");
						sender.sendMessage("��6/��þƮ ��� ���� ��f- �ڽ��� ��� �ִ°Ϳ� ���������� ��� ��þƮ�� �ο��մϴ�.");
						sender.sendMessage("��6/��þƮ <�ڵ�> <����> ��f- �ش� ���⿡ �ڵ常ŭ ������ �ο��մϴ�.");
						sender.sendMessage("��6/��þƮ <�ڵ�> �ִ� ��f- �ش� ���⿡ �ڵ� ��ŭ ������ �ִ�� �ο��մϴ�.");
						sender.sendMessage("");
						sender.sendMessage("��6��ɾ� ��c/ec 3 ��6�� �ļ� ������������ �Ѿ����.");
						return false;
					}

					else if (args[0].equalsIgnoreCase("3")) {
						sender.sendMessage("��a���7��                                                 ��a���7��");
						sender.sendMessage("��7���c�� ��e----- ��6ESPOO core ��e-- ��6���� ��e----- ��7���c��");
						sender.sendMessage("");
						sender.sendMessage("��6/ǥ���� <��> <����> ��f- ǥ������ ������ �����մϴ�.");
						sender.sendMessage("��6/�� ��f- �� ��踦 Ȯ���մϴ�.");
						sender.sendMessage("��6/���� ��f- ������ ų, ���� ������ �����ϴ� ������ ���ϴ�.");
						sender.sendMessage("��6/tppod <x> <y> <z> ��f- x, y, z ��ġ�� �̵��մϴ�.");
						sender.sendMessage("��6/f t <�г���> <�����̸�> <x> <y> <z> <yaw> <pitch> ��f- �÷��̾ �ش� ��ǥ�� �̵���ŵ�ϴ�.");
						sender.sendMessage("��6/f t <�г���> <�����̸�> <x> <y> <z> ��f- �÷��̾ �ش� ��ǥ�� �̵���ŵ�ϴ�.");
						sender.sendMessage("��6/f tp <�̵���ų�г���> <��ǥ�г���> ��f- �̵���ų �г����� ��ǥ �г������� �̵���ŵ�ϴ�.");
						sender.sendMessage("��6/f s <�̵���ų�г���> ��f- �÷��̾ �ڽ��� ��ġ�� �̵���ŵ�ϴ�.");
						sender.sendMessage(
								"��6/f tpall <��ǥ�г���> ��f- ��� �÷��̾ ��ǥ �г��� ��ġ�� �̵���ŵ�ϴ�. ���� ���� ��� �ڽ��� ��ġ�� �̵���ŵ�ϴ�.");
						sender.sendMessage("��6/PInfo, /PlayerInfo, /���� ��f- ������ ���ϴ�.");
						sender.sendMessage("");
						sender.sendMessage("��6��ɾ� ��c/ec 4 ��6�� �ļ� ������������ �Ѿ����.");
						return false;
					}

					else if (args[0].equalsIgnoreCase("4")) {
						sender.sendMessage("��a���7��                                                 ��a���7��");
						sender.sendMessage("��7���c�� ��e----- ��6ESPOO core ��e-- ��6���� ��e----- ��7���c��");
						sender.sendMessage("");
						sender.sendMessage("��6/ItemTimer ��f- ������ Ÿ�̸� ������ ���ϴ�.");
						sender.sendMessage("��6/Permit ��f- �޹̼� ����, ������ ���θ� ���ϴ�.");
						sender.sendMessage("��6/play <me|world|all|playername> <����> ��f- ������ ����մϴ�.");
						sender.sendMessage("��6/nbp ��f- ���� ��ɾ ���ϴ�.");
						sender.sendMessage("��6/���չ��� �߰� ��f- ���� ������ �������� �߰��մϴ�.");
						sender.sendMessage("��6/���չ��� ���� ��f- ���� ������ �������� �����մϴ�.");
						sender.sendMessage("��6/save ��f- ������ ��ü �����մϴ�.");
						sender.sendMessage("");
						sender.sendMessage("��6��ɾ� ��c/ec 5 ��6�� �ļ� ������������ �Ѿ����.");
						return false;
					}

					else if (args[0].equalsIgnoreCase("5")) {
						sender.sendMessage("��a���7��                                                 ��a���7��");
						sender.sendMessage("��7���c�� ��e----- ��6ESPOO core ��e-- ��6���� ��e----- ��7���c��");
						sender.sendMessage("");
						sender.sendMessage("��6/�����۱��� ��f- ������ ���ϴ�.");
						sender.sendMessage("��6/�����۱��� �߰� <���޼���> ��f- ����ִ� �������� ������� ���ϰ� �մϴ�.");
						sender.sendMessage("��6/�����۱��� �߰� ��� <���޼���> ��f- ����ִ� �������� ������ �ڵ嵵 ��� ������� ���ϰ� �մϴ�.");
						sender.sendMessage("��6/�����۱��� ���� ��f- ����ִ� ������ ���� ���θ� �����մϴ�.");
						sender.sendMessage("��6/�����۱��� Ȯ�� ��f- ����ִ� ������ �������θ� Ȯ���մϴ�.");
						sender.sendMessage("��6/o ��f- 5���Ŀ� ������ �ݽ��ϴ�.");
						sender.sendMessage("��6ǥ���ǿ� <playername> ��f- ǥ���ǿ� �ڽ��� �г����� ���̰� �ϴ� ����� �ֽ��ϴ�.");
						sender.sendMessage("");
						sender.sendMessage("��e----------------------------------------------------");
						return false;
					}

					else if (args[0].equalsIgnoreCase("give")) {
						String name = args[1];
						Player p = main.getOnorOffLine(name);
						int money = Integer.parseInt(args[2]);

						if (p == null) {
							sender.sendMessage("�׿��� ���� �� �� �����ϴ�.");
							return true;
						}

						p.sendMessage("��a$30 ���� �ԱݵǾ����ϴ�.");
						economy.depositPlayer(name, money);
						return true;
					}

					else if (args[0].equalsIgnoreCase("reload")) {
						sender.sendMessage("��f[��4�˸���f] ��aconfig ������ ���ε� �Ͽ����ϴ�.");
						getConfig().options().copyDefaults(true);
						reloadConfig();
						main.Motd = getConfig().getString("motd", main.Motd);
						reloadChances();
						main.all = ((ArrayList) getConfig().getStringList("Blacklist"));
						main.place = ((ArrayList) getConfig().getStringList("Blacklist Placement"));
						main.pickup = ((ArrayList) getConfig().getStringList("Blacklist Pickup"));
						main.interact = ((ArrayList) getConfig().getStringList("Blacklist Interaction"));
						main.click = ((ArrayList) getConfig().getStringList("Blacklist Click"));
						main.br = ((ArrayList) getConfig().getStringList("Blacklist Break"));
						main.world = ((ArrayList) getConfig().getStringList("Blacklist World"));
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6/ec ��f- ESPOO core ������ ���ϴ�.");
			sender.sendMessage("��6/ec reload ��f- ESPOO core config�� ���ε� �մϴ�.");
			return true;
		}
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (commandLabel.equalsIgnoreCase("cmute")) {
				mute.put(p.getName(), "true");
			}

			try {
				if (commandLabel.equals("ǥ����")) {
					Player play = (Player) sender;
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
					for (int i = 1; i < args.length; i++)
						message = message + " " + args[i];
					if (play.hasPermission("se.editCol"))
						message = message.replaceAll("([^\\\\](\\\\\\\\)*)&(.)", "$1��$3")
								.replaceAll("(([^\\\\])\\\\((\\\\\\\\)*))&(.)", "$2$3&$5")
								.replaceAll("\\\\\\\\", "\\\\");
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

			try {
				if (commandLabel.equalsIgnoreCase("ItemTimer") && p.isOp()) {
					p.sendMessage("��e�� " + ChatColor.GREEN + "Plugin source By kakao2933");
					p.sendMessage(ChatColor.AQUA + "�� ����� �����ۿ� �Ⱓ ������ ���̰� ���� �� ����ϴ� �÷������Դϴ�.");
					p.sendMessage(ChatColor.AQUA + "�⺻ �����ۿ��� NbtEditor�� Lore �߰� ������� ����Ͻ� �� ������,");
					p.sendMessage(ChatColor.AQUA + "RpgItem���� �����ڵ� ���� �Ⱓ/�⵵/��/�� ������ ���ֽø� �˴ϴ�.");
					p.sendMessage("");
					p.sendMessage(ChatColor.GOLD + "Ex) /nbtitem lore add �Ⱓ: 2015/3/6");
					p.sendMessage(ChatColor.GOLD + "Ex) /Rpgitem ���� description add �Ⱓ: 2015/3/6");
					p.sendMessage("");
					p.sendMessage(ChatColor.AQUA + "���� ��¥ : " + ChatColor.GOLD + main.SystemYear + " / "
							+ main.SystemMonth + " / " + main.SystemDay);
					p.sendMessage(
							ChatColor.RED + "�÷����ο��� ������ �߻��� �� KakaoTalk : joo2933 / joo2933@naver.com���� ���� �ٶ��ϴ�.");

				}
			} catch (NumberFormatException ex) {
				if (p.isOp()) {
					sender.sendMessage("��6/PInfo, /PlayerInfo, /���� ��f- ������ ���ϴ�.");
					sender.sendMessage("��6/ItemTimer ��f- ������ Ÿ�̸� ������ ���ϴ�.");
					sender.sendMessage("��6/Permit ��f- �޹̼� ����, ������ ���θ� ���ϴ�.");
					return true;
				} else {
					sender.sendMessage("��6/���� <�г���> ��f- ������ ���ϴ�.");
					return true;
				}
			}
		}

		else
			sender.sendMessage(
					ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
		return true;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onClickItem(InventoryClickEvent e) {
		if (e.getInventory().getType().name().equalsIgnoreCase("FURNACE")) {
			if (e.getCurrentItem() == null)
				return;
			if (e.getRawSlot() < 3)
				return;
			if (e.getCurrentItem().hasItemMeta())
				e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {
		if (e.getSource().hasItemMeta())
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		String playerName = player.getName();

		Player diedP = event.getEntity();
		if (diedP == null)
			return;

		diedP.playSound(diedP.getLocation(), Sound.WITHER_SPAWN, 1.0F, 1.0F);

		this.forceRespawn.put(playerName, Integer.valueOf(getServer().getScheduler().scheduleSyncDelayedTask(this,
				new ForceRespawn(playerName, this), this.delay * 20L)));
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();

		if (this.forceRespawn.containsKey(playerName))
			getServer().getScheduler().cancelTask(((Integer) this.forceRespawn.get(playerName)).intValue());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void teleportEvent(PlayerTeleportEvent e) {
		String cause = e.getCause().toString();
		if (!cause.equals("COMMAND"))
			return;

		Player p = e.getPlayer();
		Location to = e.getTo().clone();
		Location from = e.getFrom().clone();
		p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 1.0F, 1.0F);
		p.getWorld().playEffect(from.add(0.0D, 1.4D, 0.0D), Effect.ENDER_SIGNAL, 1);
		p.getWorld().playEffect(to.add(0.0D, 1.4D, 0.0D), Effect.ENDER_SIGNAL, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void eventOnLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);

		Player p = e.getPlayer();
		if (Players.contains(p)) {
			Players.remove(p);
		}
		for (Player pl : Bukkit.getOnlinePlayers())
			pl.showPlayer(p);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKicked(PlayerKickEvent e) {
		e.setLeaveMessage(null);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void eventOnJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();

		for (Player pl : Players)
			pl.hidePlayer(p);

		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getHelmet().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getHelmet();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setHelmet(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c������6 �������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setHelmet(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setHelmet(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

		if (p.getInventory().getChestplate() != null
				&& p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getChestplate().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getChestplate();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setChestplate(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setChestplate(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setChestplate(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getLeggings().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getChestplate();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setLeggings(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���뽺 ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setLeggings(AirItem);
						p.sendMessage("��c���뽺 ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setLeggings(AirItem);
						p.sendMessage("��c���뽺 ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getBoots().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getBoots();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setBoots(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setBoots(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setBoots(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

	}

	@EventHandler
	public void craft(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked();
		if ((getConfig().contains("blacklist.items")) && (!player.hasPermission("blacklist.bypass"))
				&& (this.list.contains(Integer.valueOf(event.getRecipe().getResult().getTypeId())))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void PlayerInetact(PlayerInteractEvent e) {
		Action a = e.getAction();
		final Player p = e.getPlayer();

		if (a == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();

			if (b.getType() == Material.ANVIL) {
				p.chat("/��");
				e.setCancelled(true);
			}

			else if (b.getType() == Material.ENCHANTMENT_TABLE) {
				p.chat("/����");
				e.setCancelled(true);
			}
		}

		if ((a.equals(Action.LEFT_CLICK_AIR)) || (a.equals(Action.LEFT_CLICK_BLOCK))
				|| (a.equals(Action.RIGHT_CLICK_AIR)) || (a.equals(Action.RIGHT_CLICK_BLOCK))) {
			if (p.getItemInHand() == null)
				return;
			if (p.getItemInHand().getItemMeta() == null)
				return;
			if (p.getItemInHand().getItemMeta().getLore() != null)
				for (String str : p.getItemInHand().getItemMeta().getLore()) {
					if (str.contains("��� ���� ���� ")) {
						String[] Cut = str.split(" ");
						String DCut = DeleteColor(Cut[3]);
						String Permit = DCut;
						if (!p.hasPermission(Permit)) {
							if (p != null)
								p.kickPlayer("��c����� �������� ����� ������ �����ϴ�.");
						}
					}

					if (str.contains("�Ⱓ:")) {
						String[] Cut = str.split(" ");
						String[] Time = Cut[1].split("/");

						int Itemyear = Integer.parseInt(Time[0]);
						int Itemmonth = Integer.parseInt(Time[1]);
						int Itemday = Integer.parseInt(Time[2]);

						if ((Itemyear < this.SystemYear) || (Itemmonth < this.SystemMonth)) {
							e.setCancelled(true);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getHelmet())
								p.getInventory().setHelmet(this.AirItem);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getChestplate())
								p.getInventory().setChestplate(this.AirItem);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getLeggings())
								p.getInventory().setLeggings(this.AirItem);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getBoots())
								p.getInventory().setBoots(this.AirItem);
							p.setItemInHand(this.AirItem);
							p.sendMessage("��c������ ��� ���ѡ�6�� ���� ��c�Ҹ��6�Ǿ����ϴ�.");
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
						} else {
							if ((Itemmonth != this.SystemMonth) || (Itemday >= this.SystemDay))
								continue;
							e.setCancelled(true);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getHelmet())
								p.getInventory().setHelmet(this.AirItem);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getChestplate())
								p.getInventory().setChestplate(this.AirItem);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getLeggings())
								p.getInventory().setLeggings(this.AirItem);
							if (p.getItemInHand().getItemMeta() == p.getInventory().getBoots())
								p.getInventory().setBoots(this.AirItem);
							p.setItemInHand(this.AirItem);
							p.sendMessage("��c������ ��� ���ѡ�6�� ���� ��c�Ҹ��6�Ǿ����ϴ�.");
							p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
						}
					}
				}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract2(PlayerInteractEvent e) {
		Action act = e.getAction();
		Player p = e.getPlayer();

		if (act == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CAKE_BLOCK) {
				e.getClickedBlock().setType(Material.AIR);
				p.setFoodLevel(p.getFoodLevel() - 1);
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

			else if (Item.getTypeId() == 260 || Item.getTypeId() == 282 || Item.getTypeId() == 297
					|| Item.getTypeId() == 319 || Item.getTypeId() == 320 || Item.getTypeId() == 322
					|| Item.getTypeId() == 349 || Item.getTypeId() == 350 || Item.getTypeId() == 357
					|| Item.getTypeId() == 360 || Item.getTypeId() == 363 || Item.getTypeId() == 364
					|| Item.getTypeId() == 365 || Item.getTypeId() == 366 || Item.getTypeId() == 367
					|| Item.getTypeId() == 375 || Item.getTypeId() == 393 || Item.getTypeId() == 400
					|| Item.getTypeId() == 394 || Item.getTypeId() == 391 || Item.getTypeId() == 392) {
				if (p.isOp()) {
					return;
				}

				if (Item.hasItemMeta() && Item.getItemMeta().hasDisplayName()) {
					if (Item.getItemMeta().hasLore() && Item.getItemMeta().getLore().contains("��f��f�����̾� �������� ��ȭ��")
							&& Item.getItemMeta().getLore().contains("��f��f���� �ٲ� �� ���� �� ����.")) {
						e.setCancelled(true);
						p.updateInventory();
						p.sendMessage("");
						p.sendMessage("��c����ǰ�� ��� �� �����ϴ�.");
					}

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
						return;
					}

					default: {
						break;
					}
					}

					if (e.getClickedBlock().getTypeId() == 60)
						return;
				}
			}
		}
	}

	@EventHandler
	public void PlayerInetact3(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getTypeId() != item) {
			return;
		}

		if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if ((perm.booleanValue()) && (!p.isOp()) && (!p.hasPermission("magicclock.use"))) {
				return;
			}

			if (Antispam.contains(p)) {
				return;
			}
			if (Players.contains(p)) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					p.showPlayer(pl);
				}
				String offmsg = getInstance().colorize(offmsgraw);
				p.sendMessage(offmsg);
				Players.remove(p);
			} else {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(pl);
				}
				String onmsg = getInstance().colorize(onmsgraw);
				p.sendMessage(onmsg);
				Players.add(p);
			}

			Antispam.add(p);
			long ticks = seconds * 20;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) getInstance(),
					(Runnable) new Runnable() {
						@Override
						public void run() {
							main.Antispam.remove(p);
						}
					}, ticks);
		}

		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock() != null)) {
			Block block = e.getClickedBlock();
			Material blockType = block.getType();
			if ((blockType == Material.WALL_SIGN) || (blockType == Material.SIGN_POST)) {
				Sign sign = (Sign) block.getState();
				sendSignChange(e.getPlayer(), sign);
			}
		}
	}

	@Deprecated
	public synchronized List<Changer> getChangerList() {
		return new ArrayList(this.changers.keySet());
	}

	@EventHandler
	public void PlayerClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getHelmet().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getHelmet();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setHelmet(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setHelmet(AirItem);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setHelmet(AirItem);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

		if (p.getInventory().getChestplate() != null
				&& p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getChestplate().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getChestplate();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setChestplate(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setChestplate(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setChestplate(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getLeggings().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getLeggings();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setLeggings(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���뽺 ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setLeggings(AirItem);
						p.sendMessage("��c���뽺 ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setLeggings(AirItem);
						p.sendMessage("��c���뽺 ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}

		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			for (String armorContent : p.getInventory().getBoots().getItemMeta().getLore()) {

				if (armorContent.contains("��� ���� ���� ")) {
					String[] Cut = armorContent.split(" ");
					String DCut = DeleteColor(Cut[3]);
					String Permit = DCut;
					ItemStack is = p.getInventory().getBoots();
					if (!p.hasPermission(Permit)) {
						p.getInventory().setBoots(AirItem);
						p.getInventory().addItem(is);
						p.sendMessage("��c���� ��6�������� ��c��� ���ѡ�6�� ������ ������ ��c������6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}

				if (armorContent.contains("�Ⱓ:")) {
					String[] Cut = armorContent.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						p.getInventory().setBoots(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						p.getInventory().setBoots(AirItem);
						p.sendMessage("��c���� ��6�����ۡ�c ��� ���ѡ�6�� ������c �Ҹ��6�Ǿ����ϴ�.");
						p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
					}
				}
			}
		}
	}

	public String colorize(String Message) {
		return Message.replaceAll("~([a-z0-9])", "��$1");
	}

	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getName().equals(s)) {
				return all;
			}
		}
		return null;
	}

	@EventHandler
	public void PlayerDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (e.getItemDrop().getItemStack().getItemMeta() == null
				|| e.getItemDrop().getItemStack().getItemMeta().getLore() == null)
			return;

		if (e.getItemDrop().getItemStack().getItemMeta().getLore() != null) {
			for (String str : e.getItemDrop().getItemStack().getItemMeta().getLore()) {

				if (str.contains("�Ⱓ:")) {
					String[] Cut = str.split(" ");
					String[] Time = Cut[1].split("/");

					int Itemyear = Integer.parseInt(Time[0]);
					int Itemmonth = Integer.parseInt(Time[1]);
					int Itemday = Integer.parseInt(Time[2]);

					if (Itemyear < SystemYear || Itemmonth < SystemMonth) {
						e.setCancelled(true);
						p.sendMessage("��c���Ⱓ��6�� ���� �������� ��c����Ҽ� �����ϴ١�6.");
						p.playSound(p.getLocation(), Sound.ZOMBIE_METAL, 1.0F, 1.0F);
					} else if (Itemmonth == SystemMonth && Itemday < SystemDay) {
						e.setCancelled(true);
						p.sendMessage("��c���Ⱓ��6�� ���� �������� ��c����Ҽ� �����ϴ١�6.");
						p.playSound(p.getLocation(), Sound.ZOMBIE_METAL, 1.0F, 1.0F);
					}
				}
			}
		}
	}

	public void outConsole(String string) {
		getLogger().info(string);
	}

	public void outConsole(Level level, String string) {
		getLogger().log(level, string);
	}

	public void loadConfig() {
		delay2 = getConfig().getInt("delaysave");
		debug = getConfig().getBoolean("debugsave");
		getConfig().options().copyDefaults(true);
		saveConfig();
		config_itemcode = getConfig().getInt("ITEMCODE");
		config_setlore = getConfig().getBoolean("SETLORE");

	}

	private boolean setupEconomy() {
		RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}

		econ = (Economy) economyProvider.getProvider();
		return economy != null;
	}

	public void reload() {
		reloadConfig();
		loadConfig();
	}

	private static String DeleteColor(String str) {
		str = str.replaceAll("&0", ""); // BLACK("��0"),
		str = str.replaceAll("&1", ""); // DARK_BLUE("��1"),
		str = str.replaceAll("&2", ""); // DARK_GREEN("��2"),
		str = str.replaceAll("&3", ""); // DARK_AQUA("��3"),
		str = str.replaceAll("&4", ""); // DARK_RED("��4"),
		str = str.replaceAll("&5", ""); // DARK_PURPLE("��5"),
		str = str.replaceAll("&6", ""); // GOLD("��6"),
		str = str.replaceAll("&7", ""); // GRAY("��7"),
		str = str.replaceAll("&8", ""); // DARK_GRAY("��8"),
		str = str.replaceAll("&9", ""); // BLUE("��9"),
		str = str.replaceAll("&a", ""); // GREEN("��a"),
		str = str.replaceAll("&b", ""); // AQUA("��b"),
		str = str.replaceAll("&c", ""); // RED("��c"),
		str = str.replaceAll("&d", ""); // LIGHT_PURPLE("��d"),
		str = str.replaceAll("&e", ""); // YELLOW("��e"),
		str = str.replaceAll("&f", ""); // WHITE("��f"),
		str = str.replaceAll("&k", ""); // MAGIC("��k"),
		str = str.replaceAll("&l", ""); // BOLD("��l"),
		str = str.replaceAll("&m", ""); // STRIKE("��m"),
		str = str.replaceAll("&n", ""); // UNDERLINE("��n"),
		str = str.replaceAll("&o", ""); // ITALIC("��o");

		str = str.replaceAll("��0", ""); // BLACK("��0"),
		str = str.replaceAll("��1", ""); // DARK_BLUE("��1"),
		str = str.replaceAll("��2", ""); // DARK_GREEN("��2"),
		str = str.replaceAll("��3", ""); // DARK_AQUA("��3"),
		str = str.replaceAll("��4", ""); // DARK_RED("��4"),
		str = str.replaceAll("��5", ""); // DARK_PURPLE("��5"),
		str = str.replaceAll("��6", ""); // GOLD("��6"),
		str = str.replaceAll("��7", ""); // GRAY("��7"),
		str = str.replaceAll("��8", ""); // DARK_GRAY("��8"),
		str = str.replaceAll("��9", ""); // BLUE("��9"),
		str = str.replaceAll("��a", ""); // GREEN("��a"),
		str = str.replaceAll("��b", ""); // AQUA("��b"),
		str = str.replaceAll("��c", ""); // RED("��c"),
		str = str.replaceAll("��d", ""); // LIGHT_PURPLE("��d"),
		str = str.replaceAll("��e", ""); // YELLOW("��e"),
		str = str.replaceAll("��f", ""); // WHITE("��f"),
		str = str.replaceAll("��k", ""); // MAGIC("��k"),
		str = str.replaceAll("��l", ""); // BOLD("��l"),
		str = str.replaceAll("��m", ""); // STRIKE("��m"),
		str = str.replaceAll("��n", ""); // UNDERLINE("��n"),
		str = str.replaceAll("��o", ""); // ITALIC("��o");

		return str;
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		if (main.chatE) {
			if (!e.getPlayer().hasPermission("AllPlayer.admin")
					&& !me.shinkhan.userguide.API.isUserGuide(p.getName())) {
				e.setCancelled(true);
				p.sendMessage("��f[��a�ȳ���f] ��cä���� ��ݵǾ� �����Ƿ� ä���� ġ�� �� �����ϴ�.");
			}
		}

		if (mute.get(p.getName()) != null) {
			e.setCancelled(true);
			mute.put(p.getName(), null);
		}
	}

	public ItemStack setMeta(ItemStack material, String name, List<String> lore) {
		if (((material == null) || material.getType() == Material.AIR) || (name == null && lore == null))
			return null;

		ItemMeta im = material.getItemMeta();
		if (name != null)
			im.setDisplayName(name);
		if (lore != null)
			im.setLore(lore);
		material.setItemMeta(im);
		return material;
	}

	public static void sendSignChange(Player player, Sign sign) {
		if ((player == null) || (!player.isOnline()))
			return;
		if (sign == null)
			return;
		String[] lines = sign.getLines();
		PacketContainer result = protocolManager.createPacket(PacketType.Play.Server.UPDATE_SIGN);
		try {
			result.getSpecificModifier(Integer.TYPE).write(0, Integer.valueOf(sign.getX()));
			result.getSpecificModifier(Integer.TYPE).write(1, Integer.valueOf(sign.getY()));
			result.getSpecificModifier(Integer.TYPE).write(2, Integer.valueOf(sign.getZ()));
			result.getStringArrays().write(0, lines);
			protocolManager.sendServerPacket(player, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public synchronized void removeChanger(Changer changer) {
		SimpleChanger changerAdapter = (SimpleChanger) this.changers.remove(changer);
		HandlerList.unregisterAll(changerAdapter);
	}

	@Deprecated
	public synchronized void addChanger(final Changer changer) {
		if (changer == null) {
			throw new IllegalArgumentException("Changer cannot be null!");
		}

		SimpleChanger oldChangerAdapter = (SimpleChanger) this.changers.put(changer,
				new SimpleChanger(this, changer.getKey(), changer.getPerm()) {
					public String getValue(Player player, Location location, String originalLine) {
						return changer.getValue(player, location);
					}
				});
		if (oldChangerAdapter != null)
			HandlerList.unregisterAll(oldChangerAdapter);
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

	public void initCommand() {
		command = new GiveAll(null);
		getCommand("����").setExecutor(command);
	}

	class ForceRespawn implements Runnable {
		private main plugin;
		private String playerName;

		public ForceRespawn(String playerName, main plugin) {
			this.playerName = playerName;
			this.plugin = plugin;
		}

		public void run() {
			Player player = this.plugin.getServer().getPlayer(this.playerName);
			if ((player != null) && (player.isDead())) {
				Packet205ClientCommand packet = new Packet205ClientCommand();
				packet.a = 1;
				((CraftPlayer) player).getHandle().playerConnection.a(packet);
				if (this.plugin.forceRespawn.containsKey(this.playerName))
					this.plugin.forceRespawn.remove(this.playerName);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		if ((!event.isCancelled())) {
			String command = event.getMessage().split(" ")[0];
			HelpTopic htopic = Bukkit.getServer().getHelpMap().getHelpTopic(command);
			if (htopic == null) {
				p.sendMessage("�Է��Ͻ� ��ɾ�� �������� �ʽ��ϴ�. \"/����\" �� �������ּ���.");
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTrample(PlayerInteractEvent event) {

		if (event.isCancelled())
			return;

		if (event.getAction() == Action.PHYSICAL) {
			Block block = event.getClickedBlock();
			if (block == null)
				return;
			int blockType = block.getTypeId();

			if (blockType == Material.getMaterial(59).getId()) {
				event.setCancelled(true);

				block.setTypeId(blockType);
				block.setData(block.getData());
			}

		}

		if (event.getAction() == Action.PHYSICAL) {
			Block block = event.getClickedBlock();

			if (block == null) {
				return;
			}
			int blockType = block.getTypeId();

			if (blockType == Material.getMaterial(60).getId()) {
				event.setCancelled(true);

				block.setType(Material.getMaterial(60));
				block.setData(block.getData());
			}
		}
	}

	private void firstRun() throws Exception {
		if (!this.configFile.exists()) {
			this.configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), this.configFile);
		}
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadYamls() {
		try {
			main.config.load(main.configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerHealth(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)) {
			if ((main.healthE) && ((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Player))) {
				e.setCancelled(true);
				return;
			}
		}

		Entity damager = e.getDamager();

		if ((damager instanceof Player)) {
			Player p = (Player) damager;
			Entity entity = e.getEntity();

			if ((!e.isCancelled()) && ((e.getEntity() instanceof LivingEntity))) {
				int i = 121;
				if ((e.getEntity() instanceof Player))
					i = 55;
				if (me.espoo.socket.main.cret.get(p.getName()) != null)
					i = 152;
				e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.STEP_SOUND, i);
			}

			if (!(entity instanceof LivingEntity))
				return;
			LivingEntity victim = (LivingEntity) entity;

			if (victim.getNoDamageTicks() > p.getMaximumNoDamageTicks() / 2.0F)
				return;

			if (me.espoo.socket.main.cret.get(p.getName()) != null) {
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����"))
					damager.getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 1.0F, 1.0F);
				me.espoo.socket.main.cret.remove(p.getName());
			} else {
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����"))
					damager.getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 0.8F, 2.0F);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player pl = event.getPlayer();
		Block item = event.getBlock();
		int id = item.getType().getId();

		if (!pl.isOp()) {
			if (id == 7 || id == 15 || id == 14 || id == 103 || id == 86 || id == 46) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Action act = e.getAction();
		Player p = e.getPlayer();
		String pname = p.getName();
		if ((act == Action.RIGHT_CLICK_AIR) || (act == Action.RIGHT_CLICK_BLOCK)) {
			if ((e.getItem() != null) && (e.getItem().getItemMeta().getDisplayName() != null)
					&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("��e��a��c��6��o ��ǥ")
					&& p.getItemInHand().getTypeId() == config_itemcode) {
				ItemStack pi = p.getItemInHand();
				int ic = pi.getTypeId();
				ItemMeta pitem = pi.getItemMeta();
				int won = Integer.parseInt((String) pitem.getLore().get(1));
				if (pitem.getLore() != null) {
					economy.depositPlayer(pname, won);
					p.sendMessage("��c��ǥ��6�� ����Ͽ� ��c" + won + " ��6���� ȹ���Ͽ����ϴ�.");
					int amount = pi.getAmount();
					if (amount == 1)
						p.getInventory().removeItem(new ItemStack[] { pi });
					else
						pi.setAmount(amount - 1);
				}
			}
		}
	}

	public List<String> addWorlds() {
		List worldList = new ArrayList();
		for (World w : getServer().getWorlds()) {
			worldList.add(w.getName());
		}
		return worldList;
	}

	public void reloadChances() {
		this.stone = (float) getConfig().getDouble("Chances.Stone");
		this.coal = ((float) getConfig().getDouble("Chances.Coal") + this.stone);
		this.iron = ((float) getConfig().getDouble("Chances.Iron") + this.coal);
		this.gold = ((float) getConfig().getDouble("Chances.Gold") + this.iron);
		this.redstone = ((float) getConfig().getDouble("Chances.Redstone") + this.gold);
		this.lapis = ((float) getConfig().getDouble("Chances.Lapis") + this.redstone);
		this.emerald = ((float) getConfig().getDouble("Chances.Emerald") + this.lapis);
		this.diamond = ((float) getConfig().getDouble("Chances.Diamond") + this.emerald);
	}

	@EventHandler
	public void onPlayerStop(PlayerMoveEvent e) {
		if (main.stopE) {
			if (!e.getPlayer().hasPermission("AllPlayer.admin")) {
				e.getPlayer().teleport(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent e) {
		if (main.levelE) {
			if (e.getPlayer().getLevel() >= main.levelF) {
				e.getPlayer().setLevel(main.levelF);
			}
		}
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
				if (ps != null) main.this.h(ps);
			}
		}, 12L);
	}

	public void h(final Player p) {
		final int hun = main.hungry.get(p);
		p.setFoodLevel(hun);
	}

	private static Enchantment getEnchantment(String query) {
		return (Enchantment) main.enchantmentNames.get(query.toLowerCase());
	}

	public static enum EnchantmentResult {
		INVALID_ID, CANNOT_ENCHANT, VICIOUS_STREAK_A_MILE_WIDE;
	}

	public static void help(CommandSender sender) {
		sender.sendMessage("��e===================== ����  =====================");
		sender.sendMessage("��6/tppod <x> <y> <z> ��f- x, y, z ��ġ�� �̵��մϴ�.");
		sender.sendMessage("��6/f t <�г���> <�����̸�> <x> <y> <z> <yaw> <pitch> ��f- �÷��̾ �ش� ��ǥ�� �̵���ŵ�ϴ�.");
		sender.sendMessage("��6/f t <�г���> <�����̸�> <x> <y> <z> ��f- �÷��̾ �ش� ��ǥ�� �̵���ŵ�ϴ�.");
		sender.sendMessage("��6/f tp <�̵���ų�г���> <��ǥ�г���> ��f- �̵���ų �г����� ��ǥ �г������� �̵���ŵ�ϴ�.");
		sender.sendMessage("��6/f s <�̵���ų�г���> ��f- �÷��̾ �ڽ��� ��ġ�� �̵���ŵ�ϴ�.");
		sender.sendMessage("��6/f tpall <��ǥ�г���> ��f- ��� �÷��̾ ��ǥ �г��� ��ġ�� �̵���ŵ�ϴ�. ���� ���� ��� �ڽ��� ��ġ�� �̵���ŵ�ϴ�.");
		sender.sendMessage("��e================================================");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onServerListPing(ServerListPingEvent event) {
		event.setMotd(this.Motd.replaceAll("(&([a-r0-9]))", "��$2"));
	}

	public long freeMem() {
		return Runtime.getRuntime().freeMemory() / 1024L / 1024L;
	}

	public long maxMem() {
		return Runtime.getRuntime().maxMemory() / 1024L / 1024L;
	}

	public long totalMem() {
		return Runtime.getRuntime().totalMemory() / 1024L / 1024L;
	}

	@EventHandler
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent e) {
		Player p = e.getPlayer();
		if ((!p.hasPermission("anticheat.gamemode")) && (e.getNewGameMode() != GameMode.SURVIVAL)) {
			e.setCancelled(true);
			p.sendMessage("��f[��4����f] ��c����� ������ �����Ƿ� ���Ӹ�尡 �Ұ��� �մϴ�.");
		}
	}

	public CommandManager getCm() {
		return cm;
	}

	public void setCm(CommandManager cm) {
		this.cm = cm;
	}

	class Cooldown extends BukkitRunnable {
		Player player;
		HashMap<String, Integer> hashmap;

		public Cooldown(HashMap<String, Integer> map) {
			this.player = player;
			this.hashmap = map;
		}

		public void run() {
			int time = ((Integer) this.hashmap.get(this.player.getName())).intValue();
			if (time != 0) {
				this.hashmap.put(this.player.getName(), Integer.valueOf(time - 1));
			} else {
				this.hashmap.remove(this.player.getName());
				Bukkit.getServer().getScheduler().cancelTask(getTaskId());
			}
		}
	}
}

class Runner implements Runnable {
	Player play;
	int line;
	String message;
	Block b;

	public Runner(Player player, int l, String m, Block block) {
		this.play = player;
		this.line = l;
		this.message = m;
		this.b = block;
	}

	public void run() {
		try {
			Sign s = (Sign) this.b.getState();
			if (!this.play.hasPermission("se.editAny")) {
				if (main.logblock == null) {
					return;
				}

				QueryParams params = new QueryParams(main.logblock);
				params.bct = QueryParams.BlockChangeType.CREATED;
				params.limit = 1;
				params.needPlayer = true;
				params.loc = this.b.getLocation();
				params.needSignText = true;
				List changes = main.logblock.getBlockChanges(params);
				if ((changes == null) || (changes.size() == 0)) {
					this.play.sendMessage("��cLogBlock�� ����� ���ڵ带 �������� �ʽ��ϴ�.");
					return;
				}
				BlockChange bc = (BlockChange) changes.get(0);
				if (bc.playerName != this.play.getName()) {
					return;
				}

				String[] message = bc.signtext.split("");
				for (int i = 0; i < 4; i++) {
					if (message[i] != s.getLine(i)) {
						return;
					}
				}
			}
			if (main.lbconsumer != null)
				main.lbconsumer.queueSignBreak(this.play.getName(), s);

			String[] lines = (String[]) s.getLines().clone();
			String tline = lines[this.line];
			lines[this.line] = this.message;
			s.setLine(this.line, this.message);
			s.update();
			SignChangeEvent e = new SignChangeEvent(this.b, this.play, lines);
			Bukkit.getPluginManager().callEvent(e);
			if (!e.isCancelled()) {
				s.setLine(this.line, this.message);
			}

			else {
				s.setLine(this.line, tline);
			}
			s.update();
		}

		catch (Exception e) {
		}
	}

}
