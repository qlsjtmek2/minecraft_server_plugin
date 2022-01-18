package me.espoo.pvp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.confuser.barapi.BarAPI;
import me.espoo.rpg.Method;
import me.espoo.rpg.party.PartyAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class main extends JavaPlugin implements Listener {
	public static String prx = "§f[§4알림§f] ";
	public static String oneName = null;
	public static String twoName = null;
	public static String threeName = null;
	public static String fourName = null;
	public static String coloName = null;
	public static String coloWinnerName = null;
	public static boolean oneVS = false;
	public static boolean twoVS = false;
	public static boolean threeVS = false;
	public static boolean fourVS = false;
	public static boolean coloVS = false;
	public static byte oneType = -1;
	public static byte twoType = -1;
	public static byte threeType = -1;
	public static byte fourType = -1;
	public static byte coloType = -1;
	public static int twoInt = 0;
	public static int threeInt = 0;
	public static int fourInt = 0;
	public static int coloBetting = 0;
	public static Economy economy = null;
	public static Permission permission = null;
    static final Map <String, Integer> Timer = new HashMap<>();
	public static HashMap<String, Integer> PartyCheck = new HashMap<String, Integer>();
	public static HashMap<Player, Integer> BossBarTimer = new HashMap<Player, Integer>();
	public static HashMap<Player, String> coloMoney = new HashMap<Player, String>();
	public static List<Player> oneList = new ArrayList<Player>();
	public static List<String> twoList = new ArrayList<String>();
	public static List<String> threeList = new ArrayList<String>();
	public static List<String> fourList = new ArrayList<String>();
	public static List<Player> coloList = new ArrayList<Player>();
	public static List<String> coloCooltimeList = new ArrayList<String>();
	
	public void onEnable()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		permission = (Permission) permissionProvider.getProvider();
        
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				BossBarTimer();
				OnevsOneTimer();
				TwovsTwoTimer();
				ThreevsThreeTimer();
				FourvsFourTimer();
				ColovsColoTimer();
			}
		}, 20L, 20L);
		
		File f = new File("plugins/OnePunchPvP/Config.yml");
		File folder = new File("plugins/OnePunchPvP");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Config.CreateConfig(f, folder, c);
		
        GUIMessage.setGUIMessage();
		getCommand("대련").setExecutor(new mainCommand(this));
		getCommand("대전").setExecutor(new mainCommand(this));
		getCommand("PVP").setExecutor(new mainCommand(this));
		getCommand("대련위치").setExecutor(new mainCommand(this));
		getCommand("대전위치").setExecutor(new mainCommand(this));
		getCommand("PVP위치").setExecutor(new mainCommand(this));
		getCommand("배팅").setExecutor(new mainCommand(this));
		getCommand("관전").setExecutor(new mainCommand(this));
		getCommand("대련취소").setExecutor(new mainCommand(this));
		getCommand("경기취소").setExecutor(new mainCommand(this));
		getCommand("대전취소").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}

	public static void BossBarTimer()
	{
		for (Player p : oneList) {
			int i = 0;
			if (main.BossBarTimer.get(p) != null) i = main.BossBarTimer.get(p);
			i++; main.BossBarTimer.put(p, i);
			
			String str = ".";
			if (i % 3 == 1) str = str + ".";
			if (i % 3 == 2) str = str + "..";
			BarAPI.setMessage(p, "§e[ " + i + "초 ]  §61:1 §c대련 §6매칭 대기중" + str);
		}
		
		for (String party : twoList) {
			if (PartyAPI.isParty(party)) {
				for (String user : PartyAPI.getUser(party)) {
					Player p = Method.getOnorOffLine(user);
					if (p != null) {
						int i = 0;
						if (main.BossBarTimer.get(p) != null) i = main.BossBarTimer.get(p);
						i++; main.BossBarTimer.put(p, i);
						
						String str = ".";
						if (i % 3 == 1) str = str + ".";
						if (i % 3 == 2) str = str + "..";
						BarAPI.setMessage(p, "§e[ " + i + "초 ]  §62:2 §c대련 §6매칭 대기중" + str );
					}
				}
			}
		}
		
		for (String party : threeList) {
			if (PartyAPI.isParty(party)) {
				for (String user : PartyAPI.getUser(party)) {
					Player p = Method.getOnorOffLine(user);
					if (p != null) {
						int i = 0;
						if (main.BossBarTimer.get(p) != null) i = main.BossBarTimer.get(p);
						i++; main.BossBarTimer.put(p, i);
						
						String str = ".";
						if (i % 3 == 1) str = str + ".";
						if (i % 3 == 2) str = str + "..";
						BarAPI.setMessage(p, "§e[ " + i + "초 ]  §63:3 §c대련 §6매칭 대기중" + str);
					}
				}
			}
		}
		
		for (String party : fourList) {
			if (PartyAPI.isParty(party)) {
				for (String user : PartyAPI.getUser(party)) {
					Player p = Method.getOnorOffLine(user);
					if (p != null) {
						int i = 0;
						if (main.BossBarTimer.get(p) != null) i = main.BossBarTimer.get(p);
						i++; main.BossBarTimer.put(p, i);
						
						String str = ".";
						if (i % 3 == 1) str = str + ".";
						if (i % 3 == 2) str = str + "..";
						BarAPI.setMessage(p, "§e[ " + i + "초 ]  §64:4 §c대련 §6매칭 대기중" + str);
					}
				}
			}
		}
		
		for (Player p : coloList) {
			int i = 0;
			if (main.BossBarTimer.get(p) != null) i = main.BossBarTimer.get(p);
			i++; main.BossBarTimer.put(p, i);
			
			String str = ".";
			if (i % 3 == 1) str = str + ".";
			if (i % 3 == 2) str = str + "..";
			BarAPI.setMessage(p, "§e[ " + i + "초 ]  §6콜로세움 §c대련 §6매칭 대기중" + str);
		}
	}
	
	public static void OnevsOneTimer()
	{
		if (oneList.size() >= 2) {
			if (!oneVS) {
				Player p1 = main.oneList.get(0);
				Player p2 = main.oneList.get(1);
				oneList.remove(0);
				oneList.remove(0);
				BarAPI.removeBar(p1);
				BarAPI.removeBar(p2);
				main.BossBarTimer.remove(p1);
				main.BossBarTimer.remove(p2);
				p1.setGameMode(GameMode.SURVIVAL);
				p2.setGameMode(GameMode.SURVIVAL);
				
				Timer timer = new Timer();
				Timer timer2 = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 200);
				timer.schedule(new TimerTask() {
					public void run() {
						p1.teleport(Position.getOneRed());
						p1.getLocation().getChunk().load();
						return;
					}
				}, timeToRun);
				
				timeToRun = new Date(System.currentTimeMillis() + 400);
				
				timer2.schedule(new TimerTask() {
					public void run() {
						p2.teleport(Position.getOneBlue());
						p2.getLocation().getChunk().load();
						return;
					}
				}, timeToRun);
				
				p1.sendMessage("§c" + p2.getName() + " §6님과의 대련이 곧 시작됩니다.");
				p2.sendMessage("§c" + p1.getName() + " §6님과의 대련이 곧 시작됩니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p1, "효과음")) p1.playSound(p1.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p2, "효과음")) p2.playSound(p2.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
				oneVS = true;
				oneType = 1;
				oneName = p1.getName() + "," + p2.getName();
				
				Timer.put("1. Don't Move", new BukkitRunnable()
				{
					int num = API.getPvPGotTime() + 1;
					public void run()
					{
						num--;
						
						if (!oneVS) {
							oneType = -1;
							oneName = null;
							Integer id = main.Timer.remove("1. Don't Move");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p1.sendMessage("§6대련이 시작되었습니다!");
							p2.sendMessage("§6대련이 시작되었습니다!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p1, "효과음")) p1.playSound(p1.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p2, "효과음")) p2.playSound(p2.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
							
							oneType = 2;
							Integer id = main.Timer.remove("1. Don't Move");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p1.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
							p2.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p1, "효과음")) p1.playSound(p1.getLocation(), Sound.CLICK, 2.0F, 1.2F);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p2, "효과음")) p2.playSound(p2.getLocation(), Sound.CLICK, 2.0F, 1.2F);
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
				
				int time = (API.getPvPGotTime() + 1) * 20; time += 20;
				
				Timer.put("1. PvP Start", new BukkitRunnable()
				{
					int num = API.getPvPTime() + 1;
					public void run()
					{
						num--;
						
						if (!oneVS) {
							oneType = -1;
							oneName = null;
							Integer id = main.Timer.remove("1. PvP Start");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num == 30) {
							p1.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
							p2.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
						}

						else if (num == 20) {
							p1.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
							p2.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
						}

						else if (num == 10) {
							p1.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
							p2.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
						}

						else if (num == 5) {
							p1.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
							p2.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
						}
						
						else if (num <= 0) {
							p1.sendMessage("");
							p1.sendMessage("§f==========================================================");
							p1.sendMessage("");
							p1.sendMessage("                                              §6대련이 종료되었습니다!");
							p1.sendMessage("");
							p1.sendMessage("§f==========================================================");
							p1.sendMessage("");
							p2.sendMessage("");
							p2.sendMessage("§f==========================================================");
							p2.sendMessage("");
							p2.sendMessage("                                              §6대련이 종료되었습니다!");
							p2.sendMessage("");
							p2.sendMessage("§f==========================================================");
							p2.sendMessage("");
							
							oneType = 3;
							Integer id = main.Timer.remove("1. PvP Start");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
				
				time += (API.getPvPTime() + 1) * 20;
				
				Timer.put("1. PvP Finish", new BukkitRunnable()
				{
					int num = API.getPvPFinishTime() + 1;
					public void run()
					{
						num--;
						
						if (!oneVS) {
							oneType = -1;
							oneName = null;
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p1.sendMessage("§6스폰으로 이동되었습니다.");
							p2.sendMessage("§6스폰으로 이동되었습니다.");
							oneType = -1;
							oneVS = false;
							oneName = null;
							p1.chat("/spawn");
							p2.chat("/spawn");
							API.sendCommand("heal " + p1.getName());
							API.sendCommand("heal " + p2.getName());
							API.oneStopAPI(p1, p2);
							
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p1.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
							p2.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
			}
		}
	}
	
	public static void TwovsTwoTimer()
	{
		if (twoList.size() >= 2) {
			if (!twoVS) {
				int i = 0;
				String pO = twoList.get(0);
				String pT = twoList.get(1);
				twoList.remove(0);
				twoList.remove(0);
				twoVS = true;
				twoType = 1;
				twoName = pO + "," + pT;
				twoInt = 0;
				
				if (PartyAPI.isParty(pO) && PartyAPI.isParty(pT)) {
					for (String user : PartyAPI.getUser(pO)) {
						Player p = Bukkit.getPlayerExact(user);
						
						if (p != null) {
							BarAPI.removeBar(p);
							main.BossBarTimer.remove(p);
							p.setGameMode(GameMode.SURVIVAL);
							
							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 100);
							if (i == 0) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getTwoRed1()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getTwoRed2()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							i++;
							
							p.sendMessage("§c" + pT + " §6님의 파티와의 대련이 곧 시작됩니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
							
							Timer.put("2. Don't Move " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPGotTime() + 1;
								public void run()
								{
									num--;
									
									if (!twoVS) {
										twoType = -1;
										twoName = null;
										twoInt = 0;
										Integer id = main.Timer.remove("2. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6대련이 시작되었습니다!");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
										
										twoType = 2;
										Integer id = main.Timer.remove("2. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
							
							int time = (API.getPvPGotTime() + 1) * 20; time += 20;
							
							Timer.put("2. PvP Start " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPTime() + 1;
								public void run()
								{
									num--;
									
									if (!twoVS) {
										twoType = -1;
										twoName = null;
										twoInt = 0;
										Integer id = main.Timer.remove("2. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}

									if (num == 300) p.sendMessage("§6현재 대련 시간이 §c5 §6분 남았습니다.");
									else if (num == 240) p.sendMessage("§6현재 대련 시간이 §c4 §6분 남았습니다.");
									else if (num == 180) p.sendMessage("§6현재 대련 시간이 §c3 §6분 남았습니다.");
									else if (num == 120) p.sendMessage("§6현재 대련 시간이 §c2 §6분 남았습니다.");
									else if (num == 60) p.sendMessage("§6현재 대련 시간이 §c1 §6분 남았습니다.");
									else if (num == 30) p.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
									else if (num == 20) p.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
									else if (num == 10) p.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
									else if (num == 5) p.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
									else if (num <= 0) {
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										p.sendMessage("                                              §6대련이 종료되었습니다!");
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										
										twoType = 3;
										Integer id = main.Timer.remove("2. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
							
							time += (API.getPvPTime() + 1) * 20;
							
							Timer.put("2. PvP Finish " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPFinishTime() + 1;
								public void run()
								{
									num--;
									
									if (!twoVS) {
										twoType = -1;
										twoName = null;
										twoInt = 0;
										Integer id = main.Timer.remove("2. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.twoStopAPI(pO, pT);
										
										Integer id = main.Timer.remove("2. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
						}
					}
					
					i = 0;
					
					for (String user : PartyAPI.getUser(pT)) {
						Player p = Bukkit.getPlayerExact(user);
						
						if (p != null) {
							BarAPI.removeBar(p);
							main.BossBarTimer.remove(p);
							p.setGameMode(GameMode.SURVIVAL);
							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 100);
							if (i == 0) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getTwoRed1()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getTwoRed2()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							i++;
							
							p.sendMessage("§c" + pT + " §6님의 파티와의 대련이 곧 시작됩니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
							
							Timer.put("2. Don't Move " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPGotTime() + 1;
								public void run()
								{
									num--;
									
									if (!twoVS) {
										twoType = -1;
										twoName = null;
										twoInt = 0;
										Integer id = main.Timer.remove("2. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6대련이 시작되었습니다!");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
										
										twoType = 2;
										Integer id = main.Timer.remove("2. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
							
							int time = (API.getPvPGotTime() + 1) * 20; time += 20;
							
							Timer.put("2. PvP Start " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPTime() + 1;
								public void run()
								{
									num--;
									
									if (!twoVS) {
										twoType = -1;
										twoName = null;
										twoInt = 0;
										Integer id = main.Timer.remove("2. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}

									if (num == 300) p.sendMessage("§6현재 대련 시간이 §c5 §6분 남았습니다.");
									else if (num == 240) p.sendMessage("§6현재 대련 시간이 §c4 §6분 남았습니다.");
									else if (num == 180) p.sendMessage("§6현재 대련 시간이 §c3 §6분 남았습니다.");
									else if (num == 120) p.sendMessage("§6현재 대련 시간이 §c2 §6분 남았습니다.");
									else if (num == 60) p.sendMessage("§6현재 대련 시간이 §c1 §6분 남았습니다.");
									else if (num == 30) p.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
									else if (num == 20) p.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
									else if (num == 10) p.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
									else if (num == 5) p.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
									else if (num <= 0) {
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										p.sendMessage("                                              §6대련이 종료되었습니다!");
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										
										twoType = 3;
										Integer id = main.Timer.remove("2. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
							
							time += (API.getPvPTime() + 1) * 20;
							
							Timer.put("2. PvP Finish " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPFinishTime() + 1;
								public void run()
								{
									num--;
									
									if (!twoVS) {
										twoType = -1;
										twoName = null;
										twoInt = 0;
										Integer id = main.Timer.remove("2. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.twoStopAPI(pO, pT);
										
										Integer id = main.Timer.remove("2. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
						}
					}
				}
			}
		}
	}
	
	public static void ThreevsThreeTimer()
	{
		if (threeList.size() >= 2) {
			if (!threeVS) {
				int i = 0;
				String pO = threeList.get(0);
				String pT = threeList.get(1);
				threeList.remove(0);
				threeList.remove(0);
				threeVS = true;
				threeType = 1;
				threeName = pO + "," + pT;
				threeInt = 0;
				
				if (PartyAPI.isParty(pO) && PartyAPI.isParty(pT)) {
					for (String user : PartyAPI.getUser(pO)) {
						Player p = Bukkit.getPlayerExact(user);
						
						if (p != null) {
							BarAPI.removeBar(p);
							main.BossBarTimer.remove(p);
							p.setGameMode(GameMode.SURVIVAL);
							
							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 100);
							if (i == 0) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getThreeRed1()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getThreeRed2()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getThreeRed3()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							i++;
							
							p.sendMessage("§c" + pT + " §6님의 파티와의 대련이 곧 시작됩니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
							
							Timer.put("3. Don't Move " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPGotTime() + 1;
								public void run()
								{
									num--;
									
									if (!threeVS) {
										threeType = -1;
										threeName = null;
										threeInt = 0;
										Integer id = main.Timer.remove("3. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6대련이 시작되었습니다!");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
										
										threeType = 2;
										Integer id = main.Timer.remove("3. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
							
							int time = (API.getPvPGotTime() + 1) * 20; time += 20;
							
							Timer.put("3. PvP Start " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPTime() + 1;
								public void run()
								{
									num--;
									
									if (!threeVS) {
										threeType = -1;
										threeName = null;
										threeInt = 0;
										Integer id = main.Timer.remove("3. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}

									if (num == 300) p.sendMessage("§6현재 대련 시간이 §c5 §6분 남았습니다.");
									else if (num == 240) p.sendMessage("§6현재 대련 시간이 §c4 §6분 남았습니다.");
									else if (num == 180) p.sendMessage("§6현재 대련 시간이 §c3 §6분 남았습니다.");
									else if (num == 120) p.sendMessage("§6현재 대련 시간이 §c2 §6분 남았습니다.");
									else if (num == 60) p.sendMessage("§6현재 대련 시간이 §c1 §6분 남았습니다.");
									else if (num == 30) p.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
									else if (num == 20) p.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
									else if (num == 10) p.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
									else if (num == 5) p.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
									else if (num <= 0) {
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										p.sendMessage("                                              §6대련이 종료되었습니다!");
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										
										threeType = 3;
										Integer id = main.Timer.remove("3. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
							
							time += (API.getPvPTime() + 1) * 20;
							
							Timer.put("3. PvP Finish " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPFinishTime() + 1;
								public void run()
								{
									num--;
									
									if (!threeVS) {
										threeType = -1;
										threeName = null;
										threeInt = 0;
										Integer id = main.Timer.remove("3. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.threeStopAPI(pO, pT);
										
										Integer id = main.Timer.remove("3. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
						}
					}
					
					i = 0;
					
					for (String user : PartyAPI.getUser(pT)) {
						Player p = Bukkit.getPlayerExact(user);
						
						if (p != null) {
							BarAPI.removeBar(p);
							main.BossBarTimer.remove(p);
							p.setGameMode(GameMode.SURVIVAL);

							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 100);
							if (i == 0) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getThreeBlue1()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getThreeBlue2()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getThreeBlue3()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							i++;
							
							p.sendMessage("§c" + pT + " §6님의 파티와의 대련이 곧 시작됩니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
							
							Timer.put("3. Don't Move " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPGotTime() + 1;
								public void run()
								{
									num--;
									
									if (!threeVS) {
										threeType = -1;
										threeName = null;
										threeInt = 0;
										Integer id = main.Timer.remove("3. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6대련이 시작되었습니다!");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
										
										threeType = 2;
										Integer id = main.Timer.remove("3. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
							
							int time = (API.getPvPGotTime() + 1) * 20; time += 20;
							
							Timer.put("3. PvP Start " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPTime() + 1;
								public void run()
								{
									num--;
									
									if (!threeVS) {
										threeType = -1;
										threeName = null;
										threeInt = 0;
										Integer id = main.Timer.remove("3. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}

									if (num == 300) p.sendMessage("§6현재 대련 시간이 §c5 §6분 남았습니다.");
									else if (num == 240) p.sendMessage("§6현재 대련 시간이 §c4 §6분 남았습니다.");
									else if (num == 180) p.sendMessage("§6현재 대련 시간이 §c3 §6분 남았습니다.");
									else if (num == 120) p.sendMessage("§6현재 대련 시간이 §c2 §6분 남았습니다.");
									else if (num == 60) p.sendMessage("§6현재 대련 시간이 §c1 §6분 남았습니다.");
									else if (num == 30) p.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
									else if (num == 20) p.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
									else if (num == 10) p.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
									else if (num == 5) p.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
									else if (num <= 0) {
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										p.sendMessage("                                              §6대련이 종료되었습니다!");
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										
										threeType = 3;
										Integer id = main.Timer.remove("3. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
							
							time += (API.getPvPTime() + 1) * 20;
							
							Timer.put("3. PvP Finish " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPFinishTime() + 1;
								public void run()
								{
									num--;
									
									if (!threeVS) {
										threeType = -1;
										threeName = null;
										threeInt = 0;
										Integer id = main.Timer.remove("3. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.threeStopAPI(pO, pT);
										
										Integer id = main.Timer.remove("3. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
						}
					}
				}
			}
		}
	}

	public static void FourvsFourTimer()
	{
		if (fourList.size() >= 2) {
			if (!fourVS) {
				int i = 0;
				String pO = fourList.get(0);
				String pT = fourList.get(1);
				fourList.remove(0);
				fourList.remove(0);
				fourVS = true;
				fourType = 1;
				fourName = pO + "," + pT;
				fourInt = 0;
				
				if (PartyAPI.isParty(pO) && PartyAPI.isParty(pT)) {
					for (String user : PartyAPI.getUser(pO)) {
						Player p = Bukkit.getPlayerExact(user);
						
						if (p != null) {
							BarAPI.removeBar(p);
							main.BossBarTimer.remove(p);
							p.setGameMode(GameMode.SURVIVAL);
							
							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 100);
							if (i == 0) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourRed1()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourRed2()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourRed3()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 3) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourRed4()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							i++;
							
							p.sendMessage("§c" + pT + " §6님의 파티와의 대련이 곧 시작됩니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
							
							Timer.put("4. Don't Move " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPGotTime() + 1;
								public void run()
								{
									num--;
									
									if (!fourVS) {
										fourType = -1;
										fourName = null;
										fourInt = 0;
										Integer id = main.Timer.remove("4. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6대련이 시작되었습니다!");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
										
										fourType = 2;
										Integer id = main.Timer.remove("4. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
							
							int time = (API.getPvPGotTime() + 1) * 20; time += 20;
							
							Timer.put("4. PvP Start " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPTime() + 1;
								public void run()
								{
									num--;
									
									if (!fourVS) {
										fourType = -1;
										fourName = null;
										fourInt = 0;
										Integer id = main.Timer.remove("4. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}

									if (num == 300) p.sendMessage("§6현재 대련 시간이 §c5 §6분 남았습니다.");
									else if (num == 240) p.sendMessage("§6현재 대련 시간이 §c4 §6분 남았습니다.");
									else if (num == 180) p.sendMessage("§6현재 대련 시간이 §c3 §6분 남았습니다.");
									else if (num == 120) p.sendMessage("§6현재 대련 시간이 §c2 §6분 남았습니다.");
									else if (num == 60) p.sendMessage("§6현재 대련 시간이 §c1 §6분 남았습니다.");
									else if (num == 30) p.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
									else if (num == 20) p.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
									else if (num == 10) p.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
									else if (num == 5) p.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
									else if (num <= 0) {
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										p.sendMessage("                                              §6대련이 종료되었습니다!");
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										
										fourType = 3;
										Integer id = main.Timer.remove("4. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
							
							time += (API.getPvPTime() + 1) * 20;
							
							Timer.put("4. PvP Finish " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPFinishTime() + 1;
								public void run()
								{
									num--;
									
									if (!fourVS) {
										fourType = -1;
										fourName = null;
										fourInt = 0;
										Integer id = main.Timer.remove("4. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.fourStopAPI(pO, pT);
										
										Integer id = main.Timer.remove("4. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
						}
					}
					
					i = 0;
					
					for (String user : PartyAPI.getUser(pT)) {
						Player p = Bukkit.getPlayerExact(user);
						
						if (p != null) {
							BarAPI.removeBar(p);
							main.BossBarTimer.remove(p);
							p.setGameMode(GameMode.SURVIVAL);

							Timer timer = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 100);
							if (i == 0) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourBlue1()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 1) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourBlue2()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 2) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourBlue3()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							else if (i == 3) { timer.schedule(new TimerTask() { public void run() {
							p.teleport(Position.getFourBlue4()); p.getLocation().getChunk().load();
							return; }}, timeToRun); }
							
							i++;
							
							p.sendMessage("§c" + pT + " §6님의 파티와의 대련이 곧 시작됩니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
							
							Timer.put("4. Don't Move " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPGotTime() + 1;
								public void run()
								{
									num--;
									
									if (!fourVS) {
										fourType = -1;
										fourName = null;
										fourInt = 0;
										Integer id = main.Timer.remove("4. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6대련이 시작되었습니다!");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
										
										fourType = 2;
										Integer id = main.Timer.remove("4. Don't Move " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§6대련 시작까지 §c" + num + " §6초 남았습니다.");
										if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
							
							int time = (API.getPvPGotTime() + 1) * 20; time += 20;
							
							Timer.put("4. PvP Start " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPTime() + 1;
								public void run()
								{
									num--;
									
									if (!fourVS) {
										fourType = -1;
										fourName = null;
										fourInt = 0;
										Integer id = main.Timer.remove("4. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}

									if (num == 300) p.sendMessage("§6현재 대련 시간이 §c5 §6분 남았습니다.");
									else if (num == 240) p.sendMessage("§6현재 대련 시간이 §c4 §6분 남았습니다.");
									else if (num == 180) p.sendMessage("§6현재 대련 시간이 §c3 §6분 남았습니다.");
									else if (num == 120) p.sendMessage("§6현재 대련 시간이 §c2 §6분 남았습니다.");
									else if (num == 60) p.sendMessage("§6현재 대련 시간이 §c1 §6분 남았습니다.");
									else if (num == 30) p.sendMessage("§6현재 대련 시간이 §c30 §6초 남았습니다.");
									else if (num == 20) p.sendMessage("§6현재 대련 시간이 §c20 §6초 남았습니다.");
									else if (num == 10) p.sendMessage("§6현재 대련 시간이 §c10 §6초 남았습니다.");
									else if (num == 5) p.sendMessage("§6현재 대련 시간이 §c5 §6초 남았습니다.");
									else if (num <= 0) {
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										p.sendMessage("                                              §6대련이 종료되었습니다!");
										p.sendMessage("");
										p.sendMessage("§f==========================================================");
										p.sendMessage("");
										
										fourType = 3;
										Integer id = main.Timer.remove("4. PvP Start " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
							
							time += (API.getPvPTime() + 1) * 20;
							
							Timer.put("4. PvP Finish " + user + "", new BukkitRunnable()
							{
								int num = API.getPvPFinishTime() + 1;
								public void run()
								{
									num--;
									
									if (!fourVS) {
										fourType = -1;
										fourName = null;
										fourInt = 0;
										Integer id = main.Timer.remove("4. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
									
									if (num <= 0) {
										p.sendMessage("§6스폰으로 이동되었습니다.");
										API.sendCommand("heal " + p.getName());
										API.sendCommand("spawn " + p.getName());
										API.fourStopAPI(pO, pT);
										
										Integer id = main.Timer.remove("4. PvP Finish " + user + "");
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
						}
					}
				}
			}
		}
	}
	
	public static void ColovsColoTimer()
	{
		if (coloList.size() >= 2) {
			if (!coloVS) {
				Player p1 = main.coloList.get(0);
				Player p2 = main.coloList.get(1);
				coloList.remove(0);
				coloList.remove(0);
				BarAPI.removeBar(p1);
				BarAPI.removeBar(p2);
				main.BossBarTimer.remove(p1);
				main.BossBarTimer.remove(p2);
				p1.setGameMode(GameMode.SURVIVAL);
				p2.setGameMode(GameMode.SURVIVAL);
				p1.getInventory().setHelmet(new MaterialData(298, (byte) 0).toItemStack(1));
				p1.getInventory().setChestplate(new MaterialData(299, (byte) 0).toItemStack(1));
				p1.getInventory().setLeggings(new MaterialData(300, (byte) 0).toItemStack(1));
				p1.getInventory().setBoots(new MaterialData(301, (byte) 0).toItemStack(1));
				p1.setItemInHand(new MaterialData(268, (byte) 0).toItemStack(1));
				p2.getInventory().setHelmet(new MaterialData(298, (byte) 0).toItemStack(1));
				p2.getInventory().setChestplate(new MaterialData(299, (byte) 0).toItemStack(1));
				p2.getInventory().setLeggings(new MaterialData(300, (byte) 0).toItemStack(1));
				p2.getInventory().setBoots(new MaterialData(301, (byte) 0).toItemStack(1));
				p2.setItemInHand(new MaterialData(268, (byte) 0).toItemStack(1));
				
				Timer timer = new Timer();
				Timer timer2 = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 200);
				timer.schedule(new TimerTask() {
					public void run() {
						p1.teleport(Position.getColoRed());
						p1.getLocation().getChunk().load();
						return;
					}
				}, timeToRun);
				
				timeToRun = new Date(System.currentTimeMillis() + 400);
				
				timer2.schedule(new TimerTask() {
					public void run() {
						p2.teleport(Position.getColoBlue());
						p2.getLocation().getChunk().load();
						return;
					}
				}, timeToRun);
				
				Bukkit.broadcastMessage(prx + "§6콜로세움에서 §c" + p1.getName() + " §6님과 §c" + p2.getName() + " §6님이 대련을 시작할 예정입니다!");
				Bukkit.broadcastMessage(prx + "§6채팅창에 §e[ /관전 ] §6입력시 콜로세움 관전장으로 §c이동 §6합니다.");
				p1.sendMessage("§c" + p2.getName() + " §6님과의 콜로세움 대련이 곧 시작됩니다.");
				p2.sendMessage("§c" + p1.getName() + " §6님과의 콜로세움 대련이 곧 시작됩니다.");
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p1, "효과음")) p1.playSound(p1.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p2, "효과음")) p2.playSound(p2.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
				
				coloVS = true;
				coloType = 1;
				coloName = p1.getName() + "," + p2.getName();
				if (main.coloBetting == 0) main.coloBetting = 1000;
				
				Timer.put("5. Don't Move", new BukkitRunnable()
				{
					int num = API.getColoGotTime() + 1;
					public void run()
					{
						num--;
						
						if (!coloVS) {
							coloType = -1;
							coloName = null;
							Integer id = main.Timer.remove("5. Don't Move");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num == 0) {
							for (Player p : Bukkit.getWorld("world_colosseum").getPlayers()) {
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 1.25F);
							}
							
							Bukkit.broadcastMessage(prx + "§6콜로세움에서 §c" + p1.getName() + " §6VS §c" + p2.getName() + " §6님의 대결이 시작되었습니다! §e[ /관전 ]");
							
							coloType = 2;
							Integer id = main.Timer.remove("5. Don't Move");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							if (num % 5 == 0 && num != 5) {
								for (Player p : Bukkit.getWorld("world_colosseum").getPlayers()) {
									p.sendMessage(prx + "§6대련 시작까지 §c" + num + " §6초 남았습니다.");
									if (!p1.getName().equalsIgnoreCase(p.getName()) && !p2.getName().equalsIgnoreCase(p.getName())) {
										if (main.coloMoney.get(p) == null) {
											p.sendMessage(prx + "§6채팅창에 §e[ /배팅 <1/2> ] §6입력하셔서 배팅을 해보세요. 배팅 금액: §c" + main.coloBetting);
											p.sendMessage(prx + "§61. §c" + p1.getName());
											p.sendMessage(prx + "§62. §c" + p2.getName());
										}
									}
								}
							}
							
							else if (num <= 5) {
								for (Player p : Bukkit.getWorld("world_colosseum").getPlayers()) {
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 2.0F, 1.2F);
									p.sendMessage(prx + "§6대련 시작까지 §c" + num + " §6초 남았습니다.");
								}
							}
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
				
				int time = (API.getColoGotTime() + 1) * 20; time += 20;
				
				Timer.put("5. PvP Start", new BukkitRunnable()
				{
					int num = API.getColoTime() + 1;
					public void run()
					{
						num--;
						
						if (!coloVS) {
							coloType = -1;
							coloName = null;
							Integer id = main.Timer.remove("5. PvP Start");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num % 10 == 0 && num > 10) for (Player p : Bukkit.getWorld("world_colosseum").getPlayers()) p.sendMessage(prx + "§6현재 대련 시간이 §c" + num + " §6초 남았습니다.");
						else if (num <= 5 && num > 0) for (Player p : Bukkit.getWorld("world_colosseum").getPlayers()) p.sendMessage(prx + "§6현재 대련 시간이 §c" + num + " §6초 남았습니다.");
						
						else if (num == 0) {
							p1.sendMessage("");
							p1.sendMessage("§f==========================================================");
							p1.sendMessage("");
							p1.sendMessage("                                              §6대련이 종료되었습니다!");
							p1.sendMessage("");
							p1.sendMessage("§f==========================================================");
							p1.sendMessage("");
							p2.sendMessage("");
							p2.sendMessage("§f==========================================================");
							p2.sendMessage("");
							p2.sendMessage("                                              §6대련이 종료되었습니다!");
							p2.sendMessage("");
							p2.sendMessage("§f==========================================================");
							p2.sendMessage("");
							
							for (Player p : Bukkit.getWorld("world_colosseum").getPlayers()) {
								if (!p1.equals(p) && !p2.equals(p)) {
									p.sendMessage(prx + "§6타이머가 지나 대련이 §c종료 §6되었습니다.");
								}
							}
							
							API.setColosseumCooltime(p1);
							API.setColosseumCooltime(p2);
							
							coloType = 3;
							Integer id = main.Timer.remove("5. PvP Start");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
				
				API.coloReturnBetting(prx + "§6콜로세움 경기가 §c무승부 §6로 끝나서 §c" + main.coloBetting + " §6원을 돌려받았습니다.");
				time += (API.getColoTime() + 1) * 20;
				
				Timer.put("5. PvP Finish", new BukkitRunnable()
				{
					int num = API.getColoFinishTime() + 1;
					public void run()
					{
						num--;
						
						if (!coloVS) {
							coloType = -1;
							coloName = null;
							Integer id = main.Timer.remove("5. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p1.sendMessage("§6스폰으로 이동되었습니다.");
							p2.sendMessage("§6스폰으로 이동되었습니다.");
							coloType = -1;
							coloVS = false;
							coloName = null;
							coloBetting = 0;
	    					main.coloWinnerName = null;
							p1.chat("/spawn");
							p2.chat("/spawn");
							API.sendCommand("heal " + p1.getName());
							API.sendCommand("heal " + p2.getName());
							API.coloStopAPI(p1);
							API.coloStopAPI(p2);
							p1.getInventory().setHelmet(null);
							p1.getInventory().setChestplate(null);
							p1.getInventory().setLeggings(null);
							p1.getInventory().setBoots(null);
							p1.setItemInHand(null);
							p2.getInventory().setHelmet(null);
							p2.getInventory().setChestplate(null);
							p2.getInventory().setLeggings(null);
							p2.getInventory().setBoots(null);
							p2.setItemInHand(null);
							
							Integer id = main.Timer.remove("5. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p1.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
							p2.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), time, 20L).getTaskId());
			}
		}
	}
}
