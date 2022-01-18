package me.espoo.banip;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class main extends JavaPlugin implements PluginMessageListener, Listener {
    
	public void onEnable()
	{
	    String readFilePath = "C:/Users/신희곤/Desktop/BanList.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreateConfig(f, config);
		
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (sender.isOp()) {
				if (commandLabel.equals("bb")) {
					if (args.length > 1) {
						if (args[0].equals("add")) {
							if (main.al.contains(args[1])) {
								sender.sendMessage(ChatColor.RED + args[1] + " IP는 이미 밴돼어있습니다.");
								return false;
							}
							
							else if (isIPv4Address(args[1])) {
								main.al.add(args[1]);

								Bukkit.broadcastMessage(ChatColor.GREEN + args[1] + " IP를 밴하였습니다.");
								return false;
							} else {
								sender.sendMessage("IP 형식이 틀립니다.");
								return false;
							}
						}
						
						else if (args[0].equals("remove")) {
							if (isIPv4Address(args[1])) {
								if (!main.al.contains(args[1])) {
									sender.sendMessage(ChatColor.RED + " IP가 존재하지 않습니다.");
									return false;
								} else {
									main.al.remove(args[1]);
									Bukkit.broadcastMessage(ChatColor.GREEN + args[1] + " IP를 밴을 제거하였습니다.");
									return false;
								}
							} else {
								sender.sendMessage(ChatColor.RED + "IP 형식이 틀립니다.");
								return false;
							}
						} else {
							sender.sendMessage(ChatColor.GOLD + "/bb add <닉네임> " + ChatColor.WHITE + "- 플레이어를 아이피밴 합니다.");
							sender.sendMessage(ChatColor.GOLD + "/bb del <닉네임> " + ChatColor.WHITE + "- 플레이어 아이피밴을 해제합니다.");
							return false;
						}

					} else {
						sender.sendMessage(ChatColor.RED + "IP를 입력해주세요");
						return false;
					}
				}
		    } else {
				sender.sendMessage(ChatColor.RED + "당신은 권한이 없습니다.");
				return false;
			} return false;
		} catch (NumberFormatException ex) {
			sender.sendMessage(ChatColor.GOLD + "/bb add <닉네임> " + ChatColor.WHITE + "- 플레이어를 아이피밴 합니다.");
			sender.sendMessage(ChatColor.GOLD + "/bb del <닉네임> " + ChatColor.WHITE + "- 플레이어 아이피밴을 해제합니다.");
			return false;
		} return false;
	}
	
	@EventHandler
	public void onEvent(PlayerChannelEvent event) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

	    out.writeUTF("IP");

	    event.getPlayer().sendPluginMessage(this, "BungeeCord", out.toByteArray());

	    BukkitTask task = new BungeeRun(event, al, this.plip, this.plni).runTaskLaterAsynchronously(this, 7L);
	}

	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    String subchannel = in.readUTF();

	    if ((channel.equals("BungeeCord")) && (subchannel.equals("IP")))
	    {
	    	String ip = in.readUTF();
	    	int port = in.readInt();

	    	this.plip.add(ip);
	    	this.plni.add(player.getName());
	    	
	    	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "BungeeCord Join : " + player.getName() + "[" + ip + ":" + port + "]");
	    }
	}
}
