/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprCharAt extends SimpleExpression<Character>
/*    */ {
/*    */   private Expression<Number> number;
/*    */   private Expression<String> text;
/*    */ 
/*    */   public Class<? extends Character> getReturnType()
/*    */   {
/* 17 */     return Character.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 25 */     this.number = arg0[0];
/* 26 */     this.text = arg0[1];
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 31 */     return "char at";
/*    */   }
/*    */   @Nullable
/*    */   protected Character[] get(Event arg0) {
/* 36 */     Number number = (Number)this.number.getSingle(arg0);
/* 37 */     String text = (String)this.text.getSingle(arg0);
/* 38 */     Character ch = Character.valueOf(text.charAt(number.intValue() - 1));
/* 39 */     return new Character[] { ch };
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprCharAt
 * JD-Core Version:    0.6.0
 */