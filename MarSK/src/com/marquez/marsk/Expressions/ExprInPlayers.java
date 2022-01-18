/*    */ package com.marquez.marsk.Expressions;
/*    */ 
/*    */ import ch.njol.skript.Skript;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.skript.lang.util.SimpleExpression;
/*    */ import ch.njol.util.Kleenean;
/*    */ import com.marquez.marsk.AreaFile;
/*    */ import com.marquez.marsk.Locations;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class ExprInPlayers extends SimpleExpression<Player>
/*    */ {
/*    */   private Expression<String> area;
/*    */ 
/*    */   public Class<? extends Player> getReturnType()
/*    */   {
/* 26 */     return Player.class;
/*    */   }
/*    */ 
/*    */   public boolean isSingle() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
/* 34 */     String area = (String)arg0[0].getSingle(null);
/* 35 */     if (AreaFile.findArea(area) == -1) {
/* 36 */       Skript.error("'" + area + "' 은(는) 존재하지 않는 이름입니다.");
/* 37 */       return false;
/*    */     }
/* 39 */     this.area = arg0[0];
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 44 */     return "players in area " + this.area;
/*    */   }
/*    */   @Nullable
/*    */   protected Player[] get(Event arg0) {
/* 49 */     String area = (String)this.area.getSingle(arg0);
/* 50 */     if (AreaFile.findArea(area) == -1) {
/* 51 */       return null;
/*    */     }
/* 53 */     List player = new ArrayList();
/* 54 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 55 */       if (Locations.isInPosition(new Locations(AreaFile.foundArea(area)), p.getLocation(), p.getWorld())) {
/* 56 */         player.add(p);
/*    */       }
/*    */     }
/* 59 */     if (player.size() == 0) {
/* 60 */       return null;
/*    */     }
/* 62 */     Player[] playerlist = new Player[player.size()];
/* 63 */     for (int i = 0; i < player.size(); i++) {
/* 64 */       playerlist[i] = ((Player)player.get(i));
/*    */     }
/* 66 */     return playerlist;
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Expressions.ExprInPlayers
 * JD-Core Version:    0.6.0
 */