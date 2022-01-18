/*    */ package me.espoo.Banitem;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.logging.Logger;

import me.espoo.oneman.main;

/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.world.ChunkLoadEvent;
/*    */ 
/*    */ public class WorldScanner
/*    */   implements Listener
/*    */ {
/*    */   public static main plugin;
/*    */ 
/*    */   public WorldScanner(main instance)
/*    */   {
/* 18 */     plugin = instance;
/*    */   }
/* 22 */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onWorldLoad(ChunkLoadEvent e) { if ((plugin.getConfig().getStringList("Blacklist World").size() > 0) && 
/* 23 */       (!e.isNewChunk())) {
/* 24 */       Chunk c = e.getChunk();
/* 25 */       World world = e.getWorld();
/* 26 */       int bx = c.getX() << 4;
/* 27 */       int bz = c.getZ() << 4;
/* 28 */       for (int xx = bx; xx < bx + 16; xx++)
/* 29 */         for (int zz = bz; zz < bz + 16; zz++)
/* 30 */           for (int yy = 0; yy < 128; yy++) {
/* 31 */             int id = world.getBlockTypeIdAt(xx, yy, zz);
/* 32 */             byte data = world.getBlockAt(xx, yy, zz).getData();
/* 33 */             if ((plugin.world.contains(id + ":" + data + ":" + world)) || (plugin.world.contains(id + ":-1:" + world))) {
/* 34 */               plugin.log.info("A Banned Item [" + id + ":" + data + "] is Detected at x:" + xx + ", y:" + yy + ", z:" + zz + ", World:" + world.getName() + "Removed");
/* 35 */               Player[] players = Bukkit.getOnlinePlayers();
/* 36 */               for (Player op : players) {
/* 37 */                 if ((op.isOp()) || (op.hasPermission("banitem.*"))) {
/* 38 */                   op.sendMessage("A Banned Item [" + id + ":" + data + "] is Detected at x: " + xx + ", y: " + yy + ", z: " + zz + ", World: " + world.getName());
/*    */                 }
/*    */               }
/*    */ 
/* 42 */               if (plugin.getConfig().getBoolean("WorldBlockRemove"))
/* 43 */                 world.getBlockAt(xx, yy, zz).setType(Material.AIR);
/*    */             }
/*    */           }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\[�뜝�럥�닡�뜝�럩逾졾뜝�럥占싸몄낯占쎈쳴�뜝占�]banitem.jar
 * Qualified Name:     com.abcalvin.BanItem.WorldScanner
 * JD-Core Version:    0.6.0
 */