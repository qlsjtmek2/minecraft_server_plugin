package com.linmalu.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Data_Script_Methods
{
	private LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> scripts = new LinkedHashMap<>(0);
	
	public void setScripts(String fileName)
	{
		if(!scripts.containsKey(fileName))
			scripts.put(fileName, new LinkedHashMap<String, ArrayList<String>>(0));
	}
	public void setScripts(String fileName, String scriptName)
	{
		if(scripts.containsKey(fileName))
		{
			if(!scripts.get(fileName).containsKey(scriptName))
				scripts.get(fileName).put(scriptName, new ArrayList<String>(0));
		}else{
			scripts.put(fileName, new LinkedHashMap<String, ArrayList<String>>(0));
			scripts.get(fileName).put(scriptName, new ArrayList<String>(0));
		}
	}
	public void setScripts(String fileName, String scriptName, String script)
	{
		if(scripts.containsKey(fileName))
		{
			if(scripts.get(fileName).containsKey(scriptName))
				scripts.get(fileName).get(scriptName).add(script);
			else{
				scripts.get(fileName).put(scriptName, new ArrayList<String>(0));
				scripts.get(fileName).get(scriptName).add(script);
			}
		}else{
			scripts.put(fileName, new LinkedHashMap<String, ArrayList<String>>(0));
			scripts.get(fileName).put(scriptName, new ArrayList<String>(0));
			scripts.get(fileName).get(scriptName).add(script);
		}
	}
	public String[] getFileNames()
	{
		return scripts.keySet().toArray(new String[scripts.size()]);
	}
	public String[] getScriptNames(String fileName)
	{
		return scripts.get(fileName).keySet().toArray(new String[scripts.get(fileName).size()]);
	}
	public String[] getScripts(String fileName, String scriptName)
	{
		return scripts.get(fileName).get(scriptName).toArray(new String[scripts.get(fileName).get(scriptName).size()]);
	}
	public boolean equalScripts(String fileName, String scriptName)
	{
		if(scripts.containsKey(fileName))
			if(scripts.get(fileName).containsKey(scriptName))
				return true;
		return false;
	}
	public void clearScripts()
	{
		for(String fileName : getFileNames())
		{
			for(String scriptName : getScriptNames(fileName))
				scripts.get(fileName).get(scriptName).clear();
			scripts.get(fileName).clear();
		}
		scripts.clear();
	}
}
