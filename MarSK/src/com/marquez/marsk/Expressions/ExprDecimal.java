/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import java.text.DecimalFormat;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprDecimal extends SimpleExpression<Number>
/*    */ {
/*    */   private Expression<Integer> line;
/*    */   private Expression<Number> number;
/*    */ 
/*    */   public Class<? extends Number> getReturnType()
/*    */   {
/* 19 */     return Number.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 27 */     this.line = arg0[0];
/* 28 */     this.number = arg0[1];
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 33 */     return "decimal";
/*    */   }
/*    */   @Nullable
/*    */   protected Number[] get(Event arg0) {
/* 38 */     Integer line = (Integer)this.line.getSingle(arg0);
/* 39 */     Number number = (Number)this.number.getSingle(arg0);
/* 40 */     String lineStr = "";
/* 41 */     for (int i = 0; i < line.intValue(); i++) {
/* 42 */       lineStr = lineStr + "#";
/*    */     }
/* 44 */     DecimalFormat format = new DecimalFormat("." + lineStr);
/* 45 */     String str = format.format(number);
/* 46 */     Number num = Float.valueOf(str);
/* 47 */     return new Number[] { num };
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprDecimal
 * JD-Core Version:    0.6.0
 */