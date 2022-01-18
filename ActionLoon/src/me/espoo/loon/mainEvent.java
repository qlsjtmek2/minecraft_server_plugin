package me.espoo.loon;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.nisovin.magicspells.spells.targeted.PainSpell;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	GUIMessage G;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/ActionLoon/Player/" + p.getName() + ".yml");
		if (!f.exists()) {
			File folder = new File("plugins/ActionLoon");
			File folder2 = new File("plugins/ActionLoon/Player");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			P.CreatePlayerInfo(f, folder, folder2, config);
		}
		
		String name = P.getInfoString(p, "룬.아이템 이름");
		if (name == null) {
			return;
		}
		
		String[] str = ChatColor.stripColor(name).split(" ");
		if (main.Loon.containsKey(p)) main.Loon.remove(p);
		if (main.level.containsKey(p)) main.level.remove(p);
		main.Loon.put(p, str[1]);
		
		if (str.length == 5) {
			main.level.put(p, Integer.parseInt(str[4].replace("+", "")));
		} else {
			main.level.put(p, 0);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (main.Loon.containsKey(p)) main.Loon.remove(p);
		if (main.level.containsKey(p)) main.level.remove(p);
	}

	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("룬 장착")) return;
     	if (e.getCurrentItem() == null) return;
     	
     	ItemStack i = e.getCurrentItem();
     	
     	if (e.getRawSlot() == 13 && e.getCursor() != null && e.getCursor().getAmount() > 1) {
 			Player p = (Player) e.getWhoClicked();
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
 			p.sendMessage("§c룬이 겹쳐진 상태이면 올리실 수 없습니다.");
 			e.setCancelled(true);
 			return;
     	}
     	
     	if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && e.getRawSlot() > 26 && i.getAmount() > 1) {
 			Player p = (Player) e.getWhoClicked();
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
 			p.sendMessage("§c룬이 겹쳐진 상태이면 올리실 수 없습니다.");
 			e.setCancelled(true);
 			return;
     	}
     	
		if (e.getCurrentItem().hasItemMeta()) {
			ItemMeta m = i.getItemMeta();
			if (m.getDisplayName() != null) {
				if (ChatColor.stripColor(m.getDisplayName()).contains("속성 룬")) {
					return;
				} else e.setCancelled(true);
			} else e.setCancelled(true);
		} else {
			if (i.getType() != Material.AIR)
				e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("룬 장착")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(13) != null) {
			ItemStack i = e.getInventory().getItem(13);
			if (P.getInfoString(p, "룬.아이템 이름") != null && P.getInfoString(p, "룬.아이템 이름").equalsIgnoreCase(i.getItemMeta().getDisplayName())) {
				return;
			}
			
			if (!i.getItemMeta().getDisplayName().contains("룬")) {
				p.getWorld().dropItemNaturally(p.getLocation(), i);
				return;
			}
			
			P.setInfoInt(p, "룬.아이템 코드", i.getTypeId());
			P.setInfoString(p, "룬.아이템 이름", i.getItemMeta().getDisplayName());
			P.setInfoList(p, "룬.아이템 설명", i.getItemMeta().getLore());
			p.sendMessage("§a성공적으로 룬을 장착하였습니다!");
			
			String[] str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).split(" ");
			if (main.Loon.containsKey(p)) main.Loon.remove(p);
			
			main.Loon.put(p, str[1]);
			
			if (str.length == 5) {
				main.level.put(p, Integer.parseInt(str[4].replace("+", "")));
			} else {
				main.level.put(p, 0);
			}
			
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
		} else {
			if (P.getInfoInt(p, "룬.아이템 코드") == 0 && P.getInfoString(p, "룬.아이템 이름") == null && P.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) return;
			p.sendMessage("§c성공적으로 룬 장착을 해제하셨습니다.");
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			P.setInfoInt(p, "룬.아이템 코드", 0);
			P.setInfoString(p, "룬.아이템 이름", "NONE");
			P.setInfoList(p, "룬.아이템 설명", Arrays.asList());
			if (main.Loon.containsKey(p)) main.Loon.remove(p);
			if (main.level.containsKey(p)) main.level.remove(p);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player p = (Player) e.getDamager();
			LivingEntity en = (LivingEntity) e.getEntity();
			
			if (main.Loon.containsKey(p)) {
				if (PainSpell.playerCheckPluginsplayer.contains(p)) {
					PainSpell.playerCheckPluginsplayer.remove(p);
					return;
				}
				
				String str = main.Loon.get(p);
				if (str.equalsIgnoreCase("부패")) {
					switch (main.level.get(p)) {
						case 0:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true));
							break;
						
						case 1:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 3, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 400, 4, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 6, true));
							break;
					}
				}
				
				else if (str.equalsIgnoreCase("독")) {
					switch (main.level.get(p)) {
						case 0:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 0, true));
							break;

						case 1:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 3, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 400, 4, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 6, true));
							break;
					}
				}
				
				else if (str.equalsIgnoreCase("불")) {
					switch (main.level.get(p)) {
						case 0:
							en.setFireTicks(20);
							break;
							
						case 1:
							en.setFireTicks(20);
							break;
							
						case 2:
							en.setFireTicks(40);
							break;
							
						case 3:
							en.setFireTicks(60);
							break;
							
						case 4:
							en.setFireTicks(80);
							break;
							
						case 5:
							en.setFireTicks(100);
							Integer id1 = main.Timer.remove(p.getName());
							if (id1 != null) Bukkit.getServer().getScheduler().cancelTask(id1);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										if (en != null) en.damage(2);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionLoon"), 20L, 20L).getTaskId());
							break;
							
						case 6:
							en.setFireTicks(140);
							Integer id = main.Timer.remove(p.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										if (en != null) en.damage(4);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionLoon"), 20L, 20L).getTaskId());
							break;
							
						case 7:
							en.setFireTicks(200);
							Integer id2 = main.Timer.remove(p.getName());
							if (id2 != null) Bukkit.getServer().getScheduler().cancelTask(id2);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										if (en != null) en.damage(6);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionLoon"), 20L, 20L).getTaskId());
							break;
							
						case 8:
							en.setFireTicks(300);
							Integer id3 = main.Timer.remove(p.getName());
							if (id3 != null) Bukkit.getServer().getScheduler().cancelTask(id3);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										if (en != null) en.damage(10);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionLoon"), 20L, 20L).getTaskId());
							break;
							
						case 9:
							en.setFireTicks(600);
							Integer id4 = main.Timer.remove(p.getName());
							if (id4 != null) Bukkit.getServer().getScheduler().cancelTask(id4);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										if (en != null) en.damage(20);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionLoon"), 20L, 20L).getTaskId());
							break;
							
						case 10:
							en.setFireTicks(900);
							Integer id5 = main.Timer.remove(p.getName());
							if (id5 != null) Bukkit.getServer().getScheduler().cancelTask(id5);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										if (en != null) en.damage(30);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionLoon"), 20L, 20L).getTaskId());
							break;
					}
				}
				
				else if (str.equalsIgnoreCase("얼음")) {
					switch (main.level.get(p)) {
						case 0:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 0, true));
							break;
					
						case 1:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 3, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 4, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 6, true));
							break;
					}
				}
				
				else if (str.equalsIgnoreCase("어둠")) {
					switch (main.level.get(p)) {
						case 0:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 0, true));
							break;
						
						case 1:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 80, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 140, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 4, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 5, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 7, true));
							break;
					}
				}
			}
		}
	}
}
