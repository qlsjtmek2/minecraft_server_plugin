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
							ply.sendMessage("��c��Ƽ���̽� ��e[ " + p.getName() + " ] ��c���� �������� �����Ͽ� ��Ƽ�� �ػ�Ǿ����ϴ�.");
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
						if (ply != null) ply.sendMessage("��c��Ƽ�� ��e[ " + p.getName() + " ] ��c���� �����̽��ϴ�.");
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
			
			if (name.equalsIgnoreCase("���")) {
				e.setCancelled(true);
				p.sendMessage("��6���������� �÷��̾� �ʴ븦 ��c��� ��6�ϼ̽��ϴ�.");
				main.InvitePartyPlayer.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Method.searchOnlinePlayer(name) == null) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			name = Method.searchOnlinePlayer(name);
			
			if (Method.getOnorOffLine(name) == null) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			Player pl = Method.getOnorOffLine(name);
			
			if (!me.espoo.option.PlayerYml.getInfoBoolean(pl, "��Ƽ �ʴ�")) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� ��Ƽ �ʴ� �ź� �����Դϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (Party.players.containsKey(pl.getName())) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� �̹� ��Ƽ�� �ֽ��ϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (main.OkInviteParty.get(pl) != null) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� �̹� ��Ƽ �ʴ븦 ���� �����Դϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (API.isUserVSMatch(pl)) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� ��� ��Ī�� �������Դϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (API.isUserVSPlaying(pl.getName())) {
				e.setCancelled(true);
				p.sendMessage("��c�� �÷��̾�� ����� �������Դϴ�. �ٽ� �Է��� �ֽʽÿ�");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			PartyGUI.InviteGUI(pl, Party.players.get(p.getName()).getName());
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(pl, "ȿ����")) pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
			p.sendMessage("��6���������� ��c" + name + " ��6���� �ʴ��ϼ̽��ϴ�.");
			main.OkInviteParty.put(pl, p);
			main.InvitePartyPlayer.remove(p);
			e.setCancelled(true);
		}
		
		else if (main.SetLoreParty.containsKey(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("���")) {
				e.setCancelled(true);
				p.sendMessage("��6���������� ���� �߰�/���� ������ ��c��� ��6�ϼ̽��ϴ�.");
				main.SetLoreParty.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
					p.sendMessage("��6���������� ������ ��c�߰� ��6�ϼ̽��ϴ�.");
					p.sendMessage("��6�߰��� �κ�: ��f" + message);
					p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					Party party = Party.players.get(p.getName());
					Scanner scan = new Scanner(lore.split(" ")[1]);
					if (!scan.hasNextInt())
					{
						p.sendMessage("");
						p.sendMessage("��6������ ��c������6�Ͻ÷��� ��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
						p.sendMessage("��6<������ ����> �κп��� ���ڸ� �� �� �ֽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					int removeline = Integer.parseInt(lore.split(" ")[1]) - 1;
					
					if (removeline < 0) {
						p.sendMessage("");
						p.sendMessage("��6������ ��c������6�Ͻ÷��� ��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
						p.sendMessage("��6<������ ����> �κп��� ���ڸ� �� �� �ֽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					List<String> list = party.getLore();
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						party.setLore(list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("��6���������� ��c" + removeline + 1 + " ��6��° ������ �����ϼ̽��ϴ�.");
						p.sendMessage("��6������ �κ�: ��f" + str);
						p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return;
					} else {
						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("��c�ش� �κ��� �������� �ʴ� �����Դϴ�. �ٽ� �Է��� �ּ���.");
						p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				}
				
				else {
					p.sendMessage("");
					p.sendMessage("��6������ ��c�߰���6�Ͻ÷��� ��e'+ <����>'��6, ������ ��c������6�Ͻ÷���");
					p.sendMessage("��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
					p.sendMessage("��f* ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
					p.sendMessage("��f");
					p.sendMessage("��7����) ��f'+ �ȳ��ϼ���. ���� ��� ��Ƽ�Դϴ�.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("��6������ ��c�߰���6�Ͻ÷��� ��e'+ <����>'��6, ������ ��c������6�Ͻ÷���");
				p.sendMessage("��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
				p.sendMessage("��f* ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
				p.sendMessage("��f");
				p.sendMessage("��7����) ��f'+ �ȳ��ϼ���. ���� ��� ��Ƽ�Դϴ�.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
		
		else if (Method.getPlayerChat(p).equalsIgnoreCase("��Ƽ")) {
			if (!Party.players.containsKey(p.getName()) || !Party.partylist.contains(Party.players.get(p.getName()))) {
				GuildAPI.setPlayerChat(p, "��ü");
				return;
			}
			
			e.setCancelled(true);
			
			Party party = Party.players.get(p.getName());
			for (String pl : party.getUsers()) {
				Player player = Bukkit.getPlayerExact(pl);
				if (player.isOp()) continue;
				if (p.isOp()) player.sendMessage("��f[��e��Ƽä�á�f] ��f" + p.getName() + " ��7:: ��e" + e.getMessage());
				else player.sendMessage("��f[��e��Ƽä�á�f] ��r" + p.getDisplayName() + " ��7:: ��e" + e.getMessage());
			}
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.isOp()) {
					if (p.isOp()) pl.sendMessage("��f[��e��Ƽä�á�f] ��f" + p.getName() + " ��7:: ��e" + e.getMessage());
					else pl.sendMessage("��f[��e��Ƽä�á�f] ��r" + p.getDisplayName() + " ��7:: ��e" + e.getMessage());
				}
			}
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.YELLOW + "��Ƽä��" + ChatColor.WHITE + "] " + ChatColor.RESET + p.getDisplayName() + " " + ChatColor.GRAY + ":: " + ChatColor.YELLOW + e.getMessage());
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("��Ƽ�� �ʴ�����̽��ϴ�. �����Ͻðڽ��ϱ�?")) {
			if (main.OkInviteParty.get(p) != null) {
				p.sendMessage("��c�κ��丮�� �ݾ� ��Ƽ �ʴ뿡 �ڵ����� �����Ͽ����ϴ�.");
				main.OkInviteParty.get(p).sendMessage("��c" + p.getName() + "���� ��Ƽ �ʴ븦 �����ϼ̽��ϴ�.");
				main.OkInviteParty.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
		
		else if (e.getInventory().getName().equalsIgnoreCase("�÷��̾��� ��Ƽ ������ �����Ͻðڽ��ϱ�?")) {
			if (main.PartyJoin.get(p) != null) {
				p.sendMessage("��c�κ��丮�� �ݾ� ��Ƽ �ʴ뿡 �ڵ����� �����Ͽ����ϴ�.");
				main.PartyJoin.get(p).sendMessage("��c" + p.getName() + "���� ��Ƽ �ʴ븦 �����ϼ̽��ϴ�.");
				main.PartyJoin.remove(p);
				p.closeInventory();
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
			}
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("��Ƽ�� �����ñ���!")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6��Ƽ ����")) {
					if (Party.players.containsKey(p.getName())) {
						if (Guild.players.containsKey(p.getName())) {
							Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("��c���� ���Ե� ��尡 �������� �����̹Ƿ� ��Ƽ ������ �Ұ����մϴ�.");
								return;
							}
						}
						
						if (API.isUserVSMatch(p)) {
							e.setCancelled(true);
							p.sendMessage("��c����� ��Ī�� �������̹Ƿ� ��Ƽ ������ �Ұ����մϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (API.isUserVSPlaying(p.getName())) {
							e.setCancelled(true);
							p.sendMessage("��c����� ����� �������̹Ƿ� ��Ƽ ������ �Ұ����մϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2.0F, 1.5F);
    					p.closeInventory();
    					
    					Party party = new Party(p.getName());
    					party.addParty();
    					p.sendMessage("��6��Ƽ�� ���������� �����Ǿ����ϴ�. ��e[ /��Ƽ ]");
					} else {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    					p.sendMessage("��c����� �̹� ��Ƽ�� �־� �����Ͻ� �� �����ϴ�.");
					}
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b��Ƽ ���")) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					PartyGUI.ListGUI(p, 1);
				}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ�� �ʴ�����̽��ϴ�. �����Ͻðڽ��ϱ�?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, �����ϰڽ��ϴ�.")) {
					if (Guild.players.containsKey(p.getName())) {
						Guild guild = Guild.players.get(p.getName());
						if (guild.getWar_Guild() != null) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        					p.sendMessage("��c���� ���Ե� ��尡 �������� �����̹Ƿ� ��Ƽ ������ �Ұ����մϴ�.");
        					main.OkInviteParty.remove(p);
        					p.closeInventory();
							return;
						}
					}
					
					if (API.isUserVSMatch(p)) {
						e.setCancelled(true);
						p.sendMessage("��c����� ��Ī�� �������̹Ƿ� ��Ƽ�� �����Ͻ� �� �����ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					if (API.isUserVSPlaying(p.getName())) {
						e.setCancelled(true);
						p.sendMessage("��c����� ����� �������̹Ƿ� ��Ƽ�� �����Ͻ� �� �����ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					Party party = new Party(main.OkInviteParty.get(p).getName());
					for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ�� ��e[ " + p.getName() + " ] ��b���� �����ϼ̽��ϴ�.");
					party.addUser(p);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) Method.castLvup(p);
					p.sendMessage("��6���������� ��Ƽ�� �����ϼ̽��ϴ�.");
					main.OkInviteParty.remove(p);
					p.closeInventory();
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, �������� �ʰڽ��ϴ�.")) {
					main.OkInviteParty.get(p).sendMessage("��c" + p.getName() + "���� ��Ƽ �ʴ븦 �����ϼ̽��ϴ�.");
					main.OkInviteParty.remove(p);
					p.closeInventory();
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�÷��̾��� ��Ƽ ������ �����Ͻðڽ��ϱ�?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, �����ϰڽ��ϴ�.")) {
					if (main.PartyJoin.get(p) != null) {
						Party party = new Party(p.getName());
    					Player player = main.PartyJoin.get(p);
    					
    					if (Guild.players.containsKey(p.getName())) {
    						Guild guild = Guild.players.get(p.getName());
							if (guild.getWar_Guild() != null) {
	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	        					p.sendMessage("��c���� ���Ե� ��尡 �������� �����̹Ƿ� ��Ƽ ���� ������ �Ұ����մϴ�.");
	        					main.PartyJoin.remove(p);
	        					p.closeInventory();
								return;
							}
						}
    					
    					if (API.isUserVSMatch(player)) {
    						e.setCancelled(true);
    						p.sendMessage("��c�� �÷��̾�� ��Ī�� �������̹Ƿ� ��Ƽ�� ���Խ�ų �� �����ϴ�.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					if (API.isUserVSPlaying(player.getName())) {
    						e.setCancelled(true);
    						p.sendMessage("��c�� �÷��̾�� ����� �������̹Ƿ� ��Ƽ�� ���Խ�ų �� �����ϴ�.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ�� ��e[ " + player.getName() + " ] ��b���� �����ϼ̽��ϴ�.");
						party.addUser(player);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) Method.castLvup(player);
						player.sendMessage("��6���������� ��Ƽ�� �����ϼ̽��ϴ�.");
						main.PartyJoin.remove(p);
						p.closeInventory();
					} else {
						p.sendMessage("��4ERROR: ��c��û�� �÷��̾��� �����͸� ã�� �� �����ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);	
					}
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, �������� �ʰڽ��ϴ�.")) {
					if (main.PartyJoin.get(p) != null) {
    					main.PartyJoin.get(p).sendMessage("��c" + p.getName() + "���� ��Ƽ ��û�� �����ϼ̽��ϴ�.");
    					main.PartyJoin.remove(p);
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);	
					} else {
						p.sendMessage("��4ERROR: ��c��û�� �÷��̾��� �����͸� ã�� �� �����ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);	
					}
				}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ �ɼ�")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		Party party = new Party(p.getName());
    		
    		if (party.getName().equalsIgnoreCase(p.getName()) && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6��Ƽ ���� ��e��l����")) {
    				Guild guild = Guild.players.get(p.getName());
					if (main.SetLore.containsValue(guild.getName())) {
						p.sendMessage("��c����� �̹� ��� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.closeInventory();
						return;
					}
					
					if (main.SetStatMessage.containsValue(guild.getName())) {
						p.sendMessage("��c����� �̹� ���� �޼����� �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.closeInventory();
						return;
					}
					
					if (main.SetItemCode.containsValue(guild.getName())) {
						p.sendMessage("��c����� �̹� ������ �ڵ带 �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.closeInventory();
						return;
					}
					
					if (main.SetLoreParty.containsValue(party)) {
						p.sendMessage("��c����� �̹� ��Ƽ ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.closeInventory();
						return;
					}
					
					main.SetLoreParty.put(p, party.getName());
					p.sendMessage("");
					p.sendMessage("��6������ ��c�߰���6�Ͻ÷��� ��e'+ <����>'��6, ������ ��c������6�Ͻ÷���");
					p.sendMessage("��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
					p.sendMessage("��f* ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
					p.sendMessage("��f");
					p.sendMessage("��7����) ��f'+ �ȳ��ϼ���. ���� ��� ��Ƽ�Դϴ�.', '- 2'");
					p.closeInventory();
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��3��Ƽ ��������")) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					
					if (party.getJoinType().equalsIgnoreCase("����")) party.setJoinType("��û");
					else if (party.getJoinType().equalsIgnoreCase("��û")) party.setJoinType("�����");
					else if (party.getJoinType().equalsIgnoreCase("�����")) party.setJoinType("����");
					
					if (party.getName().equalsIgnoreCase(p.getName()))
						PartyGUI.OptionGUI(p);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��a��Ƽ ����ġ �й� ����")) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
					party.setIsExp(!party.getIsExp());
					
					if (party.getName().equalsIgnoreCase(p.getName()))
						PartyGUI.OptionGUI(p);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
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
							p.sendMessage("��6���������� ��Ƽ���� ��c" + pl + " ��6���� ���� ���� ���׽��ϴ�.");
							
	    					if (Method.getOnorOffLine(pl) != null) {
	    						Player pp = Method.getOnorOffLine(pl);
	    						pp.sendMessage("��c����� ��f" + p.getName() + " ��c���� ��Ƽ���� ���� ���� ���ϼ̽��ϴ�.");
		    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "ȿ����")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
	    					}
							
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
							p.closeInventory();
							for (String pls : party.getUsers()) Bukkit.getPlayerExact(pls).sendMessage("��c��Ƽ�� ��e[ " + pl + " ] ��c���� ���� ���� ���ϼ̽��ϴ�.");
						}
						
						else if (e.getClick() == ClickType.LEFT) {
	    					p.chat("/���� " + pl);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						}
						
						else if (e.getClick() == ClickType.SHIFT_LEFT) {
							Player player = Method.getOnorOffLine(pl);
							if (player == null) {
        						p.sendMessage("��cERROR: �絵�� �÷��̾ �������� �ʽ��ϴ�.");
            					p.closeInventory();
        					} else {
        						if (player.getName().equalsIgnoreCase(party.getName())) return;
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "ȿ����")) player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
            					p.closeInventory();
            					p.sendMessage("��6���������� ��c" + player.getName() + " ��6�Կ��� ��Ƽ���� �絵�ϼ̽��ϴ�.");
            					player.sendMessage("��6����� ��c" + pl + " ��6���� ��Ƽ���� ��c�絵��6�����̽��ϴ�.");
            					party.changePartyReader(player.getName());
        					}
						}
					}
				}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
     		if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("��2��lP")) {
         		e.setCancelled(true);
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().hasItemMeta()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    					p.closeInventory();
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��c��Ƽ Ż��")) {
    					Party party = Party.players.get(p.getName());
    					
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						party.subUser(p);
						p.sendMessage("��6���������� ��c" + party.getName() + "��6 ���� ��Ƽ���� �����̽��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("��c��Ƽ�� ��e[ " + p.getName() + " ] ��c���� �����̽��ϴ�.");
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b��Ƽ ä��")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
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
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b��Ƽ ä��")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					switch (Method.getPlayerChat(p)) {
    						case "��ü":
    							Method.setPlayerChat(p, "��Ƽ");
    						case "��Ƽ":
    							Method.setPlayerChat(p, "��ü");
    						case "���":
    							Method.setPlayerChat(p, "��Ƽ");
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
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��4��Ƽ ����")) {
    					Party party = Party.players.get(p.getName());
    					if (party != null && party.getName().equalsIgnoreCase(p.getName())) {
    						if (party.getUsers().size() == 1) {
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                				party.removeParty();
            					p.sendMessage("��a���������� ��Ƽ�� �����ϼ̽��ϴ�.");
            					p.closeInventory();
    						} else {
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            					p.sendMessage("��c��� ��Ƽ���� ���� ���� ��Ű�� ������ ������ �ֽñ� �ٶ��ϴ�.");
    						}
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��a�÷��̾� �ʴ�")) {
    					Party party = Party.players.get(p.getName());
    					if (party != null && party.getName().equalsIgnoreCase(p.getName())) {
        					if (party.getUsers().size() >= 4) {
        						p.closeInventory();
        						p.sendMessage("��c���� �ִ� �ο� ���� �ѵ��� �ʰ��Ǿ� ���̻� �÷��̾ �ʴ��Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
            				
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					p.closeInventory();
        					p.sendMessage("��6������ ��c�ʴ��6�Ͻðڽ��ϱ�? ä��â�� �����ֽñ� �ٶ��ϴ�.");
        					p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
        					main.InvitePartyPlayer.add(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��Ƽ �ɼ�")) {
    					if (Party.partylist.contains(Party.players.get(p.getName()).getName().equalsIgnoreCase(p.getName()))) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
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
 		if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("��Ƽ") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("���")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().getTypeId() == 119) return;
    		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
    			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
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
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��ŷ Ȯ��")) {
					PartyGUI.ListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��ŷ Ȯ��")) {
					PartyGUI.ListGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
				}
				
				else {
					String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0]);
					if (!Party.players.containsKey(p.getName())) {
						if (main.PartyJoin.containsValue(p)) {
							
							p.sendMessage("��c����� �̹� �� ��Ƽ�� ��û�ϼ̽��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
	    					p.closeInventory();
							return;
						}
						
						Party party = Party.players.get(name);
						if (party.getJoinType().equalsIgnoreCase("����")) {
        					if (party.getUsers().size() >= 4) {
        						p.closeInventory();
        						p.sendMessage("��c�� ��Ƽ�� ���� �ִ� �ο� ���� �ѵ��� �ʰ��Ǿ� �����Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (Guild.players.containsKey(p.getName())) {
        						Guild guild = Guild.players.get(p.getName());
    							if (guild.getWar_Guild() != null) {
    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    	        					p.sendMessage("��c���� ���Ե� ��尡 �������� �����̹Ƿ� ��Ƽ ������ �Ұ����մϴ�.");
    	        					p.closeInventory();
    								return;
    							}
    						}
        					
        					if (API.isUserVSMatch(p)) {
        						e.setCancelled(true);
        						p.sendMessage("��c����� ��Ī�� �������̹Ƿ� ��Ƽ�� �����Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (API.isUserVSPlaying(p.getName())) {
        						e.setCancelled(true);
        						p.sendMessage("��c����� ����� �������̹Ƿ� ��Ƽ�� �����Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					for (String pl : party.getUsers()) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ�� ��e[ " + p.getName() + " ] ��b���� �����ϼ̽��ϴ�.");
        					party.addUser(p);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) Method.castLvup(p);
							p.sendMessage("��6���������� ��Ƽ�� �����ϼ̽��ϴ�.");
							p.closeInventory();
						}
						
						else if (party.getJoinType().equalsIgnoreCase("��û")) {
        					if (party.getUsers().size() >= 4) {
        						p.closeInventory();
        						p.sendMessage("��c�� ��Ƽ�� ���� �ִ� �ο� ���� �ѵ��� �ʰ��Ǿ� �����Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (Guild.players.containsKey(p.getName())) {
        						Guild guild = Guild.players.get(p.getName());
    							if (guild.getWar_Guild() != null) {
    	        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    	        					p.sendMessage("��c���� ���Ե� ��尡 �������� �����̹Ƿ� ��Ƽ ������ �Ұ����մϴ�.");
    	        					p.closeInventory();
    								return;
    							}
    						}
        					
        					if (API.isUserVSMatch(p)) {
        						e.setCancelled(true);
        						p.sendMessage("��c����� ��Ī�� �������̹Ƿ� ��Ƽ�� ���� ��û�� �Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					if (API.isUserVSPlaying(p.getName())) {
        						e.setCancelled(true);
        						p.sendMessage("��c����� ����� �������̹Ƿ� ��Ƽ�� ���� ��û�� �Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					main.PartyJoin.put(Bukkit.getPlayerExact(party.getName()), p);
							p.sendMessage("��6���������� ��c" + name + "��6 ��Ƽ�� ��û�ϼ̽��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
							p.closeInventory();
							
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (pl.getName().equalsIgnoreCase(party.getName()))
									PartyGUI.AskJoinParty(pl, name);
							}
						}
						
						else if (party.getJoinType().equalsIgnoreCase("�����")) {
							p.sendMessage("��c�� ��Ƽ�� ����� �Ǿ��־� ������ �� �����ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
					}
				}
    		}
     	}
 	}
}
