package me.shinkhan.userguide;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public void onEnable()
	{
		File f = new File("plugins/DHUserGuide/Config.yml");
		File folder = new File("plugins/DHUserGuide");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Config.CreateConfig(f, folder, c);

		getCommand("�������̵�").setExecutor(new mainCommand(this));
		getCommand("���̵�").setExecutor(new mainCommand(this));
		getCommand("guide").setExecutor(new mainCommand(this));
		getCommand("userguide").setExecutor(new mainCommand(this));
		getCommand("ug").setExecutor(new mainCommand(this));
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
}
