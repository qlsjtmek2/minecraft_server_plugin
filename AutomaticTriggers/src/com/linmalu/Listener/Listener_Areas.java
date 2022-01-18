package com.linmalu.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.linmalu.Data.Data_Sctipt_Runnable;
import com.linmalu.Main.AutomaticTriggers;

public class Listener_Areas implements Listener
{
	private AutomaticTriggers plugin;

	public Listener_Areas(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void AreaEvent(PlayerTeleportEvent event)
	{
		Player player = event.getPlayer();
		String worldName = player.getWorld().getName();
		String locationFrom = event.getFrom().getBlockX() + "," + event.getFrom().getBlockY() + "," + event.getFrom().getBlockZ();
		//바뀐후
		String locationTo = event.getTo().getBlockX() + "," + event.getTo().getBlockY() + "," + event.getTo().getBlockZ();
		String from = plugin.areas.equalsLocation(worldName, locationFrom);
		String to = plugin.areas.equalsLocation(worldName, locationTo);
		if((from == null && to != null) || (from !=null && to == null))
		{
			String location;
			String eventName;
			if(from != null)
			{
				location = from;
				eventName = "areaexit";
			}
			else
			{
				location = to;
				eventName = "areaenter";
			}
			new Thread(new Data_Sctipt_Runnable(plugin, new String[]{location}, event.getPlayer(), worldName, location, eventName)).start();
		}
	}
	@EventHandler
	public void AreaEvent(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		String worldName = player.getWorld().getName();
		String locationFrom = event.getFrom().getBlockX() + "," + event.getFrom().getBlockY() + "," + event.getFrom().getBlockZ();
		//바뀐후
		String locationTo = event.getTo().getBlockX() + "," + event.getTo().getBlockY() + "," + event.getTo().getBlockZ();
		String from = plugin.areas.equalsLocation(worldName, locationFrom);
		String to = plugin.areas.equalsLocation(worldName, locationTo);
		if((from == null && to != null) || (from !=null && to == null))
		{
			String location;
			String eventName;
			if(from != null)
			{
				location = from;
				eventName = "areaexit";
			}
			else
			{
				location = to;
				eventName = "areaenter";
			}
			new Thread(new Data_Sctipt_Runnable(plugin, new String[]{location}, event.getPlayer(), worldName, location, eventName)).start();
		}
	}
	@EventHandler
	public void AreaEvent(PlayerInteractEvent event)
	{
		if(event.getPlayer().getItemInHand().getTypeId() == plugin.config.wandID && plugin.listener.equalsListener(event.getPlayer().getName()) && plugin.listener.getListener(event.getPlayer().getName()).scriptLocation && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK))
		{
			Player player = event.getPlayer();
			String location = event.getClickedBlock().getLocation().getBlockX() + "," + event.getClickedBlock().getLocation().getBlockY() + "," + event.getClickedBlock().getLocation().getBlockZ();
			Listener_Data data = plugin.listener.getListener(player.getName());
			if(data.eventName.equals("areaset"))
			{
				if(event.getAction() == Action.LEFT_CLICK_BLOCK)
				{
					plugin.listener.setLeftLocation(player.getName(), location, player.getWorld().getName());
					player.sendMessage(ChatColor.GREEN + "1번째 지점 : " + ChatColor.YELLOW + location);
				}
				else if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					plugin.listener.setRightLocation(player.getName(), location, player.getWorld().getName());
					player.sendMessage(ChatColor.GREEN + "2번째 지점 : " + ChatColor.YELLOW + location);
				}
			}
		}
	}
	@EventHandler
	public void AreaEvent(BlockBreakEvent event)
	{
		if(plugin.listener.equalsListener(event.getPlayer().getName()) && event.getPlayer().getItemInHand().getTypeId() == plugin.config.wandID)
		{
			Player player = event.getPlayer();
			if(plugin.listener.getListener(player.getName()).eventName.equals("areaset"))
				event.setCancelled(true);
		}
	}
}
