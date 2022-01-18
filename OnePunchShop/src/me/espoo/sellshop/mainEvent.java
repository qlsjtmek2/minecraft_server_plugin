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
     	if (!e.getInventory().getName().equalsIgnoreCase("판매 상점")) return;
     	if (e.getCurrentItem() == null) return;
     	
     	ItemStack i = e.getCurrentItem();
		if (i.getTypeId() == 102 || i.getTypeId() == 10 || i.getTypeId() == 386 || i.getTypeId() == 324)
			e.setCancelled(true);
		
		else if (i.hasItemMeta())
			if (i.getItemMeta().hasDisplayName())
				if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6판매 하기 §f]"))
					e.setCancelled(true);
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("판매 상점")) return;
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
					if (e.getCurrentItem().getTypeId() == 324 && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7§l뒤로 가기 §f]")) {
						p.chat("/상점");
						return;
					}
					
					else if (e.getCurrentItem().getTypeId() == 388 && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6판매 하기 §f]")) {
						if (e.getView().getItem(24) != null && e.getView().getItem(24).getType() == Material.AIR) {
							p.sendMessage("§c판매할 아이템을 빈칸에 넣어주시기 바랍니다.");
							if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						List<String> list = e.getCurrentItem().getItemMeta().getLore();
						boolean isSell = false;
						String money = null;
						String amount = null;
						String itemname = null;
						
						for (String str : list) {
							if (str.contains("판매 가능 상태")) {
								isSell = true;
								break;
							}
						}
						
						if (isSell) {
							for (String str : list) {
								if (str.contains("판매 아이템")) {
									String s = ChatColor.stripColor(str);
									s = s.replaceAll("판매 아이템 :: ", "");
									s = s.replaceAll("개", "");
									String[] st = s.split(", ");
									itemname = st[0]; amount = st[1];
								}
								
								if (str.contains("판매 가격")) {
									String s = ChatColor.stripColor(str);
									s = s.replaceAll(" ", "");
									s = s.replaceAll("판매가격::", "");
									s = s.replaceAll("원", "");
									money = s;
								}
							}
							
							mainGUI.deleteItemGUI(p, e.getView());
							mainGUI.updateGUI(p, e.getView());
							main.economy.depositPlayer(p.getName(), Integer.parseInt(money));
							if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SKELETON_HURT, 2.0F, 1.5F);
							p.sendMessage("§6당신은 §c" + itemname + " §6아이템을 §c" + amount + " §6개 팔아 §c" + money + " §6원을 획득하셨습니다.");
							return;
						} else {
							p.sendMessage("§c이 아이템은 판매할 수 있는 아이템이 아닙니다.");
							if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
		
		if (i.getTitle().equalsIgnoreCase("판매 상점")) {
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
