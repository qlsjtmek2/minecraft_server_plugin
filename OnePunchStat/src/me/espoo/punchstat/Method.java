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
        message = message.replaceAll("&1", "��1");
        message = message.replaceAll("&2", "��2");
        message = message.replaceAll("&3", "��3");
        message = message.replaceAll("&4", "��4");
        message = message.replaceAll("&5", "��5");
        message = message.replaceAll("&6", "��6");
        message = message.replaceAll("&7", "��7");
        message = message.replaceAll("&8", "��8");
        message = message.replaceAll("&9", "��9");
        message = message.replaceAll("&0", "��0");
        message = message.replaceAll("&a", "��a");
        message = message.replaceAll("&b", "��b");
        message = message.replaceAll("&c", "��c");
        message = message.replaceAll("&d", "��d");
        message = message.replaceAll("&e", "��e");
        message = message.replaceAll("&f", "��f");
        message = message.replaceAll("&k", "��k");
        message = message.replaceAll("&l", "��l");
        message = message.replaceAll("&m", "��m");
        message = message.replaceAll("&n", "��n");
        message = message.replaceAll("&o", "��o");
        message = message.replaceAll("&r", "��r");
        return message;
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
	
	public static void add1StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "�� �ٷ� ȿ��", effect);
		PlayerYml.setInfoInt(p, "�� �ٷ� ���ӽð�", time);
	}
	
	public static void add2StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "���� ȿ��", effect);
		PlayerYml.setInfoInt(p, "���� ���ӽð�", time);
		if (PlayerYml.getInfoBoolean(p, "���� ���� ����")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
	}
	
	public static void add3StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "�ٸ� �ٷ� ȿ��", effect);
		PlayerYml.setInfoInt(p, "�ٸ� �ٷ� ���ӽð�", time);
		if (PlayerYml.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
			int y = (PlayerYml.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
			if (y > 0) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, y - 1, true));
			}
		}
	}
	
	public static void add4StatEffect(Player p, int effect, int time) {
		PlayerYml.setInfoInt(p, "���ǵ� ȿ��", effect);
		PlayerYml.setInfoInt(p, "���ǵ� ���ӽð�", time);
		if (PlayerYml.getInfoBoolean(p, "���ǵ� ���� ����")) {
			float y = (float) (PlayerYml.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		if (P.getInfoInt(p, "��.������ �ڵ�") != 0 && P.getInfoString(p, "��.������ �̸�") != null && !P.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
			String[] str = ChatColor.stripColor(P.getInfoString(p, "��.������ �̸�")).split(" ");
			if (str[1].equalsIgnoreCase("����")) {
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
		int i = PlayerYml.getInfoInt(p, "�� �ٷ� ȿ��");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get2StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "���� ȿ��");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get3StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "�ٸ� �ٷ� ȿ��");
		if (i != 0) {
			return i;
		} else {
			return 0;
		}
	}
	
	public static int get4StatEffect(Player p) {
		int i = PlayerYml.getInfoInt(p, "���ǵ� ȿ��");
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

				if (armorContent.contains("�� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("�� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("�� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("�� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("�� �ٷ� ����")) {
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

				if (armorContent.contains("���� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("���� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("���� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("���� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("���� ����")) {
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

				if (armorContent.contains("�ٸ� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("�ٸ� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("�ٸ� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("�ٸ� �ٷ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("�ٸ� �ٷ� ����")) {
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

				if (armorContent.contains("���ǵ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getChestplate().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("���ǵ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getLeggings().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("���ǵ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getInventory().getBoots().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String armorContent = (String)localIterator.next();

				if (armorContent.contains("���ǵ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(armorContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		if (p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
			Iterator<String> localIterator = p.getItemInHand().getItemMeta().getLore().iterator();
			
			while (localIterator.hasNext()) {
				String handContent = (String)localIterator.next();

				if (handContent.contains("���ǵ� ����")) {
					i += Integer.parseInt(ChatColor.stripColor(handContent.split(" ")[0].replace("+", "")));
				}
			}
		}
		
		return i;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "���� ���� " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "������ " + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/����a ���� <1|2|3|4> <�г���> <��> " + ChatColor.WHITE + "- �÷��̾��� ���� ���� �����մϴ�.");
		sender.sendMessage(ChatColor.GOLD + "/����a �߰� <1|2|3|4> <�г���> <��> " + ChatColor.WHITE + "- �÷��̾��� ���� ���� �߰��մϴ�.");
		sender.sendMessage(ChatColor.GOLD + "/����a ���� <1|2|3|4> <�г���> <��> " + ChatColor.WHITE + "- �÷��̾��� ���� ���� ���ϴ�.");
		sender.sendMessage(ChatColor.GOLD + "/����a ���� <�г���>" + ChatColor.WHITE + "- �÷��̾��� ���� ���� ���ϴ�.");
		sender.sendMessage(ChatColor.GOLD + "/����a Ȱ��ȭ <1|2|3|4> <�г���>" + ChatColor.WHITE + "- �÷��̾��� ������ Ȱ��ȭ/��Ȱ��ȭ ��ŵ�ϴ�.");
		sender.sendMessage(ChatColor.GOLD + "");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "1 " + ChatColor.WHITE + "- �� �ٷ�");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "2 " + ChatColor.WHITE + "- ����");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "3 " + ChatColor.WHITE + "- �ٸ� �ٷ�");
		sender.sendMessage(ChatColor.WHITE + "* " + ChatColor.GOLD + "4 " + ChatColor.WHITE + "- ���ǵ�");
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
