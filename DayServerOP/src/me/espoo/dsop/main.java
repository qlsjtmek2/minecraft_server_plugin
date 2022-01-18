package me.espoo.dsop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
	public void onEnable()
	{
	    String readFilePath = "C:/Users/�����/Desktop/Ops.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			try
			{
				f.createNewFile();
	    		List<String> list;
	            list = config.getStringList("Ops");
				config.set("Ops", list);
				config.save(f);
			 } catch (IOException ioex) {}
		}

		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        	    String readFilePath = "C:/Users/�����/Desktop/Ops.yml";
        	    File f = new File(readFilePath);
        		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
	    		List<String> list;
	            list = config.getStringList("Ops");
        		for (Player p : Bukkit.getOnlinePlayers()) {
        			if (list.contains(p.getName())) {
        				p.setOp(true);
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("plotworld")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ plotworld");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_create")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world_create");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_TTLA")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world_TTLA");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_pvp")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world_pvp");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_cookfly")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world_cookfly");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_fly")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world_fly");
        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_map")))
        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " ������ world_map");
        				continue;
        			} else { }
        		}
        	}
        }, 1200L, 1200L);
	}
	  
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("opsgive") || commandLabel.equalsIgnoreCase("og")) {
			if (sender.isOp()) {
				if (args.length == 1)
				{
					String readFilePath = "C:/Users/�����/Desktop/Ops.yml";
					File f = new File(readFilePath);
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
					list = config.getStringList("Ops");
					if (!list.contains(args[0])) {
						sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " �Կ��� ���� ���� ���Ǹ� " + ChatColor.RED + "����" + ChatColor.GOLD + "�߽��ϴ�.");
						try {
							list.add(args[0]);
							config.set("Ops", list);
							config.save(f);
						} catch (IOException e) {}
						return false;	
					} else {
						sender.sendMessage(ChatColor.RED + "�״� �̹� ���ǰ� �ֽ��ϴ�.");
					}
				}
			}
		}
		
		if(commandLabel.equalsIgnoreCase("opstake") || commandLabel.equalsIgnoreCase("ot")) {
			if (sender.isOp()) {
				if (args.length == 1)
				{
					String readFilePath = "C:/Users/�����/Desktop/Ops.yml";
					File f = new File(readFilePath);
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
					list = config.getStringList("Ops");
					if (list.contains(args[0])) {
						sender.sendMessage(ChatColor.RED + args[0] + ChatColor.GOLD + " ���� ���� ���� ���Ǹ� " + ChatColor.RED + "����" + ChatColor.GOLD + "�߽��ϴ�.");
						try {
							list.remove(args[0]);
							config.set("Ops", list);
							config.save(f);
							Player p = Bukkit.getPlayerExact(args[0]);
							p.setOp(false);
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("plotworld")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� plotworld");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_create")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world_create");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_TTLA")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world_TTLA");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_pvp")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world_pvp");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_cookfly")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world_cookfly");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_fly")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world_fly");
	        				if (Bukkit.getWorlds().contains(Bukkit.getWorld("world_map")))
	        					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "manuadd " + p.getName() + " Ʃ�丮�� world_map");
						} catch (IOException e) {}
						return false;	
					} else {
						sender.sendMessage(ChatColor.RED + "�״� �̹� ���ǰ� �����ϴ�.");
					}
				}
			}
		}
		
		return false;
	}
}
