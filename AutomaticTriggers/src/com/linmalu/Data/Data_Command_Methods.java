package com.linmalu.Data;

import java.util.LinkedHashMap;

public class Data_Command_Methods {
	private LinkedHashMap<String, Data_Command> commands = new LinkedHashMap<>(0);

	public void putCommands(String key, Data_Command value)
	{
		commands.put(key, value);
	}
	public Data_Command getCommands(String key)
	{
		return commands.get(key);
	}
	public boolean equalsCommands(String key, int line)
	{
		if(commands.containsKey(key))
			if(line == 0 || commands.get(key).script.size() >= line)
				return true;
		return false;
	}
	public void removeCommands(String key)
	{
		commands.remove(key);
	}
	public void clearCommands()
	{
		for(String key : getKeyCommands())
			commands.get(key).script.clear();
		commands.clear();
	}
	public String[] getKeyCommands()
	{
		return commands.keySet().toArray(new String[commands.keySet().toArray().length]);
	}
	public void setOverride(String key, boolean override)
	{
		commands.get(key).override = override;
	}
	public boolean getOverride(String key)
	{
		return commands.get(key).override;
	}
	public void addScripts(String key, String script)
	{
		commands.get(key).script.add(script);
	}
	public void addScripts(String key, String script, int line)
	{
		commands.get(key).script.add(line -1, script);
	}
	public void editScripts(String key, String script, int line)
	{
		commands.get(key).script.set(line -1, script);
	}
	public String[] getScripts(String key)
	{
		return commands.get(key).script.toArray(new String[commands.get(key).script.size()]);
	}
	public void removeScripts(String key, int line)
	{
		commands.get(key).script.remove(line -1);
	}
}
