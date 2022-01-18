package me.shinkhan.BarHealth;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
	static final Map<String, Integer> Timer = new HashMap<String, Integer>();
	public static boolean isEpicBoss = false;
	public static String barName;
	
	public void onEnable()
	{
		PluginManager pm = Bukkit.getPluginManager();
		Plugin pl = pm.getPlugin("EpicBossRecoded");
		if (pl != null) {
			isEpicBoss = true;
			System.out.println("[DHBarHealth] ���Ⱥ��� ȣȯ ���� ���� �Ϸ�Ǿ����ϴ�.");
		} else {
			System.out.println("[DHBarHealth] ���Ⱥ��� ȣȯ ���� ������ �߸��Ǿ����ϴ�.");
			System.out.println("[DHBarHealth] ���Ⱥ��� �÷������� �ְų�, ���Ⱥ��� ������ ������ �޾��ֽñ� �ٶ��ϴ�.");
			isEpicBoss = false;
		}
		
		File folder = new File("plugins/DHBarHealth");
		File F = new File("plugins/DHBarHealth/config.yml");
		YamlConfiguration C = YamlConfiguration.loadConfiguration(F);
		if (!F.exists()) Config.CreateConfig(F, folder, C);
		
		barName = Config.getString("������ ǥ��");
		
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
			if (commandLabel.equalsIgnoreCase("barcheck")) {
				sender.sendMessage("��6���� �� �������� ��cDHBarHealth ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
				sender.sendMessage("��6������ :: ��cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("��cDHBarHealth ��6�÷������� ������ּż� �����մϴ�.  ��f- shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6���� �� �������� ��cDHBarHealth ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
			sender.sendMessage("��6������ :: ��cshinkhan");
		} return false;
	}
}
