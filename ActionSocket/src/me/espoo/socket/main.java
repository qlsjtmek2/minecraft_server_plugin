package me.espoo.socket;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public static HashMap<Player, Integer> damage = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cretprobe = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cretdamage = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> heart = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> defense = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> dodge = new HashMap<Player, Integer>();
	public static final Map <String, String> cret = new HashMap<>();
	
	public void onEnable()
	{
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("���Ͽ���").setExecutor(new mainCommand(this));
		getCommand("��������").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
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
