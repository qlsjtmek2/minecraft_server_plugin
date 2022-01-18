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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
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
			if (commandLabel.equalsIgnoreCase("��ȭ")) {
				if (args.length == 0) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.DARK_RED + "�ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
						return false;
					}

					Player p = (Player) sender;
					ItemStack i = p.getItemInHand();
					
					if (p.getItemInHand() == null) {
						p.sendMessage("��c�տ� �������� ��� ��ɾ �Է��� �ֽñ� �ٶ��ϴ�.");
						return false;
					}

					if (Method.getIntYml(p, "��ȭ ��ȸ") <= 0) {
						p.sendMessage("��c��ȭ ��ȸ�� ��� ��ȭ�� �Ͻ� �� �����ϴ�.");
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
			    				if (str.contains("���ݷ�")) {
			    					list.remove(str);
			    					break;
			    				} else { continue; }
			    			}
			    			
			    			switch (RandomAmount) {
		    				case 1:
		    					list.add("��f+0 ���ݷ�");
		    					m.setLore(list);
		    					i.setItemMeta(m);
		    					p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
		    					break;
		    				case 2:
		    					list.add("��f+0 ���ݷ�");
		    					m.setLore(list);
		    					i.setItemMeta(m);
		    					p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
		    					break;
							case 3:
								list.add("��f+0 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
								break;
							case 4:
								list.add("��f+0 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
								break;
							case 5:
								list.add("��f+1 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 6:
								list.add("��f+1 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 7:
								list.add("��f+2 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 8:
								list.add("��f+2 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 9:
								list.add("��b+3 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c3 ��6�߰��Ǿ����ϴ�.");
								break;
							case 10:
								list.add("��c+4 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c4 ��6�߰��Ǿ����ϴ�.");
								break;
			    			}
		    			} else {
		    				switch (RandomAmount) {
							case 1:
								list.add("��f+0 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
								break;
							case 2:
								list.add("��f+0 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
								break;
							case 3:
								list.add("��f+0 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
								break;
							case 4:
								list.add("��f+0 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c0 ��6�߰��Ǿ����ϴ�.");
								break;
							case 5:
								list.add("��f+1 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 6:
								list.add("��f+1 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 7:
								list.add("��f+2 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 8:
								list.add("��f+2 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 9:
								list.add("��b+3 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c3 ��6�߰��Ǿ����ϴ�.");
								break;
							case 10:
								list.add("��c+4 ���ݷ�");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���⿡ ��c��ȭ��6�� �Ͽ� ���ݷ��� ��c4 ��6�߰��Ǿ����ϴ�.");
								break;
		    				}
		    			}
						
						int n = Method.getIntYml(p, "��ȭ ��ȸ") - 1;
						Method.setIntYml(p, "��ȭ ��ȸ", n);
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
			    				if (str.contains("�����")) {
			    					list.remove(str);
			    					break;
			    				} else { continue; }
			    			}
			    			
			    			switch (RandomAmount) {
							case 1:
								list.add("��f+1 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 2:
								list.add("��f+1 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 3:
								list.add("��f+1 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 4:
								list.add("��f+2 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 5:
								list.add("��f+2 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 6:
								list.add("��f+2 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 7:
								list.add("��f+3 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c3 ��6�߰��Ǿ����ϴ�.");
								break;
							case 8:
								list.add("��f+3 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c3 ��6�߰��Ǿ����ϴ�.");
								break;
							case 9:
								list.add("��b+4 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c4 ��6�߰��Ǿ����ϴ�.");
								break;
							case 10:
								list.add("��c+5 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c5 ��6�߰��Ǿ����ϴ�.");
								break;
			    			}
		    			} else {
		    				switch (RandomAmount) {
							case 1:
								list.add("��f+1 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 2:
								list.add("��f+1 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 3:
								list.add("��f+1 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c1 ��6�߰��Ǿ����ϴ�.");
								break;
							case 4:
								list.add("��f+2 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 5:
								list.add("��f+2 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 6:
								list.add("��f+2 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c2 ��6�߰��Ǿ����ϴ�.");
								break;
							case 7:
								list.add("��f+3 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c3 ��6�߰��Ǿ����ϴ�.");
								break;
							case 8:
								list.add("��f+3 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c3 ��6�߰��Ǿ����ϴ�.");
								break;
							case 9:
								list.add("��b+4 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c4 ��6�߰��Ǿ����ϴ�.");
								break;
							case 10:
								list.add("��c+5 �����");
								m.setLore(list);
								i.setItemMeta(m);
								p.sendMessage("��6����� ���� ��c��ȭ��6�� �Ͽ� ������� ��c5 ��6�߰��Ǿ����ϴ�.");
								break;
			    			}
		    			}
						
						int n = Method.getIntYml(p, "��ȭ ��ȸ") - 1;
						Method.setIntYml(p, "��ȭ ��ȸ", n);
						castLvup(p);
					} else {
						p.sendMessage("��c���⳪ ���� ��ȭ�� �����մϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����")) {
					Help(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.DARK_RED + "�ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
						return false;
					}

					Player p = (Player) sender;
					int i = Method.getIntYml(p, "��ȭ ��ȸ");
					
					p.sendMessage("��6����� ��ȭ ��ȸ�� ��c" + i + " ��6�� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			Help(sender);
		}
		
		if (commandLabel.equalsIgnoreCase("��ȭ�߰�")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "�ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
			
			Player p = (Player) sender;
			
			if (!p.isOp()) {
				return false;
			}
			
			p.sendMessage("��6��ȭ ��ȸ�� ��c1 ��6�߰��Ǿ����ϴ�.");
			int n = Method.getIntYml(p, "��ȭ ��ȸ") + 1;
			Method.setIntYml(p, "��ȭ ��ȸ", n);
		} return false;
	}
	
	public static void Help(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "��ȭ " + ChatColor.YELLOW + "-- " + ChatColor.GOLD + "������ " 
						 + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1 " + ChatColor.YELLOW + "----");
		sender.sendMessage(ChatColor.GOLD + "/��ȭ " + ChatColor.WHITE + "- �տ� �� �������� ��ȭ�մϴ�.");
		sender.sendMessage(ChatColor.GOLD + "/��ȭ ����" + ChatColor.WHITE + "- �� ��ȭ ��ȸ�� ���ϴ�.");
		sender.sendMessage(ChatColor.GOLD + "/��ȭ ����" + ChatColor.WHITE + "- ��ȭ ������ ���ϴ�.");
	}
	
	public static void castLvup(Player p) {
		Bukkit.getServer().dispatchCommand(p, "cast lvup");
	}
}
