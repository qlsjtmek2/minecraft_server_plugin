package me.shinkhan.epm;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import uk.org.whoami.authme.events.LoginEvent;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/DHEchoPetManager/Player/" + p.getName() + ".yml");
		if (!f.exists()) {
			File folder = new File("plugins/DHEchoPetManager");
			File folder2 = new File("plugins/DHEchoPetManager/Player");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			PlayerYml.CreateInfo(f, folder, folder2, config);
		}
	}
	
	@EventHandler
	public void onLogin(LoginEvent e) {
		Player p = e.getPlayer();
		if (API.isShowPet(p)) {
			PlayerTimer timer = new PlayerTimer(p, new Runnable() {
				@Override
				public void run() {
					API.appCommandPet(p, API.getSetPet(p));
				}
			});
			
			timer.setTime(1);
			timer.Start();
		}
	}
	
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		
		if (API.isShowPet(p) && !p.getWorld().getName().contains("world_pvp")) {
			PlayerTimer timer = new PlayerTimer(p, new Runnable() {
				@Override
				public void run() {
					API.appCommandPet(p, API.getSetPet(p));
				}
			});
			
			timer.setTime(1);
			timer.Start();
		}
	}

	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("�� ���")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		
    		if (e.getCurrentItem().hasItemMeta()) {
    			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    				if (e.getCurrentItem().getItemMeta().hasLore()) {
    					if (e.getCurrentItem().getItemMeta().getLore().get(0).equalsIgnoreCase("��7������ Ŭ���� �� ������ �����մϴ�.")) {
    						if (!API.isShowPet(p)) {
    							p.sendMessage("��c���� ���� ��Ȱ��ȭ �����Դϴ�. ��e[ /�� ��� -> �� ���� ������ Ŭ�� ] ��c�ϼž߸� �� ��ȯ�� �����մϴ�.");
    							return;
    						}
    						
    						String pet = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(1));
    						p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    						p.closeInventory();
    						API.appCommandPet(p, pet);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
    						p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 1.8F, 1.5F);
    						p.closeInventory();
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��e�� ����")) {
    						if (API.isShowPet(p)) {
    							API.setShowPet(p, false);
    							API.removeCommandPet(p);
    						} else {
    							API.setShowPet(p, true);
    							API.appCommandPet(p, API.getSetPet(p));
    						}
    						
    						GUI.openGUI(p);
    						p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    				}
    			}
    		}
     	}
	}
}
