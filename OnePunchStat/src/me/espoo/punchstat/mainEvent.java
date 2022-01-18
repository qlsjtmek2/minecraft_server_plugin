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
		
		if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
			float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		
		if (P.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
			int i = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 10;
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
		
		if (P.getInfoBoolean(p, "���� ���� ����")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
		
		if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
			float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		
		if (P.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
			int i = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 10;
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
		
		if (P.getInfoBoolean(p, "���� ���� ����")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
		
		if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
			float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
		
		if (P.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
			int i = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 10;
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
    	if (e.getInventory().getName().equalsIgnoreCase(G.getMessage(p, "���� GUI �̸�"))) {
    		e.setCancelled(true);
    		
    		if (e.getCurrentItem() != null) {
    			if (e.getCurrentItem().getItemMeta() != null) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "�� �ٷ�.������ �̸�"))) {
    						boolean is = PlayerYml.getInfoBoolean(p, "�� �ٷ� ���� ����");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "�� �ٷ� ���� ����", false);
    						} else {
    							PlayerYml.setInfoBoolean(p, "�� �ٷ� ���� ����", true);
    						} GUI.openGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "����.������ �̸�"))) {
        					boolean is = PlayerYml.getInfoBoolean(p, "���� ���� ����");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "���� ���� ����", false);
    							StatsRunAPI.PlayerHHealth(p);
    						} else {
    							PlayerYml.setInfoBoolean(p, "���� ���� ����", true);
    							StatsRunAPI.PlayerHealth(p);
    						} GUI.openGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "�ٸ� �ٷ�.������ �̸�"))) {
        					boolean is = PlayerYml.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "�ٸ� �ٷ� ���� ����", false);
    							p.removePotionEffect(PotionEffectType.JUMP);
    						} else {
    							PlayerYml.setInfoBoolean(p, "�ٸ� �ٷ� ���� ����", true);
    							int i = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
    							if (i > 0) {
    								p.removePotionEffect(PotionEffectType.JUMP);
    								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, i - 1, true));
    							}
    						} GUI.openGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "���ǵ�.������ �̸�"))) {
        					boolean is = PlayerYml.getInfoBoolean(p, "���ǵ� ���� ����");
    						if (is == true) {
    							PlayerYml.setInfoBoolean(p, "���ǵ� ���� ����", false);
    							p.setWalkSpeed(0.2F);
    						} else {
    							PlayerYml.setInfoBoolean(p, "���ǵ� ���� ����", true);
    							float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
				if (P.getInfoBoolean(attacker, "�� �ٷ� ���� ����")) {
					StatsRunAPI.EntityDamage(attacker, event);
				}
			}
			
			else if ((entity instanceof Snowball)) {
				Snowball ar = (Snowball) entity;
				if ((ar.getShooter() != null) && ((ar.getShooter() instanceof Player))) {
					attacker = (Player) ar.getShooter();
					if (P.getInfoBoolean(attacker, "�� �ٷ� ���� ����")) {
						StatsRunAPI.EntityDamage(attacker, event);
					}
				}
			}
			
			else if ((entity instanceof Arrow)) {
				Arrow ar = (Arrow) entity;
				if ((ar.getShooter() != null) && ((ar.getShooter() instanceof Player))) {
					attacker = (Player) ar.getShooter();
					if (P.getInfoBoolean(attacker, "�� �ٷ� ���� ����")) {
						StatsRunAPI.EntityDamage(attacker, event);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if (P.getInfoBoolean(event.getPlayer(), "���� ���� ����")) {
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
			p.sendMessage("��6����� ��c�ٸ� �ٷ� ��6���� ȿ���� ��c+1 ��6����Ͽ����ϴ�.");
			P.setInfoInt(p, "�ٸ� �ٷ�", P.getInfoInt(p, "�ٸ� �ٷ�") + 1);
			E.castLvup(p);
			
			if (P.getInfoInt(p, "�ٸ� �ٷ�") >= 200) {
				if (!M.permission.has(p, "magicspells.grant.��������")) {
					p.sendMessage("��6�ٸ� �ٷ� ������ ��c200 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ���� ��6��ų�� ȹ���ϼ̽��ϴ�.");
					
					for (World world : Bukkit.getWorlds()) {
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.��������");
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.��������");
					}
				}
			}
			
			else if (P.getInfoInt(p, "�ٸ� �ٷ�") >= 500) {
				if (!M.permission.has(p, "magicspells.grant.���ɴ�ű")) {
					p.sendMessage("��6�ٸ� �ٷ� ������ ��c500 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ��ű ��6��ų�� ȹ���ϼ̽��ϴ�.");
					
					for (World world : Bukkit.getWorlds()) {
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.���ɴ�ű");
						M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.���ɴ�ű");
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
					p.sendMessage("��6����� ��c���� ��6���� ȿ���� ��c+1 ��6����Ͽ����ϴ�.");
					P.setInfoInt(p, "����", P.getInfoInt(p, "����") + 1);
					E.castLvup(p);
					
					if (P.getInfoBoolean(p, "���� ���� ����")) {
						StatsRunAPI.PlayerHealth(p);
					} else {
						StatsRunAPI.PlayerHHealth(p);
					}
					
					if (P.getInfoInt(p, "����") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.���ɹ�ġ��")) {
							p.sendMessage("��6���� ������ ��c200 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ��ġ�� ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.���ɹ�ġ��");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.���ɹ�ġ��");
							}
						}
					}
					
					else if (P.getInfoInt(p, "����") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.���ɹ���")) {
							p.sendMessage("��6���� ������ ��c500 ��6�� �����Ͽ� ��d���� ��f- ���� ��ȭ ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.���ɹ���");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.���ɹ���");
							}
						}
					}
				}
			}
		}
		
		if (P.getInfoInt(p, "���ǵ�") < 200) {
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
					p.sendMessage("��6����� ��c���ǵ� ��6���� ȿ���� ��c+1 ��6����Ͽ����ϴ�.");
					P.setInfoInt(p, "���ǵ�", P.getInfoInt(p, "���ǵ�") + 1);
					E.castLvup(p);
					
					if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
						float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					
					if (P.getInfoInt(p, "���ǵ�") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.�����¿�ݺ��ٱ�")) {
							p.sendMessage("��6���ǵ� ������ ��c200 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� �¿� �ݺ��ٱ� ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.�����¿�ݺ��ٱ�");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.�����¿�ݺ��ٱ�");
							}
						}
					}
					
					else if (P.getInfoInt(p, "���ǵ�") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.���ɷ���")) {
							p.sendMessage("��6���ǵ� ������ ��c500 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ���� ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.���ɷ���");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.���ɷ���");
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
					p.sendMessage("��6����� ��c���ǵ� ��6���� ȿ���� ��c+1 ��6����Ͽ����ϴ�.");
					P.setInfoInt(p, "���ǵ�", P.getInfoInt(p, "���ǵ�") + 1);
					E.castLvup(p);
					
					if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
						float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
					
					if (P.getInfoInt(p, "���ǵ�") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.�����¿�ݺ��ٱ�")) {
							p.sendMessage("��6���ǵ� ������ ��c200 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� �¿� �ݺ��ٱ� ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.�����¿�ݺ��ٱ�");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.�����¿�ݺ��ٱ�");
							}
						}
					}
					
					else if (P.getInfoInt(p, "���ǵ�") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.���ɷ���")) {
							p.sendMessage("��6���ǵ� ������ ��c500 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ���� ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.���ɷ���");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.���ɷ���");
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
					p.sendMessage("��6����� ��c�� �ٷ� ��6���� ȿ���� ��c+1 ��6����Ͽ����ϴ�.");
					P.setInfoInt(p, "�� �ٷ�", P.getInfoInt(p, "�� �ٷ�") + 1);
					E.castLvup(p);
					
					if (P.getInfoInt(p, "�� �ٷ�") >= 200) {
						if (!M.permission.has(p, "magicspells.grant.���Ӻ�����ġ")) {
							p.sendMessage("��6�� �ٷ� ������ ��c200 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ���� ��ġ ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.���Ӻ�����ġ");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.���Ӻ�����ġ");
							}
						}
					}
					
					else if (P.getInfoInt(p, "�� �ٷ�") >= 500) {
						if (!M.permission.has(p, "magicspells.grant.������ġ")) {
							p.sendMessage("��6�� �ٷ� ������ ��c500 ��6�� �����Ͽ� ��c��Ƽ�� ��f- ���� ��ġ ��6��ų�� ȹ���ϼ̽��ϴ�.");
							
							for (World world : Bukkit.getWorlds()) {
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.grant.������ġ");
								M.permission.playerAdd(world.getName(), p.getName(), "magicspells.cast.������ġ");
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
		if (P.getInfoBoolean(p, "���� ���� ����")) {
			StatsRunAPI.PlayerHealth(p);
		} else {
			StatsRunAPI.PlayerHHealth(p);
		}
		
		if (P.getInfoBoolean(p, "���ǵ� ���� ����")) {
			float i = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
