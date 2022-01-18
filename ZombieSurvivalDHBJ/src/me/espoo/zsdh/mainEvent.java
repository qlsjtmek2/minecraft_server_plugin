package me.espoo.zsdh;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class mainEvent extends JavaPlugin implements Listener {
	main main;
	
	public mainEvent(main main)
	{
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 300);
		timer1.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					p.sendMessage("");
				}
				p.sendMessage("��e�����������������������������������������");
				p.sendMessage("");
				p.sendMessage(" ����������6ȯ���մϴ� ��e" + p.getName() + "��6��! ���õ� ��ſ� ��c����ũ����Ʈ ��6�ϼ���~");
				p.sendMessage("");
				p.sendMessage("��e�����������������������������������������");
				p.sendMessage("");
				return;
			}
		}, timeToRun);

		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			Method.CreatePlayerInfo(p, f, folder, folder2, config);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Method.subStartList(p.getName());
		Method.setInfoBoolean(p, "GameJoin", false);
		
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		
		if (main.Gun.get(p.getName()) != null) {
			main.Gun.remove(p.getName());
		}
		
		if (main.zombieData.get(p.getName()) == "true") {
			Method.subTeamZombie(p.getName());
			if (Method.getTeamZombie() <= 0) {
				Method.HWin();
				return;
			}
		}
		
		else if (main.humanData.get(p.getName()) == "true") {
			Method.subTeamHuman(p.getName());
			if (Method.getTeamHuman() <= 0) {
				Method.ZWin();
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();
		Method.subStartList(p.getName());
		Method.setInfoBoolean(p, "GameJoin", false);
		
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		
		if (main.Gun.get(p.getName()) != null) {
			main.Gun.remove(p.getName());
		}
		
		if (main.zombieData.get(p.getName()) == "true") {
			Method.subTeamZombie(p.getName());
			if (Method.getTeamZombie() <= 0) {
				Method.HWin();
				return;
			}
		}
		
		else if (main.humanData.get(p.getName()) == "true") {
			Method.subTeamHuman(p.getName());
			if (Method.getTeamHuman() <= 0) {
				Method.ZWin();
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (main.stoppvp.get("server") != null) {
			e.setCancelled(true);
			return;
		}
		
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Entity en = e.getDamager();
			
			if (main.humanData.get(p.getName()) != null && 
				e.getDamager().getType() == EntityType.EGG ||
				main.humanData.get(p.getName()) != null &&
				e.getDamager().getType() == EntityType.SNOWBALL ||
				main.humanData.get(p.getName()) != null && 
				e.getDamager().getType() == EntityType.ARROW) {
				e.setCancelled(true);
				return;
			}
		
			if (en instanceof Player) {
				Player ep = (Player) en;
				if (main.zombieData.get(p.getName()) != null && main.zombieData.get(ep.getName()) != null) {
					e.setCancelled(true);
					return;
				}
				
				else if (main.humanData.get(p.getName()) != null && main.humanData.get(ep.getName()) != null) {
					e.setCancelled(true);
					return;
				}
		        
				if (Method.getConfigBoolean("Start")) {
			        if (main.zombieData.get(ep.getName()) != null) {
			        	if (main.zombieData.get(p.getName()) == null) {
			        		if (ep.getWorld().getName().equalsIgnoreCase("world_pvp")) {
			        			if (ep.getItemInHand().getTypeId() == 372) {
							        e.setCancelled(true);
							       	Method.RankingStat(ep);
							        Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + "��6 ���� ��c" + ep.getName() + " ��6�Կ� ���ؼ� �����Ǽ̽��ϴ�.");
							        p.playSound(p.getLocation(), Sound.GHAST_CHARGE, 100.0f, 100.0f);
							        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
							        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 1, true));
							        int HD = Method.getInfoInt(p, "HumanDeath") + 1;
							        int ZK = Method.getInfoInt(ep, "ZombieKill") + 1;
							        Method.setInfoInt(p, "HumanDeath", HD);
							        Method.setInfoInt(ep, "ZombieKill", ZK);
							        if (main.Gun.get(p.getName()) == null) main.Gun.put(p.getName(), "true");
							        main.humanData.remove(p.getName());
	                        		p.closeInventory();
									main.zombieData.put(p.getName(), "true");
									Method.addTeamZombie(p.getName());
									Method.subTeamHuman(p.getName());
							        if (Method.getTeamHuman() == 0) {
							        	Method.ZWinGameStop();
										Integer id = main.taskIds.remove("StartTimer");
												
										if (id != null) {
											Bukkit.getServer().getScheduler().cancelTask(id);
										}
										
										return;
							        }
									p.getInventory().clear();
									p.getInventory().setItem(0, new ItemStack(372, 1));
									p.getInventory().setHelmet(new ItemStack(91, 1));
				        		}
			        		}
			        	}
			        }
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		PlayerDeathEvent Event0 = (PlayerDeathEvent) e;
		Player p = Event0.getEntity();
		Player kp = (Player) p.getKiller();
		
		if (kp == null) {
			Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + "��6 ���� ��c�ǹ��� ��6�ϼ̽��ϴ�.");
    		String warpName = Method.getConfigString("WarpName");
			Timer timer1 = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 1 * 1000);
			timer1.schedule(new TimerTask() {
				public void run() {
					if (main.zombieData.get(p.getName()) == "����") {
				        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
				        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 2, true));
					}
					
					else if (main.zombieData.get(p.getName()) != null) {
				        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
				        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 1, true));
					}
					
					else if (main.humanData.get(p.getName()) != null) {
						if (!p.hasPermission("coloredtags.bold_MHG")) {
							if (main.zombieData.get(p.getName()) != null) main.zombieData.remove(p.getName());
							if (main.humanData.get(p.getName()) != null) main.humanData.remove(p.getName());
							main.zombieData.put(p.getName(), "true");
							p.getInventory().clear();
							p.setFoodLevel(20);
							p.setLevel(0);
							p.setExhaustion(0.0F);
							p.setExp(0.0F);
							p.setHealth(20);
							p.getInventory().setHelmet(null);
							p.getInventory().setBoots(null);
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setItem(0, new ItemStack(372, 1));
							p.getInventory().setHelmet(new ItemStack(91, 1));
							Method.subTeamHuman(p.getName());
							Method.addTeamZombie(p.getName());
							p.sendMessage(main.aprx + "��f����� �ǹ��� �ϼ����Ƿ� �ڵ����� ������ ���� �˴ϴ�.");
						}
					}
					
					else {
						if (!p.hasPermission("coloredtags.bold_MHG")) {
							if (Method.getConfigBoolean("Start")) {
								if (main.zombieData.get(p.getName()) != null) main.zombieData.remove(p.getName());
								if (main.humanData.get(p.getName()) != null) main.humanData.remove(p.getName());
								main.zombieData.put(p.getName(), "true");
								p.getInventory().clear();
								p.setFoodLevel(20);
								p.setLevel(0);
								p.setExhaustion(0.0F);
								p.setExp(0.0F);
								p.setHealth(20);
								p.getInventory().setHelmet(null);
								p.getInventory().setBoots(null);
								p.getInventory().setChestplate(null);
								p.getInventory().setLeggings(null);
								p.getInventory().setItem(0, new ItemStack(372, 1));
								p.getInventory().setHelmet(new ItemStack(91, 1));
								p.sendMessage(main.aprx + "��f����� �ǹ��� �ϼ����Ƿ� �ڵ����� ������ ���� �˴ϴ�.");
							}
						}
					}

					if (!p.hasPermission("coloredtags.bold_MHG")) {
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/warp " + warpName);
							p.setOp(false);
						} else {
							p.chat("/warp " + warpName);
						}
					} return;
				}
			}, timeToRun);
			return;
		} else {
			if (Method.getConfigBoolean("Start")) {
				if (kp.getWorld().getName().equalsIgnoreCase("world_pvp")) {
					if (main.zombieData.get(p.getName()) != null) {
				    	if (main.humanData.get(kp.getName()) != null) {
				    		String warpName = Method.getConfigString("WarpName");
					        Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + "��6 ���� ��c" + kp.getName() + " ��6�Կ� ���ؼ� ����ϼ̽��ϴ�.");
					        int HK = Method.getInfoInt(kp, "HumanKill") + 1;
					        Method.setInfoInt(kp, "HumanKill", HK);
					        int ZD = Method.getInfoInt(p, "ZombieDeath");
					        Method.setInfoInt(p, "ZombieDeath", ZD+1);
					        
							Timer timer1 = new Timer();
							Date timeToRun = new Date(System.currentTimeMillis() + 1 * 1000);
							timer1.schedule(new TimerTask() {
								public void run() {
									if (main.zombieData.get(p.getName()).equals("����")) {
								        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
								        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 2, true));
									}
									
									else if (main.zombieData != null) {
								        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
								        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 1, true));
									}

									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/warp " + warpName);
										p.setOp(false);
									} else {
										p.chat("/warp " + warpName);
									}
									return;
								}
							}, timeToRun);
				    	}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		String[] a = e.getMessage().split(" ");
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world_pvp"))
		{
			if (!e.getPlayer().isOp())
			{
				if (Method.getConfigBoolean("Start")) {
					if (!(a[0].equalsIgnoreCase("/����")) && !(a[0].equalsIgnoreCase("/�޴�")) && e.getPlayer().isOp() == false
					 	 	 && !(a[0].equalsIgnoreCase("/�α���")) && !(a[0].equalsIgnoreCase("/l")) && !(a[0].equalsIgnoreCase("/login"))
						 	 && !(a[0].equalsIgnoreCase("/register")) && !(a[0].equalsIgnoreCase("/ȸ������")) && !(a[0].equalsIgnoreCase("/rank"))
						 	 && !(a[0].equalsIgnoreCase("/��������")) && !(a[0].equalsIgnoreCase("/â��")) && !(a[0].equalsIgnoreCase("/���մ�"))
						 	 && !(a[0].equalsIgnoreCase("/list")) && !(a[0].equalsIgnoreCase("/ī���ּ�"))&& !(a[0].equalsIgnoreCase("/��"))
						 	 && !(a[0].equalsIgnoreCase("/����")) && !(a[0].equalsIgnoreCase("/��ɾ�")) && !(a[0].equalsIgnoreCase("/zs"))
						 	 && !(a[0].equalsIgnoreCase("/����")) && !(a[0].equalsIgnoreCase("/��")) && !(a[0].equalsIgnoreCase("/ġ��"))
						 	 && !(a[0].equalsIgnoreCase("/��ƼŬ")) && !(a[0].equalsIgnoreCase("/give")) && !(a[0].equalsIgnoreCase("/����"))
						 	 && !(a[0].equalsIgnoreCase("/��������������")) && !(a[0].equalsIgnoreCase("/nick")) && !(a[0].equalsIgnoreCase("/��������������"))
						 	 && !(a[0].equalsIgnoreCase("/����")) && !(a[0].equalsIgnoreCase("/�л�����")) && !(a[0].equalsIgnoreCase("/�к�����"))
						 	 && !(a[0].equalsIgnoreCase("/��")) && !(a[0].equalsIgnoreCase("/m")) && !(a[0].equalsIgnoreCase("/me"))
						 	 && !(a[0].equalsIgnoreCase("/w")) && !(a[0].equalsIgnoreCase("/pay")) && !(a[0].equalsIgnoreCase("/��"))
						 	 && !(a[0].equalsIgnoreCase("/money")) && !(a[0].equalsIgnoreCase("/������"))) {
								e.getPlayer().sendMessage(main.aprx + "��c���� �����̹� �����߿��� ��ɾ �Է��Ͻ� �� �����ϴ�.");
								e.setCancelled(true);
							}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		Action act = e.getAction();
		Player p = e.getPlayer();
	    if ((act == Action.RIGHT_CLICK_AIR) || (act == Action.RIGHT_CLICK_BLOCK)) {
	        if ((e.getItem() != null) && p.getItemInHand().getTypeId() == 372) {
	    		if (main.zombieData.get(p.getName()) != null) {
	    			if (Method.getConfigBoolean("Start")) {
	    				if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
	    	            	if (!(main.spam.contains(p))) {
		                        main.spam.add(p);
		                        new Timer().schedule(new TimerTask() {
		                            @Override
		                            public void run() {
		                            	p.sendMessage(main.aprx + "��f���� ��a�ż��� �߰��� ��f��ų �ߵ�!");
		                		        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, true));
		                		        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 1, true));
		                                p.playSound(p.getLocation(), Sound.EXPLODE, 200.0f, 100.0f);
		                                this.cancel();
		                            }
		                        }, 0L, 12*1000L);
		                        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)main, (Runnable)new Runnable() {
		                        	@Override
		                        	public void run() {
		                        		main.spam.remove(p);
		                        	}
		                        }, 240L);
	    	            	} else {
	    	                	p.sendMessage(main.aprx + "��c������ ��ų ��Ÿ���� 12���Դϴ�.");
	    	                	return;
	    	                }
	    				}
	    			}
	    		}
	    	}
	    }
	}
}
