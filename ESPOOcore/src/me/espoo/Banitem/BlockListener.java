/*    */ package me.espoo.Banitem;
/*    */ 
/*    */ import me.espoo.main.main;

import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockBreakEvent;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class BlockListener
/*    */   implements Listener
/*    */ {
/*    */   public static main plugin;
/*    */ 
/*    */   public BlockListener(main instance)
/*    */   {
/* 17 */     plugin = instance;
/*    */   }
/* 21 */   @EventHandler
/*    */   public void onBlockBreak(BlockBreakEvent e) { Player player = e.getPlayer();
/* 22 */     String world = player.getWorld().getName().toLowerCase();
/* 23 */     Block item = e.getBlock();
/* 24 */     int id = item.getType().getId();
/* 25 */     byte data = item.getData();
/* 26 */     if ((!player.hasPermission("banitem.bypass." + id + ":" + data)) && (!player.hasPermission("banitem.break." + id + ":*")) && 
/* 27 */       (!player.hasPermission("banitem.break." + id + ":" + data)) && (!player.hasPermission("banitem.bypass." + id + ":*")) && 
/* 28 */       (!player.isOp()) && (!player.hasPermission("banitem.*")))
/*    */     {
/* 30 */       itemcheck itemmethod = new itemcheck(plugin.all, id, data, world);
/* 31 */       itemcheck itemmethod1 = new itemcheck(plugin.br, id, data, world);
/* 32 */       if (((itemmethod.getnumber() == 1) || (itemmethod1.getnumber() == 1)) && (
/* 33 */         (itemmethod.worldcheck() == 1) || (itemmethod1.worldcheck() == 1))) {
/* 34 */         e.setCancelled(true);
/* 35 */   	   		   player.sendMessage("§c당신은 이 아이템을 사용하실 수 없습니다.");
/*    */       }
/*    */     } }
/*    */ 
/*    */   @EventHandler
/*    */   private void onPlayerPlacement(BlockPlaceEvent e)
/*    */   {
/* 47 */     Player player = e.getPlayer();
/* 48 */     ItemStack item = player.getItemInHand();
/* 49 */     String world = player.getWorld().getName();
/* 50 */     int id = item.getType().getId();
/* 51 */     byte data = item.getData().getData();
/* 52 */     if ((!player.hasPermission("banitem.bypass." + id + ":" + data)) && (!player.hasPermission("banitem.place." + id + ":*")) && 
/* 53 */       (!player.hasPermission("banitem.place." + id + ":" + data)) && (!player.hasPermission("banitem.bypass." + id + ":*")) && 
/* 54 */       (!player.isOp()) && (!player.hasPermission("banitem.*")))
/*    */     {
/* 56 */       itemcheck itemmethod = new itemcheck(plugin.all, id, data, world);
/* 57 */       itemcheck itemmethod1 = new itemcheck(plugin.place, id, data, world);
/* 58 */       if (((itemmethod.getnumber() == 1) || (itemmethod1.getnumber() == 1)) && (
/* 59 */         (itemmethod.worldcheck() == 1) || (itemmethod1.worldcheck() == 1))) { e.setCancelled(true);
/* 35 */   	   		   player.sendMessage("§c당신은 이 아이템을 사용하실 수 없습니다.");
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\[�븘�씠�뀥諛⑹�]banitem.jar
 * Qualified Name:     com.abcalvin.BanItem.BlockListener
 * JD-Core Version:    0.6.0
 */