package me.espoo.ptk;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public static PACommand command2;
	Timer timer = new Timer();
	
	public void onEnable()
    {
		saveDefaultConfig();
		reloadConfig();
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PAEvent(this), this);
	    command2 = new PACommand(this);
		getCommand("대련").setExecutor(command2);
		try {
			main.command2.CreateAllowCommand();
		} catch (IOException ex) {
			Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
		}
		for (Player ap : Bukkit.getOnlinePlayers()) {
			main.command2.PVPTime.put(ap.getName(), "");
		}
		main.command2.blue = "";
		main.command2.red = "";
		main.command2.Start = false;
		this.timer.schedule(new TimeA(), 1000L, 1000L);
    }
	
	public void onDisable()
	{
		this.timer.cancel();
	}
	
	class TimeA extends TimerTask
	{
	    int time;
	    int lasttime;
	    int GodTimes;
	    int PVPTime;
	    int PVPAcceptTime;
	    double Spawn1x;
	    double Spawn1y;
	    double Spawn1z;
	    double Spawn2x;
	    double Spawn2y;
	    double Spawn2z;
	    double Spawn3x;
	    double Spawn3y;
	    double Spawn3z;
	    float Spawn1yaw;
	    float Spawn1pitch;
	    float Spawn2yaw;
	    float Spawn2pitch;
	    float Spawn3yaw;
	    float Spawn3pitch;
	    World Spawn1World;
	    World Spawn2World;
	    World Spawn3World;
	    String[] Spawn1 = main.this.getConfig().getString("PATele1").split(", ");
	    String[] Spawn2 = main.this.getConfig().getString("PATele2").split(", ");
	    String[] Spawn3 = main.this.getConfig().getString("PATele4").split(", ");

	    TimeA() { 
	    	this.time = 300;
	    	this.lasttime = main.this.getConfig().getInt("PVPLastExitTime");
	    	this.PVPTime = main.this.getConfig().getInt("PVPTime");
	    	this.GodTimes = main.this.getConfig().getInt("PVPGodTime");
	    	this.PVPAcceptTime = main.this.getConfig().getInt("PVPAcceptTime");
	    	this.Spawn1x = Double.parseDouble(this.Spawn1[0]);
	    	this.Spawn1y = Double.parseDouble(this.Spawn1[1]);
	    	this.Spawn1z = Double.parseDouble(this.Spawn1[2]);
	    	this.Spawn1yaw = Float.parseFloat(this.Spawn1[3]);
	    	this.Spawn1pitch = Float.parseFloat(this.Spawn1[4]);
	    	this.Spawn2x = Double.parseDouble(this.Spawn2[0]);
	    	this.Spawn2y = Double.parseDouble(this.Spawn2[1]);
	    	this.Spawn2z = Double.parseDouble(this.Spawn2[2]);
	    	this.Spawn2yaw = Float.parseFloat(this.Spawn2[3]);
	    	this.Spawn2pitch = Float.parseFloat(this.Spawn2[4]);
	    	this.Spawn3x = Double.parseDouble(this.Spawn3[0]);
	    	this.Spawn3y = Double.parseDouble(this.Spawn3[1]);
	    	this.Spawn3z = Double.parseDouble(this.Spawn3[2]);
	    	this.Spawn3yaw = Float.parseFloat(this.Spawn3[3]);
	    	this.Spawn3pitch = Float.parseFloat(this.Spawn3[4]);
	    	this.Spawn1World = Bukkit.getWorld(this.Spawn1[5]);
	    	this.Spawn2World = Bukkit.getWorld(this.Spawn2[5]);
	    	this.Spawn3World = Bukkit.getWorld(this.Spawn3[5]);
	    	
	    }
	    @SuppressWarnings("unused")
		public void run()
	    {
	    	int ap;
	    	String[] a;
	    	if (!main.command2.Start) {
	        for (Player ap1 : Bukkit.getOnlinePlayers()) {
	        	a = ((String)main.command2.PVPTime.get(ap1.getName())).split(", ");
	        	if ((a[0] != null) && (a[0] != ""))
	        		if (a[2].equals("1")) {
	        			if (a[0].equals("PVPAcceptWaitP")) {
	        				ap1.sendMessage("§c" + this.PVPAcceptTime + "§6초가 지나 자동으로 §c대전 신청§6이§c 취소§6 되었습니다.");
	        				main.command2.PVPTime.put(ap1.getName(), "");
	        			} else if (a[0].equals("PVPAcceptWait")) {
	        				ap1.sendMessage("§c" + this.PVPAcceptTime + "§6초가 지나 자동으로 §c대전 신청§6이§c 취소§6 되었습니다.");
	        				main.command2.PVPTime.put(ap1.getName(), "");
	        			}
	        			main.command2.PVPTime.put(ap1.getName(), a[0] + ", " + a[1] + ", 0");
	        		} else if (Integer.parseInt(a[2]) > 1) {
	        			main.command2.PVPTime.put(ap1.getName(), a[0] + ", " + a[1] + ", " + (Integer.parseInt(a[2]) - 1));
	        		} else if (Integer.parseInt(a[2]) == 0) {
	        			main.command2.PVPTime.put(ap1.getName(), "");
	        		}
	        	}
	    	}
	    	else {
	    		Player red = Bukkit.getPlayerExact(main.command2.red);
	    		Player blue = Bukkit.getPlayerExact(main.command2.blue);
	    		Player[] arrayOfPlayer2 = Bukkit.getOnlinePlayers(); ap = arrayOfPlayer2.length; for (int a1 = 0; a1 < ap; a1++) { Player ap1 = arrayOfPlayer2[a1];
	    		String[] a11 = ((String)main.command2.PVPTime.get(ap1.getName())).split(", ");
	    		if ((a11[0] != null) && (a11[0] != ""))
	    			if (a11[2].equals("1")) {
	    				if (a11[0].equals("PVPFirstStartTime")) {
	    					Location loc1 = new Location(this.Spawn1World, this.Spawn1x, this.Spawn1y, this.Spawn1z, this.Spawn1yaw, this.Spawn1pitch);
			                ap1.teleport(loc1);
			                ap1.sendMessage("§c" + this.GodTimes + "§6초 동안 움직일 수 §c없습니다§6.");
			                main.command2.GodTime = true;
			                main.command2.PVPTime.put(ap1.getName(), "GODTime, " + a11[1] + ", " + (this.GodTimes + 1));
	    				} else if (a11[0].equals("PVPFirstStartTimeP")) {
	    					Location loc2 = new Location(this.Spawn2World, this.Spawn2x, this.Spawn2y, this.Spawn2z, this.Spawn2yaw, this.Spawn2pitch);
			                ap1.teleport(loc2);
			                ap1.sendMessage("§c" + this.GodTimes + "§6초 동안 움직일 수 §c없습니다§6.");
			                main.command2.GodTime = true;
			                main.command2.PVPTime.put(ap1.getName(), "GODTimeP, " + a11[1] + ", " + (this.GodTimes + 1));
	    				} else if (a11[0].equals("GODTime")) {
	    					ap1.sendMessage("§6대전 시간 §c" + this.PVPTime + "§6초가 주어졌습니다.");
			                main.command2.GodTime = false;
			                main.command2.PVPTime.put(ap1.getName(), "PVPStartTime, " + a11[1] + ", " + (this.PVPTime + 1));
	    				} else if (a11[0].equals("GODTimeP")) {
	    					ap1.sendMessage("§6대전 시간 §c" + this.PVPTime + "§6초가 주어졌습니다.");
			                main.command2.GodTime = false;
			                main.command2.PVPTime.put(ap1.getName(), "PVPStartTimeP, " + a11[1] + ", " + (this.PVPTime + 1));
	    				} else if (a11[0].equals("PVPStartTime")) {
	    					ap1.sendMessage("§c대전 시간§6이 §c종료§6되었습니다.");
			                ap1.sendMessage("§c" + this.lasttime + "§6초뒤 스폰으로 §c자동 이동§6됩니다.");
			                main.command2.PVPTime.put(ap1.getName(), "LastExitTime, " + a11[1] + ", " + (this.lasttime + 1));
	    				} else if (a11[0].equals("PVPStartTimeP")) {
	    					ap1.sendMessage("§c대전 시간§6이 §c종료§6되었습니다.");
			                ap1.sendMessage("§c" + this.lasttime + "§6초뒤 스폰으로 §c자동 이동§6됩니다.");
			                main.command2.PVPTime.put(ap1.getName(), "LastExitTimeP, " + a11[1] + ", " + (this.lasttime + 1));
	    				} else if (a11[0].equals("LastExitTime")) {
	    					Location loc1 = new Location(this.Spawn3World, this.Spawn3x, this.Spawn3y, this.Spawn3z, this.Spawn3yaw, this.Spawn3pitch);
			                ap1.teleport(loc1);
			                main.command2.Start = false;
			                main.command2.blue = "";
			                main.command2.red = "";
	    				} else if (a11[0].equals("LastExitTimeP")) {
	    					Location loc1 = new Location(this.Spawn3World, this.Spawn3x, this.Spawn3y, this.Spawn3z, this.Spawn3yaw, this.Spawn3pitch);
			                ap1.teleport(loc1);
			                main.command2.Start = false;
			                main.command2.blue = "";
			                main.command2.red = "";
	    				}
	    			} else if (Integer.parseInt(a11[2]) > 1)
	    				main.command2.PVPTime.put(ap1.getName(), a11[0] + ", " + a11[1] + ", " + (Integer.parseInt(a11[2]) - 1));
	    			else if (Integer.parseInt(a11[2]) == 0)
	    				main.command2.PVPTime.put(ap1.getName(), "");
	    		}
	    	}
	    }
	}
}
