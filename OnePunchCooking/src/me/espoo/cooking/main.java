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
					if (PlayerYml.getInfoInt(p, "���� �ð�") >= 1) {
						int i = PlayerYml.getInfoInt(p, "���� �ð�") - 1;
						PlayerYml.setInfoInt(p, "���� �ð�", i);
						if (i == 0) {
							p.sendMessage("��c�丮��6�� ��� ����������ϴ�. ��e[ /�丮 ] ��6��ɾ�� ã�ư��ּ���!");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						}
						
						if (p.getOpenInventory() != null) {
							if (p.getOpenInventory().getTitle().equalsIgnoreCase("�丮 ���մ�")) {
								GUI.openGUI(p);
							}
						}
					}
				}
			}
		}, 1200L, 1200L);
		
		getCommand("�丮").setExecutor(new mainCommand(this));
		getCommand("�丮ȿ��").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
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
}
