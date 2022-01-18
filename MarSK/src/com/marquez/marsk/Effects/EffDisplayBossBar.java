/*    */ package com.marquez.marsk.Effects;
/*    */ 
/*    */ import ch.njol.skript.lang.Effect;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import javax.annotation.Nullable;
/*    */ import me.confuser.barapi.BarAPI;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class EffDisplayBossBar extends Effect
/*    */ {
/*    */   private Expression<String> text;
/*    */   private Expression<Number> percent;
/*    */   private Expression<Player> player;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 18 */     this.text = arg0[0];
/* 19 */     this.percent = arg0[1];
/* 20 */     this.player = arg0[2];
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 25 */     return "display bar";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 29 */     String text = (String)this.text.getSingle(arg0);
/* 30 */     Number percent = (Number)this.percent.getSingle(arg0);
/* 31 */     Player player = (Player)this.player.getSingle(arg0);
/* 32 */     BarAPI.setMessage(player, text, percent.floatValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffDisplayBossBar
 * JD-Core Version:    0.6.0
 */