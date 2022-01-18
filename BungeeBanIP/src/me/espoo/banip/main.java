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
	    String readFilePath = "C:/Users/�����/Desktop/BanList.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreateConfig(f, config);
		
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (sender.isOp()) {
				if (commandLabel.equals("bb")) {
					if (args.length > 1) {
						if (args[0].equals("add")) {
							if (main.al.contains(args[1])) {
								sender.sendMessage(ChatColor.RED + args[1] + " IP�� �̹� ��ž��ֽ��ϴ�.");
								return false;
							}
							
							else if (isIPv4Address(args[1])) {
								main.al.add(args[1]);

								Bukkit.broadcastMessage(ChatColor.GREEN + args[1] + " IP�� ���Ͽ����ϴ�.");
								return false;
							} else {
								sender.sendMessage("IP ������ Ʋ���ϴ�.");
								return false;
							}
						}
						
						else if (args[0].equals("remove")) {
							if (isIPv4Address(args[1])) {
								if (!main.al.contains(args[1])) {
									sender.sendMessage(ChatColor.RED + " IP�� �������� �ʽ��ϴ�.");
									return false;
								} else {
									main.al.remove(args[1]);
									Bukkit.broadcastMessage(ChatColor.GREEN + args[1] + " IP�� ���� �����Ͽ����ϴ�.");
									return false;
								}
							} else {
								sender.sendMessage(ChatColor.RED + "IP ������ Ʋ���ϴ�.");
								return false;
							}
						} else {
							sender.sendMessage(ChatColor.GOLD + "/bb add <�г���> " + ChatColor.WHITE + "- �÷��̾ �����ǹ� �մϴ�.");
							sender.sendMessage(ChatColor.GOLD + "/bb del <�г���> " + ChatColor.WHITE + "- �÷��̾� �����ǹ��� �����մϴ�.");
							return false;
						}

					} else {
						sender.sendMessage(ChatColor.RED + "IP�� �Է����ּ���");
						return false;
					}
				}
		    } else {
				sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
				return false;
			} return false;
		} catch (NumberFormatException ex) {
			sender.sendMessage(ChatColor.GOLD + "/bb add <�г���> " + ChatColor.WHITE + "- �÷��̾ �����ǹ� �մϴ�.");
			sender.sendMessage(ChatColor.GOLD + "/bb del <�г���> " + ChatColor.WHITE + "- �÷��̾� �����ǹ��� �����մϴ�.");
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
