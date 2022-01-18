/*    */ package com.marquez.marsk.Conditions;
/*    */ 
/*    */ import ch.njol.skript.Skript;
/*    */ import ch.njol.skript.lang.Condition;
/*    */ import ch.njol.skript.lang.Expression;
/*    */ import ch.njol.skript.lang.SkriptParser.ParseResult;
/*    */ import ch.njol.util.Kleenean;
/*    */ import com.marquez.marsk.AreaFile;
/*    */ import com.marquez.marsk.Locations;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class CondIsInArea extends Condition
/*    */ {
/*    */   private Expression<Player> player;
/*    */   private Expression<String> area;
/*    */ 
/*    */   public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
/*    */   {
/* 23 */     String area = (String)arg0[1].getSingle(null);
/* 24 */     if (AreaFile.findArea(area) == -1) {
/* 25 */       Skript.error("'" + area + "' 은(는) 존재하지 않는 이름입니다.");
/* 26 */       return false;
/*    */     }
/* 28 */     this.player = arg0[0];
/* 29 */     this.area = arg0[1];
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString(@Nullable Event arg0, boolean arg1) {
/* 34 */     return ((Player)this.player.getSingle(arg0)).getName() + " is in area " + this.area;
/*    */   }
/*    */ 
/*    */   public boolean check(Event arg0) {
/* 38 */     String area = (String)this.area.getSingle(arg0);
/* 39 */     Player player = (Player)this.player.getSingle(arg0);
/* 40 */     if (AreaFile.findArea(area) == -1) {
/* 41 */       return false;
/*    */     }
/*    */ 
/* 44 */     return Locations.isInPosition(new Locations(AreaFile.foundArea(area)), player.getLocation(), player.getWorld());
/*    */   }
/*    */ }

/* Location:           C:\Users\신희곤\Desktop\서버 버킷폴더\NEW 액션RPG Project\액션RPG\plugins\[스크립트]MarSK.jar
 * Qualified Name:     com.marquez.marsk.Conditions.CondIsInArea
 * JD-Core Version:    0.6.0
 */