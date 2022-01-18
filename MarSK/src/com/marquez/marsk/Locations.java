/*    */ package com.marquez.marsk;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class Locations extends JavaPlugin
/*    */ {
/* 12 */   public static HashMap<Player, List<String>> playerArea = new HashMap();
/*    */   public Location pos1;
/*    */   public Location pos2;
/*    */ 
/*    */   public Locations(Area area)
/*    */   {
/* 17 */     Location pos1 = (Location)area.getLoc().get(0);
/* 18 */     Location pos2 = (Location)area.getLoc().get(1);
/* 19 */     int[] x = swap((int)pos1.getX(), (int)pos2.getX());
/* 20 */     int[] y = swap((int)pos1.getY(), (int)pos2.getY());
/* 21 */     int[] z = swap((int)pos1.getZ(), (int)pos2.getZ());
/* 22 */     pos1.setX(x[0]);
/* 23 */     pos2.setX(x[1]);
/* 24 */     pos1.setY(y[0]);
/* 25 */     pos2.setY(y[1]);
/* 26 */     pos1.setZ(z[0]);
/* 27 */     pos2.setZ(z[1]);
/* 28 */     this.pos1 = pos1;
/* 29 */     this.pos2 = pos2;
/*    */   }
/*    */ 
/*    */   public int[] swap(int i1, int i2) {
/* 33 */     int[] a = { i1, i2 };
/* 34 */     if (i1 >= i2) {
/* 35 */       a = new int[] { i2, i1 };
/*    */     }
/* 37 */     return a;
/*    */   }
/*    */ 
/*    */   public static boolean isInPosition(Locations ls, Location loc, World world)
/*    */   {
/* 45 */     return (world.getName().equals(loc.getWorld().getName())) && 
/* 42 */       (ls.pos1.getBlockX() <= loc.getBlockX()) && (ls.pos2.getBlockX() >= loc.getBlockX()) && 
/* 43 */       (ls.pos1.getBlockY() <= loc.getBlockY()) && (ls.pos2.getBlockY() >= loc.getBlockY()) && 
/* 44 */       (ls.pos1.getBlockZ() <= loc.getBlockZ()) && (ls.pos2.getBlockZ() >= loc.getBlockZ());
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Locations
 * JD-Core Version:    0.6.0
 */