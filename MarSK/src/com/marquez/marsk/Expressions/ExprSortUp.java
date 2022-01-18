/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprSortUp extends SimpleExpression<Number>
/*    */ {
/*    */   private Expression<Number> numbers;
/*    */ 
/*    */   public Class<? extends Number> getReturnType()
/*    */   {
/* 17 */     return Number.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 25 */     this.numbers = arg0[0];
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 30 */     return "sort up";
/*    */   }
/*    */   @Nullable
/*    */   protected Number[] get(Event arg0) {
/* 35 */     Number[] array = (Number[])this.numbers.getAll(arg0);
/* 36 */     Arrays.sort(array);
/* 37 */     return array;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprSortUp
 * JD-Core Version:    0.6.0
 */