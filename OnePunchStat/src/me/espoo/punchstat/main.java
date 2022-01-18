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
		getCommand("스텟").setExecutor(new mainCommand(this));
		getCommand("스텟a").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		unRegisterLoreAttributes();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					JumpTimer(p);
					
					if (PlayerYml.getInfoInt(p, "팔 근력 지속시간") != 0) {
						int i = PlayerYml.getInfoInt(p, "팔 근력 지속시간") - 5;
						if (i <= 0) {
							p.sendMessage("§6팔 근력 스텟 부가 효과 지속시간이 §c만료§6되었습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "팔 근력 지속시간", 0);
							PlayerYml.setInfoInt(p, "팔 근력 효과", 0);
						} else {
							PlayerYml.setInfoInt(p, "팔 근력 지속시간", i);
						}
					}
					
					if (PlayerYml.getInfoInt(p, "복근 지속시간") != 0) {
						int i = PlayerYml.getInfoInt(p, "복근 지속시간") - 5;
						if (i <= 0) {
							p.sendMessage("§6복근 스텟 부가 효과 지속시간이 §c만료§6되었습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "복근 지속시간", 0);
							PlayerYml.setInfoInt(p, "복근 효과", 0);
							if (PlayerYml.getInfoBoolean(p, "복근 스텟 적용")) {
								StatsRunAPI.PlayerHealth(p);
							} else {
								StatsRunAPI.PlayerHHealth(p);
							}
						} else {
							PlayerYml.setInfoInt(p, "복근 지속시간", i);
						}
					}
					
					if (PlayerYml.getInfoInt(p, "다리 근력 지속시간") != 0) {
						int i = PlayerYml.getInfoInt(p, "다리 근력 지속시간") - 5;
						if (i <= 0) {
							p.sendMessage("§6다리 근력 스텟 부가 효과 지속시간이 §c만료§6되었습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "다리 근력 지속시간", 0);
							PlayerYml.setInfoInt(p, "다리 근력 효과", 0);
							if (P.getInfoBoolean(p, "다리 근력 스텟 적용")) {
								int y = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
								if (y > 0) {
									p.removePotionEffect(PotionEffectType.JUMP);
									p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, y - 1, true));
								}
							}
						} else {
							PlayerYml.setInfoInt(p, "다리 근력 지속시간", i);
						}
					}
					
					if (PlayerYml.getInfoInt(p, "스피드 지속시간") != 0) {
						int i = PlayerYml.getInfoInt(p, "스피드 지속시간") - 5;
						if (i <= 0) {
							p.sendMessage("§6스피드 스텟 부가 효과 지속시간이 §c만료§6되었습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 2.0F, 1.5F);
							PlayerYml.setInfoInt(p, "스피드 지속시간", 0);
							PlayerYml.setInfoInt(p, "스피드 효과", 0);
							if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
								float y = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
							PlayerYml.setInfoInt(p, "스피드 지속시간", i);
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	@SuppressWarnings("static-access")
	public static void JumpTimer(Player p)
	{
		if (P.getInfoBoolean(p, "다리 근력 스텟 적용")) {
			int i = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
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
