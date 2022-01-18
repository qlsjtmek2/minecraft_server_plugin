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
							p.sendMessage("¡×3ÀÌ°÷Àº ½ºÅ³ »ç¿ë Á¦ÇÑ ±¸¿ª ÀÔ´Ï´Ù.");
							return;
						}
					}
				}
			}
			
			switch (accept) {
				case 1:
					if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 2:
					if (!rank.equalsIgnoreCase("À¯Àú")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 3:
					if (!rank.equalsIgnoreCase("À¯Àú") && !rank.equalsIgnoreCase("Á¤È¸¿ø")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 4:
					if (rank.equalsIgnoreCase("½ºÅÇ") || rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
				case 5:
					if (rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(p.getLocation())) is = true; break;
			} e.setCancelled(is);
			
			if (is) p.sendMessage("¡×3ÀÌ°÷Àº ½ºÅ³ »ç¿ë Á¦ÇÑ ±¸¿ª ÀÔ´Ï´Ù.");
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
						if (!rank.equalsIgnoreCase("À¯Àú")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("À¯Àú") && !rank.equalsIgnoreCase("Á¤È¸¿ø")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("½ºÅÇ") || rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
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
						if (!rank.equalsIgnoreCase("À¯Àú")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("À¯Àú") && !rank.equalsIgnoreCase("Á¤È¸¿ø")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("½ºÅÇ") || rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(e.getBlock().getLocation())) is = false; break;
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
			    					p.sendMessage("¡×6±æµå ¡×c¿µÅä ¡×6ÁöÁ¤ Ã¹¹øÂ° ÁöÁ¡ ¡×e[ " + st.toString() + " ]");
			    					e.setCancelled(true);
			    					
			    					if (main.pos1.get(p) != null && main.pos2.get(p) != null) {
				    					p.sendMessage("¡×f- ¡×6ÁÂÇ¥°¡ ¸ðµÎ ÁöÁ¤µÇ¾ú½À´Ï´Ù. '/±æµå -> ±æµå ºÎµ¿»ê »ý¼º Å¬¸¯'");
			    					}
		    					} else {
		    						if (!main.pos1.get(p).equalsIgnoreCase(st.toString())) {
		    							main.pos1.remove(p);
		    							main.pos1.put(p, st.toString());
				    					p.sendMessage("¡×6±æµå ¡×c¿µÅä ¡×6ÁöÁ¤ Ã¹¹øÂ° ÁöÁ¡ ¡×e[ " + st.toString() + " ]");
				    					e.setCancelled(true);
				    					
				    					if (main.pos1.get(p) != null && main.pos2.get(p) != null) {
					    					p.sendMessage("¡×f- ¡×6ÁÂÇ¥°¡ ¸ðµÎ ÁöÁ¤µÇ¾ú½À´Ï´Ù. '/±æµå -> ±æµå ºÎµ¿»ê »ý¼º Å¬¸¯'");
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
			    					p.sendMessage("¡×6±æµå ¡×c¿µÅä ¡×6ÁöÁ¤ µÎ¹øÂ° ÁöÁ¡ ¡×e[ " + st.toString() + " ]");
			    					e.setCancelled(true);
			    					
			    					if (main.pos2.get(p) != null && main.pos2.get(p) != null) {
				    					p.sendMessage("¡×f- ¡×6ÁÂÇ¥°¡ ¸ðµÎ ÁöÁ¤µÇ¾ú½À´Ï´Ù. '/±æµå -> ±æµå ºÎµ¿»ê »ý¼º Å¬¸¯'");
			    					}
		    					} else {
		    						if (!main.pos2.get(p).equalsIgnoreCase(st.toString())) {
		    							main.pos2.remove(p);
		    							main.pos2.put(p, st.toString());
				    					p.sendMessage("¡×6±æµå ¡×c¿µÅä ¡×6ÁöÁ¤ µÎ¹øÂ° ÁöÁ¡ ¡×e[ " + st.toString() + " ]");
				    					e.setCancelled(true);
				    					
				    					if (main.pos2.get(p) != null && main.pos2.get(p) != null) {
					    					p.sendMessage("¡×f- ¡×6ÁÂÇ¥°¡ ¸ðµÎ ÁöÁ¤µÇ¾ú½À´Ï´Ù. '/±æµå -> ±æµå ºÎµ¿»ê »ý¼º Å¬¸¯'");
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
						if (!rank.equalsIgnoreCase("À¯Àú")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("À¯Àú") && !rank.equalsIgnoreCase("Á¤È¸¿ø")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("½ºÅÇ") || rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("¸Å´ÏÀú")) if (GuildAPI.inGuildArea(e.getClickedBlock().getLocation())) is = false; break;
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
			
			if (guild.equalsIgnoreCase("Ãë¼Ò")) {
				e.setCancelled(true);
				p.sendMessage("¡×6¼º°øÀûÀ¸·Î ±æµå Ã¢¼³À» ¡×cÃë¼Ò ¡×6ÇÏ¼Ì½À´Ï´Ù.");
				main.CreateGuild.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (guild.matches("[¤¡-¤¾¤¿-¤Ó°¡-ÆRa-zA-Z0-9_]+")) {
				if (Method.GuildNameAgree(guild)) {
					if (!GuildAPI.isGuild(guild)) {
						e.setCancelled(true);
						GuildGUI.CheckCreateGUI(p);
						main.CreateGuild.remove(p);
						main.GuildName.put(p.getName(), guild);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
					} else {
						e.setCancelled(true);
						p.sendMessage("¡×cÀÌ¹Ì °°Àº ÀÌ¸§À» °¡Áø È¸»ç°¡ ÀÖ½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					}
				} else {
					e.setCancelled(true);
					p.sendMessage("¡×c»ç¿ëÀÌ ºÒ°¡´ÉÇÑ ¹®ÀÚ°¡ Æ÷ÇÔµÇ¾ú°Å³ª ±æµå ÀÌ¸§ÀÌ 2±ÛÀÚ, 6±ÛÀÚº¸´Ù Å®´Ï´Ù.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				}
			} else {
				e.setCancelled(true);
				p.sendMessage("¡×c»ç¿ëÀÌ ºÒ°¡´ÉÇÑ ¹®ÀÚ°¡ Æ÷ÇÔµÇ¾î ÀÖ½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			}
		}
		
		else if (main.InvitePlayer.contains(p)) {
			String name = e.getMessage();
			
			if (GuildAPI.getJoinGuild(p) != null) {
				String guild = GuildAPI.getJoinGuild(p);
				if (GuildAPI.isWar(guild)) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					p.sendMessage("¡×cÇöÀç °¡ÀÔµÈ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå ÃÊ´ë°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
					main.InvitePlayer.remove(p);
					e.setCancelled(true);
					return;
				}
			}
			
			if (name.equalsIgnoreCase("Ãë¼Ò")) {
				e.setCancelled(true);
				p.sendMessage("¡×6¼º°øÀûÀ¸·Î ÇÃ·¹ÀÌ¾î ÃÊ´ë¸¦ ¡×cÃë¼Ò ¡×6ÇÏ¼Ì½À´Ï´Ù.");
				main.InvitePlayer.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Method.searchOnlinePlayer(name) == null) {
				e.setCancelled(true);
				p.sendMessage("¡×cÀÌ ÇÃ·¹ÀÌ¾î´Â Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			name = Method.searchOnlinePlayer(name);
			
			if (Method.getOnorOffLine(name) == null) {
				e.setCancelled(true);
				p.sendMessage("¡×cÀÌ ÇÃ·¹ÀÌ¾î´Â ¿Â¶óÀÎÀÌ ¾Æ´Õ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			Player pl = Method.getOnorOffLine(name);
			
			if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "±æµå ÃÊ´ë")) {
				e.setCancelled(true);
				p.sendMessage("¡×c±× ÇÃ·¹ÀÌ¾î´Â ±æµå ÃÊ´ë °ÅºÎ »óÅÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (GuildAPI.getJoinGuild(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("¡×c±× ÇÃ·¹ÀÌ¾î´Â ÀÌ¹Ì ±æµå°¡ ÀÖ½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (main.OkInvite.get(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("¡×c±× ÇÃ·¹ÀÌ¾î´Â ÀÌ¹Ì ±æµå ÃÊ´ë¸¦ ¹ÞÀº »óÅÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ½Ê½Ã¿À");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			GuildGUI.InviteGUI(pl, GuildAPI.getJoinGuild(p));
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + name + " ¡×6´ÔÀ» ÃÊ´ëÇÏ¼Ì½À´Ï´Ù.");
			main.OkInvite.put(pl, p);
			main.InvitePlayer.remove(p);
			e.setCancelled(true);
		}
		
		else if (main.SetStatMessage.containsKey(p)) {
			String StatMessage = e.getMessage();
			
			if (StatMessage.equalsIgnoreCase("Ãë¼Ò")) {
				e.setCancelled(true);
				p.sendMessage("¡×6¼º°øÀûÀ¸·Î »óÅÂ ¸Þ¼¼Áö ¼öÁ¤À» ¡×cÃë¼Ò ¡×6ÇÏ¼Ì½À´Ï´Ù.");
				main.SetStatMessage.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			GuildAPI.setInfoMessage(GuildAPI.getJoinGuild(p), StatMessage);
			e.setCancelled(true);
			p.sendMessage("¡×6¼º°øÀûÀ¸·Î »óÅÂ ¸Þ¼¼Áö¸¦ ¼öÁ¤ÇÏ¼Ì½À´Ï´Ù. ¡×e[ /±æµå ]");
			main.SetStatMessage.remove(p);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		}
		
		else if (main.SetLore.containsKey(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("Ãë¼Ò")) {
				e.setCancelled(true);
				p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¼³¸í Ãß°¡/»èÁ¦ ¼öÁ¤À» ¡×cÃë¼Ò ¡×6ÇÏ¼Ì½À´Ï´Ù.");
				main.SetLore.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¼³¸íÀ» ¡×cÃß°¡ ¡×6ÇÏ¼Ì½À´Ï´Ù.");
					p.sendMessage("¡×6Ãß°¡µÈ ºÎºÐ: ¡×f" + message);
					p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					String name = GuildAPI.getJoinGuild(p);
					Scanner scan = new Scanner(lore.split(" ")[1]);
					if (!scan.hasNextInt())
					{
						p.sendMessage("");
						p.sendMessage("¡×6¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é ¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
						p.sendMessage("¡×6<»èÁ¦ÇÒ ¶óÀÎ> ºÎºÐ¿¡´Â ¼ýÀÚ¸¸ µé¾î°¥ ¼ö ÀÖ½À´Ï´Ù.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					int removeline = Integer.parseInt(lore.split(" ")[1]) - 1;
					
					if (removeline < 0) {
						p.sendMessage("");
						p.sendMessage("¡×6¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é ¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
						p.sendMessage("¡×6<»èÁ¦ÇÒ ¶óÀÎ> ºÎºÐ¿¡´Â ¼ýÀÚ¸¸ µé¾î°¥ ¼ö ÀÖ½À´Ï´Ù.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					List<String> list = GuildAPI.getInfoLore(name);
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						GuildAPI.setInfoLore(name, list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + removeline + " ¡×6¹øÂ° ¼³¸íÀ» »èÁ¦ÇÏ¼Ì½À´Ï´Ù.");
						p.sendMessage("¡×6»èÁ¦µÈ ºÎºÐ: ¡×f" + str);
						p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return;
					} else {
						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("¡×cÇØ´ç ºÎºÐÀº Á¸ÀçÇÏÁö ¾Ê´Â ¶óÀÎÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
						p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				}
				
				else {
					p.sendMessage("");
					p.sendMessage("¡×6¼³¸íÀ» ¡×cÃß°¡¡×6ÇÏ½Ã·Á¸é ¡×e'+ <¼³¸í>'¡×6, ¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é");
					p.sendMessage("¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
					p.sendMessage("¡×f* ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
					p.sendMessage("¡×f");
					p.sendMessage("¡×7¿¹½Ã) ¡×f'+ ¾È³çÇÏ¼¼¿ä. OO ±æµåÀÔ´Ï´Ù.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("¡×6¼³¸íÀ» ¡×cÃß°¡¡×6ÇÏ½Ã·Á¸é ¡×e'+ <¼³¸í>'¡×6, ¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é");
				p.sendMessage("¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
				p.sendMessage("¡×f* ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
				p.sendMessage("¡×f");
				p.sendMessage("¡×7¿¹½Ã) ¡×f'+ ¾È³çÇÏ¼¼¿ä. OO ±æµåÀÔ´Ï´Ù.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
		
		else if (GuildAPI.getPlayerChat(p).equalsIgnoreCase("±æµå")) {
			e.setCancelled(true);
			for (Player pl : GuildAPI.getOnLineUser(GuildAPI.getJoinGuild(p))) {
				if (pl.isOp()) continue;
				if (p.isOp()) pl.sendMessage("¡×f[¡×6±æµåÃ¤ÆÃ¡×f] ¡×f" + p.getName() + " ¡×7:: ¡×e" + e.getMessage());
				else pl.sendMessage("¡×f[¡×6±æµåÃ¤ÆÃ¡×f] ¡×r" + p.getDisplayName() + " ¡×7:: ¡×e" + e.getMessage());
			}
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.isOp()) {
					if (p.isOp()) pl.sendMessage("¡×f[¡×6±æµåÃ¤ÆÃ¡×f] ¡×f" + p.getName() + " ¡×7:: ¡×e" + e.getMessage());
					else pl.sendMessage("¡×f[¡×6±æµåÃ¤ÆÃ¡×f] ¡×r" + p.getDisplayName() + " ¡×7:: ¡×e" + e.getMessage());
				}
			}
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "±æµåÃ¤ÆÃ" + ChatColor.WHITE + "] " + ChatColor.RESET + p.getDisplayName() + " " + ChatColor.GRAY + ":: " + ChatColor.YELLOW + e.getMessage());
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("±æµå¿¡ ÃÊ´ë¹ÞÀ¸¼Ì½À´Ï´Ù. ¼ö¶ôÇÏ½Ã°Ú½À´Ï±î?")) {
			if (main.OkInvite.get(p) != null) {
				p.sendMessage("¡×cÀÎº¥Åä¸®¸¦ ´Ý¾Æ ±æµå ÃÊ´ë¿¡ ÀÚµ¿À¸·Î °ÅÀýÇÏ¿´½À´Ï´Ù.");
				main.OkInvite.get(p).sendMessage("¡×c" + p.getName() + "´ÔÀÌ ±æµå ÃÊ´ë¸¦ °ÅÀýÇÏ¼Ì½À´Ï´Ù.");
				main.OkInvite.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå¸¦ Ã¢¼³ÇÏ½Ã°Ú½À´Ï±î?")) {
			if (main.CreateGuild.contains(p)) {
				p.sendMessage("¡×cÀÎº¥Åä¸®¸¦ ´Ý¾Æ ÀÚµ¿À¸·Î ±æµå Ã¢¼³À» Ãë¼ÒÇÏ¿´½À´Ï´Ù.");
				main.GuildName.remove(p.getName());
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå ¿µÅä¸¦ »ý¼ºÇÏ½Ã°Ú½À´Ï±î?")) {
			if (main.LandMoney.get(p) != null) main.LandMoney.remove(p);
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("¼³Á¤ÇÒ ¾ÆÀÌÅÛ ÄÚµå¸¦ Å¬¸¯ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.")) {
			if (main.SetItemCode.get(p) != null) main.SetItemCode.remove(p);
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("±æµå°¡ ¾øÀ¸½Ã±º¿ä!")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå Ã¢¼³")) {
    					if (main.economy.getBalance(p.getName()) >= Double.parseDouble(GuildAPI.getCreateMoney())) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
        					p.closeInventory();
        					main.CreateGuild.add(p);
        					p.sendMessage("¡×6Ã¤ÆÃÃ¢¿¡ Ã¢¼³ÇÒ ¡×c±æµå ÀÌ¸§¡×6À» Àû¾îÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = Double.parseDouble(GuildAPI.getCreateMoney()) - main.economy.getBalance(p.getName());
        					p.sendMessage("¡×c" + num + " ¿øÀÌ ºÎÁ·ÇÏ¿© ±æµå¸¦ Ã¢¼³ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ·©Å·")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					GuildGUI.RankingGUI(p, 1);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("µðÀÚÀÌ³Ê ¿É¼Ç")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå »óÅÂ ¸Þ¼¼Áö ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ »óÅÂ ¸Þ¼¼Áö¸¦ ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¼³¸íÀ» ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("¡×6º¯°æÇÒ »óÅÂ ¸Þ¼¼Áö¸¦ ¡×cÃ¤ÆÃÃ¢¡×6¿¡ ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ¼³¸í ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ ¼³¸íÀ» ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì »óÅÂ ¸Þ¼¼Áö¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("¡×6¼³¸íÀ» ¡×cÃß°¡¡×6ÇÏ½Ã·Á¸é ¡×e'+ <¼³¸í>'¡×6, ¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é");
    						p.sendMessage("¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f* ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
    						p.sendMessage("¡×f");
    						p.sendMessage("¡×7¿¹½Ã) ¡×f'+ ¾È³çÇÏ¼¼¿ä. OO ±æµåÀÔ´Ï´Ù.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ¾ÆÀÌÅÛ ÄÚµå ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¼³¸íÀ» ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì »óÅÂ ¸Þ¼¼Áö¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, name);
    						GuildGUI.SetItemCodeGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("½ºÅÇ ¿É¼Ç")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå »óÅÂ ¸Þ¼¼Áö ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ »óÅÂ ¸Þ¼¼Áö¸¦ ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¼³¸íÀ» ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("¡×6º¯°æÇÒ »óÅÂ ¸Þ¼¼Áö¸¦ ¡×cÃ¤ÆÃÃ¢¡×6¿¡ ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ¼³¸í ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ ¼³¸íÀ» ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì »óÅÂ ¸Þ¼¼Áö¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("¡×6¼³¸íÀ» ¡×cÃß°¡¡×6ÇÏ½Ã·Á¸é ¡×e'+ <¼³¸í>'¡×6, ¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é");
    						p.sendMessage("¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f* ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
    						p.sendMessage("¡×f");
    						p.sendMessage("¡×7¿¹½Ã) ¡×f'+ ¾È³çÇÏ¼¼¿ä. OO ±æµåÀÔ´Ï´Ù.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ¾ÆÀÌÅÛ ÄÚµå ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¼³¸íÀ» ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì »óÅÂ ¸Þ¼¼Áö¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, name);
    						GuildGUI.SetItemCodeGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×3±æµå °¡ÀÔÁ¶°Ç")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					String name = GuildAPI.getJoinGuild(p);
	    					if (GuildAPI.getJoinType(name).equalsIgnoreCase("°ø°³")) GuildAPI.setJoinType(name, 1);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("½ÅÃ»¼­")) GuildAPI.setJoinType(name, 2);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("ºñ°ø°³")) GuildAPI.setJoinType(name, 0);
	    					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×9°¡ÀÔ ½ÅÃ»¼­ ¸ñ·Ï")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("¸Å´ÏÀú ¿É¼Ç")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå »óÅÂ ¸Þ¼¼Áö ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ »óÅÂ ¸Þ¼¼Áö¸¦ ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¼³¸íÀ» ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("¡×6º¯°æÇÒ »óÅÂ ¸Þ¼¼Áö¸¦ ¡×cÃ¤ÆÃÃ¢¡×6¿¡ ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ¼³¸í ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ ¼³¸íÀ» ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì »óÅÂ ¸Þ¼¼Áö¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, name);
    						p.sendMessage("");
    						p.sendMessage("¡×6¼³¸íÀ» ¡×cÃß°¡¡×6ÇÏ½Ã·Á¸é ¡×e'+ <¼³¸í>'¡×6, ¼³¸íÀ» ¡×c»èÁ¦¡×6ÇÏ½Ã·Á¸é");
    						p.sendMessage("¡×e'- <»èÁ¦ÇÒ ¶óÀÎ>' ¡×6À» ÀÔ·ÂÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        					p.sendMessage("¡×f* ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
    						p.sendMessage("¡×f");
    						p.sendMessage("¡×7¿¹½Ã) ¡×f'+ ¾È³çÇÏ¼¼¿ä. OO ±æµåÀÔ´Ï´Ù.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ¾ÆÀÌÅÛ ÄÚµå ¡×e¡×l¼öÁ¤")) {
    						String name = GuildAPI.getJoinGuild(p);
    						if (main.SetItemCode.containsValue(name)) {
    							p.sendMessage("¡×cÀÌ¹Ì ´©°¡ ¾ÆÀÌÅÛ ÄÚµå¸¦ ¼öÁ¤ÇÏ°í ÀÖ½À´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ¼³¸íÀ» ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì »óÅÂ ¸Þ¼¼Áö¸¦ ¼³Á¤ÇÏ°í °è½Ê´Ï´Ù. 'Ãë¼Ò' ¸¦ Ã¤ÆÃÃ¢¿¡ ÀÔ·Â ÈÄ ´Ù½Ã ½ÇÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, name);
    						GuildGUI.SetItemCodeGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×3±æµå °¡ÀÔÁ¶°Ç")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					String name = GuildAPI.getJoinGuild(p);
	    					if (GuildAPI.getJoinType(name).equalsIgnoreCase("°ø°³")) GuildAPI.setJoinType(name, 1);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("½ÅÃ»¼­")) GuildAPI.setJoinType(name, 2);
	    					else if (GuildAPI.getJoinType(name).equalsIgnoreCase("ºñ°ø°³")) GuildAPI.setJoinType(name, 0);
	    					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×9°¡ÀÔ ½ÅÃ»¼­ ¸ñ·Ï")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×e°æÇèÄ¡ º¸³Ê½º ¡×6¡×l¼³Á¤")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					GuildAPI.setExpStat(GuildAPI.getJoinGuild(p));
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×aÈ÷¾î·Î Á¡¼ö º¸³Ê½º ¡×6¡×l¼³Á¤")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					GuildAPI.setScoreStat(GuildAPI.getJoinGuild(p));
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×bÇà¿î ¡×6¡×l¼³Á¤")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					GuildAPI.setLuckStat(GuildAPI.getJoinGuild(p));
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a±æµå ¾÷±×·¹ÀÌµå ÁøÇà")) {
	    					String guild = GuildAPI.getJoinGuild(p);
	    					int i = GuildAPI.getUpGrade(guild); i += 1;
	    					int money = Integer.parseInt(GuildConfig.getGuildList("±æµå ¾÷±×·¹ÀÌµå").get(i).split(" ")[1]);
	    					
	    					if (GuildConfig.getGuildList("±æµå ¾÷±×·¹ÀÌµå").size() >= (i + 2)) {
		    					if (main.economy.getBalance(p.getName()) >= money) {
			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) Method.castLvup(p);
		        					p.closeInventory();
		        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ±æµå¸¦ ¡×c" + i + " ¡×6´Ü°è·Î ¾÷±×·¹ÀÌµå ÇÏ¼Ì½À´Ï´Ù.");
		        					GuildAPI.setUpGrade(guild, i);
		    						main.economy.withdrawPlayer(p.getName(), (double) money);
		    					} else {
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		        					double num = (double) money - main.economy.getBalance(p.getName());
		        					p.closeInventory();
		        					p.sendMessage("¡×c" + num + " ¿øÀÌ ºÎÁ·ÇÏ¿© ±æµå ¾÷±×·¹ÀÌµå¸¦ ÁøÇàÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
		    					}
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("±æµå ½ºÅÝ")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("±æµå ½ºÅÝ ¼³Á¤")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×e°æÇèÄ¡ º¸³Ê½º ¡×6¡×l½ºÅÝ")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getExpStat(name) < GuildAPI.getMaxExpStat(name)) {
    						if (GuildAPI.isSubPoint(name, 12000)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							GuildAPI.subPoint(name, 12000);
    							GuildAPI.addExpStat(name, 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("¡×cÆ÷ÀÎÆ®°¡ ºÎÁ·ÇÏ¿© °æÇèÄ¡ º¸³Ê½º ½ºÅÝÀ» ¿Ã¸®½Ç ¼ö ¾ø½À´Ï´Ù.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×aÈ÷¾î·Î Á¡¼ö º¸³Ê½º ¡×6¡×l½ºÅÝ")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getScoreStat(name) < GuildAPI.getMaxScoreStat(name)) {
    						if (GuildAPI.isSubPoint(name, 12000)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							GuildAPI.subPoint(name, 12000);
    							GuildAPI.addScoreStat(name, 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("¡×cÆ÷ÀÎÆ®°¡ ºÎÁ·ÇÏ¿©È÷¾î·Î Á¡¼ö º¸³Ê½º ½ºÅÝÀ» ¿Ã¸®½Ç ¼ö ¾ø½À´Ï´Ù.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×bÇà¿î ¡×6¡×l½ºÅÝ")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getLuckStat(name) < GuildAPI.getMaxLuckStat(name)) {
    						if (GuildAPI.isSubPoint(name, 16000)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							GuildAPI.subPoint(name, 16000);
    							GuildAPI.addLuckStat(name, 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("¡×cÆ÷ÀÎÆ®°¡ ºÎÁ·ÇÏ¿© Çà¿î ½ºÅÝÀ» ¿Ã¸®½Ç ¼ö ¾ø½À´Ï´Ù.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå¸¦ Ã¢¼³ÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, Ã¢¼³ÇÏ°Ú½À´Ï´Ù.")) {
    					if (main.economy.getBalance(p.getName()) >= Double.parseDouble(GuildAPI.getCreateMoney())) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) Method.castLvup(p);
        					String guild = main.GuildName.get(p.getName());
        					main.GuildName.remove(p.getName());
        					p.closeInventory();
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + guild + " ¡×6±æµå¸¦ Ã¢¼³ÇÏ¼Ì½À´Ï´Ù.");
        					GuildAPI.setJoinGuild(p, guild);
        					GuildYml.addGuild(guild, p.getName());
    						main.economy.withdrawPlayer(p.getName(), Double.parseDouble(GuildAPI.getCreateMoney()));
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = Double.parseDouble(GuildAPI.getCreateMoney()) - main.economy.getBalance(p.getName());
        					p.sendMessage("¡×c" + num + " ¿øÀÌ ºÎÁ·ÇÏ¿© ±æµå¸¦ Ã¢¼³ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        					p.closeInventory();
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, Ã¢¼³ÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ±æµå Ã¢¼³À» ¡×cÃë¼Ò ¡×6ÇÏ¼Ì½À´Ï´Ù.");
    					main.GuildName.remove(p.getName());
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("½ºÅÇ ±ÇÇÑ ¼³Á¤ Ã¢")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (main.ClickAccept.get(p) != null) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×eÀ¯Àú ¡×f±ÇÇÑ Áö±Þ")) {
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×cÀ¯Àú ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "À¯Àú");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×aÁ¤È¸¿ø ¡×f±ÇÇÑ Áö±Þ")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 2) <= GuildAPI.getClassAmount(name, "Á¤È¸¿ø")) {
        						p.closeInventory();
        						p.sendMessage("¡×cÇöÀç Á¤È¸¿ø ÀÚ¸®°¡ ¸ðµÎ Â÷¼­ Á¤È¸¿ø ±ÇÇÑÀ» Áö±ÞÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×cÁ¤È¸¿ø ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "Á¤È¸¿ø");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×dµðÀÚÀÌ³Ê ¡×f±ÇÇÑ Áö±Þ")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 3) <= GuildAPI.getClassAmount(name, "µðÀÚÀÌ³Ê")) {
        						p.closeInventory();
        						p.sendMessage("¡×cÇöÀç µðÀÚÀÌ³Ê ÀÚ¸®°¡ ¸ðµÎ Â÷¼­ µðÀÚÀÌ³Ê ±ÇÇÑÀ» Áö±ÞÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×cµðÀÚÀÌ³Ê ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "µðÀÚÀÌ³Ê");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("¸Å´ÏÀú ±ÇÇÑ ¼³Á¤ Ã¢")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (main.ClickAccept.get(p) != null) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×eÀ¯Àú ¡×f±ÇÇÑ Áö±Þ")) {
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×cÀ¯Àú ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "À¯Àú");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×aÁ¤È¸¿ø ¡×f±ÇÇÑ Áö±Þ")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 2) <= GuildAPI.getClassAmount(name, "Á¤È¸¿ø")) {
        						p.closeInventory();
        						p.sendMessage("¡×cÇöÀç Á¤È¸¿ø ÀÚ¸®°¡ ¸ðµÎ Â÷¼­ Á¤È¸¿ø ±ÇÇÑÀ» Áö±ÞÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×cÁ¤È¸¿ø ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "Á¤È¸¿ø");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×dµðÀÚÀÌ³Ê ¡×f±ÇÇÑ Áö±Þ")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 3) <= GuildAPI.getClassAmount(name, "µðÀÚÀÌ³Ê")) {
        						p.closeInventory();
        						p.sendMessage("¡×cÇöÀç µðÀÚÀÌ³Ê ÀÚ¸®°¡ ¸ðµÎ Â÷¼­ µðÀÚÀÌ³Ê ±ÇÇÑÀ» Áö±ÞÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×cµðÀÚÀÌ³Ê ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "µðÀÚÀÌ³Ê");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×c½ºÅÇ ¡×f±ÇÇÑ Áö±Þ")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getClassConfig(name, GuildAPI.getUpGrade(name), 3) <= GuildAPI.getClassAmount(name, "½ºÅÇ")) {
        						p.closeInventory();
        						p.sendMessage("¡×cÇöÀç ½ºÅÇ ÀÚ¸®°¡ ¸ðµÎ Â÷¼­ ½ºÅÇ ±ÇÇÑÀ» Áö±ÞÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + main.ClickAccept.get(p) + " ¡×6´ÔÀÇ ±ÇÇÑÀ» ¡×c½ºÅÇ ¡×6·Î º¯°æ½ÃÄ×½À´Ï´Ù.");
        					GuildAPI.setUserClass(GuildAPI.getJoinGuild(p), main.ClickAccept.get(p), "½ºÅÇ");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå¸¦ Å»ÅðÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, Å»ÅðÇÏ°Ú½À´Ï´Ù.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String guild = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(guild)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("¡×cÇöÀç °¡ÀÔµÈ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå Å»Åð°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					String name = GuildAPI.getJoinGuild(p);
						GuildAPI.subUser(name, p);
						p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + name + "¡×6 ±æµå¿¡¼­ Å»ÅðÇÏ¼Ì½À´Ï´Ù.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("¡×c±æµå¿¡ ¡×e[ " + p.getName() + " ] ¡×c´ÔÀÌ Å»ÅðÇÏ¼Ì½À´Ï´Ù.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, Å»ÅðÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå¸¦ Æó¼âÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, Æó¼âÇÏ°Ú½À´Ï´Ù.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String guild = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(guild)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("¡×cÇöÀç °¡ÀÔµÈ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå Æó¼â°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					String name = GuildAPI.getJoinGuild(p);
    					GuildYml.deleteGuild(name, p);
						p.sendMessage("¡×4¼º°øÀûÀ¸·Î ¡×c" + name + "¡×4 ±æµå¸¦ Æó¼âÇÏ¼Ì½À´Ï´Ù.");
						Bukkit.broadcastMessage("¡×f[¡×4¾Ë¸²¡×f] ¡×c" + name + " ¡×6±æµå¸¦ ¡×c" + p.getName() + " ¡×6´ÔÀÌ Æó¼âÇÏ¼Ì½À´Ï´Ù.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
						p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, Æó¼âÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ÀüÀï¿¡¼­ Ç×º¹ÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, Ç×º¹ÇÏ°Ú½À´Ï´Ù.")) {
    					String name = GuildAPI.getJoinGuild(p);
    					String emeny = GuildAPI.getWarGuild(name);
    					String emenymaster = GuildAPI.getManager(emeny);
    					
    					for (Player pl : GuildAPI.getOnLineUser(name)) {
    						pl.sendMessage("");
    						pl.sendMessage("¡×c±æµå ¸¶½ºÅÍ°¡ ±æµå ÀüÀï¿¡¼­ ¡×fÇ×º¹¡×cÀ» ¼±¾ðÇß½À´Ï´Ù.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					for (Player pl : GuildAPI.getOnLineUser(emeny)) {
    						pl.sendMessage("");
    						pl.sendMessage("¡×6»ó´ë¹æ ±æµå ¸¶½ºÅÍ°¡ ±æµå ÀüÀï¿¡¼­ ¡×cÇ×º¹¡×6À» ¼±¾ðÇß½À´Ï´Ù.");
    						if (emenymaster.equalsIgnoreCase(pl.getName()))
    							pl.sendMessage("¡×e[ /±æµå -> ÀüÀï Ç×º¹ ¼ö¶ô/°ÅÀý ] ¡×6À¸·Î Ç×º¹À» ¼ö¶ô ¶Ç´Â °ÅÀýÇÏ¼¼¿ä.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					GuildAPI.setSubmit(emeny, true);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
						p.closeInventory();
						Player ply = Method.getOnorOffLine(emenymaster);
						if (ply != null) ply.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, Ç×º¹ÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("±æµå¿¡ ÃÊ´ë¹ÞÀ¸¼Ì½À´Ï´Ù. ¼ö¶ôÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, ¼ö¶ôÇÏ°Ú½À´Ï´Ù.")) {
    					String name = GuildAPI.getJoinGuild(main.OkInvite.get(p));
    					if (GuildAPI.isWar(name)) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("¡×cÇöÀç °¡ÀÔÇÒ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå °¡ÀÔÀÌ ºÒ°¡´ÉÇÕ´Ï´Ù.");
    						p.closeInventory();
    						main.OkInvite.remove(p);
    						return;
    					}
    					
    					for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("¡×a±æµå¿¡ ¡×e[ " + p.getName() + " ] ¡×a´ÔÀÌ °¡ÀÔÇÏ¼Ì½À´Ï´Ù.");
						GuildAPI.addUser(name, p);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) Method.castLvup(p);
						p.sendMessage("¡×6¼º°øÀûÀ¸·Î ±æµå¿¡ °¡ÀÔÇÏ¼Ì½À´Ï´Ù.");
						main.OkInvite.remove(p);
						p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, ¼ö¶ôÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					main.OkInvite.get(p).sendMessage("¡×c" + p.getName() + "´ÔÀÌ ±æµå ÃÊ´ë¸¦ °ÅÀýÇÏ¼Ì½À´Ï´Ù.");
    					main.OkInvite.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå ¿µÅä¸¦ »ý¼ºÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, »ý¼ºÇÏ°Ú½À´Ï´Ù.")) {
						double money = main.LandMoney.get(p);
    					if (main.LandMoney.get(p) != null) main.LandMoney.remove(p);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) Method.castLvup(p);
						p.closeInventory();
						
						main.economy.withdrawPlayer(p.getName(), money);
						String name = GuildAPI.getJoinGuild(p);
						for (Player pls : GuildAPI.getOnLineUser(name)) {
							pls.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + name + " ¡×6±æµåÀÇ ¿µÅä°¡ ¼º¼ºµÇ¾ú½À´Ï´Ù.");
							pls.sendMessage("¡×f- ¡×c" + main.pos1.get(p) + " * " + main.pos2.get(p));
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
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, »ý¼ºÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					if (main.LandMoney.get(p) != null) main.LandMoney.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ±æµå ¿µÅä¸¦ Á¦°ÅÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, Á¦°ÅÇÏ°Ú½À´Ï´Ù.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String guild = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(guild)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("¡×cÇöÀç ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå ¿µÅä Á¦°Å°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
	        					p.closeInventory();
								return;
							}
						}
    					
						String name = GuildAPI.getJoinGuild(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						p.closeInventory();
						
						for (Player pls : GuildAPI.getOnLineUser(name)) {
							pls.sendMessage("¡×c" + name + " ¡×6±æµåÀÇ ¿µÅä°¡ ±æµåÀå¿¡ ÀÇÇØ ¡×c¼Ò¸ê¡×6ÇÏ¿´½À´Ï´Ù.");
						}
						
						GuildAPI.setGuildLand(name, false);
						GuildAPI.setLandPos1(name, "999999999,999999999,999999999");
						GuildAPI.setLandPos2(name, "999999999,999999999,999999999");
						GuildAPI.setLandAcceptUse(name, -1);
						GuildAPI.setLandAcceptBlock(name, -1);
						GuildAPI.setLandHome(name, "999999999,999999999,999999999,0,0");
						GuildAPI.setLandPvPType(name, false);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, Á¦°ÅÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Á¤¸»·Î ÀÌ ±æµå¿Í ÀüÀïÇÏ½Ã°Ú½À´Ï±î?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, ÀüÀïÇÏ°Ú½À´Ï´Ù.")) {
						String attack = GuildAPI.getJoinGuild(p);
						String safe = ChatColor.stripColor(e.getInventory().getItem(13).getItemMeta().getDisplayName());
						
    					if (GuildAPI.getJoinGuild(p) != null) {
							if (GuildAPI.isWar(attack)) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("¡×cÀÌ¹Ì ±æµå°¡ ÀüÀïÁßÀÌ¹Ç·Î ÀüÀï ¼±Æ÷¸¦ ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					if (GuildAPI.getUserInteger(attack) < 10) {
    						p.sendMessage("¡×c´ç½ÅÀÇ ±æµå ÀÎ¿ø¼ö°¡ 10¸í ÀÌÇÏÀÌ¹Ç·Î ÀüÀïÀ» ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        					p.closeInventory();
							return;
    					} else {
    						if (GuildAPI.getUserInteger(safe) < 10) {
    							p.sendMessage("¡×c»ó´ë¹æÀÇ ±æµå ÀÎ¿ø¼ö°¡ 10¸í ÀÌÇÏÀÌ¹Ç·Î ÀüÀïÀ» ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
    					if (!GuildAPI.getGuildLand(attack)) {
    						p.sendMessage("¡×c´ç½ÅÀÇ ±æµå ¿µÅä°¡ Á¸ÀçÇÏÁö ¾Æ´ÏÇÏ¿© ÀüÀïÀ» ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        					p.closeInventory();
							return;
    					} else {
    						if (!GuildAPI.getGuildLand(safe)) {
    							p.sendMessage("¡×c»ó´ë¹æÀÇ ±æµå ¿µÅä°¡ Á¸ÀçÇÏÁö ¾Æ´ÏÇÏ¿© ÀüÀïÀ» ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
			        	Bukkit.broadcastMessage("");
			        	Bukkit.broadcastMessage("¡×f[¡×4¾Ë¸²¡×f] ¡×c" + attack + " ¡×6±æµå°¡ ¡×c" + safe + " ¡×6±æµå¿¡°Ô ÀüÀï ¼±ÀüÆ÷°í¸¦ ¼±¾ðÇß½À´Ï´Ù.");
			        	Bukkit.broadcastMessage("");
			        	
			        	for (Player pl : Bukkit.getOnlinePlayers())
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
			        	
			        	GuildAPI.setWarGuild(attack, safe);
			        	GuildAPI.setWarGuild(safe, attack);
			        	
			        	for (Player pl : GuildAPI.getOnLineUser(attack)) PartyAPI.AutoKickPlayer(pl);
			        	for (Player pl : GuildAPI.getOnLineUser(safe)) PartyAPI.AutoKickPlayer(pl);
			        	p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, ÀüÀïÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("¼³Á¤ÇÒ ¾ÆÀÌÅÛ ÄÚµå¸¦ Å¬¸¯ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (main.SetItemCode.get(p) != null) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
            			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
            				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
            					
            				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
            					GuildGUI.MoptionGUI(p);
        						
            				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
            					GuildGUI.DoptionGUI(p);
        						
            				else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
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
        			p.sendMessage("¡×6¼º°øÀûÀ¸·Î Á¤º¸ ¾ÆÀÌÅÛ ÄÚµå¸¦ ¡×c" + bldr + " ¡×6À¸·Î º¯°æÇÏ¼Ì½À´Ï´Ù.");
        			p.closeInventory();
        			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("±æµå ¿µÅä ¸Þ´º")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6¡×l±æµå »óÁ¡")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					p.chat("/±æµå»óÁ¡");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×c¡×l±æµå ¿µÅä »èÁ¦")) {
    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    						GuildGUI.LandRemoveGUI(p, GuildAPI.getJoinGuild(p));
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a±æµå »óÀÚ, È­·Î ±ÇÇÑ ¿©ºÎ")) {
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
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×b±æµå ºí·° ¼³Ä¡, Á¦°Å ¿©ºÎ")) {
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
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×c±æµå ¿µÅä ³»¿¡¼­ PvP ¿©ºÎ")) {
    					String guild = GuildAPI.getJoinGuild(p);
    					boolean type = GuildAPI.getLandPvPType(guild);
    					
    					GuildAPI.setLandPvPType(guild, !type);
    					GuildGUI.GuildMenuGUI(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a¡×l±æµå È¨ ÀÌµ¿")) {
    					String name = GuildAPI.getJoinGuild(p);
    					if (GuildAPI.getGuildLand(name)) {
    						String[] home = GuildAPI.getLandHome(name).split(",");
    						Location l = new Location(Bukkit.getWorld("world_guild"), Double.parseDouble(home[0]), 
    						Double.parseDouble(home[1]), Double.parseDouble(home[2]), Float.parseFloat(home[3]), Float.parseFloat(home[4]));
    						p.closeInventory();
    						p.teleport(l);
    					}
    				}
    				
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fµÚ·Î °¡±â")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×e¡×l¿µÅä È¨ ÁöÁ¤")) {
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
    						p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¼­ÀÖ´Â À§Ä¡°¡ È¨À¸·Î ¡×cÁöÁ¤¡×6µÇ¾ú½À´Ï´Ù.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					} else {
    						p.sendMessage("¡×c±æµå ¿µÅä ¾ÈÀÌ ¾Æ´Ñ °÷Àº È¨À¸·Î ÁöÁ¤ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					}
    				}
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
         	if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("±æµå") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("·©Å·")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6´ÙÀ½ ·©Å· È®ÀÎ")) {
    						GuildGUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6ÀÌÀü ·©Å· È®ÀÎ")) {
    						GuildGUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[1]);
    						if (GuildAPI.isGuild(name)) {
    							if (GuildAPI.getJoinGuild(p) == null) {
        							if (e.getAction() == InventoryAction.PICKUP_ALL) {
        								if (GuildAPI.getJoinList(name).contains(p.getName())) {
        									p.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ÀÌ ±æµå¿¡ ½ÅÃ»¼­¸¦ ³ÖÀ¸¼Ì½À´Ï´Ù.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        			    					p.closeInventory();
        									return;
        								}
        								
        								if (GuildAPI.isWar(name)) {
        									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									p.sendMessage("¡×cÇöÀç °¡ÀÔÇÒ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå °¡ÀÔÀÌ ºÒ°¡´ÉÇÕ´Ï´Ù.");
        									p.closeInventory();
        									return;
        								}
        								
        								if (GuildAPI.getJoinType(name).equalsIgnoreCase("°ø°³")) {
        		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        		        						p.closeInventory();
        		        						p.sendMessage("¡×cÀÌ ±æµå´Â ÇöÀç ÃÖ´ë ÀÎ¿ø ¼ö¿ë ÇÑµµ°¡ ÃÊ°úµÇ¾î °¡ÀÔÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		        						return;
        		        					}
        		        					
        									for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("¡×a±æµå¿¡ ¡×e[ " + p.getName() + " ] ¡×a´ÔÀÌ °¡ÀÔÇÏ¼Ì½À´Ï´Ù.");
        									GuildAPI.addUser(name, p);
        									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) Method.castLvup(p);
        									p.sendMessage("¡×6¼º°øÀûÀ¸·Î ±æµå¿¡ °¡ÀÔÇÏ¼Ì½À´Ï´Ù.");
        									p.closeInventory();
        								}
        								else if (GuildAPI.getJoinType(name).equalsIgnoreCase("½ÅÃ»¼­")) {
        									if (GuildAPI.isWar(name)) {
            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            									p.sendMessage("¡×cÇöÀç °¡ÀÔÇÒ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå °¡ÀÔÀÌ ºÒ°¡´ÉÇÕ´Ï´Ù.");
            									p.closeInventory();
            									return;
            								}
        									
        		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        		        						p.closeInventory();
        		        						p.sendMessage("¡×cÀÌ ±æµå´Â ÇöÀç ÃÖ´ë ÀÎ¿ø ¼ö¿ë ÇÑµµ°¡ ÃÊ°úµÇ¾î °¡ÀÔÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		        						return;
        		        					}
        		        					
        									GuildAPI.addJoinList(name, p.getName());
        									p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + name + "¡×6 ±æµå¿¡ ½ÅÃ»¼­¸¦ ÀÛ¼ºÇÏ¼Ì½À´Ï´Ù.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        									p.closeInventory();
        									
        									for (Player pl : Bukkit.getOnlinePlayers()) {
        										for (String str : GuildAPI.getClassPlayer(name, pl.getName(), "½ºÅÇ")) {
        											if (pl.getName().equalsIgnoreCase(str)) {
            											pl.sendMessage("¡×6´ç½ÅÀÇ ±æµå¿¡ ¡×c" + p.getName() + " ¡×6´ÔÀÌ °¡ÀÔ ½ÅÃ»¼­¸¦ ÀÛ¼ºÇÏ¼Ì½À´Ï´Ù.");
        											}
        										}
        											
        										if (pl.getName().equalsIgnoreCase(GuildAPI.getManager(name)))
        											pl.sendMessage("¡×6´ç½ÅÀÇ ±æµå¿¡ ¡×c" + p.getName() + " ¡×6´ÔÀÌ °¡ÀÔ ½ÅÃ»¼­¸¦ ÀÛ¼ºÇÏ¼Ì½À´Ï´Ù.");
        									}
        								}
        								else if (GuildAPI.getJoinType(name).equalsIgnoreCase("ºñ°ø°³")) {
        									p.sendMessage("¡×cÀÌ ±æµå´Â ºñ°ø°³ µÇ¾îÀÖ¾î °¡ÀÔ ½ÅÃ»¼­¸¦ Á¦ÃâÇÒ ¼ö ¾ø½À´Ï´Ù.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									p.closeInventory();
        									return;
        								}
        							}
    							}
    							
    							else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
        							if (e.getAction() == InventoryAction.PICKUP_HALF) { // ±æµå ÀüÀï ºÎºÐ
        								if (GuildAPI.isWar(name)) {
        									p.sendMessage("¡×cÀÌ¹Ì ±× ±æµå´Â ÀüÀïÀÌ ÁøÇàÁßÀÌ¿©¼­ ÀüÀï ¼±ÀüÆ÷°í¸¦ ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
        								}
        								
        								if (GuildAPI.getJoinGuild(p).equalsIgnoreCase(name)) {
        									p.sendMessage("¡×cÀÚ½ÅÀÇ ±æµå¿Í ÀüÀïÇÏ½Ã°Ú½À´Ï±î? ¾ÈÅ¸±õ°Ôµµ ±×°ÍÀº ºÒ°¡´ÉÇÕ´Ï´Ù.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
        							         
        							        // ½Ã°£Â÷ÀÌ¸¦ ½Ã°£,ºÐ,ÃÊ¸¦ °öÇÑ °ªÀ¸·Î ³ª´©¸é ÇÏ·ç ´ÜÀ§°¡ ³ª¿È
        							        long diff = endDate.getTime() - beginDate.getTime();
        							        long diffDays = diff / (24 * 60 * 60 * 1000);
        							 
        							        if (diffDays > 7) {
                								try {
                							        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy,MM,dd");
                							        Date beginDate2 = formatter2.parse(endate);
                							        Date endDate2 = formatter2.parse(day);
                							         
                							        // ½Ã°£Â÷ÀÌ¸¦ ½Ã°£,ºÐ,ÃÊ¸¦ °öÇÑ °ªÀ¸·Î ³ª´©¸é ÇÏ·ç ´ÜÀ§°¡ ³ª¿È
                							        long diff2 = endDate2.getTime() - beginDate2.getTime();
                							        long diffDays2 = diff2 / (24 * 60 * 60 * 1000);
                							 
                							        if (diffDays2 > 7) {
                							        	GuildGUI.WarCheckGUI(p, name);
                				    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                							        } else {
                							        	p.sendMessage("¡×c»ý¼º ³¯Â¥°¡ 8ÀÏ ¹Ì¸¸ÀÎ ±æµå¿Í´Â ÀüÀïÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
                    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                							        }
                							    } catch (ParseException ev) {
                							        ev.printStackTrace();
                							    }
        							        } else {
        							        	p.sendMessage("¡×c´ç½Å ±æµå´Â »ý¼º ³¯Â¥°¡ 8ÀÏ ¹Ì¸¸ÀÌ¹Ç·Î ±æµå ÀüÀïÀ» ¼±Æ÷ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
            			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("¾çµµÇÒ") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("À¯Àú") && e.getInventory().getName().split(" ")[2].equalsIgnoreCase("¸ñ·Ï")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6´ÙÀ½ ¸ñ·Ï È®ÀÎ")) {
    						GuildGUI.giveMasterListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6ÀÌÀü ¸ñ·Ï È®ÀÎ")) {
    						GuildGUI.giveMasterListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(player)) {
    		    						p.sendMessage("¡×cÀÚ½Å¿¡°Ô ±æµå ÀåÀ» ¾çµµÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (GuildAPI.getJoinGuild(p) != null) {
		    							String guild = GuildAPI.getJoinGuild(p);
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("¡×c±æµå°¡ ÀüÀïÁßÀÎ »óÅÂ¿¡¼­´Â ±æµå ¾çµµ°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
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
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("À¯Àú") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("¸ñ·Ï")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6´ÙÀ½ ¸ñ·Ï È®ÀÎ")) {
    						GuildGUI.userListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6ÀÌÀü ¸ñ·Ï È®ÀÎ")) {
    						GuildGUI.userListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ")) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getUserClass(name, pl.getName()).equalsIgnoreCase("½ºÅÇ") || GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("¡×c´ç½ÅÀº ÀÌ ÇÃ·¹ÀÌ¾î¸¦ °­Á¦ ÅðÀå½ÃÅ³ ¼ö ¾ø½À´Ï´Ù.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (GuildAPI.getJoinGuild(p) != null) {
		    							String guild = GuildAPI.getJoinGuild(p);
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("¡×cÇöÀç °¡ÀÔµÈ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå °­Á¦ ÅðÀåÀÌ ºÒ°¡´ÉÇÕ´Ï´Ù.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl.getPlayer());
    								p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + name + "¡×6 ±æµå¿¡¼­ °­Á¦ ÅðÀå ½ÃÄ×½À´Ï´Ù.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("¡×c´ç½ÅÀº " + name + " ±æµå¿¡¼­ °­Á¦ ÅðÀå ´çÇÏ¼Ì½À´Ï´Ù.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "È¿°úÀ½")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("¡×c±æµå¿¡ ¡×e[ " + e.getCurrentItem().getItemMeta().getDisplayName() + " ] ¡×c´ÔÀÌ °­Á¦ ÅðÀå ´çÇÏ¼Ì½À´Ï´Ù.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getUserClass(name, player).equalsIgnoreCase("½ºÅÇ") || GuildAPI.getManager(name).equalsIgnoreCase(player)) {
    		    						p.sendMessage("¡×c´ç½ÅÀº ÀÌ ÇÃ·¹ÀÌ¾îÀÇ °è±ÞÀ» ¼öÁ¤ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					main.ClickAccept.put(p, player);
    		    					GuildGUI.StepAcceptGUI(p);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    						}
    						
    						else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("¡×c´ç½ÅÀº ÀÌ ÇÃ·¹ÀÌ¾î¸¦ °­Á¦ ÅðÀå½ÃÅ°½Ç ¼ö ¾ø½À´Ï´Ù.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (GuildAPI.getJoinGuild(p) != null) {
		    							String guild = GuildAPI.getJoinGuild(p);
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("¡×cÇöÀç °¡ÀÔµÈ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå °­Á¦ ÅðÀåÀÌ ºÒ°¡´ÉÇÕ´Ï´Ù.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl);
    								p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + name + "¡×6 ±æµå¿¡¼­ °­Á¦ ÅðÀå ½ÃÄ×½À´Ï´Ù.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("¡×c´ç½ÅÀº " + name + " ±æµå¿¡¼­ °­Á¦ ÅðÀå ´çÇÏ¼Ì½À´Ï´Ù.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "È¿°úÀ½")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("¡×c±æµå¿¡ ¡×e[ " + p.getName() + " ] ¡×c´ÔÀÌ °­Á¦ ÅðÀå ´çÇÏ¼Ì½À´Ï´Ù.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(player)) {
    		    						p.sendMessage("¡×c´ç½ÅÀº ÀÌ ÇÃ·¹ÀÌ¾îÀÇ °è±ÞÀ» ¼öÁ¤ÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					main.ClickAccept.put(p, player);
    		    					GuildGUI.ManegerAcceptGUI(p);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("°¡ÀÔ½ÅÃ»¼­") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("¸ñ·Ï")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
        					if (GuildAPI.getJoinGuild(p) != null) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6´ÙÀ½ ¸ñ·Ï È®ÀÎ")) {
    						GuildGUI.joinListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6ÀÌÀü ¸ñ·Ï È®ÀÎ")) {
    						GuildGUI.joinListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ") || GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p);
    								
		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
		        						p.closeInventory();
		        						p.sendMessage("¡×c±æµåÀÇ ÀÎ¿ø¼ö°¡ ÃÊ°úµÇ¾î ÇÃ·¹ÀÌ¾îÀÇ °¡ÀÔÀ» ½ÂÀÎÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		        						return;
		        					}
		        					
		        					if (GuildAPI.isWar(name)) {
    									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    									p.sendMessage("¡×cÇöÀç ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î °¡ÀÔ ½ÅÃ»À» ¹ÞÀ¸½Ç ¼ö ¾ø½À´Ï´Ù.");
    									p.closeInventory();
    									return;
    								}
		        					
    								if (Method.getOnorOffLine(pl) != null) {
    									Method.getOnorOffLine(pl).sendMessage("¡×6´ç½ÅÀº ¡×c" + name + " ¡×6±æµå¿¡ ¼º°øÀûÀ¸·Î °¡ÀÔÇÏ¼Ì½À´Ï´Ù.");
    								}
		        					
    								OfflinePlayer pp = Bukkit.getOfflinePlayer(pl);
									for (Player ply : GuildAPI.getOnLineUser(name)) ply.sendMessage("¡×a±æµå¿¡ ¡×e[ " + p.getName() + " ] ¡×a´ÔÀÌ °¡ÀÔÇÏ¼Ì½À´Ï´Ù.");
									GuildAPI.addUser(name, pp.getPlayer());
    		    					GuildAPI.subJoinList(name, pl);
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    		    					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + pl + " ¡×6´ÔÀÇ °¡ÀÔÀ» ½ÂÀÎÇÏ¼Ì½À´Ï´Ù.");
    		    					GuildGUI.joinListGUI(p, 1);
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p);
    		    					
    								if (Method.getOnorOffLine(pl) != null) {
    									Method.getOnorOffLine(pl).sendMessage("¡×c´ç½ÅÀº " + name + " ±æµå¿¡ °¡ÀÔÇÏÁö ¸øÇÏ¼Ì½À´Ï´Ù.");
    								}
    								
    								GuildAPI.subJoinList(name, pl);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    		    					p.sendMessage("¡×c¼º°øÀûÀ¸·Î " + pl + " ´ÔÀÇ °¡ÀÔÀ» °ÅºÎÇÏ¼Ì½À´Ï´Ù.");
    		    					GuildGUI.joinListGUI(p, 1);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("Á¤¸»·Î") && e.getInventory().getName().split(" ")[2].equalsIgnoreCase("´Ô¿¡°Ô") && e.getInventory().getName().split(" ")[3].equalsIgnoreCase("±æµåÀåÀ»") && e.getInventory().getName().split(" ")[4].equalsIgnoreCase("¾çµµÇÏ½Ã°Ú½À´Ï±î?")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		Player player = Method.getOnorOffLine(e.getInventory().getName().split(" ")[1]);
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¿¹, ¾çµµÇÏ°Ú½À´Ï´Ù.")) {
        					if (player == null) {
        						p.sendMessage("¡×cERROR: ¾çµµÇÒ ÇÃ·¹ÀÌ¾î°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
            					p.closeInventory();
        					} else {
        						if (GuildAPI.getJoinGuild(p) != null) {
	    							String guild = GuildAPI.getJoinGuild(p);
	    							if (GuildAPI.isWar(guild)) {
	    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	    	        					p.sendMessage("¡×cÇöÀç °¡ÀÔµÈ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµåÀå ¾çµµ°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
	    								return;
	    							}
	    						}
        						
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "È¿°úÀ½")) player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					p.closeInventory();
            					p.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + player.getName() + " ¡×6´Ô¿¡°Ô ±æµåÀåÀ» ¾çµµÇÏ¼Ì½À´Ï´Ù.");
            					player.sendMessage("¡×6´ç½ÅÀº ¡×c" + GuildAPI.getJoinGuild(p) + " ¡×6±æµå ¸¶½ºÅÍ¸¦ ¡×c¾çµµ¡×6¹ÞÀ¸¼Ì½À´Ï´Ù.");
            					GuildAPI.changeGuildMaster(p, player);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f¾Æ´Ï¿ä, ¾çµµÇÏÁö ¾Ê°Ú½À´Ï´Ù.")) {
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        				}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("¡×6¡×lG")) {
         		e.setCancelled(true);
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6±æµå ·©Å·")) {
        					GuildGUI.RankingGUI(p, 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÃ¢ ´Ý±â")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					p.closeInventory();
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×c±æµå Å»Åð")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.CheckQuitGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×6¡×l±æµå »óÁ¡")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					p.chat("/±æµå»óÁ¡");
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a±æµå ½ºÅÝ")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
	    						GuildGUI.setStatGUI(p);
	    					} else {
	    						GuildGUI.userStatGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×e±æµå À¯Àú ¸ñ·Ï")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.userListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×b±æµå Ã¤ÆÃ")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					String str = Method.NextChatMode(p, GuildAPI.getPlayerChat(p));
	    					GuildAPI.setPlayerChat(p, str);
	    					
	    					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("À¯Àú"))
	    						GuildGUI.userGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("Á¤È¸¿ø"))
	    						GuildGUI.MemberGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê"))
	    						GuildGUI.DesignerGuildGUI(p);
	    					
	    					else if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×2ÇÃ·¹ÀÌ¾î ÃÊ´ë")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        						p.closeInventory();
        						p.sendMessage("¡×cÇöÀç ÃÖ´ë ÀÎ¿ø ¼ö¿ë ÇÑµµ°¡ ÃÊ°úµÇ¾î ´õÀÌ»ó ÇÃ·¹ÀÌ¾î¸¦ ÃÊ´ëÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (GuildAPI.isWar(name)) {
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								p.sendMessage("¡×cÇöÀç °¡ÀÔÇÒ ±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå ÃÊ´ë°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
								p.closeInventory();
								return;
							}
        					
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					p.closeInventory();
	    					p.sendMessage("¡×6´©±¸¸¦ ¡×cÃÊ´ë¡×6ÇÏ½Ã°Ú½À´Ï±î? Ã¤ÆÃÃ¢¿¡ Àû¾îÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
	    					p.sendMessage("¡×f- ¡×c\"Ãë¼Ò\" ¶ó°í ÀÔ·Â½Ã Ãë¼Ò°¡ °¡´ÉÇÕ´Ï´Ù.");
        					main.InvitePlayer.add(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f±æµå ¿É¼Ç")) {
	    					if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("µðÀÚÀÌ³Ê")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.DoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a¡×l±æµå ¶¥ ¸Þ´º")) {
        					if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
        						GuildGUI.GuildMenuGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a¡×l±æµå È¨ ÀÌµ¿")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.getGuildLand(name)) {
        						String[] home = GuildAPI.getLandHome(name).split(",");
        						Location l = new Location(Bukkit.getWorld("world_guild"), Double.parseDouble(home[0]), 
        						Double.parseDouble(home[1]), Double.parseDouble(home[2]), Float.parseFloat(home[3]), Float.parseFloat(home[4]));
        						p.closeInventory();
        						p.teleport(l);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÀüÀï Ç×º¹ÇÏ±â")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.isWar(name)) {
        						GuildGUI.WarSubmitGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×fÀüÀï Ç×º¹ ¼ö¶ô/°ÅÀý")) {
        					String name = GuildAPI.getJoinGuild(p);
        					if (GuildAPI.isWar(name)) {
        						if (GuildAPI.isSubmit(name)) {
            						if (e.getAction() == InventoryAction.PICKUP_HALF) {
            	    					String emeny = GuildAPI.getWarGuild(name);
            	    					
            							for (Player pl : Bukkit.getOnlinePlayers()) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("¡×e[ " + name + " vs " + emeny + " ] ¡×6±æµåÀÇ ÀüÀï¿¡¼­ ¡×c" + name + " ¡×6±æµå°¡ ½Â¸®ÇÏ¿´½À´Ï´Ù!");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.5F);
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
            	    						pl.sendMessage("¡×6±æµå ¸¶½ºÅÍ°¡ ±æµå Ç×º¹À» ¡×c°ÅÀý¡×6 ÇÏ¿´½À´Ï´Ù.");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.5F);
            	    					}
            	    					
            	    					for (Player pl : GuildAPI.getOnLineUser(emeny)) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("¡×c»ó´ë¹æ ±æµå°¡ ÀüÀï Ç×º¹À» °ÅÀýÇÏ¿´½À´Ï´Ù.");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "È¿°úÀ½")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            	    					}
            	    					
            	    					p.closeInventory();
            	    					Player ply = Method.getOnorOffLine(GuildAPI.getManager(emeny));
            							if (ply != null) ply.closeInventory();
            	    					GuildAPI.setSubmit(name, false);
            						}
        						}
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×a±æµå ¶¥ »ý¼º")) {
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
                        							p.sendMessage("¡×c" + i + "¿øÀÌ ºÎÁ·ÇÏ¿©¼­ ±æµå ¿µÅä¸¦ ±¸¸ÅÇÏ½Ç ¼ö ¾ø½À´Ï´Ù.");
                            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                            						p.closeInventory();
                            						return;
                        						}
                							} else {
                    							p.sendMessage("¡×cÁÖº¯ 60 ºí·°¿¡ ±æµå ¿µÅä°¡ Á¸ÀçÇÕ´Ï´Ù. ´Ù¸¥ °÷À» ÁöÁ¤ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
                        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                        						p.closeInventory();
                        						return;
                    						}
            								
            							} else {
                							p.sendMessage("¡×cÁÖº¯ 60 ºí·°¿¡ ±æµå ¿µÅä°¡ Á¸ÀçÇÕ´Ï´Ù. ´Ù¸¥ °÷À» ÁöÁ¤ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
                    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                    						p.closeInventory();
                    						return;
                						}
            						} else {
            							p.sendMessage("¡×cÀÚ½ÅÀÌ ÁöÁ¤ÇÑ Æ÷Áö¼Ç ¾È¿¡¼­ ¾ÆÀÌÅÛÀ» Å¬¸¯ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
                						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                						p.closeInventory();
                						return;
            						}
        						} else {
            						p.sendMessage("¡×c±æµå ¿µÅä ¿ùµå·Î °¡¼­ ¾ÆÀÌÅÛÀ» Å¬¸¯ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            						p.closeInventory();
            						return;
            					}
        					} else {
        						p.sendMessage("¡×c±æµå ¿µÅä ¿ùµå·Î °¡¼­ Æ÷Áö¼ÇÀ» ÁöÁ¤ÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						p.closeInventory();
        						return;
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f±æµå ¿É¼Ç ¡×7/ ¡×f°¡ÀÔ½ÅÃ»¼­ ¸ñ·Ï")) {
	    					if (GuildAPI.getUserClass(GuildAPI.getJoinGuild(p), p.getName()).equalsIgnoreCase("½ºÅÇ")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.SoptionGUI(p);
	    					}
	    					
	    					else if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
	    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.MoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×e±æµå ¾çµµ")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.giveMasterListGUI(p, 1);
        				}
        				
        				if (GuildAPI.getManager(GuildAPI.getJoinGuild(p)).equalsIgnoreCase(p.getName())) {
            				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("¡×f[¡×4!¡×f] ¡×4±æµå Æó¼â")) {
    	    					String name = GuildAPI.getJoinGuild(p);
    	    					if (GuildAPI.getUser(name).size() != 1) {
            						p.sendMessage("¡×c¸ðµç À¯Àú¸¦ °­Á¦ ÅðÀå ½ÃÅ°°í Æó¼â¸¦ ÁøÇàÇØ ÁÖ½Ã±â ¹Ù¶ø´Ï´Ù.");
            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            						p.closeInventory();
            						return;
    	    					}
    	    					
    	    					if (GuildAPI.isWar(name)) {
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
									p.sendMessage("¡×c±æµå°¡ ÀüÀïÁßÀÎ »óÅÂÀÌ¹Ç·Î ±æµå Æó¼â°¡ ºÒ°¡´ÉÇÕ´Ï´Ù.");
									p.closeInventory();
									return;
								}
    	    					
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "È¿°úÀ½")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
            					GuildGUI.CheckDeleteGuildGUI(p);
            				}
        				}
        			}
        		}
         	}
     	}
	}
}
