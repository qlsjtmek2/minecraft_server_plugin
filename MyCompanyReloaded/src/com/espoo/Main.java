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
		getCommand("회사").setExecutor(new Command(this));
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
	    economy = (Economy)economyProvider.getProvider();
		
		PluginDescriptionFile pdFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
    }
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void eventOnJoin(PlayerJoinEvent e) 
	{
		Player p = e.getPlayer();
		String Cname = UserData.getStringYml(p, "회사");
		File cf = new File("plugins/MyCompany/Company/" + Cname + ".yml");
		if (!(Cname.equalsIgnoreCase("NONE")) && !(cf.exists())) {
			p.sendMessage("§c당신이 가입한 회사가 파산되어 강제 사퇴 되었습니다.");
			UserData.SetBooleanData(p, "직원", false);
			UserData.SetStringData(p, "회사", "NONE");
		}
	}
}
