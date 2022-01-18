package me.shinkhan.PvPTarget;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	Method D;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("§6이 서버에는 당신의 플러그인인 §cDHPvPTarget §6가 들어있습니다!");
					return;
				}
			}, timeToRun);
		}
		
		if (D.isQuitList(n)) {
			D.subQuitList(n);
			
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					String message = D.replaceAllColors(D.getConfigString("입장 메세지"));
					p.sendMessage(message);
					return;
				}
			}, timeToRun);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		if (M.Name.containsKey(p.getName())) {
	        final ItemStack[] stacks = (ItemStack[]) p.getInventory().getContents().clone();
	        final ItemStack[] armorstacks = (ItemStack[]) p.getInventory().getArmorContents().clone();
	        
	        p.getInventory().clear();
	        p.getInventory().setHelmet(new ItemStack(0));
	        p.getInventory().setChestplate(new ItemStack(0));
	        p.getInventory().setLeggings(new ItemStack(0));
	        p.getInventory().setBoots(new ItemStack(0));
	        p.setLevel(0);
	        p.setExp(0.0F);
	        
	        for (ItemStack stack : stacks) {
	        	if ((stack == null) || (stack.getType() == Material.AIR))
	        		continue;
	        	p.getLocation().getWorld().dropItemNaturally(p.getLocation(), stack);
	        }
	        
	        for (ItemStack stack : armorstacks) {
	        	if ((stack == null) || (stack.getType() == Material.AIR))
	        		continue;
	        	p.getLocation().getWorld().dropItemNaturally(p.getLocation(), stack);
	        }
	        
	        for (int i = 0; i < p.getLevel(); i++) {
	          ((ExperienceOrb) p.getLocation().getWorld().spawn(p.getLocation(), ExperienceOrb.class)).setExperience(2 * i + 1);
	        }
	        
			if (D.getConfigBoolean("전투 퇴장 방송 여부")) {
				String message = D.replaceAllColors(D.getConfigString("방송 메세지"));
				message = message.replaceAll("<playername>", n);
				Bukkit.broadcastMessage(message);
				D.addQuitList(n);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			final Player ep = (Player) e.getEntity();
			final Entity en = e.getDamager();
			
			if (en instanceof Player) {
				final Player dp = (Player) en;
		        
				if (!(D.getWorldList().contains(ep.getWorld().getName()))) {
					final long s = D.getConfigInt("전투 시간 (초)") * 20L;
					if (!M.Name.containsKey(dp.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							dp.sendMessage(message);
						}
					}
					
					if (!M.Name.containsKey(ep.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							ep.sendMessage(message);
						}
					}
							

					Integer id = main.Timer.remove(dp.getName());
					Integer ie = main.Timer.remove(ep.getName());
					if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
					if (!M.Name.containsKey(dp.getName())) M.Name.put(dp.getName(), "true");
					if (!M.Name.containsKey(ep.getName())) M.Name.put(ep.getName(), "true");
					
					M.Timer.put(dp.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer id = main.Timer.remove(dp.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							else return;
							if (M.Name.containsKey(dp.getName())) M.Name.remove(dp.getName());
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							dp.sendMessage(message);
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
					
					M.Timer.put(ep.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer ie = main.Timer.remove(ep.getName());
							if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							ep.sendMessage(message);
							if (M.Name.containsKey(ep.getName())) M.Name.remove(ep.getName());
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
				}
			}
			
			else if (en instanceof Arrow) {
				final Player dp = (Player) ((Arrow)en).getShooter();
		        
				if (!(D.getWorldList().contains(ep.getWorld().getName()))) {
					final long s = D.getConfigInt("전투 시간 (초)") * 20L;
					if (!M.Name.containsKey(dp.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							dp.sendMessage(message);
						}
					}
					
					if (!M.Name.containsKey(ep.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							ep.sendMessage(message);
						}
					}
							

					Integer id = main.Timer.remove(dp.getName());
					Integer ie = main.Timer.remove(ep.getName());
					if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
					if (!M.Name.containsKey(dp.getName())) M.Name.put(dp.getName(), "true");
					if (!M.Name.containsKey(ep.getName())) M.Name.put(ep.getName(), "true");
					
					M.Timer.put(dp.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer id = main.Timer.remove(dp.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							else return;
							if (M.Name.containsKey(dp.getName())) M.Name.remove(dp.getName());
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							dp.sendMessage(message);
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
					
					M.Timer.put(ep.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer ie = main.Timer.remove(ep.getName());
							if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							ep.sendMessage(message);
							if (M.Name.containsKey(ep.getName())) M.Name.remove(ep.getName());
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
				}
			}
			
			else if (en instanceof Snowball) {
				final Player dp = (Player) ((Snowball)en).getShooter();
		        
				if (!(D.getWorldList().contains(ep.getWorld().getName()))) {
					final long s = D.getConfigInt("전투 시간 (초)") * 20L;
					if (!M.Name.containsKey(dp.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							dp.sendMessage(message);
						}
					}
					
					if (!M.Name.containsKey(ep.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							ep.sendMessage(message);
						}
					}
							

					Integer id = main.Timer.remove(dp.getName());
					Integer ie = main.Timer.remove(ep.getName());
					if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
					if (!M.Name.containsKey(dp.getName())) M.Name.put(dp.getName(), "true");
					if (!M.Name.containsKey(ep.getName())) M.Name.put(ep.getName(), "true");
					
					M.Timer.put(dp.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer id = main.Timer.remove(dp.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							else return;
							if (M.Name.containsKey(dp.getName())) M.Name.remove(dp.getName());
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							dp.sendMessage(message);
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
					
					M.Timer.put(ep.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer ie = main.Timer.remove(ep.getName());
							if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							ep.sendMessage(message);
							if (M.Name.containsKey(ep.getName())) M.Name.remove(ep.getName());
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
				}
			}
			
			else if (en instanceof Egg) {
				final Player dp = (Player) ((Egg)en).getShooter();
		        
				if (!(D.getWorldList().contains(ep.getWorld().getName()))) {
					final long s = D.getConfigInt("전투 시간 (초)") * 20L;
					if (!M.Name.containsKey(dp.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							dp.sendMessage(message);
						}
					}
					
					if (!M.Name.containsKey(ep.getName())) {
						if (D.getConfigBoolean("전투 메세지 여부")) {
							String message = D.replaceAllColors(D.getConfigString("전투 시작 메세지"));
							ep.sendMessage(message);
						}
					}
							

					Integer id = main.Timer.remove(dp.getName());
					Integer ie = main.Timer.remove(ep.getName());
					if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
					if (!M.Name.containsKey(dp.getName())) M.Name.put(dp.getName(), "true");
					if (!M.Name.containsKey(ep.getName())) M.Name.put(ep.getName(), "true");
					
					M.Timer.put(dp.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer id = main.Timer.remove(dp.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							else return;
							if (M.Name.containsKey(dp.getName())) M.Name.remove(dp.getName());
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							dp.sendMessage(message);
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
					
					M.Timer.put(ep.getName(), new BukkitRunnable()
					{
						public void run()
						{
							Integer ie = main.Timer.remove(ep.getName());
							if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
							String message = D.replaceAllColors(D.getConfigString("전투 종료 메세지"));
							ep.sendMessage(message);
							if (M.Name.containsKey(ep.getName())) M.Name.remove(ep.getName());
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHPvPTarget"), s, s).getTaskId());
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();
		String[] a = e.getMessage().split(" ");
		if (D.getConfigBoolean("명령어 사용 금지")) {
			if (M.Name.containsKey(p.getName())) {
				if (!(D.getWorldList().contains(p.getWorld().getName()))) {
					if (!e.getPlayer().isOp()) {
						if (!(D.getCommandList().contains(a[0]))) {
							String message = D.replaceAllColors(D.getConfigString("명령어 메세지"));
							p.sendMessage(message);
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
