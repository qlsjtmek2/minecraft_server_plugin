package me.shinkhan.Streng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
    
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
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
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/DHStrengthen/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHStrengthen");
		File folder2 = new File("plugins/DHStrengthen/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists())
			Method.CreatePlayerInfo(p, f, folder, folder2, config);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("강화")) {
				if (args.length == 0) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.DARK_RED + "콘솔에선 실행이 불가능한 명령어 입니다.");
						return false;
					}

					Player p = (Player) sender;
					ItemStack i = p.getItemInHand();
					
					if (p.getItemInHand() == null) {
						p.sendMessage("§c손에 아이템을 들고 명령어를 입력해 주시기 바랍니다.");
						return false;
					}

					if (Method.getIntYml(p, "강화 기회") <= 0) {
						p.sendMessage("§c강화 기회가 없어서 강화를 하실 수 없습니다.");
						return false;
					}
					
					int Id = i.getTypeId();
					ItemMeta m = i.getItemMeta();
					
					if (Id == 268 || Id == 272 || Id == 283 || Id == 267 || Id == 276 || Id == 261) {
						int RandomAmount = new Random().nextInt(10) + 1;
						List<String> list = new ArrayList<String>();
						
		    			if (m.getLore() != null) {
							list = m.getLore();
			    			for (String str : list) {
			    				if (str.contains("공격력")) {
			    					list.remove(str);
			    					break;
			    				} else { continue; }
			    			}
			    			
			    			switch (RandomAmount) {
		    				case 1:
		    					list.add("§f+0 공격력");
		    					m.setLore(list);
		    					i.setItemMeta(m);
		    					p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
		    					break;
		    				case 2:
		    					list.add("§f+0 공격력");
		    					m.setLore(list);
		    					i.setItemMeta(m);
		    					p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
		    					break;
							case 3:
								list.add("§f+0 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
								break;
							case 4:
								list.add("§f+0 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
								break;
							case 5:
								list.add("§f+1 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c1 §6추가되었습니다.");
								break;
							case 6:
								list.add("§f+1 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c1 §6추가되었습니다.");
								break;
							case 7:
								list.add("§f+2 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c2 §6추가되었습니다.");
								break;
							case 8:
								list.add("§f+2 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c2 §6추가되었습니다.");
								break;
							case 9:
								list.add("§b+3 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c3 §6추가되었습니다.");
								break;
							case 10:
								list.add("§c+4 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c4 §6추가되었습니다.");
								break;
			    			}
		    			} else {
		    				switch (RandomAmount) {
							case 1:
								list.add("§f+0 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
								break;
							case 2:
								list.add("§f+0 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
								break;
							case 3:
								list.add("§f+0 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
								break;
							case 4:
								list.add("§f+0 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c0 §6추가되었습니다.");
								break;
							case 5:
								list.add("§f+1 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c1 §6추가되었습니다.");
								break;
							case 6:
								list.add("§f+1 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c1 §6추가되었습니다.");
								break;
							case 7:
								list.add("§f+2 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c2 §6추가되었습니다.");
								break;
							case 8:
								list.add("§f+2 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c2 §6추가되었습니다.");
								break;
							case 9:
								list.add("§b+3 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c3 §6추가되었습니다.");
								break;
							case 10:
								list.add("§c+4 공격력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 무기에 §c강화§6를 하여 공격력이 §c4 §6추가되었습니다.");
								break;
		    				}
		    			}
						
						int n = Method.getIntYml(p, "강화 기회") - 1;
						Method.setIntYml(p, "강화 기회", n);
						castLvup(p);
					}
					
					else if (Id == 298 || Id == 299 || Id == 300 || Id == 301
						  || Id == 302 || Id == 303 || Id == 304 || Id == 305
						  || Id == 306 || Id == 307 || Id == 308 || Id == 309
						  || Id == 310 || Id == 311 || Id == 312 || Id == 313
						  || Id == 314 || Id == 315 || Id == 316 || Id == 317) {
						
						int RandomAmount = new Random().nextInt(10) + 1;
						List<String> list = new ArrayList<String>();
						
		    			if (m.getLore() != null) {
							list = m.getLore();
			    			for (String str : list) {
			    				if (str.contains("생명력")) {
			    					list.remove(str);
			    					break;
			    				} else { continue; }
			    			}
			    			
			    			switch (RandomAmount) {
							case 1:
								list.add("§f+1 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c1 §6추가되었습니다.");
								break;
							case 2:
								list.add("§f+1 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c1 §6추가되었습니다.");
								break;
							case 3:
								list.add("§f+1 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c1 §6추가되었습니다.");
								break;
							case 4:
								list.add("§f+2 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c2 §6추가되었습니다.");
								break;
							case 5:
								list.add("§f+2 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c2 §6추가되었습니다.");
								break;
							case 6:
								list.add("§f+2 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c2 §6추가되었습니다.");
								break;
							case 7:
								list.add("§f+3 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c3 §6추가되었습니다.");
								break;
							case 8:
								list.add("§f+3 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c3 §6추가되었습니다.");
								break;
							case 9:
								list.add("§b+4 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c4 §6추가되었습니다.");
								break;
							case 10:
								list.add("§c+5 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c5 §6추가되었습니다.");
								break;
			    			}
		    			} else {
		    				switch (RandomAmount) {
							case 1:
								list.add("§f+1 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c1 §6추가되었습니다.");
								break;
							case 2:
								list.add("§f+1 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c1 §6추가되었습니다.");
								break;
							case 3:
								list.add("§f+1 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c1 §6추가되었습니다.");
								break;
							case 4:
								list.add("§f+2 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c2 §6추가되었습니다.");
								break;
							case 5:
								list.add("§f+2 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c2 §6추가되었습니다.");
								break;
							case 6:
								list.add("§f+2 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c2 §6추가되었습니다.");
								break;
							case 7:
								list.add("§f+3 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c3 §6추가되었습니다.");
								break;
							case 8:
								list.add("§f+3 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c3 §6추가되었습니다.");
								break;
							case 9:
								list.add("§b+4 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c4 §6추가되었습니다.");
								break;
							case 10:
								list.add("§c+5 생명력");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("§6당신은 방어구에 §c강화§6를 하여 생명력이 §c5 §6추가되었습니다.");
								break;
			    			}
		    			}
						
						int n = Method.getIntYml(p, "강화 기회") - 1;
						Method.setIntYml(p, "강화 기회", n);
						castLvup(p);
					} else {
						p.sendMessage("§c무기나 방어구만 강화가 가능합니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("도움말")) {
					Help(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("보기")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.DARK_RED + "콘솔에선 실행이 불가능한 명령어 입니다.");
						return false;
					}

					Player p = (Player) sender;
					int i = Method.getIntYml(p, "강화 기회");
					
					p.sendMessage("§6당신의 강화 기회는 §c" + i + " §6번 입니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			Help(sender);
		}
		
		if (commandLabel.equalsIgnoreCase("강화추가")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "콘솔에선 실행이 불가능한 명령어 입니다.");
				return false;
			}
			
			Player p = (Player) sender;
			
			if (!p.isOp()) {
				return false;
			}
			
			p.sendMessage("§6강화 기회가 §c1 §6추가되었습니다.");
			int n = Method.getIntYml(p, "강화 기회") + 1;
			Method.setIntYml(p, "강화 기회", n);
		} return false;
	}
	
	public static void Help(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "강화 " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "페이지 " 
						 + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/강화 " + ChatColor.WHITE + "- 손에 든 아이템을 강화합니다.");
		sender.sendMessage(ChatColor.GOLD + "/강화 보기" + ChatColor.WHITE + "- 내 강화 기회를 봅니다.");
		sender.sendMessage(ChatColor.GOLD + "/강화 도움말" + ChatColor.WHITE + "- 강화 도움말을 봅니다.");
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
}
