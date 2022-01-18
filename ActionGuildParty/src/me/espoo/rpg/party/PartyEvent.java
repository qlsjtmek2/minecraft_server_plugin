package me.espoo.rpg.party;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.pvp.API;
import me.espoo.rpg.Method;
import me.espoo.rpg.main;
import me.espoo.rpg.guild.Guild;
import me.espoo.rpg.guild.GuildAPI;

@SuppressWarnings("deprecation")
public class PartyEvent extends JavaPlugin implements Listener {
	main M;
	
	public PartyEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (Party.players.containsKey(p.getName())) {
			Party.players.remove(p.getName());
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (Party.players.containsKey(p.getName()) && Party.partylist.contains(Party.players.get(p.getName()))) {
			if (main.InvitePartyPlayer.contains(p)) main.InvitePartyPlayer.remove(p);
			if (main.SetLoreParty.get(p) != null) main.SetLoreParty.remove(p);
			if (main.PartyJoin.containsKey(p)) main.PartyJoin.remove(p);
			if (main.PartyJoin.containsValue(p)) main.PartyJoin.remove(p);
			
			Party party = Party.players.get(p.getName());
			
			if (party.getName().equalsIgnoreCase(p.getName())) {
				Timer timer = new Timer();
				Date timeToRun = new Date(System.currentTimeMillis() + 100);
				timer.schedule(new TimerTask() { public void run() {
					for (String pl : party.getUsers()) {
						Player ply = Bukkit.getPlayerExact(pl);
						if (ply != null) {
							party.subUser(ply);
							ply.sendMessage("§c파티장이신 §e[ " + p.getName() + " ] §c님이 서버에서 퇴장하여 파티가 해산되었습니다.");
						}
					}
					
					party.removeParty();
					return;
				} }, timeToRun);
			} else {
				party.subUser(p);
				Timer timer = new Timer(); Date timeToRun = new Date(System.currentTimeMillis() + 100);
				timer.schedule(new TimerTask() { public void run() {
					for (String pl : party.getUsers()) {
						Player ply = Bukkit.getPlayerExact(pl);
						if (ply != null) ply.sendMessage("§c파티에 §e[ " + p.getName() + " ] §c님이 나가셨습니다.");
					} return;
				} }, timeToRun);
			}
		}
	}
	
	@SuppressWarnings("resource")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.InvitePartyPlayer.contains(p)) {
			String name = e.getMessage();
			
			if (name.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 플레이어 초대를 §c취소 §6하셨습니다.");
				main.InvitePartyPlayer.remove(p);
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
			
			if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "파티 초대")) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 파티 초대 거부 상태입니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Party.players.containsKey(pl.getName())) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 이미 파티가 있습니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (main.OkInviteParty.get(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 이미 파티 초대를 받은 상태입니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (API.isUserVSMatch(pl)) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 대련 매칭을 진행중입니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (API.isUserVSPlaying(pl.getName())) {
				e.setCancelled(true);
				p.sendMessage("§c그 플레이어는 대련을 진행중입니다. 다시 입력해 주십시오");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			PartyGUI.InviteGUI(pl, Party.players.get(p.getName()).getName());
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "효과음")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			p.sendMessage("§6성공적으로 §c" + name + " §6님을 초대하셨습니다.");
			main.OkInviteParty.put(pl, p);
			main.InvitePartyPlayer.remove(p);
			e.setCancelled(true);
		}
		
		else if (main.SetLoreParty.containsKey(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 설명 추가/삭제 수정을 §c취소 §6하셨습니다.");
				main.SetLoreParty.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (lore.contains(" ")) {
				if (lore.split(" ")[0].equals("+")) {
					Party party = Party.players.get(p.getName());
					String message = Method.getFinalArg(lore.split(" "), 1);
					message = Method.replaceAllColors(message);
					List<String> list = party.getLore();
					list.add(message);
					party.setLore(list);

					e.setCancelled(true);
					p.sendMessage("");
					p.sendMessage("§6성공적으로 설명을 §c추가 §6하셨습니다.");
					p.sendMessage("§6추가된 부분: §f" + message);
					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					Party party = Party.players.get(p.getName());
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
					
					List<String> list = party.getLore();
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						party.setLore(list);

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
					p.sendMessage("§7예시) §f'+ 안녕하세요. 괴인 잡는 파티입니다.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
				p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
				p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
				p.sendMessage("§f");
				p.sendMessage("§7예시) §f'+ 안녕하세요. 괴인 잡는 파티입니다.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
		
		else if (Method.getPlayerChat(p).equalsIgnoreCase("파티")) {
			if (!Party.players.containsKey(p.getName()) || !Party.partylist.contains(Party.players.get(p.getName()))) {
				GuildAPI.setPlayerChat(p, "전체");
				return;
			}
			
			e.setCancelled(true);
			
			Party party = Party.players.get(p.getName());
			for (String pl : party.getUsers()) {
				Player player = Bukkit.getPlayerExact(pl);
				if (player.isOp()) continue;
				if (p.isOp()) player.sendMessage("§f[§e파티채팅§f] §f" + p.getName() + " §7:: §e" + e.getMessage());
				else player.sendMessage("§f[§e파티채팅§f] §r" + p.getDisplayName() + " §7:: §e" + e.getMessage());
			}
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.isOp()) {
					if (p.isOp()) pl.sendMessage("§f[§e파티채팅§f] §f" + p.getName() + " §7:: §e" + e.getMessage());
					else pl.sendMessage("§f[§e파티채팅§f] §r" + p.getDisplayName() + " §7:: §e" + e.getMessage());
				}
			}
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.YELLOW + "파티채팅" + ChatColor.WHITE + "] " + ChatColor.RESET + p.getDisplayName() + " " + ChatColor.GRAY + ":: " + ChatColor.YELLOW + e.getMessage());
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("파티에 초대받으셨습니다. 수락하시겠습니까?")) {
			if (main.OkInviteParty.get(p) != null) {
				p.sendMessage("§c인벤토리를 닫아 파티 초대에 자동으로 거절하였습니다.");
				main.OkInviteParty.get(p).sendMessage("§c" + p.getName() + "님이 파티 초대를 거절하셨습니다.");
				main.OkInviteParty.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("플레이어의 파티 가입을 수락하시겠습니까?")) {
			if (main.PartyJoin.get(p) != null) {
				p.sendMessage("§c인벤토리를 닫아 파티 초대에 자동으로 거절하였습니다.");
				main.PartyJoin.get(p).sendMessage("§c" + p.getName() + "님이 파티 초대를 거절하셨습니다.");
				main.PartyJoin.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("파티가 없으시군요!")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6파티 생성")) {
					if (Party.players.containsKey(p.getName())) {
						if (Guild.players.containsKey(p.getName())) {
							Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 파티 생성이 불가능합니다.");
								return;
							}
						}
						
						if (API.isUserVSMatch(p)) {
							e.setCancelled(true);
							p.sendMessage("§c당신은 매칭을 진행중이므로 파티 생성이 불가능합니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (API.isUserVSPlaying(p.getName())) {
							e.setCancelled(true);
							p.sendMessage("§c당신은 대련을 진행중이므로 파티 생성이 불가능합니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
    					p.closeInventory();
    					
    					Party party = new Party(p.getName());
    					party.addParty();
    					p.sendMessage("§6파티가 성공적으로 생성되었습니다. §e[ /파티 ]");
					} else {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					p.sendMessage("§c당신은 이미 파티가 있어 생성하실 수 없습니다.");
					}
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b파티 목록")) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					PartyGUI.ListGUI(p, 1);
				}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("파티에 초대받으셨습니다. 수락하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 수락하겠습니다.")) {
					if (Guild.players.containsKey(p.getName())) {
						Guild guild = Guild.players.get(p.getName());
						if (guild.getWar_Guild() != null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 파티 가입이 불가능합니다.");
        					main.OkInviteParty.remove(p);
        					p.closeInventory();
							return;
						}
					}
					
					if (API.isUserVSMatch(p)) {
						e.setCancelled(true);
						p.sendMessage("§c당신은 매칭을 진행중이므로 파티에 가입하실 수 없습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					if (API.isUserVSPlaying(p.getName())) {
						e.setCancelled(true);
						p.sendMessage("§c당신은 대련을 진행중이므로 파티에 가입하실 수 없습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					Party party = new Party(main.OkInviteParty.get(p).getName());
					for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("§b파티에 §e[ " + p.getName() + " ] §b님이 가입하셨습니다.");
					party.addUser(p);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(p);
					p.sendMessage("§6성공적으로 파티에 가입하셨습니다.");
					main.OkInviteParty.remove(p);
					p.closeInventory();
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 수락하지 않겠습니다.")) {
					main.OkInviteParty.get(p).sendMessage("§c" + p.getName() + "님이 파티 초대를 거절하셨습니다.");
					main.OkInviteParty.remove(p);
					p.closeInventory();
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("플레이어의 파티 가입을 수락하시겠습니까?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f예, 수락하겠습니다.")) {
					if (main.PartyJoin.get(p) != null) {
						Party party = new Party(p.getName());
    					Player player = main.PartyJoin.get(p);
    					
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 파티 가입 수락이 불가능합니다.");
	        					main.PartyJoin.remove(p);
	        					p.closeInventory();
								return;
							}
						}
    					
    					if (API.isUserVSMatch(player)) {
    						e.setCancelled(true);
    						p.sendMessage("§c그 플레이어는 매칭을 진행중이므로 파티에 가입시킬 수 없습니다.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					if (API.isUserVSPlaying(player.getName())) {
    						e.setCancelled(true);
    						p.sendMessage("§c그 플레이어는 대련을 진행중이므로 파티에 가입시킬 수 없습니다.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("§b파티에 §e[ " + player.getName() + " ] §b님이 가입하셨습니다.");
						party.addUser(player);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(player);
						player.sendMessage("§6성공적으로 파티에 가입하셨습니다.");
						main.PartyJoin.remove(p);
						p.closeInventory();
					} else {
						p.sendMessage("§4ERROR: §c신청한 플레이어의 데이터를 찾을 수 없습니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);	
					}
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 수락하지 않겠습니다.")) {
					if (main.PartyJoin.get(p) != null) {
    					main.PartyJoin.get(p).sendMessage("§c" + p.getName() + "님이 파티 신청을 거절하셨습니다.");
    					main.PartyJoin.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);	
					} else {
						p.sendMessage("§4ERROR: §c신청한 플레이어의 데이터를 찾을 수 없습니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);	
					}
				}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("파티 옵션")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		Party party = new Party(p.getName());
    		
    		if (party.getName().equalsIgnoreCase(p.getName()) && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6파티 설명 §e§l수정")) {
    				Guild guild = Guild.players.get(p.getName());
					if (main.SetLore.containsValue(guild.getName())) {
						p.sendMessage("§c당신은 이미 길드 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
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
					
					if (main.SetLoreParty.containsValue(party)) {
						p.sendMessage("§c당신은 이미 파티 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.closeInventory();
						return;
					}
					
					main.SetLoreParty.put(p, party.getName());
					p.sendMessage("");
					p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
					p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
					p.sendMessage("§f");
					p.sendMessage("§7예시) §f'+ 안녕하세요. 괴인 잡는 파티입니다.', '- 2'");
					p.closeInventory();
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3파티 가입조건")) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					
					if (party.getJoinType().equalsIgnoreCase("공개")) party.setJoinType("신청");
					else if (party.getJoinType().equalsIgnoreCase("신청")) party.setJoinType("비공개");
					else if (party.getJoinType().equalsIgnoreCase("비공개")) party.setJoinType("공개");
					
					if (party.getName().equalsIgnoreCase(p.getName()))
						PartyGUI.OptionGUI(p);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a파티 경험치 분배 여부")) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					party.setIsExp(!party.getIsExp());
					
					if (party.getName().equalsIgnoreCase(p.getName()))
						PartyGUI.OptionGUI(p);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
					if (!Party.players.containsKey(p.getName())) {
						PartyGUI.noPartyGUI(p);
					}
					
					else if (party.getName().equalsIgnoreCase(p.getName())) {
						PartyGUI.MasterPartyGUI(p);
					}
					
					else {
						PartyGUI.userPartyGUI(p);
					}
				}
    		}
     	}
     	
     	else {
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (p.getOpenInventory() == null || !Party.players.containsKey(p.getName()) || !Party.partylist.contains(Party.players.get(p.getName()))) return;
    		if (Party.partylist.contains(p.getName()) && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			Party party = Party.players.get(p.getName());
				for (String pl : party.getUsers()) {
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(pl)) {
			         	e.setCancelled(true);
						if (e.getClick() == ClickType.RIGHT) {
	    					OfflinePlayer op = Bukkit.getOfflinePlayer(pl);
	    					
	    					if (party.getName().equalsIgnoreCase(op.getName())) {
	    						return;
	    					}
	    					
	    					party.subUser(op);
							p.sendMessage("§6성공적으로 파티에서 §c" + pl + " §6님을 강제 퇴장 시켰습니다.");
							
	    					if (Method.getOnorOffLine(pl) != null) {
	    						Player pp = Method.getOnorOffLine(pl);
	    						pp.sendMessage("§c당신은 §f" + p.getName() + " §c님의 파티에서 강제 퇴장 당하셨습니다.");
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "효과음")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
	    					}
							
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
							p.closeInventory();
							for (String pls : party.getUsers()) Bukkit.getPlayerExact(pls).sendMessage("§c파티에 §e[ " + pl + " ] §c님이 강제 퇴장 당하셨습니다.");
						}
						
						else if (e.getClick() == ClickType.LEFT) {
	    					p.chat("/정보 " + pl);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						}
						
						else if (e.getClick() == ClickType.SHIFT_LEFT) {
							Player player = Method.getOnorOffLine(pl);
							if (player == null) {
        						p.sendMessage("§cERROR: 양도할 플레이어가 존재하지 않습니다.");
            					p.closeInventory();
        					} else {
        						if (player.getName().equalsIgnoreCase(party.getName())) return;
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "효과음")) player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					p.closeInventory();
            					p.sendMessage("§6성공적으로 §c" + player.getName() + " §6님에게 파티장을 양도하셨습니다.");
            					player.sendMessage("§6당신은 §c" + pl + " §6님의 파티장을 §c양도§6받으셨습니다.");
            					party.changePartyReader(player.getName());
        					}
						}
					}
				}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
     		if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("§2§lP")) {
         		e.setCancelled(true);
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().hasItemMeta()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c파티 탈퇴")) {
    					Party party = Party.players.get(p.getName());
    					
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						party.subUser(p);
						p.sendMessage("§6성공적으로 §c" + party.getName() + "§6 님의 파티에서 나가셨습니다.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("§c파티에 §e[ " + p.getName() + " ] §c님이 나가셨습니다.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b파티 채팅")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					String str = Method.NextChatMode(p, Method.getPlayerChat(p));
    					Method.setPlayerChat(p, str);
    					
    					if (!Party.players.containsKey(p.getName())) {
    						PartyGUI.noPartyGUI(p);
    						return;
    					}
    					
    					Party party = Party.players.get(p.getName());
    					if (party.getName().equalsIgnoreCase(p.getName())) {
    						PartyGUI.MasterPartyGUI(p);
    					} else {
    						PartyGUI.userPartyGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b파티 채팅")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					switch (Method.getPlayerChat(p)) {
    						case "전체":
    							Method.setPlayerChat(p, "파티");
    						case "파티":
    							Method.setPlayerChat(p, "전체");
    						case "길드":
    							Method.setPlayerChat(p, "파티");
    					}
    					
    					if (!Party.players.containsKey(p.getName())) {
    						PartyGUI.noPartyGUI(p);
    						return;
    					}
    					
    					Party party = Party.players.get(p.getName());
    					if (party.getName().equalsIgnoreCase(p.getName())) {
    						PartyGUI.MasterPartyGUI(p);
    					} else {
    						PartyGUI.userPartyGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4파티 해제")) {
    					Party party = Party.players.get(p.getName());
    					if (party != null && party.getName().equalsIgnoreCase(p.getName())) {
    						if (party.getUsers().size() == 1) {
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                				party.removeParty();
            					p.sendMessage("§a성공적으로 파티를 해제하셨습니다.");
            					p.closeInventory();
    						} else {
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            					p.sendMessage("§c모든 파티원을 강제 퇴장 시키고 해제를 진행해 주시기 바랍니다.");
    						}
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a플레이어 초대")) {
    					Party party = Party.players.get(p.getName());
    					if (party != null && party.getName().equalsIgnoreCase(p.getName())) {
        					if (party.getUsers().size() >= 4) {
        						p.closeInventory();
        						p.sendMessage("§c현재 최대 인원 수용 한도가 초과되어 더이상 플레이어를 초대하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
            				
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					p.closeInventory();
        					p.sendMessage("§6누구를 §c초대§6하시겠습니까? 채팅창에 적어주시기 바랍니다.");
        					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
        					main.InvitePartyPlayer.add(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f파티 옵션")) {
    					if (Party.partylist.contains(Party.players.get(p.getName()).getName().equalsIgnoreCase(p.getName()))) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    						PartyGUI.OptionGUI(p);
    					}
    				}
        		}
     		}
     	}
	}
	
	@EventHandler
    public void onSpaceClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
 		if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("파티") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("목록")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().getTypeId() == 119) return;
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
					if (!Party.players.containsKey(p.getName())) {
						PartyGUI.noPartyGUI(p);
						return;
					}
					
					Party party = Party.players.get(p.getName());
					if (party.getName().equalsIgnoreCase(p.getName())) {
						PartyGUI.MasterPartyGUI(p);
					} else {
						PartyGUI.userPartyGUI(p);
					}
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 랭킹 확인")) {
					PartyGUI.ListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 랭킹 확인")) {
					PartyGUI.ListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
				
				else {
					String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
					if (!Party.players.containsKey(p.getName())) {
						if (main.PartyJoin.containsValue(p)) {
							
							p.sendMessage("§c당신은 이미 이 파티에 신청하셨습니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	    					p.closeInventory();
							return;
						}
						
						Party party = Party.players.get(name);
						if (party.getJoinType().equalsIgnoreCase("공개")) {
        					if (party.getUsers().size() >= 4) {
        						p.closeInventory();
        						p.sendMessage("§c이 파티는 현재 최대 인원 수용 한도가 초과되어 가입하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (Guild.players.containsKey(p.getName())) {
        						Guild guild = Guild.players.get(p.getName());
    							if (guild.getWar_Guild() != null) {
    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 파티 가입이 불가능합니다.");
    	        					p.closeInventory();
    								return;
    							}
    						}
        					
        					if (API.isUserVSMatch(p)) {
        						e.setCancelled(true);
        						p.sendMessage("§c당신은 매칭을 진행중이므로 파티에 가입하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (API.isUserVSPlaying(p.getName())) {
        						e.setCancelled(true);
        						p.sendMessage("§c당신은 대련을 진행중이므로 파티에 가입하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("§b파티에 §e[ " + p.getName() + " ] §b님이 가입하셨습니다.");
        					party.addUser(p);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) Method.castLvup(p);
							p.sendMessage("§6성공적으로 파티에 가입하셨습니다.");
							p.closeInventory();
						}
						
						else if (party.getJoinType().equalsIgnoreCase("신청")) {
        					if (party.getUsers().size() >= 4) {
        						p.closeInventory();
        						p.sendMessage("§c이 파티는 현재 최대 인원 수용 한도가 초과되어 가입하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (Guild.players.containsKey(p.getName())) {
        						Guild guild = Guild.players.get(p.getName());
    							if (guild.getWar_Guild() != null) {
    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    	        					p.sendMessage("§c현재 가입된 길드가 전쟁중인 상태이므로 파티 가입이 불가능합니다.");
    	        					p.closeInventory();
    								return;
    							}
    						}
        					
        					if (API.isUserVSMatch(p)) {
        						e.setCancelled(true);
        						p.sendMessage("§c당신은 매칭을 진행중이므로 파티에 가입 신청을 하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (API.isUserVSPlaying(p.getName())) {
        						e.setCancelled(true);
        						p.sendMessage("§c당신은 대련을 진행중이므로 파티에 가입 신청을 하실 수 없습니다.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					main.PartyJoin.put(Bukkit.getPlayerExact(party.getName()), p);
							p.sendMessage("§6성공적으로 §c" + name + "§6 파티에 신청하셨습니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
							p.closeInventory();
							
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (pl.getName().equalsIgnoreCase(party.getName()))
									PartyGUI.AskJoinParty(pl, name);
							}
						}
						
						else if (party.getJoinType().equalsIgnoreCase("비공개")) {
							p.sendMessage("§c이 파티는 비공개 되어있어 가입할 수 없습니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
					}
				}
    		}
     	}
 	}
}
