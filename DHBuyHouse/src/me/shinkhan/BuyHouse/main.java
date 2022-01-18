package me.shinkhan.BuyHouse;

import java.io.File;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener {
    public static Economy economy = null;
    
	public void onEnable()
	{
		File f = new File("plugins/DHBuyHouse/config.yml");
		File folder = new File("plugins/DHBuyHouse");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreateConfig(f, folder, config);
		
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
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
	
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("housecheck")) {
				sender.sendMessage("��6���� �� �������� ��cDHBuyHouse ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
				sender.sendMessage("��6������ :: ��cshinkhan");
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (!p.getName().equalsIgnoreCase("shinkhan")) return false;
					else
					{
						Bukkit.broadcastMessage("��cDHBuyHouse ��6�÷������� ������ּż� �����մϴ�.  ��f- shinkhan -");
						return false;
					}
				} else return false;
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6���� �� �������� ��cDHBuyHouse ��6�÷������� ����Ǿ� �ֽ��ϴ�.");
			sender.sendMessage("��6������ :: ��cshinkhan");
		} 
		
		try {
			if (commandLabel.equalsIgnoreCase("�ڵ�����")) {	
				if (args.length == 0) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (p.isOp()) {
							Method.HelpOpMessage(sender);
							return false;
						} else {
							Method.HelpUserMessage(sender);
							return false;
						}
					} else {
						Method.HelpConMessage(sender);
						return false;
					}
				}
				
				else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("����")) {
						if (!(sender instanceof Player)) {
							return false;
						} Player p = (Player) sender;
						
						if (args[1].equalsIgnoreCase("���")) {
							p.sendMessage("��6����� ��c�ڵ� ���� ��6��� ����Դϴ�.");
							p.sendMessage("��f - ��6�� ��f:: " + Method.getInfoInt(p, "�� ����"));
							p.sendMessage("��f - ��6â�� ��f:: " + Method.getInfoInt(p, "â�� ����"));
							p.sendMessage("��f - ��6��� ��f:: " + Method.getInfoInt(p, "��� ����"));
							p.sendMessage("��f - ��6��þƮ ��f:: " + Method.getInfoInt(p, "��þƮ ����"));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��")) {
							p.sendMessage("��6����� ��c" + Method.getInfoInt(p, "�� ����") + " ��6���� ���� �����ϰ� �ֽ��ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("â��")) {
							p.sendMessage("��6����� ��c" + Method.getInfoInt(p, "â�� ����") + " ��6���� â�� �����ϰ� �ֽ��ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("���")) {
							p.sendMessage("��6����� ��c" + Method.getInfoInt(p, "��� ����") + " ��6���� ��縦 �����ϰ� �ֽ��ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��þƮ")) {
							p.sendMessage("��6����� ��c" + Method.getInfoInt(p, "��þƮ ����") + " ��6���� ��þƮ�� �����ϰ� �ֽ��ϴ�.");
							return false;
						}
					}
				}
				
				else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("�ʱ�ȭ")) {
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("���")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "�� ����", 0);
							Method.setInfoInt(p, "â�� ����", 0);
							Method.setInfoInt(p, "��� ����", 0);
							Method.setInfoInt(p, "��þƮ ����", 0);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��� ���� �ʱ�ȭ �Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "�� ����", 0);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� ���� ���� �ʱ�ȭ �Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("â��")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "â�� ����", 0);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� â�� ���� ���� �ʱ�ȭ �Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("���")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "��� ����", 0);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��� ���� ���� �ʱ�ȭ �Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��þƮ")) {
							String name = Method.searchOnlinePlayer(args[2]);
							
							File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
							if (!f.exists()) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
								return false;
							}
							
							if (Method.getOnorOffLine(name) == null) {
								sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
								return false;
							}
							
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "��þƮ ����", 0);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��þƮ ���� ���� �ʱ�ȭ �Ͽ����ϴ�.");
							return false;
						}
					}
				}
				
				else if (args.length == 4) {
					if (args[0].equalsIgnoreCase("����"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("��"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "�� ����", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("â��"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "â�� ����", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� â�� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("���"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "��� ����", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��þƮ"))
						{
							Player p = Method.getOnorOffLine(name);
							Method.setInfoInt(p, "��þƮ ����", Integer.parseInt(args[3]));
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��þƮ ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �����Ͽ����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("�߰�"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("��"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "�� ����") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "�� ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("â��"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "â�� ����") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "â�� ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� â�� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("���"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "��� ����") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "��� ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��þƮ"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "��þƮ ����") + Integer.parseInt(args[3]);
							Method.setInfoInt(p, "��þƮ ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��þƮ ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ���� �߰��Ͽ����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (!sender.isOp()) {
							sender.sendMessage(ChatColor.RED + "����� ������ �����ϴ�.");
							return false;
						}
						
						String name = Method.searchOnlinePlayer(args[2]);
						
						File f = new File("plugins/DHBuyHouse/Player/" + name + ".yml");
						if (!f.exists()) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �������� �ʽ��ϴ�.");
							return false;
						}
						
						if (Method.getOnorOffLine(name) == null) {
							sender.sendMessage(ChatColor.RED + "�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}
						
						Scanner scan = new Scanner(args[3]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (args[1].equalsIgnoreCase("��"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "�� ����") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "�� ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� �� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("â��"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "â�� ����") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "â�� ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� â�� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("���"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "��� ����") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "��� ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��� ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��þƮ"))
						{
							Player p = Method.getOnorOffLine(name);
							int i = Method.getInfoInt(p, "��þƮ ����") - Integer.parseInt(args[3]);
							if (i < 0) i = 0;
							Method.setInfoInt(p, "��þƮ ����", i);
							sender.sendMessage(ChatColor.GOLD + "���������� " + ChatColor.RED + name + ChatColor.GOLD + " ���� ��þƮ ���� ���� " + ChatColor.RED + args[3] + ChatColor.GOLD + " ��ŭ �����ϴ�.");
							return false;
						}
					}
				}
				
				else {}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {
					Method.HelpOpMessage(sender);
					return false;
				} else {
					Method.HelpUserMessage(sender);
					return false;
				}
			} else {
				Method.HelpConMessage(sender);
				return false;
			}
		} return false;
	}
}
