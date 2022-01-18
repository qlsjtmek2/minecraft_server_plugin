package me.espoo.punchstat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.file.PlayerYml;

public class Method {
	static me.espoo.loon.PlayerYml P;
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        return message;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	public static void add1StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "팔 근력 효과", effect);
		PlayerYml.setInfoInt(p, "팔 근력 지속시간", time);
	}
	
	public static void add2StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "복근 효과", effect);
		PlayerYml.setInfoInt(p, "복근 지속시간", time);
		if (PlayerYml.getInfoBoolean(p, "복근 스텟 적용")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
	}
	
	public static void add3StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "다리 근력 효과", effect);
		PlayerYml.setInfoInt(p, "다리 근력 지속시간", time);
		if (PlayerYml.getInfoBoolean(p, "다리 근력 스텟 적용")) {
			int y = (PlayerYml.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
			if (y > 0) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, y - 1, true));
			}
		}
	}
	
	public static void add4StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "스피드 효과", effect);
		PlayerYml.setInfoInt(p, "스피드 지속시간", time);
		if (PlayerYml.getInfoBoolean(p, "스피드 스텟 적용")) {
			float y = (float) (PlayerYml.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
			if (y <= 1F) {
				if (y > 0.2F) {
					p.setWalkSpeed(y);
				} else {
					p.setWalkSpeed(0.2F);
				}
			} else {
				p.setWalkSpeed(1F);
			}
		} else {
			p.setWalkSpeed(0.2F);
		}
	}
	
	@SuppressWarnings("static-access")
	public static int getLoon(Player p) {
		if (P.getInfoInt(p, "룬.아이템 코드") != 0 && P.getInfoString(p, "룬.아이템 이름") != null && !P.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
			String[] str = ChatColor.stripColor(P.getInfoString(p, "룬.아이템 이름")).split(" ");
			if (str[1].equalsIgnoreCase("히든")) {
				if (str.length == 4) {
					return 5;
				}
				
				else if (str.length == 5) {
					int i = Integer.parseInt(ChatColor.stripColor(str[4])) + 1;
					return 5 * i;
				}
			}
		}
		
		return 0;
	}
	
	public static int get1StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "팔 근력 효과");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get2StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "복근 효과");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get3StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "다리 근력 효과");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get4StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "스피드 효과");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get1Stat(Player p) {
		int i = 0;
		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getHelmet().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("팔 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("팔 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("팔 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("팔 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("팔 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(handContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		return i;
	}

	public static int get2Stat(Player p) {
		int i = 0;
		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getHelmet().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("복근 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("복근 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("복근 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("복근 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("복근 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(handContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		return i;
	}
	
	public static int get3Stat(Player p) {
		int i = 0;
		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getHelmet().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("다리 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("다리 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("다리 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("다리 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("다리 근력 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(handContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		return i;
	}
	
	public static int get4Stat(Player p) {
		int i = 0;
		if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getHelmet().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("스피드 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("스피드 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("스피드 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("스피드 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("스피드 스텟")) {
					i += Integer.parseInt(ChatColor.stripColor(handContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		return i;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "스텟 어드민 " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "페이지 " + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/스텟a 설정 <1|2|3|4> <닉네임> <값> " + ChatColor.WHITE + "- 플레이어의 스텟 값을 설정합니다.");
		sender.sendMessage(ChatColor.GOLD + "/스텟a 추가 <1|2|3|4> <닉네임> <값> " + ChatColor.WHITE + "- 플레이어의 스텟 값을 추가합니다.");
		sender.sendMessage(ChatColor.GOLD + "/스텟a 빼기 <1|2|3|4> <닉네임> <값> " + ChatColor.WHITE + "- 플레이어의 스텟 값을 뺍니다.");
		sender.sendMessage(ChatColor.GOLD + "/스텟a 보기 <닉네임>" + ChatColor.WHITE + "- 플레이어의 스텟 값을 봅니다.");
		sender.sendMessage(ChatColor.GOLD + "/스텟a 활성화 <1|2|3|4> <닉네임>" + ChatColor.WHITE + "- 플레이어의 스텟을 활성화/비활성화 시킵니다.");
		sender.sendMessage(ChatColor.GOLD + "");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "1 " + ChatColor.WHITE + "- 팔 근력");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "2 " + ChatColor.WHITE + "- 복근");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "3 " + ChatColor.WHITE + "- 다리 근력");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "4 " + ChatColor.WHITE + "- 스피드");
	}
	
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
	
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
}
