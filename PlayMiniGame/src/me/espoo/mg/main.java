package me.espoo.mg;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.mg.Data.GameData;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener {
	public static main miniGames;
	public static String p = "§f[§4알림§f] ";
	public static String a = "§f[§a안내§f] ";
	public static String w = "§f[§c경고§f] ";
	public static Economy economy = null;
	
	public void onEnable()
	{
		File f = new File("plugins/PlayMiniGame/config.yml");
		File rf = new File("plugins/PlayMiniGame/Ranking.yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/schematics");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		YamlConfiguration rconfig = YamlConfiguration.loadConfiguration(rf);
		
		if (!f.exists()) Method.CreateConfig(f, folder, config);
		if (!rf.exists()) Method.CreateRanking(rf, folder, rconfig);
		if (!folder2.exists()) Method.CreateSchemFolder(folder, folder2);
       	
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
	    miniGames = this;
	    Method.SetMap();
		getCommand("미니게임").setExecutor(new mainCommand(this));
		getCommand("점수").setExecutor(new mainCommand(this));
		getCommand("스코어").setExecutor(new mainCommand(this));
		getCommand("wjatn").setExecutor(new mainCommand(this));
		getCommand("tmzhdj").setExecutor(new mainCommand(this));
		getCommand("score").setExecutor(new mainCommand(this));
		getCommand("MG").setExecutor(new mainCommand(this));
		getCommand("MiniGame").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				if (GameData.gameState) new GameData().check();
			}
		}, 20L, 20L);
		
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 p.getWorld().setStorm(false);
					 p.getWorld().setThundering(false);
					 p.getWorld().setTime(846000);
				 }
			 }
		 }, 60L, 60L);
		 
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 Bukkit.broadcastMessage(main.p + "§6접속 유지 보상으로 §c500 §6원 을 §c획득§6하셨습니다.");
				 Bukkit.broadcastMessage(main.p + "§c플러그인§6은 §c린마루 §6님의 플러그인을 보고 만들었음을 밝힙니다.");
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 Method.addMoneyNo(p, 500);
				 }
			 }
		 }, 10000L, 10000L);
	    
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		if (GameData.gameState) GameData.GameStop();
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
}
