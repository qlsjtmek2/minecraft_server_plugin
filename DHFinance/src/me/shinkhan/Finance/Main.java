package me.shinkhan.Finance;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.shinkhan.Finance.config.Borrow;
import me.shinkhan.Finance.config.Deposit;
import me.shinkhan.Finance.config.GUIMessage;
import me.shinkhan.Finance.config.Invest;
import me.shinkhan.Finance.config.Message;
import me.shinkhan.Finance.config.PlayerYml;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
    public static Economy economy = null;

	public void onEnable()
	{
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("Bank").setExecutor(new mainCommand(this));
		getCommand("BK").setExecutor(new mainCommand(this));
		getCommand("��").setExecutor(new mainCommand(this));
		getCommand("��").setExecutor(new mainCommand(this));
		
		File folder = new File("plugins/DHFinance");
		File BF = new File("plugins/DHFinance/Borrow.yml");
		File DF = new File("plugins/DHFinance/Deposit.yml");
		File IF = new File("plugins/DHFinance/Invest.yml");
		File MF = new File("plugins/DHFinance/Message.yml");
		File GF = new File("plugins/DHFinance/GUIMessage.yml");
		YamlConfiguration Bconfig = YamlConfiguration.loadConfiguration(BF);
		YamlConfiguration Dconfig = YamlConfiguration.loadConfiguration(DF);
		YamlConfiguration Iconfig = YamlConfiguration.loadConfiguration(IF);
		YamlConfiguration Mconfig = YamlConfiguration.loadConfiguration(MF);
		YamlConfiguration Gconfig = YamlConfiguration.loadConfiguration(GF);
		if (!BF.exists()) Borrow.CreateBorrowConfig(BF, folder, Bconfig);
		if (!DF.exists()) Deposit.CreateDepositConfig(DF, folder, Dconfig);
		if (!IF.exists()) Invest.CreateInvestConfig(IF, folder, Iconfig);
		if (!MF.exists()) Message.CreateMessageConfig(MF, folder, Mconfig);
		if (!GF.exists()) GUIMessage.CreateGUIMessageConfig(GF, folder, Gconfig);
		
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		getServer().getPluginManager().registerEvents(new Event(this), this);
		
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 BorrowTimer(p);
					 InvestTimer(p);
					 DepositTimer(p);
				 }
			 }
		 }, 1200L, 1200L);
		 
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
	
	public static void BorrowTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") != 0) {
			int num = PlayerYml.getInfoInt(p, "���� Ÿ�̸�") - 1;
			PlayerYml.setInfoInt(p, "���� Ÿ�̸�", num);
			
			if (num <= 0) {
				if (p.isOnline()) {
					p.sendMessage(Message.getMessage("���� ��ȯ �ʰ�"));
					PlayerYml.setInfoBoolean(p, "���� �Ϸ�", true);
				}
			}
		}
	}
	
	public static void InvestTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") != 0) {
			int num = PlayerYml.getInfoInt(p, "���� Ÿ�̸�") - 1;
			PlayerYml.setInfoInt(p, "���� Ÿ�̸�", num);
			
			if (num <= 0) {
				if (p.isOnline()) {
					p.sendMessage(Message.getMessage("���� ���� �Ϸ�"));
					PlayerYml.setInfoBoolean(p, "���� �Ϸ�", true);
				}
			}
		}
	}
	
	public static void DepositTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "���� Ÿ�̸�") != 0) {
			int num = PlayerYml.getInfoInt(p, "���� Ÿ�̸�") - 1;
			PlayerYml.setInfoInt(p, "���� Ÿ�̸�", num);
			
			if (num <= 0) {
				if (p.isOnline()) {
					p.sendMessage(Message.getMessage("���� ���� �Ϸ�"));
					PlayerYml.setInfoBoolean(p, "���� �Ϸ�", true);
				}
			}
		}
	}
}
