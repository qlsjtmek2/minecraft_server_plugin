/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.classes.Changer.ChangeMode;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import ch.njol.util.coll.CollectionUtils;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprMaxHealth extends SimpleExpression<Number>
/*    */ {
/*    */   private Expression<Player> player;
/*    */ 
/*    */   public Class<? extends Number> getReturnType()
/*    */   {
/* 18 */     return Number.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 26 */     this.player = arg0[0];
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 31 */     return "mh";
/*    */   }
/*    */   @Nullable
/*    */   protected Number[] get(Event arg0) {
/* 36 */     Player p = (Player)this.player.getSingle(arg0);
/* 37 */     return new Number[] { Integer.valueOf(p.getMaxHealth() / 2) };
/*    */   }
/*    */ 
/*    */   @Nullable
/*    */   public Class<?>[] acceptChange(Changer.ChangeMode mode) {
/* 43 */     if ((mode == Changer.ChangeMode.SET) || (mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.REMOVE)) {
/* 44 */       return (Class[])CollectionUtils.array(new Class[] { Number.class });
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode)
/*    */   {
/* 51 */     Player p = (Player)this.player.getSingle(e);
/* 52 */     Number number = (Number)delta[0];
/* 53 */     number = Double.valueOf(number.doubleValue() * 2.0D);
/* 54 */     p.setMaxHealth(number.intValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprMaxHealth
 * JD-Core Version:    0.6.0
 */