/*    */ package com.marquez.marsk;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ public class Area
/*    */ {
/*    */   private String name;
/*    */   private List<Location> loc;
/*    */ 
/*    */   public Area(String name, List<Location> loc)
/*    */   {
/* 12 */     this.name = name;
/* 13 */     this.loc = loc;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 17 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 21 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public List<Location> getLoc() {
/* 25 */     return this.loc;
/*    */   }
/*    */ 
/*    */   public void setLoc(List<Location> loc) {
/* 29 */     this.loc = loc;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Area
 * JD-Core Version:    0.6.0
 */