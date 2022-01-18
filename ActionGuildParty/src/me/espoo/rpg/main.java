package me.espoo.rpg;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.guild.Guild;
import me.espoo.rpg.guild.GuildCommand;
import me.espoo.rpg.guild.GuildConfig;
import me.espoo.rpg.guild.GuildEvent;
import me.espoo.rpg.guild.GuildYml;
import me.espoo.rpg.party.PartyCommand;
import me.espoo.rpg.party.PartyEvent;
import net.milkbowl.vault.economy.Economy;
import us.talabrek.ultimateskyblock.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

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
	public static int createGuildMoney;
	public static List<String> guildConfig = new ArrayList<String>();
	
	public void onEnable()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				AutoDeleteGuild();
			}
		}, 72000L, 72000L);
		
		File folder = new File("plugins/ActionGuildParty");
		File F1 = new File("plugins/ActionGuildParty/Guild.yml");
		File F2 = new File("plugins/ActionGuildParty/GuildConfig.yml");
		YamlConfiguration C1 = YamlConfiguration.loadConfiguration(F1);
		YamlConfiguration C2 = YamlConfiguration.loadConfiguration(F2);
		if (!F1.exists()) GuildYml.CreateGuildYml(F1, folder, C1);
		if (!F2.exists()) GuildConfig.CreateGuildConfig(F2, folder, C2);
		
		for (String file : getFileList()) {
		    PlayerInfo pi = uSkyBlock.getInstance().readPlayerFile(file);
		    uSkyBlock.getInstance().addActivePlayer(file, pi);
		}
		
		createGuildMoney = Integer.parseInt(GuildConfig.getGuildString("��� â�� ���"));
		guildConfig = GuildConfig.getGuildList("��� ���׷��̵�");

		GUIMessage.setGUIMessage();
		getCommand("expg").setExecutor(new GuildCommand(this));
		getCommand("guild").setExecutor(new GuildCommand(this));
		getCommand("���").setExecutor(new GuildCommand(this));
		getCommand("expp").setExecutor(new PartyCommand(this));
		getCommand("party").setExecutor(new PartyCommand(this));
		getCommand("��Ƽ").setExecutor(new PartyCommand(this));
		getCommand("chatmode").setExecutor(new PartyCommand(this));
		getCommand("cm").setExecutor(new PartyCommand(this));
		getCommand("ä�ø��").setExecutor(new PartyCommand(this));
		getServer().getPluginManager().registerEvents(new GuildEvent(this), this);
		getServer().getPluginManager().registerEvents(new PartyEvent(this), this);
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static void AutoDeleteGuild()
	{
		List<String> list = GuildYml.getGuildList();
		for (String str : list) {
			Guild guild = Guild.guilds.get(str);
			String st = guild.getManagerLastJoin();
			String master = guild.getManager();
			Calendar cal = Calendar.getInstance();
			if (Integer.parseInt(st.split(",")[0]) < (cal.get(Calendar.MONTH) + 1)) {
				if (Integer.parseInt(st.split(",")[1]) <= cal.get(Calendar.DATE)) {
					for (String player : guild.getUsers()) {
						if (master.equalsIgnoreCase(player.split(",")[0])) continue;
						guild.subUser(Bukkit.getOfflinePlayer(player.split(",")[0]));
					}
					
					guild.deleteGuildData();
					Bukkit.broadcastMessage("��f[��4�˸���f] ��c" + str + " ��6����� ��� �����Ͱ� �Ѵ޵��� ������ ���� �ʾ� ������ ��c����6�Ǿ����ϴ�.");
				}
			}
		}
	}
	
	public static List<String> getFileList()
	{
		List<String> list = new ArrayList<String>();
		File directory = new File("plugins/uSkyBlock/players");
		/** ������ ���丮 ���� ������ ���� **/
	    File[] childs = directory.listFiles(new FileFilter() {
	    	public boolean accept(File pathname) { return pathname.isFile(); }
	    });

	    //childs.length�� �ش� ���� ���� ����+�������� ������ ���Ѵ�.
	    for (int i = 0; i < childs.length; i++) {
	    	list.add(childs[i].getName());
	    } return list;
	}
}
