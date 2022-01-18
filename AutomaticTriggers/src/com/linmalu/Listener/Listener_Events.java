package com.linmalu.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.linmalu.Data.Data_Sctipt_Runnable;
import com.linmalu.Main.AutomaticTriggers;

public class Listener_Events implements Listener
{
	private AutomaticTriggers plugin;

	public Listener_Events(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void EventEvent(CreatureSpawnEvent event)
	{
		String args[] = {"EntitySpawn"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, null, args[0]);
		run.eventEntitySpawn = event;
		new Thread(run).start();
	}
	@EventHandler
	public void EventEvent(EntityDeathEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			String args[] = {"PlayerDeath"};
			Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, null, args[0]);
			run.eventPlayerDeath = event;
			new Thread(run).start();
		}
		else
		{
			String args[] = {"EntityDeath"};
			Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, null, args[0]);
			run.eventEntityDeath = event;
			new Thread(run).start();
		}
	}
	@EventHandler
	public void EventEvent(PlayerRespawnEvent event)
	{
		String args[] = {"PlayerRespawn"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, null, args[0]);
		run.eventPlayerRespawn = event;
		new Thread(run).start();
	}
	@EventHandler
	public void EventEvent(PlayerJoinEvent event)
	{
		String args[] = {"Join"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventJoin = event;
		new Thread(run).start();
	}
	@EventHandler
	public void EventEvent(PlayerQuitEvent event)
	{
		String args[] = {"Quit"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventQuit = event;
		new Thread(run).start();
		plugin.listener.removeListener(event.getPlayer().getName());
	}
	@EventHandler
	public void EventEvent(PlayerKickEvent event)
	{
		String args[] = {"Kick"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventKick = event;
		new Thread(run).start();
		plugin.listener.removeListener(event.getPlayer().getName());
	}
	@EventHandler
	public void EventEvent(BlockBreakEvent event)
	{
		String args[] = {"BlockBreak"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventBlockBreak = event;
		new Thread(run).start();
		Player player = event.getPlayer();
		String worldName = player.getWorld().getName();
		String location = event.getBlock().getLocation().getBlockX() + "," + event.getBlock().getLocation().getBlockY() + "," + event.getBlock().getLocation().getBlockZ();
		if(plugin.config.block && (plugin.touchs.equalsTouchs(worldName, location, 0) || plugin.walks.equalsWalks(worldName, location, 0)))
			event.setCancelled(true);
	}
	@EventHandler
	public void EventEvent(BlockPlaceEvent event)
	{
		String args[] = {"BlockPlaced"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventBlockPlaced = event;
		new Thread(run).start();
	}
	@EventHandler
	public void EventEvent(PlayerInteractEntityEvent event)
	{
		String args[] = {"Interact"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventInteract = event;
		new Thread(run).start();
	}
	@EventHandler
	public void EventEvent(PlayerLevelChangeEvent event)
	{
		String args[] = {"Level"};
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), args[0]);
		run.eventLevel = event;
		new Thread(run).start();
	}
	@EventHandler
	public void EventEvent(AsyncPlayerChatEvent event)
	{
		String args[] = event.getMessage().split(" ");
		Data_Sctipt_Runnable run = new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), "Chat");
		run.eventChat = event;
		new Thread(run).start();
	}
}
