package me.espoo.rpg.exp;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.PlayerYml;
import me.espoo.rpg.main;

public class ExpEvent extends JavaPlugin implements Listener {
	main M;
	
	public ExpEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/OnePunchRpg");
		File folder2 = new File("plugins/OnePunchRpg/Player");
		File f = new File("plugins/OnePunchRpg/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		Action act = e.getAction();
		Player p = e.getPlayer();
	    
	    if (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK)
	    {
	        if (e.getItem() != null && e.getItem().getItemMeta().getDisplayName() != null && 
	        	e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6����ġ ���ʽ� ������") && 
	        	e.getItem().getItemMeta().hasLore()) {
	        	
	        	ExpGUI.openGUI(p);
	    	}
	    }
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("����ġ ���ʽ� ����â")) return;
     	if (e.getCurrentItem() == null) return;
     	e.setCancelled(true);
     	
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getCurrentItem().hasItemMeta()) {
			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6â �ݱ�")) {
					p.sendMessage("��c����ġ ���ʽ� ������ ����� ����ϼ̽��ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					p.closeInventory();
				}
				
				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6����ġ ���ʽ� ������ ��c���")) {
					ItemStack item = p.getItemInHand();
					List<String> lore = item.getItemMeta().getLore();
					
					for (String str : lore) {
						if (str.contains("��6����ġ ���ʽ�") && str.contains("����")) {
							str = ChatColor.stripColor(str.replaceAll(" ", ""));
							String type = str.replaceAll("����ġ���ʽ�", "").split("��")[0];
							int bouns = Integer.parseInt(str.replaceAll("����ġ���ʽ�" + type + "��", "").split("%")[0]);
							int time = 0;
							
							if (str.contains("����")) {
								time = -1;
								p.sendMessage("��6���������� ��c" + bouns + "% ��6�� ���ʽ��� ��c������6������ ȹ���ϼ̽��ϴ�.");
							}
							
							else if (str.contains("H")) {
								time = Integer.parseInt(str.replaceAll("H����", "").split("��ŭ")[1]) * 60;
								p.sendMessage("��6���������� ��c" + bouns + "% ��6�� ���ʽ��� ��c" + Integer.parseInt(str.replaceAll("H����", "").split("��ŭ")[1]) + " ��6�ð� ��ŭ ȹ���ϼ̽��ϴ�.");
							}
							
							else if (str.contains("M")) {
								time = Integer.parseInt(str.replaceAll("M����", "").split("��ŭ")[1]);
								p.sendMessage("��6���������� ��c" + bouns + "% ��6�� ���ʽ��� ��c" + Integer.parseInt(str.replaceAll("M����", "").split("��ŭ")[1]) + " ��6�� ��ŭ ȹ���ϼ̽��ϴ�.");
							}
							
							ExpAPI.setBounsAll(type, p.getName(), bouns, time);
							p.closeInventory();
							p.setItemInHand(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 1.5F);
						}
					}
				}
			}
		}
	}
}
