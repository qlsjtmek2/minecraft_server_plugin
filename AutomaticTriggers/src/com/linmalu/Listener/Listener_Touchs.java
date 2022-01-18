package com.linmalu.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.linmalu.Data.Data_Sctipt_Runnable;
import com.linmalu.Data.Data_Touch_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class Listener_Touchs implements Listener
{
	private AutomaticTriggers plugin;

	public Listener_Touchs(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler//(priority=EventPriority.HIGHEST)
	public void TouchEvent(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			Player player = event.getPlayer();
			String worldName = player.getWorld().getName();
			String location = event.getClickedBlock().getLocation().getBlockX() + "," + event.getClickedBlock().getLocation().getBlockY() + "," + event.getClickedBlock().getLocation().getBlockZ();
			Data_Touch_Methods touch = plugin.touchs;
			if(event.getPlayer().getItemInHand().getTypeId() == plugin.config.wandID && event.getAction() == Action.RIGHT_CLICK_BLOCK && plugin.listener.equalsListener(event.getPlayer().getName()) && plugin.listener.getListener(event.getPlayer().getName()).scriptLocation)
			{
				Listener_Data data = plugin.listener.getListener(event.getPlayer().getName());
				String script = data.script;
				int line = data.line;
				if(data.eventName.equals("touch"))
				{
					touch.setTouchs(worldName, location, script);
					player.sendMessage(ChatColor.GREEN + "터치가 설정되었습니다.");
				}else if(data.eventName.equals("touchadd"))
				{
					if(touch.equalsTouchs(worldName, location, line))
					{
						touch.addTouchs(worldName, location, line, script);
						player.sendMessage(ChatColor.GREEN + "터치에 내용이 추가되었습니다.");
					}
					else
						player.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
				}else if(data.eventName.equals("touchremove"))
				{
					if(touch.equalsTouchs(worldName, location, line))
					{
						touch.removeTouchs(worldName, location, line);
						player.sendMessage(ChatColor.GREEN + "터치의 내용이 삭제되었습니다.");
					}
					else
						player.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
				}else if(data.eventName.equals("touchedit"))
				{
					if(touch.equalsTouchs(worldName, location, line))
					{
						touch.editTouchs(worldName, location, line, script);
						player.sendMessage(ChatColor.GREEN + "터치의 내용이 수정되었습니다.");
					}
					else
						player.sendMessage(ChatColor.RED + "줄번호가 올바르지 않습니다.");
				}else if(data.eventName.equals("touchview"))
				{
					if(touch.equalsTouchs(worldName, location, line))
					{
						player.sendMessage(ChatColor.GREEN + " -- " + location + " --");
						String scripts[] = touch.viewTouchs(worldName, location);
						for(int i = 0; i < scripts.length; i++)
							player.sendMessage(ChatColor.GREEN + (i + 1 + ". ") + ChatColor.YELLOW + scripts[i]);
					}
					else
						player.sendMessage(ChatColor.RED + "터치가 없습니다.");
				}else if(data.eventName.equals("touchdelete"))
				{
					touch.deleteTouchs(worldName, location);
					player.sendMessage(ChatColor.GREEN + "터치가 삭제되었습니다.");
				}
				else
					return;
				plugin.listener.removeListener(player.getName());
			}
			else if(touch.equalsTouchs(worldName, location, 0) && !plugin.listener.equalsListener(event.getPlayer().getName()))
			{
				String args[] = {location};
				new Thread(new Data_Sctipt_Runnable(plugin, args, event.getPlayer(), worldName, location, "touch")).start();
			}
		}
	}
}
