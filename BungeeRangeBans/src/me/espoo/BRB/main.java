package me.espoo.BRB;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class main extends JavaPlugin implements PluginMessageListener, Listener {
	Logger log = Logger.getLogger("Minecraft");
	public static String file = "C:/Users/Administrator/Desktop/Ban.yml";
    static final Map <String, String> player = new HashMap<>();
    public static main Main;
	
	public void onEnable()
	{
	    String readFilePath = file;
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Config.CreateConfig(f, config);
		}
		
		Main = this;
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

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

	@EventHandler
	public void onEvent(PlayerJoinEvent event)
	{
		new SchedulerRun(this, event.getPlayer()).runTaskLater(this, 7L);
	}

	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();

		if ((channel.equals("BungeeCord")) && (subchannel.equals("IP")))
		{
			String ip = in.readUTF();
			if (main.player.containsKey(player.getName())) main.player.remove(player.getName());
			main.player.put(player.getName(), ip);
		}
	}
}
