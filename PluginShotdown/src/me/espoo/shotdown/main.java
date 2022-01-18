package me.espoo.shotdown;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
	public void onEnable()
	{
	    String readFilePath = "C:/Users/�����/Desktop/�ָ�.yml";
	    File f = new File(readFilePath);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			try
			{
				f.createNewFile();
				config.set("�ָ��Դϱ�?", false);
				config.save(f);
			 } catch (IOException ioex) {}
		}

		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        	    String readFilePath = "C:/Users/�����/Desktop/�ָ�.yml";
        	    File f = new File(readFilePath);
        		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        		Calendar oCalendar = Calendar.getInstance();
        		if (config.getBoolean("�ָ��Դϱ�?") == false && oCalendar.get(Calendar.HOUR_OF_DAY) == 23 
        		 && oCalendar.get(Calendar.MINUTE) == 59 && oCalendar.get(Calendar.SECOND) >= 40) {
					getServer().dispatchCommand(getServer().getConsoleSender(), "save");
					getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
					getServer().dispatchCommand(getServer().getConsoleSender(), "backup");
					getServer().dispatchCommand(getServer().getConsoleSender(), "asw save");
					getServer().dispatchCommand(getServer().getConsoleSender(), "stop");
        		}
        	}
        }, 200L, 200L);
	}
	  
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
}
