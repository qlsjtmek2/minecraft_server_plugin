package com.linmalu.Listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.linmalu.Data.Data_Area_Methods;
import com.linmalu.Data.Data_Command;
import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Main.AutomaticTriggers;

public class Listener_Locations implements Listener
{
	private AutomaticTriggers plugin;

	public Listener_Locations(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void AreaEvent(PlayerInteractEvent event)
	{
		if(event.getPlayer().getItemInHand().getTypeId() == plugin.config.wandID && plugin.listener.equalsListener(event.getPlayer().getName()) && !plugin.listener.getListener(event.getPlayer().getName()).scriptLocation && event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Listener_Data listener = plugin.listener.getListener(event.getPlayer().getName());
			String location = event.getClickedBlock().getLocation().getBlockX() + "," + event.getClickedBlock().getLocation().getBlockY() + "," + event.getClickedBlock().getLocation().getBlockZ();
			listener.script = listener.scriptCommand + " " + location + " " + event.getPlayer().getWorld().getName() + " " + listener.script;
			listener.scriptLocation = true;
			Data_Area_Methods areas = plugin.areas;
			Data_Command_Methods cmds = plugin.commands;
			Data_Event_Methods events = plugin.events;
			String worldName = event.getPlayer().getWorld().getName();
			if(listener.eventName.startsWith("area"))
			{
				String areaName = areas.getAreaName(worldName, listener.scriptName);
				areas.setAreas(worldName, areaName, listener.script);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " ������ ������ �߰��Ǿ����ϴ�.");				
			}
			else if(listener.eventName.equals("areaadd"))
			{
				areas.addAreas(worldName, listener.scriptName, listener.line, listener.script);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " ������ " + listener.line + "��°�ٿ� �߰��Ǿ����ϴ�.");
			}
			else if(listener.eventName.equals("areaedit"))
			{
				areas.editAreas(worldName, listener.scriptName, listener.line, listener.script);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + " ������ " + ChatColor.GREEN + listener.line + "��°���� ����Ǿ����ϴ�.");
			}
			else if(listener.eventName.equals("command"))
			{
				if(!cmds.equalsCommands(listener.scriptName, 0))
				{
					cmds.putCommands(listener.scriptName, new Data_Command(listener.script));
					event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " ��ɾ �����Ǿ����ϴ�.");
				}else{
					cmds.addScripts(listener.scriptName, listener.script);
					event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " ��ɾ ������ �߰��Ǿ����ϴ�.");
				}
			}
			else if(listener.eventName.equals("commandadd"))
			{
				cmds.addScripts(listener.scriptName, listener.script, listener.line);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " ��ɾ��� " + listener.line + "��°�ٿ� �߰��Ǿ����ϴ�.");
			}
			else if(listener.eventName.equals("commandedit"))
			{
				cmds.editScripts(listener.scriptName, listener.script, listener.line);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + " ��ɾ��� " + ChatColor.GREEN + listener.line + "��°���� ����Ǿ����ϴ�.");
			}
			else if(listener.eventName.equals("event"))
			{
				events.setEvent(listener.scriptName, listener.script);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " �̺�Ʈ�� ������ �߰��Ǿ����ϴ�.");
			}
			else if(listener.eventName.equals("eventadd"))
			{
				events.addEvents(listener.scriptName, listener.line, listener.script);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + ChatColor.GREEN + " �̺�Ʈ�� " + listener.line + "��°�ٿ� �߰��Ǿ����ϴ�.");
			}
			else if(listener.eventName.equals("eventedit"))
			{
				events.editEvents(listener.scriptName, listener.line, listener.script);
				event.getPlayer().sendMessage(ChatColor.YELLOW + listener.scriptName + " �̺�Ʈ�� " + ChatColor.GREEN + listener.line + "��°���� ����Ǿ����ϴ�.");
			}
			else
			{
				event.getPlayer().sendMessage(ChatColor.GREEN + "���ϴ� ��ġ�� ������ ��Ŭ�� �ϼ���.");
				return;
			}
			plugin.listener.removeListener(event.getPlayer().getName());
		}
	}
}
