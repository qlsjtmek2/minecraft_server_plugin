/*    */ package com.marquez.marsk.Conditions;
/*    */ 
/*    */ import ch.njol.skript.ScriptLoader;
/*    */ import ch.njol.skript.Skript;
/*    */ import ch.njol.skript.lang.Condition;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class CondIsChest extends Condition
/*    */ {
/*    */   private Expression<String> inventorytype;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 20 */     if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
/* 21 */       Skript.error("'Inventory Type' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
/* 22 */       return false;
/*    */     }
/* 24 */     this.inventorytype = arg0[0];
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 29 */     return "is chest";
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0) {
/* 33 */     String inventorytype = (String)this.inventorytype.getSingle(arg0);
/*    */ 
/* 35 */     return inventorytype.equals("Chest");
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Conditions.CondIsChest
 * JD-Core Version:    0.6.0
 */