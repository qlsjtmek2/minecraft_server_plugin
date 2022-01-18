package com.linmalu.Main;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import com.linmalu.Command.ATarea;
import com.linmalu.Command.ATareaadd;
import com.linmalu.Command.ATareadelete;
import com.linmalu.Command.ATareaedit;
import com.linmalu.Command.ATarearemove;
import com.linmalu.Command.ATareaset;
import com.linmalu.Command.ATareaview;
import com.linmalu.Command.ATcommand;
import com.linmalu.Command.ATcommandadd;
import com.linmalu.Command.ATcommanddelete;
import com.linmalu.Command.ATcommandedit;
import com.linmalu.Command.ATcommandoverride;
import com.linmalu.Command.ATcommandremove;
import com.linmalu.Command.ATcommandview;
import com.linmalu.Command.ATevent;
import com.linmalu.Command.ATeventadd;
import com.linmalu.Command.ATeventdelete;
import com.linmalu.Command.ATeventedit;
import com.linmalu.Command.ATeventremove;
import com.linmalu.Command.ATeventview;
import com.linmalu.Command.ATrigger;
import com.linmalu.Command.ATtouch;
import com.linmalu.Command.ATtouchadd;
import com.linmalu.Command.ATtouchdelete;
import com.linmalu.Command.ATtouchedit;
import com.linmalu.Command.ATtouchremove;
import com.linmalu.Command.ATtouchview;
import com.linmalu.Command.ATwalk;
import com.linmalu.Command.ATwalkadd;
import com.linmalu.Command.ATwalkdelete;
import com.linmalu.Command.ATwalkedit;
import com.linmalu.Command.ATwalkremove;
import com.linmalu.Command.ATwalkview;
import com.linmalu.Config.Config_Methods;
import com.linmalu.Data.Data_Area_Methods;
import com.linmalu.Data.Data_CommandEvent;
import com.linmalu.Data.Data_Command_Methods;
import com.linmalu.Data.Data_Cooldown_Methods;
import com.linmalu.Data.Data_Event_Methods;
import com.linmalu.Data.Data_ScriptCommand_Methods;
import com.linmalu.Data.Data_ScriptField_Methods;
import com.linmalu.Data.Data_ScriptVariable_Methods;
import com.linmalu.Data.Data_Script_Methods;
import com.linmalu.Data.Data_Sctipt_Runnable;
import com.linmalu.Data.Data_Touch_Methods;
import com.linmalu.Data.Data_Walk_Methods;
import com.linmalu.Listener.Listener_Areas;
import com.linmalu.Listener.Listener_Commands;
import com.linmalu.Listener.Listener_Events;
import com.linmalu.Listener.Listener_Locations;
import com.linmalu.Listener.Listener_Methods;
import com.linmalu.Listener.Listener_Touchs;
import com.linmalu.Listener.Listener_Walks;

public final class AutomaticTriggers extends JavaPlugin
{
	public ArrayList<Data_CommandEvent> commandEventData = new ArrayList<Data_CommandEvent>(0);
	public Data_ScriptCommand_Methods scriptCommands;
	public Data_ScriptField_Methods scriptFields;
	public Data_ScriptVariable_Methods scriptVariables;
	public Data_Area_Methods areas;
	public Data_Command_Methods commands;
	public Data_Event_Methods events;
	public Data_Touch_Methods touchs;
	public Data_Walk_Methods walks;
	public Data_Script_Methods scripts;
	public Data_Cooldown_Methods cooldown;
	public Listener_Methods listener; 
	public Config_Methods config;
	public Vault vault;

	public void onEnable()
	{
		getLogger().info("플러그인 불러오는중");
		scriptCommands = new Data_ScriptCommand_Methods(this);
		scriptFields = new Data_ScriptField_Methods(this);
		scriptVariables = new Data_ScriptVariable_Methods();
		areas = new Data_Area_Methods();
		commands = new Data_Command_Methods();
		events = new Data_Event_Methods();
		touchs = new Data_Touch_Methods();
		walks = new Data_Walk_Methods();
		scripts = new Data_Script_Methods();
		cooldown = new Data_Cooldown_Methods();
		listener = new Listener_Methods();
		saveDefaultConfig();
		config = new Config_Methods(this);
		config.ReloadConfig();
		config.ReloadTriggers();
		saveResource("ex.script.yml", true);
		vault = new Vault(this);
		//이벤트
		getServer().getPluginManager().registerEvents(new Listener_Areas(this), this);
		getServer().getPluginManager().registerEvents(new Listener_Commands(this), this);
		getServer().getPluginManager().registerEvents(new Listener_Events(this), this);
		getServer().getPluginManager().registerEvents(new Listener_Touchs(this), this);
		getServer().getPluginManager().registerEvents(new Listener_Walks(this), this);
		getServer().getPluginManager().registerEvents(new Listener_Locations(this), this);
		//기본명령어
		getCommand("atrigger").setExecutor(new ATrigger(this));
		//공간
		getCommand("atarea").setExecutor(new ATarea(this));
		getCommand("atareaset").setExecutor(new ATareaset(this));
		getCommand("atareaadd").setExecutor(new ATareaadd(this));
		getCommand("atarearemove").setExecutor(new ATarearemove(this));
		getCommand("atareaedit").setExecutor(new ATareaedit(this));
		getCommand("atareaview").setExecutor(new ATareaview(this));
		getCommand("atareadelete").setExecutor(new ATareadelete(this));
		//명령어
		getCommand("atcommand").setExecutor(new ATcommand(this));
		getCommand("atcommandoverride").setExecutor(new ATcommandoverride(this));
		getCommand("atcommandadd").setExecutor(new ATcommandadd(this));
		getCommand("atcommandremove").setExecutor(new ATcommandremove(this));
		getCommand("atcommandedit").setExecutor(new ATcommandedit(this));
		getCommand("atcommandview").setExecutor(new ATcommandview(this));
		getCommand("atcommanddelete").setExecutor(new ATcommanddelete(this));
		//이벤트
		getCommand("atevent").setExecutor(new ATevent(this));
		getCommand("ateventadd").setExecutor(new ATeventadd(this));
		getCommand("ateventremove").setExecutor(new ATeventremove(this));
		getCommand("ateventedit").setExecutor(new ATeventedit(this));
		getCommand("ateventview").setExecutor(new ATeventview(this));
		getCommand("ateventdelete").setExecutor(new ATeventdelete(this));
		//터치
		getCommand("attouch").setExecutor(new ATtouch(this));
		getCommand("attouchadd").setExecutor(new ATtouchadd(this));
		getCommand("attouchremove").setExecutor(new ATtouchremove(this));
		getCommand("attouchedit").setExecutor(new ATtouchedit(this));
		getCommand("attouchview").setExecutor(new ATtouchview(this));
		getCommand("attouchdelete").setExecutor(new ATtouchdelete(this));
		//발판
		getCommand("atwalk").setExecutor(new ATwalk(this));
		getCommand("atwalkadd").setExecutor(new ATwalkadd(this));
		getCommand("atwalkremove").setExecutor(new ATwalkremove(this));
		getCommand("atwalkedit").setExecutor(new ATwalkedit(this));
		getCommand("atwalkview").setExecutor(new ATwalkview(this));
		getCommand("atwalkdelete").setExecutor(new ATwalkdelete(this));
		//스케줄
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				config.AutoSave();
				Timer();
			}}, 20L,20L);
		getLogger().info("플러그인 불러오기완료");
	}
	public void onDisable()
	{
		getLogger().info("플러그인 저장중");
		config.SaveTriggers();
		//		Bukkit.getScheduler().cancelTasks(this);
		getLogger().info("플러그인 저장완료");
	}
	public String ScriptLine(String args[], int line)
	{
		String script = "";
		for(int i = line; i < args.length; i++)
		{
			if(i == line)
				script += args[i];
			else
				script += " " + args[i];
		}
		return script;
	}
	private void Timer()
	{
		new Thread(new Data_Sctipt_Runnable(this, new String[]{"Timer"}, null, "Timer")).start();
	}
}