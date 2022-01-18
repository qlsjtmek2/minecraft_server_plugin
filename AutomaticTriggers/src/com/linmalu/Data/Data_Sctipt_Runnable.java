package com.linmalu.Data;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.linmalu.Main.AutomaticTriggers;

public class Data_Sctipt_Runnable implements Runnable
{
	private AutomaticTriggers plugin;
	private String args[];
	private String scripts[];
	private Player player;
	public String eventName;
	public CreatureSpawnEvent eventEntitySpawn = null;
	public EntityDeathEvent eventEntityDeath = null;
	public PlayerRespawnEvent eventPlayerRespawn = null;
	public EntityDeathEvent eventPlayerDeath = null;
	public PlayerJoinEvent eventJoin = null;
	public PlayerQuitEvent eventQuit = null;
	public PlayerKickEvent eventKick = null;
	public BlockBreakEvent eventBlockBreak =  null;
	public BlockPlaceEvent eventBlockPlaced = null;
	public PlayerInteractEntityEvent eventInteract = null;
	public AsyncPlayerChatEvent eventChat = null;
	public PlayerLevelChangeEvent  eventLevel = null;

	public Data_Sctipt_Runnable(AutomaticTriggers plugin, String args[], Player player, String eventName)
	{
		this.plugin = plugin;
		this.args = args;
		this.scripts = plugin.events.getScripts(eventName);
		this.player = player;
		this.eventName = eventName;
	}
	public Data_Sctipt_Runnable(AutomaticTriggers plugin, String args[], PlayerCommandPreprocessEvent event)
	{
		this.plugin = plugin;
		this.args = args;
		this.scripts = plugin.commands.getScripts(args[0]);
		this.player = event.getPlayer();
		this.eventName = "command";
	}
	public Data_Sctipt_Runnable(AutomaticTriggers plugin, String args[], Player player, String worldName, String location, String eventName)
	{
		this.plugin = plugin;
		this.args = args;
		this.player = player;
		this.eventName = eventName;
		if(eventName.equals("areaenter") || eventName.equals("areaexit"))
			this.scripts = plugin.areas.getScripts(worldName, location);
		else if(eventName.equals("touch"))
			this.scripts = plugin.touchs.getScripts(worldName, location);
		else if(eventName.equals("walk"))
			this.scripts = plugin.walks.getScripts(worldName, location);
	}
	public void run()
	{
		if(scripts.length != 0)
			plugin.scriptCommands.ScriptCommand(args, scripts, player, this);
	}
}
