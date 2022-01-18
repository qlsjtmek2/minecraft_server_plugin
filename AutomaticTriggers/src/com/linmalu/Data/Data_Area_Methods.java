package com.linmalu.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Data_Area_Methods
{
	private LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> areas = new LinkedHashMap<>(0);
	
	public void setAreas(String worldName)
	{
		if(!areas.containsKey(worldName))
			areas.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
	}
	public void setAreas(String worldName, String location)
	{
		if(areas.containsKey(worldName))
		{
			if(!areas.get(worldName).containsKey(location))
				areas.get(worldName).put(location, new ArrayList<String>(0));
		}else{
			areas.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
			areas.get(worldName).put(location, new ArrayList<String>(0));
		}
	}
	public void setAreas(String worldName, String location, String script)
	{
		if(areas.containsKey(worldName))
		{
			if(areas.get(worldName).containsKey(location))
				areas.get(worldName).get(location).add(script);
			else{
				areas.get(worldName).put(location, new ArrayList<String>(0));
				areas.get(worldName).get(location).add(script);
			}
		}else{
			areas.put(worldName, new LinkedHashMap<String, ArrayList<String>>(0));
			areas.get(worldName).put(location, new ArrayList<String>(0));
			areas.get(worldName).get(location).add(script);
		}
	}
	public String[] getWorldNames()
	{
		return areas.keySet().toArray(new String[areas.size()]);
	}
	public String[] getLocations(String worldName)
	{
		return areas.get(worldName).keySet().toArray(new String[areas.get(worldName).size()]);
	}
	public String[] getScripts(String worldName, String location)
	{
		if(location.split(",").length == 1)
			location = getAreaName(worldName, location);
		return areas.get(worldName).get(location).toArray(new String[areas.get(worldName).get(location).size()]);
	}
	public void clearAreas()
	{
		for(String worldName : getWorldNames())
		{
			for(String areaName : getLocations(worldName))
				areas.get(worldName).get(areaName).clear();
			areas.get(worldName).clear();
		}
		areas.clear();
	}
	public String getAreaName(String worldName, String areaName)
	{
		if(areas.containsKey(worldName))
			for(String location : getLocations(worldName))
				if(location.split(",")[0].equals(areaName))
					return location;
		return null;
	}
	public boolean equalsAreas(String worldName, String areaName, int line)
	{
		String location = getAreaName(worldName, areaName);
		if(location != null)
			if(line == 0 || areas.get(worldName).get(location).size() >= line)
				return true;
		return false;
	}
	public int[] intLocation(String[] location)
	{
		int num[] = new int[location.length];
		for(int i = 0; i < location.length; i++)
		{
			num[i] = Integer.parseInt(location[i]);
		}
		return num;
	}
	public String equalsLocation(String worldName, String locationName)
	{
		if(areas.containsKey(worldName))
		{
			int location[] = intLocation(locationName.split(","));
			for(String arealocation : getLocations(worldName))
			{
				int area[] = intLocation(arealocation.substring(arealocation.indexOf(",") +1).split(","));
				if((area[0] <= location[0] && location[0] <= area[3]) || (area[0] >= location[0] && location[0] >= area[3]))
					if((area[1] <= location[1] && location[1] <= area[4]) || (area[1] >= location[1] && location[1] >= area[4]))
						if((area[2] <= location[2] && location[2] <= area[5]) || (area[2] >= location[2] && location[2] >= area[5]))
							return arealocation;
			}
		}
		return null;
	}
	public void addAreas(String worldName, String areaName, int line, String script)
	{
		String location = getAreaName(worldName, areaName);
		areas.get(worldName).get(location).add(line -1, script);
	}
	public void removeAreas(String worldName, String areaName, int line)
	{
		String location = getAreaName(worldName, areaName);
		areas.get(worldName).get(location).remove(line -1);
	}
	public void editAreas(String worldName, String areaName, int line, String script)
	{
		String location = getAreaName(worldName, areaName);
		areas.get(worldName).get(location).set(line -1, script);
	}
	public String[] viewAreas(String worldName, String areaName)
	{
		String location = getAreaName(worldName, areaName);
		return areas.get(worldName).get(location).toArray(new String[areas.get(worldName).get(location).size()]);
	}
	public void deleteAreas(String worldName, String areaName)
	{
		String location = getAreaName(worldName, areaName);
		areas.get(worldName).get(location).clear();
		areas.get(worldName).remove(location);
	}
}
