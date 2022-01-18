// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;

import kr.tpsw.rsprefix.api.InvAPI;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import kr.tpsw.rsprefix.api.PrefixPlayer;
import kr.tpsw.rsprefix.api.FileAPI;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import kr.tpsw.rsprefix.api.RanPreAPI;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class EventListener implements Listener
{
    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
        	if (event.hasItem() && event.getItem().isSimilar(RanPreAPI.ranpre)) {
                final ItemStack is = event.getItem();
                if (is.getAmount() == 1) {
                    event.getPlayer().setItemInHand(new ItemStack(0));
                }
                else {
                    is.setAmount(is.getAmount() - 1);
                    event.getPlayer().setItemInHand(is);
                }
                RanPreAPI.runRandomPrefix(event.getPlayer());
                return;
        	}
        	
        	if (event.hasItem()) {
        		ItemStack item = event.getItem();
        		if (item.hasItemMeta()) {
        			if (item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
        				String title = (String) item.getItemMeta().getLore().get(0);
        				if (item.getType() == Material.ENCHANTED_BOOK && item.getDurability() == 147) {
        					boolean isLoaded = false;
        					Player p = event.getPlayer();
        					if (!FileAPI.isLoadedPlayer(p.getName())) {
        						FileAPI.loadPlayer(p.getName());
        						isLoaded = true;
        					}
        					if (item.getAmount() == 1) {
        	                    event.getPlayer().setItemInHand(new ItemStack(0));
        	                }
        	                else {
        	                	item.setAmount(item.getAmount() - 1);
        	                    event.getPlayer().setItemInHand(item);
        	                }

        					PrefixPlayer pp = FileAPI.getPrefixPlayer(p.getName());
        					List<String> list = pp.getList();
        					if (!list.contains(title)) {
        						list.add(title);
        					}
        					pp.needUpdateInv = true;

        					if (isLoaded) {
        				        FileAPI.savePlayer(p.getName());
        					}
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.5F);
        					p.sendMessage("§6성공적으로 §f<" + title + "§f> §6칭호를 받았습니다.");
        				}
        			}
        		}
        	}
        }
    }
    
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final PrefixPlayer pp = FileAPI.getPrefixPlayer(event.getPlayer().getName());
        if (me.shinkhan.userguide.API.isUserGuide(event.getPlayer().getName()))
        	event.setFormat(String.valueOf("§f[§2유저§a가이드§f]") + " §r" + event.getFormat());
        else if (!pp.getMainPrefix().equalsIgnoreCase(""))
        	event.setFormat(String.valueOf(pp.getMainPrefix()) + " §r" + event.getFormat());
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final String name = event.getPlayer().getName();
        if (!FileAPI.isLoadedPlayer(name)) {
            FileAPI.loadPlayer(name);
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final String name = event.getPlayer().getName();
        PrefixPlayer pp = FileAPI.getPrefixPlayer(name);
        if (pp.getList().isEmpty()) pp.setMainPrefix(-1);
        FileAPI.savePlayer(name);
    }
    
    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        if (event.getSlot() == -999) {
            return;
        }
        if (event.getInventory().getName().startsWith(InvAPI.horusCode)) {
            event.setCancelled(true);
            final Material m = event.getCurrentItem().getType();
            if (m == Material.WORKBENCH || m == Material.ANVIL) {
                final ItemStack is = event.getCurrentItem();
                if (is.hasItemMeta()) {
                    final ItemMeta im = is.getItemMeta();
                    if (im.hasDisplayName()) {
                        final String name = im.getDisplayName();
                        if (name.equals("§e\uc774\uc804 \ubaa9\ub85d") || name.equals("§e\ub2e4\uc74c \ubaa9\ub85d")) {
                            final String inv = event.getInventory().getName();
                            final int i1 = inv.indexOf("§b:");
                            final String target = inv.substring(4, i1 - 1);
                            int index = Integer.valueOf(inv.substring(i1 + 3, inv.length()));
                            if (name.equals("§e\uc774\uc804 \ubaa9\ub85d")) {
                                --index;
                            }
                            else {
                                ++index;
                            }
                            InvAPI.viewInv(target, (Player)event.getWhoClicked(), index);
                        }
                    }
                }
            }
        }
    }
}
