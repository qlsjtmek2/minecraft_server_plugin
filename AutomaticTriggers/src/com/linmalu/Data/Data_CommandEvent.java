package com.linmalu.Data;

import org.bukkit.entity.Player;

public class Data_CommandEvent
{
	public Player player;
	public String name;
	public String script;
	public int line;

	public Data_CommandEvent(Player player, String name, String script, int line)
	{
		this.player = player;
		this.name = name;
		this.script = script;
		this.line = line;
	}
}
