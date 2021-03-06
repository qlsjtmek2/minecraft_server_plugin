package me.espoo.rpg.guild;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.Method;
import me.espoo.rpg.main;
import me.espoo.rpg.party.Party;
import us.talabrek.ultimateskyblock.IslandCommand;
import us.talabrek.ultimateskyblock.PlayerInfo;
import us.talabrek.ultimateskyblock.Settings;
import us.talabrek.ultimateskyblock.uSkyBlock;

@SuppressWarnings("deprecation")
public class GuildEvent extends JavaPlugin implements Listener {
	main M;
	
	public GuildEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();

		if (!p.isOp() && p.getWorld().getName().equalsIgnoreCase("skyworld")) {
			Guild guild = Guild.players.get(p.getName());
			String rank = guild.getUserClass(p.getName());
			int accept = guild.getIsland_Build();
			boolean is = true;
			
			switch (accept) {
				case 1:
					is = false; break;
				case 2:
					if (!rank.equalsIgnoreCase("????")) is = false; break;
				case 3:
					if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) is = false; break;
				case 4:
					if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) is = false; break;
				case 5:
					if (rank.equalsIgnoreCase("??????")) is = false; break;
			} e.setCancelled(is);
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if (!p.isOp() && p.getWorld().getName().equalsIgnoreCase("skyworld")) {
			Guild guild = Guild.players.get(p.getName());
			String rank = guild.getUserClass(p.getName());
			int accept = guild.getIsland_Build();
			boolean is = true;
			
			switch (accept) {
				case 1:
					is = false; break;
				case 2:
					if (!rank.equalsIgnoreCase("????")) is = false; break;
				case 3:
					if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) is = false; break;
				case 4:
					if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) is = false; break;
				case 5:
					if (rank.equalsIgnoreCase("??????")) is = false; break;
			} e.setCancelled(is);
		}
	}
	
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		if (main.CreateGuild.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity entity = e.getEntity();
		if (damager instanceof Player && entity instanceof Player) {
			Player player = (Player) damager;
			Player damaged = (Player) entity;
			String world = player.getWorld().getName();
			
			if (GuildAPI.checkInputOnlyNumberAndAlphabet(damaged.getName())) {
				if (world.equalsIgnoreCase("world_colosseum") || world.equalsIgnoreCase("world_pvp") || world.equalsIgnoreCase("world_pvp2") || world.equalsIgnoreCase("skyworld")) return;
				if (Guild.players.containsKey(player.getName()) && Guild.players.containsKey(damaged.getName())) {
					Guild guild = Guild.players.get(player.getName());
					if (world.equalsIgnoreCase("skyworld")) {
						if (uSkyBlock.getInstance().playerIsOnIsland(player) && !guild.getIsland_PVP()) {
							e.setCancelled(true);
						}
					}
					
					String war = guild.getWar_Guild();
	    			if (war != null && war.equalsIgnoreCase(Guild.players.get(damaged.getName()).getName())) {
	    				e.setCancelled(false);
	    				return;
	    			} else e.setCancelled(true);
	    		} else e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBreakStat(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if (Guild.players.containsKey(p.getName())) {
			Block b = e.getBlock();
			if (b.getType() == Material.MELON_BLOCK || b.getType() == Material.PUMPKIN) {
				Guild guild = Guild.players.get(p.getName());
				if (guild.getStat_Plant() > 0) {
					int stat = guild.getStat_Plant();
					stat /= 2;
					
					if (stat >= (new Random().nextInt(10) + 1)) {
						for (ItemStack item : b.getDrops())
							p.getWorld().dropItemNaturally(b.getLocation(), item);
						p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, 57);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.2F);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.sendMessage("??6???? ?????? ??c???? ?????? ??6?????? ???????? ??c2?? ??6?? ??????????????.");
						return;
					}
				}
			}
			
			else if (b.getType() == Material.CROPS || b.getType() == Material.CARROT || b.getType() == Material.POTATO) {
					if (b.getData() >= 7) {
						Guild guild = Guild.players.get(p.getName());
						if (guild.getStat_Plant() > 0) {
							int stat = guild.getStat_Plant();
							stat /= 2;
							
							if (stat >= (new Random().nextInt(10) + 1)) {
								for (ItemStack item : b.getDrops())
									p.getWorld().dropItemNaturally(b.getLocation(), item);
								p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, 57);
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.2F);
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.sendMessage("??6???? ?????? ??c???? ?????? ??6?????? ???????? ??c2?? ??6?? ??????????????.");
								return;
							}
						}
					}
				}
			
			else if (b.getTypeId() == 1 || b.getTypeId() == 16 || b.getTypeId() == 15 || b.getTypeId() == 14 || b.getTypeId() == 56 || b.getTypeId() == 129) {
				ItemStack i = p.getItemInHand();
				if (i != null && i.hasItemMeta() && i.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) return;
				Guild guild = Guild.players.get(p.getName());
				if (guild.getStat_Mine() > 0) {
					int stat = guild.getStat_Mine();
					if (stat > 1) {
						stat /= 2;
						if (stat >= (new Random().nextInt(10) + 1)) {
							for (ItemStack item : b.getDrops())
								p.getWorld().dropItemNaturally(b.getLocation(), item);
							p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, 57);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.2F);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.sendMessage("??6???? ?????? ??c???? ?????? ??6?????? ???????? ??c2?? ??6?? ??????????????.");
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public static void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
	    if (!p.isOp()) {
		    if (p.getWorld().getName().equalsIgnoreCase("skyworld")) {
		    	Guild guild = Guild.players.get(p.getName());
				
				if (e.getClickedBlock().getType() != Material.ANVIL && e.getClickedBlock().getType() != Material.BEACON && e.getClickedBlock().getType() != Material.CHEST && 
					e.getClickedBlock().getType() != Material.CAKE_BLOCK && e.getClickedBlock().getType() != Material.ENCHANTMENT_TABLE && e.getClickedBlock().getType() != Material.ENDER_CHEST && 
					e.getClickedBlock().getType() != Material.FURNACE && e.getClickedBlock().getType() != Material.BURNING_FURNACE && e.getClickedBlock().getType() != Material.JUKEBOX && 
					e.getClickedBlock().getType() != Material.NOTE_BLOCK && e.getClickedBlock().getType() != Material.TRAPPED_CHEST && e.getClickedBlock().getType() != Material.LEVER && 
					e.getClickedBlock().getType() != Material.STONE_BUTTON && e.getClickedBlock().getType() != Material.WOOD_BUTTON && e.getClickedBlock().getType() != Material.TRAP_DOOR && 
					e.getClickedBlock().getType() != Material.FENCE_GATE && e.getClickedBlock().getType() != Material.ENDER_PORTAL && e.getClickedBlock().getType() != Material.IRON_DOOR && 
					e.getClickedBlock().getType() != Material.WOODEN_DOOR && e.getClickedBlock().getType() != Material.WOOD_DOOR && e.getClickedBlock().getType() != Material.HOPPER && 
					e.getClickedBlock().getType() != Material.HOPPER_MINECART && e.getClickedBlock().getType() != Material.DROPPER && e.getClickedBlock().getType() != Material.DISPENSER && 
					e.getClickedBlock().getType() != Material.CAULDRON && e.getClickedBlock().getType() != Material.BREWING_STAND) return;
				
				String rank = guild.getUserClass(p.getName());
				int accept = guild.getIsland_Chest();
				boolean is = true;
				
				switch (accept) {
					case 1:
						is = false; break;
					case 2:
						if (!rank.equalsIgnoreCase("????")) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("????") && !rank.equalsIgnoreCase("??????")) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("????") || rank.equalsIgnoreCase("??????")) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("??????")) is = false; break;
				} e.setCancelled(is);
		    }
	    }
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Guild guild = Guild.players.get(p.getName());
		
		if (guild != null) {
			if (main.SetItemCode.get(p) != null) main.SetItemCode.remove(p);
			if (main.SetLore.get(p) != null) main.SetLore.remove(p);
			if (main.SetStatMessage.get(p) != null) main.SetStatMessage.remove(p);
			if (main.InvitePlayer.contains(p)) main.InvitePlayer.remove(p);
			if (main.CreateGuild.contains(p)) main.CreateGuild.remove(p);
			
			if (guild.getManager().equalsIgnoreCase(p.getName())) {
				Calendar cal = Calendar.getInstance();
				StringBuilder str = new StringBuilder();
				str.append(cal.get(Calendar.MONTH) + 1);
				str.append(",");
				str.append(cal.get(Calendar.DATE));
				guild.setManagerLastJoin(str.toString());
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
			
			if (Guild.players.containsKey(p.getName())) {
				Guild guild = Guild.players.get(p.getName());
				if (guild.getWar_Guild() != null) {
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
			
			if (Guild.players.containsKey(pl.getName())) {
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
			
			GuildGUI.InviteGUI(pl, Guild.players.get(p.getName()));
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
			
			Guild.players.get(p.getName()).setInfo_StatMessage(StatMessage);
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
					Guild guild = Guild.players.get(p.getName());
					String message = Method.getFinalArg(lore.split(" "), 1);
					message = Method.replaceAllColors(message);
					List<String> list = guild.getInfo_Lore();
					list.add(message);
					guild.setInfo_Lore(list);

					e.setCancelled(true);
					p.sendMessage("");
					p.sendMessage("??6?????????? ?????? ??c???? ??6??????????.");
					p.sendMessage("??6?????? ????: ??f" + message);
					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					Guild guild = Guild.players.get(p.getName());
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
					
					List<String> list = guild.getInfo_Lore();
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						guild.setInfo_Lore(list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("??6?????????? ??c" + removeline + 1 + " ??6???? ?????? ??????????????.");
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
			Guild guild = Guild.players.get(p.getName());
			if (guild == null) {
				GuildAPI.setPlayerChat(p, "????");
				return;
			}
			
			e.setCancelled(true);
			for (Player pl : guild.getOnLineUser()) {
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
    					if (main.economy.getBalance(p.getName()) >= (double) main.createGuildMoney) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
        					p.closeInventory();
        					main.CreateGuild.add(p);
        					p.sendMessage("??6???????? ?????? ??c???? ??????6?? ?????????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = (double) main.createGuildMoney - main.economy.getBalance(p.getName());
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
    		Guild guild = Guild.players.get(p.getName());
    		
    		if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ?????? ??e??l????")) {
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ???? ???????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("??6?????? ???? ???????? ??c????????6?? ?????? ?????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ??e??l????")) {
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, guild.getName());
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
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ?????? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, guild.getName());
    						GuildGUI.SetItemCodeGUI(p);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    		Guild guild = Guild.players.get(p.getName());
    		
    		if (guild.getUserClass(p.getName()).equalsIgnoreCase("????")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ?????? ??e??l????")) {
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ???? ???????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("??6?????? ???? ???????? ??c????????6?? ?????? ?????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ??e??l????")) {
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, guild.getName());
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
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ?????? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, guild.getName());
    						GuildGUI.SetItemCodeGUI(p);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??3???? ????????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					if (guild.getJoinType().equalsIgnoreCase("????")) guild.setJoinType("??????");
	    					else if (guild.getJoinType().equalsIgnoreCase("??????")) guild.setJoinType("??????");
	    					else if (guild.getJoinType().equalsIgnoreCase("??????")) guild.setJoinType("????");
	    					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??9???? ?????? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    		Guild guild = Guild.players.get(p.getName());
    		
    		if (guild.getManager().equalsIgnoreCase(p.getName())) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ?????? ??e??l????")) {
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ???? ???????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("??6?????? ???? ???????? ??c????????6?? ?????? ?????? ????????.");
        					p.sendMessage("??f- ??c\"????\" ???? ?????? ?????? ??????????.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6???? ???? ??e??l????")) {
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, guild.getName());
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
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("??c???? ???? ?????? ?????? ???????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ?????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("??c?????? ???? ???? ???????? ???????? ????????. '????' ?? ???????? ???? ?? ???? ?????? ?????? ????????.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, guild.getName());
    						GuildGUI.SetItemCodeGUI(p);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??3???? ????????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					if (guild.getJoinType().equalsIgnoreCase("????")) guild.setJoinType("??????");
	    					else if (guild.getJoinType().equalsIgnoreCase("??????")) guild.setJoinType("??????");
	    					else if (guild.getJoinType().equalsIgnoreCase("??????")) guild.setJoinType("????");
	    					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??9???? ?????? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e?????? ?????? ??6??l????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					guild.setStat_IsExp(!guild.getStat_IsExp());
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ?????? ??6??l????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					guild.setStat_IsPlant(!guild.getStat_IsPlant());
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??b???? ?????? ??6??l????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					guild.setStat_IsMine(!guild.getStat_IsMine());
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ?????????? ????")) {
	    					int i = guild.getUpgrade() + 1;
	    					int money = Integer.parseInt(main.guildConfig.get(i).split(" ")[1]);
	    					
	    					if (main.guildConfig.size() >= (i + 2)) {
		    					if (main.economy.getBalance(p.getName()) >= money) {
			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
		        					p.closeInventory();
		        					p.sendMessage("??6?????????? ?????? ??c" + i + " ??6?????? ?????????? ??????????.");
		        					guild.setUpgrade(i);
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
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (guild.getManager().equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (guild.getManager().equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e?????? ?????? ??6??l????")) {
    					Guild guild = Guild.players.get(p.getName());
    					if (guild.getStat_Exp() < guild.getMaxExpStat()) {
    						if (guild.getPoint() >= 12000) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							guild.setPoint(guild.getPoint() - 12000);
    							guild.setStat_Exp(guild.getStat_Exp() + 1);
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
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ?????? ??6??l????")) {
    					Guild guild = Guild.players.get(p.getName());
    					if (guild.getStat_Plant() < guild.getMaxPlantStat()) {
    						if (guild.getPoint() >= 12000) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
	        					guild.setPoint(guild.getPoint() - 12000);
    							guild.setStat_Plant(guild.getStat_Plant() + 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("??c???????? ???????? ???? ?????? ?????? ?????? ?? ????????.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??b???? ?????? ??6??l????")) {
    					Guild guild = Guild.players.get(p.getName());
    					if (guild.getStat_Mine() < guild.getMaxMineStat()) {
    						if (guild.getPoint() >= 12000) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
	        					guild.setPoint(guild.getPoint() - 12000);
    							guild.setStat_Mine(guild.getStat_Mine() + 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("??c???????? ???????? ???? ?????? ?????? ?????? ?? ????????.");
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
    					if (main.economy.getBalance(p.getName()) >= main.createGuildMoney) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) Method.castLvup(p);
        					String name = main.GuildName.get(p.getName());
        					Guild guild = new Guild(name, p.getName());
        					main.GuildName.remove(p.getName());
        					p.closeInventory();
        					p.sendMessage("??6?????????? ??c" + name + " ??6?????? ??????????????.");
        					guild.saveGuildData();
    						main.economy.withdrawPlayer(p.getName(), main.createGuildMoney);
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = main.createGuildMoney - main.economy.getBalance(p.getName());
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
        					Guild guild = Guild.players.get(p.getName());
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a?????? ??f???? ????")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 2) <= GuildAPI.getClassAmount(guild, "??????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ?????? ?????? ???? ???? ?????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c?????? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "??????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??d???????? ??f???? ????")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 3) <= GuildAPI.getClassAmount(guild, "????????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???????? ?????? ???? ???? ???????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???????? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "????????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    	    					Guild guild = Guild.players.get(p.getName());
    	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
        					Guild guild = Guild.players.get(p.getName());
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a?????? ??f???? ????")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 2) <= GuildAPI.getClassAmount(guild, "??????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ?????? ?????? ???? ???? ?????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c?????? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "??????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??d???????? ??f???? ????")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 3) <= GuildAPI.getClassAmount(guild, "????????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???????? ?????? ???? ???? ???????? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???????? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "????????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??c???? ??f???? ????")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 3) <= GuildAPI.getClassAmount(guild, "????")) {
        						p.closeInventory();
        						p.sendMessage("??c???? ???? ?????? ???? ???? ???? ?????? ???????? ?? ????????.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("??6?????????? ??c" + main.ClickAccept.get(p) + " ??6???? ?????? ??c???? ??6?? ??????????????.");
        					guild.setUserClass(main.ClickAccept.get(p), "????");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f?? ????")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    	    					Guild guild = Guild.players.get(p.getName());
    	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					Guild guild = Guild.players.get(p.getName());
    					guild.subUser(p);
						p.sendMessage("??6?????????? ??c" + guild.getName() + "??6 ???????? ??????????????.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (Player pl : guild.getOnLineUser()) pl.sendMessage("??c?????? ??e[ " + p.getName() + " ] ??c???? ??????????????.");
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
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					Guild guild = Guild.players.get(p.getName());
						p.sendMessage("??4?????????? ??c" + guild.getName() + "??4 ?????? ??????????????.");
						Bukkit.broadcastMessage("??f[??4??????f] ??c" + guild.getName() + " ??6?????? ??c" + p.getName() + " ??6???? ??????????????.");
    					guild.deleteGuildData();
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
    					Guild guild = Guild.players.get(p.getName());
    					Guild emeny = Guild.guilds.get(guild.getWar_Guild());
    					String emenymaster = emeny.getManager();
    					
    					for (Player pl : guild.getOnLineUser()) {
    						pl.sendMessage("");
    						pl.sendMessage("??c???? ???????? ???? ???????? ??f??????c?? ????????????.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					for (Player pl : emeny.getOnLineUser()) {
    						pl.sendMessage("");
    						pl.sendMessage("??6?????? ???? ???????? ???? ???????? ??c??????6?? ????????????.");
    						if (emenymaster.equalsIgnoreCase(pl.getName()))
    							pl.sendMessage("??e[ /???? -> ???? ???? ????/???? ] ??6???? ?????? ???? ???? ??????????.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					emeny.setWar_IsSurrender(true);
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
    					Guild guild = Guild.players.get(main.OkInvite.get(p).getName());
    					if (guild.getWar_Guild() != null) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ?????? ????????????.");
    						p.closeInventory();
    						main.OkInvite.remove(p);
    						return;
    					}
    					
    					for (Player pl : guild.getOnLineUser()) pl.sendMessage("??a?????? ??e[ " + p.getName() + " ] ??a???? ??????????????.");
    					guild.addUser(p);
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
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("?????? ???? ?????? ???????????????????")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??, ????????????????.")) {
    					Guild guild = Guild.players.get(p.getName());
    					for (Player pl : guild.getOnLineUser()) pl.sendMessage("??c?????????? " + p.getName() + " ???? ???? ???? ?????? ????????.");
						p.closeInventory();
						
						if ((!uSkyBlock.getInstance().onRestartCooldown(p)) || (Settings.general_cooldownRestart == 0))
						{
							uSkyBlock.getInstance().deletePlayerIsland(p.getName());
				            uSkyBlock.getInstance().setRestartCooldown(p);
				            IslandCommand is = new IslandCommand();
				            is.createIsland(p);
						}

						p.sendMessage("??6?????????? ???? ?????? ????????. ??e" + uSkyBlock.getInstance().getRestartCooldownTime(p) / 1000L + " ?? ????.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f??????, ?????????? ??????????.")) {
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
    					Guild attack = Guild.players.get(p.getName());
    					Guild safe = Guild.guilds.get(ChatColor.stripColor(e.getInventory().getItem(13).getItemMeta().getDisplayName()));
						
    					if (Guild.players.containsKey(p.getName())) {
							if (attack.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("??c???? ?????? ???????????? ???? ?????? ???? ?? ????????.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					if (attack.getUsers().size() < 10) {
    						p.sendMessage("??c?????? ???? ???????? 10?? ?????????? ?????? ???? ?? ????????.");
        					p.closeInventory();
							return;
    					} else {
    						if (safe.getUsers().size() < 10) {
    							p.sendMessage("??c???????? ???? ???????? 10?? ?????????? ?????? ???? ?? ????????.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
    					if (!attack.getIsland_Boolean()) {
    						p.sendMessage("??c?????? ???? ?????? ???????? ???????? ?????? ???? ?? ????????.");
        					p.closeInventory();
							return;
    					} else {
    						if (!safe.getIsland_Boolean()) {
    							p.sendMessage("??c???????? ???? ?????? ???????? ???????? ?????? ???? ?? ????????.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
			        	Bukkit.broadcastMessage("");
			        	Bukkit.broadcastMessage("??f[??4??????f] ??c" + attack.getName() + " ??6?????? ??c" + safe.getName() + " ??6???????? ???? ?????????? ????????????.");
			        	Bukkit.broadcastMessage("");
			        	
			        	for (Player pl : Bukkit.getOnlinePlayers())
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "??????")) pl.playSound(pl.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
			        	
			        	attack.setWar_Guild(safe.getName());
			        	safe.setWar_Guild(attack.getName());
			        	
			        	for (Player pl : attack.getOnLineUser()) {
			        		if (Party.players.containsKey(pl)) {
			        			Party party = Party.players.get(p.getName());
			        			
			        			if (party.getName().equalsIgnoreCase(pl.getName())) {
			        				for (String user : party.getUsers()) {
			        					if (user.equalsIgnoreCase(pl.getName())) continue;
			        					party.subUser(pl);
			        				}
			        				
			        				party.removeParty();
			        			} else {
			        				party.subUser(pl);
			        			}
			        		}
			        	}
			        	
			        	for (Player pl : safe.getOnLineUser()) {
			        		if (Party.players.containsKey(pl)) {
			        			Party party = Party.players.get(p.getName());
			        			
			        			if (party.getName().equalsIgnoreCase(pl.getName())) {
			        				for (String user : party.getUsers()) {
			        					if (user.equalsIgnoreCase(pl.getName())) continue;
			        					party.subUser(pl);
			        				}
			        				
			        				party.removeParty();
			        			} else {
			        				party.subUser(pl);
			        			}
			        		}
			        	}
			        	
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
            				Guild guild = Guild.players.get(p.getName());
            				if (guild.getManager().equalsIgnoreCase(p.getName()))
            					GuildGUI.MoptionGUI(p);
        						
            				else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
            					GuildGUI.DoptionGUI(p);
        						
            				else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
        			
        			GuildAPI.setInfoCode(GuildAPI.getJoinGuild(p.getName()), bldr.toString());
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
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??c??l???? ???? ??????")) {
    					if (guild.getManager().equalsIgnoreCase(p.getName()))
    						GuildGUI.LandReloadGUI(p, GuildAPI.getJoinGuild(p.getName()));
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ????, ???? ???? ????")) {
    					String guild = GuildAPI.getJoinGuild(p.getName());
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
    					String guild = GuildAPI.getJoinGuild(p.getName());
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
    					String guild = GuildAPI.getJoinGuild(p.getName());
    					boolean type = GuildAPI.getLandPvPType(guild);
    					
    					GuildAPI.setLandPvPType(guild, !type);
    					GuildGUI.GuildMenuGUI(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a??l???? ?? ????")) {
    					String name = GuildAPI.getJoinGuild(p.getName());
    					if (GuildAPI.getGuildLand(name)) {
    						p.closeInventory();
        					PlayerInfo pi = (PlayerInfo) uSkyBlock.getInstance().getActivePlayers().get(p.getName());
        					
        					if (pi.getHasParty()) {
            					if ((pi.getHomeLocation() != null) || (pi.getHasParty()))
            						uSkyBlock.getInstance().homeTeleport(p);
            					else
            						((PlayerInfo)uSkyBlock.getInstance().getActivePlayers().get(p.getName())).setHomeLocation(pi.getIslandLocation());	
        					} else {
        						if (!uSkyBlock.getInstance().hasParty(GuildAPI.getManager(name)))
        				        {
        							if (pi.getHasIsland())
        					            uSkyBlock.getInstance().deletePlayerIsland(p.getName());
        							IslandCommand is = new IslandCommand();
        					        is.addPlayertoParty(p.getName(), GuildAPI.getManager(name));
        					        is.addPlayertoParty(GuildAPI.getManager(name), GuildAPI.getManager(name));
        				        } else {
        							if (pi.getHasIsland())
        					            uSkyBlock.getInstance().deletePlayerIsland(p.getName());
        							IslandCommand is = new IslandCommand();
        					        is.addPlayertoParty(p.getName(), GuildAPI.getManager(name));
        				        }
        						
            					if ((pi.getHomeLocation() != null) || (pi.getHasParty()))
            						uSkyBlock.getInstance().homeTeleport(p);
            					else
            						((PlayerInfo)uSkyBlock.getInstance().getActivePlayers().get(p.getName())).setHomeLocation(pi.getIslandLocation());	
        					}
    					}
    				}
    				
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ????")) {
    					if (Guild.players.containsKey(p.getName())) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (guild.getManager().equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e??l???? ?? ????")) {
    					uSkyBlock.getInstance().homeSet(p);
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
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    							if (!Guild.players.containsKey(p.getName())) {
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
    							
    							else if (guild.getManager().equalsIgnoreCase(p.getName())) {
        							if (e.getAction() == InventoryAction.PICKUP_HALF) { // ???? ???? ????
        								if (GuildAPI.isWar(name)) {
        									p.sendMessage("??c???? ?? ?????? ?????? ???????????? ???? ?????????? ???? ?? ????????.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
        								}
        								
        								if (GuildAPI.getJoinGuild(p.getName()).equalsIgnoreCase(name)) {
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
        								String mydate = GuildAPI.getMakeDate(GuildAPI.getJoinGuild(p.getName()));
        								
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
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    						if (guild.getManager().equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (guild.getManager().equalsIgnoreCase(player)) {
    		    						p.sendMessage("??c???????? ???? ???? ???????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (Guild.players.containsKey(p.getName())) {
		    							String guild = GuildAPI.getJoinGuild(p.getName());
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("??c?????? ???????? ?????????? ???? ?????? ????????????.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    		    					GuildGUI.GiveMasterGUI(p, GuildAPI.getJoinGuild(p.getName()), player);
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
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    						if (guild.getUserClass(p.getName()).equalsIgnoreCase("????")) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getUserClass(name, pl.getName()).equalsIgnoreCase("????") || GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("??c?????? ?? ?????????? ???? ???????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (Guild.players.containsKey(p.getName())) {
		    							String guild = GuildAPI.getJoinGuild(p.getName());
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ???? ?????? ????????????.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl.getPlayer());
    								p.sendMessage("??6?????????? ??c" + e.getCurrentItem().getItemMeta().getDisplayName() + "??6 ???? ???????? ???? ???? ??????????.");
    								
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
    		    					String name = GuildAPI.getJoinGuild(p.getName());
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
    						
    						else if (guild.getManager().equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("??c?????? ?? ?????????? ???? ?????????? ?? ????????.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (Guild.players.containsKey(p.getName())) {
		    							String guild = GuildAPI.getJoinGuild(p.getName());
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("??c???? ?????? ?????? ???????? ?????????? ???? ???? ?????? ????????????.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl);
    								p.sendMessage("??6?????????? ??c" + pl.getName() + "??6 ???? ???????? ???? ???? ??????????.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("??c?????? " + name + " ???????? ???? ???? ????????????.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "??????")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("??c?????? ??e[ " + pl.getName() + " ] ??c???? ???? ???? ????????????.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (guild.getManager().equalsIgnoreCase(player)) {
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
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
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
    						if (guild.getUserClass(p.getName()).equalsIgnoreCase("????") || guild.getManager().equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    								
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
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					
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
        						if (Guild.players.containsKey(p.getName())) {
	    							String guild = GuildAPI.getJoinGuild(p.getName());
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
            					player.sendMessage("??6?????? ??c" + GuildAPI.getJoinGuild(p.getName()) + " ??6???? ???????? ??c??????6????????????.");
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
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					if (guild.getManager().equalsIgnoreCase(p.getName())) {
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
	    					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.userGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("??????"))
	    						GuildGUI.MemberGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????"))
	    						GuildGUI.DesignerGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("????"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??2???????? ????")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
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
	    					if (guild.getUserClass(p.getName()).equalsIgnoreCase("????????")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.DoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a??l???? ?? ????")) {
        					if (guild.getManager().equalsIgnoreCase(p.getName())) {
        						GuildGUI.GuildMenuGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??a??l???? ?? ????")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
        					if (GuildAPI.getGuildLand(name)) {
        						p.closeInventory();
            					PlayerInfo pi = (PlayerInfo) uSkyBlock.getInstance().getActivePlayers().get(p.getName());
            					if ((pi.getHomeLocation() != null) || (pi.getHasParty()))
            						uSkyBlock.getInstance().homeTeleport(p);
            					else
            						((PlayerInfo)uSkyBlock.getInstance().getActivePlayers().get(p.getName())).setHomeLocation(pi.getIslandLocation());
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ????????")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
        					if (GuildAPI.isWar(name)) {
        						GuildGUI.WarSubmitGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ???? ????/????")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
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
        				    GuildAPI.setGuildLand(GuildAPI.getJoinGuild(p.getName()), true);
        				    boolean is = p.isOp();
        				    p.setOp(true);
        					p.chat("/??");
        					p.setOp(is);
        					PlayerTimer timer = new PlayerTimer(p, new Runnable() {
        						@Override
        						public void run() {
                					uSkyBlock.getInstance().homeSet(p);
        						}
        					});
        					
        					timer.setTime(1);
        					timer.Start();
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f???? ???? ??7/ ??f?????????? ????")) {
	    					if (guild.getUserClass(p.getName()).equalsIgnoreCase("????")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.SoptionGUI(p);
	    					}
	    					
	    					else if (guild.getManager().equalsIgnoreCase(p.getName())) {
	    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.MoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??e???? ????")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.giveMasterListGUI(p, 1);
        				}
        				
        				if (guild.getManager().equalsIgnoreCase(p.getName())) {
            				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??f[??4!??f] ??4???? ????")) {
    	    					String name = GuildAPI.getJoinGuild(p.getName());
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
