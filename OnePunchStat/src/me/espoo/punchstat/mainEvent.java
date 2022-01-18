package me.espoo.punchstat;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.file.PlayerYml;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	Method E;
	PlayerYml P;
	GUIMessage G;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
		
		if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
			float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		
		if (P.getInfoBoolean(p, "다리 근력 스텟 적용")) {
			int i = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 10;
			if (i > 0) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
		
		if (P.getInfoBoolean(p, "복근 스텟 적용")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
		
		if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
			float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		
		if (P.getInfoBoolean(p, "다리 근력 스텟 적용")) {
			int i = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 10;
			if (i > 0) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		File folder = new File("plugins/OnePunchStat");
		File folder2 = new File("plugins/OnePunchStat/Player");
		File f = new File("plugins/OnePunchStat/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
		
		if (P.getInfoBoolean(p, "복근 스텟 적용")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
		
		if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
			float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		
		if (P.getInfoBoolean(p, "다리 근력 스텟 적용")) {
			int i = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 10;
			if (i > 0) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
    	Player p = (Player) e.getWhoClicked();
    	if (e.getInventory().getName().equalsIgnoreCase(G.getMessage(p, "스텟 GUI 이름"))) {
    		e.setCancelled(true);
    		
    		if (e.getCurrentItem() != null) {
    			if (e.getCurrentItem().getItemMeta() != null) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "팔 근력.아이템 이름"))) {
    						boolean is = PlayerYml.getInfoBoolean(p, "팔 근력 스텟 적용");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "팔 근력 스텟 적용", false);
    						} else {
    							PlayerYml.setInfoBoolean(p, "팔 근력 스텟 적용", true);
    						} GUI.openGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "복근.아이템 이름"))) {
        					boolean is = PlayerYml.getInfoBoolean(p, "복근 스텟 적용");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "복근 스텟 적용", false);
    							StatsRunAPI.PlayerHHealth(p);
    						} else {
    							PlayerYml.setInfoBoolean(p, "복근 스텟 적용", true);
    							StatsRunAPI.PlayerHealth(p);
    						} GUI.openGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "다리 근력.아이템 이름"))) {
        					boolean is = PlayerYml.getInfoBoolean(p, "다리 근력 스텟 적용");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "다리 근력 스텟 적용", false);
    							p.removePotionEffect(PotionEffectType.JUMP);
    						} else {
    							PlayerYml.setInfoBoolean(p, "다리 근력 스텟 적용", true);
    							int i = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
    							if (i > 0) {
    								p.removePotionEffect(PotionEffectType.JUMP);
    								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
    							}
    						} GUI.openGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "스피드.아이템 이름"))) {
        					boolean is = PlayerYml.getInfoBoolean(p, "스피드 스텟 적용");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "스피드 스텟 적용", false);
    							p.setWalkSpeed(0.2F);
    						} else {
    							PlayerYml.setInfoBoolean(p, "스피드 스텟 적용", true);
    							float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
    							if (i <= 1F) {
    								if (i > 0.2F) {
    									p.setWalkSpeed(i);
    								} else {
    									p.setWalkSpeed(0.2F);
    								}
    							} else {
    								p.setWalkSpeed(1F);
    							}
    						} GUI.openGUI(p);
        				}
    				}
    			}
    		}
    	}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (!event.isCancelled()) {
			Entity entity = event.getDamager();
			Player attacker = null;
			if ((entity instanceof Player)) {
				attacker = ((Player)entity);
				if (P.getInfoBoolean(attacker, "팔 근력 스텟 적용")) {
					StatsRunAPI.EntityDamage(attacker, event);
				}
			}
			
			else if ((entity instanceof Snowball)) {
				Snowball ar = (Snowball) entity;
				if ((ar.getShooter() != null) && ((ar.getShooter() instanceof Player))) {
					attacker = (Player) ar.getShooter();
					if (P.getInfoBoolean(attacker, "팔 근력 스텟 적용")) {
						StatsRunAPI.EntityDamage(attacker, event);
					}
				}
			}
			
			else if ((entity instanceof Arrow)) {
				Arrow ar = (Arrow) entity;
				if ((ar.getShooter() != null) && ((ar.getShooter() instanceof Player))) {
					attacker = (Player) ar.getShooter();
					if (P.getInfoBoolean(attacker, "팔 근력 스텟 적용")) {
						StatsRunAPI.EntityDamage(attacker, event);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if (P.getInfoBoolean(event.getPlayer(), "복근 스텟 적용")) {
			StatsRunAPI.PlayerHealth(event.getPlayer());
		} else {
			StatsRunAPI.PlayerHHealth(event.getPlayer());
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void SneakEvent(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (M.S3.get(p.getName()) == null) M.S3.put(p.getName(), 0);
		int y = M.S3.get(p.getName()) + 1;
		M.S3.put(p.getName(), y);
			
		if (y >= 200) {
			M.S3.remove(p.getName());
			p.sendMessage("§6당신의 §c다리 근력 §6스텟 효과가 §c+1 §6상승하였습니다.");
			P.setInfoInt(p, "다리 근력", P.getInfoInt(p, "다리 근력") + 1);
			E.castLvup(p);
			
			if (P.getInfoInt(p, "다리 근력") >= 200) {
				if (!M.permission.has(p, "magicspells.grant.진심점프")) {
					p.sendMessage("§6다리 근력 스텟이 §c200 §6을 돌파하여 §c액티브 §f- 진심 점프 §6스킬을 획득하셨습니다.");
					
					for (World world : Bukkit.getWorlds()) {
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심점프");
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심점프");
					}
				}
			}
			
			else if (P.getInfoInt(p, "다리 근력") >= 500) {
				if (!M.permission.has(p, "magicspells.grant.진심니킥")) {
					p.sendMessage("§6다리 근력 스텟이 §c500 §6을 돌파하여 §c액티브 §f- 진심 니킥 §6스킬을 획득하셨습니다.");
					
					for (World world : Bukkit.getWorlds()) {
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심니킥");
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심니킥");
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (e.getFrom().getY() < e.getTo().getY() && e.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
			if (p.isSneaking()) {
				if (M.S2.get(p.getName()) == null) M.S2.put(p.getName(), 0);
				int y = M.S2.get(p.getName()) + 1;
				M.S2.put(p.getName(), y);
					
				if (y >= 300) {
					M.S2.remove(p.getName());
					p.sendMessage("§6당신의 §c복근 §6스텟 효과가 §c+1 §6상승하였습니다.");
					P.setInfoInt(p, "복근", P.getInfoInt(p, "복근") + 1);
					E.castLvup(p);
					
					if (P.getInfoBoolean(p, "복근 스텟 적용")) {
						StatsRunAPI.PlayerHealth(p);
					} else {
						StatsRunAPI.PlayerHHealth(p);
					}
					
					if (P.getInfoInt(p, "복근") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.진심박치기")) {
							p.sendMessage("§6복근 스텟이 §c200 §6을 돌파하여 §c액티브 §f- 진심 박치기 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심박치기");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심박치기");
							}
						}
					}
					
					else if (P.getInfoInt(p, "복근") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.진심버프")) {
							p.sendMessage("§6복근 스텟이 §c500 §6을 돌파하여 §d버프 §f- 진심 강화 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심버프");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심버프");
							}
						}
					}
				}
			}
		}
		
		if (P.getInfoInt(p, "스피드") < 200) {
			final int xFrom = e.getFrom().getBlockX();
	        final int xTo = e.getTo().getBlockX();
	        final int yFrom = e.getFrom().getBlockY();
	        final int yTo = e.getTo().getBlockY();
	        final int zFrom = e.getFrom().getBlockZ();
	        final int zTo = e.getTo().getBlockZ();
	        
	        if (xFrom == xTo && yFrom == yTo && zFrom - zTo == 1 || xFrom == xTo && yFrom == yTo && zFrom - zTo == -1) {
	        	if (M.S4.get(p.getName()) == null) M.S4.put(p.getName(), 0);
				int y = M.S4.get(p.getName()) + 1;
				M.S4.put(p.getName(), y);
				
				if (y >= 1300) {
					M.S4.remove(p.getName());
					p.sendMessage("§6당신의 §c스피드 §6스텟 효과가 §c+1 §6상승하였습니다.");
					P.setInfoInt(p, "스피드", P.getInfoInt(p, "스피드") + 1);
					E.castLvup(p);
					
					if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
						float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					
					if (P.getInfoInt(p, "스피드") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.진심좌우반복뛰기")) {
							p.sendMessage("§6스피드 스텟이 §c200 §6을 돌파하여 §c액티브 §f- 진심 좌우 반복뛰기 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심좌우반복뛰기");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심좌우반복뛰기");
							}
						}
					}
					
					else if (P.getInfoInt(p, "스피드") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.진심런닝")) {
							p.sendMessage("§6스피드 스텟이 §c500 §6을 돌파하여 §c액티브 §f- 진심 런닝 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심런닝");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심런닝");
							}
						}
					}
				}
	        }
	        
	        else if (xFrom - xTo == 1 && yFrom == yTo && zFrom == zTo || xFrom - xTo == -1 && yFrom == yTo && zFrom == zTo) {
	        	if (M.S4.get(p.getName()) == null) M.S4.put(p.getName(), 0);
				int y = M.S4.get(p.getName()) + 1;
				M.S4.put(p.getName(), y);
				
				if (y >= 1500) {
					M.S4.remove(p.getName());
					p.sendMessage("§6당신의 §c스피드 §6스텟 효과가 §c+1 §6상승하였습니다.");
					P.setInfoInt(p, "스피드", P.getInfoInt(p, "스피드") + 1);
					E.castLvup(p);
					
					if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
						float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					
					if (P.getInfoInt(p, "스피드") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.진심좌우반복뛰기")) {
							p.sendMessage("§6스피드 스텟이 §c200 §6을 돌파하여 §c액티브 §f- 진심 좌우 반복뛰기 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심좌우반복뛰기");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심좌우반복뛰기");
							}
						}
					}
					
					else if (P.getInfoInt(p, "스피드") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.진심런닝")) {
							p.sendMessage("§6스피드 스텟이 §c500 §6을 돌파하여 §c액티브 §f- 진심 런닝 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심런닝");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심런닝");
							}
						}
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
		
		if (act == Action.LEFT_CLICK_BLOCK)
	    {
			Block b = e.getClickedBlock();
			if (b.getType() != Material.AIR && b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER 
			 && b.getType() != Material.LAVA && b.getType() != Material.STATIONARY_LAVA) {
				if (M.S1.get(p.getName()) == null) M.S1.put(p.getName(), 0);
				int y = M.S1.get(p.getName()) + 1;
				M.S1.put(p.getName(), y);
					
				if (y >= 300) {
					M.S1.remove(p.getName());
					p.sendMessage("§6당신의 §c팔 근력 §6스텟 효과가 §c+1 §6상승하였습니다.");
					P.setInfoInt(p, "팔 근력", P.getInfoInt(p, "팔 근력") + 1);
					E.castLvup(p);
					
					if (P.getInfoInt(p, "팔 근력") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.연속보통펀치")) {
							p.sendMessage("§6팔 근력 스텟이 §c200 §6을 돌파하여 §c액티브 §f- 연속 보통 펀치 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.연속보통펀치");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.연속보통펀치");
							}
						}
					}
					
					else if (P.getInfoInt(p, "팔 근력") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.진심펀치")) {
							p.sendMessage("§6팔 근력 스텟이 §c500 §6을 돌파하여 §c액티브 §f- 진심 펀치 §6스킬을 획득하셨습니다.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.진심펀치");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.진심펀치");
							}
						}
					}
				}
			}
	    }
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		HumanEntity h = event.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		if (P.getInfoBoolean(p, "복근 스텟 적용")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
		
		if (P.getInfoBoolean(p, "스피드 스텟 적용")) {
			float i = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
	}
}
