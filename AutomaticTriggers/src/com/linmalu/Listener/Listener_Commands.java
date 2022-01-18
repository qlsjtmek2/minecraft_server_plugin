package com.linmalu.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.linmalu.Data.Data_Sctipt_Runnable;
import com.linmalu.Main.AutomaticTriggers;

public class Listener_Commands implements Listener
{
	private AutomaticTriggers plugin;

	public Listener_Commands(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void CommandEvent(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		String args[] = event.getMessage().split(" ");
		args[0] = args[0].replace("/", "").toLowerCase();
		if(plugin.commands.equalsCommands(args[0], 0))
		{
			if(plugin.commands.getOverride(args[0]))
			{
				if(plugin.config.overrideLog)
					plugin.getLogger().info(player.getName() + " ¸í·É¾î: " + event.getMessage());
				event.setCancelled(true);
			}
			new Thread(new Data_Sctipt_Runnable(plugin, args, event)).start();
		}
	}
}
