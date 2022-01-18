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
import org.bukkit.entity.Fireball;
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
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.ThrownPotion;
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
		Player p = e.getPlayer();
		Boss b = e.getBoss();

	    is = true;
		B.setMessage(p, Config.getName(b.getName().replaceAll("_", " "), b.getName().replaceAll("_", " "), 0, b.getMaxHealth()), 0.0F);
	  	B.removeBar(p);
	    
	    Integer id = main.Timer.remove(p.getName());
	    if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBossDamage(BossDamageEvent e) {
		final Player p = e.getPlayer();
		final Boss b = e.getBoss();
		
	    is = true;
	    
		PlayerTimer timer = new PlayerTimer(p, new Runnable() {
			@Override
			public void run() {
				float health = b.getHealth();
				health /= b.getMaxHealth(); health *= 100.0F;
				if (health <= 0.0F)
					health = 0.0F;
				B.setMessage(p, Config.getName(b.getName().replaceAll("_", " "), b.getName().replaceAll("_", " "), b.getHealth(), b.getMaxHealth()), health);
				
				if (b.getHealth() <= 0) {
					B.setMessage(p, Config.getName(b.getName().replaceAll("_", " "), b.getName().replaceAll("_", " "), 0, b.getMaxHealth()), 0.0F);
					B.removeBar(p);
				}
			}
		});
		
		timer.Pause();
		timer.setTime(1);
		timer.Start();
		
	    Integer id = main.Timer.remove(p.getName());
	    if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	    
	    main.Timer.put(p.getName(), new BukkitRunnable()
	    {
	    	public void run()
	    	{
	    		if (B.hasBar(p)) B.removeBar(p);
	    		Integer id = main.Timer.remove(p.getName());
	    		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
	    	}
	    }.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHBarHealth"), 120L, 120L).getTaskId());
	}

	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onEntityDamage(final EntityDamageByEntityEvent e) {
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

			else if (ed instanceof SmallFireball) {
				if (!(((SmallFireball) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((SmallFireball) ed).getShooter();
			}
			
			else if (ed instanceof Fireball) {
				if (!(((Fireball) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Fireball) ed).getShooter();
			}
			
			else if (ed instanceof ThrownPotion) {
				if (!(((ThrownPotion) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((ThrownPotion) ed).getShooter();
			}
			
			else return;
			
			final Player findp = dp;
			PlayerTimer timer = new PlayerTimer(dp, new Runnable() {
				@Override
				public void run() {
					float health = ep.getHealth();
					health /= ep.getMaxHealth(); health *= 100.0F;
					if (health <= 0.0F)
						health = 0.0F;
					B.setMessage(findp, Config.getName(ep.getName(), ep.getDisplayName(), ep.getHealth(), ep.getMaxHealth()), health);
					
					if (ep.isDead()) {
						B.setMessage(findp, Config.getName(ep.getName(), ep.getDisplayName(), 0, ep.getMaxHealth()), 0.0F);
						B.removeBar(findp);
					}
				}
			});
			
			timer.Pause();
			timer.setTime(1);
			timer.Start();
			
		    Integer id = main.Timer.remove(dp.getName());
		    if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		    
		    main.Timer.put(dp.getName(), new BukkitRunnable()
		    {
		    	public void run()
		    	{
		    		if (B.hasBar(findp)) B.removeBar(findp);
		    		Integer id = main.Timer.remove(findp.getName());
		    		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		    	}
		    }.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHBarHealth"), 120L, 120L).getTaskId());
		}
		
		else if (en instanceof LivingEntity) {
			if (is) {
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

			else if (ed instanceof SmallFireball) {
				if (!(((SmallFireball) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((SmallFireball) ed).getShooter();
			}

			else if (ed instanceof Fireball) {
				if (!(((Fireball) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((Fireball) ed).getShooter();
			}
			
			else if (ed instanceof ThrownPotion) {
				if (!(((ThrownPotion) ed).getShooter() instanceof Player)) return;
				dp = (Player) ((ThrownPotion) ed).getShooter();
			}

			else return;
			
			final Player findp = dp;
			final Damageable finde = de;
			final String finn = n;
			
			PlayerTimer timer = new PlayerTimer(dp, new Runnable() {
				@Override
				public void run() {
					float health = finde.getHealth();
					health /= finde.getMaxHealth(); health *= 100.0F;
					if (health <= 0.0F)
						health = 0.0F;
					B.setMessage(findp, Config.getName(finn, finn, finde.getHealth(), finde.getMaxHealth()), health);
					B.setHealth(findp, health);
					
					if (finde.getHealth() <= 0) {
						B.setMessage(findp, Config.getName(finn, finn, 0, finde.getMaxHealth()), 0.0F);
						B.removeBar(findp);
					}
				}
			});
			
			timer.Pause();
			timer.setTime(1);
			timer.Start();
			
		    Integer id = main.Timer.remove(dp.getName());
		    if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		    
		    main.Timer.put(dp.getName(), new BukkitRunnable()
		    {
		    	public void run()
		    	{
		    		if (B.hasBar(findp)) B.removeBar(findp);
		    		Integer id = main.Timer.remove(findp.getName());
		    		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		    	}
		    }.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHBarHealth"), 120L, 120L).getTaskId());
		}
	}
}
