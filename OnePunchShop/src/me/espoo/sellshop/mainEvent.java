package me.espoo.sellshop;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.option.PlayerYml;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	GUIMessage G;
	
	public mainEvent(main main)
	{
		this.M = main;
	}

	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("�Ǹ� ����")) return;
     	if (e.getCurrentItem() == null) return;
     	
     	ItemStack i = e.getCurrentItem();
		if (i.getTypeId() == 102 || i.getTypeId() == 10 || i.getTypeId() == 386 || i.getTypeId() == 324)
			e.setCancelled(true);
		
		else if (i.hasItemMeta())
			if (i.getItemMeta().hasDisplayName())
				if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6�Ǹ� �ϱ� ��f]"))
					e.setCancelled(true);
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("�Ǹ� ����")) return;
     	if (e.getCurrentItem() == null) return;
     	
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 50);
		timer1.schedule(new TimerTask() {
			public void run() {
				mainGUI.updateGUI(p, p.getOpenInventory());
				return;
			}
		}, timeToRun);
			
		if (e.getCurrentItem().hasItemMeta()) {
			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().hasLore()) {
					if (e.getCurrentItem().getTypeId() == 324 && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7��l�ڷ� ���� ��f]")) {
						p.chat("/����");
						return;
					}
					
					else if (e.getCurrentItem().getTypeId() == 388 && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6�Ǹ� �ϱ� ��f]")) {
						if (e.getView().getItem(24) != null && e.getView().getItem(24).getType() == Material.AIR) {
							p.sendMessage("��c�Ǹ��� �������� ��ĭ�� �־��ֽñ� �ٶ��ϴ�.");
							if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						List<String> list = e.getCurrentItem().getItemMeta().getLore();
						boolean isSell = false;
						String money = null;
						String amount = null;
						String itemname = null;
						
						for (String str : list) {
							if (str.contains("�Ǹ� ���� ����")) {
								isSell = true;
								break;
							}
						}
						
						if (isSell) {
							for (String str : list) {
								if (str.contains("�Ǹ� ������")) {
									String s = ChatColor.stripColor(str);
									s = s.replaceAll("�Ǹ� ������ :: ", "");
									s = s.replaceAll("��", "");
									String[] st = s.split(", ");
									itemname = st[0]; amount = st[1];
								}
								
								if (str.contains("�Ǹ� ����")) {
									String s = ChatColor.stripColor(str);
									s = s.replaceAll(" ", "");
									s = s.replaceAll("�ǸŰ���::", "");
									s = s.replaceAll("��", "");
									money = s;
								}
							}
							
							mainGUI.deleteItemGUI(p, e.getView());
							mainGUI.updateGUI(p, e.getView());
							main.economy.depositPlayer(p.getName(), Integer.parseInt(money));
							if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.SKELETON_HURT, 2.0F, 1.5F);
							p.sendMessage("��6����� ��c" + itemname + " ��6�������� ��c" + amount + " ��6�� �Ⱦ� ��c" + money + " ��6���� ȹ���ϼ̽��ϴ�.");
							return;
						} else {
							p.sendMessage("��c�� �������� �Ǹ��� �� �ִ� �������� �ƴմϴ�.");
							if (PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		InventoryView i = e.getView();
		
		if (i.getTitle().equalsIgnoreCase("�Ǹ� ����")) {
            int t = 0;
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
                ItemStack it = contents[j];
                if (it == null) {
                    ++t;
                }
            }
            
            if (t <= 1) {
    			if (i.getItem(24) != null && i.getItem(24).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(24));
    			}
            } else {
            	if (i.getItem(24) != null && i.getItem(24).getType() != Material.AIR) {
                	p.getInventory().addItem(i.getItem(24));
    			}
            }
		}
	}
}
