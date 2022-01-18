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
		getCommand("금융").setExecutor(new mainCommand(this));
		getCommand("대출").setExecutor(new mainCommand(this));
		getCommand("투자").setExecutor(new mainCommand(this));
		getCommand("예금").setExecutor(new mainCommand(this));
		getCommand("Bank").setExecutor(new mainCommand(this));
		getCommand("BK").setExecutor(new mainCommand(this));
		getCommand("ㅂ").setExecutor(new mainCommand(this));
		getCommand("ㅇ").setExecutor(new mainCommand(this));
		
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public static void BorrowTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "대출 타이머") != 0) {
			int num = PlayerYml.getInfoInt(p, "대출 타이머") - 1;
			PlayerYml.setInfoInt(p, "대출 타이머", num);
			
			if (num <= 0) {
				if (p.isOnline()) {
					p.sendMessage(Message.getMessage("대출 상환 초과"));
					PlayerYml.setInfoBoolean(p, "대출 완료", true);
				}
			}
		}
	}
	
	public static void InvestTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "예금 타이머") != 0) {
			int num = PlayerYml.getInfoInt(p, "예금 타이머") - 1;
			PlayerYml.setInfoInt(p, "예금 타이머", num);
			
			if (num <= 0) {
				if (p.isOnline()) {
					p.sendMessage(Message.getMessage("예금 만기 완료"));
					PlayerYml.setInfoBoolean(p, "예금 완료", true);
				}
			}
		}
	}
	
	public static void DepositTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "투자 타이머") != 0) {
			int num = PlayerYml.getInfoInt(p, "투자 타이머") - 1;
			PlayerYml.setInfoInt(p, "투자 타이머", num);
			
			if (num <= 0) {
				if (p.isOnline()) {
					p.sendMessage(Message.getMessage("투자 만기 완료"));
					PlayerYml.setInfoBoolean(p, "투자 완료", true);
				}
			}
		}
	}
}
