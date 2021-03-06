package me.espoo.rpg.guild;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.magicspells.events.SpellCastEvent;

import me.espoo.rpg.Method;
import me.espoo.rpg.main;
import me.espoo.rpg.party.PartyAPI;

@SuppressWarnings("deprecation")
public class GuildEvent extends JavaPlugin implements Listener {
	main M;
	
	public GuildEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		if (main.CreateGuild.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityExplode(EntityExplodeEvent e)
	{
		Entity en = e.getEntity();
		if (e.isCancelled()) return;
		
		if (en.getWorld().getName().equalsIgnoreCase("world_guild")) {
			for (int x = 0; x < e.blockList().size(); x++) {
				if (GuildAPI.inGuildArea(((Block) e.blockList().get(x)).getLocation())) {
					e.blockList().remove(x);
					x--;	
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSpellCast(SpellCastEvent e)
	{
		Player p = e.getCaster();
		
		if (p.getWorld().getName().equalsIgnoreCase("world_guild")) {
			if (GuildAPI.getJoinGuild(p) == null) {
				e.setCancelled(false); return;
			}
			
			String guild = GuildAPI.getJoinGuild(p);
			
			if (!GuildAPI.getGuildLand(guild)) {
				e.setCancelled(false); return;
			}
			
			String rank = GuildAPI.getUserClass(guild, p.getName());
			int accept = GuildAPI.getLandAcceptBlock(guild);
			boolean is = false;
			
			String name = GuildAPI.getGuildArea(p.getLocation());
			if (name != null) {
				if (guild != null) {
					if (GuildAPI.isWar(name)) {
						if (!GuildAPI.getWarGuild(name).equalsIgnoreCase(guild)) {
							e.setCancelled(true);
							p.sendMessage("??3?????? ???? ???? ???? ???? ??????.");
							return;
						}
					}
				}
			}
			
			switch (accept) {
				case 1:
					if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 2:
					if (!rank.equalsIgnoreCase("????")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 3:
					if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 4:
					if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 5:
					if (rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
			} e.setCancelled(is);
			
			if (is) p.sendMessage("??3?????? ???? ???? ???? ???? ??????.");
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		
		if (!p.isOp()) {
			if (p.getWorld().getName().equalsIgnoreCase("world_guild")) {
				if (GuildAPI.getJoinGuild(p) == null) {
					e.setCancelled(true); return;
				}
				
				String guild = GuildAPI.getJoinGuild(p);
					
				if (!GuildAPI.getGuildLand(guild)) {
					e.setCancelled(true); return;
				}
				
				String rank = GuildAPI.getUserClass(guild, p.getName());
				int accept = GuildAPI.getLandAcceptBlock(guild);
				boolean is = true;
				
				String name = GuildAPI.getGuildArea(e.getBlock().getLocation());
				if (name != null) {
					if (guild != null) {
						if (GuildAPI.isWar(name)) {
							if (!GuildAPI.getWarGuild(name).equalsIgnoreCase(guild)) {
								e.setCancelled(false); return;
							}
						}
					}
				}
				
				switch (accept) {
					case 1:
						if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 2:
						if (!rank.equalsIgnoreCase("????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
				} e.setCancelled(is);
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if (!p.isOp()) {
			if (p.getWorld().getName().equalsIgnoreCase("world_guild")) {
				if (GuildAPI.getJoinGuild(p) == null) {
					e.setCancelled(true); return;
				}
					
				String guild = GuildAPI.getJoinGuild(p);
					
				if (!GuildAPI.getGuildLand(guild)) {
					e.setCancelled(true); return;
				}
				
				String rank = GuildAPI.getUserClass(guild, p.getName());
				int accept = GuildAPI.getLandAcceptBlock(guild);
				boolean is = true;
				
				String name = GuildAPI.getGuildArea(e.getBlock().getLocation());
				if (name != null) {
					if (guild != null) {
						if (GuildAPI.isWar(name)) {
							if (!GuildAPI.getWarGuild(name).equalsIgnoreCase(guild)) {
								e.setCancelled(false); return;
							}
						}
					}
				}
				
				switch (accept) {
					case 1:
						if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 2:
						if (!rank.equalsIgnoreCase("????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
				} e.setCancelled(is);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public static void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
	    int hand = p.getItemInHand().getTypeId();
	    
	    if (hand == 270) {
	    	if (p.getWorld().getName().equalsIgnoreCase("world_guild")) {
	    		if (GuildAPI.getJoinGuild(p) != null) {
	    			String guild = GuildAPI.getJoinGuild(p);
		    		if (GuildAPI.getManager(guild).equalsIgnoreCase(p.getName())) {
		    			if (!GuildAPI.getGuildLand(guild)) {
		    				if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
		    					StringBuilder st = new StringBuilder();
		    					Location l = e.getClickedBlock().getLocation();
		    					st.append(l.getBlockX()); st.append(","); st.append(l.getBlockY()); st.append(","); st.append(l.getBlockZ());
		    					if (main.pos1.get(p) == null) {
			    					main.pos1.put(p, st.toString());
			    					p.sendMessage("??6???? ??c???? ??6???? ?????? ???? ??e[ " + st.toString() + " ]");
			    					e.setCancelled(true);
			    					
			    					if (main.pos1.get(p) != null && main.pos2.get(p) != null) {
				    					p.sendMessage("??f- ??6?????? ???? ??????????????. '/???? -> ???? ?????? ???? ????'");
			    					}
		    					} else {
		    						if (!main.pos1.get(p).equalsIgnoreCase(st.toString())) {
		    							main.pos1.remove(p);
		    							main.pos1.put(p, st.toString());
				    					p.sendMessage("??6???? ??c???? ??6???? ?????? ???? ??e[ " + st.toString() + " ]");
				    					e.setCancelled(true);
				    					
				    					if (main.pos1.get(p) != null && main.pos2.get(p) != null) {
					    					p.sendMessage("??f- ??6?????? ???? ??????????????. '/???? -> ???? ?????? ???? ????'");
				    					}
		    						} else {
		    							e.setCancelled(true);
		    						}
		    					}
		    				}
		    				
		    				else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		    					StringBuilder st = new StringBuilder();
		    					Location l = e.getClickedBlock().getLocation();
		    					st.append(l.getBlockX()); st.append(","); st.append(l.getBlockY()); st.append(","); st.append(l.getBlockZ());
		    					if (main.pos2.get(p) == null) {
			    					main.pos2.put(p, st.toString());
			    					p.sendMessage("??6???? ??c???? ??6???? ?????? ???? ??e[ " + st.toString() + " ]");
			    					e.setCancelled(true);
			    					
			    					if (main.pos2.get(p) != null && main.pos2.get(p) != null) {
				    					p.sendMessage("??f- ??6?????? ???? ??????????????. '/???? -> ???? ?????? ???? ????'");
			    					}
		    					} else {
		    						if (!main.pos2.get(p).equalsIgnoreCase(st.toString())) {
		    							main.pos2.remove(p);
		    							main.pos2.put(p, st.toString());
				    					p.sendMessage("??6???? ??c???? ??6???? ?????? ???? ??e[ " + st.toString() + " ]");
				    					e.setCancelled(true);
				    					
				    					if (main.pos2.get(p) != null && main.pos2.get(p) != null) {
					    					p.sendMessage("??f- ??6?????? ???? ??????????????. '/???? -> ???? ?????? ???? ????'");
				    					}
		    						} else {
		    							e.setCancelled(true);
		    						}
		    					}
		    				}
		    			}
		    		}
		    	}
			}
	    }
	    
	    
	    if (!p.isOp()) {
		    if (p.getWorld().getName().equalsIgnoreCase("world_guild")) {
		    	if (GuildAPI.getJoinGuild(p) == null) {
					e.setCancelled(true); return;
				}
				
				String guild = GuildAPI.getJoinGuild(p);
					
				if (!GuildAPI.getGuildLand(guild)) {
					e.setCancelled(true); return;
				}
				
				if (e.getClickedBlock().getType() != Material.ANVIL && e.getClickedBlock().getType() != Material.BEACON && e.getClickedBlock().getType() != Material.CHEST && 
					e.getClickedBlock().getType() != Material.CAKE_BLOCK && e.getClickedBlock().getType() != Material.ENCHANTMENT_TABLE && e.getClickedBlock().getType() != Material.ENDER_CHEST && 
					e.getClickedBlock().getType() != Material.FURNACE && e.getClickedBlock().getType() != Material.BURNING_FURNACE && e.getClickedBlock().getType() != Material.JUKEBOX && 
					e.getClickedBlock().getType() != Material.NOTE_BLOCK && e.getClickedBlock().getType() != Material.TRAPPED_CHEST && e.getClickedBlock().getType() != Material.LEVER && 
					e.getClickedBlock().getType() != Material.STONE_BUTTON && e.getClickedBlock().getType() != Material.WOOD_BUTTON && e.getClickedBlock().getType() != Material.TRAP_DOOR && 
					e.getClickedBlock().getType() != Material.FENCE_GATE && e.getClickedBlock().getType() != Material.ENDER_PORTAL && e.getClickedBlock().getType() != Material.IRON_DOOR && 
					e.getClickedBlock().getType() != Material.WOODEN_DOOR && e.getClickedBlock().getType() != Material.WOOD_DOOR && e.getClickedBlock().getType() != Material.HOPPER && 
					e.getClickedBlock().getType() != Material.HOPPER_MINECART && e.getClickedBlock().getType() != Material.DROPPER && e.getClickedBlock().getType() != Material.DISPENSER && 
					e.getClickedBlock().getType() != Material.CAULDRON && e.getClickedBlock().getType() != Material.BREWING_STAND) return;
				
				String rank = GuildAPI.getUserClass(guild, p.getName());
				int accept = GuildAPI.getLandAcceptUse(guild);
				boolean is = true;
				
				String name = GuildAPI.getGuildArea(e.getClickedBlock().getLocation());
				if (name != null) {
					if (!GuildAPI.getWarGuild(name).equalsIgnoreCase(guild)) {
						e.setCancelled(false); return;
					}
				}
				
				switch (accept) {
					case 1:
						if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 2:
						if (!rank.equalsIgnoreCase("????")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("??????")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
				} e.setCancelled(is);
		    }
	    }
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String guild = GuildAPI.getJoinGuild(p);
		
		if (guild != null) {
			if (main.SetItemCode.get(p) != null) main.SetItemCode.remove(p);
			if (main.SetLore.get(p) != null) main.SetLore.remove(p);
			if (main.SetStatMessage.get(p) != null) main.SetStatMessage.remove(p);
			if (main.InvitePlayer.contains(p)) main.InvitePlayer.remove(p);
			if (main.CreateGuild.contains(p)) main.CreateGuild.remove(p);
			
			if (GuildAPI.getManager(guild).equalsIgnoreCase(p.getName())) {
				Calendar cal = Calendar.getInstance();
				StringBuilder str = new StringBuilder();
				str.append(cal.get(Calendar.MONTH) + 1);
				str.append(",");
				str.append(cal.get(Calendar.DATE));
				GuildAPI.setJoinMaster(p, str.toString());
			}
		}
	}
	
	@SuppressWarnings("resource")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.CreateGuild.contains(p)) {
			String guild = e.getMessage();
			
			if (guild.equalsIgnoreCase("????")) {
				e.setCancelled(true);
				p.sendMessage("??6?????????? ???? ?????? ??c???? ??6??????????.");
				main.CreateGuild.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (guild.matches("[??-????-????-?Ra-zA-Z0-9_]+")) {
				if (Method.GuildNameAgree(guild)) {
					if (!GuildAPI.isGuild(guild)) {
						e.setCancelled(true);
						GuildGUI.CheckCreateGUI(p);
						main.CreateGuild.remove(p);
						main.GuildName.put(p.getName(), guild);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
					} else {
						e.setCancelled(true);
						p.sendMessage("??c???? ???? ?????? ???? ?????? ????????. ???? ?????? ????????");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					}
				} else {
					e.setCancelled(true);
					p.sendMessage("??c?????? ???????? ?????? ???????????? ???? ?????? 2????, 6???????? ??????.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				}
			} else {
				e.setCancelled(true);
				p.sendMessage("??c?????? ???????? ?????? ???????? ????????. ???? ?????? ????????.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			}
		}
		
		else if (main.InvitePlayer.contains(p)) {
			String name = e.getMessage();
			
			if (GuildAPI.getJoinGuild(p) != null) {
				String guild = GuildAPI.getJoinGuild(p);
				if (GuildAPI.isWar(guild)) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
					main.InvitePlayer.remove(p);
					e.setCancelled(true);
					return;
				}
			}
			
			if (name.equalsIgnoreCase("????")) {
				e.setCancelled(true);
				p.sendMessage("??6?????????? ???????? ?????? ??c???? ??6??????????.");
				main.InvitePlayer.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Method.searchOnlinePlayer(name) == null) {
				e.setCancelled(true);
				p.sendMessage("??c?? ?????????? ???????? ????????. ???? ?????? ????????");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			name = Method.searchOnlinePlayer(name);
			
			if (Method.getOnorOffLine(name) == null) {
				e.setCancelled(true);
				p.sendMessage("??c?? ?????????? ???????? ????????. ???? ?????? ????????");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			Player pl = Method.getOnorOffLine(name);
			
			if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "???? ????")) {
				e.setCancelled(true);
				p.sendMessage("??c?? ?????????? ???? ???? ???? ??????????. ???? ?????? ????????");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (GuildAPI.getJoinGuild(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("??c?? ?????????? ???? ?????? ????????. ???? ?????? ????????");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (main.OkInvite.get(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("??c?? ?????????? ???? ???? ?????? ???? ??????????. ???? ?????? ????????");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			GuildGUI.InviteGUI(pl, GuildAPI.getJoinGuild(p));
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			p.sendMessage("??6?????????? ??c" + name + " ??6???? ??????????????.");
			main.OkInvite.put(pl, p);
			main.InvitePlayer.remove(p);
			e.setCancelled(true);
		}
		
		else if (main.SetStatMessage.containsKey(p)) {
			String StatMessage = e.getMessage();
			
			if (StatMessage.equalsIgnoreCase("????")) {
				e.setCancelled(true);
				p.sendMessage("??6?????????? ???? ?????? ?????? ??c???? ??6??????????.");
				main.SetStatMessage.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			GuildAPI.setInfoMessage(GuildAPI.getJoinGuild(p), StatMessage);
			e.setCancelled(true);
			p.sendMessage("??6?????????? ???? ???????? ??????????????. ??e[ /???? ]");
			main.SetStatMessage.remove(p);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		}
		
		else if (main.SetLore.containsKey(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("????")) {
				e.setCancelled(true);
				p.sendMessage("??6?????????? ???? ????/???? ?????? ??c???? ??6??????????.");
				main.SetLore.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (lore.contains(" ")) {
				if (lore.split(" ")[0].equals("+")) {
					String name = GuildAPI.getJoinGuild(p);
					String message = Method.getFinalArg(lore.split(" "), 1);
					message = Method.replaceAllColors(message);
					List<String> list = GuildAPI.getInfoLore(name);
					list.add(message);
					GuildAPI.setInfoLore(name, list);

					e.setCancelled(true);
					p.sendMessage("");
					p.sendMessage("??6?????????? ?????? ??c???? ??6??????????.");
					p.sendMessage("??6?????? ????: ??f" + message);
					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					String name = GuildAPI.getJoinGuild(p);
					Scanner scan = new Scanner(lore.split(" ")[1]);
					if (!scan.hasNextInt())
					{
						p.sendMessage("");
						p.sendMessage("??6?????? ??c??????6???????? ??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
						p.sendMessage("??6<?????? ????> ???????? ?????? ?????? ?? ????????.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					int removeline = Integer.parseInt(lore.split(" ")[1]) - 1;
					
					if (removeline < 0) {
						p.sendMessage("");
						p.sendMessage("??6?????? ??c??????6???????? ??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
						p.sendMessage("??6<?????? ????> ???????? ?????? ?????? ?? ????????.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					List<String> list = GuildAPI.getInfoLore(name);
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						GuildAPI.setInfoLore(name, list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("??6?????????? ??c" + removeline + " ??6???? ?????? ??????????????.");
						p.sendMessage("??6?????? ????: ??f" + str);
						p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return;
					} else {
						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("??c???? ?????? ???????? ???? ??????????. ???? ?????? ??????.");
						p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				}
				
				else {
					p.sendMessage("");
					p.sendMessage("??6?????? ??c??????6???????? ??e'+ <????>'??6, ?????? ??c??????6????????");
					p.sendMessage("??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
					p.sendMessage("??f* ??c\"????\" ???? ?????? ?????? ??????????.");
					p.sendMessage("??f");
					p.sendMessage("??7????) ??f'+ ??????????. OO ??????????.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("??6?????? ??c??????6???????? ??e'+ <????>'??6, ?????? ??c??????6????????");
				p.sendMessage("??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
				p.sendMessage("??f* ??c\"????\" ???? ?????? ?????? ??????????.");
				p.sendMessage("??f");
				p.sendMessage("??7????) ??f'+ ??????????. OO ??????????.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
		
		else if (GuildAPI.getPlayerChat(p).equalsIgnoreCase("????")) {
			e.setCancelled(true);
			for (Player pl : GuildAPI.getOnLineUser(GuildAPI.getJoinGuild(p))) {
				if (pl.isOp()) continue;
				if (p.isOp()) pl.sendMessage("??f[??6??????????f] ??f" + p.getName() + " ??7:: ??e" + e.getMessage());
				else pl.sendMessage("??f[??6??????????f] ??r" + p.getDisplayName() + " ??7:: ??e" + e.getMessage());
			}
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.isOp()) {
					if (p.isOp()) pl.sendMessage("??f[??6??????????f] ??f" + p.getName() + " ??7:: ??e" + e.getMessage());
					else pl.sendMessage("??f[??6??????????f] ??r" + p.getDisplayName() + " ??7:: ??e" + e.getMessage());
				}
			}
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "????????" + ChatColor.WHITE + "] " + ChatColor.RESET + p.getDisplayName() + " " + ChatColor.GRAY + ":: " + ChatColor.YELLOW + e.getMessage());
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("?????? ????????????????. ?????????????????")) {
			if (main.OkInvite.get(p) != null) {
				p.sendMessage("??c?????????? ???? ???? ?????? ???????? ??????????????.");
				main.OkInvite.get(p).sendMessage("??c" + p.getName() + "???? ???? ?????? ??????????????.");
				main.OkInvite.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("?????? ?????? ?????????????????")) {
			if (main.CreateGuild.contains(p)) {
				p.sendMessage("??c?????????? ???? ???????? ???? ?????? ??????????????.");
				main.GuildName.remove(p.getName());
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("?????? ???? ?????? ?????????????????")) {
			if (main.LandMoney.get(p) != null) main.LandMoney.remove(p);
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("?????? ?????? ?????? ?????? ?????? ????????.")) {
			if (main.SetItemCode.get(p) != null) main.SetItemCode.remove(p);
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("?????? ??????????!")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ????")) {
    					if (main.economy.getBalance(p.getName()) >= Double.parseDouble(GuildAPI.getCreateMoney())) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
        					p.closeInventory();
        					main.CreateGuild.add(p);
        					p.sendMessage("??6???????? ?????? ??c???? ??????6?? ?????????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = Double.parseDouble(GuildAPI.getCreateMoney()) - main.economy.getBalance(p.getName());
        					p.sendMessage("??c" + num + " ???? ???????? ?????? ???????? ?? ????????.");
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ????")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					GuildGUI.RankingGUI(p, 1);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("???????? ????")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ?????? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c???? ???? ???? ???????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("??6?????? ???? ???????? ??c????????6?? ?????? ?????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c???? ???? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("??6?????? ??c??????6???????? ??e'+ <????>'??6, ?????? ??c??????6????????");
    						p.sendMessage("??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
        					p.sendMessage("??f* ??c\"????\" ???? ?????? ?????? ??????????.");
    						p.sendMessage("??f");
    						p.sendMessage("??7????) ??f'+ ??????????. OO ??????????.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ?????? ???? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c???? ???? ?????? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, name);
    						GuildGUI.SetItemCodeGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("???? ????")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ?????? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c???? ???? ???? ???????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("??6?????? ???? ???????? ??c????????6?? ?????? ?????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c???? ???? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("??6?????? ??c??????6???????? ??e'+ <????>'??6, ?????? ??c??????6????????");
    						p.sendMessage("??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
        					p.sendMessage("??f* ??c\"????\" ???? ?????? ?????? ??????????.");
    						p.sendMessage("??f");
    						p.sendMessage("??7????) ??f'+ ??????????. OO ??????????.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ?????? ???? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c???? ???? ?????? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, name);
    						GuildGUI.SetItemCodeGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??3???? ????????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					String name = GuildAPI.getJoinGuild(p);
	    					if (GuildAPI.getJoinType(name).equalsIgnoreCase("????")) GuildAPI.setJoinType(name, 1);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("??????")) GuildAPI.setJoinType(name, 2);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("??????")) GuildAPI.setJoinType(name, 0);
	    					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??9???? ?????? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ????")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ?????? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c???? ???? ???? ???????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("??6?????? ???? ???????? ??c????????6?? ?????? ?????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c???? ???? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("??6?????? ??c??????6???????? ??e'+ <????>'??6, ?????? ??c??????6????????");
    						p.sendMessage("??e'- <?????? ????>' ??6?? ?????? ?????? ????????.");
        					p.sendMessage("??f* ??c\"????\" ???? ?????? ?????? ??????????.");
    						p.sendMessage("??f");
    						p.sendMessage("??7????) ??f'+ ??????????. OO ??????????.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ?????? ???? ??e??l????")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("??c???? ???? ?????? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, name);
    						GuildGUI.SetItemCodeGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??3???? ????????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					String name = GuildAPI.getJoinGuild(p);
	    					if (GuildAPI.getJoinType(name).equalsIgnoreCase("????")) GuildAPI.setJoinType(name, 1);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("??????")) GuildAPI.setJoinType(name, 2);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("??????")) GuildAPI.setJoinType(name, 0);
	    					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??9???? ?????? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e?????? ?????? ??6??l????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					GuildAPI.setExpStat(GuildAPI.getJoinGuild(p));
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a?????? ???? ?????? ??6??l????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					GuildAPI.setScoreStat(GuildAPI.getJoinGuild(p));
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??b???? ??6??l????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					GuildAPI.setLuckStat(GuildAPI.getJoinGuild(p));
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ?????????? ????")) {
	    					String guild = GuildAPI.getJoinGuild(p);
	    					int i = GuildAPI.getUpGrade(guild); i += 1;
	    					int money = Integer.parseInt(GuildConfig.getGuildList("???? ??????????").get(i).split(" ")[1]);
	    					
	    					if (GuildConfig.getGuildList("???? ??????????").size() >= (i + 2)) {
		    					if (main.economy.getBalance(p.getName()) >= money) {
			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
		        					p.closeInventory();
		        					p.sendMessage("??6?????????? ?????? ??c" + i + " ??6?????? ?????????? ??????????.");
		        					GuildAPI.setUpGrade(guild, i);
		    						main.economy.withdrawPlayer(p.getName(), (double) money);
		    					} else {
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		        					double num = (double) money - main.economy.getBalance(p.getName());
		        					p.closeInventory();
		        					p.sendMessage("??c" + num + " ???? ???????? ???? ???????????? ???????? ?? ????????.");
		    					}
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("???? ????")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("???? ???? ????")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e?????? ?????? ??6??l????")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getExpStat(name) < GuildAPI.getMaxExpStat(name)) {
    						if (GuildAPI.isSubPoint(name, 12000)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							GuildAPI.subPoint(name, 12000);
    							GuildAPI.addExpStat(name, 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("??c???????? ???????? ?????? ?????? ?????? ?????? ?? ????????.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a?????? ???? ?????? ??6??l????")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getScoreStat(name) < GuildAPI.getMaxScoreStat(name)) {
    						if (GuildAPI.isSubPoint(name, 12000)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							GuildAPI.subPoint(name, 12000);
    							GuildAPI.addScoreStat(name, 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("??c???????? ?????????????? ???? ?????? ?????? ?????? ?? ????????.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??b???? ??6??l????")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getLuckStat(name) < GuildAPI.getMaxLuckStat(name)) {
    						if (GuildAPI.isSubPoint(name, 16000)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							GuildAPI.subPoint(name, 16000);
    							GuildAPI.addLuckStat(name, 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("??c???????? ???????? ???? ?????? ?????? ?? ????????.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ?????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
    					if (main.economy.getBalance(p.getName()) >= Double.parseDouble(GuildAPI.getCreateMoney())) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
        					String guild = main.GuildName.get(p.getName());
        					main.GuildName.remove(p.getName());
        					p.closeInventory();
        					p.sendMessage("??6?????????? ??c" + guild + " ??6?????? ??????????????.");
        					GuildAPI.setJoinGuild(p, guild);
        					GuildYml.addGuild(guild, p.getName());
    						main.economy.withdrawPlayer(p.getName(), Double.parseDouble(GuildAPI.getCreateMoney()));
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = Double.parseDouble(GuildAPI.getCreateMoney()) - main.economy.getBalance(p.getName());
        					p.sendMessage("??c" + num + " ???? ???????? ?????? ???????? ?? ????????.");
        					p.closeInventory();
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					p.sendMessage("??6?????????? ???? ?????? ??c???? ??6??????????.");
    					main.GuildName.remove(p.getName());
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("???? ???? ???? ??")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (main.ClickAccept.get(p) != null) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e???? ??f???? ????")) {
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a?????? ??f???? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 2) <= GuildAPI.getClassAmount(name, "??????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ?????? ?????? ???? ???? ?????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c?????? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "??????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??d???????? ??f???? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 3) <= GuildAPI.getClassAmount(name, "????????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???????? ?????? ???? ???? ???????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???????? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "????????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ???? ???? ??")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (main.ClickAccept.get(p) != null) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e???? ??f???? ????")) {
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a?????? ??f???? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 2) <= GuildAPI.getClassAmount(name, "??????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ?????? ?????? ???? ???? ?????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c?????? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "??????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??d???????? ??f???? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 3) <= GuildAPI.getClassAmount(name, "????????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???????? ?????? ???? ???? ???????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???????? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "????????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??c???? ??f???? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 3) <= GuildAPI.getClassAmount(name, "????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???? ?????? ???? ???? ???? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???? ??6?? ??????????????.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ?????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String guild = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(guild)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					String name = GuildAPI.getJoinGuild(p);
						GuildAPI.subUser(name, p);
						p.sendMessage("??6?????????? ??c" + name + "??6 ???????? ??????????????.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("??c?????? ??e[ " + p.getName() + " ] ??c???? ??????????????.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ?????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String guild = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(guild)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					String name = GuildAPI.getJoinGuild(p);
    					GuildYml.deleteGuild(name, p);
						p.sendMessage("??4?????????? ??c" + name + "??4 ?????? ??????????????.");
						Bukkit.broadcastMessage("??f[??4??????f] ??c" + name + " ??6?????? ??c" + p.getName() + " ??6???? ??????????????.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
						p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ???????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
    					String name = GuildAPI.getJoinGuild(p);
    					String emeny = GuildAPI.getWarGuild(name);
    					String emenymaster = GuildAPI.getManager(emeny);
    					
    					for (Player pl : GuildAPI.getOnLineUser(name)) {
    						pl.sendMessage("");
    						pl.sendMessage("??c???? ???????? ???? ???????? ??f??????c?? ????????????.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					for (Player pl : GuildAPI.getOnLineUser(emeny)) {
    						pl.sendMessage("");
    						pl.sendMessage("??6?????? ???? ???????? ???? ???????? ??c??????6?? ????????????.");
    						if (emenymaster.equalsIgnoreCase(pl.getName()))
    							pl.sendMessage("??e[ /???? -> ???? ???? ????/???? ] ??6???? ?????? ???? ???? ??????????.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					GuildAPI.setSubmit(emeny, true);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
						p.closeInventory();
						Player ply = Method.getOnorOffLine(emenymaster);
						if (ply != null) ply.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ????????????????. ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
    					String name = GuildAPI.getJoinGuild(main.OkInvite.get(p));
    					if (GuildAPI.isWar(name)) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
    						p.closeInventory();
    						main.OkInvite.remove(p);
    						return;
    					}
    					
    					for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("??a?????? ??e[ " + p.getName() + " ] ??a???? ??????????????.");
						GuildAPI.addUser(name, p);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
						p.sendMessage("??6?????????? ?????? ??????????????.");
						main.OkInvite.remove(p);
						p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					main.OkInvite.get(p).sendMessage("??c" + p.getName() + "???? ???? ?????? ??????????????.");
    					main.OkInvite.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ???? ?????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
						double money = main.LandMoney.get(p);
    					if (main.LandMoney.get(p) != null) main.LandMoney.remove(p);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
						p.closeInventory();
						
						main.economy.withdrawPlayer(p.getName(), money);
						String name = GuildAPI.getJoinGuild(p);
						for (Player pls : GuildAPI.getOnLineUser(name)) {
							pls.sendMessage("??6?????????? ??c" + name + " ??6?????? ?????? ??????????????.");
							pls.sendMessage("??f- ??c" + main.pos1.get(p) + " * " + main.pos2.get(p));
						}
						
						GuildAPI.setGuildLand(name, true);
						GuildAPI.setLandPos1(name, main.pos1.get(p));
						GuildAPI.setLandPos2(name, main.pos2.get(p));
						GuildAPI.setLandAcceptUse(name, 1);
						GuildAPI.setLandAcceptBlock(name, 1);
						
						StringBuilder st = new StringBuilder();
						Location l = p.getLocation();
						st.append(l.getX()); st.append(","); st.append(l.getY()); st.append(","); st.append(l.getZ()); st.append(","); st.append(l.getYaw()); st.append(","); st.append(l.getPitch());
						GuildAPI.setLandHome(name, st.toString());
						if (main.pos1.get(p) != null) main.pos1.remove(p);
						if (main.pos2.get(p) != null) main.pos2.remove(p);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					if (main.LandMoney.get(p) != null) main.LandMoney.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ???? ?????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String guild = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(guild)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ???????? ?????????? ???? ???? ?????? ????????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
						String name = GuildAPI.getJoinGuild(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						p.closeInventory();
						
						for (Player pls : GuildAPI.getOnLineUser(name)) {
							pls.sendMessage("??c" + name + " ??6?????? ?????? ???????? ???? ??c??????6??????????.");
						}
						
						GuildAPI.setGuildLand(name, false);
						GuildAPI.setLandPos1(name, "999999999,999999999,999999999");
						GuildAPI.setLandPos2(name, "999999999,999999999,999999999");
						GuildAPI.setLandAcceptUse(name, -1);
						GuildAPI.setLandAcceptBlock(name, -1);
						GuildAPI.setLandHome(name, "999999999,999999999,999999999,0,0");
						GuildAPI.setLandPvPType(name, false);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ?? ?????? ?????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
						String attack = GuildAPI.getJoinGuild(p);
						String safe = ChatColor.stripColor(e.getInventory().getItem(13).getItemMeta().getDisplayName());
						
    					if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.isWar(attack)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ???????????? ???? ?????? ???? ?? ????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					if (GuildAPI.getUserInteger(attack) < 10) {
    						p.sendMessage("??c?????? ???? ???????? 10?? ?????????? ?????? ???? ?? ????????.");
        					p.closeInventory();
							return;
    					} else {
    						if (GuildAPI.getUserInteger(safe) < 10) {
    							p.sendMessage("??c???????? ???? ???????? 10?? ?????????? ?????? ???? ?? ????????.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
    					if (!GuildAPI.getGuildLand(attack)) {
    						p.sendMessage("??c?????? ???? ?????? ???????? ???????? ?????? ???? ?? ????????.");
        					p.closeInventory();
							return;
    					} else {
    						if (!GuildAPI.getGuildLand(safe)) {
    							p.sendMessage("??c???????? ???? ?????? ???????? ???????? ?????? ???? ?? ????????.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
			        	Bukkit.broadcastMessage("");
			        	Bukkit.broadcastMessage("??f[??4??????f] ??c" + attack + " ??6?????? ??c" + safe + " ??6???????? ???? ?????????? ????????????.");
			        	Bukkit.broadcastMessage("");
			        	
			        	for (Player pl : Bukkit.getOnlinePlayers())
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
			        	
			        	GuildAPI.setWarGuild(attack, safe);
			        	GuildAPI.setWarGuild(safe, attack);
			        	
			        	for (Player pl : GuildAPI.getOnLineUser(attack)) PartyAPI.AutoKickPlayer(pl);
			        	for (Player pl : GuildAPI.getOnLineUser(safe)) PartyAPI.AutoKickPlayer(pl);
			        	p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ?????? ?????? ?????? ?????? ????????.")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (main.SetItemCode.get(p) != null) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
            			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
            				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
            					
            				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
            					GuildGUI.MoptionGUI(p);
        						
            				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
            					GuildGUI.DoptionGUI(p);
        						
            				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
            					GuildGUI.SoptionGUI(p);
            				
                			main.SetItemCode.remove(p);
            				return;
            			}
        			}
        		}
    			
    			if (e.getCurrentItem().getTypeId() != 0) {
        			StringBuilder bldr = new StringBuilder();
        			bldr.append(Integer.toString(e.getCurrentItem().getTypeId()));
        			if (e.getCurrentItem().getData().getData() != (byte) 0) {
            			bldr.append(":");
            			bldr.append(Byte.toString(e.getCurrentItem().getData().getData()));
        			}
        			
        			GuildAPI.setInfoCode(GuildAPI.getJoinGuild(p), bldr.toString());
        			main.SetItemCode.remove(p);
        			p.sendMessage("??6?????????? ???? ?????? ?????? ??c" + bldr + " ??6???? ??????????????.");
        			p.closeInventory();
        			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("???? ???? ????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6??l???? ????")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					p.chat("/????????");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??c??l???? ???? ????")) {
    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    						GuildGUI.LandRemoveGUI(p, GuildAPI.getJoinGuild(p));
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ????, ???? ???? ????")) {
    					String guild = GuildAPI.getJoinGuild(p);
    					int type = 0;
    					
    					switch (GuildAPI.getLandAcceptUse(guild)) {
    						case 1: type = 2; break;
    						case 2: type = 3; break;
    						case 3: type = 4; break;
    						case 4: type = 5; break;
    						case 5: type = 1; break;
    						default: type = 1; break;
    					}
    					
    					GuildAPI.setLandAcceptUse(guild, type);
    					GuildGUI.GuildMenuGUI(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??b???? ???? ????, ???? ????")) {
    					String guild = GuildAPI.getJoinGuild(p);
    					int type = 0;
    					
    					switch (GuildAPI.getLandAcceptBlock(guild)) {
    						case 1: type = 2; break;
    						case 2: type = 3; break;
    						case 3: type = 4; break;
    						case 4: type = 5; break;
    						case 5: type = 1; break;
    						default: type = 1; break;
    					}
    					
    					GuildAPI.setLandAcceptBlock(guild, type);
    					GuildGUI.GuildMenuGUI(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??c???? ???? ?????? PvP ????")) {
    					String guild = GuildAPI.getJoinGuild(p);
    					boolean type = GuildAPI.getLandPvPType(guild);
    					
    					GuildAPI.setLandPvPType(guild, !type);
    					GuildGUI.GuildMenuGUI(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a??l???? ?? ????")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getGuildLand(name)) {
    						String[] home = GuildAPI.getLandHome(name).split(",");
    						Location l = new Location(Bukkit.getWorld("world_guild"), Double.parseDouble(home[0]), 
    						Double.parseDouble(home[1]), Double.parseDouble(home[2]), Float.parseFloat(home[3]), Float.parseFloat(home[4]));
    						p.closeInventory();
    						p.teleport(l);
    					}
    				}
    				
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ????")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e??l???? ?? ????")) {
    					String guild = GuildAPI.getJoinGuild(p);
    					String[] pos1 = GuildAPI.getLandPos1(guild).split(",");
    					String[] pos2 = GuildAPI.getLandPos2(guild).split(",");
    					boolean is = false;
    					double X1 = Double.parseDouble(pos1[0]);
    					double Y1 = Double.parseDouble(pos1[1]);
    					double Z1 = Double.parseDouble(pos1[2]);
    					double X2 = Double.parseDouble(pos2[0]);
    					double Y2 = Double.parseDouble(pos2[1]);
    					double Z2 = Double.parseDouble(pos2[2]);
    					double XP = p.getLocation().getX();
    					double YP = p.getLocation().getY();
    					double ZP = p.getLocation().getZ();
    					
    					if (X1 > X2 && Z1 > Z2) {
    						if (X1 >= XP && XP >= X2 && Z1 >= ZP && ZP >= Z2) {
    							if (Y1 >= Y2) {
    								if (Y1 >= YP && YP >= Y2) is = true;
    							} else {
    								if (Y1 <= YP && YP <= Y2) is = true;
    							}
    						}
    					}
    					
    					
    					else if (X1 < X2 && Z1 > Z2) {
    						if (X1 <= XP && XP <= X2 && Z1 >= ZP && ZP >= Z2) {
    							if (Y1 >= Y2) {
    								if (Y1 >= YP && YP >= Y2) is = true;
    							} else {
    								if (Y1 <= YP && YP <= Y2) is = true;
    							}
    						}
    					}

    					else if (X1 < X2 && Z1 < Z2) {
    						if (X1 <= XP && XP <= X2 && Z1 <= ZP && ZP <= Z2) {
    							if (Y1 >= Y2) {
    								if (Y1 >= YP && YP >= Y2) is = true;
    							} else {
    								if (Y1 <= YP && YP <= Y2) is = true;
    							}
    						}
    					}
    					

    					else if (X1 > X2 && Z1 < Z2) {
    						if (X1 >= XP && XP >= X2 && Z1 <= ZP && ZP <= Z2) {
    							if (Y1 >= Y2) {
    								if (Y1 >= YP && YP >= Y2) is = true;
    							} else {
    								if (Y1 <= YP && YP <= Y2) is = true;
    							}
    						}
    					}
    					
    					if (is) {
    						StringBuilder st = new StringBuilder();
    						Location l = p.getLocation();
    						st.append(l.getX()); st.append(","); st.append(l.getY()); st.append(","); st.append(l.getZ()); st.append(","); st.append(l.getYaw()); st.append(","); st.append(l.getPitch());
    						GuildAPI.setLandHome(guild, st.toString());
    						p.closeInventory();
    						p.sendMessage("??6?????????? ?????? ?????? ?????? ??c??????6??????????.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					} else {
    						p.sendMessage("??c???? ???? ???? ???? ???? ?????? ???????? ?? ????????.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    				}
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
         	if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("????") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("????")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[1]);
    						if (GuildAPI.isGuild(name)) {
    							if (GuildAPI.getJoinGuild(p) == null) {
        							if (e.getAction() == InventoryAction.PICKUP_ALL) {
        								if (GuildAPI.getJoinList(name).contains(p.getName())) {
        									p.sendMessage("??c?????? ???? ?? ?????? ???????? ????????????.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        			    					p.closeInventory();
        									return;
        								}
        								
        								if (GuildAPI.isWar(name)) {
        									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
        									p.closeInventory();
        									return;
        								}
        								
        								if (GuildAPI.getJoinType(name).equalsIgnoreCase("????")) {
        		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        		        						p.closeInventory();
        		        						p.sendMessage("??c?? ?????? ???? ???? ???? ???? ?????? ???????? ???????? ?? ????????.");
        		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		        						return;
        		        					}
        		        					
        									for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("??a?????? ??e[ " + p.getName() + " ] ??a???? ??????????????.");
        									GuildAPI.addUser(name, p);
        									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
        									p.sendMessage("??6?????????? ?????? ??????????????.");
        									p.closeInventory();
        								}
        								else if (GuildAPI.getJoinType(name).equalsIgnoreCase("??????")) {
        									if (GuildAPI.isWar(name)) {
            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            									p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
            									p.closeInventory();
            									return;
            								}
        									
        		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        		        						p.closeInventory();
        		        						p.sendMessage("??c?? ?????? ???? ???? ???? ???? ?????? ???????? ???????? ?? ????????.");
        		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		        						return;
        		        					}
        		        					
        									GuildAPI.addJoinList(name, p.getName());
        									p.sendMessage("??6?????????? ??c" + name + "??6 ?????? ???????? ??????????????.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        									p.closeInventory();
        									
        									for (Player pl : Bukkit.getOnlinePlayers()) {
        										for (String str : GuildAPI.getClassPlayer(name, pl.getName(), "????")) {
        											if (pl.getName().equalsIgnoreCase(str)) {
            											pl.sendMessage("??6?????? ?????? ??c" + p.getName() + " ??6???? ???? ???????? ??????????????.");
        											}
        										}
        											
        										if (pl.getName().equalsIgnoreCase(GuildAPI.getManager(name)))
        											pl.sendMessage("??6?????? ?????? ??c" + p.getName() + " ??6???? ???? ???????? ??????????????.");
        									}
        								}
        								else if (GuildAPI.getJoinType(name).equalsIgnoreCase("??????")) {
        									p.sendMessage("??c?? ?????? ?????? ???????? ???? ???????? ?????? ?? ????????.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									p.closeInventory();
        									return;
        								}
        							}
    							}
    							
    							else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
        							if (e.getAction() == InventoryAction.PICKUP_HALF) { // ???? ???? ????
        								if (GuildAPI.isWar(name)) {
        									p.sendMessage("??c???? ?? ?????? ?????? ???????????? ???? ?????????? ???? ?? ????????.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
        								}
        								
        								if (GuildAPI.getJoinGuild(p).equalsIgnoreCase(name)) {
        									p.sendMessage("??c?????? ?????? ????????????????? ?????????? ?????? ????????????.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
        								}
        								
        								Calendar cal = Calendar.getInstance();
        								StringBuilder st = new StringBuilder();
        								st.append(cal.get(Calendar.YEAR));
        								st.append(",");
        								st.append(cal.get(Calendar.MONTH) + 1);
        								st.append(",");
        								st.append(cal.get(Calendar.DATE));
        								String day = st.toString();
        								String endate = GuildAPI.getMakeDate(name);
        								String mydate = GuildAPI.getMakeDate(GuildAPI.getJoinGuild(p));
        								
        								try {
        							        SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd");
        							        Date beginDate = formatter.parse(mydate);
        							        Date endDate = formatter.parse(day);
        							         
        							        // ?????????? ????,??,???? ???? ?????? ?????? ???? ?????? ????
        							        long diff = endDate.getTime() - beginDate.getTime();
        							        long diffDays = diff / (24 * 60 * 60 * 1000);
        							 
        							        if (diffDays > 7) {
                								try {
                							        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy,MM,dd");
                							        Date beginDate2 = formatter2.parse(endate);
                							        Date endDate2 = formatter2.parse(day);
                							         
                							        // ?????????? ????,??,???? ???? ?????? ?????? ???? ?????? ????
                							        long diff2 = endDate2.getTime() - beginDate2.getTime();
                							        long diffDays2 = diff2 / (24 * 60 * 60 * 1000);
                							 
                							        if (diffDays2 > 7) {
                							        	GuildGUI.WarCheckGUI(p, name);
                				    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                							        } else {
                							        	p.sendMessage("??c???? ?????? 8?? ?????? ???????? ???????? ?? ????????.");
                    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                							        }
                							    } catch (ParseException ev) {
                							        ev.printStackTrace();
                							    }
        							        } else {
        							        	p.sendMessage("??c???? ?????? ???? ?????? 8?? ?????????? ???? ?????? ???????? ?? ????????.");
            			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        							        }
        							    } catch (ParseException ev) {
        							        ev.printStackTrace();
        							    }
        							}
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("??????") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("????") && e.getInventory().getName().split(" ")[2].equalsIgnoreCase("????")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.giveMasterListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.giveMasterListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(player)) {
    		    						p.sendMessage("??c???????? ???? ???? ???????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (GuildAPI.getJoinGuild(p) != null) {
		    							String guild = GuildAPI.getJoinGuild(p);
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("??c?????? ???????? ?????????? ???? ?????? ????????????.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    		    					GuildGUI.GiveMasterGUI(p, GuildAPI.getJoinGuild(p), player);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("????") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("????")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.userListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.userListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????")) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getUserClass(name, pl.getName()).equalsIgnoreCase("????") || GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("??c?????? ?? ?????????? ???? ???????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (GuildAPI.getJoinGuild(p) != null) {
		    							String guild = GuildAPI.getJoinGuild(p);
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ???? ?????? ????????????.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl.getPlayer());
    								p.sendMessage("??6?????????? ??c" + name + "??6 ???????? ???? ???? ??????????.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("??c?????? " + name + " ???????? ???? ???? ????????????.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "??????")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("??c?????? ??e[ " + e.getCurrentItem().getItemMeta().getDisplayName() + " ] ??c???? ???? ???? ????????????.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getUserClass(name, player).equalsIgnoreCase("????") || GuildAPI.getManager(name).equalsIgnoreCase(player)) {
    		    						p.sendMessage("??c?????? ?? ?????????? ?????? ???????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					main.ClickAccept.put(p, player);
    		    					GuildGUI.StepAcceptGUI(p);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    						}
    						
    						else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("??c?????? ?? ?????????? ???? ?????????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (GuildAPI.getJoinGuild(p) != null) {
		    							String guild = GuildAPI.getJoinGuild(p);
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ???? ?????? ????????????.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl);
    								p.sendMessage("??6?????????? ??c" + name + "??6 ???????? ???? ???? ??????????.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("??c?????? " + name + " ???????? ???? ???? ????????????.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "??????")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("??c?????? ??e[ " + p.getName() + " ] ??c???? ???? ???? ????????????.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(player)) {
    		    						p.sendMessage("??c?????? ?? ?????????? ?????? ???????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					main.ClickAccept.put(p, player);
    		    					GuildGUI.ManegerAcceptGUI(p);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("??????????") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("????")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.joinListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ????")) {
    						GuildGUI.joinListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????") || GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p);
    								
		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
		        						p.closeInventory();
		        						p.sendMessage("??c?????? ???????? ???????? ?????????? ?????? ???????? ?? ????????.");
		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		        						return;
		        					}
		        					
		        					if (GuildAPI.isWar(name)) {
    									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    									p.sendMessage("??c???? ?????? ???????? ?????????? ???? ?????? ?????? ?? ????????.");
    									p.closeInventory();
    									return;
    								}
		        					
    								if (Method.getOnorOffLine(pl) != null) {
    									Method.getOnorOffLine(pl).sendMessage("??6?????? ??c" + name + " ??6?????? ?????????? ??????????????.");
    								}
		        					
    								OfflinePlayer pp = Bukkit.getOfflinePlayer(pl);
									for (Player ply : GuildAPI.getOnLineUser(name)) ply.sendMessage("??a?????? ??e[ " + p.getName() + " ] ??a???? ??????????????.");
									GuildAPI.addUser(name, pp.getPlayer());
    		    					GuildAPI.subJoinList(name, pl);
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    		    					p.sendMessage("??6?????????? ??c" + pl + " ??6???? ?????? ??????????????.");
    		    					GuildGUI.joinListGUI(p, 1);
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					
    								if (Method.getOnorOffLine(pl) != null) {
    									Method.getOnorOffLine(pl).sendMessage("??c?????? " + name + " ?????? ???????? ????????????.");
    								}
    								
    								GuildAPI.subJoinList(name, pl);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    		    					p.sendMessage("??c?????????? " + pl + " ???? ?????? ??????????????.");
    		    					GuildGUI.joinListGUI(p, 1);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("??????") && e.getInventory().getName().split(" ")[2].equalsIgnoreCase("??????") && e.getInventory().getName().split(" ")[3].equalsIgnoreCase("????????") && e.getInventory().getName().split(" ")[4].equalsIgnoreCase("?????????????????")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		Player player = Method.getOnorOffLine(e.getInventory().getName().split(" ")[1]);
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ??????????????.")) {
        					if (player == null) {
        						p.sendMessage("??cERROR: ?????? ?????????? ???????? ????????.");
            					p.closeInventory();
        					} else {
        						if (GuildAPI.getJoinGuild(p) != null) {
	    							String guild = GuildAPI.getJoinGuild(p);
	    							if (GuildAPI.isWar(guild)) {
	    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	    	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ?????? ?????? ????????????.");
	    								return;
	    							}
	    						}
        						
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "??????")) player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					p.closeInventory();
            					p.sendMessage("??6?????????? ??c" + player.getName() + " ??6?????? ???????? ??????????????.");
            					player.sendMessage("??6?????? ??c" + GuildAPI.getJoinGuild(p) + " ??6???? ???????? ??c??????6????????????.");
            					GuildAPI.changeGuildMaster(p, player);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ???????? ??????????.")) {
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        				}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("??6??lG")) {
         		e.setCancelled(true);
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ????")) {
        					GuildGUI.RankingGUI(p, 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					p.closeInventory();
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??c???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.CheckQuitGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6??l???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					p.chat("/????????");
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
	    						GuildGUI.setStatGUI(p);
	    					} else {
	    						GuildGUI.userStatGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e???? ???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.userListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??b???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					String str = Method.NextChatMode(p, GuildAPI.getPlayerChat(p));
	    					GuildAPI.setPlayerChat(p, str);
	    					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.userGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("??????"))
	    						GuildGUI.MemberGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????"))
	    						GuildGUI.DesignerGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??2???????? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???? ???? ???? ?????? ???????? ?????? ?????????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (GuildAPI.isWar(name)) {
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
								p.closeInventory();
								return;
							}
        					
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					p.closeInventory();
	    					p.sendMessage("??6?????? ??c??????6????????????? ???????? ?????????? ????????.");
	    					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					main.InvitePlayer.add(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ????")) {
	    					if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????????")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.DoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a??l???? ?? ????")) {
        					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
        						GuildGUI.GuildMenuGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a??l???? ?? ????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getGuildLand(name)) {
        						String[] home = GuildAPI.getLandHome(name).split(",");
        						Location l = new Location(Bukkit.getWorld("world_guild"), Double.parseDouble(home[0]), 
        						Double.parseDouble(home[1]), Double.parseDouble(home[2]), Float.parseFloat(home[3]), Float.parseFloat(home[4]));
        						p.closeInventory();
        						p.teleport(l);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ????????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.isWar(name)) {
        						GuildGUI.WarSubmitGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ???? ????/????")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.isWar(name)) {
        						if (GuildAPI.isSubmit(name)) {
            						if (e.getAction() == InventoryAction.PICKUP_HALF) {
            	    					String emeny = GuildAPI.getWarGuild(name);
            	    					
            							for (Player pl : Bukkit.getOnlinePlayers()) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("??e[ " + name + " vs " + emeny + " ] ??6?????? ???????? ??c" + name + " ??6?????? ??????????????!");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.5F);
            	    					}
            							
            							GuildAPI.setWarGuild(name, null);
            							GuildAPI.setWarGuild(emeny, null);
            	    					GuildAPI.setSubmit(name, false);
            	    					GuildAPI.setSubmit(emeny, false);
            	    					
            	    					Player ply = Method.getOnorOffLine(GuildAPI.getManager(emeny));
            							if (ply != null) ply.closeInventory();
            							GuildGUI.MasterGuildGUI(p);
            							return;
            						}
            						
            						else if (e.getAction() == InventoryAction.PICKUP_ALL) {
            	    					String emeny = GuildAPI.getWarGuild(name);
            	    					
            	    					for (Player pl : GuildAPI.getOnLineUser(name)) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("??6???? ???????? ???? ?????? ??c??????6 ??????????.");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.5F);
            	    					}
            	    					
            	    					for (Player pl : GuildAPI.getOnLineUser(emeny)) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("??c?????? ?????? ???? ?????? ??????????????.");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            	    					}
            	    					
            	    					p.closeInventory();
            	    					Player ply = Method.getOnorOffLine(GuildAPI.getManager(emeny));
            							if (ply != null) ply.closeInventory();
            	    					GuildAPI.setSubmit(name, false);
            						}
        						}
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ?? ????")) {
        					if (main.pos1.get(p) != null && main.pos2.get(p) != null) {
        						if (p.getWorld().getName().equalsIgnoreCase("world_guild")) {
        							String[] pos1 = main.pos1.get(p).split(",");
            						String[] pos2 = main.pos2.get(p).split(",");
            						boolean is = false;
            						double X1 = Double.parseDouble(pos1[0]);
            						double Y1 = Double.parseDouble(pos1[1]);
            						double Z1 = Double.parseDouble(pos1[2]);
            						double X2 = Double.parseDouble(pos2[0]);
            						double Y2 = Double.parseDouble(pos2[1]);
            						double Z2 = Double.parseDouble(pos2[2]);
            						double XP = p.getLocation().getX();
            						double YP = p.getLocation().getY();
            						double ZP = p.getLocation().getZ();
            						
            						if (X1 > X2 && Z1 > Z2) {
            							if (X1 >= XP && XP >= X2 && Z1 >= ZP && ZP >= Z2) {
            								if (Y1 >= Y2) {
            									if (Y1 >= YP && YP >= Y2) is = true;
            								} else {
            									if (Y1 <= YP && YP <= Y2) is = true;
            								}
            							}
            						}
            						
            						
            						else if (X1 < X2 && Z1 > Z2) {
            							if (X1 <= XP && XP <= X2 && Z1 >= ZP && ZP >= Z2) {
            								if (Y1 >= Y2) {
            									if (Y1 >= YP && YP >= Y2) is = true;
            								} else {
            									if (Y1 <= YP && YP <= Y2) is = true;
            								}
            							}
            						}

            						else if (X1 < X2 && Z1 < Z2) {
            							if (X1 <= XP && XP <= X2 && Z1 <= ZP && ZP <= Z2) {
            								if (Y1 >= Y2) {
            									if (Y1 >= YP && YP >= Y2) is = true;
            								} else {
            									if (Y1 <= YP && YP <= Y2) is = true;
            								}
            							}
            						}
            						

            						else if (X1 > X2 && Z1 < Z2) {
            							if (X1 >= XP && XP >= X2 && Z1 <= ZP && ZP <= Z2) {
            								if (Y1 >= Y2) {
            									if (Y1 >= YP && YP >= Y2) is = true;
            								} else {
            									if (Y1 <= YP && YP <= Y2) is = true;
            								}
            							}
            						}
            						
            						if (is) {
            							if (!GuildAPI.isAroundGuild(new Location(Bukkit.getWorld("world_guild"), X1, Y1, Z1), 8, false)) {
            								if (!GuildAPI.isAroundGuild(new Location(Bukkit.getWorld("world_guild"), X2, Y2, Z2), 8, false)) {
                								double A = 0;
                								double B = 0;
                								double C = 0;
                								
                								if (Z1 > Z2) C = Z1 - Z2;
                								else C = Z2 - Z1;
                								
                								if (Y1 > Y2) B = Y1 - Y2;
                								else B = Y2 - Y1;

                								if (X1 > X2) A = X1 - X2;
                								else A = X2 - X1;
                								
                								double money = A * B * C;
                								money *= 5;
                								
                								if (main.economy.getBalance(p.getName()) >= money) {
                									GuildGUI.LandCreateGUI(p, GuildAPI.getJoinGuild(p));
                									main.LandMoney.put(p, money);
                								} else {
                									double i = main.economy.getBalance(p.getName()) - money;
                        							p.sendMessage("??c" + i + "???? ?????????? ???? ?????? ???????? ?? ????????.");
                            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                            						p.closeInventory();
                            						return;
                        						}
                							} else {
                    							p.sendMessage("??c???? 60 ?????? ???? ?????? ??????????. ???? ???? ?????? ?????? ????????.");
                        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                        						p.closeInventory();
                        						return;
                    						}
            								
            							} else {
                							p.sendMessage("??c???? 60 ?????? ???? ?????? ??????????. ???? ???? ?????? ?????? ????????.");
                    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                    						p.closeInventory();
                    						return;
                						}
            						} else {
            							p.sendMessage("??c?????? ?????? ?????? ?????? ???????? ?????? ?????? ????????.");
                						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                						p.closeInventory();
                						return;
            						}
        						} else {
            						p.sendMessage("??c???? ???? ?????? ???? ???????? ?????? ?????? ????????.");
            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            						p.closeInventory();
            						return;
            					}
        					} else {
        						p.sendMessage("??c???? ???? ?????? ???? ???????? ?????? ?????? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						p.closeInventory();
        						return;
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ???? ??7/ ??f?????????? ????")) {
	    					if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("????")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.SoptionGUI(p);
	    					}
	    					
	    					else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
	    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.MoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.giveMasterListGUI(p, 1);
        				}
        				
        				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
            				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f[??4!??f] ??4???? ????")) {
    	    					String name = GuildAPI.getJoinGuild(p);
    	    					if (GuildAPI.getUser(name).size() != 1) {
            						p.sendMessage("??c???? ?????? ???? ???? ?????? ?????? ?????? ?????? ????????.");
            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            						p.closeInventory();
            						return;
    	    					}
    	    					
    	    					if (GuildAPI.isWar(name)) {
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
									p.sendMessage("??c?????? ???????? ?????????? ???? ?????? ????????????.");
									p.closeInventory();
									return;
								}
    	    					
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
            					GuildGUI.CheckDeleteGuildGUI(p);
            				}
        				}
        			}
        		}
         	}
     	}
	}
}
