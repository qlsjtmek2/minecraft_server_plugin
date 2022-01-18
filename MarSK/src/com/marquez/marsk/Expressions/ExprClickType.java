/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class ExprClickType extends SimpleExpression<String>
/*    */ {
/*    */   public Class<? extends String> getReturnType()
/*    */   {
/* 14 */     return String.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 26 */     return "click type";
/*    */   }
/*    */   @Nullable
/*    */   protected String[] get(Event arg0) {
/* 31 */     InventoryClickEvent e = (InventoryClickEvent)arg0;
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     if (e.isLeftClick())
/* 34 */       sb.append("LeftClick ");
/* 35 */     if (e.isRightClick())
/* 36 */       sb.append("RightClick ");
/* 37 */     if (e.isShiftClick())
/* 38 */       sb.append("ShiftClick ");
/* 39 */     return new String[] { sb.toString() };
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprClickType
 * JD-Core Version:    0.6.0
 */