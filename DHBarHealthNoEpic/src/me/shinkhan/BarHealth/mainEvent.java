package me.shinkhan.BarHealth;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.material.Mushroom;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.ThaH3lper.com.Api.BossDamageEvent;
import me.ThaH3lper.com.Api.BossDeathEvent;
import me.ThaH3lper.com.Boss.Boss;
import me.confuser.barapi.BarAPI;

public class mainEvent extends JavaPlugin implements Listener {
	static boolean is = false;
	BarAPI B;
	main M;
		
	public mainEvent(main main)
	{
		this.M = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("��6�� �������� ����� �÷������� ��cDHBarHealth ��6�� ����ֽ��ϴ�!");
					return;
				}
			}, timeToRun);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBossDeath(BossDeathEvent e) {
		final Player p = e.getPlayer();
		
		is = true;
		B.setHealth(p, -50F);
		B.removeBar(p);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBossDamage(BossDamageEvent e) {
		final Player p = e.getPlayer();
		Boss b = e.getBoss();
		
		is = true;
		int y = b.getHealth() - e.getDamage();
		if (y < 0) y = 0;
		float i = (float) b.getHealth() - (float) e.getDamage();
		i /= (float) b.getMaxHealth();
		B.setMessage(p, Config.getName(b.getName(), b.getName(), y, b.getMaxHealth()));
		if ((i * 100F) == 0F) B.setHealth(p, -50F);
		else B.setHealth(p, i * 100F);
		Integer id = main.Timer.remove(p.getName());
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		
		if (i <= 0) {
			B.removeBar(p);
		} else {
			M.Timer.put(p.getName(), new BukkitRunnable()
			{
				public void run()
				{
					B.removeBar(p);
					Integer id = main.Timer.remove(p.getName());
					if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
					return;
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHBarHealth"), 80L, 80L).getTaskId());
		} return;
	}

	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		final Entity en = e.getEntity();
		final Entity ed = e.getDamager();
		
		if (en instanceof Player) {
			final Player ep = (Player) en;
			Player dp = null;
			
			if (ed instanceof Player) dp = (Player) ed;
			else if (ed instanceof Arrow) {
				if (!(((Arrow) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Arrow) ed).getShooter();
			}
			
			else if (ed instanceof Snowball) {
				if (!(((Snowball) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Snowball) ed).getShooter();
			}
			
			else if (ed instanceof Egg) {
				if (!(((Egg) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Egg) ed).getShooter();
			}
			
			else return;
			
			int y = ep.getHealth() - e.getDamage();
			if (y < 0) y = 0;
			final Player findp = dp;
			float i = (float) ep.getHealth() - (float) e.getDamage();
			i /= (float) ep.getMaxHealth();
			B.setMessage(dp, Config.getName(ep.getName(), ep.getDisplayName(), y, ep.getMaxHealth()));
			if ((i * 100F) == 0F) B.setHealth(dp, -50F);
			else B.setHealth(dp, i * 100F);
			Integer id = main.Timer.remove(dp.getName());
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			if (i <= 0) {
				B.removeBar(dp);
			} else {
				M.Timer.put(dp.getName(), new BukkitRunnable()
				{
					public void run()
					{
						B.removeBar(findp);
						Integer id = main.Timer.remove(findp.getName());
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						return;
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHBarHealth"), 80L, 80L).getTaskId());
			} return;
		}
		
		else if (en instanceof LivingEntity) {
			if (mainEvent.is) {
				is = false;
				return;
			}
			
			Damageable de = (Damageable) en;
			String n = null;
			
			if (en instanceof Pig) n = "����";
			else if (en instanceof Blaze) n = "������";
			else if (en instanceof Mushroom) n = "���� ��";
			else if (en instanceof CaveSpider) n = "���� �Ź�";
			else if (en instanceof Cow) n = "��";
			else if (en instanceof Sheep) n = "��";
			else if (en instanceof Chicken) n = "��";
			else if (en instanceof Squid) n = "��¡��";
			else if (en instanceof Spider) n = "�Ź�";
			else if (en instanceof PigZombie) n = "���� �Ǳ׸�";
			else if (en instanceof Wolf) n = "����";
			else if (en instanceof Zombie) n = "����";
			else if (en instanceof Skeleton) n = "���̷���";
			else if (en instanceof Creeper) n = "ũ����";
			else if (en instanceof Enderman) n = "������";
			else if (en instanceof Ghast) n = "����Ʈ";
			else if (en instanceof Giant) n = "���̾�Ʈ";
			else if (en instanceof Silverfish) n = "������";
			else if (en instanceof Slime) n = "������";
			else if (en instanceof WitherSkull) n = "���� ���̷���";
			else if (en instanceof Wither) n = "����";
			else if (en instanceof EnderDragon) n = "���� �巡��";
			else if (en instanceof IronGolem) n = "ö ��";
			else if (en instanceof Snowman) n = "�����";
			else if (en instanceof MagmaCube) n = "���׸� ť��";
			else if (en instanceof Bat) n = "����";
			else if (en instanceof Ocelot) n = "������";
			else if (en instanceof Villager) n = "�ֹ�";
			else if (en instanceof Witch) n = "����";
			else n = en.getType().getName();
			
			Player dp = null;
			if (ed instanceof Player) dp = (Player) ed;
			else if (ed instanceof Arrow) {
				if (!(((Arrow) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Arrow) ed).getShooter();
			}
			
			else if (ed instanceof Snowball) {
				if (!(((Snowball) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Snowball) ed).getShooter();
			}
			
			else if (ed instanceof Egg) {
				if (!(((Egg) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Egg) ed).getShooter();
			}

			else return;
			
			int y = de.getHealth() - e.getDamage();
			if (y < 0) y = 0;
			final Player findp = dp;
			float i = (float) de.getHealth() - (float) e.getDamage();
			i /= (float) de.getMaxHealth();
			B.setMessage(dp, Config.getName(n, n, y, de.getMaxHealth()));
			if ((i * 100F) == 0F) B.setHealth(dp, -50F);
			else B.setHealth(dp, i * 100F);
			Integer id = main.Timer.remove(dp.getName());
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
			
			if (i <= 0) {
				B.removeBar(dp);
			} else {
				M.Timer.put(dp.getName(), new BukkitRunnable()
				{
					public void run()
					{
						B.removeBar(findp);
						Integer id = main.Timer.remove(findp.getName());
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						return;
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHBarHealth"), 80L, 80L).getTaskId());
			} return;
		}
	}
}
