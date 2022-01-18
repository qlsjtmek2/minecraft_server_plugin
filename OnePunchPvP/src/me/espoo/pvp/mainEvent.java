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
			p.sendMessage("§3이곳은 스킬 사용 제한 구역입니다.");
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
			p.chat("/관전");
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
					pl.sendMessage("§c" + p.getName() + " §6님이 퇴장하여서 매칭이 자동으로 §c취소 §6되었습니다.");
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
									pl.sendMessage("§f==========================================================");
									pl.sendMessage("");
									pl.sendMessage("                           §6상대 플레이어가 모두 퇴장하여 대련이 종료되었습니다!");
									pl.sendMessage("");
									pl.sendMessage("§f==========================================================");
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
												pl.sendMessage("§6스폰으로 이동되었습니다.");
												API.sendCommand("heal " + user);
												API.sendCommand("spawn " + user);
												API.twoStopAPI(emeny, party);
												
												Integer id = main.Timer.remove("2. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											} else {
												pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
											}
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
								}
							}
						} else {
							for (String user : PartyAPI.getUser(party)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("§c" + p.getName() + " §6님이 대련중 경기에서 §c퇴장 §6하였습니다.");
							}
							
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("§c" + p.getName() + " §6님이 대련중 경기에서 §c퇴장 §6하였습니다.");
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
									pl.sendMessage("§f==========================================================");
									pl.sendMessage("");
									pl.sendMessage("                           §6상대 플레이어가 모두 퇴장하여 대련이 종료되었습니다!");
									pl.sendMessage("");
									pl.sendMessage("§f==========================================================");
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
												pl.sendMessage("§6스폰으로 이동되었습니다.");
												API.sendCommand("heal " + user);
												API.sendCommand("spawn " + user);
												API.twoStopAPI(emeny, party);
												
												Integer id = main.Timer.remove("3. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											} else {
												pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
											}
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
								}
							}
						} else {
							for (String user : PartyAPI.getUser(party)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("§c" + p.getName() + " §6님이 대련중 경기에서 §c퇴장 §6하였습니다.");
							}
							
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("§c" + p.getName() + " §6님이 대련중 경기에서 §c퇴장 §6하였습니다.");
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
									pl.sendMessage("§f==========================================================");
									pl.sendMessage("");
									pl.sendMessage("                           §6상대 플레이어가 모두 퇴장하여 대련이 종료되었습니다!");
									pl.sendMessage("");
									pl.sendMessage("§f==========================================================");
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
												pl.sendMessage("§6스폰으로 이동되었습니다.");
												API.sendCommand("heal " + user);
												API.sendCommand("spawn " + user);
												API.twoStopAPI(emeny, party);
												
												Integer id = main.Timer.remove("4. PvP Finish " + user + "");
												if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
												return;
											} else {
												pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
											}
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
								}
							}
						} else {
							for (String user : PartyAPI.getUser(party)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("§c" + p.getName() + " §6님이 대련중 경기에서 §c퇴장 §6하였습니다.");
							}
							
							for (String user : PartyAPI.getUser(emeny)) {
								Player pl = Bukkit.getPlayerExact(user);
								if (pl != null) pl.sendMessage("§c" + p.getName() + " §6님이 대련중 경기에서 §c퇴장 §6하였습니다.");
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
					Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + " §6님이 연승중 퇴장하여 연승 기록이 §c초기화 §6되었습니다.");
				}
			}
			
			else if (API.isOneVSPlaying(p.getName())) {
				String name = API.getEnemyName(p.getName(), 1);
				if (name != null) {
					final Player pl = Method.getOnorOffLine(name);
					pl.sendMessage("§6대련중인 상대 플레이어가 퇴장하여 §c승리§6하셨습니다.");
					
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
								pl.sendMessage("§6스폰으로 이동되었습니다.");
								main.oneType = -1;
								main.oneVS = false;
								main.oneName = null;
								API.sendCommand("spawn " + pl.getName());
								API.sendCommand("heal " + pl.getName());
								
								Integer id = main.Timer.remove("1. PvP Finish");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
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
						pls.sendMessage(main.prx + "§6대련중 §c" + p.getName() + " §6님이 §c퇴장 §6하셨습니다.");
					
					API.deleteUserTimerMove(5);
					API.deleteUserTimerStart(5);
					API.deleteUserTimerFinish(5);
					API.coloReturnBetting(main.prx + "§6콜로세움에서 §c" + p.getName() + " §6님이 퇴장하여서 돈을 돌려받으셨습니다.");
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
							if (pl != null) pl.sendMessage("§6대련중 §c" + kp.getName() + " §6님이 §c" + p.getName() + " §6님을 죽였습니다.");
						}
						
						for (String user : PartyAPI.getUser(emeny)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("§6대련중 §c" + kp.getName() + " §6님이 §c" + p.getName() + " §6님을 죽였습니다.");
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
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              §6대련이 종료되었습니다!");
											pl.sendMessage("                                              §6WINNER: §c" + emeny + " 파티");
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
										}
									}
									
									for (String user : PartyAPI.getUser(emeny)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              §6대련이 종료되었습니다!");
											pl.sendMessage("                                              §6WINNER: §c" + emeny + " 파티");
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
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
														pl.sendMessage("§6스폰으로 이동되었습니다.");
														API.sendCommand("heal " + user);
														API.sendCommand("spawn " + user);
														API.twoStopAPI(emeny, party);
														
														Integer id = main.Timer.remove("2. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													} else {
														pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
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
							if (pl != null) pl.sendMessage("§6대련중 §c" + kp.getName() + " §6님이 §c" + p.getName() + " §6님을 죽였습니다.");
						}
						
						for (String user : PartyAPI.getUser(emeny)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("§6대련중 §c" + kp.getName() + " §6님이 §c" + p.getName() + " §6님을 죽였습니다.");
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
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              §6대련이 종료되었습니다!");
											pl.sendMessage("                                              §6WINNER: §c" + emeny + " 파티");
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
										}
									}
									
									for (String user : PartyAPI.getUser(emeny)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              §6대련이 종료되었습니다!");
											pl.sendMessage("                                              §6WINNER: §c" + emeny + " 파티");
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
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
														pl.sendMessage("§6스폰으로 이동되었습니다.");
														API.sendCommand("heal " + user);
														API.sendCommand("spawn " + user);
														API.threeStopAPI(emeny, party);
														
														Integer id = main.Timer.remove("3. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													} else {
														pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
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
							if (pl != null) pl.sendMessage("§6대련중 §c" + kp.getName() + " §6님이 §c" + p.getName() + " §6님을 죽였습니다.");
						}
						
						for (String user : PartyAPI.getUser(emeny)) {
							Player pl = Bukkit.getPlayerExact(user);
							if (pl != null) pl.sendMessage("§6대련중 §c" + kp.getName() + " §6님이 §c" + p.getName() + " §6님을 죽였습니다.");
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
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              §6대련이 종료되었습니다!");
											pl.sendMessage("                                              §6WINNER: §c" + emeny + " 파티");
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
										}
									}
									
									for (String user : PartyAPI.getUser(emeny)) {
										Player pl = Bukkit.getPlayerExact(user);
										if (pl != null) {
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
											pl.sendMessage("");
											pl.sendMessage("                                              §6대련이 종료되었습니다!");
											pl.sendMessage("                                              §6WINNER: §c" + emeny + " 파티");
											pl.sendMessage("");
											pl.sendMessage("§f==========================================================");
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
														pl.sendMessage("§6스폰으로 이동되었습니다.");
														API.sendCommand("heal " + user);
														API.sendCommand("spawn " + user);
														API.fourStopAPI(emeny, party);
														
														Integer id = main.Timer.remove("4. PvP Finish " + user + "");
														if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
														return;
													} else {
														pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
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
					
					pl.sendMessage("§6대련중 §c" + p.getName() + " §6님이 §c" + name + " §6님에게 사망하셨습니다.");
					pl.sendMessage("");
					pl.sendMessage("§f==========================================================");
					pl.sendMessage("");
					pl.sendMessage("                                              §6대련이 종료되었습니다!");
					pl.sendMessage("");
					pl.sendMessage("§f==========================================================");
					pl.sendMessage("");
					
					p.sendMessage("§6대련중 §c" + p.getName() + " §6님이 §c" + name + " §6님에게 사망하셨습니다.");
					p.sendMessage("");
					p.sendMessage("§f==========================================================");
					p.sendMessage("");
					p.sendMessage("                                              §6대련이 종료되었습니다!");
					p.sendMessage("");
					p.sendMessage("§f==========================================================");
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
								pl.sendMessage("§6스폰으로 이동되었습니다.");
								main.oneType = -1;
								main.oneVS = false;
								main.oneName = null;
								pl.chat("/spawn");
								API.sendCommand("heal " + pl.getName());
								
								Integer id = main.Timer.remove("1. PvP Finish");
								if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							} else {
								pl.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
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
						pls.sendMessage(main.prx + "§6대련중 §c" + p.getName() + " §6님이 §c" + name + " §6님에게 사망하셨습니다.");
					
					int result = (int) API.coloBettingMoney(name);
					API.coloWinBetting(name, main.prx + "§6콜로세움에서 §c" + name + " §6님이 승리하여서 §c" + main.coloBetting + "(+" + result + ") §6원을 획득하셨습니다.", 
					"§6콜로세움에서 §c" + name + " §6님이 승리하여서 §c" + main.coloBetting + " §6원을 잃으셨습니다.");
					
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
		
		if (e.getInventory().getName().equalsIgnoreCase("파티 장이 2:2 대련을 진행하자고 합니다.") ||
			e.getInventory().getName().equalsIgnoreCase("파티 장이 3:3 대련을 진행하자고 합니다.") ||
			e.getInventory().getName().equalsIgnoreCase("파티 장이 4:4 대련을 진행하자고 합니다.")) {
			String party = PartyAPI.getJoinParty(p);
			
			if (p.getName().equalsIgnoreCase(PartyAPI.getManager(party))) return;
			if (main.PartyCheck.get(party) != null) {
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				p.sendMessage("§c인벤토리 창을 닫아 자동으로 대련 매칭을 거부하였습니다.");
				main.PartyCheck.remove(party);
				
				for (String user : PartyAPI.getUser(party)) {
					if (user.equalsIgnoreCase(p.getName())) continue;
					
					Player pl = Method.getOnorOffLine(user);
					pl.sendMessage("§c" + p.getName() + " 님이 매칭을 거부하여 대련을 진행하지 않습니다.");
					pl.closeInventory();
					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				}
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("대련이 종료되었습니다. 이어서 참가하시겠습니까?")) {
			if (main.Timer.get("5. PvP Finish") == null && !main.coloList.contains(p)) {
				Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + " §6님이 콜로세움에 이어서 참가하지 않아 대련이 §c종료 §6되었습니다.");
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
							p.sendMessage("§6스폰으로 이동되었습니다.");
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
							p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
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
     	if (e.getInventory().getName().equalsIgnoreCase("대련이 종료되었습니다. 이어서 참가하시겠습니까?")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 콜로세움을 이어서 하겠습니다.")) {
    					if (main.coloWinnerName == null) {
    						main.coloWinnerName = p.getName();
        					main.coloBetting += 500;
        					Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + " §6님이 콜로세움 경기를 이어서 참가하고 있습니다. 채팅창에 §e[ /대련 ] §6입력 후 언제든지 §c참가 §6해보세요!");
        					Bukkit.broadcastMessage(main.prx + "§6현재 배팅 누적 금액: §f" + main.coloBetting + "원");
        					main.coloType = -1;
    						main.coloVS = false;
    						main.coloName = null;
        					main.coloList.add(0, p);
    						API.coloStopAPI(p);
        					p.closeInventory();
    					} else {
    						if (main.coloWinnerName.equalsIgnoreCase(p.getName())) {
    							main.coloBetting += 500;
            					Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + " §6님이 콜로세움 경기를 이어서 참가하고 있습니다. 채팅창에 §e[ /대련 ] §6입력 후 언제든지 §c참가 §6해보세요!");
            					Bukkit.broadcastMessage(main.prx + "§6현재 배팅 누적 금액: §f" + main.coloBetting + "원");
            					main.coloType = -1;
        						main.coloVS = false;
        						main.coloName = null;
            					main.coloList.add(0, p);
        						API.coloStopAPI(p);
            					p.closeInventory();
    						} else {
    							main.coloBetting = 1000;
            					Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + " §6님이 §c" + main.coloWinnerName + " §6님을 꺾고 콜로세움 경기를 이어서 참가하고 있습니다. 채팅창에 §e[ /대련 ] §6입력 후 언제든지 §c참가 §6해보세요!");
        						main.coloWinnerName = p.getName();
            					Bukkit.broadcastMessage(main.prx + "§6현재 배팅 누적 금액: §f" + main.coloBetting + "원");
            					main.coloType = -1;
        						main.coloVS = false;
        						main.coloName = null;
            					main.coloList.add(0, p);
        						API.coloStopAPI(p);
            					p.closeInventory();
    						}
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 콜로세움을 이어서 하지 않겠습니다.")) {
    					Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + " §6님이 콜로세움에 이어서 참가하지 않아 대련이 §c종료 §6되었습니다.");
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
    								p.sendMessage("§6스폰으로 이동되었습니다.");
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
    								p.sendMessage("§c" + num + " §6초 뒤 스폰으로 이동합니다.");
    							}
    						}
    					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchPvP"), 20L, 20L).getTaskId());
    					p.closeInventory();
    					return;
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("파티 장이 2:2 대련을 진행하자고 합니다.")) {
     		e.setCancelled(true);
     		
     		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 대련에 참가하겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						main.twoList.add(party);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("§6모두가 대련 매칭을 수락하여 매칭을 §c진행§6합니다.");
            					pl.sendMessage("§6매치가 잡히면 §c강제로 텔레포트 §6됩니다. 당분간 아무 작업을 수행하지 말아주시기 바랍니다.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("§6성공적으로 대련 매칭을 §c수락§6하셨습니다.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 대련에 참가하지 않겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("§c" + p.getName() + " 님이 매칭을 거부하여 대련을 진행하지 않습니다.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("§c대련 매칭을 거부하였습니다.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("파티 장이 3:3 대련을 진행하자고 합니다.")) {
     		e.setCancelled(true);
     		
     		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 대련에 참가하겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						main.threeList.add(party);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("§6모두가 대련 매칭을 수락하여 매칭을 §c진행§6합니다.");
            					pl.sendMessage("§6매치가 잡히면 §c강제로 텔레포트 §6됩니다. 당분간 아무 작업을 수행하지 말아주시기 바랍니다.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("§6성공적으로 대련 매칭을 §c수락§6하셨습니다.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 대련에 참가하지 않겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("§c" + p.getName() + " 님이 매칭을 거부하여 대련을 진행하지 않습니다.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("§c대련 매칭을 거부하였습니다.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("파티 장이 4:4 대련을 진행하자고 합니다.")) {
     		e.setCancelled(true);
     		
     		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 대련에 참가하겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
    					int num = main.PartyCheck.get(party) + 1;
    					main.PartyCheck.put(party, num);
    					
    					if (num == PartyAPI.getUserInteger(party)) {
    						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    						main.fourList.add(party);
    						
    						for (String user : PartyAPI.getUser(party)) {
        						Player pl = Method.getOnorOffLine(user);
        						pl.closeInventory();
        						pl.sendMessage("§6모두가 대련 매칭을 수락하여 매칭을 §c진행§6합니다.");
            					pl.sendMessage("§6매치가 잡히면 §c강제로 텔레포트 §6됩니다. 당분간 아무 작업을 수행하지 말아주시기 바랍니다.");
            					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					}
    					} else {
    						p.sendMessage("§6성공적으로 대련 매칭을 §c수락§6하셨습니다.");
    						p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 대련에 참가하지 않겠습니다.")) {
    					String party = PartyAPI.getJoinParty(p);
        				if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					
    					for (String user : PartyAPI.getUser(party)) {
    						if (user.equalsIgnoreCase(p.getName())) continue;
    						
    						Player pl = Method.getOnorOffLine(user);
    						pl.closeInventory();
    						pl.sendMessage("§c" + p.getName() + " 님이 매칭을 거부하여 대련을 진행하지 않습니다.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    					
        				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				p.sendMessage("§c대련 매칭을 거부하였습니다.");
        				p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("대련 하기")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c1:1 §6대련 하기")) {
    					if (PartyAPI.getJoinParty(p) != null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티가 있으므로 1:1 대련을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (!p.getWorld().getName().equalsIgnoreCase("world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c스폰에서만 1:1 대련 매칭을 진행하실 수 있습니다. §e[ /스폰, /spawn ]");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c튜토리얼을 마치지 못한 사람은 대련에 참가하실 수 없습니다.");
    						return;
    					}
    					
    					main.oneList.add(p);
    					p.sendMessage("§6매치가 잡히면 §c강제로 텔레포트 §6됩니다. 당분간 아무 작업을 수행하지 말아주시기 바랍니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b콜로세움 §6대련 하기")) {
    					if (PartyAPI.getJoinParty(p) != null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티가 있으므로 콜로세움 대련을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c튜토리얼을 마치지 못한 사람은 대련에 참가하실 수 없습니다.");
    						return;
    					}
    					
    					if (!p.getWorld().getName().equalsIgnoreCase("world_spawn") && !p.getWorld().getName().equalsIgnoreCase("world_colosseum")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c스폰이나 콜로세움장에서만 콜로세움 대련 매칭을 진행하실 수 있습니다. §e[ /스폰, /spawn ]");
    						return;
    					}
    					
    					if (main.coloCooltimeList.contains(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c콜로세움에 참가한 후 5분이 지나야만 다시 참가하실 수 있습니다.");
    						return;
    					}
    					
    					if (p.getItemInHand().getType() != Material.AIR) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c손에 아무 아이템도 들고있지 않아야 참가가 가능합니다.");
    						return;
    					}
    					
    					if (p.getInventory().getHelmet() != null || p.getInventory().getChestplate() != null 
    					 || p.getInventory().getLeggings() != null || p.getInventory().getBoots() != null) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c입고 있는 장비를 모두 벗어야 참가가 가능합니다.");
    						return;
    					}
    					
    					if (PlayerYml.getInfoInt(p, "1.아이템 코드") != 0 && PlayerYml.getInfoString(p, "1.아이템 이름") != null && !PlayerYml.getInfoList(p, "1.아이템 설명").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c첫번째 소켓을 해제해 주신 후 콜로세움에 참가해 주시기 바랍니다. [ /소켓 ]");
    						return;
    					}
    					
    					if (PlayerYml.getInfoInt(p, "2.아이템 코드") != 0 && PlayerYml.getInfoString(p, "2.아이템 이름") != null && !PlayerYml.getInfoList(p, "2.아이템 설명").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c두번째 소켓을 해제해 주신 후 콜로세움에 참가해 주시기 바랍니다. [ /소켓 ]");
    						return;
    					}
    					
    					if (PlayerYml.getInfoInt(p, "3.아이템 코드") != 0 && PlayerYml.getInfoString(p, "3.아이템 이름") != null && !PlayerYml.getInfoList(p, "3.아이템 설명").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c세번째 소켓을 해제해 주신 후 콜로세움에 참가해 주시기 바랍니다. [ /소켓 ]");
    						return;
    					}
    					
    					if (LP.getInfoInt(p, "룬.아이템 코드") != 0 && LP.getInfoString(p, "룬.아이템 이름") != null && !LP.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c착용하신 룬을 해제해 주신 후 콜로세움에 참가해 주시기 바랍니다. [ /룬 ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "팔 근력 스텟 적용")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c팔 근력 스텟을 비활성화 해주신 후 콜로세움에 참가해 주시기 바랍니다. [ /옵션 ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "복근 스텟 적용")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c복근 스텟을 비활성화 해주신 후 콜로세움에 참가해 주시기 바랍니다. [ /옵션 ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "다리 근력 스텟 적용")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c다리 근력 스텟을 비활성화 해주신 후 콜로세움에 참가해 주시기 바랍니다. [ /옵션 ]");
    						return;
    					}
    					
    					if (Ps.getInfoBoolean(p, "스피드 스텟 적용")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c스피드 스텟을 비활성화 해주신 후 콜로세움에 참가해 주시기 바랍니다. [ /옵션 ]");
    						return;
    					}
    					
    					main.coloList.add(p);
    					p.sendMessage("§6매치가 잡히면 §c강제로 텔레포트 §6됩니다. 당분간 아무 작업을 수행하지 말아주시기 바랍니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c2:2 §6대련 하기")) {
    					String party = PartyAPI.getJoinParty(p);
    					if (party == null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티가 없으므로 2:2 대련을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c튜토리얼을 마치지 못한 사람은 대련에 참가하실 수 없습니다.");
    						return;
    					}
    					
    					if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티장이 아니므로 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (PartyAPI.getUserInteger(party) != 2) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c파티원 수가 충족되지 않아 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (!API.isPartyUserInWorld(party, "world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c파티원중 스폰에 있지 않는 사람이 있으므로 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					for (String user : PartyAPI.getUser(party)) {
    						Player pl = Method.getOnorOffLine(user);
    						GUI.CheckPvPGUI(pl, 2);
    					}
    					
    					if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					main.PartyCheck.put(party, 1);
    					p.sendMessage("§6파티원들에게 §c대련 참가 여부§6를 물어봅니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c3:3 §6대련 하기")) {
    					String party = PartyAPI.getJoinParty(p);
    					if (party == null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티가 없으므로 3:3 대련을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c튜토리얼을 마치지 못한 사람은 대련에 참가하실 수 없습니다.");
    						return;
    					}
    					
    					if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티장이 아니므로 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (PartyAPI.getUserInteger(party) != 3) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c파티원 수가 충족되지 않아 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (!API.isPartyUserInWorld(party, "world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c파티원중 스폰에 있지 않는 사람이 있으므로 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					for (String user : PartyAPI.getUser(party)) {
    						Player pl = Method.getOnorOffLine(user);
    						GUI.CheckPvPGUI(pl, 3);
    					}
    					
    					if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					main.PartyCheck.put(party, 1);
    					p.sendMessage("§6파티원들에게 §c대련 참가 여부§6를 물어봅니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c4:4 §6대련 하기")) {
    					String party = PartyAPI.getJoinParty(p);
    					if (party == null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티가 없으므로 4:4 대련을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (main.permission.has(p, "coloredtags.bold_MHG") && !p.isOp()) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c튜토리얼을 마치지 못한 사람은 대련에 참가하실 수 없습니다.");
    						return;
    					}
    					
    					if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c당신은 파티장이 아니므로 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (PartyAPI.getUserInteger(party) != 4) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c파티원 수가 충족되지 않아 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					if (!API.isPartyUserInWorld(party, "world_spawn")) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c파티원중 스폰에 있지 않는 사람이 있으므로 매칭을 진행하실 수 없습니다.");
    						return;
    					}
    					
    					for (String user : PartyAPI.getUser(party)) {
    						Player pl = Method.getOnorOffLine(user);
    						GUI.CheckPvPGUI(pl, 4);
    					}
    					
    					if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
    					main.PartyCheck.put(party, 1);
    					p.sendMessage("§6파티원들에게 §c대련 참가 여부§6를 물어봅니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("매칭 취소")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c매칭 취소")) {
    					if (API.isOneVSMatch(p)) {
    						main.oneList.remove(p);
    						main.BossBarTimer.remove(p);
    						p.sendMessage("§61:1 대련 매칭이 §c취소 §6되었습니다.");
    						p.closeInventory();
    						BarAPI.removeBar(p);
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (API.isColoVSMatch(p)) {
    						main.coloList.remove(p);
    						main.BossBarTimer.remove(p);
    						p.sendMessage("§6콜로세움 대련 매칭이 §c취소 §6되었습니다.");
    						p.closeInventory();
    						BarAPI.removeBar(p);
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					String party = PartyAPI.getJoinParty(p);
    					if (party != null) {
    						if (API.isPartyVSMatch(party)) {
    							API.quitMatchParty(party);
        						p.closeInventory();
    							for (String user : PartyAPI.getUser(party)) {
    								Player pl = Method.getOnorOffLine(user);
    								if (pl != null) main.BossBarTimer.remove(pl);
    								pl.sendMessage("§c" + p.getName() + " §6님이 매칭을 §c중단 §6시켰습니다.");
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
