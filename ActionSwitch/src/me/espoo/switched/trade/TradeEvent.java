package me.espoo.switched.trade;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.switched.main;

public class TradeEvent extends JavaPlugin implements Listener {
	TradeAPI T;
	main M;
	
	public TradeEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
    public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if (p.getOpenInventory() != null && p.getOpenInventory().getTitle().contains("교환기")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		InventoryView i = e.getView();
		
		if (i.getTitle().contains("조합 추가")) {
            int t = 0;
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
                ItemStack it = contents[j];
                if (it == null) {
                    ++t;
                }
            }
            
            if (t <= 2) {
    			if (i.getItem(10) != null && i.getItem(10).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(10));
    			}
    			if (i.getItem(11) != null && i.getItem(11).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(11));
    			}
    			if (i.getItem(16) != null && i.getItem(16).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(16));
    			}
            } else {
            	if (i.getItem(10) != null && i.getItem(10).getType() != Material.AIR) {
                	p.getInventory().addItem(i.getItem(10));
    			}
            	if (i.getItem(11) != null && i.getItem(11).getType() != Material.AIR) {
                	p.getInventory().addItem(i.getItem(11));
    			}
            	if (i.getItem(16) != null && i.getItem(16).getType() != Material.AIR) {
                	p.getInventory().addItem(i.getItem(16));
    			}
            } return;
		}
		
		if (i.getTitle().contains("교환기") && !i.getTitle().equalsIgnoreCase("양털 교환기")) {
            int t = 0;
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
                ItemStack it = contents[j];
                if (it == null) {
                    ++t;
                }
            }
            
            if (t <= 2) {
    			if (i.getItem(29) != null && i.getItem(29).getType() != Material.AIR && i.getItem(29).getTypeId() != 119) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(29));
    			}
    			if (i.getItem(30) != null && i.getItem(30).getType() != Material.AIR && i.getItem(30).getTypeId() != 119) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(30));
    			}
            } else {
            	if (i.getItem(29) != null && i.getItem(29).getType() != Material.AIR && i.getItem(29).getTypeId() != 119) {
                	p.getInventory().addItem(i.getItem(29));
    			}
            	if (i.getItem(30) != null && i.getItem(30).getType() != Material.AIR && i.getItem(30).getTypeId() != 119) {
                	p.getInventory().addItem(i.getItem(30));
    			}
            } return;
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
    	if (e.getInventory().getName().contains("교환기") && !e.getInventory().getName().equalsIgnoreCase("양털 교환기")) {
    		if (e.getCurrentItem() == null) return;
    		InventoryAction action = e.getAction();
    		ItemStack current = e.getCurrentItem();
    		Player p = (Player) e.getWhoClicked();
    		String name = e.getInventory().getName().split("§2§0")[0];
    		int check = e.getRawSlot();
    		if (!e.getInventory().getName().contains("§3§0") || !e.getInventory().getName().split("§3§0")[1].contains("/")) {
    			p.closeInventory();
    			p.sendMessage("§cERROR CODE 1: 관리자에게 문의해 주세요. \"/문의\"");
    			return;
    		}
    		
    		int amount = Integer.parseInt((e.getInventory().getName().split("§3§0")[1]).split("/")[0]);
    		if (check < 45 || current.getTypeId() == 119) {
    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    			if (current.getTypeId() == 119 || check != 29 && check != 30 && check != 33 && check != 36 && check != 44) {
    				e.setCancelled(true);
        			return;
    			}
    		}
    		
    		if (!p.isOp() && e.getClick() != ClickType.LEFT && e.getClick() != ClickType.SHIFT_LEFT) {
				e.setCancelled(true);
    			return;
			}
    		
    		if (check > 44 && e.getClick() == ClickType.SHIFT_LEFT) {
    			e.setCancelled(true);
    			return;
    		}
    		
    		if (check == 44 && current.getTypeId() == 101) {
    			e.setCancelled(true);
    			return;
    		}
    		
    		if (current.hasItemMeta() && current.getItemMeta().hasDisplayName()) {
        		if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        			if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§f이곳에서 조합 결과가 나옵니다.")) {
        				e.setCancelled(true);
    					return;
        			}
        			
        			else if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§f뒤로 가기")) {
        				e.setCancelled(true);
        				if (e.getInventory().getName().contains("홍-보")) p.chat("/포탈");
        				else if (e.getInventory().getName().contains("후-원")) p.chat("/후원");
        				else p.chat("/교환기");
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    					return;
    				}
    				
    				else if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
        				e.setCancelled(true);
    					TradeGUI.openGUI(p, name, amount + 1);
    					return;
    				}
    				
    				else if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
        				e.setCancelled(true);
    					TradeGUI.openGUI(p, name, amount - 1);
    					return;
    				}
    			}
    		}

    		Timer timer = new Timer();
    		Date timeToRun = new Date(System.currentTimeMillis() + 1);
    		timer.schedule(new TimerTask() {
    			@SuppressWarnings("deprecation")
				public void run() {
    	    		Inventory GUI = e.getInventory();
    	    		ItemStack item1 = GUI.getItem(29);
    	    		ItemStack item2 = GUI.getItem(30);
    	    		
    	    		if (check == 33) {
    	    			if (item1 != null && item1.getTypeId() != 119 || item2 != null && item2.getTypeId() != 119) {
    	    				int i = 0;
    	    				if (action == InventoryAction.PICKUP_ALL) {
    	    					if (item1 != null && item1.getTypeId() != 119) {
	    							i = item1.getAmount() - T.getOne(name, amount).getAmount();
	    							item1.setAmount(i);
	    							if (i <= 0) GUI.setItem(29, null);
    	    					}
    	    					
	    						if (item2 != null && item2.getTypeId() != 119) {
	    							i = item2.getAmount() - T.getTwo(name, amount).getAmount();
	    							item2.setAmount(i);
	    							if (i <= 0) GUI.setItem(30, null);
	    						}
    	    				}
    	    				
    	    				else if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
    							if (item1 != null && item1.getTypeId() != 119 && item2 != null && item2.getTypeId() != 119) {
    								int i1 = (int)(item1.getAmount() / T.getOne(name, amount).getAmount());
    	    						int i2 = (int)(item2.getAmount() / T.getTwo(name, amount).getAmount());
    	    						i = T.CompareReturnSmaller(i1, i2);
    							} else if (item1 != null && item1.getTypeId() != 119) {
    								i = (int)(item1.getAmount() / T.getOne(name, amount).getAmount());
    							} else if (item2 != null && item2.getTypeId() != 119) {
    								i = (int)(item2.getAmount() / T.getTwo(name, amount).getAmount());
    							} else {
    								if (T.equals(item1, T.getOne(name, amount)) && T.equals(item2, T.getTwo(name, amount)) && T.CheckAmount(item1, T.getOne(name, amount)) && T.CheckAmount(item2, T.getTwo(name, amount))) {
    				    				TradeGUI.Stack3(T.getResult(name, amount), 33, GUI);
    				    				return;
    				    			} T.stack(GUI);
    				    			p.updateInventory();
    				    			return;
    							}

    							ItemStack Result = T.getResult(name, amount);
    							int ResultAmount = Result.getAmount();
	    						int TotalResultAmount = ResultAmount * i;
	    			            if (T.getInventoryEmptyAmount(p) < TotalResultAmount / Result.getMaxStackSize()) {
	    	        				e.setCancelled(true);
	    	        				p.sendMessage("§c인벤토리 공간이 부족합니다.");
	    	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.2F);
	    			            }
	    			            
	    			            if (item1 != null && item1.getTypeId() != 119) {
    	    			            int a = item1.getAmount() - (T.getOne(name, amount).getAmount() * i);
	    							item1.setAmount(a);
	    							if (a <= 0) GUI.setItem(29, null);
    							} if (item2 != null && item2.getTypeId() != 119) {
    	    			            int a = item2.getAmount() - (T.getTwo(name, amount).getAmount() * i);
	    							item2.setAmount(a);
	    							if (a <= 0) GUI.setItem(30, null);
    							}
    							
    							Result.setAmount(TotalResultAmount - ResultAmount);
    							if (Result.getAmount() != 0)
    								p.getInventory().addItem(Result);
    						}
	    				}
    	    		}
    	    		
					if (T.equals(item1, T.getOne(name, amount)) && T.equals(item2, T.getTwo(name, amount)) && T.CheckAmount(item1, T.getOne(name, amount)) && T.CheckAmount(item2, T.getTwo(name, amount))) {
	    				TradeGUI.Stack3(T.getResult(name, amount), 33, GUI);
	    				return;
	    			} T.stack(GUI);
	    			p.updateInventory();
	    			return;
    			}
    		}, timeToRun);
    	}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onClickItem(InventoryDragEvent e) {
    	if (e.getInventory().getName().contains("교환기") && !e.getInventory().getName().equalsIgnoreCase("양털 교환기")) {
    		Player p = (Player) e.getWhoClicked();
    		String name = e.getInventory().getName().split("§2§0")[0];
    		int amount = Integer.parseInt((e.getInventory().getName().split("§3§0")[1]).split("/")[0]);
    		
    		Timer timer = new Timer();
    		Date timeToRun = new Date(System.currentTimeMillis() + 6);
    		timer.schedule(new TimerTask() {
    			public void run() {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    	    		Inventory GUI = e.getInventory();
    	    		ItemStack item1 = GUI.getItem(29);
    	    		ItemStack item2 = GUI.getItem(30);
					if (T.equals(item1, T.getOne(name, amount)) && T.equals(item2, T.getTwo(name, amount)) && T.CheckAmount(item1, T.getOne(name, amount)) && T.CheckAmount(item2, T.getTwo(name, amount))) {
	    				TradeGUI.Stack3(T.getResult(name, amount), 33, GUI);
	    				return;
	    			} T.stack(GUI);
    				return;
    			}
    		}, timeToRun);
    		return;
    	}
	}

	@EventHandler
    public void onClickOPItem(InventoryClickEvent e) {
    	if (e.getInventory().getName().contains("조합 추가")) {
    		if (e.getCurrentItem() == null) return;
    		ItemStack current = e.getCurrentItem();
    		Player p = (Player) e.getWhoClicked();
    		
    		if (current.getTypeId() == 119) {
    			e.setCancelled(true);
    			return;
    		}
    		
    		if (current.hasItemMeta() && current.getItemMeta().hasDisplayName() && current.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6조합 추가 하기 §f]")) {
    			e.setCancelled(true);
    			Inventory GUI = e.getInventory();
    			ItemStack item1 = GUI.getItem(10);
    			ItemStack item2 = GUI.getItem(11);
    			ItemStack result = GUI.getItem(16);
    			
    			if (item1 == null && item2 == null || result == null) {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    				p.sendMessage("§c왼쪽이나 오른쪽 칸에는 아이템이 한개 씩 들어있어야 합니다.");
    				return;
    			}
    			
    			TradeAPI.addRecipe(e.getInventory().getName().split("§2§0")[0], item1, item2, result);
    			p.sendMessage("§a성공적으로 조합응 추가했습니다.");
    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    			return;
    		}
    	}
	}
}
