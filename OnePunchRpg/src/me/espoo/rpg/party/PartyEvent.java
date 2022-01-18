package me.espoo.rpg.party;

import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.pvp.API;
import me.espoo.rpg.Method;
import me.espoo.rpg.PlayerYml;
import me.espoo.rpg.main;
import me.espoo.rpg.guild.GuildAPI;

@SuppressWarnings("deprecation")
public class PartyEvent extends JavaPlugin implements Listener {
	main M;
	
	public PartyEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String party = PartyAPI.getJoinParty(p);
		
		if (party != null) {
			if (main.InvitePartyPlayer.contains(p)) main.InvitePartyPlayer.remove(p);
			if (main.SetLoreParty.get(p) != null) main.SetLoreParty.remove(p);
			if (main.PartyJoin.containsKey(p)) main.PartyJoin.remove(p);
			if (main.PartyJoin.containsValue(p)) main.PartyJoin.remove(p);
			
			if (PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
				if (PartyAPI.getUserInteger(party) == 1) {
					PartyYml.deleteParty(party, p);
				} else {
					Player player = Bukkit.getPlayerExact(PartyAPI.RandomParty(PartyAPI.getUser(party)));
					
					if (API.isTwoVSPlaying(party)) {
						API.setChangeName(party, player.getName(), 2);
					}
					
					else if (API.isThreeVSPlaying(party)) {
						API.setChangeName(party, player.getName(), 3);
					}
					
					else if (API.isFourVSPlaying(party)) {
						API.setChangeName(party, player.getName(), 4);
					}
					
					PartyAPI.changePartyMaster(p, player);
					PartyAPI.subUser(player.getName(), p);
					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "ȿ����")) player.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					for (String pl : PartyAPI.getUser(player.getName())) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ���̽� ��e[ " + p.getName() + " ] ��b���� �������� �����Ͽ� ��Ƽ���� ��e" + player.getName() + " ��b������ ����Ǿ����ϴ�.");
				}
			} else {
				PartyAPI.subUser(party, p);
				for (String pl : PartyAPI.getUser(party)) {
					Player ply = Bukkit.getPlayerExact(pl);
					if (ply != null) ply.sendMessage("��c��Ƽ�� ��e[ " + p.getName() + " ] ��c���� �����̽��ϴ�.");
				}
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
			
			if (PartyAPI.getJoinParty(pl) != null) {
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
			
			String party = PartyAPI.getJoinParty(p);
			if (API.isPartyVSMatch(party)) {
				e.setCancelled(true);
				p.sendMessage("��c���� ��Ƽ�� ��Ī�� �������̿��� ���� �ʴ븦 �Ͻ� �� �����ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}

			if (API.isPartyVSPlaying(party)) {
				e.setCancelled(true);
				p.sendMessage("��c���� ��Ƽ�� ����� �������̿��� ���� �ʴ븦 �Ͻ� �� �����ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			PartyGUI.InviteGUI(pl, party);
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
					String name = PartyAPI.getJoinParty(p);
					String message = Method.getFinalArg(lore.split(" "), 1);
					message = Method.replaceAllColors(message);
					List<String> list = PartyAPI.getInfo(name);
					list.add(message);
					PartyAPI.setInfo(name, list);

					e.setCancelled(true);
					p.sendMessage("");
					p.sendMessage("��6���������� ������ ��c�߰� ��6�ϼ̽��ϴ�.");
					p.sendMessage("��6�߰��� �κ�: ��f" + message);
					p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					String name = PartyAPI.getJoinParty(p);
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
					
					List<String> list = PartyAPI.getInfo(name);
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						PartyAPI.setInfo(name, list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("��6���������� ��c" + removeline + " ��6��° ������ �����ϼ̽��ϴ�.");
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
		
		else if (PartyAPI.getPlayerChat(p).equalsIgnoreCase("��Ƽ")) {
			e.setCancelled(true);
			for (String pl : PartyAPI.getUser(PartyAPI.getJoinParty(p))) {
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
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6��Ƽ ����")) {
    					if (PartyAPI.getJoinParty(p) == null) {
    						if (GuildAPI.getJoinGuild(p) != null) {
    							String name = GuildAPI.getJoinGuild(p);
    							if (GuildAPI.isWar(name)) {
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
        					PlayerYml.setInfoString(p, "���Ե� ��Ƽ", p.getName());
        					PartyYml.addParty(p.getName());
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
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ�� �ʴ�����̽��ϴ�. �����Ͻðڽ��ϱ�?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, �����ϰڽ��ϴ�.")) {
    					if (GuildAPI.getJoinGuild(p) != null) {
							String name = GuildAPI.getJoinGuild(p);
							if (GuildAPI.isWar(name)) {
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

    					String name = PartyAPI.getJoinParty(main.OkInviteParty.get(p));
    					if (API.isPartyVSMatch(name)) {
    						e.setCancelled(true);
    						p.sendMessage("��c��Ƽ�� ��Ī�� �������̿��� �� ��Ƽ�� ������ �Ͻ� �� �����ϴ�.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}

    					if (API.isPartyVSPlaying(name)) {
    						e.setCancelled(true);
    						p.sendMessage("��c��Ƽ�� ����� �������̿��� �� ��Ƽ�� ������ �Ͻ� �� �����ϴ�.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					for (String pl : PartyAPI.getUser(name)) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ�� ��e[ " + p.getName() + " ] ��b���� �����ϼ̽��ϴ�.");
						PartyAPI.addUser(name, p);
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
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�÷��̾��� ��Ƽ ������ �����Ͻðڽ��ϱ�?")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f��, �����ϰڽ��ϴ�.")) {
    					if (main.PartyJoin.get(p) != null) {
        					String name = PartyAPI.getJoinParty(p);
        					Player player = main.PartyJoin.get(p);
        					
        					if (GuildAPI.getJoinGuild(p) != null) {
    							String guild = GuildAPI.getJoinGuild(p);
    							if (GuildAPI.isWar(guild)) {
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

        					if (API.isPartyVSMatch(name)) {
        						e.setCancelled(true);
        						p.sendMessage("��c��Ƽ�� ��Ī�� �������̿��� ��Ƽ�� ���Խ�ų �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}

        					if (API.isPartyVSPlaying(name)) {
        						e.setCancelled(true);
        						p.sendMessage("��c��Ƽ�� ����� �������̿��� ��Ƽ�� ���Խ�ų �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
        					
        					for (String pl : PartyAPI.getUser(name)) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ�� ��e[ " + p.getName() + " ] ��b���� �����ϼ̽��ϴ�.");
    						PartyAPI.addUser(name, player);
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) Method.castLvup(player);
    						player.sendMessage("��6���������� ��Ƽ�� �����ϼ̽��ϴ�.");
    						main.PartyJoin.remove(p);
    						p.closeInventory();
    					} else {
    						p.sendMessage("��4ERROR: ��c��û�� �÷��̾��� �����͸� ã�� �� �������ϴ�.");
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
    						p.sendMessage("��4ERROR: ��c��û�� �÷��̾��� �����͸� ã�� �� �������ϴ�.");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);	
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("��Ƽ �ɼ�")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
    			if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6��Ƽ ���� ��e��l����")) {
    						String name = GuildAPI.getJoinGuild(p);
    						String party = PartyAPI.getJoinParty(p);
    						if (main.SetLore.containsValue(name)) {
    							p.sendMessage("��c����� �̹� ��� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetStatMessage.containsValue(name)) {
    							p.sendMessage("��c����� �̹� ���� �޼����� �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    							p.closeInventory();
    							return;
    						}
    						
    						if (main.SetItemCode.containsValue(name)) {
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
    						
    						main.SetLoreParty.put(p, party);
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
	    					
	    					String name = PartyAPI.getJoinParty(p);
	    					if (PartyAPI.getJoinType(name).equalsIgnoreCase("����")) PartyAPI.setJoinType(name, 1);
	    					else if (PartyAPI.getJoinType(name).equalsIgnoreCase("��û")) PartyAPI.setJoinType(name, 2);
	    					else if (PartyAPI.getJoinType(name).equalsIgnoreCase("�����")) PartyAPI.setJoinType(name, 0);
	    					
	    					if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName()))
	    						PartyGUI.OptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��a��Ƽ ����ġ, ����� ���� �й� ����")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					
	    					String name = PartyAPI.getJoinParty(p);
	    					PartyAPI.setShare(name, !PartyAPI.getShare(name));
	    					
	    					if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName()))
	    						PartyGUI.OptionGUI(p);
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
        					if (PartyAPI.getJoinParty(p) == null) {
        						PartyGUI.noPartyGUI(p);
        					}
        					
        					else if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
        						PartyGUI.MasterPartyGUI(p);
        					}
        					
        					else {
        						PartyGUI.userPartyGUI(p);
        					}
        				}
        			}
    			}
    		}
     	}
     	
     	else {
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (p.getOpenInventory() == null) return;
    		if (PartyAPI.getJoinParty(p) == null) return;
    		if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
    			if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
						String name = PartyAPI.getJoinParty(p);
        				for (String pl : PartyAPI.getUser(name)) {
        					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(pl)) {
        			         	e.setCancelled(true);
        						if (e.getAction() == InventoryAction.PICKUP_HALF) {
    		    					OfflinePlayer op = Bukkit.getOfflinePlayer(pl);
    		    					
    		    					if (PartyAPI.getManager(name).equalsIgnoreCase(op.getName())) {
    		    						return;
    		    					}
    		    					
    	        					if (API.isPartyVSPlaying(name)) {
    	        						p.sendMessage("��c��Ƽ�� ����� �������̿��� �÷��̾ ���� �����Ű�� �� �����ϴ�.");
    	        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    	        						return;
    	        					}
    		    					
    								PartyAPI.subUser(name, op);
    								p.sendMessage("��6���������� ��Ƽ���� ��c" + pl + " ��6���� ���� ���� ���׽��ϴ�.");
    								
    		    					if (Method.getOnorOffLine(pl) != null) {
    		    						Player pp = Method.getOnorOffLine(pl);
    		    						pp.sendMessage("��c����� ��f" + p.getName() + " ��c���� ��Ƽ���� ���� ���� ���ϼ̽��ϴ�.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(pp, "ȿ����")) pp.playSound(pp.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.5F);
    		    					}
    								
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    								p.closeInventory();
    								for (String pls : PartyAPI.getUser(name)) Bukkit.getPlayerExact(pls).sendMessage("��c��忡 ��e[ " + p.getName() + " ] ��c���� ���� ���� ���ϼ̽��ϴ�.");
    								
    	        					if (API.isPartyVSMatch(name)) {
        								for (String pls : PartyAPI.getUser(name)) {
        									Player play = Bukkit.getPlayerExact(pls);
        									play.sendMessage("��6��Ƽ���� ��c" + p.getName() + " ��6���� ���� ���� ���Ͽ��� ��� ��Ī�� �ڵ����� ��ҵǾ����ϴ�.");
        									API.StopPartyMatch(name);
        	        						if (me.espoo.option.PlayerYml.getInfoBoolean(play, "ȿ����")) play.playSound(play.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        								} return;
    	        					}
    							}
        						
        						else if (e.getAction() == InventoryAction.PICKUP_ALL) {
            						if (pl.equalsIgnoreCase(PartyAPI.getManager(name))) return;
    		    					p.chat("/���� " + pl);
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    							}
    							
        						else if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
        							Player player = Method.getOnorOffLine(pl);
        							if (player == null) {
                						p.sendMessage("��cERROR: �絵�� �÷��̾ �������� �ʽ��ϴ�.");
                    					p.closeInventory();
                					} else {
                						if (player.getName().equalsIgnoreCase(PartyAPI.getManager(name))) return;
                    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                    					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "ȿ����")) player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                    					p.closeInventory();
                    					p.sendMessage("��6���������� ��c" + player.getName() + " ��6�Կ��� ��Ƽ���� �絵�ϼ̽��ϴ�.");
                    					player.sendMessage("��6����� ��c" + pl + " ��6���� ��Ƽ���� ��c�絵��6�����̽��ϴ�.");
                    					PartyAPI.changePartyMaster(p, player);
                					}
    							}
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
    					String name = PartyAPI.getJoinParty(p);
    					if (API.isPartyVSPlaying(name)) {
    						p.sendMessage("��c��Ƽ�� ����� �������̿��� ��Ƽ���� ������ �� �����ϴ�.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
						PartyAPI.subUser(name, p);
						p.sendMessage("��6���������� ��c" + name + "��6 ���� ��Ƽ���� �����̽��ϴ�.");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						p.closeInventory();
						for (String pl : PartyAPI.getUser(name)) Bukkit.getPlayerExact(pl).sendMessage("��c��Ƽ�� ��e[ " + p.getName() + " ] ��c���� �����̽��ϴ�.");
						
						if (API.isPartyVSMatch(name)) {
							for (String pls : PartyAPI.getUser(name)) {
								Player play = Bukkit.getPlayerExact(pls);
								play.sendMessage("��6��Ƽ���� ��c" + p.getName() + " ��6���� �����ż� ��� ��Ī�� �ڵ����� ��ҵǾ����ϴ�.");
								API.StopPartyMatch(name);
        						if (me.espoo.option.PlayerYml.getInfoBoolean(play, "ȿ����")) play.playSound(play.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							} return;
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b��Ƽ ä��")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					String str = Method.NextChatMode(p, PartyAPI.getPlayerChat(p));
    					PartyAPI.setPlayerChat(p, str);
    					
    					if (PartyAPI.getJoinParty(p) == null) {
    						PartyGUI.noPartyGUI(p);
    					}
    					
    					else if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
    						PartyGUI.MasterPartyGUI(p);
    					}
    					
    					else {
    						PartyGUI.userPartyGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b��Ƽ ä��")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					switch (PartyAPI.getPlayerChat(p)) {
    						case "��ü":
    							PartyAPI.setPlayerChat(p, "��Ƽ");
    						case "��Ƽ":
    							PartyAPI.setPlayerChat(p, "��ü");
    						case "���":
    							PartyAPI.setPlayerChat(p, "��Ƽ");
    					}
    					
    					
    					if (PartyAPI.getJoinParty(p) == null) {
    						PartyGUI.noPartyGUI(p);
    					}
    					
    					else if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
    						PartyGUI.MasterPartyGUI(p);
    					}
    					
    					else {
    						PartyGUI.userPartyGUI(p);
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��4��Ƽ ����")) {
    					if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
            				String name = PartyAPI.getJoinParty(p);
            				
    						if (API.isPartyVSMatch(name)) {
        						p.sendMessage("��c��Ƽ�� ��Ī�� �������̿��� ��Ƽ ������ �Ͻ� �� �����ϴ�. ��Ī�� ��� �� ������ �ֽñ� �ٶ��ϴ�. ��e/���");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
    						
            				if (API.isPartyVSPlaying(name)) {
        						p.sendMessage("��c��Ƽ�� ����� �������̿��� ��Ƽ ������ �Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
            				
    						if (PartyAPI.getUser(name).size() == 1) {
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                				PartyYml.deleteParty(name, p);
            					p.sendMessage("��a���������� ��Ƽ�� �����ϼ̽��ϴ�.");
            					p.closeInventory();
    						} else {
            					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            					p.sendMessage("��c��� ��Ƽ���� ���� ���� ��Ű�� ������ ������ �ֽñ� �ٶ��ϴ�.");
    						}
    					}
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��a�÷��̾� �ʴ�")) {
    					if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
        					String name = PartyAPI.getJoinParty(p);
        					if (PartyAPI.getUserInteger(name) >= 4) {
        						p.closeInventory();
        						p.sendMessage("��c���� �ִ� �ο� ���� �ѵ��� �ʰ��Ǿ� ���̻� �÷��̾ �ʴ��Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
            				
    						if (API.isPartyVSMatch(name)) {
        						p.sendMessage("��c��Ƽ�� ��Ī�� �������̿��� �÷��̾� �ʴ븦 �Ͻ� �� �����ϴ�.");
        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        						return;
        					}
    						
            				if (API.isPartyVSPlaying(name)) {
        						p.sendMessage("��c��Ƽ�� ����� �������̿��� �÷��̾� �ʴ븦 �Ͻ� �� �����ϴ�.");
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
    					if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
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
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
    					if (PartyAPI.getJoinParty(p) == null) {
    						PartyGUI.noPartyGUI(p);
    					}
    					
    					else if (PartyAPI.getManager(PartyAPI.getJoinParty(p)).equalsIgnoreCase(p.getName())) {
    						PartyGUI.MasterPartyGUI(p);
    					}
    					
    					else {
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
						if (PartyAPI.isParty(name)) {
							if (PartyAPI.getJoinParty(p) == null) {
    							if (e.getAction() == InventoryAction.PICKUP_ALL) {
    								if (main.PartyJoin.containsValue(p)) {
    									
    									p.sendMessage("��c����� �̹� �� ��Ƽ�� ��û�ϼ̽��ϴ�.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    			    					p.closeInventory();
    									return;
    								}
    								
    								if (PartyAPI.getJoinType(name).equalsIgnoreCase("����")) {
    		        					if (PartyAPI.getUserInteger(name) >= 4) {
    		        						p.closeInventory();
    		        						p.sendMessage("��c�� ��Ƽ�� ���� �ִ� �ο� ���� �ѵ��� �ʰ��Ǿ� �����Ͻ� �� �����ϴ�.");
    		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		        						return;
    		        					}
    		        					
    		        					if (GuildAPI.getJoinGuild(p) != null) {
    		    							String guild = GuildAPI.getJoinGuild(p);
    		    							if (GuildAPI.isWar(guild)) {
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
    		        					
    		        					if (API.isPartyVSMatch(name)) {
    		        						e.setCancelled(true);
    		        						p.sendMessage("��c���� �� ��Ƽ�� ��Ī�� �������̿��� ��Ƽ ������ �Ͻ� �� �����ϴ�.");
    		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		        						return;
    		        					}

    		        					if (API.isPartyVSPlaying(name)) {
    		        						e.setCancelled(true);
    		        						p.sendMessage("��c���� �� ��Ƽ�� ����� �������̿��� ��Ƽ ������ �Ͻ� �� �����ϴ�.");
    		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		        						return;
    		        					}
    		        					
    		        					for (String pl : PartyAPI.getUser(name)) Bukkit.getPlayerExact(pl).sendMessage("��b��Ƽ�� ��e[ " + p.getName() + " ] ��b���� �����ϼ̽��ϴ�.");
    		        					PartyAPI.addUser(name, p);
    									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) Method.castLvup(p);
    									p.sendMessage("��6���������� ��Ƽ�� �����ϼ̽��ϴ�.");
    									p.closeInventory();
    								}
    								else if (PartyAPI.getJoinType(name).equalsIgnoreCase("��û")) {
    		        					if (PartyAPI.getUserInteger(name) >= 4) {
    		        						p.closeInventory();
    		        						p.sendMessage("��c�� ��Ƽ�� ���� �ִ� �ο� ���� �ѵ��� �ʰ��Ǿ� �����Ͻ� �� �����ϴ�.");
    		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		        						return;
    		        					}
    		        					
    		        					if (GuildAPI.getJoinGuild(p) != null) {
    		    							String guild = GuildAPI.getJoinGuild(p);
    		    							if (GuildAPI.isWar(guild)) {
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
    		        					
    		        					if (API.isPartyVSMatch(name)) {
    		        						e.setCancelled(true);
    		        						p.sendMessage("��c���� �� ��Ƽ�� ��Ī�� �������̿��� ��Ƽ ���� ��û�� �Ͻ� �� �����ϴ�.");
    		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		        						return;
    		        					}

    		        					if (API.isPartyVSPlaying(name)) {
    		        						e.setCancelled(true);
    		        						p.sendMessage("��c���� �� ��Ƽ�� ����� �������̿��� ��Ƽ ���� ��û�� �Ͻ� �� �����ϴ�.");
    		        						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		        						return;
    		        					}
    		        					
    		        					main.PartyJoin.put(Bukkit.getPlayerExact(PartyAPI.getManager(name)), p);
    									p.sendMessage("��6���������� ��c" + name + "��6 ��Ƽ�� ��û�ϼ̽��ϴ�.");
    			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    									p.closeInventory();
    									
    									for (Player pl : Bukkit.getOnlinePlayers()) {
    										if (pl.getName().equalsIgnoreCase(PartyAPI.getManager(name)))
    											PartyGUI.AskJoinParty(pl, name);
    									}
    								}
    								else if (PartyAPI.getJoinType(name).equalsIgnoreCase("�����")) {
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
     	}
 	}
}
