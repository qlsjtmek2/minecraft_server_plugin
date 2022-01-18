package me.espoo.seteffect;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.espoo.file.PlayerYml;
import me.espoo.punchstat.StatsRunAPI;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;

@SuppressWarnings("deprecation")
public class mainEvent extends JavaPlugin implements Listener {
	main M;
	me.espoo.punchstat.Method E;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void handleItemHeld(PlayerItemHeldEvent event)
	{
		Player p = event.getPlayer();
		PlayerInventory inv = p.getInventory();
		if (Method.isSlotWeapon(p, inv, event.getNewSlot())) {
			Method.switchItems(event.getNewSlot(), inv);
			p.sendMessage("§c슬롯창에 무기를 두개 이상 사용하실 수 없습니다. 하나만 사용해 주시기 바랍니다.");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (!p.isOp()) {
			Timer timer1 = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 300);
			timer1.schedule(new TimerTask() {
				public void run() {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " off");
					p.setFlySpeed(0.2F);
				}
			}, timeToRun);	
		}
	}
	
	@EventHandler
	public void onBlockCan(BlockCanBuildEvent e)
	{
		if (e.getMaterial() == Material.SPONGE)
		{
			if (e.getBlock().getWorld().getName().equalsIgnoreCase("world") || e.getBlock().getWorld().getName().equalsIgnoreCase("world_pvp")) {
				e.setBuildable(false);
				e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), 5.0f);
			}
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent e) 
	{
		Player p = e.getPlayer();
		if (M.Sn.get(p.getName()) == null) {
			M.Sn.put(p.getName(), 0);
			
			M.Timer.put(p.getName(), new BukkitRunnable()
			{
				public void run()
				{
					Integer ie = main.Timer.remove(p.getName());
					if (ie != null) Bukkit.getServer().getScheduler().cancelTask(ie);
					if (M.Sn.get(p.getName()) != null) M.Sn.remove(p.getName());
					return;
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchSetEffect"), 40, 40).getTaskId());
		} else {
			int y = M.Sn.get(p.getName()) + 1;
			M.Sn.put(p.getName(), y);
		}
		
		int i = M.Sn.get(p.getName());
		if (i >= 5) {
			M.Sn.put(p.getName(), 0);
			
			if (Method.isHelmetItem(p, " 타츠마키 모자") && Method.isChestplateItem(p, " 타츠마키 튜닉")
			 && Method.isLeggingsItem(p, " 타츠마키 바지") && Method.isBootsItem(p, " 타츠마키 신발")) {
				Method.flyPlayer(p, 28);
			}
			
			else if (Method.isHelmetItem(p, " 아토믹 사무라이 모자") && Method.isChestplateItem(p, " 아토믹 사무라이 튜닉")
				  && Method.isLeggingsItem(p, " 아토믹 사무라이 바지") && Method.isBootsItem(p, " 아토믹 사무라이 신발")) {
				p.performCommand("cast 아토믹검기");
			}
			
			else if (Method.isHelmetItem(p, " 동제 모자") && Method.isChestplateItem(p, " 동제 튜닉")
				  && Method.isLeggingsItem(p, " 동제 바지") && Method.isBootsItem(p, " 동제 신발")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 동제 " + p.getName());
			}
			
			else if (Method.isHelmetItem(p, " 메탈나이트 투구") && Method.isChestplateItem(p, " 메탈나이트 갑옷")
				  && Method.isLeggingsItem(p, " 메탈나이트 레깅스") && Method.isBootsItem(p, " 메탈나이트 부츠")) {
				p.performCommand("cast 메탈나이트");
			}
			
			else if (Method.isHelmetItem(p, " 좀비맨 모자") && Method.isChestplateItem(p, " 좀비맨 튜닉")
				  && Method.isLeggingsItem(p, " 좀비맨 바지") && Method.isBootsItem(p, " 좀비맨 신발")) {
				p.performCommand("cast 좀비맨");
			}
			
			else if (Method.isHelmetItem(p, " 돈신 모자") && Method.isChestplateItem(p, " 돈신 튜닉")
				  && Method.isLeggingsItem(p, " 돈신 바지") && Method.isBootsItem(p, " 돈신 신발")) {
				p.performCommand("cast 돈신");
			}
			
			else if (Method.isHelmetItem(p, " 번견맨 모자") && Method.isChestplateItem(p, " 번견맨 튜닉")
				  && Method.isLeggingsItem(p, " 번견맨 바지") && Method.isBootsItem(p, " 번견맨 신발")) {
				p.performCommand("cast 번견맨");
			}
			
			else if (Method.isHelmetItem(p, " 제노스 모자") && Method.isChestplateItem(p, " 제노스 갑옷")
				  && Method.isLeggingsItem(p, " 제노스 바지") && Method.isBootsItem(p, " 제노스 신발")) {
				p.performCommand("cast 머신건블로우");
			}
			
			else if (Method.isHelmetItem(p, " 탱크톱 마스터 모자") && Method.isChestplateItem(p, " 탱크톱 마스터 튜닉")
				  && Method.isLeggingsItem(p, " 탱크톱 마스터 바지") && Method.isBootsItem(p, " 탱크톱 마스터 신발")) {
				p.performCommand("cast 탱크톱마스터");
			}
			
			else if (Method.isHelmetItem(p, " 후부키 모자") && Method.isChestplateItem(p, " 후부키 튜닉")
				  && Method.isLeggingsItem(p, " 후부키 바지") && Method.isBootsItem(p, " 후부키 신발")) {
				Method.fly2Player(p, 16);
			}
			
			else if (Method.isHelmetItem(p, " 섬광의 플래시 모자") && Method.isChestplateItem(p, " 섬광의 플래시 튜닉")
				  && Method.isLeggingsItem(p, " 섬광의 플래시 바지") && Method.isBootsItem(p, " 섬광의 플래시 신발")) {
				p.performCommand("cast 풀인각");
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) return;
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if (Method.isHelmetItem(p, " 실버팽 모자") && Method.isChestplateItem(p, " 실버팽 튜닉")
			 && Method.isLeggingsItem(p, " 실버팽 레깅스") && Method.isBootsItem(p, " 실버팽 신발")) {
				int i = new Random().nextInt(10) + 1;
				if (i <= 4) {
					e.setCancelled(true);
				}
			}
			
			else if (Method.isHelmetItem(p, " 초합금 검은빛 모자") && Method.isChestplateItem(p, " 초합금 검은빛 튜닉")
				  && Method.isLeggingsItem(p, " 초합금 검은빛 바지") && Method.isBootsItem(p, " 초합금 검은빛 신발")) {
				int i = e.getDamage();
				e.setDamage((i / 10) * 6);
			}
			
			if (Method.isHandItem(p, " 유수암쇄권", 351, 2)) {
				if ((e.getDamager() instanceof Projectile))
				{
					Projectile objProj = (Projectile) e.getDamager();
					double face_angle = p.getLocation().getDirection().dot(objProj.getShooter().getLocation().getDirection());
					if (face_angle > 0.0D) if (!(objProj.getShooter() instanceof Skeleton)) return;
				} else {
					double faceAngle = p.getLocation().getDirection().dot(e.getDamager().getLocation().getDirection());
					if (faceAngle > 0.0D) return;
				}
				
				int i = (e.getDamage() / 5) * 2;
				e.setDamage(i);
			}
		}
		
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (e.getEntity() instanceof LivingEntity) {
				LivingEntity en = (LivingEntity) e.getEntity();
				
				if (Method.isHandItem(p, " 섬광의 플래시 검", 276, 0) || Method.isHandItem(p, " 아토믹 사무라이 검", 267, 0)) {
					en.setNoDamageTicks(10);
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 1, true));
				}
				
				else if (Method.isHandItem(p, " 음속의 소닉 칼", 283, 0)) {
					en.setNoDamageTicks(10);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerHealth(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        boolean is = false;
        
        if (e.getEntity() instanceof Player) {
			final Player ep = (Player) e.getEntity();
			final Entity en = e.getDamager();
			
			if (en instanceof Player) {
				final Player dp = (Player) en;
				
				
				if (GuildAPI.inGuildAreaPvP(dp.getLocation()) || GuildAPI.inGuildAreaPvP(ep.getLocation())) {
					if (GuildAPI.getJoinGuild(dp) != null && GuildAPI.getJoinGuild(ep) != null) {
						if (!GuildAPI.getWarGuild(GuildAPI.getJoinGuild(dp)).equalsIgnoreCase(GuildAPI.getJoinGuild(ep))) {
							e.setCancelled(true); is = true;
						}
					} else {
						e.setCancelled(true); is = true;
					}
				}
				
				if (PartyAPI.getJoinParty(dp) != null && PartyAPI.getJoinParty(ep) != null) {
					if (PartyAPI.getJoinParty(dp).equalsIgnoreCase(PartyAPI.getJoinParty(ep))) {
						e.setCancelled(true); is = true;
					}
				}
				
				if (dp.getWorld().getName().equalsIgnoreCase("world_plant") && ep.getWorld().getName().equalsIgnoreCase("world_plant")) {
					if (GuildAPI.getJoinGuild(dp) != null && GuildAPI.getJoinGuild(ep) != null) {
	        			if (!GuildAPI.getWarGuild(GuildAPI.getJoinGuild(dp)).equalsIgnoreCase(GuildAPI.getJoinGuild(ep))) {
	        				e.setCancelled(true); is = true;
	        			}
	        		} else {
	        			e.setCancelled(true); is = true;
	        		}
				}
				
				else if (dp.getWorld().getName().equalsIgnoreCase("world_mining") && ep.getWorld().getName().equalsIgnoreCase("world_mining")) {
					if (GuildAPI.getJoinGuild(dp) != null && GuildAPI.getJoinGuild(ep) != null) {
	        			if (!GuildAPI.getWarGuild(GuildAPI.getJoinGuild(dp)).equalsIgnoreCase(GuildAPI.getJoinGuild(ep))) {
	        				e.setCancelled(true); is = true;
	        			}
	        		} else {
	        			e.setCancelled(true); is = true;
	        		}
				}
			}
		}
		
	    if ((!e.isCancelled()) && ((e.getEntity() instanceof LivingEntity)) && !is)
	    {
	    	int i = 0;
	    	if ((damager instanceof Player)) {
	    		Player p = (Player) damager;
		    	if (me.espoo.socket.main.cret.get(p.getName()) != null) i = 152;
		    	else i = 55;
	    	} else {
	    		i = 55;
	    	}
	    	
	    	e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.STEP_SOUND, i);
	    }

	    if (!(entity instanceof LivingEntity)) return;
        LivingEntity victim = (LivingEntity)entity;

        if (victim.getNoDamageTicks() > (damager.getMaxFireTicks() / 2.0F)) return;
	    
	    if ((damager instanceof Player)) {
	    	Player p = (Player) damager;
	    	
	    	if (!is) {
		    	if (Method.isHandItem(p, " 검은빛의 역기", 351, 11)) {
		    		if (me.espoo.socket.main.cret.get(p.getName()) != null) {
		    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) damager.getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_WOOD, 1.0F, 1.0F);
		    			me.espoo.socket.main.cret.remove(p.getName());
		    		} else {
		    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) damager.getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_WOOD, 0.8F, 1.0F);
		    		}
				} else {
		    		if (me.espoo.socket.main.cret.get(p.getName()) != null) {
		    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) damager.getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 1.0F, 1.0F);
		    			me.espoo.socket.main.cret.remove(p.getName());
		    		} else {
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) damager.getWorld().playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 0.8F, 2.0F);
		    		}
				}	
	    	}
	    }
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.DongJaInfo.contains(p)) {
			String str = Method.searchOnlinePlayer(e.getMessage());
			if (str == null) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 존재하지 않습니다.");
			} else {
				Player t = Method.getOnorOffLine(str);
				if (t == null) {
					e.setCancelled(true);
					p.sendMessage("§c그 플레이어는 온라인이 아닙니다.");
				} else {
					e.setCancelled(true);
					main.DongJaInfo.remove(p);
					me.espoo.is.GUI.InfoGUI(p, t);
				}
			}
		}
		
		if (main.DongJaPos.contains(p)) {
			String str = Method.searchOnlinePlayer(e.getMessage());
			if (str == null) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 존재하지 않습니다.");
			} else {
				Player t = Method.getOnorOffLine(str);
				if (t == null) {
					e.setCancelled(true);
					p.sendMessage("§c그 플레이어는 온라인이 아닙니다.");
				} else {
					e.setCancelled(true);
					main.DongJaPos.remove(p);
					
					boolean isOp = p.isOp();
					p.setOp(true);
					p.performCommand("getpos " + t.getName());
					if (isOp == false) p.setOp(false); p.setOp(false); p.setOp(false); p.setOp(false); p.setOp(false);
				}
			}
		}
		
		if (main.DongJaState.contains(p)) {
			String str = Method.searchOnlinePlayer(e.getMessage());
			if (str == null) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 존재하지 않습니다.");
			} else {
				Player t = Method.getOnorOffLine(str);
				if (t == null) {
					e.setCancelled(true);
					p.sendMessage("§c그 플레이어는 온라인이 아닙니다.");
				} else {
					e.setCancelled(true);
					main.DongJaState.remove(p);
					me.espoo.is.GUI.StatGUI(p, t);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		HumanEntity h = event.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (Method.isHelmetItem(p, " 구동기사 투구") && Method.isChestplateItem(p, " 구동기사 갑옷")
		 && Method.isLeggingsItem(p, " 구동기사 레깅스") && Method.isBootsItem(p, " 구동기사 부츠")) {
			p.removePotionEffect(PotionEffectType.SPEED);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1, true));
		}
		
		else if (Method.isHelmetItem(p, " 포동포동 프리즈너 모자") && Method.isChestplateItem(p, " 포동포동 프리즈너 튜닉")
		 && Method.isLeggingsItem(p, " 포동포동 프리즈너 바지") && Method.isBootsItem(p, " 포동포동 프리즈너 신발")) {
			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 2, true));
		}
		
		else if (Method.isHelmetItem(p, " 아마이마스크 모자") && Method.isChestplateItem(p, " 아마이마스크 튜닉")
		&& Method.isLeggingsItem(p, " 아마이마스크 바지") && Method.isBootsItem(p, " 아마이마스크 신발")) {
			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			p.removePotionEffect(PotionEffectType.REGENERATION);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1, true));
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1, true));
		}
		
		else if (Method.isHelmetItem(p, " 음속의 소닉 모자") && Method.isChestplateItem(p, " 음속의 소닉 튜닉")
		 && Method.isLeggingsItem(p, " 음속의 소닉 바지") && Method.isBootsItem(p, " 음속의 소닉 신발")) {
			p.removePotionEffect(PotionEffectType.SPEED);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
		}
	}
	
	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
    	Player p = (Player) e.getWhoClicked();
    	
    	if (p.getGameMode() != GameMode.CREATIVE) {
        	if (e.isShiftClick()) {
        		if (e.getCurrentItem() != null) {
        	    	ItemStack i = e.getCurrentItem();
        	    	
            		if (i.getItemMeta() != null) {
            			if (i.getItemMeta().getLore() != null) {
            				int num = 0;
            				for (String str : i.getItemMeta().getLore()) {
            					if (str.contains("미감정")) break;
            					else num++;
            				}
            				
            				if (num != i.getItemMeta().getLore().size()) {
            					if (i.getTypeId() == 298 || i.getTypeId() == 299 || i.getTypeId() == 300 || i.getTypeId() == 301 ||
                					i.getTypeId() == 302 || i.getTypeId() == 303 || i.getTypeId() == 304 || i.getTypeId() == 305 ||
                					i.getTypeId() == 306 || i.getTypeId() == 307 || i.getTypeId() == 308 || i.getTypeId() == 309 ||
                					i.getTypeId() == 310 || i.getTypeId() == 311 || i.getTypeId() == 312 || i.getTypeId() == 313 ||
                					i.getTypeId() == 314 || i.getTypeId() == 315 || i.getTypeId() == 316 || i.getTypeId() == 317) {
            						p.sendMessage("§c미감정 무기는 쉬프트 클릭 하실 수 없습니다.");
            						e.setCancelled(true);
            					}
            				}
            			}
            		}
        		}
        	}
        	
        	else if (e.getSlotType() == SlotType.ARMOR) {
        		if (e.getCursor() != null) {
        	    	ItemStack u = e.getCursor();
        	    	
        	    	if (u.getItemMeta() != null) {
            			if (u.getItemMeta().getLore() != null) {
            				int num = 0;
            				for (String str : u.getItemMeta().getLore()) {
            					if (str.contains("미감정")) break;
            					else num++;
            				}
            				
            				if (num != u.getItemMeta().getLore().size()) {
            					if (u.getTypeId() == 298 || u.getTypeId() == 299 || u.getTypeId() == 300 || u.getTypeId() == 301 ||
            						u.getTypeId() == 302 || u.getTypeId() == 303 || u.getTypeId() == 304 || u.getTypeId() == 305 ||
            						u.getTypeId() == 306 || u.getTypeId() == 307 || u.getTypeId() == 308 || u.getTypeId() == 309 ||
            						u.getTypeId() == 310 || u.getTypeId() == 311 || u.getTypeId() == 312 || u.getTypeId() == 313 ||
            						u.getTypeId() == 314 || u.getTypeId() == 315 || u.getTypeId() == 316 || u.getTypeId() == 317) {
            						p.sendMessage("§c미감정 무기는 장착하실 수 없습니다.");
            						e.setCancelled(true);
            					}
            				}
            			}
            		}
        		}
        	}	
    	}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onItemHeld(PlayerItemHeldEvent event) {
		HumanEntity h = event.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
	    PlayerInventory inv = p.getInventory();
	    ItemStack item = inv.getItem(event.getNewSlot());
	    
	    if (item != null && item.getTypeId() != 0) {
	    	if (Method.isHandItem2(p, item, "검은빛의", 351, 11)) {
	    		if (PlayerYml.getInfoBoolean(p, "복근 스텟 적용")) {
	    			Timer timer1 = new Timer();
	    			Date timeToRun = new Date(System.currentTimeMillis() + 20);
	    			timer1.schedule(new TimerTask() {
	    				public void run() {
	    	    			StatsRunAPI.PlayerHealth(p);
	    				}
	    			}, timeToRun);
	    		} else {
	    			Timer timer1 = new Timer();
	    			Date timeToRun = new Date(System.currentTimeMillis() + 20);
	    			timer1.schedule(new TimerTask() {
	    				public void run() {
	    	    			StatsRunAPI.PlayerHHealth(p);
	    				}
	    			}, timeToRun);
	    		}
	    	}
	    	
	    	else if (Method.isHandItem2(p, item, "번견맨의", 351, 12)) {
	    		if (PlayerYml.getInfoBoolean(p, "스피드 스텟 적용")) {
	    			Timer timer1 = new Timer();
	    			Date timeToRun = new Date(System.currentTimeMillis() + 20);
	    			timer1.schedule(new TimerTask() {
	    				public void run() {
	    	    			float i = (float) (PlayerYml.getInfoInt(p, "스피드") + me.espoo.punchstat.Method.get4Stat(p)) / 200F;
	    	    			if (i <= 1F) {
	    	    				if (i > 0.2F) {
	    	    					p.setWalkSpeed(i);
	    	    				} else {
	    	    					p.setWalkSpeed(0.2F);
	    	    				}
	    	    			} else {
	    	    				p.setWalkSpeed(1F);
	    	    			}
	    				}
	    			}, timeToRun);
	    		} else {
	    			p.setWalkSpeed(0.2F);
	    		}
	    	}
	    	
	    	else if (Method.isHandItem(p, " 엔젤 스타일", 290, 0)) {
    			Timer timer1 = new Timer();
    			Date timeToRun = new Date(System.currentTimeMillis() + 20);
    			timer1.schedule(new TimerTask() {
    				public void run() {
    		    		if (PlayerYml.getInfoBoolean(p, "다리 근력 스텟 적용")) {
    		    			int i = (PlayerYml.getInfoInt(p, "다리 근력") + me.espoo.punchstat.Method.get3Stat(p)) / 10;
    		    			if (i > 0) {
    		    				p.removePotionEffect(PotionEffectType.JUMP);
    		    				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
    		    			}
    		    		}
    				}
    			}, timeToRun);
	    	} 
	    	
	    	else {
    			Timer timer1 = new Timer();
    			Date timeToRun = new Date(System.currentTimeMillis() + 20);
    			timer1.schedule(new TimerTask() {
    				public void run() {
    		    		if (PlayerYml.getInfoBoolean(p, "복근 스텟 적용")) {
    		    			StatsRunAPI.PlayerHealth(p);
    		    		} else {
    		    			StatsRunAPI.PlayerHHealth(p);
    		    		}

    		    		if (PlayerYml.getInfoBoolean(p, "스피드 스텟 적용")) {
    		    			float i = (float) (PlayerYml.getInfoInt(p, "스피드") + me.espoo.punchstat.Method.get4Stat(p)) / 200F;
    		    			if (i <= 1F) {
    		    				if (i > 0.2F) {
    		    					p.setWalkSpeed(i);
    		    				} else {
    		    					p.setWalkSpeed(0.2F);
    		    				}
    		    			} else {
    		    				p.setWalkSpeed(1F);
    		    			}
    		    		} else {
    		    			p.setWalkSpeed(0.2F);
    		    		}
    		    		
    		    		if (PlayerYml.getInfoBoolean(p, "다리 근력 스텟 적용")) {
    		    			int i = (PlayerYml.getInfoInt(p, "다리 근력") + me.espoo.punchstat.Method.get3Stat(p)) / 10;
    		    			if (i > 0) {
    		    				p.removePotionEffect(PotionEffectType.JUMP);
    		    				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
    		    			}
    		    		}
    				}
    			}, timeToRun);
	    	}
	    } else {
			Timer timer1 = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 20);
			timer1.schedule(new TimerTask() {
				public void run() {
		    		if (PlayerYml.getInfoBoolean(p, "복근 스텟 적용")) {
		    			StatsRunAPI.PlayerHealth(p);
		    		} else {
		    			StatsRunAPI.PlayerHHealth(p);
		    		}
		    		
		    		if (PlayerYml.getInfoBoolean(p, "스피드 스텟 적용")) {
		    			float i = (float) (PlayerYml.getInfoInt(p, "스피드") + me.espoo.punchstat.Method.get4Stat(p)) / 200F;
		    			if (i <= 1F) {
		    				if (i > 0.2F) {
		    					p.setWalkSpeed(i);
		    				} else {
		    					p.setWalkSpeed(0.2F);
		    				}
		    			} else {
		    				p.setWalkSpeed(1F);
		    			}
		    		} else {
		    			p.setWalkSpeed(0.2F);
		    		}
		    		
		    		if (PlayerYml.getInfoBoolean(p, "다리 근력 스텟 적용")) {
		    			int i = (PlayerYml.getInfoInt(p, "다리 근력") + me.espoo.punchstat.Method.get3Stat(p)) / 10;
		    			if (i > 0) {
		    				p.removePotionEffect(PotionEffectType.JUMP);
		    				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
		    			}
		    		}
				}
			}, timeToRun);
	    }
	}
}
