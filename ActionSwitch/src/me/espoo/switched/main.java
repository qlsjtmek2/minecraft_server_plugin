package me.espoo.switched;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.switched.sheep.SheepEvent;
import me.espoo.switched.trade.TradeConfig;
import me.espoo.switched.trade.TradeEvent;

public class main extends JavaPlugin implements Listener {
	public void onEnable()
	{
		GUIMessage.setGUIMessage();
		File folder = new File("plugins/ActionSwitch");
		File f = new File("plugins/ActionSwitch/Config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("���б�ȯ").setExecutor(new mainCommand(this));
		getCommand("���б�ȯ��").setExecutor(new mainCommand(this));
		getCommand("��ȯ���߰�").setExecutor(new mainCommand(this));
		getCommand("��ȯ�����").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new SheepEvent(this), this);
		getServer().getPluginManager().registerEvents(new TradeEvent(this), this);
		if (!f.exists()) TradeConfig.CreateConfig(f, folder, config);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.closeInventory();
		}

		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}

}
