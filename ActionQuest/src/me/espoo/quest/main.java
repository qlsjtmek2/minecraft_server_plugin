package me.espoo.quest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.quest.yml.mainYml;
import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener {
	public static HashMap<Player, List<String>> QuestTalk = new HashMap<Player, List<String>>();
	public static HashMap<Player, Show> ThreadShow = new HashMap<Player, Show>();
    static final Map <String, Integer> ThreadCool = new HashMap<>();
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
		QuestList.clearQuestList();
		QuestList.setQuestList();
		for (int i = 0; ; i++) {
			if (mainYml.getString((i+1) + ".이름") == null) {
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
					if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
						QuestList.clearQuestList();
						QuestList.setQuestList();
						for (int i = 0; ; i++) {
							if (mainYml.getString((i+1) + ".이름") == null) {
								mainMaxStep = i;
								break;
							}
						}
						
						sender.sendMessage("성공적으로 리로드 했습니다.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("Name") && sender.isOp()) {
						for (String str : QuestList.ListName) {
							sender.sendMessage(str);
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("Reword") && sender.isOp()) {
						for (String str : QuestList.ListReward.get(Integer.parseInt(args[1]))) {
							sender.sendMessage(str);
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("Talk") && sender.isOp()) {
						for (String str : QuestList.ListTalk.get(Integer.parseInt(args[1]))) {
							sender.sendMessage(str);
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("Task") && sender.isOp()) {
						for (String str : QuestList.ListTask.get(Integer.parseInt(args[1]))) {
							sender.sendMessage(str);
						} return false;
					}
				}
			}
		} catch (NumberFormatException ex) {
		} return false;
	}
}
