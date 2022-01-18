/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprMaxDurability extends SimpleExpression<Number>
/*    */ {
/*    */   private Expression<Integer> id;
/*    */ 
/*    */   public Class<? extends Number> getReturnType()
/*    */   {
/* 16 */     return Number.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 24 */     this.id = arg0[0];
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 29 */     return "md";
/*    */   }
/*    */   @Nullable
/*    */   protected Number[] get(Event arg0) {
/* 34 */     Integer id = (Integer)this.id.getSingle(arg0);
/* 35 */     Material mat = Material.getMaterial(id.intValue());
/* 36 */     return new Number[] { Short.valueOf(mat.getMaxDurability()) };
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprMaxDurability
 * JD-Core Version:    0.6.0
 */