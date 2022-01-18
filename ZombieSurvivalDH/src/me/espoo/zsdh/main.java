package me.espoo.zsdh;

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
	static final Map <String, Integer> taskIds = new HashMap<>();
	public static HashMap<String, String> stoppvp = new HashMap<String, String>();
	public static HashMap<String, String> Gun = new HashMap<String, String>();
    public static ArrayList<Player> spam = new ArrayList<Player>();
    public static ArrayList<Player> Zombie = new ArrayList<Player>();
	public static Permission permission = null;
	public static int sTimer = 120;
	public static String prx = "§f[§4알림§f] ";
	public static String aprx = "§f[§a안내§f] ";
	public static int StartTime = 30;
	public static int Time = 4;
	
	public void onEnable()
    {
		File sf = new File("plugins/ZombieSurvivalDH/StartList.yml");
		File mf = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File maf = new File("plugins/ZombieSurvivalDH/MapList.yml");
		File rf = new File("plugins/ZombieSurvivalDH/Ranking.yml");
		File tf = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		YamlConfiguration sconfig = YamlConfiguration.loadConfiguration(sf);
		YamlConfiguration mconfig = YamlConfiguration.loadConfiguration(mf);
		YamlConfiguration maconfig = YamlConfiguration.loadConfiguration(mf);
		YamlConfiguration rconfig = YamlConfiguration.loadConfiguration(rf);
		YamlConfiguration tconfig = YamlConfiguration.loadConfiguration(tf);
		File folder = new File("plugins/ZombieSurvivalDH");
		if (!sf.exists()) Method.CreateStartList(sf, folder, sconfig);
		if (!mf.exists()) Method.CreateMainconfig(mf, folder, mconfig);
		if (!maf.exists()) Method.CreateMapList(maf, folder, maconfig);
		if (!rf.exists()) Method.CreateRanking(rf, folder, rconfig);
		if (!tf.exists()) Method.CreateRanking(tf, folder, tconfig);
		Method.delTeamList();
		Method.setConfigBoolean("Start", false);
		Method.setConfigString("WarpName", "NONE");
		Method.setConfigBoolean("Timersup", false);
		Method.delStartList();
		getCommand("zs").setExecutor(new mainCommand(this));
		getCommand("좀비").setExecutor(new mainCommand(this));
		getCommand("총").setExecutor(new mainCommand(this));
		
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        permission = permissionProvider.getProvider();
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
				if (Method.getConfigBoolean("Timersup") == false) {
	        		if (Method.getConfigBoolean("Start") == false) {
	            		for (Player p : Bukkit.getOnlinePlayers()) {
	            			p.getWorld().setStorm(false);
	            			p.getWorld().setThundering(false);
	            			p.getWorld().setTime(846000);
	            		}
	            		
	        			sTimer -= 10;
	        			Integer id = main.taskIds.remove("StartTimer");
	        			if (id != null && Method.getConfigString("WarpName").equals("NONE")) Bukkit.getServer().getScheduler().cancelTask(id);
	        			if (sTimer == 90) Bukkit.broadcastMessage(prx + "§6현재 §c좀비 서바이벌 §6시작 카운트가 §c1분 30초 §6남았습니다.");
	        			else if (sTimer == 60) Bukkit.broadcastMessage(prx + "§6현재 §c좀비 서바이벌 §6시작 카운트가 §c1분 §6남았습니다.");
	        			else if (sTimer == 30) Bukkit.broadcastMessage(prx + "§6현재 §c좀비 서바이벌 §6시작 카운트가 §c30초 §6남았습니다.");
	        			else if (sTimer == 20) Bukkit.broadcastMessage(prx + "§6현재 §c좀비 서바이벌 §6시작 카운트가 §c20초 §6남았습니다.");
	        			else if (sTimer == 10) Bukkit.broadcastMessage(prx + "§6현재 §c좀비 서바이벌 §6시작 카운트가 §c10초 §6남았습니다.");
	        			else if (sTimer <= 0) {
	        				File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
	        				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
	        	    		List<String> list = config.getStringList("Player");
	        				if (list.size() > 2) {
	        					Method.Starting();
	        					sTimer = 120;
	        					return;
	        				} else {
	        					Bukkit.broadcastMessage(prx + "§c인원 수가 부족하여 카운팅을 다시 시작합니다.");
	        					sTimer = 120;
	        				}
	        			}
	        		} else {
	            		for (Player p : Bukkit.getOnlinePlayers()) {
	            			p.getWorld().setStorm(false);
	            			p.getWorld().setThundering(false);
	            			p.getWorld().setTime(999999L);
	            		}
	        		}
				} else {
            		for (Player p : Bukkit.getOnlinePlayers()) {
            			p.getWorld().setStorm(false);
            			p.getWorld().setThundering(false);
            			p.getWorld().setTime(846000);
            		}
    			}
			}
		}, 200L, 200L);
		
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
    }
	
	public void onDisable()
	{
		Method.delTeamList();
		Method.setConfigBoolean("Start", false);
		Method.setConfigString("WarpName", "NONE");
		Method.delStartList();
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
}
