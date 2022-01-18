/*     */ package me.espoo.Banitem;
/*     */ 
/*     */ import me.espoo.main.main;

import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class PlayerListener extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*     */   public static main plugin;
/*     */ 
/*     */   public PlayerListener(main instance)
/*     */   {
/*  20 */     plugin = instance;
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onInteractEvent(PlayerInteractEvent e) {
/*  26 */     Player player = e.getPlayer();
/*  27 */     ItemStack item = player.getItemInHand();
/*  28 */     String world = player.getWorld().getName();
/*  29 */     int id = item.getType().getId();
/*  30 */     byte data = item.getData().getData();
/*  31 */     if ((!player.hasPermission("banitem.bypass." + id + ":" + data)) && (!player.hasPermission("banitem.int." + id + ":*")) && 
/*  32 */       (!player.hasPermission("banitem.int." + id + ":" + data)) && (!player.hasPermission("banitem.bypass." + id + ":*")) && 
/*  33 */       (!player.isOp()) && (!player.hasPermission("banitem.*")))
/*     */     {
/*  35 */       itemcheck itemmethod = new itemcheck(plugin.all, id, data, world);
/*  36 */       itemcheck itemmethod1 = new itemcheck(plugin.interact, id, data, world);
/*  37 */       if (!(e instanceof Player))
/*     */       {
/*  39 */         if ((itemmethod.getnumber() == 1) || (itemmethod1.getnumber() == 1))
/*  40 */           if ((itemmethod1.worldcheck() == 1) || (itemmethod.worldcheck() == 1)) {
/*  41 */             if (plugin.getConfig().getBoolean("Confiscate")) {
/*  42 */               e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
/*  43 */               e.setCancelled(true);
/* 35 */   	   		   player.sendMessage("§c당신은 이 아이템을 사용하실 수 없습니다.");
/*     */             }
/*     */             else {
/*  51 */               int itemslot = e.getPlayer().getInventory().getHeldItemSlot();
/*  52 */               if (itemslot == 8)
/*  53 */                 e.getPlayer().getInventory().setHeldItemSlot(itemslot - 1);
/*     */               else {
/*  55 */                 e.getPlayer().getInventory().setHeldItemSlot(itemslot + 1);
/*     */               }
/*  57 */               e.setCancelled(true);
/*     */ 
/* 35 */   	   		   player.sendMessage("§c당신은 이 아이템을 사용하실 수 없습니다.");
/*     */             }
/*     */           }
/*  67 */           else if (plugin.getConfig().getBoolean("Confiscate")) {
/*  68 */             e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
/*  69 */             e.setCancelled(true);
/* 35 */   	   		   player.sendMessage("§c당신은 이 아이템을 사용하실 수 없습니다.");
/*     */           }
/*     */           else {
/*  77 */             int itemslot = e.getPlayer().getInventory().getHeldItemSlot();
/*  78 */             if (itemslot == 8)
/*  79 */               e.getPlayer().getInventory().setHeldItemSlot(itemslot - 1);
/*     */             else {
/*  81 */               e.getPlayer().getInventory().setHeldItemSlot(itemslot + 1);
/*     */             }
/*  83 */             e.setCancelled(true);
/*     */ 
/* 35 */   	   		   player.sendMessage("§c당신은 이 아이템을 사용하실 수 없습니다.");
/*     */           }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   private void onClick(InventoryClickEvent e)
/*     */   {
/*  99 */     Player player = (Player)e.getWhoClicked();
/* 100 */     ItemStack item = e.getCurrentItem();
/* 101 */     String world = player.getWorld().getName();
/* 102 */     if (item == null) {
/* 103 */       return;
/*     */     }
/* 105 */     int id = item.getType().getId();
/* 106 */     byte data = item.getData().getData();
/* 107 */     if ((!player.hasPermission("banitem.bypass." + id + ":" + data)) && (!player.hasPermission("banitem.click." + id + ":*")) && 
/* 108 */       (!player.hasPermission("banitem.click." + id + ":" + data)) && (!player.hasPermission("banitem.bypass." + id + ":*")) && 
/* 109 */       (!player.isOp()) && (!player.hasPermission("banitem.*")))
/*     */     {
/* 111 */       itemcheck itemmethod = new itemcheck(plugin.all, id, data, world);
/* 112 */       itemcheck itemmethod1 = new itemcheck(plugin.click, id, data, world);
/* 113 */       if (((itemmethod.getnumber() == 1) || (itemmethod1.getnumber() == 1)) && (
/* 114 */         (itemmethod1.worldcheck() == 1) || (itemmethod.worldcheck() == 1)))
/* 115 */         if (plugin.getConfig().getBoolean("Confiscate")) {
/* 116 */           e.setCurrentItem(new ItemStack(Material.AIR, 1));
/* 117 */           e.setCancelled(true);
/*     */         }
/*     */         else {
/* 125 */           e.setCancelled(true);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   private void onPickup(PlayerPickupItemEvent e)
/*     */   {
/* 140 */     Player player = e.getPlayer();
/* 141 */     ItemStack item = e.getItem().getItemStack();
/* 142 */     int id = item.getType().getId();
/* 143 */     byte data = item.getData().getData();
/* 144 */     String world = player.getWorld().getName();
/* 145 */     if ((!player.hasPermission("banitem.bypass." + id + ":" + data)) && (!player.hasPermission("banitem.pickup." + id + ":*")) && 
/* 146 */       (!player.hasPermission("banitem.pickup." + id + ":" + data)) && (!player.hasPermission("banitem.bypass." + id + ":*")) && 
/* 147 */       (!player.isOp()) && (!player.hasPermission("banitem.*")))
/*     */     {
/* 149 */       itemcheck itemmethod = new itemcheck(plugin.all, id, data, world);
/* 150 */       itemcheck itemmethod1 = new itemcheck(plugin.pickup, id, data, world);
/* 151 */       if (((itemmethod.getnumber() == 1) || (itemmethod1.getnumber() == 1)) && (
/* 152 */         (itemmethod1.worldcheck() == 1) || (itemmethod.worldcheck() == 1))) {
/* 153 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\[�븘�씠�뀥諛⑹�]banitem.jar
 * Qualified Name:     com.abcalvin.BanItem.PlayerListener
 * JD-Core Version:    0.6.0
 */