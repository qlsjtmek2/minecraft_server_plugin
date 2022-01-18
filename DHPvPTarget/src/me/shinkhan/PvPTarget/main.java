package me.shinkhan.PvPTarget;

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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
    static final Map <String, Integer> Timer = new HashMap<>();
	public static HashMap<String, Integer> ScoreTime = new HashMap<String, Integer>();
	public static HashMap<String, String> Name = new HashMap<String, String>();
	
	public void onEnable()
	{
		File cf = new File("plugins/DHPvPTarget/config.yml");
		File qf = new File("plugins/DHPvPTarget/Quitlist.yml");
		File folder = new File("plugins/DHPvPTarget");
		YamlConfiguration cg = YamlConfiguration.loadConfiguration(cf);
		YamlConfiguration qg = YamlConfiguration.loadConfiguration(qf);
		if (!cf.exists()) Method.Createconfig(cf, folder, cg);
		if (!qf.exists()) Method.CreateQuitList(qf, folder, qg);
		
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("ptcheck")) {
				sender.sendMessage("��6���� �� �������� ��cDHPvPTarget ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
				sender.sendMessage("��6������ :: ��cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("��cDHPvPTarget ��6�÷������� ������ּż� �����մϴ�.  ��f- shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6���� �� �������� ��cDHPvPTarget ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
			sender.sendMessage("��6������ :: ��cshinkhan");
		} return false;
	}
}