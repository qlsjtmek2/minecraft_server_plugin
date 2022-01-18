package me.espoo.pvp;

import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.nisovin.magicspells.events.SpellCastEvent;

import me.confuser.barapi.BarAPI;
import me.espoo.rpg.Method;
import me.espoo.rpg.exp.ExpAPI;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;
import me.espoo.socket.PlayerYml;

public class mainEvent extends JavaPlugin implements Listener {
	static me.espoo.loon.PlayerYml LP;
	static me.espoo.file.PlayerYml Ps;
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onColossuem(PlayerPickupItemEvent e) {
		if (API.isColoVSPlaying(e.getPlayer().getName())) {
     		e.setCancelled(true);
     	}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onColossuem(PlayerDropItemEvent e) {
		if (API.isColoVSPlaying(e.getPlayer().getName())) {
     		e.setCancelled(true);
     	}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onColossuem(EntityExplodeEvent e) {
		Entity en = e.getEntity();
		if (e.isCancelled()) return;
		
		if (en.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
			for (int x = 0; x < e.blockList().size(); x++) {
				if (GuildAPI.inGuildArea(((Block) e.blockList().get(x)).getLocation())) {
					e.blockList().remove(x);
					x--;	
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
    public void onColossuem(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (API.isColoVSPlaying(p.getName())) {
     		e.setCancelled(true);
     	}
	}
	
	@EventHandler
	public void onColossuem(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		ItemStack item = inv.getItem(e.getNewSlot());
		if (item != null && item.getTypeId() != 0 && API.isColoVSPlaying(p.getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onColossuem(SpellCastEvent e) {
		Player p = e.getCaster();
		
		if (p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
			p.sendMessage("��3�̰��� ��ų ��� ���� �����Դϴ�.");
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onColossuem(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
	    Player p = (Player) e.getDamager();
		final int X = (int) p.getLocation().getX();
		final int Y = (int) p.getLocation().getY();
		final int Z = (int) p.getLocation().getZ();
		
		if (e.getEntity() instanceof Player) {
			Player ep = (Player) e.getEntity();
			if (API.getEnemyName(p.getName(), 5) != null && API.getEnemyName(p.getName(), 5).equalsIgnoreCase(ep.getName())) {
				return;
			}
		}
		
		if (p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
			if (X <= 741 || X >= 845) {
				e.setCancelled(true);
				return;
			}
			
			if (Z <= 671 || Z >= 803) {
				e.setCancelled(true);
				return;
			}
			
			if (Y >= 14) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (API.isUserVSPlaying(p.getName())) {
			e.setCancelled(true);
			return;
		}
		
		String party = PartyAPI.getJoinParty(p);
		if (party != null) {
			if (API.isPartyVSPlaying(party)) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			p.teleport(e.getFrom());
			p.setSneaking(false);
			return;
		}
		
		else if (API.isColoVSPlaying(p.getName()) && main.coloType == 1) {
			p.teleport(e.getFrom());
			p.setSneaking(false);
			return;
		}
		
		else if (!API.isColoVSPlaying(p.getName()) && ((int) p.getLocation().getY()) < 13 && p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
			p.chat("/����");
			return;
		}

		String party = PartyAPI.getJoinParty(p);
		if (party != null) {
			if (API.isTwoVSPlaying(party) && main.twoType == 1) {
				p.teleport(e.getFrom());
				p.setSneaking(false);
				return;
			}

			else if (API.isThreeVSPlaying(party) && main.threeType == 1) {
				p.teleport(e.getFrom());
				p.setSneaking(false);
				return;
			}

			else if (API.isFourVSPlaying(party) && main.fourType == 1) {
				p.teleport(e.getFrom());
				p.setSneaking(false);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
		
		else if (API.isColoVSPlaying(p.getName()) && main.coloType == 1) {
			e.setCancelled(true);
			return;
		}

		String party = PartyAPI.getJoinParty(p);
		if (party != null) {
			if (API.isTwoVSPlaying(party) && main.twoType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isThreeVSPlaying(party) && main.threeType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isFourVSPlaying(party) && main.fourType == 1) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
		
		else if (API.isColoVSPlaying(p.getName()) && main.coloType == 1) {
			e.setCancelled(true);
			return;
		}

		String party = PartyAPI.getJoinParty(p);
		if (party != null) {
			if (API.isTwoVSPlaying(party) && main.twoType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isThreeVSPlaying(party) && main.threeType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isFourVSPlaying(party) && main.fourType == 1) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGodEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
		
		else if (API.isColoVSPlaying(p.getName()) && main.coloType == 1) {
			e.setCancelled(true);
			return;
		}

		String party = PartyAPI.getJoinParty(p);
		if (party != null) {
			if (API.isTwoVSPlaying(party) && main.twoType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isThreeVSPlaying(party) && main.threeType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isFourVSPlaying(party) && main.fourType == 1) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSpellCast(SpellCastEvent e) {
		Player p = e.getCaster();
		if (API.isOneVSPlaying(p.getName()) && main.oneType == 1) {
			e.setCancelled(true);
			return;
		}
		
		else if (API.isColoVSPlaying(p.getName()) && main.coloType == 1) {
			e.setCancelled(true);
			return;
		}

		String party = PartyAPI.getJoinParty(p);
		if (party != null) {
			if (API.isTwoVSPlaying(party) && main.twoType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isThreeVSPlaying(party) && main.threeType == 1) {
				e.setCancelled(true);
				return;
			}

			else if (API.isFourVSPlaying(party) && main.fourType == 1) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) return;
	    Player damager = (Player) e.getDamager();
	    if (API.isOneVSPlaying(damager.getName()) && main.oneType == 1 || API.isOneVSPlaying(damager.getName()) && main.oneType == 3) {
	    	e.setCancelled(true);
	    	return;
		}
		
		else if (API.isColoVSPlaying(damager.getName()) && main.coloType == 1 || API.isColoVSPlaying(damager.getName()) && main.coloType == 3) {
			e.setCancelled(true);
			return;
		}

		String party = PartyAPI.getJoinParty(damager);
		if (party != null) {
			if (API.isTwoVSPlaying(party) && main.twoType == 1 || API.isTwoVSPlaying(party) && main.twoType == 3) {
				e.setCancelled(true);
				return;
			}

			else if (API.isThreeVSPlaying(party) && main.threeType == 1 || API.isThreeVSPlaying(party) && main.threeType == 3) {
				e.setCancelled(true);
				return;
			}

			else if (API.isFourVSPlaying(party) && main.fourType == 1 || API.isFourVSPlaying(party) && main.fourType == 3) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (p.getWorld().getName().equalsIgnoreCase("world_pvp") || p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
			p.chat("/spawn");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String party = PartyAPI.getJoinParty(p);
		
		if (party != null) {
			if (API.isPartyVSMatch(party)) {
				API.quitMatchParty(party);
				for (String user : PartyAPI.getUser(party)) {
					Player pl = Method.getOnorOffLine(user);
					if (pl != null) main.BossBarTimer.remove(pl);
					pl.sendMessage("��c" + p.getName() + " ��6���� �����Ͽ��� ��Ī�� �ڵ����� ��c��� ��6�Ǿ����ϴ�.");
					BarAPI.removeBar(pl);
				}
			}
			
			else if (API.isTwoVSPlaying(party)) {
				String emeny = API.getEnemyName(party, 2);
				
				if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
					if (PartyAPI.isParty(party) && PartyAPI.isParty(emeny)) {
						double i = ExpAPI.getExp(p.getName());
						i /= 100;
						if (ExpAPI.getExp(p.getName()) - i <= 0) ExpAPI.setExp(p, 0);
						else ExpAPI.setExp(p, (int) (ExpAPI.getExp(p.getName()) - i));
						API.deletePartyTimerMove(p.getName(), 2);
						API.deletePartyTimerStart(p.getName(), 2);
						API.deletePartyTimerFinish(p.getName(), 2);
						
						if (PartyAPI.getUserInteger(party) <= 1) {
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) {
									pl.sendMessage("");
									pl.sendMessage("��f==========================================================");
									pl.sendMessage("");
									pl.sendMessage("                           ��6��� �÷��̾ ��� �����Ͽ� ����� ����Ǿ����ϴ�!");
									pl.sendMessage("");
									pl.sendMessage("��f==========================================================");
									pl.sendMessage("");

									API.deletePartyTimerStart(user, 2);
									API.deletePartyTimerFinish(user, 2);
									main.Timer.put("2. PvP Finish " + user + "", new BukkitRunnable()
									{
										int num = API.getPvPFinishTime() + 1;
										public void run()
										{
											num--;
											
											if (!main.twoVS) {
												main.twoType = -1;
												main.twoName = null;
												main.twoInt = 0;
												Integer id = main.Timer.remove("2. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											}
											
											if (num <= 0) {
												pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
												API.sendCommand("heal " + user);
												API.sendCommand("spawn " + user);
												API.twoStopAPI(emeny, party);
												
												Integer id = main.Timer.remove("2. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											} else {
												pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
											}
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
								}
							}
						} else {
							for (String user : PartyAPI.getUser(party)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("��c" + p.getName() + " ��6���� ����� ��⿡�� ��c���� ��6�Ͽ����ϴ�.");
							}
							
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("��c" + p.getName() + " ��6���� ����� ��⿡�� ��c���� ��6�Ͽ����ϴ�.");
							}
						}
					}
				}
			}
			
			else if (API.isThreeVSPlaying(party)) {
				String emeny = API.getEnemyName(party, 3);
				
				if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
					if (PartyAPI.isParty(party) && PartyAPI.isParty(emeny)) {
						double i = ExpAPI.getExp(p.getName());
						i /= 100;
						if (ExpAPI.getExp(p.getName()) - i <= 0) ExpAPI.setExp(p, 0);
						else ExpAPI.setExp(p, (int) (ExpAPI.getExp(p.getName()) - i));
						API.deletePartyTimerMove(p.getName(), 3);
						API.deletePartyTimerStart(p.getName(), 3);
						API.deletePartyTimerFinish(p.getName(), 3);
						
						if (PartyAPI.getUserInteger(party) <= 1) {
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) {
									pl.sendMessage("");
									pl.sendMessage("��f==========================================================");
									pl.sendMessage("");
									pl.sendMessage("                           ��6��� �÷��̾ ��� �����Ͽ� ����� ����Ǿ����ϴ�!");
									pl.sendMessage("");
									pl.sendMessage("��f==========================================================");
									pl.sendMessage("");

									API.deletePartyTimerStart(user, 3);
									API.deletePartyTimerFinish(user, 3);
									main.Timer.put("3. PvP Finish " + user + "", new BukkitRunnable()
									{
										int num = API.getPvPFinishTime() + 1;
										public void run()
										{
											num--;
											
											if (!main.threeVS) {
												main.threeType = -1;
												main.threeName = null;
												main.threeInt = 0;
												Integer id = main.Timer.remove("3. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											}
											
											if (num <= 0) {
												pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
												API.sendCommand("heal " + user);
												API.sendCommand("spawn " + user);
												API.twoStopAPI(emeny, party);
												
												Integer id = main.Timer.remove("3. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											} else {
												pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
											}
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
								}
							}
						} else {
							for (String user : PartyAPI.getUser(party)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("��c" + p.getName() + " ��6���� ����� ��⿡�� ��c���� ��6�Ͽ����ϴ�.");
							}
							
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("��c" + p.getName() + " ��6���� ����� ��⿡�� ��c���� ��6�Ͽ����ϴ�.");
							}
						}
					}
				}
			}
			
			else if (API.isFourVSPlaying(party)) {
				String emeny = API.getEnemyName(party, 4);
				
				if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
					if (PartyAPI.isParty(party) && PartyAPI.isParty(emeny)) {
						double i = ExpAPI.getExp(p.getName());
						i /= 100;
						if (ExpAPI.getExp(p.getName()) - i <= 0) ExpAPI.setExp(p, 0);
						else ExpAPI.setExp(p, (int) (ExpAPI.getExp(p.getName()) - i));
						API.deletePartyTimerMove(p.getName(), 4);
						API.deletePartyTimerStart(p.getName(), 4);
						API.deletePartyTimerFinish(p.getName(), 4);
						
						if (PartyAPI.getUserInteger(party) <= 1) {
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) {
									pl.sendMessage("");
									pl.sendMessage("��f==========================================================");
									pl.sendMessage("");
									pl.sendMessage("                           ��6��� �÷��̾ ��� �����Ͽ� ����� ����Ǿ����ϴ�!");
									pl.sendMessage("");
									pl.sendMessage("��f==========================================================");
									pl.sendMessage("");

									API.deletePartyTimerStart(user, 4);
									API.deletePartyTimerFinish(user, 4);
									main.Timer.put("4. PvP Finish " + user + "", new BukkitRunnable()
									{
										int num = API.getPvPFinishTime() + 1;
										public void run()
										{
											num--;
											
											if (!main.fourVS) {
												main.fourType = -1;
												main.fourName = null;
												main.fourInt = 0;
												Integer id = main.Timer.remove("4. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											}
											
											if (num <= 0) {
												pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
												API.sendCommand("heal " + user);
												API.sendCommand("spawn " + user);
												API.twoStopAPI(emeny, party);
												
												Integer id = main.Timer.remove("4. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											} else {
												pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
											}
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
								}
							}
						} else {
							for (String user : PartyAPI.getUser(party)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("��c" + p.getName() + " ��6���� ����� ��⿡�� ��c���� ��6�Ͽ����ϴ�.");
							}
							
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("��c" + p.getName() + " ��6���� ����� ��⿡�� ��c���� ��6�Ͽ����ϴ�.");
							}
						}
					}
				}
			}
		} else {
			if (API.isOneVSMatch(p)) {
				main.oneList.remove(p);
				main.BossBarTimer.remove(p);
			}
			
			else if (API.isColoVSMatch(p)) {
				main.coloList.remove(p);
				main.BossBarTimer.remove(p);
				
				if (main.coloWinnerName.equalsIgnoreCase(p.getName())) {
					main.coloWinnerName = null;
					main.coloBetting = 0;
					Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + " ��6���� ������ �����Ͽ� ���� ����� ��c�ʱ�ȭ ��6�Ǿ����ϴ�.");
				}
			}
			
			else if (API.isOneVSPlaying(p.getName())) {
				String name = API.getEnemyName(p.getName(), 1);
				if (name != null) {
					final Player pl = Method.getOnorOffLine(name);
					pl.sendMessage("��6������� ��� �÷��̾ �����Ͽ� ��c�¸���6�ϼ̽��ϴ�.");
					
					API.deleteUserTimerMove(1);
					API.deleteUserTimerStart(1);
					API.deleteUserTimerFinish(1);
					
					main.Timer.put("1. PvP Finish", new BukkitRunnable()
					{
						int num = API.getPvPFinishTime() + 1;
						public void run()
						{
							num--;
							
							if (!main.oneVS) {
								main.oneType = -1;
								main.oneName = null;
								Integer id = main.Timer.remove("1. PvP Finish");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
							
							if (num <= 0) {
								pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
								main.oneType = -1;
								main.oneVS = false;
								main.oneName = null;
								API.sendCommand("spawn " + pl.getName());
								API.sendCommand("heal " + pl.getName());
								
								Integer id = main.Timer.remove("1. PvP Finish");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
							}
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
				}
			}
			
			else if (API.isColoVSPlaying(p.getName())) {
				String name = API.getEnemyName(p.getName(), 5);
				if (name != null) {
					final Player pl = Method.getOnorOffLine(name);
					
					for (Player pls : Bukkit.getWorld("world_colosseum").getPlayers())
						pls.sendMessage(main.prx + "��6����� ��c" + p.getName() + " ��6���� ��c���� ��6�ϼ̽��ϴ�.");
					
					API.deleteUserTimerMove(5);
					API.deleteUserTimerStart(5);
					API.deleteUserTimerFinish(5);
					API.coloReturnBetting(main.prx + "��6�ݷμ��򿡼� ��c" + p.getName() + " ��6���� �����Ͽ��� ���� ���������̽��ϴ�.");
					p.getInventory().setHelmet(null);
					p.getInventory().setChestplate(null);
					p.getInventory().setLeggings(null);
					p.getInventory().setBoots(null);
					p.setItemInHand(null);
					pl.getInventory().setHelmet(null);
					pl.getInventory().setChestplate(null);
					pl.getInventory().setLeggings(null);
					pl.getInventory().setBoots(null);
					pl.setItemInHand(null);
					
					Timer timer = new Timer();
					Timer timer2 = new Timer();
					Date timeToRun = new Date(System.currentTimeMillis() + 1000);
					timer.schedule(new TimerTask() {
						public void run() {
							GUI.CheckContinueGUI(pl);
							return;
						}
					}, timeToRun);
					
					main.coloCooltimeList.add(p.getName());
					
					timeToRun = new Date(System.currentTimeMillis() + 300000);
					timer2.schedule(new TimerTask() {
						public void run() {
							main.coloCooltimeList.remove(p.getName());
							return;
						}
					}, timeToRun);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		String party = PartyAPI.getJoinParty(p);
		
		if (party != null) {
			if (API.isTwoVSPlaying(party)) {
				String emeny = API.getEnemyName(party, 2);
				Entity kn = p.getKiller();
				
				if (kn instanceof Player) {
					Player kp = (Player) p.getKiller();
					
					if (PartyAPI.isParty(party) && PartyAPI.isParty(emeny)) {
						API.deletePartyTimerStart(p.getName(), 2);
						API.deletePartyTimerFinish(p.getName(), 2);
						
						for (String user : PartyAPI.getUser(party)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("��6����� ��c" + kp.getName() + " ��6���� ��c" + p.getName() + " ��6���� �׿����ϴ�.");
						}
						
						for (String user : PartyAPI.getUser(emeny)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("��6����� ��c" + kp.getName() + " ��6���� ��c" + p.getName() + " ��6���� �׿����ϴ�.");
						}
						
						Timer timer = new Timer();
						Date timeToRun = new Date(System.currentTimeMillis() + 200);
						timer.schedule(new TimerTask() {
							public void run() {
								for (String user : PartyAPI.getUser(party)) {
									Player pl = Bukkit.getPlayerExact(user);
									if (pl != null) {
										if (pl.getWorld().getName().equalsIgnoreCase("world_pvp")) {
											main.twoInt++;
										}
									}
								}
								
								if (main.twoInt == 0) {
									for (String user : PartyAPI.getUser(party)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
											pl.sendMessage("                                              ��6WINNER: ��c" + emeny + " ��Ƽ");
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
										}
									}
									
									for (String user : PartyAPI.getUser(emeny)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
											pl.sendMessage("                                              ��6WINNER: ��c" + emeny + " ��Ƽ");
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											
											API.deletePartyTimerStart(user, 2);
											API.deletePartyTimerFinish(user, 2);
											main.Timer.put("2. PvP Finish " + user + "", new BukkitRunnable()
											{
												int num = API.getPvPFinishTime() + 1;
												public void run()
												{
													num--;
													
													if (!main.twoVS) {
														main.twoType = -1;
														main.twoName = null;
														main.twoInt = 0;
														Integer id = main.Timer.remove("2. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													}
													
													if (num <= 0) {
														pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
														API.sendCommand("heal " + user);
														API.sendCommand("spawn " + user);
														API.twoStopAPI(emeny, party);
														
														Integer id = main.Timer.remove("2. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													} else {
														pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
													}
												}
											}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
										}
									}
								} else {
									main.twoInt = 0;
								} return;
							}
						}, timeToRun);
					}
				}
			}
			
			if (API.isThreeVSPlaying(party)) {
				String emeny = API.getEnemyName(party, 3);
				Entity kn = p.getKiller();
				
				if (kn instanceof Player) {
					Player kp = (Player) p.getKiller();
					
					if (PartyAPI.isParty(party) && PartyAPI.isParty(emeny)) {
						API.deletePartyTimerStart(p.getName(), 3);
						API.deletePartyTimerFinish(p.getName(), 3);
						
						for (String user : PartyAPI.getUser(party)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("��6����� ��c" + kp.getName() + " ��6���� ��c" + p.getName() + " ��6���� �׿����ϴ�.");
						}
						
						for (String user : PartyAPI.getUser(emeny)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("��6����� ��c" + kp.getName() + " ��6���� ��c" + p.getName() + " ��6���� �׿����ϴ�.");
						}
						
						Timer timer = new Timer();
						Date timeToRun = new Date(System.currentTimeMillis() + 200);
						timer.schedule(new TimerTask() {
							public void run() {
								for (String user : PartyAPI.getUser(party)) {
									Player pl = Bukkit.getPlayerExact(user);
									if (pl != null) {
										if (pl.getWorld().getName().equalsIgnoreCase("world_pvp")) {
											main.threeInt++;
										}
									}
								}
								
								if (main.threeInt == 0) {
									for (String user : PartyAPI.getUser(party)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
											pl.sendMessage("                                              ��6WINNER: ��c" + emeny + " ��Ƽ");
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
										}
									}
									
									for (String user : PartyAPI.getUser(emeny)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
											pl.sendMessage("                                              ��6WINNER: ��c" + emeny + " ��Ƽ");
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											
											API.deletePartyTimerStart(user, 3);
											API.deletePartyTimerFinish(user, 3);
											main.Timer.put("3. PvP Finish " + user + "", new BukkitRunnable()
											{
												int num = API.getPvPFinishTime() + 1;
												public void run()
												{
													num--;
													
													if (!main.threeVS) {
														main.threeType = -1;
														main.threeName = null;
														main.threeInt = 0;
														Integer id = main.Timer.remove("3. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													}
													
													if (num <= 0) {
														pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
														API.sendCommand("heal " + user);
														API.sendCommand("spawn " + user);
														API.threeStopAPI(emeny, party);
														
														Integer id = main.Timer.remove("3. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													} else {
														pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
													}
												}
											}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
										}
									}
								} else {
									main.threeInt = 0;
								} return;
							}
						}, timeToRun);
					}
				}
			}
			
			if (API.isFourVSPlaying(party)) {
				String emeny = API.getEnemyName(party, 4);
				Entity kn = p.getKiller();
				
				if (kn instanceof Player) {
					Player kp = (Player) p.getKiller();
					
					if (PartyAPI.isParty(party) && PartyAPI.isParty(emeny)) {
						API.deletePartyTimerStart(p.getName(), 4);
						API.deletePartyTimerFinish(p.getName(), 4);
						
						for (String user : PartyAPI.getUser(party)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("��6����� ��c" + kp.getName() + " ��6���� ��c" + p.getName() + " ��6���� �׿����ϴ�.");
						}
						
						for (String user : PartyAPI.getUser(emeny)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("��6����� ��c" + kp.getName() + " ��6���� ��c" + p.getName() + " ��6���� �׿����ϴ�.");
						}
						
						Timer timer = new Timer();
						Date timeToRun = new Date(System.currentTimeMillis() + 200);
						timer.schedule(new TimerTask() {
							public void run() {
								for (String user : PartyAPI.getUser(party)) {
									Player pl = Bukkit.getPlayerExact(user);
									if (pl != null) {
										if (pl.getWorld().getName().equalsIgnoreCase("world_pvp")) {
											main.fourInt++;
										}
									}
								}
								
								if (main.fourInt == 0) {
									for (String user : PartyAPI.getUser(party)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
											pl.sendMessage("                                              ��6WINNER: ��c" + emeny + " ��Ƽ");
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
										}
									}
									
									for (String user : PartyAPI.getUser(emeny)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
											pl.sendMessage("                                              ��6WINNER: ��c" + emeny + " ��Ƽ");
											pl.sendMessage("");
											pl.sendMessage("��f==========================================================");
											pl.sendMessage("");
											
											API.deletePartyTimerStart(user, 4);
											API.deletePartyTimerFinish(user, 4);
											main.Timer.put("4. PvP Finish " + user + "", new BukkitRunnable()
											{
												int num = API.getPvPFinishTime() + 1;
												public void run()
												{
													num--;
													
													if (!main.fourVS) {
														main.fourType = -1;
														main.fourName = null;
														main.fourInt = 0;
														Integer id = main.Timer.remove("4. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													}
													
													if (num <= 0) {
														pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
														API.sendCommand("heal " + user);
														API.sendCommand("spawn " + user);
														API.fourStopAPI(emeny, party);
														
														Integer id = main.Timer.remove("4. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													} else {
														pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
													}
												}
											}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
										}
									}
								} else {
									main.fourInt = 0;
								} return;
							}
						}, timeToRun);
					}
				}
			}
		} else {
			if (API.isOneVSPlaying(p.getName())) {
				String name = API.getEnemyName(p.getName(), 1);
				if (name != null) {
					final Player pl = Method.getOnorOffLine(name);
					API.deleteUserTimerStart(1);
					API.deleteUserTimerFinish(1);
					
					pl.sendMessage("��6����� ��c" + p.getName() + " ��6���� ��c" + name + " ��6�Կ��� ����ϼ̽��ϴ�.");
					pl.sendMessage("");
					pl.sendMessage("��f==========================================================");
					pl.sendMessage("");
					pl.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
					pl.sendMessage("");
					pl.sendMessage("��f==========================================================");
					pl.sendMessage("");
					
					p.sendMessage("��6����� ��c" + p.getName() + " ��6���� ��c" + name + " ��6�Կ��� ����ϼ̽��ϴ�.");
					p.sendMessage("");
					p.sendMessage("��f==========================================================");
					p.sendMessage("");
					p.sendMessage("                                              ��6����� ����Ǿ����ϴ�!");
					p.sendMessage("");
					p.sendMessage("��f==========================================================");
					p.sendMessage("");
					
					main.Timer.put("1. PvP Finish", new BukkitRunnable()
					{
						int num = API.getPvPFinishTime() + 1;
						public void run()
						{
							num--;
							
							if (!main.oneVS) {
								main.oneType = -1;
								main.oneName = null;
								Integer id = main.Timer.remove("1. PvP Finish");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
							
							if (num <= 0) {
								pl.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
								main.oneType = -1;
								main.oneVS = false;
								main.oneName = null;
								pl.chat("/spawn");
								API.sendCommand("heal " + pl.getName());
								
								Integer id = main.Timer.remove("1. PvP Finish");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								pl.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
							}
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
					return;
				}
			}
			
			else if (API.isColoVSPlaying(p.getName())) {
				String name = API.getEnemyName(p.getName(), 5);
				if (name != null) {
					final Player pl = Method.getOnorOffLine(name);
					API.deleteUserTimerStart(5);
					API.deleteUserTimerFinish(5);
					
					for (Player pls : Bukkit.getWorld("world_colosseum").getPlayers())
						pls.sendMessage(main.prx + "��6����� ��c" + p.getName() + " ��6���� ��c" + name + " ��6�Կ��� ����ϼ̽��ϴ�.");
					
					int result = (int) API.coloBettingMoney(name);
					API.coloWinBetting(name, main.prx + "��6�ݷμ��򿡼� ��c" + name + " ��6���� �¸��Ͽ��� ��c" + main.coloBetting + "(+" + result + ") ��6���� ȹ���ϼ̽��ϴ�.", 
					"��6�ݷμ��򿡼� ��c" + name + " ��6���� �¸��Ͽ��� ��c" + main.coloBetting + " ��6���� �����̽��ϴ�.");
					
					p.getInventory().setHelmet(null);
					p.getInventory().setChestplate(null);
					p.getInventory().setLeggings(null);
					p.getInventory().setBoots(null);
					p.setItemInHand(null);
					pl.getInventory().setHelmet(null);
					pl.getInventory().setChestplate(null);
					pl.getInventory().setLeggings(null);
					pl.getInventory().setBoots(null);
					pl.setItemInHand(null);
					
					Timer timer = new Timer();
					Timer timer2 = new Timer();
					Date timeToRun = new Date(System.currentTimeMillis() + 1000);
					timer.schedule(new TimerTask() {
						public void run() {
							GUI.CheckContinueGUI(pl);
							return;
						}
					}, timeToRun);
					
					main.coloCooltimeList.add(p.getName());
					
					timeToRun = new Date(System.currentTimeMillis() + 300000);
					timer2.schedule(new TimerTask() {
						public void run() {
							main.coloCooltimeList.remove(p.getName());
							return;
						}
					}, timeToRun);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� 2:2 ����� �������ڰ� �մϴ�.") ||
			e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� 3:3 ����� �������ڰ� �մϴ�.") ||
			e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� 4:4 ����� �������ڰ� �մϴ�.")) {
			String party = PartyAPI.getJoinParty(p);
			
			if (p.getName().equalsIgnoreCase(PartyAPI.getManager(party))) return;
			if (main.PartyCheck.get(party) != null) {
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				p.sendMessage("��c�κ��丮 â�� �ݾ� �ڵ����� ��� ��Ī�� �ź��Ͽ����ϴ�.");
				main.PartyCheck.remove(party);
				
				for (String user : PartyAPI.getUser(party)) {
					if (user.equalsIgnoreCase(p.getName())) continue;
					
					Player pl = Method.getOnorOffLine(user);
					pl.sendMessage("��c" + p.getName() + " ���� ��Ī�� �ź��Ͽ� ����� �������� �ʽ��ϴ�.");
					pl.closeInventory();
					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				}
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("����� ����Ǿ����ϴ�. �̾ �����Ͻðڽ��ϱ�?")) {
			if (main.Timer.get("5. PvP Finish") == null && !main.coloList.contains(p)) {
				Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + " ��6���� �ݷμ��� �̾ �������� �ʾ� ����� ��c���� ��6�Ǿ����ϴ�.");
				main.Timer.put("5. PvP Finish", new BukkitRunnable()
				{
					int num = API.getPvPFinishTime() + 1;
					public void run()
					{
						num--;
						
						if (!main.coloVS) {
							main.coloType = -1;
							main.coloName = null;
							Integer id = main.Timer.remove("5. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
						
						if (num <= 0) {
							p.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
							main.coloType = -1;
							main.coloVS = false;
							main.coloName = null;
							main.coloBetting = 0;
	    					main.coloWinnerName = null;
							API.coloStopAPI(p);
							p.chat("/spawn");
							API.sendCommand("heal " + p.getName());
							p.getInventory().setHelmet(null);
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.setItemInHand(null);
							
							Integer id = main.Timer.remove("5. PvP Finish");
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						} else {
							p.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
						}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
			}
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("����� ����Ǿ����ϴ�. �̾ �����Ͻðڽ��ϱ�?")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, �ݷμ����� �̾ �ϰڽ��ϴ�.")) {
    					if (main.coloWinnerName == null) {
    						main.coloWinnerName = p.getName();
        					main.coloBetting += 500;
        					Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + " ��6���� �ݷμ��� ��⸦ �̾ �����ϰ� �ֽ��ϴ�. ä��â�� ��e[ /��� ] ��6�Է� �� �������� ��c���� ��6�غ�����!");
        					Bukkit.broadcastMessage(main.prx + "��6���� ���� ���� �ݾ�: ��f" + main.coloBetting + "��");
        					main.coloType = -1;
    						main.coloVS = false;
    						main.coloName = null;
        					main.coloList.add(0, p);
    						API.coloStopAPI(p);
        					p.closeInventory();
    					} else {
    						if (main.coloWinnerName.equalsIgnoreCase(p.getName())) {
    							main.coloBetting += 500;
            					Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + " ��6���� �ݷμ��� ��⸦ �̾ �����ϰ� �ֽ��ϴ�. ä��â�� ��e[ /��� ] ��6�Է� �� �������� ��c���� ��6�غ�����!");
            					Bukkit.broadcastMessage(main.prx + "��6���� ���� ���� �ݾ�: ��f" + main.coloBetting + "��");
            					main.coloType = -1;
        						main.coloVS = false;
        						main.coloName = null;
            					main.coloList.add(0, p);
        						API.coloStopAPI(p);
            					p.closeInventory();
    						} else {
    							main.coloBetting = 1000;
            					Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + " ��6���� ��c" + main.coloWinnerName + " ��6���� ���� �ݷμ��� ��⸦ �̾ �����ϰ� �ֽ��ϴ�. ä��â�� ��e[ /��� ] ��6�Է� �� �������� ��c���� ��6�غ�����!");
        						main.coloWinnerName = p.getName();
            					Bukkit.broadcastMessage(main.prx + "��6���� ���� ���� �ݾ�: ��f" + main.coloBetting + "��");
            					main.coloType = -1;
        						main.coloVS = false;
        						main.coloName = null;
            					main.coloList.add(0, p);
        						API.coloStopAPI(p);
            					p.closeInventory();
    						}
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, �ݷμ����� �̾ ���� �ʰڽ��ϴ�.")) {
    					Bukkit.broadcastMessage(main.prx + "��c" + p.getName() + " ��6���� �ݷμ��� �̾ �������� �ʾ� ����� ��c���� ��6�Ǿ����ϴ�.");
    					main.Timer.put("5. PvP Finish", new BukkitRunnable()
    					{
    						int num = API.getPvPFinishTime() + 1;
    						public void run()
    						{
    							num--;
    							
    							if (!main.coloVS) {
    								main.coloType = -1;
    								main.coloName = null;
    								Integer id = main.Timer.remove("5. PvP Finish");
    								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
    								return;
    							}
    							
    							if (num <= 0) {
    								p.sendMessage("��6�������� �̵��Ǿ����ϴ�.");
    								main.coloType = -1;
    								main.coloVS = false;
    								main.coloName = null;
    								main.coloBetting = 0;
    		    					main.coloWinnerName = null;
    								API.coloStopAPI(p);
									p.chat("/spawn");
    								API.sendCommand("heal " + p.getName());
    								p.getInventory().setHelmet(null);
    								p.getInventory().setChestplate(null);
    								p.getInventory().setLeggings(null);
    								p.getInventory().setBoots(null);
    								p.setItemInHand(null);
    								
    								Integer id = main.Timer.remove("5. PvP Finish");
    								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
    								return;
    							} else {
    								p.sendMessage("��c" + num + " ��6�� �� �������� �̵��մϴ�.");
    							}
    						}
    					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
    					p.closeInventory();
    					return;
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� 2:2 ����� �������ڰ� �մϴ�.")) {
     		e.setCancelled(true);
     		
     		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, ��ÿ� �����ϰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						main.twoList.add(party);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("��6��ΰ� ��� ��Ī�� �����Ͽ� ��Ī�� ��c�����6�մϴ�.");
            					pl.sendMessage("��6��ġ�� ������ ��c������ �ڷ���Ʈ ��6�˴ϴ�. ��а� �ƹ� �۾��� �������� �����ֽñ� �ٶ��ϴ�.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("��6���������� ��� ��Ī�� ��c������6�ϼ̽��ϴ�.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, ��ÿ� �������� �ʰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("��c" + p.getName() + " ���� ��Ī�� �ź��Ͽ� ����� �������� �ʽ��ϴ�.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("��c��� ��Ī�� �ź��Ͽ����ϴ�.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� 3:3 ����� �������ڰ� �մϴ�.")) {
     		e.setCancelled(true);
     		
     		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, ��ÿ� �����ϰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						main.threeList.add(party);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("��6��ΰ� ��� ��Ī�� �����Ͽ� ��Ī�� ��c�����6�մϴ�.");
            					pl.sendMessage("��6��ġ�� ������ ��c������ �ڷ���Ʈ ��6�˴ϴ�. ��а� �ƹ� �۾��� �������� �����ֽñ� �ٶ��ϴ�.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("��6���������� ��� ��Ī�� ��c������6�ϼ̽��ϴ�.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, ��ÿ� �������� �ʰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("��c" + p.getName() + " ���� ��Ī�� �ź��Ͽ� ����� �������� �ʽ��ϴ�.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("��c��� ��Ī�� �ź��Ͽ����ϴ�.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ ���� 4:4 ����� �������ڰ� �մϴ�.")) {
     		e.setCancelled(true);
     		
     		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, ��ÿ� �����ϰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						main.fourList.add(party);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("��6��ΰ� ��� ��Ī�� �����Ͽ� ��Ī�� ��c�����6�մϴ�.");
            					pl.sendMessage("��6��ġ�� ������ ��c������ �ڷ���Ʈ ��6�˴ϴ�. ��а� �ƹ� �۾��� �������� �����ֽñ� �ٶ��ϴ�.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("��6���������� ��� ��Ī�� ��c������6�ϼ̽��ϴ�.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, ��ÿ� �������� �ʰڽ��ϴ�.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("��c" + p.getName() + " ���� ��Ī�� �ź��Ͽ� ����� �������� �ʽ��ϴ�.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("��c��� ��Ī�� �ź��Ͽ����ϴ�.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��� �ϱ�")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c1:1 ��6��� �ϱ�")) {
    					if (PartyAPI.getJoinParty(p) != null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ�� �����Ƿ� 1:1 ����� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!p.getWorld().getName().equalsIgnoreCase("world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c���������� 1:1 ��� ��Ī�� �����Ͻ� �� �ֽ��ϴ�. ��e[ /����, /spawn ]");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��cƩ�丮���� ��ġ�� ���� ����� ��ÿ� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					main.oneList.add(p);
    					p.sendMessage("��6��ġ�� ������ ��c������ �ڷ���Ʈ ��6�˴ϴ�. ��а� �ƹ� �۾��� �������� �����ֽñ� �ٶ��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b�ݷμ��� ��6��� �ϱ�")) {
    					if (PartyAPI.getJoinParty(p) != null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ�� �����Ƿ� �ݷμ��� ����� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��cƩ�丮���� ��ġ�� ���� ����� ��ÿ� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!p.getWorld().getName().equalsIgnoreCase("world_spawn") && !p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�����̳� �ݷμ����忡���� �ݷμ��� ��� ��Ī�� �����Ͻ� �� �ֽ��ϴ�. ��e[ /����, /spawn ]");
    						return;
    					}
    					
    					if (main.coloCooltimeList.contains(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�ݷμ��� ������ �� 5���� �����߸� �ٽ� �����Ͻ� �� �ֽ��ϴ�.");
    						return;
    					}
    					
    					if (p.getItemInHand().getType() != Material.AIR) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�տ� �ƹ� �����۵� ������� �ʾƾ� ������ �����մϴ�.");
    						return;
    					}
    					
    					if (p.getInventory().getHelmet() != null || p.getInventory().getChestplate() != null 
    					 || p.getInventory().getLeggings() != null || p.getInventory().getBoots() != null) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�԰� �ִ� ��� ��� ����� ������ �����մϴ�.");
    						return;
    					}
    					
    					if (PlayerYml.getInfoInt(p, "1.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "1.������ �̸�") != null && !PlayerYml.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��cù��° ������ ������ �ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /���� ]");
    						return;
    					}
    					
    					if (PlayerYml.getInfoInt(p, "2.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "2.������ �̸�") != null && !PlayerYml.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�ι�° ������ ������ �ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /���� ]");
    						return;
    					}
    					
    					if (PlayerYml.getInfoInt(p, "3.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "3.������ �̸�") != null && !PlayerYml.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����° ������ ������ �ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /���� ]");
    						return;
    					}
    					
    					if (LP.getInfoInt(p, "��.������ �ڵ�") != 0 && LP.getInfoString(p, "��.������ �̸�") != null && !LP.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�����Ͻ� ���� ������ �ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /�� ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "�� �ٷ� ���� ����")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�� �ٷ� ������ ��Ȱ��ȭ ���ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /�ɼ� ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "���� ���� ����")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c���� ������ ��Ȱ��ȭ ���ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /�ɼ� ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "�ٸ� �ٷ� ���� ����")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�ٸ� �ٷ� ������ ��Ȱ��ȭ ���ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /�ɼ� ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "���ǵ� ���� ����")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c���ǵ� ������ ��Ȱ��ȭ ���ֽ� �� �ݷμ��� ������ �ֽñ� �ٶ��ϴ�. [ /�ɼ� ]");
    						return;
    					}
    					
    					main.coloList.add(p);
    					p.sendMessage("��6��ġ�� ������ ��c������ �ڷ���Ʈ ��6�˴ϴ�. ��а� �ƹ� �۾��� �������� �����ֽñ� �ٶ��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c2:2 ��6��� �ϱ�")) {
    					String party = PartyAPI.getJoinParty(p);
    					if (party == null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ�� �����Ƿ� 2:2 ����� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��cƩ�丮���� ��ġ�� ���� ����� ��ÿ� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ���� �ƴϹǷ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (PartyAPI.getUserInteger(party) != 2) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c��Ƽ�� ���� �������� �ʾ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!API.isPartyUserInWorld(party, "world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c��Ƽ���� ������ ���� �ʴ� ����� �����Ƿ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					for (String user : PartyAPI.getUser(party)) {
    						Player pl = Method.getOnorOffLine(user);
    						GUI.CheckPvPGUI(pl, 2);
    					}
    					
    					if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					main.PartyCheck.put(party, 1);
    					p.sendMessage("��6��Ƽ���鿡�� ��c��� ���� ���Ρ�6�� ����ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c3:3 ��6��� �ϱ�")) {
    					String party = PartyAPI.getJoinParty(p);
    					if (party == null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ�� �����Ƿ� 3:3 ����� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��cƩ�丮���� ��ġ�� ���� ����� ��ÿ� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ���� �ƴϹǷ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (PartyAPI.getUserInteger(party) != 3) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c��Ƽ�� ���� �������� �ʾ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!API.isPartyUserInWorld(party, "world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c��Ƽ���� ������ ���� �ʴ� ����� �����Ƿ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					for (String user : PartyAPI.getUser(party)) {
    						Player pl = Method.getOnorOffLine(user);
    						GUI.CheckPvPGUI(pl, 3);
    					}
    					
    					if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					main.PartyCheck.put(party, 1);
    					p.sendMessage("��6��Ƽ���鿡�� ��c��� ���� ���Ρ�6�� ����ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c4:4 ��6��� �ϱ�")) {
    					String party = PartyAPI.getJoinParty(p);
    					if (party == null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ�� �����Ƿ� 4:4 ����� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��cƩ�丮���� ��ġ�� ���� ����� ��ÿ� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c����� ��Ƽ���� �ƴϹǷ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (PartyAPI.getUserInteger(party) != 4) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c��Ƽ�� ���� �������� �ʾ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					if (!API.isPartyUserInWorld(party, "world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c��Ƽ���� ������ ���� �ʴ� ����� �����Ƿ� ��Ī�� �����Ͻ� �� �����ϴ�.");
    						return;
    					}
    					
    					for (String user : PartyAPI.getUser(party)) {
    						Player pl = Method.getOnorOffLine(user);
    						GUI.CheckPvPGUI(pl, 4);
    					}
    					
    					if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					main.PartyCheck.put(party, 1);
    					p.sendMessage("��6��Ƽ���鿡�� ��c��� ���� ���Ρ�6�� ����ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ī ���")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c��Ī ���")) {
    					if (API.isOneVSMatch(p)) {
    						main.oneList.remove(p);
    						main.BossBarTimer.remove(p);
    						p.sendMessage("��61:1 ��� ��Ī�� ��c��� ��6�Ǿ����ϴ�.");
    						p.closeInventory();
    						BarAPI.removeBar(p);
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (API.isColoVSMatch(p)) {
    						main.coloList.remove(p);
    						main.BossBarTimer.remove(p);
    						p.sendMessage("��6�ݷμ��� ��� ��Ī�� ��c��� ��6�Ǿ����ϴ�.");
    						p.closeInventory();
    						BarAPI.removeBar(p);
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					String party = PartyAPI.getJoinParty(p);
    					if (party != null) {
    						if (API.isPartyVSMatch(party)) {
    							API.quitMatchParty(party);
        						p.closeInventory();
    							for (String user : PartyAPI.getUser(party)) {
    								Player pl = Method.getOnorOffLine(user);
    								if (pl != null) main.BossBarTimer.remove(pl);
    								pl.sendMessage("��c" + p.getName() + " ��6���� ��Ī�� ��c�ߴ� ��6���׽��ϴ�.");
    								BarAPI.removeBar(pl);
    							}
    						}
    					}
    				}
    			}
    		}
     	}
	}
}
