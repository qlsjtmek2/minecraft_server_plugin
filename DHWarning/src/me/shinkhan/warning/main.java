package me.shinkhan.warning;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class main extends JavaPlugin implements Listener {
	public void onEnable()
	{
		File folder = new File("plugins/DHWarning/Player");
		if (!folder.isDirectory()) folder.mkdirs();
		
		getCommand("경고").setExecutor(new mainCommand(this));
		getCommand("wa").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(this, this);
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
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (API.isWarning(p.getName())) {
			String[] str = e.getFormat().split(" ");
			StringBuilder message = new StringBuilder();
			int num = 0;
			for (String st : str) {
				if (ChatColor.stripColor(st).equalsIgnoreCase("%1$s")) {
					str[num] = str[num] + "§4" + API.getWarning(p.getName());
					message.append(str[num]);
					if (str.length != (num - 1))
						message.append(" ");
					num++;
				} else {
					message.append(str[num]);
					if (str.length != (num - 1))
						message.append(" ");
					num++;
				}
			}
			
			e.setFormat(message.toString());
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/DHWarning");
		File folder2 = new File("plugins/DHWarning/Player");
		File f = new File("plugins/DHWarning/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
		PlayerYml.setString(p.getName(), "IP", API.cleanIP(p.getAddress().toString()));
	}
}