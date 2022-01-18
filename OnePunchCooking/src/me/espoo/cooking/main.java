package me.espoo.cooking;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	public void onEnable()
	{
		GUIMessage.setWood();
		GUIMessage.setWeek();
		GUIMessage.setLava();
		GUIMessage.setWeekFir();
		GUIMessage.setWeekMake();
		GUIMessage.setWeekFin();
		FoodList.setFood();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (PlayerYml.getInfoInt(p, "남은 시간") >= 1) {
						int i = PlayerYml.getInfoInt(p, "남은 시간") - 1;
						PlayerYml.setInfoInt(p, "남은 시간", i);
						if (i == 0) {
							p.sendMessage("§c요리§6가 모두 만들어졌습니다. §e[ /요리 ] §6명령어로 찾아가주세요!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						}
						
						if (p.getOpenInventory() != null) {
							if (p.getOpenInventory().getTitle().equalsIgnoreCase("요리 조합대")) {
								GUI.openGUI(p);
							}
						}
					}
				}
			}
		}, 1200L, 1200L);
		
		getCommand("요리").setExecutor(new mainCommand(this));
		getCommand("요리효과").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
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
}
