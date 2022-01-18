package com.linmalu.Listener;

import java.util.HashMap;

public class Listener_Methods
{
	private HashMap<String, Listener_Data> listener = new HashMap<>(0);

	public void setListener(String playerName, String scriptCommand, String script, int line, String eventName, boolean scriptLocation)
	{
		listener.put(playerName, new Listener_Data(scriptCommand, script, line, eventName, scriptLocation));
	}
	public void setListener(String playerName, String scriptCommand, String script, int line, String eventName, boolean scriptLocation, String scriptName)
	{
		listener.put(playerName, new Listener_Data(scriptCommand, script, line, eventName, scriptLocation, scriptName));
	}
	public Listener_Data getListener(String playerName)
	{
		if(listener.containsKey(playerName))
			return listener.get(playerName);
		return null;
	}
	public boolean equalsListener(String playerName)
	{
		if(listener.containsKey(playerName))
			return true;
		return false;
	}
	public void removeListener(String playerName)
	{
		listener.remove(playerName);
	}
	public void setLeftLocation(String playerName, String location, String worldName)
	{
		listener.get(playerName).leftLocation = location;
		listener.get(playerName).worldName = worldName;
	}
	public void setRightLocation(String playerName, String location, String worldName)
	{
		listener.get(playerName).rightLocation = location;
		listener.get(playerName).worldName = worldName;
	}
	public boolean containsLocation(String playerName)
	{
		if(listener.get(playerName).leftLocation != null && listener.get(playerName).rightLocation != null)
			return true;
		return false;
	}
}
