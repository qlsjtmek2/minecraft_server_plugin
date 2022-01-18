package me.shinkhan.BarHealth;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
	static final Map<String, Integer> Timer = new HashMap<String, Integer>();
	public static boolean isEpicBoss = false;
	public static String barName;
	
	public void onEnable()
	{
		PluginManager pm = Bukkit.getPluginManager();
		Plugin pl = pm.getPlugin("EpicBossRecoded");
		if (pl != null) {
			isEpicBoss = true;
			System.out.println("[DHBarHealth] 에픽보스 호환 버전 구동 완료되었습니다.");
		} else {
			System.out.println("[DHBarHealth] 에픽보스 호환 버전 구동이 잘못되었습니다.");
			System.out.println("[DHBarHealth] 에픽보스 플러그인을 넣거나, 에픽보스 미적용 버전을 받아주시기 바랍니다.");
			isEpicBoss = false;
		}
		
		File folder = new File("plugins/DHBarHealth");
		File F = new File("plugins/DHBarHealth/config.yml");
		YamlConfiguration C = YamlConfiguration.loadConfiguration(F);
		if (!F.exists()) Config.CreateConfig(F, folder, C);
		
		barName = Config.getString("보스바 표시");
		
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("barcheck")) {
				sender.sendMessage("§6현재 이 서버에는 §cDHBarHealth §6플러그인이 적용되어 있습니다.");
				sender.sendMessage("§6제작자 :: §cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("§cDHBarHealth §6플러그인을 사용해주셔서 감사합니다.  §f- shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6현재 이 서버에는 §cDHBarHealth §6플러그인이 적용되어 있습니다.");
			sender.sendMessage("§6제작자 :: §cshinkhan");
		} return false;
	}
}
