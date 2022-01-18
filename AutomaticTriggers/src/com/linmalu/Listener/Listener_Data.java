package com.linmalu.Listener;

public class Listener_Data
{
	public String scriptCommand;
	public String script;
	public int line;
	public String eventName;
	public boolean scriptLocation;
	public String scriptName = null;
	public String leftLocation = null;
	public String rightLocation = null;
	public String worldName = null;
	public Listener_Data(String scriptCommand, String script, int line, String eventName, boolean scriptLocation)
	{
		this.scriptCommand = scriptCommand;
		this.script = script;
		this.line = line;
		this.eventName = eventName;
		this.scriptLocation = scriptLocation;
	}
	public Listener_Data(String scriptCommand, String script, int line, String eventName, boolean scriptLocation, String scriptName)
	{
		this.scriptCommand = scriptCommand;
		this.script = script;
		this.line = line;
		this.eventName = eventName;
		this.scriptLocation = scriptLocation;
		this.scriptName = scriptName;
	}
}
