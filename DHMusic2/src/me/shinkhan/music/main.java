package me.shinkhan.music;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public static HashMap<Player, String> Type = new HashMap<Player, String>();
	public static HashMap<Player, Integer> Song = new HashMap<Player, Integer>();
	private static main instance = null;
	public static String var = "1.3";
	
	public void onEnable()
	{
		GUIMessage.setGUIMessage();
		File folder = new File("plugins/DHMusic");
		File F = new File("plugins/DHMusic/config.yml");
		YamlConfiguration C = YamlConfiguration.loadConfiguration(F);
		if (!F.exists()) Config.CreateConfig(F, folder, C);
		if (!folder.isDirectory())
			folder.mkdirs();
		instance = this;
		
		getCommand("음악").setExecutor(new Comand(this));
		getCommand("musiccheck").setExecutor(new Comand(this));
		getServer().getPluginManager().registerEvents(new Event(this), this);
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

	public static main getInstance()
	{
		return instance;
	}
}
