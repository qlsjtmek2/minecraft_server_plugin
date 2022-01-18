package com.linmalu.Data;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Data_ScriptVariable_Methods
{
	private HashMap<String, String> scriptVariables = new HashMap<String, String>(0);

	public String ScriptVariable(String script, Player player)
	{
		int prefixIndex = script.length();
		int suffixIndex = script.length();
		while(true)
		{
			prefixIndex = script.lastIndexOf("<", prefixIndex - 1);
			suffixIndex = script.indexOf(">", prefixIndex) + 1;
			if(prefixIndex == -1 || suffixIndex == 0)
				break;
			script = script.substring(0, prefixIndex) + ScriptVariables(script.substring(prefixIndex, suffixIndex), player) + script.substring(suffixIndex);
		}
		return script;
	}
	public String ScriptVariables(String script, Player player)
	{
		script = script.replace("<", "").replace(">", "").replace("{playername}", player.getName());
		return getScriptVariables(script);
	}
	public void putScriptVariables(String key, String value)
	{
		scriptVariables.put(key, value);
	}
	public String getScriptVariables(String key)
	{
		if(scriptVariables.containsKey(key))
			return scriptVariables.get(key);
		return String.valueOf(0);
	}
	public boolean equalsScriptVariables(String key)
	{
		return scriptVariables.containsKey(key);
	}
	public void removeScriptVariables(String key)
	{
		scriptVariables.remove(key);
	}
	public void clearScriptVariables()
	{
		scriptVariables.clear();
	}
	public String[] getKeyScriptVariables()
	{
		String keys[] = new String[scriptVariables.keySet().toArray().length];
		for(int i = 0; i < scriptVariables.keySet().toArray().length; i++)
			keys[i] = (String) scriptVariables.keySet().toArray()[i];
		return keys;
	}
}
