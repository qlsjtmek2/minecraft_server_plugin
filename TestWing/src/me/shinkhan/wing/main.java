package me.shinkhan.wing;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
	public static List<Player> Wing = new ArrayList<Player>();
	
	public void onEnable()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (Wing.contains(p)) {
						//WingAPI.drawWing(p, WingList.OK1, 0.22, 0.42, ParticleEffect.SMOKE_NORMAL);
						//WingAPI.drawWingColor(p, WingList.OK2, 0.22, 0.42, ParticleEffect.REDSTONE, ParticleEffect.SPELL_INSTANT);
						//WingAPI.drawWing(p, WingList.OK3, 0.22, 0.42, ParticleEffect.NOTE);
						//WingAPI.drawWingColor(p, WingList.OK4, 0.22, 0.42, ParticleEffect.SPELL_INSTANT, ParticleEffect.REDSTONE);
						//WingAPI.drawWing(p, WingList.OK5, 0.22, 0.42, ParticleEffect.HEART);
						//WingAPI.drawWingColor(p, WingList.OK6, 0.14, 0.42, ParticleEffect.REDSTONE, ParticleEffect.FLAME);
						List<ParticleEffect> list = new ArrayList<ParticleEffect>();
						list.add(ParticleEffect.SPELL);
						list.add(ParticleEffect.SPELL_INSTANT);
						list.add(ParticleEffect.SPELL_MOB);
						list.add(ParticleEffect.SPELL_MOB_AMBIENT);
						list.add(ParticleEffect.SPELL_WITCH);
						WingAPI.drawWingColor(p, WingAPI.exp3, 0.22, 0.42, list);
					}
				}
			}
		}, 1L, 1L);
		
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("wing")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (Wing.contains(p)) {
						Wing.remove(p);
					} else {
						Wing.add(p);
					} return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			return false;
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("particle") || commandLabel.equalsIgnoreCase("p")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						p.sendMessage(ChatColor.GREEN + "��: /p circle [��ƼŬ�̸�] [����]");
						p.sendMessage(ChatColor.GREEN + "��: /p sphere [��ƼŬ�̸�] [��ƼŬ����] [����]");
						p.sendMessage(ChatColor.GREEN + "����: /p helix [��ƼŬ�̸�] [ũ��]");
						return false;
					}
					
					ParticleEffect effect = ParticleEffect.fromName(args[1]);
					String a = args[0];
					if (a.equalsIgnoreCase("circle")) {
						Circle.a(p, effect, Double.valueOf(args[2]));
					} else if (a.equalsIgnoreCase("helix")) {
						Helix.a(p, effect, Double.valueOf(args[2]));
					} else if (a.equalsIgnoreCase("sphere")) {
						Sphere.a(p, effect, Double.valueOf(args[2]),
								Double.valueOf(args[3]));
					}
					
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			return false;
		} return false;
	}
}
