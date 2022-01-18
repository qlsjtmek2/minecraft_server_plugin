/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.ScriptLoader;
/*    */ import ch.njol.skript.Skript;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.event.inventory.InventoryType.SlotType;
/*    */ 
/*    */ public class ExprSlotType extends SimpleExpression<String>
/*    */ {
/*    */   public Class<? extends String> getReturnType()
/*    */   {
/* 16 */     return String.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 24 */     if (!ScriptLoader.isCurrentEvent(InventoryClickEvent.class)) {
/* 25 */       Skript.error("'Slot Type' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
/* 26 */       return false;
/*    */     }
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 32 */     return "slot type";
/*    */   }
/*    */   @Nullable
/*    */   protected String[] get(Event arg0) {
/* 37 */     InventoryClickEvent e = (InventoryClickEvent)arg0;
/* 38 */     String slottype = "";
/* 39 */     if (e.getSlotType().equals(InventoryType.SlotType.ARMOR))
/* 40 */       slottype = "Armour";
/* 41 */     else if (e.getSlotType().equals(InventoryType.SlotType.CONTAINER))
/* 42 */       slottype = "Container";
/* 43 */     else if (e.getSlotType().equals(InventoryType.SlotType.CRAFTING))
/* 44 */       slottype = "Crafting";
/* 45 */     else if (e.getSlotType().equals(InventoryType.SlotType.FUEL))
/* 46 */       slottype = "Fuel";
/* 47 */     else if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
/* 48 */       slottype = "Outside";
/* 49 */     else if (e.getSlotType().equals(InventoryType.SlotType.QUICKBAR))
/* 50 */       slottype = "Quickbar";
/* 51 */     else if (e.getSlotType().equals(InventoryType.SlotType.RESULT))
/* 52 */       slottype = "Result ";
/* 53 */     return new String[] { slottype };
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprSlotType
 * JD-Core Version:    0.6.0
 */