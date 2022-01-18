package me.espoo.pvp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.espoo.rpg.Method;
import me.espoo.rpg.party.PartyAPI;

public class API {
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
	
	public static void coloReturnBetting(String message) {
		HashMap<Player, String> hash = main.coloMoney;
		List<Player> list = new ArrayList<Player>(hash.keySet());
		
		for (Player p : list) {
			main.economy.depositPlayer(p.getName(), Integer.parseInt(hash.get(p).split(",")[1]));
			if (message != null) p.sendMessage(message);
		}
		main.coloMoney.clear();
	}
	
	public static void coloWinBetting(String name, String message1, String message2) {
		HashMap<Player, String> hash = main.coloMoney;
		List<Player> list = new ArrayList<Player>(hash.keySet());
		List<Player> winPlayer = new ArrayList<Player>();
		int bettingMoney = main.coloBetting;
		int totalMoney = 0;
		double result = 0;
		
		for (Player p : list) {
			String user = hash.get(p).split(",")[0];
			if (!user.equalsIgnoreCase(name)) {
				totalMoney += bettingMoney;
				if (message2 != null) p.sendMessage(message2);
			} else {
				winPlayer.add(p);
			}
		}
		
		if (winPlayer.size() != 0) {
			result = totalMoney / winPlayer.size();
			result += bettingMoney;
			
			for (Player p : winPlayer) {
				main.economy.depositPlayer(p.getName(), result);
				if (message1 != null) p.sendMessage(message1);
			}
		}
		
		main.coloMoney.clear();
	}
	
	public static double coloBettingMoney(String name) {
		HashMap<Player, String> hash = main.coloMoney;
		List<Player> list = new ArrayList<Player>(hash.keySet());
		int bettingMoney = main.coloBetting;
		int totalMoney = 0;
		int num = 0;
		
		for (Player p : list) {
			String user = hash.get(p).split(",")[0];
			if (!user.equalsIgnoreCase(name)) {
				totalMoney += bettingMoney;
			} else {
				num++;
			}
		}
		
		if (num == 0) return 0;
		return totalMoney / num;
	}
	
	public static void setColosseumCooltime(Player p) {
		main.coloCooltimeList.add(p.getName());
		
		Timer timer = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 300000);
		timer.schedule(new TimerTask() {
			public void run() {
				main.coloCooltimeList.remove(p.getName());
				return;
			}
		}, timeToRun);
	}
	
	public static String getColoPlayer(int num) {
		if (main.coloVS) {
			if (num == 1) {
				return main.coloName.split(",")[0];
			}
			
			else if (num == 2) {
				return main.coloName.split(",")[1];
			}
			
			else return null;
		} else return null;
	}
	
	public static void coloStopAPI(Player p) {
		if (!p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
			Integer id = main.Timer.remove("5. Don't Move");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			id = main.Timer.remove("5. PvP Start");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			id = main.Timer.remove("5. PvP Finish");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			main.coloMoney.clear();
		}
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
	
	public static void twoStopAPI(String pO, String pT) {
		boolean is = false;
		if (API.getEnemyName(pO, 2).equalsIgnoreCase(pT)) {
			if (PartyAPI.isParty(pO)) {
				for (String user : PartyAPI.getUser(pO)) {
					Player p = Bukkit.getPlayerExact(user);
					if (p != null) {
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) is = true;
					}
				}
			}
			
			if (PartyAPI.isParty(pT)) {
				for (String user : PartyAPI.getUser(pT)) {
					Player p = Bukkit.getPlayerExact(user);
					if (p != null) {
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) is = true;
					}
				}
			}
			
			if (is == false) {
				main.twoVS = false;
				main.twoType = -1;
				main.twoName = null;
				main.twoInt = 0;
				
				if (PartyAPI.isParty(pO)) {
					for (String user : PartyAPI.getUser(pO)) {
						Integer id = main.Timer.remove("2. Don't Move " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("2. PvP Start " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("2. PvP Finish " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					}
				}
				
				if (PartyAPI.isParty(pT)) {
					for (String user : PartyAPI.getUser(pT)) {
						Integer id = main.Timer.remove("2. Don't Move " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("2. PvP Start " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("2. PvP Finish " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					}
				}
			}
		}
	}
	
	public static void threeStopAPI(String pO, String pT) {
		boolean is = false;
		if (API.getEnemyName(pO, 3).equalsIgnoreCase(pT)) {
			if (PartyAPI.isParty(pO)) {
				for (String user : PartyAPI.getUser(pO)) {
					Player p = Bukkit.getPlayerExact(user);
					if (p != null) {
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) is = true;
					}
				}
			}
			
			if (PartyAPI.isParty(pT)) {
				for (String user : PartyAPI.getUser(pT)) {
					Player p = Bukkit.getPlayerExact(user);
					if (p != null) {
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) is = true;
					}
				}
			}
			
			if (is == false) {
				main.threeVS = false;
				main.threeType = -1;
				main.threeName = null;
				main.threeInt = 0;
				
				if (PartyAPI.isParty(pO)) {
					for (String user : PartyAPI.getUser(pO)) {
						Integer id = main.Timer.remove("3. Don't Move " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("3. PvP Start " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("3. PvP Finish " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					}
				}
				
				if (PartyAPI.isParty(pT)) {
					for (String user : PartyAPI.getUser(pT)) {
						Integer id = main.Timer.remove("3. Don't Move " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("3. PvP Start " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("3. PvP Finish " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					}
				}
			}
		}
	}
	
	public static void fourStopAPI(String pO, String pT) {
		boolean is = false;
		if (API.getEnemyName(pO, 4).equalsIgnoreCase(pT)) {
			if (PartyAPI.isParty(pO)) {
				for (String user : PartyAPI.getUser(pO)) {
					Player p = Bukkit.getPlayerExact(user);
					if (p != null) {
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) is = true;
					}
				}
			}
			
			if (PartyAPI.isParty(pT)) {
				for (String user : PartyAPI.getUser(pT)) {
					Player p = Bukkit.getPlayerExact(user);
					if (p != null) {
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) is = true;
					}
				}
			}
			
			if (is == false) {
				main.fourVS = false;
				main.fourType = -1;
				main.fourName = null;
				main.fourInt = 0;
				
				if (PartyAPI.isParty(pO)) {
					for (String user : PartyAPI.getUser(pO)) {
						Integer id = main.Timer.remove("4. Don't Move " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("4. PvP Start " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("4. PvP Finish " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					}
				}
				
				if (PartyAPI.isParty(pT)) {
					for (String user : PartyAPI.getUser(pT)) {
						Integer id = main.Timer.remove("4. Don't Move " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("4. PvP Start " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						
						id = main.Timer.remove("4. PvP Finish " + user + "");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					}
				}
			}
		}
	}
	
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
	
	// 0: X, 1: Y, 2: Z, 3: World, 4: Yaw, 5: Pitch
	public static String LocationToString(Location loc) {
	    return String.valueOf(loc.getX()) + "," + String.valueOf(loc.getY()) + "," + String.valueOf(loc.getZ()) + "," + loc.getWorld().getName() + "," + loc.getYaw() + "," + loc.getPitch();
	}
	
	public static void quitMatchParty(String party) {
		if (main.twoList.contains(party)) {
			main.twoList.remove(party);
			return;
		}
		
		else if (main.threeList.contains(party)) {
			main.threeList.remove(party);
			return;
		}
		
		else if (main.fourList.contains(party)) {
			main.fourList.remove(party);
			return;
		}
	}

	public static Location StringToLocation(String s) {
		String[] loc = s.split(",");
		Location loc2 = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
		return loc2;
	}
	
	public static boolean isPartyUserInWorld(String party, String world) {
		for (String user : PartyAPI.getUser(party)) {
			Player p = Method.getOnorOffLine(user);
			if (!p.getWorld().getName().equalsIgnoreCase(world)) {
				return false;
			}
		} return true;
	}
	
	// 1-1:1, 2-2:2, 3-3:3, 4-4:4, 5-콜로세움
	public static String getEnemyName(String p, int num) {
		switch (num) {
			case 1:
				if (main.oneName == null) return null;
				if (main.oneName.split(",")[0].equalsIgnoreCase(p)) return main.oneName.split(",")[1];
				else if (main.oneName.split(",")[1].equalsIgnoreCase(p)) return main.oneName.split(",")[0];
				else return null;
				
			case 2:
				if (main.twoName == null) return null;
				if (main.twoName.split(",")[0].equalsIgnoreCase(p)) return main.twoName.split(",")[1];
				else if (main.twoName.split(",")[1].equalsIgnoreCase(p)) return main.twoName.split(",")[0];
				else return null;
				
			case 3:
				if (main.threeName == null) return null;
				if (main.threeName.split(",")[0].equalsIgnoreCase(p)) return main.threeName.split(",")[1];
				else if (main.threeName.split(",")[1].equalsIgnoreCase(p)) return main.threeName.split(",")[0];
				else return null;
				
			case 4:
				if (main.fourName == null) return null;
				if (main.fourName.split(",")[0].equalsIgnoreCase(p)) return main.fourName.split(",")[1];
				else if (main.fourName.split(",")[1].equalsIgnoreCase(p)) return main.fourName.split(",")[0];
				else return null;
				
			case 5:
				if (main.coloName == null) return null;
				if (main.coloName.split(",")[0].equalsIgnoreCase(p)) return main.coloName.split(",")[1];
				else if (main.coloName.split(",")[1].equalsIgnoreCase(p)) return main.coloName.split(",")[0];
				else return null;
				
			default: return null;
		}
	}
	
	// 1-1:1, 2-2:2, 3-3:3, 4-4:4, 5-콜로세움
	public static void setChangeName(String p, String p2, int num) {
		switch (num) {
			case 1:
				if (main.oneName.split(",")[0].equalsIgnoreCase(p)) {
					main.oneName = p2 + "," + main.oneName.split(",")[1]; break;
				}
				
				else if (main.oneName.split(",")[1].equalsIgnoreCase(p)) {
					main.oneName = main.oneName.split(",")[0] + "," + p2; break;
				} else break;
				
			case 2:
				if (main.twoName.split(",")[0].equalsIgnoreCase(p)) {
					main.twoName = p2 + "," + main.twoName.split(",")[1]; break;
				}
				
				else if (main.twoName.split(",")[1].equalsIgnoreCase(p)) {
					main.twoName = main.twoName.split(",")[0] + "," + p2; break;
				} else break;
				
			case 3:
				if (main.threeName.split(",")[0].equalsIgnoreCase(p)) {
					main.threeName = p2 + "," + main.threeName.split(",")[1]; break;
				}
				
				else if (main.threeName.split(",")[1].equalsIgnoreCase(p)) {
					main.threeName = main.threeName.split(",")[0] + "," + p2; break;
				} else break;
				
			case 4:
				if (main.fourName.split(",")[0].equalsIgnoreCase(p)) {
					main.fourName = p2 + "," + main.fourName.split(",")[1]; break;
				}
				
				else if (main.fourName.split(",")[1].equalsIgnoreCase(p)) {
					main.fourName = main.fourName.split(",")[0] + "," + p2; break;
				} else break;
				
			case 5:
				if (main.coloName.split(",")[0].equalsIgnoreCase(p)) {
					main.coloName = p2 + "," + main.coloName.split(",")[1]; break;
				}
				
				else if (main.coloName.split(",")[1].equalsIgnoreCase(p)) {
					main.coloName = main.coloName.split(",")[0] + "," + p2; break;
				} else break;
				
			default: break;
		}
	}
	
	public static boolean isOneVSMatch(Player p) {
		if (main.oneList.contains(p)) return true;
		return false;
	}
	
	public static boolean isTwoVSMatch(String party) {
		if (main.twoList.contains(party)) return true;
		return false;
	}
	
	public static boolean isThreeVSMatch(String party) {
		if (main.threeList.contains(party)) return true;
		return false;
	}
	
	public static boolean isFourVSMatch(String party) {
		if (main.fourList.contains(party)) return true;
		return false;
	}
	
	public static boolean isColoVSMatch(Player p) {
		if (main.coloList.contains(p)) return true;
		return false;
	}
	
	public static boolean isPartyVSMatch(String party) {
		if (API.isTwoVSMatch(party) || API.isThreeVSMatch(party) || API.isFourVSMatch(party)) return true;
		return false;
	}
	
	public static boolean isUserVSMatch(Player p) {
		if (API.isOneVSMatch(p) || API.isColoVSMatch(p)) return true;
		return false;
	}
	
	public static boolean isOneVSPlaying(String p) {
		String name = main.oneName;
		if (name == null) return false;
		
		if (name.split(",")[0].equalsIgnoreCase(p) || name.split(",")[1].equalsIgnoreCase(p)) return true;
		return false;
	}
	
	public static boolean isTwoVSPlaying(String party) {
		String name = main.twoName;
		if (name == null) return false;
		
		if (name.split(",")[0].equalsIgnoreCase(party) || name.split(",")[1].equalsIgnoreCase(party)) return true;
		return false;
	}
	
	public static boolean isThreeVSPlaying(String party) {
		String name = main.threeName;
		if (name == null) return false;
		
		if (name.split(",")[0].equalsIgnoreCase(party) || name.split(",")[1].equalsIgnoreCase(party)) return true;
		return false;
	}
	
	public static boolean isFourVSPlaying(String party) {
		String name = main.fourName;
		if (name == null) return false;
		
		if (name.split(",")[0].equalsIgnoreCase(party) || name.split(",")[1].equalsIgnoreCase(party)) return true;
		return false;
	}
	
	public static boolean isColoVSPlaying(String p) {
		String name = main.coloName;
		if (name == null) return false;
		
		if (name.split(",")[0].equalsIgnoreCase(p) || name.split(",")[1].equalsIgnoreCase(p)) return true;
		return false;
	}
	
	public static boolean isUserVSPlaying(String p) {
		if (API.isOneVSPlaying(p) || API.isColoVSPlaying(p)) return true;
		return false;
	}
	
	public static boolean isPartyVSPlaying(String party) {
		if (API.isTwoVSPlaying(party) || API.isThreeVSPlaying(party) || API.isFourVSPlaying(party)) return true;
		return false;
	}
	
	public static void StopPartyMatch(String party) {
		if (API.isTwoVSPlaying(party)) {
			int num = 0;
			List<String> list = main.twoList;
			for (String str : list) {
				if (str.equalsIgnoreCase(party)) main.twoList.remove(num);
				else num++;
			}
		}
		
		else if (API.isThreeVSPlaying(party)) {
			int num = 0;
			List<String> list = main.threeList;
			for (String str : list) {
				if (str.equalsIgnoreCase(party)) main.threeList.remove(num);
				else num++;
			}
		}
		
		else if (API.isFourVSPlaying(party)) {
			int num = 0;
			List<String> list = main.fourList;
			for (String str : list) {
				if (str.equalsIgnoreCase(party)) main.fourList.remove(num);
				else num++;
			}
		}
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
}
