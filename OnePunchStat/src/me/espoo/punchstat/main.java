package me.espoo.punchstat;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.file.PlayerYml;
import net.milkbowl.vault.permission.Permission;

public class main  extends JavaPlugin implements Listener {
	public static HashMap<String, Integer> S1 = new HashMap<String, Integer>();
	public static HashMap<String, Integer> S2 = new HashMap<String, Integer>();
	public static HashMap<String, Integer> S3 = new HashMap<String, Integer>();
	public static HashMap<String, Integer> S4 = new HashMap<String, Integer>();
	public static Permission permission = null;
	static me.espoo.file.PlayerYml P;
	private static Plugin pl = Bukkit.getPluginManager().getPlugin("LoreAttributes");
	
	public void onEnable()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		permission = (Permission) permissionProvider.getProvider();
		getCommand("����").setExecutor(new mainCommand(this));
		getCommand("����a").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		unRegisterLoreAttributes();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					JumpTimer(p);
					
					if (PlayerYml.getInfoInt(p, "�� �ٷ� ���ӽð�") != 0) {
						int i = PlayerYml.getInfoInt(p, "�� �ٷ� ���ӽð�") - 5;
						if (i <= 0) {
							p.sendMessage("��6�� �ٷ� ���� �ΰ� ȿ�� ���ӽð��� ��c�����6�Ǿ����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "�� �ٷ� ���ӽð�", 0);
							PlayerYml.setInfoInt(p, "�� �ٷ� ȿ��", 0);
						} else {
							PlayerYml.setInfoInt(p, "�� �ٷ� ���ӽð�", i);
						}
					}
					
					if (PlayerYml.getInfoInt(p, "���� ���ӽð�") != 0) {
						int i = PlayerYml.getInfoInt(p, "���� ���ӽð�") - 5;
						if (i <= 0) {
							p.sendMessage("��6���� ���� �ΰ� ȿ�� ���ӽð��� ��c�����6�Ǿ����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "���� ���ӽð�", 0);
							PlayerYml.setInfoInt(p, "���� ȿ��", 0);
							if (PlayerYml.getInfoBoolean(p, "���� ���� ����")) {
								StatsRunAPI.PlayerHealth(p);
							} else {
								StatsRunAPI.PlayerHHealth(p);
							}
						} else {
							PlayerYml.setInfoInt(p, "���� ���ӽð�", i);
						}
					}
					
					if (PlayerYml.getInfoInt(p, "�ٸ� �ٷ� ���ӽð�") != 0) {
						int i = PlayerYml.getInfoInt(p, "�ٸ� �ٷ� ���ӽð�") - 5;
						if (i <= 0) {
							p.sendMessage("��6�ٸ� �ٷ� ���� �ΰ� ȿ�� ���ӽð��� ��c�����6�Ǿ����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "�ٸ� �ٷ� ���ӽð�", 0);
							PlayerYml.setInfoInt(p, "�ٸ� �ٷ� ȿ��", 0);
							if (P.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
								int y = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
								if (y > 0) {
									p.removePotionEffect(PotionEffectType.JUMP);
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, y - 1, true));
								}
							}
						} else {
							PlayerYml.setInfoInt(p, "�ٸ� �ٷ� ���ӽð�", i);
						}
					}
					
					if (PlayerYml.getInfoInt(p, "���ǵ� ���ӽð�") != 0) {
						int i = PlayerYml.getInfoInt(p, "���ǵ� ���ӽð�") - 5;
						if (i <= 0) {
							p.sendMessage("��6���ǵ� ���� �ΰ� ȿ�� ���ӽð��� ��c�����6�Ǿ����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "���ǵ� ���ӽð�", 0);
							PlayerYml.setInfoInt(p, "���ǵ� ȿ��", 0);
							if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
								float y = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
						} else {
							PlayerYml.setInfoInt(p, "���ǵ� ���ӽð�", i);
						}
					}
				}
			}
		}, 100L, 100L);
		
		File folder = new File("plugins/OnePunchStat");
		File GF = new File("plugins/OnePunchStat/GUIMessage.yml");
		YamlConfiguration Gconfig = YamlConfiguration.loadConfiguration(GF);
		if (!GF.exists()) GUIMessage.CreateGUIMessageConfig(GF, folder, Gconfig);
		 
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
	
	@SuppressWarnings("static-access")
	public static void JumpTimer(Player p)
	{
		if (P.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
			int i = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
			if (i > 0) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
			}
		}
	}
	
	public static void unRegisterLoreAttributes() {
		try {
			PlayerJoinEvent.getHandlerList().unregister(pl);
			PlayerRespawnEvent.getHandlerList().unregister(pl);
			EntityTargetEvent.getHandlerList().unregister(pl);
			InventoryCloseEvent.getHandlerList().unregister(pl);
		} catch (Exception localException) {}
	}
}
