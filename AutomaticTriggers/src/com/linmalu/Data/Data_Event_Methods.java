package com.linmalu.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Data_Event_Methods
{
	private LinkedHashMap<String, ArrayList<String>> events = new LinkedHashMap<>(0);

	public Data_Event_Methods()
	{
		events.put("EntitySpawn", new ArrayList<String>(0));
		events.put("EntityDeath", new ArrayList<String>(0));
		events.put("PlayerRespawn", new ArrayList<String>(0));
		events.put("PlayerDeath", new ArrayList<String>(0));
		events.put("Join", new ArrayList<String>(0));
		events.put("Quit", new ArrayList<String>(0));
		events.put("Kick", new ArrayList<String>(0));
		events.put("BlockBreak", new ArrayList<String>(0));
		events.put("BlockPlaced", new ArrayList<String>(0));
		events.put("Interact", new ArrayList<String>(0));
		events.put("Level", new ArrayList<String>(0));
		events.put("Chat", new ArrayList<String>(0));
		events.put("Timer", new ArrayList<String>(0));
	}
	public void setEvent(String eventName, String script)
	{
		if(events.containsKey(eventName))
			events.get(eventName).add(script);
	}
	public String[] getScripts(String eventName)
	{
		return events.get(eventName).toArray(new String[events.get(eventName).size()]);
	}
	public String[] getEventNames()
	{
		return events.keySet().toArray(new String[events.size()]);
	}
	public void clearEvents()
	{
		for(String eventName : getEventNames())
			events.get(eventName).clear();
	}
	public boolean equalsEvents(String eventName, int line)
	{
		if(events.containsKey(eventName))
			if(line == 0 || events.get(eventName).size() >= line)
				return true;
		return false;
	}
	public void addEvents(String eventName, int line, String script)
	{
		events.get(eventName).add(line -1, script);
	}
	public void removeEvents(String eventName, int line)
	{
		events.get(eventName).remove(line -1);
	}
	public void editEvents(String eventName, int line, String script)
	{
		events.get(eventName).set(line -1, script);
	}
	public String[] viewEvents(String eventName)
	{
		return events.get(eventName).toArray(new String[events.get(eventName).size()]);
	}
	public void deleteEvents(String eventName)
	{
		events.get(eventName).clear();
	}
}
