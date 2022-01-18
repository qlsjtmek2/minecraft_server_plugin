package me.espoo.loon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public static Timer effectTimer = new Timer();
    static final Map <String, Integer> Timer = new HashMap<>();
	public static HashMap<String, Integer> ScoreTime = new HashMap<String, Integer>();
	public static HashMap<String, String> Name = new HashMap<String, String>();
	
	public void onEnable()
	{
		effectTimer.schedule(new Runa(this), 50L, 50L);
		GUIMessage.setEander();
		GUIMessage.setTip();
		GUIMessage.setLoon();
		getCommand("룬").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		effectTimer.cancel();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
}

class Runa extends TimerTask {
	main main;
	PlayerYml P;
	
	public Runa(main main) {
		this.main = main;
	}
	
	@SuppressWarnings("static-access")
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (P.getInfoInt(p, "룬.아이템 코드") != 0 && P.getInfoString(p, "룬.아이템 이름") != null && !P.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(P.getInfoString(p, "룬.아이템 이름")).split(" ");
				
				if (str[1].equals("불")) {
					try {
						Particle.sendToLocation(Particle.FLAME, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 0.01F, 2, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("바람")) {
					try {
						Particle.sendToLocation(Particle.INSTANT_SPELL, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 0.01F, 3, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("치유")) {
					try {
						Particle.sendToLocation(Particle.HEART, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("어둠")) {
					try {
						Particle.sendToLocation(Particle.LARGE_SMOKE, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 0.01F, 1, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("독")) {
					try {
						Particle.sendToLocation(Particle.HAPPY_VILLAGER, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 0.02F, 3, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("부패")) {
					try {
						Particle.sendToLocation(Particle.MOB_SPELL_AMBIENT, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 2.0F, 4, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("얼음")) {
					try {
						Particle.sendToLocation(Particle.MAGIC_CRIT, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 0.02F, 3, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
				
				else if (str[1].equals("히든")) {
					try {
						Particle.sendToLocation(Particle.ENCHANTMENT_TABLE, p.getLocation().add(0D, 3D, 0D), 0.0F, 0.0F, 0.0F, 0.02F, 5, main.getServer().getVersion());
					} catch (Exception e1) {}
					continue;
				}
			}
		}
	}
}