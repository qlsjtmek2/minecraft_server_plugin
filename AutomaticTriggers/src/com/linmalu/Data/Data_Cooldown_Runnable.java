package com.linmalu.Data;

import org.bukkit.entity.Player;

import com.linmalu.Main.AutomaticTriggers;

public class Data_Cooldown_Runnable implements Runnable
{
	private AutomaticTriggers plugin;
	private Data_Cooldown data;
	private int cooldown;
	
	public Data_Cooldown_Runnable(AutomaticTriggers plugin, String name, Player player, int cooldown)
	{
		this.plugin = plugin;
		this.data = new Data_Cooldown(name, player);
		this.cooldown = cooldown;
	}
	public void run()
	{
		plugin.cooldown.putData(data);
		try{
			Thread.sleep(cooldown);
		}catch (InterruptedException e){}
		plugin.cooldown.removeData(data);
	}
}
