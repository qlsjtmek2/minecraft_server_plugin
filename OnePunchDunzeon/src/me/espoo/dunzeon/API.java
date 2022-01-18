package me.espoo.dunzeon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.espoo.rpg.Method;
import me.espoo.rpg.party.PartyAPI;

public class API {
	public static int getPvPGotTime() {
		return 5;
	}
	
	public static int getPvPTimeOne() {
		return Config.getInt("1라운트.타이머");
	}
	
	public static int getPvPTimeTwo() {
		return Config.getInt("2라운트.타이머");
	}
	
	public static int getPvPTimeThree() {
		return Config.getInt("3라운트.타이머");
	}
	
	public static int getPvPTimeFour() {
		return Config.getInt("4라운트.타이머");
	}
	
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
	
	public static boolean isPartyUserInLocation(String party) {
		for (String user : PartyAPI.getUser(party)) {
			Player p = Method.getOnorOffLine(user);
			
			if (!API.isInArea(p.getLocation())) {
				return false;
			}
		} return true;
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
	
    public static boolean isInArea(Location loc) {
		double XP = loc.getX();
		double YP = loc.getY();
		double ZP = loc.getZ();
    	
		Location pos1 = Position.getStangByOne();
		Location pos2 = Position.getStangByTwo();
		double X1 = pos1.getX();
		double Y1 = pos1.getY();
		double Z1 = pos1.getZ();
		double X2 = pos2.getX();
		double Y2 = pos2.getY();
		double Z2 = pos2.getZ();
		
		if (X1 >= XP && XP >= X2 || X1 <= XP && XP <= X2) {
			if (Y1 >= YP && YP >= Y2 || Y1 <= YP && YP <= Y2) {
				if (Z1 >= ZP && ZP >= Z2 || Z1 <= ZP && ZP <= Z2) {
					return true;
				}
			}
		}
		
		return false;
    }
    
    public static boolean isInAssociation(Location loc) {
		double XP = loc.getX();
		double YP = loc.getY();
		double ZP = loc.getZ();
    	
		Location pos1 = Position.getAssociationOne();
		Location pos2 = Position.getAssociationTwo();
		double X1 = pos1.getX();
		double Y1 = pos1.getY();
		double Z1 = pos1.getZ();
		double X2 = pos2.getX();
		double Y2 = pos2.getY();
		double Z2 = pos2.getZ();
		
		if (X1 >= XP && XP >= X2 || X1 <= XP && XP <= X2) {
			if (Y1 >= YP && YP >= Y2 || Y1 <= YP && YP <= Y2) {
				if (Z1 >= ZP && ZP >= Z2 || Z1 <= ZP && ZP <= Z2) {
					return true;
				}
			}
		}
		
		return false;
    }
    
    public static int getRoundTimer(int i) {
    	return Config.getInt(i + "라운트.타이머");
    }
    
    public static void StopAPI(Player p) {
    	boolean is = true;
    	List<Player> pList = main.pList;
    	
    	if (pList.get(pList.size() - 1).equals(p)) {
    		is = false;
    	}
    	
    	if (!is) {
    		API.RoundStop(pList, "§f[§4알림§f] <playername> §c님이 §4괴인 협회 §c원정에 실패하셨습니다.");
    	} else {
    		API.subUser(p, null);
    	}
    }
    
	public static void RoundStart(List<Player> pList) {
		int i = 0;
		main.Round = true;
		main.pList = pList;
		
		for (Player p : pList) {
			if (Method.getOnorOffLine(p.getName()) != null && main.pList.contains(p)) {
				String user = p.getName();
				Timer timer = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 100);
				if (i == 0) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getOne1()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getOne2()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getOne3()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 3) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getOne4()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				i++;
				
				p.sendMessage("§6괴인 협회 §c1 라운드 §6가 곧 시작됩니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
				main.Timer.put("1. Don't Move " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPGotTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("1. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p.sendMessage("§6괴인 협회 §c1 라운드 §6가 시작되었습니다!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
							
							main.oneRound = 3;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn 코뿔소_레슬러 괴인협회-1");
								API.sendCommand("eb boss spawn 마셜_고릴라 괴인협회-2");
								API.sendCommand("eb boss spawn 전기메기남 괴인협회-3");
							}

							Integer id = main.Timer.remove("1. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("§6전투 시작까지 §c" + num + " §6초 남았습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), 20L, 20L).getTaskId());
				
				int time = (API.getPvPGotTime() + 1) * 20; time += 20;
				
				main.Timer.put("1. PvP Start " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPTimeOne() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("1. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (main.oneRound == 0) {
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								main.oneRound = -1;
								API.TwoRoundStart();
								
								Integer id = main.Timer.remove("1. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								Integer id = main.Timer.remove("1. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						}
						
						else if (main.oneRound == -1) {
							Integer id = main.Timer.remove("1. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}

						if (num % 10 == 0 && num >= 10) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 5 && num > 0) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  §6타이머가 모두 지나 전투가 종료되었습니다!");
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z시 " + p.getName());
							API.sendCommand("heal " + p.getName());
							API.StopAPI(p);
							
							Integer id = main.Timer.remove("1. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), time, 20L).getTaskId());
			}
		}
	}
	
	public static void TwoRoundStart() {
		List<Player> pList = main.pList;
		int i = 0;
		main.Round = true;
		
		for (Player p : pList) {
			if (Method.getOnorOffLine(p.getName()) != null && main.pList.contains(p)) {
				String user = p.getName();
				Timer timer = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 100);
				if (i == 0) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getTwo1()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getTwo2()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getTwo3()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 3) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getTwo4()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				i++;

				p.sendMessage("§6괴인 협회 §c2 라운드 §6가 곧 시작됩니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
				main.Timer.put("2. Don't Move " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPGotTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("2. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p.sendMessage("§6괴인 협회 §c2 라운드 §6가 시작되었습니다!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);

							main.twoRound = 4;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn 충신 괴인협회-4");
								API.sendCommand("eb boss spawn 피닉스남 괴인협회-5");
								API.sendCommand("eb boss spawn 괴인공주_여신안경 괴인협회-6");
								API.sendCommand("eb boss spawn 라플레시돈 괴인협회-7");
							}

							Integer id = main.Timer.remove("2. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("§6전투 시작까지 §c" + num + " §6초 남았습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), 20L, 20L).getTaskId());
				
				int time = (API.getPvPGotTime() + 1) * 20; time += 20;
				
				main.Timer.put("2. PvP Start " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPTimeTwo() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("2. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (main.twoRound == 0) {
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								main.twoRound = -1;
								API.ThreeRoundStart();
								
								Integer id = main.Timer.remove("2. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								Integer id = main.Timer.remove("2. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						}
						
						else if (main.twoRound == -1) {
							Integer id = main.Timer.remove("2. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}

						if (num % 10 == 0 && num >= 10) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 5 && num > 0) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  §6타이머가 모두 지나 전투가 종료되었습니다!");
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z시 " + p.getName());
							API.sendCommand("heal " + p.getName());
							API.StopAPI(p);
							
							Integer id = main.Timer.remove("2. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), time, 20L).getTaskId());
			}
		}
	}
	
	public static void ThreeRoundStart() {
		List<Player> pList = main.pList;
		int i = 0;
		main.Round = true;
		
		for (Player p : pList) {
			if (Method.getOnorOffLine(p.getName()) != null && main.pList.contains(p)) {
				String user = p.getName();
				Timer timer = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 100);
				if (i == 0) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getThree1()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getThree2()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getThree3()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 3) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getThree4()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				i++;

				p.sendMessage("§6괴인 협회 §c3 라운드 §6가 곧 시작됩니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
				main.Timer.put("3. Don't Move " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPGotTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("3. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p.sendMessage("§6괴인 협회 §c3 라운드 §6가 시작되었습니다!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);

							main.threeRound = 4;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn 이블_천연수 괴인협회-8");
								API.sendCommand("eb boss spawn 잇몸 괴인협회-9");
								API.sendCommand("eb boss spawn 추남_대통령 괴인협회-10");
								API.sendCommand("eb boss spawn 홈리스_황제 괴인협회-11");
							}

							Integer id = main.Timer.remove("3. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("§6전투 시작까지 §c" + num + " §6초 남았습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), 20L, 20L).getTaskId());
				
				int time = (API.getPvPGotTime() + 1) * 20; time += 20;
				
				main.Timer.put("3. PvP Start " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPTimeThree() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("3. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (main.threeRound == 0) {
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								main.threeRound = -1;
								API.FourRoundStart();
								
								Integer id = main.Timer.remove("3. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								Integer id = main.Timer.remove("3. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						}
						
						else if (main.threeRound == -1) {
							Integer id = main.Timer.remove("3. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}

						if (num % 10 == 0 && num >= 10) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 5 && num > 0) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  §6타이머가 모두 지나 전투가 종료되었습니다!");
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z시 " + p.getName());
							API.sendCommand("heal " + p.getName());
							API.StopAPI(p);
							
							Integer id = main.Timer.remove("3. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), time, 20L).getTaskId());
			}
		}
	}
	
	public static void FourRoundStart() {
		List<Player> pList = main.pList;
		int i = 0;
		main.Round = true;
		
		for (Player p : pList) {
			if (Method.getOnorOffLine(p.getName()) != null && main.pList.contains(p)) {
				String user = p.getName();
				Timer timer = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 100);
				if (i == 0) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getFour1()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getFour2()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getFour3()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				else if (i == 3) { timer.schedule(new TimerTask() { public void run() {
				p.teleport(Position.getFour4()); p.getLocation().getChunk().load();
				return; }}, timeToRun); }
				
				i++;

				p.sendMessage("§6괴인 협회 §c4 라운드 §6가 곧 시작됩니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
				main.Timer.put("4. Don't Move " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPGotTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("4. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p.sendMessage("§6괴인 협회 §c4 라운드 §6가 시작되었습니다!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);

							main.fourRound = 3;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn 너무_크게_자란_포치 괴인협회-12");
								API.sendCommand("eb boss spawn 황금_정자 괴인협회-13");
								API.sendCommand("eb boss spawn 사이코스 괴인협회-14");
							}

							Integer id = main.Timer.remove("4. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("§6전투 시작까지 §c" + num + " §6초 남았습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), 20L, 20L).getTaskId());
				
				int time = (API.getPvPGotTime() + 1) * 20; time += 20;
				
				main.Timer.put("4. PvP Start " + user + "", new BukkitRunnable()
				{
					int num = API.getPvPTimeFour() + 1;
					public void run()
					{
						num--;
						
						if (!main.Round) {
							API.RoundStop(main.pList, null);
							Integer id = main.Timer.remove("4. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (main.fourRound == 0) {
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								main.fourRound = -1;
								API.FinishRound();
								
								Integer id = main.Timer.remove("4. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								Integer id = main.Timer.remove("4. PvP Start " + user + "");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						}
						
						else if (main.fourRound == -1) {
							Integer id = main.Timer.remove("4. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}

						if (num % 10 == 0 && num >= 10) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 5 && num > 0) p.sendMessage("§6현재 전투 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  §6타이머가 모두 지나 전투가 종료되었습니다!");
							p.sendMessage("");
							p.sendMessage("§f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z시 " + p.getName());
							API.sendCommand("heal " + p.getName());
							API.StopAPI(p);
							
							Integer id = main.Timer.remove("4. PvP Start " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), time, 20L).getTaskId());
			}
		}
	}
	
	public static void FinishRound() {
		List<Player> pList = main.pList;
		for (Player p : pList) {
			if (Method.getOnorOffLine(p.getName()) != null && main.pList.contains(p)) {
				int money = 60000 / main.pList.size();
				int score = 2000 / main.pList.size();
				API.sendCommand("eco give " + p.getName() + " " + money);
				API.sendCommand("점수 추가 " + p.getName() + " " + score);
			}
		}
		
		if (pList.size() == 1) {
			Bukkit.broadcastMessage("§f[§4알림§f] " + pList.get(0).getName() + " §a님이 §2괴인 협회 §a원정에 성공하셨습니다.");
		} else {
			for (Player p : main.pList) {
				if (PartyAPI.isParty(p.getName())) {
					Bukkit.broadcastMessage("§f[§4알림§f] " + p.getName() + " §a님의 파티가 §2괴인 협회 §a원정에 성공하셨습니다.");
					break;
				}
			}
		}
		
		for (Player p : main.pList) {
			API.sendCommand("warp Z시 " + p.getName());
			API.sendCommand("heal " + p.getName());
		}
		
		API.RoundStop(pList, null);
	}
	
	public static void deleteTimerMove(String user) {
		Integer id = main.Timer.remove("1. Don't Move " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		id = main.Timer.remove("2. Don't Move " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		id = main.Timer.remove("3. Don't Move " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		id = main.Timer.remove("4. Don't Move " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public static void deleteTimerStart(String user) {
		Integer id = main.Timer.remove("1. PvP Start " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		id = main.Timer.remove("2. PvP Start " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		id = main.Timer.remove("3. PvP Start " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		id = main.Timer.remove("4. PvP Start " + user + "");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	public static void subUser(Player p, String message) {
		List<Player> pList = main.pList;
		if (pList.contains(p)) pList.remove(p);
		deleteTimerMove(p.getName());
		deleteTimerStart(p.getName());
		main.CoolTime.put(p.getName(), 10);
		
		main.Timer.put("Cooltime. " + p.getName(), new BukkitRunnable()
		{
			public void run()
			{
				int num = main.CoolTime.get(p.getName()) - 1;
				main.CoolTime.put(p.getName(), num);
				
				if (num <= 0) {
					main.CoolTime.remove(p.getName());
					Integer id = main.Timer.remove("Cooltime. " + p.getName());
					if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					return;
				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), 1200L, 1200L).getTaskId());
		
		if (pList.size() == 0) {
			RoundStop(pList, "§f[§4알림§f] <playername> §c님이 §4괴인 협회 §c원정에 실패하셨습니다.");
		} else {
			main.pList = pList;
			
			if (message != null) {
				for (Player pl : pList)
					pl.sendMessage(message);
			}
		}
	}
	
	public static void RoundStop(List<Player> pList, String message) {
		main.Round = false;
		main.oneRound = -1;
		main.twoRound = -1;
		main.threeRound = -1;
		main.fourRound = -1;

		if (pList != null && !pList.isEmpty()) {
			for (Player p : pList) {
				deleteTimerMove(p.getName());
				deleteTimerStart(p.getName());
				main.CoolTime.put(p.getName(), 10);
				
				main.Timer.put("Cooltime. " + p.getName(), new BukkitRunnable()
				{
					public void run()
					{
						if (main.CoolTime.get(p.getName()) != null) {
							int num = main.CoolTime.get(p.getName()) - 1;
							main.CoolTime.put(p.getName(), num);
							
							if (num <= 0) {
								main.CoolTime.remove(p.getName());
								Integer id = main.Timer.remove("Cooltime. " + p.getName());
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						} else {
							Integer id = main.Timer.remove("Cooltime. " + p.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;	
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchDunzeon"), 1200L, 1200L).getTaskId());
			}
		}
		
		API.sendCommand("eb boss killtype 코뿔소_레슬러");
		API.sendCommand("eb boss killtype 마셜_고릴라");
		API.sendCommand("eb boss killtype 전기메기남");
		API.sendCommand("eb boss killtype 충신");
		API.sendCommand("eb boss killtype 피닉스남");
		API.sendCommand("eb boss killtype 괴인공주_여신안경");
		API.sendCommand("eb boss killtype 라플레시돈");
		API.sendCommand("eb boss killtype 이블_천연수");
		API.sendCommand("eb boss killtype 잇몸");
		API.sendCommand("eb boss killtype 추남_대통령");
		API.sendCommand("eb boss killtype 홈리스_황제");
		API.sendCommand("eb boss killtype 너무_크게_자란_포치");
		API.sendCommand("eb boss killtype 황금_정자");
		API.sendCommand("eb boss killtype 사이코스");
		
		if (message != null) {
			boolean is = false;
			for (Player p : main.pList) {
				if (PartyAPI.isParty(p.getName())) {
					Bukkit.broadcastMessage(message.replaceAll("<playername>", p.getName()));
					is = true; break;
				}
			}
			
			if (!is && pList.size() == 1) {
				Bukkit.broadcastMessage(message.replaceAll("<playername>", pList.get(0).getName()));
			}
			
			if (!is && pList.size() <= 0) {
				Bukkit.broadcastMessage(message.replaceAll("<playername>", "§c어떤 플레이어"));
			}
		}
		
		main.pList = new ArrayList<Player>();
	}
}
