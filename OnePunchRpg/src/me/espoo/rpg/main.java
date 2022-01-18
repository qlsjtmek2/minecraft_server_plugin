package me.espoo.rpg;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.exp.ExpAPI;
import me.espoo.rpg.exp.ExpCommand;
import me.espoo.rpg.exp.ExpConfig;
import me.espoo.rpg.exp.ExpEvent;
import me.espoo.rpg.exp.ExpYml;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.guild.GuildCommand;
import me.espoo.rpg.guild.GuildConfig;
import me.espoo.rpg.guild.GuildEvent;
import me.espoo.rpg.guild.GuildYml;
import me.espoo.rpg.party.PartyCommand;
import me.espoo.rpg.party.PartyEvent;
import me.espoo.rpg.party.PartyYml;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener {
	public static Economy economy = null;
	public static List<Player> CreateGuild = new ArrayList<Player>();
	public static List<Player> InvitePlayer = new ArrayList<Player>();
	public static List<Player> InvitePartyPlayer = new ArrayList<Player>();
	public static HashMap<Player, Player> OkInviteParty = new HashMap<Player, Player>();
	public static HashMap<Player, Player> PartyJoin = new HashMap<Player, Player>();
	public static HashMap<Player, String> SetLoreParty = new HashMap<Player, String>();
	public static HashMap<Player, String> SetLore = new HashMap<Player, String>();
	public static HashMap<Player, String> SetStatMessage = new HashMap<Player, String>();
	public static HashMap<Player, String> SetItemCode = new HashMap<Player, String>();
	public static HashMap<Player, Player> OkInvite = new HashMap<Player, Player>();
	public static HashMap<String, String> GuildName = new HashMap<String, String>();
	public static HashMap<Player, String> ClickAccept = new HashMap<Player, String>();
	public static HashMap<Player, String> pos1 = new HashMap<Player, String>();
	public static HashMap<Player, String> pos2 = new HashMap<Player, String>();
	public static HashMap<Player, Double> LandMoney = new HashMap<Player, Double>();
	
	public void onEnable()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ServerBounsTimer(p);
					TypeBounsTimer(p);
				}
			}
		}, 600L, 600L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				AutoDeleteGuild();
			}
		}, 72000L, 72000L);
		
		File folder = new File("plugins/OnePunchRpg");
		File F1 = new File("plugins/OnePunchRpg/Exp.yml");
		File F2 = new File("plugins/OnePunchRpg/ExpConfig.yml");
		File F3 = new File("plugins/OnePunchRpg/Guild.yml");
		File F4 = new File("plugins/OnePunchRpg/GuildConfig.yml");
		File F5 = new File("plugins/OnePunchRpg/Party.yml");
		YamlConfiguration C1 = YamlConfiguration.loadConfiguration(F1);
		YamlConfiguration C2 = YamlConfiguration.loadConfiguration(F2);
		YamlConfiguration C3 = YamlConfiguration.loadConfiguration(F3);
		YamlConfiguration C4 = YamlConfiguration.loadConfiguration(F4);
		YamlConfiguration C5 = YamlConfiguration.loadConfiguration(F5);
		if (!F1.exists()) ExpYml.CreateExpYml(F1, folder, C1);
		if (!F2.exists()) ExpConfig.CreateExpConfig(F2, folder, C2);
		if (!F3.exists()) GuildYml.CreateGuildYml(F3, folder, C3);
		if (!F4.exists()) GuildConfig.CreateGuildConfig(F4, folder, C4);
		if (!F5.exists()) PartyYml.CreatePartyYml(F5, folder, C5);

		GUIMessage.setGUIMessage();
		getCommand("exp").setExecutor(new ExpCommand(this));
		getCommand("경험치").setExecutor(new ExpCommand(this));
		getCommand("expg").setExecutor(new GuildCommand(this));
		getCommand("guild").setExecutor(new GuildCommand(this));
		getCommand("길드").setExecutor(new GuildCommand(this));
		getCommand("expp").setExecutor(new PartyCommand(this));
		getCommand("party").setExecutor(new PartyCommand(this));
		getCommand("파티").setExecutor(new PartyCommand(this));
		getCommand("chatmode").setExecutor(new PartyCommand(this));
		getCommand("cm").setExecutor(new PartyCommand(this));
		getCommand("채팅모드").setExecutor(new PartyCommand(this));
		getServer().getPluginManager().registerEvents(new ExpEvent(this), this);
		getServer().getPluginManager().registerEvents(new GuildEvent(this), this);
		getServer().getPluginManager().registerEvents(new PartyEvent(this), this);
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		File F = new File("plugins/OnePunchRpg/Party.yml");
		if (F.exists()) F.delete();
		
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			PlayerYml.setInfoString(p, "가입된 파티", null);
		}
		
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public static void AutoDeleteGuild()
	{
		List<String> list = GuildYml.getGuildList();
		for (String str : list) {
			String st = GuildAPI.getJoinMaster(str);
			String master = GuildAPI.getManager(str);
			Calendar cal = Calendar.getInstance();
			if (Integer.parseInt(st.split(",")[0]) < (cal.get(Calendar.MONTH) + 1)) {
				if (Integer.parseInt(st.split(",")[1]) <= cal.get(Calendar.DATE)) {
					for (String player : GuildAPI.getUser(str)) {
						if (master.equalsIgnoreCase(player.split(",")[0])) continue;
						GuildAPI.subUser(str, Bukkit.getOfflinePlayer(player.split(",")[0]));
					}
					
					GuildYml.deleteGuild(str, Bukkit.getOfflinePlayer(master));
					Bukkit.broadcastMessage("§f[§4알림§f] §c" + str + " §6길드의 길드 마스터가 한달동안 접속을 하지 않아 강제로 §c폐쇄§6되었습니다.");
				}
			}
		}
	}

	public static void ServerBounsTimer(Player p)
	{
		int bouns = ExpAPI.getServerBouns();
		int time = ExpAPI.getServerTimerBouns();
		
		if (time != 0) {
			time--;
			ExpAPI.setServerBounsTimer(time);
			
			if (time <= 0) {
				ExpAPI.setServerBouns(100);
				Bukkit.broadcastMessage("§f[§4알림§f] §6서버 경험치 §c" + bouns + "% §6이벤트가 종료되었습니다.");
			}
		}
	}
	
	public static void TypeBounsTimer(Player p)
	{
		for (String type : ExpConfig.getExpList("타입 목록")) {
			List<String> list = ExpConfig.getExpList("보너스." + type);
			int i = 0;
			for (String str : ExpConfig.getExpList("보너스." + type)) {
				int time = Integer.parseInt(str.split(",")[2]);
				if (time == -1) continue;
				
				time--;
				if (time <= 0) {
					if (Method.getOnorOffLine(str.split(",")[0]) != null) {
						Method.getOnorOffLine(str.split(",")[0]).sendMessage("§c" + type + "§6의 보너스 타이머가 만료되었습니다.");
					} list.remove(i);
				} else {
					list.set(i, str.split(",")[0] + "," + str.split(",")[1] + "," + time);
					i++;
				}
			}
			
			ExpConfig.setExpList("보너스." + type, list);
		}
	}
}
