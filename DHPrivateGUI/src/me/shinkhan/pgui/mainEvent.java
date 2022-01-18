package me.shinkhan.pgui;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.option.PlayerYml;

@SuppressWarnings("deprecation")
public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		File f = new File("plugins/DHPrivateGUI/" + p.getName() + ".yml");
		File folder = new File("plugins/DHPrivateGUI");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) QuitTime.CreateQuitTime(f, folder, config);
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("§6이 서버에는 당신의 플러그인인 §cDHPrivateGUI §6가 들어있습니다!");
					return;
				}
			}, timeToRun);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		QuitTime.setMakeDate(p, QuitTime.getNowTime(p));
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		Action act = e.getAction();
		Player p = e.getPlayer();
	    
        if (act == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {    
                Sign s = (Sign) b.getState();
                
                if (s.getLine(0).equalsIgnoreCase("[잠금]") || s.getLine(0).equalsIgnoreCase("[private]")) {
                	if (s.getLine(1).equalsIgnoreCase(p.getName())) {
    					if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                		mainGUI.ManagerGUI(p, s);
                		if (main.sign.get(p) != null) main.sign.put(p, s);
                		else {
                			main.sign.remove(p);
                			main.sign.put(p, s);
                		}
                		
                		return;
                	}
                	
                	else if (s.getLine(2).equalsIgnoreCase(p.getName()) || s.getLine(3).equalsIgnoreCase(p.getName())) {
    					if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                		mainGUI.UserGUI(p, s);
                		if (main.sign.get(p) != null) main.sign.put(p, s);
                		else {
                			main.sign.remove(p);
                			main.sign.put(p, s);
                		}
                		
                		return;
                	}
                	
                	else {
                		p.sendMessage("§c당신은 이 표지판 잠금에 대한 권한이 없습니다.");
                		return;
                	}
                }
            }
        }
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayerExact(e.getPlayer().getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("잠금 유저") || e.getInventory().getName().equalsIgnoreCase("잠금 관리자"))
			if (main.sign.get(p) != null && !main.addPlayer.contains(p)) main.sign.remove(p);
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		Sign s = main.sign.get(p);
		
		if (s != null) {
			if (main.addPlayer.contains(p)) {
				String name = e.getMessage();
				String one = s.getLine(1);
				String two = s.getLine(2);
				String three = s.getLine(3);
				
				if (name.equalsIgnoreCase("취소")) {
					e.setCancelled(true);
					p.sendMessage("§6성공적으로 플레이어 추가를 §c취소 §6하셨습니다.");
					main.addPlayer.remove(p);
					if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				if (one.equalsIgnoreCase(name) || two.equalsIgnoreCase(name) || three.equalsIgnoreCase(name)) {
					e.setCancelled(true);
					p.sendMessage("§c이 플레이어는 이미 표지판 잠금 유저중 한명입니다. 다시 입력해 주십시오");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				if (!Method.checkInputOnlyNumberAndAlphabet(name)) {
					e.setCancelled(true);
					p.sendMessage("§c플레이어 닉네임중 잘못된 문자가 있습니다. 다시 입력해 주십시오");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
				p.sendMessage("§6성공적으로 §c" + name + " §6님을 추가하셨습니다.");
				main.addPlayer.remove(p);
				main.sign.remove(p);
				
				if (two.equalsIgnoreCase("")) s.setLine(2, name);
				else if (three.equalsIgnoreCase("")) s.setLine(3, name);
				s.update();
				
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("잠금 유저")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack i = e.getCurrentItem();
    		
    		if (i.hasItemMeta()) {
    			if (i.getItemMeta().hasDisplayName()) {
    				if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
    					p.closeInventory();
    					if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    				}
    				
    				else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§c표지판 탈퇴")) {
    					Sign s = main.sign.get(p);
    					if (s == null) {
    						p.sendMessage("§cERROR: 다시 표지판을 우클릭 해주시기 바랍니다.");
    						p.closeInventory();
    						if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						return;
    					}
    					
    					if (s.getLine(2).equalsIgnoreCase(p.getName())) {
    						s.setLine(2, null);
    						s.update();
    						p.sendMessage("§a성공적으로 표지판 잠금에서 탈퇴하셨습니다.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    						p.closeInventory();
    					}
    					
    					else if (s.getLine(3).equalsIgnoreCase(p.getName())) {
    						s.setLine(3, null);
    						s.update();
    						p.sendMessage("§a성공적으로 표지판 잠금에서 탈퇴하셨습니다.");
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    						p.closeInventory();
    					}
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("잠금 관리자")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack i = e.getCurrentItem();
    		
    		if (i.hasItemMeta()) {
    			if (i.getItemMeta().hasDisplayName()) {
    				if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
    					p.closeInventory();
    					if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    				}
    				
    				else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§b플레이어 추가")) {
    					if (!main.addPlayer.contains(p)) {
    						main.addPlayer.add(p);
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					p.closeInventory();
        					p.sendMessage("§6누구를 §c추가§6하시겠습니까? 채팅창에 적어주시기 바랍니다.");
        					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
    					}
    				}
    				
    				else {
    					if (i.getTypeId() == 397) {
    						if (Method.checkInputOnlyNumberAndAlphabet(ChatColor.stripColor(i.getItemMeta().getDisplayName()))) {
    							if (i.getData().getData() == 0 || i.getData().getData() == 3) {
    								if (e.getAction() == InventoryAction.PICKUP_HALF) {
    									Sign s = main.sign.get(p);
    									if (s != null) {
    										String name = ChatColor.stripColor(i.getItemMeta().getDisplayName());
    										
    										if (!name.equalsIgnoreCase(p.getName())) {
    											if (s.getLine(2).equalsIgnoreCase(name)) {
    												s.setLine(2, s.getLine(3));
    												s.setLine(3, null);
    												s.update();
    												p.sendMessage("§6성공적으로 §c" + name + " §6님을 강제 퇴장시켰습니다.");
    					    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					    						p.closeInventory();
    											}
    											
    											else if (s.getLine(3).equalsIgnoreCase(name)) {
    												s.setLine(3, null);
    												s.update();
    												p.sendMessage("§6성공적으로 §c" + name + " §6님을 강제 퇴장시켰습니다.");
    					    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					    						p.closeInventory();
    											}
    										}
    									}
    								}
    								
    								else if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    									Sign s = main.sign.get(p);
    									if (s != null) {
    										String name = ChatColor.stripColor(i.getItemMeta().getDisplayName());
    										
    										if (!name.equalsIgnoreCase(p.getName())) {
    											if (s.getLine(2).equalsIgnoreCase(name)) {
    												String one = s.getLine(1);
    												String two = s.getLine(2);
    												s.setLine(1, two);
    												s.setLine(2, one);
    												s.update();
    												p.sendMessage("§6성공적으로 §c" + name + " §6님에게 표지판 관리 권한을 §c양도§6하였습니다.");
    					    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					    						p.closeInventory();
    											}
    											
    											else if (s.getLine(3).equalsIgnoreCase(name)) {
    												String one = s.getLine(1);
    												String two = s.getLine(3);
    												s.setLine(1, two);
    												s.setLine(3, one);
    												s.update();
    												p.sendMessage("§6성공적으로 §c" + name + " §6님에게 표지판 관리 권한을 §c양도§6하였습니다.");
    					    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					    						p.closeInventory();
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
     	}
	}
}
