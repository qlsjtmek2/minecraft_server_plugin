package com.linmalu.Main;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault
{
	public boolean checkVault = false;
	public Economy economy = null;

	public Vault(AutomaticTriggers plugin)
	{
		if(plugin.getServer().getPluginManager().getPlugin("Vault") != null)
		{
			checkVault = true;
			RegisteredServiceProvider<Economy> economy = plugin.getServer().getServicesManager().getRegistration(Economy.class);
			if (economy != null)
				this.economy = economy.getProvider();
		}
	}
}
