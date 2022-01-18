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
		return Config.getInt("1���Ʈ.Ÿ�̸�");
	}
	
	public static int getPvPTimeTwo() {
		return Config.getInt("2���Ʈ.Ÿ�̸�");
	}
	
	public static int getPvPTimeThree() {
		return Config.getInt("3���Ʈ.Ÿ�̸�");
	}
	
	public static int getPvPTimeFour() {
		return Config.getInt("4���Ʈ.Ÿ�̸�");
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
    	return Config.getInt(i + "���Ʈ.Ÿ�̸�");
    }
    
    public static void StopAPI(Player p) {
    	boolean is = true;
    	List<Player> pList = main.pList;
    	
    	if (pList.get(pList.size() - 1).equals(p)) {
    		is = false;
    	}
    	
    	if (!is) {
    		API.RoundStop(pList, "��f[��4�˸���f] <playername> ��c���� ��4���� ��ȸ ��c������ �����ϼ̽��ϴ�.");
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
				
				p.sendMessage("��6���� ��ȸ ��c1 ���� ��6�� �� ���۵˴ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
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
							p.sendMessage("��6���� ��ȸ ��c1 ���� ��6�� ���۵Ǿ����ϴ�!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
							
							main.oneRound = 3;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn �ڻԼ�_������ ������ȸ-1");
								API.sendCommand("eb boss spawn ����_���� ������ȸ-2");
								API.sendCommand("eb boss spawn ����ޱⳲ ������ȸ-3");
							}

							Integer id = main.Timer.remove("1. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("��6���� ���۱��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
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

						if (num % 10 == 0 && num >= 10) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 5 && num > 0) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  ��6Ÿ�̸Ӱ� ��� ���� ������ ����Ǿ����ϴ�!");
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z�� " + p.getName());
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

				p.sendMessage("��6���� ��ȸ ��c2 ���� ��6�� �� ���۵˴ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
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
							p.sendMessage("��6���� ��ȸ ��c2 ���� ��6�� ���۵Ǿ����ϴ�!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);

							main.twoRound = 4;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn ��� ������ȸ-4");
								API.sendCommand("eb boss spawn �Ǵн��� ������ȸ-5");
								API.sendCommand("eb boss spawn ���ΰ���_���žȰ� ������ȸ-6");
								API.sendCommand("eb boss spawn ���÷��õ� ������ȸ-7");
							}

							Integer id = main.Timer.remove("2. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("��6���� ���۱��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
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

						if (num % 10 == 0 && num >= 10) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 5 && num > 0) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  ��6Ÿ�̸Ӱ� ��� ���� ������ ����Ǿ����ϴ�!");
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z�� " + p.getName());
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

				p.sendMessage("��6���� ��ȸ ��c3 ���� ��6�� �� ���۵˴ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
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
							p.sendMessage("��6���� ��ȸ ��c3 ���� ��6�� ���۵Ǿ����ϴ�!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);

							main.threeRound = 4;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn �̺�_õ���� ������ȸ-8");
								API.sendCommand("eb boss spawn �ո� ������ȸ-9");
								API.sendCommand("eb boss spawn �߳�_����� ������ȸ-10");
								API.sendCommand("eb boss spawn Ȩ����_Ȳ�� ������ȸ-11");
							}

							Integer id = main.Timer.remove("3. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("��6���� ���۱��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
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

						if (num % 10 == 0 && num >= 10) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 5 && num > 0) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  ��6Ÿ�̸Ӱ� ��� ���� ������ ����Ǿ����ϴ�!");
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z�� " + p.getName());
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

				p.sendMessage("��6���� ��ȸ ��c4 ���� ��6�� �� ���۵˴ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
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
							p.sendMessage("��6���� ��ȸ ��c4 ���� ��6�� ���۵Ǿ����ϴ�!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);

							main.fourRound = 3;
							if (main.pList.get(main.pList.size() - 1).equals(p)) {
								API.sendCommand("eb boss spawn �ʹ�_ũ��_�ڶ�_��ġ ������ȸ-12");
								API.sendCommand("eb boss spawn Ȳ��_���� ������ȸ-13");
								API.sendCommand("eb boss spawn �����ڽ� ������ȸ-14");
							}

							Integer id = main.Timer.remove("4. Don't Move " + user + "");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("��6���� ���۱��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
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

						if (num % 10 == 0 && num >= 10) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 5 && num > 0) p.sendMessage("��6���� ���� �ð��� ��c" + num + " ��6�� ���ҽ��ϴ�.");
						else if (num <= 0) {
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							p.sendMessage("                                  ��6Ÿ�̸Ӱ� ��� ���� ������ ����Ǿ����ϴ�!");
							p.sendMessage("");
							p.sendMessage("��f==========================================================");
							p.sendMessage("");
							API.sendCommand("warp Z�� " + p.getName());
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
				API.sendCommand("���� �߰� " + p.getName() + " " + score);
			}
		}
		
		if (pList.size() == 1) {
			Bukkit.broadcastMessage("��f[��4�˸���f] " + pList.get(0).getName() + " ��a���� ��2���� ��ȸ ��a������ �����ϼ̽��ϴ�.");
		} else {
			for (Player p : main.pList) {
				if (PartyAPI.isParty(p.getName())) {
					Bukkit.broadcastMessage("��f[��4�˸���f] " + p.getName() + " ��a���� ��Ƽ�� ��2���� ��ȸ ��a������ �����ϼ̽��ϴ�.");
					break;
				}
			}
		}
		
		for (Player p : main.pList) {
			API.sendCommand("warp Z�� " + p.getName());
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
			RoundStop(pList, "��f[��4�˸���f] <playername> ��c���� ��4���� ��ȸ ��c������ �����ϼ̽��ϴ�.");
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
		
		API.sendCommand("eb boss killtype �ڻԼ�_������");
		API.sendCommand("eb boss killtype ����_����");
		API.sendCommand("eb boss killtype ����ޱⳲ");
		API.sendCommand("eb boss killtype ���");
		API.sendCommand("eb boss killtype �Ǵн���");
		API.sendCommand("eb boss killtype ���ΰ���_���žȰ�");
		API.sendCommand("eb boss killtype ���÷��õ�");
		API.sendCommand("eb boss killtype �̺�_õ����");
		API.sendCommand("eb boss killtype �ո�");
		API.sendCommand("eb boss killtype �߳�_�����");
		API.sendCommand("eb boss killtype Ȩ����_Ȳ��");
		API.sendCommand("eb boss killtype �ʹ�_ũ��_�ڶ�_��ġ");
		API.sendCommand("eb boss killtype Ȳ��_����");
		API.sendCommand("eb boss killtype �����ڽ�");
		
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
				Bukkit.broadcastMessage(message.replaceAll("<playername>", "��c� �÷��̾�"));
			}
		}
		
		main.pList = new ArrayList<Player>();
	}
}
