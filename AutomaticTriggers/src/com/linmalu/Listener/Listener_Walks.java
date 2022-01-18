package com.linmalu.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.linmalu.Data.Data_Sctipt_Runnable;
import com.linmalu.Data.Data_Walk_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class Listener_Walks implements Listener
{
	private AutomaticTriggers plugin;

	public Listener_Walks(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler//(priority=EventPriority.HIGHEST)
	public void WalkEvent(PlayerInteractEvent event)
	{
		if(event.getPlayer().getItemInHand().getTypeId() == plugin.config.wandID && event.getAction() == Action.RIGHT_CLICK_BLOCK && plugin.listener.equalsListener(event.getPlayer().getName()) && plugin.listener.getListener(event.getPlayer().getName()).scriptLocation)
		{
			Player player = event.getPlayer();
			String worldName = player.getWorld().getName();
			String location = event.getClickedBlock().getLocation().getBlockX() + "," + event.getClickedBlock().getLocation().getBlockY() + "," + event.getClickedBlock().getLocation().getBlockZ();
			Data_Walk_Methods walk = plugin.walks;
			Listener_Data data = plugin.listener.getListener(event.getPlayer().getName());
			String script = data.script;
			int line = data.line;
			//발판트리거
			if(data.eventName.equals("walk"))
			{
				walk.setWalks(worldName, location, script);
				player.sendMessage(ChatColor.GREEN + "발판이 설정되었습니다.");
			}else if(data.eventName.equals("walkadd"))
			{
				if(walk.equalsWalks(worldName, location, line))
				{
					walk.addWalks(worldName, location, line, script);
					player.sendMessage(ChatColor.GREEN + "발판의 내용이 추가되었습니다.");
				}
				else
					player.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
			}else if(data.eventName.equals("walkremove"))
			{
				if(walk.equalsWalks(worldName, location, line))
				{
					walk.removeWalks(worldName, location, line);
					player.sendMessage(ChatColor.GREEN + "발판의 내용이 삭제되었습니다.");
				}
				else
					player.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
			}else if(data.eventName.equals("walkedit"))
			{
				if(walk.equalsWalks(worldName, location, line))
				{
					walk.editWalks(worldName, location, line, script);
					player.sendMessage(ChatColor.GREEN + "발판의 내용이 수정되었습니다.");
				}
				else
					player.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
			}else if(data.eventName.equals("walkview"))
			{
				if(walk.equalsWalks(worldName, location, line))
				{
					player.sendMessage(ChatColor.GREEN + " -- " + location + " --");
					String scripts[] = walk.viewWalks(worldName, location);
					for(int i = 0; i < scripts.length; i++)
						player.sendMessage(ChatColor.GREEN + (i + 1 + ". ") + ChatColor.YELLOW + scripts[i]);
				}
				else
					player.sendMessage(ChatColor.RED + "발판이 없습니다.");
			}else if(data.eventName.equals("walkdelete"))
			{
				walk.deleteWalks(worldName, location);
				player.sendMessage(ChatColor.GREEN + "발판이 삭제되었습니다.");
			}
			else
				return;
			plugin.listener.removeListener(player.getName());
		}
	}
	@EventHandler
	public void WalkEvent(PlayerMoveEvent event)
	{
		//바뀌기전
		String locationFrom = event.getFrom().getBlockX() + "," + (event.getFrom().getBlockY() -1) + "," + event.getFrom().getBlockZ();
		//바뀐후
		String locationTo = event.getTo().getBlockX() + "," + (event.getTo().getBlockY() -1) + "," + event.getTo().getBlockZ();
		String worldName = event.getPlayer().getWorld().getName();
		if(!locationFrom.equals(locationTo) && plugin.walks.equalsWalks(worldName, locationTo, 0))
		{
			String args[] = {locationTo};
			new Thread(new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), worldName, locationTo, "walk")).start();
		}
	}
}
