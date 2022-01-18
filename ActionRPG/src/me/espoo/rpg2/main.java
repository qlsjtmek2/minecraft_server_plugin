package me.espoo.rpg2;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class main extends JavaPlugin implements Listener {
    static final Map <String, Integer> shop = new HashMap<>();
    static final Map <String, String> buyme = new HashMap<>();
    static final Map <String, String> buyto = new HashMap<>();
    static final Map <String, Integer> buytype = new HashMap<>();
	static final Map <String, String> in = new HashMap<>();
	static final Map <String, String> gp = new HashMap<>();
	public static HashMap<String, String> Megaphone = new HashMap<String, String>();
	public static HashMap<String, String> EMegaphone = new HashMap<String, String>();
	public static HashMap<String, Integer> KillMessage = new HashMap<String, Integer>();
	public static HashMap<String, String> Plant = new HashMap<String, String>();
    public static ArrayList<Player> manaPotionSpem = new ArrayList<Player>();
    static final Map <String, Integer> manaPotionCool = new HashMap<>();
	public static HashMap<String, Integer> manaPotionTime = new HashMap<String, Integer>();
    public static ArrayList<Player> PotionSpem = new ArrayList<Player>();
    static final Map <String, Integer> PotionCool = new HashMap<>();
	public static HashMap<String, Integer> PotionTime = new HashMap<String, Integer>();
	public static HashMap<String, Integer> DunTime = new HashMap<String, Integer>();
    static final Map <String, Integer> DunCool = new HashMap<>();
	public static HashMap<String, Integer> SuicideTime = new HashMap<String, Integer>();
    static final Map <String, Integer> SuicideCool = new HashMap<>();
    static final Map <String, Integer> Lvup = new HashMap<>();
    static final Map <String, Integer> Plants = new HashMap<>();
    static final Map <String, Integer> TTla1 = new HashMap<>();
    static final Map <String, Integer> TTla2 = new HashMap<>();
    static final Map <String, Integer> TTla3 = new HashMap<>();
    static final Map <String, Integer> Zunzec = new HashMap<>();
	public static HashMap<Player, Location> AFK = new HashMap<Player, Location>();
    public static Economy economy = null;
	public static Timer timer = new Timer();
	public static int bdssMain = 0;
	public static int bdssPoint = 0;
	static LogBlock logblock = null;
	static Consumer lbconsumer = null;
	public static String[] cantuse = null;
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "�˸�" + ChatColor.WHITE + "] ";
	public static Permission permission = null;
	public static List<String> OPlist = new ArrayList<String>();
	public static boolean BDL = false;
	public static boolean UDD = false;
	public static boolean WD = false;
	public static boolean BD = false;
	public static boolean SKLT = false;
	
	public void onEnable()
    {
		File f = new File("plugins/ActionRPG/config.yml");
		File cf = new File("plugins/ActionRPG/OpList.yml");
		File fi = new File("plugins/ActionRPG/Itemname.yml");
		File df = new File("plugins/ActionRPG/Dunzeon.yml");
		File folder = new File("plugins/ActionRPG");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		YamlConfiguration configi = YamlConfiguration.loadConfiguration(fi);
		YamlConfiguration cconfig = YamlConfiguration.loadConfiguration(cf);
		YamlConfiguration dconfig = YamlConfiguration.loadConfiguration(df);
		
		OPlist = Method.getOpList();
		
		if (!f.exists()) Method.CreateConfig(f, folder, config);
		if (!fi.exists()) Config.CreateConfig(fi, folder, configi);
		if (!cf.exists()) Method.CreateOpList(cf, folder, cconfig);
		if (!df.exists()) Dunzeon.CreateConfig(df, folder, dconfig);
		
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		permission = (Permission) permissionProvider.getProvider();
		Method.setConfigBoolean("���ε�", false);
        main.logblock = ((LogBlock)getServer().getPluginManager().getPlugin("LogBlock"));
        if (main.logblock != null) main.lbconsumer = main.logblock.getConsumer();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        		for (Player p : Bukkit.getOnlinePlayers()) {
        			if (p.getWorld().getName().contains("world_dun")) {
        				if (AFK.containsKey(p)) {
        					if (isSimilar(p.getLocation(), AFK.get(p))) {
        						AFK.remove(p);
        						p.chat("/spawn");
        						p.sendMessage("��c����� �������� ����� �Ͽ��� �ڵ����� �������� �̵��Ǿ����ϴ�.");
        						continue;
        					} else {
            					AFK.put(p, p.getLocation());
            					continue;
        					}
        				} else {
        					AFK.put(p, p.getLocation());
        					continue;
        				}
        			} else {
        				if (AFK.containsKey(p)) AFK.remove(p);
        			}
        		}
			}
		}, 600L, 600L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        		for (Player p : Bukkit.getOnlinePlayers()) {
        			if (p.isOp()) {
        				if (!OPlist.contains(p.getName())) {
        					System.out.println(p.getName() + "���� ������ ���� ���Ǹ� ������ �־ �����߽��ϴ�.");
        					if (p.isOnline()) p.setOp(false);
        				}
        			}
        		}
			}
		}, 100L, 100L);

		getCommand("dropscript").setExecutor(new mainCommand2(this));
		getCommand("�����Ȯ��").setExecutor(new mainCommand2(this));
		getCommand("�𵥵�Ȯ��").setExecutor(new mainCommand2(this));
		getCommand("����Ȯ��").setExecutor(new mainCommand2(this));
		getCommand("�ε�Ȯ��").setExecutor(new mainCommand2(this));
		getCommand("���̷���Ȯ��").setExecutor(new mainCommand2(this));
		getCommand("��������").setExecutor(new mainCommand2(this));
		getCommand("�Ӹ�").setExecutor(new mainCommand2(this));
		getCommand("nbth").setExecutor(new mainCommand2(this));
		getCommand("in").setExecutor(new mainCommand2(this));
		getCommand("gp").setExecutor(new mainCommand2(this));
		getCommand("���Ǳ���").setExecutor(new mainCommand2(this));
		getCommand("��").setExecutor(new mainCommand(this));
		getCommand("AR").setExecutor(new mainCommand(this));
		getCommand("�׾�").setExecutor(new mainCommand(this));
		getCommand("ARPG").setExecutor(new mainCommand(this));
		getCommand("�׼Ǿ�����").setExecutor(new mainCommand(this));
		getCommand("Ȯ����").setExecutor(new mainCommand(this));
		getCommand("�̺���").setExecutor(new mainCommand(this));
		getCommand("ǥ����").setExecutor(new mainCommand2(this));
		getCommand("Ʃ�丮��ħ").setExecutor(new mainCommand(this));
		getCommand("��������GUI").setExecutor(new mainCommand(this));
		getCommand("�ص��˰���").setExecutor(new mainCommand(this));
		getCommand("���Ǿ˰���1").setExecutor(new mainCommand(this));
		getCommand("���Ǿ˰���2").setExecutor(new mainCommand(this));
		getCommand("���Ǿ˰���3").setExecutor(new mainCommand(this));
		getCommand("���Ǿ˰���4").setExecutor(new mainCommand(this));
		getCommand("���Ǿ˰���5").setExecutor(new mainCommand(this));
		getCommand("�������Ǿ˰���1").setExecutor(new mainCommand(this));
		getCommand("�������Ǿ˰���2").setExecutor(new mainCommand(this));
		getCommand("�������Ǿ˰���3").setExecutor(new mainCommand(this));
		getCommand("�������Ǿ˰���4").setExecutor(new mainCommand(this));
		getCommand("�������Ǿ˰���5").setExecutor(new mainCommand(this));
		getCommand("��������������").setExecutor(new mainCommand(this));
		getCommand("��������������").setExecutor(new mainCommand(this));
		getCommand("����1").setExecutor(new mainCommand(this));
		getCommand("����10").setExecutor(new mainCommand(this));
		getCommand("����40").setExecutor(new mainCommand(this));
		getCommand("����60").setExecutor(new mainCommand(this));
		getCommand("�ڽ����Ĺ����𵥵�").setExecutor(new mainCommand(this));
		getCommand("�ڽ������").setExecutor(new mainCommand(this));
		getCommand("�ڽ������ѽ��̷���").setExecutor(new mainCommand(this));
		getCommand("�ڽ���ȭ������").setExecutor(new mainCommand(this));
		getCommand("�ڽ���������").setExecutor(new mainCommand(this));
		getCommand("�ڽ�������ȭ�ֹ���").setExecutor(new mainCommand(this));
		getCommand("�ڽ�ü������").setExecutor(new mainCommand(this));
		getCommand("�ڽ���������").setExecutor(new mainCommand(this));
		getCommand("�ڽ��εμ����ǻ�����").setExecutor(new mainCommand(this));
		getCommand("�ڽ�����").setExecutor(new mainCommand(this));
		getCommand("�ڽ�������").setExecutor(new mainCommand(this));
		getCommand("1�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("2�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("3�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("4�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("5�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("6�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("7�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("8�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("9�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("10�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("11�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("12�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("13�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("14�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("15�ܰ���ť��").setExecutor(new mainCommand(this));
		getCommand("LvUP").setExecutor(new mainCommand(this));
		getCommand("���ǽ���").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("ī����").setExecutor(new mainCommand(this));
		getCommand("�߰�����").setExecutor(new mainCommand(this));
		getCommand("��������").setExecutor(new mainCommand2(this));
		getCommand("�̵�����").setExecutor(new mainCommand(this));
		getCommand("��������").setExecutor(new mainCommand(this));
		getCommand("��������").setExecutor(new mainCommand(this));
		getCommand("�εμ������").setExecutor(new mainCommand(this));
		getCommand("�����ʱ�ȭ").setExecutor(new mainCommand(this));
		getCommand("��������").setExecutor(new mainCommand(this));
		getCommand("����Ż").setExecutor(new mainCommand(this));
		getCommand("�ڻ�").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        		Calendar oCalendar = Calendar.getInstance();
        		if (oCalendar.get(Calendar.HOUR_OF_DAY) == 23  && oCalendar.get(Calendar.MINUTE) == 59 && oCalendar.get(Calendar.SECOND) >= 0) {
					getServer().dispatchCommand(getServer().getConsoleSender(), "save");
					getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
					getServer().dispatchCommand(getServer().getConsoleSender(), "backup");
					getServer().dispatchCommand(getServer().getConsoleSender(), "asw save");
					getServer().dispatchCommand(getServer().getConsoleSender(), "o");
        		}
        	}
        }, 200L, 200L);
		
		PluginDescriptionFile pdFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
    }

	public void onDisable()
	{
		Method.setConfigBoolean("���ε�", false);
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
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

	public static boolean isSimilar(Location l1, Location l2)  
	{
		return l1.getX() == l2.getX() && l1.getZ() == l2.getZ() ? true : false;
	}
}
