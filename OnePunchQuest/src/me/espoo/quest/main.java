package me.espoo.quest;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.quest.yml.mainYml;
import me.espoo.quest.yml.positionYml;
import me.espoo.quest.yml.subYml;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener {
	public static HashMap<Player, List<String>> QuestTalk = new HashMap<Player, List<String>>();
	public static HashMap<Player, Show> ThreadShow = new HashMap<Player, Show>();
	public static HashMap<Player, String> QuestType = new HashMap<Player, String>();
	public static HashMap<Player, String> QuestNPC = new HashMap<Player, String>();
	public static Economy economy = null;
	public static int mainMaxStep = 0;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public void onEnable()
	{
		File folder = new File("plugins/OnePunchQuest");
		File F1 = new File("plugins/OnePunchQuest/main.yml");
		File F2 = new File("plugins/OnePunchQuest/sub.yml");
		File F3 = new File("plugins/OnePunchQuest/position.yml");
		YamlConfiguration C1 = YamlConfiguration.loadConfiguration(F1);
		YamlConfiguration C2 = YamlConfiguration.loadConfiguration(F2);
		YamlConfiguration C3 = YamlConfiguration.loadConfiguration(F3);
		if (!F1.exists()) mainYml.CreateMainYml(F1, folder, C1);
		if (!F2.exists()) subYml.CreateSubYml(F2, folder, C2);
		if (!F3.exists()) positionYml.CreatePositionYml(F3, folder, C3);
		
		for (int i = 0; ; i++) {
			if (mainYml.getString((i+1) + ".NPC") == null) {
				mainMaxStep = i;
				break;
			}
		}
		
		GUIMessage.setGUIMessage();
		getServer().getPluginManager().registerEvents(new Event(this), this);
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("퀘스트")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("hidden") && sender.isOp()) {
						System.out.println(main.mainMaxStep);
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
						for (int i = 0; ; i++) {
							if (mainYml.getString((i+1) + ".NPC") == null) {
								mainMaxStep = i;
								break;
							}
						}
						return false;
					}
				}
				
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.QuestListGUI(p, 1);
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6/퀘스트 §f- 현재 보유중인 퀘스트 목록을 확인합니다.");
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("퀘스트좌표저장")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					API.addPosition(API.LocationToString(p.getLocation()));
					p.sendMessage("§6성공적으로 서계신 §c좌표 §6를 저장하셨습니다.");
					return false;
				} else {
					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6/퀘스트좌표저장 §f- 현재 서있는 좌표를 position.yml 파일에 저장합니다.");
		}
		
		return false;
	}
}
