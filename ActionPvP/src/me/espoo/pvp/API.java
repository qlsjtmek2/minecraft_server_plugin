package me.espoo.pvp;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.espoo.pvp.yml.PVPPlayer;

public class API {
    public static Map<String, PVPPlayer> pvpplayer;
    
    static {
        API.pvpplayer = new HashMap<String, PVPPlayer>();
    }
    
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
	
	// 2-2:2, 3-3:3, 4-4:4
	public static void deletePartyTimerMove(String user, int i) {
		Integer id = main.Timer.remove(Integer.toString(i) + ". Don't Move " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	// 2-2:2, 3-3:3, 4-4:4
	public static void deletePartyTimerStart(String user, int i) {
		Integer id = main.Timer.remove(Integer.toString(i) + ". PvP Start " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	// 2-2:2, 3-3:3, 4-4:4
	public static void deletePartyTimerFinish(String user, int i) {
		Integer id = main.Timer.remove(Integer.toString(i) + ". PvP Finish " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	// 1-1:1, 5-콜로세움
	public static void deleteUserTimerMove(int i) {
		Integer id = main.Timer.remove(Integer.toString(i) + ". Don't Move");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	// 1-1:1, 5-콜로세움
	public static void deleteUserTimerStart(int i) {
		Integer id = main.Timer.remove(Integer.toString(i) + ". PvP Start");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	// 1-1:1, 5-콜로세움
	public static void deleteUserTimerFinish(int i) {
		Integer id = main.Timer.remove(Integer.toString(i) + ". PvP Finish");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public static void oneStopAPI(Player p1, Player p2) {
		if (!p1.getWorld().getName().equalsIgnoreCase("world_pvp") && !p2.getWorld().getName().equalsIgnoreCase("world_pvp")) {
			Integer id = main.Timer.remove("1. Don't Move");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			id = main.Timer.remove("1. PvP Start");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			id = main.Timer.remove("1. PvP Finish");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		}
	}
	
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
	
	// 0: X, 1: Y, 2: Z, 3: World, 4: Yaw, 5: Pitch
	public static String LocationToString(Location loc) {
	    return String.valueOf(loc.getX()) + "," + String.valueOf(loc.getY()) + "," + String.valueOf(loc.getZ()) + "," + loc.getWorld().getName() + "," + loc.getYaw() + "," + loc.getPitch();
	}
	
	public static Location StringToLocation(String s) {
		String[] loc = s.split(",");
		Location loc2 = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
		return loc2;
	}
	
	// 1-1:1, 2-2:2, 3-3:3, 4-4:4, 5-콜로세움
	public static String getEnemyName(String p) {
		if (main.oneName == null) return null;
		if (main.oneName.split(",")[0].equalsIgnoreCase(p)) return main.oneName.split(",")[1];
		else if (main.oneName.split(",")[1].equalsIgnoreCase(p)) return main.oneName.split(",")[0];
		else return null;
	}
	
	public static void setChangeName(String p, String p2) {
		if (main.oneName.split(",")[0].equalsIgnoreCase(p)) {
			main.oneName = p2 + "," + main.oneName.split(",")[1];
		}
		
		else if (main.oneName.split(",")[1].equalsIgnoreCase(p)) {
			main.oneName = main.oneName.split(",")[0] + "," + p2;
		}
	}
	
	public static boolean isOneVSMatch(Player p) {
		if (main.oneList.contains(p)) return true;
		return false;
	}
	
	public static boolean isUserVSMatch(Player p) {
		if (API.isOneVSMatch(p)) return true;
		return false;
	}
	
	public static boolean isOneVSPlaying(String p) {
		String name = main.oneName;
		if (name == null) return false;
		
		if (name.split(",")[0].equalsIgnoreCase(p) || name.split(",")[1].equalsIgnoreCase(p)) return true;
		return false;
	}
	
	public static boolean isUserVSPlaying(String p) {
		if (API.isOneVSPlaying(p)) return true;
		return false;
	}
	
	public static int getPvPGotTime() {
		return Config.getInt("대련 설정.무적 시간 (초)");
	}
	
	public static int getPvPTime() {
		return Config.getInt("대련 설정.대련 시간 (초)");
	}
	
	public static int getPvPFinishTime() {
		return Config.getInt("대련 설정.마무리 시간 (초)");
	}
	
	public static int getColoGotTime() {
		return Config.getInt("콜로세움 설정.무적 시간 (초)");
	}
	
	public static int getColoTime() {
		return Config.getInt("콜로세움 설정.대련 시간 (초)");
	}
	
	public static int getColoFinishTime() {
		return Config.getInt("콜로세움 설정.마무리 시간 (초)");
	}
	
    public static double getDotsecond(final double value) {
        return Double.valueOf(Math.round(value * 100.0)) / 100.0;
    }
	
    public static boolean isUnderClass(String playerclass, String compareclass) {
    	if (compareclass.equalsIgnoreCase("F")) return false;
    	else if (compareclass.equalsIgnoreCase("E")) {
    		if (playerclass.equalsIgnoreCase("F")) return true;
    		else return false;
    	} else if (compareclass.equalsIgnoreCase("D")) {
    		if (playerclass.equalsIgnoreCase("F") || playerclass.equalsIgnoreCase("E")) return true;
    		else return false;
    	} else if (compareclass.equalsIgnoreCase("C")) {
    		if (playerclass.equalsIgnoreCase("F") || playerclass.equalsIgnoreCase("E") || playerclass.equalsIgnoreCase("D")) return true;
    		else return false;
    	} else if (compareclass.equalsIgnoreCase("B")) {
    		if (playerclass.equalsIgnoreCase("F") || playerclass.equalsIgnoreCase("E") || playerclass.equalsIgnoreCase("D") || playerclass.equalsIgnoreCase("C")) return true;
    		else return false;
    	} else if (compareclass.equalsIgnoreCase("A")) {
    		if (playerclass.equalsIgnoreCase("F") || playerclass.equalsIgnoreCase("E") || playerclass.equalsIgnoreCase("D") || playerclass.equalsIgnoreCase("C") || playerclass.equalsIgnoreCase("B")) return true;
    		else return false;
    	} else if (compareclass.equalsIgnoreCase("S")) {
    		if (playerclass.equalsIgnoreCase("F") || playerclass.equalsIgnoreCase("E") || playerclass.equalsIgnoreCase("D") || playerclass.equalsIgnoreCase("C") || playerclass.equalsIgnoreCase("B") || playerclass.equalsIgnoreCase("A")) return true;
    		else return false;
    	} else if (compareclass.equalsIgnoreCase("SS")) {
    		if (playerclass.equalsIgnoreCase("F") || playerclass.equalsIgnoreCase("E") || playerclass.equalsIgnoreCase("D") || playerclass.equalsIgnoreCase("C") || playerclass.equalsIgnoreCase("B") || playerclass.equalsIgnoreCase("A") || playerclass.equalsIgnoreCase("S")) return true;
    		else return false;
    	} else return false;
    }
	
    public static void Upgrade(Player p) {
		PVPPlayer pp = API.getPVPPlayera(p.getName());
        int kill = pp.getKill();
        String Class = pp.getclass();
        
        if (kill >= 800 && isUnderClass(Class, "SS")) {
        	p.sendMessage("§6성공적으로 §4§lSS §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§4§lSS");
        	removeAllPermission(p);
        	addPermission(p, "essentials.kits.SS");
        	return;
        } else if (kill >= 500 && kill < 800 && isUnderClass(Class, "S")) {
        	p.sendMessage("§6성공적으로 §4§lS §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§4S");
        	removeAllPermission(p);
        	addPermission(p, "essentials.kits.S");
        	return;
        } else if (kill >= 300 && kill < 500 && isUnderClass(Class, "A")) {
        	p.sendMessage("§6성공적으로 §c§lA §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§cA");
        	removeAllPermission(p);
        	addPermission(p, "essentials.kits.A");
        	return;
        } else if (kill >= 120 && kill < 300 && isUnderClass(Class, "B")) {
        	p.sendMessage("§6성공적으로 §d§lB §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§dB");
        	removeAllPermission(p);
        	addPermission(p, "essentials.kits.B");
        	return;
        } else if (kill >= 60 && kill < 120 && isUnderClass(Class, "C")) {
        	p.sendMessage("§6성공적으로 §b§lC §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§bC");
        	return;
        } else if (kill >= 30 && kill < 60 && isUnderClass(Class, "D")) {
        	p.sendMessage("§6성공적으로 §6§lD §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§6D");
        	return;
        } else if (kill >= 5 && kill < 30 && isUnderClass(Class, "E")) {
        	p.sendMessage("§6성공적으로 §e§lE §6랭크로 승급하셨습니다!");
        	castLvup(p);
        	pp.setclass("§eE");
        	return;
        } else {
        	p.sendMessage("§c당신은 조건이 맞지 않아 승급하실 수 없습니다.");
        	return;
        }
    }
	
	public static void addPermission(Player p, String permission) {
		for (World world : Bukkit.getWorlds()) {
			String n = world.getName();
			if (n.equalsIgnoreCase("world_the_end") || n.equalsIgnoreCase("world_nether")) continue;
			main.permission.playerAdd(n, p.getName(), permission);
		}
	}
	
	public static void removeAllPermission(Player p) {
		for (World world : Bukkit.getWorlds()) {
			String n = world.getName();
			if (n.equalsIgnoreCase("world_the_end") || n.equalsIgnoreCase("world_nether")) continue;
			if (main.permission.playerHas(n, p.getName(), "essentials.kits.B"))
				main.permission.playerRemove(n, p.getName(), "essentials.kits.B");
			if (main.permission.playerHas(n, p.getName(), "essentials.kits.A"))
				main.permission.playerRemove(n, p.getName(), "essentials.kits.A");
			if (main.permission.playerHas(n, p.getName(), "essentials.kits.S"))
				main.permission.playerRemove(n, p.getName(), "essentials.kits.S");
			if (main.permission.playerHas(n, p.getName(), "essentials.kits.SS"))
				main.permission.playerRemove(n, p.getName(), "essentials.kits.SS");
		}
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cast forcecast " + p.getName() + " lvup");
	}
    
	public static void removeOP(Player p, boolean is) {
		if (is) {
			p.setOp(is);
		} else {
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
			p.setOp(is);
		}
	}
    
	public static String ChangeTppos(String str) {
		String[] st = str.split(",");
		return st[3] + " " + st[0] + " " + st[1] + " " + st[2] + " " + st[4] + " " + st[5];
	}
	
	public static void ConsoleCommandTP(String p, String name) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "f t " + p + " " + name);
	}
	
    public static PVPPlayer getPVPPlayera(String name) {
        return pvpplayer.get(name);
    }
    
    public static void setPVPPlayer(String name) {
        pvpplayer.put(name, new PVPPlayer(name));
    }
}
