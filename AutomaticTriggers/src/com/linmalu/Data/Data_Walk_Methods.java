package com.linmalu.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Data_Walk_Methods
{
	private LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> walks = new LinkedHashMap<>(0);

	public void setWalks(String worldName)
	{
		if(!walks.containsKey(worldName))
			walks.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
	}
	public void setWalks(String worldName, String location)
	{
		if(walks.containsKey(worldName))
		{
			if(!walks.get(worldName).containsKey(location))
				walks.get(worldName).put(location, new ArrayList<String>(0));
		}else{
			walks.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
			walks.get(worldName).put(location, new ArrayList<String>(0));
		}
	}
	public void setWalks(String worldName, String location, String script)
	{
		if(walks.containsKey(worldName))
		{
			if(walks.get(worldName).containsKey(location))
				walks.get(worldName).get(location).add(script);
			else{
				walks.get(worldName).put(location, new ArrayList<String>(0));
				walks.get(worldName).get(location).add(script);
			}
		}else{
			walks.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
			walks.get(worldName).put(location, new ArrayList<String>(0));
			walks.get(worldName).get(location).add(script);
		}
	}
	public String[] getWorldNames()
	{
		return walks.keySet().toArray(new String[walks.size()]);
	}
	public String[] getLocations(String worldName)
	{
		return walks.get(worldName).keySet().toArray(new String[walks.get(worldName).size()]);
	}
	public String[] getScripts(String worldName, String location)
	{
		return walks.get(worldName).get(location).toArray(new String[walks.get(worldName).get(location).size()]);
	}
	public void clearWalks()
	{
		for(String worldName : getWorldNames())
		{
			for(String location : getLocations(worldName))
				walks.get(worldName).get(location).clear();
			walks.get(worldName).clear();
		}
		walks.clear();
	}
	public boolean equalsWalks(String worldName, String location, int line)
	{
		if(walks.containsKey(worldName))
			if(walks.get(worldName).containsKey(location))
				if(walks.get(worldName).get(location).size() >= line)
					return true;
		return false;
	}
	public void addWalks(String worldName, String location, int line, String script)
	{
		walks.get(worldName).get(location).add(line -1, script);
	}
	public void removeWalks(String worldName, String location, int line)
	{
		walks.get(worldName).get(location).remove(line -1);
	}
	public void editWalks(String worldName, String location, int line, String script)
	{
		walks.get(worldName).get(location).set(line -1, script);
	}
	public String[] viewWalks(String worldName, String location)
	{
		return walks.get(worldName).get(location).toArray(new String[walks.get(worldName).get(location).size()]);
	}
	public void deleteWalks(String worldName, String location)
	{
		walks.get(worldName).get(location).clear();
		walks.get(worldName).remove(location);
	}
}
