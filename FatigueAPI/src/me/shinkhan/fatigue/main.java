package me.shinkhan.fatigue;

import java.io.File;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public static ChatColor GOLD = ChatColor.GOLD;
	public static ChatColor RED = ChatColor.RED;
	
	public void onEnable()
	{
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 API.addFatigue(p, 1D);
				 }
			 }
		 }, 12000L, 12000L);
		 
		getServer().getPluginManager().registerEvents(new Event(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + RED + "�� " + RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("fa") || commandLabel.equalsIgnoreCase("�Ƿε�")) {	
				if (args.length == 0) {
					if (sender.isOp()) {
						Method.HelpMessage(sender);
						return false;
					} else {
						Player p = (Player) sender;
						p.sendMessage(GOLD + "����� �Ƿε��� " + RED + API.getFatigue(p) + GOLD + "% �Դϴ�.");
					}
				}
				
				else if (args.length == 1) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						
						if (args[0].equalsIgnoreCase("����")) {
							p.sendMessage(GOLD + "����� �Ƿε��� " + RED + API.getFatigue(p) + GOLD + "% �Դϴ�.");
							return false;
						}
					} else {
						sender.sendMessage(RED + "����� �� ��ɾ �����ų �� �����ϴ�.");
						return false;
					}
				}
				
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("����")) {
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Player p = Method.getOnorOffLine(name);
						sender.sendMessage(RED + p.getName() + GOLD + "���� �Ƿε��� " + RED + API.getFatigue(p) + GOLD + "% �Դϴ�.");
						return false;
					}
				}

				else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("����"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[2]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}

						Player p = Method.getOnorOffLine(name);
						API.setFatigue(p, (double) Integer.parseInt(args[2]));
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("�߰�"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[2]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}

						Player p = Method.getOnorOffLine(name);
						API.addFatigue(p, (double) Integer.parseInt(args[2]));
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[1]);
						
						File f = new File("plugins/FatigueAPI/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[2]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}

						Player p = Method.getOnorOffLine(name);
						API.subFatigue(p, (double) Integer.parseInt(args[2]));
						return false;
					}
				}
				
				else {}
			}
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				Method.HelpMessage(sender);
				return false;
			} else {
				Player p = (Player) sender;
				p.sendMessage("����� �Ƿε��� " + RED + API.getFatigue(p) + GOLD + "% �Դϴ�.");
			}
		} return false;
	}
}
