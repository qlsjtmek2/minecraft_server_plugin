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
					if (!rank.equalsIgnoreCase("유저")) is = false; break;
				case 3:
					if (!rank.equalsIgnoreCase("유저") && !rank.equalsIgnoreCase("정회원")) is = false; break;
				case 4:
					if (rank.equalsIgnoreCase("스탭") || rank.equalsIgnoreCase("매니저")) is = false; break;
				case 5:
					if (rank.equalsIgnoreCase("매니저")) is = false; break;
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
					if (!rank.equalsIgnoreCase("유저")) is = false; break;
				case 3:
					if (!rank.equalsIgnoreCase("유저") && !rank.equalsIgnoreCase("정회원")) is = false; break;
				case 4:
					if (rank.equalsIgnoreCase("스탭") || rank.equalsIgnoreCase("매니저")) is = false; break;
				case 5:
					if (rank.equalsIgnoreCase("매니저")) is = false; break;
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
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.2F);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.sendMessage("§6길드 스텟중 §c농사 보너스 §6효과가 발동되어 §c2배 §6더 드랍되었습니다.");
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
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.2F);
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.sendMessage("§6길드 스텟중 §c농사 보너스 §6효과가 발동되어 §c2배 §6더 드랍되었습니다.");
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
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.2F);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.sendMessage("§6길드 스텟중 §c채광 보너스 §6효과가 발동되어 §c2배 §6더 드랍되었습니다.");
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
						if (!rank.equalsIgnoreCase("유저")) is = false; break;
					case 3:
						if (!rank.equalsIgnoreCase("유저") && !rank.equalsIgnoreCase("정회원")) is = false; break;
					case 4:
						if (rank.equalsIgnoreCase("스탭") || rank.equalsIgnoreCase("매니저")) is = false; break;
					case 5:
						if (rank.equalsIgnoreCase("매니저")) is = false; break;
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
			
			if (guild.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 길드 창설을 §c취소 §6하셨습니다.");
				main.CreateGuild.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (guild.matches("[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_]+")) {
				if (Method.GuildNameAgree(guild)) {
					if (!GuildAPI.isGuild(guild)) {
						e.setCancelled(true);
						GuildGUI.CheckCreateGUI(p);
						main.CreateGuild.remove(p);
						main.GuildName.put(p.getName(), guild);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
					} else {
						e.setCancelled(true);
						p.sendMessage("§c이미 같은 이름을 가진 회사가 있습니다. 다시 입력해 주십시오");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					}
				} else {
					e.setCancelled(true);
					p.sendMessage("§c사용이 불가능한 문자가 포함되었거나 길드 이름이 2글자, 6글자보다 큽니다.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				}
			} else {
				e.setCancelled(true);
				p.sendMessage("§c사용이 불가능한 문자가 포함되어 있습니다. 다시 입력해 주십시오.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			}
		}
		
		else if (main.InvitePlayer.contains(p)) {
			String name = e.getMessage();
			
			if (Guild.players.containsKey(p.getName())) {
				Guild guild = Guild.players.get(p.getName());
				if (guild.getWar_Guild() != null) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드 초대가 불가능합니다.");
					main.InvitePlayer.remove(p);
					e.setCancelled(true);
					return;
				}
			}
			
			if (name.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 플레이어 초대를 §c취소 §6하셨습니다.");
				main.InvitePlayer.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Method.searchOnlinePlayer(name) == null) {
				e.setCancelled(true);
				p.sendMessage("§c이 플레이어는 존재하지 않습니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			name = Method.searchOnlinePlayer(name);
			
			if (Method.getOnorOffLine(name) == null) {
				e.setCancelled(true);
				p.sendMessage("§c이 플레이어는 온라인이 아닙니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			Player pl = Method.getOnorOffLine(name);
			
			if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "길드 초대")) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 길드 초대 거부 상태입니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Guild.players.containsKey(pl.getName())) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 이미 길드가 있습니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (main.OkInvite.get(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 이미 길드 초대를 받은 상태입니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			GuildGUI.InviteGUI(pl, Guild.players.get(p.getName()));
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			p.sendMessage("§6성공적으로 §c" + name + " §6님을 초대하셨습니다.");
			main.OkInvite.put(pl, p);
			main.InvitePlayer.remove(p);
			e.setCancelled(true);
		}
		
		else if (main.SetStatMessage.containsKey(p)) {
			String StatMessage = e.getMessage();
			
			if (StatMessage.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 상태 메세지 수정을 §c취소 §6하셨습니다.");
				main.SetStatMessage.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			Guild.players.get(p.getName()).setInfo_StatMessage(StatMessage);
			e.setCancelled(true);
			p.sendMessage("§6성공적으로 상태 메세지를 수정하셨습니다. §e[ /길드 ]");
			main.SetStatMessage.remove(p);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		}
		
		else if (main.SetLore.containsKey(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 설명 추가/삭제 수정을 §c취소 §6하셨습니다.");
				main.SetLore.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
					p.sendMessage("§6성공적으로 설명을 §c추가 §6하셨습니다.");
					p.sendMessage("§6추가된 부분: §f" + message);
					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					Guild guild = Guild.players.get(p.getName());
					Scanner scan = new Scanner(lore.split(" ")[1]);
					if (!scan.hasNextInt())
					{
						p.sendMessage("");
						p.sendMessage("§6설명을 §c삭제§6하시려면 §e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
						p.sendMessage("§6<삭제할 라인> 부분에는 숫자만 들어갈 수 있습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					int removeline = Integer.parseInt(lore.split(" ")[1]) - 1;
					
					if (removeline < 0) {
						p.sendMessage("");
						p.sendMessage("§6설명을 §c삭제§6하시려면 §e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
						p.sendMessage("§6<삭제할 라인> 부분에는 숫자만 들어갈 수 있습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					List<String> list = guild.getInfo_Lore();
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						guild.setInfo_Lore(list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("§6성공적으로 §c" + removeline + 1 + " §6번째 설명을 삭제하셨습니다.");
						p.sendMessage("§6삭제된 부분: §f" + str);
						p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return;
					} else {
						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("§c해당 부분은 존재하지 않는 라인입니다. 다시 입력해 주세요.");
						p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				}
				
				else {
					p.sendMessage("");
					p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
					p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
					p.sendMessage("§f");
					p.sendMessage("§7예시) §f'+ 안녕하세요. OO 길드입니다.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
				p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
				p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
				p.sendMessage("§f");
				p.sendMessage("§7예시) §f'+ 안녕하세요. OO 길드입니다.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
		
		else if (GuildAPI.getPlayerChat(p).equalsIgnoreCase("길드")) {
			Guild guild = Guild.players.get(p.getName());
			if (guild == null) {
				GuildAPI.setPlayerChat(p, "전체");
				return;
			}
			
			e.setCancelled(true);
			for (Player pl : guild.getOnLineUser()) {
				if (pl.isOp()) continue;
				if (p.isOp()) pl.sendMessage("§f[§6길드채팅§f] §f" + p.getName() + " §7:: §e" + e.getMessage());
				else pl.sendMessage("§f[§6길드채팅§f] §r" + p.getDisplayName() + " §7:: §e" + e.getMessage());
			}
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.isOp()) {
					if (p.isOp()) pl.sendMessage("§f[§6길드채팅§f] §f" + p.getName() + " §7:: §e" + e.getMessage());
					else pl.sendMessage("§f[§6길드채팅§f] §r" + p.getDisplayName() + " §7:: §e" + e.getMessage());
				}
			}
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "길드채팅" + ChatColor.WHITE + "] " + ChatColor.RESET + p.getDisplayName() + " " + ChatColor.GRAY + ":: " + ChatColor.YELLOW + e.getMessage());
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("길드에 초대받으셨습니다. 수락하시겠습니까?")) {
			if (main.OkInvite.get(p) != null) {
				p.sendMessage("§c인벤토리를 닫아 길드 초대에 자동으로 거절하였습니다.");
				main.OkInvite.get(p).sendMessage("§c" + p.getName() + "님이 길드 초대를 거절하셨습니다.");
				main.OkInvite.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("정말로 길드를 창설하시겠습니까?")) {
			if (main.CreateGuild.contains(p)) {
				p.sendMessage("§c인벤토리를 닫아 자동으로 길드 창설을 취소하였습니다.");
				main.GuildName.remove(p.getName());
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("설정할 아이템 코드를 클릭해 주시기 바랍니다.")) {
			if (main.SetItemCode.get(p) != null) main.SetItemCode.remove(p);
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("길드가 없으시군요!")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 창설")) {
    					if (main.economy.getBalance(p.getName()) >= (double) main.createGuildMoney) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
        					p.closeInventory();
        					main.CreateGuild.add(p);
        					p.sendMessage("§6채팅창에 창설할 §c길드 이름§6을 적어주시기 바랍니다.");
        					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = (double) main.createGuildMoney - main.economy.getBalance(p.getName());
        					p.sendMessage("§c" + num + " 원이 부족하여 길드를 창설하실 수 없습니다.");
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 랭킹")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					GuildGUI.RankingGUI(p, 1);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("디자이너 옵션")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		Guild guild = Guild.players.get(p.getName());
    		
    		if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 상태 메세지 §e§l수정")) {
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 상태 메세지를 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("§6변경할 상태 메세지를 §c채팅창§6에 입력해 주시기 바랍니다.");
        					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 설명 §e§l수정")) {
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 설명을 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 상태 메세지를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
    						p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
        					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
    						p.sendMessage("§f");
    						p.sendMessage("§7예시) §f'+ 안녕하세요. OO 길드입니다.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 아이템 코드 §e§l수정")) {
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 아이템 코드를 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 상태 메세지를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, guild.getName());
    						GuildGUI.SetItemCodeGUI(p);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("스탭 옵션")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		Guild guild = Guild.players.get(p.getName());
    		
    		if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭")) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 상태 메세지 §e§l수정")) {
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 상태 메세지를 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("§6변경할 상태 메세지를 §c채팅창§6에 입력해 주시기 바랍니다.");
        					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 설명 §e§l수정")) {
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 설명을 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 상태 메세지를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
    						p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
        					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
    						p.sendMessage("§f");
    						p.sendMessage("§7예시) §f'+ 안녕하세요. OO 길드입니다.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 아이템 코드 §e§l수정")) {
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 아이템 코드를 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 상태 메세지를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, guild.getName());
    						GuildGUI.SetItemCodeGUI(p);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3길드 가입조건")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					if (guild.getJoinType().equalsIgnoreCase("공개")) guild.setJoinType("신청서");
	    					else if (guild.getJoinType().equalsIgnoreCase("신청서")) guild.setJoinType("비공개");
	    					else if (guild.getJoinType().equalsIgnoreCase("비공개")) guild.setJoinType("공개");
	    					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9가입 신청서 목록")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("매니저 옵션")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		Guild guild = Guild.players.get(p.getName());
    		
    		if (guild.getManager().equalsIgnoreCase(p.getName())) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 상태 메세지 §e§l수정")) {
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 상태 메세지를 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetStatMessage.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("§6변경할 상태 메세지를 §c채팅창§6에 입력해 주시기 바랍니다.");
        					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 설명 §e§l수정")) {
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 설명을 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 상태 메세지를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetLore.put(p, guild.getName());
    						p.sendMessage("");
    						p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
    						p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
        					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
    						p.sendMessage("§f");
    						p.sendMessage("§7예시) §f'+ 안녕하세요. OO 길드입니다.', '- 2'");
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 아이템 코드 §e§l수정")) {
    						if (main.SetItemCode.containsValue(guild.getName())) {
    							p.sendMessage("§c이미 누가 아이템 코드를 수정하고 있습니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetLore.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(guild.getName())) {
    							p.sendMessage("§c당신은 이미 상태 메세지를 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						main.SetItemCode.put(p, guild.getName());
    						GuildGUI.SetItemCodeGUI(p);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3길드 가입조건")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					if (guild.getJoinType().equalsIgnoreCase("공개")) guild.setJoinType("신청서");
	    					else if (guild.getJoinType().equalsIgnoreCase("신청서")) guild.setJoinType("비공개");
	    					else if (guild.getJoinType().equalsIgnoreCase("비공개")) guild.setJoinType("공개");
	    					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
	    						GuildGUI.DoptionGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.SoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9가입 신청서 목록")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.joinListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e경험치 보너스 §6§l설정")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					guild.setStat_IsExp(!guild.getStat_IsExp());
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a농사 보너스 §6§l설정")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					guild.setStat_IsPlant(!guild.getStat_IsPlant());
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b채광 보너스 §6§l설정")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					guild.setStat_IsMine(!guild.getStat_IsMine());
        					GuildGUI.MoptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a길드 업그레이드 진행")) {
	    					int i = guild.getUpgrade() + 1;
	    					int money = Integer.parseInt(main.guildConfig.get(i).split(" ")[1]);
	    					
	    					if (main.guildConfig.size() >= (i + 2)) {
		    					if (main.economy.getBalance(p.getName()) >= money) {
			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(p);
		        					p.closeInventory();
		        					p.sendMessage("§6성공적으로 길드를 §c" + i + " §6단계로 업그레이드 하셨습니다.");
		        					guild.setUpgrade(i);
		    						main.economy.withdrawPlayer(p.getName(), (double) money);
		    					} else {
		        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		        					double num = (double) money - main.economy.getBalance(p.getName());
		        					p.closeInventory();
		        					p.sendMessage("§c" + num + " 원이 부족하여 길드 업그레이드를 진행하실 수 없습니다.");
		    					}
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
        		}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("길드 스텟")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (guild.getManager().equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("길드 스텟 설정")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (guild.getManager().equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
    							GuildGUI.userGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
    							GuildGUI.MemberGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
    							GuildGUI.DesignerGuildGUI(p);
    						
    						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
    							GuildGUI.StepGuildGUI(p);
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						GuildGUI.noGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e경험치 보너스 §6§l스텟")) {
    					Guild guild = Guild.players.get(p.getName());
    					if (guild.getStat_Exp() < guild.getMaxExpStat()) {
    						if (guild.getPoint() >= 12000) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    							guild.setPoint(guild.getPoint() - 12000);
    							guild.setStat_Exp(guild.getStat_Exp() + 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("§c포인트가 부족하여 경험치 보너스 스텟을 올리실 수 없습니다.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a농사 보너스 §6§l스텟")) {
    					Guild guild = Guild.players.get(p.getName());
    					if (guild.getStat_Plant() < guild.getMaxPlantStat()) {
    						if (guild.getPoint() >= 12000) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
	        					guild.setPoint(guild.getPoint() - 12000);
    							guild.setStat_Plant(guild.getStat_Plant() + 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("§c포인트가 부족하여 농사 보너스 스텟을 올리실 수 없습니다.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b채광 보너스 §6§l스텟")) {
    					Guild guild = Guild.players.get(p.getName());
    					if (guild.getStat_Mine() < guild.getMaxMineStat()) {
    						if (guild.getPoint() >= 12000) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
	        					guild.setPoint(guild.getPoint() - 12000);
    							guild.setStat_Mine(guild.getStat_Mine() + 1);
    							GuildGUI.setStatGUI(p);
    						} else {
    							p.closeInventory();
    							p.sendMessage("§c포인트가 부족하여 채광 보너스 스텟을 올리실 수 없습니다.");
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					}
    					} else {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("정말로 길드를 창설하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 창설하겠습니다.")) {
    					if (main.economy.getBalance(p.getName()) >= main.createGuildMoney) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(p);
        					String name = main.GuildName.get(p.getName());
        					Guild guild = new Guild(name, p.getName());
        					main.GuildName.remove(p.getName());
        					p.closeInventory();
        					p.sendMessage("§6성공적으로 §c" + name + " §6길드를 창설하셨습니다.");
        					guild.saveGuildData();
    						main.economy.withdrawPlayer(p.getName(), main.createGuildMoney);
    					} else {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					double num = main.createGuildMoney - main.economy.getBalance(p.getName());
        					p.sendMessage("§c" + num + " 원이 부족하여 길드를 창설하실 수 없습니다.");
        					p.closeInventory();
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 창설하지 않겠습니다.")) {
    					p.sendMessage("§6성공적으로 길드 창설을 §c취소 §6하셨습니다.");
    					main.GuildName.remove(p.getName());
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("스탭 권한 설정 창")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (main.ClickAccept.get(p) != null) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e유저 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c유저 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "유저");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a정회원 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 2) <= GuildAPI.getClassAmount(guild, "정회원")) {
        						p.closeInventory();
        						p.sendMessage("§c현재 정회원 자리가 모두 차서 정회원 권한을 지급하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c정회원 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "정회원");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d디자이너 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 3) <= GuildAPI.getClassAmount(guild, "디자이너")) {
        						p.closeInventory();
        						p.sendMessage("§c현재 디자이너 자리가 모두 차서 디자이너 권한을 지급하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c디자이너 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "디자이너");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    	    					Guild guild = Guild.players.get(p.getName());
    	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("매니저 권한 설정 창")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (main.ClickAccept.get(p) != null) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e유저 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c유저 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "유저");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a정회원 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 2) <= GuildAPI.getClassAmount(guild, "정회원")) {
        						p.closeInventory();
        						p.sendMessage("§c현재 정회원 자리가 모두 차서 정회원 권한을 지급하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c정회원 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "정회원");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d디자이너 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 3) <= GuildAPI.getClassAmount(guild, "디자이너")) {
        						p.closeInventory();
        						p.sendMessage("§c현재 디자이너 자리가 모두 차서 디자이너 권한을 지급하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c디자이너 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "디자이너");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c스탭 §f권한 지급")) {
        					Guild guild = Guild.players.get(p.getName());
        					if (GuildAPI.getClassConfig(guild, guild.getUpgrade(), 3) <= GuildAPI.getClassAmount(guild, "스탭")) {
        						p.closeInventory();
        						p.sendMessage("§c현재 스탭 자리가 모두 차서 스탭 권한을 지급하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					p.sendMessage("§6성공적으로 §c" + main.ClickAccept.get(p) + " §6님의 권한을 §c스탭 §6로 변경시켰습니다.");
        					guild.setUserClass(main.ClickAccept.get(p), "스탭");
        					main.ClickAccept.remove(p);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    	    					Guild guild = Guild.players.get(p.getName());
    	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("정말로 길드를 탈퇴하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 탈퇴하겠습니다.")) {
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드 탈퇴가 불가능합니다.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					Guild guild = Guild.players.get(p.getName());
    					guild.subUser(p);
						p.sendMessage("§6성공적으로 §c" + guild.getName() + "§6 길드에서 탈퇴하셨습니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (Player pl : guild.getOnLineUser()) pl.sendMessage("§c길드에 §e[ " + p.getName() + " ] §c님이 탈퇴하셨습니다.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 탈퇴하지 않겠습니다.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("정말로 길드를 폐쇄하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 폐쇄하겠습니다.")) {
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드 폐쇄가 불가능합니다.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					Guild guild = Guild.players.get(p.getName());
						p.sendMessage("§4성공적으로 §c" + guild.getName() + "§4 길드를 폐쇄하셨습니다.");
						Bukkit.broadcastMessage("§f[§4알림§f] §c" + guild.getName() + " §6길드를 §c" + p.getName() + " §6님이 폐쇄하셨습니다.");
    					guild.deleteGuildData();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
						p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 폐쇄하지 않겠습니다.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("정말로 전쟁에서 항복하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 항복하겠습니다.")) {
    					Guild guild = Guild.players.get(p.getName());
    					Guild emeny = Guild.guilds.get(guild.getWar_Guild());
    					String emenymaster = emeny.getManager();
    					
    					for (Player pl : guild.getOnLineUser()) {
    						pl.sendMessage("");
    						pl.sendMessage("§c길드 마스터가 길드 전쟁에서 §f항복§c을 선언했습니다.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					for (Player pl : emeny.getOnLineUser()) {
    						pl.sendMessage("");
    						pl.sendMessage("§6상대방 길드 마스터가 길드 전쟁에서 §c항복§6을 선언했습니다.");
    						if (emenymaster.equalsIgnoreCase(pl.getName()))
    							pl.sendMessage("§e[ /길드 -> 전쟁 항복 수락/거절 ] §6으로 항복을 수락 또는 거절하세요.");
    						pl.sendMessage("");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
    					}
    					
    					emeny.setWar_IsSurrender(true);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ANVIL_USE, 2.0F, 1.6F);
						p.closeInventory();
						Player ply = Method.getOnorOffLine(emenymaster);
						if (ply != null) ply.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 항복하지 않겠습니다.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("길드에 초대받으셨습니다. 수락하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 수락하겠습니다.")) {
    					Guild guild = Guild.players.get(main.OkInvite.get(p).getName());
    					if (guild.getWar_Guild() != null) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c현재 가입할 길드가 전쟁중인 상태이므로 길드 가입이 불가능합니다.");
    						p.closeInventory();
    						main.OkInvite.remove(p);
    						return;
    					}
    					
    					for (Player pl : guild.getOnLineUser()) pl.sendMessage("§a길드에 §e[ " + p.getName() + " ] §a님이 가입하셨습니다.");
    					guild.addUser(p);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(p);
						p.sendMessage("§6성공적으로 길드에 가입하셨습니다.");
						main.OkInvite.remove(p);
						p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 수락하지 않겠습니다.")) {
    					main.OkInvite.get(p).sendMessage("§c" + p.getName() + "님이 길드 초대를 거절하셨습니다.");
    					main.OkInvite.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("정말로 길드 영토를 재시작하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 재시작하겠습니다.")) {
    					Guild guild = Guild.players.get(p.getName());
    					for (Player pl : guild.getOnLineUser()) pl.sendMessage("§c길드장이신 " + p.getName() + " 님이 길드 섬을 초기화 했습니다.");
						p.closeInventory();
						
						if ((!uSkyBlock.getInstance().onRestartCooldown(p)) || (Settings.general_cooldownRestart == 0))
						{
							uSkyBlock.getInstance().deletePlayerIsland(p.getName());
				            uSkyBlock.getInstance().setRestartCooldown(p);
				            IslandCommand is = new IslandCommand();
				            is.createIsland(p);
						}

						p.sendMessage("§6성공적으로 섬을 재시작 했습니다. §e" + uSkyBlock.getInstance().getRestartCooldownTime(p) / 1000L + " 초 소요.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 재시작하지 않겠습니다.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("정말로 이 길드와 전쟁하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 전쟁하겠습니다.")) {
    					Guild attack = Guild.players.get(p.getName());
    					Guild safe = Guild.guilds.get(ChatColor.stripColor(e.getInventory().getItem(13).getItemMeta().getDisplayName()));
						
    					if (Guild.players.containsKey(p.getName())) {
							if (attack.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("§c이미 길드가 전쟁중이므로 전쟁 선포를 하실 수 없습니다.");
	        					p.closeInventory();
								return;
							}
						}
    					
    					if (attack.getUsers().size() < 10) {
    						p.sendMessage("§c당신의 길드 인원수가 10명 이하이므로 전쟁을 하실 수 없습니다.");
        					p.closeInventory();
							return;
    					} else {
    						if (safe.getUsers().size() < 10) {
    							p.sendMessage("§c상대방의 길드 인원수가 10명 이하이므로 전쟁을 하실 수 없습니다.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
    					if (!attack.getIsland_Boolean()) {
    						p.sendMessage("§c당신의 길드 영토가 존재하지 아니하여 전쟁을 하실 수 없습니다.");
        					p.closeInventory();
							return;
    					} else {
    						if (!safe.getIsland_Boolean()) {
    							p.sendMessage("§c상대방의 길드 영토가 존재하지 아니하여 전쟁을 하실 수 없습니다.");
            					p.closeInventory();
    							return;
    						}
    					}
    					
			        	Bukkit.broadcastMessage("");
			        	Bukkit.broadcastMessage("§f[§4알림§f] §c" + attack.getName() + " §6길드가 §c" + safe.getName() + " §6길드에게 전쟁 선전포고를 선언했습니다.");
			        	Bukkit.broadcastMessage("");
			        	
			        	for (Player pl : Bukkit.getOnlinePlayers())
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.7F);
			        	
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
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 전쟁하지 않겠습니다.")) {
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("설정할 아이템 코드를 클릭해 주시기 바랍니다.")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (main.SetItemCode.get(p) != null) {
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
            			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
            				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
            				Guild guild = Guild.players.get(p.getName());
            				if (guild.getManager().equalsIgnoreCase(p.getName()))
            					GuildGUI.MoptionGUI(p);
        						
            				else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
            					GuildGUI.DoptionGUI(p);
        						
            				else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
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
        			p.sendMessage("§6성공적으로 정보 아이템 코드를 §c" + bldr + " §6으로 변경하셨습니다.");
        			p.closeInventory();
        			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("길드 영토 메뉴")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§l길드 영토 재시작")) {
    					if (guild.getManager().equalsIgnoreCase(p.getName()))
    						GuildGUI.LandReloadGUI(p, GuildAPI.getJoinGuild(p.getName()));
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a길드 상자, 화로 권한 여부")) {
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
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b길드 블럭 설치, 제거 여부")) {
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
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c길드 영토 내에서 PvP 여부")) {
    					String guild = GuildAPI.getJoinGuild(p.getName());
    					boolean type = GuildAPI.getLandPvPType(guild);
    					
    					GuildAPI.setLandPvPType(guild, !type);
    					GuildGUI.GuildMenuGUI(p);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§l길드 홈 이동")) {
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
    				
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f뒤로 가기")) {
    					if (Guild.players.containsKey(p.getName())) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    						if (guild.getManager().equalsIgnoreCase(p.getName()))
    							GuildGUI.MasterGuildGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§l영토 홈 지정")) {
    					uSkyBlock.getInstance().homeSet(p);
    				}
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
         	if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("길드") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("랭킹")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 랭킹 확인")) {
    						GuildGUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 랭킹 확인")) {
    						GuildGUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[1]);
    						if (GuildAPI.isGuild(name)) {
    							if (!Guild.players.containsKey(p.getName())) {
        							if (e.getAction() == InventoryAction.PICKUP_ALL) {
        								if (GuildAPI.getJoinList(name).contains(p.getName())) {
        									p.sendMessage("§c당신은 이미 이 길드에 신청서를 넣으셨습니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        			    					p.closeInventory();
        									return;
        								}
        								
        								if (GuildAPI.isWar(name)) {
        									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									p.sendMessage("§c현재 가입할 길드가 전쟁중인 상태이므로 길드 가입이 불가능합니다.");
        									p.closeInventory();
        									return;
        								}
        								
        								if (GuildAPI.getJoinType(name).equalsIgnoreCase("공개")) {
        		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        		        						p.closeInventory();
        		        						p.sendMessage("§c이 길드는 현재 최대 인원 수용 한도가 초과되어 가입하실 수 없습니다.");
        		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		        						return;
        		        					}
        		        					
        									for (Player pl : GuildAPI.getOnLineUser(name)) pl.sendMessage("§a길드에 §e[ " + p.getName() + " ] §a님이 가입하셨습니다.");
        									GuildAPI.addUser(name, p);
        									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(p);
        									p.sendMessage("§6성공적으로 길드에 가입하셨습니다.");
        									p.closeInventory();
        								}
        								else if (GuildAPI.getJoinType(name).equalsIgnoreCase("신청서")) {
        									if (GuildAPI.isWar(name)) {
            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            									p.sendMessage("§c현재 가입할 길드가 전쟁중인 상태이므로 길드 가입이 불가능합니다.");
            									p.closeInventory();
            									return;
            								}
        									
        		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        		        						p.closeInventory();
        		        						p.sendMessage("§c이 길드는 현재 최대 인원 수용 한도가 초과되어 가입하실 수 없습니다.");
        		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		        						return;
        		        					}
        		        					
        									GuildAPI.addJoinList(name, p.getName());
        									p.sendMessage("§6성공적으로 §c" + name + "§6 길드에 신청서를 작성하셨습니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        									p.closeInventory();
        									
        									for (Player pl : Bukkit.getOnlinePlayers()) {
        										for (String str : GuildAPI.getClassPlayer(name, pl.getName(), "스탭")) {
        											if (pl.getName().equalsIgnoreCase(str)) {
            											pl.sendMessage("§6당신의 길드에 §c" + p.getName() + " §6님이 가입 신청서를 작성하셨습니다.");
        											}
        										}
        											
        										if (pl.getName().equalsIgnoreCase(GuildAPI.getManager(name)))
        											pl.sendMessage("§6당신의 길드에 §c" + p.getName() + " §6님이 가입 신청서를 작성하셨습니다.");
        									}
        								}
        								else if (GuildAPI.getJoinType(name).equalsIgnoreCase("비공개")) {
        									p.sendMessage("§c이 길드는 비공개 되어있어 가입 신청서를 제출할 수 없습니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									p.closeInventory();
        									return;
        								}
        							}
    							}
    							
    							else if (guild.getManager().equalsIgnoreCase(p.getName())) {
        							if (e.getAction() == InventoryAction.PICKUP_HALF) { // 길드 전쟁 부분
        								if (GuildAPI.isWar(name)) {
        									p.sendMessage("§c이미 그 길드는 전쟁이 진행중이여서 전쟁 선전포고를 하실 수 없습니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
        								}
        								
        								if (GuildAPI.getJoinGuild(p.getName()).equalsIgnoreCase(name)) {
        									p.sendMessage("§c자신의 길드와 전쟁하시겠습니까? 안타깝게도 그것은 불가능합니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
        							         
        							        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
        							        long diff = endDate.getTime() - beginDate.getTime();
        							        long diffDays = diff / (24 * 60 * 60 * 1000);
        							 
        							        if (diffDays > 7) {
                								try {
                							        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy,MM,dd");
                							        Date beginDate2 = formatter2.parse(endate);
                							        Date endDate2 = formatter2.parse(day);
                							         
                							        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
                							        long diff2 = endDate2.getTime() - beginDate2.getTime();
                							        long diffDays2 = diff2 / (24 * 60 * 60 * 1000);
                							 
                							        if (diffDays2 > 7) {
                							        	GuildGUI.WarCheckGUI(p, name);
                				    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                							        } else {
                							        	p.sendMessage("§c생성 날짜가 8일 미만인 길드와는 전쟁하실 수 없습니다.");
                    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                							        }
                							    } catch (ParseException ev) {
                							        ev.printStackTrace();
                							    }
        							        } else {
        							        	p.sendMessage("§c당신 길드는 생성 날짜가 8일 미만이므로 길드 전쟁을 선포하실 수 없습니다.");
            			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("양도할") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("유저") && e.getInventory().getName().split(" ")[2].equalsIgnoreCase("목록")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
    						GuildGUI.giveMasterListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
    						GuildGUI.giveMasterListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (guild.getManager().equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (guild.getManager().equalsIgnoreCase(player)) {
    		    						p.sendMessage("§c자신에게 길드 장을 양도하실 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (Guild.players.containsKey(p.getName())) {
		    							String guild = GuildAPI.getJoinGuild(p.getName());
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("§c길드가 전쟁중인 상태에서는 길드 양도가 불가능합니다.");
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
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("유저") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("목록")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
    						GuildGUI.userListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
    						GuildGUI.userListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭")) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getUserClass(name, pl.getName()).equalsIgnoreCase("스탭") || GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("§c당신은 이 플레이어를 강제 퇴장시킬 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (Guild.players.containsKey(p.getName())) {
		    							String guild = GuildAPI.getJoinGuild(p.getName());
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드 강제 퇴장이 불가능합니다.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl.getPlayer());
    								p.sendMessage("§6성공적으로 §c" + e.getCurrentItem().getItemMeta().getDisplayName() + "§6 님을 길드에서 강제 퇴장 시켰습니다.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("§c당신은 " + name + " 길드에서 강제 퇴장 당하셨습니다.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "효과음")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("§c길드에 §e[ " + e.getCurrentItem().getItemMeta().getDisplayName() + " ] §c님이 강제 퇴장 당하셨습니다.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (GuildAPI.getUserClass(name, player).equalsIgnoreCase("스탭") || GuildAPI.getManager(name).equalsIgnoreCase(player)) {
    		    						p.sendMessage("§c당신은 이 플레이어의 계급을 수정하실 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					main.ClickAccept.put(p, player);
    		    					GuildGUI.StepAcceptGUI(p);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    						}
    						
    						else if (guild.getManager().equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					OfflinePlayer pl = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    					
    		    					if (GuildAPI.getManager(name).equalsIgnoreCase(pl.getName())) {
    		    						p.sendMessage("§c당신은 이 플레이어를 강제 퇴장시키실 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					if (Guild.players.containsKey(p.getName())) {
		    							String guild = GuildAPI.getJoinGuild(p.getName());
		    							if (GuildAPI.isWar(guild)) {
		    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		    	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드 강제 퇴장이 불가능합니다.");
		    	        					p.closeInventory();
		    								return;
		    							}
		    						}
    		    					
    								GuildAPI.subUser(name, pl);
    								p.sendMessage("§6성공적으로 §c" + pl.getName() + "§6 님을 길드에서 강제 퇴장 시켰습니다.");
    								
    		    					if (Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
    		    						Player pp = Method.getOnorOffLine(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
    		    						pp.sendMessage("§c당신은 " + name + " 길드에서 강제 퇴장 당하셨습니다.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "효과음")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (Player pls : GuildAPI.getOnLineUser(name)) pls.sendMessage("§c길드에 §e[ " + pl.getName() + " ] §c님이 강제 퇴장 당하셨습니다.");
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					String player = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					if (guild.getManager().equalsIgnoreCase(player)) {
    		    						p.sendMessage("§c당신은 이 플레이어의 계급을 수정하실 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    						return;
    		    					}
    		    					
    		    					main.ClickAccept.put(p, player);
    		    					GuildGUI.ManegerAcceptGUI(p);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("가입신청서") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("목록")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (Guild.players.containsKey(p.getName())) {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						if (guild.getManager().equalsIgnoreCase(p.getName()))
        							GuildGUI.MasterGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
        							GuildGUI.userGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
        							GuildGUI.MemberGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
        							GuildGUI.DesignerGuildGUI(p);
        						
        						else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
        							GuildGUI.StepGuildGUI(p);
        					} else {
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        						GuildGUI.noGuildGUI(p);
        					}
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
    						GuildGUI.joinListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
    						GuildGUI.joinListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭") || guild.getManager().equalsIgnoreCase(p.getName())) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    								
		        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
		        						p.closeInventory();
		        						p.sendMessage("§c길드의 인원수가 초과되어 플레이어의 가입을 승인하실 수 없습니다.");
		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
		        						return;
		        					}
		        					
		        					if (GuildAPI.isWar(name)) {
    									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    									p.sendMessage("§c현재 길드가 전쟁중인 상태이므로 가입 신청을 받으실 수 없습니다.");
    									p.closeInventory();
    									return;
    								}
		        					
    								if (Method.getOnorOffLine(pl) != null) {
    									Method.getOnorOffLine(pl).sendMessage("§6당신은 §c" + name + " §6길드에 성공적으로 가입하셨습니다.");
    								}
		        					
    								OfflinePlayer pp = Bukkit.getOfflinePlayer(pl);
									for (Player ply : GuildAPI.getOnLineUser(name)) ply.sendMessage("§a길드에 §e[ " + p.getName() + " ] §a님이 가입하셨습니다.");
									GuildAPI.addUser(name, pp.getPlayer());
    		    					GuildAPI.subJoinList(name, pl);
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    		    					p.sendMessage("§6성공적으로 §c" + pl + " §6님의 가입을 승인하셨습니다.");
    		    					GuildGUI.joinListGUI(p, 1);
    							}
    							
    							if (e.getAction() == InventoryAction.PICKUP_HALF) {
    								String pl = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    		    					String name = GuildAPI.getJoinGuild(p.getName());
    		    					
    								if (Method.getOnorOffLine(pl) != null) {
    									Method.getOnorOffLine(pl).sendMessage("§c당신은 " + name + " 길드에 가입하지 못하셨습니다.");
    								}
    								
    								GuildAPI.subJoinList(name, pl);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    		    					p.sendMessage("§c성공적으로 " + pl + " 님의 가입을 거부하셨습니다.");
    		    					GuildGUI.joinListGUI(p, 1);
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("정말로") && e.getInventory().getName().split(" ")[2].equalsIgnoreCase("님에게") && e.getInventory().getName().split(" ")[3].equalsIgnoreCase("길드장을") && e.getInventory().getName().split(" ")[4].equalsIgnoreCase("양도하시겠습니까?")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		Player player = Method.getOnorOffLine(e.getInventory().getName().split(" ")[1]);
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 양도하겠습니다.")) {
        					if (player == null) {
        						p.sendMessage("§cERROR: 양도할 플레이어가 존재하지 않습니다.");
            					p.closeInventory();
        					} else {
        						if (Guild.players.containsKey(p.getName())) {
	    							String guild = GuildAPI.getJoinGuild(p.getName());
	    							if (GuildAPI.isWar(guild)) {
	    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	    	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 길드장 양도가 불가능합니다.");
	    								return;
	    							}
	    						}
        						
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "효과음")) player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					p.closeInventory();
            					p.sendMessage("§6성공적으로 §c" + player.getName() + " §6님에게 길드장을 양도하셨습니다.");
            					player.sendMessage("§6당신은 §c" + GuildAPI.getJoinGuild(p.getName()) + " §6길드 마스터를 §c양도§6받으셨습니다.");
            					GuildAPI.changeGuildMaster(p, player);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 양도하지 않겠습니다.")) {
        					p.closeInventory();
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        				}
        			}
        		}
         	}
         	
         	else if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("§6§lG")) {
         		e.setCancelled(true);
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6길드 랭킹")) {
        					GuildGUI.RankingGUI(p, 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					p.closeInventory();
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c길드 탈퇴")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.CheckQuitGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a길드 스텟")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					if (guild.getManager().equalsIgnoreCase(p.getName())) {
	    						GuildGUI.setStatGUI(p);
	    					} else {
	    						GuildGUI.userStatGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e길드 유저 목록")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.userListGUI(p, 1);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b길드 채팅")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					String str = Method.NextChatMode(p, GuildAPI.getPlayerChat(p));
	    					GuildAPI.setPlayerChat(p, str);
	    					
	    					if (guild.getManager().equalsIgnoreCase(p.getName()))
	    						GuildGUI.MasterGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("유저"))
	    						GuildGUI.userGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("정회원"))
	    						GuildGUI.MemberGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너"))
	    						GuildGUI.DesignerGuildGUI(p);
	    					
	    					else if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭"))
	    						GuildGUI.StepGuildGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2플레이어 초대")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
        					if (GuildAPI.getUserInteger(name) >= GuildAPI.getMaxUser(name)) {
        						p.closeInventory();
        						p.sendMessage("§c현재 최대 인원 수용 한도가 초과되어 더이상 플레이어를 초대하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (GuildAPI.isWar(name)) {
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								p.sendMessage("§c현재 가입할 길드가 전쟁중인 상태이므로 길드 초대가 불가능합니다.");
								p.closeInventory();
								return;
							}
        					
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					p.closeInventory();
	    					p.sendMessage("§6누구를 §c초대§6하시겠습니까? 채팅창에 적어주시기 바랍니다.");
	    					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
        					main.InvitePlayer.add(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f길드 옵션")) {
	    					if (guild.getUserClass(p.getName()).equalsIgnoreCase("디자이너")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.DoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§l길드 땅 메뉴")) {
        					if (guild.getManager().equalsIgnoreCase(p.getName())) {
        						GuildGUI.GuildMenuGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§l길드 홈 이동")) {
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
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f전쟁 항복하기")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
        					if (GuildAPI.isWar(name)) {
        						GuildGUI.WarSubmitGUI(p);
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f전쟁 항복 수락/거절")) {
        					String name = GuildAPI.getJoinGuild(p.getName());
        					if (GuildAPI.isWar(name)) {
        						if (GuildAPI.isSubmit(name)) {
            						if (e.getAction() == InventoryAction.PICKUP_HALF) {
            	    					String emeny = GuildAPI.getWarGuild(name);
            	    					
            							for (Player pl : Bukkit.getOnlinePlayers()) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("§e[ " + name + " vs " + emeny + " ] §6길드의 전쟁에서 §c" + name + " §6길드가 승리하였습니다!");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ANVIL_USE, 2.0F, 1.5F);
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
            	    						pl.sendMessage("§6길드 마스터가 길드 항복을 §c거절§6 하였습니다.");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.5F);
            	    					}
            	    					
            	    					for (Player pl : GuildAPI.getOnLineUser(emeny)) {
            	    						pl.sendMessage("");
            	    						pl.sendMessage("§c상대방 길드가 전쟁 항복을 거절하였습니다.");
            	    						pl.sendMessage("");
            	        					if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            	    					}
            	    					
            	    					p.closeInventory();
            	    					Player ply = Method.getOnorOffLine(GuildAPI.getManager(emeny));
            							if (ply != null) ply.closeInventory();
            	    					GuildAPI.setSubmit(name, false);
            						}
        						}
        					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a길드 섬 생성")) {
        				    GuildAPI.setGuildLand(GuildAPI.getJoinGuild(p.getName()), true);
        				    boolean is = p.isOp();
        				    p.setOp(true);
        					p.chat("/섬");
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
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f길드 옵션 §7/ §f가입신청서 목록")) {
	    					if (guild.getUserClass(p.getName()).equalsIgnoreCase("스탭")) {
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.SoptionGUI(p);
	    					}
	    					
	    					else if (guild.getManager().equalsIgnoreCase(p.getName())) {
	    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    						GuildGUI.MoptionGUI(p);
	    					}
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e길드 양도")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GuildGUI.giveMasterListGUI(p, 1);
        				}
        				
        				if (guild.getManager().equalsIgnoreCase(p.getName())) {
            				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f[§4!§f] §4길드 폐쇄")) {
    	    					String name = GuildAPI.getJoinGuild(p.getName());
    	    					if (GuildAPI.getUser(name).size() != 1) {
            						p.sendMessage("§c모든 유저를 강제 퇴장 시키고 폐쇄를 진행해 주시기 바랍니다.");
            						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            						p.closeInventory();
            						return;
    	    					}
    	    					
    	    					if (GuildAPI.isWar(name)) {
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
									p.sendMessage("§c길드가 전쟁중인 상태이므로 길드 폐쇄가 불가능합니다.");
									p.closeInventory();
									return;
								}
    	    					
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
            					GuildGUI.CheckDeleteGuildGUI(p);
            				}
        				}
        			}
        		}
         	}
     	}
	}
}
