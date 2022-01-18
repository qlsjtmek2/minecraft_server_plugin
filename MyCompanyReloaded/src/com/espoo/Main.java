package com.espoo;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.espoo.Command;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
	static Economy economy = null;

	public void onEnable()
    {	
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("ȸ��").setExecutor(new Command(this));
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
	    economy = (Economy)economyProvider.getProvider();
		
		PluginDescriptionFile pdFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
    }
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void eventOnJoin(PlayerJoinEvent e) 
	{
		Player p = e.getPlayer();
		String Cname = UserData.getStringYml(p, "ȸ��");
		File cf = new File("plugins/MyCompany/Company/" + Cname + ".yml");
		if (!(Cname.equalsIgnoreCase("NONE")) && !(cf.exists())) {
			p.sendMessage("��c����� ������ ȸ�簡 �Ļ�Ǿ� ���� ���� �Ǿ����ϴ�.");
			UserData.SetBooleanData(p, "����", false);
			UserData.SetStringData(p, "ȸ��", "NONE");
		}
	}
}
