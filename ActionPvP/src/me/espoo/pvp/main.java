package me.espoo.pvp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.confuser.barapi.BarAPI;
import me.espoo.pvp.ranking.AutoSave;
import me.espoo.pvp.ranking.AutoUpdateRank;
import me.espoo.pvp.ranking.Ranking;
import me.espoo.pvp.yml.CustomConfig2;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class main extends JavaPlugin implements Listener {
	public static String prx = "§f[§4알림§f] ";
	public static String oneName = null;
	public static boolean oneVS = false;
	public static byte oneType = -1;
	public static Economy economy = null;
	public static Permission permission = null;
    static final Map <String, Integer> Timer = new HashMap<>();
	public static HashMap<String, Integer> PartyCheck = new HashMap<String, Integer>();
	public static HashMap<Player, Integer> BossBarTimer = new HashMap<Player, Integer>();
	public static List<Player> oneList = new ArrayList<Player>();
    public static CustomConfig2 user;
	
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
			}
		}, 20L, 20L);

        main.user = new CustomConfig2(this, "User");
		File f = new File("plugins/ActionPvP/Config.yml");
		File folder = new File("plugins/ActionPvP");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Config.CreateConfig(f, folder, c);
		
        GUIMessage.setGUIMessage();
		getCommand("대련").setExecutor(new mainCommand(this));
		getCommand("대전").setExecutor(new mainCommand(this));
		getCommand("PVP").setExecutor(new mainCommand(this));
		getCommand("대련위치").setExecutor(new mainCommand(this));
		getCommand("대전위치").setExecutor(new mainCommand(this));
		getCommand("PVP위치").setExecutor(new mainCommand(this));
		getCommand("대련취소").setExecutor(new mainCommand(this));
		getCommand("경기취소").setExecutor(new mainCommand(this));
		getCommand("대전취소").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		
		defaultSetting();
	    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AutoUpdateRank(), 21000L, 21000L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AutoSave(), 7000L, 7000L);
	    Ranking.updateRanking();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
        main.user.saveConfig();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
    public void defaultSetting() {
        for (String target : main.user.getKeys(false)) {
			API.setPVPPlayer(target);
	    }
        
        Ranking.updateRanking();
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
				API.ConsoleCommandTP(p1.getName(), Position.getOneRed());
				p1.getLocation().getChunk().load();
				
				PlayerTimer timer = new PlayerTimer(p2, new Runnable() {
					@Override
					public void run() {
						API.ConsoleCommandTP(p2.getName(), Position.getOneBlue());
						p1.getLocation().getChunk().load();
						p2.getLocation().getChunk().load();
					}
				});
				
				timer.setTime(4);
				timer.Start();
				
				if (me.shinkhan.epm.API.getSetPet(p1) != null && me.shinkhan.epm.API.isShowPet(p1)) {
					p1.sendMessage("§c당신의 펫은 1:1에서 적용되지 않습니다.");
				}
				
				if (me.shinkhan.epm.API.getSetPet(p2) != null && me.shinkhan.epm.API.isShowPet(p2)) {
					p2.sendMessage("§c당신의 펫은 1:1에서 적용되지 않습니다.");
				}
				
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
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionPvP"), 20L, 20L).getTaskId());
				
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
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionPvP"), time, 20L).getTaskId());
				
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
							if (p1.isOnline()) p1.setHealth(p1.getMaxHealth());
							if (p2.isOnline()) p2.setHealth(p2.getMaxHealth());
							API.oneStopAPI(p1, p2);
							
							Integer id = main.Timer.remove("1. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p1.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
							p2.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionPvP"), time, 20L).getTaskId());
			}
		}
	}
}
