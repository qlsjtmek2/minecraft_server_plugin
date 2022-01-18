package me.espoo.dunzeon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class main extends JavaPlugin implements Listener {
	public static HashMap<String, Integer> PartyCheck = new HashMap<String, Integer>();
	public static HashMap<String, Integer> CoolTime = new HashMap<String, Integer>();
    static final Map <String, Integer> Timer = new HashMap<>();
    public static List<Player> pList = new ArrayList<Player>();
    public static List<Player> onePosition = new ArrayList<Player>();
    public static List<Player> twoPosition = new ArrayList<Player>();
	public static Permission permission = null;
	public static boolean Round = false;
	public static int oneRound = -1;
	public static int twoRound = -1;
	public static int threeRound = -1;
	public static int fourRound = -1;
	
	public void onEnable()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		permission = (Permission) permissionProvider.getProvider();
		
		File f = new File("plugins/OnePunchDunzeon/Config.yml");
		File folder = new File("plugins/OnePunchDunzeon");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Config.CreateConfig(f, folder, c);
		
		getCommand("괴인협회입장").setExecutor(new mainCommand(this));
		getCommand("괴인협회위치").setExecutor(new mainCommand(this));
		getCommand("변수빼기").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}

}
