package me.espoo.loon;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class main extends JavaPlugin implements Listener {
    static final Map <String, Integer> Timer = new HashMap<>();
	public static HashMap<String, Integer> ScoreTime = new HashMap<String, Integer>();
	public static HashMap<String, String> Name = new HashMap<String, String>();
	public static HashMap<Player, String> Loon = new HashMap<Player, String>();
	public static HashMap<Player, Integer> level = new HashMap<Player, Integer>();
	
	public void onEnable()
	{
		GUIMessage.setEander();
		GUIMessage.setTip();
		GUIMessage.setLoon();
		getCommand("룬").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 LoonTimer(p);
				 }
			 }
		 }, 60L, 60L);
		
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
	
	public static void LoonTimer(Player p)
	{
		if (main.Loon.containsKey(p)) {
			String str = main.Loon.get(p);
			if (str.equalsIgnoreCase("치유")) {
				if (p.getWorld().getName().equalsIgnoreCase("world")) return;
				switch (main.level.get(p)) {
					case 0:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 0, false));
						break;

					case 1:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 0, false));
						break;
						
					case 2:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 0, false));
						break;
						
					case 3:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 1, false));
						break;
						
					case 4:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 1, false));
						break;
						
					case 5:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 1, false));
						break;
						
					case 6:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 2, false));
						break;
						
					case 7:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 2, false));
						break;
						
					case 8:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 3, false));
						break;
						
					case 9:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 3, false));
						break;
						
					case 10:
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 4, false));
						break;
				}
			}
			
			else if (str.equalsIgnoreCase("바람")) {
				if (p.getWorld().getName().equalsIgnoreCase("world")) return;
				switch (main.level.get(p)) {
					case 0:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 0, false));
						break;
					
					case 1:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 0, false));
						break;
					
					case 2:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 0, false));
						break;
					
					case 3:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 1, false));
						break;
					
					case 4:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 1, false));
						break;
					
					case 5:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 1, false));
						break;
					
					case 6:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 2, false));
						break;
					
					case 7:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 2, false));
						break;
					
					case 8:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 3, false));
						break;
					
					case 9:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 3, false));
						break;
					
					case 10:
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90, 4, false));
						break;
				}
			}
		}
	}
}