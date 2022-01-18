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
/*    */ public class CondIsContainer extends Condition
/*    */ {
/*    */   private Expression<String> slottype;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 21 */     if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
/* 22 */       Skript.error("'Slot Type' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
/* 23 */       return false;
/*    */     }
/* 25 */     this.slottype = arg0[0];
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 30 */     return "is container";
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0) {
/* 34 */     String slottype = (String)this.slottype.getSingle(arg0);
/*    */ 
/* 36 */     return slottype.equals("Container");
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Conditions.CondIsContainer
 * JD-Core Version:    0.6.0
 */