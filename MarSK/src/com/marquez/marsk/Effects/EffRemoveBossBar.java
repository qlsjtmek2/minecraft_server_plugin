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
/*    */ public class EffRemoveBossBar extends Effect
/*    */ {
/*    */   private Expression<Player> player;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 16 */     this.player = arg0[0];
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 21 */     return "display bar";
/*    */   }
/*    */ 
/*    */   protected void execute(Event arg0) {
/* 25 */     Player player = (Player)this.player.getSingle(arg0);
/* 26 */     BarAPI.removeBar(player);
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Effects.EffRemoveBossBar
 * JD-Core Version:    0.6.0
 */