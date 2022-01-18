package com.linmalu.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Data_Touch_Methods
{
	private LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> touchs = new LinkedHashMap<>(0);

	public void setTouchs(String worldName)
	{
		if(!touchs.containsKey(worldName))
			touchs.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
	}
	public void setTouchs(String worldName, String location)
	{
		if(touchs.containsKey(worldName))
		{
			if(!touchs.get(worldName).containsKey(location))
				touchs.get(worldName).put(location, new ArrayList<String>(0));
		}else{
			touchs.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
			touchs.get(worldName).put(location, new ArrayList<String>(0));
		}
	}
	public void setTouchs(String worldName, String location, String script)
	{
		if(touchs.containsKey(worldName))
		{
			if(touchs.get(worldName).containsKey(location))
				touchs.get(worldName).get(location).add(script);
			else{
				touchs.get(worldName).put(location, new ArrayList<String>(0));
				touchs.get(worldName).get(location).add(script);
			}
		}else{
			touchs.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
			touchs.get(worldName).put(location, new ArrayList<String>(0));
			touchs.get(worldName).get(location).add(script);
		}
	}
	public String[] getScripts(String worldName, String location)
	{
		return touchs.get(worldName).get(location).toArray(new String[touchs.get(worldName).get(location).size()]);
	}
	public String[] getWorldNames()
	{
		return touchs.keySet().toArray(new String[touchs.size()]);
	}
	public String[] getLocations(String worldName)
	{
		return touchs.get(worldName).keySet().toArray(new String[touchs.get(worldName).size()]);
	}
	public void clearTouchs()
	{
		for(String worldName : getWorldNames())
		{
			for(String location : getLocations(worldName))
				touchs.get(worldName).get(location).clear();
			touchs.get(worldName).clear();
		}
		touchs.clear();
	}
	public boolean equalsTouchs(String worldName, String location, int line)
	{
		if(touchs.containsKey(worldName))
			if(touchs.get(worldName).containsKey(location))
				if(line == 0 || touchs.get(worldName).get(location).size() >= line)
					return true;
		return false;
	}
	public void addTouchs(String worldName, String location, int line, String script)
	{
		touchs.get(worldName).get(location).add(line -1, script);
	}
	public void removeTouchs(String worldName, String location, int line)
	{
		touchs.get(worldName).get(location).remove(line -1);
	}
	public void editTouchs(String worldName, String location, int line, String script)
	{
		touchs.get(worldName).get(location).set(line -1, script);
	}
	public String[] viewTouchs(String worldName, String location)
	{
		return touchs.get(worldName).get(location).toArray(new String[touchs.get(worldName).get(location).size()]);
	}
	public void deleteTouchs(String worldName, String location)
	{
		touchs.get(worldName).get(location).clear();
		touchs.get(worldName).remove(location);
	}
}
