package com.linmalu.Config;

import com.linmalu.Main.AutomaticTriggers;

public class Config_Methods
{
	private AutomaticTriggers plugin;
	private Config_Areas config_Area;
	private Config_Commands config_Command;
	private Config_Events config_Event;
	private Config_Touchs config_Touch;
	private Config_Walks config_Walk;
	private Config_Variables config_Variable;
	private Config_Scripts config_Script;
	public int wandID;
	public int autoSave;
	public boolean overrideLog;
	public boolean errorLog;
	public int whileCount;
	public boolean block;
	public boolean autoSaveStart = false;

	public Config_Methods(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		config_Area = new Config_Areas(plugin);
		config_Command = new Config_Commands(plugin);
		config_Event = new Config_Events(plugin);
		config_Touch = new Config_Touchs(plugin);
		config_Walk = new Config_Walks(plugin);
		config_Variable = new Config_Variables(plugin);
		config_Script = new Config_Scripts(plugin);
	}
	public void AutoSave()
	{
		if(autoSaveStart)
			if(autoSave > 0)
				autoSave--;
			else
			{
				SaveTriggers();
				autoSave = plugin.getConfig().getInt("옵션.자동저장");
				plugin.getLogger().info("자동저장완료");
			}
	}
	public void ReloadConfig()
	{
		plugin.reloadConfig();
		wandID = plugin.getConfig().getInt("옵션.도구");
		autoSave = plugin.getConfig().getInt("옵션.자동저장");
		overrideLog = plugin.getConfig().getBoolean("옵션.덮어쓰기로그");
		errorLog = plugin.getConfig().getBoolean("옵션.에러로그");
		whileCount = plugin.getConfig().getInt("옵션.while제한횟수");
		block = plugin.getConfig().getBoolean("옵션.블럭보호");
		if(autoSave > 0)
			autoSaveStart = true;
		else
			autoSaveStart = false;
		plugin.getLogger().info("config.yml 불러오기 완료");
	}
	public void SaveTriggers()
	{
		config_Area.SaveArea();
		config_Command.SaveCommand();
		config_Event.SaveEvent();
		config_Touch.SaveTouch();
		config_Walk.SaveWalk();
		config_Variable.SaveVariable();
	}
	public void ReloadTriggers()
	{
		config_Area.ReloadArea();
		config_Command.ReloadCommand();
		config_Event.ReloadEvent();
		config_Touch.ReloadTouch();
		config_Walk.ReloadWalk();
		config_Variable.ReloadVariable();
		config_Script.ReloadScript();
	}
}
