package me.shinkhan.shop;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class Event extends JavaPlugin implements Listener {
	main M;
	
	public Event(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("§6이 서버에는 당신의 플러그인인 §cDHShop " + main.var + "v §6가 들어있습니다!");
					return;
				}
			}, timeToRun);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	
     	if (e.getInventory().getName().contains(" ")) {
     		if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("§l●")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
				String str = e.getInventory().getName().split(" ")[1];
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f뒤로 가기")) {
        					p.chat("/상점");
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
    						GUI.openGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1, str);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
    						GUI.openGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1, str);
    					}
        			} else {
        				if (e.getCurrentItem().getItemMeta().hasLore()) {
    						if (e.getAction() == InventoryAction.PICKUP_HALF) {
    							ItemStack trash = e.getCurrentItem();
    	    					int code = trash.getTypeId();
    	    					byte date = trash.getData().getData();
    	    					ItemMeta meta = trash.getItemMeta();
    	    					List<String> lore = meta.getLore();
    	    					int amount = Integer.parseInt(ChatColor.stripColor(lore.get(0).split(" ")[3]));
    							ItemStack item = new MaterialData(code, date).toItemStack(amount);
    	    					double money = -1;
    	    					int invair = Method.getInventoryAir(p);
    	    					
    	    					if (lore.size() == 2) {
    	    						if (lore.get(1).contains("구매")) {
    	    							money = Integer.parseInt(ChatColor.stripColor(lore.get(1).split(" ")[2]));
    	    						}
    	    					}
    	    					
    	    					else if (lore.size() == 3) {
            						if (lore.get(1).contains("구매")) {
            							money = Integer.parseInt(ChatColor.stripColor(lore.get(1).split(" ")[2]));
            						}
            						
            						else if (lore.get(2).contains("구매")) {
            							money = Integer.parseInt(ChatColor.stripColor(lore.get(2).split(" ")[2]));
            						}
    	    					}
    	    					
    	    					if (money != -1) {
        				            double hasmoney = main.economy.getBalance(p.getName());
        							if (hasmoney < money) {
        								double one = money / amount;
        								if (hasmoney >= one) {
        									amount = (int) (hasmoney / one);
        									money = amount * one;
        								} else {
            								p.sendMessage("§c돈이 부족하여 구매하실 수 없습니다.");
            		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            		    					return;
        								}
        							}
									
        							if (invair < 0 || invair < (amount / item.getMaxStackSize()) + 1) {
        								p.sendMessage("§c인벤토리 공간이 부족하여 구매하실 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		    					return;
        							}
        				            
        				            p.sendMessage("§a당신은 " + item.getType().toString() + " 아이템 " + amount + " 개를 $" + money + "에 샀습니다.");
        				            main.economy.withdrawPlayer(p.getName(), money);
        				            p.getInventory().addItem(new MaterialData(code, date).toItemStack(amount));
    	    					}
    						}
    						
    						else if (e.getAction() == InventoryAction.PICKUP_ALL) {
    							ItemStack trash = e.getCurrentItem();
    	    					int code = trash.getTypeId();
    	    					byte date = trash.getData().getData();
    	    					ItemMeta meta = trash.getItemMeta();
    	    					List<String> lore = meta.getLore();
    	    					int amount = Integer.parseInt(ChatColor.stripColor(lore.get(0).split(" ")[3]));
    							ItemStack item = new MaterialData(code, date).toItemStack(amount);
    	    					double money = -1;
    	    					
    	    					if (lore.size() == 2) {
    	    						if (lore.get(1).contains("판매")) {
    	    							money = Integer.parseInt(ChatColor.stripColor(lore.get(1).split(" ")[2]));
    	    						}
    	    					}
    	    					
    	    					else if (lore.size() == 3) {
    	    						if (lore.get(1).contains("판매")) {
    	    							money = Integer.parseInt(ChatColor.stripColor(lore.get(1).split(" ")[2]));
    	    						}
    	    						
    	    						else if (lore.get(2).contains("판매")) {
    	    							money = Integer.parseInt(ChatColor.stripColor(lore.get(2).split(" ")[2]));
    	    						}
    	    					}
    	    					
    	    					if (money != -1) {
    	    						int invamount = Method.getAmount(item, p.getInventory());
    				            	
    				            	if (invamount <= 0) {
    				            		p.sendMessage("§c판매할 아이템이 부족하여서 판매하실 수 없습니다.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		    					return;
    				            	}
    				            	
    				            	else if (invamount < amount) {
    				            		double num = money / amount;
    				            		money = invamount * num;
    				            		amount = invamount;
    				            	}
    				            	
    				            	p.sendMessage("§a당신은 " + item.getType().toString() + " 아이템 " + amount + " 개를 $" + money + "에 팔았습니다.");
        			    			main.economy.depositPlayer(p.getName(), money);
        				            p.getInventory().removeItem(new MaterialData(code, date).toItemStack(amount));
        				            p.updateInventory();
    	    					}
    						}
        				}
        			}
        		}
         	}
     	}
	}
}
